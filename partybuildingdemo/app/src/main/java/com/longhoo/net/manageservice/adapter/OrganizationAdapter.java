package com.longhoo.net.manageservice.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;
import com.longhoo.net.R;

import com.longhoo.net.headline.adapter.HeadlineAdapter;
import com.longhoo.net.headline.bean.HeadlineBean;
import com.longhoo.net.manageservice.bean.OrganizationallBean;
import com.longhoo.net.manageservice.ui.OrgazationGroupActivity;
import com.longhoo.net.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/11/27.组织适配器
 */

public class OrganizationAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<OrganizationallBean.DataBean.OrganizationBean> list;

    public OrganizationAdapter(Context context, List<OrganizationallBean.DataBean.OrganizationBean> list) {
        this.context = context;
        this.list = list;
    }

    public void AddOrganizationAdapter(List<OrganizationallBean.DataBean.OrganizationBean> list) {
        this.list.clear();
        this.list = list;
    }

    public void AddAllOrganizationAdapter(List<OrganizationallBean.DataBean.OrganizationBean> list) {

        this.list.addAll(list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OrganizationAdapter.OrginalViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_orgn, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof OrganizationAdapter.OrginalViewHolder) {
            final OrganizationallBean.DataBean.OrganizationBean bean = list.get(position);
            ((OrginalViewHolder) holder).itemTvTitle.setText(bean.getName());
            ((OrginalViewHolder) holder).itemTvTitle2.setText(bean.getName());

            //click：是否可点击（1可点击，0不可点击）
            if (bean.getClick().equals("0")) {
                ((OrginalViewHolder) holder).itemTvTitle.setVisibility(View.GONE);
                ((OrginalViewHolder) holder).itemTvTitle2.setVisibility(View.VISIBLE);
            } else {
                ((OrginalViewHolder) holder).itemTvTitle2.setVisibility(View.GONE);
                ((OrginalViewHolder) holder).itemTvTitle.setVisibility(View.VISIBLE);
            }

            ((OrginalViewHolder) holder).itemTvTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(context, OrgazationGroupActivity.class);
                    intent.putExtra("title", bean.getName());
                    intent.putExtra("oid", bean.getOid());
                    context.startActivity(intent);
                }
            });
            ((OrginalViewHolder) holder).itemTvTitle2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (bean.getClick().equals("0")) {
                        ToastUtils.getInstance().showToast(context, "很抱歉您没有查看权限！");
                        return;
                    }

                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class OrginalViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_org)
        TextView itemTvTitle;
        @BindView(R.id.tv_org2)
        TextView itemTvTitle2;

        public OrginalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
