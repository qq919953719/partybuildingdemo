package com.longhoo.net.manageservice.ui;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.longhoo.net.MyApplication;
import com.longhoo.net.R;

import com.longhoo.net.manageservice.adapter.OrganizationAdapter;
import com.longhoo.net.manageservice.bean.OrganizationallBean;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.httprequest.HttpRequestPresenter;
import com.longhoo.net.utils.httprequest.HttpRequestView;

import com.longhoo.net.widget.base.BaseLazyFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 首页——组织
 */
public class OrganizationFragmentOld extends BaseLazyFragment implements OnRefreshLoadmoreListener, HttpRequestView {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    Unbinder unbinder;
    OrganizationAdapter adapter;
    private HttpRequestPresenter requestPresenter;
    private int mPage = 1;
    private Activity mActivity;
    private List<OrganizationallBean.DataBean.OrganizationBean> dataList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected int getContentId() {
        return R.layout.fragment_organization_old;
    }

    @Override
    protected void onLazyLoad() {
        smartRefreshLayout.autoRefresh(200);
    }

    @Override
    protected void initViews(View view) {
        requestPresenter = new HttpRequestPresenter(getActivity(), this);
        mActivity = this.getActivity();
        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(mActivity));
        smartRefreshLayout.setRefreshFooter(new ClassicsFooter(mActivity));
        smartRefreshLayout.setOnRefreshLoadmoreListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(this.getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new OrganizationAdapter(mActivity, dataList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initToolbar() {


    }

    @Override
    protected long setLoadInterval() {
        return 0;
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        MyApplication App = (MyApplication) mActivity.getApplication();
        mPage++;
        Map<String, String> params = new HashMap<>();
        params.put("page", String.valueOf(mPage));
        params.put("token", String.valueOf(App.getmLoginBean().getData().getToken()));
        requestPresenter.doHttpData(Consts.BASE_URL + "/Organization/index", Consts.REQUEST_METHOD_POST, Consts.REQUEST_LOADMORE, params);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mPage = 1;
        MyApplication App = (MyApplication) mActivity.getApplication();
        if (App.getmLoginBean() == null) {
            ToastUtils.getInstance().showToast(getActivity(), "登录过期，请重新登陆！");
            smartRefreshLayout.finishRefresh(0);
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("page", String.valueOf(mPage));
        if (App.getmLoginBean() == null) {
            params.put("token", "");
        } else {
            params.put("token", String.valueOf(App.getmLoginBean().getData().getToken()));
        }

//        params.put("token", String.valueOf("mCR2G6JKoWvjOoDfOreYcg=="));


        requestPresenter.doHttpData(Consts.BASE_URL + "/Organization/index", Consts.REQUEST_METHOD_POST, Consts.REQUEST_REFRESH, params);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onNetworkError() {

    }

    @Override
    public void onRefreshSuccess(String response) {
        smartRefreshLayout.finishRefresh(0);


        try {
            JSONObject obj = null;
            obj = new JSONObject(response);
            if (obj.has("code")) {
                if (obj.getString("code").equals("0")) {

                    Gson gson = new Gson();
                    OrganizationallBean searchResultBean = gson.fromJson(response, OrganizationallBean.class);
                    dataList = searchResultBean.getData().getOrganization();
                    adapter.AddOrganizationAdapter(dataList);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(mActivity, "数据异常", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(mActivity, "数据异常", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onRefreshError() {

    }

    @Override
    public void onLoadMoreSuccess(String response) {
        smartRefreshLayout.finishLoadmore(0);
        Gson gson = new Gson();
        OrganizationallBean searchResultBean = gson.fromJson(response, OrganizationallBean.class);
        dataList.addAll(searchResultBean.getData().getOrganization());
        adapter.AddAllOrganizationAdapter(dataList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadMoreError() {

    }
}
