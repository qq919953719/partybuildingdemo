package com.longhoo.net.headline.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.webkit.WebView;
import android.widget.ImageView;

import com.longhoo.net.MainActivity;
import com.longhoo.net.MyApplication;
import com.longhoo.net.R;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.GlideManager;
import com.longhoo.net.utils.SPTool;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.httprequest.OkHttpCallback;
import com.longhoo.net.utils.httprequest.OkHttpUtil;
import com.longhoo.net.widget.base.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


public class SplashActivity extends BaseActivity implements View.OnClickListener {
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE};
    private static final int PERMISSION_REQUESTCODE = 0;
    private ImageView ivAdv;
    private WebView webView;
    private ImageView ivbg;
    private ImageView ivbgCache;
    private MyHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
//判断是否是AndroidN以及更高的版本
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            Uri contentUri = FileProvider.getUriForFile(SplashActivity.this, BuildConfig.APPLICATION_ID + ".fileProvider", apkFile);
//            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
//        } else {
//            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        }
//        startActivity(intent);
        //去除title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去掉Activity上面的状态栏、全屏、透明虚拟按键
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initViews() {
        //初始化view
        MyApplication App = (MyApplication) getApplicationContext();
        App.setmLoginBean(null);
        ivAdv = (ImageView) findViewById(R.id.iv_adv);
        webView = (WebView) findViewById(R.id.agent_view);
        ivbg = findViewById(R.id.wel_bg);
        ivbgCache = findViewById(R.id.wel_bg_cache);
        mHandler = new MyHandler(this);
        getPic();
        //检查权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermissions(permissions);
        } else {
            doRealAction();
        }
    }

    @Override
    protected void initToolbar() {

    }

    private void checkPermissions(String[] permissions) {
        List<String> needRequestPermissionsList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                needRequestPermissionsList.add(permission);
            }
        }
        if (needRequestPermissionsList != null && needRequestPermissionsList.size() > 0) {
            ActivityCompat.requestPermissions(this, needRequestPermissionsList.toArray(new String[needRequestPermissionsList.size()]), PERMISSION_REQUESTCODE);
        } else {
            //权限都有了
            doRealAction();
        }
    }

    /**
     * 检测是否已授权
     *
     * @param grantResults
     * @return
     */
    private boolean verifyPermissions(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUESTCODE) {
            if (!verifyPermissions(grantResults)) {
                ToastUtils.getInstance().showToast(this, "请先授予部分权限！");
                finish();
            } else {
                //权限都有了
                doRealAction();
            }

        }
    }

    private void doRealAction() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String cachePath = SPTool.getString(SplashActivity.this, "splash_pic_path", "");
                if (!TextUtils.isEmpty(cachePath)) {
                    showAdvImg(ivbg, cachePath);
                }
                mHandler.sendEmptyMessageDelayed(0, 1200);
            }
        }, 300);
    }

    private class MyHandler extends Handler {
        WeakReference<Activity> mWeakReference;

        public MyHandler(Activity activity) {
            mWeakReference = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Activity activity = mWeakReference.get();
            if (activity == null) {
                return;
            }
            switch (msg.what) {
                case 0:
                    if (isFinishing())
                        return;
                    //检查是否第一次进入应用
//                    boolean isFirst = SPTool.getBoolean(SplashActivity.this, Consts.IS_FIRST_IN_APP, true);
//                    if (isFirst) {
//                        Intent intent = new Intent(SplashActivity.this, GuideActivity.class);
//                        startActivity(intent);
//                    } else {
                    // 判断是否登录
                    boolean isLogin = EnterCheckUtil.getInstance().isLogin(true);
                    if (isLogin) {
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                    //}
                    finish();
                    break;
            }
        }
    }

    private void showAdvImg(ImageView imageView, String path) {
        if (!TextUtils.isEmpty(path)) {
            GlideManager.getInstance().load(SplashActivity.this, path, imageView, R.drawable.splash);
        }
        imageView.setVisibility(View.VISIBLE);
        Animation animation = new AlphaAnimation(0, 1.0f);
        animation.setDuration(800);
        animation.setFillAfter(true);
        imageView.setAnimation(animation);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_adv:

                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    private void getPic() {
        OkHttpUtil.getInstance().doAsyncGet(Consts.BASE_URL + "/Index/public_getshowimg", new OkHttpCallback() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.optString("code");
                    String msg = jsonObject.optString("msg");
                    String data = jsonObject.optString("data");
                    if (TextUtils.equals(code, "0")) {
                        String cachePath = SPTool.getString(SplashActivity.this, "splash_pic_path", "");
                        if (!TextUtils.equals(data, cachePath)) {
                            //更新图片
                            if (!isDestroyed()) {
                                SPTool.putString(SplashActivity.this, "splash_pic_path", data);
                                GlideManager.getInstance().load(SplashActivity.this, data, ivbgCache);
                            }

                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });
    }
}
