package com.longhoo.net.partyaffairs.ui;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.longhoo.net.MyApplication;
import com.longhoo.net.R;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.widget.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AppForPartyActivity extends BaseActivity {

    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.tv_approve)
    TextView tvApprove;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
//    @BindView(R.id.tv_approve)
//    TextView tvApprove;

    @Override
    protected int getContentId() {
        return R.layout.activity_app_for_party;
    }

    @Override
    protected void initViews() {
    }

    @Override
    protected void initToolbar() {
        tvTitle.setText("入党申请");
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

    @OnClick({R.id.tv_submit, R.id.tv_approve})
    public void onClick(View view) {
        if (!EnterCheckUtil.getInstance().isLogin(true)) {
            return;
        }
        Intent intent = null;
        switch (view.getId()) {
            case R.id.tv_submit:
                if (EnterCheckUtil.getInstance().IS_MEMBER()) {
                    ToastUtils.getInstance().showToast(this, "很抱歉，此模块不对外开放！");
                    return;
                }
                intent = new Intent(this, CommitForPartyActivity.class);
                startActivity(intent);

                break;
            case R.id.tv_approve:
                if (EnterCheckUtil.getInstance().IS_MEMBER()) {
                    intent = new Intent(this, ApplyListActivity.class);
                    startActivity(intent);
                } else {
                    ToastUtils.getInstance().showToast(this, "很抱歉，此模块不对外开放！");
                }
                break;
        }
    }

}
