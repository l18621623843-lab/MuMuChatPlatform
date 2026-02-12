package com.kk.mumuchat.common.core.utils.jwt;

import cn.hutool.jwt.JWT;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * JWT令牌封装
 *
 * @author xueyi
 */
@Data
public class Jwt implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private JWT jwt;

    public Jwt(JWT jwt) {
        this.jwt = jwt;
    }
}
