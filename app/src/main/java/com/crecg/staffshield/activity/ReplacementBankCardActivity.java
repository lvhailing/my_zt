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
 * 更换银行卡
 * Created by junde on 2019/7/23.
 */

public class ReplacementBankCardActivity extends BaseActivity implements View.OnClickListener{
    private ImageView iv_back;
    private TextView tv_common_title;

    private TextView tv_old_bank_card_num; // 原银行卡号
    private TextView tv_look_at_bank_info; // 查看支持的银行及限额
    private EditText et_new_bank_card_num; // 新银行号
    private EditText et_phone_num; // 输入手机号
    private TextView tv_get_verify_code; // 获取验证码
    private EditText et_verify_code; // 输入的验证码
    private Button btn_sure; // 确定
    private String oldBankCardNum; // 原银行卡号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_replacement_bank_card);

        initView();

    }

    private void initView() {
        setTitle();

        oldBankCardNum = getIntent().getStringExtra("oldBankCardNum");

        tv_old_bank_card_num = findViewById(R.id.tv_old_bank_card_num);
        tv_look_at_bank_info = findViewById(R.id.tv_look_at_bank_info);
        et_new_bank_card_num = findViewById(R.id.et_new_bank_card_num);
        et_phone_num = findViewById(R.id.et_phone_num);
        tv_get_verify_code = findViewById(R.id.tv_get_verify_code);
        et_verify_code = findViewById(R.id.et_verify_code);
        btn_sure = findViewById(R.id.btn_sure);

        tv_get_verify_code.setOnClickListener(this);
        btn_sure.setOnClickListener(this);
    }

    private void setTitle() {
        iv_back = findViewById(R.id.iv_back);
        tv_common_title = findViewById(R.id.tv_common_title);

        iv_back.setImageResource(R.mipmap.img_arrow_left2);
        tv_common_title.setText("更换银行卡");

        iv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_get_verify_code: // 获取验证码
                break;
            case R.id.btn_sure: // 确定
                break;
        }

    }
}
