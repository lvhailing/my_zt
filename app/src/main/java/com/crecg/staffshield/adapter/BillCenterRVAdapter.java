package com.crecg.staffshield.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.crecg.crecglibrary.network.model.BillCenterItemInnerDataModel;
import com.crecg.staffshield.R;

import java.util.List;
import java.util.Map;


/**
 * 账单中心 列表  RecyclerView的 Adapter 类
 */
public class BillCenterRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private Map<String, List<BillCenterItemInnerDataModel>> map;

    private static final int TYPE_ITEM_ONE = 1;    //月条目
    private static final int TYPE_ITME_TWO = 2; //账单条目
    private static final int TYPE_FOOTER = 3;   //底线条目

    public static final int PULLUP_LOAD_MORE = 0;   //上拉加载更多
    public static final int LOADING_MORE = 1;   //正在加载中
    public static final int NO_LOAD_MORE = 2;   //没有加载更多 隐藏
    //上拉加载更多状态-默认为0
    private int mLoadMoreStatus = 0;


    public BillCenterRVAdapter(Context context, Map<String, List<BillCenterItemInnerDataModel>> map) {
        mContext = context;
        this.map = map;
    }

    public void setData(Map<String, List<BillCenterItemInnerDataModel>> map) {
        this.map = map;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM_ONE) {
            //月条目
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_bill_center_list_month, parent, false);
            return new ItemOneViewHolder(itemView);
        } else if (viewType == TYPE_ITME_TWO) {
            //账单条目
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_bill_center_list, parent, false);
            return new ItemTwoViewHolder(itemView);
        } else if (viewType == TYPE_FOOTER) {
            //底线条条目
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.load_more_footview_layout, parent, false);
            return new FooterViewHolder(itemView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String itemOneData = "";
        BillCenterItemInnerDataModel itemTwoData = null;
        int count = -1;
        for (Map.Entry<String, List<BillCenterItemInnerDataModel>> entry : map.entrySet()) {
            count++;
            if (count == position) {
                itemOneData = entry.getKey();
                break;
            }
            List<BillCenterItemInnerDataModel> list = entry.getValue();
            if (list != null && list.size() > 0) {
                for (BillCenterItemInnerDataModel model : list) {
                    count++;
                    if (count == position) {
                        itemTwoData = model;
                        break;
                    }
                }
            }
        }
        if (holder instanceof ItemOneViewHolder) {
            ItemOneViewHolder viewHolder = (ItemOneViewHolder) holder;
            viewHolder.tv_month.setText(itemOneData);
        } else if (holder instanceof ItemTwoViewHolder) {
            if (itemTwoData == null) {
                return;
            }
            ItemTwoViewHolder viewHolder = (ItemTwoViewHolder) holder;
            String typeCode = itemTwoData.rtxnTypeCode;
            if ("WTHI".equals(typeCode)) {
                viewHolder.tv_card_title.setText("勘设联名卡 - 银行卡 (" + itemTwoData.withdradalNo + ")");
                viewHolder.iv_left_category_card.setBackgroundResource(R.mipmap.img_bill_center_bank_card_out);
            } else if ("RCGI".equals(typeCode)) {
                viewHolder.tv_card_title.setText("银行卡 - 勘设联名卡 (" + itemTwoData.payNo + ")");
                viewHolder.iv_left_category_card.setBackgroundResource(R.mipmap.img_bill_center_bank_card_into);
            } else if ("XYSG".equals(typeCode)) { // 工资宝买入
                viewHolder.tv_card_title.setText("勘设联名卡 - 工资宝");
                viewHolder.iv_left_category_card.setBackgroundResource(R.mipmap.img_bill_center_salary_buy);
            } else if ("XYSH".equals(typeCode)) { // 工资宝赎回
                viewHolder.tv_card_title.setText("工资宝 - 勘设联名卡");
                viewHolder.iv_left_category_card.setBackgroundResource(R.mipmap.img_bill_center_salary_redeem);
            } else if ("XYSY".equals(typeCode)) { // 工资宝收益
                viewHolder.tv_card_title.setText("工资宝收益");
                viewHolder.tv_money.setTextColor(mContext.getResources().getColor(R.color.txt_red_fc514e));
                viewHolder.iv_left_category_card.setBackgroundResource(R.mipmap.img_bill_center_salary_income);
                viewHolder.iv_arrow_right.setVisibility(View.INVISIBLE);
            } else if ("CPDJ".equals(typeCode)) {
                viewHolder.tv_card_title.setText("勘设联名卡 - 理财");
                viewHolder.iv_left_category_card.setBackgroundResource(R.mipmap.img_bill_center_financial_buy);
            } else if ("CPJD".equals(typeCode)) { // 流标 = 回款
                viewHolder.tv_card_title.setText("理财 - 勘设联名卡");
                viewHolder.iv_left_category_card.setBackgroundResource(R.mipmap.img_bill_center_financial_money_back);
            } else if ("CPHK".equals(typeCode)) { // 回款
                viewHolder.tv_card_title.setText("理财 - 勘设联名卡");
                viewHolder.iv_left_category_card.setBackgroundResource(R.mipmap.img_bill_center_financial_money_back);
            }
            viewHolder.tv_bill_time.setText(itemTwoData.createTime);
            viewHolder.tv_money.setText(itemTwoData.billTrsAmount);

            initListener(viewHolder.itemView, itemTwoData.id);
        } else if (holder instanceof FooterViewHolder) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            switch (mLoadMoreStatus) {
                case PULLUP_LOAD_MORE: //上拉加载更多
                    footerViewHolder.tvLoadText.setText("数据加载中...");
                    break;
                case LOADING_MORE: //正在加载中
                    footerViewHolder.tvLoadText.setText("正加载更多...");
                    break;
                case NO_LOAD_MORE:  //没有加载更多 隐藏
                    footerViewHolder.pbLoad.setVisibility(View.GONE);
                    footerViewHolder.tvLoadText.setText("我是有底线的");
                    break;

            }
        }
    }

    @Override
    public int getItemCount() {
        if (map != null) {
//            Log.i("hh", "total count " +"map.keySet().size = " + map.keySet().size() + " -- getTotalCount() = "+getTotalCount() + " -- Footer:-- "+1);
        }
        return map == null ? 0 : map.keySet().size() + getTotalCount() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        int count = -1;
        for (Map.Entry<String, List<BillCenterItemInnerDataModel>> entry : map.entrySet()) {
            count++;
            if (count == position) {
                return TYPE_ITEM_ONE;
            }
            List<BillCenterItemInnerDataModel> list = entry.getValue();
            if (list != null && list.size() > 0) {
                for (BillCenterItemInnerDataModel model : list) {
                    count++;
                    if (count == position) {
                        return TYPE_ITME_TWO;
                    }
                }
            }
        }
        return TYPE_FOOTER;
    }

    /**
     * 月份节点布局 viewHolder
     */
    public class ItemOneViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_month; // 显示的交易 时间

        public ItemOneViewHolder(View itemView) {
            super(itemView);
            tv_month = itemView.findViewById(R.id.tv_month);
        }
    }

    /**
     * 具体账单交易内容布局 viewHolder
     */
    public class ItemTwoViewHolder extends RecyclerView.ViewHolder {
        private final ImageView iv_left_category_card; // 左侧卡的图标
        private final ImageView iv_arrow_right; // 左侧卡的图标
        private final TextView tv_card_title; //
        private final TextView tv_bill_time; // 账单时间
        private final TextView tv_money; // 收益（元）

        public ItemTwoViewHolder(View itemView) {
            super(itemView);
            iv_left_category_card = itemView.findViewById(R.id.iv_left_category_card);
            iv_arrow_right = itemView.findViewById(R.id.iv_arrow_right);
            tv_card_title = itemView.findViewById(R.id.tv_card_title);
            tv_bill_time = itemView.findViewById(R.id.tv_bill_time);
            tv_money = itemView.findViewById(R.id.tv_money);
        }
    }

    /**
     *  Footer布局
     */
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

    /**
     * 获取到内层List集合item 个数
     * @return count
     */
    private int getTotalCount() {
        int count = 0;
        for (Map.Entry<String, List<BillCenterItemInnerDataModel>> entry : map.entrySet()) {
            List<BillCenterItemInnerDataModel> list = entry.getValue();
            if (list != null && list.size() > 0) {
                count += list.size();
            }
        }
        return count;
    }

    /**
     * item 点击监听
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
     */
    public void changeMoreStatus(int status) {
        mLoadMoreStatus = status;
        notifyDataSetChanged();
    }
}
