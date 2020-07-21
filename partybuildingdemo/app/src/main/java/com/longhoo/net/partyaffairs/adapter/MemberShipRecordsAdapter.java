package com.longhoo.net.partyaffairs.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.partyaffairs.bean.MemberShipRecordsBean;
import com.longhoo.net.utils.Util;
import com.longhoo.net.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ck on 2018/1/3.
 */

public class MemberShipRecordsAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<MemberShipRecordsBean.DataBean> list;

    public MemberShipRecordsAdapter(Context context, List<MemberShipRecordsBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecordsViewHolder(LayoutInflater.from(context).inflate(R.layout.item_membership_record, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (position < 0 || position > list.size() - 1)
            return;
        if (holder instanceof RecordsViewHolder) {
            MemberShipRecordsBean.DataBean bean = list.get(position);
            ((RecordsViewHolder) holder).tvTitle.setText(bean.getContent() + "");
            ((RecordsViewHolder) holder).tvMoney.setText(bean.getFee() + "元");
            ((RecordsViewHolder) holder).tvTime.setText(Utils.getDataTime(bean.getTime(),"MM-dd HH:mm"));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class RecordsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.tv_time)
        TextView tvTime;
        public RecordsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
