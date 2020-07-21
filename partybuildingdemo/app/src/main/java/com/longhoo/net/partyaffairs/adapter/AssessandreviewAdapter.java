package com.longhoo.net.partyaffairs.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.mine.bean.SignBean;
import com.longhoo.net.partyaffairs.bean.AssessmentandreviewBean;
import com.longhoo.net.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ${CC} on 2017/12/4.
 */

public class AssessandreviewAdapter extends RecyclerView.Adapter {


    Context mContext;


    private List<AssessmentandreviewBean.DataBean.AssessBean> list;

    public AssessandreviewAdapter(Context context, List<AssessmentandreviewBean.DataBean.AssessBean> list) {
        this.mContext = context;
        this.list = list;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AssessandreviewAdapterHolder(LayoutInflater.from(mContext).inflate(R.layout.item_assessmentreview, parent, false));
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int pos = position;
        if (pos < 0 || pos > list.size() - 1)
            return;
        if (holder instanceof AssessandreviewAdapterHolder) {
            final AssessmentandreviewBean.DataBean.AssessBean bean = list.get(position);
            ((AssessandreviewAdapterHolder) holder).tvRule.setText(bean.getInfo());

            ((AssessandreviewAdapterHolder) holder).tvScore.setText(bean.getScore());
            ((AssessandreviewAdapterHolder) holder).tvAutoscore.setText(bean.getScoresum());

        }
    }

    class AssessandreviewAdapterHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_rule)
        TextView tvRule;
        @BindView(R.id.tv_score)
        TextView tvScore;
        @BindView(R.id.tv_autoscore)
        TextView tvAutoscore;

        public AssessandreviewAdapterHolder(View itemView) {
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
