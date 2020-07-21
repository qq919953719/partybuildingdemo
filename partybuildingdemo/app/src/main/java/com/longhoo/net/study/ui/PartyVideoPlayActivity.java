package com.longhoo.net.study.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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
import com.longhoo.net.study.bean.VideoDetailBean;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.GlideManager;
import com.longhoo.net.utils.MessageEvent;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.utils.WriteReadListUtil;
import com.longhoo.net.utils.httprequest.OkHttpCallback;
import com.longhoo.net.utils.httprequest.OkHttpUtil;
import com.longhoo.net.widget.NoScrollViewPager;
import com.longhoo.net.widget.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.JZMediaManager;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * 党课展示视频播放
 */
public class PartyVideoPlayActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = PartyVideoPlayActivity.class.getSimpleName();
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
    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.coordinator_layout)
    LinearLayout coordinatorLayout;
    @BindView(R.id.fl_allcontainer)
    FrameLayout flAllcontainer;
    @BindView(R.id.video_image)
    ImageView videoImage;

    private Fragment[] fragments;
    private String[] titles;
    private CustomFragmentPagerAdapter pagerAdapter;

    private String mVideoPath = "";
    private String mThumbPath = "";
    private String videoId;
    private String description;
    private String title = "";
    private boolean isZan;
    private int zanNum;
    public boolean isChanged;
    private int mPage = 1;

    public static void startVideoPlay(Context context, String videoId) {
        Intent intent = new Intent(context, PartyVideoPlayActivity.class);
        intent.putExtra("videoId", videoId);
        context.startActivity(intent);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_party_video_play;
    }

    @Override
    protected void initViews() {
        EventBus.getDefault().register(this);
        //沉浸式状态栏和透明导航栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        if (getIntent() != null) {
            videoId = getIntent().getStringExtra("videoId");
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
        String url = Consts.BASE_URL + "/Classdisplay/getDisplay";
        Map<String, String> params = new HashMap<>();
        params.put("token", token);
        if (!TextUtils.isEmpty(videoId)) {
            params.put("id", videoId);
        }
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                if (isFinishing()) {
                    return;
                }
                ULog.e(Consts.TAG, "直播详情" + ":" + response);
                //处理数据
                Gson gson = new Gson();
                try {
                    VideoDetailBean videoDetailBean = gson.fromJson(response, VideoDetailBean.class);
                    String code = videoDetailBean.getCode();
                    if (!TextUtils.equals(code, "0")) {
                        ToastUtils.getInstance().showToast(PartyVideoPlayActivity.this, "获取数据失败~");
                        return;
                    }
                    mVideoPath = videoDetailBean.getData().getVideourl();
                    mThumbPath = videoDetailBean.getData().getVideoimg();
                    title = videoDetailBean.getData().getTitle();
                    description = videoDetailBean.getData().getDes();
                    tvViewNum.setText(videoDetailBean.getData().getRed_num() + "");
                    zanNum = videoDetailBean.getData().getFab_num();
                    tvZanNum.setText(zanNum + "");
                    isZan = videoDetailBean.getData().getIs_fab() == 0 ? false : true;
                    ivZan.setSelected(isZan);
                    tvScore.setText("学习积分：" + videoDetailBean.getData().getPoint());
                    if (videoDetailBean.getData().getPoint().equals("0")) {
                        tvScore.setVisibility(View.GONE);
                    } else {
                        tvScore.setVisibility(View.VISIBLE);
                    }
                    initUI();

                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(PartyVideoPlayActivity.this, "数据异常，获取数据失败~");
            }
        });
    }

    /**
     * 初始化UI
     */
    private void initUI() {
        tvTitle.setText(title);
        titles = new String[]{"详情"};
        fragments = new Fragment[]{VideoIntroduceFragment.newInstance(description)};
        pagerAdapter = new CustomFragmentPagerAdapter(getSupportFragmentManager(), titles, fragments);
        viewpager.setAdapter(pagerAdapter);
        tabLayout.setViewPager(viewpager);
        tabLayout.setTabSpaceEqual(false);
        //videoPlayer.startButton.performClick();
        if (TextUtils.isEmpty(mVideoPath)) {
            flAllcontainer.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(mThumbPath)) {
                flAllcontainer.setVisibility(View.VISIBLE);
                videoImage.setVisibility(View.VISIBLE);
                GlideManager.getInstance().load(this, mThumbPath, videoImage);
            }

        } else {
            ivPicLive.setVisibility(View.GONE);
            videoPlayer.setVisibility(View.VISIBLE);
            videoPlayer.setUp(mVideoPath, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "");
            if (!TextUtils.isEmpty(mThumbPath)) {
                GlideManager.getInstance().load(this, mThumbPath, videoPlayer.thumbImageView);
            }
            videoPlayer.fullscreenButton.setVisibility(View.INVISIBLE);
            videoPlayer.progressBar.setVisibility(View.INVISIBLE);
            videoPlayer.setIsLiveStream(false);
            flAllcontainer.setVisibility(View.VISIBLE);



            mHandler.post(mRunnable);
        }


    }

    @OnClick({R.id.iv_back, R.id.zan_panel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.zan_panel:
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
                String url = Consts.BASE_URL + "/Classdisplay/setFabulous";
                Map<String, String> params = new HashMap<>();
                params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
                params.put("did", videoId);
                OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
                    @Override
                    public void onSuccess(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String code = jsonObject.optString("code");
                            String msg = jsonObject.optString("msg");
                            ToastUtils.getInstance().showToast(PartyVideoPlayActivity.this, msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(String errorMsg) {
                        ToastUtils.getInstance().showToast(PartyVideoPlayActivity.this, "网络异常~");
                    }
                });
                break;
        }
    }

    int FristInto = 0;
    Boolean isPause = true;
    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {

        public void run() {
            //   try {
            if (videoPlayer.currentState == videoPlayer.CURRENT_STATE_PLAYING) {
                if (FristInto==0){
                    JZMediaManager.seekTo(0);   //第一次播放回到0秒，解决缓存导致刚开始播放就弹出验证码的问题
                    FristInto++;
                }

                long totalPositionWhenPlaying = videoPlayer.getDuration();////总时长
                long currentPositionWhenPlaying = videoPlayer.getCurrentPositionWhenPlaying();////获取当前播放进度
                if (currentPositionWhenPlaying > totalPositionWhenPlaying / 2) {
                    //时间过半，需要验证
                    if (isPause) {
                        JZVideoPlayer.goOnPlayOnPause();
                        isPause = false;
                        Intent mIntent = new Intent();
                        mIntent.setClass(PartyVideoPlayActivity.this, VerificationCodeActivity.class);
                        startActivity(mIntent);
                    }

                }
                if ((totalPositionWhenPlaying - 100) <= currentPositionWhenPlaying) {
                    WriteReadListUtil mWriteReadListUtil = new WriteReadListUtil();
                    mWriteReadListUtil.UpLoadWriteInfo(PartyVideoPlayActivity.this, "2", videoId, totalPositionWhenPlaying);
                    Log.e("播放完毕", "总时长" + totalPositionWhenPlaying);
                    //isPause = true;
                }
                //每0.1秒执行一次
                // mHandler.postDelayed(mRunnable, 100);
            }
//            } catch (Exception e) {
//                //每0.1秒执行一次
            mHandler.postDelayed(mRunnable, 100);
//            }


        }

    };

    @Override
    protected void initToolbar() {
    }

    @Override
    protected void onDestroy() {
        JZVideoPlayer.releaseAllVideos();
        mHandler.removeCallbacks(mRunnable);
        videoPlayer.removeAllViews();


        if (TextUtils.isEmpty(mVideoPath)) {
            WriteReadListUtil mWriteReadListUtil = new WriteReadListUtil();
            mWriteReadListUtil.UpLoadWriteInfo(PartyVideoPlayActivity.this, "2", videoId, 0);
        }

        super.onDestroy();

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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        int msgType = event.msgType;
        if (msgType == MessageEvent.MSG_FINISH_VIDEO_TIME) {
            //刷新页面
            ToastUtils.getInstance().showToast(PartyVideoPlayActivity.this, "您未正常观看视频，已退出详情页！");
            finish();
        }
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
