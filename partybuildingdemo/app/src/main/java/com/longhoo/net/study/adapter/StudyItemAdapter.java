package com.longhoo.net.study.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.longhoo.net.R;
import com.longhoo.net.headline.bean.CustomItemBean;
import com.longhoo.net.study.bean.StudyItemBean;
import com.longhoo.net.utils.GlideManager;
import com.longhoo.net.utils.Utils;

/**
 * Created by ck on 2017/7/5.
 * 在线学习
 */

public class StudyItemAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<StudyItemBean.DataBean> list;

    public StudyItemAdapter(Context context, List<StudyItemBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HeadlineViewHolder(LayoutInflater.from(context).inflate(R.layout.item_costum, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position < 0 || position > list.size() - 1)
            return;
        if (holder instanceof HeadlineViewHolder) {
            StudyItemBean.DataBean bean = list.get(position);
            ((HeadlineViewHolder) holder).itemTvTitle.setText(bean.getTitle() + "");
            ((HeadlineViewHolder) holder).itemTime.setText(Utils.getDataTime(bean.getTime() + ""));
            ((HeadlineViewHolder) holder).itemTvSpecial.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(bean.getPic())) {
                ((HeadlineViewHolder) holder).itemIvThumb.setVisibility(View.VISIBLE);
                GlideManager.getInstance().load(context, bean.getPic(), ((HeadlineViewHolder) holder).itemIvThumb);
            } else {
                ((HeadlineViewHolder) holder).itemIvThumb.setVisibility(View.GONE);
            }
            int score = bean.getScore();
            if (score <= 0) {
                ((HeadlineViewHolder) holder).itemTvScore.setVisibility(View.GONE);
            } else {
                ((HeadlineViewHolder) holder).itemTvScore.setText("学习积分：" + score);
                ((HeadlineViewHolder) holder).itemTvScore.setVisibility(View.VISIBLE);
            }
            String tag = bean.getTag();
//            if(!TextUtils.isEmpty(tag)){
//                ((HeadlineViewHolder) holder).itemTag.setVisibility(View.VISIBLE);
//                ((HeadlineViewHolder) holder).itemTag.setText(tag);
//            }else{
//                ((HeadlineViewHolder) holder).itemTag.setVisibility(View.GONE);
//            }
            if (list.size() > 1 && position == list.size() - 1) {
                ((HeadlineViewHolder) holder).line.setVisibility(View.GONE);
            } else {
                ((HeadlineViewHolder) holder).line.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class HeadlineViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_iv_thumb)
        ImageView itemIvThumb;
        @BindView(R.id.item_tv_title)
        TextView itemTvTitle;
        @BindView(R.id.item_special)
        TextView itemTvSpecial;
        @BindView(R.id.item_tv_time)
        TextView itemTime;
        @BindView(R.id.item_tv_score)
        TextView itemTvScore;
        @BindView(R.id.item_tv_tag)
        TextView itemTag;
        @BindView(R.id.line)
        View line;

        public HeadlineViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
