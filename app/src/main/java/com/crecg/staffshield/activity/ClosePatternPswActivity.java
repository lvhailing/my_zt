package com.crecg.staffshield.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;


import com.crecg.staffshield.R;
import com.crecg.staffshield.utils.PreferenceUtil;
import com.crecg.staffshield.widget.GestureView;

import java.util.List;

public class ClosePatternPswActivity extends AppCompatActivity implements GestureView.GestureCallBack {
    private GestureView gestureView;
    private TextView tv_user_name;
    private int gestureFlg = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_close_pattern_psw);

        gestureFlg = getIntent().getIntExtra("gestureFlg", -1);
        gestureView = (GestureView) findViewById(R.id.gesture1);
        tv_user_name = (TextView) findViewById(R.id.tv_user_name);
        gestureView.setGestureCallBack(ClosePatternPswActivity.this);
        gestureView.clearCacheLogin();
    }

    @Override
    public void gestureVerifySuccessListener(int stateFlag, List<GestureView.GestureBean> data, boolean success) {
        if (success) {
            if (gestureFlg == 1) {
                //删除密码
                PreferenceUtil.putGestureFlag(false);
                gestureView.clearCache();
                Toast.makeText(ClosePatternPswActivity.this, "清空手势密码成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ClosePatternPswActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else if (gestureFlg == 2) {
                //修改密码
                Toast.makeText(ClosePatternPswActivity.this, "验证手势密码成功,请重新设置", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ClosePatternPswActivity.this, SettingPatternPswActivity.class);
                startActivity(intent);
                finish();
            } else if (gestureFlg == 3) {
                Intent intent = new Intent(ClosePatternPswActivity.this, SettingActivity.class);
                startActivity(intent);
                finish();
            }
        } else {

        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
