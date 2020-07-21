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
import com.longhoo.net.manageservice.bean.DemocraticLifeBean;
import com.longhoo.net.manageservice.bean.OrganizationallCommentBean;
import com.longhoo.net.manageservice.ui.ThirdLifeCointentActivity;
import com.longhoo.net.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ${CC} on 2017/12/4.
 */

public class DemocraticLifeAdapter extends RecyclerView.Adapter {


    Context mContext;
    private List<DemocraticLifeBean.DataBean.ListBean> list;

    public DemocraticLifeAdapter(Context context, List<DemocraticLifeBean.DataBean.ListBean> list) {
        this.mContext = context;
        this.list = list;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DemocraticLifeAdapter.PartyCommunityAdapterHolder(LayoutInflater.from(mContext).inflate(R.layout.item_democraticlife, parent, false));
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int pos = position;
        if (pos < 0 || pos > list.size() - 1)
            return;
        if (holder instanceof DemocraticLifeAdapter.PartyCommunityAdapterHolder) {
            final DemocraticLifeBean.DataBean.ListBean bean = list.get(position);

            ((PartyCommunityAdapterHolder) holder).tvTitle.setText(bean.getContent());
            ((PartyCommunityAdapterHolder) holder).tvTime.setText(Utils.getDataTime(bean.getTime()));

            ((PartyCommunityAdapterHolder) holder).tvAllview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(mContext, ThirdLifeCointentActivity.class);
                    intent.putExtra("did",bean.getDid());
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
