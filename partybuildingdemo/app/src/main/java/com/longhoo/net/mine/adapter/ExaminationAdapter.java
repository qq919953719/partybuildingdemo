package com.longhoo.net.mine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.mine.bean.MyExaminationBean;
import com.longhoo.net.study.ui.AnswerAnalysisActivity;
import com.longhoo.net.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ${CC} on 2017/12/4.
 */

public class ExaminationAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<MyExaminationBean.DataBean> list;

    public ExaminationAdapter(Context context, List<MyExaminationBean.DataBean> list) {
        this.mContext = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ExaminationAdapterHolder(LayoutInflater.from(mContext).inflate(R.layout.item_examination, parent, false));
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int pos = position;
        if (pos < 0 || pos > list.size() - 1)
            return;
        if (holder instanceof ExaminationAdapterHolder) {
            final MyExaminationBean.DataBean bean = list.get(position);
            ((ExaminationAdapterHolder) holder).tvTime.setText("考试时间：" + bean.getTime());

            ((ExaminationAdapterHolder) holder).tvScore.setText("考试成绩：" + bean.getScore() + "分");
            ((ExaminationAdapterHolder) holder).tvRight.setText("答对：" + bean.getRight() + "题");
            ((ExaminationAdapterHolder) holder).tvWrong.setText("答错：" + bean.getWrong() + "题");
//            ((ExaminationAdapterHolder) holder).tvContent.setText("答对：" + bean.getScore() + "分\n\n答对：" + bean.getRight() + "题\n\n答错：" + bean.getWrong() + "题");
            ((ExaminationAdapterHolder) holder).tvTitle.setText(bean.getName());
            ((ExaminationAdapterHolder) holder).lvAllview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int euid = bean.getEuid();
                    String title = bean.getName();
                    AnswerAnalysisActivity.goToMyTest(mContext,euid,title);
                }
            });

        }
    }

    class ExaminationAdapterHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_score)
        TextView tvScore;
        @BindView(R.id.tv_right)
        TextView tvRight;
        @BindView(R.id.tv_wrong)
        TextView tvWrong;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.lv_allview)
        LinearLayout lvAllview;

        public ExaminationAdapterHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
