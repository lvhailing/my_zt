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
import com.crecg.crecglibrary.network.model.MyInsuranceItemModel;
import com.crecg.staffshield.R;

import java.util.ArrayList;


/**
 * 我的理财列表  RecyclerView的 Adapter 类
 */
public class MyInsuranceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final ArrayList<MyInsuranceItemModel> list;
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


    public MyInsuranceAdapter(Context context, ArrayList<MyInsuranceItemModel> list) {
        mContext = context;
        this.list = list;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) { // 加载我的保险列表 item 布局
            View itemView = mInflater.inflate(R.layout.item_my_insurance, parent, false);
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
            itemViewHolder.tv_type_insurance.setText(list.get(position).orderInsuranceType);
            if ("0".equals(list.get(position).status)) {
                itemViewHolder.tv_status_insurance.setText("待处理");
            } else if ("1".equals(list.get(position).status)) {
                itemViewHolder.tv_status_insurance.setText("已处理");
            }
            itemViewHolder.tv_name.setText(list.get(position).name);
            itemViewHolder.tv_phone.setText(list.get(position).mobile);
            itemViewHolder.tv_date.setText(list.get(position).createTime);


//            initListener(itemViewHolder.itemView, list.get(position).productId);
        } else if (holder instanceof FooterViewHolder) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;

            switch (mLoadMoreStatus) {
                case PULLUP_LOAD_MORE: //上拉加载更多
                    footerViewHolder.tvLoadText.setText("数据加载中...");
                    break;
                case LOADING_MORE: //正在加载中
                    footerViewHolder.tvLoadText.setText("正加载更多...");
                    break;
                case NO_LOAD_MORE:  //没有加载更多 隐藏 隐藏加载更多
                    footerViewHolder.loadLayout.setVisibility(View.GONE);
                    break;

            }
        }

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size() + 1;
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
        private final TextView tv_type_insurance; // 保险类别
        private final TextView tv_status_insurance; // 保险状态
        private final TextView tv_name; // 联系人姓名
        private final TextView tv_phone; // 联系电话
        private final TextView tv_date; // 预约时间

        public ItemViewHolder(View itemView) {
            super(itemView);
            tv_type_insurance = itemView.findViewById(R.id.tv_type_insurance);
            tv_status_insurance = itemView.findViewById(R.id.tv_status_insurance);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_phone = itemView.findViewById(R.id.tv_phone);
            tv_date = itemView.findViewById(R.id.tv_date);

        }

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


    public void AddHeaderItem(ArrayList<MyInsuranceItemModel> items) {
        list.addAll(0, items);
        notifyDataSetChanged();
    }

    public void AddFooterItem(ArrayList<MyInsuranceItemModel> items) {
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
