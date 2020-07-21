package com.longhoo.net.study.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.zxing.integration.android.IntentIntegrator;
import com.longhoo.net.R;
import com.longhoo.net.study.adapter.ExaminationListAdapter;
import com.longhoo.net.study.bean.ExaminationListBean;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.utils.httprequest.HttpRequestPresenter;
import com.longhoo.net.utils.httprequest.HttpRequestView;
import com.longhoo.net.widget.HeaderAndFooterWrapper;
import com.longhoo.net.widget.base.BaseActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class ExaminationCenterActivity extends BaseActivity implements HttpRequestView, OnRefreshLoadmoreListener, HeaderAndFooterWrapper.OnItemClickListener, ExaminationListAdapter.OnTestClickListener {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    private ExaminationListAdapter adapter;
    private HeaderAndFooterWrapper wrapperAdapter;
    private List<ExaminationListBean.DataBean> dataList = new ArrayList<>();
    private HttpRequestPresenter requestPresenter;
    private String url = Consts.BASE_URL + "/exam/lists";
    private int mPage;
    private String type;

    public static void goTo(Context context, String type) {
        Intent intent = new Intent(context, ExaminationCenterActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_examination_center;
    }

    @Override
    protected void initViews() {
        if (getIntent() != null) {
            type = getIntent().getStringExtra("type");
        }
        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(this));
        smartRefreshLayout.setRefreshFooter(new ClassicsFooter(this));
        smartRefreshLayout.setOnRefreshLoadmoreListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new ExaminationListAdapter(this, dataList, type);
        adapter.setOnTestClickListener(this);
        wrapperAdapter = new HeaderAndFooterWrapper(adapter);
        wrapperAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(wrapperAdapter);
        requestPresenter = new HttpRequestPresenter(this, this);
        smartRefreshLayout.autoRefresh(200);
    }

    @Override
    protected void initToolbar() {
        if (TextUtils.equals(type, "0")) {  //自我测试
            tvTitle.setText("学习题库");
        } else {   //在线答题
            tvTitle.setText("在线考试");
        }
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.left_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        ULog.e(Consts.TAG, "在线测试:" + response);
        //处理数据
        List<ExaminationListBean.DataBean> tempList = null;
        Gson gson = new Gson();
        try {
            ExaminationListBean examinationListBean = gson.fromJson(response, ExaminationListBean.class);
            String code = examinationListBean.getCode();
            if (!TextUtils.equals(code, "0")) {
                ToastUtils.getInstance().showToast(this, "获取数据失败~");
                smartRefreshLayout.finishRefresh(0);
                return;
            }
            tempList = examinationListBean.getData();
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
        ToastUtils.getInstance().showToast(this, "刷新失败~");
        smartRefreshLayout.finishRefresh(0);
    }

    @Override
    public void onLoadMoreSuccess(String response) {
        ULog.e(Consts.TAG, "在线测试:" + mPage + ":" + response);
        //处理数据
        List<ExaminationListBean.DataBean> tempList2 = null;
        Gson gson = new Gson();
        try {
            ExaminationListBean examinationListBean = gson.fromJson(response, ExaminationListBean.class);
            String code = examinationListBean.getCode();
            if (!TextUtils.equals(code, "0")) {
                ToastUtils.getInstance().showToast(this, "获取数据失败~");
                smartRefreshLayout.finishLoadmore(0);
                return;
            }
            tempList2 = examinationListBean.getData();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            smartRefreshLayout.finishLoadmore(0);
        }
        if (tempList2 == null) {
            ToastUtils.getInstance().showToast(this, "解析错误！");
            smartRefreshLayout.finishLoadmore(0);
            return;
        }
        //没有数据或数据<limit，说明无更多
        if (tempList2.size() <= 0) {
            ToastUtils.getInstance().showToast(this, "没有更多了~");
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
        ToastUtils.getInstance().showToast(this, "加载失败~");
        smartRefreshLayout.finishLoadmore(0);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        Map<String, String> params = new HashMap<>();
        params.put("page", mPage + "");
        params.put("uid", EnterCheckUtil.getInstance().getUid_Token()[0]);
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        requestPresenter.doHttpData(url, Consts.REQUEST_METHOD_POST, Consts.REQUEST_LOADMORE, params);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        smartRefreshLayout.setEnableLoadmore(true);
        mPage = 1;
        Map<String, String> params = new HashMap<>();
        params.put("page", mPage + "");
        params.put("uid", EnterCheckUtil.getInstance().getUid_Token()[0]);
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
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
    public void onItemClick(View view, int position) {
//        if(position<0||position>dataList.size()-1){
//            return;
//        }
//        Intent intent = new Intent(this,ExaminationDetailActivity.class);
//        intent.putExtra("exam_data_info", dataList.get(position));
//        startActivity(intent);
    }

    @Override
    public void onTestClick(int position) {
        if (position < 0 || position > dataList.size() - 1) {
            return;
        }
        Intent intent = null;
        if (TextUtils.equals(type, "0")) {  //自我测试
            intent = new Intent(this, SelfExamAnalysisActivity.class);
            intent.putExtra("question_title", dataList.get(position).getName());
            intent.putExtra("question_id", dataList.get(position).getId());
            intent.putExtra("question_time", dataList.get(position).getExamtime());
        } else {
            if(dataList.get(position).getStatus()==0){
                intent = new Intent(this, ExaminationDetailActivity.class);
                intent.putExtra("exam_id", dataList.get(position).getId());
            }else if(dataList.get(position).getStatus()==1){
                ToastUtils.getInstance().showToast(this,"考试未开始~");
            }else{
                ToastUtils.getInstance().showToast(this,"考试已结束~");
            }
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
