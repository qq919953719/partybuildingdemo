package com.longhoo.net.manageservice.ui;

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

import com.longhoo.net.R;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.utils.httprequest.HttpRequestPresenter;
import com.longhoo.net.utils.httprequest.HttpRequestView;
import com.longhoo.net.widget.base.BaseFragment;
import com.longhoo.net.widget.base.VideoPlayActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class OrganizationSurveyFragment extends BaseFragment implements HttpRequestView {
    public static final String url = Consts.BASE_URL + "/index/public_msg";
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
    private HttpRequestPresenter requestPresenter;
    private String videoUrl;
    private String thumbUrl = "";

    @Override
    protected int getContentId() {
        return R.layout.fragment_organization_survey;
    }

    @Override
    protected void initView(View view) {
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
        requestPresenter = new HttpRequestPresenter(getActivity(), this);
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("type","4");
        requestPresenter.doInitHttpData(url, Consts.REQUEST_METHOD_POST, Consts.REQUEST_REFRESH, params);
    }

    @Override
    protected void initToolbar() {
    }

    @Override
    public void onNetworkError() {
        infoContainer.setVisibility(View.GONE);
        progressbar.setVisibility(View.GONE);
        llNetErrorPanel.setVisibility(View.VISIBLE);
        ToastUtils.getInstance().showToast(getActivity(), "网络异常~");
        tvReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressbar.setVisibility(View.VISIBLE);
                llNetErrorPanel.setVisibility(View.GONE);
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Map<String, String> params = new HashMap<>();
                        requestPresenter.doInitHttpData(url, Consts.REQUEST_METHOD_POST, Consts.REQUEST_REFRESH, params);
                    }
                }, 200);
            }
        });
    }

    @Override
    public void onRefreshSuccess(String response) {
        ULog.e("cc", "news_detail:" + response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            String code = jsonObject.optString("code");
            String msg = jsonObject.optString("msg");
            JSONObject data = jsonObject.optJSONObject("data");
            String info = data.optString("info");
            if (!TextUtils.equals(code, "0")) {
                ToastUtils.getInstance().showToast(getActivity(), "获取数据失败~");
            }
            if (!TextUtils.isEmpty(info)&&!TextUtils.equals(info,"null")) {
                webView.loadData(info, "text/html; charset=UTF-8", null);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        progressbar.setVisibility(View.GONE);
        llNetErrorPanel.setVisibility(View.GONE);
        infoContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRefreshError() {
        ToastUtils.getInstance().showToast(getActivity(), "详情获取失败~");
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
                    VideoPlayActivity.startVideoPlay(getActivity(), videoUrl, thumbUrl, "", "党建概况", VideoPlayActivity.LIVE_TYPE_VIDEO, VideoPlayActivity.STYLE_REVIEW, "党建概况");
                }
                break;
        }
    }


    @Override
    public void onDestroy() {
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
