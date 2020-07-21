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
import com.longhoo.net.study.bean.PartyClassDisplayListBean;
import com.longhoo.net.utils.GlideManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 党课展示Adapter
 */

public class PartyClassDisplayAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<PartyClassDisplayListBean.DataBean> list;
    private String tag;

    public PartyClassDisplayAdapter(Context context, List<PartyClassDisplayListBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PartyClassDisplayViewHolder(LayoutInflater.from(context).inflate(R.layout.item_party_class_display, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (position < 0 || position > list.size() - 1)
            return;
        if (holder instanceof PartyClassDisplayViewHolder) {
            final PartyClassDisplayListBean.DataBean bean = list.get(position);
            ((PartyClassDisplayViewHolder) holder).itemTvTitle.setText(bean.getTitle() + "");
            GlideManager.getInstance().load(context, bean.getVideoimg(), ((PartyClassDisplayViewHolder) holder).ivThumb);
            if (TextUtils.isEmpty(bean.getVideoimg())) {
                ((PartyClassDisplayViewHolder) holder).ivThumb.setVisibility(View.GONE);
            } else {
                ((PartyClassDisplayViewHolder) holder).ivThumb.setVisibility(View.VISIBLE);
                if (TextUtils.isEmpty(bean.getVideourl())) {
                    ((PartyClassDisplayViewHolder) holder).videoImage.setVisibility(View.GONE);
                } else {
                    ((PartyClassDisplayViewHolder) holder).videoImage.setVisibility(View.VISIBLE);
                }
            }


//            ((PartyClassDisplayViewHolder) holder).zanPanel.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (onZanClickListener != null) {
//                        onZanClickListener.onZanClick(position);
//                    }
//                    int fabNum = bean.getFab_num();
//                    if (((PartyClassDisplayViewHolder) holder).ivZan.isSelected()) {
//                        ((PartyClassDisplayViewHolder) holder).ivZan.setSelected(false);
//                        bean.setFab_num(--fabNum);
//                        ((PartyClassDisplayViewHolder) holder).tvZanNum.setText(String.valueOf(fabNum<=0?0:fabNum));
//                    }else{
//                        ((PartyClassDisplayViewHolder) holder).ivZan.setSelected(true);
//                        bean.setFab_num(++fabNum);
//                        ((PartyClassDisplayViewHolder) holder).tvZanNum.setText(String.valueOf(fabNum));
//                    }
//                }
//            });
            ((PartyClassDisplayViewHolder) holder).tvViewNum.setText(bean.getRed_num() + "");
            ((PartyClassDisplayViewHolder) holder).tvCommentNum.setText(bean.getCom_num() + "");
            ((PartyClassDisplayViewHolder) holder).tvZanNum.setText(bean.getFab_num() + "");
            ((PartyClassDisplayViewHolder) holder).tvScore.setText("学习积分：" + bean.getPoint() + "");
            if (bean.getPoint().equals("0")) {
                ((PartyClassDisplayViewHolder) holder).tvScore.setVisibility(View.GONE);
            } else {
                ((PartyClassDisplayViewHolder) holder).tvScore.setVisibility(View.VISIBLE);
            }

            int isFab = bean.getIs_fab();
            if (isFab == 0) {
                //未点赞
                ((PartyClassDisplayViewHolder) holder).ivZan.setSelected(false);
            } else {
                //已点赞
                ((PartyClassDisplayViewHolder) holder).ivZan.setSelected(true);
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class PartyClassDisplayViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_tv_title)
        TextView itemTvTitle;
        @BindView(R.id.iv_thumb)
        ImageView ivThumb;
        @BindView(R.id.video_image)
        ImageView videoImage;


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
        @BindView(R.id.tv_score)
        TextView tvScore;

        public PartyClassDisplayViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private OnZanClickListener onZanClickListener;

    public void setOnZanClickListener(OnZanClickListener onZanClickListener) {
        this.onZanClickListener = onZanClickListener;
    }

    public interface OnZanClickListener {
        void onZanClick(int position);
    }
}
