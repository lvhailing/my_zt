package com.crecg.staffshield.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.crecg.crecglibrary.network.model.BuyFundProgressModel;
import com.crecg.crecglibrary.network.model.TransactionDetailListModel;
import com.crecg.staffshield.R;
import com.crecg.staffshield.adapter.BuyFundDetailAdapter;
import com.crecg.staffshield.adapter.RegularFinanciancialDetailAdapter;

import java.util.List;

/**
 * 理财买入成功时状态详情页
 */

public class RegularFinancingBuyStatusDetailActivity extends Activity {
    private ImageView iv_back;
    private TextView tv_common_title;
    private TextView tv_buying_amount; // 买入金额
    private RecyclerView recycler_view;
    private Button btn_complete;
    private List<TransactionDetailListModel> prodList;
    private String trsAmount;
    private RegularFinanciancialDetailAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regular_financial_management_buying_success);
        initView();
        initData();
    }

    private void initView() {
        trsAmount = getIntent().getStringExtra("trsAmount");
        prodList = (List<TransactionDetailListModel>) getIntent().getSerializableExtra("prodList");

        initTitle();

        tv_buying_amount = findViewById(R.id.tv_buying_amount);
        recycler_view = findViewById(R.id.recycler_view);
        btn_complete = findViewById(R.id.btn_complete);

        adapter = new RegularFinanciancialDetailAdapter(this, prodList);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        recycler_view.setAdapter(adapter);

        btn_complete.setOnClickListener(new View.OnClickListener() {
            Intent intent;

            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initData() {
        tv_buying_amount.setText(trsAmount); // 设置交易金额
    }

    private void initTitle() {
        iv_back = findViewById(R.id.iv_back);
        tv_common_title = findViewById(R.id.tv_common_title);

        iv_back.setVisibility(View.GONE);
        tv_common_title.setText("定期理财买入详情");
    }
}
