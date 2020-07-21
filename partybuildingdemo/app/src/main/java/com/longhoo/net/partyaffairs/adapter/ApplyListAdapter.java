package com.longhoo.net.partyaffairs.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.mine.bean.PartyFeeBean;
import com.longhoo.net.partyaffairs.bean.ApplyBean;
import com.longhoo.net.partyaffairs.ui.ApprovalActivity;
import com.longhoo.net.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ${CC} on 2017/12/4.
 */

public class ApplyListAdapter extends RecyclerView.Adapter {


    Context mContext;


    private List<ApplyBean.DataBean> list;

    public ApplyListAdapter(Context context, List<ApplyBean.DataBean> list) {
        this.mContext = context;
        this.list = list;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ApplyListAdapterHolder(LayoutInflater.from(mContext).inflate(R.layout.item_applylist, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int pos = position;
        if (pos < 0 || pos > list.size() - 1)
            return;
        if (holder instanceof ApplyListAdapterHolder) {
            final ApplyBean.DataBean bean = list.get(position);
//            status  0 待审核 1已通过   2未通过
            if (bean.getStatus().equals("0")) {
                ((ApplyListAdapterHolder) holder).tvTime.setText("待审核");
            }
            if (bean.getStatus().equals("1")) {
                ((ApplyListAdapterHolder) holder).tvTime.setText("已通过");
            }
            if (bean.getStatus().equals("2")) {
                ((ApplyListAdapterHolder) holder).tvTime.setText("未通过");
            }
            ((ApplyListAdapterHolder) holder).tvTitle.setText(bean.getName());
            ((ApplyListAdapterHolder) holder).lvAllview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("id", bean.getId());
                    intent.setClass(mContext, ApprovalActivity.class);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    class ApplyListAdapterHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.lv_allview)
        RelativeLayout lvAllview;

        public ApplyListAdapterHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
