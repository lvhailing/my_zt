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
 * 定期理财买入
 */

public class RegularFinancialManagementBuyingActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back;
    private TextView tv_common_title;
    private TextView tv_money_amount; //账户余额
    private EditText et_all_money_amount; // 购买金额
    private TextView tv_transfer_of_funds; // 资金转入
    private TextView tv_all; // 全部
    private TextView tv_date_start_accruing_interest; // 计息日期
    private TextView tv_about_agreement; // 相关协议
    private Button btn_buy; // 确认买入
    private ImageView iv_selected_or_unselected; // 相关协议未选中状态
    private String whereToEnterFlag; // 1:首页进   2：工资宝详情页进

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regular_financial_management_buying);

        initView();
    }

    private void initView() {

        iv_back = findViewById(R.id.iv_back);
        tv_common_title = findViewById(R.id.tv_common_title);
        iv_back.setBackgroundResource(R.mipmap.img_arrow_left);
        tv_common_title.setText("堪设一号"); // Todo 需要动态获取标题

//        tv_money_amount = findViewById(R.id.tv_money_amount);
//        et_all_money_amount = findViewById(R.id.et_all_money_amount);
//        tv_transfer_of_funds = findViewById(R.id.tv_transfer_of_funds);
//        tv_all = findViewById(R.id.tv_all);
//        tv_date_start_accruing_interest = findViewById(R.id.tv_date_start_accruing_interest);
//        tv_about_agreement = findViewById(R.id.tv_about_agreement);
//        iv_selected_or_unselected = findViewById(R.id.iv_selected_or_unselected);
//        btn_buy = findViewById(R.id.btn_buy);

//        tv_money_amount.setText("201222.00");
//        iv_back.setOnClickListener(this);
//        tv_transfer_of_funds.setOnClickListener(this);
//        tv_all.setOnClickListener(this);
//        tv_about_agreement.setOnClickListener(this);
//        btn_buy.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_transfer_of_funds: // 资金转入
                intent = new Intent(RegularFinancialManagementBuyingActivity.this, MoneyFromEntityBankToElectronicBankActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_all: // 全部
                et_all_money_amount.setText(tv_money_amount.getText());
                break;
            case R.id.tv_about_agreement: // 相关协议
                break;
            case R.id.btn_buy: // 确认买入
                String moneyAmount = et_all_money_amount.getText().toString();
                if (TextUtils.isEmpty(moneyAmount)) {
                    ToastUtil.showCustom("买入金额不能为空");
                } else {
                    intent = new Intent(this, TransactionPasswordActivity.class);
                    intent.putExtra("fromFlag", "wageTreasureBuy");
                    intent.putExtra("whereToEnterFlag", whereToEnterFlag);
                    startActivity(intent);
                }
                break;
        }
    }
}
