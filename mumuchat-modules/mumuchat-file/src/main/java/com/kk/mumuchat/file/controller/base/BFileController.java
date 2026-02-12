package com.kk.mumuchat.file.controller.base;

import com.kk.mumuchat.common.core.constant.basic.BaseConstants;
import com.kk.mumuchat.common.core.web.result.R;
import com.kk.mumuchat.file.api.domain.SysFile;
import com.kk.mumuchat.file.api.feign.RemoteFileManageService;
import com.kk.mumuchat.file.service.IFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件服务 | 文件管理 | 通用 业务处理
 *
 * @author xueyi
 */
@Slf4j
public class BFileController {

    @Autowired
    protected IFileService fileService;

    @Autowired
    protected RemoteFileManageService remoteFileManageService;

    /**
     * 文件上传
     *
     * @param file     文件
     * @param cusName  自定义文件名
     * @param cusRoute 自定义文件路径
     * @return 文件信息
     */
    protected R<SysFile> uploadFile(MultipartFile file, String cusName, String cusRoute) {
        try {
            // 上传并返回访问地址
            SysFile sysFile = fileService.uploadFile(file, cusName, cusRoute);
            sysFile.setFolderId(BaseConstants.TOP_ID);
            remoteFileManageService.saveFile(sysFile);
            return R.ok(sysFile);
        } catch (Exception e) {
            log.error("上传文件失败", e);
            return R.fail(e.getMessage());
        }
    }

    /**
     * 删除文件
     *
     * @param url 文件路径
     * @return 结果
     */
    protected R<Boolean> deleteFile(String url) {
        try {
            Boolean result = fileService.deleteFile(url);
            remoteFileManageService.delFile(url);
            return R.ok(result);
        } catch (Exception e) {
            log.error("文件删除失败", e);
            return R.fail(e.getMessage());
        }
    }
}