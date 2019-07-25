package com.crecg.staffshield.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crecg.staffshield.R;
import com.crecg.staffshield.common.BaseActivity;
import com.crecg.staffshield.utils.PreferenceUtil;

/**
 * 账户余额页(中铁联名卡页)
 */

public class AccountBalanceActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back;
    private TextView tv_common_title;
    private TextView tv_right_txt;
    private RelativeLayout rl_tied_card; // 已绑卡显示的布局
    private RelativeLayout rl_untied_card; // 未绑卡显示的布局
    private TextView btn_turn_out; // 转出
    private TextView btn_turn_into; // 转入

    private String btnFlag = "1"; // 1:转出     2:转入


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_account_balance);

        initView();
    }

    private void initView() {
        iv_back = findViewById(R.id.iv_back);
        tv_common_title = findViewById(R.id.tv_common_title);
        tv_right_txt = findViewById(R.id.tv_right_txt);
        iv_back.setBackgroundResource(R.mipmap.img_arrow_left);
        tv_common_title.setText("账户余额");
        tv_right_txt.setVisibility(View.VISIBLE);
        tv_right_txt.setText("明细");

        rl_tied_card = findViewById(R.id.rl_tied_card);
        rl_untied_card = findViewById(R.id.rl_untied_card);
        btn_turn_out = findViewById(R.id.btn_turn_out);
        btn_turn_into = findViewById(R.id.btn_turn_into);


        iv_back.setOnClickListener(this);
        tv_right_txt.setOnClickListener(this);
        rl_tied_card.setOnClickListener(this);
        rl_untied_card.setOnClickListener(this);
        btn_turn_out.setOnClickListener(this);
        btn_turn_into.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_right_txt: // 明细
                intent = new Intent(this, BillCenterActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_tied_card: // 已开通联名卡（跳转到联名卡详情页）
                intent = new Intent(this, ReplacementBankCardActivity.class);
                intent.putExtra("oldBankCardNum", "" + "");
                startActivity(intent);
                break;
            case R.id.rl_untied_card: // 未开通联名卡（弹框提示用户开通理财账户，然后跳转添加银行卡页面）
                intent = new Intent(this, AddBankCardActivity.class);
//                intent.putExtra("userName", PreferenceUtil.getUserRealName());
                startActivity(intent);
                break;
            case R.id.btn_turn_out: // 转出
                if ("1".equals(btnFlag)) {
                    btn_turn_out.setBackground(getResources().getDrawable(R.drawable.shape_rect_btn_blue2));
                    btn_turn_out.setTextColor(getResources().getColor(R.color.white));
                    btn_turn_into.setBackground(getResources().getDrawable(R.drawable.shape_rect_btn_white));
                    btn_turn_into.setTextColor(getResources().getColor(R.color.main_blue_4A67F5));
                    btnFlag = "2";
                }
                intent = new Intent(AccountBalanceActivity.this, ElectronicBankToEntityBankActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_turn_into: // 转入
                if ("2".equals(btnFlag)) {
                    btn_turn_into.setBackground(getResources().getDrawable(R.drawable.shape_rect_btn_blue2));
                    btn_turn_into.setTextColor(getResources().getColor(R.color.white));
                    btn_turn_out.setBackground(getResources().getDrawable(R.drawable.shape_rect_btn_white));
                    btn_turn_out.setTextColor(getResources().getColor(R.color.main_blue_4A67F5));
                    btnFlag = "1";
                }
                intent = new Intent(AccountBalanceActivity.this, EntityBankToElectronicBankActivity.class);
                startActivity(intent);
                break;
        }
    }
}
