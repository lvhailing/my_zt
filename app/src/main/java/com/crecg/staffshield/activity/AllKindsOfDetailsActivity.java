package com.crecg.staffshield.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crecg.staffshield.R;
import com.crecg.staffshield.common.BaseActivity;

/**
 * Created by junde on 2019/7/19.
 */

public class AllKindsOfDetailsActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back;
    private TextView tv_common_title;
    private TextView tv_title_category; // 例：转入金额（元）
    private TextView tv_transfer_amount; // 例：（转入金额）5000.00
    private TextView tv_notes_transfer; // 例：转入进度
    private RecyclerView recycler_view; // 显示进度的列表
    private LinearLayout ll_about_bank_layout; // 仅联名卡相关时显示布局
    private TextView tv_turn_into_or_out; // 转入或转出
    private TextView tv_bank_name; // 例：招商银行(0123)张三
    private TextView tv_creation_date; // 创建时间
    private TextView tv_bank_flow_num; // 流水号
    private Button btn_complete; // 完成 按钮

    /**
     * 1:工资宝转入详情；
     * 2：工资宝赎回详情
     * 3：工资宝转入账单详情
     * 4：工资宝赎回账单详情
     * <p>
     * 5：联名卡转入详情；
     * 6：联名卡转出详情；
     * 7：联名卡转入账单详情
     * 8：联名卡转出账单详情
     * <p>
     * 9：定期理财买入详情；
     * 10：定期理财买入账单详情；
     * 11：定期理财赎回账单详情；
     */
    private int fromFlag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_all_kinds_of_details);

        initView();
    }

    private void initView() {
        fromFlag = getIntent().getIntExtra("fromFlag", 0);
        iv_back = findViewById(R.id.iv_back);
        tv_common_title = findViewById(R.id.tv_common_title);

        tv_title_category = findViewById(R.id.tv_title_category);
        tv_transfer_amount = findViewById(R.id.tv_transfer_amount);
        tv_notes_transfer = findViewById(R.id.tv_notes_transfer);
        ll_about_bank_layout = findViewById(R.id.ll_about_bank_layout);
        tv_turn_into_or_out = findViewById(R.id.tv_turn_into_or_out);
        tv_bank_name = findViewById(R.id.tv_bank_name);
        tv_creation_date = findViewById(R.id.tv_creation_date);
        tv_bank_flow_num = findViewById(R.id.tv_bank_flow_num);
        btn_complete = findViewById(R.id.btn_complete);

        recycler_view = findViewById(R.id.recycler_view);

        switch (fromFlag) {
            case 1: // 工资宝转入详情；
                iv_back.setVisibility(View.GONE);
                tv_common_title.setText(getResources().getString(R.string.title_wage_treasure_buying_detail));

                tv_title_category.setText(getResources().getString(R.string.transfer_amount));
                tv_transfer_amount.setText("5000.00");
                tv_notes_transfer.setText(getResources().getString(R.string.turn_into_progress));
                btn_complete.setVisibility(View.VISIBLE);
                btn_complete.setOnClickListener(this);
                break;
            case 2: // 工资宝赎回详情
                iv_back.setVisibility(View.GONE);
                tv_common_title.setText(getResources().getString(R.string.title_wage_treasure_redemption_detail));

                tv_title_category.setText(getResources().getString(R.string.redemption_amount));
                tv_transfer_amount.setText("5000.00");
                tv_notes_transfer.setText(getResources().getString(R.string.redemption_progress));
                btn_complete.setVisibility(View.VISIBLE);
                btn_complete.setOnClickListener(this);
                break;
            case 3: // 工资宝转入账单详情
                iv_back.setBackgroundResource(R.mipmap.img_arrow_left);
                tv_common_title.setText(getResources().getString(R.string.title_bill_details));
                iv_back.setOnClickListener(this);

                tv_title_category.setText(getResources().getString(R.string.transfer_amount));
                tv_transfer_amount.setText("5000.00");
                tv_notes_transfer.setText(getResources().getString(R.string.turn_into_progress));
                btn_complete.setVisibility(View.GONE);
                break;
            case 4: // 工资宝赎回账单详情
                iv_back.setBackgroundResource(R.mipmap.img_arrow_left);
                tv_common_title.setText(getResources().getString(R.string.title_bill_details));
                iv_back.setOnClickListener(this);

                tv_title_category.setText(getResources().getString(R.string.redemption_amount));
                tv_transfer_amount.setText("5000.00");
                tv_notes_transfer.setText(getResources().getString(R.string.redemption_progress));
                btn_complete.setVisibility(View.GONE);
                break;
            case 5: // 联名卡转入详情；
                iv_back.setVisibility(View.GONE);
                tv_common_title.setText(getResources().getString(R.string.title_bank_turn_into_detail));

                tv_title_category.setText(getResources().getString(R.string.transfer_amount));
                tv_transfer_amount.setText("5000.00");
                tv_notes_transfer.setText(getResources().getString(R.string.turn_into_progress));
                ll_about_bank_layout.setVisibility(View.VISIBLE);
                tv_turn_into_or_out.setText("转入到");
                tv_bank_name.setText("勘设联名卡(可用余额)"); // Todo 后需调接口获取
                tv_creation_date.setText("2019-07-19 15:44"); // Todo 后需调接口获取
                tv_bank_flow_num.setText("2018112810500201010233232321"); // Todo 后需调接口获取
                btn_complete.setVisibility(View.VISIBLE);
                btn_complete.setOnClickListener(this);
                break;
            case 6: // 联名卡转出详情；
                iv_back.setVisibility(View.GONE);
                tv_common_title.setText(getResources().getString(R.string.title_bank_roll_out_detail));

                tv_title_category.setText(getResources().getString(R.string.roll_out_amount));
                tv_transfer_amount.setText("5000.00");
                tv_notes_transfer.setText(getResources().getString(R.string.turn_out_progress));
                ll_about_bank_layout.setVisibility(View.VISIBLE);
                tv_turn_into_or_out.setText("转出到");
                tv_bank_name.setText("招商银行(0123)张三"); // Todo 后需调接口获取
                tv_creation_date.setText("2019-07-19 15:44"); // Todo 后需调接口获取
                tv_bank_flow_num.setText("2018112810500201010233232321"); // Todo 后需调接口获取
                btn_complete.setVisibility(View.VISIBLE);
                btn_complete.setOnClickListener(this);
                break;
            case 7: // 联名卡转入账单详情
                iv_back.setBackgroundResource(R.mipmap.img_arrow_left);
                tv_common_title.setText(getResources().getString(R.string.title_bill_details));
                iv_back.setOnClickListener(this);

                tv_title_category.setText("银行卡(2321)-勘设联名卡");
                tv_transfer_amount.setText("5000.00");
                tv_notes_transfer.setText(getResources().getString(R.string.turn_into_progress));
                ll_about_bank_layout.setVisibility(View.VISIBLE);
                tv_turn_into_or_out.setText("转入到");
                tv_bank_name.setText("勘设联名卡(可用余额)"); // Todo 后需调接口获取
                tv_creation_date.setText("2019-07-19 15:44"); // Todo 后需调接口获取
                tv_bank_flow_num.setText("2018112810500201010233232321"); // Todo 后需调接口获取
                break;
            case 8: // 联名卡转出账单详情
                iv_back.setBackgroundResource(R.mipmap.img_arrow_left);
                tv_common_title.setText(getResources().getString(R.string.title_bill_details));
                iv_back.setOnClickListener(this);

                tv_title_category.setText("勘设联名卡-银行卡(2321)");
                tv_transfer_amount.setText("5000.00");
                tv_notes_transfer.setText(getResources().getString(R.string.turn_out_progress));
                ll_about_bank_layout.setVisibility(View.VISIBLE);
                tv_turn_into_or_out.setText("转出到");
                tv_bank_name.setText("招商银行(0123)张三"); // Todo 后需调接口获取
                tv_creation_date.setText("2019-07-19 15:44"); // Todo 后需调接口获取
                tv_bank_flow_num.setText("2018112810500201010233232321"); // Todo 后需调接口获取
                break;
            case 9: // 定期理财买入详情；
                iv_back.setVisibility(View.GONE);
                tv_common_title.setText(getResources().getString(R.string.title_regular_financial_buying_detail));

                tv_title_category.setText(getResources().getString(R.string.purchase_amount));
                tv_transfer_amount.setText("5000.00");
                tv_notes_transfer.setText(getResources().getString(R.string.buying_progress));
                btn_complete.setVisibility(View.VISIBLE);
                btn_complete.setOnClickListener(this);
                break;
            case 10: // 定期理财买入账单详情；
                iv_back.setBackgroundResource(R.mipmap.img_arrow_left);
                tv_common_title.setText(getResources().getString(R.string.title_bill_details));
                iv_back.setOnClickListener(this);

                tv_title_category.setText(getResources().getString(R.string.purchase_amount));
                tv_transfer_amount.setText("5000.00");
                tv_notes_transfer.setText(getResources().getString(R.string.buying_progress));

                break;
            case 11: // 定期理财赎回账单详情；
                iv_back.setBackgroundResource(R.mipmap.img_arrow_left);
                tv_common_title.setText(getResources().getString(R.string.title_bill_details));
                iv_back.setOnClickListener(this);

                tv_title_category.setText(getResources().getString(R.string.redemption_amount));
                tv_transfer_amount.setText("5000.00");
                tv_notes_transfer.setText(getResources().getString(R.string.payment_progress));
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back: // 返回
                finish();
                break;
            case R.id.btn_complete: // 完成 按钮
                break;
        }
    }
}
