package com.crecg.staffshield.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.crecg.crecglibrary.network.model.BuyFundProgressModel;
import com.crecg.crecglibrary.network.model.TransactionDetailListModel;
import com.crecg.staffshield.R;
import com.crecg.staffshield.adapter.BuyFundDetailAdapter;
import com.crecg.staffshield.common.BaseActivity;

import java.util.List;

/**
 * 工资宝买入状态详情页
 * Created by junde on 2019/7/2.
 */

public class WageTreasureTurnSuccessActivity extends BaseActivity {

    private ImageView iv_back;
    private TextView tv_common_title;
    private TextView tv_title_category;
    private TextView tv_transfer_amount;
    private TextView tv_transaction_type_title;
    private TextView btn_complete; //完成
    private RecyclerView recycler_view;
    private BuyFundDetailAdapter adapter;

    private String fromFlag; // wageTreasureRedeem:工资宝赎回     wageTreasureBuy:工资宝买入
    private String whereToEnterFlag; //  1:首页进   2：工资宝详情页进
    private String trsAmount;
    private List<TransactionDetailListModel> prodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_wage_treasure_turn_success);

        initView();
        initData();
    }

    private void initView() {
        fromFlag = getIntent().getStringExtra("fromFlag");
        whereToEnterFlag = getIntent().getStringExtra("whereToEnterFlag");
        trsAmount = getIntent().getStringExtra("trsAmount");
        prodList = (List<TransactionDetailListModel>) getIntent().getSerializableExtra("prodList");

        initTitle();

        tv_title_category = findViewById(R.id.tv_title_category);
        tv_transfer_amount = findViewById(R.id.tv_transfer_amount);
        tv_transaction_type_title = findViewById(R.id.tv_transaction_type_title);
        recycler_view = findViewById(R.id.recycler_view);
        btn_complete = findViewById(R.id.btn_complete);

        adapter = new BuyFundDetailAdapter(this, prodList);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        recycler_view.setAdapter(adapter);

        btn_complete.setOnClickListener(new View.OnClickListener() {
            Intent intent;

            @Override
            public void onClick(View v) {
                if ("1".equals(whereToEnterFlag)) { // 表示从首页工资宝进
                    intent = new Intent(WageTreasureTurnSuccessActivity.this, MainActivity.class);
                    intent.putExtra("goBackFlag", "1"); // 返回到首页
                    startActivity(intent);
                    finish();
                } else if ("2".equals(whereToEnterFlag)) { // 表示从工资宝详情页进
//                    intent = new Intent(WageTreasureTurnSuccessActivity.this, SalaryTreasureDetailWebActivity.class);
//                    intent.putExtra("goBackFlag", "2"); // 返回到工资宝详情页
                    finish();
                }

            }
        });

    }

    private void initData() {
        if (fromFlag.equals("wageTreasureBuy")) { // 工资宝买入
            tv_title_category.setText("转入金额（元）");
            tv_transaction_type_title.setText("转入进度");
        } else if (fromFlag.equals("wageTreasureRedeem")) { // 工资宝赎回
            tv_title_category.setText("赎回金额（元）");
            tv_transaction_type_title.setText("赎回进度");
        }

        tv_transfer_amount.setText(trsAmount); // 设置交易金额
    }

    private void initTitle() {
        iv_back = findViewById(R.id.iv_back);
        tv_common_title = findViewById(R.id.tv_common_title);

        iv_back.setVisibility(View.GONE);
        if (fromFlag.equals("wageTreasureBuy")) { // 工资宝买入
            tv_common_title.setText("工资宝转入详情");
        } else if (fromFlag.equals("wageTreasureRedeem")) { // 工资宝赎回
            tv_common_title.setText("工资宝赎回详情");
        }
    }
}
