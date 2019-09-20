package com.crecg.staffshield.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crecg.crecglibrary.RemoteFactory;
import com.crecg.crecglibrary.network.CommonObserverAdapter;
import com.crecg.crecglibrary.network.CommonRequestProxy;
import com.crecg.crecglibrary.network.model.AccountInfoModel;
import com.crecg.crecglibrary.network.model.CommonResultModel;
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
 * (工资宝)赎回至堪设联名卡
 */

public class WageTreasureRedemptionActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back;
    private TextView tv_common_title;
    private EditText et_money; // 可赎回金额
    private TextView tv_all;
    private ImageView iv1;
    private ImageView iv2;
    private Button btn_confirm_redeem; // 确认赎回
    private String flag = "n"; // n:实时赎回  y：普通到账
    private String money;
    private LinearLayout ll_up_selected;
    private LinearLayout ll_down_unselected;
    private String userProdAmount;

    private String whereToEnterFlag; // 1:首页进   2：工资宝详情页进
    private String prodId; // 基金代码
    private String prodSubId; // 基金标识码
    private String prodName; // 基金名称

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_redeemption_of_electronic_bank_card);

        initView();
        requestData();
    }

    private void initView() {
        initTitle();
        whereToEnterFlag = getIntent().getStringExtra("whereToEnterFlag");
//        prodId = getIntent().getStringExtra("prodId");
//        prodSubId = getIntent().getStringExtra("prodSubId");
//        prodName = getIntent().getStringExtra("prodName");

        et_money = findViewById(R.id.et_money);
        tv_all = findViewById(R.id.tv_all);
        iv1 = findViewById(R.id.iv1);
        iv2 = findViewById(R.id.iv2);
        ll_up_selected = findViewById(R.id.ll_up_selected);
        ll_down_unselected = findViewById(R.id.ll_down_unselected);
        btn_confirm_redeem = findViewById(R.id.btn_confirm_redeem);

        tv_all.setOnClickListener(this);
        ll_up_selected.setOnClickListener(this);
        ll_down_unselected.setOnClickListener(this);
        btn_confirm_redeem.setOnClickListener(this);
    }

    private void initTitle() {
        iv_back = findViewById(R.id.iv_back);
        tv_common_title = findViewById(R.id.tv_common_title);

        iv_back.setImageResource(R.mipmap.img_arrow_left2);
        tv_common_title.setText("赎回至堪设联名卡");

        iv_back.setOnClickListener(this);
    }

    /**
     * 请求接口数据，获取页面数据信息
     */
    private void requestData() {
        HashMap<String, Object> param = new HashMap<>();
        param.put("userId", "8");
        String data = DESUtil.encMap(param);
        HashMap<String, Object> paramWrapper = new HashMap<>();
        paramWrapper.put("requestKey", data);
        RemoteFactory.getInstance().getProxy(CommonRequestProxy.class)
                .getAccountInfoData(paramWrapper).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new CommonObserverAdapter<String>() {
            @Override
            public void onMyError() {
                ToastUtil.showCustom("获取数据失败");
            }

            @Override
            public void onMySuccess(String result) {
                if (result == null) {
                    return;
                }
                CommonResultModel<AccountInfoModel> dataModel = new Gson().fromJson(result, new TypeToken<CommonResultModel<AccountInfoModel>>() {
                }.getType());
                AccountInfoModel accountInfoData = dataModel.data;
                if (dataModel.data == null) {
                    return;
                }
                userProdAmount = accountInfoData.userProdAmount;
                et_money.setHint("可赎回" + userProdAmount);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_all: // 全部
                et_money.setText(userProdAmount);
                break;
            case R.id.ll_up_selected:
                if ("y".equals(flag)) { // 普通到账
                    iv1.setBackgroundResource(R.mipmap.img_redeem_selected);
                    iv2.setBackgroundResource(R.mipmap.img_redeem_unselected);
                    flag = "n";
                }
                break;
            case R.id.ll_down_unselected:
                if ("n".equals(flag)) { // 实时赎回
                    iv2.setBackgroundResource(R.mipmap.img_redeem_selected);
                    iv1.setBackgroundResource(R.mipmap.img_redeem_unselected);
                    flag = "y";
                }
                break;

            case R.id.btn_confirm_redeem: // 确认赎回
                money = et_money.getText().toString();
                if (TextUtils.isEmpty(money)) {
                    ToastUtil.showCustom("赎回金额不能为空");
                    return;
                }
                Intent intent = new Intent(WageTreasureRedemptionActivity.this, TransactionPasswordActivity.class);
                intent.putExtra("fromFlag", "wageTreasureRedeem"); // 表示工资宝的赎回
                intent.putExtra("whereToEnterFlag", whereToEnterFlag);
                intent.putExtra("trsAmount", money); // 工资宝赎回的金额
                intent.putExtra("redemptionFlag", flag); // 工资宝赎回方式
                startActivity(intent);
                finish();
                break;
        }
    }


}
