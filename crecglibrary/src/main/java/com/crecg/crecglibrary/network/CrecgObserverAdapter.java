package com.crecg.crecglibrary.network;


import android.text.TextUtils;

import com.crecg.crecglibrary.CommonConstants;
import com.crecg.crecglibrary.network.model.ResultModel;
import com.crecg.crecglibrary.utils.ToastUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 基础ObserverAdapter
 */
public abstract class CrecgObserverAdapter<T extends ResultModel> implements Observer<T> {
    public static final String TAG = CrecgObserverAdapter.class.getSimpleName();

    @Override
    public void onSubscribe(Disposable d) {
        onMySubscribe(d);
    }

    @Override
    public void onNext(T result) {
        if (result.error_code == CommonConstants.SUCCESS) {
            onMySuccess(result);
            return;
        }
        if (!TextUtils.isEmpty(result.reason)) {
            //状态码不为0
            ToastUtil.showToast(result.reason);
        } else {
            //状态码不为0，error_msg为空，则异常提示
            ToastUtil.showToast(CommonConstants.ERRO_NETWORK_MSG);
        }
        //最终通知各观察者此异常
        onError(new IllegalArgumentException("error code: " + result.error_code));
    }

    @Override
    public void onError(Throwable e) {
        onMyError(e);
    }

    @Override
    public void onComplete() {
        onMyComplete();
    }


    public void onMySubscribe(Disposable d) {

    }

    public void onMySuccess(T result) {

    }

    public void onMyError(Throwable e) {

    }

    public void onMyComplete() {

    }

}
