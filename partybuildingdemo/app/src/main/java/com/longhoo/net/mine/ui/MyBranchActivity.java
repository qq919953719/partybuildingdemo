package com.longhoo.net.mine.ui;


import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.partyaffairs.adapter.CustomFragmentPagerAdapter;
import com.longhoo.net.widget.NoScrollViewPager;
import com.longhoo.net.widget.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author CK 我的支部
 */
public class MyBranchActivity extends BaseActivity implements ViewPager.OnPageChangeListener {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_partyfee)
    TextView tvPartyfee;
    @BindView(R.id.iv_partyfee)
    ImageView ivPartyfee;
    @BindView(R.id.partyfee_panel)
    LinearLayout partyfeePanel;
    @BindView(R.id.tv_branch)
    TextView tvBranch;
    @BindView(R.id.iv_branch)
    ImageView ivBranch;
    @BindView(R.id.branch_panel)
    LinearLayout branchPanel;
    @BindView(R.id.view_pager)
    NoScrollViewPager viewPager;
    private CustomFragmentPagerAdapter adapter;
    private boolean isShowBack = true;

    private Fragment[] fragments;
    private String[] titles;

    @Override
    protected int getContentId() {
        return R.layout.activity_my_branch;
    }


    @Override
    protected void initViews() {
        fragments = new Fragment[]{new PartyfeeManageFragment(), new OrganizationaLifeFragment()};
        titles = new String[]{"党费管理", "组织生活管理"};
        adapter = new CustomFragmentPagerAdapter(getSupportFragmentManager(), titles, fragments);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(fragments.length);
        viewPager.addOnPageChangeListener(this);
        viewPager.setCurrentItem(0);
        viewPager.setNoScroll(true);
    }

    @Override
    protected void initToolbar() {
        tvTitle.setText("我的支部");
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        if (isShowBack) {
            toolbar.setNavigationIcon(R.drawable.left_arrow);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    @OnClick({R.id.partyfee_panel, R.id.branch_panel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.partyfee_panel:
                viewPager.setCurrentItem(0);
                break;
            case R.id.branch_panel:
                viewPager.setCurrentItem(1);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            tvPartyfee.setTextColor(Color.parseColor("#e51f20"));
            tvBranch.setTextColor(Color.parseColor("#323232"));
            ivPartyfee.setImageResource(R.drawable.ic_tab_arrow_select);
            ivBranch.setImageResource(R.drawable.ic_tab_arrow_unselect);
        } else {
            tvPartyfee.setTextColor(Color.parseColor("#323232"));
            tvBranch.setTextColor(Color.parseColor("#e51f20"));
            ivPartyfee.setImageResource(R.drawable.ic_tab_arrow_unselect);
            ivBranch.setImageResource(R.drawable.ic_tab_arrow_select);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
