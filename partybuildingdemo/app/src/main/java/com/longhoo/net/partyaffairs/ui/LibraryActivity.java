package com.longhoo.net.partyaffairs.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.longhoo.net.MyApplication;
import com.longhoo.net.R;
import com.longhoo.net.partyaffairs.adapter.LibraryAdapter;
import com.longhoo.net.partyaffairs.bean.LibraryBean;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.DisplayUtil;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.MessageEvent;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.utils.Utils;
import com.longhoo.net.utils.httprequest.HttpRequestPresenter;
import com.longhoo.net.utils.httprequest.HttpRequestView;
import com.longhoo.net.utils.httprequest.OkHttpCallback;
import com.longhoo.net.utils.httprequest.OkHttpUtil;
import com.longhoo.net.widget.GridSpacingItemDecoration;
import com.longhoo.net.widget.HeaderAndFooterWrapper;
import com.longhoo.net.widget.base.BaseActivity;
import com.longhoo.net.widget.base.WebViewActivity;
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
import butterknife.OnClick;

public class LibraryActivity extends BaseActivity implements HttpRequestView, OnRefreshLoadmoreListener, LibraryAdapter.OnExchangeClickListener,HeaderAndFooterWrapper.OnItemClickListener {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    private LibraryAdapter adapter;
    private HeaderAndFooterWrapper wrapperAdapter;
    private List<LibraryBean.DataBean> dataList = new ArrayList<>();
    private HttpRequestPresenter requestPresenter;
    private String url = Consts.BASE_URL + "/Application/libaryList";
    private AlertDialog alertDialog;
    private int mPage = 1;

    @Override
    protected int getContentId() {
        return R.layout.activity_library;
    }

    @Override
    protected void initViews() {
        EventBus.getDefault().register(this);
        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(this));
        smartRefreshLayout.setRefreshFooter(new ClassicsFooter(this));
        smartRefreshLayout.setOnRefreshLoadmoreListener(this);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, DisplayUtil.dp2px(this,10),false));
        adapter = new LibraryAdapter(this, dataList);
        adapter.setOnExchangeClickListener(this);
        wrapperAdapter = new HeaderAndFooterWrapper(adapter);
        recyclerView.setAdapter(wrapperAdapter);
        wrapperAdapter.setOnItemClickListener(this);
        requestPresenter = new HttpRequestPresenter(this, this);
        smartRefreshLayout.autoRefresh(200);
    }

    @Override
    protected void initToolbar() {
        tvTitle.setText("图书馆");
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
        ULog.e(Consts.TAG, "图书馆:" + response);
        //处理数据
        List<LibraryBean.DataBean> tempList = null;
        Gson gson = new Gson();
        try {
            LibraryBean libraryBean = gson.fromJson(response, LibraryBean.class);
            String code = libraryBean.getCode();
            if (!TextUtils.equals(code, "0")) {
                ToastUtils.getInstance().showToast(this, "获取数据失败~");
                smartRefreshLayout.finishRefresh(0);
                return;
            }
            tempList = libraryBean.getData();
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
        ULog.e(Consts.TAG, "图书馆:" + mPage + ":" + response);
        //处理数据
        List<LibraryBean.DataBean> tempList2 = null;
        Gson gson = new Gson();
        try {
            LibraryBean libraryBean = gson.fromJson(response, LibraryBean.class);
            String code = libraryBean.getCode();
            if (!TextUtils.equals(code, "0")) {
                ToastUtils.getInstance().showToast(this, "获取数据失败~");
                smartRefreshLayout.finishLoadmore(0);
                return;
            }
            tempList2 = libraryBean.getData();
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
        smartRefreshLayout.finishRefresh(0);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        smartRefreshLayout.setEnableLoadmore(true);
        mPage = 1;
        Map<String, String> params = new HashMap<>();
        params.put("uid", EnterCheckUtil.getInstance().getUid_Token()[0]);
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("page",mPage+"");
        requestPresenter.doHttpData(url, Consts.REQUEST_METHOD_POST, Consts.REQUEST_REFRESH, params);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        Map<String, String> params = new HashMap<>();
        params.put("uid", EnterCheckUtil.getInstance().getUid_Token()[0]);
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("page",mPage+"");
        requestPresenter.doHttpData(url, Consts.REQUEST_METHOD_POST, Consts.REQUEST_LOADMORE, params);
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
        hideDialog();
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onExchangeClick(int position) {
        if (position >= dataList.size()) {
            return;
        }
        onItemClick(null,position);
        //showAlertDialog(position);
    }

    private void showAlertDialog(final int position) {
        final String gid = dataList.get(position).getGid();
        final String thumb = dataList.get(position).getThumb();
        final String title = dataList.get(position).getGname();
        final String score = dataList.get(position).getScore();
        alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.alert_commit);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = Utils.getDeviceSize(this).x * 650 / 750;
        window.setAttributes(params);
        TextView tvContent = (TextView) alertDialog.findViewById(R.id.tv_alert_content);
        TextView tvCancle = (TextView) alertDialog.findViewById(R.id.tv_alert_cancle);
        TextView tvCommit = (TextView) alertDialog.findViewById(R.id.tv_alert_commit);
        tvContent.setText("确定使用" + score + "积分借阅" + title + "吗");
        tvCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideDialog();
            }
        });
        tvCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideDialog();
                doExchange(position,gid, thumb, title, score);
            }
        });
    }

    private void hideDialog() {
        if (alertDialog != null) {
            if (alertDialog.isShowing()) {
                alertDialog.dismiss();
            }
            alertDialog = null;
        }
    }

    private void doExchange(final int position, String gid, String thumb, String gName, String score) {
        String url = Consts.BASE_URL + "/Application/libaryBorrow";
        Map<String, String> params = new HashMap<>();
        params.put("uid", EnterCheckUtil.getInstance().getUid_Token()[0]);
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("gid", gid);
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                if(isFinishing()){
                    return;
                }
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.optString("code");
                    String msg = jsonObject.optString("msg");
                    ToastUtils.getInstance().showToast(LibraryActivity.this, msg);
                    if(TextUtils.equals(code,"0")){
                        dataList.get(position).setType("3");
                        wrapperAdapter.notifyItemChanged(position);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtils.getInstance().showToast(LibraryActivity.this, "服务器异常~");
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(LibraryActivity.this, "网络异常~");
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        if (position < 0 || position > dataList.size() - 1) {
            return;
        }
        String gid = dataList.get(position).getGid();
        Intent intent = new Intent(this,LibraryDetailActivity.class);
        intent.putExtra("library_position",position);
        intent.putExtra("library_gid",gid);
        startActivity(intent);
    }

    @OnClick({R.id.tv_rule})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_rule:
                WebViewActivity.goToWebView(this,Consts.BASE_URL+"/Other/public_book","借阅规则",false);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        int position = event.position;
        String type = event.message;
        if(position<0||position>dataList.size()-1){
            return;
        }
        dataList.get(position).setType(type);
        wrapperAdapter.notifyItemChanged(position);
    }
}
