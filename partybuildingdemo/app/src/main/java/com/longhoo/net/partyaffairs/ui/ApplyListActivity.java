package com.longhoo.net.partyaffairs.ui;

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
import com.longhoo.net.partyaffairs.adapter.ApplyListAdapter;
import com.longhoo.net.partyaffairs.bean.ApplyBean;
import com.longhoo.net.utils.Consts;
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
 * Created by ${CC} on 2018/1/3.
 */

public class ApplyListActivity extends BaseActivity implements OnRefreshLoadmoreListener, HttpRequestView {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;

    private List<ApplyBean.DataBean> dataList = new ArrayList<>();
    ApplyListAdapter adapter;
    private HttpRequestPresenter requestPresenter;
    private int mPage = 1;


    @Override
    protected int getContentId() {
        return R.layout.activity_democraticlife;
    }

    @Override
    protected void initViews() {
        requestPresenter = new HttpRequestPresenter(this, this);
        toolbar.setBackgroundColor(Color.parseColor("#e71e1e"));
        tvTitle.setTextColor(Color.parseColor("#ffffff"));
        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(this));
        smartRefreshLayout.setRefreshFooter(new ClassicsFooter(this));
        smartRefreshLayout.setOnRefreshLoadmoreListener(this);
        smartRefreshLayout.autoRefresh(0);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new ApplyListAdapter(this, dataList);
        recyclerView.setAdapter(adapter);
    }
    @Override
    protected void initToolbar() {
//        setSupportActionBar(toolbar);
        tvTitle.setText("入党申请审批");
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
                    ApplyBean searchResultBean = gson.fromJson(response, ApplyBean.class);
                    if (searchResultBean.getData().size() > 0) {
                        dataList.clear();
                        dataList.addAll(searchResultBean.getData());
                    } else {
                        Toast.makeText(ApplyListActivity.this, "暂无数据", Toast.LENGTH_SHORT).show();
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(ApplyListActivity.this, "数据异常", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(ApplyListActivity.this, "数据异常", Toast.LENGTH_SHORT).show();
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
                    ApplyBean searchResultBean = gson.fromJson(response, ApplyBean.class);

                    if (searchResultBean.getData().size() > 0) {
                        dataList.addAll(searchResultBean.getData());
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(ApplyListActivity.this, "数据异常", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(ApplyListActivity.this, "数据异常", Toast.LENGTH_SHORT).show();
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
        MyApplication App = (MyApplication) getApplicationContext();
        if(App.getmLoginBean()==null){
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("page", String.valueOf(mPage + ""));
        params.put("oid", String.valueOf(App.getmLoginBean().getData().getOid() + ""));
//        http:另外需要传入page(页数) 、oid(组织id
//test.025nj.com/dangjian/index.php?m=Api&/Said/public_said&uid=0&page=1&signid=0&search=
        if (App.getmLoginBean() == null) {
            params.put("token", "");
        } else {
            params.put("token", String.valueOf(App.getmLoginBean().getData().getToken()));

        }
//        params.put("token", String.valueOf("mCR2G6JKoWvjOoDfOreYcg=="));
        requestPresenter.doHttpData(Consts.BASE_URL + "/Application/membershipList", Consts.REQUEST_METHOD_POST, Consts.REQUEST_LOADMORE, params);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mPage = 1;
        MyApplication App = (MyApplication) getApplicationContext();
        Map<String, String> params = new HashMap<>();
        params.put("page", String.valueOf(mPage + ""));
        params.put("oid", String.valueOf(App.getmLoginBean().getData().getOid() + ""));
//        http:
        if (App.getmLoginBean() == null) {
            params.put("token", "");
        } else {
            params.put("token", String.valueOf(App.getmLoginBean().getData().getToken()));
        }
        requestPresenter.doHttpData(Consts.BASE_URL + "/Application/membershipList", Consts.REQUEST_METHOD_POST, Consts.REQUEST_REFRESH, params);

    }
}
