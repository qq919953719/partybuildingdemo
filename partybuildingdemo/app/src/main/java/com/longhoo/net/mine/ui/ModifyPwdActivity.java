package com.longhoo.net.mine.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.longhoo.net.MyApplication;
import com.longhoo.net.R;
import com.longhoo.net.utils.AES64;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.MessageEvent;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.utils.Utils;
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

import static com.longhoo.net.mine.ui.MeFragment.MSG_LOGIN_OUT;

public class ModifyPwdActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_old)
    EditText etOld;
    @BindView(R.id.iv_del_old)
    ImageView ivDelOld;
    @BindView(R.id.edt_comfrimpwd)
    EditText edtComfrimpwd;
    @BindView(R.id.img_comfrimeye)
    ImageView imgComfrimeye;
    @BindView(R.id.tv_commit)
    TextView tvCommit;

    @Override
    protected int getContentId() {
        return R.layout.activity_modify_pwd;
    }

    @Override
    protected void initViews() {
        ivDelOld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pwdCansee();
            }
        });
        imgComfrimeye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pwdCansee2();
            }
        });
    }
    boolean isSee2 = false;

    void pwdCansee2() {
        if (isSee2) {
            isSee2 = false;
            imgComfrimeye.setImageResource(R.drawable.closeeye);
            edtComfrimpwd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
        } else {
            edtComfrimpwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            isSee2 = true;
            imgComfrimeye.setImageResource(R.drawable.openeye);
        }
    }
        boolean isSee = false;

    void pwdCansee() {
        if (isSee) {
            ivDelOld.setImageResource(R.drawable.closeeye);
            isSee = false;
            etOld.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
        } else {
            etOld.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            isSee = true;
            ivDelOld.setImageResource(R.drawable.openeye);
        }
    }

    @Override
    protected void initToolbar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        tvTitle.setText("修改密码");
        toolbar.setNavigationIcon(R.drawable.left_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),
                            0);
                }
                onBackPressed();
            }
        });
    }


    @OnClick({R.id.tv_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_commit:
                doCommit();
                break;
        }
    }

    private String newPwd;

    private void doCommit() {
        String oldPwd = etOld.getText().toString().trim();
        newPwd = edtComfrimpwd.getText().toString().trim();
        if (TextUtils.isEmpty(oldPwd)) {
            ToastUtils.getInstance().showToast(this, "请输入原密码！");
            return;
        } else if (TextUtils.isEmpty(newPwd)) {
            ToastUtils.getInstance().showToast(this, "请输入新密码！");
            return;
        }
        if(newPwd.length()<6||newPwd.length()>16){
            ToastUtils.getInstance().showToast(this, "新密码的长度应在6-16位！");
            return;
        }
        try {
            oldPwd = new AES64().encrypt(oldPwd);
            newPwd = new AES64().encrypt(newPwd);
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.getInstance().showToast(this, "AES加密失败！");
            return;
        }
        Utils.showHideSoftInput(this, etOld, false);
        String url = Consts.BASE_URL + "/Index/log_uppwd";
        final Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("oldpwd", oldPwd);
        params.put("newpwd", newPwd);
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                ULog.e("cc", "modify:" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    ToastUtils.getInstance().showToast(ModifyPwdActivity.this, jsonObject.optString("msg"));
                    if (TextUtils.equals(jsonObject.optString("code"), "0")) {
                        MyApplication App = (MyApplication) getApplicationContext();
                        App.setLoginReturn(true);
                        EnterCheckUtil.getInstance().outLogin();
                        MessageEvent event = new MessageEvent();
                        event.message = MSG_LOGIN_OUT;
                        EventBus.getDefault().post(event);
                        Intent intent = new Intent(ModifyPwdActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtils.getInstance().showToast(ModifyPwdActivity.this, "服务器异常~");
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(ModifyPwdActivity.this, "网络错误~");
            }
        });

    }

}
