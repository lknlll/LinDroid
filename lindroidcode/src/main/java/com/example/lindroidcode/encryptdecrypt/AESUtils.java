package com.example.lindroidcode.encryptdecrypt;

import android.util.Base64;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtils {

    private static final String TAG = AESUtils.class.getSimpleName();
    private static final String AES = "AES"; //AES 加密
    private static final String AES_KEY = "(((((****&&^^%%////**---+++++{}|:<>^^%%$$##-login-jd!!!!!!!!!"; //AES 密钥
    /**
     * 加密
     */
    public static String encrypt(byte[] clear) throws Exception {
        //根据给定的字节数组和与给定的密钥内容相关联的密钥算法的名称构造一个密钥
        SecretKeySpec skeySpec = new SecretKeySpec(handleKey(), AES);//存密钥对象
        //"算法/模式/填充"
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));//偏移量
        //Todo 偏移量不能随便设
        byte[] encrypted = cipher.doFinal(clear);
        return Base64.encodeToString(encrypted,Base64.DEFAULT);
    }
    /**
     * 解密
     * @param encrypted 密文的Base64
     */
    public static String decrypt(byte[] encrypted) throws Exception {
        byte[] decoded = Base64.decode(encrypted,Base64.DEFAULT);
        SecretKeySpec skeySpec = new SecretKeySpec(handleKey(), AES);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
        //Todo 偏移量不能随便设
        byte[] decrypted = cipher.doFinal(decoded);
        return new String(decrypted);
    }

    private static byte[] handleKey() throws Exception{
        MessageDigest md5;
        md5 = MessageDigest.getInstance("MD5");
        return md5.digest(AES_KEY.getBytes());
    }
}