package com.crecg.staffshield.activity;

import android.os.Bundle;

import com.crecg.staffshield.R;
import com.crecg.staffshield.common.BaseActivity;
import com.crecg.staffshield.widget.TitleBar;

/**
 *  客服中心
 * Created by junde on 2018/12/21.
 */

public class CustomerServiceCenterActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_service_center_activity);

        initTopTitle();
        initView();

    }

    private void initTopTitle() {
        TitleBar title = (TitleBar) findViewById(R.id.rl_title);
        title.setTitle(getResources().getString(R.string.title_null)).setLogo(R.mipmap.ic_launcher, false)
             .setIndicator(R.mipmap.title_icon_back).setCenterText(getResources().getString(R.string.title_customer_service_center))
             .showMore(false).setOnActionListener(new TitleBar.OnActionListener() {

            @Override
            public void onMenu(int id) {
            }

            @Override
            public void onBack() {
                finish();
            }

            @Override
            public void onAction(int id) {

            }
        });
    }

    private void initView() {

    }
}
