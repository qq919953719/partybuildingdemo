package com.longhoo.net.mine.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.mine.bean.PartyManageBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PartyManageAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<PartyManageBean.DataBean> dataList;

    public PartyManageAdapter(Context context, List<PartyManageBean.DataBean> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PartyManageViewHolder(LayoutInflater.from(context).inflate(R.layout.item_partyfee_manage, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PartyManageViewHolder) {
            ((PartyManageViewHolder) holder).tvName.setText(dataList.get(position).getUname());

            if(dataList.get(position).getStatus()==0){
                ((PartyManageViewHolder) holder).tvIfPay.setText("否");
                ((PartyManageViewHolder) holder).tvFee.setText(dataList.get(position).getYfee()+"");
            }else{
                ((PartyManageViewHolder) holder).tvIfPay.setText("是");
                ((PartyManageViewHolder) holder).tvFee.setText(dataList.get(position).getFee()+"");
            }
            ((PartyManageViewHolder) holder).tvMonth.setText(dataList.get(position).getMonth()+"");
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class PartyManageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_fee)
        TextView tvFee;
        @BindView(R.id.tv_if_pay)
        TextView tvIfPay;
        @BindView(R.id.tv_month)
        TextView tvMonth;
        public PartyManageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
