package com.longhoo.net.mine.ui;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.longhoo.net.MainActivity;
import com.longhoo.net.R;
import com.longhoo.net.mine.bean.LoginBean;
import com.longhoo.net.utils.AES64;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.SPTool;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.utils.Utils;
import com.longhoo.net.utils.httprequest.OkHttpCallback;
import com.longhoo.net.utils.httprequest.OkHttpUtil;
import com.longhoo.net.widget.base.BaseActivity;
import com.longhoo.net.widget.base.WebViewActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by ${CC} on 2017/12/1.
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.edt_uname)
    EditText edtUname;
    @BindView(R.id.lv_uname)
    LinearLayout lvUname;
    @BindView(R.id.img_lock)
    ImageView imgLock;
    @BindView(R.id.edt_pwd)
    EditText edtPwd;
    @BindView(R.id.lv_pwd)
    LinearLayout lvPwd;
    @BindView(R.id.img_eye)
    ImageView imgEye;
    @BindView(R.id.lv_login)
    LinearLayout lvLogin;
    @BindView(R.id.lv_look)
    LinearLayout lvLook;
    @BindView(R.id.tv_topbar)
    TextView tvTopbar;
    long lastTime;
    @BindView(R.id.tv_regist)
    TextView tvRegist;
    @BindView(R.id.tv_forggetpwd)
    TextView tvForggetpwd;
    public static final String REFRESH_TAG = "refresh_tag";
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.img_pwd)
    ImageView imgPwd;
    @BindView(R.id.lv_remberpwd)
    LinearLayout lvRemberpwd;
    @BindView(R.id.tv_login)
    TextView tvLogin;

    @Override
    protected int getContentId() {
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        return R.layout.activity_login;
    }

//    @Override
//    public void onBackPressed() {
//        MyApplication App = (MyApplication) getApplicationContext();
//        if (App.isLoginReturn()) {
//            lastTime = SPTool.getLong(this, "last_back_time", 0);
//            long curTime = System.currentTimeMillis();
//            if (curTime - lastTime > 2 * 1000) {
//                ToastUtils.getInstance().showToast(this, "再按一次退出");
//                SPTool.putLong(this, "last_back_time", curTime);
//                return;
//            }
//            App.setLoginReturn(false);
//            super.onBackPressed();
//        } else {
//            super.onBackPressed();
//        }
//
//    }

    @Override
    protected void initViews() {

//        ImmersionBar.with(this)
//                .titleBar(findViewById(R.id.tv_topbar), false)
//                .transparentBar()
//                .init();
        edtPwd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
        lvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtUname.getText().toString().trim().equals("")) {
                    Toast.makeText(LoginActivity.this, "账号不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edtPwd.getText().toString().trim().equals("")) {
                    Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                SPTool.putString(LoginActivity.this, Consts.SP_PHONE, edtUname.getText().toString().trim());
                userLogin(edtUname.getText().toString().trim(), edtPwd.getText().toString().trim());
            }
        });
        imgEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pwdCansee();
            }
        });
        lvLook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebViewActivity.goToWebView(LoginActivity.this,Consts.BASE_URL+"/application/public_score?type=1","用户保密协议",false);

            }
        });
        tvRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //注册
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
//                finish();
            }
        });
        tvForggetpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //忘记密码
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, ForgetPwdActivity.class);
                startActivity(intent);

            }
        });
        if (!SPTool.getString(LoginActivity.this, Consts.SP_PHONE, "").equals("")) {
            edtUname.setText(SPTool.getString(LoginActivity.this, Consts.SP_PHONE, ""));
        }

        if (SPTool.getBoolean(LoginActivity.this, Consts.SP_ISREMBERPWD, false)) {
            imgPwd.setImageResource(R.drawable.icon_cos);
            edtPwd.setText(SPTool.getString(LoginActivity.this, Consts.SP_PWD, ""));
        } else {
            imgPwd.setImageResource(R.drawable.icon_ncos);
        }

        imgPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IsRemberPwd();
            }
        });
    }


    void IsRemberPwd() {


        if (SPTool.getBoolean(LoginActivity.this, Consts.SP_ISREMBERPWD, false)) {
            SPTool.putBoolean(LoginActivity.this, Consts.SP_ISREMBERPWD, false);
        } else {
            SPTool.putBoolean(LoginActivity.this, Consts.SP_ISREMBERPWD, true);
        }

        if (SPTool.getBoolean(LoginActivity.this, Consts.SP_ISREMBERPWD, false)) {
            imgPwd.setImageResource(R.drawable.icon_cos);
        } else {
            imgPwd.setImageResource(R.drawable.icon_ncos);
        }

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


    @Override
    protected void initToolbar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        tvTitle.setText("登录");
    }


    void userLogin(String strUser, String strPWD) {
/**
 * 用户登录
 * post 参数
 * {
 *      account aes加密后的账号
 *      upwd  aes加密后的密码
 *      itoken IOS token
 *      atoken Android token
 *      versionno App版本号
 *      versioncode App版本
 *      sys 系统 {0 IOS  1 Android}
 * }
 * post 接口名 /Index/public_login
 *
 * get 请求地址
 * IOS
 */


        try {
            strUser = new AES64().encrypt(strUser);
            strPWD = new AES64().encrypt(strPWD);
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.getInstance().showToast(this, "AES加密失败！");
            return;
        }

        tvLogin.setText("正在登录中...");
        lvLogin.setEnabled(false);
        Map<String, String> map = new HashMap<>();
        map.put("account", String.valueOf(strUser));
        map.put("upwd", String.valueOf(strPWD));
        map.put("atoken", String.valueOf(Utils.getToken(LoginActivity.this)));
        map.put("versionno", String.valueOf(Utils.getVersionCode(LoginActivity.this)));
        map.put("versioncode", String.valueOf(Utils.getVersionName(LoginActivity.this)));
        map.put("sys", String.valueOf("1"));
        OkHttpUtil.getInstance().doAsyncPost(Consts.BASE_URL + "/index/public_login", map, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                tvLogin.setText("登录");
                lvLogin.setEnabled(true);
                if(isFinishing()){
                    return;
                }
                ULog.e("cc","登录："+response);
                try {
                    JSONObject obj = new JSONObject(response);
                    if (obj.has("code")) {
                        if (obj.getString("code").equals("0")) {
                            if (SPTool.getBoolean(LoginActivity.this, Consts.SP_ISREMBERPWD, false)) {
                                SPTool.putString(LoginActivity.this, Consts.SP_PWD, edtPwd.getText().toString().trim());
                            } else {
                                SPTool.putString(LoginActivity.this, Consts.SP_PWD, "");
                            }
                            Gson gson = new Gson();
                            final LoginBean mLoginBean = gson.fromJson(response, LoginBean.class);
                            //保存bean，下次不用登录
                            EnterCheckUtil.getInstance().saveLogin(mLoginBean);
                            Toast.makeText(LoginActivity.this, mLoginBean.getMsg(), Toast.LENGTH_SHORT).show();
                            //登录过后，让之前停留的界面刷新
//                            MessageEvent event = new MessageEvent();
//                            event.message = REFRESH_TAG;
//                            EventBus.getDefault().post(event);
                            Utils.showHideSoftInput(LoginActivity.this, edtPwd, false);
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();

                        } else {
                            if (obj.has("msg")) {
                                String strMSG = obj.getString("msg");
                                Toast.makeText(LoginActivity.this, strMSG, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoginActivity.this, "数据异常", Toast.LENGTH_SHORT).show();
                            }

                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "数据异常", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(LoginActivity.this, "网络错误~");
                tvLogin.setText("登录");
                lvLogin.setEnabled(true);
            }
        });
    }
}
