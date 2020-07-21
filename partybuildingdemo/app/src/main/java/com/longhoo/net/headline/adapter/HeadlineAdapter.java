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
import com.longhoo.net.headline.bean.HeadlineBean;
import com.longhoo.net.utils.GlideManager;
import com.longhoo.net.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ${CC} on 2017/12/4
 * 党员社区.
 */

public class HeadlineAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<HeadlineBean.DataBean> list;

    public HeadlineAdapter(Context context, List<HeadlineBean.DataBean> list) {
        this.mContext = context;
        this.list = list;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HeadlineHolder(LayoutInflater.from(mContext).inflate(R.layout.item_headline2, parent, false));
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int pos = position;
        if (pos < 0 || pos > list.size() - 1)
            return;
        if (holder instanceof HeadlineHolder) {
            final HeadlineBean.DataBean bean = list.get(position);
            if (TextUtils.isEmpty(bean.getPic())) {
                ((HeadlineHolder) holder).itemIvThumb.setVisibility(View.GONE);
            } else {
                ((HeadlineHolder) holder).itemIvThumb.setVisibility(View.VISIBLE);
                GlideManager.getInstance().load(mContext, bean.getPic(), ((HeadlineHolder) holder).itemIvThumb);
            }

            ((HeadlineHolder) holder).itemTvTitle.setText(bean.getTitle());
            if (bean.getIs_top() == 0) {
                ((HeadlineHolder) holder).itemSpecial.setVisibility(View.GONE);
            } else {
                ((HeadlineHolder) holder).itemSpecial.setVisibility(View.VISIBLE);
            }

            ((HeadlineHolder) holder).itemTvTime.setText(Utils.getDataTime(bean.getTime() + ""));
            if (pos == getItemCount() - 1) {
                ((HeadlineHolder) holder).line.setVisibility(View.GONE);
            } else {
                ((HeadlineHolder) holder).line.setVisibility(View.VISIBLE);
            }
            if (bean.getType() == 2) {
                ((HeadlineHolder) holder).itemTvTag.setText("工作动态");
                ((HeadlineHolder) holder).lineTag.setVisibility(View.VISIBLE);
            } else if (bean.getType() == 3) {
                ((HeadlineHolder) holder).itemTvTag.setText("基层党建");
                ((HeadlineHolder) holder).lineTag.setVisibility(View.VISIBLE);
            } else if (bean.getType() == 4) {
                ((HeadlineHolder) holder).itemTvTag.setText("党风廉政");
                ((HeadlineHolder) holder).lineTag.setVisibility(View.VISIBLE);
            } else if (bean.getType() == 5) {
                ((HeadlineHolder) holder).itemTvTag.setText("干部工作");
                ((HeadlineHolder) holder).lineTag.setVisibility(View.VISIBLE);
            } else if (bean.getType() == 6) {
                ((HeadlineHolder) holder).itemTvTag.setText("时政要闻");
                ((HeadlineHolder) holder).lineTag.setVisibility(View.VISIBLE);
            } else {
                ((HeadlineHolder) holder).lineTag.setVisibility(View.GONE);
            }
        }
    }

    class HeadlineHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_iv_thumb)
        ImageView itemIvThumb;
        @BindView(R.id.item_special)
        TextView itemSpecial;
        @BindView(R.id.item_tv_title)
        TextView itemTvTitle;
        @BindView(R.id.item_tv_time)
        TextView itemTvTime;
        @BindView(R.id.item_tv_tag)
        TextView itemTvTag;
        @BindView(R.id.line)
        View line;
        @BindView(R.id.line_tag)
        View lineTag;

        public HeadlineHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
