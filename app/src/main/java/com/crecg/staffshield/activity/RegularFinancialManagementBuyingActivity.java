package com.crecg.staffshield.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crecg.crecglibrary.RemoteFactory;
import com.crecg.crecglibrary.network.CommonObserverAdapter;
import com.crecg.crecglibrary.network.CommonRequestProxy;
import com.crecg.crecglibrary.network.model.AccountInfoModel;
import com.crecg.crecglibrary.network.model.CommonResultModel;
import com.crecg.crecglibrary.network.model.RegularFinacialProductDataModel;
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
 * 定期理财买入
 */

public class RegularFinancialManagementBuyingActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back;
    private TextView tv_common_title;
    private TextView tv_money_amount; //账户余额
    private EditText et_all_money_amount; // 购买金额
    private TextView tv_transfer_of_funds; // 资金转入
    private TextView tv_all; // 全部
    private TextView tv_incremental_amount; // 递增金额1万元
    private TextView tv_surplus_amount; //剩余可购买金额201万元
    private RelativeLayout rl_agree; // 选框
    private TextView tv_about_agreement; // 相关协议
    private Button btn_buy; // 确认买入
    private ImageView iv_selected_or_unselected; // 相关协议未选中状态
    private String whereToEnterFlag; // 1:首页进   2：工资宝详情页进

    private boolean isCheckedFlag = false;
    private String title;
    private String productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regular_financial_management_buying);

        initView();
        requestProductTenderInfoData();
    }

    private void initView() {
        title = getIntent().getStringExtra("title");
        productId = getIntent().getStringExtra("productId");

        setTitle();

        tv_money_amount = findViewById(R.id.tv_money_amount);
        et_all_money_amount = findViewById(R.id.et_all_money_amount);
        tv_transfer_of_funds = findViewById(R.id.tv_transfer_of_funds);
        tv_incremental_amount = findViewById(R.id.tv_incremental_amount);
        tv_all = findViewById(R.id.tv_all);
        tv_surplus_amount = findViewById(R.id.tv_surplus_amount);
        rl_agree = findViewById(R.id.rl_agree);
        tv_about_agreement = findViewById(R.id.tv_about_agreement);
        iv_selected_or_unselected = findViewById(R.id.iv_selected_or_unselected);
        btn_buy = findViewById(R.id.btn_buy);

        tv_money_amount.setText("201222.00");
        iv_back.setOnClickListener(this);
        tv_transfer_of_funds.setOnClickListener(this);
        tv_all.setOnClickListener(this);
        rl_agree.setOnClickListener(this);
        tv_about_agreement.setOnClickListener(this);
        btn_buy.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }

    private void setTitle() {
        iv_back = findViewById(R.id.iv_back);
        tv_common_title = findViewById(R.id.tv_common_title);

        iv_back.setImageResource(R.mipmap.img_arrow_left2);
        tv_common_title.setText(title); // Todo 需要动态获取标题
    }

    /**
     * 获取当前要买的理财产品信息
     */
    private void requestProductTenderInfoData() {
        HashMap<String, Object> param = new HashMap<>();
        param.put("userId", "8");
        param.put("productId", productId);
        String data = DESUtil.encMap(param);
        HashMap<String, Object> paramWrapper = new HashMap<>();
        paramWrapper.put("requestKey", data);
        RemoteFactory.getInstance().getProxy(CommonRequestProxy.class)
                .getProductTenderInfoData(paramWrapper)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CommonObserverAdapter<String>() {
            @Override
            public void onMyError() {
                ToastUtil.showCustom("获取当前要买的理财产品数据失败");
            }

            @Override
            public void onMySuccess(String result) {
                if (result == null) {
                    return;
                }
                CommonResultModel<RegularFinacialProductDataModel> dataModel = new Gson().fromJson(result, new TypeToken<CommonResultModel<RegularFinacialProductDataModel>>() {
                }.getType());
                RegularFinacialProductDataModel finacialProductInfoData = dataModel.data;
                if (finacialProductInfoData == null) {
                    return;
                }
                tv_money_amount.setText(finacialProductInfoData.localDepositAvailbal); // 账户余额

                et_all_money_amount.setHint(finacialProductInfoData.tenderInitAmount + "万起投，单笔限购" + finacialProductInfoData.highSingleInvest + "万");

                String str1 = "递增金额";
                String str2 = finacialProductInfoData.tenderIncreaseAmount;
                String str3 = "万元";
                String total = str1 + str2 + str3;
                SpannableString titleString = new SpannableString(total);
                titleString.setSpan(new ForegroundColorSpan(Color.parseColor("#999999")), 0, 4, Spanned.SPAN_INCLUSIVE_INCLUSIVE); // 即包括开始下标，又包括结束下标
                titleString.setSpan(new ForegroundColorSpan(Color.parseColor("#333333")), 4, str2.length() + 4, Spanned.SPAN_EXCLUSIVE_INCLUSIVE); // 不包括开始下标，但包括结束下标
                titleString.setSpan(new ForegroundColorSpan(Color.parseColor("#999999")), total.length() - 2, total.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE); // 不包括开始下标，但包括结束下标
                tv_incremental_amount.setText(titleString);

                String str4 = "剩余可购买金额";
                Log.i("hh", "str4.length = " + str4.length());
                String str5 = finacialProductInfoData.syAmount;
                Log.i("hh", "str5.length = " + str5.length());
                String str6 = "万元";
                String total2 = str4 + str5 + str6;
                Log.i("hh", "total2.length = " + total2.length() + " --- total2 = " + total2);

                SpannableString titleString2 = new SpannableString(total2);
                titleString2.setSpan(new ForegroundColorSpan(Color.parseColor("#999999")), 0, 7, Spanned.SPAN_INCLUSIVE_INCLUSIVE); // 即包括开始下标，又包括结束下标
                titleString2.setSpan(new ForegroundColorSpan(Color.parseColor("#333333")), 7, str5.length() + 7, Spanned.SPAN_INCLUSIVE_INCLUSIVE); // 不包括开始下标，但包括结束下标
                titleString2.setSpan(new ForegroundColorSpan(Color.parseColor("#999999")), total2.length() - 2, total2.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE); // 不包括开始下标，但包括结束下标
                tv_surplus_amount.setText(titleString2);

            }
        });
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_transfer_of_funds: // 资金转入
                intent = new Intent(RegularFinancialManagementBuyingActivity.this, EntityBankToElectronicBankActivity.class);
                intent.putExtra("fromFlag", "regular");
                startActivity(intent);
                break;
            case R.id.tv_all: // 全部
                et_all_money_amount.setText(tv_money_amount.getText());
                break;
            case R.id.rl_agree: // 是否勾选相关协议
                if (isCheckedFlag) {
                    iv_selected_or_unselected.setBackgroundResource(R.mipmap.img_unselected);
                    isCheckedFlag = false;
                } else {
                    iv_selected_or_unselected.setBackgroundResource(R.mipmap.img_seleced);
                    isCheckedFlag = true;
                }
                break;
            case R.id.tv_about_agreement: // 相关协议
                break;
            case R.id.btn_buy: // 确认买入
                String moneyAmount = et_all_money_amount.getText().toString();
                if (TextUtils.isEmpty(moneyAmount)) {
                    ToastUtil.showCustom("买入金额不能为空");
                    return;
                }
                if (!isCheckedFlag) {
                    ToastUtil.showCustom("请勾选协议");
                    return;
                }
                intent = new Intent(this, TransactionPasswordActivity.class);
                intent.putExtra("fromFlag", "regularFinancialBuy");
                intent.putExtra("productId", productId);
                intent.putExtra("trsAmount", moneyAmount);
//                intent.putExtra("whereToEnterFlag", 4);
                startActivity(intent);
                finish();

                break;
        }
    }
}
