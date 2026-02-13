package com.kk.mumuchat.file.service.impl;

import com.kk.mumuchat.common.core.utils.core.StrUtil;
import com.kk.mumuchat.common.core.utils.file.FileUtil;
import com.kk.mumuchat.file.api.domain.SysFile;
import com.kk.mumuchat.file.config.MinioConfig;
import com.kk.mumuchat.file.service.IFileService;
import com.kk.mumuchat.file.utils.FileUploadUtils;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * Minio 文件存储
 *
 * @author mumuchat
 */
@Service
public class MinioFileServiceImpl implements IFileService {

    @Autowired
    private MinioConfig minioConfig;

    @Autowired
    private MinioClient client;

    @Override
    @SneakyThrows
    public SysFile uploadFile(MultipartFile file, String cusName, String cusRoute) {
        String fileName = FileUploadUtils.extractFilename(file);
        InputStream inputStream = file.getInputStream();
        PutObjectArgs args = PutObjectArgs.builder()
                .bucket(minioConfig.getBucketName())
                .object(fileName)
                .stream(inputStream, file.getSize(), -1)
                .contentType(file.getContentType())
                .build();
        client.putObject(args);
        inputStream.close();
        SysFile sysFile = new SysFile();
        sysFile.setUrl(minioConfig.getUrl() + StrUtil.SLASH + minioConfig.getBucketName() + StrUtil.SLASH + fileName);
        sysFile.setPath(minioConfig.getBucketName() + StrUtil.SLASH + fileName);
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
