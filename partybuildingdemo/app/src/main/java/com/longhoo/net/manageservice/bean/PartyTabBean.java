package com.longhoo.net.manageservice.bean;

import android.content.Intent;

/**
 * Created by CK on 2018/7/5.
 * Email:910663958@qq.com
 */

public class PartyTabBean {
    private String title;
    private int resId;
    private Intent intent;
    private boolean canClick = true;

    public PartyTabBean(Intent intent, int resId,String title) {
        this.title = title;
        this.resId = resId;
        this.intent = intent;
    }

    public PartyTabBean(Intent intent, int resId,String title, boolean canClick) {
        this.title = title;
        this.resId = resId;
        this.intent = intent;
        this.canClick = canClick;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public boolean isCanClick() {
        return canClick;
    }

    public void setCanClick(boolean canClick) {
        this.canClick = canClick;
    }
}
