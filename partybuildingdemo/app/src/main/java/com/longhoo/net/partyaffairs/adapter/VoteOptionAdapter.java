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
import com.longhoo.net.partyaffairs.bean.VoteOptionBean;
import com.longhoo.net.utils.GlideManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ck on 2017/12/19.
 */

public class VoteOptionAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<VoteOptionBean.DataBean> list;
    private OnVoteClickListener onVoteClickListener;

    public VoteOptionAdapter(Context context, List<VoteOptionBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VoteOptionViewHodler(LayoutInflater.from(context).inflate(R.layout.item_vote_option, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (position < 0 || position > list.size() - 1)
            return;
        if (holder instanceof VoteOptionViewHodler) {
            ((VoteOptionViewHodler) holder).tvName.setText(list.get(position).getTitle() + "");
            ((VoteOptionViewHodler) holder).tvNum.setText(list.get(position).getNum() + "");
            String thumb = list.get(position).getThumbnail();
            if (!TextUtils.isEmpty(thumb)) {
                GlideManager.getInstance().load(context, thumb, ((VoteOptionViewHodler) holder).ivThumb);
            }
            if (list.get(position).getIs_zan().equals("0")) {
                ((VoteOptionViewHodler) holder).tvSignUp.setBackgroundResource(R.drawable.bg_red_solid_25);
                ((VoteOptionViewHodler) holder).tvSignUp.setText("给TA投票");
            } else {
                ((VoteOptionViewHodler) holder).tvSignUp.setBackgroundResource(R.drawable.bg_black_solid_25);
                ((VoteOptionViewHodler) holder).tvSignUp.setText("已投票");
            }

            ((VoteOptionViewHodler) holder).tvSignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onVoteClickListener != null) {
                        onVoteClickListener.OnVoteClick(position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class VoteOptionViewHodler extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_thumb)
        ImageView ivThumb;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_num)
        TextView tvNum;
        @BindView(R.id.tv_vote)
        TextView tvSignUp;

        public VoteOptionViewHodler(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setOnVoteClickListener(OnVoteClickListener onVoteClickListener) {
        this.onVoteClickListener = onVoteClickListener;
    }

    public interface OnVoteClickListener {
        void OnVoteClick(int position);
    }
}
