package com.longhoo.net.partyaffairs.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.partyaffairs.bean.VoteListBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cc on 2017/12/19.
 */

public class VoteListAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<VoteListBean.DataBean> list;

    public VoteListAdapter(Context context, List<VoteListBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VoteListViewHodler(LayoutInflater.from(context).inflate(R.layout.item_vote_list, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position < 0 || position > list.size() - 1)
            return;
        if (holder instanceof VoteListViewHodler) {
            ((VoteListViewHodler) holder).tvTitle.setText(list.get(position).getName() + "");
            ((VoteListViewHodler) holder).tvContent.setText(list.get(position).getContent() + "");
            ((VoteListViewHodler) holder).tvTime.setText(list.get(position).getAddtime());
            //  ((VoteListViewHodler) holder).tvTime.setText(list.get(position).getStatus());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class VoteListViewHodler extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_time)
        TextView tvTime;

        public VoteListViewHodler(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
