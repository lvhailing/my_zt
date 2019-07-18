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
 * 资金转入(联名卡转入)
 */

public class EntityBankToElectronicBankActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back;
    private TextView tv_common_title;
    private TextView tv_entity_bank_card_name; // 实体银行卡名称
    private TextView tv_entity_bank_card_last_num; // 实体银行卡后四位卡号
    private TextView tv_transfer_limits; // 日转账限制额度
    private TextView tv_electronic_bank_name1;
    private TextView tv_electronic_bank_name2;
    private EditText et_change_into_money; // 输入转入金额
    private Button btn_confirm_change_into;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_entity_to_electronic);

        initView();
    }

    private void initView() {
        iv_back = findViewById(R.id.iv_back);
        tv_common_title = findViewById(R.id.tv_common_title);
        iv_back.setBackgroundResource(R.mipmap.img_arrow_left);
        tv_common_title.setText(getResources().getString(R.string.title_fund_change_into));

        tv_entity_bank_card_name = findViewById(R.id.tv_entity_bank_card_name);
        tv_entity_bank_card_last_num = findViewById(R.id.tv_entity_bank_card_last_num);
        tv_transfer_limits = findViewById(R.id.tv_transfer_limits);
        tv_electronic_bank_name1 = findViewById(R.id.tv_electronic_bank_name1);
        tv_electronic_bank_name2 = findViewById(R.id.tv_electronic_bank_name2);
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
