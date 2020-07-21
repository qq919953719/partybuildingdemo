package com.longhoo.net.study.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.study.bean.VideoConListBean;
import com.longhoo.net.utils.GlideManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 视频会议Adapter
 */

public class VideoConAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<VideoConListBean.DataBean.ListBean> list;
    private String tag;

    public VideoConAdapter(Context context, List<VideoConListBean.DataBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VideoConViewHolder(LayoutInflater.from(context).inflate(R.layout.item_video_conference, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position < 0 || position > list.size() - 1)
            return;
        if (holder instanceof VideoConViewHolder) {
            VideoConListBean.DataBean.ListBean bean = list.get(position);
            ((VideoConViewHolder) holder).itemTvTitle.setText(bean.getName() + "");
            GlideManager.getInstance().load(context, bean.getThumb(), ((VideoConViewHolder) holder).ivThumb);
            ((VideoConViewHolder) holder).tvViewNum.setText(bean.getHits() + "");
            ((VideoConViewHolder) holder).tvCommentNum.setText(bean.getComments() + "");
            ((VideoConViewHolder) holder).tvZanNum.setText(bean.getZan() + "");
            String isZan = bean.getIs_zan();
            if (TextUtils.equals(isZan, "1")) { //未点赞
                ((VideoConViewHolder) holder).ivZan.setSelected(false);
            }else{
                ((VideoConViewHolder) holder).ivZan.setSelected(true);
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class VideoConViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_tv_title)
        TextView itemTvTitle;
        @BindView(R.id.iv_thumb)
        ImageView ivThumb;
        @BindView(R.id.tv_view_num)
        TextView tvViewNum;
        @BindView(R.id.tv_comment_num)
        TextView tvCommentNum;
        @BindView(R.id.iv_zan)
        ImageView ivZan;
        @BindView(R.id.tv_zan_num)
        TextView tvZanNum;
        @BindView(R.id.zan_panel)
        LinearLayout zanPanel;

        public VideoConViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
