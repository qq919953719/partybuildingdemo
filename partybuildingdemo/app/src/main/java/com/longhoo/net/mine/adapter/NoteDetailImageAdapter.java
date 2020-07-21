package com.longhoo.net.mine.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.longhoo.net.R;
import com.longhoo.net.utils.DisplayUtil;
import com.longhoo.net.utils.GlideManager;
import com.longhoo.net.utils.ULog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by CK on 2018/5/16.
 * Email:910663958@qq.com
 */

public class NoteDetailImageAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<String> list;

    public NoteDetailImageAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_note_detail_image, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        if(position<0||position>list.size()-1)
            return;
        if(holder instanceof ViewHolder){
           // GlideManager.getInstance().load(context,list.get(position),((ViewHolder) holder).imageView);
            Glide.with(context).asBitmap().load(list.get(position)).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                    ULog.i("TAG", "width: " + resource.getWidth() + "     height: " + resource.getHeight());
                    int imgWidth = resource.getWidth();
                    int imgHeight =  resource.getHeight();
                    int screenWidth = DisplayUtil.getScreenWidth(context)-2*DisplayUtil.dp2px(context,12);
                    float ratio = (float) imgWidth / (float) imgHeight;
                    int height = (int) (screenWidth / ratio);
                    LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) ((ViewHolder) holder).imageView.getLayoutParams();
                    lp.width = screenWidth;
                    lp.height = height;
                    ((ViewHolder) holder).imageView.setImageBitmap(resource);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_view)
        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
