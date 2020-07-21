package com.longhoo.net.supervision.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.supervision.bean.ActSituationBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 活动统计——支部党建活动情况
 */
public class ActSituationAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<ActSituationBean.DataBean> list;


    public ActSituationAdapter(Context context, List<ActSituationBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ActSstatisticsViewHolder(LayoutInflater.from(context).inflate(R.layout.item_act_statistics, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (position < 0 || position > list.size() - 1)
            return;
        if (holder instanceof ActSstatisticsViewHolder) {
            ActSituationBean.DataBean bean = list.get(position);
            ((ActSstatisticsViewHolder) holder).tvName.setText(bean.getTypename() + "");
            ((ActSstatisticsViewHolder) holder).tvZhibu.setText(bean.getOname());
            ((ActSstatisticsViewHolder) holder).tvNum.setText(bean.getNum()+"");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ActSstatisticsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_zhibu)
        TextView tvZhibu;
        @BindView(R.id.tv_num)
        TextView tvNum;
        public ActSstatisticsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
