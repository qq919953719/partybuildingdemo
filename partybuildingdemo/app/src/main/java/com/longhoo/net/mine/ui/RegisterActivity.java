package com.longhoo.net.mine.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.longhoo.net.R;
import com.longhoo.net.utils.AES64;
import com.longhoo.net.utils.Consts;
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

/**
 * Created by ${CC} on 2017/12/28.
 */

public class RegisterActivity extends BaseActivity {


    @BindView(R.id.tv_topbar)
    TextView tvTopbar;
    @BindView(R.id.edt_uname)
    EditText edtUname;
    @BindView(R.id.iv_del_code)
    ImageView ivDelCode;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.edt_code)
    EditText edtCode;
    @BindView(R.id.img_lock)
    ImageView imgLock;
    @BindView(R.id.edt_pwd)
    EditText edtPwd;
    @BindView(R.id.lv_pwd)
    LinearLayout lvPwd;
    @BindView(R.id.img_eye)
    ImageView imgEye;
    @BindView(R.id.img_comfrimlock)
    ImageView imgComfrimlock;
    @BindView(R.id.edt_comfrimpwd)
    EditText edtComfrimpwd;
    @BindView(R.id.lv_confrimpwd)
    LinearLayout lvConfrimpwd;
    @BindView(R.id.img_comfrimeye)
    ImageView imgComfrimeye;
    @BindView(R.id.lv_regist)
    LinearLayout lvRegist;
    @BindView(R.id.lv_look)
    LinearLayout lvLook;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private int mCountDownTime = 60;
    private String phone;

    @Override
    protected int getContentId() {
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        return R.layout.activity_register;
    }

    @Override
    protected void initViews() {
//        ImmersionBar.with(this)
//                .titleBar(findViewById(R.id.tv_topbar), false)
//                .transparentBar()
//                .init();
        tvCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doRequestCode();
            }
        });
        lvRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doCommit();
            }
        });

        imgEye.setOnClickListener(new View.OnClickListener() {
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
        lvLook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    boolean isSee = false;

    void pwdCansee() {
        if (isSee) {
            imgEye.setImageResource(R.drawable.closeeye);
            isSee = false;
            edtPwd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
        } else {
            edtPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            isSee = true;
            imgEye.setImageResource(R.drawable.openeye);
        }
    }

    boolean isSee2 = false;

    void pwdCansee2() {
        if (isSee2) {
            imgComfrimeye.setImageResource(R.drawable.closeeye);
            isSee2 = false;
            edtComfrimpwd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
        } else {
            edtComfrimpwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            isSee2 = true;
            imgComfrimeye.setImageResource(R.drawable.openeye);
        }
    }

    @Override
    protected void initToolbar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        tvTitle.setText("注册");
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


    /**
     * 获取验证码
     */
    private void doRequestCode() {
        String phoneText = edtUname.getText().toString().trim();
        if (phoneText.equals("")) {
            ToastUtils.getInstance().showToast(this, "请输入手机号！");
            return;
        }
        if (!Utils.isMobile(phoneText)) {
            ToastUtils.getInstance().showToast(this, "手机格式不正确！");
            return;
        }
        try {
            phoneText = new AES64().encrypt(phoneText);
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.getInstance().showToast(this, "AES加密失败！");
            return;
        }

        tvCode.setBackground(getResources().getDrawable(R.drawable.grey_corner_bg));
        tvCode.setText(mCountDownTime + "s后重试");
        tvCode.setEnabled(false);
        mHandler.sendEmptyMessageDelayed(0, 1000);
        String url = Consts.BASE_URL + "/Index/public_code";
        Map<String, String> params = new HashMap<>();
        params.put("phone", phoneText);
        params.put("versionno", String.valueOf(Utils.getVersionCode(this)));
        params.put("versioncode", String.valueOf(Utils.getVersionName(this)));
        params.put("sys", String.valueOf("1"));
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    ToastUtils.getInstance().showToast(RegisterActivity.this, jsonObject.optString("msg"));
                    if (jsonObject.has("code")) {
                        if (!jsonObject.getString("code").equals("0")) {
                            mCountDownTime = 1;
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtils.getInstance().showToast(RegisterActivity.this, "服务器异常~");
                    mCountDownTime = 1;
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(RegisterActivity.this, "网络错误~");
                mCountDownTime = 1;
            }
        });
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                if (--mCountDownTime == 0) {
                    tvCode.setBackground(getResources().getDrawable(R.drawable.shape_corner_red));
                    tvCode.setEnabled(true);
                    tvCode.setText("获取验证码");
                    mCountDownTime = 60;
                    removeMessages(0);
                } else {
                    tvCode.setText((mCountDownTime) + "s后重试");
                    sendEmptyMessageDelayed(0, 1000);
                }
            }
        }
    };

    /**
     * 注册手机号
     */

    private String pwd;

    private void doCommit() {
        if (edtUname.getText().toString().trim().equals("")) {
            ToastUtils.getInstance().showToast(this, "请输入手机号！");
            return;
        }
        if (edtCode.getText().toString().trim().equals("")) {
            ToastUtils.getInstance().showToast(this, "请输入验证码！");
            return;
        }
        if (edtPwd.getText().toString().trim().equals("")) {
            ToastUtils.getInstance().showToast(this, "请输入密码！");
            return;
        }
        if (edtPwd.getText().toString().trim().length() < 6) {
            ToastUtils.getInstance().showToast(this, "密码长度不能小于6位数！");
            return;
        }
        if (edtComfrimpwd.getText().toString().trim().equals("")) {
            ToastUtils.getInstance().showToast(this, "请确认密码！");
            return;
        }
        if (!edtComfrimpwd.getText().toString().trim().equals(edtPwd.getText().toString().trim())) {
            ToastUtils.getInstance().showToast(this, "两次输入的密码不一致！");
            return;
        }
        String strPhone = edtUname.getText().toString().trim();
        pwd = edtComfrimpwd.getText().toString().trim();

        try {
            strPhone = new AES64().encrypt(strPhone);
            pwd = new AES64().encrypt(pwd);


        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.getInstance().showToast(this, "AES加密失败！");
            return;
        }
        /**
         *  phone aes加密后的手机号
         upwd  aes加密后的密码
         vcode 验证码
         */
        String url = Consts.BASE_URL + "/Index/public_regist";
        final Map<String, String> params = new HashMap<>();
        params.put("phone", strPhone);
        params.put("upwd", pwd);
        params.put("vcode", edtCode.getText().toString().trim());
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                ULog.e("cc", "modify:" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    ToastUtils.getInstance().showToast(RegisterActivity.this, jsonObject.optString("msg"));
                    if (TextUtils.equals(jsonObject.optString("code"), "0")) {

                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtils.getInstance().showToast(RegisterActivity.this, "服务器异常~");
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(RegisterActivity.this, "网络错误~");
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
