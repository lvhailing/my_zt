package com.crecg.staffshield.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.crecg.staffshield.R;
import com.crecg.staffshield.common.BaseActivity;

/**
 * 注册(第一步)
 * Created by hong on 2018/12/24.
 */

public class RegisterOneStepActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_login_phone; // 手机号
    private EditText et_login_password; // 登录密码
    private TextView tv_login_forget_password; // 忘记密码
    private TextView tv_login_sign; // 新用户注册
    private Button btn_login; // 登录
    private ImageView iv_login_delete_phone; // 删除手机号
    private ImageView iv_login_delete_password; // 删除登录密码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_one_step_activity);

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

        tv_login_forget_password.setOnClickListener(this);
        tv_login_sign.setOnClickListener(this);
        iv_login_delete_phone.setOnClickListener(this);
        iv_login_delete_password.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login_forget_password: // 忘记密码
                break;
            case R.id.iv_fine_password_delete_phone: // 删除手机号
                break;
            case R.id.iv_login_delete_password: // 删除登录密码
                break;
            case R.id.btn_login: // 登录
                break;
            case R.id.tv_login_sign: // 新用户注册
                break;
        }
    }
}
