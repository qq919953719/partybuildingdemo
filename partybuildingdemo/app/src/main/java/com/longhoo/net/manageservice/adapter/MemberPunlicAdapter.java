package com.longhoo.net.manageservice.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.manageservice.bean.MemberPublicListBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cc on 2017/7/5.
 * 通用的资讯类item适配器
 */

public class MemberPunlicAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<MemberPublicListBean.DataBean.ListBean> list;
    private String tag;

    public MemberPunlicAdapter(Context context, List<MemberPublicListBean.DataBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HeadlineViewHolder(LayoutInflater.from(context).inflate(R.layout.item_mumber_applylist, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position < 0 || position > list.size() - 1)
            return;
        if (holder instanceof HeadlineViewHolder) {
            MemberPublicListBean.DataBean.ListBean bean = list.get(position);
            //check_status 审核状态  0审核中   1已通过   2驳回
            if (bean.getCheck_status().equals("0")) {
                ((HeadlineViewHolder) holder).tvState.setText("审核中");
                ((HeadlineViewHolder) holder).tvState.setTextColor(Color.parseColor("#EBB22B"));
                ((HeadlineViewHolder) holder).tvState.setBackgroundResource(R.drawable.bg_hui_stroke_6);
            }
            if (bean.getCheck_status().equals("1")) {
                ((HeadlineViewHolder) holder).tvState.setText("已通过");
                ((HeadlineViewHolder) holder).tvState.setBackgroundResource(R.drawable.bg_hui_cross_stroke_3);
                ((HeadlineViewHolder) holder).tvState.setTextColor(Color.parseColor("#e71e1e"));
            }
            if (bean.getCheck_status().equals("2")) {
                ((HeadlineViewHolder) holder).tvState.setText("已驳回");
                ((HeadlineViewHolder) holder).tvState.setBackgroundResource(R.drawable.bg_hui_stroke_3);
                ((HeadlineViewHolder) holder).tvState.setTextColor(Color.parseColor("#666666"));
            }
            ((HeadlineViewHolder) holder).tvTitle.setText(list.get(position).getName() + "");
            //((HeadlineViewHolder) holder).tvContent.setText(list.get(position).getRecommender() + "");
            ((HeadlineViewHolder) holder).tvTime.setText(list.get(position).getAddtime());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class HeadlineViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_state)
        TextView tvState;

        public HeadlineViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
