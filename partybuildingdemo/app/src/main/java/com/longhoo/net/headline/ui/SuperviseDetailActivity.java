package com.longhoo.net.headline.ui;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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
import com.longhoo.net.R;
import com.longhoo.net.headline.bean.SuperviseBean;
import com.longhoo.net.manageservice.ui.OpenFileActivity;
import com.longhoo.net.study.ui.PartyVideoPlayActivity;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.FileUtils;
import com.longhoo.net.utils.GlideManager;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.utils.Utils;
import com.longhoo.net.utils.httprequest.HttpRequestPresenter;
import com.longhoo.net.utils.httprequest.HttpRequestView;
import com.longhoo.net.widget.base.BaseActivity;
import com.longhoo.net.widget.base.VideoPlayActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 党纪监督详情
 */
public class SuperviseDetailActivity extends BaseActivity implements HttpRequestView {
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
    @BindView(R.id.ll_file)
    LinearLayout llFile;
    @BindView(R.id.iv_file)
    ImageView ivFile;
    @BindView(R.id.tv_file)
    TextView tvFile;
    private HttpRequestPresenter requestPresenter;
    private String url = "";
    private String nid = "82";
    private String videoUrl;
    private String thumbUrl = "";
    private String titleText="";
    private String filePath="";
    private String fileName="";
    private String fileSize="";

    @Override
    protected int getContentId() {
        return R.layout.activity_supervise_detail;
    }

    @Override
    protected void initViews() {
        if (getIntent() != null) {
            nid = getIntent().getStringExtra("noti_news_tid");
            url = getIntent().getStringExtra("noti_news_url");
        }
        if(TextUtils.isEmpty(url)){
            url = Consts.BASE_URL + "/Application/disciplineInfo";
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
        params.put("uid", EnterCheckUtil.getInstance().getUid_Token()[0]);
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("tid", nid);
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
        ULog.e("ck", "supervise_news_detail:" + response);
        Gson gson = new Gson();
        try {
            SuperviseBean detailsBean = gson.fromJson(response, SuperviseBean.class);
            if (!TextUtils.equals(detailsBean.getCode(), "0")) {
                ToastUtils.getInstance().showToast(this, "获取数据失败~");
                return;
            }
            //更新UI
            SuperviseBean.DataBean newsBean = detailsBean.getData();
            tvTime.setText(Utils.getDataTime(newsBean.getTime()) + "");
//            videoUrl = newsBean.getVideo();
//            thumbUrl = newsBean.getPic();
            if (!TextUtils.isEmpty(videoUrl)) {
                flVideoPlay.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(thumbUrl)) {
                    GlideManager.getInstance().load(this,thumbUrl,ivVideoPlay);
                }
            }
            webView.loadData(newsBean.getInfo(), "text/html; charset=UTF-8", null);
            //附件点击查看
//            fileName =newsBean.getFilename();
//            filePath = newsBean.getFiles();
//            fileSize = newsBean.getFilesize();
            if(!TextUtils.isEmpty(filePath)){
                llFile.setVisibility(View.VISIBLE);
                tvFile.setText(fileName+"");
                setFileImage(filePath);
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
        ToastUtils.getInstance().showToast(this,"详情获取失败~");
    }

    @Override
    public void onLoadMoreSuccess(String response) {

    }

    @Override
    public void onLoadMoreError() {

    }

    @OnClick({R.id.fl_play_video,R.id.ll_file})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fl_play_video:
                if (!TextUtils.isEmpty(videoUrl)) {
                    VideoPlayActivity.startVideoPlay(this,videoUrl,thumbUrl,"",titleText, VideoPlayActivity.LIVE_TYPE_VIDEO, VideoPlayActivity.STYLE_REVIEW,titleText);
                }
                break;
            case R.id.ll_file:
                //WebViewActivity.goToWebView(this,"https://view.officeapps.live.com/op/view.aspx?src="+filePath,fileName,false);
                Intent intent = new Intent(this, OpenFileActivity.class);
                intent.putExtra("file_path",filePath);
                intent.putExtra("file_name",fileName);
                intent.putExtra("file_size",fileSize);
                startActivity(intent);
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

    private void setFileImage(String filePath){
        if(TextUtils.isEmpty(filePath)){
            return;
        }
        String fileType = FileUtils.getFileType(filePath);
        if(TextUtils.equals(fileType,".doc")||TextUtils.equals(fileType,".docx")){
            ivFile.setImageResource(R.drawable.file_word);
        }else if(TextUtils.equals(fileType,".xlsx")||TextUtils.equals(fileType,".xls")){
            ivFile.setImageResource(R.drawable.file_excel);
        }else if(TextUtils.equals(fileType,".ppt")||TextUtils.equals(fileType,".pptx")){
            ivFile.setImageResource(R.drawable.file_ppt);
        }else if(TextUtils.equals(fileType,".pdf")){
            ivFile.setImageResource(R.drawable.file_pdf);
        }else{
            ivFile.setImageResource(R.drawable.file_normal);
        }
    }
}
