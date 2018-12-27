package com.crecg.staffshield.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.crecg.crecglibrary.RemoteFactory;
import com.crecg.crecglibrary.network.CommonRequestProxy;
import com.crecg.crecglibrary.network.CrecgObserverAdapter;
import com.crecg.crecglibrary.network.model.DataModel;
import com.crecg.crecglibrary.network.model.DataWrapper;
import com.crecg.crecglibrary.network.model.ResultModel;
import com.crecg.crecglibrary.network.model.ResultUserLoginBean;
import com.crecg.crecglibrary.network.model.ResultUserLoginContentBean;
import com.crecg.crecglibrary.utils.ToastUtil;
import com.crecg.staffshield.R;
import com.crecg.staffshield.common.BaseActivity;
import com.crecg.staffshield.utils.DESUtil;
import com.crecg.staffshield.utils.RequestUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class TestActivity1 extends BaseActivity implements View.OnClickListener {
    private Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);

        initView();
    }

    private void initView() {
        btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(this);

    }

    private void requestLogin() {
        Map<String, Object> params = new HashMap<>();
        params.put("mobile", "17111111111");
        params.put("password", "a11111");
        RequestUtil.getBasicMap(params);

        RemoteFactory.getInstance().getProxy(CommonRequestProxy.class).getLoginByPost(params)
//                .getBookListByGet()   //get请求样例
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new CrecgObserverAdapter<ResultModel<ResultUserLoginContentBean>>() {
            @Override
            public void onMyError(Throwable e) {
                //server取单据失败
                ToastUtil.showCustom("调接口失败");
            }

            @Override
            public void onMySuccess(ResultModel<ResultUserLoginContentBean> result) {
                //server取单据成功
                if (result != null && result.data != null) {
                    String strResult = result.toString();
                    try {
                        String data = DESUtil.decrypt(strResult);
                        Log.i("aaa", "如来保登录接口： " + data);
                        ToastUtil.showCustom("调接口成功");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                requestLogin();
                break;

            default:
                break;
        }
    }

}
