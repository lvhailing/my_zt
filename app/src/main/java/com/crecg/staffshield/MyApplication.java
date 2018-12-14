package com.crecg.staffshield;

import android.app.Application;

import com.crecg.crecglibrary.CrecgLibManager;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by lvhailing on 2018/12/13.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        CrecgLibManager.getInstance().init(this);

        //初始化极光推送sdk
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}
