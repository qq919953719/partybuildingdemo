package com.longhoo.net.widget.base;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.IntDef;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.longhoo.net.MyApplication;
import com.longhoo.net.R;
import com.longhoo.net.partyaffairs.adapter.CustomFragmentPagerAdapter;
import com.longhoo.net.study.bean.VideoListBean;
import com.longhoo.net.study.ui.LiveDetailListFragment;
import com.longhoo.net.study.ui.LiveIntroduceFragment;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.GlideManager;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.utils.httprequest.OkHttpCallback;
import com.longhoo.net.utils.httprequest.OkHttpUtil;
import com.longhoo.net.widget.NoScrollViewPager;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * 通用的视频播放器
 */
public class VideoPlayActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = VideoPlayActivity.class.getSimpleName();
    private JZVideoPlayerStandard videoPlayer;
    private ImageView ivPicLive;
    private String mVideoPath = "";
    private String mThumbPath = "";

    //顶部标题栏
    private ImageView ivBack;
    private TextView tvTitle;
    private Fragment[] fragments;
    private String[] titles;
    private CustomFragmentPagerAdapter pagerAdapter;
    private NoScrollViewPager viewPager;
    private SlidingTabLayout tabLayout;
    private String termId;
    private String description;

    @IntDef({LIVE_TYPE_VIDEO, LIVE_TYPE_PIC})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

    ;

    @IntDef({STYLE_TRAILER, STYLE_LIVE, STYLE_REVIEW})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Style {
    }

    ;
    private int liveType;
    private int liveStyle = 2;
    public static final int LIVE_TYPE_PIC = 0; //视频直播
    public static final int LIVE_TYPE_VIDEO = 1; //图文直播
    public static final int STYLE_TRAILER = 2;  //预告
    public static final int STYLE_LIVE = 3;  //直播
    public static final int STYLE_REVIEW = 4;  //回顾

    private String title = "";
    private boolean isPlay;
    private boolean isPause;
    //private boolean isLiveStream; /**是否直播流*/
    //private boolean isCache;  /**是否缓存视频*/
    private LiveDetailListFragment mLiveListFragment;

    public static void startVideoPlay(Context context, String videoPath, String thumb, String termId, String description, @Type int liveType, @Style int liveStyle, String title) {
        Intent intent = new Intent(context, VideoPlayActivity.class);
        intent.putExtra("videoPath", videoPath);
        intent.putExtra("thumbPath", thumb);
        intent.putExtra("termId", termId);
        intent.putExtra("description", description);
        intent.putExtra("liveType", liveType);
        intent.putExtra("liveStyle", liveStyle);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_video_play;
    }

    @Override
    protected void initViews() {
        //沉浸式状态栏和透明导航栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        if (getIntent() != null) {
            termId = getIntent().getStringExtra("termId");
            mVideoPath = getIntent().getStringExtra("videoPath");
            mThumbPath = getIntent().getStringExtra("thumbPath");
            description = getIntent().getStringExtra("description");
            liveType = getIntent().getIntExtra("liveType", LIVE_TYPE_PIC);
            liveStyle = getIntent().getIntExtra("liveStyle", STYLE_TRAILER);
            title = getIntent().getStringExtra("title");
        }
        ivBack = (ImageView) findViewById(R.id.iv_back);
        videoPlayer = (JZVideoPlayerStandard) findViewById(R.id.video_player);
        ivBack.setOnClickListener(this);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        ivPicLive = (ImageView) findViewById(R.id.iv_pic_live);
        viewPager = (NoScrollViewPager) findViewById(R.id.viewpager);
        tabLayout = (SlidingTabLayout) findViewById(R.id.tab_layout);
        //从首页广告烂点击而来，只有播放id，此时要请求接口获取数据
        if (TextUtils.isEmpty(mVideoPath) && TextUtils.isEmpty(mThumbPath)) {
            getVideoData();
        } else {  //否则，直接初始化
            initUI();
        }
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
        String url = Consts.BASE_URL + "c=Live&a=live_lists";
        Map<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("id", termId);
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                if (isFinishing()) {
                    return;
                }
                ULog.e(Consts.TAG, "直播详情" + ":" + response);
                //处理数据
                List<VideoListBean.DataBean.ListBean> tempList = null;
                Gson gson = new Gson();
                try {
                    VideoListBean videoListBean = gson.fromJson(response, VideoListBean.class);
                    int code = videoListBean.getCode();
                    if (code != 0) {
                        ToastUtils.getInstance().showToast(VideoPlayActivity.this, "获取数据失败~");
                        return;
                    }
                    tempList = videoListBean.getData().getList();
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
                if (tempList == null) {
                    ToastUtils.getInstance().showToast(VideoPlayActivity.this, "服务器异常，获取数据失败~");
                    return;
                }
                if (tempList.size() <= 0) {
                    ToastUtils.getInstance().showToast(VideoPlayActivity.this, "服务器异常，获取数据失败~");
                    return;
                }
                mVideoPath = tempList.get(0).getZburl();
                mThumbPath = tempList.get(0).getThumb();
                title = tempList.get(0).getName();
                description = tempList.get(0).getDescription();
                String type = tempList.get(0).getType();
                String isStart = tempList.get(0).getIstart();
                String isEnd = tempList.get(0).getIsend();
                //判断是图文直播还是预告、直播还是回顾
                if (TextUtils.equals(type, "0")) {  //0图文 1视频
                    liveType = VideoPlayActivity.LIVE_TYPE_PIC;
                } else if (TextUtils.equals(type, "1")) {
                    liveType = VideoPlayActivity.LIVE_TYPE_VIDEO;
                }
                if (TextUtils.equals(isStart, "0")) {
                    liveStyle = VideoPlayActivity.STYLE_TRAILER;
                } else {
                    if (TextUtils.equals(isEnd, "0")) {
                        liveStyle = VideoPlayActivity.STYLE_LIVE;
                    } else {
                        liveStyle = VideoPlayActivity.STYLE_REVIEW;
                    }
                }
                initUI();
            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(VideoPlayActivity.this, "数据异常，获取数据失败~");
            }
        });
    }

    /**
     * 初始化UI
     */
    private void initUI() {
        tvTitle.setText(title);
//        if (termId != null) {
//            titles = new String[]{"图文直播", "简介"};
//            fragments = new Fragment[]{LiveDetailListFragment.newInstance(termId), LiveIntroduceFragment.newInstance(description)};
//        } else {
        titles = new String[]{"简介"};
        fragments = new Fragment[]{LiveIntroduceFragment.newInstance(description)};
        //}
        pagerAdapter = new CustomFragmentPagerAdapter(getSupportFragmentManager(), titles, fragments);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setViewPager(viewPager);
        tabLayout.setTabSpaceEqual(false);
        videoPlayer.setVisibility(View.GONE);
        //判断直播类型
        if (liveType == LIVE_TYPE_PIC || liveStyle == STYLE_TRAILER) {
            ivPicLive.setVisibility(View.VISIBLE);
            videoPlayer.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(mThumbPath)) {
                GlideManager.getInstance().load(this, mThumbPath, ivPicLive);
            }
            if (liveStyle == STYLE_TRAILER) {
                ToastUtils.getInstance().showToast(this, "当前直播未开始...");
            }
        } else {
            ivPicLive.setVisibility(View.GONE);
            videoPlayer.setVisibility(View.VISIBLE);
            videoPlayer.setUp(mVideoPath, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "");
            if (!TextUtils.isEmpty(mThumbPath)) {
                GlideManager.getInstance().load(this, mThumbPath, videoPlayer.thumbImageView);
            }
            if (liveStyle == STYLE_LIVE) {
                videoPlayer.setIsLiveStream(true);
            } else {
                videoPlayer.setIsLiveStream(false);
            }
            videoPlayer.startButton.performClick();
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
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }

}
