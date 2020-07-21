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
import com.longhoo.net.study.bean.StudyItemBean;
import com.longhoo.net.study.bean.StudyRecordBean;
import com.longhoo.net.utils.GlideManager;
import com.longhoo.net.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 学习档案
 */

public class StudyRecordAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<StudyRecordBean.DataBean> list;

    public StudyRecordAdapter(Context context, List<StudyRecordBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new StudyRecordViewHolder(LayoutInflater.from(context).inflate(R.layout.item_study_record, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position < 0 || position > list.size() - 1)
            return;
        StudyRecordBean.DataBean bean = list.get(position);
        if (holder instanceof StudyRecordViewHolder) {
            ((StudyRecordViewHolder) holder).tvName.setText(bean.getRealname()+"");
            ((StudyRecordViewHolder) holder).tvAllScore.setText(bean.getCount()+"");
            ((StudyRecordViewHolder) holder).tvTrainScore.setText(bean.getClassCount()+"");
            ((StudyRecordViewHolder) holder).tvMyReportScore.setText(bean.getCourseCount()+"");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


   public class StudyRecordViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_all_score)
        TextView tvAllScore;
        @BindView(R.id.tv_train_score)
        TextView tvTrainScore;
        @BindView(R.id.tv_my_report_score)
        TextView tvMyReportScore;
        public StudyRecordViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
