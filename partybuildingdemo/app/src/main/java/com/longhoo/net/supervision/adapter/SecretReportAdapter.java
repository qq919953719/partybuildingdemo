package com.longhoo.net.supervision.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.mine.bean.NoteListBean;
import com.longhoo.net.mine.ui.NoteListActivity;
import com.longhoo.net.supervision.bean.SecretReportBean;
import com.longhoo.net.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 书记述职
 */

public class SecretReportAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<SecretReportBean.DataBeanX.DataBean> list;

    public SecretReportAdapter(Context context, List<SecretReportBean.DataBeanX.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SecretReportViewHolder(LayoutInflater.from(context).inflate(R.layout.item_secret_report, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (position < 0 || position > list.size() - 1)
            return;
        if (holder instanceof SecretReportViewHolder) {
            SecretReportBean.DataBeanX.DataBean bean = list.get(position);
            ((SecretReportViewHolder) holder).tvTitle.setText(bean.getTitle() + "");
            ((SecretReportViewHolder) holder).tvTime.setText(bean.getTime());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class SecretReportViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.ll_main_container)
        LinearLayout llMainContainer;
        public SecretReportViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
