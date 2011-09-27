package org.f0rb.demo.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-3-1
 * Time: 23:44:25
 * To change this template use File | Settings | File Templates.
 */
public class SecureUtils {
    /**
     * 如果系统中存在旧版本的数据，则此值不能修改，否则在进行密码解析的时候出错
     */
    private static final String PASSWORD_CRYPT_KEY = "__jDlog_";
    private final static String DES = "DES";
    private final static String ISO8859_1 = "8859_1";

    /**
     * 数据加密
     *
     * @param data 明文
     * @param key  密钥
     * @return
     */
    public static String encrypt(String data, String key) {
        if (data != null)
            try {
                return byte2hex(encrypt(data.getBytes(), key.getBytes()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        return null;
    }

    /**
     * 加密
     *
     * @param src 数据源
     * @param key 密钥，长度必须是8的倍数
     * @return 返回加密后的数据
     * @throws Exception 异常
     */
    public static byte[] encrypt(byte[] src, byte[] key)
            throws Exception {
        //		DES算法要求有一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 从原始密匙数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
        // 创建一个密匙工厂，然后用它把DESKeySpec转换成
        // 一个SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(DES);
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
        // 现在，获取数据并加密
        // 正式执行加密操作
        return cipher.doFinal(src);
    }

    /**
     * 解密
     *
     * @param src 数据源
     * @param key 密钥，长度必须是8的倍数
     * @return 返回解密后的原始数据
     * @throws Exception 密钥创建异常
     */
    public static byte[] decrypt(byte[] src, byte[] key)
            throws Exception {
        //		DES算法要求有一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 从原始密匙数据创建一个DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
        // 创建一个密匙工厂，然后用它把DESKeySpec对象转换成
        // 一个SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance(DES);
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
        // 现在，获取数据并解密
        // 正式执行解密操作
        return cipher.doFinal(src);
    }

    /**
     * 数据解密
     *
     * @param data 密文
     * @param key  密钥
     * @return
     */
    public static String decrypt(String data, String key) {
        if (data != null)
            try {
                return new String(decrypt(hex2byte(data.getBytes()), key.getBytes()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        return null;
    }


    /**
     * 密码解密
     *
     * @param data 密码密文
     * @return null 或者 password的明文
     */
    public static String decryptPassword(String data) {
        if (data != null)
            try {
                return new String(decrypt(hex2byte(data.getBytes()), PASSWORD_CRYPT_KEY.getBytes()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        return null;
    }

    /**
     * 密码加密
     *
     * @param password 密码明文
     * @return null 或 password的密文
     */
    public static String encryptPassword(String password) {
        if (password != null)
            try {
                return byte2hex(encrypt(password.getBytes(), PASSWORD_CRYPT_KEY.getBytes()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        return null;
    }

    /**
     * 二行制转字符串
     *
     * @param b
     * @return
     */
    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; b != null && n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
        }
        return hs.toUpperCase();
    }

    public static byte[] hex2byte(byte[] b) {
        if ((b.length % 2) != 0)
            throw new IllegalArgumentException("长度不是偶数");
        byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2);
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }

}
