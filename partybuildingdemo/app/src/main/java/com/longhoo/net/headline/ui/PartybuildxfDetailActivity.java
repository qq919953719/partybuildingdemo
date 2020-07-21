package com.longhoo.net.headline.ui;

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
import com.longhoo.net.headline.bean.PartybuildxfDetailBean;
import com.longhoo.net.mine.ui.LoginActivity;
import com.longhoo.net.study.ui.PartyVideoPlayActivity;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.GlideManager;
import com.longhoo.net.utils.MessageEvent;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.utils.httprequest.HttpRequestPresenter;
import com.longhoo.net.utils.httprequest.HttpRequestView;
import com.longhoo.net.utils.httprequest.OkHttpCallback;
import com.longhoo.net.utils.httprequest.OkHttpUtil;
import com.longhoo.net.widget.base.BaseActivity;
import com.longhoo.net.widget.base.VideoPlayActivity;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

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
import butterknife.OnClick;

public class PartybuildxfDetailActivity extends BaseActivity implements HttpRequestView {
    public static final String url = Consts.BASE_URL + "/News/public_pioneerinfo";
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.line)
    View line;
    @BindView(R.id.iv_thumb)
    ImageView ivThumb;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.iv_zan)
    ImageView ivZan;
    @BindView(R.id.iv_play_video)
    ImageView ivPlayVideo;
    @BindView(R.id.fl_play_video)
    FrameLayout flPlayVideo;
    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.info_container)
    ScrollView infoContainer;
    @BindView(R.id.tv_support)
    TextView tvSupport;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.tv_reload)
    TextView tvReload;
    @BindView(R.id.ll_net_error_panel)
    LinearLayout llNetErrorPanel;
    @BindView(R.id.tv_name)
    TextView tvName;
    private int num;
    private HttpRequestPresenter requestPresenter;
    private int position = 0;
    private String nid = "82";
    private String isZan;
    private String title;
    private String videoUrl;
    private String thumbUrl;
    private int zanCount = 0;
    private List<LocalMedia> picList = new ArrayList<>();

    @Override
    protected int getContentId() {
        return R.layout.activity_partybuildxf_detail;
    }

    @Override
    protected void initViews() {
        EventBus.getDefault().register(this);
        if (getIntent() != null) {
            nid = getIntent().getStringExtra("news_nid");
            position = getIntent().getIntExtra("news_position",0);
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
        requestPresenter = new HttpRequestPresenter(this,this);
        Map<String, String> params = new HashMap<>();
        params.put("uid", EnterCheckUtil.getInstance().getUid_Token()[0]);
        params.put("nid", nid);
        requestPresenter.doInitHttpData(url, Consts.REQUEST_METHOD_POST, Consts.REQUEST_REFRESH, params);
        infoContainer.setVisibility(View.GONE);
        tvSupport.setVisibility(View.GONE);
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
        tvSupport.setVisibility(View.GONE);
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
                        params.put("nid", nid);
                        requestPresenter.doInitHttpData(url, Consts.REQUEST_METHOD_POST, Consts.REQUEST_REFRESH, params);
                    }
                }, 200);
            }
        });
    }

    @Override
    public void onRefreshSuccess(String response) {
        ULog.e("ck", "partyjxf_detail:" + response);
        Gson gson = new Gson();
        try {
            PartybuildxfDetailBean detailsBean = gson.fromJson(response, PartybuildxfDetailBean.class);
            if (!TextUtils.equals(detailsBean.getCode(), "0")) {
                ToastUtils.getInstance().showToast(this, "获取数据失败~");
                return;
            }
            //更新UI
            PartybuildxfDetailBean.DataBean.NewsBean newsBean = detailsBean.getData().getNews();
            title = "身边榜样—" + newsBean.getTitle();
            tvTitle.setText(title);
            tvName.setText(newsBean.getTitle()+"");
            tvContent.setText(newsBean.getDigest() + "");
            tvCount.setText(newsBean.getZan() + "人赞");
            try{
                zanCount = Integer.parseInt(newsBean.getZan());
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
            ivZan.setVisibility(View.GONE);
            isZan = newsBean.getIszan();
            changeSupportState(false);
            if (!TextUtils.isEmpty(newsBean.getPic())) {
                GlideManager.getInstance().load(this,newsBean.getPic(),ivThumb);
                picList.add(new LocalMedia(newsBean.getPic(),0, PictureMimeType.ofImage(),null));
            }
            //视频
            videoUrl = newsBean.getVideo();
            thumbUrl = newsBean.getPic();
            if (!TextUtils.isEmpty(videoUrl)) {
                flPlayVideo.setVisibility(View.VISIBLE);
                //ivVideoPlay.setImageBitmap(ThumbnailUtils.createVideoThumbnail(videoUrl, MediaStore.Video.Thumbnails.MINI_KIND));
                if (!TextUtils.isEmpty(thumbUrl)) {
                    GlideManager.getInstance().load(this,thumbUrl,ivPlayVideo);
                }
            }
            webView.loadData(newsBean.getInfo(), "text/html; charset=UTF-8", null);
            num = Integer.parseInt(newsBean.getCom());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            infoContainer.setVisibility(View.GONE);
            tvSupport.setVisibility(View.GONE);
            ToastUtils.getInstance().showToast(this, "服务器异常~~");
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        progressbar.setVisibility(View.GONE);
        llNetErrorPanel.setVisibility(View.GONE);
        infoContainer.setVisibility(View.VISIBLE);
        tvSupport.setVisibility(View.VISIBLE);
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

    @OnClick({R.id.fl_play_video,R.id.tv_support,R.id.iv_thumb})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fl_play_video:
                if (!TextUtils.isEmpty(videoUrl)) {
                    VideoPlayActivity.startVideoPlay(this,videoUrl,thumbUrl,"",title, VideoPlayActivity.LIVE_TYPE_VIDEO, VideoPlayActivity.STYLE_REVIEW,title);
                }
                break;
            case R.id.tv_support:
                doSupportClick();
                break;
            case R.id.iv_thumb:
                PictureSelector.create(this).externalPicturePreview(0,picList);
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
        EventBus.getDefault().unregister(this);
    }

    private void changeSupportState(boolean reverse){
        if (TextUtils.equals(isZan, "0")) {
            tvSupport.setText(reverse?"已点赞":"为TA点赞");
            tvSupport.setTextColor(getResources().getColor(reverse?R.color.white:R.color.colorPrimary));
            tvSupport.setBackgroundDrawable(getResources().getDrawable(reverse?R.drawable.bg_red_solid_25:R.drawable.bg_red_stroke_25));
        } else {
            tvSupport.setText(reverse?"为TA点赞":"已点赞");
            tvSupport.setTextColor(getResources().getColor(reverse?R.color.colorPrimary:R.color.white));
            tvSupport.setBackgroundDrawable(getResources().getDrawable(reverse?R.drawable.bg_red_stroke_25:R.drawable.bg_red_solid_25));
        }
    }

    private void doSupportClick(){
        boolean isLogin = EnterCheckUtil.getInstance().isLogin(true);
        if(!isLogin){
            return;
        }
        changeSupportState(true);
        String mSupportUrl = Consts.BASE_URL+"/News/log_pioneerzan";
        Map<String,String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("nid",nid);
        OkHttpUtil.getInstance().doAsyncPost(mSupportUrl, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    ULog.e("ck","详情点赞："+response);
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.optString("code");
                    String msg = jsonObject.optString("msg");
                    JSONObject data = jsonObject.optJSONObject("data");
                    if(TextUtils.equals(code,"0")){
                        if (data != null) {
                             isZan = data.optString("iszan");
                             if(TextUtils.equals(isZan,"0")){
                                 tvCount.setText(String.valueOf(--zanCount)+"人赞");
                             }else{
                                 tvCount.setText(String.valueOf(++zanCount)+"人赞");
                             }
                             MessageEvent msgEvent = new MessageEvent();
                             msgEvent.position = position;
                             msgEvent.message = isZan;
                             EventBus.getDefault().post(msgEvent);
                        }
                    }
                    ToastUtils.getInstance().showToast(PartybuildxfDetailActivity.this,msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtils.getInstance().showToast(PartybuildxfDetailActivity.this,"数据异常~");
                }
                changeSupportState(false);
            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(PartybuildxfDetailActivity.this,"网络异常~");
                changeSupportState(false);
            }
        });
    }

    /**
     * 刷新UI
     */
    private void refreshUI(){
        requestPresenter = new HttpRequestPresenter(this, this);
        Map<String, String> params = new HashMap<>();
        params.put("uid", EnterCheckUtil.getInstance().getUid_Token()[0]);
        params.put("nid", nid);
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                Gson gson = new Gson();
                try {
                    PartybuildxfDetailBean detailsBean = gson.fromJson(response, PartybuildxfDetailBean.class);
                    if (!TextUtils.equals(detailsBean.getCode(), "0")) {
                        return;
                    }
                    //更新UI
                    PartybuildxfDetailBean.DataBean.NewsBean newsBean = detailsBean.getData().getNews();
                    tvCount.setText(newsBean.getZan() + "人赞");
                    try{
                        zanCount = Integer.parseInt(newsBean.getZan());
                    }catch (NumberFormatException e){
                        e.printStackTrace();
                    }
                    ivZan.setVisibility(View.GONE);
                    isZan = newsBean.getIszan();
                    changeSupportState(false);
                } catch (JsonSyntaxException e) {

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        String message = event.message;
        if(TextUtils.equals(message,LoginActivity.REFRESH_TAG)){
            refreshUI();
        }
    }
}
