package com.longhoo.net.study.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.study.bean.TrainCourseBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ck on 2018/12/5.
 */

public class TrainCourseAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<TrainCourseBean.DataBean> list;

    public TrainCourseAdapter(Context context, List<TrainCourseBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TrainCourseViewHolder(LayoutInflater.from(context).inflate(R.layout.item_train_course, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (position < 0 || position > list.size() - 1)
            return;
        if (holder instanceof TrainCourseViewHolder) {
            TrainCourseBean.DataBean bean = list.get(position);
            ((TrainCourseViewHolder) holder).tvTitle.setText(bean.getTitle() + "");
            ((TrainCourseViewHolder) holder).tvTime.setText(bean.getAdd_time());
            ((TrainCourseViewHolder) holder).tvSignTime.setText(bean.getEnroll_starttime() + "-" + bean.getEnroll_endtime());
            ((TrainCourseViewHolder) holder).tvTrainSpace.setText(bean.getAddr());
            ((TrainCourseViewHolder) holder).tvTrainTime.setText(bean.getTrain_starttime() + "-" + bean.getTrain_endtime());
            if(TextUtils.equals(bean.getStyleid(),"1")){
                //未开始
                ((TrainCourseViewHolder) holder).tvTag.setBackground(context.getResources().getDrawable(R.drawable.bg_grey_solid_13));
                ((TrainCourseViewHolder) holder).tvTag.setText(bean.getStylename());
            }else if(TextUtils.equals(bean.getStyleid(),"2")){
                //已结束
                ((TrainCourseViewHolder) holder).tvTag.setBackground(context.getResources().getDrawable(R.drawable.bg_grey_solid_13));
                ((TrainCourseViewHolder) holder).tvTag.setText(bean.getStylename());
            }else{
                ((TrainCourseViewHolder) holder).tvTag.setBackground(context.getResources().getDrawable(R.drawable.bg_red_solid_13));
                ((TrainCourseViewHolder) holder).tvTag.setText("进行中");
            }
            ((TrainCourseViewHolder) holder).tvStudyHour.setText(bean.getClass_hour()+"学时");

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class TrainCourseViewHolder extends RecyclerView.ViewHolder {
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
        @BindView(R.id.item_tv_study_hour)
        TextView tvStudyHour;
        public TrainCourseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
