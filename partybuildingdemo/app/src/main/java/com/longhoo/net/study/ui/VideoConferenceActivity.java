package com.longhoo.net.study.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.longhoo.net.R;
import com.longhoo.net.partyaffairs.adapter.CustomFragmentPagerAdapter;
import com.longhoo.net.study.bean.VideoConListBean;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.utils.httprequest.OkHttpCallback;
import com.longhoo.net.utils.httprequest.OkHttpUtil;
import com.longhoo.net.widget.base.BaseActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 视频会议
 */
public class VideoConferenceActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.sliding_tabs)
    SlidingTabLayout slidingTabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.ll_list_panel)
    LinearLayout llListPanel;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.tv_reload)
    TextView tvReload;
    @BindView(R.id.ll_net_error_panel)
    LinearLayout llNetErrorPanel;
    private CustomFragmentPagerAdapter adapter;
    private String[] titles;
    private Fragment[] fragments;
    public static final String TAG_VIDEO_LIVE = "2";
    public static final String TAG_VIDEO_YUGAO = "1";
    public static final String TAG_VIDEO_REVIEW = "3";

    public static void goTo(Context context) {
        Intent intent = new Intent(context, VideoConferenceActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_video_conference;
    }

    @Override
    protected void initViews() {
        //doGetMenus();
        titles = new String[]{"正在直播", "预告", "往期回顾"};
        fragments = new Fragment[]{VideoConferenceFragment.newInstance(TAG_VIDEO_LIVE, ""), VideoConferenceFragment.newInstance(TAG_VIDEO_YUGAO, ""), VideoConferenceFragment.newInstance(TAG_VIDEO_REVIEW, "")};
        adapter = new CustomFragmentPagerAdapter(getSupportFragmentManager(), titles, fragments);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(fragments.length);
        slidingTabLayout.setViewPager(viewPager);
        progressBar.setVisibility(View.GONE);
        llNetErrorPanel.setVisibility(View.GONE);
        llListPanel.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initToolbar() {
        tvTitle.setText("视频会议");
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.left_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @OnClick({R.id.tv_reload})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_reload:
                //doGetMenus();
                break;
        }
    }

    /**
     * 获取tab菜单
     */
//    private void doGetMenus() {
//        progressBar.setVisibility(View.VISIBLE);
//        String url = "http://outdata.longhoo.net/?m=home&/app/video";
//        Map<String,String> params = new HashMap<>();
//        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
//        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
//            @Override
//            public void onSuccess(String response) {
//                if(isFinishing()){
//                    return;
//                }
//                ULog.e("ck", "视频会议菜单：" + response);
//                Gson gson = new Gson();
//                try {
//                    VideoConListBean menuBean = gson.fromJson(response, VideoConListBean.class);
//                    int code = menuBean.getStatus();
//                    if (code!=0) {
//                        progressBar.setVisibility(View.GONE);
//                        llListPanel.setVisibility(View.GONE);
//                        llNetErrorPanel.setVisibility(View.VISIBLE);
//                        return;
//                    }
//                    List<VideoConListBean.ListsBean> list = menuBean.getLists();
//                    int size = 0;
//                    if (list != null) {
//                        size = list.size();
//                        fragments = new Fragment[size];
//                        titles = new String[size];
//                        for(int i=0;i<size;i++){
//                            fragments[i]= PartyClassDisplayFragment.newInstance(list.get(i).getCid(),list.get(i).getName());
//                            titles[i] = list.get(i).getName();
//                        }
//                    }
//                    adapter = new CustomFragmentPagerAdapter(getSupportFragmentManager(), titles, fragments);
//                    viewPager.setOffscreenPageLimit(fragments!=null?fragments.length:0);
//                    viewPager.setAdapter(adapter);
//                    slidingTabLayout.setViewPager(viewPager);
//                    progressBar.setVisibility(View.GONE);
//                    llNetErrorPanel.setVisibility(View.GONE);
//                    llListPanel.setVisibility(View.VISIBLE);
//                    if (size <= 4) {
//                        slidingTabLayout.setTabSpaceEqual(true);
//                    } else {
//                        slidingTabLayout.setTabSpaceEqual(false);
//                        slidingTabLayout.setTabPadding(14);
//                    }
//                } catch (JsonSyntaxException e) {
//                    e.printStackTrace();
//                    progressBar.setVisibility(View.GONE);
//                    llListPanel.setVisibility(View.GONE);
//                    llNetErrorPanel.setVisibility(View.VISIBLE);
//                }
//            }
//
//            @Override
//            public void onFailure(String errorMsg) {
//                if(isFinishing()){
//                    return;
//                }
//                progressBar.setVisibility(View.GONE);
//                llListPanel.setVisibility(View.GONE);
//                llNetErrorPanel.setVisibility(View.VISIBLE);
//            }
//        });
//    }

}
