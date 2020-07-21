package com.longhoo.net.study.ui;


import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.mine.ui.MyStudyArchivesFragment;
import com.longhoo.net.partyaffairs.adapter.CustomFragmentPagerAdapter;
import com.longhoo.net.utils.MessageEvent;
import com.longhoo.net.widget.NoScrollViewPager;
import com.longhoo.net.widget.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

import static com.longhoo.net.study.ui.OffCampusTrainReportActivity.TAG_REPORT_SELF;

/**
 * @author CK 学习管理
 */
public class StudyManagerActivity extends BaseActivity implements ViewPager.OnPageChangeListener {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_train)
    TextView tvTrain;
    @BindView(R.id.iv_train)
    ImageView ivTrain;
    @BindView(R.id.train_panel)
    LinearLayout trainPanel;
    @BindView(R.id.tv_outschool)
    TextView tvOutschool;
    @BindView(R.id.iv_outschool)
    ImageView ivOutschool;
    @BindView(R.id.outschool_panel)
    LinearLayout outschoolPanel;
    @BindView(R.id.view_pager)
    NoScrollViewPager viewPager;
    @BindView(R.id.tv_study_report)
    TextView tvStudyReport;
    private CustomFragmentPagerAdapter adapter;
    private boolean isShowBack = true;

    private Fragment[] fragments;
    private String[] titles;

    @Override
    protected int getContentId() {
        return R.layout.activity_study_manager;
    }


    @Override
    protected void initViews() {
        EventBus.getDefault().register(this);
        //fragments = new Fragment[]{new TrainCourseFragment(), new OffCampusTrainFragment()};
        fragments = new Fragment[]{new TrainCourseFragment(),new MyStudyArchivesFragment()};
        titles = new String[]{"培训班报名", "学习档案"};
        adapter = new CustomFragmentPagerAdapter(getSupportFragmentManager(), titles, fragments);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(fragments.length);
        viewPager.addOnPageChangeListener(this);
        viewPager.setCurrentItem(0);
        viewPager.setNoScroll(true);
        tvStudyReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OffCampusTrainReportActivity.goTo(StudyManagerActivity.this,TAG_REPORT_SELF,"上报校外培训","");
            }
        });
    }

    @Override
    protected void initToolbar() {
        tvTitle.setText("学习管理");
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        if (isShowBack) {
            toolbar.setNavigationIcon(R.drawable.left_arrow);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    @OnClick({R.id.train_panel, R.id.outschool_panel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.train_panel:
                viewPager.setCurrentItem(0);
                break;
            case R.id.outschool_panel:
                viewPager.setCurrentItem(1);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            tvTrain.setTextColor(Color.parseColor("#e51f20"));
            tvOutschool.setTextColor(Color.parseColor("#323232"));
            ivTrain.setImageResource(R.drawable.ic_tab_arrow_select);
            ivOutschool.setImageResource(R.drawable.ic_tab_arrow_unselect);
            tvStudyReport.setVisibility(View.VISIBLE);
        } else {
            tvTrain.setTextColor(Color.parseColor("#323232"));
            tvOutschool.setTextColor(Color.parseColor("#e51f20"));
            ivTrain.setImageResource(R.drawable.ic_tab_arrow_unselect);
            ivOutschool.setImageResource(R.drawable.ic_tab_arrow_select);
            tvStudyReport.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        int type = event.msgType;
        if(type==MessageEvent.MSG_REFRESH_STUDY_ARCHIVES){
            ((MyStudyArchivesFragment)fragments[1]).refresh();
        }
    }
}
