package com.longhoo.net.study.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.study.bean.ExamOrderItemBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ck on 2017/12/19.
 */

public class ExamOrderItemAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<ExamOrderItemBean.DataBean.ListsBean> list;

    public ExamOrderItemAdapter(Context context, List<ExamOrderItemBean.DataBean.ListsBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ExaminationListViewHodler(LayoutInflater.from(context).inflate(R.layout.list_item_exam_order_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (position < 0 || position > list.size() - 1)
            return;
        if (holder instanceof ExaminationListViewHodler) {
            ((ExaminationListViewHodler) holder).tvName.setText(list.get(position).getUname() + "");
            ((ExaminationListViewHodler) holder).tvOrder.setText(list.get(position).getScore() + "分");
            ((ExaminationListViewHodler) holder).tvRank.setText((position+1)+"、");
            ((ExaminationListViewHodler) holder).tvPart.setText(list.get(position).getOname());
            ((ExaminationListViewHodler) holder).tvTime.setText(list.get(position).getTime());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ExaminationListViewHodler extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_rank)
        TextView tvRank;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_part)
        TextView tvPart;
        @BindView(R.id.tv_order)
        TextView tvOrder;
        @BindView(R.id.tv_time)
        TextView tvTime;

        public ExaminationListViewHodler(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
