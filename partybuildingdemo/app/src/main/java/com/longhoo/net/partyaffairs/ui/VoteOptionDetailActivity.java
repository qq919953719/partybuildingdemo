package com.longhoo.net.partyaffairs.ui;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.partyaffairs.bean.VoteOptionBean;
import com.longhoo.net.widget.banner.SlideBannerView;
import com.longhoo.net.widget.base.BaseActivity;

import butterknife.BindView;

public class VoteOptionDetailActivity extends BaseActivity {
    @BindView(R.id.banner_view)
    SlideBannerView bannerView;
    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.tv_vote)
    TextView tvVote;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private VoteOptionBean.DataBean dataBean;
    private int num;
    private int position;

    @Override
    protected int getContentId() {
        return R.layout.activity_vote_option_detail;
    }

    @Override
    protected void initViews() {
//        if (getIntent() != null) {
//            dataBean = (VoteOptionBean.DataBean) getIntent().getSerializableExtra("voteoption_bean");
//            position = getIntent().getIntExtra("position", 0);
//        }
//        WebSettings webSettings = webView.getSettings();
//        webSettings.setDefaultTextEncodingName("UTF-8");
//        webSettings.setJavaScriptEnabled(true);
//        webView.setWebViewClient(new WebViewClient() {
//            public boolean shouldOverrideUrlLoading(WebView view, String url) { //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
//                view.loadUrl(url);
//                return true;
//            }
//        });
//        if (dataBean != null) {
//            tvTitle.setText(dataBean.get() + "");
//            webView.loadData(dataBean.getContent1() + "", "text/html; charset=UTF-8", null);
//            tvNum.setText(dataBean.getNet() + "");
//            try {
//                num = Integer.parseInt(dataBean.getNet());
//            } catch (NumberFormatException e) {
//                e.printStackTrace();
//            }
//            List<String> thumbList = dataBean.getUploads();
//            if (thumbList.size() > 0) {
//                List<BannerEntity> bannerEntities = new ArrayList<>();
//                for (int i = 0; i < thumbList.size(); i++) {
//                    bannerEntities.add(new BannerEntity("", "", thumbList.get(i), ""));
//                }
//                bannerView.setImagePath(bannerEntities);
//                bannerView.setTitleVisibility(false);
//            }
//        }
//        tvVote.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                doVote();
//            }
//        });
    }

    @Override
    protected void initToolbar() {
       // tvTitle.setText(dataBean == null ? "投票" : "投票" + dataBean.getTitle1());
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

//    private void doVote() {
//        if (dataBean == null) {
//            ToastUtils.getInstance().showToast(this, "数据丢失，请返回重试~");
//            return;
//        }
//        String url = Consts.BASE_URL + "/Score/voteitem_action";
//        Map<String, String> params = new HashMap<>();
//        params.put("type", dataBean.getType());
//        params.put("cid", dataBean.getCid());
//        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
//        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
//            @Override
//            public void onSuccess(String response) {
//                if (isFinishing()) {
//                    return;
//                }
//                ULog.e("ck", "投票：" + response);
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    String code = jsonObject.optString("code");
//                    String msg = jsonObject.optString("msg");
//                    ToastUtils.getInstance().showToast(VoteOptionDetailActivity.this, msg);
//                    if (TextUtils.equals(code, "0")) {
//                        //投票成功
//                        tvNum.setText(String.valueOf(++num));
//                        //发送消息给投票选项列表，投票数增加
//                        MessageEvent messageEvent = new MessageEvent();
//                        messageEvent.position = position;
//                        EventBus.getDefault().post(messageEvent);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    ToastUtils.getInstance().showToast(VoteOptionDetailActivity.this, "投票失败~");
//                }
//            }
//
//            @Override
//            public void onFailure(String errorMsg) {
//                ToastUtils.getInstance().showToast(VoteOptionDetailActivity.this, "投票失败~");
//            }
//        });
//    }
}
