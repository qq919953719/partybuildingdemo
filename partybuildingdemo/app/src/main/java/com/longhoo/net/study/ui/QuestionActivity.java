package com.longhoo.net.study.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.longhoo.net.R;
import com.longhoo.net.headline.adapter.MainFragmentPagerAdapter;
import com.longhoo.net.partyaffairs.bean.QuestionBean;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.MessageEvent;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.utils.Utils;
import com.longhoo.net.utils.httprequest.OkHttpCallback;
import com.longhoo.net.utils.httprequest.OkHttpUtil;
import com.longhoo.net.widget.CountdownTextView;
import com.longhoo.net.widget.NoScrollViewPager;
import com.longhoo.net.widget.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class QuestionActivity extends BaseActivity implements ViewPager.OnPageChangeListener, CountdownTextView.OnTimeOutListener {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_count_down)
    CountdownTextView tvCountDown;
    @BindView(R.id.viewpager)
    NoScrollViewPager viewPager;
    @BindView(R.id.panel)
    LinearLayout panel;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.tv_reload)
    TextView tvReload;
    @BindView(R.id.ll_net_error_panel)
    LinearLayout llNetErrorPanel;
    @BindView(R.id.tv_next)
    TextView tvNext;
    @BindView(R.id.tv_post_exam)
    TextView tvPostExam;
    private String title = "";
    private int id;
    private int examTime;
    private int totalNum = 0; //总题数
    private List<QuestionBean.DataBean.ListsBean> dataList = new ArrayList<>();
    private MainFragmentPagerAdapter adapter;
    private int curIndex;
    private String value = "0"; //答案
    private AlertDialog alertDialog; //总得分的弹窗
    private ProgressDialog progressDialog; //提交试卷的dilog

    @Override
    protected int getContentId() {
        return R.layout.activity_question;
    }

    @Override
    protected void initViews() {
        EventBus.getDefault().register(this);
        if (getIntent() != null) {
            title = getIntent().getStringExtra("question_title");
            id = getIntent().getIntExtra("question_id", 0);
            examTime = getIntent().getIntExtra("question_time", 0);
            totalNum = getIntent().getIntExtra("question_num", 0);
        }
        ULog.e("ck", "total_num:" + totalNum);
        if (title != null) {
            if (title.length() >= 12) {
                title = title.substring(0, 9) + "...";
            }
            tvTitle.setText(title);
        }
        getQuestionList();
        tvReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getQuestionList();
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

    @OnClick({R.id.tv_reload, R.id.tv_next, R.id.tv_post_exam})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_reload:
                getQuestionList();
                break;
            case R.id.tv_next:
                ULog.e("ck", curIndex + " " + value);
//                if (tvCountDown.isStop()) {
//                    ToastUtils.getInstance().showToast(this, "答题时间到，计算总得分中~");
//                    doPostAnswer("考试时间到，提交试卷中...");
//                    return;
//                }
                doPostAnswer(null);
                break;
            case R.id.tv_post_exam:
                break;
        }
    }

    private void getQuestionList() {
        progressBar.setVisibility(View.VISIBLE);
        String url = Consts.BASE_URL + "/exam/exam_begin";
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(id));
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                if (isFinishing()) {
                    return;
                }
                ULog.e("ck", "考题：" + response);
                List<QuestionBean.DataBean.ListsBean> tempList = null;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.optString("code");
                    String msg = jsonObject.optString("msg");
                    if (TextUtils.equals(code, "1")) {
                        //已答过
                        //ToastUtils.getInstance().showToast(QuestionActivity.this, msg);
                        AnswerAnalysisActivity.goToNoraml(QuestionActivity.this, id, tvTitle.getText().toString().trim(), examTime);
                        finish();
                        return;
                    } else if (TextUtils.equals(code, "9")) {
                        //考试已结束，无法考试
                        ToastUtils.getInstance().showToast(QuestionActivity.this, msg);
                        finish();
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    finish();
                    return;
                }
                Gson gson = new Gson();
                try {
                    QuestionBean questionBean = gson.fromJson(response, QuestionBean.class);
                    String code = questionBean.getCode();
                    String msg = questionBean.getMsg();
                    if (!TextUtils.equals(code, "0")) {
                        ToastUtils.getInstance().showToast(QuestionActivity.this, msg);
                        llNetErrorPanel.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        panel.setVisibility(View.GONE);
                        tvNext.setEnabled(false);
                        return;
                    }
                    tempList = questionBean.getData().getLists();
                    long leftTime = questionBean.getData().getLeftime();
                    tvCountDown.init(null, leftTime, QuestionActivity.this);
                    tvCountDown.start(0);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
                if (tempList == null) {
                    ToastUtils.getInstance().showToast(QuestionActivity.this, "服务器异常，获取考题失败~");
                    llNetErrorPanel.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    panel.setVisibility(View.GONE);
                    tvNext.setEnabled(false);
                    return;
                }
                if (tempList.size() <= 0) {
                    ToastUtils.getInstance().showToast(QuestionActivity.this, "暂无考题，无法考试！");
                    progressBar.setVisibility(View.GONE);
                    panel.setVisibility(View.GONE);
                    tvNext.setEnabled(false);
                    return;
                }
                if (tempList.size() > 0) {
                    dataList.clear();
                    dataList.addAll(tempList);
                }
                progressBar.setVisibility(View.GONE);
                llNetErrorPanel.setVisibility(View.GONE);
                panel.setVisibility(View.VISIBLE);
                //加载viewpager
                List<Fragment> fragmentList = new ArrayList<>();
                int size = dataList.size();
                for (int i = 0; i < size; i++) {
                    fragmentList.add(QuestionFragment.newInstance(totalNum == 0 ? (i + 1) : (totalNum - size + 1 + i), (dataList.get(i))));
                }
                ULog.e("ck", "考题数量：" + size);
                adapter = new MainFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
                viewPager.setAdapter(adapter);
                viewPager.addOnPageChangeListener(QuestionActivity.this);
                viewPager.setNoScroll(true);
                viewPager.setCurrentItem(0);
            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(QuestionActivity.this, "网络异常，获取考题失败~");
                if (isFinishing()) {
                    return;
                }
                progressBar.setVisibility(View.GONE);
                llNetErrorPanel.setVisibility(View.VISIBLE);
                panel.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onDestroy() {
        tvCountDown.stop();
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        hideSignDialog();
        hideProgressDialog();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        value = event.message;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        curIndex = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 单个题目提交答案
     */
    private void doPostAnswer(String hint) {
        if (TextUtils.isEmpty(hint)) {
            tvNext.setText("本题答案提交中...");
        }
        tvNext.setEnabled(false);
        if (curIndex < 0 || curIndex > dataList.size() - 1) {
            return;
        }
        String url = Consts.BASE_URL + "/exam/exam_submit";
        int qid = dataList.get(curIndex).getId();
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("id", String.valueOf(id));
        params.put("qid", String.valueOf(qid));
        params.put("answer", value);
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                if (isFinishing()) {
                    return;
                }
                ULog.e("ck", "提交答案：" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.optString("code");
                    String msg = jsonObject.optString("msg");
                    if (TextUtils.equals(code, "2")) { //提交的是最后一题，显示得分详情
                        JSONObject data = jsonObject.optJSONObject("data");
                        if (data != null) {
                            int score = data.optInt("score");
                            int right = data.optInt("right");
                            int wrong = data.optInt("wrong");
                            showSignDialog(String.valueOf(score), String.valueOf(right), String.valueOf(wrong));
                            tvNext.setEnabled(false);
                            tvCountDown.stop();
                            tvNext.setText("确定");
                            return;
                        }
                    } else if (TextUtils.equals(code, "0")) {
                        tvNext.setText("下一题");
                    } else if (TextUtils.equals(code, "9")) {
                        tvNext.setText("确定");
                    }
                    if (curIndex < dataList.size() - 1) {
                        viewPager.setCurrentItem(++curIndex);
                    }
                    if (curIndex == dataList.size() - 1) {
                        tvNext.setText("提交");
                    }
                    value = "0";
                    tvNext.setEnabled(true);
                    ToastUtils.getInstance().showToast(QuestionActivity.this, msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtils.getInstance().showToast(QuestionActivity.this, "提交失败，请重试~");
                    tvNext.setEnabled(true);
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(QuestionActivity.this, "提交失败，请重试~");
                if (isFinishing()) {
                    return;
                }
                tvNext.setText("确定");
                tvNext.setEnabled(true);
            }
        });
    }

    /**
     * 单个题目提交答案
     */
    private void onTimeOutPostAnswer() {
        tvNext.setText("考试时间到，提交试卷中...");
        tvNext.setEnabled(false);
        if (curIndex < 0 || curIndex > dataList.size() - 1) {
            return;
        }
        String url = Consts.BASE_URL + "/exam/exam_submit";
        int qid = dataList.get(curIndex).getId();
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("id", String.valueOf(id));
        params.put("qid", String.valueOf(qid));
        params.put("answer", value);
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                if (isFinishing()) {
                    return;
                }
                ULog.e("ck", "提交答案：" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.optString("code");
                    String msg = jsonObject.optString("msg");
                    if (TextUtils.equals(code, "2")) { //提交的是最后一题，显示得分详情
                        JSONObject data = jsonObject.optJSONObject("data");
                        if (data != null) {
                            int score = data.optInt("score");
                            int right = data.optInt("right");
                            int wrong = data.optInt("wrong");
                            showSignDialog(String.valueOf(score), String.valueOf(right), String.valueOf(wrong));
                            tvNext.setEnabled(false);
                            tvCountDown.stop();
                            tvNext.setText("确定");
                            return;
                        }
                    }
                    tvNext.setText("确定");
                    value = "0";
                    tvNext.setEnabled(true);
                    ToastUtils.getInstance().showToast(QuestionActivity.this, msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtils.getInstance().showToast(QuestionActivity.this, "提交失败，请重试~");
                    tvNext.setEnabled(true);
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(QuestionActivity.this, "提交失败，请重试~");
                if (isFinishing()) {
                    return;
                }
                tvNext.setText("确定");
                tvNext.setEnabled(true);
            }
        });
    }


//    /**
//     * 先提交单个题目，再提交答案
//     */
//    private void doPostAll(String msg) {
//        tvNext.setText(msg);
//        tvNext.setEnabled(false);
//        if (curIndex < 0 || curIndex > dataList.size() - 1) {
//            tvNext.setEnabled(true);
//            return;
//        }
//        String url = ExaminationCenterActivity.BASE_EXAM_URL + "/get_answer";
//        int qid = dataList.get(curIndex).getId();
//        Map<String, String> params = new HashMap<>();
//        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
//        params.put("id", id);
//        params.put("qid", qid);
//        params.put("answer", value);
//        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
//            @Override
//            public void onSuccess(String response) {
//                if (isFinishing()) {
//                    return;
//                }
//                ULog.e("ck", "提交答案：" + response);
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    String code = jsonObject.optString("code");
//                    String msg = jsonObject.optString("msg");
//                    tvNext.setText("下一题");
//                    //ToastUtils.getInstance().showToast(QuestionActivity.this, msg);
//                    doPostExam(true, "提交试卷中...");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    //ToastUtils.getInstance().showToast(QuestionActivity.this, "提交失败，请重试~");
//                    doPostExam(true, "提交试卷中...");
//                }
//            }
//
//            @Override
//            public void onFailure(String errorMsg) {
//                ToastUtils.getInstance().showToast(QuestionActivity.this, "提交失败，请重试~");
//                if (isFinishing()) {
//                    return;
//                }
//                tvNext.setText("下一题");
//            }
//        });
//    }


//    private void doCountScore() {
//        tvNext.setText("...计算得分情况中");
//        tvNext.setEnabled(false);
//        String url = ExaminationCenterActivity.BASE_EXAM_URL + "/exam_result";
//        Map<String, String> params = new HashMap<>();
//        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
//        params.put("pid", id);
//        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
//            @Override
//            public void onSuccess(String response) {
//                if (isFinishing()) {
//                    return;
//                }
//                ULog.e("ck", "得分情况：" + response);
//                tvNext.setText("确定");
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    String code = jsonObject.optString("code");
//                    String msg = jsonObject.optString("msg");
//                    if (!TextUtils.equals(code, "0")) {
//                        ToastUtils.getInstance().showToast(QuestionActivity.this, msg);
//                        tvNext.setEnabled(true);
//                        return;
//                    }
//                    JSONObject data = jsonObject.optJSONObject("data");
//                    String score = "", right = "", wrong = "";
//                    if (data != null) {
//                        score = data.optString("score");
//                        right = data.optString("right");
//                        wrong = data.optString("wrong");
//                    }
//                    showSignDialog(score, right, wrong);
//                    tvNext.setEnabled(false);
//                    tvCountDown.stop();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    tvNext.setEnabled(true);
//                    ToastUtils.getInstance().showToast(QuestionActivity.this, "服务器异常，请重新提交~");
//                }
//            }
//
//            @Override
//            public void onFailure(String errorMsg) {
//                ToastUtils.getInstance().showToast(QuestionActivity.this, "网络异常，请重新提交~");
//                if (isFinishing()) {
//                    return;
//                }
//                tvNext.setText("确定");
//                tvNext.setEnabled(true);
//            }
//        });
//    }

    //    /**
//     * 提交试卷(直接跳转到答案解析界面)
//     */
//    /**
//     * @param isNeedCountScore 是否需要计算得分情况
//     * @param msg
//     */
//    private void doPostExam(final boolean isNeedCountScore, String msg) {
//        if (!isNeedCountScore) {
//            showProgressDialog(msg);
//        }
//        tvPostExam.setEnabled(false);
//        String url = ExaminationCenterActivity.BASE_EXAM_URL + "/get_all_exam";
//        Map<String, String> params = new HashMap<>();
//        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
//        params.put("id", id);
//        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
//            @Override
//            public void onSuccess(String response) {
//                if (isFinishing()) {
//                    return;
//                }
//                ULog.e("ck", "提交试卷：" + response);
//                hideProgressDialog();
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    String code = jsonObject.optString("code");
//                    if (!TextUtils.equals(code, "0")) {
//                        ToastUtils.getInstance().showToast(QuestionActivity.this, "提交失败，请重新提交~");
//                        return;
//                    }
//                    if (isNeedCountScore) {
//                        doCountScore();
//                    } else {
//                        //进入答案解析界面
//                        Intent intent = new Intent(QuestionActivity.this, AnswerAnalysisActivity.class);
//                        intent.putExtra("question_title", tvTitle.getText().toString().trim());
//                        intent.putExtra("question_id", id);
//                        intent.putExtra("question_time", examTime);
//                        startActivity(intent);
//                        finish();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    tvNext.setEnabled(true);
//                    ToastUtils.getInstance().showToast(QuestionActivity.this, "服务器异常，请重新提交~");
//                }
//            }
//
//            @Override
//            public void onFailure(String errorMsg) {
//                ToastUtils.getInstance().showToast(QuestionActivity.this, "网络异常，请重新提交~");
//                if (isFinishing()) {
//                    return;
//                }
//                hideProgressDialog();
//            }
//        });
//    }
    private void showSignDialog(String score, String right, String wrong) {
        alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface dialog, int keyCode,
                                 KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK
                        && event.getRepeatCount() == 0) {
                    finish();
                }
                return false;
            }
        });
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(false);
        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.alert_exam_score);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = Utils.getDeviceSize(this).x * 650 / 750;
        window.setAttributes(params);
        TextView tvScore = (TextView) alertDialog.findViewById(R.id.tv_score);
        TextView tvRight = (TextView) alertDialog.findViewById(R.id.tv_right);
        TextView tvWrong = (TextView) alertDialog.findViewById(R.id.tv_wrong);
        TextView tvAnalysis = (TextView) alertDialog.findViewById(R.id.tv_analysis);
        tvAnalysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnswerAnalysisActivity.goToNoraml(QuestionActivity.this, id, tvTitle.getText().toString().trim(), examTime);
                finish();
            }
        });
        tvScore.setText("考试成绩：" + score + "分");
        tvRight.setText("答    对：" + right + "题");
        tvWrong.setText("答    错：" + wrong + "题");
    }

    private void hideSignDialog() {
        if (alertDialog != null) {
            if (alertDialog.isShowing()) {
                alertDialog.dismiss();
            }
        }
    }

    private void showProgressDialog(String msg) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
        }
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void hideProgressDialog() {
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }

    @Override
    public void onTimeOut() {
        ToastUtils.getInstance().showToast(this, "答题时间到，计算总得分中~");
        onTimeOutPostAnswer();
    }
}
