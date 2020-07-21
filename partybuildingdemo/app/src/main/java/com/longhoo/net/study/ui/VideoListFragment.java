package com.longhoo.net.study.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.longhoo.net.R;
import com.longhoo.net.study.adapter.VideoListAdapter;
import com.longhoo.net.study.bean.StudyMenusBean;
import com.longhoo.net.study.bean.VideoListBean;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.utils.httprequest.HttpRequestPresenter;
import com.longhoo.net.utils.httprequest.HttpRequestView;
import com.longhoo.net.utils.httprequest.OkHttpCallback;
import com.longhoo.net.utils.httprequest.OkHttpUtil;
import com.longhoo.net.widget.HeaderAndFooterWrapper;
import com.longhoo.net.widget.banner.BannerEntity;
import com.longhoo.net.widget.banner.SlideBannerView;
import com.longhoo.net.widget.base.BaseLazyFragment;
import com.longhoo.net.widget.base.VideoPlayActivity;
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

public class VideoListFragment extends BaseLazyFragment implements OnRefreshLoadmoreListener, HttpRequestView, HeaderAndFooterWrapper.OnItemClickListener
        , SlideBannerView.BannerItemCLickListener {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    private StudyMenusBean.DataBean menusBean;
    private String url;
    private String title;
    private static final String STUDY_MENUSBEAN = "study_menusbean";
    private HttpRequestPresenter requestPresenter;
    private Activity mActivity;
    private int page;
    private HeaderAndFooterWrapper adapterWrapper;
    private List<VideoListBean.DataBean.ListBean> dataList = new ArrayList<>();
    private VideoListAdapter adapter;
    private List<VideoListBean.DataBean.ListBean> bannerList = new ArrayList<>();
    private SlideBannerView bannerView;

    public static VideoListFragment newInstance(StudyMenusBean.DataBean menusBean) {
        VideoListFragment fragment = new VideoListFragment();
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
        return R.layout.fragment_list_video;
    }

    @Override
    protected void onLazyLoad() {
        smartRefreshLayout.autoRefresh(200);
    }

    @Override
    protected void initViews(View view) {
        if (getArguments() != null) {
            menusBean = (StudyMenusBean.DataBean) getArguments().getSerializable(STUDY_MENUSBEAN);
            url = menusBean.getName();
            title = menusBean.getName();
        }
        //url = Consts.BASE_URL+"/Live/public_live_lists";
        mActivity = this.getActivity();
        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(mActivity));
        smartRefreshLayout.setRefreshFooter(new ClassicsFooter(mActivity));
        smartRefreshLayout.setOnRefreshLoadmoreListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(this.getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new VideoListAdapter(mActivity, dataList);
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
        ULog.e(Consts.TAG, title + ":" + response);
        //处理数据
        List<VideoListBean.DataBean.ListBean> tempList = null;
        Gson gson = new Gson();
        try {
            VideoListBean videoListBean = gson.fromJson(response, VideoListBean.class);
            int code = videoListBean.getCode();
            if (code != 0) {
                ToastUtils.getInstance().showToast(mActivity, "获取数据失败~");
                smartRefreshLayout.finishRefresh(0);
                return;
            }
            tempList = videoListBean.getData().getList();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            smartRefreshLayout.finishRefresh(0);
        }
        if (tempList == null) {
            ToastUtils.getInstance().showToast(mActivity, "服务器异常~");
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
        ULog.e(Consts.TAG, title + " " + page + ":" + response);
        //处理数据
        List<VideoListBean.DataBean.ListBean> tempList2 = null;
        Gson gson = new Gson();
        try {
            VideoListBean videoListBean = gson.fromJson(response, VideoListBean.class);
            int code = videoListBean.getCode();
            if (code != 0) {
                ToastUtils.getInstance().showToast(mActivity, "获取数据失败~");
                smartRefreshLayout.finishLoadmore(0);
                return;
            }
            tempList2 = videoListBean.getData().getList();
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
        params.put("showtype", "1");
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("page", page + "");
        requestPresenter.doHttpData(url, Consts.REQUEST_METHOD_POST, Consts.REQUEST_LOADMORE, params);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        //请求刷新列表数据
        smartRefreshLayout.setEnableLoadmore(true);
        page = 1;
        Map<String, String> params = new HashMap<>();
        params.put("showtype", "1");
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("page", page + "");
        requestPresenter.doHttpData(url, Consts.REQUEST_METHOD_POST, Consts.REQUEST_REFRESH, params);
        //获取轮播图数据
        doGetBanner();
    }

    @Override
    public void onItemClick(View view, int position) {
       itemClick(position,dataList);
    }

    private void itemClick(int position,List<VideoListBean.DataBean.ListBean> list){
        if (position >= list.size()) {
            return;
        }
        String id = list.get(position).getId();
        String type = list.get(position).getType();
        String thumb = list.get(position).getThumb();
        String des = list.get(position).getDescription();
        String title = list.get(position).getName();
        String zbUrl = list.get(position).getZburl();
        String isStart = list.get(position).getIstart();
        String isEnd = list.get(position).getIsend();
        //判断是图文直播还是预告、直播还是回顾
        int liveType,liveStyle;
        if(TextUtils.equals(type,"0")){
            liveType = VideoPlayActivity.LIVE_TYPE_PIC;
        }else{
            liveType = VideoPlayActivity.LIVE_TYPE_VIDEO;
        }
        if (TextUtils.equals(isStart, "0")) {
           liveStyle = VideoPlayActivity.STYLE_TRAILER;
        } else {
            if (TextUtils.equals(isEnd, "0")) {
                liveStyle = VideoPlayActivity.STYLE_LIVE;
            } else {
                liveStyle = VideoPlayActivity.STYLE_REVIEW;
            }
        }
        VideoPlayActivity.startVideoPlay(getActivity(), zbUrl, thumb, id, des,liveType,liveStyle, title);
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

    private void addRefreshHeader() {
        if (bannerView == null) {
            bannerView = new SlideBannerView(getActivity());
        }
        adapterWrapper.addHeaderView(bannerView);
        List<BannerEntity> bannerEntities = new ArrayList<>();
        for (int i = 0; i < bannerList.size(); i++) {
            bannerEntities.add(new BannerEntity(bannerList.get(i).getId(), bannerList.get(i).getName(), bannerList.get(i).getThumb(), ""));
        }
        ULog.e("ck", bannerEntities.size() + "数量");
        bannerView.setImagePath(bannerEntities);
        bannerView.setOnBannerItemClickListener(this);
        adapterWrapper.notifyDataSetChanged();
    }

    @Override
    public void onBannerItemClick(int position) {
        itemClick(position,bannerList);
    }

    private void doGetBanner() {
        Map<String, String> params = new HashMap<>();
        params.put("showtype", "2");
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("page", "1");
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                if(!isPrepared){
                    return;
                }
                ULog.e(Consts.TAG, title + " 轮播数据:" + response);
                //处理数据
                List<VideoListBean.DataBean.ListBean> tempList = null;
                Gson gson = new Gson();
                try {
                    VideoListBean videoListBean = gson.fromJson(response, VideoListBean.class);
                    int code = videoListBean.getCode();
                    tempList = videoListBean.getData().getList();
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
                if (tempList == null) {
                    return;
                }
                bannerList.clear();
                adapterWrapper.clearHeaderViews();
                if (tempList.size() > 0) {
                    bannerList.addAll(tempList);
                    addRefreshHeader();
                }
            }

            @Override
            public void onFailure(String errorMsg) {
            }
        });
    }
}
