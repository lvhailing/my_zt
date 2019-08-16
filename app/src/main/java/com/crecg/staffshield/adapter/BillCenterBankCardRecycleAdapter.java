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

import com.crecg.crecglibrary.network.model.BillCenterListData;
import com.crecg.staffshield.R;

import java.util.ArrayList;


/**
 * 账单中心 -- 全部 列表  RecyclerView的 Adapter 类
 */
public class BillCenterBankCardRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context mContext;
    private final ArrayList<BillCenterListData> list;
    LayoutInflater mInflater;
    private static final int TYPE_TIME = 0; // 时间布局
    private static final int TYPE_ITEM = 1; // 账单中心布局
    private static final int TYPE_FOOTER = 2;

    //上拉加载更多
    public static final int PULLUP_LOAD_MORE = 0;
    //正在加载中
    public static final int LOADING_MORE = 1;
    //没有加载更多 隐藏
    public static final int NO_LOAD_MORE = 2;

    //上拉加载更多状态-默认为0
    private int mLoadMoreStatus = 0;


    public BillCenterBankCardRecycleAdapter(Context context, ArrayList<BillCenterListData> list) {
        mContext = context;
        this.list = list;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_TIME) { // 加载时间节点布局
            View itemView = mInflater.inflate(R.layout.item_bill_center_list_time, parent, false);

            return new ItemTimeViewHolder(itemView);
        } else if (viewType == TYPE_ITEM) { // 加载账单布局
            View itemView = mInflater.inflate(R.layout.item_bill_center_list, parent, false);

            return new ItemViewHolder(itemView);
        } else {
            if (viewType == TYPE_FOOTER) {
                View itemView = mInflater.inflate(R.layout.load_more_footview_layout, parent, false);

                return new FooterViewHolder(itemView);
            }
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
            itemTimeViewHolder.tv_time.setText("");
        } else if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
//            itemViewHolder.tv_card_title.setText(list.get(position).title);
//            itemViewHolder.tv_bill_time.setText(list.get(position).time);
//            itemViewHolder.tv_money.setText(list.get(position).income);

//            if (list.get(position).cardType.equals("one")) {
//                itemViewHolder.iv_left_category_card.setBackgroundResource(R.mipmap.img_bill_center_bank_card); // 银行卡
//            } else if (list.get(position).cardType.equals("two")) {
//                itemViewHolder.iv_left_category_card.setBackgroundResource(R.mipmap.img_bill_center_salary); // 工资宝
//            } else if (list.get(position).cardType.equals("three")) {
//                itemViewHolder.iv_left_category_card.setBackgroundResource(R.mipmap.img_bill_center_salary); // 定期理财
//            }

//            initListener(itemViewHolder.itemView,list.get(position).getQuestionId());
        } else if (holder instanceof FooterViewHolder) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;

//            switch (mLoadMoreStatus) {
//                case PULLUP_LOAD_MORE: //上拉加载更多
//                    footerViewHolder.tvLoadText.setText("数据加载中...");
//                    break;
//                case LOADING_MORE: //正在加载中
//                    footerViewHolder.tvLoadText.setText("正加载更多...");
//                    break;
//                case NO_LOAD_MORE:  //没有加载更多 隐藏
            //隐藏加载更多
//                    footerViewHolder.loadLayout.setVisibility(View.GONE);
//                    break;

//            }
        }

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).type == TYPE_TIME) {
            // 时间节点布局
            return TYPE_TIME;
        } else if (position + 1 == getItemCount()) {
            //最后一个item设置为footerView
            return TYPE_FOOTER;
        } else {
            // 账单布局
            return TYPE_ITEM;
        }
    }


    /**
     * item 点击监听
     * @param itemView
     */
    private void initListener(View itemView, final String id) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // 跳转到账单详情
//                Intent intent = new Intent(mContext, TrainingAskDetailsActivity.class);
//                intent.putExtra("questionId", id);
//                Log.i("hh","我参与的---提问列表Item的questionId:"+id);
//                mContext.startActivity(intent);
            }
        });
    }

    public void AddHeaderItem(ArrayList<BillCenterListData> items) {
        list.addAll(0, items);
        notifyDataSetChanged();
    }

    public void AddFooterItem(ArrayList<BillCenterListData> items) {
        list.addAll(items);
        notifyDataSetChanged();
    }

    /**
     * 更新加载更多状态
     *
     * @param status
     */
    public void changeMoreStatus(int status) {
        mLoadMoreStatus = status;
        notifyDataSetChanged();
    }
}
