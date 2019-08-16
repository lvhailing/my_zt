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
public class BillCenterAllRecycleAdapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<BillCenterListData> list;
    private Context mContext;


    public BillCenterAllRecycleAdapter2(Context context, ArrayList<BillCenterListData> list) {
        mContext = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_bill_center_list2, parent, false);
        return new BillCenterAllRecycleAdapter2.ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder viewHolder = (ItemViewHolder) holder;
            for (BillCenterListData item : list) {
                View timeView = LayoutInflater.from(mContext).inflate(R.layout.item_bill_center_list_time, null);
                TextView tv_time = timeView.findViewById(R.id.tv_month);
                tv_time.setText(item.time);
                viewHolder.ll_container.addView(timeView);

                List<BillCenterModelData> neiList = item.jsonData;
                for (BillCenterModelData neiItem : neiList) {
                    View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_bill_center_list, null);
                    TextView tv_card_title = itemView.findViewById(R.id.tv_card_title);
                    TextView tv_bill_time = itemView.findViewById(R.id.tv_bill_time);
                    TextView tv_money = itemView.findViewById(R.id.tv_money);
                    ImageView iv_left_category_card = itemView.findViewById(R.id.iv_left_category_card);

                    tv_card_title.setText(neiItem.title);
                    tv_bill_time.setText(neiItem.time);
                    tv_money.setText(neiItem.income);

                    if (neiItem.cardType.equals("bank")) {
                        iv_left_category_card.setBackgroundResource(R.mipmap.img_bill_center_bank_card); // 银行卡
                    } else if (neiItem.cardType.equals("salaryTreasure")) {
                        iv_left_category_card.setBackgroundResource(R.mipmap.img_bill_center_salary); // 工资宝
                    } else if (neiItem.cardType.equals("regularFinancial")) {
                        iv_left_category_card.setBackgroundResource(R.mipmap.img_bill_center_financial); // 定期理财
                    }
                    viewHolder.ll_container.addView(itemView);

                    initListener(viewHolder.itemView, neiItem.title);
                }
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

            pbLoad = (ProgressBar) itemView.findViewById(R.id.pbLoad);
            tvLoadText = (TextView) itemView.findViewById(R.id.tvLoadText);
            loadLayout = (LinearLayout) itemView.findViewById(R.id.loadLayout);
        }
    }

    @Override
    public int getItemCount() {
        return 1;
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
        notifyDataSetChanged();
    }
}
