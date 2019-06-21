package com.crecg.crecglibrary.network;


import android.text.TextUtils;

import com.crecg.crecglibrary.CommonConstants;
import com.crecg.crecglibrary.network.model.ResultModel;
import com.crecg.crecglibrary.utils.ToastUtil;
import com.crecg.crecglibrary.utils.encrypt.DESUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 基础ObserverAdapter
 */
public abstract class CommonObserverAdapter<T1,T2> implements Observer<T1> {
    public static final String TAG = CommonObserverAdapter.class.getSimpleName();

    @Override
    public void onSubscribe(Disposable d) {
    }

    @Override
    public void onNext(T1 result) {
        String encStr = (String)result;
        if (TextUtils.isEmpty(encStr)) {
            onMyError();
        } else {
            try {
                String desStr = DESUtil.decrypt(encStr);
                ResultModel<T2> desModel = new Gson().fromJson(desStr,new TypeToken<ResultModel<T2>>() {
                }.getType());
                onMySuccess(desModel);
            } catch (Exception e) {
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
    }

    public void onMySuccess(ResultModel<T2> result) {
    }

    public void onMyError() {
    }

}
