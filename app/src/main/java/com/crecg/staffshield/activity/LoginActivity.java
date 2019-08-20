package com.crecg.staffshield.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.crecg.crecglibrary.RemoteFactory;
import com.crecg.crecglibrary.network.CommonObserverAdapter;
import com.crecg.crecglibrary.network.CommonRequestProxy;
import com.crecg.crecglibrary.network.model.CommonResultModel;
import com.crecg.crecglibrary.network.model.LoginModel;
import com.crecg.crecglibrary.utils.ToastUtil;
import com.crecg.crecglibrary.utils.encrypt.DESUtil;
import com.crecg.staffshield.R;
import com.crecg.staffshield.common.BaseActivity;
import com.crecg.staffshield.utils.PreferenceUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
                intent = new Intent(this, FindLoginPasswordActivity.class);
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
                intent = new Intent(this, RegisterTwoStepActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * 登录
     */
    private void getLoginByPost() {
        HashMap<String, Object> param = new HashMap<>();
        param.put("mobile", loginPhone); // 测试号："13593262371"
        param.put("password", loginPassword);
        String  data = DESUtil.encMap(param);

        HashMap<String, Object> paramWrapper = new HashMap<>();
        paramWrapper.put("requestKey", data);

        RemoteFactory.getInstance().getProxy(CommonRequestProxy.class)
                .getLoginByPost(paramWrapper)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CommonObserverAdapter<String, LoginModel>() {
            @Override
            public void onMyError() {
                ToastUtil.showCustom("登录接口获取数据失败");
            }

            @Override
            public void onMySuccess(String result) {
                if (result != null) {
//                    Log.i("hh", "登录接口数据1：" + result);
                    CommonResultModel<LoginModel> loginModel = new Gson().fromJson(result, new TypeToken<CommonResultModel<LoginModel>>() {
                    }.getType());
//                    Log.i("hh", "登录接口数据2：" + loginModel);
//                    Log.i("hh", "登录接口数据3：" + "userid= " + loginModel.data.userId + "mobile= " + loginModel.data.mobile);
                    if (loginModel.data == null) {
                        return;
                    }
                    try {
                        String encUserId = DESUtil.encrypt(loginModel.data.userId);
                        String mobile = DESUtil.encrypt(loginModel.data.mobile);
                        String idNo = DESUtil.encrypt(loginModel.data.idNo);
                        PreferenceUtil.setUserId(encUserId);
                        PreferenceUtil.setPhone(mobile);
                        PreferenceUtil.setIdNo(idNo);

                        if (Boolean.parseBoolean(loginModel.data.flag)) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            ToastUtil.showCustom(loginModel.data.message);
                        } else {
                            ToastUtil.showCustom(loginModel.data.message);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

//    public static <T> T jsonToObject(String jsonStr, Type listType) {
//        return new Gson().fromJson(jsonStr,listType);
//    }
}
