package com.kk.mumuchat.file.api.feign;

import com.kk.mumuchat.common.core.constant.basic.SecurityConstants;
import com.kk.mumuchat.common.core.web.result.R;
import com.kk.mumuchat.file.api.domain.SysFile;
import com.kk.mumuchat.file.api.feign.factory.RemoteFileFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件服务
 *
 * @author xueyi
 */
@FeignClient(contextId = "remoteFileService", path = "/inner", value = "${xueyi.remote.service.file}", fallbackFactory = RemoteFileFallbackFactory.class)
public interface RemoteFileService {

    /**
     * 上传文件
     *
     * @param file 文件信息
     * @return 结果
     */
    @PostMapping(value = "/upload", headers = SecurityConstants.FROM_SOURCE_INNER, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    R<SysFile> upload(@RequestPart(value = "file") MultipartFile file);

    /**
     * 删除文件
     *
     * @param url 文件地址
     * @return 结果
     */
    @DeleteMapping(value = "/delete", headers = SecurityConstants.FROM_SOURCE_INNER)
    R<Boolean> delete(@RequestParam("url") String url);
}
