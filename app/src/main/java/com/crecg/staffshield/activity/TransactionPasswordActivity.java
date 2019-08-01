package com.crecg.staffshield.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.crecg.staffshield.R;
import com.crecg.staffshield.common.BaseActivity;
import com.xnumberkeyboard.android.OnNumberKeyboardListener;
import com.xnumberkeyboard.android.XNumberKeyboardView;

import java.util.ArrayList;
import java.util.List;

/**
 * 请输入交易密码
 */

public class TransactionPasswordActivity extends BaseActivity implements OnNumberKeyboardListener {

    private ImageView iv_close_keyboard;
    private ImageView iv_pass1;
    private ImageView iv_pass2;
    private ImageView iv_pass3;
    private ImageView iv_pass4;
    private ImageView iv_pass5;
    private ImageView iv_pass6;
    private XNumberKeyboardView view_keyboard;
    private List<String> transactionPwdList = new ArrayList<>();
    private TextView tv_forget_transaction_pwd;
    private String fromFlag; // wageTreasureRedeem:工资宝赎回  wageTreasureBuy:工资宝买入
    private String whereToEnterFlag; //  1:首页进   2：工资宝详情页进

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_transaction_pwd);

        initView();
    }

    private void initView() {
        fromFlag = getIntent().getStringExtra("fromFlag");
        whereToEnterFlag = getIntent().getStringExtra("whereToEnterFlag");
        iv_close_keyboard = findViewById(R.id.iv_close_keyboard);
        view_keyboard = findViewById(R.id.view_keyboard);
        iv_pass1 = findViewById(R.id.iv_pass1);
        iv_pass2 = findViewById(R.id.iv_pass2);
        iv_pass3 = findViewById(R.id.iv_pass3);
        iv_pass4 = findViewById(R.id.iv_pass4);
        iv_pass5 = findViewById(R.id.iv_pass5);
        iv_pass6 = findViewById(R.id.iv_pass6);
        tv_forget_transaction_pwd = findViewById(R.id.tv_forget_transaction_pwd);

        view_keyboard.setOnNumberKeyboardListener(this);
        iv_close_keyboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_forget_transaction_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TransactionPasswordActivity.this, ResetTransactionPasswordActivity.class));
            }
        });

    }

    @Override
    public void onNumberKey(int keyCode, String insert) {
        if (keyCode == XNumberKeyboardView.KEYCODE_BOTTOM_RIGHT && transactionPwdList.size() > 0) {
            // 右下角按键的点击事件，删除一位输入的文字
            transactionPwdList.remove(transactionPwdList.size() - 1);

        } else if (transactionPwdList.size() < 6) {
            transactionPwdList.add(insert);
        }
        refreshVerifyCodeView();
    }

    private void refreshVerifyCodeView() {
        if (transactionPwdList.size() < 0 || transactionPwdList.size() > 6) {
            return;
        }

        iv_pass1.setVisibility(View.INVISIBLE);
        iv_pass2.setVisibility(View.INVISIBLE);
        iv_pass3.setVisibility(View.INVISIBLE);
        iv_pass4.setVisibility(View.INVISIBLE);
        iv_pass5.setVisibility(View.INVISIBLE);
        iv_pass6.setVisibility(View.INVISIBLE);

        if (transactionPwdList.size() == 1) {
            iv_pass1.setVisibility(View.VISIBLE);
        } else if (transactionPwdList.size() == 2) {
            iv_pass1.setVisibility(View.VISIBLE);
            iv_pass2.setVisibility(View.VISIBLE);
        } else if (transactionPwdList.size() == 3) {
            iv_pass1.setVisibility(View.VISIBLE);
            iv_pass2.setVisibility(View.VISIBLE);
            iv_pass3.setVisibility(View.VISIBLE);
        } else if (transactionPwdList.size() == 4) {
            iv_pass1.setVisibility(View.VISIBLE);
            iv_pass2.setVisibility(View.VISIBLE);
            iv_pass3.setVisibility(View.VISIBLE);
            iv_pass4.setVisibility(View.VISIBLE);
        } else if (transactionPwdList.size() == 5) {
            iv_pass1.setVisibility(View.VISIBLE);
            iv_pass2.setVisibility(View.VISIBLE);
            iv_pass3.setVisibility(View.VISIBLE);
            iv_pass4.setVisibility(View.VISIBLE);
            iv_pass5.setVisibility(View.VISIBLE);
        } else if (transactionPwdList.size() == 6) {
            iv_pass1.setVisibility(View.VISIBLE);
            iv_pass2.setVisibility(View.VISIBLE);
            iv_pass3.setVisibility(View.VISIBLE);
            iv_pass4.setVisibility(View.VISIBLE);
            iv_pass5.setVisibility(View.VISIBLE);
            iv_pass6.setVisibility(View.VISIBLE);

            // Todo 调后台接口成功后跳转到成功状态页
//            if (fromFlag.equals("wageTreasureRedeem")) { // 工资宝赎回
//                Intent intent = new Intent(this, WageTreasureRedeemDetailActivity.class);
////            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//            } else if (fromFlag.equals("wageTreasureBuy")) { // 工资宝买入（一：从工资宝详情页进的买入，二：从首页点“立即买入”进）
//                Intent intent = new Intent(this, WageTreasureTurnSuccessActivity.class);
//                intent.putExtra("whereToEnterFlag",whereToEnterFlag);
//                startActivity(intent);
//            }


            // 删除键不可点
            view_keyboard.setDelBtnEnable(false);
        }

    }
}
