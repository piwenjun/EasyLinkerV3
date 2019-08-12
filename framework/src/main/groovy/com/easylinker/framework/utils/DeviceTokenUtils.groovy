package com.easylinker.framework.utils

import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject

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
     * 获取设备信息，传入的是Base64文本
     * @return
     */
    static JSONArray getInfo(String text) {
        return JSONObject.parseArray(parse(text))
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


    /**
     * 把设备的数据域和数据进行对比
     * @param dataFields :数组字段 :["value", "temp", "hum"]
     * @param dataArray :JSON:{field:"value",data:"112233"}* @return
     */
    static JSONObject xo(JSONArray dataFields, JSONObject dataJson) {
        JSONObject result = new JSONObject()
        for (String k : dataJson.keySet()) {
            if (dataFields.contains(k)) {
                result.put(k, dataJson.getString(k))
            }
        }
        return result
    }

    static void main(String[] args) {
        String hello = '["VALUE"]'

        String token = token(hello)
        println("不用密钥的：" + Base64.encoder.encodeToString(hello.bytes))
        println("用密钥的：" + token)
        println("原文：" + parse(token))
        String listString = parse("ByKgGhwFFOshdrS5d0D7lR/5QGjSx0/0wFXp787hSjT/mZivVhIEbiJPIZ7UI634dOyw+TEaHLWx7df94fCzAeVsJlGYdBTz3lu698vDgyI=")
        println("测试解密:" + listString)
        JSONArray jsonArray = getInfo("9miWMhDguMuoe73OSaI8BOiP+0T5pdshpEj50am5Su/hHMdJ4g4NPSJPIZ7UI634dOyw+TEaHLWx7df94fCzAeVsJlGYdBTz3lu698vDgyI=")

        println(JSONObject.parseArray(jsonArray[2].toString()))
//        JSONArray a1 = new JSONArray()
//        a1.add("G")
//        a1.add("B")
//        a1.add("K")
//        a1.add("C")
//        JSONObject j2 = new JSONObject()
//        j2.put("C", "1")
//        j2.put("G", "2")
//        j2.put("X", "6")
//        j2.put("K", "6")
//
//
//        println JSONObject.toJSONString(xo(a1, j2))

    }
}
