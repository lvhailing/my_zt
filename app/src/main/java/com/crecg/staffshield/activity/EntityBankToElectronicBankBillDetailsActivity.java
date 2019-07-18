package com.crecg.staffshield.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.crecg.staffshield.R;
import com.crecg.staffshield.common.BaseActivity;

/**
 * 资金转入账单详情(联名卡转入账单详情)
 */

public class EntityBankToElectronicBankBillDetailsActivity extends BaseActivity {
    private ImageView iv_back;
    private TextView tv_common_title;
    private TextView tv_money_flow; // 银行卡-堪设联名卡
    private TextView tv_transfer_amount; // 转入金额
    private TextView tv_turn_into_date; // 转入申请时间
    private TextView tv_payment_date; // 到账时间
    private TextView tv_creation_date; // 创建时间
    private TextView tv_bank_flow_num; // 流水号
    private Button btn_complete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_entity_to_electronic_bill_detaisl);

        initView();
    }

    private void initView() {
        iv_back = findViewById(R.id.iv_back);
        tv_common_title = findViewById(R.id.tv_common_title);
        iv_back.setBackgroundResource(R.mipmap.img_arrow_left);
        tv_common_title.setText("账单详情");

        tv_money_flow = findViewById(R.id.tv_money_flow);
        tv_transfer_amount = findViewById(R.id.tv_transfer_amount);
        tv_turn_into_date = findViewById(R.id.tv_turn_into_date);
        tv_payment_date = findViewById(R.id.tv_payment_date);
        tv_creation_date = findViewById(R.id.tv_creation_date);
        tv_bank_flow_num = findViewById(R.id.tv_bank_flow_num);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
