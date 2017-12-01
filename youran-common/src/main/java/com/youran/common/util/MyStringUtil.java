package com.youran.common.util;

import java.io.UnsupportedEncodingException;

/**
 * Title:
 * Description:
 * Author: cbb
 * Create Time:2017/11/24 17:13
 */
public class MyStringUtil {

    /**
     * 按字节长度截取字符串
     * 按照一个汉字两个字节的gbk编码风格截取
     * @param str
     * @param len
     * @return
     */
    public static String substringByByte(String str, int len){
        String result = null;
        if (str != null) {
            try {
                byte[] a = str.getBytes("gbk");
                if (a.length <= len) {
                    result = str;
                } else if (len > 0) {
                    result = new String(a, 0, len,"gbk");
                    int length = result.length();
                    if (str.charAt(length - 1) != result.charAt(length - 1)) {
                        if (length < 2) {
                            result = null;
                        } else {
                            result = result.substring(0, length - 1);
                        }
                    }
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                throw new RuntimeException("gbk编码不支持");
            }
        }
        return result;
    }

}
