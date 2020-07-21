package com.longhoo.net.partyaffairs.ui;

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
import com.longhoo.net.manageservice.ui.OpenFileActivity;
import com.longhoo.net.partyaffairs.bean.OpenPartyContentNode;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.FileUtils;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.utils.WriteReadListUtil;
import com.longhoo.net.utils.httprequest.HttpRequestPresenter;
import com.longhoo.net.utils.httprequest.HttpRequestView;
import com.longhoo.net.widget.base.BaseActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class OpenPartyContentActivity extends BaseActivity implements HttpRequestView {


    @Override
    protected int getContentId() {
        return R.layout.activity_open_party_content;
    }

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

    @BindView(R.id.file_panel)
    LinearLayout filePanel;
    private int num;
    private HttpRequestPresenter requestPresenter;
    private String url = Consts.BASE_URL + "/Partywork/public_partywork_lists";
    private String nid = "";
    private String thumbUrl = "";
    private String titleText = "";


    public static void goTo(Context context, String nid) {
        Intent intent = new Intent(context, OpenPartyContentActivity.class);
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
        params.put("id", nid + "");
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
                        params.put("id", nid);
                        requestPresenter.doInitHttpData(url, Consts.REQUEST_METHOD_POST, Consts.REQUEST_REFRESH, params);
                    }
                }, 200);
            }
        });
    }

    @Override
    public void onRefreshSuccess(String response) {
        ULog.e("cc", "news_detail:" + response);
        Gson gson = new Gson();
        try {
            OpenPartyContentNode detailsBean = gson.fromJson(response, OpenPartyContentNode.class);
            if (!TextUtils.equals(detailsBean.getCode(), "0")) {
                ToastUtils.getInstance().showToast(this, "获取数据失败~");
                return;
            }
            //更新UI
            final OpenPartyContentNode.DataBean.ListBean newsBean = detailsBean.getData().getList().get(0);
            titleText = newsBean.getTitle() + "";
            tvMainTitle.setText(titleText);
            tvTime.setText(newsBean.getAddtime() + "");
            webView.loadData(newsBean.getContent(), "text/html; charset=UTF-8", null);

            if (!TextUtils.isEmpty(newsBean.getFiled())) {
                View view = LayoutInflater.from(this).inflate(R.layout.layout_item_file, null);
                LinearLayout llFile = view.findViewById(R.id.ll_file);
                ImageView ivFile = view.findViewById(R.id.iv_file);
                final TextView tvFile = view.findViewById(R.id.tv_file);
                tvFile.setText(newsBean.getFiledname());
                setFileImage(ivFile, newsBean.getFiled());

                llFile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(OpenPartyContentActivity.this, OpenFileActivity.class);
                        intent.putExtra("file_path", newsBean.getFiled());
                        intent.putExtra("file_name", newsBean.getFiledname());
                        intent.putExtra("file_size", "");
                        startActivity(intent);
                    }
                });

                filePanel.addView(view);
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

}
