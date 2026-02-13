package com.kk.mumuchat.common.core.utils.http;

import cn.hutool.http.HttpConfig;
import cn.hutool.http.HttpConnection;

import java.nio.charset.Charset;

/**
 * Http客户端工具类
 *
 * @author mumuchat
 */
public class HttpResponse extends cn.hutool.http.HttpResponse {

    protected HttpResponse(HttpConnection httpConnection, HttpConfig config, Charset charset, boolean isAsync, boolean isIgnoreBody) {
        super(httpConnection, config, charset, isAsync, isIgnoreBody);
    }
}