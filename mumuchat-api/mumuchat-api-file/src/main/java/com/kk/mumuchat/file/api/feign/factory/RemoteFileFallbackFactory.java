package com.kk.mumuchat.file.api.feign.factory;

import com.kk.mumuchat.common.core.web.result.R;
import com.kk.mumuchat.file.api.domain.SysFile;
import com.kk.mumuchat.file.api.feign.RemoteFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件服务 降级处理
 *
 * @author xueyi
 */
@Slf4j
@Component
public class RemoteFileFallbackFactory implements FallbackFactory<RemoteFileService> {

    @Override
    public RemoteFileService create(Throwable throwable) {
        log.error("文件服务调用失败:{},失败原因：", throwable.getMessage(), throwable);
        return new RemoteFileService() {
            @Override
            public R<SysFile> upload(MultipartFile file) {
                return R.fail("上传文件失败:" + throwable.getMessage());
            }

            @Override
            public R<Boolean> delete(String url) {
                return R.fail("删除文件失败:" + throwable.getMessage());
            }
        };
    }
}