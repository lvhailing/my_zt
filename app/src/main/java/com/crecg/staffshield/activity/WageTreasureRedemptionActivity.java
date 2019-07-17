package com.crecg.staffshield.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crecg.crecglibrary.utils.ToastUtil;
import com.crecg.staffshield.R;
import com.crecg.staffshield.common.BaseActivity;

/**
 * (工资宝)赎回至堪设联名卡
 */

public class WageTreasureRedemptionActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back;
    private TextView tv_common_title;
    private EditText et_money;
    private TextView tv_all;
    private ImageView iv1;
    private ImageView iv2;
    private Button btn_confirm_redeem; // 确认赎回
    private String flag = "1"; // 1:实时赎回  2：普通到账
    private String money;
    private LinearLayout ll_up_selected;
    private LinearLayout ll_down_unselected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_redeemption_of_electronic_bank_card);

        initView();
    }

    private void initView() {
        iv_back = findViewById(R.id.iv_back);
        tv_common_title = findViewById(R.id.tv_common_title);
        iv_back.setBackgroundResource(R.mipmap.img_arrow_left);
        tv_common_title.setText("赎回至堪设联名卡");

        et_money = findViewById(R.id.et_money);
        tv_all = findViewById(R.id.tv_all);
        iv1 = findViewById(R.id.iv1);
        iv2 = findViewById(R.id.iv2);
        ll_up_selected = findViewById(R.id.ll_up_selected);
        ll_down_unselected = findViewById(R.id.ll_down_unselected);
        btn_confirm_redeem = findViewById(R.id.btn_confirm_redeem);

        iv_back.setOnClickListener(this);
        tv_all.setOnClickListener(this);
        ll_up_selected.setOnClickListener(this);
        ll_down_unselected.setOnClickListener(this);
        btn_confirm_redeem.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_all: // 全部
                et_money.setText("2225.00");
                break;
            case R.id.ll_up_selected:
                if ("2".equals(flag)) {
                    iv1.setBackgroundResource(R.mipmap.img_redeem_selected);
                    iv2.setBackgroundResource(R.mipmap.img_redeem_unselected);
                    flag = "1";
                }
                    break;
            case R.id.ll_down_unselected:
                if ("1".equals(flag)) {
                    iv2.setBackgroundResource(R.mipmap.img_redeem_selected);
                    iv1.setBackgroundResource(R.mipmap.img_redeem_unselected);
                    flag = "2";
                }
                break;

            case R.id.btn_confirm_redeem: // 确认赎回
                money = et_money.getText().toString();
                if (TextUtils.isEmpty(money)) {
                    ToastUtil.showCustom("赎回金额不能为空");
                    return;
                }
                Intent intent = new Intent(WageTreasureRedemptionActivity.this, TransactionPasswordActivity.class);
                intent.putExtra("fromFlag", "wageTreasureRedeem");
                startActivity(intent);
                break;
        }
    }
}
