package com.crecg.staffshield.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.crecg.crecglibrary.network.model.HomeAndFinancialProductList;
import com.crecg.crecglibrary.network.model.ProductModelTestData;
import com.crecg.crecglibrary.utils.ToastUtil;
import com.crecg.staffshield.R;

import java.util.ArrayList;


/**
 * 定期理财列表  RecyclerView的 Adapter 类
 */
public class RegularFinancialListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final ArrayList<HomeAndFinancialProductList> list;
    Context mContext;
    LayoutInflater mInflater;
    private static final int TYPE_ITEM_ONE = 0; // 热卖中或即将开售的产品布局
    private static final int TYPE_ITEM_TWO = 1; // 已售罄、计息中、已回款等产品布局
    private static final int TYPE_FOOTER = 2;

    //上拉加载更多
    public static final int PULLUP_LOAD_MORE = 0;
    //正在加载中
    public static final int LOADING_MORE = 1;
    //没有加载更多 隐藏
    public static final int NO_LOAD_MORE = 2;

    //上拉加载更多状态-默认为0
    private int mLoadMoreStatus = 0;


    public RegularFinancialListAdapter(Context context, ArrayList<HomeAndFinancialProductList> list) {
        mContext = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM_ONE) { // 加载热卖中或即将开售的产品布局
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_regular_products_list_layout1, parent, false);
            return new ItemViewHolder1(itemView);
        } else if (viewType == TYPE_ITEM_TWO) { // 加载 已售罄、计息中、已回款等产品布局
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_regular_products_list_layout2, parent, false);
            return new ItemViewHolder2(itemView);
        } else if (viewType == TYPE_FOOTER) {
            View itemView = mInflater.inflate(R.layout.load_more_footview_layout, parent, false);
            return new FooterViewHolder(itemView);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String status = list.get(position).status;
        /**
         *  init：初始状态--即将开卖
         *  tender：融资中--热卖中
         *  fail：融资失败---已卖完
         *  success：融资成功--已卖完
         *  repaying: 还款中--计息中
         *  repayed：已还清--已还款
         *  prepayed: 提前还款结清--已还款
         *
         *  产品状态顺序：热卖中(tender)-->即将开售(init)-->已售馨(产品已满标，但钱还未打给借款人)-->计息中（表示钱已打给借款人）-->已回款
         */
        if (holder instanceof ItemViewHolder1) {
            ItemViewHolder1 itemViewHolder1 = (ItemViewHolder1) holder;
            itemViewHolder1.tv_regular_product_name.setText(list.get(position).name);
            itemViewHolder1.tv_product_annualized_return.setText(list.get(position).annualRate);
            itemViewHolder1.tv_product_cycle.setText(list.get(position).timeLimit.toString());
            itemViewHolder1.tv_initial_investment_amount.setText(list.get(position).tenderInitAmount.toString()+"元起投");
            if ("tender".equals(status)) { // 热卖布局
                itemViewHolder1.ll_best_sell.setVisibility(View.VISIBLE);
                itemViewHolder1.fl_start_sell.setVisibility(View.GONE);
                float fCurrentProgress = Float.parseFloat(list.get(position).bfbAmount);
                itemViewHolder1.progressbar.setProgress((int)fCurrentProgress);
                itemViewHolder1.tv_surplus_money.setText(list.get(position).syAmount); // 剩余可投金额

            } else if ("init".equals(status)) { // 即将开售布局
                itemViewHolder1.fl_start_sell.setVisibility(View.VISIBLE);
                itemViewHolder1.ll_best_sell.setVisibility(View.GONE);
                itemViewHolder1.tv_start_sale_time.setText(list.get(position).tenderStartTime);
            }
            // item 点击监听
            initListener(itemViewHolder1.itemView, list.get(position).id);
        } else if (holder instanceof ItemViewHolder2) {
            ItemViewHolder2 itemViewHolder2 = (ItemViewHolder2) holder;
            if ("fail".equals(status) || "success".equals(status)) { // 已售罄
                itemViewHolder2.tv_regular_product_name.setText(list.get(position).name);
                itemViewHolder2.tv_product_annualized_return.setText(list.get(position).annualRate);
                itemViewHolder2.tv_product_cycle.setText(list.get(position).timeLimit.toString());
                itemViewHolder2.tv_initial_investment_amount.setText(list.get(position).tenderInitAmount.toString()+"元起投");
                itemViewHolder2.iv_product_state.setBackgroundResource(R.mipmap.img_regular_product_sell_out);
            } else if ("repaying".equals(status)) { // 计息中
                itemViewHolder2.tv_regular_product_name.setText(list.get(position).name);
                itemViewHolder2.tv_product_annualized_return.setText(list.get(position).annualRate);
                itemViewHolder2.tv_product_cycle.setText(list.get(position).timeLimit.toString());
                itemViewHolder2.tv_initial_investment_amount.setText(list.get(position).tenderInitAmount.toString()+"元起投");
                itemViewHolder2.iv_product_state.setBackgroundResource(R.mipmap.img_regular_product_interest_bearing);
            } else if ("repayed".equals(status) || "prepayed".equals(status)) { // 已回款
                itemViewHolder2.tv_regular_product_name.setText(list.get(position).name);
                itemViewHolder2.tv_regular_product_name.setTextColor(mContext.getResources().getColor(R.color.txt_black_999999));
                itemViewHolder2.tv_product_annualized_return.setText(list.get(position).annualRate);
                itemViewHolder2.tv_product_annualized_return.setTextColor(mContext.getResources().getColor(R.color.txt_black_999999));
                itemViewHolder2.tv_product_cycle.setText(list.get(position).timeLimit.toString());
                itemViewHolder2.tv_product_cycle.setTextColor(mContext.getResources().getColor(R.color.txt_black_999999));
                itemViewHolder2.tv_initial_investment_amount.setText(list.get(position).tenderInitAmount.toString()+"元起投");
                itemViewHolder2.tv_initial_investment_amount.setTextColor(mContext.getResources().getColor(R.color.txt_black_999999));
                itemViewHolder2.iv_product_state.setBackgroundResource(R.mipmap.img_regular_product_payment_returned);
                itemViewHolder2.tv_income_category.setTextColor(mContext.getResources().getColor(R.color.txt_black_999999));
                itemViewHolder2.tv_day.setTextColor(mContext.getResources().getColor(R.color.txt_black_999999));
            }

            // item 点击监听
            initListener(itemViewHolder2.itemView, list.get(position).id);
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
//                    footerViewHolder.loadLayout.setVisibility(View.GONE);
//                    break;
//
//            }
        }


    }

    @Override
    public int getItemCount() {
//        return list == null ? 0 : list.size() + 1;
        Log.i("hh", "定期理财列表list.size() = " + list.size());
        return list == null ? 0 : list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).status .equals("tender") || list.get(position).status.equals("init")) {
            return TYPE_ITEM_ONE;
        } else if (position + 1 == getItemCount()) {
            //最后一个item设置为footerView
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM_TWO;
        }
    }

    // 热卖中、即将开售
    public class ItemViewHolder1 extends RecyclerView.ViewHolder {
        private final TextView tv_regular_product_name; // 产品名称
        private final TextView tv_product_annualized_return; // 年化收益率
        private final TextView tv_income_category; // 收益类别
        private final TextView tv_product_cycle; // 产品周期
        private final TextView tv_initial_investment_amount; // 10万元起投
        private final LinearLayout ll_best_sell; // 热卖布局
        private final ProgressBar progressbar;
        private final TextView tv_surplus_money; // 剩余可投金额
        private final FrameLayout fl_start_sell; // 即即开售布局
        private final TextView tv_start_sale_time;

        public ItemViewHolder1(View itemView) {
            super(itemView);
            tv_regular_product_name = itemView.findViewById(R.id.tv_regular_product_name);
            tv_product_annualized_return = itemView.findViewById(R.id.tv_product_annualized_return);
            tv_income_category = itemView.findViewById(R.id.tv_income_category);
            tv_product_cycle = itemView.findViewById(R.id.tv_product_cycle);
            tv_initial_investment_amount = itemView.findViewById(R.id.tv_initial_investment_amount);

            ll_best_sell = itemView.findViewById(R.id.ll_best_sell);
            progressbar = itemView.findViewById(R.id.progressbar);
            tv_surplus_money = itemView.findViewById(R.id.tv_surplus_money);

            fl_start_sell = itemView.findViewById(R.id.fl_start_sell);
            tv_start_sale_time = itemView.findViewById(R.id.tv_start_sale_time);
        }

    }

    // 已售罄、计息中、已回款
    public class ItemViewHolder2 extends RecyclerView.ViewHolder {
        private final TextView tv_regular_product_name; // 产品名称
        private final TextView tv_product_annualized_return; // 年化收益率
        private final TextView tv_income_category; // 收益类别
        private final TextView tv_product_cycle; // 产品周期
        private final TextView tv_initial_investment_amount; // 10万元起投
        private final ImageView iv_product_state; // 产品状态显示的图片
        private final TextView tv_day; //


        public ItemViewHolder2(View itemView) {
            super(itemView);
            tv_regular_product_name = itemView.findViewById(R.id.tv_regular_product_name);
            tv_product_annualized_return = itemView.findViewById(R.id.tv_product_annualized_return);
            tv_income_category = itemView.findViewById(R.id.tv_income_category);
            tv_product_cycle = itemView.findViewById(R.id.tv_product_cycle);
            tv_initial_investment_amount = itemView.findViewById(R.id.tv_initial_investment_amount);
            iv_product_state = itemView.findViewById(R.id.iv_product_state);
            tv_day = itemView.findViewById(R.id.tv_day);
        }

    }

    /**
     * item 点击监听
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
                ToastUtil.showCustom("当前item的flag = "+id);
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


    public void AddHeaderItem(ArrayList<HomeAndFinancialProductList> items) {
        list.addAll(0, items);
        notifyDataSetChanged();
    }

    public void AddFooterItem(ArrayList<HomeAndFinancialProductList> items) {
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
