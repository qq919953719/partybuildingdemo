package com.longhoo.net.headline.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/29.
 */

public class GuideAdapter extends PagerAdapter {
    private List<ImageView> imageViewsList = new ArrayList<>();
    private Context context;

    public GuideAdapter(Context context, List<ImageView> imageViewsList) {
        this.context = context;
        this.imageViewsList = imageViewsList;
    }

    @Override
    public int getCount() {
        return imageViewsList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ((ViewPager) container).addView(imageViewsList.get(position), 0);
        return imageViewsList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (position >= imageViewsList.size()) {
            return;
        }
        ((ViewPager) container).removeView(imageViewsList.get(position));
    }
}
