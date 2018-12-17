package com.crecg.staffshield.common;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.crecg.staffshield.R;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by junde on 2018/12/15.
 */

public class BaseActivity extends FragmentActivity implements SwipeRefreshLayout.OnRefreshListener {


    private SwipeRefreshLayout swipe;
    private BaseActivity mContext;  // Activity 上下文

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity);
        mContext = this;

        initView();
    }

    private void initView() {

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
    }

    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    public void onStop() {
        super.onStop();
    }

    @Override
    public void onRefresh() {

    }
}
