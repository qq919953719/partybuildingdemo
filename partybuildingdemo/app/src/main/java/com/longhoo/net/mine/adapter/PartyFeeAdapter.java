package com.longhoo.net.mine.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.mine.bean.PartyFeeBean;
import com.longhoo.net.manageservice.ui.ThirdLifeCointentActivity;
import com.longhoo.net.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ${CC} on 2017/12/4.
 */

public class PartyFeeAdapter extends RecyclerView.Adapter {


    Context mContext;

    private List<PartyFeeBean.DataBean> list;

    public PartyFeeAdapter(Context context, List<PartyFeeBean.DataBean> list) {
        this.mContext = context;
        this.list = list;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PartyCommunityAdapterHolder(LayoutInflater.from(mContext).inflate(R.layout.item_partyfee, parent, false));
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int pos = position;
        if (pos < 0 || pos > list.size() - 1)
            return;
        if (holder instanceof PartyCommunityAdapterHolder) {
            final PartyFeeBean.DataBean bean = list.get(position);
            ((PartyCommunityAdapterHolder) holder).tvName.setText(bean.getContent());
            ((PartyCommunityAdapterHolder) holder).tvTime.setText(bean.getTime()+"");
            ((PartyCommunityAdapterHolder) holder).tvFee.setText(bean.getFee()+"元");


        }
    }

    class PartyCommunityAdapterHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_fee)
        TextView tvFee;
        @BindView(R.id.lv_allview)
        LinearLayout lvAllview;

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
