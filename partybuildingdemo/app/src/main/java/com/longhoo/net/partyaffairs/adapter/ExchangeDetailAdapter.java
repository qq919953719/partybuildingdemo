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
import com.longhoo.net.partyaffairs.bean.ExchangeDetailBean;
import com.longhoo.net.utils.GlideManager;
import com.longhoo.net.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ck on 2017/12/18.
 */

public class ExchangeDetailAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<ExchangeDetailBean.DataBean> list;
    private String tag;

    public ExchangeDetailAdapter(Context context, List<ExchangeDetailBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HeadlineViewHolder(LayoutInflater.from(context).inflate(R.layout.item_exchange_detail, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position < 0 || position > list.size() - 1)
            return;
        if (holder instanceof HeadlineViewHolder) {
            ExchangeDetailBean.DataBean bean = list.get(position);
            ((HeadlineViewHolder) holder).itemTvTitle.setText(bean.getGname() + "");
            ((HeadlineViewHolder) holder).itemTvTime.setText(Utils.getDataTime(bean.getTime()));
            ((HeadlineViewHolder) holder).itemTvScore.setText(bean.getScore()+"积分");
            if (!TextUtils.isEmpty(bean.getThumb())) {
                GlideManager.getInstance().load(context,bean.getThumb(),((HeadlineViewHolder) holder).itemIvThumb);
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class HeadlineViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_iv_thumb)
        ImageView itemIvThumb;
        @BindView(R.id.item_tv_title)
        TextView itemTvTitle;
        @BindView(R.id.item_tv_time)
        TextView itemTvTime;
        @BindView(R.id.item_tv_score)
        TextView itemTvScore;

        public HeadlineViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
