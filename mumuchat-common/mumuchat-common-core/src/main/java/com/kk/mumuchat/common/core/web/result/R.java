package com.kk.mumuchat.common.core.web.result;

import com.kk.mumuchat.common.core.constant.basic.Constants;
import com.kk.mumuchat.common.core.utils.core.NumberUtil;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 响应信息主体
 *
 * @author mumuchat
 */
@Data
public class R<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 成功 */
    public static final int SUCCESS = Constants.SUCCESS;

    /** 失败 */
    public static final int FAIL = Constants.FAIL;

    /** 状态码 */
    private int code;

    /** 提示信息 */
    private String msg;

    /** 数据 */
    private T data;

    public static R<Boolean> success(Integer rows) {
        return restResult(rows > NumberUtil.Zero, SUCCESS, null);
    }

    public static <T> R<T> ok() {
        return restResult(null, SUCCESS, null);
    }

    public static <T> R<T> ok(T data) {
        return restResult(data, SUCCESS, null);
    }

    public static <T> R<T> ok(T data, String msg) {
        return restResult(data, SUCCESS, msg);
    }

    public static <T> R<T> fail() {
        return restResult(null, FAIL, null);
    }

    public static <T> R<T> fail(String msg) {
        return restResult(null, FAIL, msg);
    }

    public static <T> R<T> fail(T data) {
        return restResult(data, FAIL, null);
    }

    public static <T> R<T> fail(T data, String msg) {
        return restResult(data, FAIL, msg);
    }

    public static <T> R<T> fail(int code, String msg) {
        return restResult(null, code, msg);
    }

    private static <T> R<T> restResult(T data, int code, String msg) {
        R<T> apiResult = new R<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

    public boolean isOk() {
        return this.code == SUCCESS;
    }

    public boolean isFail() {
        return this.code == FAIL;
    }
}
