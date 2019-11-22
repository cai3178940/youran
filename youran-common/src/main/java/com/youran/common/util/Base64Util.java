package com.youran.common.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

/**
 * base64编码工具
 *
 * @author: cbb
 * @date: 2019/11/22
 */
public class Base64Util {

    /**
     * 编码
     *
     * @param bytes
     * @return
     */
    public static String encode(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * 编码文件
     *
     * @param file
     * @return
     */
    public static String encodeFile(File file) throws IOException {
        byte[] bytes = FileUtils.readFileToByteArray(file);
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * 编码
     *
     * @param text
     * @return
     */
    public static byte[] decode(String text) {
        return Base64.getDecoder().decode(text);
    }

}
