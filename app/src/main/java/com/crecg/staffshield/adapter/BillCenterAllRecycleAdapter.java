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

import com.crecg.crecglibrary.network.model.BillCenterListData;
import com.crecg.crecglibrary.network.model.BillCenterModelData;
import com.crecg.staffshield.R;

import java.util.ArrayList;
import java.util.List;


/**
 * 账单中心 -- 全部 列表  RecyclerView的 Adapter 类
 */
public class BillCenterAllRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final ArrayList<BillCenterListData> list;
    Context mContext;
    LayoutInflater mInflater;
    private static final int TYPE_TIME = 0; // 时间布局
    private static final int TYPE_ITEM = 1; // 账单中心布局


    public BillCenterAllRecycleAdapter(Context context, ArrayList<BillCenterListData> list) {
        mContext = context;
        this.list = list;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_TIME) { // 加载时间节点布局
            View itemView = mInflater.inflate(R.layout.item_bill_center_list_time, parent, false);

            return new BillCenterAllRecycleAdapter.ItemTimeViewHolder(itemView);
        }
        if (viewType == TYPE_ITEM) { // 加载账单布局
            View itemView = mInflater.inflate(R.layout.item_bill_center_list, parent, false);

            return new BillCenterAllRecycleAdapter.ItemViewHolder(itemView);
        }
        return null;
    }

    /**
     * 时间节点布局 viewHolder
     */
    public class ItemTimeViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_time; // 显示的交易 时间

        public ItemTimeViewHolder(View itemView) {
            super(itemView);
            tv_time = itemView.findViewById(R.id.tv_month);
        }
    }

    /**
     * 账单布局 viewHolder
     */
    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private final ImageView iv_left_category_card; // 左侧卡的图标
        private final TextView tv_card_title; //
        private final TextView tv_bill_time; // 账单时间
        private final TextView tv_money; // 收益（元）

        public ItemViewHolder(View itemView) {
            super(itemView);
            iv_left_category_card = itemView.findViewById(R.id.iv_left_category_card);
            tv_card_title = itemView.findViewById(R.id.tv_card_title);
            tv_bill_time = itemView.findViewById(R.id.tv_bill_time);
            tv_money = itemView.findViewById(R.id.tv_money);
        }

    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {

        private final ProgressBar pbLoad;
        private final TextView tvLoadText;
        private final LinearLayout loadLayout;

        public FooterViewHolder(View itemView) {
            super(itemView);

            pbLoad = (ProgressBar) itemView.findViewById(R.id.pbLoad);
            tvLoadText = (TextView) itemView.findViewById(R.id.tvLoadText);
            loadLayout = (LinearLayout) itemView.findViewById(R.id.loadLayout);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemTimeViewHolder) {
            ItemTimeViewHolder itemTimeViewHolder = (ItemTimeViewHolder) holder;
            String time = "";
            if (position == 0) {
                time = list.get(0).time;
            } else {
                int count2 = -1;
                int count3 = 0;
                for (BillCenterListData item : list) {
                    count2++;
                    count3++;
                    count2 += item.jsonData.size();
                    if (position - 1 == count2) {
                        time = list.get(count3).time;
                    }
                }
            }
            itemTimeViewHolder.tv_time.setText(time);
        } else if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

            int count2 = -1;
            int count3 = -1;
            int count4 = 0;
            for (BillCenterListData item : list) {
                count2++;
                count3++;
                count2 += item.jsonData.size();
                if (position <= count2) {
                    List<BillCenterModelData> neiList = list.get(count3).jsonData;
                    BillCenterModelData itemNei = neiList.get(position - count3 - count4 - 1);

                    itemViewHolder.tv_card_title.setText(itemNei.title);
                    itemViewHolder.tv_bill_time.setText(itemNei.time);
                    itemViewHolder.tv_money.setText(itemNei.income);

                    if (itemNei.cardType.equals("bank")) {
                        itemViewHolder.iv_left_category_card.setBackgroundResource(R.mipmap.img_bill_center_bank_card); // 银行卡
                    } else if (itemNei.cardType.equals("salaryTreasure")) {
                        itemViewHolder.iv_left_category_card.setBackgroundResource(R.mipmap.img_bill_center_salary); // 工资宝
                    } else if (itemNei.cardType.equals("regularFinancial")) {
                        itemViewHolder.iv_left_category_card.setBackgroundResource(R.mipmap.img_bill_center_financial); // 定期理财
                    }
                    initListener(itemViewHolder.itemView, itemNei.title);
                    break;
                }
                count4 += item.jsonData.size();
            }
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (list != null) {
            count += list.size();
            for (BillCenterListData item : list) {
                if (item.jsonData != null) {
                    count += item.jsonData.size();
                }
            }
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_TIME;
        }
        int count2 = -1;
        for (BillCenterListData item : list) {
            count2++;
            if (item.jsonData != null) {
                count2 += item.jsonData.size();
                if (position - 1 == count2) {
                    return TYPE_TIME;
                }
            }
        }
        return TYPE_ITEM;
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
        notifyDataSetChanged();
    }
}
