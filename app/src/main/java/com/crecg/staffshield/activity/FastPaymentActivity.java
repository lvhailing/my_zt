package com.crecg.staffshield.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crecg.staffshield.R;
import com.crecg.staffshield.common.BaseActivity;

/**
 *  快捷支付--签约
 */

public class FastPaymentActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back;
    private TextView tv_common_title;

    private String btnFlag = "1"; // 1:转出     2:转入
    private TextView tv_tips;
    private EditText et_fast_payment_verify_code; // 银行验证码
    private TextView tv_get_verify_code; // 获取验证码
    private Button btn_next_step;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_fast_payment);

        initView();
    }

    private void initView() {
        iv_back = findViewById(R.id.iv_back);
        tv_common_title = findViewById(R.id.tv_common_title);
        iv_back.setBackgroundResource(R.mipmap.img_arrow_left);
        tv_common_title.setText("快捷支付-签约");

        tv_tips = findViewById(R.id.tv_tips);
        et_fast_payment_verify_code = findViewById(R.id.et_fast_payment_verify_code);
        tv_get_verify_code = findViewById(R.id.tv_get_verify_code);
        btn_next_step = findViewById(R.id.btn_next_step);

        iv_back.setOnClickListener(this);
        tv_get_verify_code.setOnClickListener(this);
        btn_next_step.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_get_verify_code: // 获取验证码
                // Todo 调银行接口
                break;
            case R.id.btn_next_step: // 下一步
                break;
        }
    }
}
