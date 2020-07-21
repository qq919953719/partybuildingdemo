package com.longhoo.net.manageservice.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.longhoo.net.R;
import com.longhoo.net.utils.DisplayUtil;
import com.longhoo.net.utils.GlideManager;
import com.longhoo.net.utils.Utils;


import java.util.List;

public class GridViewAdapter extends BaseAdapter {
    private Context context;
    private List<String> picList;
    private int height;

    public GridViewAdapter(Context context, List<String> picList) {
        this.context = context;
        this.picList = picList;
        height= (Utils.getDeviceSize(context).x- DisplayUtil.dp2px(context,38))/3*2/3;
    }

    @Override
    public int getCount() {
        return picList.size();
    }

    @Override
    public Object getItem(int i) {
        return picList == null ? null : picList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void setItemHeight(int height) {
        this.height = height;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (i < 0 || i > picList.size() - 1) {
            return null;
        }
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_grid_pic,viewGroup,false);
            holder.imageView = (ImageView) view.findViewById(R.id.item_image);
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
            holder.imageView.setLayoutParams(lp);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        if (!TextUtils.isEmpty(picList.get(i))) {
            GlideManager.getInstance().load(context,picList.get(i),holder.imageView);
        }
        return view;
    }

    private class ViewHolder {
        //FrameLayout frameLayout;
        ImageView imageView;
    }
}