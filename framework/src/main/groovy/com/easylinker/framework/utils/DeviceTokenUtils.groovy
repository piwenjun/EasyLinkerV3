package com.easylinker.framework.utils

import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.DESKeySpec
import java.nio.charset.StandardCharsets
import java.security.SecureRandom

/**
 * 设备Token辅助生成工具类
 * @author wwhai* @date 2019/8/3 9:58
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
class DeviceTokenUtils {
    private static final String SECRET_KEY = "TGYB%TTYGJB74yrhe34rQ"
    /**
     * 用Base64进行编码设备信息：
     * 一般只编码SID
     * @param content
     * @return
     */
    static String token(String content) {
        byte[] enByte = desEncrypt(content.bytes, SECRET_KEY)
        String b64 = Base64.encoder.encodeToString(enByte)
        return b64
    }

    /**
     * 解析token
     * @param content
     * @return
     */
    static String parse(String content) {
        byte[] b64 = Base64.decoder.decode(content)
        byte[] deByte = desDecrypt(b64, SECRET_KEY)
        String origin = new String(deByte, StandardCharsets.UTF_8)
        return origin
    }

    /**
     * 获取设备信息，目前的版本是必须有2个信息：SID和TYPE
     * @return
     */
    static String[] getInfo(String text) {
        //1 剔除[]
        text = text.substring(1, text.length() - 1)
        if (text.split(",").length == 2) {
            return [text.split(",")[0].trim(), text.split(",")[1].trim()]
        } else {
            return []
        }
    }
    /**
     * des 加密
     * @param plainText
     * @param desKeyParameter 加密秘钥
     * @return 二进制字节数组* @throws Exception
     */
    private static byte[] desEncrypt(byte[] plainText, String desKeyParameter) throws Exception {
        SecureRandom sr = new SecureRandom()
        byte[] rawKeyData = desKeyParameter.getBytes()
        DESKeySpec dks = new DESKeySpec(rawKeyData)
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES")
        SecretKey key = keyFactory.generateSecret(dks)
        Cipher cipher = Cipher.getInstance("DES")
        cipher.init(Cipher.ENCRYPT_MODE, key, sr)
        byte[] data = plainText
        byte[] encryptedData = cipher.doFinal(data)
        return encryptedData
    }

    /**
     * des 解密
     * @param encryptText
     * @param desKeyParameter 解密秘钥
     * @return 二进制字节数组* @throws Exception
     */
    private static byte[] desDecrypt(byte[] encryptText, String desKeyParameter) throws Exception {
        SecureRandom sr = new SecureRandom()
        byte[] rawKeyData = desKeyParameter.getBytes()
        DESKeySpec dks = new DESKeySpec(rawKeyData)
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES")
        SecretKey key = keyFactory.generateSecret(dks)
        Cipher cipher = Cipher.getInstance("DES")
        cipher.init(Cipher.DECRYPT_MODE, key, sr)
        byte[] encryptedData = encryptText
        byte[] decryptedData = cipher.doFinal(encryptedData)
        return decryptedData
    }


    static void main(String[] args) {
        String hello = "hello world"

        String token = token(hello)
        println("不用密钥的：" + Base64.encoder.encodeToString(hello.bytes))
        println("用密钥的：" + token)
        println("原文：" + parse(token))
        String listString = parse("Du7Rg40X41Iqc5cYCbl2rdgYXNO8/e9ooc5DWXxJyM99eMGMvpqPck5JWLIQkQCI")

    }
}
