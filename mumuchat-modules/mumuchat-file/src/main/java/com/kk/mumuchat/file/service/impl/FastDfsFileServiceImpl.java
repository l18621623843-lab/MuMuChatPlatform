package com.kk.mumuchat.file.service.impl;

import com.alibaba.nacos.common.utils.IoUtils;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.kk.mumuchat.common.core.utils.core.StrUtil;
import com.kk.mumuchat.common.core.utils.file.FileTypeUtil;
import com.kk.mumuchat.common.core.utils.file.FileUtil;
import com.kk.mumuchat.file.api.domain.SysFile;
import com.kk.mumuchat.file.service.IFileService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * FastDFS 文件存储
 *
 * @author mumuchat
 */
@Service
public class FastDfsFileServiceImpl implements IFileService {

    /**
     * 域名或本机访问地址
     */
    @Value("${fdfs.domain}")
    public String domain;

    @Autowired
    private FastFileStorageClient storageClient;

    @Override
    @SneakyThrows
    public SysFile uploadFile(MultipartFile file, String cusName, String cusRoute) {
        InputStream inputStream = file.getInputStream();
        StorePath storePath = storageClient.uploadFile(inputStream, file.getSize(),
                FileTypeUtil.getExtension(file), null);
        IoUtils.closeQuietly(inputStream);
        SysFile sysFile = new SysFile();
        sysFile.setUrl(domain + StrUtil.SLASH + storePath.getFullPath());
        sysFile.setPath(storePath.getFullPath());
        sysFile.setSize(file.getSize());
        sysFile.setName(FileUtil.getName(sysFile.getUrl()));
        sysFile.setNick(sysFile.getName());
        return sysFile;
    }

    @Override
    @SneakyThrows
    public Boolean deleteFile(String url) {
        return true;
    }
}
