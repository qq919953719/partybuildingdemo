package com.longhoo.net.widget.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.widget.qqx5webview.X5WebView;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.BindView;


/**
 * Created by Administrator on 2017/2/16.
 */
public class WebViewActivity extends BaseActivity implements DownloadListener {
    /**
     * isSHARE false  分享  true  不分享
     */
    public String isSHARE;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.web_view)
    X5WebView mWebView;
    @BindView(R.id.top_image)
    ImageView topImage;
    @BindView(R.id.top_text)
    TextView topText;
    private WebSettings mWebSettings;
    private String mstrUrl = "";//访问地址
    private String mstrTitle = "";//标题
    ValueCallback<Uri> mUploadMessage;
    String WXcallbackURL = "";
    boolean isShare = false;
    //App MyApp;

    public static void goToWebView(Context context, String url, String title, boolean isShare) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        intent.putExtra("share", isShare);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            mstrUrl = intent.getStringExtra("url");
            mstrTitle = intent.getStringExtra("title");
            isShare = intent.getBooleanExtra("share", false);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void initViews() {
        InitController();
        InitX5WebView();
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
        if (mstrTitle != null) {
            if(mstrTitle.length()>=12){
                mstrTitle = mstrTitle.substring(0,12)+"...";
            }
        }
        tvTitle.setText(mstrTitle);
        toolbar.setNavigationIcon(R.drawable.left_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
//        if (isShare) {
//            topImage.setVisibility(View.VISIBLE);
//            topImage.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });
//
//        }else{
//            topImage.setVisibility(View.GONE);
//        }
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
            WebViewActivity.this.startActivityForResult(
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
            WebViewActivity.this.startActivityForResult(
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
            WebViewActivity.this.startActivityForResult(
                    Intent.createChooser(i, "File Chooser"),
                    X5WebView.FILE_CHOOSER);

        }

        //   显示网页名字
        public void onReceivedTitle(WebView webview, String title) {
            if (mstrTitle == null) {
                tvTitle.setText(title);
            }
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
