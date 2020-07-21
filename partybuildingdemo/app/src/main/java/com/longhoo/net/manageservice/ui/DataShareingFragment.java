package com.longhoo.net.manageservice.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.longhoo.net.R;
import com.longhoo.net.manageservice.adapter.DataShareingAdapter;
import com.longhoo.net.manageservice.bean.DataShareingBean;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.utils.httprequest.HttpRequestPresenter;
import com.longhoo.net.utils.httprequest.HttpRequestView;
import com.longhoo.net.widget.HeaderAndFooterWrapper;
import com.longhoo.net.widget.base.BaseLazyFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class DataShareingFragment extends BaseLazyFragment implements OnRefreshLoadmoreListener, HttpRequestView,HeaderAndFooterWrapper.OnItemClickListener {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    private String url;
    private String type;
    private String title;
    private HttpRequestPresenter requestPresenter;
    private Activity mActivity;
    private int page;
    private HeaderAndFooterWrapper adapterWrapper;
    private List<DataShareingBean.DataBeanX.DataBean> dataList = new ArrayList<>();
    private DataShareingAdapter adapter;

    public static DataShareingFragment newInstance(String type,String title) {
        DataShareingFragment fragment = new DataShareingFragment();
        Bundle args = new Bundle();
        args.putString("type",type);
        args.putString("title",title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentId() {
        return R.layout.fragment_data_shareing;
    }

    @Override
    protected void onLazyLoad() {
        smartRefreshLayout.autoRefresh(200);
    }

    @Override
    protected void initViews(View view) {
        if (getArguments() != null) {
            type = getArguments().getString("type");
            title = getArguments().getString("title");
        }
        url = Consts.BASE_URL+"/Application/getShareFileList";
        mActivity = this.getActivity();
        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(mActivity));
        smartRefreshLayout.setRefreshFooter(new ClassicsFooter(mActivity));
        smartRefreshLayout.setOnRefreshLoadmoreListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(this.getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new DataShareingAdapter(mActivity, dataList);
        adapterWrapper = new HeaderAndFooterWrapper(adapter);
        adapterWrapper.setOnItemClickListener(this);
        recyclerView.setAdapter(adapterWrapper);
        requestPresenter = new HttpRequestPresenter(mActivity, this);
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected long setLoadInterval() {
        return 0;
    }

    @Override
    public void onNetworkError() {
        finishRefreshLoad();
        ToastUtils.getInstance().showToast(mActivity, "网络连接异常，请检查网络设置~");
    }

    @Override
    public void onRefreshSuccess(String response) {
        if (!isPrepared) {
            return;
        }
        ULog.e(Consts.TAG, type+" "+title+":" + response);
        //处理数据
        List<DataShareingBean.DataBeanX.DataBean> tempList = null;
        Gson gson = new Gson();
        try {
            DataShareingBean listBean = gson.fromJson(response, DataShareingBean.class);
            String code = listBean.getCode();
            if (!TextUtils.equals(code, "0")) {
                ToastUtils.getInstance().showToast(mActivity, "获取数据失败~");
                smartRefreshLayout.finishRefresh(0);
                return;
            }
            tempList = listBean.getData().getData();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            smartRefreshLayout.finishRefresh(0);
        }
        if (tempList == null) {
            ToastUtils.getInstance().showToast(mActivity, "暂无数据~");
            smartRefreshLayout.finishRefresh(0);
            return;
        }
        if (tempList.size() == 0) {
            ToastUtils.getInstance().showToast(mActivity, "暂无数据~");
            smartRefreshLayout.finishRefresh(0);
            return;
        }
        dataList.clear();
        if (tempList.size() > 0) {
            dataList.addAll(tempList);
           // page++;
        }
        adapterWrapper.notifyDataSetChanged();
        smartRefreshLayout.finishRefresh(0);
        smartRefreshLayout.setEnableLoadmore(false);
    }

    @Override
    public void onRefreshError() {
        ToastUtils.getInstance().showToast(mActivity, "刷新失败~");
        smartRefreshLayout.finishRefresh(0);
    }

    @Override
    public void onLoadMoreSuccess(String response) {
        if (!isPrepared) {
            return;
        }
        ULog.e(Consts.TAG, type+" "+title+" "+page+":" + response);
        //处理数据
        List<DataShareingBean.DataBeanX.DataBean> tempList2 = null;
        Gson gson = new Gson();
        try {
            DataShareingBean listBean = gson.fromJson(response, DataShareingBean.class);
            String code = listBean.getCode();
            if (!TextUtils.equals(code, "0")) {
                ToastUtils.getInstance().showToast(mActivity, "获取数据失败~");
                smartRefreshLayout.finishLoadmore(0);
                return;
            }
            tempList2 = listBean.getData().getData();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            smartRefreshLayout.finishLoadmore(0);
        }
        if (tempList2 == null) {
            ToastUtils.getInstance().showToast(mActivity, "暂无数据！");
            smartRefreshLayout.finishLoadmore(0);
            return;
        }
        //没有数据或数据<limit，说明无更多
        if (tempList2.size() <= 0) {
            ToastUtils.getInstance().showToast(mActivity, "没有更多了~");
            smartRefreshLayout.setEnableLoadmore(false);
        } else {
            dataList.addAll(tempList2);
            //page++;
            adapterWrapper.notifyDataSetChanged();
        }
        smartRefreshLayout.finishLoadmore(0);
        smartRefreshLayout.setEnableLoadmore(false);
    }

    @Override
    public void onLoadMoreError() {
        ToastUtils.getInstance().showToast(mActivity, "加载失败~");
        smartRefreshLayout.finishLoadmore(0);
    }

    @Override
    public void onItemClick(View view, int position) {
        if (position >= dataList.size()) {
            return;
        }
        String sid = dataList.get(position).getSid();
        DataShareingDetailActivity.goTo(getActivity(),sid);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
//        Map<String, String> params = new HashMap<>();
//        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
//        params.put("page",page+"");
//        params.put("type",type);
//        requestPresenter.doHttpData(url, Consts.REQUEST_METHOD_POST, Consts.REQUEST_LOADMORE, params);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        smartRefreshLayout.setEnableLoadmore(true);
        page = 1;
        Map<String,String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        //params.put("page",page+"");
        params.put("type",type);
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
}
