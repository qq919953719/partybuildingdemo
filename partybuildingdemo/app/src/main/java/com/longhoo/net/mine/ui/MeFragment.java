package com.longhoo.net.mine.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.luck.picture.lib.tools.PictureFileUtils;
import com.longhoo.net.MyApplication;
import com.longhoo.net.R;
import com.longhoo.net.manageservice.bean.PartyTabBean;
import com.longhoo.net.manageservice.ui.OrgazationPersonalActivity;
import com.longhoo.net.mine.adapter.MinAdapter;
import com.longhoo.net.pay.ZhiFuUtil;
import com.longhoo.net.supervision.ui.ActStatisticsActivity;
import com.longhoo.net.utils.CacheUtil;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.MessageEvent;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.utils.httprequest.OkHttpCallback;
import com.longhoo.net.utils.httprequest.OkHttpUtil;
import com.longhoo.net.widget.base.BaseLazyFragment;
import com.longhoo.net.widget.base.WebViewActivity;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 主页——我的
 */
public class MeFragment extends BaseLazyFragment implements GridView.OnItemClickListener, ZhiFuUtil.ZhiFuResultListerner {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_set)
    ImageView ivSet;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_work_number)
    TextView tvWorkNumber;
    @BindView(R.id.tv_zongzhi)
    TextView tvZongzhi;
    @BindView(R.id.tv_zhibu)
    TextView tvZhibu;
    @BindView(R.id.tv_my_score)
    TextView tvMyScore;
    @BindView(R.id.lv_headview)
    LinearLayout lvHeadview;
    @BindView(R.id.grid_view)
    GridView gridView;
    @BindView(R.id.lv_min)
    LinearLayout lvMin;
    @BindView(R.id.tv_name1)
    TextView tvName1;
    @BindView(R.id.lv_set)
    LinearLayout lvSet;
    @BindView(R.id.tv_changepwd)
    TextView tvChangepwd;
    @BindView(R.id.lv_changpwd)
    LinearLayout lvChangpwd;
    @BindView(R.id.tv_userxieyi)
    TextView tvUserxieyi;
    @BindView(R.id.lv_userxieyi)
    LinearLayout lvUserxieyi;
    @BindView(R.id.tv_aboutours)
    TextView tvAboutours;
    @BindView(R.id.lv_aboutours)
    LinearLayout lvAboutours;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.tv_versioncount)
    TextView tvVersioncount;
    @BindView(R.id.lv_version)
    LinearLayout lvVersion;
    @BindView(R.id.tv_clear_cace)
    TextView tvClearCace;
    @BindView(R.id.tv_clear_cacecount)
    TextView tvClearCacecount;
    @BindView(R.id.lv_clear_cace)
    LinearLayout lvClearCace;
    @BindView(R.id.lv_listbottom)
    LinearLayout lvListbottom;
    @BindView(R.id.tv_cancle)
    TextView tvCancle;
    @BindView(R.id.lv_cancle)
    LinearLayout lvCancle;
    @BindView(R.id.rv_set)
    RelativeLayout rvSet;
    Unbinder unbinder;
    private MinAdapter adapter;
    private List<PartyTabBean> dataList = new ArrayList<>();
    public static final String MSG_LOGIN_OUT = "msg_login_out";//退出登录，至mainactivity刷新登录状态

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentId() {
        return R.layout.fragment_me;
    }

    void GetPTCConetnt() {

        MyApplication app = (MyApplication) getActivity().getApplicationContext();
        if (app.getmLoginBean() == null) {
            ToastUtils.getInstance().showToast(getActivity(), "登录过期，请重新登陆！");
            return;
        }
        tvName.setText("姓名：" + app.getmLoginBean().getData().getRealname());
        tvName1.setText("姓名：" + app.getmLoginBean().getData().getRealname());
        tvWorkNumber.setText("工号：" + app.getmLoginBean().getData().getNum());
        tvZongzhi.setText("总支：" + app.getmLoginBean().getData().getConame());
        tvZhibu.setText("支部：" + app.getmLoginBean().getData().getOname());

//        Map<String, String> map = new HashMap<>();
//        map.put("uid", String.valueOf(App.getmLoginBean().getData().getUid()));
//        map.put("token", String.valueOf(App.getmLoginBean().getData().getToken()));
//
//        OkHttpUtil.getInstance().doAsyncPost(Consts.BASE_URL + "/Index/log_userinfo", map, new OkHttpCallback() {
//            @Override
//            public void onSuccess(String response) {
//                ULog.e("学习积分：" + response);
//                try {
//                    JSONObject obj = new JSONObject(response);
//                    if (obj.has("code")) {
//                        if (obj.getString("code").equals("0")) {
//                            Gson gson = new Gson();
//                            final PersonalInfoBean mPTCConetntBean = gson.fromJson(response, PersonalInfoBean.class);
//                            tvName.setText("姓名："+mPTCConetntBean.getData().getRealname());
////                            tvScore.setText("学习积分：" + mPTCConetntBean.getData().getScore());
////                            //roleid 会员角色；1表示管理员；2表示普通党员；3表示组织内非党员
////                            tvStatues.setText(mPTCConetntBean.getData().getParty_duty());
//                            tvName1.setText("姓名："+mPTCConetntBean.getData().getRealname());
//                            tvWorkNumber.setText("工号："+mPTCConetntBean.getData().getPhone());
//                            tvZongzhi.setText("总支："+mPTCConetntBean.getData().getConame());
//                            tvZhibu.setText("支部："+mPTCConetntBean.getData().getOname());
//
//                        } else {
//                            Toast.makeText(getActivity(), "暂无数据~", Toast.LENGTH_SHORT).show();
//                        }
//                    } else {
//                        Toast.makeText(getActivity(), "暂无数据~", Toast.LENGTH_SHORT).show();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                } catch (JsonSyntaxException e) {
//                    e.printStackTrace();
//                }catch (NullPointerException e){
//                    e.printStackTrace();
//                }
//
//            }
//
//            @Override
//            public void onFailure(String errorMsg) {
//                ToastUtils.getInstance().showToast(getActivity(), "网络错误~");
//
//            }
//        });


    }

    @Override
    protected void onLazyLoad() {
        GetPTCConetnt();
//        if (EnterCheckUtil.getInstance().IS_COMMON_PEOPLE()) {
//            tvVersioncount.setText("当前版本" + Utils.getVersionName(getActivity()));
//            tvClearCacecount.setText(CacheUtil.getInstance().getAllCacheSize(getActivity()));
//            rvSet.setVisibility(View.VISIBLE);
//            lvMin.setVisibility(View.GONE);
//            ivSet.setVisibility(View.GONE);
//            return;
//        }
        final MyApplication mApp = (MyApplication) getActivity().getApplicationContext();
        if (mApp.getmLoginBean() != null) {
            tvName1.setText(mApp.getmLoginBean().getData().getRealname());
        }
        rvSet.setVisibility(View.GONE);
        lvMin.setVisibility(View.VISIBLE);
        ivSet.setVisibility(View.VISIBLE);
        lvHeadview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(getActivity(), OrgazationPersonalActivity.class);
                intent.putExtra("did", mApp.getmLoginBean() == null ? "" : mApp.getmLoginBean().getData().getUid());
                intent.putExtra("type", "1");
                getActivity().startActivity(intent);
            }
        });

        ivSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), SetActivity.class);
                getActivity().startActivity(intent);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        getMsgNum();
    }

    @Override
    protected void initViews(View view) {

        Intent intent1 = new Intent(getActivity(), SigninActivity.class);
        Intent intent2 = new Intent(getActivity(), PartyFeeActivity.class);
        //Intent intent3 = new Intent(getActivity(), MyEnrollActivity.class);
        //intent3.putExtra("tag", MyEnrollActivity.TAG_MY_ENCROLL);
        Intent intent4 = new Intent(getActivity(), MyExaminationActivity.class);
        //   Intent intent5 = new Intent(getActivity(), ScoreDetailActivity.class);
        Intent intent6 = new Intent(getActivity(), MyPartyCommunity.class);
        Intent intent7 = new Intent(getActivity(), MyStudyArchivesActivity.class);
        // Intent intent8 = new Intent(getActivity(), MyBranchActivity.class);
        Intent intent8 = new Intent(getActivity(), ActStatisticsActivity.class);

        Intent intent9 = new Intent(getActivity(), MyTaskActivity.class);
        Intent intent10 = new Intent(getActivity(), MyStudyReportIndexActivity.class);
        Intent intent11 = new Intent(getActivity(), SortScoreActivity.class);
        //dataList.add(new PartyTabBean(intent9, R.drawable.me_tab_9, "我的任务"));
        dataList.add(new PartyTabBean(intent11, R.drawable.scoresort, "积分排名"));
        dataList.add(new PartyTabBean(intent1, R.drawable.me_tab_1, "签到记录"));
        dataList.add(new PartyTabBean(intent2, R.drawable.me_tab_2, "我的党费"));
        //dataList.add(new PartyTabBean(intent3, R.drawable.me_tab_3, "我的报名"));
        dataList.add(new PartyTabBean(intent8, R.drawable.me_tab_8, "我的支部"));
        dataList.add(new PartyTabBean(intent4, R.drawable.me_tab_4, "我的考试"));
        // dataList.add(new PartyTabBean(intent5, R.drawable.me_tab_5, "我的积分"));

        // dataList.add(new PartyTabBean(intent7, R.drawable.me_tab_7, "我的学习档案"));

        dataList.add(new PartyTabBean(intent10, R.drawable.studyreport, "我的学习报告"));
        dataList.add(new PartyTabBean(intent6, R.drawable.me_tab_6, "我的发布"));
        adapter = new MinAdapter(this.getActivity(), dataList);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);
        getMsgNum();
    }

    protected void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("确认退出吗？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LoginOut();
                dialog.dismiss();

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    @OnClick({R.id.lv_changpwd, R.id.lv_userxieyi, R.id.lv_aboutours,
            R.id.lv_version, R.id.lv_clear_cace, R.id.lv_cancle})
    public void onClick(View view) {
        String url;
        Intent intent = null;
        switch (view.getId()) {
            case R.id.lv_changpwd:
                intent = new Intent(getActivity(), ModifyPwdActivity.class);
                startActivity(intent);
                break;
            case R.id.lv_userxieyi:
                url = Consts.BASE_URL + "/Other/public_regist";
                WebViewActivity.goToWebView(getActivity(), url, "用户协议", false);
                break;
            case R.id.lv_aboutours:
                url = Consts.BASE_URL + "/Other/public_about";
                WebViewActivity.goToWebView(getActivity(), url, "关于我们", false);
                break;
            case R.id.lv_version:
                break;
            case R.id.lv_clear_cace:
                //清理缓存
                PictureFileUtils.deleteCacheDirFile(getActivity());
                CacheUtil.getInstance().clearAllCache(getActivity());
                tvClearCacecount.setText("0M");
                break;
            case R.id.lv_cancle:
                MyApplication app = (MyApplication) getActivity().getApplicationContext();
                if (app.getmLoginBean() == null) {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    getActivity().startActivity(intent);
                    return;
                }
                dialog();

                break;

        }
    }

    Boolean isLogin = false;

    public void LoginOut() {
        EnterCheckUtil.getInstance().outLogin();
        isLogin = true;
        MessageEvent event = new MessageEvent();
        event.message = MSG_LOGIN_OUT;
        EventBus.getDefault().post(event);
    }

    @Override
    protected void initToolbar() {
        tvTitle.setText("我的");
        toolbar.setTitle("");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.left_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() instanceof MeActivity) {
                    getActivity().finish();
                }
            }
        });
    }

    @Override
    protected long setLoadInterval() {
        return 0;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (position < 0 || position > dataList.size() - 1) {
            return;
        }
        boolean isLogin = EnterCheckUtil.getInstance().isLogin(true);
        if (!isLogin) {
            return;
        }
        Intent intent = dataList.get(position).getIntent();
        boolean canClick = dataList.get(position).isCanClick();
        if (canClick) {
            if (intent == null) {

            } else {
                startActivity(intent);
            }
        } else {
            ToastUtils.getInstance().showToast(getActivity(), "您当前没有此权限！");
        }
    }

    @Override
    public void OnZhiFuSuccess(String strOrderNum) {
        Toast.makeText(getActivity(), strOrderNum, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnZhiFuFail(String strInfo) {
        Toast.makeText(getActivity(), strInfo, Toast.LENGTH_SHORT).show();
    }

    /**
     * 获取未读消息数
     */
    public void getMsgNum() {
        String mSupportUrl = Consts.BASE_URL + "/application/myTasksNums";
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        OkHttpUtil.getInstance().doAsyncPost(mSupportUrl, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                if (!isPrepared) {
                    return;
                }
                ULog.e("ck", "未读消息：" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.optString("code");
                    String msg = jsonObject.optString("msg");
                    int msgNum = 0;
                    JSONObject data = jsonObject.optJSONObject("data");
                    if (data != null) {
                        msgNum = data.optInt("data");
                        tvMyScore.setText("积分：" + data.optInt("score"));
                    }
                    if (TextUtils.equals(code, "0")) {
                        updateProgressPartly(0, msgNum);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String errorMsg) {
            }
        });
    }

    private void updateProgressPartly(int position, int msgCount) {
        int firstVisiblePosition = gridView.getFirstVisiblePosition();
        int lastVisiblePosition = gridView.getLastVisiblePosition();
        if (position >= firstVisiblePosition && position <= lastVisiblePosition) {
            View view = gridView.getChildAt(position - firstVisiblePosition);
            if (view.getTag() instanceof MinAdapter.ViewHolder) {
                MinAdapter.ViewHolder vh = (MinAdapter.ViewHolder) view.getTag();
                if (vh == null) {
                    return;
                }
                if (vh.msgDot == null) {
                    return;
                }
                if (msgCount <= 0) {
                    vh.msgDot.setVisibility(View.GONE);
                } else {
                    vh.msgDot.setVisibility(View.GONE);
                    vh.msgDot.setText(msgCount + "");
                    adapter.notifyDataSetChanged();
                }

            }
        }
    }
}
