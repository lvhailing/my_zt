package com.crecg.staffshield.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.crecg.crecglibrary.RemoteFactory;
import com.crecg.crecglibrary.network.CommonObserverAdapter;
import com.crecg.crecglibrary.network.CommonRequestProxy;
import com.crecg.crecglibrary.network.model.BankBillDetailDataModel;
import com.crecg.crecglibrary.network.model.CommonResultModel;
import com.crecg.crecglibrary.network.model.MyDataModel;
import com.crecg.crecglibrary.utils.ToastUtil;
import com.crecg.crecglibrary.utils.encrypt.DESUtil;
import com.crecg.staffshield.R;
import com.crecg.staffshield.common.BaseActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 *  银行卡账单详情（包含转入与转出）
 * Created by junde on 2019/8/26.
 */

public class BankBillDetailActivity extends BaseActivity {

    private ImageView iv_back;
    private TextView tv_common_title;
    private TextView tv_title_category;
    private TextView tv_transfer_amount;
    private ImageView iv_top_circle;
    private ImageView iv_top_line;
    private TextView tv_top_title;
    private TextView tv_top_date;
    private TextView tv_down_title;
    private TextView tv_down_date;
    private ImageView iv_down_line;
    private ImageView iv_down_circle;
    private TextView tv_bank_name;
    private TextView tv_creation_date;
    private TextView tv_bank_flow_num;
    private String type; // 交易类型
    private String transId;  // 渠道流水号（充值或提现成功后返回）
    private BankBillDetailDataModel billDetailData;
    private TextView tv_turn_into_or_out; // 转出到or转入到


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_bank_bill_detail);

        initView();
        requestData();
    }

    private void initView() {
        initTitle();

        type = getIntent().getStringExtra("type");
        transId = getIntent().getStringExtra("transId");
        Log.i("hh","transId = " + transId);
        tv_title_category = findViewById(R.id.tv_title_category);
        tv_transfer_amount = findViewById(R.id.tv_transfer_amount);
        iv_top_circle = findViewById(R.id.iv_top_circle);
        iv_top_line = findViewById(R.id.iv_top_line);
        tv_top_title = findViewById(R.id.tv_top_title);
        tv_top_date = findViewById(R.id.tv_top_date);
        iv_down_line = findViewById(R.id.iv_down_line);
        iv_down_circle = findViewById(R.id.iv_down_circle);
        tv_down_title = findViewById(R.id.tv_down_title);
        tv_down_date = findViewById(R.id.tv_down_date);
        tv_turn_into_or_out = findViewById(R.id.tv_turn_into_or_out);
        tv_bank_name = findViewById(R.id.tv_bank_name);
        tv_creation_date = findViewById(R.id.tv_creation_date);
        tv_bank_flow_num = findViewById(R.id.tv_bank_flow_num);
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

    /**
     *  获取银行卡提现或充值详情数据
     */
    private void requestData() {
        HashMap<String, Object> param = new HashMap<>();
        param.put("userId", "26");
        param.put("oriJnlNo", transId);
        String data = DESUtil.encMap(param);

        HashMap<String, Object> paramWrapper = new HashMap<>();
        paramWrapper.put("requestKey", data);
        RemoteFactory.getInstance().getProxy(CommonRequestProxy.class)
                .getRechargeWithdrawalData(paramWrapper)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CommonObserverAdapter<String>() {
                    @Override
                    public void onMyError() {
                        ToastUtil.showCustom("银行卡账单详情数据获取失败");
                    }

                    @Override
                    public void onMySuccess(String result) {
                        if (result == null) {
                            return;
                        }
                        CommonResultModel<BankBillDetailDataModel> billDetailDataModel = new Gson().fromJson(result, new TypeToken<CommonResultModel<BankBillDetailDataModel>>() {
                        }.getType());
                        if (billDetailDataModel == null) {
                            return;
                        }
                        billDetailData = billDetailDataModel.data;
                        setData(billDetailData);
                    }
                });
    }

    private void setData(BankBillDetailDataModel billDetailData) {
        String flag = billDetailData.flag;
        if ("true".equals(flag)) {
           String type = billDetailData.trsType;
           String bankName = billDetailData.bankName;
            if (type.equals("RCGI")) { // 充值（即从银行卡转钱到联名卡）
                tv_title_category.setText(bankName + "-勘设联名卡");
                tv_turn_into_or_out.setText("转入到");
                iv_top_circle.setBackground(getResources().getDrawable(R.mipmap.img_check_mark));
                iv_top_line.setBackground(getResources().getDrawable(R.color.main_blue_4A67F5));
                tv_top_title.setText("转入申请已提交，银行处理中");
                tv_top_date.setText(billDetailData.submitTime);
                if ("0".equals(billDetailData.tradeStatus)) { // 交易成功
                    iv_down_line.setBackground(getResources().getDrawable(R.color.main_blue_4A67F5));
                    iv_down_circle.setBackground(getResources().getDrawable(R.mipmap.img_check_mark));
                    tv_down_title.setText("转入成功");
                    tv_down_title.setTextColor(getResources().getColor(R.color.main_blue_4A67F5));
                    tv_down_date.setText("实时到账");
                } else if ("1".equals(billDetailData.tradeStatus)) { // 交易失败
                    iv_down_line.setBackground(getResources().getDrawable(R.color.bg_line));
                    iv_down_circle.setBackground(getResources().getDrawable(R.mipmap.img_circle_gray));
                    tv_down_title.setText("转入失败");
                    tv_down_date.setText(billDetailData.trsEndTime);
                }else if ("2".equals(billDetailData.tradeStatus)) { // 交易正在进行中
                    iv_down_line.setBackground(getResources().getDrawable(R.color.bg_line));
                    iv_down_circle.setBackground(getResources().getDrawable(R.mipmap.img_circle_gray));
                    tv_down_title.setText("预计到账时间");
                    tv_down_date.setText("实时到账");
                }
            } else if(type.equals("WTHI")){ // 提现（即从联名卡转出到银行卡）
                tv_title_category.setText("勘设联名卡-" + bankName);
                tv_turn_into_or_out.setText("转出到");
                iv_top_circle.setBackground(getResources().getDrawable(R.mipmap.img_check_mark));
                iv_top_line.setBackground(getResources().getDrawable(R.color.main_blue_4A67F5));
                tv_top_title.setText("转出申请已提交，银行处理中");
                tv_top_date.setText(billDetailData.submitTime);
                if ("0".equals(billDetailData.tradeStatus)) { // 交易成功
                    iv_down_line.setBackground(getResources().getDrawable(R.color.main_blue_4A67F5));
                    iv_down_circle.setBackground(getResources().getDrawable(R.mipmap.img_check_mark));
                    tv_down_title.setText("转出成功");
                    tv_down_title.setTextColor(getResources().getColor(R.color.main_blue_4A67F5));
                    tv_down_date.setText("实时到账");
                } else if ("1".equals(billDetailData.tradeStatus)) { // 交易失败
                    iv_down_line.setBackground(getResources().getDrawable(R.color.bg_line));
                    iv_down_circle.setBackground(getResources().getDrawable(R.mipmap.img_circle_gray));
                    tv_down_title.setText("转出失败");
                    tv_down_date.setText(billDetailData.trsEndTime);
                }else if ("2".equals(billDetailData.tradeStatus)) { // 交易正在进行中
                    iv_down_line.setBackground(getResources().getDrawable(R.color.bg_line));
                    iv_down_circle.setBackground(getResources().getDrawable(R.mipmap.img_circle_gray));
                    tv_down_title.setText("预计到账时间");
                    tv_down_date.setText("实时到账");
                }
            }
            tv_transfer_amount.setText(billDetailData.trsAmount); // 交易金额
            tv_bank_name.setText(billDetailData.bankName); // 银行名称
            tv_creation_date.setText(billDetailData.createTime); // 创建时间
            tv_bank_flow_num.setText(billDetailData.oriJnlNo); // 流水号
        }
    }
}
