package com.longhoo.net.mine.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longhoo.net.MyApplication;
import com.longhoo.net.R;
import com.longhoo.net.utils.CacheUtil;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.MessageEvent;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.Utils;
import com.longhoo.net.widget.base.BaseActivity;
import com.longhoo.net.widget.base.WebViewActivity;
import com.luck.picture.lib.tools.PictureFileUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.longhoo.net.mine.ui.MeFragment.MSG_LOGIN_OUT;

/**
 * Created by ${CC} on 2017/12/21.
 */

public class SetActivity extends BaseActivity {
    @BindView(R.id.tv_changepwd)
    TextView tvChangepwd;
    @BindView(R.id.lv_changpwd)
    LinearLayout lvChangpwd;
    @BindView(R.id.tv_userxieyi)
    TextView tvUserxieyi;
    @BindView(R.id.lv_userxieyi)
    LinearLayout lvUserxieyi;
    @BindView(R.id.tv_aboutours)
    TextView tvAboutours;
    @BindView(R.id.lv_aboutours)
    LinearLayout lvAboutours;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.tv_versioncount)
    TextView tvVersioncount;
    @BindView(R.id.lv_version)
    LinearLayout lvVersion;
    @BindView(R.id.tv_clear_cace)
    TextView tvClearCace;
    @BindView(R.id.tv_clear_cacecount)
    TextView tvClearCacecount;
    @BindView(R.id.lv_clear_cace)
    LinearLayout lvClearCace;
    @BindView(R.id.lv_listbottom)
    LinearLayout lvListbottom;
    @BindView(R.id.tv_cancle)
    TextView tvCancle;
    @BindView(R.id.lv_cancle)
    LinearLayout lvCancle;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_mobile)
    TextView tvMobile;
    @BindView(R.id.lv_changmobile)
    LinearLayout lvChangmobile;

    @Override
    protected int getContentId() {
        return R.layout.activity_set;
    }

    @Override
    protected void initViews() {
        EventBus.getDefault().register(this);
        tvVersioncount.setText("当前版本" + Utils.getVersionName(SetActivity.this));
        tvClearCacecount.setText(CacheUtil.getInstance().getAllCacheSize(SetActivity.this));
        MyApplication app = (MyApplication) getApplicationContext();
        if (app.getmLoginBean() == null) {
            ToastUtils.getInstance().showToast(this, "登录过期，请重新登陆！");
            return;
        }
        tvMobile.setText("修改手机号");
    }

    @Override
    protected void initToolbar() {
        tvTitle.setText("设置");
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


    protected void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SetActivity.this);
        builder.setMessage("确认退出吗？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LoginOut();
                dialog.dismiss();

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    @OnClick({R.id.lv_changpwd, R.id.lv_userxieyi, R.id.lv_aboutours,
            R.id.lv_version, R.id.lv_clear_cace, R.id.lv_cancle})
    public void onClick(View view) {
        String url;
        Intent intent = null;
        switch (view.getId()) {
            case R.id.lv_changpwd:

                intent = new Intent(SetActivity.this, ModifyPwdActivity.class);

                startActivity(intent);
                break;
            case R.id.lv_userxieyi:
                WebViewActivity.goToWebView(this, Consts.BASE_URL + "/application/public_score?type=1", "用户协议", false);
                break;
            case R.id.lv_aboutours:
                WebViewActivity.goToWebView(this, Consts.BASE_URL + "/application/public_score?type=2", "关于我们", false);
                break;
            case R.id.lv_version:
                break;
            case R.id.lv_clear_cace:
                //清理缓存
                PictureFileUtils.deleteCacheDirFile(SetActivity.this);
                CacheUtil.getInstance().clearAllCache(SetActivity.this);
                tvClearCacecount.setText("0M");
                break;
            case R.id.lv_cancle:
                dialog();

                break;

        }
    }

    public void LoginOut() {
        EnterCheckUtil.getInstance().outLogin();
        MessageEvent event = new MessageEvent();
        event.message = MSG_LOGIN_OUT;
        EventBus.getDefault().post(event);
        finish();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        String msg = event.message;
        if (TextUtils.equals(msg, MeFragment.MSG_LOGIN_OUT)) {
            if (!isFinishing()) {
                finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.lv_changmobile)
    public void onViewClicked() {
        Intent intent=new Intent();
        intent = new Intent(SetActivity.this, Change_MobileActivity.class);
        startActivity(intent);
    }
}
