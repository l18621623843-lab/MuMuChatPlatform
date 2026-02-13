package com.kk.mumuchat.gen.util;

import com.kk.mumuchat.common.core.constant.basic.DictConstants;
import com.kk.mumuchat.common.core.utils.core.ArrayUtil;
import com.kk.mumuchat.common.core.utils.core.CollUtil;
import com.kk.mumuchat.common.core.utils.core.NumberUtil;
import com.kk.mumuchat.common.core.utils.core.ObjectUtil;
import com.kk.mumuchat.common.core.utils.core.ReUtil;
import com.kk.mumuchat.common.core.utils.core.StrUtil;
import com.kk.mumuchat.gen.config.GenConfig;
import com.kk.mumuchat.gen.constant.GenConstants;
import com.kk.mumuchat.gen.domain.dto.GenTableColumnDto;
import com.kk.mumuchat.gen.domain.dto.GenTableDto;
import com.kk.mumuchat.gen.domain.dto.GenTableOptionDto;
import com.kk.mumuchat.system.api.authority.constant.AuthorityConstants;

import java.io.File;
import java.util.List;

/**
 * 代码生成器 工具类
 *
 * @author mumuchat
 */
public class GenUtil {

    /**
     * 初始化表信息
     */
    public static void initTable(GenTableDto genTable) {
        genTable.setClassName(StrUtil.convertToCamelCase(genTable.getName()));
        getRemoveItem(genTable);
        genTable.setTplCategory(GenConstants.TemplateType.BASE.getCode());
        genTable.setBusinessName(getBusinessName(genTable.getName()));
        genTable.setFunctionName(replaceText(genTable.getComment()));
        genTable.setAuthorityName(getAuthorityName(genTable));
        genTable.setFunctionAuthor(GenConfig.getAuthor());
        if (StrUtil.isNotBlank(GenConfig.getUiPath()))
            genTable.setUiPath(GenConfig.getUiPath());
    }

    /**
     * 初始化其它生成选项
     */
    public static void initTableOptions(List<GenTableColumnDto> columnList, GenTableDto table) {
        GenTableOptionDto optionInfo = new GenTableOptionDto();
        GenTableOptionDto.MenuInfo menuInfo = new GenTableOptionDto.MenuInfo();
        // 1.设置默认模块
        menuInfo.setParentModuleId(AuthorityConstants.MODULE_DEFAULT_NODE);
        // 2.设置默认菜单
        menuInfo.setParentMenuId(AuthorityConstants.MENU_TOP_NODE);

        GenTableOptionDto.BasicInfo basicInfo = new GenTableOptionDto.BasicInfo();

        // 3.检测是否为多租户模式
        String[] javaFields = columnList.stream().map(GenTableColumnDto::getJavaField).toArray(String[]::new);
        String isTenant = ArrayUtil.containsAny(GenConfig.getEntity().getBack().getTenant(), javaFields)
                ? DictConstants.DicYesNo.YES.getCode()
                : DictConstants.DicYesNo.NO.getCode();
        basicInfo.setIsTenant(isTenant);
        // 4.设置默认源策略模式
        String sourceMode = StrUtil.equals(basicInfo.getIsTenant(), DictConstants.DicYesNo.YES.getCode())
                ? GenConstants.SourceMode.ISOLATE.getCode()
                : GenConstants.SourceMode.MASTER.getCode();
        basicInfo.setSourceMode(sourceMode);
        // 5.设置默认依赖缩写模式
        basicInfo.setDependMode(DictConstants.DicYesNo.NO.getCode());

        // 6.初始化接口配置
        GenTableOptionDto.ApiInfo apiInfo = new GenTableOptionDto.ApiInfo();
        apiInfo.setApiList(DictConstants.DicYesNo.YES.getCode());
        apiInfo.setApiGetInfo(DictConstants.DicYesNo.YES.getCode());
        apiInfo.setApiAdd(DictConstants.DicYesNo.YES.getCode());
        apiInfo.setApiEdit(DictConstants.DicYesNo.YES.getCode());
        apiInfo.setApiBatchRemove(DictConstants.DicYesNo.YES.getCode());
        // 是否可配置状态变更接口
        apiInfo.setHasApiES(DictConstants.DicYesNo.NO.getCode());
        apiInfo.setApiEditStatus(DictConstants.DicYesNo.NO.getCode());
        apiInfo.setApiImport(DictConstants.DicYesNo.NO.getCode());
        apiInfo.setApiExport(DictConstants.DicYesNo.NO.getCode());
        apiInfo.setApiCache(DictConstants.DicYesNo.NO.getCode());

        GenTableOptionDto.FieldInfo fieldInfo = new GenTableOptionDto.FieldInfo();
        columnList.forEach(column -> {
            GenConstants.OptionField optionField = GenConstants.OptionField.getByCode(column.getJavaField());
            if (ObjectUtil.isNotNull(optionField)) {
                switch (optionField) {
                    case ID -> {
                        if (column.getIsPk()) {
                            fieldInfo.setTreeCode(column.getId());
                        }
                    }
                    case NAME -> fieldInfo.setTreeName(column.getId());
                    case STATUS -> {
                        apiInfo.setHasApiES(DictConstants.DicYesNo.YES.getCode());
                        apiInfo.setApiEditStatus(DictConstants.DicYesNo.YES.getCode());
                    }
                    case SORT -> fieldInfo.setSort(column.getId());
                    case PARENT_ID -> {
                        fieldInfo.setParentId(column.getId());
                        table.setTplCategory(GenConstants.TemplateType.TREE.getCode());
                    }
                    case ANCESTORS -> fieldInfo.setAncestors(column.getId());
                    case LEVEL -> fieldInfo.setLevel(column.getId());
                }
            }
        });
        optionInfo.setBasicInfo(basicInfo);
        optionInfo.setMenuInfo(menuInfo);
        optionInfo.setFieldInfo(fieldInfo);
        optionInfo.setApiInfo(apiInfo);
        table.setOptions(optionInfo);
    }

    /**
     * 初始化列属性字段
     */
    public static void initColumnField(GenTableColumnDto column, GenTableDto table) {
        String dataType = column.getType();
        column.setTableId(table.getId());
        column.setCreateBy(table.getCreateBy());
        // 设置java字段名
        column.setJavaField(StrUtil.toCamelCase(column.getName()));
        String javaField = column.getJavaField();
        // 设置默认类型
        column.setJavaType(GenConstants.JavaType.STRING.getCode());
        // 设置默认显示类型
        column.setHtmlType(GenConstants.DisplayType.INPUT.getCode());
        // 设置默认查询类型（长整型防精度丢失，到前端会自动转成字符串，故使用文本框）
        column.setQueryType(GenConstants.QueryType.EQ.getCode());
        if (ArrayUtil.contains(GenConfig.getDataBase().getTypeStr(), dataType) && !StrUtil.equals(column.getComment(), column.readNameNoSuffix())) {
            column.setHtmlType(GenConstants.DisplayType.SELECT.getCode());
            if (StrUtil.contains(column.getComment(), GenConstants.GenCheck.NORMAL_DISABLE.getInfo())) {
                column.setHtmlType(GenConstants.DisplayType.RADIO_BUTTON_GROUP.getCode());
                column.setDictType(DictConstants.DictType.SYS_NORMAL_DISABLE.getCode());
            } else if (StrUtil.contains(column.getComment(), GenConstants.GenCheck.SHOW_HIDE.getInfo())) {
                column.setHtmlType(GenConstants.DisplayType.RADIO_BUTTON_GROUP.getCode());
                column.setDictType(DictConstants.DictType.SYS_SHOW_HIDE.getCode());
            } else if (StrUtil.contains(column.getComment(), GenConstants.GenCheck.YES_NO.getInfo())) {
                column.setHtmlType(GenConstants.DisplayType.RADIO_BUTTON_GROUP.getCode());
                column.setDictType(DictConstants.DictType.SYS_YES_NO.getCode());
            }
        } else if (ArrayUtil.contains(GenConfig.getDataBase().getTypeText(), dataType)) {
            column.setHtmlType(GenConstants.DisplayType.INPUT_TEXTAREA.getCode());
        } else if (ArrayUtil.contains(GenConfig.getDataBase().getTypeDate(), dataType)) {
            column.setJavaType(GenConstants.JavaType.DATE.getCode());
            column.setHtmlType(GenConstants.DisplayType.DATE_PICKER.getCode());
        } else if (ArrayUtil.contains(GenConfig.getDataBase().getTypeFloat(), dataType)) {
            column.setHtmlType(GenConstants.DisplayType.INPUT_NUMBER.getCode());
            column.setJavaType(GenConstants.JavaType.BIG_DECIMAL.getCode());
        } else if (ArrayUtil.contains(GenConfig.getDataBase().getTypeNumber(), dataType)) {
            column.setHtmlType(GenConstants.DisplayType.INPUT_NUMBER.getCode());
            column.setJavaType(GenConstants.JavaType.INTEGER.getCode());
        } else if (ArrayUtil.contains(GenConfig.getDataBase().getTypeLong(), dataType)) {
            column.setJavaType(GenConstants.JavaType.LONG.getCode());
        }

        // 插入字段（默认除必须移除的参数外所有字段都需要插入）
        boolean mustCheck = ArrayUtil.contains(GenConfig.getEntity().getMustHide(), javaField) || column.getIsPk();
        column.setIsInsert(!(ArrayUtil.contains(GenConfig.getOperate().getNotInsert(), javaField) || mustCheck));
        // 查看字段
        column.setIsView(!(ArrayUtil.contains(GenConfig.getOperate().getNotView(), javaField) || mustCheck));
        // 编辑字段
        column.setIsEdit(!(ArrayUtil.contains(GenConfig.getOperate().getNotEdit(), javaField) || mustCheck));
        // 列表字段
        column.setIsList(!(ArrayUtil.contains(GenConfig.getOperate().getNotList(), javaField) || mustCheck));
        // 查询字段
        column.setIsQuery(!(ArrayUtil.contains(GenConfig.getOperate().getNotQuery(), javaField) || mustCheck));
        // 导入字段
        column.setIsImport(!(ArrayUtil.contains(GenConfig.getOperate().getNotImport(), javaField) || mustCheck));
        // 导出字段
        column.setIsExport(!(ArrayUtil.contains(GenConfig.getOperate().getNotExport(), javaField) || mustCheck));
        // 隐藏字段
        column.setIsHide(ArrayUtil.contains(GenConfig.getEntity().getMustHide(), javaField));
        // 掩蔽字段（默认不掩蔽）
        column.setIsCover(Boolean.FALSE);

        // 特殊指定
        GenConstants.GenField field = GenConstants.GenField.getByCode(javaField);
        if (ObjectUtil.isNotNull(field)) {
            switch (field) {
                case NAME -> column.setQueryType(GenConstants.QueryType.LIKE.getCode());
                case SEX -> column.setDictType(DictConstants.DictType.SYS_USER_SEX.getCode());
                case LOGO, IMAGE -> column.setHtmlType(GenConstants.DisplayType.IMAGE_UPLOAD.getCode());
                case FILE -> column.setHtmlType(GenConstants.DisplayType.FILE_UPLOAD.getCode());
                case COMMENT -> column.setHtmlType(GenConstants.DisplayType.TINYMCE.getCode());
                case REMARK -> column.setHtmlType(GenConstants.DisplayType.INPUT_TEXTAREA.getCode());
            }
        }
        // 最终校验
        basicInitColumn(column);
    }

    /**
     * 最终校验列属性字段
     */
    public static void updateCheckColumn(GenTableDto table) {
        GenTableOptionDto objectJson = table.getOptions();
        GenTableOptionDto.FieldInfo fieldInfo = objectJson.getFieldInfo();
        table.getSubList().forEach(column -> {
            if (StrUtil.equalsAny(table.getTplCategory(), GenConstants.TemplateType.TREE.getCode())) {
                if (ObjectUtil.equals(column.getId(), fieldInfo.getParentId())) {
                    column.setJavaField(GenConstants.OptionField.PARENT_ID.getCode());
                } else if (ObjectUtil.equals(column.getId(), fieldInfo.getAncestors())) {
                    column.setJavaField(GenConstants.OptionField.ANCESTORS.getCode());
                }
            }
            // 最终校验
            basicInitColumn(column);
        });
    }

    /**
     * 最终校验列属性字段
     */
    public static void basicInitColumn(GenTableColumnDto column) {
        // 校验是否需要隐藏
        boolean isMustHide = ArrayUtil.contains(GenConfig.getEntity().getMustHide(), column.getName());
        if (column.getIsHide() || isMustHide) {
            if (isMustHide) {
                column.setIsHide(Boolean.TRUE);
            }
            column.setIsView(Boolean.FALSE);
            column.setIsInsert(Boolean.FALSE);
            column.setIsEdit(Boolean.FALSE);
            column.setIsImport(Boolean.FALSE);
            column.setIsExport(Boolean.FALSE);
            column.setIsUnique(Boolean.FALSE);
            column.setIsRequired(Boolean.FALSE);
            column.setIsList(Boolean.FALSE);
            column.setIsQuery(Boolean.FALSE);
        }
    }

    /**
     * 获取符合的替换前缀组 | 获取包名 | 获取模块名
     *
     * @param genTable 业务表对象
     */
    public static void getRemoveItem(GenTableDto genTable) {
        if (GenConfig.getAutoRemovePre() && CollUtil.isNotEmpty(GenConfig.getRemoveLists())) {
            for (GenConfig.RemoveItem removeItem : GenConfig.getRemoveLists()) {
                if (StrUtil.equals(StrUtil.sub(genTable.getName(), NumberUtil.Zero, removeItem.getPrefix().length()), removeItem.getPrefix())) {
                    genTable.setPrefix(StrUtil.convertToCamelCase(removeItem.getPrefix()));
                    genTable.setRdPackageName(removeItem.getRdPackageName());
                    genTable.setModuleName(getModuleName(removeItem.getRdPackageName()));
                    String fePackageName = StrUtil.isNotBlank(removeItem.getFePackageName()) ? removeItem.getFePackageName() : genTable.getModuleName();
                    genTable.setFePackageName(fePackageName);
                    genTable.setGenPath(StrUtil.isNotBlank(removeItem.getBackPackageRoute()) ? (System.getProperty("user.dir") + StrUtil.replace(removeItem.getBackPackageRoute(), StrUtil.SLASH, File.separator) + File.separator + "src" + File.separator) : (System.getProperty("user.dir") + File.separator + "src" + File.separator));
                    genTable.setUiPath(StrUtil.isNotBlank(GenConfig.getUiPath()) ? (System.getProperty("user.dir") + StrUtil.replace(GenConfig.getUiPath(), StrUtil.SLASH, File.separator) + File.separator) : (System.getProperty("user.dir") + File.separator));
                    return;
                }
            }
        }
    }

    /**
     * 获取模块名
     *
     * @param packageName 包名
     * @return 模块名
     */
    public static String getModuleName(String packageName) {
        int lastIndex = packageName.lastIndexOf(StrUtil.DOT);
        int nameLength = packageName.length();
        return StrUtil.sub(packageName, lastIndex + NumberUtil.One, nameLength);
    }

    /**
     * 获取权限标识
     *
     * @param genTable 业务表对象
     * @return 权限标识
     */
    public static String getAuthorityName(GenTableDto genTable) {
        return StrUtil.format("{}:{}", genTable.getModuleName(), genTable.getBusinessName());
    }

    /**
     * 获取业务名
     *
     * @param tableName 表名
     * @return 业务名
     */
    public static String getBusinessName(String tableName) {
        int lastIndex = tableName.lastIndexOf(StrUtil.UNDERLINE);
        int nameLength = tableName.length();
        return StrUtil.sub(tableName, lastIndex + NumberUtil.One, nameLength);
    }

    /**
     * 关键字替换
     *
     * @param text 需要被替换的名字
     * @return 替换后的名字
     */
    public static String replaceText(String text) {
        return ReUtil.replaceAll(text, "(?:信息表|表|雪忆)", StrUtil.EMPTY);
    }
}