package com.longhoo.net.study.ui;

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
import com.longhoo.net.partyaffairs.adapter.CustomFragmentPagerAdapter;
import com.longhoo.net.study.bean.ClassDisplayMenuBean;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.utils.httprequest.OkHttpCallback;
import com.longhoo.net.utils.httprequest.OkHttpUtil;
import com.longhoo.net.widget.base.BaseActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 党课展示
 */
public class PartyClassDisplayActivity extends BaseActivity {

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
    private Fragment [] fragments;

    @Override
    protected int getContentId() {
        return R.layout.activity_party_class_display;
    }

    @Override
    protected void initViews() {
         doGetMenus();
    }

    @Override
    protected void initToolbar() {
        tvTitle.setText("党建课程 ");
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
        String url = Consts.BASE_URL + "/ClassDisplay/getDisplayCatList";
        Map<String,String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                if(isFinishing()){
                    return;
                }
                ULog.e("ck", "党课展示菜单：" + response);
                Gson gson = new Gson();
                try {
                    ClassDisplayMenuBean menuBean = gson.fromJson(response, ClassDisplayMenuBean.class);
                    String code = menuBean.getCode();
                    String msg = menuBean.getMsg();
                    if (!TextUtils.equals(code, "0")) {
                        progressBar.setVisibility(View.GONE);
                        llListPanel.setVisibility(View.GONE);
                        llNetErrorPanel.setVisibility(View.VISIBLE);
                        return;
                    }
                    List<ClassDisplayMenuBean.DataBean> list = menuBean.getData();
                    int size = 0;
                    if (list != null) {
                        size = list.size();
                        fragments = new Fragment[size];
                        titles = new String[size];
                        for(int i=0;i<size;i++){
                            fragments[i]= PartyClassDisplayFragment.newInstance(list.get(i).getCid(),list.get(i).getName());
                            titles[i] = list.get(i).getName();
                        }
                    }
                    adapter = new CustomFragmentPagerAdapter(getSupportFragmentManager(), titles, fragments);
                    viewPager.setOffscreenPageLimit(fragments!=null?fragments.length:0);
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
                if(isFinishing()){
                    return;
                }
                progressBar.setVisibility(View.GONE);
                llListPanel.setVisibility(View.GONE);
                llNetErrorPanel.setVisibility(View.VISIBLE);
            }
        });
    }

}
