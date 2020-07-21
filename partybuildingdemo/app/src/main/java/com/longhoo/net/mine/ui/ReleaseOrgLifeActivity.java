package com.longhoo.net.mine.ui;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.longhoo.net.MyApplication;
import com.longhoo.net.R;
import com.longhoo.net.manageservice.bean.OrganizationLifeDetailBean;
import com.longhoo.net.partyaffairs.bean.TaiZhangMenuBean;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.MessageEvent;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.utils.Utils;
import com.longhoo.net.utils.httprequest.OkHttpCallback;
import com.longhoo.net.utils.httprequest.OkHttpUtil;
import com.longhoo.net.widget.MyDialog;
import com.longhoo.net.widget.base.BaseActivity;
import com.longhoo.net.widget.treelist.MultiLevelActivity;
import com.longhoo.net.widget.treelist.MultiLevelBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 发布组织生活
 */
public class ReleaseOrgLifeActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_sel_type)
    TextView tvSelType;
    @BindView(R.id.et_train_title)
    EditText etTrainTitle;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.iv_date)
    ImageView ivDate;
    @BindView(R.id.iv_add_people)
    ImageView ivAddPeople;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.et_address)
    TextView etAddress;
    @BindView(R.id.tv_selected_people)
    TextView tvSelectedPeople;
    @BindView(R.id.tv_sel_compere)
    TextView tvSelCompere;
    @BindView(R.id.tv_sel_recorder)
    TextView tvSelRecorder;
    private String[] zuZhiTypeArrays;
    private List<TaiZhangMenuBean.DataBean> zuZhiTypeList = new ArrayList<>();
    private List<MultiLevelBean.DataBeanX.DataBean> compereList = new ArrayList<>();
    private List<MultiLevelBean.DataBeanX.DataBean> recorderList = new ArrayList<>();
    private MyDialog dialog;
    private int cid;
    private int mCompereUid;
    private int mRecorderUid;
    private String memberString = "";
    private String hasSelectedIds = "";
    OrganizationLifeDetailBean.DataBean detailBean;

    @Override
    protected int getContentId() {
        return R.layout.activity_release_org_life;
    }

    @Override
    protected void initViews() {
        EventBus.getDefault().register(this);
        detailBean = getIntent().getParcelableExtra("detailBean");
        if (detailBean != null) {
            tvSelType.setText(detailBean.getType());
            etTrainTitle.setText(detailBean.getContent());
            tvDate.setText(Utils.getDataTimeMine(detailBean.getMeeting_time() + ""));
            tvSelectedPeople.setText(detailBean.getAttend_memb().replace("\\r", "\r"));
            etAddress.setText(detailBean.getMeeting_address());
            tvSelCompere.setText(detailBean.getCompere());
            tvSelRecorder.setText(detailBean.getRecorder());
            etContent.setText(detailBean.getDesc());
            hasSelectedIds = detailBean.getOrgid() + "," + detailBean.getAttend_memb1();
            mRecorderUid = detailBean.getRecorder1();
            mCompereUid = Integer.parseInt(detailBean.getCompere1());
        } else {
            getData();
        }
        doGetMenus();

        ivDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickData();
            }
        });
    }

    @Override
    protected void initToolbar() {
        tvTitle.setText("发布组织生活");
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.left_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        if (detailBean != null) {
            tvTitle.setText("修改组织生活");
        }
    }

    @OnClick({R.id.tv_submit, R.id.iv_add_people, R.id.tv_selected_people, R.id.tv_sel_type, R.id.tv_sel_compere, R.id.tv_sel_recorder})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_submit:
                doRelease();
                break;
            case R.id.tv_selected_people:
            case R.id.iv_add_people:
//                Intent intent = new Intent(this, MultiLevelActivity.class);
//                startActivity(intent);
//                overridePendingTransition(R.anim.right_to_left, R.anim.anim_none);
                MultiLevelActivity.goTo(this, hasSelectedIds);
                break;
            case R.id.tv_sel_type:
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                dialog = new MyDialog(ReleaseOrgLifeActivity.this, R.style.MyDialogStyle);
                if (zuZhiTypeArrays == null) {
                    zuZhiTypeArrays = new String[]{"", ""};
                }
                dialog.setMyItems(zuZhiTypeArrays);
                dialog.setMyDialogItemClickListener(new MyDialog.MyDialogItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (position >= 0 && position <= zuZhiTypeList.size() - 1) {
                            cid = zuZhiTypeList.get(position).getCid();
                            tvSelType.setText(zuZhiTypeList.get(position).getName());
                        }

                    }
                });
                dialog.show();
                break;
            case R.id.tv_sel_compere:
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                dialog = new MyDialog(ReleaseOrgLifeActivity.this, R.style.MyDialogStyle);
                String[] comperes = new String[compereList.size()];
                for (int i = 0; i < compereList.size(); i++) {
                    comperes[i] = compereList.get(i).getName();
                }
                dialog.setMyItems(comperes);
                dialog.setMyDialogItemClickListener(new MyDialog.MyDialogItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (position >= 0 && position <= compereList.size() - 1) {
                            mCompereUid = compereList.get(position).getUid();
                            tvSelCompere.setText(compereList.get(position).getName());
                        }

                    }
                });
                dialog.show();
                break;
            case R.id.tv_sel_recorder:
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                dialog = new MyDialog(ReleaseOrgLifeActivity.this, R.style.MyDialogStyle);
                String[] recorders = new String[recorderList.size()];
                for (int i = 0; i < recorderList.size(); i++) {
                    recorders[i] = recorderList.get(i).getName();
                }
                dialog.setMyItems(recorders);
                dialog.setMyDialogItemClickListener(new MyDialog.MyDialogItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (position >= 0 && position <= recorderList.size() - 1) {
                            mRecorderUid = recorderList.get(position).getUid();
                            tvSelRecorder.setText(recorderList.get(position).getName());
                        }

                    }
                });
                dialog.show();
                break;
        }
    }

    private void pickData() {
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
                tvDate.setText(simpleDateFormat.format(date));

            }
        });
        dialog.show();
    }

    /**
     * 获取tab菜单
     */
    private void doGetMenus() {
        String url = Consts.BASE_URL + "/Orgmeeting/meetingType";
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                if (isFinishing()) {
                    return;
                }
                ULog.e("ck", "组织生活菜单：" + response);
                Gson gson = new Gson();
                try {
                    TaiZhangMenuBean taiZhangMenuBean = gson.fromJson(response, TaiZhangMenuBean.class);
                    String code = taiZhangMenuBean.getCode();
                    String msg = taiZhangMenuBean.getMsg();
                    if (!TextUtils.equals(code, "0")) {
                        ToastUtils.getInstance().showToast(ReleaseOrgLifeActivity.this, msg);
                        return;
                    }
                    zuZhiTypeList.clear();
                    zuZhiTypeList.addAll(taiZhangMenuBean.getData());
                    for (int i = 0; i < zuZhiTypeList.size(); i++) {
                        if (zuZhiTypeList.get(i).getCid() == 0) {
                            zuZhiTypeList.remove(i);
                        }
                    }
                    if (zuZhiTypeList != null && zuZhiTypeList.size() > 0) {
                        int size = zuZhiTypeList.size();
                        zuZhiTypeArrays = new String[size];
                        for (int i = 0; i < size; i++) {
                            zuZhiTypeArrays[i] = zuZhiTypeList.get(i).getName();
                        }
                        tvSelType.setText(zuZhiTypeList.get(0).getName());
                        cid = zuZhiTypeList.get(0).getCid();
                    }

                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    ToastUtils.getInstance().showToast(ReleaseOrgLifeActivity.this, "服务器异常~");
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(ReleaseOrgLifeActivity.this, "网络异常~");
            }
        });
    }

    private void getData() {
        String url = Consts.BASE_URL + "/application/getOrgLifePeople_new1";
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                List<MultiLevelBean.DataBeanX.DataBean> tempList = null;
                Gson gson = new Gson();
                try {
                    MultiLevelBean multiLevelBean = gson.fromJson(response, MultiLevelBean.class);
                    String code = multiLevelBean.getCode();
                    if (!TextUtils.equals(code, "0")) {
                        ToastUtils.getInstance().showToast(ReleaseOrgLifeActivity.this, "获取数据失败~");
                        return;
                    }
                    tempList = multiLevelBean.getData().getData();
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
                compereList.clear();
                if (tempList != null && tempList.size() > 0) {
                    try {
                        compereList.addAll(tempList);
                        tvSelCompere.setText(compereList.get(0).getName());
                        mCompereUid = compereList.get(0).getUid();
                    } catch (Exception e) {

                    }

                }
                recorderList.clear();
                if (tempList != null && tempList.size() > 0) {
                    recorderList.addAll(tempList);
                    tvSelRecorder.setText(recorderList.get(0).getName());
                    mRecorderUid = recorderList.get(0).getUid();

                }
            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(ReleaseOrgLifeActivity.this, "网络错误~");
            }
        });
    }

    private void doRelease() {
        String title = etTrainTitle.getText().toString().trim();
        if (TextUtils.isEmpty(title)) {
            ToastUtils.getInstance().showToast(this, "请输入会议名称！");
            return;
        }
        String time = tvDate.getText().toString().trim();
        if (TextUtils.isEmpty(time)) {
            ToastUtils.getInstance().showToast(this, "请选择参会时间！");
            return;
        }
        if (detailBean == null) {
            if (TextUtils.isEmpty(memberString)) {
                ToastUtils.getInstance().showToast(this, "请选择参会人员！");
                return;
            }
        }

        String address = etAddress.getText().toString().trim();
        if (TextUtils.isEmpty(address)) {
            ToastUtils.getInstance().showToast(this, "请选择参会地址！");
            return;
        }
        String content = etContent.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            ToastUtils.getInstance().showToast(this, "请输入参会内容！");
            return;
        }
        String orgid = "";
        String attend_memb = "";
        if (detailBean != null) {
            orgid = detailBean.getOrgid();
            attend_memb = detailBean.getAttend_memb1();
            cid = detailBean.getTypeid();
        } else if (memberString.contains("&ck&")) {
            String[] strings = memberString.split("&ck&");
            if (strings.length >= 2) {
                orgid = strings[0];
                attend_memb = strings[1];
            }
        }
        if (TextUtils.isEmpty(orgid) || TextUtils.isEmpty(attend_memb)) {
            ToastUtils.getInstance().showToast(this, "请选择参会人员！");
            return;
        }
        tvSubmit.setText("正在提交中...");
        tvSubmit.setEnabled(false);
        String url = Consts.BASE_URL + "/application/addThreemeeting";
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("type", cid + "");
        params.put("content", title);
        params.put("meeting_time", time);
        params.put("attend_memb", attend_memb);
        params.put("meeting_address", address);
        params.put("desc", content);
        params.put("orgid", orgid);
        params.put("compere", mCompereUid + "");
        params.put("recorder", mRecorderUid + "");
        if (detailBean != null) {
            params.put("tid", detailBean.getTid() + "");
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
                        if (detailBean != null) {
                            MyApplication app = (MyApplication) getApplicationContext();
                            app.RemoveActivity("com.longhoo.net.manageservice.ui.OrganizationLifedetailActivity");
                        }
                        finish();
                    }
                    ToastUtils.getInstance().showToast(ReleaseOrgLifeActivity.this, msg);
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
                ToastUtils.getInstance().showToast(ReleaseOrgLifeActivity.this, "网络异常~");
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
            if (detailBean != null) {
                if (memberString.contains(",&ck&")) {
                    String[] strings = memberString.split("&ck&");
                    if (strings.length >= 2) {
                        detailBean.setOrgid(strings[0]);
                        detailBean.setAttend_memb1(strings[1]);
                    }
                }
            }
            hasSelectedIds = memberString.replace("&ck&", "");
        }
        // ULog.e("ck--","memberString:"+memberString);
    }

}
