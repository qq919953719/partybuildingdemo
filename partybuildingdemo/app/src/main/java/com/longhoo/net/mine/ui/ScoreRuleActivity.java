package com.longhoo.net.mine.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.longhoo.net.R;
import com.longhoo.net.mine.bean.ScoreRuleBean;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.utils.httprequest.OkHttpCallback;
import com.longhoo.net.utils.httprequest.OkHttpUtil;
import com.longhoo.net.widget.base.BaseActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScoreRuleActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.line)
    View line;
    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.tv_reload)
    TextView tvReload;
    @BindView(R.id.ll_net_error_panel)
    LinearLayout llNetErrorPanel;

    @Override
    protected int getContentId() {
        return R.layout.activity_score_rule;
    }

    @Override
    protected void initViews() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) { //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(url);
                return true;
            }
        });
        doGetRules();
    }

    @Override
    protected void initToolbar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        tvTitle.setText("积分规则");
        toolbar.setNavigationIcon(R.drawable.left_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    /**
     * 获取积分规则
     */
    private void doGetRules() {
        String url = Consts.BASE_URL + "/census/interRule";
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                if (isFinishing()) {
                    return;
                }
                ULog.e("cc", "积分规则：" + response);
                Gson gson = new Gson();
                try {
                    ScoreRuleBean mScoreRuleBean = gson.fromJson(response, ScoreRuleBean.class);
                    String code = mScoreRuleBean.getCode();
                    String msg = mScoreRuleBean.getMsg();
                    if (!TextUtils.equals(code, "0")) {
                        ToastUtils.getInstance().showToast(ScoreRuleActivity.this, msg);
                        return;
                    }

                    webView.loadData(mScoreRuleBean.getData().getInfo(), "text/html; charset=UTF-8", null);
                    progressbar.setVisibility(View.GONE);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    ToastUtils.getInstance().showToast(ScoreRuleActivity.this, "服务器异常~");
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(ScoreRuleActivity.this, "网络异常~");
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
