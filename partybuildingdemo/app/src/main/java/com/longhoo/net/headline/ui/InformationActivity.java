package com.longhoo.net.headline.ui;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.longhoo.net.R;
import com.longhoo.net.headline.adapter.InformationAdapter;
import com.longhoo.net.headline.bean.InformationBean;
import com.longhoo.net.mine.ui.MyLibrayrActivity;
import com.longhoo.net.manageservice.ui.OrgazationLifeCointentActivity;
import com.longhoo.net.partyaffairs.ui.ApprovalActivity;
import com.longhoo.net.study.ui.ExaminationDetailActivity;
import com.longhoo.net.partyaffairs.ui.NotiNewsDetailActivity;
import com.longhoo.net.manageservice.ui.PayMembershipDuesActivity;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
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

public class InformationActivity extends BaseActivity implements HttpRequestView, OnRefreshLoadmoreListener,
        HeaderAndFooterWrapper.OnItemClickListener,InformationAdapter.OnContainerClickListener {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    private InformationAdapter adapter;
    private HeaderAndFooterWrapper wrapperAdapter;
    private List<InformationBean.DataBean.NewsBean> dataList = new ArrayList<>();
    private HttpRequestPresenter requestPresenter;
    private String url = Consts.BASE_URL + "/News/log_msg_new";
    private int mPage = 1;

    @Override
    protected int getContentId() {
        return R.layout.activity_information;
    }

    @Override
    protected void initViews() {
        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(this));
        smartRefreshLayout.setRefreshFooter(new ClassicsFooter(this));
        smartRefreshLayout.setOnRefreshLoadmoreListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new InformationAdapter(this, dataList);
        adapter.setOnContainerClickListener(this);
        wrapperAdapter = new HeaderAndFooterWrapper(adapter);
        //wrapperAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(wrapperAdapter);
        requestPresenter = new HttpRequestPresenter(this, this);
        smartRefreshLayout.autoRefresh(200);
    }

    @Override
    protected void initToolbar() {
        tvTitle.setText("通知公告");
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
    public void onNetworkError() {
        finishRefreshLoad();
        ToastUtils.getInstance().showToast(this, "网络连接异常，请检查网络设置~");
    }

    @Override
    public void onRefreshSuccess(String response) {
        ULog.e(Consts.TAG, "通知公告:" + response);
        //处理数据
        List<InformationBean.DataBean.NewsBean> tempList = null;
        Gson gson = new Gson();
        try {
            InformationBean bean = gson.fromJson(response, InformationBean.class);
            String code = bean.getCode();
            if (!TextUtils.equals(code, "0")) {
                ToastUtils.getInstance().showToast(this, "获取数据失败~");
                smartRefreshLayout.finishRefresh(0);
                return;
            }
            tempList = bean.getData().getNews();
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
        ULog.e(Consts.TAG, "通知公告:" + mPage + ":" + response);
        //处理数据
        List<InformationBean.DataBean.NewsBean> tempList2 = null;
        Gson gson = new Gson();
        try {
            InformationBean bean = gson.fromJson(response, InformationBean.class);
            String code = bean.getCode();
            if (!TextUtils.equals(code, "0")) {
                ToastUtils.getInstance().showToast(this, "获取数据失败~");
                smartRefreshLayout.finishLoadmore(0);
                return;
            }
            tempList2 = bean.getData().getNews();
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
        params.put("page", mPage + "");
        requestPresenter.doHttpData(url, Consts.REQUEST_METHOD_POST, Consts.REQUEST_LOADMORE, params);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        smartRefreshLayout.setEnableLoadmore(true);
        mPage = 1;
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
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
    }

    @Override
    public void onItemClick(View view, int position) {
        if (position < 0 || position > dataList.size() - 1) {
            return;
        }
        ///需要登录
        if(!EnterCheckUtil.getInstance().isLogin(true)){
            return;
        }
        String type = dataList.get(position).getType();
        String title = dataList.get(position).getTitle();
        String tid = dataList.get(position).getTid();
        ULog.e("ck",type+" "+tid);
        Intent intent = null;
        if (TextUtils.equals(type, "0")) { //通知公告
                intent = new Intent(this, NotiNewsDetailActivity.class);
                intent.putExtra("noti_news_mid", tid);
        } else if (TextUtils.equals(type, "1")) { //入党审核动态
                intent = new Intent(this, ApprovalActivity.class);
                intent.putExtra("id", tid);
        } else if (TextUtils.equals(type, "2")) { //党费缴纳
                intent = new Intent(this, PayMembershipDuesActivity.class);
        } else if (TextUtils.equals(type, "3")) { //借书列表
                intent = new Intent(this, MyLibrayrActivity.class);
        } else if (TextUtils.equals(type, "4")) { //在线测试
                intent = new Intent(this, ExaminationDetailActivity.class);
                intent.putExtra("exam_id",tid);
        } else if (TextUtils.equals(type, "5")) {
            intent = new Intent(this, NotiNewsDetailActivity.class);
            intent.putExtra("noti_news_mid", tid);
        } else if (TextUtils.equals(type, "6")) { //民主生活会
                intent = new Intent(this, OrgazationLifeCointentActivity.class);
                intent.putExtra("lid", tid);
                intent.putExtra("type", "2");
        } else if (TextUtils.equals(type, "7")) { //组织生活会
                intent = new Intent(this, OrgazationLifeCointentActivity.class);
                intent.putExtra("lid", tid);
                intent.putExtra("type", "3");
        } else if (TextUtils.equals(type, "8")) { //三会一课
                intent = new Intent(this, OrgazationLifeCointentActivity.class);
                intent.putExtra("lid", tid);
                intent.putExtra("type", "4");
        }else if (TextUtils.equals(type, "9")) { //新的通知详情
                intent = new Intent(this, NotificationDetailActivity.class);
                intent.putExtra("noti_news_tid", tid);
        }else if (TextUtils.equals(type, "10")) { //党纪监督详情
                intent = new Intent(this, SuperviseDetailActivity.class);
                intent.putExtra("noti_news_tid", tid);
        }
        if (intent != null) {
            startActivity(intent);
        }
    }

    @Override
    public void onContainerClick(int position) {
        onItemClick(null,position);
    }
}
