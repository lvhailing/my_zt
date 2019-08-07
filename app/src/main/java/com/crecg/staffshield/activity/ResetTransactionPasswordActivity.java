package com.crecg.staffshield.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.crecg.crecglibrary.RemoteFactory;
import com.crecg.crecglibrary.network.CommonObserverAdapter;
import com.crecg.crecglibrary.network.CommonRequestProxy;
import com.crecg.crecglibrary.network.UrlRoot;
import com.crecg.crecglibrary.network.model.CommonResultModel;
import com.crecg.crecglibrary.network.model.ReturnOnlyTrueOrFalseModel;
import com.crecg.crecglibrary.utils.IdCardCheckUtils;
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
 * 重置交易密码
 */

public class ResetTransactionPasswordActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back;
    private TextView tv_common_title;
    private EditText et_reset_transaction_password_id_num; // 身份证号
    private EditText et_reset_transaction_password_verify_code; // 验证码
    private EditText et_reset_transaction_password_new; // 新密码
    private EditText et_reset_transaction_password_confirm; // 确认密码
    private TextView tv_reset_transaction_password_get_verify_code; // 获取验证码

    private ImageView iv_delete_id_card;
    private ImageView iv_reset_transaction_password_delete_verify_code;
    private ImageView iv_reset_transaction_password_delete_new;
    private ImageView iv_reset_transaction_password_delete_confirm;

    private Button btn_sure; // 确定
    private String et_idNo;
    private String verifyCode;
    private String newPwd;
    private String confirmPwd;

    private boolean smsflag = true;
    private MyHandler mHandler;
    private String btnString;
    private boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_reset_transaction_password);

        initView();

    }

    private void initView() {
        iv_back = findViewById(R.id.iv_back);
        tv_common_title = findViewById(R.id.tv_common_title);
        iv_back.setImageResource(R.mipmap.img_arrow_left2);
        tv_common_title.setText(getResources().getString(R.string.reset_transaction_password));

        et_reset_transaction_password_id_num = findViewById(R.id.et_reset_transaction_password_id_num);
        et_reset_transaction_password_verify_code = findViewById(R.id.et_reset_transaction_password_verify_code);
        et_reset_transaction_password_new = findViewById(R.id.et_reset_transaction_password_new);
        et_reset_transaction_password_confirm = findViewById(R.id.et_reset_transaction_password_confirm);
        tv_reset_transaction_password_get_verify_code = findViewById(R.id.tv_reset_transaction_password_get_verify_code);

        iv_delete_id_card = findViewById(R.id.iv_delete_id_card);
        iv_reset_transaction_password_delete_verify_code = findViewById(R.id.iv_reset_transaction_password_delete_verify_code);
        iv_reset_transaction_password_delete_new = findViewById(R.id.iv_reset_transaction_password_delete_new);
        iv_reset_transaction_password_delete_confirm = findViewById(R.id.iv_reset_transaction_password_delete_confirm);
        btn_sure = findViewById(R.id.btn_sure);

        iv_back.setOnClickListener(this);
        tv_reset_transaction_password_get_verify_code.setOnClickListener(this);
        iv_delete_id_card.setOnClickListener(this);
        iv_reset_transaction_password_delete_verify_code.setOnClickListener(this);
        iv_reset_transaction_password_delete_new.setOnClickListener(this);
        iv_reset_transaction_password_delete_confirm.setOnClickListener(this);
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
            case R.id.tv_reset_transaction_password_get_verify_code: // 获取验证码
                tv_reset_transaction_password_get_verify_code.setClickable(false);
                getVerifyCodeByPost();
                break;
            case R.id.iv_delete_id_card: // 删除身分证号
                break;
            case R.id.iv_reset_transaction_password_delete_verify_code: // 删除验证码
                break;
            case R.id.iv_reset_transaction_password_delete_new: // 删除新密码
                break;
            case R.id.iv_reset_transaction_password_delete_confirm: // 删除确认密码
                break;
            case R.id.btn_sure: // 确定
                checkDataNull();
                break;
        }

    }
    /**
     * 获取验证码
     */
    private void getVerifyCodeByPost() {
        HashMap<String, Object> param = new HashMap<>();
//        param.put("mobile", userPhone);
        param.put("userId", userId);
        param.put("busiType", UrlRoot.RESETPWD);
        String data = DESUtil.encMap(param);
        Log.i("hh", "用户Id:" + userId);

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
                        CommonResultModel<ReturnOnlyTrueOrFalseModel> verifyCodeModel = new Gson().fromJson(result, new TypeToken<CommonResultModel<ReturnOnlyTrueOrFalseModel>>() {
                        }.getType());
                        if (verifyCodeModel.data == null) {
                            return;
                        }
                        if (Boolean.parseBoolean(verifyCodeModel.data.flag)) {
                            ToastUtil.showCustom(verifyCodeModel.data.message);
                            smsflag = true;
                            startThread();
                        }else {
                            tv_reset_transaction_password_get_verify_code.setClickable(true);
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

    private void checkDataNull() {
        et_idNo = et_reset_transaction_password_id_num.getText().toString();
        verifyCode = et_reset_transaction_password_verify_code.getText().toString();
        newPwd = et_reset_transaction_password_new.getText().toString();
        confirmPwd = et_reset_transaction_password_confirm.getText().toString();
        if (TextUtils.isEmpty(et_idNo)) {
            ToastUtil.showCustom("请输入身份证号");
            return;
        }
        if (!TextUtils.isEmpty(et_idNo) && !IdCardCheckUtils.isIdCard((et_idNo.toUpperCase()))) {
            ToastUtil.showCustom("请输入正确的身份证号");
            return;
        }
        if(TextUtils.isEmpty(verifyCode)){
            ToastUtil.showCustom("请输入验证码");
            return;
        }
        if(TextUtils.isEmpty(newPwd)){
            ToastUtil.showCustom("请输入交易密码");
            return;
        }
        if(TextUtils.isEmpty(confirmPwd)){
            ToastUtil.showCustom("请确认交易密码");
            return;
        }
        ResetTransactionPwdByPost();
    }

    private void ResetTransactionPwdByPost() {
        HashMap<String, Object> param = new HashMap<>();
        param.put("userId", userId);
        param.put("idNo", et_idNo);
        param.put("validateCode", verifyCode);
        param.put("eacTrsPwd", newPwd); // 测试时输入的交易密码是:333 333
        String data = DESUtil.encMap(param);

        HashMap<String, Object> paramWrapper = new HashMap<>();
        paramWrapper.put("requestKey", data);
        RemoteFactory.getInstance().getProxy(CommonRequestProxy.class)
                .resetTransactionPwdByPost(paramWrapper)
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
                        CommonResultModel<ReturnOnlyTrueOrFalseModel> verifyCodeModel = new Gson().fromJson(result, new TypeToken<CommonResultModel<ReturnOnlyTrueOrFalseModel>>() {
                        }.getType());
                        if (verifyCodeModel.data == null) {
                            return;
                        }
                        if (Boolean.parseBoolean(verifyCodeModel.data.flag)) {
                            ToastUtil.showCustom(verifyCodeModel.data.message);
                            Intent intent = new Intent(ResetTransactionPasswordActivity.this, SettingActivity.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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

        private void setButtonStyle(int time) {
            if (time == 0) {
                tv_reset_transaction_password_get_verify_code.setClickable(true);
                tv_reset_transaction_password_get_verify_code.setTextColor(getResources().getColor(R.color.main_blue_4A67F5));
                tv_reset_transaction_password_get_verify_code.setText(getResources().getString(R.string.get_verify_code_again));
            } else if (time < 60) {
                tv_reset_transaction_password_get_verify_code.setClickable(false);
                tv_reset_transaction_password_get_verify_code.setTextColor(getResources().getColor(R.color.gray_d));
                tv_reset_transaction_password_get_verify_code.setText(btnString + "(" + time + ")");

            }
        }
    }
}
