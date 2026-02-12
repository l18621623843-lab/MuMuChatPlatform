package com.kk.mumuchat.common.core.constant.basic;

import com.kk.mumuchat.common.core.utils.core.EnumUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 应用通用常量
 *
 * @author xueyi
 */
public class AppConstants {

    /** 应用类型 */
    @Getter
    @AllArgsConstructor
    public enum AppType {

        WECHAT_MP("0", "微信公众号"),
        WECHAT_MA("1", "微信小程序"),
        SMS("2", "短信"),
        EMAIL("3", "邮件"),
        DING_DING("4", "钉钉"),
        TIKTOK_MA("6", "抖音小程序"),
        H5("7", "H5"),
        TIKTOK_MA_PARTNER("8", "抖音服务商小程序");

        private final String code;
        private final String info;

        public static AppType getByCode(String code) {
            return EnumUtil.getByCode(AppType.class, code);
        }

        public static AppType getByCodeElseNull(String code) {
            return EnumUtil.getByCodeElseNull(AppType.class, code);
        }
    }

    /** 微信支付类型 */
    @Getter
    @AllArgsConstructor
    public enum WechatPayType {

        WECHAT_ACCOUNT("wechat_account", "公众号支付"),
        WECHAT_APPLET("wechat_applet", "小程序支付"),
        APP("app", "app支付");

        private final String code;
        private final String info;

    }
}
