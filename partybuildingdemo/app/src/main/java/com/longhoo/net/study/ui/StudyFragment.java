package com.longhoo.net.study.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
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
import com.longhoo.net.mine.ui.NoteTakingActivity;
import com.longhoo.net.partyaffairs.adapter.CustomFragmentPagerAdapter;
import com.longhoo.net.study.bean.StudyMenusBean;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.utils.httprequest.OkHttpCallback;
import com.longhoo.net.utils.httprequest.OkHttpUtil;
import com.longhoo.net.widget.base.BaseLazyFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author CK 首页——学习
 */
public class StudyFragment extends BaseLazyFragment {

    @BindView(R.id.sliding_tabs)
    SlidingTabLayout slidingTabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_write)
    TextView tvWrite;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ll_list_panel)
    LinearLayout llListPanel;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.tv_reload)
    TextView tvReload;
    @BindView(R.id.ll_net_error_panel)
    LinearLayout llNetErrorPanel;
    //private String[] titles = {"学习动态", "直播", "两学一做", "资料库"};
    private CustomFragmentPagerAdapter adapter;
    private List<StudyMenusBean.DataBean> menuBeanList = new ArrayList<>();
    private boolean isShowBack;

    public static StudyFragment newInstance(boolean isShowBack){
        StudyFragment fragment = new StudyFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isShowBackIcon",isShowBack);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (getArguments() != null) {
            isShowBack = getArguments().getBoolean("isShowBackIcon");
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentId() {
        return R.layout.fragment_study;
    }

    @Override
    protected void onLazyLoad() {
        doGetMenus();
    }

    @Override
    protected void initViews(View view) {
    }

    @Override
    protected void initToolbar() {
        tvTitle.setText("在线学习");
        toolbar.setTitle("");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        if (isShowBack) {
            toolbar.setNavigationIcon(R.drawable.left_arrow);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                }
            });
        }
    }

    @Override
    protected long setLoadInterval() {
        return 0;
    }

    @OnClick({R.id.tv_write,R.id.tv_reload})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_write:
                Intent intent = new Intent(getActivity(), NoteTakingActivity.class);
                startActivity(intent);
                break;
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
        String url = Consts.BASE_URL + "/Onlinestudy/public_menustudy_second";
        Map<String,String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        OkHttpUtil.getInstance().doAsyncPost(url, new HashMap<String, String>(), new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                if(!isPrepared){
                    return;
                }
                ULog.e("ck", "学习菜单：" + response);
                Gson gson = new Gson();
                try {
                    StudyMenusBean studyMenusBean = gson.fromJson(response, StudyMenusBean.class);
                    String code = studyMenusBean.getCode();
                    String msg = studyMenusBean.getMsg();
                    if (!TextUtils.equals(code, "0")) {
                        progressBar.setVisibility(View.GONE);
                        llListPanel.setVisibility(View.GONE);
                        llNetErrorPanel.setVisibility(View.VISIBLE);
                        return;
                    }
                    menuBeanList.clear();
                    menuBeanList.addAll(studyMenusBean.getData());
                    adapter = new CustomFragmentPagerAdapter(getChildFragmentManager(), menuBeanList, null);
                    viewPager.setAdapter(adapter);
                    viewPager.setOffscreenPageLimit(menuBeanList.size());
                    progressBar.setVisibility(View.GONE);
                    llNetErrorPanel.setVisibility(View.GONE);
                    llListPanel.setVisibility(View.VISIBLE);
                    int size = menuBeanList.size();
                    if (size <= 4) {
                        if (size <= 1) {
                            slidingTabLayout.setVisibility(View.GONE);
                        }
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
                if(!isPrepared){
                    return;
                }
                progressBar.setVisibility(View.GONE);
                llListPanel.setVisibility(View.GONE);
                llNetErrorPanel.setVisibility(View.VISIBLE);
            }
        });
    }

}
