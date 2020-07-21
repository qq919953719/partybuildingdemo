package com.longhoo.net.study.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.study.bean.SelfAnswerAnalysisBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by CK on 2017/12/26.
 * Email:910663958@qq.com
 */

public class SelfExamAnalysisAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<SelfAnswerAnalysisBean.DataBean.ListBean> dataList;

    public SelfExamAnalysisAdapter(Context context, List<SelfAnswerAnalysisBean.DataBean.ListBean> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AnswerViewHolder(LayoutInflater.from(context).inflate(R.layout.item_self_answer_analysis, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position < 0 || position > dataList.size() - 1) {
            return;
        }
        if (holder instanceof AnswerViewHolder) {
            ((AnswerViewHolder) holder).tvRight.setText("正确答案："+dataList.get(position).getAnswershow()+"");
            ((AnswerViewHolder) holder).tvAnalysis.setText(dataList.get(position).getAnswerinfo()+"");
            ((AnswerViewHolder) holder).tvTitle.setText((position+1) + "、"+dataList.get(position).getName());
            int type = dataList.get(position).getType();
            String [] selectionList = dataList.get(position).getSelections().split("\\|");
            if (type==1) {
                int userAnswer=0;
                initRadioGroup(selectionList, (userAnswer-1),(AnswerViewHolder) holder);
            } else if (type==2) {
                int []answers=null;
                initCheckBox(selectionList, answers,(AnswerViewHolder) holder);
            }
        }
    }


    private void initRadioGroup(String [] selectionList,int userAnswer,AnswerViewHolder holder) {
        if (selectionList == null) {
            return;
        }
        holder.container.removeAllViews();
        LinearLayout.LayoutParams flp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        RadioGroup radioGroup = new RadioGroup(context);
        int size = selectionList.length;
        for (int i = 0; i < size; i++) {
            final int index = i;
            View view = LayoutInflater.from(context).inflate(R.layout.layout_radio_button, null);
            RadioButton radioButton = (RadioButton) view.findViewById(R.id.radio_button);
            RadioGroup.LayoutParams lp = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            radioButton.setLayoutParams(lp);
            radioButton.setId(index);
            radioButton.setText(selectionList[i].trim());
            radioButton.setEnabled(false);
            radioGroup.addView(view);
            if(i==userAnswer){
                radioButton.setChecked(true);
            }
        }
        holder.container.addView(radioGroup);
    }

    private void initCheckBox(String [] selectionList,int [] userAnswer,AnswerViewHolder holder) {
        if (selectionList == null) {
            return;
        }
        holder.container.removeAllViews();
        int size = selectionList.length;
        for (int i = 0; i < size; i++) {
            final int index = i;
            final View view = LayoutInflater.from(context).inflate(R.layout.layout_checkbox, null);
            CheckBox checkBox = (CheckBox) view.findViewById(R.id.check_box);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            checkBox.setLayoutParams(lp);
            checkBox.setId(index);
            checkBox.setText(selectionList[i].trim());
            checkBox.setEnabled(false);
            if (userAnswer != null) {
                for(int j=0;j<userAnswer.length;j++){
                    if (userAnswer[j] == i) {
                        checkBox.setChecked(true);
                    }
                }
            }
            holder.container.addView(view);
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class AnswerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.container)
        LinearLayout container;
        @BindView(R.id.tv_right)
        TextView tvRight;
        @BindView(R.id.tv_analysis)
        TextView tvAnalysis;
        public AnswerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
