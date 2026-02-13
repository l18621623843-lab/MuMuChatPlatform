package com.kk.mumuchat.file.controller.inner;

import com.kk.mumuchat.common.core.web.result.R;
import com.kk.mumuchat.common.security.annotation.InnerAuth;
import com.kk.mumuchat.file.api.domain.SysFile;
import com.kk.mumuchat.file.controller.base.BFileController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件服务 | 文件管理 | 内部调用 业务处理
 *
 * @author mumuchat
 */
@InnerAuth
@RestController
@RequestMapping("/inner")
public class IFileController extends BFileController {

    @PostMapping("/upload")
    @Operation(summary = "文件上传", parameters = {
            @Parameter(name = "file", description = "文件", required = true),
            @Parameter(name = "cusName", description = "自定义文件名"),
            @Parameter(name = "cusRoute", description = "自定义文件路径")
    })
    public R<SysFile> uploadInner(@RequestParam("file") MultipartFile file,
                                  @RequestParam(value = "cusName", required = false, defaultValue = "") String cusName,
                                  @RequestParam(value = "cusRoute", required = false, defaultValue = "") String cusRoute) {
        return uploadFile(file, cusName, cusRoute);
    }

    /**
     * 删除文件
     */
    @DeleteMapping(value = "/delete")
    public R<Boolean> deleteInner(@RequestParam String url) {
        return deleteFile(url);
    }
}