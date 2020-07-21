package com.longhoo.net.mine.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.longhoo.net.R;
import com.longhoo.net.mine.adapter.ReadNumAdapter;
import com.longhoo.net.mine.bean.ReadNumBean;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.utils.httprequest.HttpRequestPresenter;
import com.longhoo.net.utils.httprequest.HttpRequestView;
import com.longhoo.net.widget.HeaderAndFooterWrapper;
import com.longhoo.net.widget.MyDialog;
import com.longhoo.net.widget.base.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyStudyReportActivity extends BaseActivity implements OnRefreshLoadmoreListener, HttpRequestView, HeaderAndFooterWrapper.OnItemClickListener {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_day)
    TextView tvDay;
    @BindView(R.id.tv_month)
    TextView tvMonth;
    @BindView(R.id.tv_year)
    TextView tvYear;
    @BindView(R.id.tv_data)
    TextView tvData;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    private HttpRequestPresenter requestPresenter;
    private List<ReadNumBean.DataBean> dataList = new ArrayList<>();
    private HeaderAndFooterWrapper adapterWarpper;
    ReadNumAdapter adapter;

    @Override
    protected int getContentId() {
        return R.layout.activity_my_study_report;
    }

    @Override
    protected void initViews() {
        if (getIntent() == null) {
            ToastUtils.getInstance().showToast(this, "数据异常~");
            return;
        }
        strType = getIntent().getStringExtra("type");
        strTitle = getIntent().getStringExtra("title");
        if (TextUtils.isEmpty(strType)) {
            tvNum.setVisibility(View.INVISIBLE);
            tvTitle.setText(strTitle);
        } else {
            tvTitle.setText(strTitle);
        }
        if (strTitle.contains("心得")) {
            tvTime.setText("评论数");
        }
        if (strTitle.contains("答题")) {
            tvNum.setText("答题数");
        }
        requestPresenter = new HttpRequestPresenter(this, this);
        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(this));
        smartRefreshLayout.setRefreshFooter(new ClassicsFooter(this));
        smartRefreshLayout.setOnRefreshLoadmoreListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new ReadNumAdapter(this, dataList, strType);
        adapterWarpper = new HeaderAndFooterWrapper(adapter);
        recyclerView.setAdapter(adapterWarpper);
        adapterWarpper.setOnItemClickListener(this);
        smartRefreshLayout.autoRefresh(500);
        tvMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMonth(tvMonth);
            }
        });
        tvYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getYear(tvYear);
            }
        });
        tvDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDay(tvDay);
            }
        });
        tvData.setText("   年 份   ");
    }

    @Override
    protected void initToolbar() {

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.left_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }


    public static List<String> getDayByMonth(int yearParam, int monthParam) {
        List list = new ArrayList();
        int month = monthParam;
        int year = yearParam;
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, yearParam);
        c.set(Calendar.MONTH, monthParam - 1);//注意一定要写5，不要写6！Calendar.MONTH是从0到11的！
        int day = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i = 1; i <= day; i++) {
            String aDate = null;
            if (month < 10 && i < 10) {
                aDate = String.valueOf(year) + "-0" + month + "-0" + i + "日";
            }
            if (month < 10 && i >= 10) {
                aDate = String.valueOf(year) + "-0" + month + "-" + i + "日";
            }
            if (month >= 10 && i < 10) {
                aDate = String.valueOf(year) + "-" + month + "-0" + i + "日";
            }
            if (month >= 10 && i >= 10) {
                aDate = String.valueOf(year) + "-" + month + "-" + i + "日";
            }
            String strDate[] = aDate.split("-");
            list.add(strDate[strDate.length - 1]);
        }
        return list;
    }


    void getDay(final TextView tvSelDay) {
        MyDialog dialog = new MyDialog(MyStudyReportActivity.this, R.style.MyDialogStyle);
        List<String> stringList = getDayByMonth(chooseYear, choosemonth);
        String[] arrays = new String[stringList.size()];
        stringList.toArray(arrays);
        dialog.setMyItems(arrays);
        dialog.setMyDialogItemClickListener(new MyDialog.MyDialogItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                chooseDay = position + 1;
                tvSelDay.setText(chooseDay + "日");
                smartRefreshLayout.autoRefresh(500);
                tvData.setText("   日 期   ");
            }
        });
        dialog.show();

    }

    int choosemonth = 0;
    int chooseYear = 0;
    int chooseDay = 0;
    String strType = "0";
    String strTitle = "0";

    void getMonth(final TextView tvSelMontn) {

        MyDialog dialog = new MyDialog(MyStudyReportActivity.this, R.style.MyDialogStyle);
        final int[] arraysInt = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        final String[] arrays = new String[]{"1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"};
        dialog.setMyItems(arrays);
        dialog.setMyDialogItemClickListener(new MyDialog.MyDialogItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                choosemonth = arraysInt[position];
                tvSelMontn.setText(arrays[position]);
                tvDay.setText("日");
                tvData.setText("   日 期   ");

                chooseDay = 0;
                tvDay.setVisibility(View.VISIBLE);
                smartRefreshLayout.autoRefresh(500);
            }
        });
        dialog.show();

    }

    int[] arraysYearInt = null;
    String[] arraysYear = null;

    void getYear(final TextView tvSelYear) {

        MyDialog dialog = new MyDialog(MyStudyReportActivity.this, R.style.MyDialogStyle);
//        arraysYearInt = new int[]{2019, 2020, 2021, 2022, 2023, 2024};
//        arraysYear = new String[]{"2019年", "2020年", "2021年", "2022年", "2023年", "2024年"};
        dialog.setMyItems(arraysYear);
        dialog.setMyDialogItemClickListener(new MyDialog.MyDialogItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                chooseYear = arraysYearInt[position];
                tvSelYear.setText(arraysYear[position]);
                tvMonth.setText("月");
                tvDay.setText("日");
                choosemonth = 0;
                chooseDay = 0;
                tvData.setText("   月 份   ");
                tvDay.setVisibility(View.GONE);
                tvMonth.setVisibility(View.VISIBLE);
                smartRefreshLayout.autoRefresh(500);

            }
        });
        dialog.show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {

    }


    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        /**
         * 返回报告统计列表   传类型type 则返回各分类下报告情况  不传则返回总时长统计
         * @desc /app/report/report_lists
         *
         * 参数
         * @poram int    type 报告类型  非必填  1阅读  2视频  3答题  不传则返回所有类型时长
         * @poram int year 年 非必填  默认为0
         * @poram int month 月  非必填   默认为0
         * @poram int days  日  非必填   默认为0
         *
         * 返回值
         * @return string date 日期
         * @return string num 数量/次数
         * @return string duration 学习时长
         */
        smartRefreshLayout.setEnableLoadmore(false);
        Map<String, String> params = new HashMap<>();
        params.put("uid", EnterCheckUtil.getInstance().getUid_Token()[0]);
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("type", strType);
        params.put("year", chooseYear + "");
        params.put("month", choosemonth + "");
        params.put("days", chooseDay + "");
        requestPresenter.doHttpData(Consts.BASE_URL + "/report/report_lists", Consts.REQUEST_METHOD_POST, Consts.REQUEST_REFRESH, params);
    }

    @Override
    public void onNetworkError() {
        finishRefreshLoad();
        ToastUtils.getInstance().showToast(this, "网络连接异常，请检查网络设置~");
    }

    @Override
    public void onRefreshSuccess(String response) {
        smartRefreshLayout.finishRefresh(0);
        try {
            JSONObject obj = null;
            obj = new JSONObject(response);
            ULog.e("学习列表：" + response);
            if (obj.has("code")) {
                if (obj.getString("code").equals("0")) {
                    Gson gson = new Gson();
                    ReadNumBean searchResultBean = gson.fromJson(response, ReadNumBean.class);

                    if (searchResultBean.getData().size() > 0) {
                        if (chooseYear == 0) {
                            String strYear = "";
                            for (int i = 0; i <= searchResultBean.getData().size() - 1; i++) {
                                if (strYear.equals("")) {
                                    strYear = searchResultBean.getData().get(i).getDate() + "年";
                                } else {
                                    strYear = strYear + "," + searchResultBean.getData().get(i).getDate() + "年";
                                }

                            }
                            arraysYear = strYear.split(",");

                            List<Integer> listYearInt = new ArrayList();
                            for (int i = 0; i <= searchResultBean.getData().size() - 1; i++) {

                                listYearInt.add(Integer.parseInt(searchResultBean.getData().get(i).getDate()));
                            }
                            arraysYearInt=new int[searchResultBean.getData().size()];
                            for (int i = 0; i < listYearInt.size(); i++) {
                                arraysYearInt[i] = listYearInt.get(i);
                            }

//                            arraysYearInt = new int[]{2019, 2020, 2021, 2022, 2023, 2024};
//
//
//                            arraysYear = new String[]{"2019年", "2020年", "2021年", "2022年", "2023年", "2024年"};
                        }
                        dataList.clear();
                        dataList.addAll(searchResultBean.getData());
                    } else {
                        Toast.makeText(MyStudyReportActivity.this, "暂无数据", Toast.LENGTH_SHORT).show();
                    }

                    adapterWarpper.notifyDataSetChanged();
                } else {
                    Toast.makeText(MyStudyReportActivity.this, "数据异常", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MyStudyReportActivity.this, "数据异常", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRefreshError() {
        finishRefreshLoad();
        ToastUtils.getInstance().showToast(this, "网络连接异常，请检查网络设置~");
    }

    @Override
    public void onLoadMoreSuccess(String response) {

    }

    @Override
    public void onLoadMoreError() {

    }

    @Override
    public void onItemClick(View view, int position) {

    }

    private void finishRefreshLoad() {
        if (smartRefreshLayout != null) {
            if (smartRefreshLayout.isRefreshing()) {
                smartRefreshLayout.finishRefresh(0);
            }
            if (smartRefreshLayout.isLoading()) {
                smartRefreshLayout.finishLoadmore(0);
            }
        }
    }
}
