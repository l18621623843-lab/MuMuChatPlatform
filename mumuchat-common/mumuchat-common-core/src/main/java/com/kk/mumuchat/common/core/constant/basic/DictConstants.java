package com.kk.mumuchat.common.core.constant.basic;

import com.kk.mumuchat.common.core.utils.core.BooleanUtil;
import com.kk.mumuchat.common.core.utils.core.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 字典通用常量
 *
 * @author xueyi
 */
public class DictConstants {

    /** 字典类型 */
    @Getter
    @AllArgsConstructor
    public enum DictType {

        SYS_NORMAL_DISABLE("sys_normal_disable", "系统开关列表"),
        SYS_SHOW_HIDE("sys_show_hide", "常规：显隐列表"),
        SYS_COMMON_PRIVATE("sys_common_private", "常规：公共私有列表"),
        SYS_YES_NO("sys_yes_no", "常规：是否列表"),
        SYS_USER_SEX("sys_user_sex", "用户性别列表");

        private final String code;
        private final String info;

    }

    /** 常规：是否列表（Y是 N否） */
    @Getter
    @AllArgsConstructor
    public enum DicYesNo {

        YES("Y", "是"),
        NO("N", "否");

        private final String code;
        private final String info;

        public static String getByBool(Boolean bool) {
            return BooleanUtil.isTrue(bool) ? YES.getCode() : NO.getCode();
        }

        public static Boolean isYes(String code) {
            return YES.getCode().equals(code);
        }

        public static Boolean isNo(String code) {
            return NO.getCode().equals(code);
        }
    }

    /** 常规：删除状态列表（0正常 1删除） */
    @Getter
    @AllArgsConstructor
    public enum DicDelStatus {

        NORMAL("0", "正常"),
        DEL("1", "删除");

        private final String code;
        private final String info;

    }

    /** 常规：显隐列表（0显示 1隐藏） */
    @Getter
    @AllArgsConstructor
    public enum DicShowHide {

        SHOW("0", "显示"),
        HIDE("1", "隐藏");

        private final String code;
        private final String info;

    }

    /** 字典：功能状态列表（Y开启 N关闭） */
    @Getter
    @AllArgsConstructor
    public enum DicFunStatusEnum {

        OPEN("Y", "开启"),
        CLOSE("N", "关闭");

        private final String code;
        private final String info;
    }

    /** 常规：公共私有列表（0公共 1私有） */
    @Getter
    @AllArgsConstructor
    public enum DicCommonPrivate {

        COMMON("0", "公共"),
        PRIVATE("1", "私有");

        private final String code;
        private final String info;

        /** 是否公共 */
        public static boolean isCommon(String code) {
            return StrUtil.equals(COMMON.getCode(), code);
        }

        /** 是否私有 */
        public static boolean isPrivate(String code) {
            return StrUtil.equals(PRIVATE.getCode(), code);
        }
    }

    /** 常规：状态列表（0正常 1失败） */
    @Getter
    @AllArgsConstructor
    public enum DicStatus {

        NORMAL("0", "正常"),
        FAIL("1", "失败");

        private final String code;
        private final String info;

    }

    /** 字典：字典数据类型列表（0默认 1只增 2只减 3只读） */
    @Getter
    @AllArgsConstructor
    public enum DicDataType {

        DEFAULT("0", "默认"),
        INCREASE("1", "只增"),
        SUBTRACT("2", "只减"),
        READ("3", "只读");

        private final String code;
        private final String info;

    }

    /** 字典：缓存类型列表（0租户 1全局） */
    @Getter
    @AllArgsConstructor
    public enum DicCacheType {

        TENANT("0", "租户"),
        OVERALL("1", "全局");

        private final String code;
        private final String info;

    }

    /** 常规：日志类型列表（0登录 1退出） */
    @Getter
    @AllArgsConstructor
    public enum LogType {

        LOGIN("0", "登录"),
        OUT("1", "退出");

        private final String code;
        private final String info;

    }
}
