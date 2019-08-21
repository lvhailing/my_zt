package com.crecg.crecglibrary.utils.encrypt;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.Key;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class DESUtil {
    // 密钥
    private final static String secretKey = "QAZXSWEDCVFDFGFDRTGBNHHGC";
    // 向量
    private final static String iv = "01234567";
    // 加解密统一使用的编码方式
    private final static String encoding = "utf-8";

    /**
     * 3DES加密
     * 普通文本加密
     */
    public static String encrypt(String plainText) throws Exception {
        Key desKey = null;
        DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("desede");
        desKey = keyFactory.generateSecret(spec);

        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, desKey, ips);
        byte[] encryptData = cipher.doFinal(plainText.getBytes(encoding));
        return AlipayBase64.encode(encryptData);
    }

    /**
     * 3DES解密
     * 普通文本解密
     */
    public static String decrypt(String encryptText) throws Exception {
        Key desKey = null;
        DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        desKey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, desKey, ips);

        byte[] decryptData = cipher.doFinal(AlipayBase64.decode(encryptText));

        return new String(decryptData, encoding);
    }

    /**
     * map 加密
     *
     * @param param
     * @return 返回的是加密后的入参
     */
    public static String encMap(Map<String, Object> param) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        Map<String, Object> sortMap = sortMap(param);
//        Log.i("hh", "排序后的入参为：" + sortMap);
        String str_md5 = gson.toJson(sortMap);
        String md5 = MD5.stringToMD5(str_md5);

        String result = null;
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("check", md5);
            map.put("data", sortMap);
            String encrypt = gson.toJson(map);
            Log.i("hh", "加密前入参：" + encrypt);
            result = DESUtil.encrypt(encrypt);
//            Log.i("hh", "加密后入参：" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * map 未加密
     */
    public static String unencryptedMap(Map<String, Object> param) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        Map<String, Object> sortMap = sortMap(param);

        String result = null;
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("data", sortMap);
            String encrypt = gson.toJson(sortMap);
            Log.i("hh", "未加密排序后的入参为：" + encrypt);
            result = encrypt;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 对入参进行升序排列
     *
     * @param map 无序的入参
     * @return （升序：如：a,b,c....）排序后的入参
     */
    public static Map<String, Object> sortMap(Map<String, Object> map) {
        if (map == null) {
            return new HashMap<>();
        } else if (map.isEmpty()) {
            return map;
        }

        Map<String, Object> sortMap = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String s, String t1) {
                return s.compareTo(t1);
            }
        });
        sortMap.putAll(map);

        return sortMap;
    }

}
