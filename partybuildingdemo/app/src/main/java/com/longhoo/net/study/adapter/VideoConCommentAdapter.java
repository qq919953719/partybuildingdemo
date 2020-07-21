package com.longhoo.net.study.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.study.bean.VideoCommentBean;
import com.longhoo.net.study.bean.VideoConCommentBean;
import com.longhoo.net.utils.GlideManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ck on 2018/12/19.
 * 视频会议评论列表
 */

public class VideoConCommentAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<VideoConCommentBean.DataBean.ListBean> list;

    public VideoConCommentAdapter(Context context, List<VideoConCommentBean.DataBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VideoConCommentViewHolder(LayoutInflater.from(context).inflate(R.layout.item_comment_list, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (position < 0 || position > list.size() - 1)
            return;
        if (holder instanceof VideoConCommentViewHolder) {
            ((VideoConCommentViewHolder) holder).tvName.setText(list.get(position).getRealname() + "");
            ((VideoConCommentViewHolder) holder).tvContent.setText(list.get(position).getContent());
            ((VideoConCommentViewHolder) holder).tvTime.setText(list.get(position).getTime()+"");
            //GlideManager.getInstance().load(context,list.get(position).getHeadpic(),((VideoConCommentViewHolder) holder).ivHeadpic,R.drawable.ic_default_head);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class VideoConCommentViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_headpic)
        ImageView ivHeadpic;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_content)
        TextView tvContent;
        public VideoConCommentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
