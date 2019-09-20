package com.crecg.staffshield.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.crecg.crecglibrary.network.model.CashWithdrawalDataModel;
import com.crecg.staffshield.R;
import com.crecg.staffshield.common.BaseActivity;

import java.io.Serializable;

/**
 * 充值提现详情页
 * （带完成按钮，此页面只展示一次）
 */

public class RechargeAndCashWithdrawalDetailActivity extends BaseActivity {
    private ImageView iv_back;
    private TextView tv_common_title;
    private TextView tv_title_category; // 转出(入)金额（元）
    private TextView tv_transfer_amount; // 转出金额
    private TextView tv_turn_into_date; // 转出申请时间
    private TextView tv_type_transfer_accounts; // 转出到 or 转入到
    private TextView tv_payment_date; // 到账时间
    private TextView tv_bank_name;
    private TextView tv_creation_date; // 创建时间
    private TextView tv_bank_flow_num; // 流水号
    private Button btn_complete;
    private String trsAmount;
    private String submitTime;
    private String transId;
    private String creatTime;
    private String bankName;
    private String bankNum;
    private String type; // RCGI=充值    WTHI=提现

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_electronic_to_entity_detail);

        initView();
        initData();
    }

    private void initView() {
        setTitle();

        type = getIntent().getStringExtra("type");
        trsAmount = getIntent().getStringExtra("trsAmount");
        submitTime = getIntent().getStringExtra("submitTime");
        transId = getIntent().getStringExtra("transId");
        creatTime = getIntent().getStringExtra("creatTime");
        bankName = getIntent().getStringExtra("bankName");
        bankNum = getIntent().getStringExtra("bankNum");


        tv_title_category = findViewById(R.id.tv_title_category);
        tv_transfer_amount = findViewById(R.id.tv_transfer_amount);
        tv_turn_into_date = findViewById(R.id.tv_turn_into_date);
        tv_type_transfer_accounts = findViewById(R.id.tv_type_transfer_accounts);
        tv_payment_date = findViewById(R.id.tv_payment_date);
        tv_bank_name = findViewById(R.id.tv_bank_name);
        tv_creation_date = findViewById(R.id.tv_creation_date);
        tv_bank_flow_num = findViewById(R.id.tv_bank_flow_num);
        btn_complete = findViewById(R.id.btn_complete);

        btn_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void initData() {
        if (type.equals("RCGI")) {
            tv_title_category.setText("转入金额（元）");
            tv_transfer_amount.setText(trsAmount);
            tv_turn_into_date.setText(submitTime);
            tv_type_transfer_accounts.setText("转入到");
            tv_bank_name.setText("勘设联名卡（可用余额）");
            tv_creation_date.setText(creatTime);
            tv_bank_flow_num.setText(transId);
        } else if (type.equals("WTHI")) {
            tv_title_category.setText("转出金额（元）");
            tv_transfer_amount.setText(trsAmount);
            tv_turn_into_date.setText(submitTime);
            tv_type_transfer_accounts.setText("转出到");
            tv_bank_name.setText(bankName + "(" + bankNum.substring(bankNum.length() - 4, bankNum.length()) + ")");
            tv_creation_date.setText(creatTime);
            tv_bank_flow_num.setText(transId);
        }

    }

    private void setTitle() {
        iv_back = findViewById(R.id.iv_back);
        tv_common_title = findViewById(R.id.tv_common_title);

        iv_back.setVisibility(View.INVISIBLE);
        tv_common_title.setText("勘设联名卡转出详情");
    }

}
