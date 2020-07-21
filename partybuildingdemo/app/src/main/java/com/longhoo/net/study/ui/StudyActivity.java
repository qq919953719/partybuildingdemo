package com.longhoo.net.study.ui;

import android.support.v4.app.FragmentTransaction;

import com.longhoo.net.R;
import com.longhoo.net.widget.base.BaseActivity;


public class StudyActivity extends BaseActivity {
    @Override
    protected int getContentId() {
        return R.layout.activity_study;
    }

    @Override
    protected void initViews() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container,StudyFragment.newInstance(true));
        ft.commit();
    }

    @Override
    protected void initToolbar() {

    }
}
