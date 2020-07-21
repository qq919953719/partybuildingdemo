package com.longhoo.net.manageservice.ui;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupWindow;

import com.longhoo.net.R;
import com.longhoo.net.manageservice.adapter.PartyaffairsHomeAdapter;
import com.longhoo.net.manageservice.bean.PartyTabBean;
import com.longhoo.net.partyaffairs.ui.OpenPartyActivity;
import com.longhoo.net.partyaffairs.ui.PartyDisciplineSupervisionActivity;
import com.longhoo.net.partyaffairs.ui.TaiZhangActivity;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.utils.httprequest.OkHttpCallback;
import com.longhoo.net.utils.httprequest.OkHttpUtil;
import com.longhoo.net.widget.base.BaseLazyFragment;
import com.longhoo.net.widget.base.WebViewActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * @author CK 首页——管理服务
 */
public class PartyaffairsFragment extends BaseLazyFragment implements GridView.OnItemClickListener {
    @BindView(R.id.grid_view)
    GridView gridView;
    private PartyaffairsHomeAdapter adapter;
    private List<PartyTabBean> dataList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentId() {
        return R.layout.fragment_partyaffairs;
    }

    @Override
    protected void onLazyLoad() {
//        MyApplication app = (MyApplication)getContext().getApplicationContext();
//        if (app.getmLoginBean() != null) {
//            String roleId = app.getmLoginBean().getData().getRoleid();
//            if(TextUtils.equals(roleId,"0")||TextUtils.equals(roleId,"1")||TextUtils.equals(roleId,"2")){
//                getTopNoti();
//            }else{
//                topPanel.setVisibility(View.GONE);
//            }
//        }else{
//            topPanel.setVisibility(View.GONE);
//        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        MyApplication app = (MyApplication)getContext().getApplicationContext();
//        if (app.getmLoginBean() != null) {
//            String roleId = app.getmLoginBean().getData().getRoleid();
//            if(TextUtils.equals(roleId,"0")||TextUtils.equals(roleId,"1")||TextUtils.equals(roleId,"2")){
//                getTopNoti();
//            }else{
//                topPanel.setVisibility(View.GONE);
//            }
//        }else{
//            topPanel.setVisibility(View.GONE);
//        }
    }

    @Override
    protected void initViews(View view) {
//        PartyTabBean bean1 = new PartyTabBean(ExaminationCenterActivity.class, R.drawable.dangwu_tab_1, "在线学习");
//        bean1.setExtra("0");
//        PartyTabBean bean7 = new PartyTabBean(ExaminationCenterActivity.class, R.drawable.dangwu_tab_7, "在线答题");
//        bean7.setExtra("1");
//        PartyTabBean bean6 = new PartyTabBean(ExamOrderActivity.class, R.drawable.dangwu_tab_6, "成绩排名");
        Intent intent1 = new Intent(getActivity(), OrganizationActivity.class);
        Intent intent2 = new Intent(getActivity(), TaiZhangActivity.class);
        intent2.putExtra("oid", "");
        intent2.putExtra("title", "组织生活");
        intent2.putExtra("type", "2");
        Intent intent3 = new Intent(getActivity(), PayMoonListActivity.class);
        Intent intent4 = new Intent(getActivity(), DataShareingActivity.class);
        Intent intent5 = new Intent(getActivity(), ActsEncrollActivity.class);
        Intent intent6 = new Intent(getActivity(), MaterialReportActivity.class);
        Intent intent7 = new Intent(getActivity(), MemberDevelopmentActivity.class);
        Intent intent8 = new Intent(getActivity(), OpenPartyActivity.class);
        PartyTabBean bean1 = new PartyTabBean(intent1, R.drawable.partyaffairs_header_1, "组织架构");
        PartyTabBean bean2 = new PartyTabBean(intent2, R.drawable.partyaffairs_header_2, "组织生活");
        PartyTabBean bean3 = new PartyTabBean(intent3, R.drawable.partyaffairs_header_3, "党费缴纳");
        PartyTabBean bean4 = new PartyTabBean(intent4, R.drawable.partyaffairs_header_4, "资料共享");
        PartyTabBean bean5 = new PartyTabBean(intent5, R.drawable.partyaffairs_header_5, "党员活动");
        PartyTabBean bean6 = new PartyTabBean(intent6, R.drawable.partyaffairs_header_6, "材料上报");
        PartyTabBean bean7 = new PartyTabBean(intent7, R.drawable.partydevlepment, "党员发展");
        PartyTabBean bean8 = new PartyTabBean(intent8, R.drawable.partyaffairs_header_4, "党务公开");

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
        dataList.add(bean4);
        dataList.add(bean5);
        dataList.add(bean6);
        dataList.add(bean7);
        dataList.add(bean8);
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

//    private void getTopNoti() {
//        String url = Consts.BASE_URL + "/News/log_dangmsg";
//        Map<String, String> params = new HashMap<>();
//        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
//        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
//            @Override
//            public void onSuccess(String response) {
//                if (!isPrepared) {
//                    return;
//                }
//                try {
//                    ULog.e("ck", "dang_msg:" + response);
//                    JSONObject jsonObject = new JSONObject(response);
//                    String code = jsonObject.optString("code");
//                    JSONObject data = jsonObject.optJSONObject("data");
//                    if (data != null) {
//                        JSONObject news = data.optJSONObject("news");
//                        if (news != null) {
//                            final String mid = news.optString("mid");
//                            String title = news.optString("title");
//                            if (TextUtils.isEmpty(title)) {
//                                topPanel.setVisibility(View.GONE);
//                            } else {
//                                topPanel.setVisibility(View.VISIBLE);
//                                tvTopNoti.setText(title + "");
//                                tvTopNoti.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        Intent intent = new Intent(getActivity(), NotiNewsDetailActivity.class);
//                                        intent.putExtra("noti_news_mid", mid);
//                                        startActivity(intent);
//                                    }
//                                });
//                            }
//                        } else {
//                            topPanel.setVisibility(View.GONE);
//                        }
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(String errorMsg) {
//
//            }
//        });
//    }

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

    private Map<String, String> reviewUrlMap = new HashMap<>();

    private void doGetReviewUrl(final String type, final String title) {
        final String uid = EnterCheckUtil.getInstance().getUid_Token()[0];
        final String token = EnterCheckUtil.getInstance().getUid_Token()[1];
        String url = Consts.BASE_URL + "/Application/pingyi";
        Map<String, String> params = new HashMap<>();
        params.put("uid", uid);
        params.put("token", token);
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                ULog.e("民主评议、支部评议url:" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.optString("code");
                    String msg = jsonObject.optString("msg");
                    JSONObject data = jsonObject.optJSONObject("data");
                    JSONArray info = data.optJSONArray("info");
                    int size = info.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject jo = (JSONObject) info.opt(i);
                        String url = jo.optString("address");
                        String type = jo.optString("type");
                        reviewUrlMap.put(type, url);
                    }
                    //成功
                    if (TextUtils.equals(code, "0")) {
                        if (reviewUrlMap.size() == 0) {
                            ToastUtils.getInstance().showToast(getActivity(), "敬请期待~");
                        } else {
                            for (Map.Entry<String, String> entry : reviewUrlMap.entrySet()) {
                                if (TextUtils.equals(entry.getKey(), type)) {
                                    //打开网页
                                    if (TextUtils.isEmpty(entry.getValue())) {
                                        ToastUtils.getInstance().showToast(getActivity(), "敬请期待~");
                                    } else {
                                        ULog.e(entry.getValue() + "&uid=" + uid + "&token=" + token);
                                        WebViewActivity.goToWebView(getActivity(), entry.getValue() + "&uid=" + uid + "&token=" + token, title, false);
                                    }
                                }
                            }
                        }
                    } else {
                        ToastUtils.getInstance().showToast(getActivity(), msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtils.getInstance().showToast(getActivity(), "解析异常~");
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(getActivity(), "网络异常~");
            }
        });
    }

    /**
     * 弹出底部对话框
     */
    public void openPop(View view) {
        View popView = LayoutInflater.from(getActivity()).inflate(
                R.layout.choose_video_img, null);
        View rootView = popView.findViewById(R.id.allview); // 當前頁面的根佈局
        final PopupWindow popupWindow = new PopupWindow(popView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        setBackgroundAlpha(0.5f);//设置屏幕透明度

        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);// 点击空白处时，隐藏掉pop窗口
        // 顯示在根佈局的底部
        popupWindow.showAtLocation(rootView, Gravity.BOTTOM | Gravity.LEFT, 0,
                0);

        popView.findViewById(R.id.tuwen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //图文


                Intent intent = null;
                intent = new Intent(getActivity(), PartyDisciplineSupervisionActivity.class);
                intent.putExtra("count", 6);
                intent.putExtra("video", false);
                intent.putExtra("image", true);
                startActivity(intent);
                popupWindow.dismiss();
            }
        });

        popView.findViewById(R.id.shipin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                intent = new Intent(getActivity(), PartyDisciplineSupervisionActivity.class);
                intent.putExtra("count", 1);
                intent.putExtra("video", true);
                intent.putExtra("image", false);
                startActivity(intent);
                popupWindow.dismiss();
            }
        });

        popView.findViewById(R.id.quxiao).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取消
                popupWindow.dismiss();
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // popupWindow隐藏时恢复屏幕正常透明度
                setBackgroundAlpha(1.0f);
            }
        });

    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha 屏幕透明度0.0-1.0 1表示完全不透明
     */
    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        (getActivity()).getWindow().setAttributes(lp);
    }

}
