package com.longhoo.net.study.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.longhoo.net.R;
import com.longhoo.net.manageservice.adapter.ErweimaSignAdapter;
import com.longhoo.net.manageservice.bean.ActsEncrollDetailBean;
import com.longhoo.net.manageservice.bean.ErWeimaSignBean;
import com.longhoo.net.study.bean.TrainCourseDetailBean;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.DisplayUtil;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.httprequest.OkHttpCallback;
import com.longhoo.net.utils.httprequest.OkHttpUtil;
import com.longhoo.net.widget.base.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 培训班报名详情
 */
public class TrainCourseDetailActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_head_time)
    TextView tvHeadTime;
    @BindView(R.id.tv_encroll_time)
    TextView tvEncrollTime;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_train_time)
    TextView tvTrainTime;
    @BindView(R.id.tv_train_obj)
    TextView tvTrainObj;
    @BindView(R.id.ll_train_obj)
    LinearLayout llTrainObj;
    @BindView(R.id.tag_name_intro)
    TextView tagNameIntro;
    @BindView(R.id.tv_acts_intro)
    TextView tvActsIntro;
    @BindView(R.id.tv_train_content)
    TextView tvTrainContent;
    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.iv_erweima)
    ImageView ivErweima;
    @BindView(R.id.tv_sign_name)
    TextView tvSignName;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.tv_tag_tuwen)
    TextView tvTagTuwen;
    private String title = "";
    private String id;
    private Activity mActivity;

    public static void goTo(Context context, String id) {
        Intent intent = new Intent(context, TrainCourseDetailActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_train_course_detail;
    }

    @Override
    protected void initViews() {
        if (getIntent() != null) {
            id = getIntent().getStringExtra("id");
        }
        mActivity = TrainCourseDetailActivity.this;
        WebSettings webSettings = webView.getSettings();
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) { //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(url);
                return true;
            }
        });

        String url = "";
        llTrainObj.setVisibility(View.VISIBLE);
        url = Consts.BASE_URL + "/Study/getClassInfo";
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("id", id);
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                Gson gson = new Gson();
                String msg = "";
                try {
                    TrainCourseDetailBean bean = gson.fromJson(response, TrainCourseDetailBean.class);
                    String code = bean.getCode();
                    msg = bean.getMsg();
                    if (!TextUtils.equals(code, "0")) {
                        ToastUtils.getInstance().showToast(TrainCourseDetailActivity.this, msg);
                        return;
                    }
                    title = bean.getData().getTitle();
                    if (title.length() > 15) {
                        title = title.substring(0, 15) + "...";
                    }
                    tvTitle.setText(title + "");
                    tvHeadTime.setText(bean.getData().getAdd_time());
                    tvEncrollTime.setText(bean.getData().getEnroll_starttime() + "-" + bean.getData().getEnroll_endtime());
                    tvTrainTime.setText(bean.getData().getTrain_starttime() + "-" + bean.getData().getTrain_endtime());
                    tvActsIntro.setText(Html.fromHtml(bean.getData().getDes() + ""));
                    tvAddress.setText(bean.getData().getAddr());
                    tvTrainObj.setText(bean.getData().getUnamestr());
                    tvTrainContent.setText(bean.getData().getContent());
                    if (TextUtils.isEmpty(bean.getData().getFengcai())) {
                        webView.setVisibility(View.GONE);
                        tvTagTuwen.setVisibility(View.GONE);
                    } else {
                        webView.setVisibility(View.VISIBLE);
                        tvTagTuwen.setVisibility(View.VISIBLE);
                        webView.loadData(bean.getData().getFengcai(), "text/html; charset=UTF-8", null);
                    }
                    if (bean.getData().getIsEnroll() == 1) {
                        tvSubmit.setEnabled(false);
                        tvSubmit.setText("未开始");
                    } else if (bean.getData().getIsEnroll() == 2) {
                        tvSubmit.setEnabled(false);
                        tvSubmit.setText("已结束");
                    } else if (bean.getData().getIsEnroll() == 3) {
                        tvSubmit.setEnabled(false);
                        tvSubmit.setText("已报名");
                    } else {
                        tvSubmit.setEnabled(true);
                        tvSubmit.setText("报名");
                    }
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(TrainCourseDetailActivity.this, "网络异常~");
            }
        });
        getErweima();
    }

    @Override
    protected void initToolbar() {
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

    @OnClick({R.id.tv_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_submit:
                doEncrollClass();
                break;
        }
    }

    /**
     * 培训班报名
     */
    private void doEncrollClass() {
        String url = Consts.BASE_URL + "/Study/setClassEnroll";
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("id", id);
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.optString("code");
                    String msg = jsonObject.optString("msg");
                    ToastUtils.getInstance().showToast(TrainCourseDetailActivity.this, msg);
                    if (TextUtils.equals(code, "-1")) {
                        tvSubmit.setEnabled(false);
                        tvSubmit.setBackground(getResources().getDrawable(R.drawable.bg_disenabled_solid_25));
                    }
                    if (TextUtils.equals(code, "0")) {
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(TrainCourseDetailActivity.this, "网络异常！");
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

    private void getErweima() {
        String url = Consts.BASE_URL + "/Study/createEwm";
        Map<String, String> map = new HashMap<>();
        map.put("uid", EnterCheckUtil.getInstance().getUid_Token()[0]);
        map.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        map.put("id", id + "");
        OkHttpUtil.getInstance().doAsyncPost(url, map, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                //处理数据
                Gson gson = new Gson();
                try {
                    ErWeimaSignBean signBean = gson.fromJson(response, ErWeimaSignBean.class);
                    int code = signBean.getCode();
                    if (code == 0) {
                        Glide.with(mActivity).asBitmap().load(signBean.getData().getEwm_img()).into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                int imgWidth = resource.getWidth();
                                int imgHeight = resource.getHeight();
                                int screenWidth = DisplayUtil.getScreenWidth(mActivity) - 2 * DisplayUtil.dp2px(mActivity, 10);
                                float ratio = (float) imgWidth / (float) imgHeight;
                                int height = (int) (screenWidth / ratio);
                                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) ivErweima.getLayoutParams();
                                lp.width = screenWidth;
                                lp.height = height;
                                ivErweima.setLayoutParams(lp);
                                ivErweima.setImageBitmap(resource);
                            }
                        });
                        recyclerView.setLayoutManager(new LinearLayoutManager(TrainCourseDetailActivity.this));
                        List<ErWeimaSignBean.DataBean.UserlistBean> list = new ArrayList<>();
                        if (signBean.getData().getUserlist() != null) {
                            list.addAll(signBean.getData().getUserlist());
                        }
                        recyclerView.setAdapter(new ErweimaSignAdapter(TrainCourseDetailActivity.this, list));
                        ivErweima.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);
                        tvSignName.setVisibility(View.VISIBLE);
                    }
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });
    }

}
