package com.longhoo.net.mine.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.longhoo.net.R;
import com.longhoo.net.mine.adapter.PartyManageAdapter;
import com.longhoo.net.mine.bean.PartyManageBean;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.httprequest.HttpRequestPresenter;
import com.longhoo.net.utils.httprequest.HttpRequestView;
import com.longhoo.net.widget.MyDialog;
import com.longhoo.net.widget.base.BaseActivity;
import com.longhoo.net.widget.base.BaseFragment;
import com.longhoo.net.widget.base.BaseLazyFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的支部-党费管理
 */
public class PartyfeeManageFragment extends BaseFragment implements OnRefreshLoadmoreListener, HttpRequestView {

    @BindView(R.id.tv_sel_status)
    TextView tvSelStatus;
    @BindView(R.id.tv_sel_montn)
    TextView tvSelMontn;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    private String url = Consts.BASE_URL+"/fee/org_fee.html";
    private HttpRequestPresenter requestPresenter;
    private List<PartyManageBean.DataBean> dataList = new ArrayList<>();
    private PartyManageAdapter adapter;
    private String status= "1";//0未支付 1已支付
    private int month;
    private int mPage=1;

    @Override
    protected int getContentId() {
        return R.layout.activity_partyfee_manage;
    }

    @Override
    protected void initView(View view) {

        Calendar c = Calendar.getInstance();
        month = c.get(Calendar.MONTH) + 1;// 获取当前月份
        tvSelMontn.setText(month+"月");
        requestPresenter = new HttpRequestPresenter(getActivity(),this);
        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        smartRefreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));
        smartRefreshLayout.setOnRefreshLoadmoreListener(this);
        smartRefreshLayout.autoRefresh(200);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new PartyManageAdapter(getActivity(),dataList);
        recyclerView.setAdapter(adapter);
        tvSelStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDialog dialog = new MyDialog(getActivity(), R.style.MyDialogStyle);
                final String[] arrays = new String[]{"已缴纳", "未缴纳"};
                dialog.setMyItems(arrays);
                dialog.setMyDialogItemClickListener(new MyDialog.MyDialogItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (position == 0) {
                            status = "1";
                            tvSelStatus.setText("已缴纳");
                        }else{
                            status = "0";
                            tvSelStatus.setText("未缴纳");
                        }
                        smartRefreshLayout.autoRefresh(200);
                    }
                });
                dialog.show();
            }
        });
        tvSelMontn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDialog dialog = new MyDialog(getActivity(), R.style.MyDialogStyle);
                final int[] arraysInt = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
                final String[] arrays = new String[]{"1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"};
                dialog.setMyItems(arrays);
                dialog.setMyDialogItemClickListener(new MyDialog.MyDialogItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                       month = arraysInt[position];
                       tvSelMontn.setText(arrays[position]);
                       smartRefreshLayout.autoRefresh(200);
                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    protected void initToolbar() {

    }


    @Override
    public void onNetworkError() {
        finishRefreshLoad();
        ToastUtils.getInstance().showToast(getActivity(), "网络连接异常，请检查网络设置~");
    }

    @Override
    public void onRefreshSuccess(String response) {
        List<PartyManageBean.DataBean> tempList = null;
        Gson gson = new Gson();
        try {
            PartyManageBean scoreDetailBean = gson.fromJson(response, PartyManageBean.class);
            String code = scoreDetailBean.getCode();
            if (!TextUtils.equals(code, "0")) {
                ToastUtils.getInstance().showToast(getActivity(), "获取数据失败~");
                smartRefreshLayout.finishRefresh(0);
                return;
            }
            tempList = scoreDetailBean.getData();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            smartRefreshLayout.finishRefresh(0);
        }
        dataList.clear();
        if (tempList!=null&&tempList.size() > 0) {
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
        List<PartyManageBean.DataBean> tempList2 = null;
        Gson gson = new Gson();
        try {
            PartyManageBean scoreDetailBean = gson.fromJson(response, PartyManageBean.class);
            String code = scoreDetailBean.getCode();
            if (!TextUtils.equals(code, "0")) {
                ToastUtils.getInstance().showToast(getActivity(), "获取数据失败~");
                smartRefreshLayout.finishLoadmore(0);
                return;
            }
            tempList2 = scoreDetailBean.getData();
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

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        Map<String,String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("status",status+"");
        params.put("month",month+"");
        params.put("page",mPage+"");
        requestPresenter.doHttpData(url, Consts.REQUEST_METHOD_POST, Consts.REQUEST_LOADMORE, params);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        smartRefreshLayout.setEnableLoadmore(true);
        mPage=1;
        Map<String,String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("status",status+"");
        params.put("month",month+"");
        params.put("page",mPage+"");
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
