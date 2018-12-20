package com.crecg.staffshield.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.crecg.staffshield.R;
import com.crecg.staffshield.common.BaseActivity;



public class TestActivity1 extends BaseActivity implements View.OnClickListener {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);

        initView();
    }

    private void initView() {
        textView = findViewById(R.id.tv);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.btn_um:
        }
    }

}
