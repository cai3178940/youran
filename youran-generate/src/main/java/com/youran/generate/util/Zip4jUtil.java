package com.youran.generate.util;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * <p>Title:文件压缩工具</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 2017/5/20
 */
public class Zip4jUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(Zip4jUtil.class);

    /**
     * 压缩文件夹
     * @param folderToAdd 待压缩的文件夹
     * @param outFile 输出zip文件
     */
    public static void compressFolder(File folderToAdd, File outFile){
        try {
            ZipFile zipFile = new ZipFile(outFile);
            ZipParameters parameters = new ZipParameters();
            zipFile.addFolder(folderToAdd, parameters);
        } catch (ZipException e) {
            LOGGER.error("压缩zip包异常",e);
        }
    }

    /**
     * 解压zip文件
     * @param zipFile 待解压的zip文件
     * @param destDir 解压目录
     */
    public static void extractAll(File zipFile,String destDir){
        try {
            ZipFile zip = new ZipFile(zipFile);
            zip.extractAll(destDir);
        } catch (ZipException e) {
            LOGGER.error("解压zip包异常",e);
        }
    }

}
