package com.crecg.staffshield.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crecg.staffshield.R;
import com.crecg.staffshield.activity.MoneyFromEntityBankToElectronicBankActivity;
import com.crecg.staffshield.activity.MoneyFromElectronicBankToEntityBankActivity;
import com.crecg.staffshield.activity.MyFinancialManagementActivity;
import com.crecg.staffshield.activity.SettingActivity;

/**
 * 我的 模块
 */

public class MeFragment extends Fragment implements View.OnClickListener {

    private View mView;
    //    private RelativeLayout me_rl_customer_service_center; // 客服中心
    private Context context;
    private ImageView iv_mine_setting; // 设置
    private ImageView iv_eye_open; // 显示或隐藏 资产总额
    private TextView me_tv_total_assets; // 资产总额
    private TextView me_tv_yesterday_profit; //昨日收益
    private TextView me_tv_accumulated_income; // 累计收益
    private Button btn_change_into; // 转入
    private Button btn_turn_out; // 转出
    private LinearLayout ll_salary_treasure; // 工资宝 布局
    private LinearLayout ll_conduct_financial_transactions; // 定期理财 布局
    private RelativeLayout rl_invite_colleagues; // 邀请同事

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
        iv_eye_open = mView.findViewById(R.id.iv_eye_open);
        btn_change_into = mView.findViewById(R.id.btn_change_into);
        btn_turn_out = mView.findViewById(R.id.btn_turn_out);
        ll_salary_treasure = mView.findViewById(R.id.ll_salary_treasure);
        ll_conduct_financial_transactions = mView.findViewById(R.id.ll_conduct_financial_transactions);
        rl_invite_colleagues = mView.findViewById(R.id.rl_invite_colleagues);
//        me_rl_customer_service_center = mView.findViewById(R.id.me_rl_customer_service_center);

//        me_rl_customer_service_center.setOnClickListener(this);
        iv_mine_setting.setOnClickListener(this);
        btn_change_into.setOnClickListener(this);
        btn_turn_out.setOnClickListener(this);
        ll_salary_treasure.setOnClickListener(this);
        ll_conduct_financial_transactions.setOnClickListener(this);
        rl_invite_colleagues.setOnClickListener(this);
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
            case R.id.btn_change_into: // 转入
                intent = new Intent(context, MoneyFromEntityBankToElectronicBankActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_turn_out: // 转出
                intent = new Intent(context, MoneyFromElectronicBankToEntityBankActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_salary_treasure: // 工资宝
                break;
            case R.id.ll_conduct_financial_transactions: // 定期理财
                intent = new Intent(context, MyFinancialManagementActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_invite_colleagues: // 邀请同事（跳H5）
                break;
        }
    }
}
