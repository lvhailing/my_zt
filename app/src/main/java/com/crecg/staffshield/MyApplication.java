package com.crecg.staffshield;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.crecg.crecglibrary.CrecgLibManager;
//import com.hyphenate.chat.ChatClient;
//import com.hyphenate.helpdesk.easeui.UIProvider;
import com.crecg.staffshield.utils.PreferenceUtil;
import com.crecg.staffshield.utils.SystemInfo;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.helpdesk.easeui.UIProvider;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

import java.util.HashSet;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by hong on 2018/12/13.
 */

public class MyApplication extends Application {
    private BroadcastReceiver mReceiver;
    public String netType;
    IntentFilter mFilter;
    HashSet<NetListener> mListeners = new HashSet<NetListener>();
    private static MyApplication instance;
    public static String mAppId;
    public static String mDownloadPath;

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
//        NetworkUtils.setContext(this);
        PreferenceUtil.initialize(this);
        SystemInfo.initialize(this);
        initNetReceiver();
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

        //Kefu sdk 初始化简写方式：
        ChatClient.getInstance().init(this, new ChatClient.Options()
                .setAppkey("1424181211061451#kefuchannelapp61589")
                .setTenantId("61589") // 设置租户id
                .showAgentInputState()  // 坐席输入状态显示功能开关 调用此方法开启
                .showVisitorWaitCount() // 显示访客等待数功能开关 调用此方法开启
                .showMessagePredict()); // 消息预知功能开关 调用此方法开启
        // Kefu EaseUI的初始化
        UIProvider.getInstance().init(this);

        mAppId = getString(R.string.app_id);
        mDownloadPath = "/" + mAppId + "/download";
    }

    public interface NetListener {
        void onNetWorkChange(String netType);
    }

    /**
     * 加入网络监听
     * @param l
     * @return
     */
    public boolean addNetListener(NetListener l) {
        boolean rst = false;
        if (l != null && mListeners != null) {
            rst = mListeners.add(l);
        }
        return rst;
    }

    /**
     * 初始化网络监听器
     */
    private void initNetReceiver() {
        mReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                    ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo info = manager.getActiveNetworkInfo();
                    if (info != null && info.isConnected()) {
                        netType = info.getTypeName();
                    } else {
                        netType = "";
                    }
                    for (NetListener lis : mListeners) {
                        if (lis != null) {
                            lis.onNetWorkChange(netType);
                        }
                    }
                }
            }
        };
        mFilter = new IntentFilter();
        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
    }

    /**
     * 注册网络监听器
     */
    public void registReceiver() {
        try {
            registerReceiver(mReceiver, mFilter);
        } catch (Exception e) {
        }
    }

    /**
     * 注销网络监听器
     */
    public void unRegisterNetListener() {
        if (mListeners != null) {
            mListeners.clear();
        }
        try {
            unregisterReceiver(mReceiver);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
