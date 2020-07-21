package com.longhoo.net.manageservice.ui;


import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.longhoo.net.R;
import com.longhoo.net.manageservice.bean.MaterialMenuBean;
import com.longhoo.net.mine.bean.MyStudyArchivesMenuBean;
import com.longhoo.net.partyaffairs.adapter.CustomFragmentPagerAdapter;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.utils.httprequest.OkHttpCallback;
import com.longhoo.net.utils.httprequest.OkHttpUtil;
import com.longhoo.net.widget.MyDialog;
import com.longhoo.net.widget.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author CK 首页——材料上报
 */
public class MaterialReportActivity extends BaseActivity {

    @BindView(R.id.sliding_tabs)
    SlidingTabLayout slidingTabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tv_title)
    TextView tvTitle;
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
    @BindView(R.id.tv_sel_year)
    TextView tvSelYear;
    private CustomFragmentPagerAdapter adapter;
    private Fragment[] fragments;
    private String[] titles;
    private List<String> yearList = new ArrayList<>();

    @Override
    protected int getContentId() {
        return R.layout.activity_material_report;
    }

    @Override
    protected void initViews() {
        getYearData();
        doGetMenus();
    }

    @Override
    protected void initToolbar() {
        tvTitle.setText("材料上报");
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


    @OnClick({R.id.tv_reload, R.id.tv_sel_year})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_reload:
                doGetMenus();
                break;
            case R.id.tv_sel_year:
                MyDialog dialog = new MyDialog(MaterialReportActivity.this, R.style.MyDialogStyle);
                if (yearList == null) {
                    return;
                }
                int size = yearList.size();
                final String[] arrays = new String[size];
                for (int i = 0; i < size; i++) {
                    arrays[i] = yearList.get(i);
                }
                dialog.setMyItems(arrays);
                dialog.setMyDialogItemClickListener(new MyDialog.MyDialogItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        tvSelYear.setText(arrays[position]);
                        doRefreshFragment(arrays[position]);
                    }
                });
                dialog.show();
                break;
        }
    }

    private void doRefreshFragment(String time) {
        int size = fragments.length;
        for (int i = 0; i < size; i++) {
            ((MaterialReportFragment) fragments[i]).doRefresh(time);
        }
    }

    /**
     * 获取年份
     */
    private void getYearData() {
        String url = Consts.BASE_URL + "/Inforeporting/yearSelect";
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                List<String> list = null;
                Gson gson = new Gson();
                try {
                    MyStudyArchivesMenuBean bean = gson.fromJson(response, MyStudyArchivesMenuBean.class);
                    int code = bean.getCode();
                    if (code != 0) {
                        ToastUtils.getInstance().showToast(MaterialReportActivity.this, "获取数据失败~");
                        return;
                    }
                    list = bean.getData();
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    ToastUtils.getInstance().showToast(MaterialReportActivity.this, "数据异常！~");
                }
                yearList.clear();
                if (list != null && list.size() > 0) {
                    yearList.addAll(list);
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(MaterialReportActivity.this, "网络错误~");
            }
        });
    }

    /**
     * 获取tab菜单
     */
    private void doGetMenus() {
        progressBar.setVisibility(View.VISIBLE);
        String url = Consts.BASE_URL + "/Inforeporting/infoReportingTypeList";
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                ULog.e("ck", "菜单：" + response);
                Gson gson = new Gson();
                try {
                    MaterialMenuBean menuBean = gson.fromJson(response, MaterialMenuBean.class);
                    String code = menuBean.getCode();
                    String msg = menuBean.getMsg();
                    if (!TextUtils.equals(code, "0")) {
                        progressBar.setVisibility(View.GONE);
                        llListPanel.setVisibility(View.GONE);
                        llNetErrorPanel.setVisibility(View.VISIBLE);
                        return;
                    }
                    List<MaterialMenuBean.DataBeanX.DataBean> list = menuBean.getData().getData();
                    fragments = new Fragment[list.size()];
                    titles = new String[list.size()];
                    for (int i = 0; i < list.size(); i++) {
                        fragments[i] = MaterialReportFragment.newInstance(list.get(i).getFid());
                        titles[i] = list.get(i).getName();
                    }
                    adapter = new CustomFragmentPagerAdapter(getSupportFragmentManager(), titles, fragments);
                    viewPager.setAdapter(adapter);
                    viewPager.setOffscreenPageLimit(list.size());
                    progressBar.setVisibility(View.GONE);
                    llNetErrorPanel.setVisibility(View.GONE);
                    llListPanel.setVisibility(View.VISIBLE);
                    int size = list.size();
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
                progressBar.setVisibility(View.GONE);
                llListPanel.setVisibility(View.GONE);
                llNetErrorPanel.setVisibility(View.VISIBLE);
            }
        });
    }

}
