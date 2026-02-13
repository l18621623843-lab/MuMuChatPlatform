package com.kk.mumuchat.common.datasource.processor;

import com.baomidou.dynamic.datasource.processor.DsProcessor;
import com.kk.mumuchat.common.core.utils.core.ObjectUtil;
import com.kk.mumuchat.common.datasource.annotation.Isolate;
import com.kk.mumuchat.common.datasource.utils.DSUtil;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.kk.mumuchat.common.core.constant.basic.TenantConstants.ISOLATE;

/**
 * 租户库源策略
 *
 * @author mumuchat
 */
@Component
public class DsIsolateExpressionProcessor extends DsProcessor {

    @Override
    public boolean matches(String key) {
        return key.startsWith(ISOLATE);
    }

    @Override
    public String doDetermineDatasource(MethodInvocation invocation, String key) {
        return Optional.of(invocation.getMethod())
                .map(method -> AnnotationUtils.findAnnotation(method, Isolate.class))
                .filter(ObjectUtil::isNotNull).map(DSUtil::loadDs).orElseGet(() ->
                        Optional.ofNullable(invocation.getThis()).map(Object::getClass)
                                .map(clazz -> AnnotationUtils.findAnnotation(clazz, Isolate.class))
                                .filter(ObjectUtil::isNotNull).map(DSUtil::loadDs).orElse(null));
    }
}