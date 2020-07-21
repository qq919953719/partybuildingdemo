package com.longhoo.net.study.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.longhoo.net.MyApplication;
import com.longhoo.net.R;
import com.longhoo.net.study.adapter.LiveDetailListAdapter;
import com.longhoo.net.study.bean.LiveDetailListBean;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.utils.httprequest.HttpRequestPresenter;
import com.longhoo.net.utils.httprequest.HttpRequestView;
import com.longhoo.net.widget.base.BaseLazyFragment;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;


/**
 * 直播详情页
 */
public class LiveDetailListFragment extends BaseLazyFragment implements HttpRequestView, LiveDetailListAdapter.OnGridItemClickListener,OnRefreshLoadmoreListener{
    private static final String TERM_ID = "term_id";
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private String mTermId;
    private int mPage = 1;
    private HttpRequestPresenter requestPresenter;
    private String url = Consts.BASE_URL+"/Live/liveinfo_lists";
    private LiveDetailListAdapter adapter;
    private List<LiveDetailListBean.DataBean.ListBean> dataList = new ArrayList<>();
    private LinearLayoutManager manager;
    boolean mFull = false;

    public static LiveDetailListFragment newInstance(String mTermId) {
        LiveDetailListFragment fragment = new LiveDetailListFragment();
        Bundle args = new Bundle();
        args.putString(TERM_ID, mTermId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTermId = getArguments().getString(TERM_ID);
        }
    }


    @Override
    protected int getContentId() {
        return R.layout.fragment_live_detail_list;
    }

    @Override
    protected void onLazyLoad() {
        refreshLayout.autoRefresh(200);
    }

    @Override
    protected void initViews(View view) {
        requestPresenter = new HttpRequestPresenter(getActivity(), this);
        adapter = new LiveDetailListAdapter(getActivity(), dataList);
        adapter.setOnGridItemClickListener(this);
        recyclerView.setAdapter(adapter);
        manager = new LinearLayoutManager(this.getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        refreshLayout.setOnRefreshLoadmoreListener(this);
//        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
//            @Override
//            public void onChildViewAttachedToWindow(View view) {
//            }
//
//            @Override
//            public void onChildViewDetachedFromWindow(View view) {
//                JZVideoPlayer jzvd = (JZVideoPlayer) view.findViewById(R.id.video_item_player);
//                if (jzvd != null && JZUtils.dataSourceObjectsContainsUri(jzvd.dataSourceObjects, JZMediaManager.getCurrentDataSource())) {
//                    JZVideoPlayer.releaseAllVideos();
//                }
//            }
//        });
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
        ToastUtils.getInstance().showToast(getActivity(), "网络异常~");
    }

    @Override
    public void onRefreshSuccess(String response) {
        if (!isPrepared) {
            return;
        }
        refreshLayout.setEnableLoadmore(true);
        ULog.e(Consts.TAG, "播放图文:" + response);
        //处理数据
        List<LiveDetailListBean.DataBean.ListBean> tempList = null;
        Gson gson = new Gson();
        try {
            LiveDetailListBean liveDetailListBean = gson.fromJson(response, LiveDetailListBean.class);
            String code = liveDetailListBean.getCode();
            if (!TextUtils.equals(code,"0")) {
                ToastUtils.getInstance().showToast(getActivity(), "获取数据失败~");
                refreshLayout.finishRefresh(0);
                return;
            }
            tempList = liveDetailListBean.getData().getList();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            refreshLayout.finishRefresh(0);
        }
        if (tempList == null) {
            ToastUtils.getInstance().showToast(getActivity(), "服务器异常~");
            refreshLayout.finishRefresh(0);
            return;
        }
        dataList.clear();
        if (tempList.size() > 0) {
            dataList.addAll(tempList);
            mPage++;
        }
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(recyclerView!=null&&!getActivity().isFinishing()){
                    recyclerView.scrollToPosition(0);
                }
            }
        },500);
        adapter.notifyDataSetChanged();
        refreshLayout.finishRefresh(0);
    }

    @Override
    public void onRefreshError() {
        ToastUtils.getInstance().showToast(getActivity(), "刷新失败~");
        refreshLayout.finishRefresh(0);
    }

    @Override
    public void onLoadMoreSuccess(String response) {
        if (!isPrepared) {
            return;
        }
        ULog.e(Consts.TAG, "播放图文:" + mPage + ":" + response);
        //处理数据
        List<LiveDetailListBean.DataBean.ListBean> tempList = null;
        Gson gson = new Gson();
        try {
            LiveDetailListBean liveDetailListBean = gson.fromJson(response, LiveDetailListBean.class);
            String code = liveDetailListBean.getCode();
            if (!TextUtils.equals(code,"0")) {
                ToastUtils.getInstance().showToast(getActivity(), "获取数据失败~");
                refreshLayout.finishLoadmore(0);
                return;
            }
            tempList = liveDetailListBean.getData().getList();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            refreshLayout.finishLoadmore(0);
        }
        if (tempList == null) {
            ToastUtils.getInstance().showToast(getActivity(), "服务器异常~");
            refreshLayout.finishLoadmore(0);
            return;
        }
        //没有数据或数据<limit，说明无更多
        if (tempList.size() <= 0) {
            refreshLayout.setEnableLoadmore(false);
        } else {
            dataList.addAll(tempList);
            mPage++;
            adapter.notifyDataSetChanged();
        }
        refreshLayout.finishLoadmore(0);
    }

    @Override
    public void onLoadMoreError() {
        ToastUtils.getInstance().showToast(getActivity(), "加载失败~");
        refreshLayout.finishLoadmore(0);
    }

    @Override
    public void onGridItemClick(int listPosition, int gridPosition) {
        if (listPosition >= 0 && listPosition <= dataList.size() - 1) {
            List<LocalMedia> photoList = new ArrayList<>();
            for (LiveDetailListBean.DataBean.ListBean.PhotosBean photosBean : dataList.get(listPosition).getPhotos()) {
                photoList.add(new LocalMedia(photosBean.getUrl(), 0, PictureMimeType.ofImage(), ""));
            }
            PictureSelector.create(this).externalPicturePreview(gridPosition, photoList);
        }
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("page", String.valueOf(mPage));
        params.put("lid", mTermId);
        requestPresenter.doHttpData(url, Consts.REQUEST_METHOD_POST, Consts.REQUEST_LOADMORE, params);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        refreshLayout.setEnableLoadmore(true);
        mPage = 1;
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("page", String.valueOf(mPage));
        params.put("lid", mTermId);
        requestPresenter.doHttpData(url, Consts.REQUEST_METHOD_POST, Consts.REQUEST_REFRESH, params);
    }
}
