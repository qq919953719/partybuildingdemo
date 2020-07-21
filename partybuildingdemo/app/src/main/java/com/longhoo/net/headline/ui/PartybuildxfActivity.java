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
import com.longhoo.net.MyApplication;
import com.longhoo.net.R;
import com.longhoo.net.headline.adapter.PartybuildxfAdapter;
import com.longhoo.net.headline.bean.PartybuildxfBean;
import com.longhoo.net.mine.ui.LoginActivity;
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

public class PartybuildxfActivity extends BaseActivity implements HttpRequestView, OnRefreshLoadmoreListener, HeaderAndFooterWrapper.OnItemClickListener, PartybuildxfAdapter.OnSupportClickListener {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    private PartybuildxfAdapter adapter;
    private HeaderAndFooterWrapper wrapperAdapter;
    private List<PartybuildxfBean.DataBean.NewsBean> dataList = new ArrayList<>();
    private HttpRequestPresenter requestPresenter;
    private String url = Consts.BASE_URL + "/News/public_pioneernews";
    private int mPage = 1;

    @Override
    protected int getContentId() {
        return R.layout.activity_partybuildxf;
    }

    @Override
    protected void initViews() {
        EventBus.getDefault().register(this);
        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(this));
        smartRefreshLayout.setRefreshFooter(new ClassicsFooter(this));
        smartRefreshLayout.setOnRefreshLoadmoreListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new PartybuildxfAdapter(this, dataList);
        adapter.setOnSupprotClickListener(this);
        wrapperAdapter = new HeaderAndFooterWrapper(adapter);
        wrapperAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(wrapperAdapter);
        requestPresenter = new HttpRequestPresenter(this, this);
        smartRefreshLayout.autoRefresh(200);
    }

    @Override
    protected void initToolbar() {
        tvTitle.setText("身边榜样");
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
        ULog.e(Consts.TAG, "身边榜样:" + response);
        //处理数据
        List<PartybuildxfBean.DataBean.NewsBean> tempList = null;
        Gson gson = new Gson();
        try {
            PartybuildxfBean customItemBean = gson.fromJson(response, PartybuildxfBean.class);
            String code = customItemBean.getCode();
            if (!TextUtils.equals(code, "0")) {
                ToastUtils.getInstance().showToast(this, "获取数据失败~");
                smartRefreshLayout.finishRefresh(0);
                return;
            }
            tempList = customItemBean.getData().getNews();
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
        ULog.e(Consts.TAG, "身边榜样:" + mPage + ":" + response);
        //处理数据
        List<PartybuildxfBean.DataBean.NewsBean> tempList2 = null;
        Gson gson = new Gson();
        try {
            PartybuildxfBean partybuildxfBean = gson.fromJson(response, PartybuildxfBean.class);
            String code = partybuildxfBean.getCode();
            if (!TextUtils.equals(code, "0")) {
                ToastUtils.getInstance().showToast(this, "获取数据失败~");
                smartRefreshLayout.finishLoadmore(0);
                return;
            }
            tempList2 = partybuildxfBean.getData().getNews();
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
        params.put("uid", EnterCheckUtil.getInstance().getUid_Token()[0]);
        params.put("page", mPage + "");
        requestPresenter.doHttpData(url, Consts.REQUEST_METHOD_POST, Consts.REQUEST_LOADMORE, params);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        smartRefreshLayout.setEnableLoadmore(true);
        mPage = 1;
        Map<String, String> params = new HashMap<>();
        params.put("uid", EnterCheckUtil.getInstance().getUid_Token()[0]);
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
        if (position >= dataList.size()) {
            return;
        }
        String nid = dataList.get(position).getNid();
        Intent intent = new Intent(this, PartybuildxfDetailActivity.class);
        intent.putExtra("news_position",position);
        intent.putExtra("news_nid", nid);
        startActivity(intent);
    }

    @Override
    public void OnSupportClick(boolean shouldSupport, final int position) {
        boolean isLogin = EnterCheckUtil.getInstance().isLogin(true);
        if(!isLogin){
            return;
        }
        String mSupportUrl = Consts.BASE_URL + "/News/log_pioneerzan";
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("nid", dataList.get(position).getNid());
        OkHttpUtil.getInstance().doAsyncPost(mSupportUrl, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                if(isFinishing()){
                    return;
                }
                try {
                    ULog.e("ck", "列表点赞：" + response);
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.optString("code");
                    String msg = jsonObject.optString("msg");
                    JSONObject data = jsonObject.optJSONObject("data");
                    String isZan = dataList.get(position).getZan();
                    if (TextUtils.equals(code, "0")) {
                        if (data != null) {
                            isZan = data.optString("iszan");
                        }
                        dataList.get(position).setIszan(isZan);
                        int zanCount=0;
                        try{
                            zanCount = Integer.parseInt(dataList.get(position).getZan());
                        }catch (NumberFormatException e){
                            e.printStackTrace();
                        }
                        ULog.e("ck",zanCount+"");
                        if(TextUtils.equals(isZan,"0")){
                            dataList.get(position).setZan(String.valueOf(--zanCount));
                        }else{
                            dataList.get(position).setZan(String.valueOf(++zanCount));
                        }
                    }
                    wrapperAdapter.notifyItemChanged(position);
                    ToastUtils.getInstance().showToast(PartybuildxfActivity.this, msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtils.getInstance().showToast(PartybuildxfActivity.this, "数据异常~");
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(PartybuildxfActivity.this, "网络异常~");
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        int position = event.position;
        String message = event.message;
        ULog.e("ck",message);
        if(TextUtils.equals(message,LoginActivity.REFRESH_TAG)){
            smartRefreshLayout.autoRefresh(500);//整个刷新
        }else{                            //单个item刷新
            if(position<0||position>dataList.size()-1){
                return;
            }
            dataList.get(position).setIszan(message);
            int zanCount=0;
            try{
                zanCount = Integer.parseInt(dataList.get(position).getZan());
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
            if(TextUtils.equals(message,"0")){
                dataList.get(position).setZan(String.valueOf(--zanCount));
            }else{
                dataList.get(position).setZan(String.valueOf(++zanCount));
            }
            wrapperAdapter.notifyItemChanged(position);
        }

    }
}
