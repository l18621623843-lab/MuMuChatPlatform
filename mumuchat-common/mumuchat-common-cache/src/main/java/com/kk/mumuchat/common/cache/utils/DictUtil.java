package com.kk.mumuchat.common.cache.utils;

import com.alibaba.fastjson2.JSON;
import com.kk.mumuchat.common.cache.service.CacheService;
import com.kk.mumuchat.common.core.constant.basic.DictConstants;
import com.kk.mumuchat.common.core.constant.basic.SecurityConstants;
import com.kk.mumuchat.common.core.context.SecurityContextHolder;
import com.kk.mumuchat.common.core.exception.UtilException;
import com.kk.mumuchat.common.core.utils.core.ClassUtil;
import com.kk.mumuchat.common.core.utils.core.ConvertUtil;
import com.kk.mumuchat.common.core.utils.core.ObjectUtil;
import com.kk.mumuchat.common.core.utils.core.SpringUtil;
import com.kk.mumuchat.common.core.utils.core.StrUtil;
import com.kk.mumuchat.system.api.dict.constant.ConfigConstants;
import com.kk.mumuchat.system.api.dict.domain.dto.SysConfigDto;
import com.kk.mumuchat.system.api.dict.domain.dto.SysDictDataDto;
import com.kk.mumuchat.system.api.dict.domain.dto.SysDictTypeDto;
import com.kk.mumuchat.system.api.dict.domain.dto.SysImExDto;
import com.kk.mumuchat.system.api.dict.feign.RemoteConfigService;
import com.kk.mumuchat.system.api.dict.feign.RemoteDictService;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字典缓存管理工具类
 *
 * @author mumuchat
 */
public class DictUtil {

    private static CacheService cacheService;

    private static CacheService getCacheService() {
        if (ObjectUtil.isNull(cacheService)) {
            cacheService = SpringUtil.getBean(CacheService.class);
        }
        return cacheService;
    }

    /**
     * 获取参数缓存
     *
     * @param code 参数编码
     * @return 参数数据
     */
    public static <T> T getConfigCacheToStr(String code) {
        return getConfigCache(code, String.class, StrUtil.EMPTY);
    }

    /**
     * 获取参数缓存
     *
     * @param code 参数编码
     * @return 参数数据
     */
    public static <T> T getConfigCacheToObj(String code) {
        return getConfigCache(code, Object.class, null);
    }

    /**
     * 获取参数缓存
     *
     * @param code         参数编码
     * @param clazz        数据类型
     * @param defaultValue 默认值
     * @return 参数数据
     */
    @SuppressWarnings(value = {"unchecked"})
    public static <T> T getConfigCache(String code, Class<?> clazz, Object defaultValue) {
        SysConfigDto config = getCacheService().getCacheMapValue(ConfigConstants.CacheType.ROUTE_CONFIG_KEY, code);
        if (ObjectUtil.isNull(config)) {
            return (T) defaultValue;
        }
        String value;
        if (StrUtil.equals(DictConstants.DicCacheType.TENANT.getCode(), config.getCacheType())) {
            value = getCacheService().getCacheMapValue(ConfigConstants.CacheType.SYS_CONFIG_KEY, code);
            if (ObjectUtil.isNull(value)) {
                SpringUtil.getBean(RemoteConfigService.class).syncCacheInner();
                value = getCacheService().getCacheMapValue(ConfigConstants.CacheType.SYS_CONFIG_KEY, code);
            }
        } else {
            value = SecurityContextHolder.setEnterpriseIdFun(SecurityConstants.COMMON_TENANT_ID, () ->
                    getCacheService().getCacheMapValue(ConfigConstants.CacheType.TE_CONFIG_KEY, code));
        }
        Object obj = ClassUtil.equals(Object.class, clazz)
                ? value
                : ClassUtil.isCollection(clazz)
                ? JSON.parseArray(value, clazz)
                : ClassUtil.isSimpleType(clazz)
                ? ConvertUtil.convert(clazz, value, defaultValue)
                : JSON.parseObject(value, clazz);
        return (T) (ObjectUtil.isNotNull(obj) ? obj : defaultValue);
    }

    /**
     * 获取字典缓存
     *
     * @param codes 字典编码集合
     * @return 字典数据列表
     */
    public static Map<String, List<SysDictDataDto>> getDictCache(Collection<String> codes) {
        Map<String, List<SysDictDataDto>> map = new HashMap<>();
        codes.forEach(code -> map.put(code, getDictCache(code)));
        return map;
    }

    /**
     * 获取字典缓存
     *
     * @param code 字典编码
     * @return 字典数据列表
     */
    public static List<SysDictDataDto> getDictCache(String code) {
        SysDictTypeDto type = getCacheService().getCacheMapValue(ConfigConstants.CacheType.ROUTE_DICT_KEY, code);
        if (ObjectUtil.isNull(type)) {
            return null;
        }
        List<SysDictDataDto> dictList;
        if (StrUtil.equals(DictConstants.DicCacheType.TENANT.getCode(), type.getCacheType())) {
            dictList = getCacheService().getCacheMapValue(ConfigConstants.CacheType.SYS_DICT_KEY, code);
            if (ObjectUtil.isNull(dictList)) {
                SpringUtil.getBean(RemoteDictService.class).syncCacheInner();
                dictList = getCacheService().getCacheMapValue(ConfigConstants.CacheType.SYS_DICT_KEY, code);
            }
        } else {
            dictList = SecurityContextHolder.setEnterpriseIdFun(SecurityConstants.COMMON_TENANT_ID, () ->
                    getCacheService().getCacheMapValue(ConfigConstants.CacheType.TE_DICT_KEY, code));
        }
        return dictList;
    }

    /**
     * 获取导入导出配置缓存
     *
     * @param exCode 配置编码
     * @return 配置数据
     */
    public static SysImExDto getExCache(String exCode) {
        SysImExDto exInfo = getCacheService().getCacheMapValue(ConfigConstants.CacheType.ROUTE_IM_EX_KEY, exCode);
        if (ObjectUtil.isNull(exInfo)) {
            return null;
        }
        SysImExDto value;
        if (StrUtil.equals(DictConstants.DicCacheType.TENANT.getCode(), exInfo.getCacheType())) {
            value = getCacheService().getCacheMapValue(ConfigConstants.CacheType.SYS_IM_EX_KEY, exCode);
            if (ObjectUtil.isNull(value)) {
                SpringUtil.getBean(RemoteConfigService.class).syncCacheInner();
                value = getCacheService().getCacheMapValue(ConfigConstants.CacheType.SYS_IM_EX_KEY, exCode);
            }
        } else {
            value = SecurityContextHolder.setEnterpriseIdFun(SecurityConstants.COMMON_TENANT_ID, () ->
                    getCacheService().getCacheMapValue(ConfigConstants.CacheType.TE_IM_EX_KEY, exCode));
        }
        return value;
    }

    /**
     * 获取导入配置缓存
     *
     * @param exCode 配置编码
     * @return 配置数据
     */
    public static String getExImportCache(String exCode) {
        SysImExDto exInfo = getExCache(exCode);
        if (ObjectUtil.isNotNull(exInfo) && StrUtil.isNotBlank(exInfo.getImportConfig())) {
            return exInfo.getImportConfig();
        } else {
            throw new UtilException("编码{}对应的导入配置不存在", exCode);
        }
    }

    /**
     * 获取导出配置缓存
     *
     * @param exCode 配置编码
     * @return 配置数据
     */
    public static String getExExportCache(String exCode) {
        SysImExDto exInfo = getExCache(exCode);
        if (ObjectUtil.isNotNull(exInfo) && StrUtil.isNotBlank(exInfo.getExportConfig())) {
            return exInfo.getExportConfig();
        } else {
            throw new UtilException("编码{}对应的导出配置不存在", exCode);
        }
    }
}
