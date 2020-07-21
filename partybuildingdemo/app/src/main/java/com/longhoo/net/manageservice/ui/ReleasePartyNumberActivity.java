package com.longhoo.net.manageservice.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.longhoo.net.R;
import com.longhoo.net.manageservice.bean.ActsEncrollDetailBean;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.MessageEvent;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.httprequest.OkHttpCallback;
import com.longhoo.net.utils.httprequest.OkHttpUtil;
import com.longhoo.net.widget.base.BaseActivity;
import com.longhoo.net.widget.treelist.MultiLevelActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class ReleasePartyNumberActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_train_title)
    EditText etTrainTitle;
    @BindView(R.id.et_unit)
    EditText etUnit;
    @BindView(R.id.et_attend_number)
    EditText etAttendNumber;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.tv_activity_startdate)
    TextView tvActivityStartdate;
    @BindView(R.id.iv_activity_startdate)
    ImageView ivActivityStartdate;
    @BindView(R.id.tv_activity_enddate)
    TextView tvActivityEnddate;
    @BindView(R.id.iv_activity_enddate)
    ImageView ivActivityEnddate;
    @BindView(R.id.tv_sign_up_startdate)
    TextView tvSignUpStartdate;
    @BindView(R.id.iv_sign_up_startdate)
    ImageView ivSignUpStartdate;
    @BindView(R.id.tv_sign_up_endtdate)
    TextView tvSignUpEndtdate;
    @BindView(R.id.iv_sign_up_endtdate)
    ImageView ivSignUpEndtdate;
    @BindView(R.id.tv_selected_people)
    TextView tvSelectedPeople;
    @BindView(R.id.iv_add_people)
    ImageView ivAddPeople;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    private String hasSelectedIds = "";
    private String memberString = "";
    private ActsEncrollDetailBean.DataBean mActsEncrollDetailBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getContentId() {
        return R.layout.activity_release_party_number;
    }

    @Override
    protected void initViews() {
        EventBus.getDefault().register(this);
        mActsEncrollDetailBean = getIntent().getParcelableExtra("ActivityInfo");
        if (mActsEncrollDetailBean != null) {
            tvTitle.setText("修改党员活动");
            etTrainTitle.setText(mActsEncrollDetailBean.getPtitle());
            etTrainTitle.requestFocus();
            etTrainTitle.setSelection(etTrainTitle.getText().length());
            etUnit.setText(mActsEncrollDetailBean.getOunit());
            etAttendNumber.setText(mActsEncrollDetailBean.getMeetingobj());
            etAddress.setText(mActsEncrollDetailBean.getAddr());
            tvActivityStartdate.setText(mActsEncrollDetailBean.getCdate());
            tvActivityEnddate.setText(mActsEncrollDetailBean.getEnddate());
            tvSignUpStartdate.setText(mActsEncrollDetailBean.getStime());
            tvSignUpEndtdate.setText(mActsEncrollDetailBean.getEtime());

            tvSelectedPeople.setText(mActsEncrollDetailBean.getUidNameStr());
            hasSelectedIds = mActsEncrollDetailBean.getOidstr() + "," + mActsEncrollDetailBean.getUidstr();
            //  10108,11759,10113,10106,10109


            etContent.setText(mActsEncrollDetailBean.getInfo());
        }
    }

    @Override
    protected void initToolbar() {
        tvTitle.setText("发起党员活动");
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.left_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }


    private void pickData(final TextView tvTime) {
        DatePickDialog dialog = new DatePickDialog(this);
        //设置上下年分限制
        dialog.setYearLimt(5);
        //设置标题
        dialog.setTitle("选择时间");
        //设置类型
        dialog.setType(DateType.TYPE_ALL);
        //设置消息体的显示格式，日期格式
        dialog.setMessageFormat("yyyy-MM-dd HH:mm");
        //设置选择回调
        dialog.setOnChangeLisener(null);
        //设置点击确定按钮回调
        dialog.setOnSureLisener(new OnSureLisener() {
            @Override
            public void onSure(Date date) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                tvTime.setText(simpleDateFormat.format(date));

            }
        });
        dialog.show();
    }

    @OnClick({R.id.iv_activity_startdate, R.id.iv_activity_enddate, R.id.iv_sign_up_startdate, R.id.iv_sign_up_endtdate, R.id.iv_add_people, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_activity_startdate:
                pickData(tvActivityStartdate);
                break;
            case R.id.iv_activity_enddate:
                pickData(tvActivityEnddate);
                break;
            case R.id.iv_sign_up_startdate:
                pickData(tvSignUpStartdate);
                break;
            case R.id.iv_sign_up_endtdate:
                pickData(tvSignUpEndtdate);
                break;
            case R.id.iv_add_people:
                MultiLevelActivity.goTo(this, hasSelectedIds);
                break;
            case R.id.tv_submit:
                doRelease();
                break;
        }
    }


    private void doRelease() {

        String strPtitle = etTrainTitle.getText().toString().trim();
        if (TextUtils.isEmpty(strPtitle)) {
            ToastUtils.getInstance().showToast(this, "请输入活动名称！");
            return;
        }
        String strOunit = etUnit.getText().toString().trim();
        if (TextUtils.isEmpty(strOunit)) {
            ToastUtils.getInstance().showToast(this, "请输入组织单位！");
            return;
        }
        String strAttendNumber = etAttendNumber.getText().toString().trim();
        if (TextUtils.isEmpty(strAttendNumber)) {
            ToastUtils.getInstance().showToast(this, "请输入参会对象！");
            return;
        }
        String strAdress = etAddress.getText().toString().trim();
        if (TextUtils.isEmpty(strAdress)) {
            ToastUtils.getInstance().showToast(this, "请输入参会地址！");
            return;
        }
        String strActivityStartdate = tvActivityStartdate.getText().toString().trim();
        if (TextUtils.isEmpty(strActivityStartdate)) {
            ToastUtils.getInstance().showToast(this, "请输入活动开始时间！");
            return;
        }
        String strActivityEnddate = tvActivityEnddate.getText().toString().trim();
        if (TextUtils.isEmpty(strActivityEnddate)) {
            ToastUtils.getInstance().showToast(this, "请输入活动结束时间！");
            return;
        }
        String strSignUpStartdate = tvSignUpStartdate.getText().toString().trim();
        if (TextUtils.isEmpty(strSignUpStartdate)) {
            ToastUtils.getInstance().showToast(this, "请输入报名开始时间！");
            return;
        }
        String strSignUpEndtdate = tvSignUpEndtdate.getText().toString().trim();
        if (TextUtils.isEmpty(strSignUpEndtdate)) {
            ToastUtils.getInstance().showToast(this, "请输入报名结束时间！");
            return;
        }
        String orgid = "";
        String attend_memb = "";

        if (mActsEncrollDetailBean != null) {
            orgid = mActsEncrollDetailBean.getOidstr();
            attend_memb = mActsEncrollDetailBean.getUidstr();
        } else {
            if (memberString.contains("&ck&")) {
                String[] strings = memberString.split("&ck&");
                if (strings.length >= 2) {
                    orgid = strings[0];
                    attend_memb = strings[1];
                }
            }
        }

        if (TextUtils.isEmpty(orgid) || TextUtils.isEmpty(attend_memb)) {
            ToastUtils.getInstance().showToast(this, "请选择参会人员！");
            return;
        }
        String strContent = etContent.getText().toString().trim();
        if (TextUtils.isEmpty(strContent)) {
            ToastUtils.getInstance().showToast(this, "请输入具体安排！");
            return;
        }
        tvSubmit.setText("正在提交中...");
        tvSubmit.setEnabled(false);
        String url = Consts.BASE_URL + "/Activity/addActivity";
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("ptitle", strPtitle + "");
        params.put("ounit", strOunit + "");
        params.put("meetingobj", strAttendNumber + "");
        params.put("addr", strAdress + "");
        params.put("cdate", strActivityStartdate + "");
        params.put("enddate", strActivityEnddate + "");
        params.put("stime", strSignUpStartdate + "");
        params.put("etime", strSignUpEndtdate + "");
        params.put("oidstr", orgid);
        params.put("uidstr", attend_memb);
        params.put("info", strContent);
        if (mActsEncrollDetailBean != null) {
            params.put("id", mActsEncrollDetailBean.getAid() + "");
        }
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.optString("code");
                    String msg = jsonObject.optString("msg");
                    if (TextUtils.equals(code, "0")) {
                        MessageEvent event = new MessageEvent();
                        event.msgType = MessageEvent.MSG_REFRESH_ORG_LIFE_LIST;
                        EventBus.getDefault().post(event);
//                        if (mActsEncrollDetailBean!=null){
//                            MyApplication app = (MyApplication) getApplicationContext();
//                            app.RemoveActivity("com.longhoo.net.study.ui.ActsEncrollDetailActivity");
//                        }
                        finish();
                    }
                    ToastUtils.getInstance().showToast(ReleasePartyNumberActivity.this, msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                tvSubmit.setText("提交");
                tvSubmit.setEnabled(true);
            }

            @Override
            public void onFailure(String errorMsg) {
                tvSubmit.setText("提交");
                tvSubmit.setEnabled(true);
                ToastUtils.getInstance().showToast(ReleasePartyNumberActivity.this, "网络异常~");
            }
        });
    }


    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        String msg = event.message;
        int msgType = event.msgType;
        if (msgType == MessageEvent.MSG_PICK_PEOPLE) {
            memberString = msg;
            tvSelectedPeople.setText(event.message2);
            if (mActsEncrollDetailBean != null) {
                if (memberString.contains(",&ck&")) {
                    String[] strings = memberString.split("&ck&");
                    if (strings.length >= 2) {
                        mActsEncrollDetailBean.setOidstr(strings[0]);
                        mActsEncrollDetailBean.setUidstr(strings[1]);
                    }
                }
            }
            hasSelectedIds = memberString.replace("&ck&", "");
            //hasSelectedIds = event.message3;
        }
        // ULog.e("ck--","memberString:"+memberString);
    }
}
