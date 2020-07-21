package com.longhoo.net.partyaffairs.ui;

import android.support.v7.widget.Toolbar;
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

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.longhoo.net.MyApplication;
import com.longhoo.net.R;
import com.longhoo.net.partyaffairs.bean.LibraryDetailBean;
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

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class LibraryDetailActivity extends BaseActivity implements HttpRequestView {
    public static final String url = Consts.BASE_URL + "/Application/bookDetail";
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.line)
    View line;
    @BindView(R.id.iv_thumb)
    ImageView ivThumb;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.info_container)
    ScrollView infoContainer;
    @BindView(R.id.tv_borrow)
    TextView tvBorrow;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.tv_reload)
    TextView tvReload;
    @BindView(R.id.ll_net_error_panel)
    LinearLayout llNetErrorPanel;
    private int num;
    private HttpRequestPresenter requestPresenter;
    private String gid = "82";
    private int position;//在图书馆列表中所对应的位置

    @Override
    protected int getContentId() {
        return R.layout.activity_library_detail;
    }

    @Override
    protected void initViews() {
        if (getIntent() != null) {
            gid = getIntent().getStringExtra("library_gid");
            position = getIntent().getIntExtra("library_position",0);
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
        tvBorrow.setVisibility(View.GONE);
        requestPresenter = new HttpRequestPresenter(this, this);
        Map<String, String> params = new HashMap<>();
        params.put("uid", EnterCheckUtil.getInstance().getUid_Token()[0]);
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("gid", gid);
        requestPresenter.doInitHttpData(url, Consts.REQUEST_METHOD_POST, Consts.REQUEST_REFRESH, params);
    }

    @Override
    protected void initToolbar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        tvTitle.setText("图书详情");
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
        tvBorrow.setVisibility(View.GONE);
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
                        params.put("gid", gid);
                        requestPresenter.doInitHttpData(url, Consts.REQUEST_METHOD_POST, Consts.REQUEST_REFRESH, params);
                    }
                }, 200);
            }
        });
    }

    @Override
    public void onRefreshSuccess(String response) {
        ULog.e("ck", "图书详情:" + response);
        Gson gson = new Gson();
        try {
            LibraryDetailBean detailsBean = gson.fromJson(response, LibraryDetailBean.class);
            if (!TextUtils.equals(detailsBean.getCode(), "0")) {
                ToastUtils.getInstance().showToast(this, "获取数据失败~");
                return;
            }
            //更新UI
            LibraryDetailBean.DataBean newsBean = detailsBean.getData();
            tvName.setText(newsBean.getGname() + "");
            tvContent.setText("库存："+newsBean.getStore());
            changeBorrowState(newsBean.getType(),newsBean.getTitle()+"");
            if (!TextUtils.isEmpty(newsBean.getThumb())) {
                GlideManager.getInstance().load(this,newsBean.getThumb(),ivThumb);
            }
            //视频
            webView.loadData(newsBean.getContent(), "text/html; charset=UTF-8", null);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            infoContainer.setVisibility(View.GONE);
            tvBorrow.setVisibility(View.GONE);
            ToastUtils.getInstance().showToast(this, "服务器异常~~");
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        progressbar.setVisibility(View.GONE);
        llNetErrorPanel.setVisibility(View.GONE);
        infoContainer.setVisibility(View.VISIBLE);
        tvBorrow.setVisibility(View.VISIBLE);
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

    @OnClick({R.id.tv_borrow})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_borrow:
                doBorrowClick();
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

    private void changeBorrowState(String type,String title) {
        if (TextUtils.equals(type, "0")) {
            tvBorrow.setText(title);
            tvBorrow.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_red_solid_25));
            tvBorrow.setTextColor(getResources().getColor(R.color.white));
            tvBorrow.setEnabled(true);
            tvType.setText("可借");
            tvType.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_red_stroke_25));
            tvType.setTextColor(getResources().getColor(R.color.colorPrimary));
        } else if(TextUtils.equals(type,"1")){
            tvBorrow.setText(title);
            tvBorrow.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_disenabled_solid_25));
            tvBorrow.setTextColor(getResources().getColor(R.color.white));
            tvBorrow.setEnabled(false);
            tvType.setText("已借");
            tvType.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_red_solid_25));
            tvType.setTextColor(getResources().getColor(R.color.white));
        }else if(TextUtils.equals(type,"2")){
            tvBorrow.setText(title);
            tvBorrow.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_disenabled_solid_25));
            tvBorrow.setTextColor(getResources().getColor(R.color.white));
            tvBorrow.setEnabled(false);
            tvType.setText("借出");
            tvType.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_disenabled_solid_25));
            tvType.setTextColor(getResources().getColor(R.color.white));
        }else if(TextUtils.equals(type,"3")){
            tvBorrow.setText(title);
            tvBorrow.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_disenabled_solid_25));
            tvBorrow.setTextColor(getResources().getColor(R.color.white));
            tvBorrow.setEnabled(false);
            tvType.setText("审核中");
            tvType.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_disenabled_solid_25));
            tvType.setTextColor(getResources().getColor(R.color.white));
        }
    }

    private void doBorrowClick() {
        String mSupportUrl = Consts.BASE_URL + "/Application/libaryBorrow";
        Map<String, String> params = new HashMap<>();
        params.put("uid", EnterCheckUtil.getInstance().getUid_Token()[0]);
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("gid", gid);
        OkHttpUtil.getInstance().doAsyncPost(mSupportUrl, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                if(isFinishing()){
                    return;
                }
                try {
                    ULog.e("ck", "图书借阅点击：" + response);
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.optString("code");
                    String msg = jsonObject.optString("msg");
                    JSONObject data = jsonObject.optJSONObject("data");
                    if (TextUtils.equals(code, "0")) {
                        tvBorrow.setText("正在审核中...");
                        tvBorrow.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_disenabled_solid_25));
                        tvBorrow.setTextColor(getResources().getColor(R.color.white));
                        tvBorrow.setEnabled(false);
                        tvType.setText("审核中");
                        tvType.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_disenabled_solid_25));
                        tvType.setTextColor(getResources().getColor(R.color.white));
                        //列表页数据改变
                        MessageEvent event = new MessageEvent();
                        event.position = position;
                        event.message = "3";  //正在审核中
                        EventBus.getDefault().post(event);
                    }else if (TextUtils.equals(code, "10")) {
                        tvBorrow.setText("已在审核中...");
                        tvBorrow.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_disenabled_solid_25));
                        tvBorrow.setTextColor(getResources().getColor(R.color.white));
                        tvBorrow.setEnabled(false);
                        tvType.setText("审核中");
                        tvType.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_disenabled_solid_25));
                        tvType.setTextColor(getResources().getColor(R.color.white));
                    }
                    ToastUtils.getInstance().showToast(LibraryDetailActivity.this, msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtils.getInstance().showToast(LibraryDetailActivity.this, "数据异常~");
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(LibraryDetailActivity.this, "网络异常~");
            }
        });
    }

}
