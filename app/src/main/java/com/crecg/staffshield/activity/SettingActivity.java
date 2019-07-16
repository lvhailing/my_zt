package com.crecg.staffshield.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crecg.staffshield.R;
import com.crecg.staffshield.common.BaseActivity;
import com.crecg.staffshield.utils.ActivityStack;
import com.crecg.staffshield.utils.PreferenceUtil;


/**
 * 设置页面
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back;
    private TextView tv_common_title;
    private ImageButton ib_setting_gesture; // 手势密码 开关
    private LinearLayout ll_show_after_login; // 登录后显示的布局
    private RelativeLayout rl_setting_change_gesture_password; // 修改手势密码

    private RelativeLayout rl_modify_login_password; // 修改登录密码
    private RelativeLayout rl_reset_transaction_password; // 重置交易密码
    private RelativeLayout rl_clear_local_cache; // 清空本地缓存
    private RelativeLayout rl_safe_exit; // 安全退出
    private ActivityStack stack;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_setting);

        initView();
        initData();
        registerBroadcastReceiver();

    }

    private void initData() {
// 设置手势密码开关状态
//        if (PreferenceUtil.getGestureFlag()) {
//            ib_setting_gesture.setImageResource(R.mipmap.img_set_on);
//            rl_setting_change_gesture_password.setVisibility(View.VISIBLE);
//        } else {
//            ib_setting_gesture.setImageResource(R.mipmap.img_set_off);
//            rl_setting_change_gesture_password.setVisibility(View.GONE);
//        }
        if (PreferenceUtil.isGestureChose()) {
            ib_setting_gesture.setImageResource(R.mipmap.img_set_on);
            rl_setting_change_gesture_password.setVisibility(View.VISIBLE);

        } else {
            ib_setting_gesture.setImageResource(R.mipmap.img_set_off);
            rl_setting_change_gesture_password.setVisibility(View.GONE);
        }
    }

    public void initView() {
        stack = ActivityStack.getActivityManage();
        stack.addActivity(this);
        iv_back = findViewById(R.id.iv_back);
        tv_common_title = findViewById(R.id.tv_common_title);
        iv_back.setBackgroundResource(R.mipmap.img_arrow_left);
        tv_common_title.setText(getResources().getString(R.string.title_setting));

        ib_setting_gesture = findViewById(R.id.ib_setting_gesture);
        ll_show_after_login = findViewById(R.id.ll_show_after_login);
        rl_setting_change_gesture_password = findViewById(R.id.rl_setting_change_gesture_password);
        rl_modify_login_password = findViewById(R.id.rl_modify_login_password);
        rl_reset_transaction_password = findViewById(R.id.rl_reset_transaction_password);
        rl_clear_local_cache = findViewById(R.id.rl_clear_local_cache);
        rl_safe_exit = findViewById(R.id.rl_safe_exit);

        iv_back.setOnClickListener(this);
        ib_setting_gesture.setOnClickListener(this);
        rl_setting_change_gesture_password.setOnClickListener(this);
        rl_modify_login_password.setOnClickListener(this);
        rl_reset_transaction_password.setOnClickListener(this);
        rl_clear_local_cache.setOnClickListener(this);
        rl_safe_exit.setOnClickListener(this);

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (PreferenceUtil.isGestureChose()) {
            ib_setting_gesture.setImageResource(R.mipmap.img_set_on);
            rl_setting_change_gesture_password.setVisibility(View.VISIBLE);

        } else {
            ib_setting_gesture.setImageResource(R.mipmap.img_set_off);
            rl_setting_change_gesture_password.setVisibility(View.GONE);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ib_setting_gesture: // 手势密码 开关
                if (PreferenceUtil.isGestureChose()) {
                    ib_setting_gesture.setImageResource(R.mipmap.img_set_off);
                    rl_setting_change_gesture_password.setVisibility(View.GONE);
                    PreferenceUtil.setGestureChose(false);
                } else {
                    intent = new Intent(this, SettingPatternPswActivity.class);
//                    intent.putExtra("comeflag", 4);
//                    intent.putExtra("title", getResources().getString(R.string.setup_gesture_code));
//                    intent.putExtra("message", getResources().getString(R.string.setup_gesture_pattern));
//                    intent.putExtra("skip","skip_from_account");
                    startActivityForResult(intent, 1);
                }

                // **************************************
//                if (!PreferenceUtil.getGestureFlag()){
//                    Intent intent = new Intent(SettingActivity.this,SettingPatternPswActivity.class);
//                    startActivityForResult(intent,1);
//                }else{
//                    Intent close_intent = new Intent(SettingActivity.this,ClosePatternPswActivity.class);
//                    //等于1为删除密码
//                    close_intent.putExtra("gestureFlg", 1);
//                    startActivityForResult(close_intent,1);
//                }
                break;
            case R.id.rl_setting_change_gesture_password: // 修改手势密码
                intent = new Intent(SettingActivity.this, ClosePatternPswActivity.class);
//                intent.putExtra("from", Urls.ACTIVITY_CHANGE_GESTURE);
//                intent.putExtra("title", getResources().getString(R.string.title_changegesture));
//                intent.putExtra("message", getResources().getString(R.string.set_gesture_pattern_old));
                intent.putExtra("gestureFlg", 2);
                startActivityForResult(intent, 1);

                // ******************************************************

//                Intent intent = new Intent(SettingActivity.this, ClosePatternPswActivity.class);
//                //等于2为修改密码
//                intent.putExtra("gestureFlg", 2);
//                startActivityForResult(intent, 1);
                break;
            case R.id.rl_modify_login_password: // 修改登录密码
                intent = new Intent(SettingActivity.this, ModifyLoginPasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_reset_transaction_password: // 重置交易密码
                intent = new Intent(SettingActivity.this, ResetTransactionPasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_clear_local_cache:  // 清空本地缓存 （测试：暂且跳至上传工作证明页）
                intent = new Intent(SettingActivity.this, WorkCertificateActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_safe_exit: // 安全退出

                break;

            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            initData();
        }
    }

    private ReceiveBroadCast receiveBroadCast; // 广播实例
    String myActionName = "gestureChooseState";

    // 注册广播
    public void registerBroadcastReceiver() {
        receiveBroadCast = new ReceiveBroadCast();
        IntentFilter filter = new IntentFilter();
        filter.addAction(myActionName); // 只有持有相同的action的接收者才能接收此广播
        this.registerReceiver(receiveBroadCast, filter);
    }

    // 定义一个BroadcastReceiver广播接收类：
    public class ReceiveBroadCast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent data) {
            String actionName = data.getAction();
            if (actionName.equals(myActionName)) {
//                PreferenceUtil.setGestureChose(true);
//                ib_setting_gesture.setImageResource(R.mipmap.img_set_on);
//                rl_setting_change_gesture_password.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiveBroadCast);
        super.onDestroy();
    }
}
