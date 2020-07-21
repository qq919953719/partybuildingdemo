package com.longhoo.net.manageservice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.manageservice.bean.PayMoonListBean;

import java.util.List;

public class MoonGridAdapter extends BaseAdapter {
    private Context context;
    private List<PayMoonListBean.DataBean.ListsBean> list;

    public MoonGridAdapter(Context context, List<PayMoonListBean.DataBean.ListsBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list==null?null:list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MoonGridViewHolder holder=null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_moon_grid,parent,false);
            holder = new MoonGridViewHolder();
            holder.tvMoon = convertView.findViewById(R.id.tv_moon);
            convertView.setTag(holder);
        }else{
            holder = (MoonGridViewHolder) convertView.getTag();
        }
        if(position>=0&&position<list.size()){
            //holder.tvMoon.setText(list.get(position).getMonth().replaceFirst("^0*", "")+"月");
            holder.tvMoon.setText(list.get(position).getMonth()+"月");
            if(list.get(position).getStatus()==0){
                holder.tvMoon.setBackgroundResource(R.drawable.bg_red_solid_25);
            }else{
                holder.tvMoon.setBackgroundResource(R.drawable.bg_disenabled_solid_25);
            }
        }
        return convertView;
    }

    private class MoonGridViewHolder {
        private TextView tvMoon;
    }
}