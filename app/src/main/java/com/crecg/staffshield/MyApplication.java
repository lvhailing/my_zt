package com.crecg.staffshield;

import android.app.Application;

import com.crecg.crecglibrary.CrecgLibManager;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

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

        // 必须在调用任何统计SDK接口之前调用初始化函数
        UMConfigure.init(this, "5c1b368af1f556f3b5000346", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, null);

        // 设置场景类型接口：普通统计场景类型
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);

        //禁止默认的页面统计功能，这样将不会再自动统计Activity页面。（包含Activity、Fragment或View的应用）
        MobclickAgent.openActivityDurationTrack(false);

        // 打开统计SDK调试模式（上线时记得关闭）
        UMConfigure.setLogEnabled(true);
    }
}
