package com.longhoo.net.supervision.ui;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.longhoo.net.R;
import com.longhoo.net.mine.ui.MyAttendFragment;
import com.longhoo.net.partyaffairs.adapter.CustomFragmentPagerAdapter;
import com.longhoo.net.widget.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 活动统计
 */
public class ActStatisticsActivity extends BaseActivity {

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
    private CustomFragmentPagerAdapter adapter;
    private String titles[];
    private Fragment fragments[];

    @Override
    protected int getContentId() {
        return R.layout.activity_act_statistics;
    }

    @Override
    protected void initViews() {
        // doGetMenus();
        titles = new String[]{"近期组织生活", "支部组织生活情况", "我所参加的活动"};
        fragments = new Fragment[]{new OrgLifeArrangeFragment(), new ActsSituationFragment(), new MyAttendFragment()};
        adapter = new CustomFragmentPagerAdapter(getSupportFragmentManager(), titles, fragments);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(fragments.length);
        slidingTabLayout.setViewPager(viewPager);
        progressBar.setVisibility(View.GONE);
        llNetErrorPanel.setVisibility(View.GONE);
        llListPanel.setVisibility(View.VISIBLE);
        slidingTabLayout.setTabSpaceEqual(true);
    }

    @Override
    protected void initToolbar() {
        tvTitle.setText("我的支部");
        toolbar.setTitle("");
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
                //doGetMenus();
                break;
        }
    }

    /**
     * 获取tab菜单
     */
//    private void doGetMenus() {
//        progressBar.setVisibility(View.VISIBLE);
//        String url = Consts.BASE_URL + "/News/public_menujob";
//        OkHttpUtil.getInstance().doAsyncPost(url, new HashMap<String, String>(), new OkHttpCallback() {
//            @Override
//            public void onSuccess(String response) {
//                if(isFinishing()){
//                    return;
//                }
//                ULog.e("ck", "台账菜单：" + response);
//                Gson gson = new Gson();
//                try {
//                    TaiZhangMenuBean taiZhangMenuBean = gson.fromJson(response, TaiZhangMenuBean.class);
//                    String code = taiZhangMenuBean.getCode();
//                    String msg = taiZhangMenuBean.getMsg();
//                    if (!TextUtils.equals(code, "0")) {
//                        progressBar.setVisibility(View.GONE);
//                        llListPanel.setVisibility(View.GONE);
//                        llNetErrorPanel.setVisibility(View.VISIBLE);
//                        return;
//                    }
//                    menuBeanList.clear();
//                    menuBeanList.addAll(taiZhangMenuBean.getData().getMenus());
//                    adapter = new CustomFragmentPagerAdapter(getSupportFragmentManager(), null, menuBeanList);
//                    viewPager.setAdapter(adapter);
//                    slidingTabLayout.setViewPager(viewPager);
//                    progressBar.setVisibility(View.GONE);
//                    llNetErrorPanel.setVisibility(View.GONE);
//                    llListPanel.setVisibility(View.VISIBLE);
//                    int size = menuBeanList.size();
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
//                if(isFinishing()){
//                    return;
//                }
//                progressBar.setVisibility(View.GONE);
//                llListPanel.setVisibility(View.GONE);
//                llNetErrorPanel.setVisibility(View.VISIBLE);
//            }
//        });
//    }

}
