package com.longhoo.net.mine.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.mine.bean.ReadNumBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cc on 2017/7/5.
 * 通用的资讯类item适配器
 */

public class ReadNumAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<ReadNumBean.DataBean> list;
    private String tag;


    public ReadNumAdapter(Context context, List<ReadNumBean.DataBean> list, String tag) {
        this.context = context;
        this.list = list;
        this.tag = tag;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HeadlineViewHolder(LayoutInflater.from(context).inflate(R.layout.item_read_num, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position < 0 || position > list.size() - 1)
            return;
        if (holder instanceof HeadlineViewHolder) {
            ReadNumBean.DataBean bean = list.get(position);
            if (TextUtils.isEmpty(tag)) {
                ((HeadlineViewHolder) holder).tvName.setVisibility(View.INVISIBLE);
            } else {
                ((HeadlineViewHolder) holder).tvName.setText(bean.getNum() + "");
                ((HeadlineViewHolder) holder).tvName.setVisibility(View.VISIBLE);
            }
            ((HeadlineViewHolder) holder).tvSort.setText(bean.getDate() + "");
            ((HeadlineViewHolder) holder).tvSort.setTextColor(Color.parseColor("#656565"));

            ((HeadlineViewHolder) holder).tvItemscore.setText(bean.getDuration() + "");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class HeadlineViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_itemsort)
        TextView tvSort;
        @BindView(R.id.tv_itemname)
        TextView tvName;
        @BindView(R.id.tv_itemscore)
        TextView tvItemscore;

        public HeadlineViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
