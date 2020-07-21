package com.longhoo.net.manageservice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.longhoo.net.R;
import com.longhoo.net.manageservice.bean.DocumentBean;
import com.longhoo.net.manageservice.bean.ErWeimaSignBean;
import com.longhoo.net.utils.Utils;

/**
 * 二维码签到列表
 */

public class ErweimaSignAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<ErWeimaSignBean.DataBean.UserlistBean> list;
    private String tag;

    public ErweimaSignAdapter(Context context, List<ErWeimaSignBean.DataBean.UserlistBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ErWeimaSignViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_errweima_sign, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position < 0 || position > list.size() - 1)
            return;
        if (holder instanceof ErWeimaSignViewHolder) {
            ErWeimaSignBean.DataBean.UserlistBean bean = list.get(position);
            ((ErWeimaSignViewHolder) holder).tvSignName.setText(bean.getRealname()+"");
            ((ErWeimaSignViewHolder) holder).tvSignTime.setText(Utils.getDataTimeWithMinute(bean.getTime()+""));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ErWeimaSignViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_sign_name)
        TextView tvSignName;
        @BindView(R.id.tv_sign_time)
        TextView tvSignTime;
        public ErWeimaSignViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
