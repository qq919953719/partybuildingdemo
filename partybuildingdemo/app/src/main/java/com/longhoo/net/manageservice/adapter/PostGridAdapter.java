package com.longhoo.net.manageservice.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.utils.DisplayUtil;
import com.longhoo.net.utils.GlideManager;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.utils.Utils;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.DateUtils;
import com.luck.picture.lib.tools.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PostGridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_CAMERA = 1;
    public static final int TYPE_PICTURE = 2;
    private LayoutInflater mInflater;
    private List<LocalMedia> list = new ArrayList<>();
    private int selectMax = 9;
    private Context context;
    private ViewGroup.LayoutParams lp;
    private RelativeLayout.LayoutParams lp2;
    private boolean isHideSelect = false;
    /**
     * 点击添加图片跳转
     */
    private onAddPicClickListener mOnAddPicClickListener;

    public interface onAddPicClickListener {
        void onAddPicClick();
    }

    public void setHideSelect(boolean isHideSelect) {
        this.isHideSelect = isHideSelect;
    }

    public PostGridAdapter(Context context, onAddPicClickListener mOnAddPicClickListener) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.mOnAddPicClickListener = mOnAddPicClickListener;
        lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (Utils.getDeviceSize(context).x - DisplayUtil.dp2px(context, 60)) / 3);
        lp2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (Utils.getDeviceSize(context).x - DisplayUtil.dp2px(context, 60)) / 3);
    }
    public PostGridAdapter(Context context, onAddPicClickListener mOnAddPicClickListener,int reduceHeight) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.mOnAddPicClickListener = mOnAddPicClickListener;
        lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (Utils.getDeviceSize(context).x - DisplayUtil.dp2px(context, 60)-reduceHeight) / 3);
        lp2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (Utils.getDeviceSize(context).x - DisplayUtil.dp2px(context, 60)-reduceHeight) / 3);
    }
    public void setSelectMax(int selectMax) {
        this.selectMax = selectMax;
    }

    public void setList(List<LocalMedia> list) {
        this.list = list;
    }

    public class PicViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout container;
        ImageView mImg;
        LinearLayout ll_del;
        TextView tv_duration;

        public PicViewHolder(View view) {
            super(view);
            container = (RelativeLayout) view.findViewById(R.id.container);
            mImg = (ImageView) view.findViewById(R.id.fiv);
            ll_del = (LinearLayout) view.findViewById(R.id.ll_del);
            tv_duration = (TextView) view.findViewById(R.id.tv_duration);
        }
    }

    public class SelViewHolder extends RecyclerView.ViewHolder {
        LinearLayout llSel;

        public SelViewHolder(View view) {
            super(view);
            llSel = (LinearLayout) view.findViewById(R.id.ll_sel);
        }
    }

    @Override
    public int getItemCount() {
        if (isHideSelect) {
            return list.size();
        }else{
            if (list.size() < selectMax) {
                return list.size() + 1;
            } else {
                return list.size();
            }
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (isHideSelect) {
            return TYPE_PICTURE;
        }else{
            if (isShowAddItem(position)) {
                return TYPE_CAMERA;
            } else {
                return TYPE_PICTURE;
            }
        }
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == TYPE_CAMERA) {
            return new SelViewHolder(mInflater.inflate(R.layout.item_grid_sel, viewGroup, false));
        } else {
            return new PicViewHolder(mInflater.inflate(R.layout.gv_filter_image, viewGroup, false));
        }
    }

    private boolean isShowAddItem(int position) {
        int size = list.size() == 0 ? 0 : list.size();
        return position == size;
    }

    /**
     * 设置值
     */
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        if (viewHolder instanceof SelViewHolder) {
            ((SelViewHolder) viewHolder).llSel.setLayoutParams(lp);
            ((SelViewHolder) viewHolder).llSel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnAddPicClickListener.onAddPicClick();
                }
            });
        } else if (viewHolder instanceof PicViewHolder) {
            ((PicViewHolder) viewHolder).container.setLayoutParams(lp2);
            if (isHideSelect) {
                ((PicViewHolder) viewHolder).ll_del.setVisibility(View.GONE);
            }else{
                ((PicViewHolder) viewHolder).ll_del.setVisibility(View.VISIBLE);
            }
            ((PicViewHolder) viewHolder).ll_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int index = viewHolder.getAdapterPosition();
                    // 这里有时会返回-1造成数据下标越界,具体可参考getAdapterPosition()源码，
                    // 通过源码分析应该是bindViewHolder()暂未绘制完成导致，知道原因的也可联系我~感谢
                    if (index != RecyclerView.NO_POSITION) {
                        list.remove(index);
                        notifyItemRemoved(index);
                        notifyItemRangeChanged(index, list.size());
                        //DebugUtil.i("delete position:", index + "--->remove after:" + list.size());
                    }
                }
            });
            LocalMedia media = list.get(position);
            int mimeType = media.getMimeType();
            String path = "";
            if (media.isCut() && !media.isCompressed()) {
                // 裁剪过
                path = media.getCutPath();
            } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
                // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                path = media.getCompressPath();
            } else {
                // 原图
                path = media.getPath();
            }
            // 图片
            if (media.isCompressed()) {
                ULog.i("compress image result:", new File(media.getCompressPath()).length() / 1024 + "k");
                ULog.i("压缩地址::", media.getCompressPath());
            }

            ULog.i("原图地址::", media.getPath());
            int pictureType = PictureMimeType.isPictureType(media.getPictureType());
            if (media.isCut()) {
                ULog.i("裁剪地址::", media.getCutPath());
            }
            long duration = media.getDuration();
            ((PicViewHolder) viewHolder).tv_duration.setVisibility(pictureType == PictureConfig.TYPE_VIDEO
                    ? View.VISIBLE : View.GONE);
            if (mimeType == PictureMimeType.ofAudio()) {
                ((PicViewHolder) viewHolder).tv_duration.setVisibility(View.VISIBLE);
                Drawable drawable = ContextCompat.getDrawable(context, R.drawable.picture_audio);
                StringUtils.modifyTextViewDrawable(((PicViewHolder) viewHolder).tv_duration, drawable, 0);
            } else {
                Drawable drawable = ContextCompat.getDrawable(context, R.drawable.video_icon);
                StringUtils.modifyTextViewDrawable(((PicViewHolder) viewHolder).tv_duration, drawable, 0);
            }
            ((PicViewHolder) viewHolder).tv_duration.setText(DateUtils.timeParse(duration));
            if (mimeType == PictureMimeType.ofAudio()) {
                ((PicViewHolder) viewHolder).mImg.setImageResource(R.drawable.audio_placeholder);
            } else {
                GlideManager.getInstance().load(viewHolder.itemView.getContext(), path, ((PicViewHolder) viewHolder).mImg);
            }
            //itemView 的点击事件
            if (mItemClickListener != null) {
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int adapterPosition = viewHolder.getAdapterPosition();
                        mItemClickListener.onItemClick(adapterPosition, v);
                    }
                });
            }
        }
    }

    protected OnItemClickListener mItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position, View v);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }
}
