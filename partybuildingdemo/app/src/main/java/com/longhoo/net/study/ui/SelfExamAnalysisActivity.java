package com.longhoo.net.study.ui;

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
import com.longhoo.net.study.adapter.SelfExamAnalysisAdapter;
import com.longhoo.net.study.bean.SelfAnswerAnalysisBean;
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

public class SelfExamAnalysisActivity extends BaseActivity {

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
    private int id;
    private List<SelfAnswerAnalysisBean.DataBean.ListBean> dataList = new ArrayList<>();
    private SelfExamAnalysisAdapter adapter;
    private String examTitle;
    private String examTime;
    @Override
    protected int getContentId() {
        return R.layout.activity_self_exam_analysis;
    }

    @Override
    protected void initViews() {
        if (getIntent() != null) {
            examTitle = getIntent().getStringExtra("question_title");
            id = getIntent().getIntExtra("question_id",0);
            examTime = getIntent().getStringExtra("question_time");
            try {
                tvCountDown.init(null,Long.parseLong(examTime)*60,null);
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
        }
        if (examTitle != null) {
            if (examTitle.length() >= 12) {
                examTitle = examTitle.substring(0, 9) + "...";
            }
            tvTitle.setText(examTitle);
        }
        getData();
        tvReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });
    }

    private void getData(){
        String url = Consts.BASE_URL +"/exam/examinfo_lists";
        Map<String,String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("id",String.valueOf(id));
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                if(isFinishing()){
                    return;
                }
                ULog.e("ck","考题分析："+response);
                List<SelfAnswerAnalysisBean.DataBean.ListBean> tempList = null;
                Gson gson = new Gson();
                try {
                    SelfAnswerAnalysisBean analysisBean = gson.fromJson(response, SelfAnswerAnalysisBean.class);
                    String code = analysisBean.getCode();
                    if (!TextUtils.equals(code, "0")) {
                        ToastUtils.getInstance().showToast(SelfExamAnalysisActivity.this, "获取数据失败~");
                        llNetErrorPanel.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        listPanel.setVisibility(View.GONE);
                        return;
                    }
                    tempList = analysisBean.getData().getList();
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
                if (tempList == null) {
                    ToastUtils.getInstance().showToast(SelfExamAnalysisActivity.this, "服务器异常~");
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
                LinearLayoutManager manager = new LinearLayoutManager(SelfExamAnalysisActivity.this);
                manager.setOrientation(LinearLayoutManager.VERTICAL);
                adapter = new SelfExamAnalysisAdapter(SelfExamAnalysisActivity.this,dataList);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(String errorMsg) {
                if(isFinishing()){
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
