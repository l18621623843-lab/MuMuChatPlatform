package com.kk.mumuchat.file.api.domain;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 文件上传 数据传输对象
 *
 * @author xueyi
 */
@Data
public class SysUploadDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 自定义文件名 */
    protected String cusName;

    /** 自定义文件路径 */
    protected Long cusRoute;

    /** 压缩比例 */
    protected Integer nick;

    /** 文件地址 */
    protected String url;

    /** 存储路径 */
    protected String path;

    /** 文件大小 */
    protected Long size;

    /** 文件类型（0默认 1系统） */
    protected String type;

}
