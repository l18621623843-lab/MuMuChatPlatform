package com.kk.mumuchat.common.core.utils.http;

import cn.hutool.core.net.url.UrlBuilder;

/**
 * Http客户端工具类
 *
 * @author xueyi
 */
public class HttpRequest extends cn.hutool.http.HttpRequest {

    /** @deprecated */
    @Deprecated
    public HttpRequest(String url) {
        super(url);
    }

    public HttpRequest(UrlBuilder url) {
        super(url);
    }
}
