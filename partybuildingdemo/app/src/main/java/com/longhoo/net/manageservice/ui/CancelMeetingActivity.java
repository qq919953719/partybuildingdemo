package com.longhoo.net.manageservice.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.MessageEvent;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.utils.httprequest.OkHttpCallback;
import com.longhoo.net.utils.httprequest.OkHttpUtil;
import com.longhoo.net.widget.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CancelMeetingActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.tv_sign_up)
    TextView tvSignUp;
    String strType = "";
    String strId = "";

    @Override
    protected int getContentId() {
        return R.layout.activity_cancelmeeting;
    }

    @Override
    protected void initViews() {
        strType = getIntent().getStringExtra("type");
        strId = getIntent().getStringExtra("id");
    }

    @Override
    protected void initToolbar() {
        if (strType.equals("1")) {
            tvTitle.setText("取消会议");
        } else {
            tvTitle.setText("取消活动");
        }

        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.left_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

    @OnClick({R.id.tv_title, R.id.tv_right, R.id.toolbar, R.id.tv_sign_up})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_title:
                break;
            case R.id.tv_right:
                break;
            case R.id.toolbar:
                break;
            case R.id.tv_sign_up:
                doSignUp();
                break;
        }
    }


    private void doSignUp() {
        String content = etContent.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            ToastUtils.getInstance().showToast(this, "请输入取消会议原因！");
            etContent.requestFocus();
            return;
        }
        String url = Consts.BASE_URL + "/Index/meetingRemove";
        Map<String, String> params = new HashMap<>();
        params.put("uid", EnterCheckUtil.getInstance().getUid_Token()[0]);
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("remarks", content);
        params.put("type", strType);
        params.put("id", strId);
        tvSignUp.setText("正在提交中...");


        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                if (isFinishing()) {
                    return;
                }
                ULog.e("cc", "提交：" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.optString("code");
                    String msg = jsonObject.optString("msg");
                    ToastUtils.getInstance().showToast(CancelMeetingActivity.this, msg);
                    if (TextUtils.equals(jsonObject.optString("code"), "0")) {

                        if (strType.equals("1")) {
                            MessageEvent event = new MessageEvent();
                            event.msgType = MessageEvent.MSG_FINISH_ORGNIZATION_ARCHIVES;
                            EventBus.getDefault().post(event);
                        }
                        if (strType.equals("2")) {
                            MessageEvent event = new MessageEvent();
                            event.msgType = MessageEvent.MSG_FINISH_ACTIVITY_ARCHIVES;
                            EventBus.getDefault().post(event);
                        }
                        onBackPressed();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtils.getInstance().showToast(CancelMeetingActivity.this, "服务器异常，提交失败~");
                }
                tvSignUp.setText("立即提交");
            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(CancelMeetingActivity.this, "网络异常，提交失败~");
                if (isFinishing()) {
                    return;
                }
                tvSignUp.setText("立即提交");
            }
        });


    }
}
