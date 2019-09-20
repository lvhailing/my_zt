package com.crecg.staffshield.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.crecg.crecglibrary.RemoteFactory;
import com.crecg.crecglibrary.network.CommonObserverAdapter;
import com.crecg.crecglibrary.network.CommonRequestProxy;
import com.crecg.crecglibrary.network.model.CommonResultModel;
import com.crecg.crecglibrary.network.model.FundBillDetailDataModel;
import com.crecg.crecglibrary.network.model.TransactionDetailListModel;
import com.crecg.crecglibrary.utils.ToastUtil;
import com.crecg.crecglibrary.utils.encrypt.DESUtil;
import com.crecg.staffshield.R;
import com.crecg.staffshield.adapter.FundDetailAdapter;
import com.crecg.staffshield.common.BaseActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 基金账单详情（包含买入与赎回）
 */

public class FundBillDetailActivity extends BaseActivity {

    private ImageView iv_back;
    private TextView tv_common_title;
    private TextView tv_title_category;
    private TextView tv_transfer_amount;
    private RecyclerView recycler_view; // 显示进度的列表
    private TextView tv_turn_into_or_redeem;
    private TextView tv_content_detail;
    private TextView tv_creation_date; // 创建时间
    private TextView tv_flow_num; // 流水号
    private String type;
    private String transId;  // 渠道流水号（充值或提现成功后返回）
    private ArrayList<TransactionDetailListModel> totalList = new ArrayList<>();
    private List<TransactionDetailListModel> everyList;
    private FundDetailAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_fund_bill_detail);

        initView();
        requestData();
    }

    private void initView() {
        initTitle();

        type = getIntent().getStringExtra("type");
        transId = getIntent().getStringExtra("transId");
        Log.i("hh", "transId = " + transId);

        tv_title_category = findViewById(R.id.tv_title_category);
        tv_transfer_amount = findViewById(R.id.tv_transfer_amount);
        recycler_view = findViewById(R.id.recycler_view);
        tv_turn_into_or_redeem = findViewById(R.id.tv_turn_into_or_redeem);
        tv_content_detail = findViewById(R.id.tv_content_detail);
        tv_creation_date = findViewById(R.id.tv_creation_date);
        tv_flow_num = findViewById(R.id.tv_flow_num);

        initRecyclerView();
    }

    private void initTitle() {
        iv_back = findViewById(R.id.iv_back);
        tv_common_title = findViewById(R.id.tv_common_title);
        iv_back.setImageResource(R.mipmap.img_arrow_left2);
        tv_common_title.setText("账单详情");

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initRecyclerView() {
        //设置进度
        adapter = new FundDetailAdapter(this, totalList);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        recycler_view.setAdapter(adapter);
    }

    /**
     * 获取银行卡提现或充值详情数据
     */
    private void requestData() {
        HashMap<String, Object> param = new HashMap<>();
        param.put("userId", "26");
        param.put("oriJnlNo", transId);
        String data = DESUtil.encMap(param);

        HashMap<String, Object> paramWrapper = new HashMap<>();
        paramWrapper.put("requestKey", data);
        RemoteFactory.getInstance().getProxy(CommonRequestProxy.class)
                .getFundDetailData(paramWrapper)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CommonObserverAdapter<String>() {
            @Override
            public void onMyError() {
                ToastUtil.showCustom("基金账单详情数据获取失败");
            }

            @Override
            public void onMySuccess(String result) {
                if (result == null) {
                    return;
                }
                CommonResultModel<FundBillDetailDataModel> fundDetailDataModel = new Gson().fromJson(result, new TypeToken<CommonResultModel<FundBillDetailDataModel>>() {
                }.getType());
                if (fundDetailDataModel == null) {
                    return;
                }
                FundBillDetailDataModel fundDetail = fundDetailDataModel.data;
                setData(fundDetail);
            }
        });
    }

    private void setData(FundBillDetailDataModel data) {
        String flag = data.flag;
        if ("true".equals(flag)) {
            String type = data.type;
            if ("RT04".equals(type)) {
                tv_title_category.setText("勘设联名卡-工资宝");
            } else if ("RT06".equals(type)) {
                tv_title_category.setText("工资宝-勘设联名卡");
            }
        }
        everyList = data.prodList;
        if (everyList == null) {
            return;
        }
        totalList.addAll(everyList);
        adapter.notifyDataSetChanged();

        if (type.equals("XYSG")) { // 基金买入
            tv_turn_into_or_redeem.setText("转入到");
        } else if (type.equals("XYSH")) { // 基金赎回
            tv_turn_into_or_redeem.setText("赎回到");
        }
        tv_content_detail.setText(data.productName); //
        tv_transfer_amount.setText(data.trsAmount); // 交易金额
        tv_creation_date.setText(data.createTime); // 创建时间
        tv_flow_num.setText(data.oriJnlNo); // 流水号
    }
}
