package com.longhoo.net.manageservice.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.longhoo.net.MyApplication;
import com.longhoo.net.R;
import com.longhoo.net.manageservice.bean.AwardDetailsBean;
import com.longhoo.net.mine.adapter.NoteDetailImageAdapter;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.FileUtils;
import com.longhoo.net.utils.GlideManager;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.httprequest.OkHttpCallback;
import com.longhoo.net.utils.httprequest.OkHttpUtil;
import com.longhoo.net.widget.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * 个人获奖/集体获奖详情
 */
public class AwardDetailsActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_contenttitle)
    TextView tvContenttitle;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_award_type)
    TextView tvAwardType;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.tv_award_level)
    TextView tvAwardLevel;
    @BindView(R.id.tv_award_time)
    TextView tvAwardTime;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.ll_award_level_panel)
    LinearLayout llAwardLevelPanel;
    @BindView(R.id.video_player)
    JZVideoPlayerStandard videoPlayer;
    @BindView(R.id.iv_pic_live)
    ImageView ivPicLive;
    @BindView(R.id.fl_container)
    FrameLayout flContainer;
    @BindView(R.id.iv_file)
    ImageView ivFile;
    @BindView(R.id.tv_file)
    TextView tvFile;
    @BindView(R.id.ll_file)
    LinearLayout llFile;
    @BindView(R.id.tv_read)
    TextView tvRead;
    @BindView(R.id.lv_read)
    LinearLayout lvRead;
    private String sid = "";
    List<String> picList = new ArrayList<>();
    private NoteDetailImageAdapter adapter;

    public static void goTo(Context context, String sid) {
        Intent intent = new Intent(context, AwardDetailsActivity.class);
        intent.putExtra("sid", sid);
        context.startActivity(intent);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        if (height < width) {
        } else {
            JZVideoPlayer.releaseAllVideos();
        }

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
    protected void onDestroy() {
        super.onDestroy();
        JZVideoPlayer.releaseAllVideos();
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_award_details;
    }

    @Override
    protected void initViews() {
        if (getIntent() != null) {
            sid = getIntent().getStringExtra("sid");
        }
        adapter = new NoteDetailImageAdapter(this, picList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);
        getData();
    }

    @Override
    protected void initToolbar() {
        tvTitle.setText("获奖详情");
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.left_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private String filePath = "";
    private String fileName = "";
    private String fileSize = "";

    @OnClick({R.id.ll_file})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_file:
                //WebViewActivity.goToWebView(this,"https://view.officeapps.live.com/op/view.aspx?src="+filePath,fileName,false);
                Intent intent = new Intent(this, OpenFileActivity.class);
                intent.putExtra("file_path", filePath);
                intent.putExtra("file_name", fileName);
                intent.putExtra("file_size", fileSize);
                startActivity(intent);
                break;
        }
    }

    private void setFileImage(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return;
        }
        String fileType = FileUtils.getFileType(filePath);
        if (TextUtils.equals(fileType, ".doc") || TextUtils.equals(fileType, ".docx")) {
            ivFile.setImageResource(R.drawable.file_word);
        } else if (TextUtils.equals(fileType, ".xlsx") || TextUtils.equals(fileType, ".xls")) {
            ivFile.setImageResource(R.drawable.file_excel);
        } else if (TextUtils.equals(fileType, ".ppt") || TextUtils.equals(fileType, ".pptx")) {
            ivFile.setImageResource(R.drawable.file_ppt);
        } else if (TextUtils.equals(fileType, ".pdf")) {
            ivFile.setImageResource(R.drawable.file_pdf);
        } else {
            ivFile.setImageResource(R.drawable.file_normal);
        }
    }

    /**
     * 获取数据
     */
    private void getData() {
        String url = Consts.BASE_URL + "/Inforeporting/infoReportingDetails";
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("sid", sid + "");
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                Gson gson = new Gson();
                List<String> tempList = null;
                try {
                    AwardDetailsBean detailsBean = gson.fromJson(response, AwardDetailsBean.class);
                    String code = detailsBean.getCode();
                    String msg = detailsBean.getMsg();
                    if (!TextUtils.equals(code, "0")) {
                        ToastUtils.getInstance().showToast(AwardDetailsActivity.this, msg);
                        return;
                    }
                    tvContenttitle.setText(detailsBean.getData().getData().getReward_name());
                    tvTime.setText(detailsBean.getData().getData().getTime());
                    if (detailsBean.getData().getData().getType() == 1) {
                        tvAwardType.setText("党建类");
                    } else {
                        tvAwardType.setText("非党建类");
                    }


                    if (detailsBean.getData().getData().getBack()==null){
                        lvRead.setVisibility(View.GONE);
                    }else {
                        MyApplication app = (MyApplication) getApplicationContext();
                        if (app.getmLoginBean().getData().getUid().equals(detailsBean.getData().getData().getAdminid() + "") && (!detailsBean.getData().getData().getBack().isEmpty())) {
                            tvRead.setText(detailsBean.getData().getData().getBack());

                            lvRead.setVisibility(View.VISIBLE);
                        } else {
                            lvRead.setVisibility(View.GONE);
                        }
                    }



                    tvCompany.setText(detailsBean.getData().getData().getReward_company());
                    tvAwardLevel.setText(detailsBean.getData().getData().getReward_level());
                    tvAwardTime.setText(detailsBean.getData().getData().getReward_time());
                    if (TextUtils.isEmpty(detailsBean.getData().getData().getReward_level())) {
                        llAwardLevelPanel.setVisibility(View.GONE);
                    } else {
                        llAwardLevelPanel.setVisibility(View.VISIBLE);
                    }
                    tempList = detailsBean.getData().getData().getFiles();
                    if (tempList != null && tempList.size() > 0) {
                        picList.addAll(tempList);
                    }
                    if (!TextUtils.isEmpty(detailsBean.getData().getData().getVideourl())) {
                        flContainer.setVisibility(View.VISIBLE);
                        ivPicLive.setVisibility(View.GONE);
                        videoPlayer.setVisibility(View.VISIBLE);
                        videoPlayer.setUp(detailsBean.getData().getData().getVideourl(), JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "");
                        if (!TextUtils.isEmpty(detailsBean.getData().getData().getVideoimg())) {
                            GlideManager.getInstance().load(AwardDetailsActivity.this, detailsBean.getData().getData().getVideoimg(), videoPlayer.thumbImageView);
                        }
                        videoPlayer.setIsLiveStream(false);
                        //videoPlayer.startButton.performClick();
                        videoPlayer.fullscreenButton.setVisibility(View.GONE);

                    } else {
                        flContainer.setVisibility(View.GONE);
                    }


                    if (detailsBean.getData().getData().getAppendix() != null && detailsBean.getData().getData().getAppendix_name() != null) {
                        filePath = detailsBean.getData().getData().getAppendix();
                        fileName = detailsBean.getData().getData().getAppendix_name();
                        llFile.setVisibility(View.VISIBLE);
                        tvFile.setText(fileName);
                        setFileImage(filePath);
                    }


                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
                picList.clear();
                if (tempList != null && tempList.size() > 0) {
                    picList.addAll(tempList);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(AwardDetailsActivity.this, "网络异常~");
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
