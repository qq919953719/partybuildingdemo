package com.longhoo.net.partyaffairs.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.longhoo.net.R;
import com.longhoo.net.partyaffairs.bean.ScoreDetailBean;
import com.longhoo.net.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ck on 2017/12/18.
 */

public class ScoreDetailAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<ScoreDetailBean.DataBean> list;
    private String tag;

    public ScoreDetailAdapter(Context context, List<ScoreDetailBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HeadlineViewHolder(LayoutInflater.from(context).inflate(R.layout.item_score_detail, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position < 0 || position > list.size() - 1)
            return;
        if (holder instanceof HeadlineViewHolder) {
            ScoreDetailBean.DataBean bean = list.get(position);
            ((HeadlineViewHolder) holder).tvItemScore.setText(bean.getScore()+"");
            ((HeadlineViewHolder) holder).tvItemDescription.setText(bean.getNote()+"");
            ((HeadlineViewHolder) holder).tvTime.setText(Utils.getDataTime(bean.getTime()));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class HeadlineViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_item_score)
        TextView tvItemScore;
        @BindView(R.id.tv_item_description)
        TextView tvItemDescription;
        @BindView(R.id.item_tv_time)
        TextView tvTime;
        public HeadlineViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
