package com.longhoo.net.mine.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.longhoo.net.headline.ui.NewsDetailActivity;
import com.longhoo.net.manageservice.ui.DataShareingDetailActivity;
import com.longhoo.net.manageservice.ui.OrganizationLifedetailActivity;
import com.longhoo.net.manageservice.ui.SpecialMeterialReportDetailActivity;
import com.longhoo.net.mine.adapter.MyTaskAdapter;
import com.longhoo.net.mine.bean.MyTaskBean;
import com.longhoo.net.mine.bean.MyTaskTypebean;
import com.longhoo.net.partyaffairs.ui.NotiNewsDetailActivity;
import com.longhoo.net.study.ui.ActsEncrollDetailActivity;
import com.longhoo.net.study.ui.TrainCourseDetailActivity;
import com.longhoo.net.supervision.ui.SecretReportContentActivity;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.httprequest.HttpRequestPresenter;
import com.longhoo.net.utils.httprequest.HttpRequestView;
import com.longhoo.net.utils.httprequest.OkHttpCallback;
import com.longhoo.net.utils.httprequest.OkHttpUtil;
import com.longhoo.net.widget.HeaderAndFooterWrapper;
import com.longhoo.net.widget.MyDialog;
import com.longhoo.net.widget.base.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 我的任务
 */
public class MyTaskActivity extends BaseActivity implements OnRefreshLoadmoreListener, HttpRequestView
        , HeaderAndFooterWrapper.OnItemClickListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_sel)
    TextView tvSel;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    private HttpRequestPresenter requestPresenter;
    private List<MyTaskBean.DataBeanX.DataBean> dataList = new ArrayList<>();
    private List<MyTaskTypebean.DataBeanX.DataBean> myTaskTypeList = new ArrayList<>();
    private HeaderAndFooterWrapper adapterWrapper;
    private MyTaskAdapter adapter;
    private String url = Consts.BASE_URL + "/application/myTasks";
    private String typeUrl = Consts.BASE_URL + "/application/myTasksType";
    private int type = 0;
    private int mPage = 1;


    @Override
    protected int getContentId() {
        return R.layout.activity_my_task;
    }

    @Override
    protected void initViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyTaskAdapter(this, dataList);
        adapterWrapper = new HeaderAndFooterWrapper(adapter);
        adapterWrapper.setOnItemClickListener(this);
        recyclerView.setAdapter(adapterWrapper);
        requestPresenter = new HttpRequestPresenter(this, this);
        refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this));
        refreshLayout.setOnRefreshLoadmoreListener(this);
        refreshLayout.autoRefresh(200);
        getType();
    }

    @Override
    protected void initToolbar() {
        //tvTitle.setText("我的任务");
        tvTitle.setText("通知公告");
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.left_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @OnClick({R.id.tv_sel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_sel:
                MyDialog dialog = new MyDialog(this, R.style.MyDialogStyle);
                if (myTaskTypeList.size() <= 0) {
                    return;
                }

                int size = myTaskTypeList.size();
                final String[] arrays = new String[size];
                for (int i = 0; i < size; i++) {
                    arrays[i] = myTaskTypeList.get(i).getName() + "";
                }
                dialog.setMyItems(arrays);
                dialog.setMyDialogItemClickListener(new MyDialog.MyDialogItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        tvSel.setText(arrays[position]);
                        type = myTaskTypeList.get(position).getId();
                        refreshLayout.autoRefresh(200);
                    }
                });
                dialog.show();
                break;
        }
    }

    @Override
    public void onNetworkError() {
        finishRefreshLoad();
        ToastUtils.getInstance().showToast(this, "网络连接异常，请检查网络设置~");
    }

    @Override
    public void onRefreshSuccess(String response) {
        //处理数据
        List<MyTaskBean.DataBeanX.DataBean> tempList = null;
        Gson gson = new Gson();
        try {
            MyTaskBean myTaskBean = gson.fromJson(response, MyTaskBean.class);
            String code = myTaskBean.getCode();
            if (!TextUtils.equals(code, "0")) {
                ToastUtils.getInstance().showToast(this, "获取数据失败~");
                refreshLayout.finishRefresh(0);
                return;
            }
            tempList = myTaskBean.getData().getData();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            refreshLayout.finishRefresh(0);
        }
        dataList.clear();
        if (tempList != null && tempList.size() > 0) {
            dataList.addAll(tempList);
            mPage++;
        }
        adapterWrapper.notifyDataSetChanged();
        refreshLayout.finishRefresh(0);
    }

    @Override
    public void onRefreshError() {
        ToastUtils.getInstance().showToast(this, "刷新失败~");
        refreshLayout.finishRefresh(0);
    }

    @Override
    public void onLoadMoreSuccess(String response) {
        //处理数据
        List<MyTaskBean.DataBeanX.DataBean> tempList2 = null;
        Gson gson = new Gson();
        String msg = "";
        try {
            MyTaskBean bean = gson.fromJson(response, MyTaskBean.class);
            String code = bean.getCode();
            msg = bean.getMsg();
            if (!TextUtils.equals(code, "0")) {
                ToastUtils.getInstance().showToast(MyTaskActivity.this, msg);
                refreshLayout.finishLoadmore(0);
                return;
            }
            tempList2 = bean.getData().getData();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            refreshLayout.finishLoadmore(0);
        }
        if (tempList2 == null) {
            ToastUtils.getInstance().showToast(MyTaskActivity.this, msg);
            refreshLayout.finishLoadmore(0);
            return;
        }
        //没有数据或数据<limit，说明无更多
        if (tempList2.size() <= 0) {
            ToastUtils.getInstance().showToast(MyTaskActivity.this, "没有更多了~");
            refreshLayout.setEnableLoadmore(false);
        } else {
            dataList.addAll(tempList2);
            mPage++;
            adapterWrapper.notifyDataSetChanged();
        }
        refreshLayout.finishLoadmore(0);
    }

    @Override
    public void onLoadMoreError() {
        ToastUtils.getInstance().showToast(MyTaskActivity.this, "加载失败~");
        refreshLayout.finishLoadmore(0);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        refreshLayout.setEnableLoadmore(true);
        mPage = 1;
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("type", type + "");
        params.put("page", mPage + "");
        requestPresenter.doHttpData(url, Consts.REQUEST_METHOD_POST, Consts.REQUEST_REFRESH, params);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("type", type + "");
        params.put("page", mPage + "");
        requestPresenter.doHttpData(url, Consts.REQUEST_METHOD_POST, Consts.REQUEST_LOADMORE, params);
    }

    private void finishRefreshLoad() {
        if (refreshLayout != null) {
            if (refreshLayout.isRefreshing()) {
                refreshLayout.finishRefresh(0);
            }
            if (refreshLayout.isLoading()) {
                refreshLayout.finishLoadmore(0);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finishRefreshLoad();
    }

    private void getType() {
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        OkHttpUtil.getInstance().doAsyncPost(typeUrl, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                //处理数据
                List<MyTaskTypebean.DataBeanX.DataBean> tempList = null;
                Gson gson = new Gson();
                try {
                    MyTaskTypebean myTaskTypebean = gson.fromJson(response, MyTaskTypebean.class);
                    String code = myTaskTypebean.getCode();
                    if (!TextUtils.equals(code, "0")) {
                        ToastUtils.getInstance().showToast(MyTaskActivity.this, "获取数据失败~");
                        return;
                    }
                    tempList = myTaskTypebean.getData().getData();
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
                myTaskTypeList.clear();
                if (tempList != null && tempList.size() > 0) {
                    myTaskTypeList.addAll(tempList);
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(MyTaskActivity.this, "网络错误~");
            }
        });
    }


    private void getOrganizationLifeData(final String tid) {
        String url = Consts.BASE_URL + "/Orgmeeting/meetingInfo";
        Map<String, String> map = new HashMap<>();
        map.put("uid", EnterCheckUtil.getInstance().getUid_Token()[0]);
        map.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        map.put("tid", tid + "");
        OkHttpUtil.getInstance().doAsyncPost(url, map, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
//{"code":1,"msg":"\u6b64\u4f1a\u8bae\u5df2\u53d6\u6d88","data":[]}
                //处理数据
                String msgCode = "";
                String strMsg = "";
                Gson gson = new Gson();
                try {
                    try {
                        JSONObject obj = new JSONObject(response);
                        if (obj.has("code")) {
                            msgCode = obj.getString("code");
                        }
                        if (obj.has("msg")) {
                            strMsg = obj.getString("msg");
                        }
                        if (!TextUtils.equals(msgCode, "0")) {
                            if (TextUtils.equals(msgCode, "1")) {
                                ToastUtils.getInstance().showToast(MyTaskActivity.this, strMsg);
                            } else {
                                ToastUtils.getInstance().showToast(MyTaskActivity.this, "数据获取失败");
                            }

                            return;
                        } else {
                            OrganizationLifedetailActivity.goTo(MyTaskActivity.this, tid);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                    OrganizationLifeDetailBean detailBean = gson.fromJson(response, OrganizationLifeDetailBean.class);
//                    String code = detailBean.getCode();
//                    if (!TextUtils.equals(msgCode, "0")) {
//                        if (TextUtils.equals(msgCode, "1")) {
//                            ToastUtils.getInstance().showToast(MyTaskActivity.this, strMsg);
//                        } else {
//                            ToastUtils.getInstance().showToast(MyTaskActivity.this, "数据获取失败");
//                        }
//
//                        return;
//                    } else {
//                        OrganizationLifedetailActivity.goTo(MyTaskActivity.this, tid);
//                    }

                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(String errorMsg) {

                ToastUtils.getInstance().showToast(MyTaskActivity.this, "网络错误~");

            }
        });
    }


    void getReportActivity(final String id) {


        String url = Consts.BASE_URL + "/Activity/getActivityInfo";
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("id", id);
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                Gson gson = new Gson();
                String msg = "";
                try {
//{"code":1,"msg":"\u6b64\u4f1a\u8bae\u5df2\u53d6\u6d88","data":[]}
                    //处理数据
                    String msgCode = "";
                    String strMsg = "";

                    try {
                        JSONObject obj = new JSONObject(response);
                        if (obj.has("code")) {
                            msgCode = obj.getString("code");
                        }
                        if (obj.has("msg")) {
                            strMsg = obj.getString("msg");
                        }
                        if (!TextUtils.equals(msgCode, "0")) {
                            ToastUtils.getInstance().showToast(MyTaskActivity.this, strMsg);
                            return;
                        } else {
                            ActsEncrollDetailActivity.goTo(MyTaskActivity.this, id);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                    final ActsEncrollDetailBean bean = gson.fromJson(response, ActsEncrollDetailBean.class);
//                    String code = bean.getCode();
//                    msg = bean.getMsg();
//                    if (!TextUtils.equals(msgCode, "0")) {
//                        ToastUtils.getInstance().showToast(MyTaskActivity.this, strMsg);
//                        return;
//                    } else {
//                        ActsEncrollDetailActivity.goTo(MyTaskActivity.this, id);
//                    }


                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(MyTaskActivity.this, "网络异常~");
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        if (position < 0 || position > dataList.size() - 1) {
            return;
        }
        String tid = dataList.get(position).getTid() + "";
        int type = dataList.get(position).getType();   //1、组织生活 2、书记述职 3、材料上报 4、其它 5.活动报名 6.培训班报名 7.资料共享 8工作动态   9基层风采 10党风廉政 11通知公告

        switch (type) {
            case 1:

                getOrganizationLifeData(tid);

                break;
            case 2:
                SecretReportContentActivity.goTo(this, tid);
                break;
            case 3:
                SpecialMeterialReportDetailActivity.goTo(this, tid);
                break;
            case 4:
                NotiNewsDetailActivity.goTo(this, tid);
                break;
            case 5:

                //活动报名
                getReportActivity(tid);
                break;
            case 6:
                TrainCourseDetailActivity.goTo(this, tid);
                break;
            case 7:
                DataShareingDetailActivity.goTo(this, tid);
                break;
            case 8:
            case 9:
            case 10:
            case 11:
                NewsDetailActivity.goTo(this, tid);
                break;
        }
        if (dataList.get(position).getIs_read() == 0) {
            dataList.get(position).setIs_read(1);
            adapterWrapper.notifyItemChanged(position);
        }
    }
}
