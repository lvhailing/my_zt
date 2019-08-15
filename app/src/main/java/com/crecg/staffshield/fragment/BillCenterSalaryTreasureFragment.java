package com.crecg.staffshield.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewSwitcher;

import com.crecg.crecglibrary.network.model.BillCenterListData;
import com.crecg.staffshield.R;
import com.crecg.staffshield.adapter.BillCenterSalaryTreasureRecycleAdapter;

import java.util.ArrayList;

/**
 * 账单中心 --- 工资宝
 */

public class BillCenterSalaryTreasureFragment extends Fragment {
    private static final String KEY = "param1";
    private String mParam1;
    private SwipeRefreshLayout swipe_refresh;
    private RecyclerView recycler_view;
    private int currentPage = 1;    //当前页
    private Context context;
    private int currentPosition; // 当前tab位置（0：提问，1：话题）
    private String userId;
    private ViewSwitcher vs;
    private ArrayList<BillCenterListData> list;
    private BillCenterSalaryTreasureRecycleAdapter salaryTreasureRecycleAdapter;


    public static BillCenterSalaryTreasureFragment newInstance(String param1) {
        BillCenterSalaryTreasureFragment fragment = new BillCenterSalaryTreasureFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY, param1);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            initData();
        } else {
//            Log.i("hh", this + " -- setUserVisibleHint --" + isVisibleToUser);
//            if (myFinaciaHoldAdapter != null) {
//                list.clear();
//                currentPage = 1;
//                myFinaciaHoldAdapter.changeMoreStatus(myFinaciaHoldAdapter.NO_LOAD_MORE);
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

    }
    private void initView(View view) {
        context = getActivity();

//        vs = (ViewSwitcher) view.findViewById(R.id.vs);
//        swipe_refresh = view.findViewById(R.id.swipe_refresh);
        recycler_view = view.findViewById(R.id.recycler_view);

//        TextView tv_empty = view.findViewById(R.id.tv_empty);
//        tv_empty.setText("测试...");

        initRecyclerView();
    }

    private void initRecyclerView() {
        recycler_view.setLayoutManager(new LinearLayoutManager(context));
        salaryTreasureRecycleAdapter = new BillCenterSalaryTreasureRecycleAdapter(context, list);
        recycler_view.setAdapter(salaryTreasureRecycleAdapter);
//        //添加动画
//        recycler_view.setItemAnimator(new DefaultItemAnimator());
    }

    /**
     * 获取“银行卡”列表数据
     */
    public void requestBillCenterAllData() {
    }

    private void initListener() {
//        initPullRefresh();
//        initLoadMoreListener();
    }

    private void initPullRefresh() {
        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {  // 下拉刷新
                currentPage = 1;
//                requestBillCenterAData();
            }
        });
    }

    private void initLoadMoreListener() {
        recycler_view.setOnScrollListener(new RecyclerView.OnScrollListener() {
            private int firstVisibleItem;
            private int lastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //判断RecyclerView的状态 是空闲时，同时，是最后一个可见的ITEM时才加载
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == salaryTreasureRecycleAdapter.getItemCount() && firstVisibleItem != 0) {
                    if (list.size() == 0) {
                        return;
                    }
                    currentPage++;
                    requestBillCenterAllData();
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

//    public String setUserId(String userId) {
//        this.userId = userId;
//        return userId;
//    }
}
