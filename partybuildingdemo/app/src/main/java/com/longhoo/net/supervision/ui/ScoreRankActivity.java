package com.longhoo.net.supervision.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.longhoo.net.R;
import com.longhoo.net.supervision.adapter.ScoreRankAdapter;
import com.longhoo.net.supervision.bean.ScoreRankBean;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.httprequest.HttpRequestPresenter;
import com.longhoo.net.utils.httprequest.HttpRequestView;
import com.longhoo.net.widget.HeaderAndFooterWrapper;
import com.longhoo.net.widget.base.BaseActivity;
import com.longhoo.net.widget.base.WebViewActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 积分排名
 */
public class ScoreRankActivity extends BaseActivity implements OnRefreshListener,HttpRequestView{

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_score_rule)
    TextView tvScoreRule;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;

    private HttpRequestPresenter requestPresenter;
    private List<ScoreRankBean.DataBeanX.DataBean> dataList = new ArrayList<>();
    private ScoreRankAdapter adapter;
    private String url = Consts.BASE_URL + "/application/socreList";

    @Override
    protected int getContentId() {
        return R.layout.activity_score_rank;
    }

    @Override
    protected void initViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ScoreRankAdapter(this, dataList);
        recyclerView.setAdapter(adapter);
        tvScoreRule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebViewActivity.goToWebView(ScoreRankActivity.this, Consts.BASE_URL + "/application/public_score?type=3", "积分规则", false);
            }
        });
        requestPresenter = new HttpRequestPresenter(this, this);
        refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this));
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.autoRefresh(200);
    }

    @Override
    protected void initToolbar() {
        tvTitle.setText("积分排名");
        toolbar.setTitle("");
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
        //处理数据
        List<ScoreRankBean.DataBeanX.DataBean> tempList = null;
        Gson gson = new Gson();
        try {
            ScoreRankBean scoreRankBean = gson.fromJson(response, ScoreRankBean.class);
            String code = scoreRankBean.getCode();
            if (!TextUtils.equals(code, "0")) {
                ToastUtils.getInstance().showToast(this, "获取数据失败~");
                refreshLayout.finishRefresh(0);
                return;
            }
            tempList = scoreRankBean.getData().getData();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            refreshLayout.finishRefresh(0);
        }
        dataList.clear();
        if (tempList!=null&&tempList.size() > 0) {
            dataList.addAll(tempList);
        }
        adapter.notifyDataSetChanged();
        refreshLayout.finishRefresh(0);
    }

    @Override
    public void onRefreshError() {
        ToastUtils.getInstance().showToast(this, "刷新失败~");
        refreshLayout.finishRefresh(0);
    }

    @Override
    public void onLoadMoreSuccess(String response) {

    }

    @Override
    public void onLoadMoreError() {

    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        requestPresenter.doHttpData(url, Consts.REQUEST_METHOD_POST, Consts.REQUEST_REFRESH, params);
    }

    private void finishRefreshLoad() {
        if (refreshLayout != null) {
            if (refreshLayout.isRefreshing()) {
                refreshLayout.finishRefresh(0);
            }
            if (refreshLayout.isLoading()) {
                refreshLayout.finishLoadmore(0);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finishRefreshLoad();
    }
}
