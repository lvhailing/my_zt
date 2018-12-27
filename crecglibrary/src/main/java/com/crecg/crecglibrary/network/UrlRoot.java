package com.crecg.crecglibrary.network;


public class UrlRoot {

    private final static int ENV_DEV = 1;
    private final static int ENV_PRODUCT = 2;

    // 主地址
    public static String ROOT_URL;

    private static final int mEnvironment = 1;  //1测试环境，2正式环境

    static {
        switch (mEnvironment) {
            case ENV_DEV:
                // 测试环境
                ROOT_URL = "http://123.126.102.219:30093/";
                break;
            case ENV_PRODUCT:
                // 正式环境
                ROOT_URL = "http://v.juhe.cn/";
                break;
            default:
                break;
        }
    }
}
