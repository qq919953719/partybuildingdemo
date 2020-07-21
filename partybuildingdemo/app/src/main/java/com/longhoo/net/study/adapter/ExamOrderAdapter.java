package com.longhoo.net.study.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.study.bean.ExamOrderBean;
import com.longhoo.net.study.bean.ExaminationListBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ck on 2017/12/19.
 */

public class ExamOrderAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<ExaminationListBean.DataBean> list;

    public ExamOrderAdapter(Context context, List<ExaminationListBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ExaminationListViewHodler(LayoutInflater.from(context).inflate(R.layout.list_item_exam_order, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (position < 0 || position > list.size() - 1)
            return;
        if (holder instanceof ExaminationListViewHodler) {
            ((ExaminationListViewHodler) holder).tvTitle.setText(list.get(position).getName() + "");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ExaminationListViewHodler extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        public ExaminationListViewHodler(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
