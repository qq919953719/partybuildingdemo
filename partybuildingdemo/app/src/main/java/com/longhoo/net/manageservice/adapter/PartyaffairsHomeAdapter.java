package com.longhoo.net.manageservice.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.manageservice.bean.PartyTabBean;

import java.util.List;

/**
 * Created by ck on 2017/12/4.
 */

public class PartyaffairsHomeAdapter extends BaseAdapter {
    private Context context;
    private List<PartyTabBean> dataList;

    public PartyaffairsHomeAdapter(Context context, List<PartyTabBean> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int i) {
        return dataList.get(i);
    }


    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_partyaffairs_home, viewGroup, false);
            holder = new ViewHolder();
            holder.ivThumb = (ImageView) view.findViewById(R.id.iv_thumb);
            holder.tvTitle = (TextView) view.findViewById(R.id.tv_title);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        if (i >= 0 && i < getCount()) {
            holder.tvTitle.setText(dataList.get(i).getTitle() + "");
            holder.ivThumb.setImageResource(dataList.get(i).getResId());
        }
        return view;
    }


    class ViewHolder {
        ImageView ivThumb;
        TextView tvTitle;
    }

    /**
     * 根据图片的名称获取对应的资源id
     *
     * @param resourceName
     * @return
     */
    public int getDrawResourceID(String resourceName) {
        Resources res = context.getResources();
        int picid = res.getIdentifier(resourceName, "drawable", context.getPackageName());
        return picid;
    }
}
