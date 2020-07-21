package com.longhoo.net.supervision.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.supervision.bean.OrgLifeArrangeBean;
import com.longhoo.net.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 活动统计——本周组织生活安排
 */
public class OrgLifeArrangeAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<OrgLifeArrangeBean.DataBean> list;


    public OrgLifeArrangeAdapter(Context context, List<OrgLifeArrangeBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OrgLifeArrangeViewHolder(LayoutInflater.from(context).inflate(R.layout.item_org_life_arrange, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (position < 0 || position > list.size() - 1)
            return;
        if (holder instanceof OrgLifeArrangeViewHolder) {
            OrgLifeArrangeBean.DataBean bean = list.get(position);
            ((OrgLifeArrangeViewHolder) holder).tvOname.setText(bean.getOname());
            ((OrgLifeArrangeViewHolder) holder).tvArrangeName.setText(bean.getContent());
            ((OrgLifeArrangeViewHolder) holder).tvAddress.setText(bean.getMeeting_address());

            ((OrgLifeArrangeViewHolder) holder).tvAttendMan.setText(bean.getUser_name());


            ((OrgLifeArrangeViewHolder) holder).tvTime.setText(Utils.getDataTimeMinute(bean.getMeeting_time() + ""));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class OrgLifeArrangeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_oname)
        TextView tvOname;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_arrange_name)
        TextView tvArrangeName;
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.tv_attend_man)
        TextView tvAttendMan;

        public OrgLifeArrangeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
