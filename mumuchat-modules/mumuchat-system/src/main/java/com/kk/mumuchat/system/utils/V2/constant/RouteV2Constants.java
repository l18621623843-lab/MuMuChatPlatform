package com.kk.mumuchat.system.utils.V2.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 路由常量
 *
 * @author mumuchat
 */
public class RouteV2Constants {

    /** 组件标识 */
    @Getter
    @AllArgsConstructor
    public enum ComponentType {

        LAYOUT("LAYOUT"),
        IFRAME("IFrame");

        private final String code;

    }
}
