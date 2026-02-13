package com.kk.mumuchat.common.core.exception;

import com.kk.mumuchat.common.core.utils.core.StrUtil;

import java.io.Serial;

/**
 * 工具类异常
 *
 * @author mumuchat
 */
public class UtilException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public UtilException(Throwable e) {
        super(e.getMessage(), e);
    }

    public UtilException(String message) {
        super(message);
    }

    public UtilException(String message, Object... params) {
        super(StrUtil.format(message, params));
    }

    public UtilException(Throwable throwable, String message) {
        super(message, throwable);
    }
}
