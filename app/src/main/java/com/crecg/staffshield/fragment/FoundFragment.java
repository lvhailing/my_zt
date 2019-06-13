package com.crecg.staffshield.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.crecg.staffshield.R;
import com.crecg.staffshield.adapter.FoundFragmentAdapter;

/**
 * Created by junde on 2018/12/15.
 */

public class FoundFragment extends Fragment {

    private View mView;
    private Context context;
    private SwipeRefreshLayout swipe_refresh;
    private LinearLayout ll_vp;
    private LinearLayout ll_point_container;
    private RecyclerView recycler_view;
//    private MouldList<PolicyRecordList2B> totalList = new MouldList<>();
    private FoundFragmentAdapter foundFragmentAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_found, container, false);
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

        ll_vp = (LinearLayout) mView.findViewById(R.id.ll_vp);
        ll_point_container = (LinearLayout) mView.findViewById(R.id.ll_point_container);
        recycler_view = (RecyclerView) mView.findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
//        foundFragmentAdapter = new FoundFragmentAdapter(getActivity(), totalList);
//        recycler_view.setAdapter(foundFragmentAdapter);
        //添加动画
        recycler_view.setItemAnimator(new DefaultItemAnimator());
    }
}
