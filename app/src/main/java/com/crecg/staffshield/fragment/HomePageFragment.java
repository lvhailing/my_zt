package com.crecg.staffshield.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.crecg.crecglibrary.network.model.ProductModelTestData;
import com.crecg.crecglibrary.utils.ToastUtil;
import com.crecg.staffshield.R;
import com.crecg.staffshield.activity.AllKindsOfDetailsActivity;
import com.crecg.staffshield.activity.RegularFinancialManagementBuyingActivity;
import com.crecg.staffshield.activity.RegularFinancialManagementListActivity;
import com.crecg.staffshield.activity.SalaryTreasureDetailActivity;
import com.crecg.staffshield.activity.TestActivity1;
import com.crecg.staffshield.activity.WageTreasureBuyingActivity;
import com.crecg.staffshield.utils.DataUtil;
import com.crecg.staffshield.widget.MyRollViewPager;

import java.util.ArrayList;
import java.util.List;

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
    private TextView tv_home_more; // 更多

    private LinearLayout ll_container; // 加载首页定期产品
    private List<ProductModelTestData> list;
    private List<String> picList; // 滑动的图片集合
    private int[] imageResId; // 图片ID
    private MyRollViewPager rollViewPager;


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Log.i("hh", "HomePageFragment --- " + getClass());
        }
    }

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
        list = DataUtil.getProductList();

        // 模拟轮播图数据
        auto_Image();

        freshVP();
    }

    private void auto_Image() {
        picList = new ArrayList<String>();
        picList.add("https://csdnimg.cn/feed/20190712/9bb6aa25f82b061230d08a65a1b534c5.png");
        picList.add("https://ss.csdn.net/p?https://mmbiz.qpic.cn/mmbiz_jpg/Pn4Sm0RsAujxlx50uwaMWjpghsyVBYjZsgibRQlSTg5V28wWfNOz6QDwrIWHGolXJqFBoA3rWYlv6tnLwbDFQ8g/640?wx_fmt=jpeg");
        picList.add("https://ss.csdn.net/p?https://mmbiz.qpic.cn/mmbiz_jpg/Rm6KfjqOnWwDkSZUHmIjUAZpc1QXd43BgpEI45JKS7qJA2kghDYBr0CgwhJHTtat7RVawRcSiaFp8PgHm9q0YZA/640?wx_fmt=jpeg");

        imageResId = new int[]{R.mipmap.bg_banner_default1, R.mipmap.bg_banner_default2, R.mipmap.bg_banner_default3};
    }

    private void initView() {
        context = getContext();
        ll_container = mView.findViewById(R.id.ll_container);
        for (ProductModelTestData product : list) {
            ll_container.addView(getItem(product));
        }

        swipe_refresh = mView.findViewById(R.id.swipe_refresh);
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
        tv_home_more = mView.findViewById(R.id.tv_home_more);


        ll_home_salary_treasure.setOnClickListener(this);
        ll_home_manage_money.setOnClickListener(this);
        ll_home_enterprise_dynamics.setOnClickListener(this);
        ll_home_insurance.setOnClickListener(this);
        ll_go_to_salary_treasure.setOnClickListener(this);
        home_btn_transfer_immediately.setOnClickListener(this);
        tv_home_more.setOnClickListener(this);
    }

    private View getItem(final ProductModelTestData product) {
        final int flag = product.flag;
        View ll_item;
        // 加载 item 布局
        ll_item = LayoutInflater.from(context).inflate(R.layout.item_regular_products2, null);
        if (product.flag == 1 || product.flag == 2) {
            LinearLayout ll_container1 = ll_item.findViewById(R.id.ll_container1);
            ll_container1.setVisibility(View.VISIBLE);

            TextView tv_regular_product_name1 = ll_item.findViewById(R.id.tv_regular_product_name); // 产品名称
            TextView tv_product_annualized_return1 = ll_item.findViewById(R.id.tv_product_annualized_return); // 年化收益率
            TextView tv_product_cycle1 = ll_item.findViewById(R.id.tv_product_cycle); // 产品周期（例：31天）
            TextView tv_initial_investment_amount1 = ll_item.findViewById(R.id.tv_initial_investment_amount); // 起投金额
            TextView tv_start_sale_time1 = ll_item.findViewById(R.id.tv_start_sale_time);  // 产品开售时间
            ProgressBar progressbar = ll_item.findViewById(R.id.progressbar); // 产品进度条
            TextView tv_surplus_money1 = ll_item.findViewById(R.id.tv_surplus_money); // 产品剩余可投金额
            ImageView iv_will_sell_state1 = ll_item.findViewById(R.id.iv_will_sell_state); // 产品即将开售状态
            FrameLayout fl_start_sell1 = ll_item.findViewById(R.id.fl_start_sell);
            LinearLayout ll_best_sell1 = ll_item.findViewById(R.id.ll_best_sell);
            tv_regular_product_name1.setText(product.name);
            tv_product_annualized_return1.setText(product.annualizedReturn);
            tv_product_cycle1.setText(product.day);
            tv_initial_investment_amount1.setText(product.investmentAmount);
            tv_start_sale_time1.setText(product.date);

            if (flag == 1) {
                //热卖中
                ll_best_sell1.setVisibility(View.VISIBLE);
            } else if (flag == 2) {
                //即将开售
                iv_will_sell_state1.setVisibility(View.VISIBLE);
                iv_will_sell_state1.setBackgroundResource(R.mipmap.img_regular_product_on_sale);
                fl_start_sell1.setVisibility(View.VISIBLE);
            }
        } else {
            RelativeLayout rl_container2 = ll_item.findViewById(R.id.rl_container2);
            rl_container2.setVisibility(View.VISIBLE);

            TextView tv_regular_product_name2 = ll_item.findViewById(R.id.tv_regular_product_name2); // 产品名称
            TextView tv_product_annualized_return2 = ll_item.findViewById(R.id.tv_product_annualized_return2); // 年化收益率
            TextView tv_product_cycle2 = ll_item.findViewById(R.id.tv_product_cycle2); // 产品周期（例：31天）
            TextView tv_initial_investment_amount2 = ll_item.findViewById(R.id.tv_initial_investment_amount2); // 起投金额
            ImageView iv_product_state2 = ll_item.findViewById(R.id.iv_product_state2); // 产品状态图片

            tv_regular_product_name2.setText(product.name);
            tv_product_annualized_return2.setText(product.annualizedReturn);
            tv_product_cycle2.setText(product.day);
            tv_initial_investment_amount2.setText(product.investmentAmount);

            if (flag == 3) {
                //已售罄
                iv_product_state2.setBackground(getResources().getDrawable(R.mipmap.img_regular_product_sell_out));
            } else if (flag == 4) {
                //计息中
                iv_product_state2.setBackground(getResources().getDrawable(R.mipmap.img_regular_product_interest_bearing));
            } else if (flag == 5) {
                //已回款
                iv_product_state2.setBackground(getResources().getDrawable(R.mipmap.img_regular_product_payment_returned));
            }
        }

        // item 点击跳转定期理财购买页
        ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, RegularFinancialManagementBuyingActivity.class);
//                startActivity(intent);
            }
        });
        return ll_item;
    }

    @Override
    public void onResume() {
        super.onResume();
//        Log.i("hh", "首页 onResume方法：" + getClass());
        initData();
    }

    /**
     * 获取首页数据
     */
    private void requestHomeData() {

    }

    /**
     * 请求轮播图数据
     */
    private void freshVP() {
        if (context == null) {
            return;
        }
        if (rollViewPager == null) {
            //第一次从后台获取到数据
            rollViewPager = new MyRollViewPager(context, picList, ll_point_container);
            rollViewPager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_MOVE:
//                            swipe_refresh.setEnabled(false);
                            break;
                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_CANCEL:
//                            swipe_refresh.setEnabled(true);
                            break;
                    }
                    return false;
                }
            });
            rollViewPager.setCycle(true);
            rollViewPager.setOnMyListener(new MyRollViewPager.MyClickListener() {
                @Override
                public void onMyClick(int position) {
                    if (picList != null && picList.size() != 0) {
                        ToastUtil.showCustom("当前的position = " + position);
                    }

                }
            });
            rollViewPager.startRoll();
            ll_vp.addView(rollViewPager);
        } else {
            //第二或之后获取到数据，刷新vp
            rollViewPager.setPicList(picList);
            rollViewPager.reStartRoll();
        }
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
                intent = new Intent(context, TestActivity1.class);
                startActivity(intent);
                break;
            case R.id.ll_go_to_salary_treasure:  // 活期 工资宝布局 (跳转工资宝详情页H5)
                intent = new Intent(context, SalaryTreasureDetailActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_transfer_immediately:  // 立即转入 （已经开户跳转工资宝买入页）
                // Todo 点立即转入时，需要先判断用户是否开通联名卡账户
                intent = new Intent(context, AllKindsOfDetailsActivity.class);
                intent.putExtra("fromFlag", 1);
                startActivity(intent);
                break;
            case R.id.tv_home_more: // 更多 （跳转到定期理财列表页）
                intent = new Intent(context, RegularFinancialManagementListActivity.class);
                startActivity(intent);
        }
    }
}
