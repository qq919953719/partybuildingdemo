package com.longhoo.net.mine.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.longhoo.net.MyApplication;
import com.longhoo.net.R;
import com.longhoo.net.mine.bean.MyReportIndexBean;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.utils.httprequest.OkHttpCallback;
import com.longhoo.net.utils.httprequest.OkHttpUtil;
import com.longhoo.net.widget.base.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyStudyReportIndexActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_tip_score)
    TextView tvTipScore;
    @BindView(R.id.tv_mydaytime)
    TextView tvMydaytime;
    @BindView(R.id.tv_myallscore)
    TextView tvMyallscore;
    @BindView(R.id.tv_readnum)
    TextView tvReadnum;
    @BindView(R.id.tv_readtime)
    TextView tvReadtime;
    @BindView(R.id.tv_questionnum)
    TextView tvQuestionnum;
    @BindView(R.id.tv_questimetime)
    TextView tvQuestimetime;
    @BindView(R.id.tv_videonum)
    TextView tvVideonum;
    @BindView(R.id.tv_videotime)
    TextView tvVideotime;
    @BindView(R.id.tv_xindenum)
    TextView tvXindenum;
    @BindView(R.id.tv_xindetime)
    TextView tvXindetime;
    @BindView(R.id.tv_sort)
    TextView tvSort;
    @BindView(R.id.lv_readinfo)
    LinearLayout lvReadinfo;
    @BindView(R.id.lv_videoinfo)
    LinearLayout lvVideoinfo;
    @BindView(R.id.lv_questioninfo)
    LinearLayout lvQuestioninfo;
    @BindView(R.id.lv_xindeinfo)
    LinearLayout lvXindeinfo;
    @BindView(R.id.lv_allstudy_time)
    LinearLayout lvAllstudyTime;

    @Override
    protected int getContentId() {
        return R.layout.activity_my_study_report_index;
    }

    @Override
    protected void initViews() {
        getStudyInfo();
        tvSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent11 = new Intent(MyStudyReportIndexActivity.this, MyStudySortActivity.class);
                startActivity(intent11);
            }
        });
    }

    @Override
    protected void initToolbar() {
        tvTitle.setText("学习报告");
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


    void getStudyInfo() {

        MyApplication App = (MyApplication) this.getApplicationContext();
        if (App.getmLoginBean() == null) {
            ToastUtils.getInstance().showToast(this, "登录过期，请重新登陆！");
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("uid", String.valueOf(App.getmLoginBean().getData().getUid()));
        map.put("token", String.valueOf(App.getmLoginBean().getData().getToken()));
        OkHttpUtil.getInstance().doAsyncPost(Consts.BASE_URL + "/report/myreport", map, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                ULog.e("我的学习报告：" + response);
                try {
                    JSONObject obj = new JSONObject(response);
                    if (obj.has("code")) {
                        if (obj.getString("code").equals("0")) {
                            Gson gson = new Gson();
                            final MyReportIndexBean mMyReportIndexBean = gson.fromJson(response, MyReportIndexBean.class);
                            tvMydaytime.setText(mMyReportIndexBean.getData().getSameDay_time() + "");
                            tvMyallscore.setText(mMyReportIndexBean.getData().getCount_time() + "");

                            tvReadnum.setText(mMyReportIndexBean.getData().getCat_learn_report().get(0).getNum() + "");
                            tvReadtime.setText(mMyReportIndexBean.getData().getCat_learn_report().get(0).getTime() + "");

                            tvVideonum.setText(mMyReportIndexBean.getData().getCat_learn_report().get(2).getNum() + "");
                            tvVideotime.setText(mMyReportIndexBean.getData().getCat_learn_report().get(2).getTime() + "");

                            tvQuestionnum.setText(mMyReportIndexBean.getData().getCat_learn_report().get(1).getNum() + "");
                            tvQuestimetime.setText(mMyReportIndexBean.getData().getCat_learn_report().get(1).getTime() + "");

                            tvXindenum.setText(mMyReportIndexBean.getData().getCat_learn_report().get(3).getNum() + "");
                            tvXindetime.setText(mMyReportIndexBean.getData().getCat_learn_report().get(4).getNum() + "");


                            lvReadinfo.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent11 = new Intent(MyStudyReportIndexActivity.this, MyStudyReportActivity.class);
                                    intent11.putExtra("type", mMyReportIndexBean.getData().getCat_learn_report().get(0).getType() + "");
                                    intent11.putExtra("title", "阅读统计");
                                    startActivity(intent11);
                                }
                            });
                            lvVideoinfo.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent11 = new Intent(MyStudyReportIndexActivity.this, MyStudyReportActivity.class);
                                    intent11.putExtra("type", mMyReportIndexBean.getData().getCat_learn_report().get(1).getType() + "");
                                    intent11.putExtra("title", "视频统计");
                                    startActivity(intent11);
                                }
                            });
                            lvQuestioninfo.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent11 = new Intent(MyStudyReportIndexActivity.this, MyStudyReportActivity.class);
                                    intent11.putExtra("type", mMyReportIndexBean.getData().getCat_learn_report().get(2).getType() + "");
                                    intent11.putExtra("title", "答题统计");
                                    startActivity(intent11);
                                }
                            });
//                            lvXindeinfo.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    Intent intent11 = new Intent(MyStudyReportIndexActivity.this, MyStudyReportActivity.class);
//                                    intent11.putExtra("type", mMyReportIndexBean.getData().getCat_learn_report().get(3).getType() + "");
//                                    intent11.putExtra("title", "心得统计");
//                                    startActivity(intent11);
//                                }
//                            });
                            lvAllstudyTime.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent11 = new Intent(MyStudyReportIndexActivity.this, MyStudyReportActivity.class);
                                    intent11.putExtra("type", "");
                                    intent11.putExtra("title", "总时长统计");
                                    startActivity(intent11);
                                }
                            });
                        } else {
                            ToastUtils.getInstance().showToast(MyStudyReportIndexActivity.this, "暂无数据~");
                        }
                    } else {
                        ToastUtils.getInstance().showToast(MyStudyReportIndexActivity.this, "暂无数据~");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(MyStudyReportIndexActivity.this, "网络错误~");

            }
        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
