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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.crecg.crecglibrary.network.model.ProductModel;
import com.crecg.staffshield.R;
import com.crecg.staffshield.activity.TestActivity1;

import java.util.ArrayList;

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

    private LinearLayout ll_container; // 加载首页定期产品
    private ArrayList<ProductModel> list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initData();
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_home_page, container, false);
            try {
                initView();
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

    private void initData() {
        // 模拟数据
        ProductModel product1 = new ProductModel();
        product1.annualizedReturn = "6.12%";
        product1.date = "2019-06-04 14:00";
        product1.day = "22";
        product1.name = "中铁1号";
        product1.investmentAmount = "10万起投";
        product1.flag = 1;

        ProductModel product2 = new ProductModel();
        product2.annualizedReturn = "6.22%";
        product2.date = "2029-06-04 24:00";
        product2.day = "22";
        product2.name = "中铁2号";
        product2.investmentAmount = "20万起投";
        product2.flag = 2;

        ProductModel product3 = new ProductModel();
        product3.annualizedReturn = "6.32%";
        product3.date = "2039-06-04 34:00";
        product3.day = "22";
        product3.name = "中铁3号";
        product3.investmentAmount = "30万起投";
        product3.flag = 3;

        list = new ArrayList<>();
        list.add(product1);
        list.add(product2);
        list.add(product3);
    }

    private void initView() {
        context = getContext();
        ll_container = mView.findViewById(R.id.ll_container);
        for (ProductModel product : list) {
            ll_container.addView(getItem(product));
        }

        swipe_refresh = (SwipeRefreshLayout) mView.findViewById(R.id.swipe_refresh);
        swipe_refresh.setColorSchemeResources(R.color.colorPrimary);

        scrollView = mView.findViewById(R.id.scrollView);
        ll_vp = mView.findViewById(R.id.ll_vp);
        ll_point_container = mView.findViewById(R.id.ll_point_container);
        ll_home_salary_treasure = mView.findViewById(R.id.ll_home_salary_treasure);
        ll_home_manage_money = mView.findViewById(R.id.ll_home_manage_money);
        ll_home_enterprise_dynamics = mView.findViewById(R.id.ll_home_enterprise_dynamics);
        ll_home_insurance = mView.findViewById(R.id.ll_home_insurance);
        tv_annualized_return = mView.findViewById(R.id.tv_annualized_return);
        ll_go_to_salary_treasure = mView.findViewById(R.id.ll_go_to_salary_treasure);
        home_btn_transfer_immediately = mView.findViewById(R.id.tv_transfer_immediately);


        ll_home_salary_treasure.setOnClickListener(this);
        ll_home_manage_money.setOnClickListener(this);
        ll_home_enterprise_dynamics.setOnClickListener(this);
        ll_home_insurance.setOnClickListener(this);
        ll_go_to_salary_treasure.setOnClickListener(this);
        home_btn_transfer_immediately.setOnClickListener(this);
    }

    private View getItem(final ProductModel product) {
        View ll_item = LayoutInflater.from(context).inflate(R.layout.item_regular_products, null);
        TextView tv_regular_product_name1 = ll_item.findViewById(R.id.tv_regular_product_name); // 产品名称
        TextView tv_product_annualized_return1 = ll_item.findViewById(R.id.tv_product_annualized_return); // 年化收益率
        TextView tv_product_cycle1 = ll_item.findViewById(R.id.tv_product_cycle); // 产品周期（例：31天）
        TextView tv_initial_investment_amount1 = ll_item.findViewById(R.id.tv_initial_investment_amount); // 起投金额
        TextView tv_start_sale_time1 = ll_item.findViewById(R.id.tv_start_sale_time);  // 产品开售时间
        TextView tv_surplus_money1 = ll_item.findViewById(R.id.tv_surplus_money); // 产品剩余可投金额
        ImageView iv_will_sell_state1 = ll_item.findViewById(R.id.iv_will_sell_state); // 产品即将开售状态
        FrameLayout fl_start_sell1 = ll_item.findViewById(R.id.fl_start_sell);
        LinearLayout ll_best_sell1 = ll_item.findViewById(R.id.ll_best_sell);

        tv_regular_product_name1.setText(product.name);
        tv_product_annualized_return1.setText(product.annualizedReturn);
        tv_product_cycle1.setText(product.day);
        tv_initial_investment_amount1.setText(product.investmentAmount);
        tv_start_sale_time1.setText(product.date);
        int flag = product.flag;
        if (flag == 1) {
            //即将开售
            iv_will_sell_state1.setBackground(getResources().getDrawable(R.mipmap.img_on_sale));
            ll_best_sell1.setVisibility(View.GONE);
            fl_start_sell1.setVisibility(View.VISIBLE);
        } else if (flag == 2) {
            //热卖中
            iv_will_sell_state1.setVisibility(View.GONE);
            ll_best_sell1.setVisibility(View.VISIBLE);
            fl_start_sell1.setVisibility(View.GONE);
        } else if (flag == 3) {
            //售罄
            iv_will_sell_state1.setBackground(getResources().getDrawable(R.mipmap.img_sell_out));
            ll_best_sell1.setVisibility(View.GONE);
            fl_start_sell1.setVisibility(View.VISIBLE);
        }
        ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TestActivity1.class);
                intent.putExtra("aaa", product.investmentAmount);
                startActivity(intent);
            }
        });
        return ll_item;
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
            case R.id.tv_transfer_immediately:  // 立即转入
                intent = new Intent(context, TestActivity1.class);
                startActivity(intent);
                break;
        }
    }
}
