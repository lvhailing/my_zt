package com.crecg.crecglibrary.network;


import android.text.TextUtils;
import android.util.Log;

import com.crecg.crecglibrary.utils.encrypt.DESUtil;

import java.net.URLDecoder;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 基础ObserverAdapter
 *
 * T1：观察者期望观察的项的类型
 */
public abstract class CommonObserverAdapter<T1, T2> implements Observer<T1> {
    public static final String TAG = CommonObserverAdapter.class.getSimpleName();

    @Override
    public void onSubscribe(Disposable d) {
    }

    @Override
    public void onNext(T1 result) {
        String encStr = (String) result; // 后台返回的加密数据
        Log.i("hh", "后台返回的加密数据 -- encStr：" + encStr);
        if (TextUtils.isEmpty(encStr)) {
            onMyError();
        } else {
            try {
                String decodeStr = URLDecoder.decode(encStr, "utf-8");
                Log.i("hh", "onNext方法中解码后数据--decodeStr：" + decodeStr);

                String desStr = DESUtil.decrypt(decodeStr); // 解密后的返回数据
                Log.i("hh", "onNext方法中解密后数据--desStr：" + desStr);
                onMySuccess(desStr);
            } catch (Exception e) {
                Log.i("hh", "onNext --- catch的错：" + e);
                e.printStackTrace();
            }
        }
    }

    //这个方法是收到系统回调后 再传递给fragment的
    @Override
    public void onError(Throwable e) {
        Log.i("hh","系统回调的onError方法："+e.getMessage());
        onMyError();
    }

    @Override
    public void onComplete() {
//        Log.i("hh", "onComplete()回调了");
    }

    public void onMySuccess(String result) {
    }

    public void onMyError() {
    }

}
