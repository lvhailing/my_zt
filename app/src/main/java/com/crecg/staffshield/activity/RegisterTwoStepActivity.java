package com.crecg.staffshield.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.crecg.crecglibrary.RemoteFactory;
import com.crecg.crecglibrary.network.CommonObserverAdapter;
import com.crecg.crecglibrary.network.CommonRequestProxy;
import com.crecg.crecglibrary.network.model.ResultModel;
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
 * 注册(第二步：身份认证)
 */

public class RegisterTwoStepActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back; // 返回
    private TextView tv_common_title; //  标题
    private EditText et_register_two_step_name; // 姓名
    private EditText et_register_two_step_id; // 身份证号
    private EditText et_register_two_step_login_password; //  登录密码
    private Button btn_register_next_step; // 完成注册
    private String userName;
    private String userIdNo;
    private String loginPwd;
    private String mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_two_step);

        initView();
    }

    private void initView() {
        mobile = getIntent().getStringExtra("mobile");
        iv_back = findViewById(R.id.iv_back);
        tv_common_title = findViewById(R.id.tv_common_title);
        iv_back.setBackgroundResource(R.mipmap.img_arrow_left);
        tv_common_title.setText("注册");

        et_register_two_step_name = findViewById(R.id.et_register_two_step_name);
        et_register_two_step_id = findViewById(R.id.et_register_two_step_id);
        et_register_two_step_login_password = findViewById(R.id.et_register_two_step_login_password);
        btn_register_next_step = findViewById(R.id.btn_register_next_step);

        iv_back.setOnClickListener(this);
        btn_register_next_step.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_register_next_step: // 完成注册
                checkNull();
                break;

        }
    }

    private void checkNull() {
        userName = et_register_two_step_name.getText().toString();
        userIdNo = et_register_two_step_id.getText().toString();
        loginPwd = et_register_two_step_login_password.getText().toString();

        if (TextUtils.isEmpty(userName)) {
            ToastUtil.showCustom("请输入真实姓名");
            return;
        }
        if (TextUtils.isEmpty(userIdNo)) {
            ToastUtil.showCustom("请输入身份证号");
            return;
        }
        if (!TextUtils.isEmpty(userIdNo) && !IdCardCheckUtils.isIdCard((userIdNo.toUpperCase()))) {
            ToastUtil.showCustom("请输入正确的身份证号");
            return;
        }
        if (TextUtils.isEmpty(loginPwd)) {
            ToastUtil.showCustom("请输入登录密码");
            return;
        }

        getRegisterTwoByPost();
    }

    private void getRegisterTwoByPost() {
        HashMap<String, Object> param = new HashMap<>();
        param.put("userName", userName);
        param.put("idNo", userIdNo);
        param.put("password", loginPwd);
        param.put("mobile", mobile);
        String data = DESUtil.encMap(param);

        HashMap<String, Object> paramWrapper = new HashMap<>();
        paramWrapper.put("requestKey", data);

        RemoteFactory.getInstance().getProxy(CommonRequestProxy.class)
                .getRegisterTwoStepByPost(paramWrapper)
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
                            Intent intent = new Intent(RegisterTwoStepActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }else {
                            ToastUtil.showCustom(registerOneStepModel.data.message);
                        }
                    }
                });
    }
}
