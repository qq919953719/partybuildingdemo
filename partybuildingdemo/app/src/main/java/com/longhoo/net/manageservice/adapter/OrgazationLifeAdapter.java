package com.longhoo.net.manageservice.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.manageservice.bean.OrgazationLifeBean;
import com.longhoo.net.manageservice.ui.OrgazationLifeCointentActivity;
import com.longhoo.net.manageservice.ui.ThirdLifeCointentActivity;
import com.longhoo.net.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ${CC} on 2017/12/4.
 */

public class OrgazationLifeAdapter extends RecyclerView.Adapter {


    Context mContext;
    String type = "";

    private List<OrgazationLifeBean.DataBean.ListBean> list;

    public OrgazationLifeAdapter(Context context, List<OrgazationLifeBean.DataBean.ListBean> list, String type) {
        this.mContext = context;
        this.list = list;
        this.type = type;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PartyCommunityAdapterHolder(LayoutInflater.from(mContext).inflate(R.layout.item_democraticlife, parent, false));
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int pos = position;
        if (pos < 0 || pos > list.size() - 1)
            return;
        if (holder instanceof PartyCommunityAdapterHolder) {
            final OrgazationLifeBean.DataBean.ListBean bean = list.get(position);

            ((PartyCommunityAdapterHolder) holder).tvTitle.setText(bean.getContent());
            ((PartyCommunityAdapterHolder) holder).tvTime.setText(Utils.getDataTime(bean.getTime()));
            ((PartyCommunityAdapterHolder) holder).tvType.setText(bean.getType());
            ((PartyCommunityAdapterHolder) holder).tvType.setVisibility(View.VISIBLE);
            ((PartyCommunityAdapterHolder) holder).tvAllview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(mContext, OrgazationLifeCointentActivity.class);

                    if (type.equals("1")) {
                        intent.putExtra("lid", bean.getLid());
                    } else {
                        intent.putExtra("lid", bean.getTid());
                    }


                    intent.putExtra("type", type);
                    mContext.startActivity(intent);
                }
            });


        }
    }

    class PartyCommunityAdapterHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.lv_bottom)
        LinearLayout lvBottom;
        @BindView(R.id.tv_allview)
        LinearLayout tvAllview;
        @BindView(R.id.tv_type)
        TextView tvType;

        public PartyCommunityAdapterHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private OnGridItemClickListener onGridItemClickListener;

    public void setOnGridItemClickListener(OnGridItemClickListener onGridItemClickListener) {
        this.onGridItemClickListener = onGridItemClickListener;
    }

    public interface OnGridItemClickListener {
        void onGridItemClick(int listPosition, int gridPosition);
    }
}
