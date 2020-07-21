package com.longhoo.net.study.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.longhoo.net.R;
import com.longhoo.net.headline.ui.PartyMemberSQActivity;
import com.longhoo.net.headline.ui.PartyflzActivity;
import com.longhoo.net.manageservice.bean.PartyTabBean;
import com.longhoo.net.mine.ui.NoteListActivity;
import com.longhoo.net.partyaffairs.ui.VoteListActivity;
import com.longhoo.net.study.adapter.StudyCommunicationAdapter;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.widget.base.BaseLazyFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author CK 首页——学习交流
 */
public class StudyCommunicationFragment extends BaseLazyFragment implements GridView.OnItemClickListener {
    @BindView(R.id.grid_view)
    GridView gridView;
    private StudyCommunicationAdapter adapter;
    private List<PartyTabBean> dataList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentId() {
        return R.layout.fragment_study_communication;
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
        Intent intent1 = new Intent(getActivity(), StudyActivity.class);
        Intent intent2 = new Intent(getActivity(), OnlineExamActivity.class);
        Intent intent3 = new Intent(getActivity(), NoteListActivity.class);
        Intent intent4 = new Intent(getActivity(), PartyClassDisplayActivity.class);
        Intent intent5 = new Intent(getActivity(), VideoConferenceActivity.class);
        Intent intent6 = new Intent(getActivity(), PartyMemberSQActivity.class);
        Intent intent7 = new Intent(getActivity(), StudyManagerActivity.class);
        Intent intent8 = new Intent(getActivity(), VoteListActivity.class);
        //Intent intent9 = new Intent(getActivity(), CurrentAffairsActivity.class);
        Intent intent9 = new Intent(getActivity(), PartyflzActivity.class);
        intent9.putExtra("type", "4");
       // PartyflzActivity.goTo(mActivity, "4");


      //  PartyTabBean bean1 = new PartyTabBean(intent1, R.drawable.study_header_1, "在线学习");
        PartyTabBean bean2 = new PartyTabBean(intent2, R.drawable.study_header_2, "在线答题");
        PartyTabBean bean3 = new PartyTabBean(intent3, R.drawable.study_header_3, "学习笔记");
        PartyTabBean bean4 = new PartyTabBean(intent4, R.drawable.study_header_4, "党建课程");
        //PartyTabBean bean5 = new PartyTabBean(intent5, R.drawable.study_header_5, "视频会议");
        PartyTabBean bean6 = new PartyTabBean(intent6, R.drawable.study_header_6, "党员社区");
     //   PartyTabBean bean7 = new PartyTabBean(intent7, R.drawable.study_header_7, "学习管理");
        PartyTabBean bean8 = new PartyTabBean(intent8, R.drawable.onlinevote, "在线投票");
        PartyTabBean bean9 = new PartyTabBean(intent9, R.drawable.timenews, "党风廉政");

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
      //  dataList.add(bean1);
        dataList.add(bean2);
        dataList.add(bean3);
        dataList.add(bean4);
        //dataList.add(bean5);
        dataList.add(bean6);
        //dataList.add(bean7);
        dataList.add(bean8);
        dataList.add(bean9);
        adapter = new StudyCommunicationAdapter(this.getActivity(), dataList);
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

            } else {
                startActivity(intent);
            }
        } else {
            ToastUtils.getInstance().showToast(getActivity(), "很抱歉，此模块不对外开放！");
        }
    }
}
