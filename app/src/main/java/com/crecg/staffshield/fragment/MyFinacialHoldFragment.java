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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;


import com.crecg.staffshield.R;
import com.crecg.staffshield.adapter.MyPartakeAskAdapter;

import java.util.LinkedHashMap;

/**
 * 我的理财 -- 持有中列表 Fragment
 */
public class MyFinacialHoldFragment extends Fragment {
    private static final String KEY = "param1";

    private String mParam1;
    private SwipeRefreshLayout swipe_refresh;
    private RecyclerView recycler_view;
    private MyPartakeAskAdapter myPartakeAskAdapter;
//    private MouldList<MyAskList2B> totalList = new MouldList<>();
    private int currentPage = 1;    //当前页
    private Context context;
    private int currentPosition; // 当前tab位置（0：提问，1：话题）
    private String userId;
    private ViewSwitcher vs;
//    private MouldList<MyAskList2B> everyList;


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
            Log.i("hh", this + " -- setUserVisibleHint --" + isVisibleToUser);
//            totalList.clear();
            currentPage = 1;
            requestAskData();
        } else {
            Log.i("hh", this + " -- setUserVisibleHint --" + isVisibleToUser);
            if (myPartakeAskAdapter != null) {
//                totalList.clear();
                currentPage = 1;
                myPartakeAskAdapter.changeMoreStatus(myPartakeAskAdapter.NO_LOAD_MORE);
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
        View view = inflater.inflate(R.layout.fragment_found, container, false); // Todo need modify
        initView(view);
        initListener();

        return view;
    }

    private void initView(View view) {
        context = getActivity();

//        vs = (ViewSwitcher) view.findViewById(R.id.vs);
        swipe_refresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);

//        TextView tv_empty = (TextView) view.findViewById(R.id.tv_empty);
//        tv_empty.setText("未参与提问");

        initRecyclerView();
    }

    private void initRecyclerView() {
//        recycler_view.setLayoutManager(new LinearLayoutManager(context));
//        myPartakeAskAdapter = new MyPartakeAskAdapter(context, totalList);
//        recycler_view.setAdapter(myPartakeAskAdapter);
//        //添加动画
//        recycler_view.setItemAnimator(new DefaultItemAnimator());
    }

    /**
     * 获取提问列表数据
     */
    public void requestAskData() {
    }

    private void initListener() {
        initPullRefresh();
        initLoadMoreListener();
    }

    private void initPullRefresh() {
        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {  // 下拉刷新
                currentPage = 1;
                requestAskData();
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
//                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == myPartakeAskAdapter.getItemCount() && firstVisibleItem != 0) {
//                    if (everyList.size() == 0) {
//                        return;
//                    }
//                    currentPage++;
//                    requestAskData();
//                }
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

    public String setUserId(String userId) {
        this.userId = userId;
        return userId;
    }
}