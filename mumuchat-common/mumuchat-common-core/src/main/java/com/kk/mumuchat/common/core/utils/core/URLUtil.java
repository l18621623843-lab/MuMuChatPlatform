package com.kk.mumuchat.common.core.utils.core;

import com.alibaba.fastjson2.JSONObject;

import java.util.regex.Pattern;

/**
 * URL工具类
 *
 * @author mumuchat
 */
public class URLUtil extends cn.hutool.core.util.URLUtil {

    /**
     * 替换模板变量
     *
     * @param template 模板字段
     * @param params   入参对象
     * @return 替换后的模板字段
     */
    public static String replaceTemplateVariables(String template, JSONObject params) {
        if (StrUtil.isBlank(template) || ObjectUtil.isNull(params)) {
            return template;
        }
        return replaceTemplateVariables(template, params, "\\{\\{(.*?)}}");
    }

    /**
     * 替换模板变量
     *
     * @param template 模板字段
     * @param params   入参对象
     * @param regex    正则表达式
     * @return 替换后的模板字段
     */
    public static String replaceTemplateVariables(String template, JSONObject params, String regex) {
        if (StrUtil.hasBlank(template, regex) || ObjectUtil.isNull(params)) {
            return template;
        }
        return Pattern.compile(regex)
                .matcher(template)
                .replaceAll(matchResult -> {
                    String key = matchResult.group(1).trim();
                    return params.containsKey(key) ? params.getString(key) : matchResult.group();
                });
    }
}
