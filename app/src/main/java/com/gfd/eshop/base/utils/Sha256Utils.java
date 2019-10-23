package com.gfd.eshop.base.utils;


import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Sha256加密工具类
 */
public class Sha256Utils {

    private Sha256Utils() {
    }

    /**
     * 获取Hash值
     * @param password
     * @return
     */
    private static byte[] getHash(String password) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            digest.reset();
        } catch (NoSuchAlgorithmException e) {
            throw new UnsupportedOperationException(e);
        }
        return digest.digest(password.getBytes(Charset.defaultCharset()));
    }

    /**
     * 二进制转成十六进制
     * @param strForEncrypt
     * @return
     */
    public static String bin2hex(String strForEncrypt) {
        byte[] data = getHash(strForEncrypt);
        return String.format("%0" + (data.length * 2) + "X", new BigInteger(1, data));
    }

}
