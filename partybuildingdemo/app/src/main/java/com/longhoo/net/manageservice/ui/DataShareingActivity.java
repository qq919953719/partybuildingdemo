package com.longhoo.net.manageservice.ui;

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
import com.longhoo.net.R;
import com.longhoo.net.manageservice.bean.DataShareMenuBean;
import com.longhoo.net.partyaffairs.adapter.CustomFragmentPagerAdapter;
import com.longhoo.net.partyaffairs.bean.TaiZhangMenuBean;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.utils.httprequest.OkHttpCallback;
import com.longhoo.net.utils.httprequest.OkHttpUtil;
import com.longhoo.net.widget.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 管理服务--资料共享
 */
public class DataShareingActivity extends BaseActivity {

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
    private String[] titles;
    private Fragment[] fragments;

    @Override
    protected int getContentId() {
        return R.layout.activity_data_shareing;
    }

    @Override
    protected void initViews() {
        doGetMenus();
//        titles = new String[]{"十九大学习", "党章"};
////        fragments = new Fragment[]{DataShareingFragment.newInstance("1", "十九大学习"), DataShareingFragment.newInstance("2", "党章")};
////        adapter = new CustomFragmentPagerAdapter(getSupportFragmentManager(), titles, fragments);
////        viewPager.setAdapter(adapter);
////        viewPager.setOffscreenPageLimit(fragments.length);
////        slidingTabLayout.setViewPager(viewPager);
////        progressBar.setVisibility(View.GONE);
////        llNetErrorPanel.setVisibility(View.GONE);
////        llListPanel.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initToolbar() {
        tvTitle.setText("资料共享");
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
                doGetMenus();
                break;
        }
    }

    /**
     * 获取tab菜单
     */
    private void doGetMenus() {
        progressBar.setVisibility(View.VISIBLE);
        String url = Consts.BASE_URL + "/Application/shareFileTypeList";
        Map<String,String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        OkHttpUtil.getInstance().doAsyncPost(url,params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                if (isFinishing()) {
                    return;
                }
                ULog.e("ck", "菜单：" + response);
                Gson gson = new Gson();
                try {
                    DataShareMenuBean menuBean = gson.fromJson(response, DataShareMenuBean.class);
                    String code = menuBean.getCode();
                    String msg = menuBean.getMsg();
                    if (!TextUtils.equals(code, "0")) {
                        progressBar.setVisibility(View.GONE);
                        llListPanel.setVisibility(View.GONE);
                        llNetErrorPanel.setVisibility(View.VISIBLE);
                        return;
                    }
                    List<DataShareMenuBean.DataBeanX.DataBean> tempList = menuBean.getData().getData();
                    int size = 0;
                    if (tempList != null) {
                        size = tempList.size();
                        titles = new String[size];
                        fragments = new Fragment[size];
                        for (int i = 0; i < size; i++) {
                            titles[i] = tempList.get(i).getName();
                            fragments[i] = DataShareingFragment.newInstance(tempList.get(i).getFid(), tempList.get(i).getName());
                        }
                    }
                    adapter = new CustomFragmentPagerAdapter(getSupportFragmentManager(), titles, fragments);
                    viewPager.setAdapter(adapter);
                    progressBar.setVisibility(View.GONE);
                    llNetErrorPanel.setVisibility(View.GONE);
                    llListPanel.setVisibility(View.VISIBLE);
                    if (size <= 4) {
                        slidingTabLayout.setTabSpaceEqual(true);
                    } else {
                        slidingTabLayout.setTabSpaceEqual(false);
                        slidingTabLayout.setTabPadding(14);
                    }
                    slidingTabLayout.setViewPager(viewPager);
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

}
