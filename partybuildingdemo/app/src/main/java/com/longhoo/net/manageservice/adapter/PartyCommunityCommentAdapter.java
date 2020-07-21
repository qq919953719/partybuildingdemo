package com.longhoo.net.manageservice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.manageservice.bean.OrganizationallCommentBean;
import com.longhoo.net.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ${CC} on 2017/12/4.
 */

public class PartyCommunityCommentAdapter extends RecyclerView.Adapter {


    Context mContext;
    private List<OrganizationallCommentBean.DataBean.ComsBean> list;

    public PartyCommunityCommentAdapter(Context context, List<OrganizationallCommentBean.DataBean.ComsBean> list) {
        this.mContext = context;
        this.list = list;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PartyCommunityCommentAdapter.PartyCommunityAdapterHolder(LayoutInflater.from(mContext).inflate(R.layout.item_ccmtcomment, parent, false));
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int pos = position;
        if (pos < 0 || pos > list.size() - 1)
            return;
        if (holder instanceof PartyCommunityCommentAdapter.PartyCommunityAdapterHolder) {
            final OrganizationallCommentBean.DataBean.ComsBean bean = list.get(position);
            ((PartyCommunityAdapterHolder) holder).itemNickname.setText(bean.getRealname());
            ((PartyCommunityAdapterHolder) holder).itemTime.setText(Utils.getDataTimeMin(bean.getTime()));
            ((PartyCommunityAdapterHolder) holder).itemContent.setText(Utils.replaceTransference(bean.getInfo()));
        }
    }

    class PartyCommunityAdapterHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_time)
        TextView itemTime;
        @BindView(R.id.item_nickname)
        TextView itemNickname;
        @BindView(R.id.item_content)
        TextView itemContent;


        public PartyCommunityAdapterHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
