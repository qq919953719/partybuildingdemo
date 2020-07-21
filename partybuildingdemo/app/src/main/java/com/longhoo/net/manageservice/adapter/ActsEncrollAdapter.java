package com.longhoo.net.manageservice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.manageservice.bean.ActsEncrollBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * h活动报名
 */

public class ActsEncrollAdapter extends RecyclerView.Adapter {


    Context mContext;
    private List<ActsEncrollBean.DataBean> list;

    public ActsEncrollAdapter(Context context, List<ActsEncrollBean.DataBean> list) {
        this.mContext = context;
        this.list = list;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ActsEncrollAdapterHolder(LayoutInflater.from(mContext).inflate(R.layout.item_acts_enroll, parent, false));
    }

    public interface OnremoveListnner{
        void  ondelect(int i);
    }
    private OnremoveListnner onremoveListnner;

    public void setOnremoveListnner(OnremoveListnner onremoveListnner) {
        this.onremoveListnner = onremoveListnner;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final int pos = position;
        if (pos < 0 || pos > list.size() - 1)
            return;
        if (holder instanceof ActsEncrollAdapterHolder) {
            ((ActsEncrollAdapterHolder) holder).tvTitle.setText(list.get(position).getPtitle());
            ((ActsEncrollAdapterHolder) holder).tvTime.setText(list.get(position).getAddtime());
            ((ActsEncrollAdapterHolder) holder).tvSignTime.setText("报名时间：" + list.get(position).getStime() + "-" + list.get(position).getEtime());
            ((ActsEncrollAdapterHolder) holder).tvTrainSpace.setText("地    点：" + list.get(position).getAddr());
            ((ActsEncrollAdapterHolder) holder).tvTrainTime.setText("活动时间：" + list.get(position).getCdate() + "-" + list.get(position).getEnddate());
            if (list.get(position).getStyleid() == 2) {
                ((ActsEncrollAdapterHolder) holder).tvTag.setBackground(mContext.getResources().getDrawable(R.drawable.bg_grey_solid_13));
            } else {
                ((ActsEncrollAdapterHolder) holder).tvTag.setBackground(mContext.getResources().getDrawable(R.drawable.bg_red_solid_13));
            }
            ((ActsEncrollAdapterHolder) holder).tvTag.setText(list.get(position).getStylename());
            if (!TextUtils.isEmpty(list.get(position).getStatus())) {
                if (list.get(position).getStatus().equals("2")) {
                    ((ActsEncrollAdapterHolder) holder).tvTag.setText("已取消");
                    ((ActsEncrollAdapterHolder) holder).tvTag.setBackground(mContext.getResources().getDrawable(R.drawable.bg_grey_solid_13));
                }
            }
            ((ActsEncrollAdapterHolder) holder).lvAllView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    if (onremoveListnner!=null){
                        onremoveListnner.ondelect(position);
                    }
                    return true;
                }
            });





        }
    }

    class ActsEncrollAdapterHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_tag)
        TextView tvTag;
        @BindView(R.id.tv_sign_time)
        TextView tvSignTime;
        @BindView(R.id.tv_train_space)
        TextView tvTrainSpace;
        @BindView(R.id.tv_train_time)
        TextView tvTrainTime;
        @BindView(R.id.lv_AllView)
        LinearLayout lvAllView;

        public ActsEncrollAdapterHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
