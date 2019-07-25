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
import com.crecg.staffshield.activity.AccountBalanceActivity;
import com.crecg.staffshield.activity.EntityBankToElectronicBankActivity;
import com.crecg.staffshield.activity.ElectronicBankToEntityBankActivity;
import com.crecg.staffshield.activity.MyFinancialManagementListActivity;
import com.crecg.staffshield.activity.SalaryTreasureDetailActivity;
import com.crecg.staffshield.activity.SettingActivity;

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
    private RelativeLayout rl_invite_colleagues; // 邀请同事
    private RelativeLayout rl_account_balance; // 账户余额

    private String btnFlag = "1"; // 1:转入     2：转出
    private boolean showOrHideFlag = true; // 资产总额 默认显示

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
        iv_eye_state = mView.findViewById(R.id.iv_eye_state);
        btn_change_into = mView.findViewById(R.id.btn_change_into);
        btn_turn_out = mView.findViewById(R.id.btn_turn_out);
        rl_account_balance = mView.findViewById(R.id.rl_account_balance);
        ll_salary_treasure = mView.findViewById(R.id.ll_salary_treasure);
        ll_conduct_financial_transactions = mView.findViewById(R.id.ll_conduct_financial_transactions);
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
            case R.id.iv_eye_state: // 显示或隐藏 资产总额
                if (showOrHideFlag) {
                    iv_eye_state.setImageResource(R.mipmap.img_eye_close);
                    me_tv_total_assets.setText("****");
                    showOrHideFlag = false;
                } else {
                    iv_eye_state.setImageResource(R.mipmap.img_eye_open);
                    me_tv_total_assets.setText("270024.99");
                    showOrHideFlag = true;
                }
                break;
            case R.id.rl_account_balance: // (联名卡)账户余额  跳转前需要先判断用户是否绑卡，没绑卡先跳绑卡页，反之则跳账户余额页
                intent = new Intent(context, AccountBalanceActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_change_into: // (联名卡)转入
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
            case R.id.btn_turn_out: // (联名卡)转出
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
            case R.id.rl_invite_colleagues: // 邀请同事（跳H5）
                break;
        }
    }
}
