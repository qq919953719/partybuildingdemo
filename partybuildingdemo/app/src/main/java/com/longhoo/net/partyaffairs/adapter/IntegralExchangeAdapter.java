package com.longhoo.net.partyaffairs.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.partyaffairs.bean.IntegralExchangeBean;
import com.longhoo.net.utils.GlideManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ck on 2017/7/5.
 * 积分兑换item适配器
 */

public class IntegralExchangeAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<IntegralExchangeBean.DataBean> list;
    private String tag;
    private OnExchangeClickListener onExchangeClickListener;

    public IntegralExchangeAdapter(Context context, List<IntegralExchangeBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HeadlineViewHolder(LayoutInflater.from(context).inflate(R.layout.item_integral_exchange, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (position < 0 || position > list.size() - 1)
            return;
        if (holder instanceof HeadlineViewHolder) {
            IntegralExchangeBean.DataBean bean = list.get(position);
            ((HeadlineViewHolder) holder).tvItemTitle.setText(bean.getGname() + "");
            ((HeadlineViewHolder) holder).tvItemNum.setText(bean.getScore()+"积分");
            if(!TextUtils.isEmpty(bean.getThumb())){
                GlideManager.getInstance().load(context,bean.getThumb(),((HeadlineViewHolder) holder).ivItemThumb);
            }
            int store = 0;
            try {
                store = Integer.parseInt(bean.getStore());
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
            if(store>0){
                ((HeadlineViewHolder) holder).tvItemExchange.setBackgroundResource(R.drawable.bg_red_stroke_25);
                ((HeadlineViewHolder) holder).tvItemExchange.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                ((HeadlineViewHolder) holder).tvItemExchange.setText("兑换");
                ((HeadlineViewHolder) holder).tvItemExchange.setEnabled(true);
            }else{
                ((HeadlineViewHolder) holder).tvItemExchange.setBackgroundResource(R.drawable.bg_grey_solid_25);
                ((HeadlineViewHolder) holder).tvItemExchange.setTextColor(context.getResources().getColor(R.color.white));
                ((HeadlineViewHolder) holder).tvItemExchange.setText("库存不足");
                ((HeadlineViewHolder) holder).tvItemExchange.setEnabled(false);
            }
           ((HeadlineViewHolder) holder).tvItemExchange.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   if (onExchangeClickListener != null) {
                       onExchangeClickListener.onExchangeClick(position);
                   }
               }
           });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class HeadlineViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_item_thumb)
        ImageView ivItemThumb;
        @BindView(R.id.tv_item_title)
        TextView tvItemTitle;
        @BindView(R.id.tv_item_num)
        TextView tvItemNum;
        @BindView(R.id.tv_item_exchange)
        TextView tvItemExchange;
        public HeadlineViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setOnExchangeClickListener(OnExchangeClickListener onExchangeClickListener){
        this.onExchangeClickListener = onExchangeClickListener;
    }
    public interface OnExchangeClickListener{
        void onExchangeClick(int position);
    }
}
