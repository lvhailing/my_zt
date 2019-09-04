package com.crecg.staffshield.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crecg.crecglibrary.RemoteFactory;
import com.crecg.crecglibrary.network.CommonObserverAdapter;
import com.crecg.crecglibrary.network.CommonRequestProxy;
import com.crecg.crecglibrary.network.model.CommonResultModel;
import com.crecg.crecglibrary.network.model.MyDataModel;
import com.crecg.crecglibrary.network.model.MyFinancialDataModel;
import com.crecg.crecglibrary.utils.ToastUtil;
import com.crecg.crecglibrary.utils.encrypt.DESUtil;
import com.crecg.staffshield.R;
import com.crecg.staffshield.activity.AccountBalanceActivity;
import com.crecg.staffshield.activity.AddBankCardActivity;
import com.crecg.staffshield.activity.EntityBankToElectronicBankActivity;
import com.crecg.staffshield.activity.ElectronicBankToEntityBankActivity;
import com.crecg.staffshield.activity.MyFinancialManagementListActivity;
import com.crecg.staffshield.activity.MyInsuranceActivity;
import com.crecg.staffshield.activity.SalaryTreasureDetailActivity;
import com.crecg.staffshield.activity.SettingActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 我的 模块
 */

public class MeFragment extends Fragment implements View.OnClickListener {

    private View mView;
    //    private RelativeLayout me_rl_customer_service_center; // 客服中心
    private Context context;
    private ImageView iv_mine_setting; // 设置
    private ImageView iv_eye_state; // 显示或隐藏 资产总额
    private TextView me_tv_total_assets; // 资产总额
    private TextView me_tv_yesterday_profit; //昨日收益
    private TextView me_tv_accumulated_income; // 累计收益
    private Button btn_change_into; // 转入
    private Button btn_turn_out; // 转出
    private LinearLayout ll_salary_treasure; // 工资宝 布局
    private LinearLayout ll_conduct_financial_transactions; // 定期理财 布局
    private RelativeLayout rl_my_insurance; // 我的保险
    private RelativeLayout rl_invite_colleagues; // 邀请同事
    private RelativeLayout rl_account_balance; // 账户余额

    private String btnFlag = "1"; // 1:转入     2：转出
    private boolean showOrHideFlag = true; // 资产总额 默认显示
    private MyDataModel myData;
    private TextView tv_account_balance; // 银行存管可用余额
    private TextView tv_fund_total_holding; // 基金持仓总额
    private TextView tv_fund_yesterday_earnings; // 基金昨日收益
    private TextView tv_financial_total_holding; // 定期理财持仓总额
    private TextView tv_financial_waiting_income; // 定期理财待收收益
    private String acNo;
    private String electronicAccount;


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
//            Log.i("hh", "MeFragment --- isVisibleToUser = " + isVisibleToUser);
            if (context != null) {
                requestData();
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_me, container, false);
            try {
                initView(mView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (mView.getParent() != null) {
                ((ViewGroup) mView.getParent()).removeView(mView);
            }
        }

        return mView;
    }

    private void initView(View mView) {
        context = getContext();
        iv_mine_setting = mView.findViewById(R.id.iv_mine_setting);
        me_tv_total_assets = mView.findViewById(R.id.me_tv_total_assets);
        me_tv_yesterday_profit = mView.findViewById(R.id.me_tv_yesterday_profit);
        me_tv_accumulated_income = mView.findViewById(R.id.me_tv_accumulated_income);
        tv_account_balance = mView.findViewById(R.id.tv_account_balance);
        tv_fund_total_holding = mView.findViewById(R.id.tv_fund_total_holding);
        tv_fund_yesterday_earnings = mView.findViewById(R.id.tv_fund_yesterday_earnings);
        tv_financial_total_holding = mView.findViewById(R.id.tv_financial_total_holding);
        tv_financial_waiting_income = mView.findViewById(R.id.tv_financial_waiting_income);
        iv_eye_state = mView.findViewById(R.id.iv_eye_state);
        btn_change_into = mView.findViewById(R.id.btn_change_into);
        btn_turn_out = mView.findViewById(R.id.btn_turn_out);
        rl_account_balance = mView.findViewById(R.id.rl_account_balance);
        ll_salary_treasure = mView.findViewById(R.id.ll_salary_treasure);
        ll_conduct_financial_transactions = mView.findViewById(R.id.ll_conduct_financial_transactions);
        rl_my_insurance = mView.findViewById(R.id.rl_my_insurance);
        rl_invite_colleagues = mView.findViewById(R.id.rl_invite_colleagues);
//        me_rl_customer_service_center = mView.findViewById(R.id.me_rl_customer_service_center);

//        me_rl_customer_service_center.setOnClickListener(this);
        iv_eye_state.setOnClickListener(this);
        iv_mine_setting.setOnClickListener(this);
        rl_account_balance.setOnClickListener(this);
        btn_change_into.setOnClickListener(this);
        btn_turn_out.setOnClickListener(this);
        ll_salary_treasure.setOnClickListener(this);
        ll_conduct_financial_transactions.setOnClickListener(this);
        rl_my_insurance.setOnClickListener(this);
        rl_invite_colleagues.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
//        Log.i("hh", "onResume -- 方法执行了");
        requestData();
    }

    /**
     * 获取 我的 模块数据
     */
    private void requestData() {
        HashMap<String, Object> param = new HashMap<>();
        param.put("userId", "8");
        String data = DESUtil.encMap(param);

        HashMap<String, Object> paramWrapper = new HashMap<>();
        paramWrapper.put("requestKey", data);
        RemoteFactory.getInstance().getProxy(CommonRequestProxy.class).getMyData(paramWrapper)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CommonObserverAdapter<String>() {
            @Override
            public void onMyError() {
                ToastUtil.showCustom("我的模块数据失败");
            }

            @Override
            public void onMySuccess(String result) {
                if (result == null) {
                    return;
                }
                CommonResultModel<MyDataModel> myDataModel = new Gson().fromJson(result, new TypeToken<CommonResultModel<MyDataModel>>() {
                }.getType());
                if (myDataModel == null) {
                    return;
                }
                myData = myDataModel.data;
                acNo = myData.acNo;
                electronicAccount = myData.account;
                setData(myData);
            }
        });
    }

    private void setData(MyDataModel myData) {
        me_tv_total_assets.setText(myData.availBalance); // 总资产
        me_tv_yesterday_profit.setText(myData.userLastProfit); //昨日收益
        me_tv_accumulated_income.setText(myData.userLastProfitSum); // 累计收益
        tv_account_balance.setText(myData.depositAvailBal); // 银行存管可用余额
        tv_fund_total_holding.setText(myData.prodShare); // 基金持仓总额
        tv_fund_yesterday_earnings.setText(myData.prodLastProfit); // 基金昨日收益
        tv_financial_total_holding.setText(myData.productSum);  // 定期理财持仓总额
        tv_financial_waiting_income.setText(myData.dsProfit); // 定期理财待收收益
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
//            case R.id.me_rl_customer_service_center:
//                Intent intent = new Intent(context, CustomerServiceCenterActivity.class);
//                startActivity(intent);
//                break;
            case R.id.iv_mine_setting: // 设置
                intent = new Intent(context, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_eye_state: // 显示或隐藏 资产总额
                if (showOrHideFlag) {
                    iv_eye_state.setImageResource(R.mipmap.img_eye_close);
                    me_tv_total_assets.setText("****");
                    me_tv_yesterday_profit.setText("****");
                    me_tv_accumulated_income.setText("****");

                    showOrHideFlag = false;
                } else {
                    iv_eye_state.setImageResource(R.mipmap.img_eye_open);
                    me_tv_total_assets.setText(myData.availBalance);
                    me_tv_yesterday_profit.setText(myData.userLastProfit);
                    me_tv_accumulated_income.setText(myData.userLastProfitSum);

                    showOrHideFlag = true;
                }
                break;
            case R.id.rl_account_balance: // 跳账户余额页
                    intent = new Intent(context, AccountBalanceActivity.class);
                    startActivity(intent);
                break;
            case R.id.btn_change_into: // (联名卡)转入  需要判断是否绑卡
                if ("2".equals(btnFlag)) {
                    btn_change_into.setBackground(getResources().getDrawable(R.drawable.shape_rect_btn_blue));
                    btn_change_into.setTextColor(getResources().getColor(R.color.white));
                    btn_turn_out.setBackground(getResources().getDrawable(R.drawable.shape_rect_stroke_blue_solid_white));
                    btn_turn_out.setTextColor(getResources().getColor(R.color.main_blue_4A67F5));
                    btnFlag = "1";
                }
                intent = new Intent(context, EntityBankToElectronicBankActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_turn_out: // (联名卡)转出  需要判断是否绑卡
                if ("1".equals(btnFlag)) {
                    btn_turn_out.setBackground(getResources().getDrawable(R.drawable.shape_rect_btn_blue));
                    btn_turn_out.setTextColor(getResources().getColor(R.color.white));
                    btn_change_into.setBackground(getResources().getDrawable(R.drawable.shape_rect_stroke_blue_solid_white));
                    btn_change_into.setTextColor(getResources().getColor(R.color.main_blue_4A67F5));
                    btnFlag = "2";
                }
                intent = new Intent(context, ElectronicBankToEntityBankActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_salary_treasure: // 工资宝
                intent = new Intent(context, SalaryTreasureDetailActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_conduct_financial_transactions: // 定期理财
                intent = new Intent(context, MyFinancialManagementListActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_my_insurance: // 我的保险
                intent = new Intent(context, MyInsuranceActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_invite_colleagues: // 邀请同事（跳H5）
                break;
        }
    }
}
