package com.longhoo.net.mine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.mine.bean.SignBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ${CC} on 2017/12/4.
 */

public class PartySignAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<SignBean.DataBean> list;

    public PartySignAdapter(Context context, List<SignBean.DataBean> list) {
        this.mContext = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PartySignAdapterHolder(LayoutInflater.from(mContext).inflate(R.layout.item_sign, parent, false));
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int pos = position;
        if (pos < 0 || pos > list.size() - 1)
            return;
        if (holder instanceof PartySignAdapterHolder) {
            final SignBean.DataBean bean = list.get(position);
            ((PartySignAdapterHolder) holder).mettingthem.setText(bean.getTitle());
            ((PartySignAdapterHolder) holder).mettingtime.setText(bean.getTime());
            ((PartySignAdapterHolder) holder).tvAddTime.setText(bean.getAddtime());
            //1组织生活 2活动报名 3培训班报名
            if (bean.getType()==1) {
                ((PartySignAdapterHolder) holder).tvType.setText("组织生活");
                ((PartySignAdapterHolder) holder).tvTagTitle.setText("会议主题：");
                ((PartySignAdapterHolder) holder).tvTagTime.setText("会议时间：");
            } else if(bean.getType()==2){
                ((PartySignAdapterHolder) holder).tvType.setText("党员活动");
                ((PartySignAdapterHolder) holder).tvTagTitle.setText("活动主题：");
                ((PartySignAdapterHolder) holder).tvTagTime.setText("活动时间：");
            }else{
                ((PartySignAdapterHolder) holder).tvType.setText("培训班报名");
                ((PartySignAdapterHolder) holder).tvTagTitle.setText("培训班名称：");
                ((PartySignAdapterHolder) holder).tvTagTime.setText("培训时间：");
            }


        }
    }

    class PartySignAdapterHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.mettingthem)
        TextView mettingthem;
        @BindView(R.id.mettingtime)
        TextView mettingtime;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.tv_statues)
        TextView tvStatues;
        @BindView(R.id.lv_bottom)
        LinearLayout lvBottom;
        @BindView(R.id.lv_allview)
        LinearLayout lvAllview;
        @BindView(R.id.tv_tag_time)
        TextView tvTagTime;
        @BindView(R.id.tv_tag_title)
        TextView tvTagTitle;
        @BindView(R.id.tv_sign_time)
        TextView tvAddTime;

        public PartySignAdapterHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private OnGridItemClickListener onGridItemClickListener;

    public void setOnGridItemClickListener(OnGridItemClickListener onGridItemClickListener) {
        this.onGridItemClickListener = onGridItemClickListener;
    }

    public interface OnGridItemClickListener {
        void onGridItemClick(int listPosition, int gridPosition);
    }
}
