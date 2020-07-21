package com.longhoo.net.manageservice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.manageservice.bean.MemberZiXunBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cc on 2017/7/5.
 * 通用的资讯类item适配器
 */

public class MemberZiXunAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<MemberZiXunBean.DataBean.ListBean> list;
    private String tag;

    public MemberZiXunAdapter(Context context, List<MemberZiXunBean.DataBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HeadlineViewHolder(LayoutInflater.from(context).inflate(R.layout.item_mumber_list, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position < 0 || position > list.size() - 1)
            return;
        if (holder instanceof HeadlineViewHolder) {
            MemberZiXunBean.DataBean.ListBean bean = list.get(position);
            ((HeadlineViewHolder) holder).tvTitle.setText(list.get(position).getTitle() + "");
            ((HeadlineViewHolder) holder).tvTime.setText(list.get(position).getAdd_time());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class HeadlineViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_time)
        TextView tvTime;

        public HeadlineViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
