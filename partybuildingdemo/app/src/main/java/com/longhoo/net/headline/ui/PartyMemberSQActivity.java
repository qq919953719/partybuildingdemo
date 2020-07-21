package com.longhoo.net.headline.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.longhoo.net.R;
import com.longhoo.net.manageservice.adapter.PartyCommunityAdapter;
import com.longhoo.net.manageservice.bean.PartyCommunityBean;
import com.longhoo.net.manageservice.bean.SignBan;
import com.longhoo.net.manageservice.ui.HuaTiContentActivity;
import com.longhoo.net.manageservice.ui.PublishTopicActivity;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.MessageEvent;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.utils.httprequest.HttpRequestPresenter;
import com.longhoo.net.utils.httprequest.HttpRequestView;
import com.longhoo.net.utils.httprequest.OkHttpCallback;
import com.longhoo.net.utils.httprequest.OkHttpUtil;
import com.longhoo.net.widget.HeaderAndFooterWrapper;
import com.longhoo.net.widget.MyDialog;
import com.longhoo.net.widget.base.BaseActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PartyMemberSQActivity extends BaseActivity implements HttpRequestView, OnRefreshLoadmoreListener,
        HeaderAndFooterWrapper.OnItemClickListener, PartyCommunityAdapter.OnGridItemClickListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_sel)
    TextView tvSel;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.tv_publish)
    TextView tvPublish;
    private String url = "";
    private HttpRequestPresenter requestPresenter;
    private Activity mActivity;
    private int page;
    private HeaderAndFooterWrapper adapterWrapper;
    private List<PartyCommunityBean.DataBean.SaidsBean> dataList = new ArrayList<>();
    private PartyCommunityAdapter adapter;
    private List<SignBan> signList = new ArrayList<>();
    private String type="";

    @Override
    protected int getContentId() {
        return R.layout.activity_party_member_sq;
    }

    @Override
    protected void initViews() {
        EventBus.getDefault().register(this);
        url = Consts.BASE_URL + "/Said/saidlist";
        mActivity = this;
        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(mActivity));
        smartRefreshLayout.setRefreshFooter(new ClassicsFooter(mActivity));
        smartRefreshLayout.setOnRefreshLoadmoreListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new PartyCommunityAdapter(mActivity, dataList);
        adapter.setOnGridItemClickListemer(this);
        adapterWrapper = new HeaderAndFooterWrapper(adapter);
        adapterWrapper.setOnItemClickListener(this);
        recyclerView.setAdapter(adapterWrapper);
        requestPresenter = new HttpRequestPresenter(mActivity, this);
        tvPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(PartyMemberSQActivity.this, PublishTopicActivity.class), 100);
            }
        });
        doGetSigns();
    }

    @Override
    protected void initToolbar() {
        tvTitle.setText("党员社区");
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.left_arrow);
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
        ToastUtils.getInstance().showToast(mActivity, "网络连接异常，请检查网络设置~");
    }

    @Override
    public void onRefreshSuccess(String response) {
        if (isFinishing()) {
            return;
        }
        ULog.e(Consts.TAG, "党员社区:" + response);
        //处理数据
        List<PartyCommunityBean.DataBean.SaidsBean> tempList = null;
        Gson gson = new Gson();
        try {
            PartyCommunityBean communityBean = gson.fromJson(response, PartyCommunityBean.class);
            String code = communityBean.getCode();
            if (!TextUtils.equals(code, "0")) {
                ToastUtils.getInstance().showToast(mActivity, "获取数据失败~");
                smartRefreshLayout.finishRefresh(0);
                return;
            }
            tempList = communityBean.getData().getSaids();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            smartRefreshLayout.finishRefresh(0);
        }
        dataList.clear();
        if (tempList!=null&&tempList.size() > 0) {
            dataList.addAll(tempList);
            page++;
        }
        adapterWrapper.notifyDataSetChanged();
        smartRefreshLayout.finishRefresh(0);
    }

    @Override
    public void onRefreshError() {
        ToastUtils.getInstance().showToast(mActivity, "刷新失败~");
        smartRefreshLayout.finishRefresh(0);
    }

    @Override
    public void onLoadMoreSuccess(String response) {
        if (isFinishing()) {
            return;
        }
        ULog.e(Consts.TAG, "党员社区：" + page + ":" + response);
        //处理数据
        List<PartyCommunityBean.DataBean.SaidsBean> tempList2 = null;
        Gson gson = new Gson();
        try {
            PartyCommunityBean communityBean = gson.fromJson(response, PartyCommunityBean.class);
            String code = communityBean.getCode();
            if (!TextUtils.equals(code, "0")) {
                ToastUtils.getInstance().showToast(mActivity, "获取数据失败~");
                smartRefreshLayout.finishLoadmore(0);
                return;
            }
            tempList2 = communityBean.getData().getSaids();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            smartRefreshLayout.finishLoadmore(0);
        }
        if (tempList2 == null) {
            ToastUtils.getInstance().showToast(mActivity, "解析错误！");
            smartRefreshLayout.finishLoadmore(0);
            return;
        }
        //没有数据或数据<limit，说明无更多
        if (tempList2.size() <= 0) {
            ToastUtils.getInstance().showToast(mActivity, "没有更多了~");
            smartRefreshLayout.setEnableLoadmore(false);
        } else {
            dataList.addAll(tempList2);
            page++;
            adapterWrapper.notifyDataSetChanged();
        }
        smartRefreshLayout.finishLoadmore(0);
    }

    @Override
    public void onLoadMoreError() {
        ToastUtils.getInstance().showToast(mActivity, "加载失败~");
        smartRefreshLayout.finishLoadmore(0);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("page", page + "");
        params.put("search", "");
        params.put("type",type);
        requestPresenter.doHttpData(url, Consts.REQUEST_METHOD_POST, Consts.REQUEST_LOADMORE, params);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        smartRefreshLayout.setEnableLoadmore(true);
        page = 1;
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("page", page + "");
        params.put("search", "");
        params.put("type",type);
        requestPresenter.doHttpData(url, Consts.REQUEST_METHOD_POST, Consts.REQUEST_REFRESH, params);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent();
        intent.setClass(this, HuaTiContentActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("sid", dataList.get(position).getSid());
        startActivity(intent);
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

    //第一个参数为请求码，即调用startActivityForResult()传递过去的值
    //第二个参数为结果码，结果码用于标识返回数据来自哪个新Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case 100:
                smartRefreshLayout.autoRefresh(0);
                break;
        }
    }

    /**
     * 刷新列表点赞数
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        String msg = event.message;
        if (TextUtils.equals(msg, "refresh")) {
            smartRefreshLayout.autoRefresh(0);
        } else {
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
            try {
                browse = Integer.parseInt(dataList.get(position).getBrowse());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            dataList.get(position).setBrowse(String.valueOf(++browse));
            adapterWrapper.notifyItemChanged(position);
        }

    }

    @Override
    public void onGridItemClick(int listPosition, int position) {
        Intent intent = new Intent();
        intent.setClass(this, HuaTiContentActivity.class);
        intent.putExtra("position", listPosition);
        intent.putExtra("sid", dataList.get(listPosition).getSid());
        startActivity(intent);
    }

    @OnClick({R.id.tv_sel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_sel:
                MyDialog dialog = new MyDialog(this, R.style.MyDialogStyle);
                if (signList == null) {
                    doGetSigns();
                }

                int size = signList.size();
                final String[] arrays = new String[size];
                for (int i = 0; i < size; i++) {
                    arrays[i] = signList.get(i).getSignname();
                }
                dialog.setMyItems(arrays);
                dialog.setMyDialogItemClickListener(new MyDialog.MyDialogItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        tvSel.setText(arrays[position]);
                        type = signList.get(position).getSignid();
                        smartRefreshLayout.autoRefresh(200);
                    }
                });
                dialog.show();
                break;
        }
    }

    private void doGetSigns() {
        String url = Consts.BASE_URL + "/Said/public_saidsign";
        Map<String, String> params = new HashMap<>();
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (!TextUtils.equals(jsonObject.optString("code"), "0")) {
                        ToastUtils.getInstance().showToast(PartyMemberSQActivity.this, jsonObject.optString("msg"));
                        return;
                    }
                    JSONObject data = jsonObject.optJSONObject("data");
                    JSONArray sign = data.optJSONArray("sign");
                    int size = sign.length();
                    signList.clear();
                    signList.add(0,new SignBan("0","全部"));
                    for (int i = 0; i < size; i++) {
                        JSONObject item = sign.optJSONObject(i);
                        signList.add(new SignBan(item.optString("signid"), item.optString("signname")));
                    }
                    if(signList.size()>0){
                        type = signList.get(0).getSignid();
                        tvSel.setText(signList.get(0).getSignname());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    //ToastUtils.getInstance().showToast(PartyMemberSQActivity.this, "服务器异常~");
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    //ToastUtils.getInstance().showToast(PartyMemberSQActivity.this, "服务器异常~");
                }
                smartRefreshLayout.autoRefresh(200);
            }

            @Override
            public void onFailure(String errorMsg) {
                smartRefreshLayout.autoRefresh(200);
                ToastUtils.getInstance().showToast(PartyMemberSQActivity.this, "网络异常~");
            }
        });
    }
}
