package com.crecg.staffshield.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.crecg.crecglibrary.RemoteFactory;
import com.crecg.crecglibrary.network.CommonObserverAdapter;
import com.crecg.crecglibrary.network.CommonRequestProxy;
import com.crecg.crecglibrary.network.model.CommonResultModel;
import com.crecg.crecglibrary.network.model.HomeAndFinancialDataModel;
import com.crecg.crecglibrary.network.model.HomeAndFinancialProductItemDataModel;
import com.crecg.crecglibrary.network.model.MyInsuranceItemModel;
import com.crecg.crecglibrary.network.model.MyInsuranceModel;
import com.crecg.crecglibrary.utils.ToastUtil;
import com.crecg.crecglibrary.utils.encrypt.DESUtil;
import com.crecg.staffshield.R;
import com.crecg.staffshield.adapter.BillCenterRVAdapter;
import com.crecg.staffshield.adapter.MyInsuranceAdapter;
import com.crecg.staffshield.common.BaseActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 我的保险
 */

public class MyInsuranceActivity extends BaseActivity {

    private ImageView iv_back;
    private TextView tv_common_title;
    private SwipeRefreshLayout swipe_refresh;
    private ViewSwitcher vs;
    private RecyclerView recycler_view;
    private ArrayList<MyInsuranceItemModel> totalList = new ArrayList<>();
    private MyInsuranceAdapter adapter;
    private List<MyInsuranceItemModel> listData;
    private int currentPage = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_my_insurance);

        initView();
        initListener();
        requestListData();
    }

    private void initView() {
        setTitle();

        vs = findViewById(R.id.vs);
        swipe_refresh = findViewById(R.id.swipe_refresh);
        recycler_view = findViewById(R.id.recycler_view);
        ImageView iv_no_data = findViewById(R.id.iv_no_data);
        iv_no_data.setBackgroundResource(R.mipmap.ic_empty);

        initRecycleView();
    }

    private void initListener() {
        initPullRefresh();
        initLoadMoreListener();
    }

    /**
     *  初始化SwipeRefreshLayout下拉刷新
     */
    private void initPullRefresh() {
        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() { // 下拉刷新
                currentPage = 1;
                requestListData();
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
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount() && firstVisibleItem != 0) {
                    currentPage ++;
                    requestListData();
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

    private void initRecycleView() {
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyInsuranceAdapter(this, totalList);
        recycler_view.setAdapter(adapter);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
    }

    private void setTitle() {
        iv_back = findViewById(R.id.iv_back);
        tv_common_title = findViewById(R.id.tv_common_title);

        iv_back.setImageResource(R.mipmap.img_arrow_left2);
        tv_common_title.setText("我的保险");

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 获取保险列表数据
     */
    private void requestListData() {
        HashMap<String, Object> param = new HashMap<>();
        param.put("userId", 26);
        param.put("startPage", currentPage);
        String data = DESUtil.encMap(param);

        HashMap<String, Object> paramWrapper = new HashMap<>();
        paramWrapper.put("requestKey", data);
        RemoteFactory.getInstance().getProxy(CommonRequestProxy.class)
                .getInsuranceListData(paramWrapper)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CommonObserverAdapter<String>() {
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
                        CommonResultModel<MyInsuranceModel> dataModel = new Gson().fromJson(result, new TypeToken<CommonResultModel<MyInsuranceModel>>() {
                        }.getType());
                        MyInsuranceModel data = dataModel.data;
                        if (data == null) {
                            return;
                        }
                        List<MyInsuranceItemModel> everyList = data.dataList;

                        if (everyList == null) {
                            return;
                        }
                        if (everyList.size() == 0 && currentPage != 1) {
                            ToastUtil.showCustom("已显示全部");
                            adapter.changeMoreStatus(adapter.NO_LOAD_MORE);
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
                            adapter.changeMoreStatus(adapter.PULLUP_LOAD_MORE);
                        } else {
                            adapter.changeMoreStatus(adapter.NO_LOAD_MORE);
                        }

                    }
                });
    }

}
