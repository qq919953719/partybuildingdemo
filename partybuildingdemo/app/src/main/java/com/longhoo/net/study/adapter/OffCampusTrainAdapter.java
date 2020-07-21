package com.longhoo.net.study.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.study.bean.OffCampusBean;
import com.longhoo.net.study.bean.TrainCourseBean;
import com.longhoo.net.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 校外培训
 */

public class OffCampusTrainAdapter extends RecyclerView.Adapter {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.ll_main_container)
    LinearLayout llMainContainer;
    private Context context;
    private List<OffCampusBean.DataBean> list;

    public OffCampusTrainAdapter(Context context, List<OffCampusBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OffCampusTrainViewHolder(LayoutInflater.from(context).inflate(R.layout.item_off_campus_train, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (position < 0 || position > list.size() - 1)
            return;
        if (holder instanceof OffCampusTrainViewHolder) {
            OffCampusBean.DataBean bean = list.get(position);
            ((OffCampusTrainViewHolder) holder).tvTitle.setText(bean.getName() + "");
            ((OffCampusTrainViewHolder) holder).tvTime.setText(bean.getAddtime());

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class OffCampusTrainViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.ll_main_container)
        LinearLayout llMainContainer;
        public OffCampusTrainViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
