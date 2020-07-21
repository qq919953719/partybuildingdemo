package com.longhoo.net.mine.ui;

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
import com.longhoo.net.mine.adapter.MyStudyArchivesAdapter;
import com.longhoo.net.mine.bean.MyStudyArchivesBean;
import com.longhoo.net.mine.bean.MyStudyArchivesMenuBean;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.SwipeItemLayout;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的——我的学习档案
 */
public class MyStudyArchivesActivity extends BaseActivity implements HttpRequestView, OnRefreshLoadmoreListener,
        HeaderAndFooterWrapper.OnItemClickListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_sel_year)
    TextView tvSelYear;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.tv_all_count)
    TextView tvAllCount;
    private MyStudyArchivesAdapter adapter;
    private HeaderAndFooterWrapper wrapperAdapter;
    private List<MyStudyArchivesBean.DataBeanX.DataBean.ListBean> dataList = new ArrayList<>();
    private List<String> yearList = new ArrayList<>();
    private HttpRequestPresenter requestPresenter;
    private String url = Consts.BASE_URL + "/Study/myArchives";
    private int mPage = 1;
    private String mCurYear = "";

    @Override
    protected int getContentId() {
        return R.layout.activity_my_study_archives;
    }

    @Override
    protected void initViews() {
        refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this));
        refreshLayout.setOnRefreshLoadmoreListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new MyStudyArchivesAdapter(this, dataList);
        wrapperAdapter = new HeaderAndFooterWrapper(adapter);
        wrapperAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(wrapperAdapter);
        recyclerView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(this));
        requestPresenter = new HttpRequestPresenter(this, this);
        getYearData();
    }

    @Override
    protected void initToolbar() {
        tvTitle.setText("我的学习档案");
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
        ULog.e(Consts.TAG, "我的学习档案列表:" + response);
        //处理数据
        List<MyStudyArchivesBean.DataBeanX.DataBean.ListBean> tempList = null;
        Gson gson = new Gson();
        try {
            MyStudyArchivesBean bean = gson.fromJson(response, MyStudyArchivesBean.class);
            String code = bean.getCode();
            if (!TextUtils.equals(code, "0")) {
                ToastUtils.getInstance().showToast(this, "获取数据失败~");
                refreshLayout.finishRefresh(0);
                return;
            }
            tempList = bean.getData().getData().getList();
            tvAllCount.setText("审核通过的学时："+bean.getData().getData().getCount()+"");
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            refreshLayout.finishRefresh(0);
        }
        dataList.clear();
        if (tempList != null && tempList.size() > 0) {
            dataList.addAll(tempList);
            mPage++;
        }
        wrapperAdapter.notifyDataSetChanged();
        refreshLayout.finishRefresh(0);
    }

    @Override
    public void onRefreshError() {
        ToastUtils.getInstance().showToast(this, "刷新失败~");
        refreshLayout.finishRefresh(0);
    }

    @Override
    public void onLoadMoreSuccess(String response) {
        ULog.e(Consts.TAG, "我的学习档案列表:" + mPage + ":" + response);
        //处理数据
        List<MyStudyArchivesBean.DataBeanX.DataBean.ListBean> tempList2 = null;
        Gson gson = new Gson();
        try {
            MyStudyArchivesBean bean = gson.fromJson(response, MyStudyArchivesBean.class);
            String code = bean.getCode();
            if (!TextUtils.equals(code, "0")) {
                ToastUtils.getInstance().showToast(this, "获取数据失败~");
                refreshLayout.finishLoadmore(0);
                return;
            }
            tempList2 = bean.getData().getData().getList();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            refreshLayout.finishLoadmore(0);
        }
        if (tempList2 == null) {
            ToastUtils.getInstance().showToast(this, "暂无数据~");
            refreshLayout.finishLoadmore(0);
            return;
        }
        //没有数据或数据<limit，说明无更多
        if (tempList2.size() <= 0) {
            ToastUtils.getInstance().showToast(this, "没有更多了~");
            refreshLayout.setEnableLoadmore(false);
        } else {
            dataList.addAll(tempList2);
            mPage++;
            wrapperAdapter.notifyDataSetChanged();
        }
        refreshLayout.finishLoadmore(0);
    }

    @Override
    public void onLoadMoreError() {
        ToastUtils.getInstance().showToast(this, "加载失败~");
        refreshLayout.finishLoadmore(0);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        Map<String, String> params = new HashMap<>();
        params.put("uid", EnterCheckUtil.getInstance().getUid_Token()[0]);
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("page", mPage + "");
        params.put("pagesize", "20");
        params.put("year", mCurYear + "");
        requestPresenter.doHttpData(url, Consts.REQUEST_METHOD_POST, Consts.REQUEST_LOADMORE, params);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        refreshLayout.setEnableLoadmore(true);
        mPage = 1;
        Map<String, String> params = new HashMap<>();
        params.put("uid", EnterCheckUtil.getInstance().getUid_Token()[0]);
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("page", mPage + "");
        params.put("pagesize", "20");
        params.put("year", mCurYear + "");
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
    public void onDestroy() {
        finishRefreshLoad();
        super.onDestroy();
    }

    @OnClick({R.id.tv_sel_year})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_sel_year:
                MyDialog dialog = new MyDialog(MyStudyArchivesActivity.this, R.style.MyDialogStyle);
                if (yearList == null) {
                    return;
                }
                int size = yearList.size();
                final String[] arrays = new String[size];
                for (int i = 0; i < size; i++) {
                    arrays[i] = yearList.get(i);
                }
                dialog.setMyItems(arrays);
                dialog.setMyDialogItemClickListener(new MyDialog.MyDialogItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        mCurYear = yearList.get(position);
                        tvSelYear.setText(arrays[position]);
                        refreshLayout.autoRefresh(200);
                    }
                });
                dialog.show();
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        if (position < 0 || position > dataList.size() - 1) {
            return;
        }
    }

    private void getYearData() {
        String url = Consts.BASE_URL + "/Study/yearSelect";
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                List<String> list = null;
                Gson gson = new Gson();
                try {
                    MyStudyArchivesMenuBean bean = gson.fromJson(response, MyStudyArchivesMenuBean.class);
                    int code = bean.getCode();
                    if (code != 0) {
                        ToastUtils.getInstance().showToast(MyStudyArchivesActivity.this, "获取数据失败~");
                        return;
                    }
                    list = bean.getData();
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
                yearList.clear();
                if (list != null && list.size() > 0) {
                    yearList.addAll(list);
                    mCurYear = yearList.get(0);
                    tvSelYear.setText(mCurYear);
                }
                refreshLayout.autoRefresh(200);
            }

            @Override
            public void onFailure(String errorMsg) {
                refreshLayout.autoRefresh(200);
                ToastUtils.getInstance().showToast(MyStudyArchivesActivity.this, "网络错误~");
            }
        });
    }

}
