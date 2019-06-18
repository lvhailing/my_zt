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

import java.util.LinkedHashMap;

/**
 * 我参与的 -- 话题列表 Fragment
 */
public class MyFinacialPaymentReturnedFragment extends Fragment {
    private static final String KEY = "param1";

    private String mParam1;
    private SwipeRefreshLayout swipe_refresh;
    private RecyclerView recycler_view;
//    private MyPartakeTopicAdapter myPartakeTopicAdapter;
//    private MouldList<MyTopicList2B> totalList = new MouldList<>();
    private int currentPage = 1;    //当前页
    private Context context;
    private int currentPosition; // 当前tab位置（0：提问，1：话题）
    private String userId;
    private ViewSwitcher vs;
//    private MouldList<MyTopicList2B> everyList;
//    private MyAskList1B data;


    public static MyFinacialPaymentReturnedFragment newInstance(String param1) {
        MyFinacialPaymentReturnedFragment fragment = new MyFinacialPaymentReturnedFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY, param1);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser) {
//            //获取话题列表数据
//            Log.i("hh", this + " -- setUserVisibleHint --" + isVisibleToUser);
//            totalList.clear();
//            currentPage = 1;
//            requestTopicData();
//        } else {
//            Log.i("hh", this + " -- setUserVisibleHint --" + isVisibleToUser);
//            if (myPartakeTopicAdapter != null) {
//                totalList.clear();
//                currentPage = 1;
//                myPartakeTopicAdapter.changeMoreStatus(myPartakeTopicAdapter.NO_LOAD_MORE);
//            }
//        }
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
        View view = inflater.inflate(R.layout.fragment_found, container, false);
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
//        tv_empty.setText("未参与话题");

        initRecyclerView();
    }

    private void initRecyclerView() {
//        recycler_view.setLayoutManager(new LinearLayoutManager(context));
//        myPartakeTopicAdapter = new MyPartakeTopicAdapter(context, totalList);
//        recycler_view.setAdapter(myPartakeTopicAdapter);
//        //添加动画
//        recycler_view.setItemAnimator(new DefaultItemAnimator());
    }


    private void requestTopicData() {

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
                requestTopicData();
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
//                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == myPartakeTopicAdapter.getItemCount() && firstVisibleItem != 0) {
//                    if (everyList.size() == 0) {
//                        return;
//                    }
//
//                    currentPage++;
//                    requestTopicData();
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