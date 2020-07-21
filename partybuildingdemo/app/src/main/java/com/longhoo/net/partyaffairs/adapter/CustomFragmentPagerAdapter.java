package com.longhoo.net.partyaffairs.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.TextUtils;

import com.longhoo.net.headline.bean.PartyMemberSQBean;
import com.longhoo.net.manageservice.bean.MaterialMenuBean;
import com.longhoo.net.manageservice.ui.MaterialReportFragment;
import com.longhoo.net.partyaffairs.bean.TaiZhangMenuBean;
import com.longhoo.net.partyaffairs.ui.TaiZhangItemFragment;
import com.longhoo.net.study.bean.StudyMenusBean;
import com.longhoo.net.study.ui.StudyItemFragment;
import com.longhoo.net.study.ui.VideoListFragment;

import java.util.List;

/**
 * Created by CK on 2017/12/5.
 * Email:910663958@qq.com
 */

public class CustomFragmentPagerAdapter extends FragmentPagerAdapter {
    private String[] titles;
    private Fragment[] fragments;
    private List<StudyMenusBean.DataBean> menusBeanList;
    private List<MaterialMenuBean.DataBeanX.DataBean> menusBeanList3;

    public CustomFragmentPagerAdapter(FragmentManager fm, String[] titles, Fragment[] fragments) {
        super(fm);
        this.titles = titles;
        this.fragments = fragments;
    }

    public CustomFragmentPagerAdapter(FragmentManager fm, List<StudyMenusBean.DataBean> menusBeanList, List<TaiZhangMenuBean.DataBean> menusBeanList2) {
        super(fm);
        this.menusBeanList = menusBeanList;
    }

    public CustomFragmentPagerAdapter(FragmentManager fm, List<MaterialMenuBean.DataBeanX.DataBean> menusBeanList3) {
        super(fm);
        this.menusBeanList3 = menusBeanList3;
    }

    @Override
    public Fragment getItem(int position) {
        if (getCount() <= 0)
            return null;
        if (fragments != null) {
            return fragments[position];
        }
        //学习模块
        if (menusBeanList != null) {
            return StudyItemFragment.newInstance(menusBeanList.get(position));
        }

        //材料上报模块
        if (menusBeanList3 != null) {
            if (menusBeanList3.size() > 0) {
                return MaterialReportFragment.newInstance(menusBeanList3.get(position).getFid());
            }
        }
        return null;
    }

    @Override
    public int getCount() {
        if (fragments != null) {
            return Math.min(titles.length, fragments.length);
        }
        if (menusBeanList != null) {
            if (menusBeanList.size() > 0) {
                return menusBeanList.size();
            }
        }
        if (menusBeanList3 != null) {
            if (menusBeanList3.size() > 0) {
                return menusBeanList3.size();
            }
        }
        return 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (getCount() <= 0)
            return super.getPageTitle(position);
        if (titles != null) {
            return titles[position];
        }
        if (menusBeanList != null) {
            if (menusBeanList.size() > 0) {
                return menusBeanList.get(position).getName();
            }
        }
        if (menusBeanList3 != null) {
            if (menusBeanList3.size() > 0) {
                return menusBeanList3.get(position).getName();
            }
        }
        return "";
    }
}
