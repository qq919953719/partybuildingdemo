package com.longhoo.net.headline.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.study.ui.ExaminationCenterActivity;
import com.longhoo.net.widget.base.BaseActivity;
import com.longhoo.net.widget.qqx5webview.X5WebView;
import com.tencent.smtt.sdk.WebSettings;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WalkHealhActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.webview)
    X5WebView webview;
    @BindView(R.id.tv_parybuilding)
    TextView tvParybuilding;
    @BindView(R.id.tv_test)
    TextView tvTest;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private WebSettings mWebSettings;

    @Override
    protected int getContentId() {
        return R.layout.activity_walkhealth;
    }

    @Override
    protected void initViews() {
        mWebSettings = webview.getSettings();
        webview.setHorizontalScrollBarEnabled(false);//水平不显示
        webview.setVerticalScrollBarEnabled(false);//垂直不显示
        mWebSettings.setDisplayZoomControls(false);
        mWebSettings.setBuiltInZoomControls(false);
        mWebSettings.setSupportZoom(false);
        mWebSettings.setDisplayZoomControls(false);
//        webview.loadUrl("file:///android_asset/dangjian.html");
//        webview.loadUrl("http://dangjiantest.025nj.com/index.php/Home/index/jzactive");
        webview.loadUrl( "http://dangjian.025nj.com/index.php/Home/index/jzactive");


    }

    @Override
    protected void initToolbar() {

        toolbar.setTitle("");
        tvTitle.setText("龙虎网党员之家");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.left_arrow);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_right, R.id.tv_parybuilding, R.id.tv_test})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.tv_right:
                finish();
                break;
            case R.id.tv_parybuilding:
                Intent intent = new Intent();
                intent.setClass(WalkHealhActivity.this, PartyMemberSQActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_test:
                Intent intent2 = new Intent();
                intent2.setClass(WalkHealhActivity.this, ExaminationCenterActivity.class);
                startActivity(intent2);

                break;
        }
    }
}
