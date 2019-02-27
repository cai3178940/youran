package com.youran.common.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

/**
 * <p>Title:AES加密工具类</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 2018-03-17
 */
public class AESSecurityUtil {
    /** 密钥算法 */
    private static final String KEY_ALGORITHM = "AES";
    private static final int KEY_SIZE = 128;
    /** 加密/解密算法/工作模式/填充方法 */
//    public static final String CIPHER_ALGORITHM = "AES/ECB/NoPadding";


    /**
     * 获取密钥
     * @return
     * @throws Exception
     */
    public static Key getKey() throws Exception{
        //实例化
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        //AES 要求密钥长度为128位、192位或256位
        kg.init(KEY_SIZE);
        //生成密钥
        SecretKey secretKey = kg.generateKey();
        return secretKey;
    }

    /**
     * 转化密钥
     * @param key 密钥
     * @return Key 密钥
     * @throws Exception
     */
    public static Key codeToKey(String key) throws Exception{
        byte[] keyBytes = Base64.decodeBase64(key);
        SecretKey secretKey = new SecretKeySpec(keyBytes,KEY_ALGORITHM);
        return secretKey;
    }

    /**
     * 解密
     * @param data 待解密数据
     * @param key 密钥
     * @return byte[] 解密数据
     * @throws Exception
     */
    private static String decrypt(byte[] data,byte[] key) throws Exception{
        //还原密钥
        Key k = new SecretKeySpec(key,KEY_ALGORITHM);
        /*
         * 实例化
         * 如果使用PKCS7Padding填充方式
         * 请按如下方式实现:Cipher.getInstance(CIPHER_ALGORITHM,"BC")
         */
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        //初始化，设置解密模式
        cipher.init(Cipher.DECRYPT_MODE,k);
        //执行操作
        return new String(cipher.doFinal(data),"UTF-8");
    }

    /**
     * 解密
     * @param data 待解密数据
     * @param key 密钥
     * @return byte[] 解密数据
     * @throws Exception
     */
    public static String decrypt(String data,String key) throws Exception{
        return decrypt(Base64.decodeBase64(data), Base64.decodeBase64(key));
    }

    /**
     * 加密
     * @param data 待加密数据
     * @param key 密钥
     * @return bytes[] 加密数据
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data,byte[] key) throws Exception{
        //还原密钥
        Key k = new SecretKeySpec(key,KEY_ALGORITHM);
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        //初始化，设置为加密模式
        cipher.init(Cipher.ENCRYPT_MODE,k);
        //执行操作
        return cipher.doFinal(data);
    }

    public static String encrypt(String data,String key) throws Exception{
        byte[] dataBytes = data.getBytes("UTF-8");
        byte[] keyBytes = Base64.decodeBase64(key);
        return Base64.encodeBase64String(encrypt(dataBytes, keyBytes));
    }

    /**
     * 初始化密钥
     * @return
     * @throws Exception
     */
    public static String getKeyStr() throws Exception{
        return Base64.encodeBase64String(getKey().getEncoded());
    }

}
