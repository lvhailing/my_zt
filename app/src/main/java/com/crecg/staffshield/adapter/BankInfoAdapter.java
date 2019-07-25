package com.crecg.staffshield.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.crecg.crecglibrary.network.model.BankProgressModel;
import com.crecg.staffshield.R;

import java.util.List;


/**
 * 账单进度adapter
 */
public class BankInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<BankProgressModel> list;
    private Context mContext;


    public BankInfoAdapter(Context context, List<BankProgressModel> list) {
        mContext = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_all_kinds_of_details, parent, false);
        return new BankInfoAdapter.ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        BankProgressModel item = list.get(position);

        //设置显示隐藏
        if (position == 0) {
            viewHolder.iv_top_line.setVisibility(View.INVISIBLE);
        }
        if (position == list.size() - 1) {
            viewHolder.iv_down_line.setVisibility(View.INVISIBLE);
        }

        //设置样式
        if (item.state == 1) {
            //是进展项
            viewHolder.iv_top_line.setBackgroundColor(mContext.getResources().getColor(R.color.main_blue_4A67F5));
            viewHolder.iv_down_line.setBackgroundColor(mContext.getResources().getColor(R.color.main_blue_4A67F5));
            viewHolder.iv_middle_circle.setBackground(mContext.getResources().getDrawable(R.mipmap.img_check_mark));
            viewHolder.tv_title.setTextColor(mContext.getResources().getColor(R.color.main_blue_4A67F5));
        } else {
            //不是进展项
            viewHolder.iv_top_line.setBackgroundColor(mContext.getResources().getColor(R.color.bg_line));
            viewHolder.iv_down_line.setBackgroundColor(mContext.getResources().getColor(R.color.bg_line));
            viewHolder.iv_middle_circle.setBackground(mContext.getResources().getDrawable(R.mipmap.img_circle_gray));
            viewHolder.tv_title.setTextColor(mContext.getResources().getColor(R.color.txt_black_999999));
        }

        //设置内容
        viewHolder.tv_title.setText(item.title);
        viewHolder.tv_date.setText(item.time);
    }

    /**
     * 账单+时间布局 viewHolder
     */
    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_top_line;
        private ImageView iv_middle_circle;
        private ImageView iv_down_line;
        private TextView tv_title;
        private TextView tv_date;

        public ItemViewHolder(View itemView) {
            super(itemView);
            iv_top_line = itemView.findViewById(R.id.iv_top_line);
            iv_middle_circle = itemView.findViewById(R.id.iv_middle_circle);
            iv_down_line = itemView.findViewById(R.id.iv_down_line);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_date = itemView.findViewById(R.id.tv_date);
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }
}
