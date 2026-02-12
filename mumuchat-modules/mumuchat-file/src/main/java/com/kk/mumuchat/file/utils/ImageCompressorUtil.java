package com.kk.mumuchat.file.utils;

import com.kk.mumuchat.common.core.utils.core.StrUtil;
import com.kk.mumuchat.file.opencv.OpenCVLoader;
import lombok.extern.slf4j.Slf4j;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * 图片压缩工具类
 *
 * @author xueyi
 */
@Slf4j
public class ImageCompressorUtil {

    /**
     * 图片压缩
     * 则尝试使用 OpenCV 压缩，否则返回原文件
     *
     * @param file 上传的文件
     * @return 压缩后的 MultipartFile 或原始文件
     */
    public static MultipartFile compressIfImage(MultipartFile file) {
        return compressIfImage(file, 0.75f, 75);
    }

    /**
     * 图片压缩
     * 则尝试使用 OpenCV 压缩，否则返回原文件
     *
     * @param file       上传的文件
     * @param scaleRatio 缩放比例
     * @param quality    压缩质量
     * @return 压缩后的 MultipartFile 或原始文件
     */
    public static MultipartFile compressIfImage(MultipartFile file, float scaleRatio, int quality) {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            // 文件名为空，跳过处理
            return file;
        }

        // 判断是否为图片
        if (!isImageFile(originalFilename)) {
            // 非图片，直接返回原文件
            return file;
        }

        try {
            if(!OpenCVLoader.isLoaded) {
                return file;
            }
            // 创建临时输入文件
            Path tempInputFile = Files.createTempFile("upload-", ".tmp");
            file.transferTo(tempInputFile);

            // 读取图片
            Mat src = Imgcodecs.imread(tempInputFile.toString());
            if (src.empty()) {
                throw new IOException("无法读取图片文件: " + originalFilename);
            }

            // 获取扩展名
            String ext = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
            // 创建压缩后临时文件
            Path compressedFile = Files.createTempFile("compressed-", ext);

            Mat dst;
            boolean success;
            if (ext.equals(".jpg") || ext.equals(".jpeg")) {
                // 使用原始图像进行压缩
                dst = src.clone();
                // 如果是 .jpg/.jpeg 文件，可以设置压缩质量
                MatOfInt params = new MatOfInt(Imgcodecs.IMWRITE_JPEG_QUALITY, quality);
                success = Imgcodecs.imwrite(compressedFile.toString(), dst, params);
            } else {
                // 缩放比例
                dst = new Mat();
                Imgproc.resize(src, dst, new Size(), scaleRatio, scaleRatio, Imgproc.INTER_AREA);
                success = Imgcodecs.imwrite(compressedFile.toString(), dst);
            }

            // 释放资源
            src.release();
            dst.release();

            if (!success) {
                throw new IOException("图片压缩失败: " + originalFilename);
            }
            // 构建新文件名：yyyyMMdd_000001.ext
            byte[] compressedBytes = Files.readAllBytes(compressedFile);
            // 获取当前时间戳（毫秒）
            long timestamp = System.currentTimeMillis();
            String datePrefix = new java.text.SimpleDateFormat("yyyyMMdd").format(new java.util.Date());
            String newFileName = StrUtil.format("{}_{}{}", datePrefix, timestamp, ext);

            // 重命名文件
            File renamedFile = compressedFile.toFile();
            File targetFile = new File(renamedFile.getParent(), newFileName);
            Files.move(compressedFile, targetFile.toPath());

            // 返回压缩后的文件
            return new MockMultipartFile(
                    file.getName(),
                    newFileName,
                    file.getContentType(),
                    compressedBytes
            );
        } catch (UnsatisfiedLinkError e) {
            log.warn("OpenCV 本地库链接错误: {}", e.getMessage());
            return file;
        } catch (Exception e) {
            log.warn("⚠️ 图片压缩失败，返回原始文件。原因: ", e);
            return file;
        }
    }

    /**
     * 判断是否为支持的图片格式
     */
    private static boolean isImageFile(String filename) {
        String ext = filename.toLowerCase();
        return ext.endsWith(".jpg") || ext.endsWith(".jpeg") || ext.endsWith(".png");
    }
}
