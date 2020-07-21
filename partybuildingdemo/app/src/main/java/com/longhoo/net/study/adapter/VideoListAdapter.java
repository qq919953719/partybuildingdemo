package com.longhoo.net.study.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.study.bean.VideoListBean;
import com.longhoo.net.utils.GlideManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ck on 2017/7/5.
 * 通用的资讯类item适配器
 */

public class VideoListAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<VideoListBean.DataBean.ListBean> list;

    public VideoListAdapter(Context context, List<VideoListBean.DataBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VideoListViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list_video, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position < 0 || position > list.size() - 1)
            return;
        if (holder instanceof VideoListViewHolder) {
            VideoListBean.DataBean.ListBean bean = list.get(position);
            ((VideoListViewHolder) holder).itemTvTitle.setText(bean.getName() + "");

            if (!TextUtils.isEmpty(bean.getThumb())) {
                GlideManager.getInstance().load(context,bean.getThumb(),((VideoListViewHolder) holder).itemIvThumb);
            }
            //判断图文还是直播
            String type = list.get(position).getType();
            if(TextUtils.equals(type,"0")){
                ((VideoListViewHolder) holder).ivVideoIcon.setVisibility(View.GONE);
            }else{
                ((VideoListViewHolder) holder).ivVideoIcon.setVisibility(View.VISIBLE);
            }
            //判断是预告、直播还是回顾
            String isStart = list.get(position).getIstart();
            String isEnd = list.get(position).getIsend();
            if(TextUtils.equals(isStart,"0")){
                ((VideoListViewHolder) holder).itemTvTag.setText("预告");
                ((VideoListViewHolder) holder).itemTvTag.setBackgroundResource(R.drawable.bg_red_stroke_25);
                ((VideoListViewHolder) holder).itemTvTag.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            }else{
                if(TextUtils.equals(isEnd,"0")){
                    ((VideoListViewHolder) holder).itemTvTag.setText("正在直播");
                    ((VideoListViewHolder) holder).itemTvTag.setBackgroundResource(R.drawable.bg_red_solid_25);
                    ((VideoListViewHolder) holder).itemTvTag.setTextColor(context.getResources().getColor(R.color.white));
                }else{
                    ((VideoListViewHolder) holder).itemTvTag.setText("回顾");
                    ((VideoListViewHolder) holder).itemTvTag.setBackgroundResource(R.drawable.bg_red_stroke_25);
                    ((VideoListViewHolder) holder).itemTvTag.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                }
            }
            //FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) ((VideoListViewHolder) holder).itemTvTag.getLayoutParams();
            String score = bean.getScore();
            if(!TextUtils.isEmpty(score)){
                if(TextUtils.equals(score,"0")){
                    ((VideoListAdapter.VideoListViewHolder) holder).tvItemScore.setVisibility(View.GONE);
                    //lp.gravity = Gravity.LEFT;
                }else{
                    ((VideoListAdapter.VideoListViewHolder) holder).tvItemScore.setText("学习积分："+score);
                    ((VideoListAdapter.VideoListViewHolder) holder).tvItemScore.setVisibility(View.VISIBLE);
                    //lp.gravity = Gravity.RIGHT;
                }
            } else{
                ((VideoListAdapter.VideoListViewHolder) holder).tvItemScore.setVisibility(View.GONE);
                //lp.gravity = Gravity.RIGHT;
            }
            //((VideoListViewHolder) holder).itemTvTag.setLayoutParams(lp);
            if (list.size()>1&&position == list.size() - 1) {
                ((VideoListViewHolder) holder).line.setVisibility(View.GONE);
            }else{
                ((VideoListViewHolder) holder).line.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class VideoListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_iv_thumb)
        ImageView itemIvThumb;
        @BindView(R.id.item_tv_title)
        TextView itemTvTitle;
        @BindView(R.id.item_tv_tag)
        TextView itemTvTag;
        @BindView(R.id.iv_video_icon)
        ImageView ivVideoIcon;
        @BindView(R.id.item_tv_score)
        TextView tvItemScore;
        @BindView(R.id.line)
        View line;

        public VideoListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
