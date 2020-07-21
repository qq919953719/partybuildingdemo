package com.longhoo.net.manageservice.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.longhoo.net.MyApplication;
import com.longhoo.net.R;
import com.longhoo.net.manageservice.adapter.ErweimaSignAdapter;
import com.longhoo.net.manageservice.bean.ErWeimaSignBean;
import com.longhoo.net.manageservice.bean.OrganizationLifeDetailBean;
import com.longhoo.net.mine.adapter.NoteDetailImageAdapter;
import com.longhoo.net.mine.ui.ReleaseOrgLifeActivity;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.DisplayUtil;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.GlideManager;
import com.longhoo.net.utils.MessageEvent;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.utils.Utils;
import com.longhoo.net.utils.httprequest.OkHttpCallback;
import com.longhoo.net.utils.httprequest.OkHttpUtil;
import com.longhoo.net.widget.CustomInputDialog;
import com.longhoo.net.widget.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * 组织生活详情
 */

public class OrganizationLifedetailActivity extends BaseActivity implements CustomInputDialog.OnSumbitListener {


    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.progress_panale)
    FrameLayout progressPanale;
    @BindView(R.id.tv_contenttitle)
    TextView tvContenttitle;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.lv_meetingtype)
    LinearLayout lvMeetingtype;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_host)
    TextView tvHost;
    @BindView(R.id.tv_recorder)
    TextView tvRecorder;
    @BindView(R.id.tv_participate_people)
    TextView tvParticipatePeople;
    @BindView(R.id.tv_sign_people)
    TextView tvSignPeople;
    @BindView(R.id.wb_content)
    WebView wbContent;
    @BindView(R.id.ll_action_people)
    LinearLayout llActionPeople;
    @BindView(R.id.iv_erweima)
    ImageView ivErweima;
    @BindView(R.id.tv_sign_name)
    TextView tvSignName;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.tv_action_title)
    TextView tvActionTitle;
    @BindView(R.id.tv_action_reson)
    TextView tvActionReson;
    @BindView(R.id.tv_action_post)
    TextView tvActionPost;
    @BindView(R.id.ll_action_panel)
    LinearLayout llActionPanel;
    @BindView(R.id.tv_leave_people)
    TextView tvLeavePeople;
    @BindView(R.id.tv_metting_time)
    TextView tvMettingTime;
    @BindView(R.id.tv_jy_info)
    TextView tvJyInfo;
    @BindView(R.id.rv_jy_image)
    RecyclerView rvJyImage;
    @BindView(R.id.ll_hyjy)
    LinearLayout llHyjy;
    @BindView(R.id.tv_cancel_metting)
    TextView tvCancelMetting;
    @BindView(R.id.tv_action_input)
    TextView tvActionInput;
    @BindView(R.id.tv_cancel_metting_action_reson)
    TextView tvCancelMettingActionReson;
    @BindView(R.id.ll_cancel_metting_action_pane3)
    LinearLayout llCancelMettingActionPane3;
    @BindView(R.id.video_player)
    JZVideoPlayerStandard videoPlayer;
    @BindView(R.id.iv_pic_live)
    ImageView ivPicLive;
    @BindView(R.id.fl_container)
    FrameLayout flContainer;
    @BindView(R.id.tv_cancel_metting_action_title)
    TextView tvCancelMettingActionTitle;
    @BindView(R.id.ll_action_pane2)
    LinearLayout llActionPane2;
    @BindView(R.id.lv_meetingcontent)
    LinearLayout lvMeetingcontent;
    @BindView(R.id.tv_read_people)
    TextView tvReadPeople;
    @BindView(R.id.lv_readinfo_line)
    LinearLayout lvReadinfoLine;
    @BindView(R.id.tv_already_read)
    TextView tvAlreadyRead;
    @BindView(R.id.lv_already_read)
    LinearLayout lvAlreadyRead;
    @BindView(R.id.tv_delete_metting)
    TextView tvDeleteMetting;
    @BindView(R.id.tv_change_metting)
    TextView tvChangeMetting;
    @BindView(R.id.tv_important_metting)
    TextView tvImportantMetting;
    private String tid = "";
    private List<String> summaryImageList = new ArrayList<>();
    private CustomInputDialog inputDialog;
    private Activity mActivity;
    private NoteDetailImageAdapter adapter;
    private String summaryInfo;

    @Override
    protected int getContentId() {
        return R.layout.activity_organization_lifedetail;
    }

    public static void goTo(Context context, String tid) {
        Intent intent = new Intent(context, OrganizationLifedetailActivity.class);
        intent.putExtra("tid", tid);
        context.startActivity(intent);
    }

    @Override
    protected void initViews() {
        EventBus.getDefault().register(this);
        if (getIntent() != null) {
            tid = getIntent().getStringExtra("tid");
        }

        mActivity = OrganizationLifedetailActivity.this;
        WebSettings webSettings = wbContent.getSettings();
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setJavaScriptEnabled(true);
        wbContent.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) { //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(url);
                return true;
            }
        });
        adapter = new NoteDetailImageAdapter(OrganizationLifedetailActivity.this, summaryImageList);
        rvJyImage.setAdapter(adapter);
        rvJyImage.setLayoutManager(new LinearLayoutManager(OrganizationLifedetailActivity.this));
        rvJyImage.setNestedScrollingEnabled(false);
        getData();
        getErweima();
    }

    private void getDel() {
        String url = Consts.BASE_URL + "/Orgmeeting/delMeeting";
        Map<String, String> map = new HashMap<>();
        map.put("uid", EnterCheckUtil.getInstance().getUid_Token()[0]);
        map.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        map.put("tid", tid + "");
        OkHttpUtil.getInstance().doAsyncPost(url, map, new OkHttpCallback() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onSuccess(String response) {
                //处理数据
                Gson gson = new Gson();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.optString("code");
                    String msg = jsonObject.optString("msg");
                    ToastUtils.getInstance().showToast(mActivity, msg);
                    if (code.equals("0")) {
                        ToastUtils.getInstance().showToast(mActivity, msg);
                        MessageEvent event = new MessageEvent();
                        event.msgType = MessageEvent.MSG_REFRESH_ORG_LIFE_LIST;
                        EventBus.getDefault().post(event);
                        finish();


                    }
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });
    }

    @OnClick({R.id.tv_important_metting, R.id.tv_action_post, R.id.tv_delete_metting,R.id.tv_change_metting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_important_metting:
                if (summaryImageList.size() > 0 || !TextUtils.isEmpty(summaryInfo)) {
                    //修改会议纪要
                    EditSummaryActivity.goEdit(this, tid);
                } else {
                    //添加会议纪要
                    AddSummaryActivity.goTo(this, tid);
                }


                //修改组织生活

//                Intent intent = new Intent(OrganizationLifedetailActivity.this, ReleaseOrgLifeActivity.class);
//                intent.putExtra("detailBean", detailBean.getData());
//                startActivity(intent);
//删除组织生活
                //  getDel();

                break;


            case R.id.tv_delete_metting:
                getDel();
                break;
            case R.id.tv_change_metting:
                Intent intent = new Intent(OrganizationLifedetailActivity.this, ReleaseOrgLifeActivity.class);
                intent.putExtra("detailBean", detailBean.getData());
                startActivity(intent);
                break;

            case R.id.tv_action_post:
                inputDialog = CustomInputDialog.Build(this)
                        .setCustomTitle("请假原因")
                        .setOnSubmitListener(this);
                inputDialog.show();
                break;
        }
    }

    @Override
    protected void initToolbar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        tvTitle.setText("详情");
        tvImportantMetting.setText("添加会议纪要");
        toolbar.setNavigationIcon(R.drawable.left_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
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

    OrganizationLifeDetailBean detailBean;

    private void getData() {
        String url = Consts.BASE_URL + "/Orgmeeting/meetingInfo";
        Map<String, String> map = new HashMap<>();
        map.put("uid", EnterCheckUtil.getInstance().getUid_Token()[0]);
        map.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        map.put("tid", tid + "");

        OkHttpUtil.getInstance().doAsyncPost(url, map, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {

                ULog.e(Consts.TAG, "修改会议纪要详情1：" + response);
                progressPanale.setVisibility(View.GONE);
                //处理数据
                Gson gson = new Gson();
                try {
                    detailBean = gson.fromJson(response, OrganizationLifeDetailBean.class);
                    String code = detailBean.getCode();
                    if (!TextUtils.equals(code, "0")) {
                        ToastUtils.getInstance().showToast(mActivity, "获取数据失败~");
                        return;
                    }
                    if ((detailBean.getData().getUserid() + "").equals(EnterCheckUtil.getInstance().getUid_Token()[0])) {

//                        tvCancelMetting.setVisibility(View.VISIBLE);
//                        tvCancelMetting.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                Intent intent = new Intent();
//                                intent.putExtra("type", "1");
//                                intent.putExtra("id", tid);
//                                intent.setClass(OrganizationLifedetailActivity.this, CancelMeetingActivity.class);
//                                startActivity(intent);
//                            }
//                        });
                        tvCancelMetting.setVisibility(View.GONE);
                    } else {
                        tvCancelMetting.setVisibility(View.GONE);
                    }


                    if (!TextUtils.isEmpty(detailBean.getData().getVideourl())) {
                        flContainer.setVisibility(View.VISIBLE);
                        ivPicLive.setVisibility(View.GONE);
                        videoPlayer.setVisibility(View.VISIBLE);
                        videoPlayer.setUp(detailBean.getData().getVideourl(), JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "");
                        if (!TextUtils.isEmpty(detailBean.getData().getVideoimg())) {
                            GlideManager.getInstance().load(OrganizationLifedetailActivity.this, detailBean.getData().getVideoimg(), videoPlayer.thumbImageView);
                        }
                        videoPlayer.setIsLiveStream(false);
                        //videoPlayer.startButton.performClick();
                        videoPlayer.fullscreenButton.setVisibility(View.GONE);

                    } else {
                        flContainer.setVisibility(View.GONE);
                    }

                    tvType.setText(detailBean.getData().getType());
                    tvContenttitle.setText(detailBean.getData().getContent());
                    tvTime.setText(Utils.getDataTime(detailBean.getData().getTime() + ""));
                    tvAddress.setText(detailBean.getData().getMeeting_address());
                    tvHost.setText(detailBean.getData().getCompere());
                    tvRecorder.setText(detailBean.getData().getRecorder());
                    tvMettingTime.setText(Utils.getDataTimeMinute(detailBean.getData().getMeeting_time() + ""));
                    String attend = detailBean.getData().getAttend_memb().replace("\\r", "\r");
                    tvParticipatePeople.setText(attend);
                    String signMemb = detailBean.getData().getSign_memb().replace("\\r", "\r");
                    tvSignPeople.setText(signMemb);
                    wbContent.loadData(detailBean.getData().getDesc(), "text/html; charset=UTF-8", null);
                    wbContent.setVisibility(View.VISIBLE);
                    MyApplication app = (MyApplication) getApplicationContext();
                    if (app.getmLoginBean().getData().getUid().equals(detailBean.getData().getUserid() + "") && (!detailBean.getData().getUname_str().isEmpty())) {
                        lvReadinfoLine.setVisibility(View.VISIBLE);
                        tvReadPeople.setText(detailBean.getData().getUname_str());

                        tvAlreadyRead.setText(detailBean.getData().getUnback());
                        lvAlreadyRead.setVisibility(View.VISIBLE);
                    } else {
                        lvReadinfoLine.setVisibility(View.GONE);
                        lvAlreadyRead.setVisibility(View.GONE);
                    }
                    if (app.getmLoginBean().getData().getUid().equals(detailBean.getData().getUserid() + "")){

                        tvChangeMetting.setVisibility(View.VISIBLE);
                        tvDeleteMetting.setVisibility(View.VISIBLE);

                    }
                    else {
                        tvChangeMetting.setVisibility(View.GONE);
                        tvDeleteMetting.setVisibility(View.GONE);
                    }

                    //请假人员
                    List<OrganizationLifeDetailBean.DataBean.LeavepeopleBean> leavePeopleList = detailBean.getData().getLeavepeople();
                    String leavePeoples = "";
                    if (leavePeopleList.size() > 0) {
                        for (OrganizationLifeDetailBean.DataBean.LeavepeopleBean bean : leavePeopleList) {
                            leavePeoples += bean.getRealname() + "  ";
                        }
                        leavePeoples.substring(0, leavePeoples.lastIndexOf("  "));
                    } else {
                        leavePeoples = "无";
                    }

                    tvLeavePeople.setText(leavePeoples);
                    if (TextUtils.isEmpty(detailBean.getData().getLeavereason())) {
                        tvActionTitle.setVisibility(View.GONE);
                        tvActionReson.setVisibility(View.GONE);
                    } else {
                        tvActionTitle.setVisibility(View.VISIBLE);
                        tvActionReson.setVisibility(View.VISIBLE);
                        tvActionReson.setText(detailBean.getData().getLeavereason());
                    }
                    int leavestatus = detailBean.getData().getLeavestatus();
                    if (leavestatus == 0) {
                        tvActionPost.setEnabled(true);
                    } else {
                        tvActionPost.setEnabled(false);
                        tvActionPost.setText("已请假");
                    }
                    //参会的显示请假按钮
                    int isAttend = detailBean.getData().getIs_attend();
                    if (isAttend == 0) {
                        tvActionPost.setVisibility(View.GONE);
                    } else {
                        tvActionPost.setVisibility(View.VISIBLE);
                        //签到的不显示请假按钮
                        if (detailBean.getData().getIs_sign() == 1) {
                            //已签到
                            tvActionPost.setVisibility(View.GONE);
                        } else {
                            tvActionPost.setVisibility(View.VISIBLE);
                        }
                    }
                    //判断是否确认参加...1已确认参加....2未确认参加
                    int is_join = detailBean.getData().getIs_join();
                    if (is_join == 1) {
                        tvActionInput.setText("已确认参加");
                        tvActionInput.setEnabled(false);
                        tvActionPost.setEnabled(false);
                        //置灰
//                        tvActionInput.setVisibility(View.GONE);
//                        tvActionPost.setVisibility(View.GONE);
                    } else if (is_join == 2) {
                        if (leavestatus == 0) {
                            tvActionInput.setText("参加");
                            tvActionInput.setEnabled(true);
                            tvActionPost.setEnabled(true);
                            tvActionInput.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    onSubmit_Join();
                                }
                            });
                            //置红
                            tvActionInput.setVisibility(View.VISIBLE);
                            tvActionPost.setVisibility(View.VISIBLE);
                            if (detailBean.getData().getIs_sign() == 1) {
                                tvActionInput.setVisibility(View.GONE);
                                tvActionPost.setVisibility(View.GONE);
                            }
                        } else {
                            tvActionInput.setEnabled(false);
                            tvActionPost.setEnabled(false);
                        }
                    } else if (is_join == 3) {
                        tvActionInput.setText("");
                        tvActionInput.setVisibility(View.GONE);
                        tvActionPost.setVisibility(View.GONE);
                    } else {
                        ToastUtils.getInstance().showToast(mActivity, "is_join字段错误！~");
                    }
                    //如果是本人，则显示添加会议纪要
                    String uid = detailBean.getData().getUserid() + "";
                    String localUid = EnterCheckUtil.getInstance().getUid_Token()[0];
                    if (TextUtils.equals(uid, localUid)) {
                        tvImportantMetting.setVisibility(View.VISIBLE);
                    } else {
                        tvImportantMetting.setVisibility(View.GONE);
                    }
                    //会议纪要
                    summaryInfo = detailBean.getData().getSummaryInfo();
                    String summaryimg = detailBean.getData().getSummaryimg();
                    if (TextUtils.isEmpty(summaryInfo) && TextUtils.isEmpty(summaryimg)) {
                        llHyjy.setVisibility(View.GONE);
                    } else {
                        llHyjy.setVisibility(View.VISIBLE);
                    }
                    tvJyInfo.setText(summaryInfo);
                    summaryImageList.clear();
                    if (!TextUtils.isEmpty(summaryimg)) {
                        summaryImageList.addAll(Arrays.asList(summaryimg.split(",")));
                    }
                    adapter.notifyDataSetChanged();
                    //记录人
                    if (TextUtils.equals(EnterCheckUtil.getInstance().getUid_Token()[0], detailBean.getData().getRecorder1() + "")) {
                        tvImportantMetting.setVisibility(View.VISIBLE);
                    } else {
                        tvImportantMetting.setVisibility(View.GONE);
                    }
                    if (summaryImageList.size() > 0 || !TextUtils.isEmpty(summaryInfo)) {
                        tvImportantMetting.setText("修改会议纪要");
                    }
                    if (TextUtils.isEmpty(detailBean.getData().getCmr())) {
                        llCancelMettingActionPane3.setVisibility(View.GONE);
                        //没有会议理由隐藏
                    } else {
                        llCancelMettingActionPane3.setVisibility(View.VISIBLE);
                        tvCancelMettingActionReson.setText(detailBean.getData().getCmr());
                        tvActionInput.setText("已取消");
                        tvActionInput.setEnabled(false);
                        tvActionPost.setEnabled(false);
                        tvCancelMetting.setVisibility(View.GONE);
                        tvActionPost.setEnabled(false);
                    }

                    //组织生活详情 =》会议纪要（内容、图片、视频）
                    //sum_show = 1 显示
                    //sum_show = 0 不显示
                    int isSum_show = detailBean.getData().getSum_show();
                    if (isSum_show == 0) {
                        lvMeetingcontent.setVisibility(View.GONE);
                    } else {
                        lvMeetingcontent.setVisibility(View.VISIBLE);
                    }
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(String errorMsg) {
                progressPanale.setVisibility(View.GONE);
                ToastUtils.getInstance().showToast(mActivity, "网络错误~");

            }
        });
    }

    private void getErweima() {
        String url = Consts.BASE_URL + "/Orgmeeting/createEwm";
        Map<String, String> map = new HashMap<>();
        map.put("uid", EnterCheckUtil.getInstance().getUid_Token()[0]);
        map.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        map.put("id", tid + "");
        OkHttpUtil.getInstance().doAsyncPost(url, map, new OkHttpCallback() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onSuccess(String response) {
                //处理数据
                Gson gson = new Gson();
                try {
                    ErWeimaSignBean signBean = gson.fromJson(response, ErWeimaSignBean.class);
                    int code = signBean.getCode();
                    if (code == 0) {
                        if (!OrganizationLifedetailActivity.this.isDestroyed()) {
                            Glide.with(mActivity).asBitmap().load(signBean.getData().getEwm_img()).into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                    int imgWidth = resource.getWidth();
                                    int imgHeight = resource.getHeight();
                                    int screenWidth = DisplayUtil.getScreenWidth(mActivity) - 2 * DisplayUtil.dp2px(mActivity, 10);
                                    float ratio = (float) imgWidth / (float) imgHeight;
                                    int height = (int) (screenWidth / ratio);
                                    LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) ivErweima.getLayoutParams();
                                    lp.width = screenWidth;
                                    lp.height = height;
                                    ivErweima.setLayoutParams(lp);
                                    ivErweima.setImageBitmap(resource);
                                }
                            });
                        }
                        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
                        List<ErWeimaSignBean.DataBean.UserlistBean> list = new ArrayList<>();
                        if (signBean.getData().getUserlist() != null) {
                            list.addAll(signBean.getData().getUserlist());
                        }
                        recyclerView.setAdapter(new ErweimaSignAdapter(mActivity, list));
                        ivErweima.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);
                        if (list.size() > 0) {
                            tvSignName.setVisibility(View.VISIBLE);
                        }

                    }
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        JZVideoPlayer.releaseAllVideos();
        if (wbContent != null) {
            ViewGroup parent = (ViewGroup) wbContent.getParent();
            if (parent != null) {
                parent.removeView(wbContent);
            }
            wbContent.removeAllViews();
            wbContent.destroy();
        }
        CustomInputDialog.strContent = "";
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


    public void onSubmit_Join() {
        //is_join  参数  fid   组织生活tid 活动报名id
        String url = Consts.BASE_URL + "/index/is_join";


        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("fid", tid + "");
        params.put("type", "2");

        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.optString("code");
                    String msg = jsonObject.optString("msg");
                    ToastUtils.getInstance().showToast(mActivity, msg);
                    if (TextUtils.equals(code, "0")) {
                        tvActionPost.setEnabled(false);
                        tvActionInput.setEnabled(false);
                        getData();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(mActivity, "网络错误~");
            }
        });

    }

    @Override
    public void onSubmit() {
        String url = Consts.BASE_URL + "/application/setLeave";
        final String content = inputDialog.getCustomContent();
        if (TextUtils.isEmpty(content)) {
            ToastUtils.getInstance().showToast(this, "请输入请假原因！");
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("tid", tid + "");
        params.put("reason", content);
        params.put("type", "2");
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.optString("code");
                    String msg = jsonObject.optString("msg");
                    ToastUtils.getInstance().showToast(mActivity, msg);
                    if (TextUtils.equals(code, "0")) {
                        tvActionPost.setEnabled(false);
                        tvActionPost.setText("已请假");
                        tvActionInput.setEnabled(false);
                        getData();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(mActivity, "网络错误~");
            }
        });
        if (inputDialog != null) {
            inputDialog.dismiss();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event.msgType == MessageEvent.MSG_REFRESH_ORG_LIFE) {
            getData();
        }
        if (event.msgType == MessageEvent.MSG_FINISH_ORGNIZATION_ARCHIVES) {
            event.msgType = MessageEvent.MSG_REFRESH_ORG_LIFE_LIST;
            EventBus.getDefault().post(event);
//            finish();
            getData();
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
