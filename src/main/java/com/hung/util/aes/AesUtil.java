package com.hung.util.aes;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Aes工具类
 *
 * @author Hung
 */
public class AesUtil {
    /**
     * AES加密字符串
     *
     * @param content  需要被加密的字符串
     * @param password 加密需要的密码
     * @return 密文
     */
    public static byte[] encrypt(String content, String password) {
        try {
            // 创建AES的Key生产者
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            // 利用用户密码作为随机数初始化出
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(password.getBytes());
            kgen.init(128,random);
            //加密没关系，SecureRandom是生成安全随机数序列，password.getBytes()是种子，只要种子相同，序列就一样，所以解密只要有password就行
            // 根据用户密码，生成一个密钥
            SecretKey secretKey = kgen.generateKey();
            // 返回基本编码格式的密钥，如果此密钥不支持编码，则返回
            byte[] enCodeFormat = secretKey.getEncoded();
            // 转换为AES专用密钥
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            // 创建密码器
            Cipher cipher = Cipher.getInstance("AES");
            // 初始化为加密模式的密码器
            cipher.init(Cipher.ENCRYPT_MODE, key);
            // 加密
            byte[] byteContent = content.getBytes(StandardCharsets.UTF_8);
            byte[] result = cipher.doFinal(byteContent);
            return result;
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密AES加密过的字符串
     *
     * @param content  AES加密过过的内容
     * @param password 加密时的密码
     * @return 明文
     */
    public static byte[] decrypt(byte[] content, String password) {
        try {
            // 创建AES的Key生产者
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(password.getBytes());
            kgen.init(128,random);
            // 根据用户密码，生成一个密钥
            SecretKey secretKey = kgen.generateKey();
            // 返回基本编码格式的密钥
            byte[] enCodeFormat = secretKey.getEncoded();
            // 转换为AES专用密钥
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            // 创建密码器
            Cipher cipher = Cipher.getInstance("AES");
            // 初始化为解密模式的密码器
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static  String encryptStr(String content, String password){
        byte[] encrypt = encrypt(content, password);
        String hexStr = ParseSystemUtil.parseByte2HexStr(encrypt);
        return hexStr;
    }

    public static  String decryptStr(String hexStr, String password){
        byte[] byte2 = ParseSystemUtil.parseHexStr2Byte(hexStr);
        byte[] decrypt = decrypt(byte2, password);
        String s = new String(decrypt, StandardCharsets.UTF_8);
        return s;
    }

    public static void main(String[] args) {
        String content = "123456";
        String password = "cph";
        System.out.println("需要加密的内容：" + content);
        byte[] encrypt = encrypt(content, password);
        System.out.println("加密后的2进制密文：" + new String(encrypt));
        String hexStr = ParseSystemUtil.parseByte2HexStr(encrypt);
        System.out.println("加密后的16进制密文:" + hexStr);
        byte[] byte2 = ParseSystemUtil.parseHexStr2Byte(hexStr);
        System.out.println("加密后的2进制密文：" + new String(byte2));
        byte[] decrypt = decrypt(byte2, password);
        System.out.println("解密后的内容：" + new String(decrypt, StandardCharsets.UTF_8));
    }
}
