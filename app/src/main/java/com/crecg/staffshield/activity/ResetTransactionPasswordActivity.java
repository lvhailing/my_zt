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
 * 重置交易密码
 */

public class ResetTransactionPasswordActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_reset_transaction_password_id_num; // 身份证号
    private EditText et_reset_transaction_password_verify_code; // 验证码
    private EditText et_reset_transaction_password_new; // 新密码
    private EditText et_reset_transaction_password_confirm; // 确认密码
    private TextView tv_reset_transaction_password_get_verify_code; // 获取验证码

    private ImageView iv_delete_id_card;
    private ImageView iv_reset_transaction_password_delete_verify_code;
    private ImageView iv_reset_transaction_password_delete_new;
    private ImageView iv_reset_transaction_password_delete_confirm;

    private Button btn_reset_transaction_password_sure; // 确定

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_transaction_password);

        initView();

    }

    private void initView() {
        et_reset_transaction_password_id_num = findViewById(R.id.et_reset_transaction_password_id_num);
        et_reset_transaction_password_verify_code = findViewById(R.id.et_reset_transaction_password_verify_code);
        et_reset_transaction_password_new = findViewById(R.id.et_reset_transaction_password_new);
        et_reset_transaction_password_confirm = findViewById(R.id.et_reset_transaction_password_confirm);
        tv_reset_transaction_password_get_verify_code = findViewById(R.id.tv_reset_transaction_password_get_verify_code);

        iv_delete_id_card = findViewById(R.id.iv_delete_id_card);
        iv_reset_transaction_password_delete_verify_code = findViewById(R.id.iv_reset_transaction_password_delete_verify_code);
        iv_reset_transaction_password_delete_new = findViewById(R.id.iv_reset_transaction_password_delete_new);
        iv_reset_transaction_password_delete_confirm = findViewById(R.id.iv_reset_transaction_password_delete_confirm);
        btn_reset_transaction_password_sure = findViewById(R.id.btn_sure);

        tv_reset_transaction_password_get_verify_code.setOnClickListener(this);
        iv_delete_id_card.setOnClickListener(this);
        iv_reset_transaction_password_delete_verify_code.setOnClickListener(this);
        iv_reset_transaction_password_delete_new.setOnClickListener(this);
        iv_reset_transaction_password_delete_confirm.setOnClickListener(this);
        btn_reset_transaction_password_sure.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_reset_transaction_password_get_verify_code: // 获取验证码

                break;
            case R.id.iv_delete_id_card: // 删除身分证号
                break;
            case R.id.iv_reset_transaction_password_delete_verify_code: // 删除验证码
                break;
            case R.id.iv_reset_transaction_password_delete_new: // 删除新密码
                break;
            case R.id.iv_reset_transaction_password_delete_confirm: // 删除确认密码
                break;
            case R.id.btn_sure: // 确定 按钮
                break;
        }

    }
}
