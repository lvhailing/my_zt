package com.crecg.staffshield.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.crecg.crecglibrary.utils.ToastUtil;
import com.crecg.staffshield.R;
import com.crecg.staffshield.common.BaseActivity;
import com.crecg.staffshield.utils.StringUtil;

/**
 * 添加银行卡
 * Created by junde on 2019/7/24.
 */

public class AddBankCardActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back;
    private TextView tv_common_title;

    private ImageView iv_normal_bank_logo; // 银行logo
    private TextView tv_bank_name; // 银行名称
    private TextView tv_bank_card_num; //
    private TextView tv_look_bank_and_quota; // 支持银行及限额
    private TextView tv_cardholder; // 持卡人
    private TextView tv_id_num; // 身份证号
    private EditText et_bank_card_num; // 输入银行卡号
    private EditText et_phone_num; // 手机号
    private TextView tv_get_verify_code; // 获取验证码
    private EditText et_verify_code;
    private EditText et_transaction_password; // 交易密码
    private EditText et_transaction_password_confirm; // 确认交易密码
    private TextView tv_agreement1;
    private TextView tv_agreement2;
    private Button btn_next_step; // 下一步

    private String bankCardNum;
    private String phoneNum;
    private String verifyCode;
    private String transactionPassword;
    private String transactionPasswordConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_add_bank);
        initView();
    }

    private void initView() {
        setTitle();

        iv_normal_bank_logo = findViewById(R.id.iv_normal_bank_logo);
        tv_bank_name = findViewById(R.id.tv_bank_name);
        tv_bank_card_num = findViewById(R.id.tv_bank_card_num);
        tv_look_bank_and_quota = findViewById(R.id.tv_look_bank_and_quota);
        tv_cardholder = findViewById(R.id.tv_cardholder);
        tv_id_num = findViewById(R.id.tv_id_num);
        et_bank_card_num = findViewById(R.id.et_bank_card_num);
        et_phone_num = findViewById(R.id.et_phone_num);
        tv_get_verify_code = findViewById(R.id.tv_get_verify_code);
        et_verify_code = findViewById(R.id.et_verify_code);
        et_transaction_password = findViewById(R.id.et_transaction_password);
        et_transaction_password_confirm = findViewById(R.id.et_transaction_password_confirm);
        tv_agreement1 = findViewById(R.id.tv_agreement1);
        tv_agreement2 = findViewById(R.id.tv_agreement2);
        btn_next_step = findViewById(R.id.btn_next_step);

        tv_get_verify_code.setOnClickListener(this);
        btn_next_step.setOnClickListener(this);

        et_bank_card_num.addTextChangedListener(watcher);

    }

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            Log.d("hh", "beforeTextChanged: s = " + s + ", start = " + start + ", count = " + count + ", after = " + after);
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Log.d("hh", "onTextChanged: s = " + s + ", start = " + start + ", before = " + before + ", count = " + count);
            String str = s.toString();
            tv_bank_card_num.setText(getDefaultPlaceholderLength(str));

        }

        @Override
        public void afterTextChanged(Editable s) {
            Log.d("hh", "afterTextChanged: " + s);

            String str = s.toString().trim().replace(" ", "");
            String result = "";
            if (str.length() >= 4) {
                et_bank_card_num.removeTextChangedListener(watcher);
                for (int i = 0; i < str.length(); i++) {
                    result += str.charAt(i);
                    if ((i + 1) % 4 == 0) {
                        result += " ";
                    }
                }
                if (result.endsWith(" ")) {
                    result = result.substring(0, result.length() - 1);
                }
                et_bank_card_num.setText(result);
                et_bank_card_num.addTextChangedListener(watcher);
                et_bank_card_num.setSelection(et_bank_card_num.getText().toString().length()); //焦点设置到输入框最后位置
            }
        }
    };

    /**
     * 获取银行卡号默认占位符“*”的长度
     */
    private static String getDefaultPlaceholderLength(String input) {
        StringBuilder sb = new StringBuilder();
        String str = input.replaceAll(" ", "");
        for (int i = 0; i < str.length(); i++) {
            sb.append(str.charAt(i));
            if ((i + 1) % 4 == 0) {
                sb.append(" ");
            }
        }
        String result = sb.toString();
        if (result.endsWith(" ")) {
            result = result.substring(0, result.length() - 1);
        }
        if (result.length() >= 19) {
            return result;
        }
        String res = "**** **** **** ****";
        return result + res.substring(result.length());
    }

    private void setTitle() {
        iv_back = findViewById(R.id.iv_back);
        tv_common_title = findViewById(R.id.tv_common_title);

        iv_back.setImageResource(R.mipmap.img_arrow_left2);
        tv_common_title.setText("添加银行卡");

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
            case R.id.btn_next_step: // 下一步
                checkDataNull();
                Intent intent = new Intent(this, RealNameAuthenticationActivity.class);
                intent.putExtra("userName", "问**");
                startActivity(intent);
                break;
        }
    }

    private void checkDataNull() {
        bankCardNum = et_bank_card_num.getText().toString();
        phoneNum = et_phone_num.getText().toString();
        verifyCode = et_verify_code.getText().toString();
        transactionPassword = et_transaction_password.getText().toString();
        transactionPasswordConfirm = et_transaction_password_confirm.getText().toString();
        if (TextUtils.isEmpty(bankCardNum)) {
            ToastUtil.showCustom("请输入本人储蓄卡号");
            return;
        }
        if (TextUtils.isEmpty(phoneNum.trim())) {
            ToastUtil.showCustom("请输入手机号");
            return;
        }
        if (!StringUtil.isMobileNO(phoneNum.trim())) {
            ToastUtil.showCustom("请输入正确的手机号");
            return;
        }
        if (TextUtils.isEmpty(verifyCode)) {
            ToastUtil.showCustom("请输入验证码");
            return;
        }
        if (TextUtils.isEmpty(transactionPassword)) {
            ToastUtil.showCustom("请输入6位交易密码");
            return;
        }
        if (TextUtils.isEmpty(transactionPasswordConfirm)) {
            ToastUtil.showCustom("请输入确认交易密码");
            return;
        }
    }
}
