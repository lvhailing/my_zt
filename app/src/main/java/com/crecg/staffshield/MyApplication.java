package com.crecg.staffshield;

import android.app.Application;

import com.crecg.crecglibrary.CrecgLibManager;

/**
 * Created by lvhailing on 2018/12/13.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        CrecgLibManager.getInstance().init(this);
    }
}
