package com.longhoo.net.manageservice.ui;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.longhoo.net.R;
import com.longhoo.net.manageservice.adapter.OrganizationArchiAdapter;
import com.longhoo.net.manageservice.bean.OrganizationArchiBean;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.utils.httprequest.HttpRequestPresenter;
import com.longhoo.net.utils.httprequest.HttpRequestView;
import com.longhoo.net.utils.httprequest.OkHttpCallback;
import com.longhoo.net.utils.httprequest.OkHttpUtil;
import com.longhoo.net.widget.HeaderAndFooterWrapper;
import com.longhoo.net.widget.OrganizationLevelView;
import com.longhoo.net.widget.base.BaseFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 组织架构
 */
public class OrganizationArchiFragment extends BaseFragment implements HttpRequestView, OnRefreshListener, HeaderAndFooterWrapper.OnItemClickListener
        , ExpandableListView.OnGroupClickListener {

    @BindView(R.id.list_view)
    ExpandableListView listView;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    private HeaderAndFooterWrapper adapterWrapper;
    private OrganizationArchiAdapter adapter;
    private HttpRequestPresenter requestPresenter;
    private String url = Consts.BASE_URL + "/org/index";
    /**
     * 顶部的个人
     */
    private List<OrganizationArchiBean.DataBean> headerList = new ArrayList<>();
    /**
     * 下面的组织列表
     */
    private List<OrganizationArchiBean.DataBean> groupList = new ArrayList<>();
    private Map<String, List<OrganizationArchiBean.DataBean>> childList = new HashMap<>();
    private View headerView;

    @Override
    protected int getContentId() {
        return R.layout.fragment_organization_archi;
    }

    @Override
    protected void initView(View view) {
        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        //smartRefreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));
        smartRefreshLayout.setOnRefreshListener(this);
        smartRefreshLayout.setEnableLoadmore(false);
        adapter = new OrganizationArchiAdapter(getActivity(), groupList, childList);
        listView.setAdapter(adapter);
        requestPresenter = new HttpRequestPresenter(getActivity(), this);
        //listView进入动画
        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f);
        animation.setDuration(60);
        animation.setInterpolator(new BounceInterpolator());
        LayoutAnimationController controller = new LayoutAnimationController(animation, 1f);
        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
        listView.setLayoutAnimation(controller);
        listView.setGroupIndicator(null);
        listView.setOnGroupClickListener(this);
        smartRefreshLayout.autoRefresh(200);
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
        ULog.e(Consts.TAG, "组织架构:" + response);
        //处理数据
        List<OrganizationArchiBean.DataBean> tempList = null;
        Gson gson = new Gson();
        try {
            OrganizationArchiBean bean = gson.fromJson(response, OrganizationArchiBean.class);
            String code = bean.getCode();
            if (!TextUtils.equals(code, "0")) {
                ToastUtils.getInstance().showToast(getActivity(), "获取数据失败~");
                smartRefreshLayout.finishRefresh(0);
                return;
            }
            tempList = bean.getData();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            smartRefreshLayout.finishRefresh(0);
        }
        if (tempList == null) {
            ToastUtils.getInstance().showToast(getActivity(), "服务器异常~");
            smartRefreshLayout.finishRefresh(0);
            return;
        }
        headerList.clear();
        groupList.clear();
        //计算顶部的个人
        for (OrganizationArchiBean.DataBean bean : tempList) {
            if (bean.getHide()==2) {
                headerList.add(bean);
            } else {
                groupList.add(bean);
            }
        }

        if (headerView == null) {
            headerView = new OrganizationLevelView(getActivity(), headerList);
            listView.addHeaderView(headerView);
        }else{
            ((OrganizationLevelView)headerView).refresh(headerList);
        }

        adapter.notifyDataSetChanged();
        smartRefreshLayout.finishRefresh(0);
        smartRefreshLayout.setEnableRefresh(false);
    }

    @Override
    public void onRefreshError() {
        ToastUtils.getInstance().showToast(getActivity(), "刷新失败~");
        smartRefreshLayout.finishRefresh(0);
    }

    @Override
    public void onLoadMoreSuccess(String response) {
    }

    @Override
    public void onLoadMoreError() {
        ToastUtils.getInstance().showToast(getActivity(), "加载失败~");
        smartRefreshLayout.finishLoadmore(0);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("pid","0");
        requestPresenter.doHttpData(url, Consts.REQUEST_METHOD_POST, Consts.REQUEST_REFRESH, params);
    }

    private void finishRefreshLoad() {
        if (smartRefreshLayout != null) {
            if (smartRefreshLayout.isRefreshing()) {
                smartRefreshLayout.finishRefresh(0);
            }
        }
    }

    @Override
    public void onDestroy() {
        finishRefreshLoad();
        super.onDestroy();
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        if (groupPosition < 0 || groupPosition > groupList.size() - 1) {
            return false;
        }
        String oid = "";
        int type;
        String title = "";
        int hide;
        OrganizationArchiBean.DataBean bean = groupList.get(groupPosition);
        if (bean != null) {
            oid = bean.getOid()+"";
            type = bean.getType();
            title = bean.getName();
            hide = bean.getPerson();
            //type:0组织 1个人 2列表
            if (type== 0) {
                //进入下一页面
                Intent intent = new Intent(getActivity(), OrganizationArchiActivity.class);
                intent.putExtra("archi_title", title);
                intent.putExtra("archi_group_id", oid);
                intent.putExtra("archi_type", hide);
                startActivity(intent);
            } else if (type == 2) {
                //展开列表
                doGetData(v, oid, groupPosition);
            }
        }
        return false;
    }


    private void doGetData(final View childItemView, final String groupId, final int curGroupPos) {
        //如果已有数据，则不刷新
        if (childList.get(groupId) != null) {
            return;
        }
        //进度条可见
        if (childItemView != null) {
            OrganizationArchiAdapter.ViewHolderP vp = (OrganizationArchiAdapter.ViewHolderP) childItemView.getTag();
            if (vp != null) {
                vp.progressBar.setVisibility(View.VISIBLE);
                vp.progressBar.startAnimation(AnimationUtils.loadAnimation(getContext(),
                        R.anim.rotate));
            }
        }
        String url = Consts.BASE_URL + "/org/index";
        Map<String, String> params = new HashMap<>();
        params.put("pid", groupId+"");
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                //进度条不可见
                if (childItemView != null) {
                    OrganizationArchiAdapter.ViewHolderP vp = (OrganizationArchiAdapter.ViewHolderP) childItemView.getTag();
                    if (vp != null) {
                        vp.progressBar.setVisibility(View.GONE);
                        vp.progressBar.clearAnimation();
                    }
                }
                ULog.e(Consts.TAG, "组织架构展开列表:" + response);
                //处理数据
                List<OrganizationArchiBean.DataBean> tempList = null;
                Gson gson = new Gson();
                try {
                    OrganizationArchiBean bean = gson.fromJson(response, OrganizationArchiBean.class);
                    String code = bean.getCode();
                    if (!TextUtils.equals(code, "0")) {
                        ToastUtils.getInstance().showToast(getActivity(), "获取数据失败~");
                        return;
                    }
                    tempList = bean.getData();
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
                if (tempList == null) {
                    ToastUtils.getInstance().showToast(getActivity(), "服务器异常~");
                    return;
                }
                if (tempList.size() > 0) {
                    if (childList.get(groupId) == null) {
                        childList.put(groupId, tempList);
                    }
                }
                adapter.notifyDataSetChanged();
                listView.setSelectedGroup(curGroupPos);
            }

            @Override
            public void onFailure(String errorMsg) {
                //进度条不可见
                if (childItemView != null) {
                    OrganizationArchiAdapter.ViewHolderP vp = (OrganizationArchiAdapter.ViewHolderP) childItemView.getTag();
                    if (vp != null) {
                        vp.progressBar.setVisibility(View.GONE);
                        vp.progressBar.clearAnimation();
                    }
                }
                smartRefreshLayout.setEnableRefresh(true);
            }
        });
    }
}
