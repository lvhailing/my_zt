package com.crecg.staffshield.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.crecg.staffshield.R;
import com.crecg.staffshield.adapter.BillCenterVpAdapter;
import com.crecg.staffshield.common.BaseActivity;
import com.crecg.staffshield.fragment.BillCenterAllFragment;
import com.crecg.staffshield.fragment.BillCenterBankCardFragment;

/**
 * 账单中心
 */

public class BillCenterActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back;
    private TextView tv_common_title;
    private XTabLayout tab_layout;
    private ViewPager viewpager;
    private String[] titles;
    private BillCenterVpAdapter billCenterVpAdapter; //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_bill_center);

        initView();
        initData();
    }

    private void initView() {
        iv_back = findViewById(R.id.iv_back);
        tv_common_title = findViewById(R.id.tv_common_title);
        iv_back.setBackgroundResource(R.mipmap.img_arrow_left);
        tv_common_title.setText("账单中心");

        tab_layout = findViewById(R.id.tab_layout);
        viewpager = findViewById(R.id.viewpager);

        iv_back.setOnClickListener(this);
    }

    private void initData() {
        titles = new String[]{"全部", "银行卡", "工资宝", "定期理财"};
        billCenterVpAdapter = new BillCenterVpAdapter(getSupportFragmentManager(), titles, this);
//        ((BillCenterAllFragment) billCenterVpAdapter.getItem(0)).setUserId("111");
//        ((BillCenterBankCardFragment) billCenterVpAdapter.getItem(1)).setUserId("111");
//        ((BillCenterSalaryTreasureFragment) billCenterVpAdapter.getItem(1)).setUserId("111");
//        ((BillCenterRegularFinancialFragment) billCenterVpAdapter.getItem(1)).setUserId("111");
        viewpager.setAdapter(billCenterVpAdapter);

        //将TabLayout和ViewPager关联起来
        tab_layout.setupWithViewPager(viewpager);

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
//                    ((BillCenterAllFragment) billCenterVpAdapter.getItem(position)).setUserId("111");
                    BillCenterAllFragment billCenterAllFragment = (BillCenterAllFragment) billCenterVpAdapter.getItem(position);
                    billCenterAllFragment.getCurrentTab(position);
                } else if (position == 1) {
//                    ((BillCenterBankCardFragment) billCenterVpAdapter.getItem(position)).setUserId("111");
                    BillCenterBankCardFragment billCenterBankCardFragment = (BillCenterBankCardFragment) billCenterVpAdapter.getItem(position);
                    billCenterBankCardFragment.getCurrentTab(position);
                } else if (position == 2) {
                } else if (position == 3) {
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_next_step: // 下一步
                break;
        }
    }
}
