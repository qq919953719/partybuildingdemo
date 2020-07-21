package com.longhoo.net.mine.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.longhoo.net.R;
import com.longhoo.net.mine.adapter.StudySortAdapter;
import com.longhoo.net.mine.bean.StudySortBean;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.httprequest.HttpRequestPresenter;
import com.longhoo.net.utils.httprequest.HttpRequestView;
import com.longhoo.net.widget.HeaderAndFooterWrapper;
import com.longhoo.net.widget.base.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyStudySortActivity extends BaseActivity implements OnRefreshLoadmoreListener, HttpRequestView, HeaderAndFooterWrapper.OnItemClickListener {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_myscore)
    TextView tvMyscore;
    @BindView(R.id.tv_mysort)
    TextView tvMysort;
    @BindView(R.id.tv_data)
    TextView tvData;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    private HttpRequestPresenter requestPresenter;
    private List<StudySortBean.DataBean.ListBean.RankListBean> dataList = new ArrayList<>();
    private HeaderAndFooterWrapper adapterWarpper;
    StudySortAdapter adapter;

    @Override
    protected int getContentId() {
        return R.layout.activity_my_study_sorty;
    }

    @Override
    protected void initViews() {
        requestPresenter = new HttpRequestPresenter(this, this);
        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(this));
        smartRefreshLayout.setRefreshFooter(new ClassicsFooter(this));
        smartRefreshLayout.setOnRefreshLoadmoreListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new StudySortAdapter(this, dataList);
        adapterWarpper = new HeaderAndFooterWrapper(adapter);
        recyclerView.setAdapter(adapterWarpper);
        adapterWarpper.setOnItemClickListener(this);
        smartRefreshLayout.autoRefresh(500);

    }

    @Override
    protected void initToolbar() {
        tvTitle.setText("学习时长今日排名");
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.left_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {

    }

    int mPage = 1;

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mPage = 1;
        smartRefreshLayout.setEnableLoadmore(false);
        Map<String, String> params = new HashMap<>();
        params.put("uid", EnterCheckUtil.getInstance().getUid_Token()[0]);
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("page ", mPage + "");
        requestPresenter.doHttpData(Consts.BASE_URL + "/report/study_rank", Consts.REQUEST_METHOD_POST, Consts.REQUEST_REFRESH, params);
    }

    @Override
    public void onNetworkError() {
        finishRefreshLoad();
        ToastUtils.getInstance().showToast(this, "网络连接异常，请检查网络设置~");
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
                    StudySortBean searchResultBean = gson.fromJson(response, StudySortBean.class);

                    if (searchResultBean.getData().getList().getMy_rank() == 0) {
                        tvMyscore.setText("无");
                    } else {
                        tvMyscore.setText(searchResultBean.getData().getList().getMy_rank() + "");
                    }
                    tvMysort.setText(searchResultBean.getData().getList().getMy_study_time() + "");
                    if (searchResultBean.getData().getList().getRank_list().size() > 0) {
                        dataList.clear();
                        dataList.addAll(searchResultBean.getData().getList().getRank_list());
                    } else {
                        Toast.makeText(MyStudySortActivity.this, "暂无数据", Toast.LENGTH_SHORT).show();
                    }

                    adapterWarpper.notifyDataSetChanged();
                } else {
                    Toast.makeText(MyStudySortActivity.this, "数据异常", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MyStudySortActivity.this, "数据异常", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRefreshError() {
        finishRefreshLoad();
        ToastUtils.getInstance().showToast(this, "网络连接异常，请检查网络设置~");
    }

    @Override
    public void onLoadMoreSuccess(String response) {

    }

    @Override
    public void onLoadMoreError() {

    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
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
}
