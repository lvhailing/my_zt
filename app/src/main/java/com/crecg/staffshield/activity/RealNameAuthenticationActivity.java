package com.crecg.staffshield.activity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.crecg.staffshield.R;
import com.crecg.staffshield.common.BaseActivity;

/**
 * 实名认证
 * Created by junde on 2019/7/26.
 */

public class RealNameAuthenticationActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back;
    private TextView tv_common_title;
    private String userName;
    private TextView tv_tips;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_real_name_authentication);

        initView();
    }

    private void initView() {
        setTitle();

        userName = getIntent().getStringExtra("userName");
        tv_tips = findViewById(R.id.tv_tips);

        String txtStr = "请上传<font color = '#4A67F5'>" + userName + "</font>的身份证信息";
        tv_tips.setTextSize(16);
        tv_tips.setText(Html.fromHtml(txtStr));

    }

    private void setTitle() {
        iv_back = findViewById(R.id.iv_back);
        tv_common_title = findViewById(R.id.tv_common_title);
        iv_back.setBackgroundResource(R.mipmap.img_arrow_left);
        tv_common_title.setText("实名认证");

        iv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
