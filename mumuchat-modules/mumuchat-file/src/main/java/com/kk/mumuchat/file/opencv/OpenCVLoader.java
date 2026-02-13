package com.kk.mumuchat.file.opencv;

import com.kk.mumuchat.common.core.exception.UtilException;
import lombok.extern.slf4j.Slf4j;
import org.opencv.core.Core;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * OpenCV 原生库加载器
 * 用于在应用启动时动态加载 OpenCV 的 native 库（如 .dll、.so、.dylib），
 * 支持 Windows、Linux 和 macOS 平台。
 *
 * @author mumuchat
 */
@Slf4j
public class OpenCVLoader {

    public static Boolean isLoaded = false;

    /**
     * 初始化方法，用于触发 OpenCV native 库的加载
     * 此方法会根据当前操作系统选择对应的 native 库文件，
     * 将其从 classpath 中复制到临时目录，并通过 System.load 加载。
     */
    public static void init() {

        try {
            // 获取操作系统名称并转换为小写，便于判断
            String osName = System.getProperty("os.name").toLowerCase();
            log.info("【OpenCV】loaded osName: {}", osName);
            String libName;

            // 根据不同操作系统选择对应的 native 库文件名
            if (osName.contains("win")) {
                libName = "opencv_java4110.dll";
            } else if (osName.contains("nix") || osName.contains("nux")) {
                libName = "libopencv_java4110.so";
            } else if (osName.contains("mac")) {
                libName = "libopencv_java4110.dylib";
            } else {
                log.error("【OpenCV】Unsupported OS");
                throw new UtilException("Unsupported OS");
            }
            log.info("【OpenCV】loaded libName: {}", libName);

            // 创建一个临时目录，用于存放复制后的 native 库文件
            Path tempDir = Files.createTempDirectory("opencv");
            Path libPath = tempDir.resolve(libName);

            // 从 classpath 中读取 native 库资源流
            try (InputStream in = getLibraryInputStream(libName)) {
                try {
                    // 将 native 库复制到临时路径中
                    Files.copy(in, libPath, StandardCopyOption.REPLACE_EXISTING);
                } catch (Exception e) {
                    log.error("【OpenCV】Failed to load native OpenCV library from resources", e);
                }
            }

            // 加载本地库文件
            System.load(libPath.toString());
            log.info("【OpenCV】loaded successfully: {}", Core.VERSION);

        } catch (UnsatisfiedLinkError e) {
            log.error("【OpenCV】Native library loading failed due to UnsatisfiedLinkError", e);
            // 可选择抛出自定义异常或者静默处理
            // 捕获所有异常并抛出运行时异常，防止静默失败
            throw new UtilException("Failed to load OpenCV native library", e);
        } catch (Exception e) {
            log.error("【OpenCV】Failed to load OpenCV native library", e);
            // 捕获所有异常并抛出运行时异常，防止静默失败
            throw new UtilException("Failed to load OpenCV native library", e);
        }
    }

    /**
     * 获取对应操作系统的 OpenCV native 库输入流
     * Linux 平台优先从服务器指定目录加载，其他平台从 classpath 加载
     *
     * @param libName 库文件名称
     * @return Native 库文件的输入流
     */
    private static InputStream getLibraryInputStream(String libName) {
        // 注意：这里使用 '/' 开头表示从 classpath 根目录查找
        String resourcePath = "/opencv/native/" + libName;
        InputStream stream = OpenCVLoader.class.getResourceAsStream(resourcePath);

        if (stream == null) {
            // 另一种尝试方式
            stream = Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream("opencv/native/" + libName);
            if (stream == null) {
                throw new UtilException("Native library not found: " + resourcePath);
            }
        }
        return stream;
    }
}