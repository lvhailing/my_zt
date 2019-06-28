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
        Log.i("hh", "后台返回的加密数据 -- encStr：" + encStr);
        if (TextUtils.isEmpty(encStr)) {
            onMyError();
        } else {
            try {
                String aa = URLDecoder.decode(encStr, "utf-8");
                Log.i("hh", "onNext方法中decode的返回数据--aa：" + aa);

                String desStr = DESUtil.decrypt(aa); // 解密后的返回数据
                Log.i("hh", "onNext方法中解密后的返回数据--desStr：" + desStr);
                ResultModel<T2> desModel = new Gson().fromJson(desStr, new TypeToken<ResultModel<T2>>() {
                }.getType());
                onMySuccess(desModel);
            } catch (Exception e) {
                Log.i("hh", "onNext --- catch的错：" + e);
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onError(Throwable e)  {
        Log.i("hh", "系统回调的onError方法里错误是：" + e);
        onMyError();
    }

    @Override
    public void onComplete() {
        Log.i("hh", "回调我了吗？");
    }

    public void onMySuccess(ResultModel<T2> result) {
    }

    public void onMyError() {
    }

}
