package com.longhoo.net.study.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.study.bean.ExaminationListBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ck on 2017/12/19.
 */

public class ExaminationListAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<ExaminationListBean.DataBean> list;
    private String type;
    private OnTestClickListener onTestClickListener;

    public ExaminationListAdapter(Context context, List<ExaminationListBean.DataBean> list,String type) {
        this.context = context;
        this.list = list;
        this.type = type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ExaminationListViewHodler(LayoutInflater.from(context).inflate(R.layout.item_list_examination, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (position < 0 || position > list.size() - 1)
            return;
        if (holder instanceof ExaminationListViewHodler) {
            ((ExaminationListViewHodler) holder).tvTitle.setText(list.get(position).getName() + "");
            //((ExaminationListViewHodler) holder).tvContent.setText(list.get(position).getDesc() + "");
            ((ExaminationListViewHodler) holder).tvContent.setText("截止时间："+list.get(position).getEndtime());

            ((ExaminationListViewHodler) holder).tvTest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onTestClickListener != null) {
                        onTestClickListener.onTestClick(position);
                    }
                }
            });
            if(TextUtils.equals(type,"1")){ //在线考试
                if(list.get(position).getStatus()==0){
                    ((ExaminationListViewHodler) holder).tvTest.setText("进入测试");
                    ((ExaminationListViewHodler) holder).tvTest.setBackground(context.getResources().getDrawable(R.drawable.bg_red_solid_25));
                } else if(list.get(position).getStatus()==1){
                    ((ExaminationListViewHodler) holder).tvTest.setText("未开始");
                    ((ExaminationListViewHodler) holder).tvTest.setBackground(context.getResources().getDrawable(R.drawable.bg_disenabled_solid_25));
                }else if(list.get(position).getStatus()==2){
                    ((ExaminationListViewHodler) holder).tvTest.setText("已结束");
                    ((ExaminationListViewHodler) holder).tvTest.setBackground(context.getResources().getDrawable(R.drawable.bg_disenabled_solid_25));
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ExaminationListViewHodler extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_test)
        TextView tvTest;
        public ExaminationListViewHodler(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            if (TextUtils.equals(type, "0")) {  //自我测试
                tvTest.setText("进入学习");
            }else{
                tvTest.setText("进入测试");
            }
        }
    }

    public void setOnTestClickListener(OnTestClickListener onTestClickListener) {
        this.onTestClickListener = onTestClickListener;
    }

    public interface OnTestClickListener {
        void onTestClick(int position);
    }
}
