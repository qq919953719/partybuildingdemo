package com.longhoo.net.supervision.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.longhoo.net.R;
import com.longhoo.net.manageservice.bean.PartyTabBean;
import com.longhoo.net.mine.ui.SortScoreActivity;
import com.longhoo.net.supervision.adapter.SupervisionAndCheckAdapter;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.widget.base.BaseLazyFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author CK 首页——监督考核
 */
public class SupervisionAndCheckFragment extends BaseLazyFragment implements GridView.OnItemClickListener {
    @BindView(R.id.grid_view)
    GridView gridView;
    private SupervisionAndCheckAdapter adapter;
    private List<PartyTabBean> dataList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentId() {
        return R.layout.fragment_supervision_and_check;
    }

    @Override
    protected void onLazyLoad() {
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void initViews(View view) {
        Intent intent1 = null;
//        if(getActivity() instanceof MainActivity){
//            if(((MainActivity) getActivity()).opentag==0){
//                intent1 = new Intent(getActivity(), ScoreRankActivity.class);
//            }else{
//                intent1 = null;
//            }
//        }
        intent1 = new Intent(getActivity(), SortScoreActivity.class);

        Intent intent2 = new Intent(getActivity(),ActStatisticsActivity.class);
        Intent intent3 = new Intent(getActivity(),SecretReportActivity.class);
        PartyTabBean bean1 = new PartyTabBean(intent1, R.drawable.supervision_header_1, "积分排名");
        PartyTabBean bean2 = new PartyTabBean(intent2, R.drawable.supervision_header_2, "活动统计");
        //PartyTabBean bean3 = new PartyTabBean(intent3, R.drawable.supervision_header_3, "书记述职");

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
       // dataList.add(bean3);
        adapter = new SupervisionAndCheckAdapter(this.getActivity(), dataList);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);
    }

    @Override
    protected void initToolbar() {
    }

    @Override
    protected long setLoadInterval() {
        return 0;
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
                ToastUtils.getInstance().showToast(getActivity(), "功能暂未开放！");
            } else {
                startActivity(intent);
            }
        } else {
            ToastUtils.getInstance().showToast(getActivity(), "很抱歉，此模块不对外开放！");
        }
    }
}
