package com.crecg.staffshield.activity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.crecg.crecglibrary.utils.ToastUtil;
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
    private ImageView iv_id_card_positive; // 身份证正面
    private ImageView iv_id_card_back; // 身份证反面
    private Button btn_upload; // 确认上传


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
        iv_id_card_positive = findViewById(R.id.iv_id_card_positive);
        iv_id_card_back = findViewById(R.id.iv_id_card_back);
        btn_upload = findViewById(R.id.btn_upload);

        String txtStr = "请上传<font color = '#4A67F5'>" + userName + "</font>的身份证信息";
        tv_tips.setTextSize(16);
        tv_tips.setText(Html.fromHtml(txtStr));

        iv_id_card_positive.setOnClickListener(this);
        iv_id_card_back.setOnClickListener(this);
        btn_upload.setOnClickListener(this);
    }

    private void setTitle() {
        iv_back = findViewById(R.id.iv_back);
        tv_common_title = findViewById(R.id.tv_common_title);
        iv_back.setImageResource(R.mipmap.img_arrow_left2);
        tv_common_title.setText("实名认证");

        iv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_id_card_positive: // 身份证正面
                ToastUtil.showCustom("请上传身份证正面");
                break;
            case R.id.iv_id_card_back: // 身份证反面
                ToastUtil.showCustom("请上传身份证反面");
                break;
            case R.id.btn_upload: // 确认上传
                break;
        }
    }
}
