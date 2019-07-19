package com.crecg.staffshield.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.crecg.crecglibrary.network.model.CheckVersionModelData;
import com.crecg.staffshield.R;
import com.crecg.staffshield.adapter.FoundFragmentAdapter;
import com.crecg.staffshield.dialog.CheckVersionDialog;
import com.crecg.staffshield.dialog.OpeningAccountSuccessDialog;

/**
 * Created by junde on 2018/12/15.
 */

public class FoundFragment extends Fragment implements View.OnClickListener {

    private View mView;
    private Context context;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;

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

        btn1 = mView.findViewById(R.id.btn1);
        btn2 = mView.findViewById(R.id.btn2);
        btn3 = mView.findViewById(R.id.btn3);
        btn4 = mView.findViewById(R.id.btn4);

        btn1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                showDialog();
                break;
            case R.id.btn2:
                CheckVersionModelData version = new CheckVersionModelData();
                version.display = "修复了bug";
                version.isForce = 0;
                version.url = "https://www.baidu.com/";
                showUpdateDialog(version);
                break;
        }
    }

    private void showDialog() {
        OpeningAccountSuccessDialog dialog = new OpeningAccountSuccessDialog(context);
        dialog.show();
        dialog.setMyClickListener(new OpeningAccountSuccessDialog.MyClickListener() {
            @Override
            public void onMyClick() {
                //收到dialog的点击事件
//                doSomething();
            }
        });

    }

    private void showUpdateDialog(final CheckVersionModelData version) {
        String content = TextUtils.isEmpty(version.display) ? "发现新版本,是否更新" : version.display;
        boolean isForceUpdate = version.isForce == 1;
        CheckVersionDialog dialog = new CheckVersionDialog(context, content, isForceUpdate, new CheckVersionDialog.OnCheckVersion() {
            @Override
            public void onConfirm() {
//                startDownload(version.url);
            }
        });
        dialog.show();
    }

}
