package com.longhoo.net.mine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.mine.bean.MyEnrollBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的报名
 */

public class MyEnrollAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<MyEnrollBean.DataBean> list;

    public MyEnrollAdapter(Context mContext, List<MyEnrollBean.DataBean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyEnrollHolder(LayoutInflater.from(mContext).inflate(R.layout.item_my_enroll, parent, false));
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int pos = position;
        if (pos < 0 || pos > list.size() - 1)
            return;
        if (holder instanceof MyEnrollHolder) {
            ((MyEnrollHolder) holder).tvTitle.setText(list.get(pos).getPtitle());
            ((MyEnrollHolder) holder).tvAddr.setText(list.get(pos).getAddr());
            ((MyEnrollHolder) holder).tvTime.setText(list.get(pos).getAddtime());
            ((MyEnrollHolder) holder).tvActTime.setText(list.get(pos).getCdate()+"-"+list.get(pos).getEnddate());
            ((MyEnrollHolder) holder).tvSignTime.setText(list.get(pos).getStime()+"-"+list.get(pos).getEtime());
        }
    }

    class MyEnrollHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_tag)
        TextView tvTag;
        @BindView(R.id.tv_sign_time)
        TextView tvSignTime;
        @BindView(R.id.tv_addr)
        TextView tvAddr;
        @BindView(R.id.tv_act_time)
        TextView tvActTime;
        public MyEnrollHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
