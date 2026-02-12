package com.kk.mumuchat.common.core.exception;

import com.kk.mumuchat.common.core.utils.core.StrUtil;
import com.kk.mumuchat.common.core.web.error.ErrorCode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;

/**
 * 业务异常
 *
 * @author xueyi
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public final class ServiceException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 错误码 */
    private Integer code;

    /** 错误提示 */
    private String message;

    /** 错误明细，内部调试错误 */
    private String detailMessage;

    public ServiceException(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    public ServiceException(String message) {
        this(null, message);
    }

    public ServiceException(String message, Object... params) {
        this(null, StrUtil.format(message, params));
    }

    public ServiceException(Integer code, String message, Object... params) {
        this(code, StrUtil.format(message, params));
    }

    public ServiceException(ErrorCode errorCode) {
        this(errorCode.getCode(), errorCode.getMsg());
    }

    public ServiceException(ErrorCode errorCode, Object... params) {
        this(errorCode.getCode(), StrUtil.format(errorCode.getMsg(), params));
    }

    @Override
    public String getMessage() {
        return message;
    }
}