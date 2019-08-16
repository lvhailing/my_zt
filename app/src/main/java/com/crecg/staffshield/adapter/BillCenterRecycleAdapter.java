package com.crecg.staffshield.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.crecg.crecglibrary.network.model.BillCenterItemInnerDataModel;
import com.crecg.crecglibrary.network.model.BillCenterItemOutDataModel;
import com.crecg.staffshield.R;

import java.util.ArrayList;
import java.util.List;


/**
 * 账单中心 列表  RecyclerView的 Adapter 类
 */
public class BillCenterRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //    private List<BillCenterListData> list;
    private Context mContext;
    private ArrayList<BillCenterItemOutDataModel> list;
    private static final int TYPE_ITEM_ONE = 0; //  加载账单+时间布局
    private static final int TYPE_FOOTER = 1; //
    //上拉加载更多
    public static final int PULLUP_LOAD_MORE = 0;
    //正在加载中
    public static final int LOADING_MORE = 1;
    //没有加载更多 隐藏
    public static final int NO_LOAD_MORE = 2;
    //上拉加载更多状态-默认为0
    private int mLoadMoreStatus = 0;


    public BillCenterRecycleAdapter(Context context, ArrayList<BillCenterItemOutDataModel> list) {
        mContext = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM_ONE) {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_bill_center_list2, parent, false);
            return new BillCenterRecycleAdapter.ItemViewHolder(itemView);
        }else if (viewType == TYPE_FOOTER) {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.load_more_footview_layout, parent, false);
            return new BillCenterRecycleAdapter.FooterViewHolder(itemView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder viewHolder = (ItemViewHolder) holder;
            for (BillCenterItemOutDataModel item : list) {
                View monthView = LayoutInflater.from(mContext).inflate(R.layout.item_bill_center_list_time, null);
                TextView tv_month = monthView.findViewById(R.id.tv_month);
                tv_month.setText(item.month);
                viewHolder.ll_container.addView(monthView);

                List<BillCenterItemInnerDataModel> neiList = item.data;
                for (BillCenterItemInnerDataModel neiItem : neiList) {
                    View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_bill_center_list, null);
                    TextView tv_card_title = itemView.findViewById(R.id.tv_card_title);
                    TextView tv_bill_time = itemView.findViewById(R.id.tv_bill_time);
                    TextView tv_money = itemView.findViewById(R.id.tv_money);
                    ImageView iv_arrow_right = itemView.findViewById(R.id.iv_arrow_right);
                    ImageView iv_left_category_card = itemView.findViewById(R.id.iv_left_category_card);

                    /**
                     * WTHI = 勘设联名卡-银行卡 (提现)  对应收款账号：withdradalNo    -6000.00
                     * RCGI = 银行卡-勘设联名卡(充值)  对应收款账号：payNo     +1000.00
                     *
                     * XYSG = 工资宝买入(勘设联名卡买入工资宝)  -5000.00
                     * XYSH = 工资宝赎回 (工资宝赎回到勘设联名卡) +6000.00
                     * XYSY = 工资宝收益   +10.00
                     *
                     * CPDJ = 勘设联名卡-理财（投标）  -5000.00
                     * CPJD = 理财-勘设联名卡（流标）
                     * CPHK = 理财-勘设联名卡（回款）  +6000.00
                     */
                    String typeCode = neiItem.rtxnTypeCode;
                    if ("WTHI".equals(typeCode)) {
                        tv_card_title.setText("勘设联名卡 - 银行卡 (" + neiItem.withdradalNo + ")");
                        iv_left_category_card.setBackgroundResource(R.mipmap.img_bill_center_bank_card_out);
                    } else if ("RCGI".equals(typeCode)) {
                        tv_card_title.setText("银行卡 - 勘设联名卡 (" + neiItem.payNo + ")");
                        iv_left_category_card.setBackgroundResource(R.mipmap.img_bill_center_bank_card_into);

                    } else if ("XYSG".equals(typeCode)) { // 工资宝买入
                        tv_card_title.setText("勘设联名卡 - 工资宝");
                        iv_left_category_card.setBackgroundResource(R.mipmap.img_bill_center_salary_buy);
                    }else if ("XYSH".equals(typeCode)) { // 工资宝赎回
                        tv_card_title.setText("工资宝 - 勘设联名卡");
                        iv_left_category_card.setBackgroundResource(R.mipmap.img_bill_center_salary_redeem);
                    }else if ("XYSY".equals(typeCode)) { // 工资宝收益
                        tv_card_title.setText("工资宝收益");
                        tv_money.setTextColor(mContext.getResources().getColor(R.color.txt_red_fc514e));
                        iv_left_category_card.setBackgroundResource(R.mipmap.img_bill_center_salary_income);
                        iv_arrow_right.setVisibility(View.INVISIBLE);

                    } else if ("CPDJ".equals(typeCode)) {
                        tv_card_title.setText("勘设联名卡 - 理财");
                        iv_left_category_card.setBackgroundResource(R.mipmap.img_bill_center_financial_buy);
                    } else if ("CPJD".equals(typeCode)) { // 流标 = 回款
                        tv_card_title.setText("理财 - 勘设联名卡");
                        iv_left_category_card.setBackgroundResource(R.mipmap.img_bill_center_financial_money_back);
                    }else if ("CPHK".equals(typeCode)) { // 回款
                        tv_card_title.setText("理财 - 勘设联名卡");
                        iv_left_category_card.setBackgroundResource(R.mipmap.img_bill_center_financial_money_back);
                    }
                    tv_bill_time.setText(neiItem.createTime);
                    tv_money.setText(neiItem.billTrsAmount);

                    viewHolder.ll_container.addView(itemView);

                    initListener(viewHolder.itemView, neiItem.id);
                }
            }
        }else if (holder instanceof BillCenterRecycleAdapter.FooterViewHolder) {
            BillCenterRecycleAdapter.FooterViewHolder footerViewHolder = (BillCenterRecycleAdapter.FooterViewHolder) holder;

            switch (mLoadMoreStatus) {
                case PULLUP_LOAD_MORE: //上拉加载更多
                    footerViewHolder.tvLoadText.setText("数据加载中...");
                    break;
                case LOADING_MORE: //正在加载中
                    footerViewHolder.tvLoadText.setText("正加载更多...");
                    break;
                case NO_LOAD_MORE:  //没有加载更多 隐藏
                    footerViewHolder.loadLayout.setVisibility(View.GONE);
                    break;

            }
        }
    }

    /**
     * 账单+时间布局 viewHolder
     */
    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout ll_container;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ll_container = itemView.findViewById(R.id.ll_container);
        }

    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {

        private final ProgressBar pbLoad;
        private final TextView tvLoadText;
        private final LinearLayout loadLayout;

        public FooterViewHolder(View itemView) {
            super(itemView);

            pbLoad = itemView.findViewById(R.id.pbLoad);
            tvLoadText = itemView.findViewById(R.id.tvLoadText);
            loadLayout = itemView.findViewById(R.id.loadLayout);
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    /**
     * item 点击监听
     *
     * @param itemView
     */
    private void initListener(View itemView, final String id) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // 跳转到问题详情
//                Intent intent = new Intent(mContext, TrainingAskDetailsActivity.class);
//                intent.putExtra("questionId", id);
//                Log.i("hh","我参与的---提问列表Item的questionId:"+id);
//                mContext.startActivity(intent);
                Toast.makeText(mContext, "click: " + id, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 更新加载更多状态
     *
     * @param status
     */
    public void changeMoreStatus(int status) {
        mLoadMoreStatus = status;
//        notifyDataSetChanged();
    }
}
