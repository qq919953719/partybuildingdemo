package com.longhoo.net.mine.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.mine.bean.MyLibrayrBean;
import com.longhoo.net.partyaffairs.ui.LibraryDetailActivity;
import com.longhoo.net.utils.GlideManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ${CC} on 2017/12/4.
 */

public class PartyLibraryAdapter extends RecyclerView.Adapter {


    Context mContext;


    private List<MyLibrayrBean.DataBean> list;

    public PartyLibraryAdapter(Context context, List<MyLibrayrBean.DataBean> list) {
        this.mContext = context;
        this.list = list;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PartyLibraryAdapterHolder(LayoutInflater.from(mContext).inflate(R.layout.item_mylibrary, parent, false));
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int pos = position;
        if (pos < 0 || pos > list.size() - 1)
            return;
        if (holder instanceof PartyLibraryAdapterHolder) {
            final MyLibrayrBean.DataBean bean = list.get(position);
            ((PartyLibraryAdapterHolder) holder).tvTitle.setText(bean.getGname());
            ((PartyLibraryAdapterHolder) holder).tvTime.setText(bean.getRtime());
            ((PartyLibraryAdapterHolder) holder).tvState.setText("已借");
            GlideManager.getInstance().load(mContext,bean.getThumb(),((PartyLibraryAdapterHolder) holder).imgLib);
            ((PartyLibraryAdapterHolder) holder).allview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(mContext, LibraryDetailActivity.class);
                    intent.putExtra("library_gid", bean.getGid());
                    mContext.startActivity(intent);
                }
            });

        }
    }

    class PartyLibraryAdapterHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_lib)
        ImageView imgLib;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_state)
        TextView tvState;
        @BindView(R.id.allview)
        LinearLayout allview;

        public PartyLibraryAdapterHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
