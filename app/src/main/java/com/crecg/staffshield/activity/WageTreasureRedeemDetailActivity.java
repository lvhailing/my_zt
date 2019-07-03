package com.crecg.staffshield.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.crecg.staffshield.R;
import com.crecg.staffshield.common.BaseActivity;

/**
 * 工资宝赎回状态详情页
 * Created by junde on 2019/7/2.
 */

public class WageTreasureRedeemDetailActivity extends BaseActivity {

    private ImageView iv_back;
    private TextView tv_common_title;
    private TextView tv_redeem_time; // 工资宝赎回时间
    private TextView tv_latest_arrival_time; // 最晚到账时间
    private TextView btn_complete; //完成

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_wage_treasure_redeem_detail);

        initView();
    }

    private void initView() {
        iv_back = findViewById(R.id.iv_back);
        tv_common_title = findViewById(R.id.tv_common_title);
        iv_back.setVisibility(View.GONE);
        tv_common_title.setText("工资宝赎回详情");

        tv_redeem_time = findViewById(R.id.tv_redeem_time);
        tv_latest_arrival_time = findViewById(R.id.tv_latest_arrival_time);
        btn_complete = findViewById(R.id.btn_complete);

        btn_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(WageTreasureRedeemDetailActivity.this, MainActivity.class);
//                intent.putExtra("homeFlag","1"); //
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//                overridePendingTransition(R.anim.activity_in_from_right,
//                        R.anim.activity_out_to_left);
//
//                AppManager.getAppManager().finishAllActivity();

            }
        });

    }
}
