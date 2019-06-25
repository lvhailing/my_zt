package com.crecg.staffshield.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.crecg.crecglibrary.utils.ToastUtil;
import com.crecg.staffshield.R;
import com.crecg.staffshield.common.BaseActivity;

/**
 * 资金转入(从绑定的银行卡向开通的电子银行卡转钱)
 */

public class FundChangeIntoActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back;
    private TextView tv_common_title;
    private TextView tv_bank_card_name;
    private TextView tv_bank_card_num;
    private EditText et_change_into_money;
    private Button btn_confirm_change_into;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_fund_change_into);

        initView();
    }

    private void initView() {
        iv_back = findViewById(R.id.iv_back);
        tv_common_title = findViewById(R.id.tv_common_title);
        iv_back.setBackgroundResource(R.mipmap.img_arrow_left);
        tv_common_title.setText(getResources().getString(R.string.title_fund_change_into));

        tv_bank_card_name = findViewById(R.id.tv_bank_card_name);
        tv_bank_card_num = findViewById(R.id.tv_bank_card_num);
        et_change_into_money = findViewById(R.id.et_change_into_money);
        btn_confirm_change_into = findViewById(R.id.btn_confirm_change_into);

        iv_back.setOnClickListener(this);
        btn_confirm_change_into.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_confirm_change_into: // 确认转入
                String changeIntoMoney = et_change_into_money.getText().toString();
                if (TextUtils.isEmpty(changeIntoMoney)) {
                    ToastUtil.showCustom("转入金额不可为空");
                } else {
                    Intent intent = new Intent(this, BankShortMessageVerifyCodeActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }
}
