package com.longhoo.net.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.longhoo.net.R;

/**
 * Created by CK on 2018/4/23.
 * Email:910663958@qq.com
 */

public class GlideManager {
    private static GlideManager instance;

    public static GlideManager getInstance(){
        if (instance == null) {
            instance = new GlideManager();
        }
        return instance;
    }

//    public void load(Context context,String url, ImageView view){
//        Glide.with(context).load(url)
//                .placeholder(R.color.divider2)
//                .error(R.color.divider2)
//                .centerCrop()
//                .crossFade()
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(view);
//    }

    public void load(Context context,String url, ImageView view){
        RequestOptions options = new RequestOptions();
        options.placeholder(R.color.divider2).error(R.color.divider2).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL);
                Glide.with(context).load(url)
                .transition(new DrawableTransitionOptions().crossFade())
                .into(view);
    }

    public void load(Context context,String url, ImageView view,int errorPic){
        RequestOptions options = new RequestOptions();
        options.placeholder(errorPic).error(errorPic).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(url)
                .transition(new DrawableTransitionOptions().crossFade())
                .into(view);
    }
}
