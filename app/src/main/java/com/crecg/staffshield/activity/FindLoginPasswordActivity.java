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
 * 找回登录密码
 * Created by junde on 2018/12/24.
 */

public class FindLoginPasswordActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back; // 返回
    private TextView tv_common_title; // 标题
    private EditText et_find_password_phone; // 手机号
    private EditText et_find_password_verify_code; // 验证码
    private ImageView iv_find_password_delete_phone; // 删除手机号
    private ImageView iv_find_password_delete_verify_code; // 删除验证码
    private TextView tv_find_password_get_verify_code; // 获取验证码
    private Button btn_sure; // 确定

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_login_password);

        initView();
    }

    protected void initView() {
        iv_back = findViewById(R.id.iv_back);
        tv_common_title = findViewById(R.id.tv_common_title);
        iv_back.setBackgroundResource(R.mipmap.img_arrow_left);
        tv_common_title.setText(getResources().getString(R.string.title_find_login_password));

        et_find_password_phone = findViewById(R.id.et_find_password_phone);
        et_find_password_verify_code = findViewById(R.id.et_find_password_verify_code);
        iv_find_password_delete_phone = findViewById(R.id.iv_fine_password_delete_phone);
        iv_find_password_delete_verify_code = findViewById(R.id.iv_find_password_delete_verify_code);
        tv_find_password_get_verify_code = findViewById(R.id.tv_find_password_get_verify_code);
        btn_sure = findViewById(R.id.btn_sure);

        iv_back.setOnClickListener(this);
        iv_find_password_delete_phone.setOnClickListener(this);
        iv_find_password_delete_verify_code.setOnClickListener(this);
        tv_find_password_get_verify_code.setOnClickListener(this);
        btn_sure.setOnClickListener(this);
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
            case R.id.tv_find_password_get_verify_code:
                break;
            case R.id.btn_sure:
                break;
        }

    }
}
