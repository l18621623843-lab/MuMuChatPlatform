package com.kk.mumuchat.common.core.web.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.kk.mumuchat.common.core.constant.basic.SecurityConstants;
import com.kk.mumuchat.common.core.context.SecurityContextHolder;
import com.kk.mumuchat.common.core.utils.core.ObjectUtil;
import lombok.SneakyThrows;

import java.math.BigInteger;

/**
 * 租户标识序列化器
 *
 * @author mumuchat
 */
public class EnterpriseSerializer extends JsonSerializer<Long> {

    /**
     * 租户标识序列化
     * 内部请求允许返回租户Id
     *
     * @param enterpriseId 企业Id
     * @param gen          序列化器
     * @param provider     序列化器提供者
     */
    @Override
    @SneakyThrows
    public void serialize(Long enterpriseId, JsonGenerator gen, SerializerProvider provider) {
        if (ObjectUtil.isNotNull(enterpriseId)) {
            // 内部调用
            if (SecurityContextHolder.isInner()) {
                gen.writeNumber(enterpriseId);
                return;
            }
            // 租管级租户
            else if (SecurityConstants.TenantType.isLessor(SecurityContextHolder.getIsLessor())) {
                gen.writeNumber(enterpriseId);
                return;
            }
        }
        gen.writeNumber((BigInteger) null);
    }
}