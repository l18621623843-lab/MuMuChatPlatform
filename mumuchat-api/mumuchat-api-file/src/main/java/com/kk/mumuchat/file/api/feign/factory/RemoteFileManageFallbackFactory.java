package com.kk.mumuchat.file.api.feign.factory;

import com.kk.mumuchat.common.core.web.result.R;
import com.kk.mumuchat.file.api.domain.SysFile;
import com.kk.mumuchat.file.api.feign.RemoteFileManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 文件管理服务 降级处理
 *
 * @author xueyi
 */
@Slf4j
@Component
public class RemoteFileManageFallbackFactory implements FallbackFactory<RemoteFileManageService> {

    @Override
    public RemoteFileManageService create(Throwable throwable) {
        log.error("文件管理服务调用失败:{},失败原因：", throwable.getMessage(), throwable);
        return new RemoteFileManageService() {
            @Override
            public R<Boolean> saveFile(SysFile file) {
                return R.fail("保存文件记录失败:" + throwable.getMessage());
            }

            @Override
            public R<Boolean> delFile(String url) {
                return R.fail("删除文件记录失败:" + throwable.getMessage());
            }
        };
    }
}