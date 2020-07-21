package com.longhoo.net.supervision.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.longhoo.net.R;
import com.longhoo.net.partyaffairs.bean.TaiZhangMenuBean;
import com.longhoo.net.supervision.adapter.ActSituationAdapter;
import com.longhoo.net.supervision.bean.ActSituationBean;
import com.longhoo.net.supervision.bean.ZhibuOrgBean;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.utils.httprequest.HttpRequestPresenter;
import com.longhoo.net.utils.httprequest.HttpRequestView;
import com.longhoo.net.utils.httprequest.OkHttpCallback;
import com.longhoo.net.utils.httprequest.OkHttpUtil;
import com.longhoo.net.widget.MyDialog;
import com.longhoo.net.widget.base.BaseLazyFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 支部党建活动情况
 */
public class ActsSituationFragment extends BaseLazyFragment implements HttpRequestView, OnRefreshLoadmoreListener {

    @BindView(R.id.tv_sel_1)
    TextView tvSel1;
    @BindView(R.id.tv_sel_2)
    TextView tvSel2;
    @BindView(R.id.tv_sel_3)
    TextView tvSel3;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    private HttpRequestPresenter requestPresenter;
    private List<ActSituationBean.DataBean> dataList = new ArrayList<>();
    private List<ZhibuOrgBean.DataBean> zhibuOrgList = new ArrayList<>();
    private List<TaiZhangMenuBean.DataBean> partyTypeList = new ArrayList<>();
    private ActSituationAdapter adapter;
    private MyDialog dialog;
    private String[] zhibuArrays;
    private String[] monthArrays;
    private int mPage = 1;
    private int mOid;
    private int mType = 2; //1本周 2本月 3本季度 4本年
    private int mPartyType;
    private String url = Consts.BASE_URL + "/Census/getList";
    private String zhiBuUrl = Consts.BASE_URL + "/Census/getSelectOrg";

    @Override
    protected int getContentId() {
        return R.layout.fragment_acts_situation;
    }

    @Override
    protected void onLazyLoad() {
        getZhibu();
        getPartyType();
        smartRefreshLayout.autoRefresh(1000);
    }

    @Override
    protected void initViews(View view) {
        requestPresenter = new HttpRequestPresenter(getActivity(), this);
        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        smartRefreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));
        smartRefreshLayout.setOnRefreshLoadmoreListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ActSituationAdapter(getActivity(), dataList);
        recyclerView.setAdapter(adapter);
    }

    @OnClick({R.id.tv_sel_1, R.id.tv_sel_2, R.id.tv_sel_3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_sel_1:
                if (dialog != null) {
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    dialog = null;
                }
                if (zhibuArrays == null) {
                    getZhibu();
                    return;
                }
                dialog = new MyDialog(getActivity(), R.style.MyDialogStyle);
                dialog.setMyItems(zhibuArrays);
                dialog.setMyDialogItemClickListener(new MyDialog.MyDialogItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        tvSel1.setText(zhibuOrgList.get(position).getName());
                        mOid = zhibuOrgList.get(position).getOid();
                        smartRefreshLayout.autoRefresh(200);
                    }
                });
                dialog.show();
                break;
            case R.id.tv_sel_2:
                if (dialog != null) {
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    dialog = null;
                }
                dialog = new MyDialog(getActivity(), R.style.MyDialogStyle);
                monthArrays = new String[]{"本月", "本季度", "本年度"};
                dialog.setMyItems(monthArrays);
                dialog.setMyDialogItemClickListener(new MyDialog.MyDialogItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (position == 0) {
                            tvSel2.setText("本月");
                            mType = 2;
                        } else if (position == 1) {
                            tvSel2.setText("本季度");
                            mType = 3;
                        } else if (position == 2) {
                            tvSel2.setText("本年度");
                            mType = 4;
                        }
                        smartRefreshLayout.autoRefresh(200);
                    }
                });
                dialog.show();
                break;
            case R.id.tv_sel_3:
                if (dialog != null) {
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    dialog = null;
                }
                dialog = new MyDialog(getActivity(), R.style.MyDialogStyle);
                String[] arrays = new String[partyTypeList.size()];
                for (int i = 0; i < partyTypeList.size(); i++) {
                    arrays[i] = partyTypeList.get(i).getName();
                }
                dialog.setMyItems(arrays);
                dialog.setMyDialogItemClickListener(new MyDialog.MyDialogItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        tvSel3.setText(partyTypeList.get(position).getName());
                        mPartyType = partyTypeList.get(position).getCid();
                        smartRefreshLayout.autoRefresh(200);
                    }
                });
                dialog.show();
                break;
        }
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
        List<ActSituationBean.DataBean> tempList = null;
        Gson gson = new Gson();
        try {
            ActSituationBean actSituationBean = gson.fromJson(response, ActSituationBean.class);
            String code = actSituationBean.getCode();
            if (!TextUtils.equals(code, "0")) {
                ToastUtils.getInstance().showToast(getActivity(), "获取数据失败~");
                smartRefreshLayout.finishRefresh(0);
                return;
            }
            tempList = actSituationBean.getData();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            smartRefreshLayout.finishRefresh(0);
        }
        dataList.clear();
        if (tempList != null && tempList.size() > 0) {
            dataList.addAll(tempList);
            mPage++;
        }
        adapter.notifyDataSetChanged();
        smartRefreshLayout.finishRefresh(0);
    }

    @Override
    public void onRefreshError() {
        ToastUtils.getInstance().showToast(getActivity(), "刷新失败~");
        smartRefreshLayout.finishRefresh(0);
    }

    @Override
    public void onLoadMoreSuccess(String response) {
        List<ActSituationBean.DataBean> tempList2 = null;
        Gson gson = new Gson();
        try {
            ActSituationBean actSituationBean = gson.fromJson(response, ActSituationBean.class);
            String code = actSituationBean.getCode();
            if (!TextUtils.equals(code, "0")) {
                ToastUtils.getInstance().showToast(getActivity(), "获取数据失败~");
                smartRefreshLayout.finishLoadmore(0);
                return;
            }
            tempList2 = actSituationBean.getData();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            smartRefreshLayout.finishLoadmore(0);
        }
        if (tempList2 == null) {
            ToastUtils.getInstance().showToast(getActivity(), "解析错误！");
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
            adapter.notifyDataSetChanged();
        }
        smartRefreshLayout.finishLoadmore(0);
    }

    @Override
    public void onLoadMoreError() {
        ToastUtils.getInstance().showToast(getActivity(), "加载失败~");
        smartRefreshLayout.finishLoadmore(0);
    }

    int mPagesize = 20;

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("oid", mOid + "");
        params.put("type", mType + "");
        params.put("page", mPage + "");
        params.put("cid", mPartyType + "");
        params.put("pagesize", mPagesize + "");

        requestPresenter.doHttpData(url, Consts.REQUEST_METHOD_POST, Consts.REQUEST_LOADMORE, params);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        smartRefreshLayout.setEnableLoadmore(true);
        mPage = 1;
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("oid", mOid + "");
        params.put("type", mType + "");
        params.put("page", mPage + "");
        params.put("cid", mPartyType + "");
        params.put("pagesize", mPagesize + "");
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

    private void getZhibu() {
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        OkHttpUtil.getInstance().doAsyncPost(zhiBuUrl, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                List<ZhibuOrgBean.DataBean> tempList = null;
                Gson gson = new Gson();
                try {
                    ZhibuOrgBean actSituationBean = gson.fromJson(response, ZhibuOrgBean.class);
                    String code = actSituationBean.getCode();
                    if (!TextUtils.equals(code, "0")) {
                        ToastUtils.getInstance().showToast(getActivity(), "获取数据失败~");
                        return;
                    }
                    tempList = actSituationBean.getData();
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
                zhibuOrgList.clear();
                if (tempList != null && tempList.size() > 0) {
                    zhibuOrgList.addAll(tempList);
                    int size = zhibuOrgList.size();
                    zhibuArrays = new String[size];
                    for (int i = 0; i < size; i++) {
                        zhibuArrays[i] = zhibuOrgList.get(i).getName();
                    }
                    mOid = zhibuOrgList.get(0).getOid();
                    tvSel1.setText(zhibuOrgList.get(0).getName());
                }

            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(getActivity(), "网络错误~");
            }
        });
    }

    /**
     * 获取党建活动类型
     */
    private void getPartyType() {
        String url = Consts.BASE_URL + "/Orgmeeting/meetingType";
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                if (!isPrepared) {
                    return;
                }
                ULog.e("ck", "组织生活菜单：" + response);
                Gson gson = new Gson();
                try {
                    TaiZhangMenuBean taiZhangMenuBean = gson.fromJson(response, TaiZhangMenuBean.class);
                    String code = taiZhangMenuBean.getCode();
                    String msg = taiZhangMenuBean.getMsg();
                    if (!TextUtils.equals(code, "0")) {
                        ToastUtils.getInstance().showToast(getActivity(), msg);
                        return;
                    }
                    partyTypeList.clear();
                    partyTypeList.addAll(taiZhangMenuBean.getData());
                    if (partyTypeList != null && partyTypeList.size() > 0) {
                        tvSel3.setText(partyTypeList.get(0).getName());
                        mPartyType = partyTypeList.get(0).getCid();
                    }

                } catch (JsonSyntaxException e) {
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
}
