package com.longhoo.net.supervision.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.supervision.bean.ScoreRankBean;
import com.longhoo.net.supervision.bean.SecretReportBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 积分排名
 */
public class ScoreRankAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<ScoreRankBean.DataBeanX.DataBean> list;

    public ScoreRankAdapter(Context context, List<ScoreRankBean.DataBeanX.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ScoreRankViewHolder(LayoutInflater.from(context).inflate(R.layout.item_score_rank, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (position < 0 || position > list.size() - 1)
            return;
        if (holder instanceof ScoreRankViewHolder) {
            ScoreRankBean.DataBeanX.DataBean bean = list.get(position);
            ((ScoreRankViewHolder) holder).tvOrder.setText(bean.getNum());
            ((ScoreRankViewHolder) holder).tvName.setText(bean.getRealname());
            ((ScoreRankViewHolder) holder).tvScore.setText(bean.getScore()+"");
            ((ScoreRankViewHolder) holder).tvRank.setText(bean.getOrder()+"");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ScoreRankViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_order)
        TextView tvOrder;
        @BindView(R.id.tv_score)
        TextView tvScore;
        @BindView(R.id.tv_rank)
        TextView tvRank;
        @BindView(R.id.tv_name)
        TextView tvName;
        public ScoreRankViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
