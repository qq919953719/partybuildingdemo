package com.longhoo.net.headline.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.longhoo.net.MyApplication;
import com.longhoo.net.R;
import com.longhoo.net.headline.adapter.SpecialNewsDetailAdapter;
import com.longhoo.net.headline.bean.SpecialNewsDetailBean;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.GlideManager;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.utils.httprequest.HttpRequestPresenter;
import com.longhoo.net.utils.httprequest.HttpRequestView;
import com.longhoo.net.widget.HeaderAndFooterWrapper;
import com.longhoo.net.widget.base.BaseActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 专题(post无数据)
 */
public class SpecialNewsDetailActivity extends BaseActivity implements HttpRequestView, OnRefreshLoadmoreListener, HeaderAndFooterWrapper.OnItemClickListener {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    @BindView(R.id.tv_reload)
    TextView tvReload;
    @BindView(R.id.ll_net_error_panel)
    LinearLayout netErrorPanel;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    private Activity activity;
    private SpecialNewsDetailAdapter adapter;
    private int mPage = 1;
    private List<SpecialNewsDetailBean.DataBean.NewsBean> dataList = new ArrayList<>();
    private HttpRequestPresenter requestPresenter;
    private HeaderAndFooterWrapper adapterWrapper;
    private String gid = "";
    private String url = Consts.BASE_URL + "/News/public_specialnews";
    private ImageView ivHeaderThumb;
    private TextView tvHeaderDigest;
    private TextView tvHeaderTitle;
    private View headerView;

    @Override
    protected int getContentId() {
        return R.layout.activity_special_news_detail;
    }

    @Override
    protected void initViews() {
        if (getIntent() != null) {
            gid = getIntent().getStringExtra("news_nid");
        }
        activity = this;
        requestPresenter = new HttpRequestPresenter(this, this);
        adapter = new SpecialNewsDetailAdapter(this, dataList);
        adapterWrapper = new HeaderAndFooterWrapper(adapter);
        adapterWrapper.setOnItemClickListener(this);
        recyclerView.setAdapter(adapterWrapper);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(this));
        smartRefreshLayout.setRefreshFooter(new ClassicsFooter(this));
        smartRefreshLayout.setOnRefreshLoadmoreListener(this);
        //第一次加载
        Map<String, String> params = new HashMap<>();
        params.put("uid", EnterCheckUtil.getInstance().getUid_Token()[0]);
        params.put("gid", gid);
        params.put("page", "1");
        requestPresenter.doInitHttpData(url, Consts.REQUEST_METHOD_POST, Consts.REQUEST_REFRESH, params);
    }


    @Override
    protected void initToolbar() {
        toolbar.setTitle("");
        tvTitle.setText("详情");
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
    public void onNetworkError() {
        finishRefreshLoad();
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        netErrorPanel.setVisibility(View.VISIBLE);
        ToastUtils.getInstance().showToast(this, "网络异常~");
        tvReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                netErrorPanel.setVisibility(View.GONE);
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Map<String, String> params = new HashMap<>();
                        params.put("uid", EnterCheckUtil.getInstance().getUid_Token()[0]);
                        params.put("gid", gid);
                        params.put("page", "1");
                        if (!isFinishing()) {
                            requestPresenter.doInitHttpData(url, Consts.REQUEST_METHOD_POST, Consts.REQUEST_REFRESH, params);
                        }
                    }
                }, 200);
            }
        });
    }

    @Override
    public void onRefreshSuccess(String response) {
        ULog.e(Consts.TAG, "special:" + response);
        //处理数据
        List<SpecialNewsDetailBean.DataBean.NewsBean> tempList = null;
        Gson gson = new Gson();
        try {
            SpecialNewsDetailBean specialNewsBean = gson.fromJson(response, SpecialNewsDetailBean.class);
            String code = specialNewsBean.getCode();
            if (!TextUtils.equals(code, "0")) {
                ToastUtils.getInstance().showToast(this, "获取数据失败~");
                return;
            }
            tempList = specialNewsBean.getData().getNews();
            //更新UI
            //添加头部
            if (headerView == null) {
                headerView = LayoutInflater.from(this).inflate(R.layout.layout_special_detail_header, recyclerView, false);
                ivHeaderThumb = (ImageView) headerView.findViewById(R.id.iv_header_thumb);
                tvHeaderDigest = (TextView) headerView.findViewById(R.id.tv_header_digest);
                tvHeaderTitle = (TextView) headerView.findViewById(R.id.tv_main_title);
                adapterWrapper.addHeaderView(headerView);
            }
            if (!TextUtils.isEmpty(specialNewsBean.getData().getSpecial().getPic())) {
                GlideManager.getInstance().load(activity,specialNewsBean.getData().getSpecial().getPic(),ivHeaderThumb);
            }
            String text = "摘要：" + specialNewsBean.getData().getSpecial().getDigest();
            SpannableString spanText = new SpannableString(text);
            spanText.setSpan(new TextAppearanceSpan(this, R.style.colorSpanText), 0, 3,
                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            tvHeaderDigest.setText(spanText);
            tvHeaderTitle.setText(specialNewsBean.getData().getSpecial().getTitle() + "");
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        if (tempList == null) {
            ToastUtils.getInstance().showToast(this, "服务器异常~");
            if (progressBar.isShown()) {
                progressBar.setVisibility(View.GONE);
            }
            return;
        }
        dataList.clear();
        if (tempList.size() > 0) {
            dataList.addAll(tempList);
            mPage++;
            //缓存数据
            //FileUtils.saveList(activity, (ArrayList) tempList, FileUtils.createCacheFile(activity,"data","headline_list_cache.txt"));
        }
        adapterWrapper.notifyDataSetChanged();
        smartRefreshLayout.finishRefresh(0);
        if (!recyclerView.isShown()) {
            recyclerView.setVisibility(View.VISIBLE);
        }
        if (progressBar.isShown()) {
            progressBar.setVisibility(View.GONE);
        }
        if (netErrorPanel.isShown()) {
            netErrorPanel.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRefreshError() {
        if (progressBar.isShown()) {
            progressBar.setVisibility(View.GONE);
        }
        if (netErrorPanel.isShown()) {
            netErrorPanel.setVisibility(View.GONE);
        }
        ToastUtils.getInstance().showToast(this, "刷新失败~");
        smartRefreshLayout.finishRefresh(0);
    }

    @Override
    public void onLoadMoreSuccess(String response) {
        ULog.e(Consts.TAG, "special:" + mPage + ":" + response);
        //处理数据
        List<SpecialNewsDetailBean.DataBean.NewsBean> tempList2 = null;
        Gson gson = new Gson();
        try {
            SpecialNewsDetailBean bean = gson.fromJson(response, SpecialNewsDetailBean.class);
            String code = bean.getCode();
            if (!TextUtils.equals(code, "0")) {
                ToastUtils.getInstance().showToast(this, "获取数据失败~");
                return;
            }
            tempList2 = bean.getData().getNews();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        if (tempList2 == null) {
            ToastUtils.getInstance().showToast(this, "解析错误！");
            return;
        }
        //没有数据或数据<limit，说明无更多
        if (tempList2.size() <= 0) {
            ToastUtils.getInstance().showToast(this, "没有更多了~");
            smartRefreshLayout.setEnableLoadmore(false);
        } else {
            dataList.addAll(tempList2);
            mPage++;
            adapterWrapper.notifyDataSetChanged();
        }
        smartRefreshLayout.finishLoadmore(0);
    }

    @Override
    public void onLoadMoreError() {
        if (progressBar.isShown()) {
            progressBar.setVisibility(View.GONE);
        }
        ToastUtils.getInstance().showToast(this, "加载失败~");
        smartRefreshLayout.finishLoadmore(0);
    }

//    @Override
//    public void onItemClick(View view, int position) {
//        //新闻详情
//        Intent intent = new Intent(this, NewsDetailActivity.class);
//        intent.putExtra("news_nid", dataList.get(position).getNid());
//        intent.putExtra("news_position",position);
//        startActivityForResult(intent,100);
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        Map<String, String> params = new HashMap<>();
        params.put("uid", EnterCheckUtil.getInstance().getUid_Token()[0]);
        params.put("gid", gid);
        params.put("page", mPage + "");
        requestPresenter.doHttpData(url, Consts.REQUEST_METHOD_POST, Consts.REQUEST_LOADMORE, params);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        smartRefreshLayout.setEnableLoadmore(true);
        mPage = 1;
        Map<String, String> params = new HashMap<>();
        params.put("uid", EnterCheckUtil.getInstance().getUid_Token()[0]);
        params.put("gid", gid);
        params.put("page", mPage + "");
        requestPresenter.doHttpData(url, Consts.REQUEST_METHOD_POST, Consts.REQUEST_REFRESH, params);
    }

    @Override
    public void onDestroy() {
        finishRefreshLoad();
        super.onDestroy();
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
    public void onItemClick(View view, int position) {
        if (position >= dataList.size()) {
            return;
        }
        String nid = dataList.get(position).getNid();
        Intent intent = new Intent(this, NewsDetailActivity.class);
        intent.putExtra("news_nid", nid);
        startActivity(intent);
    }
}
