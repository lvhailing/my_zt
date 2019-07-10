package com.crecg.staffshield.common;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.crecg.crecglibrary.utils.encrypt.DESUtil;
import com.crecg.staffshield.MyApplication;
import com.crecg.staffshield.R;
import com.crecg.staffshield.utils.PreferenceUtil;
import com.crecg.staffshield.widget.CustomProgressDialog;
import com.umeng.analytics.MobclickAgent;

import cn.jpush.android.api.JPushInterface;


public class BaseActivity extends FragmentActivity implements MyApplication.NetListener, SwipeRefreshLayout.OnRefreshListener {
    public String userId = null;
    public String userPhone = null;
    public String idNo = null;
    private SwipeRefreshLayout swipe;
    private BaseActivity mContext;
    public CustomProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        try {
            userId = DESUtil.decrypt(PreferenceUtil.getUserId());
            userPhone = DESUtil.decrypt(PreferenceUtil.getPhone());
            idNo = DESUtil.decrypt(PreferenceUtil.getIdNo());

        } catch (Exception e) {
            e.printStackTrace();
        }
        mContext = this;

    }

    private void initSwipeView() {
        //为SwipeRefreshLayout设置监听事件
        swipe.setOnRefreshListener(this);
        //为SwipeRefreshLayout设置刷新时的颜色变化，最多可以设置4种
//        swipe.setColorSchemeResources(android.R.color.holo_blue_bright,
//                android.R.color.holo_green_light,
//                android.R.color.holo_orange_light,
//                android.R.color.holo_red_light);
        swipe.setEnabled(false);
    }

    public void baseSetContentView(int layoutResId) {
        LinearLayout llContent = (LinearLayout) findViewById(R.id.content);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dialog = new CustomProgressDialog(this, "", R.drawable.frame_loading, ProgressDialog.THEME_HOLO_LIGHT);
        View v = inflater.inflate(layoutResId, null);
        llContent.addView(v);
        initSwipeView();
    }

    public BaseActivity() {
    }

    public void onStart() {
        super.onStart();
    }

    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
        MobclickAgent.onResume(this); // 基础指标统计，不能遗漏
    }

    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
        MobclickAgent.onPause(this); // 基础指标统计，不能遗漏
    }

    public void onStop() {
        super.onStop();
    }

    public void startLoading() {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    public void stopLoading() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onNetWorkChange(String netType) {
        View netHint = findViewById(R.id.netfail_hint);
        netHint.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                startActivity(intent);
            }
        });
        if (netHint != null) {
            boolean netFail = TextUtils.isEmpty(netType);
            netHint.setVisibility(netFail ? View.VISIBLE : View.GONE);
        }
    }
}
