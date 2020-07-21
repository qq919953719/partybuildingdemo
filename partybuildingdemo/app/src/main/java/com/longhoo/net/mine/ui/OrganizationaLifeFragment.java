package com.longhoo.net.mine.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.longhoo.net.R;
import com.longhoo.net.manageservice.adapter.PartyaffairsHomeAdapter;
import com.longhoo.net.manageservice.bean.PartyTabBean;
import com.longhoo.net.partyaffairs.ui.TaiZhangActivity;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.widget.base.BaseLazyFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * @author CC 我的支部——组织生活管理
 */
public class OrganizationaLifeFragment extends BaseLazyFragment implements GridView.OnItemClickListener {

    @BindView(R.id.grid_view)
    GridView gridView;
    Unbinder unbinder;
    private PartyaffairsHomeAdapter adapter;
    private List<PartyTabBean> dataList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentId() {
        return R.layout.fragment_organizational_life;
    }

    @Override
    protected void onLazyLoad() {
    }

    @Override
    protected void initViews(View view) {
        Intent intent1 = new Intent(getActivity(),ReleaseOrgLifeActivity.class);
        Intent intent2 = new Intent(getActivity(), TaiZhangActivity.class);
        intent2.putExtra("isShowRight",false);
       // PartyTabBean bean1 = new PartyTabBean(intent1, R.drawable.me_branch_1, "发起组织生活",!EnterCheckUtil.getInstance().IS_MEMBER());
        PartyTabBean bean2 = new PartyTabBean(intent2, R.drawable.partyaffairs_header_2, "查看历史组织生活记录");
      //  dataList.add(bean1);
        dataList.add(bean2);
        adapter = new PartyaffairsHomeAdapter(this.getActivity(), dataList);
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
            ToastUtils.getInstance().showToast(getActivity(), "您当前没有此权限！");
        }
    }
}
