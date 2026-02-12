package com.kk.mumuchat.system.utils.V5.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 路由常量
 *
 * @author xueyi
 */
public class RouteConstants {

    /** 组件标识 */
    @Getter
    @AllArgsConstructor
    public enum ComponentType {

        LAYOUT("LAYOUT"),
        IFRAME("IFrameView");

        private final String code;

    }
}
