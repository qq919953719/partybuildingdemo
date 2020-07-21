package com.longhoo.net.manageservice.adapter;

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
import com.longhoo.net.manageservice.bean.DocumentBean;

/**
 * Created by ck on 2017/7/5.
 * 通用的资讯类item适配器
 */

public class DocumentAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<DocumentBean.DataBean.NewsBean> list;
    private String tag;

    public DocumentAdapter(Context context, List<DocumentBean.DataBean.NewsBean> list) {
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
            DocumentBean.DataBean.NewsBean bean = list.get(position);
            ((HeadlineViewHolder) holder).itemTvTitle.setText(bean.getTitle()+"");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class HeadlineViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView itemTvTitle;
        public HeadlineViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
