package com.longhoo.net.headline.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.longhoo.net.R;
import com.longhoo.net.headline.bean.SpecialNewsDetailBean;
import com.longhoo.net.utils.GlideManager;
import com.longhoo.net.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/5.
 */

public class SpecialNewsDetailAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<SpecialNewsDetailBean.DataBean.NewsBean> list;

    public SpecialNewsDetailAdapter(Context context, List<SpecialNewsDetailBean.DataBean.NewsBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SpecialViewHolder(LayoutInflater.from(context).inflate(R.layout.item_headline, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position < 0 || position > list.size() - 1)
            return;
        if (holder instanceof SpecialViewHolder) {
            SpecialNewsDetailBean.DataBean.NewsBean bean = list.get(position);
            ((SpecialNewsDetailAdapter.SpecialViewHolder) holder).itemTvTitle.setText(bean.getTitle()+"");
            ((SpecialNewsDetailAdapter.SpecialViewHolder) holder).itemTime.setText(Utils.getDataTime(bean.getTime()));
            ((SpecialNewsDetailAdapter.SpecialViewHolder) holder).itemTvSpecial.setVisibility(View.INVISIBLE);
            if(!TextUtils.isEmpty(bean.getPic())){
                GlideManager.getInstance().load(context,bean.getPic(),((SpecialViewHolder) holder).itemIvThumb);
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class SpecialViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_iv_thumb)
        ImageView itemIvThumb;
        @BindView(R.id.item_tv_title)
        TextView itemTvTitle;
        @BindView(R.id.item_special)
        TextView itemTvSpecial;
        @BindView(R.id.item_tv_time)
        TextView itemTime;
        public SpecialViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
