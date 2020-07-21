package com.longhoo.net.supervision.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.supervision.bean.ActSituationBean;
import com.longhoo.net.supervision.bean.PartyfeeSituationBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 活动统计——党费缴纳情况
 */
public class PartyfeeSituationAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<PartyfeeSituationBean.DataBean> list;


    public PartyfeeSituationAdapter(Context context, List<PartyfeeSituationBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PartyfeeSituaViewHolder(LayoutInflater.from(context).inflate(R.layout.item_partyfee_situa, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (position < 0 || position > list.size() - 1)
            return;
        if (holder instanceof PartyfeeSituaViewHolder) {
            PartyfeeSituationBean.DataBean bean = list.get(position);
            ((PartyfeeSituaViewHolder) holder).tvPayed.setText(bean.getYes() + "");
            ((PartyfeeSituaViewHolder) holder).tvZhibu.setText(bean.getName());
            ((PartyfeeSituaViewHolder) holder).tvUnpayed.setText(bean.getNo()+"");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class PartyfeeSituaViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_zhibu)
        TextView tvZhibu;
        @BindView(R.id.tv_payed)
        TextView tvPayed;
        @BindView(R.id.tv_unpayed)
        TextView tvUnpayed;
        public PartyfeeSituaViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
