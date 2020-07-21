package com.longhoo.net.manageservice.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.longhoo.net.R;
import com.longhoo.net.widget.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

public class VideoActivity extends BaseActivity {


    @BindView(R.id.video_player)
    JZVideoPlayerStandard videoPlayer;
    @BindView(R.id.iv_pic_live)
    ImageView ivPicLive;
    @BindView(R.id.fl_container)
    FrameLayout flContainer;
    String mVideoPath = "";


    @Override
    protected int getContentId() {
        return R.layout.activity_video;
    }

    public static void startVideoPlay(Context context, String videoUrl) {
        Intent intent = new Intent(context, VideoActivity.class);
        intent.putExtra("videoUrl", videoUrl);
        context.startActivity(intent);
    }

    @Override
    protected void initViews() {
        if (getIntent() != null) {
            mVideoPath = getIntent().getStringExtra("videoUrl");

        }
        // ivPicLive.setVisibility(View.VISIBLE);
        videoPlayer.setVisibility(View.VISIBLE);
        videoPlayer.setUp(mVideoPath, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "视频预览");
        videoPlayer.setIsLiveStream(true);
        videoPlayer.startButton.performClick();
        videoPlayer.fullscreenButton.setVisibility(View.GONE);
        ivPicLive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        videoPlayer.removeAllViews();
        JZVideoPlayer.releaseAllVideos();
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

    @Override
    protected void initToolbar() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
