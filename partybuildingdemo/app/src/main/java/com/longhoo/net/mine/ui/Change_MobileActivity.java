package com.longhoo.net.mine.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longhoo.net.MyApplication;
import com.longhoo.net.R;
import com.longhoo.net.mine.bean.LoginBean;
import com.longhoo.net.utils.AES64;
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
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Change_MobileActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_mobile)
    TextView tvMobile;
    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.lv_listbottom)
    LinearLayout lvListbottom;
    @BindView(R.id.tv_cancle)
    TextView tvCancle;
    @BindView(R.id.lv_confrimmbile)
    LinearLayout lvConfrimmbile;

    @Override
    protected int getContentId() {
        return R.layout.activity_changemobile;
    }

    @Override
    protected void initViews() {
        MyApplication app = (MyApplication) getApplicationContext();
        if (app.getmLoginBean() == null) {
            ToastUtils.getInstance().showToast(this, "登录过期，请重新登陆！");
            return;
        }
        if (app.getmLoginBean().getData().getPhone() == null || app.getmLoginBean().getData().getPhone().equals("")) {
            tvMobile.setText("当前手机号为空");
        } else {
            tvMobile.setText("当前手机号：" + app.getmLoginBean().getData().getPhone());
        }

    }

    @Override
    protected void initToolbar() {
        tvTitle.setText("修改手机号");
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
//        toolbar.setRight(R.drawable.turn_right);
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

    private void doCommit() {
        MyApplication app = (MyApplication) getApplicationContext();
        if (app.getmLoginBean() == null) {
            ToastUtils.getInstance().showToast(this, "登录过期，请重新登陆！");
            return;
        }


        String newMobile = etMobile.getText().toString().trim();
        if (TextUtils.isEmpty(newMobile)) {
            ToastUtils.getInstance().showToast(this, "请输入手机号码！");
            return;
        }


        if (app.getmLoginBean().getData().getMobile() == null || app.getmLoginBean().getData().getMobile().equals("")) {

        } else {
            if (app.getmLoginBean().getData().getMobile().equals(newMobile)) {
                ToastUtils.getInstance().showToast(this, "新手机号不能与当前手机号相同！");
                return;
            }

        }


        try {
            newMobile = new AES64().encrypt(newMobile);

        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.getInstance().showToast(this, "AES加密失败！");
            return;
        }
        Utils.showHideSoftInput(this, etMobile, false);
        String url = Consts.BASE_URL + "/Index/modifymobile";
        final Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("phone", newMobile);
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                ULog.e("cc", "changgemobile:" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    ToastUtils.getInstance().showToast(Change_MobileActivity.this, jsonObject.optString("msg"));
                    if (TextUtils.equals(jsonObject.optString("code"), "0")) {
                        MyApplication App = (MyApplication) getApplicationContext();
                        App.getmLoginBean().getData().setMobile(etMobile.getText().toString().trim());
                        LoginBean mLoginBean = App.getmLoginBean();
                        EnterCheckUtil.getInstance().saveLogin(mLoginBean);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtils.getInstance().showToast(Change_MobileActivity.this, "服务器异常~");
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(Change_MobileActivity.this, "网络错误~");
            }
        });

    }

    @OnClick(R.id.lv_confrimmbile)
    public void onViewClicked() {
        doCommit();
    }
}
