package com.crecg.staffshield.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.crecg.staffshield.R;
import com.crecg.staffshield.activity.CustomerServiceCenterActivity;

/**
 * Created by junde on 2018/12/15.
 */

public class MeFragment extends Fragment implements View.OnClickListener {

    private View mView;
    private RelativeLayout me_rl_customer_service_center; // 客服中心
    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.me_fragment, container, false);
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
        me_rl_customer_service_center = mView.findViewById(R.id.me_rl_customer_service_center);

        me_rl_customer_service_center.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.me_rl_customer_service_center:
                Intent intent = new Intent(context, CustomerServiceCenterActivity.class);
                startActivity(intent);
                break;
        }
    }
}
