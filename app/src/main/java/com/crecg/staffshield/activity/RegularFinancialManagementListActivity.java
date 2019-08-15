package com.crecg.staffshield.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.crecg.crecglibrary.RemoteFactory;
import com.crecg.crecglibrary.network.CommonObserverAdapter;
import com.crecg.crecglibrary.network.CommonRequestProxy;
import com.crecg.crecglibrary.network.model.CommonResultModel;
import com.crecg.crecglibrary.network.model.HomeAndFinancialDataModel;
import com.crecg.crecglibrary.network.model.HomeAndFinancialProductItemDataModel;
import com.crecg.crecglibrary.network.model.ProductModelTestData;
import com.crecg.crecglibrary.utils.ToastUtil;
import com.crecg.crecglibrary.utils.encrypt.DESUtil;
import com.crecg.staffshield.R;
import com.crecg.staffshield.adapter.RegularFinancialListAdapter;
import com.crecg.staffshield.common.BaseActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 定期理财 列表
 * Created by junde on 2019/7/10.
 */

public class RegularFinancialManagementListActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back;
    private TextView tv_common_title;

    private ViewSwitcher vs;
    private SwipeRefreshLayout swipe_refresh;
    private RecyclerView recycler_view;
    private RegularFinancialListAdapter regularFinancialListAdapter;
    private HomeAndFinancialDataModel financialListData;
    private ArrayList<HomeAndFinancialProductItemDataModel> totalList = new ArrayList<>();
    private int currentPage = 1;

    private ArrayList<ProductModelTestData> list; // 模拟数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_regular_financial_management_list);

//        initData();
        initView();
        initListener();
        requestRegularFinancialListData();

    }

    private void initView() {
        setTitle();
        vs = findViewById(R.id.vs);
        swipe_refresh = findViewById(R.id.swipe_refresh);
        recycler_view = findViewById(R.id.recycler_view);

        ImageView iv_no_data = findViewById(R.id.iv_no_data);
        iv_no_data.setBackgroundResource(R.mipmap.ic_empty);
        initRecyclerView();
    }

    /**
     *  设置页面标题
     */
    private void setTitle() {
        iv_back = findViewById(R.id.iv_back);
        tv_common_title = findViewById(R.id.tv_common_title);

        iv_back.setImageResource(R.mipmap.img_arrow_left2);
        tv_common_title.setText("定期理财");
    }

    private void initListener() {
        iv_back.setOnClickListener(this);

        initPullRefresh();
        initLoadMoreListener();
    }

    /**
     * 初始化RecycleView
     */
    private void initRecyclerView() {
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        regularFinancialListAdapter = new RegularFinancialListAdapter(this, totalList);
        recycler_view.setAdapter(regularFinancialListAdapter);

        //添加动画
        recycler_view.setItemAnimator(new DefaultItemAnimator());
    }
    /**
     *  初始化SwipeRefreshLayout下拉刷新
     */
    private void initPullRefresh() {
        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() { // 下拉刷新
                currentPage = 1;
                requestRegularFinancialListData();
            }
        });
    }

    private void initLoadMoreListener() {
        recycler_view.setOnScrollListener(new RecyclerView.OnScrollListener() {
            private int firstVisibleItem = 0;
            private int lastVisibleItem = 0;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                // 判断RecyclerView的状态: 是空闲时，并且是最后一个可见的ITEM时才加载
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == regularFinancialListAdapter.getItemCount() && firstVisibleItem != 0) {
                    currentPage ++;
                    requestRegularFinancialListData();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager)recyclerView.getLayoutManager();
                firstVisibleItem =layoutManager.findFirstVisibleItemPosition();
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }
        });
    }

    private void initData() {
        // 模拟数据
        ProductModelTestData product1 = new ProductModelTestData();
        product1.annualizedReturn = "6.12%";
        product1.date = "2019-06-04 14:00";
        product1.day = "22";
        product1.name = "中铁1号";
        product1.investmentAmount = "10万起投";
        product1.progressBar = 25;
        product1.surplusMoney = "2200000";
        product1.flag = 1;

        ProductModelTestData product2 = new ProductModelTestData();
        product2.annualizedReturn = "6.22%";
        product2.date = "2029-06-04 24:00";
        product2.day = "22";
        product2.name = "中铁2号";
        product2.investmentAmount = "20万起投";
        product2.flag = 2;

        ProductModelTestData product3 = new ProductModelTestData();
        product3.annualizedReturn = "6.32%";
        product3.date = "2039-06-04 34:00";
        product3.day = "22";
        product3.name = "中铁3号";
        product3.investmentAmount = "30万起投";
        product3.flag = 3;

        ProductModelTestData product4 = new ProductModelTestData();
        product4.annualizedReturn = "6.32%";
        product4.date = "2039-06-04 34:00";
        product4.day = "22";
        product4.name = "中铁4号";
        product4.investmentAmount = "50万起投";
        product4.flag = 4;

        ProductModelTestData product5 = new ProductModelTestData();
        product5.annualizedReturn = "6.32%";
        product5.date = "2039-06-04 34:00";
        product5.day = "22";
        product5.name = "中铁5号";
        product5.investmentAmount = "70万起投";
        product5.flag = 5;

        ProductModelTestData product6 = new ProductModelTestData();
        product6.annualizedReturn = "4.72%";
        product6.date = "2019-07-11 10:00";
        product6.day = "180";
        product6.name = "中铁6号";
        product6.investmentAmount = "10万起投";
        product6.progressBar = 85;
        product6.surplusMoney = "120000";
        product6.flag = 1;

        list = new ArrayList<>();
        list.add(product1);
        list.add(product2);
        list.add(product3);
        list.add(product4);
        list.add(product5);
        list.add(product6);
    }

    /**
     * 获取定期理财列表数据
     */
    private void requestRegularFinancialListData() {
        HashMap<String, Object> param = new HashMap<>();
        param.put("pageNum", currentPage + ""); // 页码不传默认为1
        param.put("pageSize", ""); // 页码不传默认为3条
        param.put("listType", "product"); // 定期理财传 product
        String data = DESUtil.encMap(param);

        HashMap<String, Object> paramWrapper = new HashMap<>();
        paramWrapper.put("requestKey", data);
        RemoteFactory.getInstance().getProxy(CommonRequestProxy.class)
                .requestHomeAndFinancialData(paramWrapper)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CommonObserverAdapter<String, HomeAndFinancialDataModel>() {
                    @Override
                    public void onMyError() {
                        if (swipe_refresh.isRefreshing()) {
                            //请求返回后，无论本次请求成功与否，都关闭下拉旋转
                            swipe_refresh.setRefreshing(false);
                        }
//                        ToastUtil.showCustom("定期理财列表获取数据失败");
                    }

                    @Override
                    public void onMySuccess(String result) {
                        if (swipe_refresh.isRefreshing()) {
                            //请求返回后，无论本次请求成功与否，都关闭下拉旋转
                            swipe_refresh.setRefreshing(false);
                        }
                        if (result == null) {
                            return;
                        }
                        CommonResultModel<HomeAndFinancialDataModel> financialListDataModel = new Gson().fromJson(result, new TypeToken<CommonResultModel<HomeAndFinancialDataModel>>() {
                        }.getType());
                        financialListData = financialListDataModel.data;
                        if (financialListData == null) {
                            return;
                        }
                        List<HomeAndFinancialProductItemDataModel> everyList = financialListData.productList;

                        if (everyList == null) {
                            return;
                        }
                        if (everyList.size() == 0 && currentPage != 1) {
                            ToastUtil.showCustom("已显示全部");
                            regularFinancialListAdapter.changeMoreStatus(regularFinancialListAdapter.NO_LOAD_MORE);
                        }
                        if (currentPage == 1) {
                            //刚进来时 加载第一页数据，或下拉刷新 重新加载数据 。这两种情况之前的数据都清掉
                            totalList.clear();
                        }
                        totalList.addAll(everyList);
//                        regularFinancialListAdapter.notifyDataSetChanged();
//                         0:从后台获取到数据展示的布局；1：从后台没有获取到数据时展示的布局；
                        if (totalList.size() == 0) {
                            vs.setDisplayedChild(1);
                        } else {
                            vs.setDisplayedChild(0);
                        }
                        if (totalList.size() != 0 && totalList.size() % 10 == 0) {
                            vs.setDisplayedChild(0);
                            regularFinancialListAdapter.changeMoreStatus(regularFinancialListAdapter.PULLUP_LOAD_MORE);
                        } else {
                            regularFinancialListAdapter.changeMoreStatus(regularFinancialListAdapter.NO_LOAD_MORE);
                        }

                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
