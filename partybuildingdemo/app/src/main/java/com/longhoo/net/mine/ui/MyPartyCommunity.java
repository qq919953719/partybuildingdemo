package com.longhoo.net.mine.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.longhoo.net.R;
import com.longhoo.net.manageservice.adapter.PartyCommunityAdapter;
import com.longhoo.net.manageservice.bean.PartyCommunityBean;
import com.longhoo.net.manageservice.ui.HuaTiContentActivity;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.MessageEvent;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.httprequest.HttpRequestPresenter;
import com.longhoo.net.utils.httprequest.HttpRequestView;
import com.longhoo.net.widget.HeaderAndFooterWrapper;
import com.longhoo.net.widget.base.BaseActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

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
import butterknife.ButterKnife;

/**
 * 我的发布
 */

public class MyPartyCommunity extends BaseActivity implements OnRefreshLoadmoreListener, HttpRequestView,HeaderAndFooterWrapper.OnItemClickListener
        ,PartyCommunityAdapter.OnGridItemClickListener{
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;

    private List<PartyCommunityBean.DataBean.SaidsBean> dataList = new ArrayList<>();
    private HeaderAndFooterWrapper adapterWarpper;
    PartyCommunityAdapter adapter;
    private HttpRequestPresenter requestPresenter;
    private int mPage = 1;


    @Override
    protected int getContentId() {
        return R.layout.activity_mypartycommunit;
    }

    @Override
    protected void initViews() {
        EventBus.getDefault().register(this);
        requestPresenter = new HttpRequestPresenter(this, this);
        toolbar.setBackgroundColor(Color.parseColor("#e71e1e"));
        tvTitle.setTextColor(Color.parseColor("#ffffff"));
        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(this));
        smartRefreshLayout.setRefreshFooter(new ClassicsFooter(this));
        smartRefreshLayout.setOnRefreshLoadmoreListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new PartyCommunityAdapter(this, dataList);
        adapter.setOnGridItemClickListemer(this);
        adapterWarpper = new HeaderAndFooterWrapper(adapter);
        recyclerView.setAdapter(adapterWarpper);
        adapterWarpper.setOnItemClickListener(this);
        smartRefreshLayout.autoRefresh(500);
    }

    @Override
    protected void initToolbar() {

//        setSupportActionBar(toolbar);
        tvTitle.setText("我的发布");
        toolbar.setNavigationIcon(R.drawable.left_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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
        EventBus.getDefault().unregister(this);
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
                    PartyCommunityBean searchResultBean = gson.fromJson(response, PartyCommunityBean.class);
                    if (searchResultBean.getData().getSaids().size() > 0) {
                        dataList.clear();
                        dataList.addAll(searchResultBean.getData().getSaids());
                    } else {
                        Toast.makeText(MyPartyCommunity.this, "暂无数据", Toast.LENGTH_SHORT).show();
                    }

                    adapterWarpper.notifyDataSetChanged();
                } else {
                    Toast.makeText(MyPartyCommunity.this, "数据异常", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MyPartyCommunity.this, "数据异常", Toast.LENGTH_SHORT).show();
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
                    PartyCommunityBean searchResultBean = gson.fromJson(response, PartyCommunityBean.class);

                    if (searchResultBean.getData().getSaids().size() > 0) {
                        dataList.addAll(searchResultBean.getData().getSaids());
                    }
                    adapterWarpper.notifyDataSetChanged();
                } else {
                    Toast.makeText(MyPartyCommunity.this, "数据异常", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MyPartyCommunity.this, "数据异常", Toast.LENGTH_SHORT).show();
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
        params.put("uid", EnterCheckUtil.getInstance().getUid_Token()[0]);
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        requestPresenter.doHttpData(Consts.BASE_URL + "/Said/log_mysaid", Consts.REQUEST_METHOD_POST, Consts.REQUEST_LOADMORE, params);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        smartRefreshLayout.setEnableLoadmore(true);
        mPage = 1;
        Map<String, String> params = new HashMap<>();
        params.put("page", String.valueOf(mPage + ""));
        params.put("uid", EnterCheckUtil.getInstance().getUid_Token()[0]);
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        requestPresenter.doHttpData(Consts.BASE_URL + "/Said/log_mysaid", Consts.REQUEST_METHOD_POST, Consts.REQUEST_REFRESH, params);

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

    @Override
    public void onGridItemClick(int listPosition, int position) {
        Intent intent = new Intent();
        intent.setClass(this, HuaTiContentActivity.class);
        intent.putExtra("position",listPosition);
        intent.putExtra("sid", dataList.get(listPosition).getSid());
        startActivity(intent);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent();
        intent.setClass(this, HuaTiContentActivity.class);
        intent.putExtra("position",position);
        intent.putExtra("sid", dataList.get(position).getSid());
        startActivity(intent);
    }

    /**
     * 刷新列表点赞数
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        String msg = event.message;
        if(TextUtils.equals(msg,"refresh")){
            smartRefreshLayout.autoRefresh(0);
        }else{
            int position = event.position;
            if (position < 0 || position > dataList.size() - 1) {
                return;
            }
            String sub[] = event.message.split("\\|");
            if (sub.length >= 3) {
                dataList.get(position).setIszan(sub[0]);
                dataList.get(position).setZan(sub[1]);
                dataList.get(position).setCom(sub[2]);
            }
            int browse = 0;
            try{
                browse = Integer.parseInt(dataList.get(position).getBrowse());
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
            dataList.get(position).setBrowse(String.valueOf(++browse));
            adapterWarpper.notifyItemChanged(position);
        }

    }
}