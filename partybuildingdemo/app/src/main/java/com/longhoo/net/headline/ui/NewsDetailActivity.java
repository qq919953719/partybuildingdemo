package com.longhoo.net.headline.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.longhoo.net.MyApplication;
import com.longhoo.net.R;
import com.longhoo.net.headline.bean.NewsDetailsBean;
import com.longhoo.net.manageservice.ui.OpenFileActivity;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.FileUtils;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.utils.Utils;
import com.longhoo.net.utils.WriteReadListUtil;
import com.longhoo.net.utils.httprequest.HttpRequestPresenter;
import com.longhoo.net.utils.httprequest.HttpRequestView;
import com.longhoo.net.widget.base.BaseActivity;
import com.longhoo.net.widget.base.VideoPlayActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class NewsDetailActivity extends BaseActivity implements HttpRequestView {
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
    @BindView(R.id.tv_view_num)
    TextView tvViewNum;
    @BindView(R.id.file_panel)
    LinearLayout filePanel;
    private int num;
    private HttpRequestPresenter requestPresenter;
    private String url = Consts.BASE_URL + "/Index/public_newsinfo";
    private String nid = "";
    private String videoUrl;
    private String thumbUrl = "";
    private String titleText = "";
    private List<String> filePathList = new ArrayList<>();
    private List<String> fileNameList = new ArrayList<>();

    @Override
    protected int getContentId() {
        return R.layout.activity_news_detail;
    }

    public static void goTo(Context context, String nid) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra("news_nid", nid);
        context.startActivity(intent);
    }

    @Override
    protected void initViews() {
        if (getIntent() != null) {
            nid = getIntent().getStringExtra("news_nid");
        }
        MyApplication app = (MyApplication) getApplicationContext();
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
        params.put("uid", EnterCheckUtil.getInstance().getUid_Token()[0]);
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("nid", nid + "");
        requestPresenter.doInitHttpData(url, Consts.REQUEST_METHOD_POST, Consts.REQUEST_REFRESH, params);
    }

    @Override
    protected void initToolbar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        tvTitle.setText("详情");
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
                        params.put("uid", EnterCheckUtil.getInstance().getUid_Token()[0]);
                        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
                        params.put("nid", nid);
                        requestPresenter.doInitHttpData(url, Consts.REQUEST_METHOD_POST, Consts.REQUEST_REFRESH, params);
                    }
                }, 200);
            }
        });
    }

    @Override
    public void onRefreshSuccess(String response) {
        ULog.e("ck", "news_detail:" + response);
        Gson gson = new Gson();
        try {
            NewsDetailsBean detailsBean = gson.fromJson(response, NewsDetailsBean.class);
            if (!TextUtils.equals(detailsBean.getCode(), "0")) {
                ToastUtils.getInstance().showToast(this, "获取数据失败~");
                return;
            }
            //更新UI
            NewsDetailsBean.DataBean newsBean = detailsBean.getData();
            titleText = newsBean.getTitle() + "";
            tvMainTitle.setText(titleText);
            tvTime.setText(Utils.getDataTime(newsBean.getTime() + ""));
            tvViewNum.setText(newsBean.getBrowse() + "");
//            videoUrl = newsBean.getVideo();
//            thumbUrl = newsBean.getPic();
//            if (!TextUtils.isEmpty(videoUrl)) {
//                flVideoPlay.setVisibility(View.VISIBLE);
//                if (!TextUtils.isEmpty(thumbUrl)) {
//                    GlideManager.getInstance().load(this,thumbUrl,ivVideoPlay);
//                }
//            }
            webView.loadData(newsBean.getInfo(), "text/html; charset=UTF-8", null);
            //num = Integer.parseInt(newsBean.getCom());
            //附件点击查看
            fileNameList = newsBean.getFilename();
            filePathList = newsBean.getFiles();
            if (filePathList != null && fileNameList != null) {
                for (int i = 0; i < filePathList.size(); i++) {
                    View view = LayoutInflater.from(this).inflate(R.layout.layout_item_file, null);
                    LinearLayout llFile = view.findViewById(R.id.ll_file);
                    ImageView ivFile = view.findViewById(R.id.iv_file);
                    TextView tvFile = view.findViewById(R.id.tv_file);
                    tvFile.setText(fileNameList.get(i));
                    setFileImage(ivFile, filePathList.get(i));
                    final int finalI = i;
                    llFile.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(NewsDetailActivity.this, OpenFileActivity.class);
                            intent.putExtra("file_path", filePathList.get(finalI));
                            intent.putExtra("file_name", fileNameList.get(finalI));
                            intent.putExtra("file_size", "");
                            startActivity(intent);
                        }
                    });
                    filePanel.addView(view);
                }
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            infoContainer.setVisibility(View.GONE);
            // ToastUtils.getInstance().showToast(this, "服务器异常~~");
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

    @OnClick({R.id.fl_play_video})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fl_play_video:
                if (!TextUtils.isEmpty(videoUrl)) {
                    VideoPlayActivity.startVideoPlay(this, videoUrl, thumbUrl, "", titleText, VideoPlayActivity.LIVE_TYPE_VIDEO, VideoPlayActivity.STYLE_REVIEW, titleText);
                }
                break;
        }
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
        WriteReadListUtil mWriteReadListUtil = new WriteReadListUtil();
        mWriteReadListUtil.UpLoadWriteInfo(this, "1", nid);
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

    private void setFileImage(ImageView imageView, String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return;
        }
        String fileType = FileUtils.getFileType(filePath);
        if (TextUtils.equals(fileType, ".doc") || TextUtils.equals(fileType, ".docx")) {
            imageView.setImageResource(R.drawable.file_word);
        } else if (TextUtils.equals(fileType, ".xlsx") || TextUtils.equals(fileType, ".xls")) {
            imageView.setImageResource(R.drawable.file_excel);
        } else if (TextUtils.equals(fileType, ".ppt") || TextUtils.equals(fileType, ".pptx")) {
            imageView.setImageResource(R.drawable.file_ppt);
        } else if (TextUtils.equals(fileType, ".pdf")) {
            imageView.setImageResource(R.drawable.file_pdf);
        } else {
            imageView.setImageResource(R.drawable.file_normal);
        }
    }
}
