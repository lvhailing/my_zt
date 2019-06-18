package com.crecg.staffshield.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.crecg.staffshield.R;
import com.crecg.staffshield.adapter.MyFinacialVpAdapter;
import com.crecg.staffshield.common.BaseActivity;
import com.crecg.staffshield.fragment.MyFinacialHoldFragment;
import com.crecg.staffshield.fragment.MyFinacialPaymentReturnedFragment;

/**
 * Created by junde on 2019/6/18.
 */

public class MyFinancialManagementActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_eye_state;
    private TabLayout sliding_tabs;
    private ViewPager viewpager;
    private TextView tv_waiting_income; // 待收参考收益
    private TextView tv_accumulated_income; // 累计收益
    private TextView tv_total_holdings; // 持有总额
    private String[] titles;
    private MyFinacialVpAdapter myFinacialVpAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_financial_management);

        initView();
        initData();
    }

    private void initView() {
        iv_eye_state = (ImageView) findViewById(R.id.iv_eye_state);
        tv_waiting_income = (TextView) findViewById(R.id.tv_waiting_income);
        tv_accumulated_income = (TextView) findViewById(R.id.tv_accumulated_income);
        tv_total_holdings = (TextView) findViewById(R.id.tv_total_holdings);
        sliding_tabs = (TabLayout) findViewById(R.id.sliding_tabs);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
    }

    private void initData() {
        titles = new String[]{"持有中", "已回款"};
        myFinacialVpAdapter = new MyFinacialVpAdapter(getSupportFragmentManager(), titles, this);
        ((MyFinacialHoldFragment) myFinacialVpAdapter.getItem(0)).setUserId("111");
        ((MyFinacialPaymentReturnedFragment) myFinacialVpAdapter.getItem(1)).setUserId("111");
        viewpager.setAdapter(myFinacialVpAdapter);

        //将TabLayout和ViewPager关联起来
        sliding_tabs.setupWithViewPager(viewpager);

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
                } else if (position==1){
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

    }
}
