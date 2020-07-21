package com.longhoo.net.study.adapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.manageservice.adapter.GridViewAdapter;
import com.longhoo.net.study.bean.LiveDetailListBean;
import com.longhoo.net.utils.DisplayUtil;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.utils.Utils;
import com.longhoo.net.widget.CustomGridView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * Created by CK on 2017/12/27.
 * Email:910663958@qq.com
 */

public class LiveDetailListAdapter extends RecyclerView.Adapter {
    public static final String TAG = "LiveDetailListAdapter";
    private Context context;
    private List<LiveDetailListBean.DataBean.ListBean> dateList;

    public LiveDetailListAdapter(Context context, List<LiveDetailListBean.DataBean.ListBean> dateList) {
        this.context = context;
        this.dateList = dateList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list_live_detail, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (position < 0 || position > dateList.size() - 1) {
            return;
        }
        LiveDetailListBean.DataBean.ListBean bean = dateList.get(position);
        if (holder instanceof MyViewHolder) {
            ((MyViewHolder) holder).itemTime.setText(Utils.getDataTime(bean.getTime(),"yyyy-MM-dd HH:mm:ss"));
            ((MyViewHolder) holder).itemContent.setText(bean.getDescription() + "");
            //判断时间是否显示
            //ULog.e("ck",Utils.getDataTime(dateList.get(position).getTime(),"yyyy-MM-dd HH:mm:ss"));
            if (position > 0) {
                String lastTime = dateList.get(position - 1).getTime();
                String curTime = dateList.get(position).getTime();
                try {
                    if (Long.parseLong(lastTime) - Long.parseLong(curTime) > Math.abs(5 * 60)) {
                        ((MyViewHolder) holder).itemTimeContainer.setVisibility(View.VISIBLE);
                    } else {
                        ((MyViewHolder) holder).itemTimeContainer.setVisibility(View.GONE);
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    ((MyViewHolder) holder).itemTimeContainer.setVisibility(View.VISIBLE);
                }
            } else {
                ((MyViewHolder) holder).itemTimeContainer.setVisibility(View.VISIBLE);
            }
            //图片
            //加载网格图片
            List<LiveDetailListBean.DataBean.ListBean.PhotosBean> photosBeanList =  bean.getPhotos();
            List<String> picList = new ArrayList<>();
            if (photosBeanList != null) {
                for(LiveDetailListBean.DataBean.ListBean.PhotosBean photosBean:photosBeanList){
                    picList.add(photosBean.getUrl());
                }
            }
            GridViewAdapter adapter = new GridViewAdapter(context, picList);
            int width = 0;
            int height = 0;
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) ((MyViewHolder) holder).itemGridView.getLayoutParams();
            if (picList.size() <= 0) {
                ((MyViewHolder) holder).itemGridView.setVisibility(View.GONE);
            } else if (picList.size() == 1) {
                ((MyViewHolder) holder).itemGridView.setVisibility(View.VISIBLE);
                ((MyViewHolder) holder).itemGridView.setNumColumns(1);
                width = Utils.getDeviceSize(context).x - DisplayUtil.dp2px(context, 40);
                lp.width = RecyclerView.LayoutParams.MATCH_PARENT;
            } else if (picList.size() == 2 || picList.size() == 4) {
                ((MyViewHolder) holder).itemGridView.setVisibility(View.VISIBLE);
                ((MyViewHolder) holder).itemGridView.setNumColumns(2);
                width = (Utils.getDeviceSize(context).x - DisplayUtil.dp2px(context, 40)) / 3;
                lp.width = width * 2 + DisplayUtil.dp2px(context, 4);
            } else {
                ((MyViewHolder) holder).itemGridView.setVisibility(View.VISIBLE);
                ((MyViewHolder) holder).itemGridView.setNumColumns(3);
                width = (Utils.getDeviceSize(context).x - DisplayUtil.dp2px(context, 40)) / 3;
                lp.width = RecyclerView.LayoutParams.MATCH_PARENT;
            }
            ((MyViewHolder) holder).itemGridView.setLayoutParams(lp);
            height = width * 3 / 4;
            adapter.setItemHeight(height);
            ((MyViewHolder) holder).itemGridView.setAdapter(adapter);
            ((MyViewHolder) holder).itemGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                    if (onGridItemClickListener != null) {
                        onGridItemClickListener.onGridItemClick(position, pos);
                    }
                }
            });
            //视频设置
//            String url = "http://vod.025nj.com/recordings/z1.njcast.room-1/1512279833_1512283988.mp4";
//            String thumbUrl = "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1437911928,3927946348&fm=27&gp=0.jpg";
//            ((MyViewHolder) holder).videoPlayer.setUp(url, JZVideoPlayer.SCREEN_WINDOW_LIST,"");
//            if (!TextUtils.isEmpty(thumbUrl)) {
//                RequestOptions options = new RequestOptions();
//                options.placeholder(R.color.black).error(R.color.black).centerCrop();
//                Glide.with(context).load(thumbUrl)
//                        .apply(options)
//                        .transition(new DrawableTransitionOptions().crossFade())
//                        .into(((MyViewHolder) holder).videoPlayer.thumbImageView);
//            }
//            ((MyViewHolder) holder).videoPlayer.positionInList = position;
            ((MyViewHolder) holder).videoPlayer.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return dateList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_time)
        TextView itemTime;
        @BindView(R.id.item_content)
        TextView itemContent;
        @BindView(R.id.item_grid_view)
        CustomGridView itemGridView;
        @BindView(R.id.item_time_container)
        LinearLayout itemTimeContainer;
        @BindView(R.id.video_item_player)
        JZVideoPlayerStandard videoPlayer;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private OnGridItemClickListener onGridItemClickListener;

    public void setOnGridItemClickListener(OnGridItemClickListener onGridItemClickListener) {
        this.onGridItemClickListener = onGridItemClickListener;
    }

    public interface OnGridItemClickListener {
        void onGridItemClick(int listPosition, int gridPosition);
    }
}
