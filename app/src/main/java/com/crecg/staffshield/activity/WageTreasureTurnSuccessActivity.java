package com.crecg.staffshield.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.crecg.staffshield.R;
import com.crecg.staffshield.common.BaseActivity;

/**
 * 工资宝买入状态详情页
 * Created by junde on 2019/7/2.
 */

public class WageTreasureTurnSuccessActivity extends BaseActivity {

    private ImageView iv_back;
    private TextView tv_common_title;
    private TextView tv_turn_into_time; // 工资宝转入时间
    private TextView tv_date_start_calculating_earnings; // 开始计算收益时间（11-30）
    private TextView tv_week_start_calculating_earnings; // 开始计算收益时间（周三）
    private TextView tv_date_arrival_account; // 第一笔收益到账时间（11-30）
    private TextView tv_week_arrival_account; // 第一笔收益到账时间（周三）
    private TextView btn_complete; //完成

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_wage_treasure_turn_success);

        initView();
    }

    private void initView() {
        iv_back = findViewById(R.id.iv_back);
        tv_common_title = findViewById(R.id.tv_common_title);
        iv_back.setVisibility(View.GONE);
        tv_common_title.setText("工资宝转入详情");

        tv_turn_into_time = findViewById(R.id.tv_turn_into_time);
        tv_date_start_calculating_earnings = findViewById(R.id.tv_date_start_calculating_earnings);
        tv_date_start_calculating_earnings = findViewById(R.id.tv_date_start_calculating_earnings);
        tv_week_start_calculating_earnings = findViewById(R.id.tv_week_start_calculating_earnings);
        tv_date_arrival_account = findViewById(R.id.tv_date_arrival_account);
        tv_week_arrival_account = findViewById(R.id.tv_week_arrival_account);
        btn_complete = findViewById(R.id.btn_complete);

        btn_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
