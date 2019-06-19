package com.crecg.staffshield.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.crecg.staffshield.R;
import com.crecg.staffshield.utils.PreferenceUtil;
import com.crecg.staffshield.widget.GestureView;

import java.util.List;

/**
 *  设置手势密码
 */
public class SettingPatternPswActivity extends AppCompatActivity implements GestureView.GestureCallBack{
    private ImageView iv_back;
    private GestureView gestureView;
    private int jumpFlg;
    private int flag;
    private TextView tv_common_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_pattern_psw);

        jumpFlg = getIntent().getIntExtra("jumpFlg", 0);
        flag = getIntent().getIntExtra("flag", 0);
        initView();
    }

    private void initView() {
        iv_back = findViewById(R.id.iv_back);
        tv_common_title = findViewById(R.id.tv_common_title);
        iv_back.setBackgroundResource(R.mipmap.img_arrow_left);
        tv_common_title.setText("设置手势密码");

        gestureView = (GestureView) findViewById(R.id.gesture);
        gestureView.setGestureCallBack(this);
        //不调用这个方法会造成第二次启动程序直接进入手势识别而不是手势设置
        gestureView.clearCache();
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void gestureVerifySuccessListener(int stateFlag, List<GestureView.GestureBean> data, boolean success) {
        if (stateFlag == GestureView.STATE_LOGIN) {
//            PreferenceUtil.putGestureFlag(true);
            PreferenceUtil.setGestureChose(true);
            finish();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
