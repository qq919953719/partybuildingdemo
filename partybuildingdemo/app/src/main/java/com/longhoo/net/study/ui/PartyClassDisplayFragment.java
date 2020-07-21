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
import com.longhoo.net.study.adapter.PartyClassDisplayAdapter;
import com.longhoo.net.study.bean.PartyClassDisplayListBean;
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
import com.longhoo.net.widget.base.BaseLazyFragment;
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
import butterknife.Unbinder;

/**
 * 党课展示列表
 */
public class PartyClassDisplayFragment extends BaseLazyFragment implements OnRefreshLoadmoreListener, HttpRequestView,
        HeaderAndFooterWrapper.OnItemClickListener, PartyClassDisplayAdapter.OnZanClickListener {

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
    Unbinder unbinder;
    private String cid = "";
    private String url;
    private String title;
    private HttpRequestPresenter requestPresenter;
    private Activity mActivity;
    private int page;
    private HeaderAndFooterWrapper adapterWrapper;
    private List<PartyClassDisplayListBean.DataBean> dataList = new ArrayList<>();
    private PartyClassDisplayAdapter adapter;

    private static final int TAG_ORDER_TIME = 0;
    private static final int TAG_ORDER_ZAN = 1;
    private int curOrder = 0;

    public static PartyClassDisplayFragment newInstance(String cid, String title) {
        PartyClassDisplayFragment fragment = new PartyClassDisplayFragment();
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
        return R.layout.fragment_party_class_display;
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
        url = Consts.BASE_URL + "/ClassDisplay/getDisplayList";
        mActivity = this.getActivity();
        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(mActivity));
        smartRefreshLayout.setRefreshFooter(new ClassicsFooter(mActivity));
        smartRefreshLayout.setOnRefreshLoadmoreListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(this.getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new PartyClassDisplayAdapter(mActivity, dataList);
        //adapter.setOnZanClickListener(this);
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
        switch (view.getId()) {
            case R.id.sel_time_panel:
                tvSelTime.setTextColor(Color.parseColor("#e51f20"));
                ivSelTime.setImageResource(R.drawable.ic_tab_arrow_select);
                tvSelZan.setTextColor(Color.parseColor("#323232"));
                ivSelZan.setImageResource(R.drawable.ic_tab_arrow_unselect);
                if (curOrder != TAG_ORDER_TIME) {
                    curOrder = TAG_ORDER_TIME;
                    smartRefreshLayout.autoRefresh(0);
                }
                break;
            case R.id.sel_zan_panel:
                tvSelTime.setTextColor(Color.parseColor("#323232"));
                ivSelTime.setImageResource(R.drawable.ic_tab_arrow_unselect);
                tvSelZan.setTextColor(Color.parseColor("#e51f20"));
                ivSelZan.setImageResource(R.drawable.ic_tab_arrow_select);
                if (curOrder != TAG_ORDER_ZAN) {
                    curOrder = TAG_ORDER_ZAN;
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
        List<PartyClassDisplayListBean.DataBean> tempList = null;
        Gson gson = new Gson();
        try {
            PartyClassDisplayListBean listBean = gson.fromJson(response, PartyClassDisplayListBean.class);
            String code = listBean.getCode();
            if (!TextUtils.equals(code, "0")) {
                ToastUtils.getInstance().showToast(mActivity, "获取数据失败~");
                smartRefreshLayout.finishRefresh(0);
                return;
            }
            tempList = listBean.getData();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            smartRefreshLayout.finishRefresh(0);
        }
        dataList.clear();
        if (tempList != null && tempList.size() > 0) {
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
        List<PartyClassDisplayListBean.DataBean> tempList2 = null;
        Gson gson = new Gson();
        try {
            PartyClassDisplayListBean taiZhangListBean = gson.fromJson(response, PartyClassDisplayListBean.class);
            String code = taiZhangListBean.getCode();
            if (!TextUtils.equals(code, "0")) {
                ToastUtils.getInstance().showToast(mActivity, "获取数据失败~");
                smartRefreshLayout.finishLoadmore(0);
                return;
            }
            tempList2 = taiZhangListBean.getData();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            smartRefreshLayout.finishLoadmore(0);
        }
        if (tempList2 == null) {
            ToastUtils.getInstance().showToast(mActivity, "没有更多了~");
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
        int viewNum = dataList.get(position).getRed_num();
        dataList.get(position).setRed_num(++viewNum);
        adapterWrapper.notifyItemChanged(position);
        PartyVideoPlayActivity.startVideoPlay(getActivity(), String.valueOf(dataList.get(position).getAid()));
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("page", page + "");
        params.put("cid", cid);
        if (curOrder == TAG_ORDER_TIME) {
            params.put("order", "addtime desc");
        } else {
            params.put("order", "fab_num desc");
        }
        requestPresenter.doHttpData(url, Consts.REQUEST_METHOD_POST, Consts.REQUEST_LOADMORE, params);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        smartRefreshLayout.setEnableLoadmore(true);
        page = 1;
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("page", page + "");
        params.put("cid", cid);
        if (curOrder == TAG_ORDER_TIME) {
            params.put("order", "addtime desc");
        } else {
            params.put("order", "fab_num desc");
        }
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

    @Override
    public void onZanClick(int position) {
        if (position < 0 || position > dataList.size() - 1) {
            return;
        }
//        String url = Consts.BASE_URL + "/Classdisplay/setFabulous";
//        Map<String, String> params = new HashMap<>();
//        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
//        params.put("did", dataList.get(position).getAid() + "");
//        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
//            @Override
//            public void onSuccess(String response) {
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    String code = jsonObject.optString("code");
//                    String msg = jsonObject.optString("msg");
//                    ToastUtils.getInstance().showToast(getActivity(), msg);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(String errorMsg) {
//                ToastUtils.getInstance().showToast(getActivity(), "网络异常~");
//            }
        //      });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        int msgType = event.msgType;
        if (msgType == MessageEvent.MSG_REFRESH_LIST) {
            smartRefreshLayout.autoRefresh(200);
        }
    }
}
