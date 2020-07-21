package com.longhoo.net.widget.treelist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.multilevel.treelist.Node;
import com.multilevel.treelist.TreeListViewAdapter;
import com.longhoo.net.R;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.MessageEvent;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.utils.httprequest.OkHttpCallback;
import com.longhoo.net.utils.httprequest.OkHttpUtil;
import com.longhoo.net.widget.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class MultiLevelActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.lv_tree)
    ListView lvTree;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    private List<Node> dataList = new ArrayList<>();
    private TreeListViewAdapter mAdapter;
    private String hasSelectedIds = "";

    public static void goTo(Context context, String hasSelectedIds) {
        Intent intent = new Intent(context, MultiLevelActivity.class);
        intent.putExtra("hasSelectedIds", hasSelectedIds);
        context.startActivity(intent);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_multi_level;
    }

    @Override
    protected void initViews() {
        if (getIntent() != null) {
            hasSelectedIds = getIntent().getStringExtra("hasSelectedIds");
        }
        mAdapter = new SimpleTreeAdapter(lvTree, this,
                dataList, 1, R.drawable.tree_ex, R.drawable.tree_ec);
        lvTree.setAdapter(mAdapter);
        getData();
    }

    @Override
    protected void initToolbar() {
        tvTitle.setText("选择参会人员");
        toolbar.setTitle("");
        tvRight.setText("确定");
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showData();
            }
        });
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.left_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void getData() {
        String url = Consts.BASE_URL + "/application/getOrgLifePeople_new";
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                List<MultiLevelBean.DataBeanX.DataBean> tempList = null;
                Gson gson = new Gson();
                try {
                    MultiLevelBean multiLevelBean = gson.fromJson(response, MultiLevelBean.class);
                    String code = multiLevelBean.getCode();
                    if (!TextUtils.equals(code, "0")) {
                        ToastUtils.getInstance().showToast(MultiLevelActivity.this, "获取数据失败~");
                        return;
                    }
                    tempList = multiLevelBean.getData().getData();
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
                dataList.clear();
                if (tempList != null && tempList.size() > 0) {
                    for (MultiLevelBean.DataBeanX.DataBean bean : tempList) {
                        dataList.add(new Node(bean.getOid() + "", bean.getPid() + "", TextUtils.isEmpty(bean.getName()) ? bean.getUname() :
                                bean.getName(), bean.getUid(), bean.getOid()));
                    }

                }
                ULog.e("ck--", dataList.size() + "");
                mAdapter.addData(0, dataList);
                progressBar.setVisibility(View.GONE);
                lvTree.setVisibility(View.VISIBLE);

                //选中未选中
                if (!TextUtils.isEmpty(hasSelectedIds)) {
                    ULog.e("ck", hasSelectedIds);
                    List<String> selectedList = Arrays.asList(hasSelectedIds.split(","));
                    for (int i = 0; i < dataList.size(); i++) {
                        if ((Integer) dataList.get(i).getUid() == 0) {
                            dataList.get(i).setChecked(true);
                        }
                        for (int j = 0; j < selectedList.size(); j++) {
                            if ((Integer) dataList.get(i).getUid() == Integer.parseInt(selectedList.get(j))) {
                                dataList.get(i).setChecked(true);
                                for (int k = 0; k < dataList.size(); k++) {
                                    if (dataList.get(k).getpId().toString().equals(dataList.get(k).getOid())) {
                                        dataList.get(k).setChecked(true);
                                    }
                                }

                            }
                        }
                    }
                }

            }

            @Override
            public void onFailure(String errorMsg) {
                progressBar.setVisibility(View.GONE);
                lvTree.setVisibility(View.VISIBLE);
                ToastUtils.getInstance().showToast(MultiLevelActivity.this, "网络错误~");
            }
        });
    }

    /**
     * 显示选中数据
     */
    public void showData() {
        StringBuilder zuZhiString = new StringBuilder();
        StringBuilder userString = new StringBuilder();
        StringBuilder userName = new StringBuilder();
        StringBuilder ids = new StringBuilder();
        //获取排序过的nodes
        //如果不需要刻意直接用 mDatas既可
        final List<Node> allNodes = mAdapter.getAllNodes();
        for (int i = 0; i < allNodes.size(); i++) {
            if (allNodes.get(i).isChecked()) {
                ULog.e("ck--", allNodes.get(i).getUid() + ":uid");
                if ((Integer) allNodes.get(i).getUid() == 0) {
                    zuZhiString.append(allNodes.get(i).getOid() + ",");
                } else {
                    userString.append(allNodes.get(i).getUid() + ",");
                    userName.append(allNodes.get(i).getName() + ",");
                }
                ids.append(allNodes.get(i).getId() + ",");
            }
        }
        String strNodesName = zuZhiString.toString() + "&ck&" + userString.toString();
        if (!TextUtils.isEmpty(strNodesName))
            ULog.e("ck--", strNodesName.substring(0, strNodesName.length() - 1));
        String name = userName.toString();
        if (name.contains(",")) {
            name = name.substring(0, name.lastIndexOf(","));
        }
        MessageEvent event = new MessageEvent();
        event.msgType = MessageEvent.MSG_PICK_PEOPLE;
        event.message = strNodesName;
        event.message2 = name;
        event.message3 = ids.toString();
        EventBus.getDefault().post(event);
        onBackPressed();
    }

//    @Override
//    public void onBackPressed() {
//        //super.onBackPressed();
//        moveTaskToBack(true);
//        overridePendingTransition(R.anim.anim_none, R.anim.left_to_right);
//    }
}
