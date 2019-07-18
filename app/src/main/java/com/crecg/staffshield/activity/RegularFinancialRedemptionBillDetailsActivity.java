package com.crecg.staffshield.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.crecg.staffshield.R;
import com.crecg.staffshield.common.BaseActivity;

/**
 * 定期理财赎回 账单详情页
 */

public class RegularFinancialRedemptionBillDetailsActivity extends BaseActivity {

    private ImageView iv_back;
    private TextView tv_common_title;
    private TextView tv_redemption_amount; // 赎回金额
    private TextView tv_start_calculating_earnings_date; // 开始计算收益时间（周三）
    private TextView tv_start_calculating_earnings_week; // 开始计算收益时间（周三）
    private TextView tv_maturity_time; // 理财到期时间
    private TextView tv_maturity_time_week; // 理财到期时间
    private TextView tv_principal_and_interest_date; // 本息到账时间
    private ImageView iv_second_circle; //
    private ImageView iv_third_circle; //
    private ImageView iv_first_vertical_line; //
    private ImageView iv_second_vertical_line; //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_regular_finacial_redemption_bill_details);

        initView();
    }

    private void initView() {
        iv_back = findViewById(R.id.iv_back);
        tv_common_title = findViewById(R.id.tv_common_title);
        iv_back.setBackgroundResource(R.mipmap.img_arrow_left);
        tv_common_title.setText("账单详情");

        tv_redemption_amount = findViewById(R.id.tv_redemption_amount);
        tv_start_calculating_earnings_date = findViewById(R.id.tv_start_calculating_earnings_date);
        tv_start_calculating_earnings_week = findViewById(R.id.tv_start_calculating_earnings_week);
        tv_maturity_time = findViewById(R.id.tv_maturity_time);
        tv_maturity_time_week = findViewById(R.id.tv_maturity_time_week);
        tv_principal_and_interest_date = findViewById(R.id.tv_principal_and_interest_date);
        iv_second_circle = findViewById(R.id.iv_second_circle);
        iv_third_circle = findViewById(R.id.iv_third_circle);
        iv_first_vertical_line = findViewById(R.id.iv_first_vertical_line);
        iv_second_vertical_line = findViewById(R.id.iv_second_vertical_line);


        iv_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
