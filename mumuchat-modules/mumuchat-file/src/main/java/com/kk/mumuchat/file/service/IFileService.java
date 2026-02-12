package com.kk.mumuchat.file.service;

import com.kk.mumuchat.file.api.domain.SysFile;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传接口
 *
 * @author xueyi
 */
public interface IFileService {

    /**
     * 文件上传接口
     *
     * @param file 上传的文件
     * @param cusName  自定义文件名
     * @param cusRoute 自定义文件路径
     * @return 访问地址
     */
    SysFile uploadFile(MultipartFile file, String cusName, String cusRoute) throws Exception;

    /**
     * 文件删除接口
     *
     * @param url 文件地址
     * @return 结果
     */
    Boolean deleteFile(String url) throws Exception;
}
