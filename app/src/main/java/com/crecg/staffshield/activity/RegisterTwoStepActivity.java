package com.crecg.staffshield.activity;

import android.os.Bundle;

import com.crecg.staffshield.R;
import com.crecg.staffshield.common.BaseActivity;

/**
 * 注册(第二步：身份认证)
 * Created by junde on 2018/12/25.
 */

public class RegisterTwoStepActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_two_step);

        initView();
    }

    private void initView() {

    }
}
