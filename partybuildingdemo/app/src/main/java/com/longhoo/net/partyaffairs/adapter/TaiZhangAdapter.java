package com.longhoo.net.partyaffairs.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.longhoo.net.R;
import com.longhoo.net.manageservice.adapter.ActsEncrollAdapter;
import com.longhoo.net.partyaffairs.bean.TaiZhangListBean;

/**
 * Created by ck on 2017/7/5.
 * 通用的资讯类item适配器
 */

public class TaiZhangAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<TaiZhangListBean.DataBean> list;
    private String tag;

    public TaiZhangAdapter(Context context, List<TaiZhangListBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HeadlineViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list_document, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position < 0 || position > list.size() - 1)
            return;
        if (holder instanceof HeadlineViewHolder) {
            TaiZhangListBean.DataBean bean = list.get(position);
            ((HeadlineViewHolder) holder).itemTvTitle.setText(bean.getContent() + "");
            if (list.get(position).getStatus() == 2) {
                ((HeadlineViewHolder) holder).itemTvTag.setBackground(context.getResources().getDrawable(R.drawable.bg_grey_solid_13));
                ((HeadlineViewHolder) holder).itemTvTag.setText("已取消");
            } else {
                ((HeadlineViewHolder) holder).itemTvTag.setBackground(context.getResources().getDrawable(R.drawable.bg_red_solid_13));
                ((HeadlineViewHolder) holder).itemTvTag.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class HeadlineViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView itemTvTitle;
        @BindView(R.id.tv_tag)
        TextView itemTvTag;

        public HeadlineViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
