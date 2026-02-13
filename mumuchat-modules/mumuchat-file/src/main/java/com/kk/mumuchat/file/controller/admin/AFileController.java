package com.kk.mumuchat.file.controller.admin;

import com.kk.mumuchat.common.core.web.result.AjaxResult;
import com.kk.mumuchat.common.core.web.result.R;
import com.kk.mumuchat.common.security.annotation.AdminAuth;
import com.kk.mumuchat.file.api.domain.SysFile;
import com.kk.mumuchat.file.controller.base.BFileController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件服务 | 文件管理 | 管理端 业务处理
 *
 * @author mumuchat
 */
@AdminAuth
@RestController
@RequestMapping("/admin")
public class AFileController extends BFileController {

    @PostMapping("/upload")
    @Operation(summary = "文件上传", parameters = {
            @Parameter(name = "file", description = "文件", required = true),
            @Parameter(name = "cusName", description = "自定义文件名"),
            @Parameter(name = "cusRoute", description = "自定义文件路径")
    })
    public AjaxResult upload(@RequestParam("file") MultipartFile file,
                             @RequestParam(value = "cusName", required = false, defaultValue = "") String cusName,
                             @RequestParam(value = "cusRoute", required = false, defaultValue = "") String cusRoute) {
        R<SysFile> R = uploadFile(file, cusName, cusRoute);
        return R.isOk()
                ? AjaxResult.success("上传成功！", R.getData().getUrl(), R.getData().getOriginName())
                : AjaxResult.error("上传失败！");
    }

    @AdminAuth(isAnonymous = true)
    @PostMapping("/uploadV2")
    @Operation(summary = "文件上传", parameters = {
            @Parameter(name = "file", description = "文件", required = true),
            @Parameter(name = "cusName", description = "自定义文件名"),
            @Parameter(name = "cusRoute", description = "自定义文件路径")
    })
    public AjaxResult uploadV2(@RequestParam("file") MultipartFile file,
                             @RequestParam(value = "cusName", required = false, defaultValue = "") String cusName,
                             @RequestParam(value = "cusRoute", required = false, defaultValue = "") String cusRoute) {
        R<SysFile> R = uploadFile(file, cusName, cusRoute);
        return R.isOk()
                ? AjaxResult.success("上传成功！", R.getData().getUrl(), R.getData().getOriginName())
                : AjaxResult.error("上传失败！");
    }
}