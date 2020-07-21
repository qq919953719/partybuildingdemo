package com.longhoo.net.supervision.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.longhoo.net.MyApplication;
import com.longhoo.net.R;
import com.longhoo.net.supervision.bean.SecretReportDetailBean;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.utils.httprequest.OkHttpCallback;
import com.longhoo.net.utils.httprequest.OkHttpUtil;
import com.longhoo.net.widget.WaterMarkBg;
import com.longhoo.net.widget.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 书记述职个人详情
 */
public class SecretReportDetailActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;
    @BindView(R.id.tv_add_time)
    TextView tvAddTime;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_report_obj)
    TextView tvReportObj;
    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.scroll_view)
    ScrollView scrollView;
    private String sid = "";

    public static void goTo(Context context, String sid) {
        Intent intent = new Intent(context, SecretReportDetailActivity.class);
        intent.putExtra("sid", sid);
        context.startActivity(intent);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_secret_report_detail;
    }

    @Override
    protected void initViews() {
        if (getIntent() != null) {
            sid = getIntent().getStringExtra("sid");
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
        webView.setBackgroundColor(0);
        String url = Consts.BASE_URL + "/application/secretaryDutyFileDetails";
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("id", sid);
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                Gson gson = new Gson();
                String msg = "";
                try {
                    SecretReportDetailBean bean = gson.fromJson(response, SecretReportDetailBean.class);
                    String code = bean.getCode();
                    msg = bean.getMsg();
                    if (!TextUtils.equals(code, "0")) {
                        ToastUtils.getInstance().showToast(SecretReportDetailActivity.this, msg);
                        return;
                    }
                    tvMainTitle.setText(bean.getData().getData().getTitle());
                    tvAddTime.setText(bean.getData().getData().getTime());
                    tvTime.setText(bean.getData().getData().getTime());
                    webView.loadData(bean.getData().getData().getContent(), "text/html; charset=UTF-8", null);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(SecretReportDetailActivity.this, "网络异常~");
            }
        });
        //水印
        String phone = "";
        MyApplication app = (MyApplication)getApplicationContext();
        if (app.getmLoginBean() != null) {
            if(app.getmLoginBean().getData()!=null){
                phone = app.getmLoginBean().getData().getPhone();
            }
        }
        ULog.e("ck",phone+":phone");
        List<String> labels = new ArrayList<>();
        labels.add(phone);
        scrollView.setBackgroundDrawable(new WaterMarkBg(this,labels,-15,13));

    }

    @Override
    protected void initToolbar() {
        tvTitle.setText("详情");
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

}
