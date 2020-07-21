package com.longhoo.net.manageservice.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.manageservice.bean.OrganizationArchiBean;

import java.util.List;
import java.util.Map;

/**
 * Created by CC on 2018/4/27.
 * Email:919953719@qq.com
 */

public class OrganizationArchiAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<OrganizationArchiBean.DataBean> groupList;
    private Map<String,List<OrganizationArchiBean.DataBean>> childList;

    public OrganizationArchiAdapter(Context context, List<OrganizationArchiBean.DataBean> groupList, Map<String,List<OrganizationArchiBean.DataBean>> childList) {
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
        OrganizationArchiBean.DataBean bean = groupList==null?null:groupList.get(groupPosition);
        String id="";
        if (bean != null) {
            id = bean.getOid()+"";
        }
        List<OrganizationArchiBean.DataBean> list = childList.get(id);
        return list==null?0:list.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupList==null?null:groupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        OrganizationArchiBean.DataBean bean = groupList==null?null:groupList.get(groupPosition);
        String id="";
        if (bean != null) {
            id = bean.getOid()+"";
        }
        List<OrganizationArchiBean.DataBean> list = childList.get(id);
        return list==null?null:list.get(childPosition);
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
        ViewHolderP holder = null;
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_organizationarchi, null);
            holder = new ViewHolderP();
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tvTag = (TextView) convertView.findViewById(R.id.tv_tag);
            holder.ivArrow = (ImageView) convertView.findViewById(R.id.iv_arrow);
            holder.progressBar = (ProgressBar) convertView.findViewById(R.id.progress_bar);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolderP) convertView.getTag();
        }
        OrganizationArchiBean.DataBean bean = (OrganizationArchiBean.DataBean) getGroup(groupPosition);
        if (bean == null) {
            return convertView;
        }
        holder.tvTitle.setText(bean.getName().trim()+"");
//        if(TextUtils.isEmpty(bean.getTag())){
//            holder.tvTag.setVisibility(View.GONE);
//        }else{
//            holder.tvTag.setVisibility(View.VISIBLE);
//            holder.tvTag.setText(bean.getTag()+"");
//        }

        int type = bean.getType();//0组织 1个人 2列表
        if(type==1){
            holder.ivArrow.setVisibility(View.GONE);
        }else if(type==0){
            holder.ivArrow.setVisibility(View.VISIBLE);
            holder.ivArrow.setImageResource(R.drawable.list_arrow_right);
        }else if(type==2){
            holder.ivArrow.setVisibility(View.VISIBLE);
            if(isExpanded){
                holder.ivArrow.setImageResource(R.drawable.list_arrow_up);
            }else{
                holder.ivArrow.setImageResource(R.drawable.list_arrow_down);
            }
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolderC holder = null;
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_organizationarchi_child, null);
            holder = new ViewHolderC();
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tvTag = (TextView) convertView.findViewById(R.id.tv_tag);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolderC) convertView.getTag();
        }
        OrganizationArchiBean.DataBean bean = (OrganizationArchiBean.DataBean) getChild(groupPosition,childPosition);
        if (bean == null) {
            return convertView;
        }
        holder.tvTitle.setText(bean.getName().trim()+"");
        if(TextUtils.isEmpty(bean.getTag())){
            holder.tvTag.setVisibility(View.GONE);
        }else{
            holder.tvTag.setVisibility(View.VISIBLE);
            holder.tvTag.setText(bean.getTag()+"");
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
   public class ViewHolderP{
        private TextView tvTitle;
        private TextView tvTag;
        private ImageView ivArrow;
        public ProgressBar progressBar;
   }

    /**
     * 子布局
     */
   public class ViewHolderC{
       private TextView tvTitle;
       private TextView tvTag;
   }
}
