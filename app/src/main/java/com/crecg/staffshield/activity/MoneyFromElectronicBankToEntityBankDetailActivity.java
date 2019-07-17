package com.crecg.staffshield.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.crecg.staffshield.R;
import com.crecg.staffshield.common.BaseActivity;

/**
 * 资金转出详情(联名卡转出详情)
 */

public class MoneyFromElectronicBankToEntityBankDetailActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_common_title;
    private TextView tv_transfer_amount; // 转入金额
    private TextView tv_turn_into_date; // 转入申请时间
    private TextView tv_payment_date; // 到账时间
    private TextView tv_creation_date; // 创建时间
    private TextView tv_bank_flow_num; // 流水号
    private Button btn_complete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_electronic_to_entity_detail);

        initView();
    }

    private void initView() {
        tv_common_title = findViewById(R.id.tv_common_title);
        tv_common_title.setText("堪设联名卡转出详情");

        tv_transfer_amount = findViewById(R.id.tv_transfer_amount);
        tv_turn_into_date = findViewById(R.id.tv_turn_into_date);
        tv_payment_date = findViewById(R.id.tv_payment_date);
        tv_creation_date = findViewById(R.id.tv_creation_date);
        tv_bank_flow_num = findViewById(R.id.tv_bank_flow_num);
        btn_complete = findViewById(R.id.btn_complete);

        btn_complete.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_complete: // 完成
                break;
        }
    }
}
