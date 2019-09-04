package com.crecg.staffshield.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.crecg.staffshield.R;
import com.crecg.staffshield.adapter.BillCenterVpAdapter;
import com.crecg.staffshield.common.BaseActivity;
import com.crecg.staffshield.fragment.BillCenterFragment;

/**
 * 账单中心
 */

public class BillCenterActivity extends BaseActivity {

    private ImageView iv_back;
    private TextView tv_common_title;
    private XTabLayout XTab_layout;
    private TabLayout sliding_tabs;
    private ViewPager viewpager;
    private String[] titles;
    private BillCenterVpAdapter billCenterVpAdapter; // viewPager 的adapter
    private int currentTabPosition = 0; // 默认进来加载“全部”的数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_bill_center);

        initView();
        initData();
    }

    private void initView() {
        setTitle();

//        XTab_layout = findViewById(R.id.tab_layout);
        sliding_tabs = findViewById(R.id.sliding_tabs);
        viewpager = findViewById(R.id.viewpager);
    }

    private void setTitle() {
        iv_back = findViewById(R.id.iv_back);
        tv_common_title = findViewById(R.id.tv_common_title);

        iv_back.setImageResource(R.mipmap.img_arrow_left2);
        tv_common_title.setText("账单中心");

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData() {
        titles = new String[]{"全部", "银行卡", "工资宝", "定期理财"};
        billCenterVpAdapter = new BillCenterVpAdapter(getSupportFragmentManager(), titles, this);
        ((BillCenterFragment) billCenterVpAdapter.getItem(0)).setUserId(userId);
        ((BillCenterFragment) billCenterVpAdapter.getItem(currentTabPosition)).getTabTitleCurrentPosition(currentTabPosition);
        viewpager.setAdapter(billCenterVpAdapter);

        //将TabLayout和ViewPager关联起来
//        XTab_layout.setupWithViewPager(viewpager);
        sliding_tabs.setupWithViewPager(viewpager);

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

//        XTab_layout.setOnTabSelectedListener(new XTabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(XTabLayout.Tab tab) {
//                Log.i("hh", " onTabSelected --- ");
//                currentTabPosition = tab.getPosition();
//                ((BillCenterFragment) billCenterVpAdapter.getItem(currentTabPosition)).setUserId(userId);
//                ((BillCenterFragment) billCenterVpAdapter.getItem(currentTabPosition)).getTabTitleCurrentPosition(currentTabPosition);
//            }
//
//            @Override
//            public void onTabUnselected(XTabLayout.Tab tab) {
//                Log.i("hh", " onTabUnselected --- ");
//            }
//
//            @Override
//            public void onTabReselected(XTabLayout.Tab tab) {
//                Log.i("hh", " onTabReselected --- ");
//            }
//        });


        sliding_tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                Log.i("hh", " onTabSelected --- " + tab);
                currentTabPosition = tab.getPosition();
//                Log.i("hh", " currentTabPosition = " + currentTabPosition);
                ((BillCenterFragment) billCenterVpAdapter.getItem(currentTabPosition)).setUserId(userId);
                ((BillCenterFragment) billCenterVpAdapter.getItem(currentTabPosition)).getTabTitleCurrentPosition(currentTabPosition);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
//                Log.i("bb", " onTabUnselected --- " + tab);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
//                Log.i("bb", " onTabReselected --- " + tab);
            }
        });
    }

}
