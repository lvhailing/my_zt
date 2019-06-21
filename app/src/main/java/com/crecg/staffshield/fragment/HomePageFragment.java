package com.crecg.staffshield.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.crecg.staffshield.R;
import com.crecg.staffshield.activity.TestActivity1;

/**
 * 首页
 */
public class HomePageFragment extends Fragment implements View.OnClickListener {

    private View mView;
    private Context context;
    private SwipeRefreshLayout swipe_refresh;
    private ScrollView scrollView;
    private LinearLayout ll_vp; // 顶部轮播图
    private LinearLayout ll_point_container; // 轮播图小点
    private LinearLayout ll_home_salary_treasure; // 工资宝
    private LinearLayout ll_home_manage_money; //  理财
    private LinearLayout ll_home_enterprise_dynamics; // 企业动态
    private LinearLayout ll_home_insurance; // 保险
    private LinearLayout ll_go_to_salary_treasure; // 活期 工资宝布局
    private TextView home_btn_transfer_immediately; // 立即转入
    private TextView tv_annualized_return; // 近七日年化收益率
    private LinearLayout home_include_layout_top_product_name; // 定期布局（上）
    private LinearLayout home_include_layout_center_product_name; // 定期布局（中）
    private LinearLayout home_include_layout_bottom_product_name; // 定期布局（下）
    private TextView home_tv_top_product_name; // 产品名称
    private TextView tv_top_annualized_return; // 年化收益率
    private TextView tv_top_time; // 定期产品天数
    private TextView tv_top_initial_investment_amount; // 定期产品起投金额
    private TextView tv_top_start_sale_time; // 产品开卖时间
    private ImageView iv_left_state; // 日期左侧的图标
    private ImageView iv_top_product_state; // 产品（售卖）状态

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_home_page, container, false);
            try {
                initView(mView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (mView.getParent() != null) {
                ((ViewGroup) mView.getParent()).removeView(mView);
            }
        }

        return mView;

    }

    private void initView(View mView) {
        context = getContext();
        swipe_refresh = (SwipeRefreshLayout) mView.findViewById(R.id.swipe_refresh);
        swipe_refresh.setColorSchemeResources(R.color.colorPrimary);

        scrollView = (ScrollView) mView.findViewById(R.id.scrollView);
        ll_vp = (LinearLayout) mView.findViewById(R.id.ll_vp);
        ll_point_container = (LinearLayout) mView.findViewById(R.id.ll_point_container);
        ll_home_salary_treasure = (LinearLayout) mView.findViewById(R.id.ll_home_salary_treasure);
        ll_home_manage_money = (LinearLayout) mView.findViewById(R.id.ll_home_manage_money);
        ll_home_enterprise_dynamics = (LinearLayout) mView.findViewById(R.id.ll_home_enterprise_dynamics);
        ll_home_insurance = (LinearLayout) mView.findViewById(R.id.ll_home_insurance);
        tv_annualized_return = (TextView) mView.findViewById(R.id.tv_annualized_return);
        ll_go_to_salary_treasure = (LinearLayout) mView.findViewById(R.id.ll_go_to_salary_treasure);
        home_btn_transfer_immediately = mView.findViewById(R.id.tv_transfer_immediately);
        home_include_layout_top_product_name = mView.findViewById(R.id.home_include_layout_top_product_name);
        home_include_layout_center_product_name = mView.findViewById(R.id.home_include_layout_center_product_name);
        home_include_layout_bottom_product_name = mView.findViewById(R.id.home_include_layout_bottom_product_name);
        home_tv_top_product_name = (TextView) mView.findViewById(R.id.home_tv_top_product_name);
        tv_top_annualized_return = (TextView) mView.findViewById(R.id.tv_top_annualized_return);
        tv_top_time = (TextView) mView.findViewById(R.id.tv_top_time);
        tv_top_initial_investment_amount = (TextView) mView.findViewById(R.id.tv_top_initial_investment_amount);
        tv_top_start_sale_time = (TextView) mView.findViewById(R.id.tv_top_start_sale_time);
        iv_left_state = (ImageView) mView.findViewById(R.id.iv_left_state);
        iv_top_product_state = (ImageView) mView.findViewById(R.id.iv_top_product_state);

        ll_home_salary_treasure.setOnClickListener(this);
        ll_home_manage_money.setOnClickListener(this);
        ll_home_enterprise_dynamics.setOnClickListener(this);
        ll_home_insurance.setOnClickListener(this);
        ll_go_to_salary_treasure.setOnClickListener(this);
        home_btn_transfer_immediately.setOnClickListener(this);
        home_include_layout_top_product_name.setOnClickListener(this);
        home_include_layout_center_product_name.setOnClickListener(this);
        home_include_layout_bottom_product_name.setOnClickListener(this);
    }

    /**
     * 获取首页数据
     */
    private void requestHomeData() {

    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ll_home_salary_treasure:  // 工资宝
//                intent = new Intent(context, TestActivity1.class);
//                startActivity(intent);
                break;
            case R.id.ll_home_manage_money:  // 理财
//                intent = new Intent(context, TestActivity1.class);
//                startActivity(intent);
                break;
            case R.id.ll_home_enterprise_dynamics:  // 企业动态
//                intent = new Intent(context, TestActivity1.class);
//                startActivity(intent);
                break;
            case R.id.ll_home_insurance:  // 保险
//                intent = new Intent(context, TestActivity1.class);
//                startActivity(intent);
                break;
            case R.id.ll_go_to_salary_treasure:  // 活期 工资宝布局
//                intent = new Intent(context, TestActivity1.class);
//                startActivity(intent);
                break;
            case R.id.home_include_layout_top_product_name:
                break;
            case R.id.home_include_layout_center_product_name:
                break;
            case R.id.home_include_layout_bottom_product_name:
                break;
            case R.id.tv_transfer_immediately:  // 立即转入
                intent = new Intent(context, TestActivity1.class);
                startActivity(intent);
                break;
        }
    }
}
