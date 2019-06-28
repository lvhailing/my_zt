package com.crecg.staffshield.activity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crecg.staffshield.R;
import com.crecg.staffshield.common.BaseActivity;

/**
 *  客服中心
 */

public class CustomerServiceCenterActivity extends BaseActivity {

    private LinearLayout customer_service_center_broadcast1;
    private LinearLayout customer_service_center_broadcast2;
    private LinearLayout customer_service_center_broadcast3;
    private TextView customer_service_center_more;
    private TextView customer_tv_broadcast_date1;
    private TextView customer_tv_broadcast_time1;
    private TextView customer_tv_is_news1;
    private TextView customer_tv_broadcast_content1;
    private TextView customer_tv_broadcast_date2;
    private TextView customer_tv_broadcast_time2;
    private TextView customer_tv_is_news2;
    private TextView customer_tv_broadcast_content2;
    private TextView customer_tv_broadcast_date3;
    private TextView customer_tv_broadcast_time3;
    private TextView customer_tv_is_news3;
    private TextView customer_tv_broadcast_content3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_service_center);

        initView();

    }


    private void initView() {
        customer_service_center_more = findViewById(R.id.customer_service_center_more);
        customer_tv_broadcast_date1 = (TextView) findViewById(R.id.customer_tv_broadcast_date1);
        customer_tv_broadcast_time1 = (TextView) findViewById(R.id.customer_tv_broadcast_time1);
        customer_tv_is_news1 = (TextView) findViewById(R.id.customer_tv_is_news1);
        customer_tv_broadcast_content1 = (TextView) findViewById(R.id.customer_tv_broadcast_content1);
        customer_tv_broadcast_date2 = (TextView) findViewById(R.id.customer_tv_broadcast_date2);
        customer_tv_broadcast_time2 = (TextView) findViewById(R.id.customer_tv_broadcast_time2);
        customer_tv_is_news2 = (TextView) findViewById(R.id.customer_tv_is_news2);
        customer_tv_broadcast_content2 = (TextView) findViewById(R.id.customer_tv_broadcast_content2);
        customer_tv_broadcast_date3 = (TextView) findViewById(R.id.customer_tv_broadcast_date3);
        customer_tv_broadcast_time3 = (TextView) findViewById(R.id.customer_tv_broadcast_time3);
        customer_tv_is_news3 = (TextView) findViewById(R.id.customer_tv_is_news3);
        customer_tv_broadcast_content3 = (TextView) findViewById(R.id.customer_tv_broadcast_content3);
        customer_service_center_broadcast1 = (LinearLayout) findViewById(R.id.customer_service_center_broadcast1);
        customer_service_center_broadcast2 = (LinearLayout) findViewById(R.id.customer_service_center_broadcast2);
        customer_service_center_broadcast3 = (LinearLayout) findViewById(R.id.customer_service_center_broadcast3);
    }



}
