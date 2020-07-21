package com.longhoo.net.manageservice.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.manageservice.bean.PartyCommunityBean;
import com.longhoo.net.manageservice.ui.HuaTiContentActivity;
import com.longhoo.net.utils.DisplayUtil;
import com.longhoo.net.utils.Utils;
import com.longhoo.net.widget.CustomGridView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ${CC} on 2017/12/4
 * 党员社区.
 */

public class PartyCommunityAdapter extends RecyclerView.Adapter {
    Context mContext;
    private OnGridItemClickListener onGridItemClickListener;
    private List<PartyCommunityBean.DataBean.SaidsBean> list;

    public PartyCommunityAdapter(Context context, List<PartyCommunityBean.DataBean.SaidsBean> list) {
        this.mContext = context;
        this.list = list;
    }

    public void AddCommunityAdapter(List<PartyCommunityBean.DataBean.SaidsBean> list) {
        this.list.clear();
        this.list = list;
    }

    public void AddAllCommunityAdapter(List<PartyCommunityBean.DataBean.SaidsBean> list) {
        this.list.addAll(list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PartyCommunityAdapter.PartyCommunityAdapterHolder(LayoutInflater.from(mContext).inflate(R.layout.item_partycommunity, parent, false));
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int pos = position;
        if (pos < 0 || pos > list.size() - 1)
            return;
        if (holder instanceof PartyCommunityAdapter.PartyCommunityAdapterHolder) {
            final PartyCommunityBean.DataBean.SaidsBean bean = list.get(position);


//            ((PartyCommunityAdapterHolder) holder).lvPtallview.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent();
//                    intent.setClass(mContext, HuaTiContentActivity.class);
//                    intent.putExtra("sid", bean.getSid());
//                    mContext.startActivity(intent);
//                }
//            });
            ((PartyCommunityAdapterHolder) holder).tvPtcTitle.setText(bean.getTitle());
            if (TextUtils.isEmpty(bean.getSignname())) {
                ((PartyCommunityAdapterHolder) holder).tvSign.setVisibility(View.GONE);
            } else {
                ((PartyCommunityAdapterHolder) holder).tvSign.setVisibility(View.VISIBLE);
                ((PartyCommunityAdapterHolder) holder).tvSign.setText(bean.getSignname());
            }
            ((PartyCommunityAdapterHolder) holder).tvPtcContent.setText(Utils.replaceTransference(bean.getDigest()));
            ((PartyCommunityAdapterHolder) holder).tvRelseaseName.setText(bean.getRealname());
            ((PartyCommunityAdapterHolder) holder).tvEyeCount.setText(bean.getBrowse());

            ((PartyCommunityAdapterHolder) holder).tvCommentCount.setText(bean.getCom());

            ((PartyCommunityAdapterHolder) holder).tvCollectionCount.setText(bean.getZan());
            if (bean.getIszan().equals("0")) {
                //没有赞
                ((PartyCommunityAdapterHolder) holder).imgCollection.setBackgroundResource(R.drawable.icon_collection);
            } else {
                //赞
                ((PartyCommunityAdapterHolder) holder).imgCollection.setBackgroundResource(R.drawable.icon_collectioned);
            }
            //加载网格图片
            List<String> picList = bean.getPic();
            GridViewAdapter adapter = new GridViewAdapter(mContext, picList);
            int width = 0;
            int height = 0;
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) ((PartyCommunityAdapterHolder) holder).itemGridView.getLayoutParams();
            if (picList.size() <= 0) {
                ((PartyCommunityAdapterHolder) holder).itemGridView.setVisibility(View.GONE);
            } else {

                if (picList.size() == 1) {
                    ((PartyCommunityAdapterHolder) holder).itemGridView.setVisibility(View.VISIBLE);
                    ((PartyCommunityAdapterHolder) holder).itemGridView.setNumColumns(1);
                    width = Utils.getDeviceSize(mContext).x - DisplayUtil.dp2px(mContext, 30);
                    lp.width = RecyclerView.LayoutParams.MATCH_PARENT;
                    height = width * 3 / 5;
                } else if (picList.size() == 2 || picList.size() == 4) {
                    ((PartyCommunityAdapterHolder) holder).itemGridView.setVisibility(View.VISIBLE);
                    ((PartyCommunityAdapterHolder) holder).itemGridView.setNumColumns(2);
                    width = (Utils.getDeviceSize(mContext).x - DisplayUtil.dp2px(mContext, 38)) / 2;
                    lp.width = width * 2 + DisplayUtil.dp2px(mContext, 4);
                    height = width * 3 / 4;

                } else {
                    ((PartyCommunityAdapterHolder) holder).itemGridView.setVisibility(View.VISIBLE);
                    ((PartyCommunityAdapterHolder) holder).itemGridView.setNumColumns(3);
                    width = (Utils.getDeviceSize(mContext).x - DisplayUtil.dp2px(mContext, 38)) / 3;
                    lp.width = RecyclerView.LayoutParams.MATCH_PARENT;
                    height = width * 3 / 4;
                }
                ((PartyCommunityAdapterHolder) holder).itemGridView.setLayoutParams(lp);

                adapter.setItemHeight(height);
                ((PartyCommunityAdapterHolder) holder).itemGridView.setAdapter(adapter);
                ((PartyCommunityAdapterHolder) holder).itemGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (onGridItemClickListener != null) {
                            onGridItemClickListener.onGridItemClick(pos, position);
                        }
                    }
                });
            }
            LinearLayout.LayoutParams containerLp = (LinearLayout.LayoutParams) ((PartyCommunityAdapterHolder) holder).container.getLayoutParams();
            if (position == list.size() - 1) {
                containerLp.bottomMargin = DisplayUtil.dp2px(mContext, 60);
            } else {
                containerLp.bottomMargin = 0;
            }
            if (bean.getTop() == 1) { //置顶
                ((PartyCommunityAdapterHolder) holder).itemSpecial.setVisibility(View.VISIBLE);
            }else{
                ((PartyCommunityAdapterHolder) holder).itemSpecial.setVisibility(View.GONE);
            }
        }
    }

    class PartyCommunityAdapterHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.container)
        LinearLayout container;
        @BindView(R.id.tv_ptc_title)
        TextView tvPtcTitle;
        @BindView(R.id.tv_ptc_content)
        TextView tvPtcContent;
        @BindView(R.id.tv_relsease_name)
        TextView tvRelseaseName;
        @BindView(R.id.tv_eye_count)
        TextView tvEyeCount;
        @BindView(R.id.tv_comment_count)
        TextView tvCommentCount;
        @BindView(R.id.img_collection)
        ImageView imgCollection;
        @BindView(R.id.tv_collection_count)
        TextView tvCollectionCount;
        @BindView(R.id.lv_br_view)
        LinearLayout lvBrView;
        @BindView(R.id.lv_bottomview)
        LinearLayout lvBottomview;
        @BindView(R.id.tv_sign)
        TextView tvSign;
        @BindView(R.id.item_special)
        TextView itemSpecial;
        @BindView(R.id.lv_ptallview)
        LinearLayout lvPtallview;
        @BindView(R.id.item_grid_view)
        CustomGridView itemGridView;

        public PartyCommunityAdapterHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    /**
     * 网格item点击
     *
     * @param onGridItemClickListemer
     */
    public void setOnGridItemClickListemer(OnGridItemClickListener onGridItemClickListemer) {
        this.onGridItemClickListener = onGridItemClickListemer;
    }

    public interface OnGridItemClickListener {
        void onGridItemClick(int listPosition, int position);
    }

}
