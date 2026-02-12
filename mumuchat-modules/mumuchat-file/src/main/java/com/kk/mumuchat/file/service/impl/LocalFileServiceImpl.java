package com.kk.mumuchat.file.service.impl;

import com.kk.mumuchat.common.core.utils.core.StrUtil;
import com.kk.mumuchat.common.core.utils.file.FileUtil;
import com.kk.mumuchat.file.api.domain.SysFile;
import com.kk.mumuchat.file.service.IFileService;
import com.kk.mumuchat.file.utils.FileUploadUtils;
import com.kk.mumuchat.file.utils.ImageCompressorUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * 本地文件存储
 *
 * @author xueyi
 */
@Primary
@Service
public class LocalFileServiceImpl implements IFileService {

    /** 资源映射路径 前缀 */
    @Value("${file.prefix}")
    public String localFilePrefix;

    /** 域名或本机访问地址 */
    @Value("${file.domain}")
    public String domain;

    /** 上传文件存储在本地的根路径 */
    @Value("${file.path}")
    private String localFilePath;

    @Override
    @SneakyThrows
    public SysFile uploadFile(MultipartFile file, String cusName, String cusRoute) {
        String originName = file.getOriginalFilename();
        if (StrUtil.isNotBlank(cusRoute)) {
            cusRoute = StrUtil.replace(cusRoute, StrUtil.BACKSLASH, StrUtil.SLASH);
            cusRoute = StrUtil.addPrefixIfNot(cusRoute, StrUtil.SLASH);
            cusRoute = StrUtil.replace(cusRoute, StrUtil.SLASH, File.separator);
        }
        // 重新生成图片
        String baseDir = StrUtil.isNotBlank(cusRoute)
                ? (localFilePath + cusRoute) :
                localFilePath;
        MultipartFile newFile = ImageCompressorUtil.compressIfImage(file);
        String name = FileUploadUtils.upload(baseDir, newFile);
        SysFile sysFile = new SysFile();
        sysFile.setUrl(domain + localFilePrefix + cusRoute + name);
        sysFile.setPath(name);
        sysFile.setOriginName(originName);
        sysFile.setSize(newFile.getSize());
        sysFile.setName(FileUtil.getName(sysFile.getUrl()));
        sysFile.setNick(sysFile.getName());
        return sysFile;
    }

    @Override
    @SneakyThrows
    public Boolean deleteFile(String url) {
        String localPath = url.replace(domain + localFilePrefix, localFilePath);
        File file = new File(localPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            return file.delete();
        }
        return false;
    }
}
