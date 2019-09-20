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
import com.crecg.crecglibrary.network.model.CashWithdrawalDataModel;
import com.crecg.crecglibrary.network.model.CommonResultModel;
import com.crecg.crecglibrary.network.model.RechargeDataModel;
import com.crecg.crecglibrary.network.model.RegularFinancialBuyModel;
import com.crecg.crecglibrary.network.model.TransactionDetailListModel;
import com.crecg.crecglibrary.utils.ToastUtil;
import com.crecg.crecglibrary.utils.encrypt.DESUtil;
import com.crecg.staffshield.R;
import com.crecg.staffshield.common.BaseActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xnumberkeyboard.android.OnNumberKeyboardListener;
import com.xnumberkeyboard.android.XNumberKeyboardView;

import java.io.Serializable;
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

    /**
     * fromFlag:  根据不同标志获取不同的接口数据
     *
     *  wageTreasureRedeem:工资宝赎回
     *  wageTreasureBuy:工资宝买入
     *  recharge:充值
     *  cashWithdrawal:提现
     *  regularFinancialBuy:理财买入
     */
    private String fromFlag;
    private String whereToEnterFlag; //  1:首页进   2：工资宝详情页进   3:充值页面进 4.定期理财买入页进
    private String trsAmount; // 充值金额（保留两位小数）
    private String redemptionFlag; // 工资宝赎回方式  n:实时赎回  y：普通到账
    private String flag;
    private Intent intent;
    private String productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_transaction_pwd);

        initView();
    }

    private void initView() {
        fromFlag = getIntent().getStringExtra("fromFlag");
        whereToEnterFlag = getIntent().getStringExtra("whereToEnterFlag");
        trsAmount = getIntent().getStringExtra("trsAmount");
        redemptionFlag = getIntent().getStringExtra("redemptionFlag");
        flag = getIntent().getStringExtra("flag");
        productId = getIntent().getStringExtra("productId");

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

            if (fromFlag.equals("wageTreasureRedeem")) { // 工资宝赎回
                requestFundRedemptionData();
            } else if (fromFlag.equals("wageTreasureBuy")) { // 工资宝买入（一：从工资宝详情页进的买入，二：从首页点“立即买入”进）
                requestData();
            } else if (fromFlag.equals("recharge") || fromFlag.equals("me") || fromFlag.equals("regular")) { // 表示从充值页面进，调充值接口
                requestRechargeData();
            } else if (fromFlag.equals("cashWithdrawal")) { // 提现
                requestCashWithdrawalData();
            } else if (fromFlag.equals("regularFinancialBuy")) { // 定期理财买入
                requestRegularFinancialBuy();
            }

            // 删除键不可点
            view_keyboard.setDelBtnEnable(false);

        }

    }

    /**
     * 获取充值接口数据
     */
    private void requestRechargeData() {
        String trsPwd = listToString(transactionPwdList);
        HashMap<String, Object> param = new HashMap<>();
        param.put("userId", "8");
        param.put("trsAmount", trsAmount); // 充值金额
        param.put("eacTrsPwd", trsPwd); // 交易密码  "123456 "
        String data = DESUtil.encMap(param);

        HashMap<String, Object> paramWrapper = new HashMap<>();
        paramWrapper.put("requestKey", data);
        RemoteFactory.getInstance().getProxy(CommonRequestProxy.class)
                .getRechargeData(paramWrapper)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CommonObserverAdapter<String>() {
                    @Override
                    public void onMyError() {
                        ToastUtil.showCustom("充值接口获取数据失败");
                    }

                    @Override
                    public void onMySuccess(String result) {
                        if (result == null) {
                            return;
                        }
                        CommonResultModel<RechargeDataModel> dataModel = new Gson().fromJson(result, new TypeToken<CommonResultModel<RechargeDataModel>>() {
                        }.getType());
                        RechargeDataModel rechargeData = dataModel.data;
                        if (rechargeData == null) {
                            return;
                        }
                        if (rechargeData.flag.equals("true")) {

                            if (fromFlag.equals("recharge")) { // 充值成功跳回工资宝买入页面继续工资宝的买入操作
                                intent = new Intent(TransactionPasswordActivity.this, WageTreasureBuyingActivity.class);
                                intent.putExtra("whereToEnterFlag", "3");
                            } else if (fromFlag.equals("me")) { // 表示从我的页面进入,
                                intent = new Intent(TransactionPasswordActivity.this, RechargeAndCashWithdrawalDetailActivity.class);
                                intent.putExtra("type", rechargeData.trsType);
                            } else if (fromFlag.equals("regular")) { // 表示从定期理财
                                intent = new Intent(TransactionPasswordActivity.this,RegularFinancialManagementBuyingActivity.class );
                            }
                                startActivity(intent);
                                finish();

                        } else {
                            ToastUtil.showCustom(rechargeData.message);
                        }
                    }
                });
    }

    /**
     *  获取提现接口数据
     */
    private void requestCashWithdrawalData() {
        String trsPwd = listToString(transactionPwdList);
        HashMap<String, Object> param = new HashMap<>();
        param.put("userId", "26");
        param.put("trsAmount", trsAmount); // 提现金额
        param.put("eaccountTrsPwd", trsPwd); // 交易密码  "123456 "
        String data = DESUtil.encMap(param);

        HashMap<String, Object> paramWrapper = new HashMap<>();
        paramWrapper.put("requestKey", data);
        RemoteFactory.getInstance().getProxy(CommonRequestProxy.class)
                .getCashWithdrawalData(paramWrapper)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CommonObserverAdapter<String>() {
                    @Override
                    public void onMyError() {
                        ToastUtil.showCustom("提现接口获取数据失败");
                    }

                    @Override
                    public void onMySuccess(String result) {
                        if (result == null) {
                            return;
                        }
                        CommonResultModel<CashWithdrawalDataModel> dataModel = new Gson().fromJson(result, new TypeToken<CommonResultModel<CashWithdrawalDataModel>>() {
                        }.getType());
                        CashWithdrawalDataModel cashWithdrawalData = dataModel.data;
                        if (cashWithdrawalData == null) {
                            return;
                        }
                        if (cashWithdrawalData.flag.equals("true")) {// 提现成功跳转联名卡转出详情页
                            Intent intent = new Intent(TransactionPasswordActivity.this, RechargeAndCashWithdrawalDetailActivity.class);
//                            Bundle bundle=new Bundle();
//                            bundle.putSerializable("CashWithdrawalDataModel",cashWithdrawalData);//序列化,要注意转化(Serializable)
//                            intent.putExtras(bundle);//发送数据

                            intent.putExtra("trsAmount",cashWithdrawalData.trsAmount ); // 转出金额
                            intent.putExtra("submitTime", cashWithdrawalData.submitTime);
                            intent.putExtra("transId", cashWithdrawalData.oriJnlNo);
                            intent.putExtra("creatTime", cashWithdrawalData.createTime);
                            intent.putExtra("bankName", cashWithdrawalData.bankName);
                            intent.putExtra("bankNum", cashWithdrawalData.acNo);
                            intent.putExtra("", cashWithdrawalData.trsType);
                            startActivity(intent);
                            finish();
                        } else {
                            ToastUtil.showCustom(cashWithdrawalData.message);
                        }
                    }
                });
    }

    /**
     *   工资宝买入调的接口
     */
    private void requestData() {
        String trsPwd = listToString(transactionPwdList);
        HashMap<String, Object> param = new HashMap<>();
        param.put("userId", "8");
        param.put("trsAmount", trsAmount); // 基金买入金额（保留两位小数）
        param.put("eaccountTrsPwd", trsPwd); // 交易密码  "123456 "
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
                ToastUtil.showCustom("基金买入获取数据失败");
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
                List<TransactionDetailListModel> prodList = buyFundInfoData.prodList;
                if (buyFundInfoData.flag.equals("true")) {
                    Intent intent = new Intent(TransactionPasswordActivity.this, WageTreasureTurnSuccessActivity.class);
                    intent.putExtra("fromFlag", fromFlag); // 再次把工资宝的买入或赎回类别传到详情页，来区分是买入详情还是赎回详情
                    intent.putExtra("whereToEnterFlag", whereToEnterFlag);
                    intent.putExtra("trsAmount",trsAmount);
                    intent.putExtra("prodList", (Serializable) prodList);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    /**
     *  工资宝赎回时调的接口
     */
    private void requestFundRedemptionData() {
        String trsPwd = listToString(transactionPwdList);
        HashMap<String, Object> param = new HashMap<>();
        param.put("userId", "8");
        param.put("trsAmount", trsAmount); // 基金买入金额（保留两位小数）
        param.put("eaccountTrsPwd", trsPwd); // 交易密码  "123456 "
        param.put("appointFlg", redemptionFlag); // 赎回标志: y普通赎回  n实时赎回
        String data = DESUtil.encMap(param);
        HashMap<String, Object> paramWrapper = new HashMap<>();
        paramWrapper.put("requestKey", data);
        RemoteFactory.getInstance().getProxy(CommonRequestProxy.class)
                .getFundData(paramWrapper).
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
                        CommonResultModel<BuyFundModel> dataModel = new Gson().fromJson(result, new TypeToken<CommonResultModel<BuyFundModel>>() {
                        }.getType());
                        BuyFundModel redeemptionInfoData = dataModel.data;
                        if (redeemptionInfoData == null) {
                            return;
                        }
                        String trsAmount = redeemptionInfoData.trsAmount;
                        List<TransactionDetailListModel> prodList = redeemptionInfoData.prodList;
                        if (redeemptionInfoData.flag.equals("true")) {
                            Intent intent = new Intent(TransactionPasswordActivity.this, WageTreasureTurnSuccessActivity.class);
                            intent.putExtra("fromFlag", fromFlag); // 再次把工资宝的买入或赎回类别传到详情页，来区分是买入详情还是赎回详情
                            intent.putExtra("whereToEnterFlag", whereToEnterFlag);
                            intent.putExtra("trsAmount",trsAmount);
                            intent.putExtra("prodList", (Serializable) prodList);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }

    /**
     *   理财产品买入调的接口
     */
    private void requestRegularFinancialBuy() {
        String trsPwd = listToString(transactionPwdList);
        HashMap<String, Object> param = new HashMap<>();
        param.put("userId", "8");
        param.put("productId", productId); //
        param.put("eaccountTrsPwd", trsPwd); // 交易密码  "123456 "
        param.put("trsAmount", trsAmount); // 基金买入金额（保留两位小数）
        String data = DESUtil.encMap(param);

        HashMap<String, Object> paramWrapper = new HashMap<>();
        paramWrapper.put("requestKey", data);
        RemoteFactory.getInstance().getProxy(CommonRequestProxy.class)
                .getPurchaseFinanceData(paramWrapper)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CommonObserverAdapter<String>() {
                    @Override
                    public void onMyError() {
                        ToastUtil.showCustom("理财买入获取数据失败");
                    }

                    @Override
                    public void onMySuccess(String result) {
                        if (result == null) {
                            return;
                        }
                        CommonResultModel<RegularFinancialBuyModel> dataModel = new Gson().fromJson(result, new TypeToken<CommonResultModel<RegularFinancialBuyModel>>() {
                        }.getType());
                        RegularFinancialBuyModel regularFinancialInfoData = dataModel.data;
                        if (regularFinancialInfoData == null) {
                            return;
                        }
                        String trsAmount = regularFinancialInfoData.trsAmount;
                        List<TransactionDetailListModel> prodList = regularFinancialInfoData.prodList;
                        if (regularFinancialInfoData.flag.equals("true")) {
                            Intent intent = new Intent(TransactionPasswordActivity.this, RegularFinancingBuyStatusDetailActivity.class);
                            intent.putExtra("trsAmount",trsAmount);
                            intent.putExtra("prodList", (Serializable) prodList);
                            startActivity(intent);
                            finish();
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
