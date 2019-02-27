package com.youran.generate.util;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

/**
 * <p>Title:文件压缩工具</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 2017/5/20
 */
public class Zip4jUtil {

    /**
     * 压缩文件夹
     */
    public static void compressFolder(String folderToAdd,String outFile){
        try {
            ZipFile zipFile = new ZipFile(outFile);
            ZipParameters parameters = new ZipParameters();
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
            zipFile.addFolder(folderToAdd, parameters);
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }

}
