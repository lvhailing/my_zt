package com.crecg.staffshield.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.crecg.staffshield.R;
import com.crecg.staffshield.common.BaseActivity;
import com.xnumberkeyboard.android.OnNumberKeyboardListener;
import com.xnumberkeyboard.android.XNumberKeyboardView;

import java.util.ArrayList;
import java.util.List;

/**
 * （资金转出）请输入银行短信验证码
 */

public class BankShortMessageVerifyCodeActivity extends BaseActivity implements OnNumberKeyboardListener {

    private ImageView iv_close_keyboard;
    private ImageView iv_pass1;
    private ImageView iv_pass2;
    private ImageView iv_pass3;
    private ImageView iv_pass4;
    private ImageView iv_pass5;
    private ImageView iv_pass6;
    private XNumberKeyboardView view_keyboard;
    private List<String> verifyCodeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_bank_short_message_verify_code);

        initView();
    }

    private void initView() {
        iv_close_keyboard = findViewById(R.id.iv_close_keyboard);
        view_keyboard = findViewById(R.id.view_keyboard);
        iv_pass1 = findViewById(R.id.iv_pass1);
        iv_pass2 = findViewById(R.id.iv_pass2);
        iv_pass3 = findViewById(R.id.iv_pass3);
        iv_pass4 = findViewById(R.id.iv_pass4);
        iv_pass5 = findViewById(R.id.iv_pass5);
        iv_pass6 = findViewById(R.id.iv_pass6);

        view_keyboard.setOnNumberKeyboardListener(this);
        iv_close_keyboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    public void onNumberKey(int keyCode, String insert) {
        if (keyCode == XNumberKeyboardView.KEYCODE_BOTTOM_RIGHT &&verifyCodeList.size()>0) {
            // 右下角按键的点击事件，删除一位输入的文字
            verifyCodeList.remove(verifyCodeList.size() - 1);

        } else if (verifyCodeList.size() < 6) {
            verifyCodeList.add(insert);
        }
        refreshVerifyCodeView();
    }

    private void refreshVerifyCodeView() {
        if (verifyCodeList.size() < 0 || verifyCodeList.size() > 6) {
            return;
        }

        iv_pass1.setVisibility(View.INVISIBLE);
        iv_pass2.setVisibility(View.INVISIBLE);
        iv_pass3.setVisibility(View.INVISIBLE);
        iv_pass4.setVisibility(View.INVISIBLE);
        iv_pass5.setVisibility(View.INVISIBLE);
        iv_pass6.setVisibility(View.INVISIBLE);

        if (verifyCodeList.size() == 1) {
            iv_pass1.setVisibility(View.VISIBLE);
        } else if (verifyCodeList.size() == 2) {
            iv_pass1.setVisibility(View.VISIBLE);
            iv_pass2.setVisibility(View.VISIBLE);
        } else if (verifyCodeList.size() == 3) {
            iv_pass1.setVisibility(View.VISIBLE);
            iv_pass2.setVisibility(View.VISIBLE);
            iv_pass3.setVisibility(View.VISIBLE);
        } else if (verifyCodeList.size() == 4) {
            iv_pass1.setVisibility(View.VISIBLE);
            iv_pass2.setVisibility(View.VISIBLE);
            iv_pass3.setVisibility(View.VISIBLE);
            iv_pass4.setVisibility(View.VISIBLE);
        } else if (verifyCodeList.size() == 5) {
            iv_pass1.setVisibility(View.VISIBLE);
            iv_pass2.setVisibility(View.VISIBLE);
            iv_pass3.setVisibility(View.VISIBLE);
            iv_pass4.setVisibility(View.VISIBLE);
            iv_pass5.setVisibility(View.VISIBLE);
        } else if (verifyCodeList.size() == 6) {
            iv_pass1.setVisibility(View.VISIBLE);
            iv_pass2.setVisibility(View.VISIBLE);
            iv_pass3.setVisibility(View.VISIBLE);
            iv_pass4.setVisibility(View.VISIBLE);
            iv_pass5.setVisibility(View.VISIBLE);
            iv_pass6.setVisibility(View.VISIBLE);

            // Todo 调后台接口

            // 删除键不可点
            view_keyboard.setDelBtnEnable(false);
        }

    }
}
