package com.crecg.crecglibrary;

import android.content.Context;

/**
 * 本类说明：依赖工程的工程管理类
 */
public class CrecgLibManager {

    private static CrecgLibManager sInstance;

    private Context mCtx;          //进程上下文

    private CrecgLibManager() {
    }

    public static CrecgLibManager getInstance() {
        if (sInstance == null) {
            synchronized (CrecgLibManager.class) {
                if (sInstance == null) {
                    sInstance = new CrecgLibManager();
                }
            }
        }
        return sInstance;
    }

    public static void init(Context context) {
        getInstance().mCtx = context;
    }

    public Context getContext() {
        return mCtx;
    }
}
