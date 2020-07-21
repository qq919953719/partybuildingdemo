package com.longhoo.net.manageservice.ui;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.longhoo.net.R;
import com.longhoo.net.partyaffairs.adapter.CustomFragmentPagerAdapter;
import com.longhoo.net.widget.base.BaseLazyFragment;


import butterknife.BindView;
import butterknife.Unbinder;

public class OrganizationFragment extends BaseLazyFragment {

    @BindView(R.id.sliding_tabs)
    SlidingTabLayout slidingTabs;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    Unbinder unbinder;

    @Override
    protected int getContentId() {
        return R.layout.fragment_organization;
    }

    @Override
    protected void onLazyLoad() {
        String[] meauList = {"组织架构", "组织概况"};
        Fragment[] fragmentList = new Fragment[]{new OrganizationArchiFragment(), new OrganizationSurveyFragment()};
        CustomFragmentPagerAdapter adapter = new CustomFragmentPagerAdapter(getChildFragmentManager(), meauList, fragmentList);
        viewPager.setAdapter(adapter);
        slidingTabs.setViewPager(viewPager);
    }

    @Override
    protected void initViews(View view) {
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected long setLoadInterval() {
        return 0;
    }

}
