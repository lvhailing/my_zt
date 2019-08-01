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
import com.crecg.crecglibrary.network.model.ReturnOnlyTrueOrFalseModel;
import com.crecg.crecglibrary.network.model.ResultModel;
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
 * 找回登录密码
 */

public class FindLoginPasswordActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back; // 返回
    private TextView tv_common_title; // 标题
    private EditText et_find_password_phone; // 手机号
    private EditText et_find_password_verify_code; // 验证码
    private EditText et_set_new_password; // 新密码
    private ImageView iv_find_password_delete_phone; // 删除手机号
    private ImageView iv_find_password_delete_verify_code; // 删除验证码
    private TextView tv_find_password_get_verify_code; // 获取验证码
    private Button btn_sure; // 确定
    private String phone;
    private String verifyCode;
    private String newPassword;

    private boolean smsflag = true;
    private MyHandler mHandler;
    private String btnString;
    private boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_login_password);

        initView();
    }

    protected void initView() {
        iv_back = findViewById(R.id.iv_back);
        tv_common_title = findViewById(R.id.tv_common_title);
        iv_back.setImageResource(R.mipmap.img_arrow_left2);
        tv_common_title.setText(getResources().getString(R.string.title_find_login_password));

        et_find_password_phone = findViewById(R.id.et_find_password_phone);
        et_find_password_verify_code = findViewById(R.id.et_find_password_verify_code);
        et_set_new_password = findViewById(R.id.et_set_new_password);
        iv_find_password_delete_phone = findViewById(R.id.iv_fine_password_delete_phone);
        iv_find_password_delete_verify_code = findViewById(R.id.iv_find_password_delete_verify_code);
        tv_find_password_get_verify_code = findViewById(R.id.tv_find_password_get_verify_code);
        btn_sure = findViewById(R.id.btn_sure);

        iv_back.setOnClickListener(this);
        iv_find_password_delete_phone.setOnClickListener(this);
        iv_find_password_delete_verify_code.setOnClickListener(this);
        tv_find_password_get_verify_code.setOnClickListener(this);
        btn_sure.setOnClickListener(this);

        mHandler = new MyHandler();
        btnString = getResources().getString(R.string.get_verify_code_again);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_fine_password_delete_phone:
                break;
            case R.id.iv_find_password_delete_verify_code:
                break;
            case R.id.tv_find_password_get_verify_code: // 获取验证码
                phone = et_find_password_phone.getText().toString();
                if (TextUtils.isEmpty(phone.trim())) {
                    ToastUtil.showCustom("请输入手机号");
                    return;
                }
                if (!StringUtil.isMobileNO(phone)) {
                    ToastUtil.showCustom("请输入正确的手机号");
                    return;
                }
                tv_find_password_get_verify_code.setClickable(false);
                getVerifyCodeByPost();
                break;
            case R.id.btn_sure: // 确定
                checkDataNull();
                break;
        }

    }

    private void checkDataNull() {
        phone = et_find_password_phone.getText().toString();
        verifyCode = et_find_password_verify_code.getText().toString();
        newPassword = et_set_new_password.getText().toString();
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
        if(TextUtils.isEmpty(newPassword)){
            ToastUtil.showCustom("请输入密码");
            return;
        }
        if(!StringUtil.checkPassword(newPassword)){
            ToastUtil.showCustom("请输入6至8位字母数字组合密码");
            return;
        }
        getFindPwdByPost();
    }

    /**
     * 获取验证码
     */
    private void getVerifyCodeByPost() {
        HashMap<String, Object> param = new HashMap<>();
        param.put("mobile", userPhone);
        param.put("userId", userId);
        param.put("busiType", UrlRoot.RETPWD);
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
                    tv_find_password_get_verify_code.setClickable(true);
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

    /**
     * 修改登录密码
     */
    private void getFindPwdByPost() {
            HashMap<String, Object> param = new HashMap<>();
            param.put("mobile", userPhone);
            param.put("newPassword", newPassword);
            param.put("validateCode", verifyCode);
            String data = DESUtil.encMap(param);

            HashMap<String, Object> paramWrapper = new HashMap<>();
            paramWrapper.put("requestKey", data);
            RemoteFactory.getInstance().getProxy(CommonRequestProxy.class)
                .getFindPwdByPost(paramWrapper)
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
                            Intent intent = new Intent(FindLoginPasswordActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }else {
                            ToastUtil.showCustom(verifyCodeModel.data.message);
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
            tv_find_password_get_verify_code.setClickable(true);
            tv_find_password_get_verify_code.setTextColor(getResources().getColor(R.color.main_blue_4A67F5));
            tv_find_password_get_verify_code.setText(getResources().getString(R.string.get_verify_code_again));
        } else if (time < 60) {
            tv_find_password_get_verify_code.setClickable(false);
            tv_find_password_get_verify_code.setTextColor(getResources().getColor(R.color.gray_d));
            tv_find_password_get_verify_code.setText(btnString + "(" + time + ")");

        }
    }
}
