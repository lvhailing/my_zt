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

import com.bumptech.glide.Glide;
import com.crecg.crecglibrary.network.model.MyFinancialProductItemDataModel;
import com.crecg.staffshield.R;

import java.util.ArrayList;


/**
 * 我参与的列表  RecyclerView的 Adapter 类
 */
public class MyFinacialPaymentReturnedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final ArrayList<MyFinancialProductItemDataModel> list;
    Context mContext;
    LayoutInflater mInflater;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    //上拉加载更多
    public static final int PULLUP_LOAD_MORE = 0;
    //正在加载中
    public static final int LOADING_MORE = 1;
    //没有加载更多 隐藏
    public static final int NO_LOAD_MORE = 2;

    //上拉加载更多状态-默认为0
    private int mLoadMoreStatus = 0;


    public MyFinacialPaymentReturnedAdapter(Context context, ArrayList<MyFinancialProductItemDataModel> list) {
        mContext = context;
        this.list = list;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) { // 加载我的理财：已回款列表 item 布局
            View itemView = mInflater.inflate(R.layout.item_my_financial, parent, false);

            return new ItemViewHolder(itemView);
        } else if (viewType == TYPE_FOOTER) {
            View itemView = mInflater.inflate(R.layout.load_more_footview_layout, parent, false);

            return new FooterViewHolder(itemView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.tv_financial_product_name.setText(list.get(position).name);
            itemViewHolder.tv_product_holding_share.setText(list.get(position).productSum);
            itemViewHolder.tv_product_expect_income.setText(list.get(position).yuJiPoFit);

            if (list.get(position).status.equals("fail")) { // 募集失败
                itemViewHolder.tv_product_cycle.setText("投资时间：" + list.get(position).createTime);
                Glide.with(mContext).load(R.mipmap.img_finacial_state_five).into(itemViewHolder.iv_product_state);
            } else if (list.get(position).status.equals("repayed") || list.get(position).status.equals("prepayed")) { // 已回款
                itemViewHolder.tv_product_cycle.setText("产品周期：" + list.get(position).repayStartDate + "-" + list.get(position).repayEndDate);
                Glide.with(mContext).load(R.mipmap.img_finacial_state_four).into(itemViewHolder.iv_product_state);
            }


//            initListener(itemViewHolder.itemView,list.get(position).getTopicId());
        } else if (holder instanceof FooterViewHolder) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;

//            switch (mLoadMoreStatus) {
//                case PULLUP_LOAD_MORE:
//                    footerViewHolder.tvLoadText.setText("数据加载中...");
//                    break;
//                case LOADING_MORE:
//                    footerViewHolder.tvLoadText.setText("正加载更多...");
//                    break;
//                case NO_LOAD_MORE:
                    //隐藏加载更多
//                    footerViewHolder.loadLayout.setVisibility(View.GONE);
//                    break;

//            }
        }

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size()+1;
    }

    @Override
    public int getItemViewType(int position) {

        if (position + 1 == getItemCount()) {
            //最后一个item设置为footerView
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_financial_product_name; // 产品名称
        private final TextView tv_product_holding_share; // 持有份额（元）
        private final TextView tv_product_expect_income; // 预计收益（元）
        private final TextView tv_product_cycle; //// 投资周期
        private final ImageView iv_product_state; // 产品状态

        public ItemViewHolder(View itemView) {
            super(itemView);
            tv_financial_product_name = itemView.findViewById(R.id.tv_financial_product_name);
            tv_product_holding_share = itemView.findViewById(R.id.tv_product_holding_share);
            tv_product_expect_income = itemView.findViewById(R.id.tv_product_expect_income);
            tv_product_cycle = itemView.findViewById(R.id.tv_product_cycle);
            iv_product_state =  itemView.findViewById(R.id.iv_product_state);

        }

    }

    /**
     * item 点击监听
     * @param itemView
     */
    private void initListener(View itemView, final String id) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // 跳转到产品详情
//                Intent intent = new Intent(mContext, TrainingTopicDetailsActivity.class);
//                intent.putExtra("appTopicId", id);
//                Log.i("hh", "我的理财---已回款列表Item的--topicId:" + id);
//                mContext.startActivity(intent);
            }
        });
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


    public void AddHeaderItem(ArrayList<MyFinancialProductItemDataModel> items) {
        list.addAll(0, items);
        notifyDataSetChanged();
    }

    public void AddFooterItem(ArrayList<MyFinancialProductItemDataModel> items) {
        list.addAll(items);
        notifyDataSetChanged();
    }

    /**
     * 更新加载更多状态
     * @param status
     */
    public void changeMoreStatus(int status) {
        mLoadMoreStatus = status;
        notifyDataSetChanged();
    }
}
