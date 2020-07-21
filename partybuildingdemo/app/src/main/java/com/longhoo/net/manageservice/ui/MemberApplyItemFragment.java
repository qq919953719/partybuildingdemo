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
import com.longhoo.net.manageservice.adapter.MemberPunlicAdapter;
import com.longhoo.net.manageservice.bean.MemberPublicListBean;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.MessageEvent;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.httprequest.HttpRequestPresenter;
import com.longhoo.net.utils.httprequest.HttpRequestView;
import com.longhoo.net.widget.HeaderAndFooterWrapper;
import com.longhoo.net.widget.base.BaseLazyFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 组织生活子页面
 */
public class MemberApplyItemFragment extends BaseLazyFragment implements OnRefreshLoadmoreListener, HttpRequestView, HeaderAndFooterWrapper.OnItemClickListener {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    private static final String TAIZHANG_MENUSBEAN = "taizhang_menusbean";
    private String url;
    private String cid;
    private String title;
    private HttpRequestPresenter requestPresenter;
    private Activity mActivity;
    private int page;
    private HeaderAndFooterWrapper adapterWrapper;
    private List<MemberPublicListBean.DataBean.ListBean> dataList = new ArrayList<>();
    private MemberPunlicAdapter adapter;

    public static MemberApplyItemFragment newInstance(String cid, String title) {
        MemberApplyItemFragment fragment = new MemberApplyItemFragment();
        Bundle args = new Bundle();
        args.putString("cid", cid);
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentId() {

        return R.layout.fragment_tai_zhang_item;
    }

    @Override
    protected void onLazyLoad() {
        smartRefreshLayout.autoRefresh(200);
    }

    @Override
    protected void initViews(View view) {
        EventBus.getDefault().register(this);
        if (getArguments() != null) {
            cid = getArguments().getString("cid");
            title = getArguments().getString("title");
        }
        url = Consts.BASE_URL + "/apply/public_live_lists";
        mActivity = this.getActivity();
        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(mActivity));
        smartRefreshLayout.setRefreshFooter(new ClassicsFooter(mActivity));
        smartRefreshLayout.setOnRefreshLoadmoreListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(this.getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new MemberPunlicAdapter(mActivity, dataList);
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
        //处理数据
        List<MemberPublicListBean.DataBean.ListBean> tempList = null;
        Gson gson = new Gson();
        try {
            MemberPublicListBean taiZhangListBean = gson.fromJson(response, MemberPublicListBean.class);
            String code = taiZhangListBean.getCode();
            if (!TextUtils.equals(code, "0")) {
                ToastUtils.getInstance().showToast(mActivity, "获取数据失败~");
                smartRefreshLayout.finishRefresh(0);
                return;
            }
            tempList = taiZhangListBean.getData().getList();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            smartRefreshLayout.finishRefresh(0);
        }
        if (tempList == null) {
            ToastUtils.getInstance().showToast(mActivity, "暂无数据~");
            smartRefreshLayout.finishRefresh(0);
            return;
        }
        dataList.clear();
        if (tempList.size() > 0) {
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
        if (!isPrepared) {
            return;
        }

        //处理数据
        List<MemberPublicListBean.DataBean.ListBean> tempList2 = null;
        Gson gson = new Gson();
        try {
            MemberPublicListBean taiZhangListBean = gson.fromJson(response, MemberPublicListBean.class);
            String code = taiZhangListBean.getCode();
            if (!TextUtils.equals(code, "0")) {
                ToastUtils.getInstance().showToast(mActivity, "获取数据失败~");
                smartRefreshLayout.finishLoadmore(0);
                return;
            }
            tempList2 = taiZhangListBean.getData().getList();
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
    public void onItemClick(View view, int position) {
        if (position >= dataList.size()) {
            return;
        }
//        String filePath = dataList.get(position).getFiles();
//        String fileName =dataList.get(position).getFilename();
//        String fileSize = dataList.get(position).getFileSize();
//       // WebViewActivity.goToWebView(getActivity(),"https://view.officeapps.live.com/op/view.aspx?src="+files,filename,false);
//        Intent intent = new Intent(getActivity(), OpenFileActivity.class);
//        intent.putExtra("file_path",filePath);
//        intent.putExtra("file_name",fileName);
//        intent.putExtra("file_size",fileSize);
        MemberPublicListBean.DataBean.ListBean tid = dataList.get(position);
        MemberPublicContentActivity.goTo(getActivity(), tid);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("page", page + "");
        params.put("tid", cid + "");
        params.put("pagesize", "20");
        requestPresenter.doHttpData(url, Consts.REQUEST_METHOD_POST, Consts.REQUEST_LOADMORE, params);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        smartRefreshLayout.setEnableLoadmore(true);
        page = 1;
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("page", page + "");
        params.put("tid", cid + "");
        params.put("pagesize", "20");
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        int msgType = event.msgType;
        if (msgType == MessageEvent.MSG_FRESH_STUDY_CLASS) {
            smartRefreshLayout.autoRefresh(200);
        }
    }

    @Override
    public void onDestroy() {
        finishRefreshLoad();
        super.onDestroy();
    }

    public void doRefresh() {
        smartRefreshLayout.autoRefresh(200);
    }
}
