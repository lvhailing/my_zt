package com.crecg.staffshield.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
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
 * 我的理财 (包含持有中和已回款)
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
    private String waitingIncome;
    private String accumulatedIncome;
    private String totalHoldings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_financial_management_list);

        initView();
        initData();
    }

    private void initView() {
        setTitle();

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

    private void setTitle() {
        iv_back = findViewById(R.id.iv_back);
        iv_right_btn = findViewById(R.id.iv_right_btn);
        tv_common_title = findViewById(R.id.tv_common_title);

        iv_right_btn.setVisibility(View.VISIBLE);
        iv_right_btn.setBackgroundResource(R.mipmap.img_finacial_detail);
        iv_back.setImageResource(R.mipmap.img_arrow_left2);
        tv_common_title.setText("我的理财");
    }

    private void initData() {
        titles = new String[]{"持有中", "已回款"};
        myFinacialVpAdapter = new MyFinacialVpAdapter(getSupportFragmentManager(), titles, this);
        ((MyFinacialHoldFragment) myFinacialVpAdapter.getItem(0)).setUserId(userId);
        ((MyFinacialPaymentReturnedFragment) myFinacialVpAdapter.getItem(1)).setUserId(userId);
        viewpager.setAdapter(myFinacialVpAdapter);

        //将TabLayout和ViewPager关联起来
        tab_layout.setupWithViewPager(viewpager);

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.i("hh", "onPageScrolled ---" + position);
            }

            @Override
            public void onPageSelected(int position) {
                Log.i("hh", "onPageSelected ---" + position);
                if (position == 0) {
                    ((MyFinacialHoldFragment) myFinacialVpAdapter.getItem(position)).setUserId(userId);
                    MyFinacialHoldFragment myFinacialHoldFragment = (MyFinacialHoldFragment) myFinacialVpAdapter.getItem(position);
                    myFinacialHoldFragment.getCurrentTab(position);
                } else if (position == 1) {
                    ((MyFinacialPaymentReturnedFragment) myFinacialVpAdapter.getItem(position)).setUserId(userId);
                    MyFinacialPaymentReturnedFragment myFinacialPaymentReturnedFragment = (MyFinacialPaymentReturnedFragment) myFinacialVpAdapter.getItem(position);
                    myFinacialPaymentReturnedFragment.getCurrentTab(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.i("hh", "onPageScrollStateChanged ---" + state);
            }
        });
    }

    public void setAboutMoneyData(String waitingIncome, String accumulatedIncome, String totalHoldings) {
        this.waitingIncome = waitingIncome;
        this.accumulatedIncome = accumulatedIncome;
        this.totalHoldings = totalHoldings;

        tv_waiting_income.setText(waitingIncome);
        tv_accumulated_income.setText(accumulatedIncome);
        tv_total_holdings.setText(totalHoldings);
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
                    iv_eye_state.setImageResource(R.mipmap.img_eye_close);
                    tv_waiting_income.setText("****");
                    tv_accumulated_income.setText("****");
                    tv_total_holdings.setText("****");
                    showOrHideFlag = false;
                } else {
                    iv_eye_state.setImageResource(R.mipmap.img_eye_open);
                    tv_waiting_income.setText(waitingIncome);
                    tv_accumulated_income.setText(accumulatedIncome);
                    tv_total_holdings.setText(totalHoldings);
                    showOrHideFlag = true;
                }
                break;
        }
    }
}
