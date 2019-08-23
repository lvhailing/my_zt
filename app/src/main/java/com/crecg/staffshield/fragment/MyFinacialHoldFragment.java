package com.crecg.staffshield.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewSwitcher;


import com.crecg.crecglibrary.RemoteFactory;
import com.crecg.crecglibrary.network.CommonObserverAdapter;
import com.crecg.crecglibrary.network.CommonRequestProxy;
import com.crecg.crecglibrary.network.model.CommonResultModel;
import com.crecg.crecglibrary.network.model.HomeAndFinancialDataModel;
import com.crecg.crecglibrary.network.model.HomeAndFinancialProductItemDataModel;
import com.crecg.crecglibrary.network.model.MyFinancialDataModel;
import com.crecg.crecglibrary.network.model.MyFinancialProductItemDataModel;
import com.crecg.crecglibrary.network.model.ProductModelTestData;
import com.crecg.crecglibrary.utils.ToastUtil;
import com.crecg.crecglibrary.utils.encrypt.DESUtil;
import com.crecg.staffshield.R;
import com.crecg.staffshield.activity.MyFinancialManagementListActivity;
import com.crecg.staffshield.adapter.MyFinaciaHoldAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 我的理财 -- 持有中列表 Fragment
 */
public class MyFinacialHoldFragment extends Fragment {
    private static final String KEY = "param1";

    private String mParam1;
    private Context context;
    private RecyclerView recycler_view;
    private MyFinaciaHoldAdapter myFinaciaHoldAdapter;
    private ArrayList<MyFinancialProductItemDataModel> totalList = new ArrayList<>();
    private List<MyFinancialProductItemDataModel> everyList;
    private int currentPage = 1;    //当前页
    private int currentPosition; // 当前tab位置（0：持有中，1：已回款）
    private String userId;
    private ViewSwitcher vs;
    private ArrayList<ProductModelTestData> list; // 模拟数据

    private String accumulatedIncome; // 累计收益
    private String waitingIncome; // 待收收益
    private String totalHoldings; // 持仓总额


    public static MyFinacialHoldFragment newInstance(String param1) {
        MyFinacialHoldFragment fragment = new MyFinacialHoldFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY, param1);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //页面可见时调接口刷新数据
//            Log.i("hh", this + " -- setUserVisibleHint --" + isVisibleToUser);
            totalList.clear();
            currentPage = 1;
            requestData();
//            initData();
        } else {
            Log.i("hh", this + " -- setUserVisibleHint --" + isVisibleToUser);
            if (myFinaciaHoldAdapter != null) {
                totalList.clear();
                currentPage = 1;
                myFinaciaHoldAdapter.changeMoreStatus(myFinaciaHoldAdapter.NO_LOAD_MORE);
            }
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initData();
        View view = inflater.inflate(R.layout.fragment_recycle_layout, container, false); // Todo need modify
        initView(view);
        initListener();

        return view;
    }

    private void initData() {
        // 模拟数据
        ProductModelTestData testData1 = new ProductModelTestData();
        testData1.name = "中铁1号";
        testData1.holdingShare = "200000.00";
        testData1.expectedEarnings = "1020.00";
        testData1.date = "2019/06/04-2019/08/31";
        testData1.state = "one";

        ProductModelTestData testData2 = new ProductModelTestData();
        testData2.name = "中铁2号";
        testData2.holdingShare = "3000000.00";
        testData2.expectedEarnings = "100000.00";
        testData2.date = "2019/07/01-2019/09/01";
        testData2.state = "one";

        ProductModelTestData testData3 = new ProductModelTestData();
        testData3.name = "中铁3号";
        testData3.holdingShare = "4000000.00";
        testData3.expectedEarnings = "200000.00";
        testData3.date = "2019/03/01-2019/05/31";
        testData3.state = "two";

        ProductModelTestData testData4 = new ProductModelTestData();
        testData4.name = "中铁4号";
        testData4.holdingShare = "5000000.00";
        testData4.expectedEarnings = "500000.00";
        testData4.date = "2019/10/04-2019/12/05";
        testData4.state = "two";

        ProductModelTestData testData5 = new ProductModelTestData();
        testData5.name = "中铁5号";
        testData5.holdingShare = "60000000.00";
        testData5.expectedEarnings = "600000.00";
        testData5.date = "2019/06/04-2019/08/31";
        testData5.state = "three";

        list = new ArrayList<>();
        list.add(testData1);
        list.add(testData2);
        list.add(testData3);
        list.add(testData4);
        list.add(testData5);
    }

    private void initView(View view) {
        context = getActivity();

//        vs = (ViewSwitcher) view.findViewById(R.id.vs);
        recycler_view = view.findViewById(R.id.recycler_view);

        initRecyclerView();
    }

    private void initRecyclerView() {
        recycler_view.setLayoutManager(new LinearLayoutManager(context));
        myFinaciaHoldAdapter = new MyFinaciaHoldAdapter(context, totalList);
        recycler_view.setAdapter(myFinaciaHoldAdapter);
        //添加动画
        recycler_view.setItemAnimator(new DefaultItemAnimator());
    }

    /**
     * 获取持有中列表数据
     */
    public void requestData() {
        HashMap<String, Object> param = new HashMap<>();
        param.put("userId","8");
        param.put("pageNum", currentPage);
        param.put("productType","possession");
        String data = DESUtil.encMap(param);

        HashMap<String, Object> paramWrapper = new HashMap<>();
        paramWrapper.put("requestKey", data);
        RemoteFactory.getInstance().getProxy(CommonRequestProxy.class)
                .getMyFinancialListData(paramWrapper)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CommonObserverAdapter<String>() {
                    @Override
                    public void onMyError() {
                        ToastUtil.showCustom("我的理财列表获取数据失败");
                    }

                    @Override
                    public void onMySuccess(String result) {
                        if (result == null) {
                            return;
                        }
                        CommonResultModel<MyFinancialDataModel> financialListDataModel = new Gson().fromJson(result, new TypeToken<CommonResultModel<MyFinancialDataModel>>() {
                        }.getType());
                        MyFinancialDataModel financialListData = financialListDataModel.data;
                        if (financialListData == null) {
                            return;
                        }

                        accumulatedIncome = financialListData.ljProfit; // 累计收益
                        waitingIncome = financialListData.dsProfit; // 待收收益
                        totalHoldings = financialListData.productAllSum; // 持仓总额
                        ((MyFinancialManagementListActivity)getActivity()).setAboutMoneyData(waitingIncome,accumulatedIncome,totalHoldings);
                         everyList = financialListData.listProducts;
                        if (everyList == null) {
                            return;
                        }
                        if (everyList.size() == 0 && currentPage != 1) {
                            ToastUtil.showCustom("已显示全部");
                            myFinaciaHoldAdapter.changeMoreStatus(myFinaciaHoldAdapter.NO_LOAD_MORE);
                        }
                        if (currentPage == 1) {
                            //刚进来时 加载第一页数据，或下拉刷新 重新加载数据 。这两种情况之前的数据都清掉
                            totalList.clear();
                        }
                        totalList.addAll(everyList);
                        myFinaciaHoldAdapter.notifyDataSetChanged();
//                         0:从后台获取到数据展示的布局；1：从后台没有获取到数据时展示的布局；
//                        if (totalList.size() == 0) {
//                            vs.setDisplayedChild(1);
//                        } else {
//                            vs.setDisplayedChild(0);
//                        }
//                        if (totalList.size() != 0 && totalList.size() % 10 == 0) {
//                            vs.setDisplayedChild(0);
//                            myFinaciaHoldAdapter.changeMoreStatus(myFinaciaHoldAdapter.PULLUP_LOAD_MORE);
//                        } else {
//                            myFinaciaHoldAdapter.changeMoreStatus(myFinaciaHoldAdapter.NO_LOAD_MORE);
//                        }

                    }
                });
    }

    private void initListener() {
        initLoadMoreListener();
    }


    private void initLoadMoreListener() {
        recycler_view.setOnScrollListener(new RecyclerView.OnScrollListener() {
            private int firstVisibleItem;
            private int lastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //判断RecyclerView的状态 是空闲时，同时，是最后一个可见的ITEM时才加载
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == myFinaciaHoldAdapter.getItemCount() && firstVisibleItem != 0) {
                    if (everyList.size() == 0) {
                        return;
                    }
                    currentPage++;
                    requestData();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }
        });
    }

    public void getCurrentTab(int position) {
        this.currentPosition = position;
    }

    /**
     * 接收Activity传过来的userId
     * @param userId
     * @return
     */
    public String setUserId(String userId) {
        this.userId = userId;
        return userId;
    }
}