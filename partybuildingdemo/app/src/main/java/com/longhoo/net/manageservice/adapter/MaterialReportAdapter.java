package com.longhoo.net.manageservice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.manageservice.bean.MaterialReportListBean;
import com.longhoo.net.utils.DisplayUtil;
import com.longhoo.net.utils.GlideManager;
import com.longhoo.net.utils.Utils;
import com.longhoo.net.widget.CustomGridView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.longhoo.net.manageservice.ui.MaterialReportFragment.TYPE_GROUP;
import static com.longhoo.net.manageservice.ui.MaterialReportFragment.TYPE_PERSONAL;
import static com.longhoo.net.manageservice.ui.MaterialReportFragment.TYPE_SPECIAL;

public class MaterialReportAdapter extends RecyclerView.Adapter {
    private String type;
    private Context context;
    private List<MaterialReportListBean.DataBeanX.DataBean> list;
    private OnGridItemClickListener onGridItemClickListener;

    public MaterialReportAdapter(Context context, List<MaterialReportListBean.DataBeanX.DataBean> list, String type) {
        this.context = context;
        this.list = list;
        this.type = type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int resourse;
        if (viewType == 1) {
            resourse = R.layout.item_material_report;
            return new MaterialReportViewHolder(LayoutInflater.from(context).inflate(resourse, parent, false));
        } else {
            resourse = R.layout.item_material_report_2;
            return new MaterialReportViewHolder2(LayoutInflater.from(context).inflate(resourse, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int pos = position;
        if (position < 0 || position > list.size() - 1)
            return;
        if (holder instanceof MaterialReportViewHolder) {
            MaterialReportListBean.DataBeanX.DataBean bean = list.get(position);
            ((MaterialReportViewHolder) holder).tvTitle.setText(bean.getTitle() + "");
            ((MaterialReportViewHolder) holder).tvContent.setText(Html.fromHtml(bean.getContent()+""));
            ((MaterialReportViewHolder) holder).tvTime.setText(bean.getTime());
            ((MaterialReportViewHolder) holder).tvItemTag.setText(bean.getName());
        } else if (holder instanceof MaterialReportViewHolder2) {
            MaterialReportListBean.DataBeanX.DataBean bean = list.get(position);
            ((MaterialReportViewHolder2) holder).tvTitle.setText(bean.getReward_name() + "");
            ((MaterialReportViewHolder2) holder).tvTime.setText(bean.getTime());

            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) ((MaterialReportViewHolder2) holder).gridView.getLayoutParams();
            int width,height;
            List<String> picList = bean.getFiles();
            GridViewAdapter adapter = new GridViewAdapter(context, picList);
            if (picList.size() <= 0) {
                ((MaterialReportViewHolder2) holder).gridView.setVisibility(View.GONE);
            } else {

                if (picList.size() == 1) {
                    ((MaterialReportViewHolder2) holder).gridView.setVisibility(View.VISIBLE);
                    ((MaterialReportViewHolder2) holder).gridView.setNumColumns(1);
                    width = Utils.getDeviceSize(context).x - DisplayUtil.dp2px(context, 30);
                    lp.width = RecyclerView.LayoutParams.MATCH_PARENT;
                    height = width * 3 / 5;
                } else if (picList.size() == 2 || picList.size() == 4) {
                    ((MaterialReportViewHolder2) holder).gridView.setVisibility(View.VISIBLE);
                    ((MaterialReportViewHolder2) holder).gridView.setNumColumns(2);
                    width = (Utils.getDeviceSize(context).x - DisplayUtil.dp2px(context, 38)) / 2;
                    lp.width = width * 2 + DisplayUtil.dp2px(context, 4);
                    height = width * 3 / 4;

                } else {
                    ((MaterialReportViewHolder2) holder).gridView.setVisibility(View.VISIBLE);
                    ((MaterialReportViewHolder2) holder).gridView.setNumColumns(3);
                    width = (Utils.getDeviceSize(context).x - DisplayUtil.dp2px(context, 38)) / 3;
                    lp.width = RecyclerView.LayoutParams.MATCH_PARENT;
                    height = width * 3 / 4;
                }
                ((MaterialReportViewHolder2) holder).gridView.setLayoutParams(lp);

                adapter.setItemHeight(height);
                ((MaterialReportViewHolder2) holder).gridView.setAdapter(adapter);
                ((MaterialReportViewHolder2) holder).gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (onGridItemClickListener != null) {
                            onGridItemClickListener.onGridItemClick(pos,position);
                        }
                    }
                });
            }
            RecyclerView.LayoutParams containerLp = (RecyclerView.LayoutParams) ((MaterialReportViewHolder2) holder).container.getLayoutParams();
            if (position ==list.size()-1) {
                containerLp.bottomMargin = DisplayUtil.dp2px(context,60);
            }else{
                containerLp.bottomMargin = 0;
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (TextUtils.equals(type, TYPE_SPECIAL)) {
            return 1;
        } else if (TextUtils.equals(type, TYPE_GROUP)) {
            return 2;
        } else if (TextUtils.equals(type, TYPE_PERSONAL)) {
            return 3;
        }
        return 1;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MaterialReportViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_item_tag)
        TextView tvItemTag;
        @BindView(R.id.tv_time)
        TextView tvTime;

        public MaterialReportViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class MaterialReportViewHolder2 extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.item_grid_view)
        CustomGridView gridView;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.container)
        LinearLayout container;

        public MaterialReportViewHolder2(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * 网格item点击
     * @param onGridItemClickListemer
     */
    public void setOnGridItemClickListemer(OnGridItemClickListener onGridItemClickListemer){
        this.onGridItemClickListener = onGridItemClickListemer;
    }
    public interface OnGridItemClickListener{
        void onGridItemClick(int listPosition,int position);
    }
}
