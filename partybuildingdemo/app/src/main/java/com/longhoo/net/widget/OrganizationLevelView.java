package com.longhoo.net.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ck on 2018/6/13.
 */

public class OrganizationLevelView extends FrameLayout {
    private RecyclerView recyclerView;
    private HeaderAndFooterWrapper adapterWrapper;
    private List<? extends BaseDataBean> dataList = new ArrayList<>();

    public OrganizationLevelView(@NonNull Context context,List<? extends BaseDataBean> dataList) {
        super(context);
        this.dataList = dataList;
        init();
    }

    public OrganizationLevelView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OrganizationLevelView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    public OrganizationLevelView setData( List<? extends BaseDataBean> dataList){
        this.dataList = dataList;
        return this;
    }

    public void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_level_header, null);
        recyclerView = view.findViewById(R.id.recycler_view);
        int num = dataList.size();
        if (num <= 0) {
            setVisibility(GONE);
            return;
        }
        setVisibility(VISIBLE);
        List<? extends BaseDataBean> list = dataList.subList(1,dataList.size());
        adapterWrapper = new HeaderAndFooterWrapper(new LevelAdapter(list));
        //添加header
        View headerView = LayoutInflater.from(getContext()).inflate(R.layout.layout_level_item_vew_1,null);
        TextView tvHead = headerView.findViewById(R.id.tv_head);
        tvHead.setText(dataList.get(0).getTag()+" "+dataList.get(0).getName());
        adapterWrapper.addHeaderView(headerView);
        int spanCount = Math.min((num-1),6);
        if (spanCount <= 0) {
            spanCount = 1;
        }
        GridLayoutManager manager = new GridLayoutManager(getContext(), spanCount);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapterWrapper);
        int spaceDp = getSpaceDp(spanCount);
        recyclerView.addItemDecoration(new LevelItemDecoration(spanCount, DisplayUtil.dp2px(getContext(),spaceDp)));
        addView(view);
    }

    /**
     * 刷新
     * @param list
     */
    public void refresh(List<? extends BaseDataBean> list){
        this.dataList = list;
        removeAllViews();
        init();
    }

    /**
     * 网格间隔
     * @param spanCount
     */
    private int getSpaceDp(int spanCount){
        int spaceDp = 4;
        switch (spanCount){
            case 2:
                spaceDp = 34;
                break;
            case 3:
                spaceDp = 10;
                break;
            case 4:
            case 5:
            case 6:
                spaceDp = 4;
                break;
        }
        return spaceDp;
    }

    private class LevelAdapter extends RecyclerView.Adapter{
        private List<? extends BaseDataBean> list;

        public LevelAdapter(List<? extends BaseDataBean> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new LevelViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.layout_level_item_view_2,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if(position<0||position>list.size()-1){
                return;
            }

            ((LevelViewHolder) holder).tvItemName.setText(list.get(position).getName()+"");
            String tag = list.get(position).getTag();
            if(TextUtils.isEmpty(tag)){
                ((LevelViewHolder) holder).tvItemTag.setVisibility(GONE);
            }else{
                ((LevelViewHolder) holder).tvItemTag.setVisibility(VISIBLE);
                ((LevelViewHolder) holder).tvItemTag.setText(tag);
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    private class LevelViewHolder extends RecyclerView.ViewHolder{
        private TextView tvItemName;
        private TextView tvItemTag;
        public LevelViewHolder(View itemView) {
            super(itemView);
            tvItemName = itemView.findViewById(R.id.tv_item_name);
            tvItemTag = itemView.findViewById(R.id.tv_item_tag);
        }
    }

    public static class BaseDataBean{
        private String tag;
        private String name;

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public class LevelItemDecoration extends RecyclerView.ItemDecoration{
        private int spanCount;
        private int spacing;

        public LevelItemDecoration(int spanCount, int spacing) {
            this.spanCount = spanCount;
            this.spacing = spacing;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            position -=1;
            if(position<0){
                return;
            }
            int column = position % spanCount; // item column

            outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
            outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            if (position >= spanCount) {
                outRect.top = spacing; // item top
            }
        }
    }
}
