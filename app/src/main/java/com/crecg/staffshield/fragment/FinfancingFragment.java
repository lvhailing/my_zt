package com.crecg.staffshield.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.crecg.staffshield.R;

/**
 * Created by junde on 2018/12/15.
 */

public class FinfancingFragment extends Fragment {


    private View mView;
    private Context context;
    private SwipeRefreshLayout swipe_refresh;
    private ScrollView scrollView;
    private LinearLayout ll_vp;
    private LinearLayout ll_point_container;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.finfancing_fragment, container, false);
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

        scrollView = (ScrollView) mView.findViewById(R.id.scrollView);
        ll_vp = (LinearLayout) mView.findViewById(R.id.ll_vp);
        ll_point_container = (LinearLayout) mView.findViewById(R.id.ll_point_container);
    }

    /**
     * 获取首页数据
     */
    private void requestHomeData() {

    }
}
