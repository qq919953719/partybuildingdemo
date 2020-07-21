package com.longhoo.net.study.ui;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.longhoo.net.R;
import com.longhoo.net.study.adapter.VideoConAdapter;
import com.longhoo.net.study.bean.VideoConListBean;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.MessageEvent;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.utils.httprequest.HttpRequestPresenter;
import com.longhoo.net.utils.httprequest.HttpRequestView;
import com.longhoo.net.widget.HeaderAndFooterWrapper;
import com.longhoo.net.widget.base.BaseLazyFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 视频会议列表
 */
public class VideoConferenceFragment extends BaseLazyFragment implements OnRefreshLoadmoreListener, HttpRequestView, HeaderAndFooterWrapper.OnItemClickListener {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.tv_sel_time)
    TextView tvSelTime;
    @BindView(R.id.iv_sel_time)
    ImageView ivSelTime;
    @BindView(R.id.sel_time_panel)
    LinearLayout selTimePanel;
    @BindView(R.id.tv_sel_zan)
    TextView tvSelZan;
    @BindView(R.id.iv_sel_zan)
    ImageView ivSelZan;
    @BindView(R.id.sel_zan_panel)
    LinearLayout selZanPanel;
    private String type = ""; //1预告 2正在直播 3 往期回顾 4 全部
    private String url;
    private String title;
    private HttpRequestPresenter requestPresenter;
    private Activity mActivity;
    private int page;
    private int ordertype=1;// 1时间倒序 2点赞倒序
    private HeaderAndFooterWrapper adapterWrapper;
    private List<VideoConListBean.DataBean.ListBean> dataList = new ArrayList<>();
    private VideoConAdapter adapter;

    public static VideoConferenceFragment newInstance(String type, String title) {
        VideoConferenceFragment fragment = new VideoConferenceFragment();
        Bundle args = new Bundle();
        args.putString("type", type);
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
        return R.layout.fragment_video_conference;
    }

    @Override
    protected void onLazyLoad() {
        smartRefreshLayout.autoRefresh(200);
    }

    @Override
    protected void initViews(View view) {
        EventBus.getDefault().register(this);
        if (getArguments() != null) {
            type = getArguments().getString("type");
            title = getArguments().getString("title");
        }
        url = Consts.BASE_URL + "/live/live_lists.html";
        mActivity = this.getActivity();
        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(mActivity));
        smartRefreshLayout.setRefreshFooter(new ClassicsFooter(mActivity));
        smartRefreshLayout.setOnRefreshLoadmoreListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(this.getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new VideoConAdapter(mActivity, dataList);
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

    @OnClick({R.id.sel_time_panel, R.id.sel_zan_panel})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sel_time_panel:
                tvSelTime.setTextColor(Color.parseColor("#e51f20"));
                ivSelTime.setImageResource(R.drawable.ic_tab_arrow_select);
                tvSelZan.setTextColor(Color.parseColor("#323232"));
                ivSelZan.setImageResource(R.drawable.ic_tab_arrow_unselect);
                if (ordertype != 1) {
                    ordertype = 1;
                    smartRefreshLayout.autoRefresh(0);
                }
                break;
            case R.id.sel_zan_panel:
                tvSelTime.setTextColor(Color.parseColor("#323232"));
                ivSelTime.setImageResource(R.drawable.ic_tab_arrow_unselect);
                tvSelZan.setTextColor(Color.parseColor("#e51f20"));
                ivSelZan.setImageResource(R.drawable.ic_tab_arrow_select);
                if (ordertype != 2) {
                    ordertype = 2;
                    smartRefreshLayout.autoRefresh(0);
                }
                break;
        }
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
        ULog.e(Consts.TAG, " " + title + ":" + response);
        //处理数据
        List<VideoConListBean.DataBean.ListBean> tempList = null;
        Gson gson = new Gson();
        try {
            VideoConListBean listBean = gson.fromJson(response, VideoConListBean.class);
            String code = listBean.getCode();
            if (!TextUtils.equals(code,"0")) {
                ToastUtils.getInstance().showToast(mActivity, "获取数据失败~");
                smartRefreshLayout.finishRefresh(0);
                return;
            }
            tempList = listBean.getData().getList();
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
        ULog.e(Consts.TAG, title + " " + page + ":" + response);
        //处理数据
        List<VideoConListBean.DataBean.ListBean> tempList2 = null;
        Gson gson = new Gson();
        try {
            VideoConListBean videoConListBean = gson.fromJson(response, VideoConListBean.class);
            String code = videoConListBean.getCode();
            if (!TextUtils.equals(code,"0")) {
                ToastUtils.getInstance().showToast(mActivity, "获取数据失败~");
                smartRefreshLayout.finishLoadmore(0);
                return;
            }
            tempList2 = videoConListBean.getData().getList();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            smartRefreshLayout.finishLoadmore(0);
        }
        if (tempList2 == null) {
            ToastUtils.getInstance().showToast(mActivity, "暂无数据~");
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
        int viewNum = dataList.get(position).getHits();
        dataList.get(position).setHits(++viewNum);
        adapterWrapper.notifyItemChanged(position);
        VideoConDetailActivity.startVideoPlay(getActivity(),dataList.get(position).getId(),type);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("page", page + "");
        params.put("type",type);
        params.put("ordertype",ordertype+"");
        requestPresenter.doHttpData(url, Consts.REQUEST_METHOD_POST, Consts.REQUEST_LOADMORE, params);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        smartRefreshLayout.setEnableLoadmore(true);
        page = 1;
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("page", page + "");
        params.put("type",type);
        params.put("ordertype",ordertype+"");
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
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        int msgType = event.msgType;
        if (msgType == MessageEvent.MSG_REFRESH_LIST) {
            smartRefreshLayout.autoRefresh(200);
        }
    }
}
