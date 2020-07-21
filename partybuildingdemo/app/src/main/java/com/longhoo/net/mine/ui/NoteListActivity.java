package com.longhoo.net.mine.ui;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.partyaffairs.adapter.CustomFragmentPagerAdapter;
import com.longhoo.net.utils.MessageEvent;
import com.longhoo.net.widget.NoScrollViewPager;
import com.longhoo.net.widget.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author CC 学习笔记
 */
public class NoteListActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_my_note)
    TextView tvMyNote;
    @BindView(R.id.iv_my_note)
    ImageView ivMyNote;
    @BindView(R.id.my_note_panel)
    LinearLayout myNotePanel;
    @BindView(R.id.tv_share_note)
    TextView tvShareNote;
    @BindView(R.id.iv_share_note)
    ImageView ivShareNote;
    @BindView(R.id.share_note_panel)
    LinearLayout shareNotePanel;
    @BindView(R.id.view_pager)
    NoScrollViewPager viewPager;
    @BindView(R.id.tv_siyou_note)
    TextView tvSiyouNote;
    @BindView(R.id.my_note_panel_two)
    LinearLayout myNotePanelTwo;
    @BindView(R.id.tv_gongxiang_note)
    TextView tvGongxiangNote;
    @BindView(R.id.share_gongxiang_panel)
    LinearLayout shareGongxiangPanel;
    @BindView(R.id.lv_xiaofenlei)
    LinearLayout lvXiaofenlei;
    private CustomFragmentPagerAdapter adapter;
    private boolean isShowBack = true;
    public static final int TAG_MY_NOTE = 0;
    public static final int TAG_SHARE_NOTE = 1;
    public static final int TAG_MY_NOTE2 = 2;

    private Fragment[] fragments;
    private String[] titles;

    @Override
    protected int getContentId() {
        return R.layout.activity_note_list;
    }

    public static int type = 0;

    @Override
    protected void initViews() {
        EventBus.getDefault().register(this);
        fragments = new Fragment[]{NoteListFragment.getInstance(TAG_MY_NOTE), NoteListFragment.getInstance(TAG_SHARE_NOTE)};
        titles = new String[]{"我的笔记", "分享的笔记"};
        adapter = new CustomFragmentPagerAdapter(getSupportFragmentManager(), titles, fragments);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(fragments.length);
        viewPager.addOnPageChangeListener(this);
        viewPager.setCurrentItem(0);
        viewPager.setNoScroll(true);
        type = 0;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initToolbar() {
        tvTitle.setText("学习笔记");
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

    @OnClick({R.id.my_note_panel, R.id.share_note_panel, R.id.iv_right, R.id.tv_siyou_note, R.id.tv_gongxiang_note})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_note_panel:
                type = 0;
                viewPager.setCurrentItem(0);
                findViewById(R.id.lv_xiaofenlei).setVisibility(View.VISIBLE);
                break;
            case R.id.share_note_panel:
                type = 3;
                viewPager.setCurrentItem(1);
                findViewById(R.id.lv_xiaofenlei).setVisibility(View.GONE);
                break;
            case R.id.iv_right:
                NoteTakingActivity.goAddNote(this);
                break;

            case R.id.tv_siyou_note:
                type = 0;
                findViewById(R.id.lv_xiaofenlei).setVisibility(View.VISIBLE);
                viewPager.setCurrentItem(0);
                tvSiyouNote.setTextColor(Color.parseColor("#e51f20"));
                tvGongxiangNote.setTextColor(Color.parseColor("#323232"));
                ((NoteListFragment) fragments[0]).refresh();

                break;
            case R.id.tv_gongxiang_note:
                type = 1;
                findViewById(R.id.lv_xiaofenlei).setVisibility(View.VISIBLE);
                viewPager.setCurrentItem(0);
                tvGongxiangNote.setTextColor(Color.parseColor("#e51f20"));
                tvSiyouNote.setTextColor(Color.parseColor("#323232"));
                ((NoteListFragment) fragments[0]).refresh();
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            tvMyNote.setTextColor(Color.parseColor("#e51f20"));
            tvShareNote.setTextColor(Color.parseColor("#323232"));
            ivMyNote.setImageResource(R.drawable.ic_tab_arrow_select);
            ivShareNote.setImageResource(R.drawable.ic_tab_arrow_unselect);
        } else {
            tvMyNote.setTextColor(Color.parseColor("#323232"));
            tvShareNote.setTextColor(Color.parseColor("#e51f20"));
            ivMyNote.setImageResource(R.drawable.ic_tab_arrow_unselect);
            ivShareNote.setImageResource(R.drawable.ic_tab_arrow_select);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        int msgType = event.msgType;
        switch (msgType) {
            case MessageEvent.MSG_SHARE_NOTE:
                viewPager.setCurrentItem(0);
                break;
            case MessageEvent.MSG_ADD_NOTE:
                viewPager.setCurrentItem(0);
                ((NoteListFragment) fragments[0]).refresh();
                break;
            case MessageEvent.MSG_DEL_NOTE:
                viewPager.setCurrentItem(0);
                ((NoteListFragment) fragments[0]).refresh();
                break;
            case MessageEvent.MSG_EDIT_NOTE:
                viewPager.setCurrentItem(0);
                ((NoteListFragment) fragments[0]).refresh();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
