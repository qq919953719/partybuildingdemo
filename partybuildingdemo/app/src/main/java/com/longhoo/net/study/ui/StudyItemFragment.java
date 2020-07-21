package com.longhoo.net.study.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.longhoo.net.MyApplication;
import com.longhoo.net.R;
import com.longhoo.net.headline.adapter.CustomItemAdapter;
import com.longhoo.net.headline.bean.CustomItemBean;
import com.longhoo.net.headline.bean.HeadlineBean;
import com.longhoo.net.headline.ui.NewsDetailActivity;
import com.longhoo.net.study.adapter.StudyItemAdapter;
import com.longhoo.net.study.bean.StudyItemBean;
import com.longhoo.net.study.bean.StudyMenusBean;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.utils.httprequest.HttpRequestPresenter;
import com.longhoo.net.utils.httprequest.HttpRequestView;
import com.longhoo.net.widget.HeaderAndFooterWrapper;
import com.longhoo.net.widget.banner.BannerEntity;
import com.longhoo.net.widget.banner.SlideBannerView;
import com.longhoo.net.widget.base.BaseLazyFragment;
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

public class StudyItemFragment extends BaseLazyFragment implements OnRefreshLoadmoreListener, HttpRequestView,HeaderAndFooterWrapper.OnItemClickListener
,SlideBannerView.BannerItemCLickListener{

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    private StudyMenusBean.DataBean menusBean;
    private String url;
    private int mid;
    private String title;
    private static final String STUDY_MENUSBEAN="study_menusbean";
    private HttpRequestPresenter requestPresenter;
    private Activity mActivity;
    private int page;
    private HeaderAndFooterWrapper adapterWrapper;
    private List<StudyItemBean.DataBean> dataList = new ArrayList<>();
    private StudyItemAdapter adapter;
    private SlideBannerView bannerView;
    //private List<HeadlineBean.DataBean.AdversBean> bannerList = new ArrayList<>();



    public static StudyItemFragment newInstance(StudyMenusBean.DataBean menusBean) {
        StudyItemFragment fragment = new StudyItemFragment();
        Bundle args = new Bundle();
        args.putSerializable(STUDY_MENUSBEAN, menusBean);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentId() {
        return R.layout.fragment_stydy_item;
    }

    @Override
    protected void onLazyLoad() {
        smartRefreshLayout.autoRefresh(200);
    }

    @Override
    protected void initViews(View view) {
        url = Consts.BASE_URL+"/Onlinestudy/public_study";
        if (getArguments() != null) {
            menusBean = (StudyMenusBean.DataBean) getArguments().getSerializable(STUDY_MENUSBEAN);
            mid = menusBean.getMid();
            title = menusBean.getName();
        }
        mActivity = this.getActivity();
        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(mActivity));
        smartRefreshLayout.setRefreshFooter(new ClassicsFooter(mActivity));
        smartRefreshLayout.setOnRefreshLoadmoreListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(this.getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new StudyItemAdapter(mActivity, dataList);
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
        List<StudyItemBean.DataBean> tempList = null;
        Gson gson = new Gson();
        try {
            StudyItemBean customItemBean = gson.fromJson(response, StudyItemBean.class);
            String code = customItemBean.getCode();
            if (!TextUtils.equals(code, "0")) {
                ToastUtils.getInstance().showToast(mActivity, "获取数据失败~");
                smartRefreshLayout.finishRefresh(0);
                return;
            }
            tempList = customItemBean.getData();
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
        if (!isPrepared) {
            return;
        }
        List<StudyItemBean.DataBean> tempList2 = null;
        Gson gson = new Gson();
        try {
            StudyItemBean customItemBean = gson.fromJson(response, StudyItemBean.class);
            String code = customItemBean.getCode();
            if (!TextUtils.equals(code, "0")) {
                ToastUtils.getInstance().showToast(mActivity, "获取数据失败~");
                smartRefreshLayout.finishLoadmore(0);
                return;
            }
            tempList2 = customItemBean.getData();
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
        params.put("page",page+"");
        params.put("mid",mid+"");
        params.put("pagesize","10");
        requestPresenter.doHttpData(url, Consts.REQUEST_METHOD_POST, Consts.REQUEST_LOADMORE, params);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        smartRefreshLayout.setEnableLoadmore(true);
        page = 1;
        Map<String,String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("page",page+"");
        params.put("mid",mid+"");
        params.put("pagesize","10");
        requestPresenter.doHttpData(url, Consts.REQUEST_METHOD_POST, Consts.REQUEST_REFRESH, params);
    }

    @Override
    public void onItemClick(View view, int position) {
        if (position >= dataList.size()) {
            return;
        }
        Intent intent = null;
        String nid = dataList.get(position).getNid()+"";
        intent = new Intent(mActivity, StudyItemDetailActivity.class);
        intent.putExtra("news_nid", nid);
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
    }

//    private void addRefreshHeader() {
//        if (bannerView == null) {
//            bannerView = new SlideBannerView(getActivity());
//            adapterWrapper.addHeaderView(bannerView);
//        }
//        List<BannerEntity> bannerEntities = new ArrayList<>();
//        for (int i = 0; i < bannerList.size(); i++) {
//            bannerEntities.add(new BannerEntity(bannerList.get(i).getTid(), bannerList.get(i).getTitle(), bannerList.get(i).getPic(), bannerList.get(i).getType()+""));
//        }
//        bannerView.setImagePath(bannerEntities);
//        bannerView.setOnBannerItemClickListener(this);
//    }

    @Override
    public void onBannerItemClick(int position) {

    }
}
