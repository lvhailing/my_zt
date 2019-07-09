package com.crecg.staffshield.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.crecg.staffshield.R;
import com.crecg.staffshield.adapter.MyFinacialVpAdapter;
import com.crecg.staffshield.common.BaseActivity;
import com.crecg.staffshield.fragment.MyFinacialHoldFragment;
import com.crecg.staffshield.fragment.MyFinacialPaymentReturnedFragment;

/**
 * 我的理财 (包含持仓和已回款)
 */

public class MyFinancialManagementListActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back;
    private ImageView iv_right_btn;
    private TextView tv_common_title;
    private ImageView iv_eye_state;
    private XTabLayout tab_layout;
    private ViewPager viewpager;
    private TextView tv_waiting_income; // 待收参考收益
    private TextView tv_accumulated_income; // 累计收益
    private TextView tv_total_holdings; // 持有总额
    private String[] titles;
    private MyFinacialVpAdapter myFinacialVpAdapter;

    private boolean showOrHideFlag = true; // 持有总额 默认显示


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_financial_management_list);

        initView();
        initData();
    }

    private void initView() {
        iv_back = findViewById(R.id.iv_back);
        iv_right_btn = findViewById(R.id.iv_right_btn);
        tv_common_title = findViewById(R.id.tv_common_title);
        iv_right_btn.setVisibility(View.VISIBLE);
        iv_right_btn.setBackgroundResource(R.mipmap.img_finacial_detail);
        iv_back.setBackgroundResource(R.mipmap.img_arrow_left);
        tv_common_title.setText("我的理财");

        iv_eye_state = findViewById(R.id.iv_eye_state);
        tv_waiting_income = findViewById(R.id.tv_waiting_income);
        tv_accumulated_income = findViewById(R.id.tv_accumulated_income);
        tv_total_holdings = findViewById(R.id.tv_total_holdings);
        tab_layout = findViewById(R.id.tab_layout);
        viewpager = findViewById(R.id.viewpager);

        iv_back.setOnClickListener(this);
        iv_right_btn.setOnClickListener(this);
        iv_eye_state.setOnClickListener(this);
    }

    private void initData() {
        titles = new String[]{"持有中", "已回款"};
        myFinacialVpAdapter = new MyFinacialVpAdapter(getSupportFragmentManager(), titles, this);
        ((MyFinacialHoldFragment) myFinacialVpAdapter.getItem(0)).setUserId("111");
        ((MyFinacialPaymentReturnedFragment) myFinacialVpAdapter.getItem(1)).setUserId("111");
        viewpager.setAdapter(myFinacialVpAdapter);

        //将TabLayout和ViewPager关联起来
        tab_layout.setupWithViewPager(viewpager);

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    ((MyFinacialHoldFragment) myFinacialVpAdapter.getItem(position)).setUserId("111");
                    MyFinacialHoldFragment myFinacialHoldFragment = (MyFinacialHoldFragment) myFinacialVpAdapter.getItem(position);
                    myFinacialHoldFragment.getCurrentTab(position);
                } else if (position == 1) {
                    ((MyFinacialPaymentReturnedFragment) myFinacialVpAdapter.getItem(position)).setUserId("111");
                    MyFinacialPaymentReturnedFragment myFinacialPaymentReturnedFragment = (MyFinacialPaymentReturnedFragment) myFinacialVpAdapter.getItem(position);
                    myFinacialPaymentReturnedFragment.getCurrentTab(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_right_btn: // 明细
                Intent intent = new Intent(this, BillCenterActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_eye_state: // 小眼睛 （显示或隐藏金额）
                if (showOrHideFlag) {
                    tv_waiting_income.setText("****");
                    showOrHideFlag = false;
                } else {
                    tv_waiting_income.setText("27000.55");
                    showOrHideFlag = true;
                }
                break;
        }
    }
}
