package com.longhoo.net.mine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.mine.bean.NoteListBean;
import com.longhoo.net.mine.ui.NoteListActivity;
import com.longhoo.net.mine.ui.NoteTakingActivity;
import com.longhoo.net.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ck on 2017/5/8.
 * 工作笔记
 */

public class NoteListAdapter extends RecyclerView.Adapter {
    private int type;
    private Context context;
    private List<NoteListBean.DataBean.BijiBean> list;

    public NoteListAdapter(Context context, List<NoteListBean.DataBean.BijiBean> list, int type) {
        this.context = context;
        this.list = list;
        this.type = type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NoteListViewHolder(LayoutInflater.from(context).inflate(R.layout.item_note_list, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (position < 0 || position > list.size() - 1)
            return;
        if (holder instanceof NoteListViewHolder) {
            NoteListBean.DataBean.BijiBean bean = list.get(position);
            ((NoteListViewHolder) holder).tvTitle.setText(bean.getTitle() + "");
            ((NoteListViewHolder) holder).tvTime.setText(Utils.getDataTime(bean.getTime(), "yyyy-MM-dd HH:mm"));
            ((NoteListViewHolder) holder).ivDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onDelClickListener != null) {
                        onDelClickListener.onDelClick(position);
                    }
                }
            });
            ((NoteListViewHolder) holder).llMainContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(position);
                    }
                }
            });
            if (type == NoteListActivity.TAG_MY_NOTE) {
                ((NoteListViewHolder) holder).fromPanel.setVisibility(View.GONE);
            }else{
                ((NoteListViewHolder) holder).fromPanel.setVisibility(View.VISIBLE);
                ((NoteListViewHolder) holder).tvFrom.setText(bean.getRealname());
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class NoteListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_from)
        TextView tvFrom;
        @BindView(R.id.from_panel)
        LinearLayout fromPanel;
        @BindView(R.id.ll_main_container)
        LinearLayout llMainContainer;
        @BindView(R.id.iv_del)
        ImageView ivDel;

        public NoteListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private OnDelClickListener onDelClickListener;

    public void setOnDelClickListener(OnDelClickListener onDelClickListener) {
        this.onDelClickListener = onDelClickListener;
    }

    public interface OnDelClickListener {
        void onDelClick(int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
