package com.crecg.staffshield.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.crecg.crecglibrary.network.model.ProductModelTestData;
import com.crecg.staffshield.R;
import com.crecg.staffshield.adapter.RegularFinancialListAdapter;
import com.crecg.staffshield.common.BaseActivity;

import java.util.ArrayList;

/**
 * 定期理财 列表
 * Created by junde on 2019/7/10.
 */

public class RegularFinancialManagementListActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back;
    private TextView tv_common_title;

    private RecyclerView recycler_view;
    private RegularFinancialListAdapter regularFinancialListAdapter;
    private ArrayList<ProductModelTestData> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_regular_financial_management_list);

        initData();
        initView();
    }

    private void initView() {
        iv_back = findViewById(R.id.iv_back);
        tv_common_title = findViewById(R.id.tv_common_title);
        iv_back.setImageResource(R.mipmap.img_arrow_left2);
        tv_common_title.setText("定期理财");

        recycler_view = findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        regularFinancialListAdapter = new RegularFinancialListAdapter(this, list);
        recycler_view.setAdapter(regularFinancialListAdapter);


        iv_back.setOnClickListener(this);
    }

    private void initData() {
        // 模拟数据
        ProductModelTestData product1 = new ProductModelTestData();
        product1.annualizedReturn = "6.12%";
        product1.date = "2019-06-04 14:00";
        product1.day = "22";
        product1.name = "中铁1号";
        product1.investmentAmount = "10万起投";
        product1.progressBar = 25;
        product1.surplusMoney = "2200000";
        product1.flag = 1;

        ProductModelTestData product2 = new ProductModelTestData();
        product2.annualizedReturn = "6.22%";
        product2.date = "2029-06-04 24:00";
        product2.day = "22";
        product2.name = "中铁2号";
        product2.investmentAmount = "20万起投";
        product2.flag = 2;

        ProductModelTestData product3 = new ProductModelTestData();
        product3.annualizedReturn = "6.32%";
        product3.date = "2039-06-04 34:00";
        product3.day = "22";
        product3.name = "中铁3号";
        product3.investmentAmount = "30万起投";
        product3.flag = 3;

        ProductModelTestData product4 = new ProductModelTestData();
        product4.annualizedReturn = "6.32%";
        product4.date = "2039-06-04 34:00";
        product4.day = "22";
        product4.name = "中铁4号";
        product4.investmentAmount = "50万起投";
        product4.flag = 4;

        ProductModelTestData product5 = new ProductModelTestData();
        product5.annualizedReturn = "6.32%";
        product5.date = "2039-06-04 34:00";
        product5.day = "22";
        product5.name = "中铁5号";
        product5.investmentAmount = "70万起投";
        product5.flag = 5;

        ProductModelTestData product6 = new ProductModelTestData();
        product6.annualizedReturn = "4.72%";
        product6.date = "2019-07-11 10:00";
        product6.day = "180";
        product6.name = "中铁6号";
        product6.investmentAmount = "10万起投";
        product6.progressBar = 85;
        product6.surplusMoney = "120000";
        product6.flag = 1;

        list = new ArrayList<>();
        list.add(product1);
        list.add(product2);
        list.add(product3);
        list.add(product4);
        list.add(product5);
        list.add(product6);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
