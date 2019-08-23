package com.crecg.staffshield.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crecg.crecglibrary.RemoteFactory;
import com.crecg.crecglibrary.network.CommonObserverAdapter;
import com.crecg.crecglibrary.network.CommonRequestProxy;
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
 * 注册(第二步：身份认证)
 */

public class RegisterTwoStepActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back; // 返回
    private TextView tv_common_title; //  标题
    private EditText et_register_two_step_name; // 姓名
    private EditText et_register_two_step_id; // 身份证号
    private EditText et_register_two_step_login_password; //  登录密码
    private TextView tv_registration_agreement; // 注册协议
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

        setTitle();

        et_register_two_step_name = findViewById(R.id.et_register_two_step_name);
        et_register_two_step_id = findViewById(R.id.et_register_two_step_id);
        et_register_two_step_login_password = findViewById(R.id.et_register_two_step_login_password);
        tv_registration_agreement = findViewById(R.id.tv_registration_agreement);
        String str = "1、注册表示同意<font color = '#4A67F5'>《注册协议》</font>";
        tv_registration_agreement.setText(Html.fromHtml(str));
        btn_register_next_step = findViewById(R.id.btn_register_next_step);

        iv_back.setOnClickListener(this);
        tv_registration_agreement.setOnClickListener(this);
        btn_register_next_step.setOnClickListener(this);
    }

    private void setTitle() {
        iv_back = findViewById(R.id.iv_back);
        tv_common_title = findViewById(R.id.tv_common_title);

        iv_back.setImageResource(R.mipmap.img_arrow_left2);
        tv_common_title.setText("注册");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_registration_agreement: // 注册协议
                Intent intent = new Intent(this, UploadWorkProofActivity.class);
                startActivity(intent);
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

    /**
     * 获取 注册--身份认证 接口数据
     */
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
                .getRegisterTwoStepByPost(paramWrapper).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new CommonObserverAdapter<String>() {
            @Override
            public void onMyError() {
                ToastUtil.showCustom("注册接口二获取数据失败");
            }

            @Override
            public void onMySuccess(String result) {
                if (result == null) {
                    return;
                }
                CommonResultModel<ReturnOnlyTrueOrFalseModel> registerOneStepModel = new Gson().fromJson(result, new TypeToken<CommonResultModel<ReturnOnlyTrueOrFalseModel>>() {
                }.getType());
                if (registerOneStepModel.data == null) {
                    return;
                }
                if (Boolean.parseBoolean(registerOneStepModel.data.flag)) {
                    ToastUtil.showCustom(registerOneStepModel.data.message);
                    Intent intent = new Intent(RegisterTwoStepActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    ToastUtil.showCustom(registerOneStepModel.data.message);
                }
            }
        });
    }
}
