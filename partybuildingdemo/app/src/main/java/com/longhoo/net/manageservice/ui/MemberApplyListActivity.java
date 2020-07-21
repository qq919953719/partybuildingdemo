package com.longhoo.net.manageservice.ui;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.longhoo.net.MyApplication;
import com.longhoo.net.R;
import com.longhoo.net.partyaffairs.adapter.CustomFragmentPagerAdapter;
import com.longhoo.net.partyaffairs.bean.TaiZhangMenuBean;
import com.longhoo.net.partyaffairs.ui.TaiZhangItemFragment;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.MessageEvent;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.utils.httprequest.OkHttpCallback;
import com.longhoo.net.utils.httprequest.OkHttpUtil;
import com.longhoo.net.widget.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 党员发展申请列表
 */
public class MemberApplyListActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.sliding_tabs)
    SlidingTabLayout slidingTabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.ll_list_panel)
    LinearLayout llListPanel;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.tv_reload)
    TextView tvReload;
    @BindView(R.id.ll_net_error_panel)
    LinearLayout llNetErrorPanel;
    private CustomFragmentPagerAdapter adapter;
    private Fragment[] fragments;
    private String[] titles;
    String strTid = "";

    @Override
    protected int getContentId() {
        return R.layout.activity_tai_zhang;
    }

    @Override
    protected void initViews() {
        EventBus.getDefault().register(this);
        doGetMenus();

        progressBar.setVisibility(View.VISIBLE);
        llNetErrorPanel.setVisibility(View.GONE);
        llListPanel.setVisibility(View.GONE);

    }

    List<TaiZhangMenuBean.DataBean> list = null;

    @Override
    protected void initToolbar() {
        tvTitle.setText("申请");
        toolbar.setTitle("");

        MyApplication app = (MyApplication) getApplicationContext();
        int roleId = app.getmLoginBean().getData().getRoleid();
        String check = app.getmLoginBean().getData().getApplycheck();
        int mcheck =Integer.parseInt(check);
        if (roleId == 1 || roleId == 2||mcheck==1) {
            tvRight.setVisibility(View.VISIBLE);
            tvRight.setText("提交");
            tvRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (list != null) {
                        if (list.size() > 0) {
                            strTid = list.get(viewPager.getCurrentItem()).getId();
                            Intent intent = new Intent(MemberApplyListActivity.this, MemberApplyActivity.class);
                            intent.putExtra("tid", strTid);
                            startActivity(intent);
                        }
                    }

                }
            });
        }
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.left_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @OnClick({R.id.tv_reload})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_reload:
                doGetMenus();
                break;
        }
    }

    /**
     * 获取tab菜单
     */
    private void doGetMenus() {
        progressBar.setVisibility(View.VISIBLE);
        //index.php?m=Api&c=Apply&a=public_type_list
        String url = Consts.BASE_URL + "/Apply/public_type_list";
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                if (isFinishing()) {
                    return;
                }
                ULog.e("cc", "组织生活菜单：" + response);
                Gson gson = new Gson();
                try {
                    TaiZhangMenuBean taiZhangMenuBean = gson.fromJson(response, TaiZhangMenuBean.class);
                    String code = taiZhangMenuBean.getCode();
                    String msg = taiZhangMenuBean.getMsg();
                    if (!TextUtils.equals(code, "0")) {
                        progressBar.setVisibility(View.GONE);
                        llListPanel.setVisibility(View.GONE);
                        llNetErrorPanel.setVisibility(View.VISIBLE);
                        return;
                    }
                    list = taiZhangMenuBean.getData();
                    if (list != null && list.size() > 0) {
                        fragments = new Fragment[list.size()];
                        titles = new String[list.size()];
                        for (int i = 0; i < list.size(); i++) {
                            fragments[i] = MemberApplyItemFragment.newInstance(list.get(i).getId(), list.get(i).getName());
                            titles[i] = list.get(i).getName();
                        }
                        adapter = new CustomFragmentPagerAdapter(getSupportFragmentManager(), titles, fragments);
                        viewPager.setAdapter(adapter);
                        viewPager.setOffscreenPageLimit(fragments.length);
                        progressBar.setVisibility(View.GONE);
                        llNetErrorPanel.setVisibility(View.GONE);
                        llListPanel.setVisibility(View.VISIBLE);
                        int size = fragments.length;
                        if (size <= 4) {
                            slidingTabLayout.setTabSpaceEqual(true);
                        } else {
                            slidingTabLayout.setTabSpaceEqual(false);
                            slidingTabLayout.setTabPadding(14);
                        }
                        slidingTabLayout.setViewPager(viewPager);
                    }

                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    progressBar.setVisibility(View.GONE);
                    llListPanel.setVisibility(View.GONE);
                    llNetErrorPanel.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                if (isFinishing()) {
                    return;
                }
                progressBar.setVisibility(View.GONE);
                llListPanel.setVisibility(View.GONE);
                llNetErrorPanel.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event.msgType == MessageEvent.MSG_REFRESH_ORG_LIFE_LIST) {
            if (fragments != null && fragments.length > 0) {
                for (int i = 0; i < fragments.length; i++) {
                    ((TaiZhangItemFragment) fragments[i]).doRefresh();
                }
            }
        }
    }
}
