package com.crecg.staffshield.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.crecg.staffshield.R;
import com.crecg.staffshield.common.BaseActivity;

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
        btn_register_next_step.setOnClickListener(this);
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
            case R.id.btn_register_next_step: // 下一步
                Intent intent = new Intent(this, RegisterTwoStepActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_registration_agreement: // 注册协议
                break;
            case R.id.tv_register_privacy_policy: //  隐私策略
                break;
        }
    }
}
