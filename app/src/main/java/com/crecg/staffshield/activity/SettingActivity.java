package com.crecg.staffshield.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crecg.staffshield.R;
import com.crecg.staffshield.common.BaseActivity;
import com.crecg.staffshield.utils.ActivityStack;
import com.crecg.staffshield.utils.PreferenceUtil;
import com.crecg.staffshield.widget.TitleBar;


/**
 * 设置页面
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {
    private ImageButton ib_setting_gesture; // 手势密码 开关
    private LinearLayout ll_show_after_login; // 登录后显示的布局
    private RelativeLayout rl_setting_change_gesture_password; // 修改手势密码
    private RelativeLayout rl_setting_invite; // 推荐app给好友
    private RelativeLayout rl_setting_contact_customer_service;  // 联系客服
    private RelativeLayout rl_setting_platform_bulletin;  // 平台公告
    private RelativeLayout rl_setting_service_agreement; // 服务协议
    private RelativeLayout rl_setting_about;  //  关于如来保
    private TextView tv_setting_version_code;  //  版本号
    private Button btn_setting_logout;  // 退出登录

    private RelativeLayout rl_modify_login_password; // 修改登录密码
    private RelativeLayout rl_reset_transaction_password; // 重置交易密码
    private RelativeLayout rl_clear_local_cache; // 清空本地缓存
    private ActivityStack stack;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_setting);

        initTopTitle();
        initView();
        initData();
        registerBroadcastReceiver();

    }

    private void initTopTitle() {
//        TitleBar title = (TitleBar) findViewById(R.id.rl_title);
//        title.setTitle(getResources().getString(R.string.title_null)).setLogo(R.mipmap.ic_launcher, false)
//                .setIndicator(R.mipmap.img_arrow_left).setCenterText(getResources().getString(R.string.setting_title))
//                .showMore(false).setOnActionListener(new TitleBar.OnActionListener() {
//
//            @Override
//            public void onMenu(int id) {
//            }
//
//            @Override
//            public void onBack() {
//                finish();
//            }
//
//            @Override
//            public void onAction(int id) {
//
//            }
//        });
    }

    public void initView() {
        stack = ActivityStack.getActivityManage();
        stack.addActivity(this);

//        ib_setting_gesture = (ImageButton) findViewById(R.id.ib_setting_gesture);
//        ll_show_after_login = (LinearLayout) findViewById(R.id.ll_show_after_login);
        rl_modify_login_password = findViewById(R.id.rl_modify_login_password);
        rl_reset_transaction_password = findViewById(R.id.rl_reset_transaction_password);
        rl_clear_local_cache = findViewById(R.id.rl_clear_local_cache);
//        rl_setting_contact_customer_service = (RelativeLayout) findViewById(R.id.rl_setting_contact_customer_service);
//        rl_setting_platform_bulletin = (RelativeLayout) findViewById(R.id.rl_setting_platform_bulletin);
//        rl_setting_service_agreement = (RelativeLayout) findViewById(R.id.rl_setting_service_agreement);
//        rl_setting_about = (RelativeLayout) findViewById(R.id.rl_setting_about);
//
//        View v_show_no_login = findViewById(R.id.v_show_no_login);
//        tv_setting_version_code = (TextView) findViewById(R.id.tv_setting_version_code);
//        btn_setting_logout = (Button) findViewById(R.id.btn_setting_logout);

//        if (PreferenceUtil.isLogin()) { // 登录后显示的
//            ll_show_after_login.setVisibility(View.VISIBLE);
//            v_show_no_login.setVisibility(View.GONE);
//            btn_setting_logout.setVisibility(View.VISIBLE);
//        }else { // 未登录时显示的
//            ll_show_after_login.setVisibility(View.GONE);
//            v_show_no_login.setVisibility(View.VISIBLE);
//            btn_setting_logout.setVisibility(View.GONE);
//        }

//        ib_setting_gesture.setOnClickListener(this);
//        rl_setting_change_gesture_password.setOnClickListener(this);
//        rl_setting_invite.setOnClickListener(this);
//        rl_setting_contact_customer_service.setOnClickListener(this);
//        rl_setting_platform_bulletin.setOnClickListener(this);
//        rl_setting_service_agreement.setOnClickListener(this);
//        rl_setting_about.setOnClickListener(this);

        rl_modify_login_password.setOnClickListener(this);
        rl_reset_transaction_password.setOnClickListener(this);
        rl_clear_local_cache.setOnClickListener(this);

    }

    public void initData() {
//        tv_setting_version_code.setText(SystemInfo.sVersionName);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

//        if (PreferenceUtil.isGestureChose()) {
//            ib_setting_gesture.setImageResource(R.mipmap.img_set_on);
//            rl_setting_change_gesture_password.setVisibility(View.VISIBLE);
//
//        } else {
//            ib_setting_gesture.setImageResource(R.mipmap.img_set_off);
//            rl_setting_change_gesture_password.setVisibility(View.GONE);
//            imgGestureSwitch.setVisibility(View.GONE);
//        }


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
//            case R.id.ib_setting_gesture: // 手势密码 开关
//                if (PreferenceUtil.isGestureChose()) {
//                    ib_setting_gesture.setImageResource(R.mipmap.img_set_off);
//                    rl_setting_change_gesture_password.setVisibility(View.GONE);
//                    PreferenceUtil.setGestureChose(false);
//                } else {
//                    intent = new Intent(this, GestureEditActivity.class);
//                    intent.putExtra("comeflag", 4);
//                    intent.putExtra("title", getResources().getString(R.string.setup_gesture_code));
//                    intent.putExtra("message", getResources().getString(R.string.setup_gesture_pattern));
//                    intent.putExtra("skip","skip_from_account");
//                    startActivity(intent);
//                }

//                break;

//            case R.id.rl_setting_change_gesture_password: // 修改手势密码
//                intent = new Intent(SettingActivity.this, GestureVerifyActivity.class);
//                intent.putExtra("from", Urls.ACTIVITY_CHANGE_GESTURE);
//                intent.putExtra("title", getResources().getString(R.string.title_changegesture));
//                intent.putExtra("message", getResources().getString(R.string.set_gesture_pattern_old));
//                startActivity(intent);

//                break;

            case R.id.rl_modify_login_password: // 修改登录密码
                intent = new Intent(SettingActivity.this, ModifyLoginPasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_reset_transaction_password: // 重置交易密码
                intent= new Intent(SettingActivity.this, ResetTransactionPasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_clear_local_cache:  // 清空本地缓存 （测试：暂且跳至上传工作证明页）
                intent = new Intent(SettingActivity.this, WorkCertificateActivity.class);
                startActivity(intent);
                break;
//            case R.id.rl_setting_platform_bulletin:  // 平台公告
//                intent = new Intent(SettingActivity.this, PlatformBulletinActivity.class);
//                startActivity(intent);
//                break;
//            case R.id.rl_setting_service_agreement:  // 服务协议
//                intent = new Intent(SettingActivity.this, WebActivity.class);
//                intent.putExtra("type", WebActivity.WEB_TYPE_SIGN_AGREEMENT);
//                intent.putExtra("title", getResources().getString(R.string.setting_service_agreement));
//                intent.putExtra("url", Urls.URL_SERVICE_AGREEMENT);
//                startActivity(intent);

//                break;

//            case R.id.rl_setting_about: // 关于如来保
//              intent = new Intent(SettingActivity.this, WebActivity.class);
//                intent.putExtra("type", WebActivity.WEB_TYPE_ABOUT_US);
//                intent.putExtra("title", getResources().getString(R.string.setting_about));
//                intent.putExtra("url", Urls.URL_ABOUT_US /*+ SystemInfo.sVersionName*/);
//                startActivity(intent);
//                break;

//            case R.id.btn_setting_logout: // 退出登录
//                UserLoadout out = new UserLoadout(SettingActivity.this, userId);
//                out.requestData();
//                finish();

//                break;


            default:
                break;
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
