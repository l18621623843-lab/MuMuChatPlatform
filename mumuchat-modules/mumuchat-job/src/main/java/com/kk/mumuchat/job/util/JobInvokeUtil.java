package com.kk.mumuchat.job.util;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.kk.mumuchat.common.core.constant.basic.HttpConstants;
import com.kk.mumuchat.common.core.constant.basic.SecurityConstants;
import com.kk.mumuchat.common.core.context.SecurityContextHolder;
import com.kk.mumuchat.common.core.exception.ServiceException;
import com.kk.mumuchat.common.core.exception.UtilException;
import com.kk.mumuchat.common.core.properties.RemoteServiceProperties;
import com.kk.mumuchat.common.core.utils.core.CollUtil;
import com.kk.mumuchat.common.core.utils.core.MapUtil;
import com.kk.mumuchat.common.core.utils.core.NumberUtil;
import com.kk.mumuchat.common.core.utils.core.ObjectUtil;
import com.kk.mumuchat.common.core.utils.core.SpringUtil;
import com.kk.mumuchat.common.core.utils.core.StrUtil;
import com.kk.mumuchat.common.core.utils.http.HttpRequest;
import com.kk.mumuchat.common.core.web.result.R;
import com.kk.mumuchat.job.api.domain.dto.SysJobDto;
import com.kk.mumuchat.job.constant.ScheduleConstants;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 任务执行工具类
 *
 * @author xueyi
 */
@Slf4j
public class JobInvokeUtil {

    /** 应用是否正在关闭 */
    private static volatile boolean isShuttingDown = false;

    /**
     * 服务映射关系缓存
     * key: 服务Id, value: 服务映射Id
     */
    private static Map<String, String> serviceMappingMap;

    /**
     * Nacos命名服务实例（单例模式）
     * 用于服务发现，避免重复创建连接
     */
    private static volatile NamingService namingService;

    /**
     * 服务实例缓存
     * key: 服务映射ID, value: 服务实例信息
     * 用于缓存已发现的服务实例，减少频繁的服务发现调用
     */
    private static final Map<String, Instance> serviceInstanceCache = new ConcurrentHashMap<>();

    /**
     * 缓存超时时间（毫秒）
     * 默认60秒，超过此时间将重新获取服务实例
     */
    private static final long CACHE_TIMEOUT = 60 * 1000;

    /**
     * 缓存时间戳记录
     * key: 服务映射ID, value: 最后更新时间戳
     * 用于判断缓存是否过期
     */
    private static final Map<String, Long> cacheTimestamps = new ConcurrentHashMap<>();

    /**
     * 监听应用关闭事件
     */
    public static void onContextClosed() {
        isShuttingDown = true;
        // 清理资源
        clearResources();
    }

    /**
     * 清理静态资源
     */
    private static void clearResources() {
        if (namingService != null) {
            try {
                namingService.shutDown();
            } catch (Exception e) {
                log.warn("Failed to shutdown naming service", e);
            }
            namingService = null;
        }
        serviceInstanceCache.clear();
        cacheTimestamps.clear();
        serviceMappingMap = null;
    }

    /**
     * 获取Nacos命名服务实例
     * 采用双重检查锁定实现单例模式，确保线程安全
     *
     * @return NamingService Nacos命名服务实例
     * @throws NacosException Nacos连接异常
     */
    private static NamingService getNamingService() throws NacosException {
        if (namingService == null) {
            synchronized (JobInvokeUtil.class) {
                if (namingService == null) {
                    NacosDiscoveryProperties nacosDiscoveryProperties = SpringUtil.getBean(NacosDiscoveryProperties.class);
                    namingService = NacosFactory.createNamingService(nacosDiscoveryProperties.getNacosProperties());
                }
            }
        }
        return namingService;
    }

    /**
     * 获取缓存的服务实例
     * 首先检查缓存是否有效，如果有效则直接返回缓存实例
     * 如果缓存失效或不存在，则重新获取服务实例并更新缓存
     *
     * @param serviceMappingId 服务映射ID
     * @return Instance 服务实例信息，如果未找到返回null
     * @throws NacosException Nacos服务发现异常
     */
    private static Instance getCachedInstance(String serviceMappingId) throws NacosException {
        Long lastUpdate = cacheTimestamps.get(serviceMappingId);
        long currentTime = System.currentTimeMillis();

        // 检查缓存是否有效（未过期）
        if (lastUpdate != null && (currentTime - lastUpdate) < CACHE_TIMEOUT) {
            return serviceInstanceCache.get(serviceMappingId);
        }

        // 缓存失效或不存在，重新获取并更新缓存
        NamingService namingService = getNamingService();
        List<Instance> instances = namingService.getAllInstances(serviceMappingId);

        if (CollUtil.isNotEmpty(instances)) {
            Instance instance = instances.get(0);
            serviceInstanceCache.put(serviceMappingId, instance);
            cacheTimestamps.put(serviceMappingId, currentTime);
            return instance;
        }

        return null;
    }

    /**
     * 获取服务映射关系Map
     * key: 服务Id, value: 服务映射Id
     *
     * @return 服务映射关系Map
     */
    public static Map<String, String> getServiceMappingMap() {
        if (isShuttingDown) {
            return Collections.emptyMap();
        }
        if (MapUtil.isNotEmpty(serviceMappingMap)) {
            return serviceMappingMap;
        }
        serviceMappingMap = new HashMap<>();
        RemoteServiceProperties properties = SpringUtil.getBean(RemoteServiceProperties.class);
        Optional.ofNullable(properties)
                .map(RemoteServiceProperties::getMapping)
                .filter(CollUtil::isNotEmpty)
                .ifPresent(list ->
                        list.forEach(item ->
                                // 服务映射关系
                                serviceMappingMap.put(item.getServiceId(), StrUtil.getStrOrBlankElse(item.getMappingId(), item.getServiceId()))));
        return serviceMappingMap;
    }

    /**
     * 根据服务ID获取服务映射ID
     *
     * @param serviceId 服务ID
     * @return 服务映射ID，如果未找到则返回null
     */
    public static String getServiceMappingId(String serviceId) {
        Map<String, String> mappingMap = getServiceMappingMap();
        return mappingMap.get(serviceId);
    }

    /**
     * 执行方法
     *
     * @param sysJob 系统任务
     */
    public static void invokeMethod(SysJobDto sysJob) throws Exception {
        if (isShuttingDown) {
            log.warn("Application is shutting down, skipping job execution: {}", sysJob.getName());
            return;
        }
        ScheduleConstants.JobGroupType jobGroupType = ScheduleConstants.JobGroupType.getByCode(sysJob.getJobGroup());
        String enterpriseId = sysJob.getInvokeTenant();
        switch (jobGroupType) {
            case DEFAULT -> {
                String invokeTarget = sysJob.getInvokeTarget();
                String beanName = getBeanName(invokeTarget);
                String methodName = getMethodName(invokeTarget);
                List<Object[]> methodParams = getMethodParams(invokeTarget);
                if (!isValidClassName(beanName)) {
                    Object bean = SpringUtil.getBean(beanName);
                    invokeMethod(bean, methodName, methodParams, enterpriseId);
                } else {
                    Object bean = Class.forName(beanName).getDeclaredConstructor().newInstance();
                    invokeMethod(bean, methodName, methodParams, enterpriseId);
                }
            }
            case INNER_SYSTEM, EXTERNAL_SYSTEM -> {
                Map<String, String> headersMap = new HashMap<>();
                String aprUrl;
                // 内部接口调用
                if (ObjectUtil.equals(jobGroupType, ScheduleConstants.JobGroupType.INNER_SYSTEM)) {
                    try {
                        String serviceMappingId = JobInvokeUtil.getServiceMappingId(sysJob.getServerType());
                        if (StrUtil.isBlank(serviceMappingId)) {
                            throw new ServiceException("执行失败，服务{}不存在！", sysJob.getServerType());
                        }

                        // 使用缓存获取实例
                        Instance instance = getCachedInstance(serviceMappingId);
                        if (ObjectUtil.isNotNull(instance)) {
                            aprUrl = StrUtil.format("http://{}:{}{}", instance.getIp(), instance.getPort(),
                                    StrUtil.startWith(sysJob.getApiUrl(), StrUtil.SLASH)
                                            ? sysJob.getApiUrl()
                                            : StrUtil.SLASH + sysJob.getApiUrl());
                            headersMap.put(SecurityConstants.FROM_SOURCE, SecurityConstants.INNER);
                        } else {
                            throw new UtilException("【定时任务】没有找到服务实例，任务名: {}, 目标服务: {}", sysJob.getName(), sysJob.getServerType());
                        }
                    } catch (NacosException e) {
                        throw new UtilException("【定时任务】获取Nacos服务实例失败，任务名: {}，异常原因: ", sysJob.getName(), e);
                    }
                } else {
                    aprUrl = sysJob.getApiUrl();
                }
                HttpConstants.HttpType httpType = HttpConstants.HttpType.getByCode(sysJob.getHttpType());
                try {
                    String result = switch (httpType) {
                        case GET -> HttpRequest.get(aprUrl)
                                .headerMap(headersMap, Boolean.TRUE)
                                .execute().body();
                        case POST -> HttpRequest.post(aprUrl)
                                .headerMap(headersMap, Boolean.TRUE)
                                .execute().body();
                        case PUT -> HttpRequest.put(aprUrl)
                                .headerMap(headersMap, Boolean.TRUE)
                                .execute().body();
                        case DELETE -> HttpRequest.delete(aprUrl)
                                .headerMap(headersMap, Boolean.TRUE)
                                .execute().body();
                    };
                    sysJob.setResult(result);
                    Optional.ofNullable(result)
                            .map(item -> JSONObject.parseObject(item, R.class))
                            .ifPresent(info -> {
                                if (info.isOk()) {
                                    log.info("【定时任务】请求接口成功，任务名：{}，接口地址：{}，返回结果：{}", sysJob.getName(), aprUrl, result);
                                } else {
                                    log.error("【定时任务】请求接口失败，任务名：{}，接口地址：{}，返回结果：{}", sysJob.getName(), aprUrl, result);
                                    throw new ServiceException("任务执行失败，失败原因:{}", info.getMsg());
                                }
                            });
                } catch (Exception e) {
                    throw new UtilException("【定时任务】请求接口失败，任务名：{}，接口地址：{}，异常原因：", sysJob.getName(), aprUrl, e);
                }
            }
        }
    }

    /**
     * 调用任务方法
     *
     * @param bean         目标对象
     * @param methodName   方法名称
     * @param methodParams 方法参数
     * @param enterpriseId 租户Id
     */
    private static void invokeMethod(Object bean, String methodName, List<Object[]> methodParams, String enterpriseId)
            throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {
        try {
            if (StrUtil.isBlank(enterpriseId)) {
                SecurityContextHolder.setEnterpriseId(enterpriseId);
            }
            if (CollUtil.isNotEmpty(methodParams)) {
                Method method = bean.getClass().getMethod(methodName, getMethodParamsType(methodParams));
                method.invoke(bean, getMethodParamsValue(methodParams));
            } else {
                Method method = bean.getClass().getMethod(methodName);
                method.invoke(bean);
            }
        } finally {
            SecurityContextHolder.remove();
        }
    }

    /**
     * 校验是否为为class包名
     *
     * @param invokeTarget 名称
     * @return true是 false否
     */
    public static boolean isValidClassName(String invokeTarget) {
        return StrUtil.count(invokeTarget, ".") > NumberUtil.One;
    }

    /**
     * 获取bean名称
     *
     * @param invokeTarget 目标字符串
     * @return bean名称
     */
    public static String getBeanName(String invokeTarget) {
        String beanName = StrUtil.subBefore(invokeTarget, StrUtil.PARENTHESES_START);
        return StrUtil.subBeforeLast(beanName, StrUtil.DOT);
    }

    /**
     * 获取bean方法
     *
     * @param invokeTarget 目标字符串
     * @return method方法
     */
    public static String getMethodName(String invokeTarget) {
        String methodName = StrUtil.subBefore(invokeTarget, StrUtil.PARENTHESES_START);
        return StrUtil.subAfterLast(methodName, StrUtil.DOT);
    }

    /**
     * 获取method方法参数相关列表
     *
     * @param invokeTarget 目标字符串
     * @return method方法相关参数列表
     */
    public static List<Object[]> getMethodParams(String invokeTarget) {
        String methodStr = StrUtil.subBetween(invokeTarget, StrUtil.PARENTHESES_START, StrUtil.PARENTHESES_END);
        if (StrUtil.isEmpty(methodStr))
            return null;
        String[] methodParams = methodStr.split(",(?=([^\"']*[\"'][^\"']*[\"'])*[^\"']*$)");
        List<Object[]> clazz = new LinkedList<>();
        for (String methodParam : methodParams) {
            String str = StrUtil.trimToEmpty(methodParam);
            // String字符串类型，以'或"开头
            if (StrUtil.startWithAny(str, "'", "\"")) {
                clazz.add(new Object[]{StrUtil.sub(str, NumberUtil.One, str.length() - NumberUtil.One), String.class});
            }
            // boolean布尔类型，等于true或者false
            else if (StrUtil.TRUE.equalsIgnoreCase(str) || StrUtil.FALSE.equalsIgnoreCase(str)) {
                clazz.add(new Object[]{Boolean.valueOf(str), Boolean.class});
            }
            // long长整形，以L结尾
            else if (StrUtil.endWith(str, "L")) {
                clazz.add(new Object[]{Long.valueOf(StrUtil.sub(str, NumberUtil.Zero, str.length() - NumberUtil.One)), Long.class});
            }
            // double浮点类型，以D结尾
            else if (StrUtil.endWith(str, "D")) {
                clazz.add(new Object[]{Double.valueOf(StrUtil.sub(str, NumberUtil.Zero, str.length() - NumberUtil.One)), Double.class});
            }
            // 其他类型归类为整形
            else {
                clazz.add(new Object[]{Integer.valueOf(str), Integer.class});
            }
        }
        return clazz;
    }

    /**
     * 获取参数类型
     *
     * @param methodParams 参数相关列表
     * @return 参数类型列表
     */
    public static Class<?>[] getMethodParamsType(List<Object[]> methodParams) {
        Class<?>[] clazz = new Class<?>[methodParams.size()];
        int index = NumberUtil.Zero;
        for (Object[] os : methodParams) {
            clazz[index] = (Class<?>) os[NumberUtil.One];
            index++;
        }
        return clazz;
    }

    /**
     * 获取参数值
     *
     * @param methodParams 参数相关列表
     * @return 参数值列表
     */
    public static Object[] getMethodParamsValue(List<Object[]> methodParams) {
        Object[] clazz = new Object[methodParams.size()];
        int index = NumberUtil.Zero;
        for (Object[] os : methodParams) {
            clazz[index] = os[NumberUtil.Zero];
            index++;
        }
        return clazz;
    }
}
