package com.longhoo.net.partyaffairs.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.utils.httprequest.OkHttpCallback;
import com.longhoo.net.utils.httprequest.OkHttpUtil;
import com.longhoo.net.widget.base.BaseActivity;
import com.longhoo.net.widget.qqx5webview.X5WebView;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 民主评议、支部评议
 */
public class PartyReviewActivity extends BaseActivity implements DownloadListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.web_view)
    X5WebView mWebView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private WebSettings mWebSettings;
    private String mstrUrl = "";//访问地址
    ValueCallback<Uri> mUploadMessage;
    String WXcallbackURL = "";
    private Map<String,String> resultMap = new HashMap<>();
    private String type = ""; //1.民主评议 2.支部评议

    @Override
    protected int getContentId() {
        return R.layout.activity_party_review;
    }

    @Override
    protected void initViews() {
        if (getIntent() != null) {
            type = getIntent().getStringExtra("type");
        }
        getUrl();
    }

    private void getUrl(){
        progressBar.setIndeterminate(true);
        String url = Consts.BASE_URL+"/Application/pingyi";
        Map<String,String> params = new HashMap<>();
        params.put("uid", EnterCheckUtil.getInstance().getUid_Token()[0]);
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                ULog.e("民主评议、支部评议url:"+response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.optString("code");
                    String msg = jsonObject.optString("msg");
                    JSONObject data = jsonObject.optJSONObject("data");
                    JSONArray info = data.optJSONArray("info");
                    int size = info.length();
                    for(int i=0;i<size;i++){
                        JSONObject jo = (JSONObject) info.opt(i);
                        String url = jo.optString("address");
                        String type = jo.optString("type");
                        resultMap.put(type,url);
                    }
                    //成功
                    if(TextUtils.equals(code,"0")){
                        for(Map.Entry<String,String> entry:resultMap.entrySet()){
                            if(TextUtils.equals(entry.getKey(),type)){
                                //打开网页
                                progressBar.setIndeterminate(false);
                                mstrUrl = entry.getValue()+"?uid="+EnterCheckUtil.getInstance().getUid_Token()[0]+"&token="+EnterCheckUtil.getInstance().getUid_Token()[1];
                                ULog.e(mstrUrl);
                                InitController();
                                InitX5WebView();
                            }
                        }
                    }else{
                        ToastUtils.getInstance().showToast(PartyReviewActivity.this,msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtils.getInstance().showToast(PartyReviewActivity.this,"解析异常~");
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(PartyReviewActivity.this,"网络异常~");
            }
        });
    }

    @Override
    protected void initToolbar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvTitle.setText(TextUtils.equals("1",type)?"民主评议":"支部评议");
        toolbar.setNavigationIcon(R.drawable.left_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void InitX5WebView() {
        mWebView.isImgLook = false;
        mWebView.setBackgroundColor(Color.parseColor("#ebebeb"));
        mWebSettings = mWebView.getSettings();
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setBuiltInZoomControls(true);
        mWebSettings.setDisplayZoomControls(false);
        mWebSettings.setSupportZoom(true);
        mWebSettings.setDomStorageEnabled(true);
        mWebSettings.setDatabaseEnabled(true);
        // 设置字符集编码
        mWebSettings.setDefaultTextEncodingName("UTF-8");
        // 开启JavaScript支持
        mWebSettings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url == null || url.length() == 0) {
                    return false;
                }
                ULog.e("ck", "处理前：" + url);
                //防止被360wifi劫持
                if (url.contains("wifi") && url.contains("url=")) {
                    url = mstrUrl;
                    view.loadUrl(url);
                    ULog.e("ck", "处理后：" + url);
                } else {
                    view.loadUrl(url);
                }
                return true;
            }
        });
        mWebView.loadUrl(mstrUrl);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        mWebView.getView().setOverScrollMode(View.OVER_SCROLL_ALWAYS);
        mWebView.setDownloadListener(this);
        mWebView.setWebChromeClient(mWebChormeClient);
    }

    public WebChromeClient mWebChormeClient = new WebChromeClient() {
        // The undocumented magic method override
        // Eclipse will swear at you if you try to put @Override here
        // For Android 3.0+
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            mUploadMessage = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");
            PartyReviewActivity.this.startActivityForResult(
                    Intent.createChooser(i, "File Chooser"),
                    X5WebView.FILE_CHOOSER);
        }

        // For Android 3.0+
        public void openFileChooser(ValueCallback<Uri> uploadMsg,
                                    String acceptType) {
            mUploadMessage = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("*/*");
            PartyReviewActivity.this.startActivityForResult(
                    Intent.createChooser(i, "File Browser"),
                    X5WebView.FILE_CHOOSER);
        }

        // For Android 4.1
        public void openFileChooser(ValueCallback<Uri> uploadMsg,
                                    String acceptType, String capture) {
            mUploadMessage = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");
            PartyReviewActivity.this.startActivityForResult(
                    Intent.createChooser(i, "File Chooser"),
                    X5WebView.FILE_CHOOSER);

        }

        //   显示网页名字
        public void onReceivedTitle(WebView webview, String title) {
            if (title != null) {
                if (title.length() >= 12) {
                    title = title.substring(0, 12) + "...";
                }
            }
            tvTitle.setText(title);
        }

        @Override
        public void onProgressChanged(WebView webView, int newProgress) {
            super.onProgressChanged(webView, newProgress);
            progressBar.setProgress(newProgress);
            if (newProgress >= 100) {
                webView.setVisibility(View.VISIBLE);
                hideProgressBar();
            }
        }
    };

    private void hideProgressBar() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0);
        alphaAnimation.setDuration(500);
        alphaAnimation.setFillAfter(true);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                progressBar.setVisibility(View.GONE);
            }
        });
        progressBar.startAnimation(alphaAnimation);
    }

    /**
     * 下载
     *
     * @param url
     * @param userAgent
     * @param contentDisposition
     * @param mimetype
     * @param contentLength
     */
    @Override
    public void onDownloadStart(String url, String userAgent,
                                String contentDisposition, String mimetype, long contentLength) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private void InitController() {

    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            ViewGroup parent = (ViewGroup) mWebView.getParent();
            if (parent != null) {
                parent.removeView(mWebView);
            }
            mWebView.removeAllViews();
            mWebView.destroy();
        }
        super.onDestroy();
    }
}
