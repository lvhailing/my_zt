package com.crecg.staffshield.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crecg.staffshield.R;
import com.crecg.staffshield.common.BaseActivity;
import com.crecg.staffshield.utils.StringUtil;

/**
 * 修改登录密码
 * Created by junde on 2018/12/25.
 */

public class ModifyLoginPasswordActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back;
    private TextView tv_common_title;
    private EditText et_old_password;
    private EditText et_new_password;
    private EditText et_confirm_password;
    private ImageView iv_delete_new_password; // 删除新密码
    private ImageView iv_delete_old_password; // 删除旧密码
    private ImageView iv_delete_confirm_password; // 删除确认密码
    private Button btn_sure; // 确定


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_login_password);

        initView();
    }

    private void initView() {
        iv_back = findViewById(R.id.iv_back);
        tv_common_title = findViewById(R.id.tv_common_title);
        tv_common_title.setText("修改登录密码");

        et_old_password = findViewById(R.id.et_old_password);
        et_new_password = findViewById(R.id.et_new_password);
        et_confirm_password = findViewById(R.id.et_confirm_password);
        iv_delete_new_password = findViewById(R.id.iv_delete_new_password);
        iv_delete_old_password = findViewById(R.id.iv_delete_old_password);
        iv_delete_confirm_password = findViewById(R.id.iv_delete_confirm_password);
        btn_sure = findViewById(R.id.btn_sure);


        iv_back.setOnClickListener(this);
        btn_sure.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_sure:
                String oldPassword = et_old_password.getText().toString().trim();
                String newPassword = et_new_password.getText().toString().trim();
                String confirmPassword = et_confirm_password.getText().toString().trim();
                if (TextUtils.isEmpty(oldPassword)) {
                    Toast.makeText(this, "请输入旧密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(newPassword)) {
                    Toast.makeText(this, "请输入新密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(confirmPassword)) {
                    Toast.makeText(this, "请输入确认密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!StringUtil.checkPassword(oldPassword)) {
                    Toast.makeText(this, "请输入6至8位字母数字组合密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!StringUtil.checkPassword(newPassword)) {
                    Toast.makeText(this, "请输入6至8位字母数字组合密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!newPassword.equals(confirmPassword)) {
                    Toast.makeText(this, "两次密码输入不一致，请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                }
                modifyPassword(); // 调接口
                break;
        }


    }

    /**
     *  重新修改密码
     */
    private void modifyPassword() {

    }
}
