package com.longhoo.net.manageservice.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.longhoo.net.MyApplication;
import com.longhoo.net.R;
import com.longhoo.net.partyaffairs.adapter.CustomFragmentPagerAdapter;
import com.longhoo.net.utils.MessageEvent;
import com.longhoo.net.widget.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 管理服务--活动报名
 */
public class ActsEncrollActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
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
    @BindView(R.id.tv_right)
    TextView tvRight;
    private CustomFragmentPagerAdapter adapter;
    private String[] titles;
    private Fragment[] fragments;
    private boolean isShowRight = true;

    @Override
    protected int getContentId() {
        return R.layout.activity_acts_encroll;
    }

    @Override
    protected void initViews() {
        // doGetMenus();
        EventBus.getDefault().register(this);
        titles = new String[]{"十九大学习"};
        fragments = new Fragment[]{ActsEncrollFragment.newInstance("1", "十九大学习")};
        adapter = new CustomFragmentPagerAdapter(getSupportFragmentManager(), titles, fragments);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(fragments.length);
        slidingTabLayout.setViewPager(viewPager);
        progressBar.setVisibility(View.GONE);
        llNetErrorPanel.setVisibility(View.GONE);
        llListPanel.setVisibility(View.VISIBLE);
        slidingTabLayout.setVisibility(View.GONE);
        findViewById(R.id.line).setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event.msgType == MessageEvent.MSG_FINISH_SEND_ACTIVITY_ARCHIVES) {
            if (fragments != null && fragments.length > 0) {
                for (int i = 0; i < fragments.length; i++) {
                    ((ActsEncrollFragment) fragments[i]).doRefresh();
                }
            }
        }
    }

    @Override
    protected void initToolbar() {
        tvTitle.setText("党员活动 ");
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.left_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tvRight.setVisibility(isShowRight ? View.VISIBLE : View.GONE);
        MyApplication app = (MyApplication) getApplicationContext();
        //0 普通党员  2支部书记  6总支书记  1组织部
        int roleId = app.getmLoginBean().getData().getRoleid();
        if (roleId == 2 || roleId == 1) {
            //只有支部书记能发
            tvRight.setVisibility(View.VISIBLE);
        } else {
            tvRight.setVisibility(View.GONE);
        }
        tvRight.setText("发起党员活动");
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActsEncrollActivity.this, ReleasePartyNumberActivity.class);
                startActivity(intent);
            }
        });
    }

    @OnClick({R.id.tv_reload})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_reload:
                //doGetMenus();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    /**
     * 获取tab菜单
     */
//    private void doGetMenus() {
//        progressBar.setVisibility(View.VISIBLE);
//        String url = Consts.BASE_URL + "/Application/shareFileTypeList";
//        Map<String,String> params = ne HashMap<>();
//        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
//        OkHttpUtil.getInstance().doAsyncPost(url,params, new OkHttpCallback() {
//            @Override
//            public void onSuccess(String response) {
//                if (isFinishing()) {
//                    return;
//                }
//                ULog.e("ck", "菜单：" + response);
//                Gson gson = new Gson();
//                try {
//                    DataShareMenuBean menuBean = gson.fromJson(response, DataShareMenuBean.class);
//                    String code = menuBean.getCode();
//                    String msg = menuBean.getMsg();
//                    if (!TextUtils.equals(code, "0")) {
//                        progressBar.setVisibility(View.GONE);
//                        llListPanel.setVisibility(View.GONE);
//                        llNetErrorPanel.setVisibility(View.VISIBLE);
//                        return;
//                    }
//                    List<DataShareMenuBean.DataBeanX.DataBean> tempList = menuBean.getData().getData();
//                    int size = 0;
//                    if (tempList != null) {
//                        size = tempList.size();
//                        titles = new String[size];
//                        fragments = new Fragment[size];
//                        for (int i = 0; i < size; i++) {
//                            titles[i] = tempList.get(i).getName();
//                            fragments[i] = ActsEncrollFragment.newInstance(tempList.get(i).getFid(), tempList.get(i).getName());
//                        }
//                    }
//                    adapter = new CustomFragmentPagerAdapter(getSupportFragmentManager(), titles, fragments);
//                    viewPager.setAdapter(adapter);
//                    slidingTabLayout.setViewPager(viewPager);
//                    progressBar.setVisibility(View.GONE);
//                    llNetErrorPanel.setVisibility(View.GONE);
//                    llListPanel.setVisibility(View.VISIBLE);
//                    if (size <= 4) {
//                        slidingTabLayout.setTabSpaceEqual(true);
//                    } else {
//                        slidingTabLayout.setTabSpaceEqual(false);
//                        slidingTabLayout.setTabPadding(14);
//                    }
//                } catch (JsonSyntaxException e) {
//                    e.printStackTrace();
//                    progressBar.setVisibility(View.GONE);
//                    llListPanel.setVisibility(View.GONE);
//                    llNetErrorPanel.setVisibility(View.VISIBLE);
//                }
//            }
//
//            @Override
//            public void onFailure(String errorMsg) {
//                if (isFinishing()) {
//                    return;
//                }
//                progressBar.setVisibility(View.GONE);
//                llListPanel.setVisibility(View.GONE);
//                llNetErrorPanel.setVisibility(View.VISIBLE);
//            }
//        });
//    }

}
