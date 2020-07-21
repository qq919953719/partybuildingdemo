package com.longhoo.net.manageservice.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.longhoo.net.MyApplication;
import com.longhoo.net.R;
import com.longhoo.net.manageservice.bean.PersonalInfoBean;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.GlideManager;
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

/**
 * Created by ${CC} on 2017/12/18.
 */

public class OrgazationPersonalActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_contenttitle)
    ImageView tvContenttitle;
    @BindView(R.id.tv_membername)
    TextView tvMembername;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_organization)
    TextView tvOrganization;
    @BindView(R.id.meetingname)
    TextView meetingname;
    @BindView(R.id.lv_meetingtype)
    LinearLayout lvMeetingtype;
    @BindView(R.id.tv_nation)
    TextView tvNation;
    @BindView(R.id.tv_birthday)
    TextView tvBirthday;
    @BindView(R.id.tv_borth_place)
    TextView tvBorthPlace;
    @BindView(R.id.tv_education_type)
    TextView tvEducationType;
    @BindView(R.id.tv_education_date)
    TextView tvEducationDate;
    @BindView(R.id.tv_education)
    TextView tvEducation;
    @BindView(R.id.tv_education_most)
    TextView tvEducationMost;
    @BindView(R.id.tv_biye)
    TextView tvBiye;
    @BindView(R.id.tv_joinjobtime)
    TextView tvJoinjobtime;
    @BindView(R.id.tv_orgunongroup)
    TextView tvOrgunongroup;
    @BindView(R.id.tv_jobstart)
    TextView tvJobstart;
    @BindView(R.id.tv_job)
    TextView tvJob;
    @BindView(R.id.tv_onlinestate)
    TextView tvOnlinestate;
    @BindView(R.id.tv_newpeople)
    TextView tvNewpeople;
    @BindView(R.id.tv_joinmembertime)
    TextView tvJoinmembertime;
    @BindView(R.id.tv_joinmembertype)
    TextView tvJoinmembertype;
    @BindView(R.id.tv_changetime)
    TextView tvChangetime;
    @BindView(R.id.tv_changestate)
    TextView tvChangestate;
    @BindView(R.id.tv_zuozaiparty)
    TextView tvZuozaiparty;
    @BindView(R.id.tv_intopartydate)
    TextView tvIntopartydate;
    @BindView(R.id.tv_intopartytype)
    TextView tvIntopartytype;
    @BindView(R.id.tv_identifity)
    TextView tvIdentifity;
    @BindView(R.id.tv_place_police)
    TextView tvPlacePolice;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_place)
    TextView tvPlace;
    @BindView(R.id.tv_partyfee)
    TextView tvPartyfee;
    @BindView(R.id.tv_otherparty)
    TextView tvOtherparty;
    @BindView(R.id.tv_otherpartydate)
    TextView tvOtherpartydate;
    @BindView(R.id.scroll_view)
    ScrollView scrollView;
    @BindView(R.id.tv_publishphone)
    TextView tvPublishphone;
    private String strtoUid = "";

    @Override
    protected int getContentId() {
        return R.layout.activity_partymembersinformation;
    }

    protected void dialog(String strName, final String strPhoe) {
        AlertDialog.Builder builder = new AlertDialog.Builder(OrgazationPersonalActivity.this);
        builder.setMessage("您确定要拨打" + strName + "的电话么？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + strPhoe));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                dialog.dismiss();

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    @Override
    protected void initViews() {
        if (getIntent() != null) {
            strtoUid = getIntent().getStringExtra("did");
            if (getIntent().getStringExtra("type") != null) {
                if (getIntent().getStringExtra("type").equals("1")) {
                    tvPublishphone.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        GetPTCConetnt();
    }

    void GetPTCConetnt() {

        MyApplication App = (MyApplication) getApplicationContext();
        if (App.getmLoginBean() == null) {
            ToastUtils.getInstance().showToast(this, "登录过期，请重新登陆！");
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("uid", String.valueOf(strtoUid));
        map.put("token", String.valueOf(App.getmLoginBean().getData().getToken()));
        ULog.e("cc", Consts.BASE_URL + "/Org/details" + "\n" + String.valueOf(strtoUid) + "\n" + String.valueOf(App.getmLoginBean().getData().getToken()));
        OkHttpUtil.getInstance().doAsyncPost(Consts.BASE_URL + "/Org/details", map, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                ULog.e(Consts.TAG, "党员信息：" + response);
                try {
                    JSONObject obj = new JSONObject(response);
                    if (obj.has("code")) {
                        if (obj.getString("code").equals("0")) {
                            Gson gson = new Gson();
                            final PersonalInfoBean mPTCConetntBean = gson.fromJson(response, PersonalInfoBean.class);
                            lvMeetingtype.setVisibility(View.GONE);
                            if (TextUtils.isEmpty(mPTCConetntBean.getData().getHeadpic())) {
                                tvContenttitle.setVisibility(View.GONE);
                            } else {
                                tvContenttitle.setVisibility(View.VISIBLE);
                                GlideManager.getInstance().load(OrgazationPersonalActivity.this, mPTCConetntBean.getData().getHeadpic(), tvContenttitle);
                            }
//                            sex 性别 0未知 1男 2女
                            if (mPTCConetntBean.getData().getSex().equals("2")) {
                                tvSex.setText("性别：女");
                            } else if (mPTCConetntBean.getData().getSex().equals("1")) {
                                tvSex.setText("性别：男");
                            } else {
                                tvSex.setText("性别：保密");
                            }
                            tvMembername.setText(mPTCConetntBean.getData().getRealname() + "");
                            tvNation.setText(mPTCConetntBean.getData().getNation() + "");
                            tvBirthday.setText(mPTCConetntBean.getData().getBirthday() + "");
                            tvBorthPlace.setText(mPTCConetntBean.getData().getNative_place() + "");
                            tvEducationType.setText(mPTCConetntBean.getData().getEdu_cate() + "");
                            tvEducationDate.setText(mPTCConetntBean.getData().getStart_school() + "");
                            tvEducation.setText(mPTCConetntBean.getData().getEducation() + "");
                            tvEducationMost.setText(mPTCConetntBean.getData().getDegree() + "");
                            tvBiye.setText(mPTCConetntBean.getData().getFinish_school() + "");
                            tvJoinjobtime.setText(mPTCConetntBean.getData().getStart_work() + "");
                            tvOrgunongroup.setText(mPTCConetntBean.getData().getOrg_form_company() + "");
                            tvJobstart.setText(mPTCConetntBean.getData().getTake_office_time() + "");
                            tvJob.setText(mPTCConetntBean.getData().getPost_position() + "");
                            tvOnlinestate.setText(mPTCConetntBean.getData().getFrontline() + "");
                            tvNewpeople.setText(mPTCConetntBean.getData().getSocial_class() + "");
                            tvJoinmembertime.setText(mPTCConetntBean.getData().getJoin_party_date() + "");
                            tvJoinmembertype.setText(mPTCConetntBean.getData().getJoin_orgtype() + "");
                            tvChangetime.setText(mPTCConetntBean.getData().getOfficial_time() + "");
                            tvChangestate.setText(mPTCConetntBean.getData().getZzqk() + "");
                            tvZuozaiparty.setText(mPTCConetntBean.getData().getJoin_start_org() + "");
                            tvIntopartydate.setText(mPTCConetntBean.getData().getEnter_time() + "");
                            tvIntopartytype.setText(mPTCConetntBean.getData().getJoin_party_branch_type() + "");
                            tvIdentifity.setText(mPTCConetntBean.getData().getIdentity() + "");
                            tvPlacePolice.setText(mPTCConetntBean.getData().getHk_police_office() + "");
                            tvPhone.setText(mPTCConetntBean.getData().getMobile() + "");
                            tvPlace.setText(mPTCConetntBean.getData().getAddress() + "");
                            tvPartyfee.setText(mPTCConetntBean.getData().getFee() + "");
                            tvOtherparty.setText(mPTCConetntBean.getData().getOther_caucus() + "");
                            tvOtherpartydate.setText(mPTCConetntBean.getData().getStart_other_caucus() + "");

                            tvOrganization.setText(mPTCConetntBean.getData().getOname() + "");


                            tvPublishphone.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog(mPTCConetntBean.getData().getRealname(), mPTCConetntBean.getData().getPhone());
                                }
                            });
                        } else {
                            Toast.makeText(OrgazationPersonalActivity.this, "数据异常", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(OrgazationPersonalActivity.this, "数据异常", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(OrgazationPersonalActivity.this, "网络错误~");

            }
        });


    }

    @Override
    protected void initToolbar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        tvTitle.setText("党员基本信息");
        toolbar.setNavigationIcon(R.drawable.left_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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
