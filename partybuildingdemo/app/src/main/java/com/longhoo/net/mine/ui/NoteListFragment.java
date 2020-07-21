package com.longhoo.net.mine.ui;

import android.content.Intent;
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
import com.longhoo.net.mine.adapter.NoteListAdapter;
import com.longhoo.net.mine.bean.NoteListBean;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.utils.httprequest.HttpRequestPresenter;
import com.longhoo.net.utils.httprequest.HttpRequestView;
import com.longhoo.net.utils.httprequest.OkHttpCallback;
import com.longhoo.net.utils.httprequest.OkHttpUtil;
import com.longhoo.net.widget.HeaderAndFooterWrapper;
import com.longhoo.net.widget.base.BaseLazyFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class NoteListFragment extends BaseLazyFragment implements HttpRequestView, OnRefreshLoadmoreListener,
        NoteListAdapter.OnItemClickListener, NoteListAdapter.OnDelClickListener {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    private NoteListAdapter adapter;
    private HeaderAndFooterWrapper wrapperAdapter;
    private List<NoteListBean.DataBean.BijiBean> dataList = new ArrayList<>();
    private HttpRequestPresenter requestPresenter;
    private String url = Consts.BASE_URL + "/Application/getBiJiList";
    private int mPage = 1;
    private int noteType;

    public static NoteListFragment getInstance(int type) {
        NoteListFragment fragment = new NoteListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("note_type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentId() {
        return R.layout.fragment_note_list;
    }

    @Override
    protected void onLazyLoad() {
        smartRefreshLayout.autoRefresh(200);
    }

    @Override
    protected void initViews(View view) {
        if (getArguments() != null) {
            noteType = getArguments().getInt("note_type");
        }
        if (noteType == NoteListActivity.TAG_MY_NOTE) {
            url = Consts.BASE_URL + "/Application/getBiJiList";
            // recyclerView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(getActivity()));
        } else {
            url = Consts.BASE_URL + "/Application/getSharedNotes";
        }
        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        smartRefreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));
        smartRefreshLayout.setOnRefreshLoadmoreListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new NoteListAdapter(getActivity(), dataList, noteType);
        adapter.setOnDelClickListener(this);
        adapter.setOnItemClickListener(this);
        wrapperAdapter = new HeaderAndFooterWrapper(adapter);
        recyclerView.setAdapter(wrapperAdapter);
        requestPresenter = new HttpRequestPresenter(getActivity(), this);
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
        ToastUtils.getInstance().showToast(getActivity(), "网络连接异常，请检查网络设置~");
    }

    @Override
    public void onRefreshSuccess(String response) {
        ULog.e(Consts.TAG, "学习笔记:" + response);
        //处理数据
        List<NoteListBean.DataBean.BijiBean> tempList = null;
        Gson gson = new Gson();
        String msg = "";
        try {
            NoteListBean bean = gson.fromJson(response, NoteListBean.class);
            String code = bean.getCode();
            msg = bean.getMsg();
            if (!TextUtils.equals(code, "0")) {
                ToastUtils.getInstance().showToast(getActivity(), msg);
                smartRefreshLayout.finishRefresh(0);
                return;
            }
            tempList = bean.getData().getBiji();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            smartRefreshLayout.finishRefresh(0);
        }
        dataList.clear();
        if (tempList != null && tempList.size() > 0) {
            dataList.addAll(tempList);
            mPage++;
        }
        wrapperAdapter.notifyDataSetChanged();
        smartRefreshLayout.finishRefresh(0);
    }

    @Override
    public void onRefreshError() {
        ToastUtils.getInstance().showToast(getActivity(), "刷新失败~");
        smartRefreshLayout.finishRefresh(0);
    }

    @Override
    public void onLoadMoreSuccess(String response) {
        ULog.e(Consts.TAG, "学习笔记:" + mPage + ":" + response);
        //处理数据
        List<NoteListBean.DataBean.BijiBean> tempList2 = null;
        Gson gson = new Gson();
        String msg = "";
        try {
            NoteListBean bean = gson.fromJson(response, NoteListBean.class);
            String code = bean.getCode();
            msg = bean.getMsg();
            if (!TextUtils.equals(code, "0")) {
                ToastUtils.getInstance().showToast(getActivity(), msg);
                smartRefreshLayout.finishLoadmore(0);
                return;
            }
            tempList2 = bean.getData().getBiji();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            smartRefreshLayout.finishLoadmore(0);
        }
        if (tempList2 == null) {
            ToastUtils.getInstance().showToast(getActivity(), msg);
            smartRefreshLayout.finishLoadmore(0);
            return;
        }
        //没有数据或数据<limit，说明无更多
        if (tempList2.size() <= 0) {
            ToastUtils.getInstance().showToast(getActivity(), "没有更多了~");
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
        ToastUtils.getInstance().showToast(getActivity(), "加载失败~");
        smartRefreshLayout.finishLoadmore(0);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        Map<String, String> params = new HashMap<>();
        params.put("uid", EnterCheckUtil.getInstance().getUid_Token()[0]);
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("page", mPage + "");
        if (NoteListActivity.type == 0) {
            params.put("type", "1");

        }
        if (NoteListActivity.type == 1) {
            params.put("type", "0");
        }
        requestPresenter.doHttpData(url, Consts.REQUEST_METHOD_POST, Consts.REQUEST_LOADMORE, params);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        smartRefreshLayout.setEnableLoadmore(true);
        mPage = 1;
        Map<String, String> params = new HashMap<>();
        params.put("uid", EnterCheckUtil.getInstance().getUid_Token()[0]);
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("page", mPage + "");
        if (NoteListActivity.type == 0) {
            params.put("type", "1");

        }
        if (NoteListActivity.type == 1) {
            params.put("type", "0");
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
        super.onDestroy();
    }

    @Override
    public void onItemClick(int position) {
        if (position < 0 || position > dataList.size() - 1) {
            return;
        }
        Intent intent = new Intent(getActivity(), NoteDetailActivity.class);
        intent.putExtra("note_id", dataList.get(position).getId());
        intent.putExtra("note_type", noteType);
        startActivity(intent);
    }

    @Override
    public void onDelClick(final int position) {
        if (position < 0 || position > dataList.size() - 1)
            return;
        String url = Consts.BASE_URL + "/Application/deleteBiJiById";
        String id = dataList.get(position).getId();
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        params.put("uid", EnterCheckUtil.getInstance().getUid_Token()[0]);
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.optString("code");
                    String msg = jsonObject.optString("msg");
                    if (TextUtils.equals(code, "0")) {
                        //成功,删除该条数据
                        dataList.remove(position);
                        wrapperAdapter.notifyDataSetChanged();
                    } else {
                        ToastUtils.getInstance().showToast(getActivity(), msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtils.getInstance().showToast(getActivity(), "服务器异常~");
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(getActivity(), "网络异常~");
            }
        });
    }

    public void refresh() {
        smartRefreshLayout.autoRefresh(200);
    }

}
