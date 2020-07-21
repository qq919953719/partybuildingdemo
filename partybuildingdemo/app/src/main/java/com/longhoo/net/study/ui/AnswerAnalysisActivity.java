package com.longhoo.net.study.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.longhoo.net.R;
import com.longhoo.net.study.adapter.AnswerAnalysisAdapter;
import com.longhoo.net.study.bean.AnswerAnalysisBean;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.utils.httprequest.OkHttpCallback;
import com.longhoo.net.utils.httprequest.OkHttpUtil;
import com.longhoo.net.widget.CountdownTextView;
import com.longhoo.net.widget.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class AnswerAnalysisActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_count_down)
    CountdownTextView tvCountDown;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.list_panel)
    LinearLayout listPanel;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.tv_reload)
    TextView tvReload;
    @BindView(R.id.ll_net_error_panel)
    LinearLayout llNetErrorPanel;
    @BindView(R.id.countdown_panel)
    LinearLayout countdownPanel;
    private int id;
    private List<AnswerAnalysisBean.DataBean> dataList = new ArrayList<>();
    private AnswerAnalysisAdapter adapter;
    private String examTitle;
    private int examTime;
    public static final int TYPE_NORMAL_ANALYSIS = 0;
    public static final int TYPE_MYTEST_ANALYSIS = 1;
    private int type;

    public static void goToNoraml(Context context, int id, String examTitle, int examTime) {
        Intent intent = new Intent(context, AnswerAnalysisActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("examTitle", examTitle);
        intent.putExtra("examTime", examTime);
        intent.putExtra("type",TYPE_NORMAL_ANALYSIS);
        context.startActivity(intent);
    }

    public static void goToMyTest(Context context, int id, String examTitle) {
        Intent intent = new Intent(context, AnswerAnalysisActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("examTitle", examTitle);
        intent.putExtra("type",TYPE_MYTEST_ANALYSIS);
        context.startActivity(intent);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_answer_analysis;
    }

    @Override
    protected void initViews() {
        if (getIntent() != null) {
            examTitle = getIntent().getStringExtra("examTitle");
            id = getIntent().getIntExtra("id", 0);
            examTime = getIntent().getIntExtra("examTime", 0);
            type = getIntent().getIntExtra("type",TYPE_NORMAL_ANALYSIS);
            tvCountDown.init(null, examTime * 60, null);
        }
        if (examTitle != null) {
            if (examTitle.length() >= 12) {
                examTitle = examTitle.substring(0, 9) + "...";
            }
            tvTitle.setText(examTitle);
        }
        if(type==TYPE_NORMAL_ANALYSIS){
            getNormalData();
        }else{
            getMyTestData();
        }
        tvReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type==TYPE_NORMAL_ANALYSIS){
                    getNormalData();
                }else{
                    getMyTestData();
                }
            }
        });
    }

    private void getNormalData() {
        String url = Consts.BASE_URL + "/exam/exam_user_info";
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("id", String.valueOf(id));
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                if (isFinishing()) {
                    return;
                }
                ULog.e("ck", "考题分析：" + response);
                List<AnswerAnalysisBean.DataBean> tempList = null;
                Gson gson = new Gson();
                try {
                    AnswerAnalysisBean analysisBean = gson.fromJson(response, AnswerAnalysisBean.class);
                    String code = analysisBean.getCode();
                    if (!TextUtils.equals(code, "0")) {
                        ToastUtils.getInstance().showToast(AnswerAnalysisActivity.this, "获取数据失败~");
                        llNetErrorPanel.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        listPanel.setVisibility(View.GONE);
                        return;
                    }
                    tempList = analysisBean.getData();
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
                if (tempList == null) {
                    ToastUtils.getInstance().showToast(AnswerAnalysisActivity.this, "服务器异常~");
                    llNetErrorPanel.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    listPanel.setVisibility(View.GONE);
                    return;
                }
                dataList.clear();
                if (tempList.size() > 0) {
                    dataList.addAll(tempList);
                }
                progressBar.setVisibility(View.GONE);
                llNetErrorPanel.setVisibility(View.GONE);
                listPanel.setVisibility(View.VISIBLE);
                //加载列表
                LinearLayoutManager manager = new LinearLayoutManager(AnswerAnalysisActivity.this);
                manager.setOrientation(LinearLayoutManager.VERTICAL);
                adapter = new AnswerAnalysisAdapter(AnswerAnalysisActivity.this, dataList);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(String errorMsg) {
                if (isFinishing()) {
                    return;
                }
                progressBar.setVisibility(View.GONE);
                llNetErrorPanel.setVisibility(View.VISIBLE);
                listPanel.setVisibility(View.GONE);
            }
        });
    }

    private void getMyTestData() {
        String url = Consts.BASE_URL + "/exam/exam_rank_info";
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("euid", String.valueOf(id));
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                if (isFinishing()) {
                    return;
                }
                ULog.e("ck", "考题分析：" + response);
                List<AnswerAnalysisBean.DataBean> tempList = null;
                Gson gson = new Gson();
                try {
                    AnswerAnalysisBean analysisBean = gson.fromJson(response, AnswerAnalysisBean.class);
                    String code = analysisBean.getCode();
                    if (!TextUtils.equals(code, "0")) {
                        ToastUtils.getInstance().showToast(AnswerAnalysisActivity.this, "获取数据失败~");
                        llNetErrorPanel.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        listPanel.setVisibility(View.GONE);
                        return;
                    }
                    tempList = analysisBean.getData();
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
                if (tempList == null) {
                    ToastUtils.getInstance().showToast(AnswerAnalysisActivity.this, "服务器异常~");
                    llNetErrorPanel.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    listPanel.setVisibility(View.GONE);
                    return;
                }
                dataList.clear();
                if (tempList.size() > 0) {
                    dataList.addAll(tempList);
                }
                progressBar.setVisibility(View.GONE);
                llNetErrorPanel.setVisibility(View.GONE);
                listPanel.setVisibility(View.VISIBLE);
                //加载列表
                LinearLayoutManager manager = new LinearLayoutManager(AnswerAnalysisActivity.this);
                manager.setOrientation(LinearLayoutManager.VERTICAL);
                adapter = new AnswerAnalysisAdapter(AnswerAnalysisActivity.this, dataList);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(String errorMsg) {
                if (isFinishing()) {
                    return;
                }
                progressBar.setVisibility(View.GONE);
                llNetErrorPanel.setVisibility(View.VISIBLE);
                listPanel.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void initToolbar() {
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
}
