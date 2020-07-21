package com.longhoo.net.study.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.manageservice.adapter.PartyaffairsHomeAdapter;
import com.longhoo.net.manageservice.bean.PartyTabBean;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.widget.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author CK 首页——学习交流——在线答题
 */
public class OnlineExamActivity extends BaseActivity implements GridView.OnItemClickListener {
    @BindView(R.id.grid_view)
    GridView gridView;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_top_noti)
    TextView tvTopNoti;
    @BindView(R.id.top_panel)
    LinearLayout topPanel;
    private PartyaffairsHomeAdapter adapter;
    private List<PartyTabBean> dataList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_on_line_exam;
    }
    

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void initViews() {
        Intent intent1 = new Intent(this, ExaminationCenterActivity.class);
        intent1.putExtra("type","0");
        Intent intent2 = new Intent(this, ExaminationCenterActivity.class);
        intent2.putExtra("type","1");
        Intent intent3 = new Intent(this, ExamOrderActivity.class);
        PartyTabBean bean1 = new PartyTabBean(intent1, R.drawable.dangwu_tab_1, "学习题库");
        PartyTabBean bean2 = new PartyTabBean(intent2, R.drawable.dangwu_tab_7, "在线考试");
        PartyTabBean bean3 = new PartyTabBean(intent3, R.drawable.dangwu_tab_6, "成绩排名");

        //积极分子、群众只能看党纪监督和考试中心
//        if (EnterCheckUtil.getInstance().IS_COMMON_PEOPLE() || EnterCheckUtil.getInstance().IS_ACTIVIST()) {
//            bean1.setCanClick(false);
//            bean2.setCanClick(false);
//            bean3.setCanClick(false);
//            bean4.setCanClick(false);
//            bean5.setCanClick(false);
//            bean6.setCanClick(true);
//            if (EnterCheckUtil.getInstance().IS_ACTIVIST()) {
//                bean7.setCanClick(true);
//            } else {
//                bean7.setCanClick(false);
//            }
//            bean8.setCanClick(false);
//            bean9.setCanClick(false);
//        }
        dataList.add(bean1);
        dataList.add(bean2);
        dataList.add(bean3);
        adapter = new PartyaffairsHomeAdapter(this, dataList);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);

    }

    @Override
    protected void initToolbar() {
        tvTitle.setText("在线答题");
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (i < 0 || i > dataList.size() - 1) {
            return;
        }
        boolean isLogin = EnterCheckUtil.getInstance().isLogin(true);
        if (!isLogin) {
            return;
        }
        Intent intent = dataList.get(i).getIntent();
        boolean canClick = dataList.get(i).isCanClick();
        if (canClick) {
            if (intent == null) {

            } else {
                startActivity(intent);
            }
        } else {
            ToastUtils.getInstance().showToast(this, "很抱歉，此模块不对外开放！");
        }
    }
}
