package com.crecg.crecglibrary.network;


import android.text.TextUtils;
import android.util.Log;

import com.crecg.crecglibrary.CommonConstants;
import com.crecg.crecglibrary.network.model.ResultModel;
import com.crecg.crecglibrary.utils.ToastUtil;
import com.crecg.crecglibrary.utils.encrypt.DESUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.net.URLDecoder;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 基础ObserverAdapter
 */
public abstract class CommonObserverAdapter<T1, T2> implements Observer<T1> {
    public static final String TAG = CommonObserverAdapter.class.getSimpleName();

    @Override
    public void onSubscribe(Disposable d) {
    }

    @Override
    public void onNext(T1 result) {
        String encStr = (String) result; // 后台返回的加密数据
//        Log.i("hh", "后台返回的加密数据 -- encStr：" + encStr);
        if (TextUtils.isEmpty(encStr)) {
            onMyError();
        } else {
            try {
                String decodeStr = URLDecoder.decode(encStr, "utf-8");
//                Log.i("hh", "onNext方法中解码后数据--decodeStr：" + decodeStr);

                String desStr = DESUtil.decrypt(decodeStr); // 解密后的返回数据
                Log.i("hh", "onNext方法中解密后数据--desStr：" + desStr);
                onMySuccess(desStr);
            } catch (Exception e) {
                Log.i("hh", "onNext --- catch的错：" + e);
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onError(Throwable e) {
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
