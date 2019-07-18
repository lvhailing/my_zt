package com.crecg.staffshield.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.crecg.staffshield.R;
import com.crecg.staffshield.common.BaseActivity;

/**
 * 定期理财买入账单详情页
 */

public class RegularFinancialBuyingBillDetailsActivity extends BaseActivity {

    private ImageView iv_back;
    private TextView tv_common_title;
    private TextView tv_buying_amount; // 买入金额
    private TextView tv_buying_date; // 买入时间
    private TextView tv_start_calculating_earnings_date; // 开始计算收益时间（周三）
    private TextView tv_start_calculating_earnings_week; // 开始计算收益时间（周三）
    private TextView tv_maturity_time; // 理财到期时间
    private TextView tv_maturity_time_week; // 理财到期时间
    private TextView tv_principal_and_interest_date; // 本息到账时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_regular_finacial_buying_bill_details);

        initView();
    }

    private void initView() {
        iv_back = findViewById(R.id.iv_back);
        tv_common_title = findViewById(R.id.tv_common_title);
        iv_back.setBackgroundResource(R.mipmap.img_arrow_left);
        tv_common_title.setText("账单详情");

        tv_buying_amount = findViewById(R.id.tv_buying_amount);
        tv_buying_date = findViewById(R.id.tv_buying_date);
        tv_start_calculating_earnings_date = findViewById(R.id.tv_start_calculating_earnings_date);
        tv_start_calculating_earnings_week = findViewById(R.id.tv_start_calculating_earnings_week);
        tv_maturity_time = findViewById(R.id.tv_maturity_time);
        tv_maturity_time_week = findViewById(R.id.tv_maturity_time_week);
        tv_principal_and_interest_date = findViewById(R.id.tv_principal_and_interest_date);

        iv_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
