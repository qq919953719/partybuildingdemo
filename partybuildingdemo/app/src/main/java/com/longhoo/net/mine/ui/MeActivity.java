package com.longhoo.net.mine.ui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.longhoo.net.R;
import com.longhoo.net.utils.MessageEvent;
import com.longhoo.net.widget.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MeActivity extends BaseActivity {


    @Override
    protected int getContentId() {
        return R.layout.activity_me;
    }

    @Override
    protected void initViews() {
        EventBus.getDefault().register(this);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, new MeFragment());
        ft.commit();
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        String msg = event.message;
        if (TextUtils.equals(msg, MeFragment.MSG_LOGIN_OUT)) {
            finish();
        }
    }
}
