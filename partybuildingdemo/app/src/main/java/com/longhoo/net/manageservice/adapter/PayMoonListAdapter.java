package com.longhoo.net.manageservice.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.manageservice.bean.PayMoonListBean;
import com.longhoo.net.widget.CustomGridView;

import java.util.List;
import java.util.Map;

/**
 * Created by CK on 2018/5/4.
 * Email:910663958@qq.com
 */

public class PayMoonListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> groupList;
    private Map<String, List<PayMoonListBean.DataBean.ListsBean>> childList;

    public PayMoonListAdapter(Context context, List<String> groupList, Map<String, List<PayMoonListBean.DataBean.ListsBean>> childList) {
        this.context = context;
        this.groupList = groupList;
        this.childList = childList;
    }

    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupList == null ? null : groupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        List<PayMoonListBean.DataBean.ListsBean> list = childList.get(groupPosition);
        return list == null ? null : list.get(childPosition);
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
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        PayMoonListAdapter.ViewHolderP holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_pay_moon_list, null);
            holder = new PayMoonListAdapter.ViewHolderP();
            holder.tvYear = (TextView) convertView.findViewById(R.id.tv_year);
            holder.ivArrow = (ImageView) convertView.findViewById(R.id.iv_arrow);
            holder.progressBar = (ProgressBar) convertView.findViewById(R.id.progress_bar);
            holder.line = convertView.findViewById(R.id.line);
            convertView.setTag(holder);
        } else {
            holder = (PayMoonListAdapter.ViewHolderP) convertView.getTag();
        }
        String year = (String) getGroup(groupPosition);
        holder.tvYear.setText(year + "");
        if (isExpanded) {
            holder.ivArrow.setImageResource(R.drawable.list_arrow_up);
        } else {
            holder.ivArrow.setImageResource(R.drawable.list_arrow_down);
        }
        if (groupPosition == 0) {
            holder.line.setVisibility(View.GONE);
        } else {
            holder.line.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        PayMoonListAdapter.ViewHolderC holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_pay_moon_list_child, null);
            holder = new PayMoonListAdapter.ViewHolderC();
            holder.container = convertView.findViewById(R.id.container);
            holder.gridView = (CustomGridView) convertView.findViewById(R.id.grid_view);
            convertView.setTag(holder);
        } else {
            holder = (PayMoonListAdapter.ViewHolderC) convertView.getTag();
        }
        String year = groupList.get(groupPosition);
        List<PayMoonListBean.DataBean.ListsBean> list = childList.get(year);
        if (list == null) {
            holder.container.setVisibility(View.GONE);
        } else {
            holder.container.setVisibility(View.VISIBLE);
            MoonGridAdapter adapter = new MoonGridAdapter(context, list);
            holder.gridView.setAdapter(adapter);
            holder.gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
            holder.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (onChildItemClickListener != null) {
                        onChildItemClickListener.onChildItemClick(groupPosition, position);
                    }
                }
            });
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    /**
     * 父布局
     */
    public class ViewHolderP {
        private TextView tvYear;
        private ImageView ivArrow;
        public ProgressBar progressBar;
        public View line;
    }

    /**
     * 子布局
     */
    public class ViewHolderC {
        private LinearLayout container;
        private CustomGridView gridView;
    }

    private OnChildItemClickListener onChildItemClickListener;

    public void setOnChildItemClickListener(OnChildItemClickListener onChildItemClickListener) {
        this.onChildItemClickListener = onChildItemClickListener;
    }

    public interface OnChildItemClickListener {
        void onChildItemClick(int groupPos, int childPos);
    }
}
