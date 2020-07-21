package com.longhoo.net.partyaffairs.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.longhoo.net.R;
import com.longhoo.net.partyaffairs.bean.CategrovyVoteNode;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.MessageEvent;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.utils.httprequest.OkHttpCallback;
import com.longhoo.net.utils.httprequest.OkHttpUtil;
import com.longhoo.net.widget.MyDialog;
import com.longhoo.net.widget.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReleaseVoteActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_train_title)
    EditText etTrainTitle;
    @BindView(R.id.tv_sel_type)
    TextView tvSelType;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.lv_add_vote)
    LinearLayout lvAddVote;
    @BindView(R.id.tv_votename)
    TextView tvVotename;
    @BindView(R.id.et_maxnum)
    EditText etMaxnum;
    private MyDialog dialog;
    List<VoteNode> mVoteNodeList = new LinkedList<>();


    @Override
    protected int getContentId() {
        return R.layout.activity_release_vote;
    }

    private List<CategrovyVoteNode.DataBean> zuZhiTypeList = new ArrayList<>();


    void AddVoteItem() {
        //获取当前时间戳
        long timeStamp = System.currentTimeMillis();
        final VoteNode mVoteNode = new VoteNode();
        mVoteNode.setVotename("");
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_voteview, null);
        mVoteNode.setTime(timeStamp);
        mVoteNode.setmView(view);
        mVoteNodeList.add(mVoteNode);
        view.findViewById(R.id.img_lable_jian).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemoveVoteItem(mVoteNode);
            }
        });
        lvAddVote.addView(view);
    }


    void RemoveVoteItem(VoteNode mVoteNode) {
        if (mVoteNodeList.size() <= 1) {
            ToastUtils.getInstance().showToast(ReleaseVoteActivity.this, "您至少需要填写一项~");
            return;
        }
        for (int i = 0; i < mVoteNodeList.size(); i++) {

            if (mVoteNode.getTime() == mVoteNodeList.get(i).getTime()) {
                lvAddVote.removeView(mVoteNodeList.get(i).getmView());
                mVoteNodeList.remove(i);
            }


        }


    }

    String getItemVoteData() {
        String strItemVoteData = "";
        for (int i = 0; i < mVoteNodeList.size(); i++) {
            if (TextUtils.isEmpty(((EditText) mVoteNodeList.get(i).getmView().findViewById(R.id.edt_voteinfo)).getText().toString().trim())) {
                return "";
            }
            if (TextUtils.isEmpty(strItemVoteData)) {
                strItemVoteData = ((EditText) mVoteNodeList.get(i).getmView().findViewById(R.id.edt_voteinfo)).getText().toString().trim();
            } else {
                strItemVoteData = strItemVoteData + "," + ((EditText) mVoteNodeList.get(i).getmView().findViewById(R.id.edt_voteinfo)).getText().toString().trim();
            }
        }
        return strItemVoteData;
    }


    @Override
    protected void initViews() {


        tvTitle.setText("发起投票");
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.left_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        AddVoteItem();
        tvVotename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddVoteItem();
            }
        });
        doGetMenus();

    }

    class VoteNode {
        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public long time;

        public String getVotename() {
            return votename;
        }

        public void setVotename(String votename) {
            this.votename = votename;
        }


        public String votename;


        public View getmView() {
            return mView;
        }

        public void setmView(View mView) {
            this.mView = mView;
        }

        public View mView;


    }


    @Override
    protected void initToolbar() {
        tvTitle.setText("发起投票");
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.left_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private String[] zuZhiTypeArrays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    private int cid;

    /**
     * 获取tab菜单
     */
    private void doGetMenus() {
        String url = Consts.BASE_URL + "/Policy/getCates";
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                if (isFinishing()) {
                    return;
                }
                ULog.e("cc", "：" + response);
                Gson gson = new Gson();
                try {
                    CategrovyVoteNode taiZhangMenuBean = gson.fromJson(response, CategrovyVoteNode.class);
                    String code = taiZhangMenuBean.getCode();
                    String msg = taiZhangMenuBean.getMsg();
                    if (!TextUtils.equals(code, "0")) {
                        ToastUtils.getInstance().showToast(ReleaseVoteActivity.this, msg);
                        return;
                    }
                    zuZhiTypeList.clear();
                    zuZhiTypeList.addAll(taiZhangMenuBean.getData());
                    for (int i = 0; i < zuZhiTypeList.size(); i++) {
                        if (zuZhiTypeList.get(i).getId() == 0) {
                            zuZhiTypeList.remove(i);
                        }
                    }
                    if (zuZhiTypeList != null && zuZhiTypeList.size() > 0) {
                        int size = zuZhiTypeList.size();
                        zuZhiTypeArrays = new String[size];
                        for (int i = 0; i < size; i++) {
                            zuZhiTypeArrays[i] = zuZhiTypeList.get(i).getName();
                        }
                        tvSelType.setText(zuZhiTypeList.get(0).getName());
                        cid = zuZhiTypeList.get(0).getId();
                    }

                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    ToastUtils.getInstance().showToast(ReleaseVoteActivity.this, "服务器异常~");
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(ReleaseVoteActivity.this, "网络异常~");
            }
        });
    }

    private void doUpData() {
        String url = Consts.BASE_URL + "/Policy/addVote";

//        catid  分类id
//        title  姓名
//        content   内容
        String strTitle = "";
        String strContent = "";
        strTitle = etTrainTitle.getText().toString().trim();
        strContent = etContent.getText().toString().trim();
        if (TextUtils.isEmpty(strTitle)) {
            ToastUtils.getInstance().showToast(this, "请输入投票主题！");
            return;
        }
//        if (TextUtils.isEmpty(cid + "")) {
//            ToastUtils.getInstance().showToast(this, "请选择分类！");
//            return;
//        }


        if (TextUtils.isEmpty(etMaxnum.getText().toString().trim())) {
            ToastUtils.getInstance().showToast(this, "请输入每人投票数！");
            return;
        }
        int maxNum = Integer.parseInt(etMaxnum.getText().toString().trim());
        if (maxNum == 0) {
            ToastUtils.getInstance().showToast(this, "每人投票数不能为0！");
            return;
        }
        if (TextUtils.isEmpty(getItemVoteData())) {
            ToastUtils.getInstance().showToast(this, "您的选项没有填写完毕！");
            return;
        }
        if (TextUtils.isEmpty(strContent + "")) {
            ToastUtils.getInstance().showToast(this, "请填写内容！");
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        // params.put("catid", cid + "");
        params.put("catname", strTitle + "");
        params.put("zan_num", etMaxnum.getText().toString().trim() + "");
        params.put("unamestr", getItemVoteData() + "");
        params.put("content", strContent + "");
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                if (isFinishing()) {
                    return;
                }
                ULog.e("cc", "：" + response);
                Gson gson = new Gson();
                try {
                    CategrovyVoteNode taiZhangMenuBean = gson.fromJson(response, CategrovyVoteNode.class);
                    String code = taiZhangMenuBean.getCode();
                    String msg = taiZhangMenuBean.getMsg();
                    if (!TextUtils.equals(code, "0")) {
                        ToastUtils.getInstance().showToast(ReleaseVoteActivity.this, msg);
                        return;
                    }
                    ToastUtils.getInstance().showToast(ReleaseVoteActivity.this, taiZhangMenuBean.getMsg());
                    zuZhiTypeList.clear();
                    zuZhiTypeList.addAll(taiZhangMenuBean.getData());
                    for (int i = 0; i < zuZhiTypeList.size(); i++) {
                        if (zuZhiTypeList.get(i).getId() == 0) {
                            zuZhiTypeList.remove(i);
                        }
                    }
                    if (zuZhiTypeList != null && zuZhiTypeList.size() > 0) {
                        int size = zuZhiTypeList.size();
                        zuZhiTypeArrays = new String[size];
                        for (int i = 0; i < size; i++) {
                            zuZhiTypeArrays[i] = zuZhiTypeList.get(i).getName();
                        }
                        tvSelType.setText(zuZhiTypeList.get(0).getName());
                        cid = zuZhiTypeList.get(0).getId();
                    }
                    MessageEvent event = new MessageEvent();
                    event.msgType = MessageEvent.MSG_REFRESH_VOTE_LIST;
                    EventBus.getDefault().post(event);
                    finish();

                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    ToastUtils.getInstance().showToast(ReleaseVoteActivity.this, "服务器异常~");
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(ReleaseVoteActivity.this, "网络异常~");
            }
        });
    }

    @OnClick({R.id.tv_sel_type, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_sel_type:
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                dialog = new MyDialog(ReleaseVoteActivity.this, R.style.MyDialogStyle);
                if (zuZhiTypeArrays == null) {
                    zuZhiTypeArrays = new String[]{"", ""};
                }
                dialog.setMyItems(zuZhiTypeArrays);
                dialog.setMyDialogItemClickListener(new MyDialog.MyDialogItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (position >= 0 && position <= zuZhiTypeList.size() - 1) {
                            cid = zuZhiTypeList.get(position).getId();
                            tvSelType.setText(zuZhiTypeList.get(position).getName());
                        }

                    }
                });
                dialog.show();
                break;
            case R.id.tv_submit:
                doUpData();
                break;
        }
    }
}
