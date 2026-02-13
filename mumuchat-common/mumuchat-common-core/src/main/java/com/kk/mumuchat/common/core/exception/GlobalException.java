package com.kk.mumuchat.common.core.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;

/**
 * 全局异常
 *
 * @author mumuchat
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class GlobalException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 错误提示 */
    private String message;

    /** 错误明细，内部调试错误 */
    private String detailMessage;

    public GlobalException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}