package com.kk.mumuchat.common.datasource.processor;

import com.baomidou.dynamic.datasource.processor.DsProcessor;
import com.kk.mumuchat.common.core.constant.basic.TenantConstants;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Component;

import static com.kk.mumuchat.common.core.constant.basic.TenantConstants.MASTER;

/**
 * 主库源策略
 *
 * @author mumuchat
 */
@Component
public class DsMasterExpressionProcessor extends DsProcessor {

    @Override
    public boolean matches(String key) {
        return key.startsWith(MASTER);
    }

    @Override
    public String doDetermineDatasource(MethodInvocation invocation, String key) {
        return TenantConstants.Source.MASTER.getCode();
    }
}