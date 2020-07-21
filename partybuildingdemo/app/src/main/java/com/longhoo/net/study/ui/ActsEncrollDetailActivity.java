package com.longhoo.net.study.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.longhoo.net.MyApplication;
import com.longhoo.net.R;
import com.longhoo.net.manageservice.adapter.ErweimaSignAdapter;
import com.longhoo.net.manageservice.bean.ActsEncrollDetailBean;
import com.longhoo.net.manageservice.bean.ErWeimaSignBean;
import com.longhoo.net.manageservice.ui.CancelMeetingActivity;
import com.longhoo.net.manageservice.ui.ReleasePartyNumberActivity;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.DisplayUtil;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.MessageEvent;
import com.longhoo.net.utils.ToastUtils;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 活动报名详情
 */
public class ActsEncrollDetailActivity extends BaseActivity implements CustomInputDialog.OnSumbitListener {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_head_time)
    TextView tvHeadTime;
    @BindView(R.id.tv_encroll_time)
    TextView tvEncrollTime;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_ounit)
    TextView tvOunit;
    @BindView(R.id.ounit_panle)
    LinearLayout ounitPanle;
    @BindView(R.id.tag_name_time)
    TextView tagNameTime;
    @BindView(R.id.tv_acts_time)
    TextView tvActsTime;
    @BindView(R.id.tv_train_obj)
    TextView tvTrainObj;
    @BindView(R.id.ll_train_obj)
    LinearLayout llTrainObj;
    @BindView(R.id.tag_name_intro)
    TextView tagNameIntro;
    @BindView(R.id.tv_acts_intro)
    TextView tvActsIntro;
    @BindView(R.id.tag_name_content)
    TextView tagNameContent;
    @BindView(R.id.web_view_content)
    WebView webViewContent;
    @BindView(R.id.tag_name_tuwen)
    TextView tagNameTuwen;
    @BindView(R.id.web_view_tuwen)
    WebView webViewTuwen;
    @BindView(R.id.iv_erweima)
    ImageView ivErweima;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.tv_sign_name)
    TextView tvSignName;
    @BindView(R.id.tv_canhui_people)
    TextView tvCanhuiPeople;
    @BindView(R.id.ll_canhui_people)
    LinearLayout llCanhuiPeople;
    @BindView(R.id.tv_sign_people)
    TextView tvSignPeople;
    @BindView(R.id.ll_sign_people)
    LinearLayout llSignPeople;
    @BindView(R.id.tv_cancel_metting)
    TextView tvCancelMetting;
    @BindView(R.id.tv_action_title)
    TextView tvActionTitle;
    @BindView(R.id.tv_action_reson)
    TextView tvActionReson;
    @BindView(R.id.tv_action_post)
    TextView tvActionPost;
    @BindView(R.id.tv_cancel_metting_action_reson)
    TextView tvCancelMettingActionReson;
    @BindView(R.id.ll_cancel_metting_action_pane3)
    LinearLayout llCancelMettingActionPane3;
    @BindView(R.id.sv_all_view)
    ScrollView svAllView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.tv_leave_people)
    TextView tvLeavePeople;
    @BindView(R.id.tv_cancel_metting_action_title)
    TextView tvCancelMettingActionTitle;
    @BindView(R.id.tv_read_people)
    TextView tvReadPeople;
    @BindView(R.id.lv_readinfo_line)
    LinearLayout lvReadinfoLine;
    @BindView(R.id.ll_action_panel)
    LinearLayout llActionPanel;
    @BindView(R.id.tv_already_read)
    TextView tvAlreadyRead;
    @BindView(R.id.lv_already_read)
    LinearLayout lvAlreadyRead;
    @BindView(R.id.tv_already_report)
    TextView tvAlreadyReport;
    @BindView(R.id.lv_already_report)
    LinearLayout lvAlreadyReport;
    private int tag;
    private String title = "";
    private String id;
    private Activity mActivity;
    private CustomInputDialog inputDialog;

    public static void goTo(Context context, String id) {
        Intent intent = new Intent(context, ActsEncrollDetailActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getData();
    }

    @Override
    public void onSubmit() {
        String url = Consts.BASE_URL + "/application/setLeave";
        final String content = inputDialog.getCustomContent();
        if (TextUtils.isEmpty(content)) {
            ToastUtils.getInstance().showToast(mActivity, "请输入请假原因！");
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("tid", id + "");
        params.put("reason", content);
        params.put("type", "4");

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
                        tvActionPost.setEnabled(false);
                        tvActionTitle.setVisibility(View.VISIBLE);
                        tvActionReson.setVisibility(View.VISIBLE);
                        tvActionReson.setText(content);
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

    @Override
    protected int getContentId() {
        return R.layout.activity_acts_encroll_detail;
    }

    @Override
    protected void initViews() {
        EventBus.getDefault().register(this);
        if (getIntent() != null) {
            tag = getIntent().getIntExtra("tag", 0);
            id = getIntent().getStringExtra("id");
        }
        mActivity = ActsEncrollDetailActivity.this;
        getData();
    }

    void getData() {


        WebSettings webSettings = webViewContent.getSettings();
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setJavaScriptEnabled(true);
        webViewContent.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) { //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(url);
                return true;
            }
        });
        WebSettings webSettings2 = webViewTuwen.getSettings();
        webSettings2.setDefaultTextEncodingName("UTF-8");
        webSettings2.setJavaScriptEnabled(true);
        webViewTuwen.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) { //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(url);
                return true;
            }
        });

        String url = "";
        ounitPanle.setVisibility(View.VISIBLE);
        url = Consts.BASE_URL + "/Activity/getActivityInfo";
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("id", id);

        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                Gson gson = new Gson();
                String msg = "";
                try {
                    final ActsEncrollDetailBean bean = gson.fromJson(response, ActsEncrollDetailBean.class);
                    String code = bean.getCode();
                    msg = bean.getMsg();
                    if (!TextUtils.equals(code, "0")) {
                        ToastUtils.getInstance().showToast(ActsEncrollDetailActivity.this, msg);
                        return;
                    }

                    MyApplication app = (MyApplication) getApplicationContext();
                    String Uid = app.getmLoginBean().getData().getUid();
                    if (Uid.equals(bean.getData().getUid() + "")) {
                        tvRight.setVisibility(View.VISIBLE);
                        tvRight.setText("修改党员活动");
                        tvRight.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(ActsEncrollDetailActivity.this, ReleasePartyNumberActivity.class);
                                intent.putExtra("ActivityInfo", bean.getData());
                                startActivity(intent);
                            }
                        });
                    } else {

                    }


                    title = bean.getData().getPtitle();
                    if (title.length() > 15) {
                        title = title.substring(0, 15) + "...";
                    }
//                    if ((bean.getData().getUid() + "").equals(EnterCheckUtil.getInstance().getUid_Token()[0])) {
//                        tvCancelMetting.setVisibility(View.VISIBLE);
//                    } else {
//                        tvCancelMetting.setVisibility(View.GONE);
//                    }
                    tvCancelMetting.setVisibility(View.GONE);
                    tvCancelMetting.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.putExtra("type", "2");
                            intent.putExtra("id", bean.getData().getAid() + "");
                            intent.setClass(ActsEncrollDetailActivity.this, CancelMeetingActivity.class);
                            startActivity(intent);
                        }
                    });

                    if (app.getmLoginBean().getData().getUid().equals(bean.getData().getUid() + "")) {
                        lvReadinfoLine.setVisibility(View.VISIBLE);
                        tvReadPeople.setText(bean.getData().getUname_str());
                        tvAlreadyRead.setText(bean.getData().getUnback());
                        lvAlreadyRead.setVisibility(View.VISIBLE);
                    } else {
                        lvReadinfoLine.setVisibility(View.GONE);
                        lvAlreadyRead.setVisibility(View.GONE);
                    }
                    //已报名人员
//                    if ((!bean.getData().getEnrollNameStr().isEmpty())) {
//
//                    }
                    tvAlreadyReport.setText(bean.getData().getEnrollNameStr());
                    //请假人员
                    List<ActsEncrollDetailBean.DataBean.LeavepeopleBean> leavePeopleList = bean.getData().getLeavepeople();
                    String leavePeoples = "";
                    if (leavePeopleList.size() > 0) {
                        for (ActsEncrollDetailBean.DataBean.LeavepeopleBean beanlist : leavePeopleList) {
                            leavePeoples += beanlist.getRealname() + "  ";
                        }
                        leavePeoples.substring(0, leavePeoples.lastIndexOf("  "));
                    } else {
                        leavePeoples = "无";
                    }

                    tvLeavePeople.setText(leavePeoples);
                    tvLeavePeople.setVisibility(View.GONE);
                    tvTitle.setText(title + "");
                    tvHeadTime.setText(bean.getData().getAddtime());
                    tvEncrollTime.setText(bean.getData().getStime() + " - " + bean.getData().getEtime());
                    tvActsTime.setText(bean.getData().getCdate() + " - " + bean.getData().getEnddate());
                    tvActsIntro.setText(Html.fromHtml(bean.getData().getInfo() + ""));
                    tvAddress.setText(bean.getData().getAddr());
                    tvOunit.setText(bean.getData().getOunit());
                    if (TextUtils.isEmpty(bean.getData().getLeavereason())) {
                        tvActionTitle.setVisibility(View.GONE);
                        tvActionReson.setVisibility(View.GONE);
                    } else {
                        tvActionTitle.setVisibility(View.VISIBLE);
                        tvActionReson.setVisibility(View.VISIBLE);
                        tvActionReson.setText(bean.getData().getLeavereason());
                    }

                    //风采图文
                    if (TextUtils.isEmpty(bean.getData().getUploads())) {
                        webViewTuwen.setVisibility(View.GONE);
                        tagNameTuwen.setVisibility(View.GONE);
                    } else {
                        webViewTuwen.setVisibility(View.VISIBLE);
                        tagNameTuwen.setVisibility(View.VISIBLE);
                        webViewTuwen.loadData(bean.getData().getUploads(), "text/html; charset=UTF-8", null);
                    }
                    //内容
                    if (TextUtils.isEmpty(bean.getData().getInfo())) {
                        webViewContent.setVisibility(View.GONE);
                        tagNameContent.setVisibility(View.GONE);
                    } else {
                        webViewContent.setVisibility(View.VISIBLE);
                        tagNameContent.setVisibility(View.VISIBLE);
                        webViewContent.loadData(bean.getData().getInfo(), "text/html; charset=UTF-8", null);
                    }
                    if (bean.getData().getIsEnroll() == 1) {
                        tvSubmit.setEnabled(false);
                        tvSubmit.setText("未开始");
                        tvActionPost.setVisibility(View.GONE);
                    } else if (bean.getData().getIsEnroll() == 2) {
                        tvSubmit.setEnabled(false);
                        tvSubmit.setText("已结束");
                        tvActionPost.setVisibility(View.GONE);
                    } else if (bean.getData().getIsEnroll() == 3) {
                        tvSubmit.setEnabled(false);
                        tvSubmit.setText("已报名");


                        tvActionPost.setVisibility(View.VISIBLE);
                        if (bean.getData().getLeavestatus() == 0) {
                            tvActionPost.setText("请假");
                            tvActionPost.setEnabled(true);
                            tvActionPost.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    inputDialog = CustomInputDialog.Build(ActsEncrollDetailActivity.this)
                                            .setCustomTitle("请假原因")
                                            .setOnSubmitListener(ActsEncrollDetailActivity.this);
                                    inputDialog.show();

                                }
                            });
                        } else {
                            tvActionPost.setText("已请假");
                            tvActionPost.setEnabled(false);
                        }
                        tvActionPost.setVisibility(View.GONE);
                        //1是已签到2是未签到
                        if (bean.getData().getIs_sign() == 1) {
                            tvActionPost.setEnabled(false);
                            tvActionPost.setVisibility(View.GONE);
                        }
                    } else {


                        tvSubmit.setEnabled(true);
                        tvSubmit.setText("报名");
                        tvActionPost.setVisibility(View.GONE);
                    }
                    //参会人员
                    String canhuiPeople = bean.getData().getMeetingobj();
                    if (TextUtils.isEmpty(canhuiPeople)) {
                        llCanhuiPeople.setVisibility(View.GONE);
                    } else {
                        llCanhuiPeople.setVisibility(View.VISIBLE);
                        tvCanhuiPeople.setText(canhuiPeople);
                    }
                    //签到人员
                    String signPeople = bean.getData().getSignUser();
                    if (TextUtils.isEmpty(signPeople)) {
                        llSignPeople.setVisibility(View.GONE);
                    } else {
                        llSignPeople.setVisibility(View.VISIBLE);
                        tvSignPeople.setText(signPeople);
                    }

                    if (TextUtils.isEmpty(bean.getData().getCmr())) {
                        llCancelMettingActionPane3.setVisibility(View.GONE);

                        //没有会议理由隐藏
                    } else {
                        llCancelMettingActionPane3.setVisibility(View.VISIBLE);
                        tvCancelMettingActionReson.setText(bean.getData().getCmr());
                        tvSubmit.setEnabled(false);
                        tvSubmit.setText("已取消");
                        tvCancelMetting.setVisibility(View.GONE);
                        tvActionPost.setEnabled(false);
                    }
                    svAllView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(ActsEncrollDetailActivity.this, "网络异常~");
            }
        });
        getErweima();
    }

    private boolean isShowRight = true;

    @Override
    protected void initToolbar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.left_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    @OnClick({R.id.tv_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_submit:
                doEncrollActs();
                break;
        }
    }

    /**
     * 活动报名
     */
    private void doEncrollActs() {
        String url = Consts.BASE_URL + "/Activity/setActivityEnroll";
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("aid", id);
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.optString("code");
                    String msg = jsonObject.optString("msg");
                    ToastUtils.getInstance().showToast(ActsEncrollDetailActivity.this, msg);
                    if (TextUtils.equals(code, "-1")) {
                        tvSubmit.setEnabled(false);
                        tvSubmit.setBackground(getResources().getDrawable(R.drawable.bg_disenabled_solid_25));
                    }
                    if (TextUtils.equals(code, "0")) {
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(ActsEncrollDetailActivity.this, "网络异常！");
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (webViewTuwen != null) {
            ViewGroup parent = (ViewGroup) webViewTuwen.getParent();
            if (parent != null) {
                parent.removeView(webViewTuwen);
            }
            webViewTuwen.removeAllViews();
            webViewTuwen.destroy();
        }
        if (webViewContent != null) {
            ViewGroup parent = (ViewGroup) webViewContent.getParent();
            if (parent != null) {
                parent.removeView(webViewContent);
            }
            webViewContent.removeAllViews();
            webViewContent.destroy();
        }
        CustomInputDialog.strContent = "";
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void getErweima() {
        String url = Consts.BASE_URL + "/Activity/createEwm";
        Map<String, String> map = new HashMap<>();
        map.put("uid", EnterCheckUtil.getInstance().getUid_Token()[0]);
        map.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        map.put("id", id + "");
        OkHttpUtil.getInstance().doAsyncPost(url, map, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                //处理数据
                Gson gson = new Gson();
                try {
                    ErWeimaSignBean signBean = gson.fromJson(response, ErWeimaSignBean.class);
                    int code = signBean.getCode();
                    if (code == 0) {
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
                        recyclerView.setLayoutManager(new LinearLayoutManager(ActsEncrollDetailActivity.this));
                        List<ErWeimaSignBean.DataBean.UserlistBean> list = new ArrayList<>();
                        if (signBean.getData().getUserlist() != null) {
                            list.addAll(signBean.getData().getUserlist());
                        }
                        recyclerView.setAdapter(new ErweimaSignAdapter(ActsEncrollDetailActivity.this, list));
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {

        if (event.msgType == MessageEvent.MSG_FINISH_ACTIVITY_ARCHIVES) {
            event.msgType = MessageEvent.MSG_FINISH_SEND_ACTIVITY_ARCHIVES;
            EventBus.getDefault().post(event);
//            finish();

//            llCancelMettingActionPane3.setVisibility(View.VISIBLE);
//            tvCancelMettingActionReson.setText(bean.getData().getCmr());

            getData();

        }


    }


}
