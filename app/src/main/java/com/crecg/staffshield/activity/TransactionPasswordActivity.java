package com.crecg.staffshield.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.crecg.crecglibrary.RemoteFactory;
import com.crecg.crecglibrary.network.CommonObserverAdapter;
import com.crecg.crecglibrary.network.CommonRequestProxy;
import com.crecg.crecglibrary.network.model.BuyFundModel;
import com.crecg.crecglibrary.network.model.BuyFundProgressModel;
import com.crecg.crecglibrary.network.model.CommonResultModel;
import com.crecg.crecglibrary.network.model.HomeAndFinancialDataModel;
import com.crecg.crecglibrary.network.model.HomeAndFinancialProductItemDataModel;
import com.crecg.crecglibrary.utils.ToastUtil;
import com.crecg.crecglibrary.utils.encrypt.DESUtil;
import com.crecg.staffshield.R;
import com.crecg.staffshield.common.BaseActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xnumberkeyboard.android.OnNumberKeyboardListener;
import com.xnumberkeyboard.android.XNumberKeyboardView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 请输入交易密码
 */

public class TransactionPasswordActivity extends BaseActivity implements OnNumberKeyboardListener {

    private ImageView iv_close_keyboard;
    private ImageView iv_pass1;
    private ImageView iv_pass2;
    private ImageView iv_pass3;
    private ImageView iv_pass4;
    private ImageView iv_pass5;
    private ImageView iv_pass6;
    private XNumberKeyboardView view_keyboard;
    private List<String> transactionPwdList = new ArrayList<>();
    private TextView tv_forget_transaction_pwd;
    private String fromFlag; // wageTreasureRedeem:工资宝赎回  wageTreasureBuy:工资宝买入
    private String whereToEnterFlag; //  1:首页进   2：工资宝详情页进
    private String prodId; // 基金代码
    private String prodSubId; // 基金标识码
    private String trsAmount; // 充值金额（保留两位小数）
    private String prodName; // 基金名称

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_transaction_pwd);

        initView();
    }

    private void initView() {
        fromFlag = getIntent().getStringExtra("fromFlag");
        whereToEnterFlag = getIntent().getStringExtra("whereToEnterFlag");
        prodId = getIntent().getStringExtra("prodId");
        prodSubId = getIntent().getStringExtra("prodSubId");
        trsAmount = getIntent().getStringExtra("trsAmount");
        prodName = getIntent().getStringExtra("prodName");

        iv_close_keyboard = findViewById(R.id.iv_close_keyboard);
        view_keyboard = findViewById(R.id.view_keyboard);
        iv_pass1 = findViewById(R.id.iv_pass1);
        iv_pass2 = findViewById(R.id.iv_pass2);
        iv_pass3 = findViewById(R.id.iv_pass3);
        iv_pass4 = findViewById(R.id.iv_pass4);
        iv_pass5 = findViewById(R.id.iv_pass5);
        iv_pass6 = findViewById(R.id.iv_pass6);
        tv_forget_transaction_pwd = findViewById(R.id.tv_forget_transaction_pwd);

        view_keyboard.setOnNumberKeyboardListener(this);
        iv_close_keyboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_forget_transaction_pwd.setOnClickListener(new View.OnClickListener() { // 忘记交易密码
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TransactionPasswordActivity.this, ResetTransactionPasswordActivity.class));
            }
        });

    }

    @Override
    public void onNumberKey(int keyCode, String insert) {
        if (keyCode == XNumberKeyboardView.KEYCODE_BOTTOM_RIGHT && transactionPwdList.size() > 0) {
            // 右下角按键的点击事件，删除一位输入的文字
            transactionPwdList.remove(transactionPwdList.size() - 1);

        } else if (transactionPwdList.size() < 6) {
            transactionPwdList.add(insert);
            Log.i("hh", "insert = " + insert);
        }
        refreshVerifyCodeView();
    }

    private void refreshVerifyCodeView() {
        if (transactionPwdList.size() < 0 || transactionPwdList.size() > 6) {
            return;
        }

        iv_pass1.setVisibility(View.INVISIBLE);
        iv_pass2.setVisibility(View.INVISIBLE);
        iv_pass3.setVisibility(View.INVISIBLE);
        iv_pass4.setVisibility(View.INVISIBLE);
        iv_pass5.setVisibility(View.INVISIBLE);
        iv_pass6.setVisibility(View.INVISIBLE);

        if (transactionPwdList.size() == 1) {
            iv_pass1.setVisibility(View.VISIBLE);
        } else if (transactionPwdList.size() == 2) {
            iv_pass1.setVisibility(View.VISIBLE);
            iv_pass2.setVisibility(View.VISIBLE);
        } else if (transactionPwdList.size() == 3) {
            iv_pass1.setVisibility(View.VISIBLE);
            iv_pass2.setVisibility(View.VISIBLE);
            iv_pass3.setVisibility(View.VISIBLE);
        } else if (transactionPwdList.size() == 4) {
            iv_pass1.setVisibility(View.VISIBLE);
            iv_pass2.setVisibility(View.VISIBLE);
            iv_pass3.setVisibility(View.VISIBLE);
            iv_pass4.setVisibility(View.VISIBLE);
        } else if (transactionPwdList.size() == 5) {
            iv_pass1.setVisibility(View.VISIBLE);
            iv_pass2.setVisibility(View.VISIBLE);
            iv_pass3.setVisibility(View.VISIBLE);
            iv_pass4.setVisibility(View.VISIBLE);
            iv_pass5.setVisibility(View.VISIBLE);
        } else if (transactionPwdList.size() == 6) {
            iv_pass1.setVisibility(View.VISIBLE);
            iv_pass2.setVisibility(View.VISIBLE);
            iv_pass3.setVisibility(View.VISIBLE);
            iv_pass4.setVisibility(View.VISIBLE);
            iv_pass5.setVisibility(View.VISIBLE);
            iv_pass6.setVisibility(View.VISIBLE);

            // Todo 调后台接口成功后跳转到成功状态页

            requestData();

            // 删除键不可点
            view_keyboard.setDelBtnEnable(false);
        }

    }

    private void requestData() {
        String trsPwd = listToString(transactionPwdList);
        HashMap<String, Object> param = new HashMap<>();
        param.put("userId", "8");
        param.put("trsAmount", trsAmount); // 充值金额（保留两位小数）
        param.put("eaccountTrsPwd", trsPwd); // 交易密码  "123456 "
        param.put("prodId", prodId); // 基金代码
        param.put("prodSubId", prodSubId); // 基金标识码
        param.put("prodName", prodName); // 基金名称
        String data = DESUtil.encMap(param);

        HashMap<String, Object> paramWrapper = new HashMap<>();
        paramWrapper.put("requestKey", data);
        RemoteFactory.getInstance().getProxy(CommonRequestProxy.class)
                .getBuyFundData(paramWrapper)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CommonObserverAdapter<String>() {
            @Override
            public void onMyError() {
                ToastUtil.showCustom("首页获取数据失败");
            }

            @Override
            public void onMySuccess(String result) {
                if (result == null) {
                    return;
                }
                CommonResultModel<BuyFundModel> dataModel = new Gson().fromJson(result, new TypeToken<CommonResultModel<BuyFundModel>>() {
                }.getType());
                BuyFundModel buyFundInfoData = dataModel.data;
                if (buyFundInfoData == null) {
                    return;
                }

                String trsAmount = buyFundInfoData.trsAmount;
                List<BuyFundProgressModel> prodList = buyFundInfoData.prodList;
                if (buyFundInfoData.flag.equals("true")) {
                    if (fromFlag.equals("wageTreasureRedeem")) { // 工资宝赎回
                        Intent intent = new Intent(TransactionPasswordActivity.this, WageTreasureTurnSuccessActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else if (fromFlag.equals("wageTreasureBuy")) { // 工资宝买入（一：从工资宝详情页进的买入，二：从首页点“立即买入”进）
                        Intent intent = new Intent(TransactionPasswordActivity.this, WageTreasureTurnSuccessActivity.class);
                        intent.putExtra("whereToEnterFlag", whereToEnterFlag);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    public static String listToString(List<String> list) {

        if (list == null) {
            return null;
        }

        StringBuilder result = new StringBuilder();
        boolean first = true;

        //第一个前面不拼接","
        for (String string : list) {
            if (first) {
                first = false;
            }
//            else{
//                result.append(",");
//            }
            result.append(string);
        }
        return result.toString();
    }
}
