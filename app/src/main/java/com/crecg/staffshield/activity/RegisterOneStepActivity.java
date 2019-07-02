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
import com.crecg.staffshield.utils.StringUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 注册(第一步：手机号认证)
 * Created by hong on 2018/12/24.
 */

public class RegisterOneStepActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_register_phone; // 手机号
    private EditText et_register_verify_code; // 验证码
    private TextView tv_register_get_verify_code; // 获取验证码
    //    private ImageView iv_register_delete_phone; // 删除手机号
//    private ImageView iv_register_delete_verify_code; // 删除验证码
    private Button btn_register_next_step; // 下一步
    private TextView tv_registration_agreement; // 注册协议
    private TextView tv_register_privacy_policy; // 隐私策略
    private ImageView iv_back; // 返回
    private TextView tv_common_title; //  标题
    private String phone;
    private String verifyCode;

    private boolean smsflag;
    private MyHandler mHandler;
    private String btnString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_one_step);

        initView();
    }

    protected void initView() {
        iv_back = findViewById(R.id.iv_back);
        tv_common_title = findViewById(R.id.tv_common_title);
        iv_back.setBackgroundResource(R.mipmap.img_arrow_left);
        tv_common_title.setText("注册");

        et_register_phone = findViewById(R.id.et_register_phone);
        et_register_verify_code = findViewById(R.id.et_register_verify_code);
        tv_register_get_verify_code = findViewById(R.id.tv_register_get_verify_code);
        tv_registration_agreement = findViewById(R.id.tv_registration_agreement);
        tv_register_privacy_policy = findViewById(R.id.tv_register_privacy_policy);
//        iv_register_delete_phone = findViewById(R.id.iv_register_delete_phone);
//        iv_register_delete_verify_code = findViewById(R.id.iv_register_delete_verify_code);
        btn_register_next_step = findViewById(R.id.btn_register_next_step);

        iv_back.setOnClickListener(this);
//        iv_register_delete_phone.setOnClickListener(this);
//        iv_register_delete_verify_code.setOnClickListener(this);
        tv_registration_agreement.setOnClickListener(this);
        tv_register_privacy_policy.setOnClickListener(this);
        tv_register_get_verify_code.setOnClickListener(this);
        btn_register_next_step.setOnClickListener(this);

        mHandler = new MyHandler();
        btnString = getResources().getString(R.string.get_verify_code_again);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
//            case R.id.iv_register_delete_phone: // 删除手机号
//                break;
//            case R.id.iv_register_delete_verify_code: // 删除验证码
//                break;
            case R.id.tv_register_get_verify_code: // 获取验证码
                phone = et_register_phone.getText().toString();
                if (TextUtils.isEmpty(phone.trim())) {
                    ToastUtil.showCustom("请输入手机号");
                    return;
                }
                if (!StringUtil.isMobileNO(phone)) {
                    ToastUtil.showCustom("请输入正确的手机号");
                    return;
                }
                et_register_phone.setClickable(false);
                getVerifyCodeByPost();
                break;
            case R.id.btn_register_next_step: // 下一步
                phone = et_register_phone.getText().toString();
                verifyCode = et_register_verify_code.getText().toString();
                if(TextUtils.isEmpty(phone.trim())){
                    ToastUtil.showCustom("请输入手机号");
                    return;
                }
                if(!StringUtil.isMobileNO(phone.trim())){
                    ToastUtil.showCustom("请输入正确的手机号");
                    return;
                }
                if(TextUtils.isEmpty(verifyCode)){
                    ToastUtil.showCustom("请输入验证码");
                    return;
                }
                getRegisterByPost();
                break;
            case R.id.tv_registration_agreement: // 注册协议
                break;
            case R.id.tv_register_privacy_policy: //  隐私策略
                break;
        }
    }

    /**
     *  注册第一步：手机号认证
     */
    private void getRegisterByPost() {
        HashMap<String, Object> param = new HashMap<>();
        param.put("mobile", phone);
        param.put("validateCode", verifyCode);
        String data = DESUtil.encMap(param);

        HashMap<String, Object> paramWrapper = new HashMap<>();
        paramWrapper.put("requestKey", data);

        RemoteFactory.getInstance().getProxy(CommonRequestProxy.class)
                .getRegisterOneStepByPost(paramWrapper)
                .subscribeOn(Schedulers.io())
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
                        ResultModel<ReturnOnlyTrueOrFalseModel> registerOneStepModel = new Gson().fromJson(result, new TypeToken<ResultModel<ReturnOnlyTrueOrFalseModel>>() {
                        }.getType());
                        if (registerOneStepModel.data == null) {
                            return;
                        }
                        if (Boolean.parseBoolean(registerOneStepModel.data.flag)) {
                            ToastUtil.showCustom(registerOneStepModel.data.message);
                            Intent intent = new Intent(RegisterOneStepActivity.this, RegisterTwoStepActivity.class);
                            intent.putExtra("mobile", registerOneStepModel.data.mobile);
                            startActivity(intent);
                        }else {
                            ToastUtil.showCustom(registerOneStepModel.data.message);
                        }
                    }
                });
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
            tv_register_get_verify_code.setClickable(true);
            tv_register_get_verify_code.setTextColor(getResources().getColor(R.color.main_blue_4A67F5));
            tv_register_get_verify_code.setText(getResources().getString(R.string.get_verify_code_again));
        } else if (time < 60) {
            tv_register_get_verify_code.setClickable(false);
            tv_register_get_verify_code.setTextColor(getResources().getColor(R.color.gray_d));
            tv_register_get_verify_code.setText(btnString + "(" + time + ")");

        }
    }

    /**
     * 获取验证码
     */
    private void getVerifyCodeByPost() {
        HashMap<String, Object> param = new HashMap<>();
        param.put("mobile", phone);
        param.put("busiType", UrlRoot.REGISTER);
        String data = DESUtil.encMap(param);
//        Log.i("hh", "手机号与用户Id:" + userPhone + "---" + userId);

        HashMap<String, Object> paramWrapper = new HashMap<>();
        paramWrapper.put("requestKey", data);
        RemoteFactory.getInstance().getProxy(CommonRequestProxy.class)
                .getVerifyCodeByPost(paramWrapper)
                .subscribeOn(Schedulers.io())
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
                        }else {
                            et_register_phone.setClickable(true);
                            smsflag = false;
                            ToastUtil.showCustom(verifyCodeModel.data.message);
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
    private boolean flag;
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
