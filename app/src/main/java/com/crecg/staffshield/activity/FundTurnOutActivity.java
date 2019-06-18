package com.crecg.staffshield.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.crecg.staffshield.R;
import com.crecg.staffshield.common.BaseActivity;

/**
 * 资金转出
 */

public class FundTurnOutActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back;
    private TextView tv_common_title;
    private TextView tv_bank_card_name;
    private TextView tv_bank_card_num;
    private TextView tv_all;
    private EditText et_turn_out_money;
    private Button btn_confirm_turn_out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_fund_turn_out);

        initView();
    }

    private void initView() {
        iv_back = findViewById(R.id.iv_back);
        tv_common_title = findViewById(R.id.tv_common_title);
        tv_common_title.setText(getResources().getString(R.string.title_fund_turn_out));

        tv_bank_card_name = findViewById(R.id.tv_bank_card_name);
        tv_bank_card_num = findViewById(R.id.tv_bank_card_num);
        et_turn_out_money = findViewById(R.id.et_turn_out_money);
        tv_all = findViewById(R.id.tv_all);
        btn_confirm_turn_out = findViewById(R.id.btn_confirm_turn_out);

        iv_back.setOnClickListener(this);
        tv_all.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_all: // 全部
                et_turn_out_money.setText("123456");
                break;
            case R.id.btn_confirm_turn_out: // 确认转出
                String turnOutMoney = et_turn_out_money.getText().toString();
                if (!TextUtils.isEmpty(turnOutMoney)) {
                    // Todo 调接口
                }
                break;
        }
    }
}
