package com.longhoo.net.headline.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.longhoo.net.R;
import com.longhoo.net.headline.bean.PartybuildxfBean;
import com.longhoo.net.utils.GlideManager;

/**
 * Created by ck on 2017/12/4.
 */

public class PartybuildxfAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<PartybuildxfBean.DataBean.NewsBean> list;
    private OnSupportClickListener onSupprotClickListener;

    public PartybuildxfAdapter(Context context, List<PartybuildxfBean.DataBean.NewsBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HeadlineViewHolder(LayoutInflater.from(context).inflate(R.layout.item_partybuildxf, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (position < 0 || position > list.size() - 1)
            return;
        if (holder instanceof HeadlineViewHolder) {
            PartybuildxfBean.DataBean.NewsBean bean = list.get(position);
            ((HeadlineViewHolder) holder).itemTvName.setText(bean.getTitle()+"");
            ((HeadlineViewHolder) holder).itemTvContent.setText(bean.getDigest()+"");
            ((HeadlineViewHolder) holder).itemTvContent.setMaxLines(2);
            ((HeadlineViewHolder) holder).itemTvCount.setText(bean.getZan()+"人赞");
            if(TextUtils.equals(bean.getIszan(),"0")){
                ((HeadlineViewHolder) holder).itemIvZan.setImageResource(R.drawable.btn_zan);
                ((HeadlineViewHolder) holder).itemIvZan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //((HeadlineViewHolder) holder).itemIvZan.setImageResource(R.drawable.btn_yizan);
                        if (onSupprotClickListener != null) {
                            onSupprotClickListener.OnSupportClick(true,position);
                        }
                    }
                });
            }else{
                ((HeadlineViewHolder) holder).itemIvZan.setImageResource(R.drawable.btn_yizan);
                ((HeadlineViewHolder) holder).itemIvZan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //((HeadlineViewHolder) holder).itemIvZan.setImageResource(R.drawable.btn_zan);
                        if (onSupprotClickListener != null) {
                            onSupprotClickListener.OnSupportClick(false,position);
                        }
                    }
                });
            }
            if(!TextUtils.isEmpty(bean.getPic())){
                GlideManager.getInstance().load(context,bean.getPic(),((HeadlineViewHolder) holder).itemIvThumb);
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class HeadlineViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_thumb)
        ImageView itemIvThumb;
        @BindView(R.id.tv_name)
        TextView itemTvName;
        @BindView(R.id.tv_content)
        TextView itemTvContent;
        @BindView(R.id.tv_count)
        TextView itemTvCount;
        @BindView(R.id.iv_zan)
        ImageView itemIvZan;
        public HeadlineViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * 点赞点击事件
     * @param onSupprotClickListener
     */
    public void setOnSupprotClickListener(OnSupportClickListener onSupprotClickListener){
        this.onSupprotClickListener = onSupprotClickListener;
    }

    public interface OnSupportClickListener{
        void OnSupportClick(boolean shouldSupport,int position);
    }
}
