package com.crecg.staffshield.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.crecg.staffshield.R;
import com.crecg.staffshield.common.BaseActivity;

/**
 * 修改登录密码
 * Created by junde on 2018/12/25.
 */

public class ModifyLoginPasswordActivity extends BaseActivity {

    private EditText et_reset_transaction_password_id_num; // 身份证号
    private EditText et_reset_transaction_password_verify_code; // 验证码
    private EditText et_reset_transaction_password_new; // 新交易密码
    private EditText et_reset_transaction_password_confirm; // 确认交易密码

    private ImageView iv_reset_transaction_password_delete_id; // 删除身份证号
    private ImageView iv_reset_transaction_password_delete_verify_code; // 删除验证吗
    private ImageView iv_reset_transaction_password_delete_new; // 删除新交易密码
    private ImageView iv_reset_transaction_password_delete_confirm; // 删除确认密码

    private TextView tv_reset_transaction_password_get_verify_code; // 获取验证码
    private Button btn_reset_transaction_password_sure; // 确定
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_login_password);

        initView();
    }

    private void initView() {
        et_reset_transaction_password_id_num = findViewById(R.id.et_reset_transaction_password_id_num);
        et_reset_transaction_password_new = findViewById(R.id.et_reset_transaction_password_new);
        et_reset_transaction_password_verify_code = findViewById(R.id.et_reset_transaction_password_verify_code);
        et_reset_transaction_password_confirm = findViewById(R.id.et_reset_transaction_password_confirm);

//        iv_reset_transaction_password_delete_id = findViewById(R.id.iv_reset_transaction_password_delete_id);
        iv_reset_transaction_password_delete_verify_code = findViewById(R.id.iv_reset_transaction_password_delete_verify_code);
        iv_reset_transaction_password_delete_new = findViewById(R.id.iv_reset_transaction_password_delete_new);
        iv_reset_transaction_password_delete_confirm = findViewById(R.id.iv_reset_transaction_password_delete_confirm);

        tv_reset_transaction_password_get_verify_code = findViewById(R.id.tv_reset_transaction_password_get_verify_code);
        btn_reset_transaction_password_sure = findViewById(R.id.btn_reset_transaction_password_sure);
    }

}
