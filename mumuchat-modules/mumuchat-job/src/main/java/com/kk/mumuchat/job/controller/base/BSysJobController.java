package com.kk.mumuchat.job.controller.base;

import com.kk.mumuchat.common.core.constant.basic.BaseConstants;
import com.kk.mumuchat.common.core.constant.basic.HttpConstants;
import com.kk.mumuchat.common.core.exception.ServiceException;
import com.kk.mumuchat.common.core.utils.core.StrUtil;
import com.kk.mumuchat.common.web.entity.controller.BaseController;
import com.kk.mumuchat.job.api.domain.dto.SysJobDto;
import com.kk.mumuchat.job.api.domain.query.SysJobQuery;
import com.kk.mumuchat.job.api.utils.CronUtils;
import com.kk.mumuchat.job.constant.ScheduleConstants;
import com.kk.mumuchat.job.service.ISysJobService;
import com.kk.mumuchat.job.util.JobInvokeUtil;
import com.kk.mumuchat.job.util.ScheduleUtil;

import java.util.Optional;

/**
 * 定时任务|调度任务管理 | 通用 业务处理
 *
 * @author mumuchat
 */
public class BSysJobController extends BaseController<SysJobQuery, SysJobDto, ISysJobService> {

    /** 定义节点名称 */
    protected String getNodeName() {
        return "调度任务";
    }

    /**
     * 前置校验 增加/修改
     */
    protected void AEHandle(BaseConstants.Operate operate, SysJobDto job) {
        if (!CronUtils.isValid(job.getCronExpression())) {
            warn(StrUtil.format("{}{}{}失败，Cron表达式不正确", operate.getInfo(), getNodeName(), job.getName()));
        }
        switch (ScheduleConstants.JobGroupType.getByCode(job.getJobGroup())) {
            case DEFAULT -> {
                if (StrUtil.isBlank(job.getInvokeTarget())) {
                    warn(StrUtil.format("{}{}{}失败，调用目标字符串不能为空", operate.getInfo(), getNodeName(), job.getName()));
                } else if (StrUtil.containsIgnoreCase(job.getInvokeTarget(), HttpConstants.Type.LOOKUP_RMI.getCode())) {
                    warn(StrUtil.format("{}{}{}失败，目标字符串不允许'rmi'调用", operate.getInfo(), getNodeName(), job.getName()));
                } else if (StrUtil.containsAnyIgnoreCase(job.getInvokeTarget(), new String[]{HttpConstants.Type.LOOKUP_LDAP.getCode(), HttpConstants.Type.LOOKUP_LDAPS.getCode()})) {
                    warn(StrUtil.format("{}{}{}失败，目标字符串不允许'ldap(s)'调用", operate.getInfo(), getNodeName(), job.getName()));
                } else if (StrUtil.containsAnyIgnoreCase(job.getInvokeTarget(), new String[]{HttpConstants.Type.HTTP.getCode(), HttpConstants.Type.HTTPS.getCode()})) {
                    warn(StrUtil.format("{}{}{}失败，目标字符串不允许'http(s)'调用", operate.getInfo(), getNodeName(), job.getName()));
                } else if (StrUtil.containsAnyIgnoreCase(job.getInvokeTarget(), ScheduleConstants.JOB_ERROR_STR)) {
                    warn(StrUtil.format("{}{}{}失败，目标字符串存在违规", operate.getInfo(), getNodeName(), job.getName()));
                } else if (!ScheduleUtil.whiteList(job.getInvokeTarget())) {
                    warn(StrUtil.format("{}{}{}失败，目标字符串不在白名单内", operate.getInfo(), getNodeName(), job.getName()));
                }
                job.setApiUrl(null);
                job.setHttpType(null);
                job.setServerType(null);
            }
            case INNER_SYSTEM -> {
                String serviceMappingId = JobInvokeUtil.getServiceMappingId(job.getServerType());
                if (StrUtil.isBlank(serviceMappingId)) {
                    warn(StrUtil.format("{}{}{}失败，归属服务必须与枚举值匹配，请联系管理员！", operate.getInfo(), getNodeName(), job.getName()));
                }
                Optional.ofNullable(HttpConstants.HttpType.getByCodeElseNull(job.getHttpType()))
                        .orElseThrow(() -> new ServiceException(StrUtil.format("{}{}{}失败，请求类型必须与枚举值匹配，请联系管理员！", operate.getInfo(), getNodeName(), job.getName())));
                if (StrUtil.isBlank(job.getApiUrl())) {
                    warn(StrUtil.format("{}{}{}失败，API地址不能为空", operate.getInfo(), getNodeName(), job.getName()));
                }
                job.setInvokeTarget(null);
            }
            case EXTERNAL_SYSTEM -> {
                Optional.ofNullable(HttpConstants.HttpType.getByCodeElseNull(job.getHttpType()))
                        .orElseThrow(() -> new ServiceException(StrUtil.format("{}{}{}失败，请求类型必须与枚举值匹配，请联系管理员！", operate.getInfo(), getNodeName(), job.getName())));
                if (StrUtil.isBlank(job.getApiUrl())) {
                    warn(StrUtil.format("{}{}{}失败，API地址不能为空", operate.getInfo(), getNodeName(), job.getName()));
                    job.setInvokeTarget(null);
                }
            }
        }
    }
}