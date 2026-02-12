package com.kk.mumuchat.gen.domain.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Optional;

/**
 * 业务其它生成选项 数据传输对象
 *
 * @author xueyi
 */
@Data
public class GenTableOptionDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 默认配置 */
    private BasicInfo basicInfo;

    /** 菜单配置 */
    private MenuInfo menuInfo;

    /** 字段配置 */
    private FieldInfo fieldInfo;

    /** 接口配置 */
    private ApiInfo apiInfo;

    /**
     * 默认配置
     */
    @Data
    public static class BasicInfo implements Serializable {

        @Serial
        private static final long serialVersionUID = 1L;

        /** 多租户模式 */
        private String isTenant;

        /** 源策略模式 */
        private String sourceMode;
        /** 依赖缩写模式 */
        private String dependMode;
    }

    /**
     * 菜单配置
     */
    @Data
    public static class MenuInfo implements Serializable {

        @Serial
        private static final long serialVersionUID = 1L;

        /** 菜单生成Id */
        private String idGenerator;

        /** 归属模块 */
        private Long parentModuleId;

        /** 上级菜单 */
        private Long parentMenuId;

        /** 归属菜单祖籍 */
        private String parentMenuAncestors;
    }

    /**
     * 字段配置
     */
    @Data
    public static class FieldInfo implements Serializable {

        @Serial
        private static final long serialVersionUID = 1L;

        /** 树编码字段 */
        private Long treeCode;

        /** 树父编码字段 */
        private Long parentId;

        /** 树名称字段 */
        private Long treeName;

        /** 祖籍列表字段 */
        private Long ancestors;

        /** 序号字段 */
        private Long sort;

        /** 层级字段 */
        private Long level;

    }

    /**
     * 接口配置
     */
    @Data
    public static class ApiInfo implements Serializable {

        @Serial
        private static final long serialVersionUID = 1L;

        /** 列表查询 */
        private String apiList;

        /** 详情查询 */
        private String apiGetInfo;

        /** 新增 */
        private String apiAdd;

        /** 修改 */
        private String apiEdit;

        /** 存在状态修改 */
        private String hasApiES;

        /** 状态修改 */
        private String apiEditStatus;

        /** 批量删除 */
        private String apiBatchRemove;

        /** 导入 */
        private String apiImport;

        /** 导出 */
        private String apiExport;

        /** 缓存 */
        private String apiCache;

    }

    public BasicInfo getBasicInfo() {
        return Optional.ofNullable(basicInfo).orElse(new BasicInfo());
    }

    public MenuInfo getMenuInfo() {
        return Optional.ofNullable(menuInfo).orElse(new MenuInfo());
    }

    public FieldInfo getFieldInfo() {
        return Optional.ofNullable(fieldInfo).orElse(new FieldInfo());
    }

    public ApiInfo getApiInfo() {
        return Optional.ofNullable(apiInfo).orElse(new ApiInfo());
    }
}
