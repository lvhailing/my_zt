package com.crecg.staffshield.utils;

import com.google.gson.Gson;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class RequestUtil {
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

    public static Map<String, Object> getBasicMap(Map<String, Object> params) {
        Map<String, Object> sortMap = sortMap(params);
        String json = new Gson().toJson(sortMap);
        String md5 = MD5.stringToMD5(json);

        String result = null;
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("check", md5);
            map.put("data", sortMap);
            String encrypt = new Gson().toJson(map);
//            Log.i("hh", "传给后台的入参为：" + encrypt);
            result = DESUtil.encrypt(encrypt);
            params.put("data", result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return params;
    }
}
