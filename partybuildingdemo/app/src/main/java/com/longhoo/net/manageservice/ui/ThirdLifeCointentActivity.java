package com.longhoo.net.manageservice.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.longhoo.net.R;
import com.longhoo.net.manageservice.bean.ThirdLifeCointentBean;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.Utils;
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
 * Created by ${CC} on 2017/12/15.
 */

public class ThirdLifeCointentActivity extends BaseActivity {
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

    @Override
    protected int getContentId() {
        return R.layout.activity_thirdlifecontent;
    }

    @Override
    protected void initViews() {
        if (getIntent() != null) {
            strDid = getIntent().getStringExtra("did");
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
        map.put("did", String.valueOf(strDid));
        map.put("uid", EnterCheckUtil.getInstance().getUid_Token()[0]);
        map.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        OkHttpUtil.getInstance().doAsyncPost(Consts.BASE_URL + "/Organization/democraticDetails", map, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if (obj.has("code")) {
                        if (obj.getString("code").equals("0")) {
                            Gson gson = new Gson();
                            final ThirdLifeCointentBean mPTCConetntBean = gson.fromJson(response, ThirdLifeCointentBean.class);
                            lvMeetingtype.setVisibility(View.GONE);
                            tvContenttitle.setText(mPTCConetntBean.getData().getDetails().getContent());
                            tvTime.setText(Utils.getDataTime(mPTCConetntBean.getData().getDetails().getMeeting_time()));


                            StringBuilder sb = new StringBuilder();
                            // 拼接一段HTML代码
                            sb.append("<html>");
                            sb.append("<head>");
                            sb.append("<title> 龙虎网党员之家 </title>");
                            sb.append("</head>");
                            sb.append("<body>");
                            sb.append(mPTCConetntBean.getData().getDetails().getMeeting_spirit());
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
                        } else {
                            Toast.makeText(ThirdLifeCointentActivity.this, "数据异常", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ThirdLifeCointentActivity.this, "数据异常", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(ThirdLifeCointentActivity.this, "网络错误~");

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
