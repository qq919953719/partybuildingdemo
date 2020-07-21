package com.longhoo.net.manageservice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.manageservice.bean.DataShareingBean;
import com.longhoo.net.manageservice.bean.DocumentBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 资料共享
 */

public class DataShareingAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<DataShareingBean.DataBeanX.DataBean> list;
    private String tag;

    public DataShareingAdapter(Context context, List<DataShareingBean.DataBeanX.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DataShareingViewHolder(LayoutInflater.from(context).inflate(R.layout.item_data_shareing, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position < 0 || position > list.size() - 1)
            return;
        if (holder instanceof DataShareingViewHolder) {
            DataShareingBean.DataBeanX.DataBean bean = list.get(position);
            ((DataShareingViewHolder) holder).tvTitle.setText(bean.getTitle() + "");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class DataShareingViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;

        public DataShareingViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
