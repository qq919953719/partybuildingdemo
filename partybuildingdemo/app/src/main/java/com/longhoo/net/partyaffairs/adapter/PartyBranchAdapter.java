package com.longhoo.net.partyaffairs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.partyaffairs.bean.PartyBranchBean;

import java.util.List;

/**
 * Created by ck on 2017/12/13.
 */

public class PartyBranchAdapter extends BaseAdapter {
    private Context context;
    private List<PartyBranchBean.DataBean> list;

    public PartyBranchAdapter(Context context, List<PartyBranchBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.size()>0?list.get(i):"";
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_spinner_commit_for_party,viewGroup,false);
            holder.textView = (TextView) view.findViewById(R.id.text_view);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        if (i >= 0 && i < list.size()) {
            holder.textView.setText(list.get(i).getName()+"");
        }
        return view;
    }

    private class ViewHolder{
        TextView textView;
    }
}
