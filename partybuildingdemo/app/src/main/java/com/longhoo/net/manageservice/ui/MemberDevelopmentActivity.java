package com.longhoo.net.manageservice.ui;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longhoo.net.MyApplication;
import com.longhoo.net.R;
import com.longhoo.net.widget.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MemberDevelopmentActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.lv_public)
    LinearLayout lvPublic;
    @BindView(R.id.lv_applay)
    LinearLayout lvApplay;


    @Override
    protected int getContentId() {
        return R.layout.activity_member_development;
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        lvPublic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MemberDevelopmentActivity.this, MemberPublicListActivity.class);
                startActivity(intent);
            }
        });

        MyApplication app = (MyApplication) getApplicationContext();
        String roleId = app.getmLoginBean().getData().getRoleid() + "";
        String check = app.getmLoginBean().getData().getApplycheck();
        if (roleId.equals("1") || roleId.equals("2")|| check.equals("1")) {
            lvApplay.setVisibility(View.VISIBLE);
            lvApplay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(MemberDevelopmentActivity.this, MemberApplyListActivity.class);
                    startActivity(intent);
                }
            });
            //显示
        } else {
            lvApplay.setVisibility(View.GONE);
            //隐藏

        }


    }

    @Override
    protected void initToolbar() {
        tvTitle.setText("党员发展");
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.left_arrow);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.left_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}
