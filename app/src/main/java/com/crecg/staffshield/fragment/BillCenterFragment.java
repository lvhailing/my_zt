package com.crecg.staffshield.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
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
import com.crecg.crecglibrary.network.model.BillCenterDataModel;
import com.crecg.crecglibrary.network.model.BillCenterItemInnerDataModel;
import com.crecg.crecglibrary.network.model.BillCenterItemOutDataModel;
import com.crecg.crecglibrary.network.model.CommonResultModel;
import com.crecg.crecglibrary.utils.ToastUtil;
import com.crecg.crecglibrary.utils.encrypt.DESUtil;
import com.crecg.staffshield.R;
import com.crecg.staffshield.adapter.BillCenterRVAdapter;
import com.crecg.staffshield.adapter.BillCenterRecycleAdapter;
import com.crecg.staffshield.utils.DataUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 账单中心 Fragment
 */

public class BillCenterFragment extends Fragment {
    private static final String KEY = "param1";
    private String mParam1;
    private SwipeRefreshLayout swipe_refresh;
    private RecyclerView recycler_view;
    private int currentPage = 1;    //当前页
    private int currentPosition; // 当前tab位置
    private String userId;
    private ViewSwitcher vs;

    private String type;
    private BillCenterRVAdapter adapter;
    private List<BillCenterItemOutDataModel> everyListOriginal = new ArrayList<>();   //未移除前的数据
    private Map<String, List<BillCenterItemInnerDataModel>> map = new LinkedHashMap<>();

    public static BillCenterFragment newInstance(String param1) {
        BillCenterFragment fragment = new BillCenterFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY, param1);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser) {
//            map.clear();
//            currentPage = 1;
//            requestBillCenterData();
//        } else {
//            if (adapter != null) {
//                map.clear();
//                currentPage = 1;
//                adapter.changeMoreStatus(adapter.NO_LOAD_MORE);
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
        View view = inflater.inflate(R.layout.fragment_recycle_layout, container, false); // Todo need modify

        initView(view);
        initLoadMoreListener();
        initPullRefreshListener();
        requestBillCenterData();

        return view;
    }

    private void initView(View view) {
        vs = view.findViewById(R.id.vs);
        swipe_refresh = view.findViewById(R.id.swipe_refresh);
        recycler_view = view.findViewById(R.id.recycler_view);

        recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new BillCenterRVAdapter(getActivity(), null);
        recycler_view.setAdapter(adapter);
        recycler_view.setItemAnimator(new DefaultItemAnimator());

    }

    private void initLoadMoreListener() {
        recycler_view.setOnScrollListener(new RecyclerView.OnScrollListener() {
            private int firstVisibleItem;
            private int lastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //判断RecyclerView的状态 是空闲时，同时，是最后一个可见的ITEM时才加载
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount() && firstVisibleItem != 0) {
                    currentPage++;
                    requestBillCenterData();
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

    private void initPullRefreshListener() {
        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() { // 下拉刷新
                currentPage = 1;
                requestBillCenterData();
            }
        });
    }

    /**
     * 获取“全部”列表数据
     */
    public void requestBillCenterData() {
        // 模拟数据
//        String result = "";
//        if (currentPage == 1) {
//            result = DataUtil.getBankInfo1();
//        } else if (currentPage == 2) {
//            result = DataUtil.getBankInfo2();
//        } else if (currentPage == 3) {
//            result = DataUtil.getBankInfo3();
//        } else if (currentPage == 4) {
//            result = DataUtil.getBankInfo4();
//        } else if (currentPage == -1 || currentPage == 0) {
//            result = DataUtil.getBankInfo0();
//        }

        HashMap<String, Object> param = new HashMap<>();
        param.put("userId", "26");
        param.put("pageNum", currentPage);
        param.put("type", type);
        Log.i("hh", "type = " + type + "  pageNum = " + currentPage);
        String data = DESUtil.encMap(param);

        HashMap<String, Object> paramWrapper = new HashMap<>();
        paramWrapper.put("requestKey", data);
        RemoteFactory.getInstance().getProxy(CommonRequestProxy.class)
                .getBillCenterListData(paramWrapper)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CommonObserverAdapter<String, BillCenterDataModel>() {
                    @Override
                    public void onMyError() {
                        if (swipe_refresh.isRefreshing()) {
                            //请求返回后，无论本次请求成功与否，都关闭下拉旋转
                            swipe_refresh.setRefreshing(false);
                        }
                        ToastUtil.showCustom("账单中心数据失败");
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
                        CommonResultModel<BillCenterDataModel> billModel = new Gson().fromJson(result, new TypeToken<CommonResultModel<BillCenterDataModel>>() {
                        }.getType());
                        if (billModel == null || billModel.data == null) {
                            return;
                        }
                        List<BillCenterItemOutDataModel> everyList = billModel.data.billList;
                        if ((everyList == null || everyList.size() == 0) && currentPage == 1) {
                            //当前是第一页，且无数据，则显示无数据页面
                            vs.setDisplayedChild(1);
                            return;
                        } else {
                            vs.setDisplayedChild(0);
                        }
                        if (everyList == null) {
                            return;
                        }
                        //方便计算本次加载的数据里有几个item
                        everyListOriginal.clear();
                        everyListOriginal.addAll(everyList);

                        if (currentPage == 1) {
                            //刚进来时 加载第一页数据，或下拉刷新 重新加载数据 。这两种情况之前的数据都清掉
                            map.clear();
                        }
                        if (map.size() == 0) {
                            //第一页
                            for (BillCenterItemOutDataModel item : everyList) {
                                map.put(item.month, item.data);
                            }
                        } else {
                            //第二页及以后
                            if (everyList.size() > 0) {
                                String firstMonthName = everyList.get(0).month;
                                if (map.containsKey(firstMonthName)) {
                                    //当前页含下一页的月份
                                    List<BillCenterItemInnerDataModel> innerList = map.get(firstMonthName);
                                    innerList.addAll(everyList.get(0).data);
                                    everyList.remove(0);
                                }
                                //将后续的数据合并到map里
                                for (BillCenterItemOutDataModel item : everyList) {
                                    map.put(item.month, item.data);
                                }
                            }
                        }

                        //重新设置下adapter里的数据
                        adapter.setData(map);

                        if ((everyListOriginal.size() == 0 || getInnerItemCount(everyListOriginal) % 10 != 0) && currentPage != 1) {
                            ToastUtil.showCustom("已显示全部" + getInnerItemCount(everyListOriginal));
                            adapter.changeMoreStatus(adapter.NO_LOAD_MORE);
                        } else {
                            adapter.changeMoreStatus(adapter.PULLUP_LOAD_MORE);
                        }
                    }
                });
    }


    public void getTabTitleCurrentPosition(int currentTabPosition) {
        if (currentTabPosition == 0) {
            type = "all";  //默认请求"全部"的数据
        } else if (currentTabPosition == 1) {
            type = "bankCard"; // 银行卡
        } else if (currentTabPosition == 2) {
            type = "fund";  // 工资宝
        } else if (currentTabPosition == 3) {
            type = "product"; // 定期理财
        }
    }

    public String setUserId(String userId) {
        this.userId = userId;
        return userId;
    }

    public int getInnerItemCount(List<BillCenterItemOutDataModel> everyList) {
        int count = 0;
        for (BillCenterItemOutDataModel itemOutList : everyList) {
            if (itemOutList != null && itemOutList.data != null && itemOutList.data.size() > 0) {
                count += itemOutList.data.size();
            }
        }
        return count;
    }
}
