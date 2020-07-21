package com.longhoo.net.manageservice.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.longhoo.net.MyApplication;
import com.longhoo.net.R;
import com.longhoo.net.manageservice.bean.OrgnizationGroupBean;
import com.longhoo.net.manageservice.ui.OrgazationPersonalActivity;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.widget.AnimatedExpandableListView;

import java.util.List;


/**
 * Created by CC on 16/3/15.
 */
public class AnimatedListAdapter extends AnimatedExpandableListView.AnimatedExpandableListAdapter {
    private Context context;
    private List<OrgnizationGroupBean.DataBean.OrganizationListBean> list;

    public AnimatedListAdapter(Context context, List<OrgnizationGroupBean.DataBean.OrganizationListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition).getMember().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(final int groupPosition, final boolean isExpanded, View convertView, ViewGroup parent) {
        final GroupHolder holder;
        if (convertView == null) {
            holder = new GroupHolder();
            convertView = View.inflate(context, R.layout.adapter_parent, null);
            holder.item_parent = (TextView) convertView.findViewById(R.id.item_parent);
            holder.img_turn = (ImageView) convertView.findViewById(R.id.img_turn);
            convertView.setTag(holder);
        } else {
            holder = (GroupHolder) convertView.getTag();
        }
        boolean isUp = true;
        holder.item_parent.setText(list.get(groupPosition).getName());


        if (isExpanded) {

            holder.img_turn.setBackgroundResource(R.drawable.true_up);

        } else {
            holder.img_turn.setBackgroundResource(R.drawable.true_down);

        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public int getRealChildrenCount(int groupPosition) {
        return list.get(groupPosition).getMember().size();
    }

    @Override
    public View getRealChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder holder;
        if (convertView == null) {
            holder = new ChildHolder();
            convertView = View.inflate(context, R.layout.adapter_child, null);
            holder.item_child = (TextView) convertView.findViewById(R.id.item_child);


            convertView.setTag(holder);
        } else {
            holder = (ChildHolder) convertView.getTag();
        }
        holder.item_child.setText(list.get(groupPosition).getMember().get(childPosition).getRealname());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (list.get(groupPosition).getMember().get(childPosition).getClick().equals("0")){
                    ToastUtils.getInstance().showToast(context,"很抱歉，您无查看权限！");
                    return;
                }
                Intent intent = new Intent();
                intent.setClass(context, OrgazationPersonalActivity.class);
                intent.putExtra("did", list.get(groupPosition).getMember().get(childPosition).getUid());
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    private static class ChildHolder {
        TextView item_child;


    }

    private static class GroupHolder {
        TextView item_parent;

        ImageView img_turn;
    }

}
