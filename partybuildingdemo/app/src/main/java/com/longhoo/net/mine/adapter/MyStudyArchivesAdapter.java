package com.longhoo.net.mine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.mine.bean.MyStudyArchivesBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的学习档案
 */

public class MyStudyArchivesAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<MyStudyArchivesBean.DataBeanX.DataBean.ListBean> list;

    public MyStudyArchivesAdapter(Context mContext, List<MyStudyArchivesBean.DataBeanX.DataBean.ListBean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyStudyArchivesHolder(LayoutInflater.from(mContext).inflate(R.layout.item_my_study_archives, parent, false));
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int pos = position;
        if (pos < 0 || pos > list.size() - 1)
            return;
        if (holder instanceof MyStudyArchivesHolder) {
            ((MyStudyArchivesHolder) holder).tvStatus.setText(list.get(pos).getStatus()+"");
            ((MyStudyArchivesHolder) holder).tvContent.setText(list.get(pos).getContent());
            ((MyStudyArchivesHolder) holder).tvClassHour.setText(list.get(pos).getClass_hour()+"");
            ((MyStudyArchivesHolder) holder).tvUnit.setText(list.get(pos).getHost_unit());
        }
    }

    class MyStudyArchivesHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_status)
        TextView tvStatus;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_class_hour)
        TextView tvClassHour;
        @BindView(R.id.tv_unit)
        TextView tvUnit;

        public MyStudyArchivesHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
