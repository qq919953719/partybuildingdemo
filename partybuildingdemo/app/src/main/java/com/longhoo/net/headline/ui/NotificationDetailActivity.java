package com.longhoo.net.headline.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.longhoo.net.MyApplication;
import com.longhoo.net.R;
import com.longhoo.net.headline.bean.NotificationDetailBean;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.utils.Utils;
import com.longhoo.net.utils.httprequest.HttpRequestPresenter;
import com.longhoo.net.utils.httprequest.HttpRequestView;
import com.longhoo.net.utils.httprequest.OkHttpCallback;
import com.longhoo.net.utils.httprequest.OkHttpUtil;
import com.longhoo.net.widget.base.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 新的通知详情，（1会议通知 2 活动通知） ，加请假或参与按钮
 */
public class NotificationDetailActivity extends BaseActivity implements HttpRequestView {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;
    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.info_container)
    ScrollView infoContainer;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.tv_reload)
    TextView tvReload;
    @BindView(R.id.ll_net_error_panel)
    LinearLayout llNetErrorPanel;
    @BindView(R.id.fl_play_video)
    FrameLayout flVideoPlay;
    @BindView(R.id.iv_play_video)
    ImageView ivVideoPlay;
    @BindView(R.id.tv_file)
    TextView tvFile;
    @BindView(R.id.tv_action_title)
    TextView tvActionTitle;
    @BindView(R.id.et_action_reson)
    EditText etActionReson;
    @BindView(R.id.tv_action_post)
    TextView tvActionPost;
    @BindView(R.id.ll_action_panel)
    LinearLayout llActionPanel;
    private HttpRequestPresenter requestPresenter;
    private String url = "";
    private String tid = "82";
    private String titleText = "";
    private String ttype = ""; //1 会议通知 2 活动通知

    @Override
    protected int getContentId() {
        return R.layout.activity_notification_detail;
    }

    @Override
    protected void initViews() {
        if (getIntent() != null) {
            tid = getIntent().getStringExtra("noti_news_tid");
        }
        if (TextUtils.isEmpty(url)) {
            url = Consts.BASE_URL + "/Organization/threemeetingDetails";
        }
        WebSettings webSettings = webView.getSettings();
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) { //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(url);
                return true;
            }
        });
        infoContainer.setVisibility(View.GONE);
        requestPresenter = new HttpRequestPresenter(this, this);
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("tid", tid+"");
        requestPresenter.doInitHttpData(url, Consts.REQUEST_METHOD_POST, Consts.REQUEST_REFRESH, params);
    }

    @Override
    protected void initToolbar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        tvTitle.setText("");
        toolbar.setNavigationIcon(R.drawable.left_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onNetworkError() {
        infoContainer.setVisibility(View.GONE);
        progressbar.setVisibility(View.GONE);
        llNetErrorPanel.setVisibility(View.VISIBLE);
        ToastUtils.getInstance().showToast(this, "网络异常~");
        tvReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressbar.setVisibility(View.VISIBLE);
                llNetErrorPanel.setVisibility(View.GONE);
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Map<String, String> params = new HashMap<>();
                        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
                        params.put("tid", tid+"");
                        requestPresenter.doInitHttpData(url, Consts.REQUEST_METHOD_POST, Consts.REQUEST_REFRESH, params);
                    }
                }, 200);
            }
        });
    }

    @Override
    public void onRefreshSuccess(String response) {
        ULog.e("ck", "noti_news_detail:" + response);
        Gson gson = new Gson();
        try {
            NotificationDetailBean detailsBean = gson.fromJson(response, NotificationDetailBean.class);
            if (!TextUtils.equals(detailsBean.getCode(), "0")) {
                ToastUtils.getInstance().showToast(this, "获取数据失败~");
                return;
            }
            //更新UI
            NotificationDetailBean.DataBean.DetailsBean msgBean = detailsBean.getData().getDetails();
            titleText = msgBean.getMeeting_title() + "";
            tvMainTitle.setText(titleText);
            tvTime.setText(Utils.getDataTime(msgBean.getTime()) + "");
            webView.loadData(msgBean.getMeeting_content(), "text/html; charset=UTF-8", null);
            ttype = msgBean.getTtype();
            //请假或是参与活动的填写
            if (TextUtils.equals("1", ttype)) {
                if (TextUtils.equals(msgBean.getLeavestatus(), "1")) {
                    etActionReson.setEnabled(false);
                    String leaveReason = msgBean.getLeavereason();
                    leaveReason = Utils.replaceTransference(leaveReason);
                    etActionReson.setText(leaveReason);
                    tvActionPost.setText("已请假");
                    tvActionPost.setEnabled(false);
                } else {
                    tvActionPost.setText("请假");
                }
            } else if (TextUtils.equals("2", ttype)) {
                tvActionTitle.setVisibility(View.GONE);
                etActionReson.setVisibility(View.GONE);
                if (TextUtils.equals(msgBean.getApplystatus(), "1")) {
                    tvActionPost.setEnabled(false);
                    tvActionPost.setText("已参与");
                } else {
                    tvActionPost.setText("参与");
                }
            } else {
                llActionPanel.setVisibility(View.GONE);
            }

        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            infoContainer.setVisibility(View.GONE);
            ToastUtils.getInstance().showToast(this, "服务器异常~~");
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        progressbar.setVisibility(View.GONE);
        llNetErrorPanel.setVisibility(View.GONE);
        infoContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRefreshError() {
        ToastUtils.getInstance().showToast(this, "详情获取失败~");
    }

    @Override
    public void onLoadMoreSuccess(String response) {

    }

    @Override
    public void onLoadMoreError() {

    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            ViewGroup parent = (ViewGroup) webView.getParent();
            if (parent != null) {
                parent.removeView(webView);
            }
            webView.removeAllViews();
            webView.destroy();
        }
        super.onDestroy();
    }

    @OnClick(R.id.tv_action_post)
    public void onClick(View view) {
        if (view.getId() == R.id.tv_action_post) {
            doAction();
        }
    }

    /**
     * 请假或参与活动
     */
    private void doAction() {
        String reason = etActionReson.getText().toString().trim();
//        if(reason.contains("\n")){
//            ULog.e("存在空格:"+reason);
//            return;
//        }
        Utils.showHideSoftInput(this, etActionReson, false);
        String url = "";
        if (TextUtils.equals("1", ttype)) {
            url = Consts.BASE_URL + "/Organization/meetingLeave";
            if (TextUtils.isEmpty(reason)) {
                ToastUtils.getInstance().showToast(this, "请填写请假理由");
                return;
            }
        } else {
            url = Consts.BASE_URL + "/Organization/activityApply";
        }
        tvActionPost.setText("正在提交中...");
        Map<String, String> params = new HashMap<>();
        params.put("tid", tid+"");
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        if (TextUtils.equals("1", ttype)) {
            params.put("type", "3");
            params.put("reason", reason);
        }
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                ULog.e("请假、参与返回：" + response);
                String code = "";
                String msg = "";
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    code = jsonObject.optString("code");
                    msg = jsonObject.optString("msg");
                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtils.getInstance().showToast(NotificationDetailActivity.this, "服务器异常~");
                    tvActionPost.setText(TextUtils.equals("1", ttype) ? "请假" : "参与");
                    etActionReson.setText("");
                    return;
                }
                //成功
                if (TextUtils.equals("0", code)) {
                    tvActionPost.setEnabled(false);
                    etActionReson.setEnabled(false);
                    tvActionPost.setText(TextUtils.equals("1", ttype) ? "已请假" : "已参与");
                } else {
                    ToastUtils.getInstance().showToast(NotificationDetailActivity.this, msg);
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(NotificationDetailActivity.this, "网络异常~");
            }
        });
    }
}
