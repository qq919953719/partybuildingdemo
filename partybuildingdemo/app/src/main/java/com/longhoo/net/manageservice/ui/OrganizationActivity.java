package com.longhoo.net.manageservice.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.widget.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 组织架构
 */
public class OrganizationActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.container)
    FrameLayout container;

    @Override
    protected int getContentId() {
        return R.layout.activity_organization;
    }

    @Override
    protected void initViews() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        OrganizationFragment fragment = new OrganizationFragment();
        ft.replace(R.id.container, fragment);
        ft.commit();
    }

    @Override
    protected void initToolbar() {
        tvTitle.setText("组织架构");
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.left_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
