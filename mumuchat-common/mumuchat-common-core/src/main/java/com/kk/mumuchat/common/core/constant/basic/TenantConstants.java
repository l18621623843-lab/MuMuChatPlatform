package com.kk.mumuchat.common.core.constant.basic;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 租户通用常量
 *
 * @author xueyi
 */
public class TenantConstants {

    /** 租户字段名 */
    public static final String TENANT_ID = "tenant_id";

    /** 公共字段名 */
    public static final String COMMON_ID = "is_common";

    /** 公共租户Id */
    public static final Long COMMON_TENANT_ID = BaseConstants.COMMON_ID;

    /** 策略源标识 */
    public static final String ISOLATE = "#isolute";

    /** 主数据源标识 */
    public static final String MASTER = "#master";

    /** 手动数据源标识（调用对象中的sourceName属性） */
    public static final String SOURCE = "#sourceName";

    /** 数据源 */
    @Getter
    @AllArgsConstructor
    public enum Source {

        MASTER("master", "默认数据源"),
        SLAVE("slave", "从数据源"),
        REGISTER("slave", "注册数据源");

        private final String code;
        private final String info;

    }

    /**
     * 策略组类型
     * 除DEFAULT外，请跟字典表 te_strategy_source_type 保持一致
     * 自定义增加新类型时，调整字典表 te_strategy_source_type 对应数据值，并在此处增加对应枚举后即可生效
     * 默认策略：默认策略下，所有租户均使用默认数据源
     */
    @Getter
    @AllArgsConstructor
    public enum StrategyType {

        DEFAULT("default", "默认策略"),
        BPM("bpm", "工作流策略");

        private final String code;
        private final String info;

    }

    /**
     * 获取通用租户Id列表
     * <p>
     * 返回包含当前企业Id和公共租户Id的列表，用于查询数据时同时获取当前租户和公共数据
     * </p>
     *
     * @param enterpriseId 企业IDD
     * @return 包含当前企业Id和公共租户Id的列表，顺序为：当前企业Id、公共租户Id
     */
    public static List<Long> getCommonTenantIds(Long enterpriseId) {
        return new ArrayList<>(){{
            add(enterpriseId);
            add(COMMON_TENANT_ID);
        }};
    }
}
