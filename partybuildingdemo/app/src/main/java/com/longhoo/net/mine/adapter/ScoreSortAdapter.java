package com.longhoo.net.mine.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.mine.bean.ScoreSortBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cc on 2017/7/5.
 * 通用的资讯类item适配器
 */

public class ScoreSortAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<ScoreSortBean.DataBean.InfoBean> list;
    private String tag;

    public ScoreSortAdapter(Context context, List<ScoreSortBean.DataBean.InfoBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HeadlineViewHolder(LayoutInflater.from(context).inflate(R.layout.item_scort_sort, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position < 0 || position > list.size() - 1)
            return;
        if (holder instanceof HeadlineViewHolder) {
            ScoreSortBean.DataBean.InfoBean bean = list.get(position);
            ((HeadlineViewHolder) holder).tvSort.setText(position + 1 + "");
            if (position == 0) {
                ((HeadlineViewHolder) holder).tvSort.setTextColor(Color.parseColor("#e71e1e"));
            } else if (position == 1) {
                ((HeadlineViewHolder) holder).tvSort.setTextColor(Color.parseColor("#fe7201"));
            } else if (position == 2) {
                ((HeadlineViewHolder) holder).tvSort.setTextColor(Color.parseColor("#5c7af8"));
            } else {
                ((HeadlineViewHolder) holder).tvSort.setTextColor(Color.parseColor("#656565"));
            }
            ((HeadlineViewHolder) holder).tvName.setText(list.get(position).getRealname() + "");
            ((HeadlineViewHolder) holder).tvItemscore.setText(list.get(position).getScore() + "");
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
