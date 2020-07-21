package com.longhoo.net.manageservice.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.longhoo.net.MyApplication;
import com.longhoo.net.R;
import com.longhoo.net.manageservice.adapter.DemocraticLifeAdapter;
import com.longhoo.net.manageservice.adapter.OrgazationLifeAdapter;
import com.longhoo.net.manageservice.bean.DemocraticLifeBean;
import com.longhoo.net.manageservice.bean.OrgazationLifeBean;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.httprequest.HttpRequestPresenter;
import com.longhoo.net.utils.httprequest.HttpRequestView;
import com.longhoo.net.widget.base.BaseActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ${CC} on 2017/12/2.
 */

public class OrgazationLifeActivity extends BaseActivity implements OnRefreshLoadmoreListener, HttpRequestView {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;

    private List<OrgazationLifeBean.DataBean.ListBean> dataList = new ArrayList<>();
    OrgazationLifeAdapter adapter;
    private HttpRequestPresenter requestPresenter;
    private int mPage = 1;
    private String strOid;
    String strTitle;
    String type = "";

    @Override
    protected int getContentId() {
        return R.layout.activity_democraticlife;
    }

    @Override
    protected void initViews() {
        requestPresenter = new HttpRequestPresenter(this, this);
        if (getIntent() != null) {
            strOid = getIntent().getStringExtra("oid");
            type= getIntent().getStringExtra("type");
            strTitle = getIntent().getStringExtra("title");
            tvTitle.setText(strTitle);
        }
        toolbar.setBackgroundColor(Color.parseColor("#e71e1e"));
        tvTitle.setTextColor(Color.parseColor("#ffffff"));
        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(this));
        smartRefreshLayout.setRefreshFooter(new ClassicsFooter(this));
        smartRefreshLayout.setOnRefreshLoadmoreListener(this);
        smartRefreshLayout.autoRefresh(0);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new OrgazationLifeAdapter(this, dataList,type);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void initToolbar() {

//        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.left_arrow);
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
    public void onNetworkError() {
        finishRefreshLoad();
        ToastUtils.getInstance().showToast(this, "网络连接异常，请检查网络设置~");
    }

    @Override
    public void onDestroy() {
        finishRefreshLoad();
        super.onDestroy();
    }

    @Override
    public void onRefreshSuccess(String response) {
        smartRefreshLayout.finishRefresh(0);

        try {
            JSONObject obj = null;
            obj = new JSONObject(response);
            if (obj.has("code")) {
                if (obj.getString("code").equals("0")) {

                    Gson gson = new Gson();
                    OrgazationLifeBean searchResultBean = gson.fromJson(response, OrgazationLifeBean.class);
                    if (searchResultBean.getData().getList().size() > 0) {
                        dataList.clear();
                        dataList.addAll(searchResultBean.getData().getList());
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(OrgazationLifeActivity.this, "数据异常", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(OrgazationLifeActivity.this, "数据异常", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onRefreshError() {
        ToastUtils.getInstance().showToast(this, "刷新失败~");
        smartRefreshLayout.finishRefresh(0);
    }

    @Override
    public void onLoadMoreSuccess(String response) {
        smartRefreshLayout.finishLoadmore(0);

        try {
            JSONObject obj = null;
            obj = new JSONObject(response);
            if (obj.has("code")) {
                if (obj.getString("code").equals("0")) {
                    Gson gson = new Gson();
                    OrgazationLifeBean searchResultBean = gson.fromJson(response, OrgazationLifeBean.class);

                    if (searchResultBean.getData().getList().size() > 0) {
                        dataList.addAll(searchResultBean.getData().getList());
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(OrgazationLifeActivity.this, "数据异常", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(OrgazationLifeActivity.this, "数据异常", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onLoadMoreError() {
        ToastUtils.getInstance().showToast(this, "加载失败~");
        smartRefreshLayout.finishLoadmore(0);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        mPage++;
        Map<String, String> params = new HashMap<>();
        params.put("page", String.valueOf(mPage + ""));
        params.put("oid", String.valueOf(strOid));
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        if (type.equals("1")) {
            requestPresenter.doHttpData(Consts.BASE_URL + "/Organization/olifeList", Consts.REQUEST_METHOD_POST, Consts.REQUEST_LOADMORE, params);
        } else {
            requestPresenter.doHttpData(Consts.BASE_URL + "/Organization/threemeetingList", Consts.REQUEST_METHOD_POST, Consts.REQUEST_LOADMORE, params);

        }


    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mPage = 1;
        Map<String, String> params = new HashMap<>();
        params.put("page", String.valueOf(mPage + ""));
        params.put("oid", String.valueOf(strOid));
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        if (type.equals("1")) {
            requestPresenter.doHttpData(Consts.BASE_URL + "/Organization/olifeList", Consts.REQUEST_METHOD_POST, Consts.REQUEST_REFRESH, params);
        } else {
            requestPresenter.doHttpData(Consts.BASE_URL + "/Organization/threemeetingList", Consts.REQUEST_METHOD_POST, Consts.REQUEST_REFRESH, params);
        }


    }


    //第一个参数为请求码，即调用startActivityForResult()传递过去的值
    //第二个参数为结果码，结果码用于标识返回数据来自哪个新Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        switch (resultCode) {
            case 100:
                smartRefreshLayout.autoRefresh(0);
                break;
            case 2:

        }

    }
}