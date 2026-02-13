package com.kk.mumuchat.common.core.exception;

import java.io.Serial;

/**
 * 验证码错误异常类
 *
 * @author mumuchat
 */
public class CaptchaException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public CaptchaException(String msg) {
        super(msg);
    }
}
