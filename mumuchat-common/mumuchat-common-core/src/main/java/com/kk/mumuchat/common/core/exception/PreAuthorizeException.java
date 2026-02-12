package com.kk.mumuchat.common.core.exception;

import lombok.NoArgsConstructor;

import java.io.Serial;

/**
 * 权限异常
 *
 * @author xueyi
 */
@NoArgsConstructor
public class PreAuthorizeException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

}
