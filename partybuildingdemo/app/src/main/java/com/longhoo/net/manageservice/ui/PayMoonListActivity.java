package com.longhoo.net.manageservice.ui;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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
import com.longhoo.net.manageservice.adapter.PayMoonListAdapter;
import com.longhoo.net.manageservice.bean.PayMoonListBean;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.MessageEvent;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.utils.httprequest.HttpRequestPresenter;
import com.longhoo.net.utils.httprequest.HttpRequestView;
import com.longhoo.net.utils.httprequest.OkHttpCallback;
import com.longhoo.net.utils.httprequest.OkHttpUtil;
import com.longhoo.net.widget.base.BaseActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class PayMoonListActivity extends BaseActivity implements HttpRequestView, OnRefreshListener
        , ExpandableListView.OnGroupClickListener, PayMoonListAdapter.OnChildItemClickListener {

    @BindView(R.id.list_view)
    ExpandableListView listView;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private PayMoonListAdapter adapter;
    private HttpRequestPresenter requestPresenter;
    private String url = Consts.BASE_URL + "/fee/fee_lists";
    private List<String> groupList = new ArrayList<>();
    private Map<String, List<PayMoonListBean.DataBean.ListsBean>> childList = new HashMap<>();

    @Override
    protected int getContentId() {
        return R.layout.activity_pay_moon_list;
    }

    @Override
    protected void initViews() {
        EventBus.getDefault().register(this);
        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(this));
        //smartRefreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));
        smartRefreshLayout.setOnRefreshListener(this);
        smartRefreshLayout.setEnableLoadmore(false);
        adapter = new PayMoonListAdapter(this, groupList, childList);
        listView.setAdapter(adapter);
        adapter.setOnChildItemClickListener(this);
        requestPresenter = new HttpRequestPresenter(this, this);
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
        tvTitle.setText("党费缴纳");
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.left_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onNetworkError() {
        finishRefreshLoad();
        ToastUtils.getInstance().showToast(this, "网络连接异常，请检查网络设置~");
    }

    @Override
    public void onRefreshSuccess(String response) {
        ULog.e(Consts.TAG, "党费缴纳——年:" + response);
        //处理数据
//        try {
//            groupList.clear();
//            JSONObject jsonObject = new JSONObject(response);
//            String code = jsonObject.optString("code");
//            String msg = jsonObject.optString("msg");
//            JSONArray jsonArray = jsonObject.optJSONArray("data");
//            if(!TextUtils.equals(code,"0")){
//                ToastUtils.getInstance().showToast(this,msg);
//                return;
//            }
//            int size = jsonArray.length();
//            for (int i = 0; i < size; i++) {
//                groupList.add(jsonArray.optString(i));
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//            ToastUtils.getInstance().showToast(this, "获取数据失败~");
//            smartRefreshLayout.finishRefresh(0);
//            return;
//        }
//        adapter.notifyDataSetChanged();
//        smartRefreshLayout.finishRefresh(0);
//        //展开第一项
//        listView.performItemClick(null,0,0);


        List<PayMoonListBean.DataBean> tempList = null;

        Gson gson = new Gson();
        try {
            PayMoonListBean moonListBean = gson.fromJson(response,PayMoonListBean.class);
            String code = moonListBean.getCode();
            String msg = moonListBean.getMsg();
            tempList = moonListBean.getData();
            if(!TextUtils.equals(code,"0")){
                ToastUtils.getInstance().showToast(PayMoonListActivity.this,msg);
                return;
            }
            groupList.clear();
            childList.clear();
            for(PayMoonListBean.DataBean dataBean:tempList){
                groupList.add(dataBean.getYear());
                childList.put(dataBean.getYear(),dataBean.getLists());
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            ToastUtils.getInstance().showToast(this, "获取数据失败~");
            smartRefreshLayout.finishRefresh(0);
            return;
        }
        adapter.notifyDataSetChanged();
        smartRefreshLayout.finishRefresh(0);

        final String curYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        for(int i=0;i<groupList.size();i++){
            if(TextUtils.equals(groupList.get(i), curYear)){
                if(!isFinishing()){
                    //展开
                    listView.expandGroup(i,true);
                }
            }
        }
    }

    @Override
    public void onRefreshError() {
        ToastUtils.getInstance().showToast(this, "刷新失败~");
        smartRefreshLayout.finishRefresh(0);
    }

    @Override
    public void onLoadMoreSuccess(String response) {
    }

    @Override
    public void onLoadMoreError() {
        ToastUtils.getInstance().showToast(this, "加载失败~");
        smartRefreshLayout.finishLoadmore(0);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        Map<String, String> params = new HashMap<>();
        params.put("uid", EnterCheckUtil.getInstance().getUid_Token()[0]);
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
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
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        if (groupPosition < 0 || groupPosition > groupList.size() - 1) {
            return false;
        }
        //展开列表
        //doGetData(v, groupPosition, groupList.get(groupPosition));
//        List<PayMoonListBean.DataBean> dataBeanList = new ArrayList<>();
//        int m=0;
//        if (groupPosition == 0) {
//            m=10;
//        }else if(groupPosition==1){
//            m=11;
//        }else{
//            m=12;
//        }
//        for(int i=0;i<m;i++){
//            PayMoonListBean.DataBean dataBean = new PayMoonListBean.DataBean();
//            dataBean.setIsPay(i%2==0?"1":"2");
//            dataBean.setMoon((i+1)+"月");
//            dataBeanList.add(dataBean);
//        }
//        if(childList.get(groupPosition)==null){
//            childList.put(groupPosition,dataBeanList);
//        }
//        adapter.notifyDataSetChanged();
        return false;
    }


//    private void doGetData(final View childItemView, final int curGroupPos, String year) {
//        if (TextUtils.isEmpty(year)) {
//            return;
//        }
//        //如果已有数据，则不刷新
//        if (childList.get(curGroupPos) != null) {
//            return;
//        }
//        //进度条可见
//        if (childItemView != null) {
//            PayMoonListAdapter.ViewHolderP vp = (PayMoonListAdapter.ViewHolderP) childItemView.getTag();
//            if (vp != null) {
//                vp.progressBar.setVisibility(View.VISIBLE);
//                vp.progressBar.startAnimation(AnimationUtils.loadAnimation(this,
//                        R.anim.rotate));
//            }
//        }
//        String url = Consts.BASE_URL + "/Application/getPayTypeByYearMonth&token";
//        Map<String, String> params = new HashMap<>();
//        params.put("uid", EnterCheckUtil.getInstance().getUid_Token()[0]);
//        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
//        params.put("year", year);
//        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
//            @Override
//            public void onSuccess(String response) {
//                //进度条不可见
//                if (childItemView != null) {
//                    PayMoonListAdapter.ViewHolderP vp = (PayMoonListAdapter.ViewHolderP) childItemView.getTag();
//                    if (vp != null) {
//                        vp.progressBar.setVisibility(View.GONE);
//                        vp.progressBar.clearAnimation();
//                    }
//                }
//                ULog.e(Consts.TAG, "年展开列表:" + response);
//                //处理数据
//                List<PayMoonListBean.DataBean> tempList = null;
//                Gson gson = new Gson();
//                try {
//                    PayMoonListBean moonListBean = gson.fromJson(response,PayMoonListBean.class);
//                    String code = moonListBean.getCode();
//                    String msg = moonListBean.getMsg();
//                    tempList = moonListBean.getData();
//                    if(!TextUtils.equals(code,"0")){
//                        ToastUtils.getInstance().showToast(PayMoonListActivity.this,msg);
//                        return;
//                    }
//                } catch (JsonSyntaxException e) {
//                    e.printStackTrace();
//                }
//                if (tempList == null) {
//                    ToastUtils.getInstance().showToast(PayMoonListActivity.this, "服务器异常~");
//                    return;
//                }
//                if (tempList.size() > 0) {
//                    if (childList.get(curGroupPos) == null) {
//                        Collections.reverse(tempList);
//                        childList.put(curGroupPos, tempList);
//                        adapter.notifyDataSetChanged();
//                    }
//                }
//                listView.setSelectedGroup(curGroupPos);
//            }
//
//            @Override
//            public void onFailure(String errorMsg) {
//                //进度条不可见
//                if (childItemView != null) {
//                    PayMoonListAdapter.ViewHolderP vp = (PayMoonListAdapter.ViewHolderP) childItemView.getTag();
//                    if (vp != null) {
//                        vp.progressBar.setVisibility(View.GONE);
//                        vp.progressBar.clearAnimation();
//                    }
//                }
//            }
//        });
//    }

    @Override
    public void onChildItemClick(int groupPos, int childPos) {
        String year = groupList.get(groupPos);
        String month = childList.get(year).get(childPos).getMonth();
        int type = childList.get(year).get(childPos).getStatus();

       int canPay = childList.get(year).get(childPos).getCanPay();
        if(canPay==0){
            ToastUtils.getInstance().showToast(this,"现在不可缴纳！");
        }else{
            if (type == 0) {
                Intent intent = new Intent(this, PayMembershipDuesActivity.class);
                intent.putExtra("year", year);
                intent.putExtra("month", month);
                ULog.e("ck--",month);
                startActivity(intent);
            }else{

            }
        }

    }

    @Subscribe(threadMode=ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event){
        String msg = event.message;
        if(TextUtils.equals(msg,"msg_finish")){
            finish();
        }
    }

}
