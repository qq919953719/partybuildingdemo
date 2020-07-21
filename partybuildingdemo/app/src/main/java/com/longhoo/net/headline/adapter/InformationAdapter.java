package com.longhoo.net.headline.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.longhoo.net.R;
import com.longhoo.net.headline.bean.InformationBean;
import com.longhoo.net.utils.Utils;

/**
 * Created by ck on 2017/7/5.
 * 公告
 */

public class InformationAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<InformationBean.DataBean.NewsBean> list;

    public InformationAdapter(Context context, List<InformationBean.DataBean.NewsBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new InformationViewHolder(LayoutInflater.from(context).inflate(R.layout.item_costum, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (position < 0 || position > list.size() - 1)
            return;
        if (holder instanceof InformationViewHolder) {
            InformationBean.DataBean.NewsBean bean = list.get(position);
            ((InformationViewHolder) holder).itemTvTitle.setText(bean.getTitle() + "");
            ((InformationViewHolder) holder).itemTime.setText(Utils.getDataTime(bean.getTime()));
            ((InformationViewHolder) holder).itemTvSpecial.setVisibility(View.GONE);
            ((InformationViewHolder) holder).itemIvThumb.setVisibility(View.GONE);
            ((InformationViewHolder) holder).itemTvTag.setVisibility(View.GONE);
            ((InformationViewHolder) holder).tvRead.setVisibility(View.VISIBLE);
            ((InformationViewHolder) holder).container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onContainerClickListener != null) {
                        ((InformationViewHolder) holder).tvRead.setText("已读");
                        ((InformationViewHolder) holder).tvRead.setBackground(context.getResources().getDrawable(R.drawable.bg_red_solid_13));
                        onContainerClickListener.onContainerClick(position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class InformationViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_iv_thumb)
        ImageView itemIvThumb;
        @BindView(R.id.item_tv_title)
        TextView itemTvTitle;
        @BindView(R.id.item_special)
        TextView itemTvSpecial;
        @BindView(R.id.item_tv_time)
        TextView itemTime;
        @BindView(R.id.item_tv_tag)
        TextView itemTvTag;
        @BindView(R.id.tv_read)
        TextView tvRead;
        @BindView(R.id.container)
        RelativeLayout container;

        public InformationViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private OnContainerClickListener onContainerClickListener;
    public void setOnContainerClickListener(OnContainerClickListener onContainerClickListener){
        this.onContainerClickListener = onContainerClickListener;
    }
    public interface OnContainerClickListener{
        void onContainerClick(int position);
    }
}
