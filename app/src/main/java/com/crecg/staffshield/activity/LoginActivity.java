package com.crecg.staffshield.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.crecg.crecglibrary.RemoteFactory;
import com.crecg.crecglibrary.network.CommonObserverAdapter;
import com.crecg.crecglibrary.network.CommonRequestProxy;
import com.crecg.crecglibrary.network.model.LoginModel;
import com.crecg.crecglibrary.network.model.ResultModel;
import com.crecg.crecglibrary.utils.ToastUtil;
import com.crecg.crecglibrary.utils.encrypt.DESUtil;
import com.crecg.staffshield.R;
import com.crecg.staffshield.common.BaseActivity;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 登录
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_login_phone; // 手机号
    private EditText et_login_password; // 登录密码
    private TextView tv_login_forget_password; // 忘记密码
    private TextView tv_login_sign; // 新用户注册
    private Button btn_login; // 登录
    private ImageView iv_login_delete_phone; // 删除手机号
    private ImageView iv_login_delete_password; // 删除登录密码
    private String loginPhone;
    private String loginPassword;
    private LoginModel loginData;
    private String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    protected void initView() {
        et_login_phone = findViewById(R.id.et_login_phone);
        et_login_password = findViewById(R.id.et_login_password);
        tv_login_forget_password = findViewById(R.id.tv_login_forget_password);
        tv_login_sign = findViewById(R.id.tv_login_sign);
        iv_login_delete_phone = findViewById(R.id.iv_fine_password_delete_phone);
        iv_login_delete_password = findViewById(R.id.iv_login_delete_password);
        btn_login = findViewById(R.id.btn_login);

        initEditTextListener();

        tv_login_forget_password.setOnClickListener(this);
        tv_login_sign.setOnClickListener(this);
        iv_login_delete_phone.setOnClickListener(this);
        iv_login_delete_password.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }

    private void initEditTextListener() {
        // 手机号 输入监听
        et_login_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // // 密码 输入监听
        et_login_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.tv_login_forget_password: // 忘记密码
                intent = new Intent(this,FindLoginPasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_fine_password_delete_phone: // 删除手机号
                break;
            case R.id.iv_login_delete_password: // 删除登录密码
                break;
            case R.id.btn_login: // 登录
                loginPhone = et_login_phone.getText().toString();
                loginPassword = et_login_password.getText().toString();
                getLoginByPost();
                break;
            case R.id.tv_login_sign: // 新用户注册
                intent = new Intent(this,RegisterOneStepActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * 登录
     */
    private void getLoginByPost() {
        HashMap<String, Object> param = new HashMap<>();
        param.put("mobile", "13593262371");
        param.put("password", "aa111111");
        data = DESUtil.encMap(param);

        HashMap<String, Object> param2 = new HashMap<>();
        param2.put("requestKey", data);

        RemoteFactory.getInstance().getProxy(CommonRequestProxy.class)
                .getLoginByPost(param2)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CommonObserverAdapter<String, LoginModel>() {
            @Override
            public void onMyError() {
                ToastUtil.showCustom("登录失败");
            }

            @Override
            public void onMySuccess(ResultModel<LoginModel> result) {
                if (result != null && result.code != null && result.data != null) {
                    Log.i("hh", "登录接口返回数据：" + result);
                    loginData = result.data;
                    String userId = loginData.userId;
                    if ("true".equals(loginData.flag)) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        ToastUtil.showCustom("登录成功");
                    } else {
                        ToastUtil.showCustom(loginData.flag);
                    }
                }
            }
        });
    }

}
