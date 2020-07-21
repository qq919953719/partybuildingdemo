package com.longhoo.net.mine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.mine.bean.MyTaskBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的任务
 */

public class MyTaskAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<MyTaskBean.DataBeanX.DataBean> list;

    public MyTaskAdapter(Context mContext, List<MyTaskBean.DataBeanX.DataBean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyTaskHolder(LayoutInflater.from(mContext).inflate(R.layout.item_my_task, parent, false));
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int pos = position;
        if (pos < 0 || pos > list.size() - 1)
            return;
        if (holder instanceof MyTaskHolder) {
            ((MyTaskHolder) holder).itemTvTitle.setText(list.get(pos).getTitle());
            ((MyTaskHolder) holder).itemTvTime.setText(list.get(pos).getTime());
            ((MyTaskHolder) holder).tvType.setText(list.get(pos).getTag());
            ((MyTaskHolder) holder).ivStatus.setVisibility(list.get(pos).getIs_read()==0?View.VISIBLE:View.GONE);
        }
    }

    class MyTaskHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_iv_thumb)
        ImageView itemIvThumb;
        @BindView(R.id.item_special)
        TextView itemSpecial;
        @BindView(R.id.item_tv_title)
        TextView itemTvTitle;
        @BindView(R.id.item_tv_time)
        TextView itemTvTime;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.line)
        View line;
        @BindView(R.id.iv_status)
        ImageView ivStatus;

        public MyTaskHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
