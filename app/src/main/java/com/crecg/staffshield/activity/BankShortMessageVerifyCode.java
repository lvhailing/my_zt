package com.crecg.staffshield.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.crecg.staffshield.R;
import com.crecg.staffshield.common.BaseActivity;
import com.xnumberkeyboard.android.OnNumberKeyboardListener;
import com.xnumberkeyboard.android.XNumberKeyboardView;

/**
 * Created by junde on 2019/6/22.
 */

public class BankShortMessageVerifyCode extends BaseActivity implements OnNumberKeyboardListener {


    private ImageView iv_close_keyboard;
    private EditText et_pass1;
    private EditText et_pass2;
    private EditText et_pass3;
    private EditText et_pass4;
    private EditText et_pass5;
    private EditText et_pass6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_bank_short_message_verify_code);

        initView();
    }

    private void initView() {
        iv_close_keyboard = findViewById(R.id.iv_close_keyboard);
        et_pass1 = findViewById(R.id.et_pass1);
        et_pass2 = findViewById(R.id.et_pass2);
        et_pass3 = findViewById(R.id.et_pass3);
        et_pass4 = findViewById(R.id.et_pass4);
        et_pass5 = findViewById(R.id.et_pass5);
        et_pass6 = findViewById(R.id.et_pass6);


        iv_close_keyboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    public void onNumberKey(int keyCode, String insert) {
        // 右下角按键的点击事件，删除一位输入的文字
        if (keyCode == XNumberKeyboardView.KEYCODE_BOTTOM_RIGHT) {

        } else {

        }
    }
}
