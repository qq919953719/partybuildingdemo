package com.longhoo.net.partyaffairs.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.longhoo.net.MyApplication;
import com.longhoo.net.R;
import com.longhoo.net.partyaffairs.adapter.VoteOptionAdapter;
import com.longhoo.net.partyaffairs.bean.VoteOptionBean;
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
import com.longhoo.net.widget.HeaderAndFooterWrapper;
import com.longhoo.net.widget.base.BaseActivity;

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

public class VoteOptionActivity extends BaseActivity implements HttpRequestView, OnRefreshLoadmoreListener, HeaderAndFooterWrapper.OnItemClickListener, VoteOptionAdapter.OnVoteClickListener {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.tv_right)
    TextView tvRight;
    private VoteOptionAdapter adapter;
    private HeaderAndFooterWrapper wrapperAdapter;
    private List<VoteOptionBean.DataBean> dataList = new ArrayList<>();
    private HttpRequestPresenter requestPresenter;
    private String url = Consts.BASE_URL + "/Vote/getList";
    private int mPage;
    private String cid, title = "", thumb, content = "", uid = "";
    // private VoteOptionBean.DataBean.AddBean.FieldsBean fieldsBean;

    public static void goToVoteOption(Activity activity, String cid, String title, String uid) {
        Intent intent = new Intent(activity, VoteOptionActivity.class);
        intent.putExtra("vote_cid", cid);
        intent.putExtra("vote_title", title);
        intent.putExtra("uid", uid);

//        intent.putExtra("vote_thumb",thumb);
//        intent.putExtra("vote_content",content);
//        intent.putExtra("vote_fields",fieldsBean);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_vote_option;
    }

    @Override
    protected void initViews() {
        if (getIntent() != null) {
            cid = getIntent().getStringExtra("vote_cid");
            title = getIntent().getStringExtra("vote_title");
            uid = getIntent().getStringExtra("uid");
//            thumb = getIntent().getStringExtra("vote_thumb");
//            content = getIntent().getStringExtra("vote_content");
//            fieldsBean = (VoteOptionBean.DataBean.AddBean.FieldsBean) getIntent().getSerializableExtra("vote_fields");
        }
        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(this));
        smartRefreshLayout.setRefreshFooter(new ClassicsFooter(this));
        smartRefreshLayout.setOnRefreshLoadmoreListener(this);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new VoteOptionAdapter(this, dataList);
        adapter.setOnVoteClickListener(this);
        wrapperAdapter = new HeaderAndFooterWrapper(adapter);
        wrapperAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(wrapperAdapter);
        requestPresenter = new HttpRequestPresenter(this, this);
        smartRefreshLayout.autoRefresh(200);
    }

    @Override
    protected void initToolbar() {
        tvTitle.setText(title);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.left_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        MyApplication app = (MyApplication) getApplicationContext();
        String roleId = app.getmLoginBean().getData().getUid();
        if (roleId.equals(uid)) {
            tvRight.setVisibility(View.VISIBLE);
            tvRight.setText("删除");
            tvRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    new AlertDialog.Builder(VoteOptionActivity.this)
                            .setTitle("提示")
                            .setMessage("您确定删除此条信息么？")
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    delVote();
                                    dialog.dismiss();
                                }
                            })
                            .setNegativeButton("否", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .show();


                }
            });
        } else {
            tvRight.setVisibility(View.GONE);
        }
    }

    private void addHeader() {
        wrapperAdapter.clearHeaderViews();
        View headerView = LayoutInflater.from(this).inflate(R.layout.layout_vote_option_header, null);
        wrapperAdapter.addHeaderView(headerView);
        ImageView ivThumb = (ImageView) headerView.findViewById(R.id.iv_thumb);
        WebView webView = (WebView) headerView.findViewById(R.id.web_view);
        TextView tvSignUp = (TextView) headerView.findViewById(R.id.tv_sign_up);
        if (!TextUtils.isEmpty(thumb)) {
            GlideManager.getInstance().load(this, thumb, ivThumb);
        }
        //tvContent.setText(content);
        WebSettings webSettings = webView.getSettings();
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) { //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(url);
                return true;
            }
        });
        webView.loadData(content, "text/html; charset=UTF-8", null);
//        tvSignUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(VoteOptionActivity.this,SignUpForVoteActivity.class);
//                intent.putExtra("cid",cid);
//                intent.putExtra("vote_fields",fieldsBean);
//                startActivity(intent);
//            }
//        });
    }

    @Override
    public void onNetworkError() {
        finishRefreshLoad();
        ToastUtils.getInstance().showToast(this, "网络连接异常，请检查网络设置~");
    }

    @Override
    public void onRefreshSuccess(String response) {
        ULog.e(Consts.TAG, "民主投票选项:" + response);
        //处理数据
        List<VoteOptionBean.DataBean> tempList = null;
        Gson gson = new Gson();
        try {
            VoteOptionBean voteOptionBean = gson.fromJson(response, VoteOptionBean.class);
            String code = voteOptionBean.getCode();
            if (!TextUtils.equals(code, "0")) {
                ToastUtils.getInstance().showToast(this, "获取数据失败~");
                smartRefreshLayout.finishRefresh(0);
                return;
            }
            tempList = voteOptionBean.getData();
            //更新投票详情数据
            //thumb = voteOptionBean.getData().getAdd().getThumb();
            content = voteOptionBean.getMsg();
            // fieldsBean = voteOptionBean.getData().getAdd().getFields();
            addHeader();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            smartRefreshLayout.finishRefresh(0);
        }
        if (tempList == null) {
            ToastUtils.getInstance().showToast(this, "服务器异常~");
            smartRefreshLayout.finishRefresh(0);
            return;
        }
        dataList.clear();
        if (tempList.size() > 0) {
            dataList.addAll(tempList);
            mPage++;
        }
        wrapperAdapter.notifyDataSetChanged();
        smartRefreshLayout.finishRefresh(0);
    }

    @Override
    public void onRefreshError() {
        ToastUtils.getInstance().showToast(this, "刷新失败~");
        smartRefreshLayout.finishRefresh(0);
    }

    @Override
    public void onLoadMoreSuccess(String response) {
        ULog.e(Consts.TAG, "民主投票选项:" + mPage + ":" + response);
        //处理数据
        List<VoteOptionBean.DataBean> tempList2 = null;
        Gson gson = new Gson();
        try {
            VoteOptionBean voteOptionBean = gson.fromJson(response, VoteOptionBean.class);
            String code = voteOptionBean.getCode();
            if (!TextUtils.equals(code, "0")) {
                ToastUtils.getInstance().showToast(this, "获取数据失败~");
                smartRefreshLayout.finishLoadmore(0);
                return;
            }
            tempList2 = voteOptionBean.getData();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            smartRefreshLayout.finishLoadmore(0);
        }
        if (tempList2 == null) {
            ToastUtils.getInstance().showToast(this, "解析错误！");
            smartRefreshLayout.finishLoadmore(0);
            return;
        }
        //没有数据或数据<limit，说明无更多
        if (tempList2.size() <= 0) {
            ToastUtils.getInstance().showToast(this, "没有更多了~");
            smartRefreshLayout.setEnableLoadmore(false);
        } else {
            dataList.addAll(tempList2);
            mPage++;
            wrapperAdapter.notifyDataSetChanged();
        }
        smartRefreshLayout.finishLoadmore(0);
    }

    @Override
    public void onLoadMoreError() {
        ToastUtils.getInstance().showToast(this, "加载失败~");
        smartRefreshLayout.finishLoadmore(0);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("catid", cid);
        params.put("page", mPage + "");
        requestPresenter.doHttpData(url, Consts.REQUEST_METHOD_POST, Consts.REQUEST_LOADMORE, params);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        smartRefreshLayout.setEnableLoadmore(true);
        mPage = 1;
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("catid", cid);
        params.put("page", mPage + "");
        requestPresenter.doHttpData(url, Consts.REQUEST_METHOD_POST, Consts.REQUEST_REFRESH, params);
    }

    private void finishRefreshLoad() {
        if (smartRefreshLayout != null) {
            if (smartRefreshLayout.isRefreshing()) {
                smartRefreshLayout.finishRefresh(0);
            }
            if (smartRefreshLayout.isLoading()) {
                smartRefreshLayout.finishLoadmore(0);
            }
        }
    }

    @Override
    public void onDestroy() {
        finishRefreshLoad();
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        if (position < 0 || position > dataList.size() - 1) {
            return;
        }
//        Intent intent = new Intent(this, VoteOptionDetailActivity.class);
//        intent.putExtra("voteoption_bean", (Parcelable) dataList.get(position));
//        intent.putExtra("position", position);
//        startActivity(intent);
    }

    @Override
    public void OnVoteClick(int position) {
        if (position < 0 || position > dataList.size() - 1) {
            return;
        }


        if (dataList.get(position).getIs_zan().equals("0")) {
            doVote(dataList.get(position), position);
        } else {
            //ToastUtils.getInstance().showToast(this, "您已");
        }

        //  Intent intent = new Intent(this, VoteOptionDetailActivity.class);
//        intent.putExtra("voteoption_bean", (Parcelable) dataList.get(position));
//        intent.putExtra("position", position);
//        startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        int position = event.position;
        if (position < 0 || position > dataList.size() - 1) {
            return;
        }
        int num = 0;
        try {
            num = Integer.parseInt(dataList.get(position).getNum());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        dataList.get(position).setNum(String.valueOf(++num));
        wrapperAdapter.notifyItemChanged(position + wrapperAdapter.getHeadersCount());
    }

    private void delVote() {
        if (cid == null) {
            ToastUtils.getInstance().showToast(this, "数据丢失，请返回重试~");
            return;
        }
        String url = Consts.BASE_URL + "/Vote/delCate";
        Map<String, String> params = new HashMap<>();
        params.put("catid", cid);
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                if (isFinishing()) {
                    return;
                }
                ULog.e("cc", "删除投票分类：" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.optString("code");
                    String msg = jsonObject.optString("msg");
                    ToastUtils.getInstance().showToast(VoteOptionActivity.this, msg);
                    if (TextUtils.equals(code, "0")) {
                        //投票成功
                        MessageEvent event = new MessageEvent();
                        event.msgType = MessageEvent.MSG_REFRESH_VOTE_LIST;
                        EventBus.getDefault().post(event);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtils.getInstance().showToast(VoteOptionActivity.this, "投票失败~");
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(VoteOptionActivity.this, "投票失败~");
            }
        });
    }

    private void doVote(VoteOptionBean.DataBean dataBean, final int Mposition) {
        if (dataBean == null) {
            ToastUtils.getInstance().showToast(this, "数据丢失，请返回重试~");
            return;
        }
        String url = Consts.BASE_URL + "/Vote/addVote";
        Map<String, String> params = new HashMap<>();
        params.put("catid", cid);
        params.put("pid", dataBean.getId() + "");
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                if (isFinishing()) {
                    return;
                }
                ULog.e("cc", "投票：" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.optString("code");
                    String msg = jsonObject.optString("msg");
                    ToastUtils.getInstance().showToast(VoteOptionActivity.this, msg);
                    if (TextUtils.equals(code, "0")) {
                        //投票成功
                        int position = Mposition;
                        if (position < 0 || position > dataList.size() - 1) {
                            return;
                        }
                        int num = 0;
                        try {
                            num = Integer.parseInt(dataList.get(position).getNum());
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                        dataList.get(position).setNum(String.valueOf(++num));
                        dataList.get(position).setIs_zan("1");
                        wrapperAdapter.notifyItemChanged(position + wrapperAdapter.getHeadersCount());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtils.getInstance().showToast(VoteOptionActivity.this, "投票失败~");
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(VoteOptionActivity.this, "投票失败~");
            }
        });
    }


}
