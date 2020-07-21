package com.longhoo.net.study.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.longhoo.net.R;
import com.longhoo.net.study.bean.ExamInfoBean;
import com.longhoo.net.study.bean.ExaminationListBean;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.utils.httprequest.OkHttpCallback;
import com.longhoo.net.utils.httprequest.OkHttpUtil;
import com.longhoo.net.widget.base.BaseActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class ExaminationDetailActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.tv_num_subject)
    TextView tvNumSubject;
    @BindView(R.id.tv_all_time)
    TextView tvAllTime;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.tv_num_people)
    TextView tvNumPeople;
    @BindView(R.id.tv_test)
    TextView tvTest;
    public static final String MSG_ADD_NUM = "msg_add_num";
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_exam_time)
    TextView tvExamTime;
    @BindView(R.id.tv_exam_score)
    TextView tvExamScore;
    @BindView(R.id.exam_time_panel)
    LinearLayout examTimePanel;
    @BindView(R.id.exam_score_panel)
    LinearLayout examScorePanel;
    @BindView(R.id.tv_exam_count)
    TextView tvExamCount;
    @BindView(R.id.line_1)
    View line1;
    @BindView(R.id.line_2)
    View line2;
    private int id;
    private String examTitle = "";
    private int examScore;
    private int examNum;
    private int examTime;
    private String examDesc = "";
    private int times;  //考试总次数
    private int leftTimes; // 剩余考试次数
//    private int current;
//    private int total;

    @Override
    protected int getContentId() {
        return R.layout.activity_examination_detail;
    }

    public static void goTo(Context context, String examId) {
        Intent intent = new Intent(context, ExaminationDetailActivity.class);
        intent.putExtra("exam_id", examId);
        context.startActivity(intent);
    }

    @Override
    protected void initViews() {
        if (getIntent() != null) {
            id = getIntent().getIntExtra("exam_id", 0);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getExamDetail();
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

    @OnClick({R.id.tv_num_people, R.id.tv_test})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_test:
                Intent intent = new Intent(this, QuestionActivity.class);
                intent.putExtra("question_id", id);
                intent.putExtra("question_title", examTitle);
                intent.putExtra("question_time", examTime);
                intent.putExtra("question_num", examNum);
                startActivity(intent);
                break;
        }
    }

    /**
     * 获取考试详情
     */
    private void getExamDetail() {
        String url = Consts.BASE_URL + "/exam/exam_info";
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("id", String.valueOf(id));
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                if (isFinishing()) {
                    return;
                }
                ULog.e(Consts.TAG, "考试详情数据" + ":" + response);
                //处理数据
                Gson gson = new Gson();
                try {
                    ExamInfoBean infoBean = gson.fromJson(response, ExamInfoBean.class);
                    String code = infoBean.getCode();
                    if (!TextUtils.equals(code, "0")) {
                        ToastUtils.getInstance().showToast(ExaminationDetailActivity.this, "获取数据失败~");
                        return;
                    }
                    examTitle = infoBean.getData().getName();
                    examScore = infoBean.getData().getScore();
                    examNum = infoBean.getData().getQnum();
                    examTime = infoBean.getData().getExamtime();
                    examDesc = infoBean.getData().getDesc();
                    times = infoBean.getData().getTimes();
                    leftTimes = infoBean.getData().getLeft_times();
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
                tvScore.setText(examScore + "分");
                tvNumSubject.setText(examNum + "题");
                tvAllTime.setText(examTime + "分钟");
                if (TextUtils.isEmpty(examDesc)) {
                    tvDesc.setVisibility(View.GONE);
                } else {
                    tvDesc.setVisibility(View.VISIBLE);
                    tvDesc.setText(examDesc + "");
                }
                if (examTitle != null) {
                    if (examTitle.length() >= 12) {
                        examTitle = examTitle.substring(0, 9) + "...";
                    }
                    tvTitle.setText(examTitle);
                }
                tvTest.setEnabled(true);
                if (leftTimes > 0) {
                    tvExamCount.setTextColor(getResources().getColor(R.color.tv_content_color));
                    tvTest.setText("立即测试");
                } else {
                    tvExamCount.setTextColor(getResources().getColor(R.color.colorPrimary));
                    tvTest.setText("考试次数已用完");
                    tvTest.setEnabled(false);
                }
                tvExamCount.setText(leftTimes + "/" + times);
            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(ExaminationDetailActivity.this, "数据异常，获取数据失败~");
            }
        });

    }

}
