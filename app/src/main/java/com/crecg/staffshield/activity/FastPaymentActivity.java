package com.crecg.staffshield.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crecg.crecglibrary.RemoteFactory;
import com.crecg.crecglibrary.network.CommonObserverAdapter;
import com.crecg.crecglibrary.network.CommonRequestProxy;
import com.crecg.crecglibrary.network.UrlRoot;
import com.crecg.crecglibrary.network.model.ResultModel;
import com.crecg.crecglibrary.network.model.ReturnOnlyTrueOrFalseModel;
import com.crecg.crecglibrary.utils.ToastUtil;
import com.crecg.crecglibrary.utils.encrypt.DESUtil;
import com.crecg.staffshield.R;
import com.crecg.staffshield.common.BaseActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 快捷支付--签约
 */

public class FastPaymentActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back;
    private TextView tv_common_title;

    private String btnFlag = "1"; // 1:转出     2:转入
    private TextView tv_tips;
    private EditText et_fast_payment_verify_code; // 银行验证码
    private TextView tv_get_verify_code; // 获取验证码
    private Button btn_next_step; // 下一步

    private boolean smsflag = true;
    private MyHandler mHandler;
    private String btnString;
    private boolean flag;
    private String verifyCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_fast_payment);

        initView();
    }

    private void initView() {
        iv_back = findViewById(R.id.iv_back);
        tv_common_title = findViewById(R.id.tv_common_title);
        iv_back.setBackgroundResource(R.mipmap.img_arrow_left);
        tv_common_title.setText("快捷支付-签约");

        tv_tips = findViewById(R.id.tv_tips);
        et_fast_payment_verify_code = findViewById(R.id.et_fast_payment_verify_code);
        tv_get_verify_code = findViewById(R.id.tv_get_verify_code);
        btn_next_step = findViewById(R.id.btn_next_step);

        iv_back.setOnClickListener(this);
        tv_get_verify_code.setOnClickListener(this);
        btn_next_step.setOnClickListener(this);

        mHandler = new MyHandler();
        btnString = getResources().getString(R.string.get_verify_code_again);
    }

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            setButtonStyle(msg.arg1);
        }
    }

    private void setButtonStyle(int time) {
        if (time == 0) {
            tv_get_verify_code.setClickable(true);
            tv_get_verify_code.setTextColor(getResources().getColor(R.color.main_blue_4A67F5));
            tv_get_verify_code.setText(getResources().getString(R.string.get_verify_code_again));
        } else if (time < 60) {
            tv_get_verify_code.setClickable(false);
            tv_get_verify_code.setTextColor(getResources().getColor(R.color.gray_d));
            tv_get_verify_code.setText(btnString + "(" + time + ")");

        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_get_verify_code: // 获取验证码
                getBankVerifyCode();
                break;
            case R.id.btn_next_step: // 下一步
                verifyCode = et_fast_payment_verify_code.getText().toString();
                if (TextUtils.isEmpty(verifyCode)) {
                    return;
                }
                checkPaymentIsSuccessful();
                break;
        }
    }

    /**
     * 获取验证码
     */
    private void getBankVerifyCode() {
        HashMap<String, Object> param = new HashMap<>();
        param.put("userId", userId);
        String data = DESUtil.encMap(param);

        HashMap<String, Object> paramWrapper = new HashMap<>();
        paramWrapper.put("requestKey", data);
        RemoteFactory.getInstance().getProxy(CommonRequestProxy.class)
                .getFastPaymentVerifyCode(paramWrapper).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CommonObserverAdapter<String, ReturnOnlyTrueOrFalseModel>() {
            @Override
            public void onMyError() {
                ToastUtil.showCustom("获取数据失败");
            }

            @Override
            public void onMySuccess(String result) {
                if (result == null) {
                    return;
                }
                ResultModel<ReturnOnlyTrueOrFalseModel> verifyCodeModel = new Gson().fromJson(result, new TypeToken<ResultModel<ReturnOnlyTrueOrFalseModel>>() {
                }.getType());
                if (verifyCodeModel.data == null) {
                    return;
                }
                if (Boolean.parseBoolean(verifyCodeModel.data.flag)) {
                    ToastUtil.showCustom(verifyCodeModel.data.message);
                    smsflag = true;
                    startThread();
                } else {
                    tv_get_verify_code.setClickable(true);
                    smsflag = false;
                    ToastUtil.showCustom(verifyCodeModel.data.message);
                }
            }
        });
    }

    /**
     * 校验验证码、快捷支付是否成功
     */
    private void checkPaymentIsSuccessful() {
        HashMap<String, Object> param = new HashMap<>();
        param.put("userId", userId);
        param.put("smsCode", verifyCode);
        String data = DESUtil.encMap(param);

        HashMap<String, Object> paramWrapper = new HashMap<>();
        paramWrapper.put("requestKey", data);
        RemoteFactory.getInstance().getProxy(CommonRequestProxy.class)
                .getFastPaymentSubmit(paramWrapper).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CommonObserverAdapter<String, ReturnOnlyTrueOrFalseModel>() {
            @Override
            public void onMyError() {
                ToastUtil.showCustom("获取数据失败");
            }

            @Override
            public void onMySuccess(String result) {
                if (result == null) {
                    return;
                }
                ResultModel<ReturnOnlyTrueOrFalseModel> checkIsSuccessfulDataModel = new Gson().fromJson(result, new TypeToken<ResultModel<ReturnOnlyTrueOrFalseModel>>() {
                }.getType());
                if (checkIsSuccessfulDataModel.data == null) {
                    return;
                }
                if (Boolean.parseBoolean(checkIsSuccessfulDataModel.data.flag)) {
                    ToastUtil.showCustom(checkIsSuccessfulDataModel.data.message);
                } else {
                    ToastUtil.showCustom(checkIsSuccessfulDataModel.data.message);
                }
            }
        });
    }


    private void startThread() {
        if (smsflag) {
            Thread t = new Thread(myRunnable);
            flag = true;
            t.start();
        }
    }

    private int time = 60;
    Runnable myRunnable = new Runnable() {

        @Override
        public void run() {
            while (flag) {
                Message msg = new Message();
                time -= 1;
                msg.arg1 = time;
                if (time == 0) {
                    flag = false;
                    mHandler.sendMessage(msg);
                    time = 60;
                    mHandler.removeCallbacks(myRunnable);
                } else {
                    mHandler.sendMessage(msg);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    };
}
