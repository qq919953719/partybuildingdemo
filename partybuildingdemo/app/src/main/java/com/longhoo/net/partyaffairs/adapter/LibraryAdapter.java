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
import com.longhoo.net.headline.adapter.CustomItemAdapter;
import com.longhoo.net.partyaffairs.bean.IntegralExchangeBean;
import com.longhoo.net.partyaffairs.bean.LibraryBean;
import com.longhoo.net.utils.GlideManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ck on 2017/12/26.
 * 图书馆item适配器
 */

public class   LibraryAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<LibraryBean.DataBean> list;
    private OnExchangeClickListener onExchangeClickListener;

    public LibraryAdapter(Context context, List<LibraryBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LibraryViewHolder(LayoutInflater.from(context).inflate(R.layout.item_library, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (position < 0 || position > list.size() - 1)
            return;
        if (holder instanceof LibraryViewHolder) {
            LibraryBean.DataBean bean = list.get(position);
            ((LibraryViewHolder) holder).tvItemTitle.setText(bean.getGname() + "");
            ((LibraryViewHolder) holder).tvItemNum.setText(bean.getScore()+"积分");
            if(!TextUtils.isEmpty(bean.getThumb())){
                GlideManager.getInstance().load(context,bean.getThumb(),((LibraryViewHolder) holder).ivItemThumb);
            }

           String type = bean.getType();
            if(TextUtils.equals(type,"0")){
                ((LibraryViewHolder) holder).tvItemExchange.setBackgroundResource(R.drawable.bg_red_stroke_25);
                ((LibraryViewHolder) holder).tvItemExchange.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                ((LibraryViewHolder) holder).tvItemExchange.setText("可借");
                ((LibraryViewHolder) holder).tvItemExchange.setEnabled(true);
                ((LibraryViewHolder) holder).tvItemExchange.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (onExchangeClickListener != null) {
                            onExchangeClickListener.onExchangeClick(position);
                        }
                    }
                });
            }else if(TextUtils.equals(type,"1")){
                ((LibraryViewHolder) holder).tvItemExchange.setBackgroundResource(R.drawable.bg_red_solid_25);
                ((LibraryViewHolder) holder).tvItemExchange.setTextColor(context.getResources().getColor(R.color.white));
                ((LibraryViewHolder) holder).tvItemExchange.setText("已借");
                ((LibraryViewHolder) holder).tvItemExchange.setEnabled(false);
            }else if(TextUtils.equals(type,"2")){
                ((LibraryViewHolder) holder).tvItemExchange.setBackgroundResource(R.drawable.bg_disenabled_solid_25);
                ((LibraryViewHolder) holder).tvItemExchange.setTextColor(context.getResources().getColor(R.color.white));
                ((LibraryViewHolder) holder).tvItemExchange.setText("借出");
                ((LibraryViewHolder) holder).tvItemExchange.setEnabled(false);
            }else if(TextUtils.equals(type,"3")){
                ((LibraryViewHolder) holder).tvItemExchange.setBackgroundResource(R.drawable.bg_disenabled_solid_25);
                ((LibraryViewHolder) holder).tvItemExchange.setTextColor(context.getResources().getColor(R.color.white));
                ((LibraryViewHolder) holder).tvItemExchange.setText("审核中");
                ((LibraryViewHolder) holder).tvItemExchange.setEnabled(false);
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class LibraryViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_item_thumb)
        ImageView ivItemThumb;
        @BindView(R.id.tv_item_title)
        TextView tvItemTitle;
        @BindView(R.id.tv_item_num)
        TextView tvItemNum;
        @BindView(R.id.tv_item_exchange)
        TextView tvItemExchange;
        public LibraryViewHolder(View itemView) {
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
