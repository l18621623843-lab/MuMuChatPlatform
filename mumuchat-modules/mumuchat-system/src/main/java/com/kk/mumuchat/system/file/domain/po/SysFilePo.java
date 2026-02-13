package com.kk.mumuchat.system.file.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.kk.mumuchat.common.core.annotation.Excel;
import com.kk.mumuchat.common.core.web.tenant.base.TBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

import static com.kk.mumuchat.common.core.constant.basic.EntityConstants.REMARK;

/**
 * 系统服务|素材模块|文件 持久化对象
 *
 * @author mumuchat
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_file", excludeProperty = { REMARK })
public class SysFilePo extends TBaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 分类Id */
    @Excel(name = "分类Id")
    protected Long folderId;

    /** 文件别名 */
    @Excel(name = "文件别名")
    protected String nick;

    /** 文件网址 */
    @Excel(name = "文件网址")
    protected String url;

    /** 存储路径 */
    @Excel(name = "存储路径")
    protected String path;

    /** 文件大小 */
    @Excel(name = "文件大小")
    protected Long size;

    /** 文件类型（0默认 1系统） */
    @Excel(name = "文件类型", readConverterExp = "0=默认,1=系统")
    protected String type;

}