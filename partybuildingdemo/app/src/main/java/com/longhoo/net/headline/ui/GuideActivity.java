package com.longhoo.net.headline.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.gyf.barlibrary.ImmersionBar;
import com.longhoo.net.MainActivity;
import com.longhoo.net.MyApplication;
import com.longhoo.net.R;
import com.longhoo.net.headline.adapter.GuideAdapter;
import com.longhoo.net.mine.bean.LoginBean;
import com.longhoo.net.mine.ui.LoginActivity;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.SPTool;
import com.longhoo.net.widget.NoScrollViewPager;
import com.longhoo.net.widget.base.BaseActivity;
import com.longhoo.net.widget.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class GuideActivity extends BaseActivity implements ViewPager.OnPageChangeListener, OnClickListener, NoScrollViewPager.OnItemTouchListener {

    @BindView(R.id.bt_inenter)
    Button btInenter;
    private NoScrollViewPager viewPager;
    private CircleIndicator indicatorPanel;

    private GuideAdapter adapter;
    private int imgArray[] = {R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3, R.drawable.guide_4};
    private List<ImageView> imageViewsList = new ArrayList<>();
    private List<View> viewList = new ArrayList<>();
    private static final int FLING_MIN_DISTANCE = 180;// 移动最小距离
    private static final int FLING_MIN_VELOCITY = 300;// 移动最小速度
    GestureDetector mygesture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_guide;
    }

    @Override
    protected void initViews() {
        ImmersionBar.with(this)
                .titleBar(findViewById(R.id.bt_enter), false)
                .transparentBar()
                .init();
        btInenter = ((Button) findViewById(R.id.bt_inenter));
        btInenter.setVisibility(View.GONE);
        btInenter.setOnClickListener(this);
        hasFragment = true;
        mygesture = new GestureDetector(this, new GuideViewTouch());
        int size = imgArray.length;
        for (int i = 0; i < size; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(imgArray[i]);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(lp);
            imageViewsList.add(imageView);
        }
        viewPager = (NoScrollViewPager) findViewById(R.id.viewpager);
        adapter = new GuideAdapter(this, imageViewsList);
        viewPager.setAdapter(adapter);
        viewPager.setOnItemTouchListener(this);
        indicatorPanel = (CircleIndicator) findViewById(R.id.indicator_panel);
        viewPager.addOnPageChangeListener(this);
        indicatorPanel.setViewPager(viewPager);
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 3) {
            btInenter.setTextColor(Color.parseColor("#e81e1e"));
            btInenter.setVisibility(View.VISIBLE);
        } else {
            btInenter.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.bt_inenter) {
            SPTool.putBoolean(this, Consts.IS_FIRST_IN_APP, false);
            // 判断是否登录
            boolean isLogin = EnterCheckUtil.getInstance().isLogin(true);
            if(isLogin){
                Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                startActivity(intent);
            }
            finish();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mygesture.onTouchEvent(ev)) {
            ev.setAction(MotionEvent.ACTION_CANCEL);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onItemTouch(int position) {
        if (position == 4) {
            SPTool.putBoolean(this, Consts.IS_FIRST_IN_APP, false);
            // 判断是否登录
            boolean isLogin = EnterCheckUtil.getInstance().isLogin(true);
            if(isLogin){
                Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                startActivity(intent);
            }
            finish();
        }

    }

    private class GuideViewTouch extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (viewPager.getCurrentItem() == imgArray.length - 1 && e1.getX() - e2.getX() > FLING_MIN_DISTANCE && Math.abs(e1.getY() - e2.getY()) < 200 && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
                onItemTouch(4);
            }
            return false;
        }
    }
}
