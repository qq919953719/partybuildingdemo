package com.longhoo.net.study.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.longhoo.net.MyApplication;
import com.longhoo.net.R;
import com.longhoo.net.partyaffairs.adapter.CustomFragmentPagerAdapter;
import com.longhoo.net.study.adapter.VideoConCommentAdapter;
import com.longhoo.net.study.bean.VideoConCommentBean;
import com.longhoo.net.study.bean.VideoConListBean;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.GlideManager;
import com.longhoo.net.utils.MessageEvent;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.Utils;
import com.longhoo.net.utils.httprequest.HttpRequestPresenter;
import com.longhoo.net.utils.httprequest.HttpRequestView;
import com.longhoo.net.utils.httprequest.OkHttpCallback;
import com.longhoo.net.utils.httprequest.OkHttpUtil;
import com.longhoo.net.widget.HeaderAndFooterWrapper;
import com.longhoo.net.widget.NoScrollViewPager;
import com.longhoo.net.widget.base.BaseActivity;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

import static com.longhoo.net.study.ui.VideoCommentFragment.PARTYCLASS_COMMENT;
import static com.longhoo.net.study.ui.VideoCommentFragment.VIDEO_CON_COMMENT;

/**
 * 视频会议视频播放
 */
public class VideoConDetailActivity extends BaseActivity implements View.OnClickListener{
    private static final String TAG = VideoConDetailActivity.class.getSimpleName();
    @BindView(R.id.video_player)
    JZVideoPlayerStandard videoPlayer;
    @BindView(R.id.iv_pic_live)
    ImageView ivPicLive;
    @BindView(R.id.fl_container)
    FrameLayout flContainer;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_zan)
    ImageView ivZan;
    @BindView(R.id.tv_zan_num)
    TextView tvZanNum;
    @BindView(R.id.zan_panel)
    LinearLayout zanPanel;
    @BindView(R.id.tv_view_num)
    TextView tvViewNum;
    @BindView(R.id.iv_view_pic)
    ImageView ivViewPic;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tab_layout)
    SlidingTabLayout tabLayout;
    @BindView(R.id.viewpager)
    NoScrollViewPager viewpager;
    @BindView(R.id.coordinator_layout)
    LinearLayout coordinatorLayout;


    private String mVideoPath = "";
    private String mThumbPath = "";
    private Fragment[] fragments;
    private String[] titles;
    private CustomFragmentPagerAdapter pagerAdapter;
    private int videoId;
    private String description;
    private String title = "";
    private boolean isZan;
    private int zanNum;
    public boolean isChanged;
    private String type = "1";//1预告 2正在直播 3 往期回顾 4 全部

    public static void startVideoPlay(Context context, int videoId, String type) {
        Intent intent = new Intent(context, VideoConDetailActivity.class);
        intent.putExtra("videoId", videoId);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_video_con_detail;
    }

    @Override
    protected void initViews() {
        //沉浸式状态栏和透明导航栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        if (getIntent() != null) {
            videoId = getIntent().getIntExtra("videoId", 0);
            type = getIntent().getStringExtra("type");
        }
        getVideoData();
    }

    /**
     * 获取视频的数据
     */
    private void getVideoData() {
        String token = "";
        MyApplication app = (MyApplication) getApplicationContext();
        if (app.getmLoginBean() != null) {
            token = app.getmLoginBean().getData().getToken();
        }
        String url = Consts.BASE_URL + "/live/live_lists.html";
        Map<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("id", videoId + "");
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                if (isFinishing()) {
                    return;
                }
                List<VideoConListBean.DataBean.ListBean> tempList = null;
                Gson gson = new Gson();
                try {
                    VideoConListBean listBean = gson.fromJson(response, VideoConListBean.class);
                    String code = listBean.getCode();
                    if (!TextUtils.equals(code, "0")) {
                        ToastUtils.getInstance().showToast(VideoConDetailActivity.this, "获取数据失败~");
                        return;
                    }
                    tempList = listBean.getData().getList();
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
                if (tempList != null && tempList.size() > 0) {
                    mVideoPath = tempList.get(0).getZburl();
                    mThumbPath = tempList.get(0).getThumb();
                    title = tempList.get(0).getName();
                    description = tempList.get(0).getDescription();
                    tvViewNum.setText(tempList.get(0).getHits() + "");
                    zanNum = tempList.get(0).getZan();
                    tvZanNum.setText(zanNum + "");
                    isZan = TextUtils.equals(tempList.get(0).getIs_zan(), "1") ? false : true;
                    ivZan.setSelected(isZan);
                    initUI();
                }

            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(VideoConDetailActivity.this, "数据异常，获取数据失败~");
            }
        });
    }

    /**
     * 初始化UI
     */
    private void initUI() {
        tvTitle.setText(title);
        titles = new String[]{"简介", "评论"};
        fragments = new Fragment[]{VideoIntroduceFragment.newInstance(description), VideoCommentFragment.newInstance(VIDEO_CON_COMMENT, videoId+"")};
        pagerAdapter = new CustomFragmentPagerAdapter(getSupportFragmentManager(), titles, fragments);
        viewpager.setAdapter(pagerAdapter);
        tabLayout.setViewPager(viewpager);
        tabLayout.setTabSpaceEqual(false);
        //判断直播类型
        if (TextUtils.equals(VideoConferenceActivity.TAG_VIDEO_YUGAO, type)) {
            ivPicLive.setVisibility(View.VISIBLE);
            videoPlayer.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(mThumbPath)) {
                GlideManager.getInstance().load(this, mThumbPath, ivPicLive);
            }
            ToastUtils.getInstance().showToast(this, "当前直播未开始...");
        } else {
            if (TextUtils.equals(VideoConferenceActivity.TAG_VIDEO_LIVE, type)) {
                videoPlayer.setIsLiveStream(true);
            } else {
                videoPlayer.setIsLiveStream(false);
            }
            ivPicLive.setVisibility(View.GONE);
            videoPlayer.setVisibility(View.VISIBLE);
            videoPlayer.setUp(mVideoPath, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "");
            if (!TextUtils.isEmpty(mThumbPath)) {
                GlideManager.getInstance().load(this, mThumbPath, videoPlayer.thumbImageView);
            }
            videoPlayer.startButton.performClick();
        }
    }

    @OnClick({R.id.iv_back,R.id.zan_panel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.zan_panel:
                String url = Consts.BASE_URL + "/live/live_zan.html";
                Map<String, String> params = new HashMap<>();
                params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
                params.put("sid", videoId + "");
                params.put("type", isZan ? "2" : "1");
                OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
                    @Override
                    public void onSuccess(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String code = jsonObject.optString("code");
                            String msg = jsonObject.optString("msg");
                            ToastUtils.getInstance().showToast(VideoConDetailActivity.this, msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(String errorMsg) {
                        ToastUtils.getInstance().showToast(VideoConDetailActivity.this, "网络异常~");
                    }
                });
                isChanged = true;
                if (isZan) {
                    isZan = false;
                    ivZan.setSelected(false);
                    tvZanNum.setText(String.valueOf(--zanNum));
                } else {
                    isZan = true;
                    ivZan.setSelected(true);
                    tvZanNum.setText(String.valueOf(++zanNum));
                }
                break;
        }
    }

    @Override
    protected void initToolbar() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JZVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onPause() {
        super.onPause();
        //videoPlayer.pauseAllVideos();
        JZVideoPlayer.goOnPlayOnPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        //videoPlayer.resumeAllVideos();
        JZVideoPlayer.goOnPlayOnResume();
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        if (isChanged) {
            MessageEvent event = new MessageEvent();
            event.msgType = MessageEvent.MSG_REFRESH_LIST;
            EventBus.getDefault().post(event);
        }
        super.onBackPressed();
    }

}
