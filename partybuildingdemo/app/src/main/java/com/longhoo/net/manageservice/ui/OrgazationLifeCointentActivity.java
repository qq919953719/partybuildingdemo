package com.longhoo.net.manageservice.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.longhoo.net.R;
import com.longhoo.net.manageservice.bean.OrgazationLifeCointentBean;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.utils.Utils;
import com.longhoo.net.utils.httprequest.OkHttpCallback;
import com.longhoo.net.utils.httprequest.OkHttpUtil;
import com.longhoo.net.widget.base.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jzvd.JZVideoPlayer;

/**
 * Created by ${CC} on 2017/12/15.
 * 三会一课
 */

public class OrgazationLifeCointentActivity extends BaseActivity {
    @BindView(R.id.tv_contenttitle)
    TextView tvContenttitle;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.meetingname)
    TextView meetingname;
    @BindView(R.id.meetingadress)
    TextView meetingadress;
    @BindView(R.id.meetinghostname)
    TextView meetinghostname;
    @BindView(R.id.meetingrecordname)
    TextView meetingrecordname;
    @BindView(R.id.invitenamemeeting)
    TextView invitenamemeeting;
    String strDid;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.lv_meetingtype)
    LinearLayout lvMeetingtype;
    @BindView(R.id.signmembmeeting)
    TextView signmembmeeting;
    @BindView(R.id.wb_content)
    WebView wbContent;
    String type = "";
    @BindView(R.id.tv_action_people)
    TextView tvActionPeople;
    @BindView(R.id.tv_action_type)
    TextView tvActionType;
    @BindView(R.id.ll_action_people)
    LinearLayout llActionPeople;


    @Override
    protected int getContentId() {
        return R.layout.activity_thirdlifecontent;
    }

    @Override
    protected void initViews() {
        if (getIntent() != null) {
            strDid = getIntent().getStringExtra("lid");
            type = getIntent().getStringExtra("type");
        }
        GetPTCConetnt();
    }

    @Override
    protected void initToolbar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        tvTitle.setText("详情");
        toolbar.setNavigationIcon(R.drawable.left_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    void GetPTCConetnt() {
        Map<String, String> map = new HashMap<>();
        map.put("uid", EnterCheckUtil.getInstance().getUid_Token()[0]);
        map.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        String strUrl = "";
        if (type.equals("1")) {
            strUrl = "/Organization/olifeDetails";
            map.put("lid", String.valueOf(strDid));
        } else {
            strUrl = "/Organization/threemeetingDetails";
            map.put("tid", String.valueOf(strDid));
        }
        OkHttpUtil.getInstance().doAsyncPost(Consts.BASE_URL + strUrl, map, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                ULog.e("三会一课类："+response);
                try {
                    JSONObject obj = new JSONObject(response);
                    if (obj.has("code")) {
                        if (obj.getString("code").equals("0")) {
                            Gson gson = new Gson();
                            final OrgazationLifeCointentBean mPTCConetntBean = gson.fromJson(response, OrgazationLifeCointentBean.class);
                            lvMeetingtype.setVisibility(View.VISIBLE);
                            tvContenttitle.setText(mPTCConetntBean.getData().getDetails().getContent());
                            tvTime.setText(Utils.getDataTime(mPTCConetntBean.getData().getDetails().getMeeting_time()));
                            meetingname.setText(mPTCConetntBean.getData().getDetails().getType());
                            StringBuilder sb = new StringBuilder();
                            // 拼接一段HTML代码
                            sb.append("<html>");
                            sb.append("<head>");
                            sb.append("<title> 龙虎网党员之家 </title>");
                            sb.append("</head>");
                            sb.append("<body>");
                            sb.append(mPTCConetntBean.getData().getDetails().getDesc());
                            sb.append("</body>");
                            sb.append("</html>");
                            // 使用简单的loadData方法会导致乱码，可能是Android API的Bug
                            //  show.loadData(sb.toString() , "text/html" , "utf-8");

                            //  加载、并显示HTML代码
                            wbContent.loadDataWithBaseURL(null, sb.toString(), "text/html", "utf-8", null);
                            meetingadress.setText(mPTCConetntBean.getData().getDetails().getMeeting_address());
                            meetinghostname.setText(mPTCConetntBean.getData().getDetails().getCompere());
                            meetingrecordname.setText(mPTCConetntBean.getData().getDetails().getRecorder());

                            String particular = mPTCConetntBean.getData().getDetails().getAttend_memb()
                                    .toString().replace("\\r", "  ");
                            invitenamemeeting.setText(particular);


                            String particular1 = mPTCConetntBean.getData().getDetails().getSign_memb()
                                    .toString().replace("\\r", "  ");
                            signmembmeeting.setText(particular1);
                            //请假或是参与活动的填写
                            String tType = mPTCConetntBean.getData().getDetails().getTtype();
                            List<OrgazationLifeCointentBean.DataBean.DetailsBean.LeavepeopleBean> leavePeopleList = mPTCConetntBean.getData().getDetails().getLeavepeople();
                            List<OrgazationLifeCointentBean.DataBean.DetailsBean.JoinpeopleBean> joinPeopleList = mPTCConetntBean.getData().getDetails().getJoinpeople();
                            String leavePeoples = "";
                            String joinPeoples = "";
                            if (leavePeopleList.size() > 0) {
                                for(OrgazationLifeCointentBean.DataBean.DetailsBean.LeavepeopleBean bean:leavePeopleList){
                                    leavePeoples +=bean.getRealname()+"  ";
                                }
                                leavePeoples.substring(0,leavePeoples.lastIndexOf("  "));
                            }
                            if (joinPeopleList.size() > 0) {
                                for(OrgazationLifeCointentBean.DataBean.DetailsBean.JoinpeopleBean bean:joinPeopleList){
                                    joinPeoples +=bean.getRealname()+"  ";
                                }
                                joinPeoples.substring(0,joinPeoples.lastIndexOf("  "));
                            }
                            if (TextUtils.equals("1", tType)) { //请假
                                if(TextUtils.isEmpty(leavePeoples)){
                                    llActionPeople.setVisibility(View.GONE);
                                }else{
                                    llActionPeople.setVisibility(View.VISIBLE);
                                    tvActionType.setText("请假人员");
                                    tvActionPeople.setText(leavePeoples);
                                }
                            }else if(TextUtils.equals("2",tType)) {//参与
                                if(TextUtils.isEmpty(joinPeoples)){
                                    llActionPeople.setVisibility(View.GONE);
                                }else{
                                    llActionPeople.setVisibility(View.VISIBLE);
                                    tvActionType.setText("报名人员");
                                    tvActionPeople.setText(joinPeoples);
                                }
                            }else{
                                llActionPeople.setVisibility(View.GONE);
                            }
                        } else {
                            Toast.makeText(OrgazationLifeCointentActivity.this, "数据异常", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(OrgazationLifeCointentActivity.this, "数据异常", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(OrgazationLifeCointentActivity.this, "网络错误~");

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
