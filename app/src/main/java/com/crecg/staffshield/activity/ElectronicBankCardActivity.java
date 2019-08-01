package com.crecg.staffshield.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.crecg.staffshield.R;
import com.crecg.staffshield.common.BaseActivity;

/**
 * 勘设-晋商联名卡
 * Created by junde on 2019/7/23.
 */

public class ElectronicBankCardActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back;
    private TextView tv_common_title;
    private TextView tv_electronic_bank_card_num;
    private TextView tv_user_name;
    private TextView tv_copy_bank_card_num;
    private ImageView iv_bank_logo;
    private TextView tv_bank_name;
    private TextView tv_bank_quota;
    private TextView tv_change_bank_card;
    private TextView tv_more_problems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_electronic_bank_card);

        initView();
    }

    private void initView() {
        setTitle();

        tv_electronic_bank_card_num = findViewById(R.id.tv_electronic_bank_card_num);
        tv_user_name = findViewById(R.id.tv_user_name);
        tv_copy_bank_card_num = findViewById(R.id.tv_copy_bank_card_num);
        iv_bank_logo = findViewById(R.id.iv_bank_logo);
        tv_bank_name = findViewById(R.id.tv_bank_name);
        tv_bank_quota = findViewById(R.id.tv_bank_quota);
        tv_change_bank_card = findViewById(R.id.tv_change_bank_card);
        tv_more_problems = findViewById(R.id.tv_more_problems);

        tv_copy_bank_card_num.setOnClickListener(this);
        tv_change_bank_card.setOnClickListener(this);
        tv_more_problems.setOnClickListener(this);

    }

    private void setTitle() {
        iv_back = findViewById(R.id.iv_back);
        tv_common_title = findViewById(R.id.tv_common_title);

        iv_back.setImageResource(R.mipmap.img_arrow_left2);
        tv_common_title.setText("更换银行卡");

        iv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_copy_bank_card_num: // 复制卡号
                break;
            case R.id.tv_change_bank_card: // 更换
                Intent intent = new Intent(this, ReplacementBankCardActivity.class);
                intent.putExtra("oldBankCardNum","6210784252301101" );
                startActivity(intent);
                break;
            case R.id.tv_more_problems: // 更多 (跳转H5)
                break;
        }

    }
}
