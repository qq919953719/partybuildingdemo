package com.longhoo.net;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.longhoo.net.headline.adapter.MainFragmentPagerAdapter;
import com.longhoo.net.headline.ui.HeadlineFragment;
import com.longhoo.net.headline.ui.MyCaptureActivity;
import com.longhoo.net.manageservice.ui.OrganizationLifedetailActivity;
import com.longhoo.net.manageservice.ui.PartyaffairsFragment;
import com.longhoo.net.mine.ui.LoginActivity;
import com.longhoo.net.mine.ui.MeFragment;
import com.longhoo.net.mine.ui.MyTaskActivity;
import com.longhoo.net.mine.ui.SetActivity;
import com.longhoo.net.study.ui.ActsEncrollDetailActivity;
import com.longhoo.net.study.ui.StudyCommunicationFragment;
import com.longhoo.net.study.ui.TrainCourseDetailActivity;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.MessageEvent;
import com.longhoo.net.utils.SPTool;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.utils.Utils;
import com.longhoo.net.utils.httprequest.OkHttpCallback;
import com.longhoo.net.utils.httprequest.OkHttpUtil;
import com.longhoo.net.widget.NoScrollViewPager;
import com.longhoo.net.widget.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.tv_msg_1)
    TextView tvMsg1;
    @BindView(R.id.iv_bottom_1)
    ImageView ivBottom1;
    @BindView(R.id.tv_bottom_1)
    TextView tvBottom1;
    @BindView(R.id.main_panel_1)
    FrameLayout mainPanel1;
    @BindView(R.id.tv_msg_2)
    TextView tvMsg2;
    @BindView(R.id.iv_bottom_2)
    ImageView ivBottom2;
    @BindView(R.id.tv_bottom_2)
    TextView tvBottom2;
    @BindView(R.id.main_panel_2)
    FrameLayout mainPanel2;
    @BindView(R.id.tv_msg_3)
    TextView tvMsg3;
    @BindView(R.id.iv_bottom_3)
    ImageView ivBottom3;
    @BindView(R.id.tv_bottom_3)
    TextView tvBottom3;
    @BindView(R.id.main_panel_3)
    FrameLayout mainPanel3;
    @BindView(R.id.tv_msg_4)
    TextView tvMsg4;
    @BindView(R.id.iv_bottom_4)
    ImageView ivBottom4;
    @BindView(R.id.tv_bottom_4)
    TextView tvBottom4;
    @BindView(R.id.main_panel_4)
    FrameLayout mainPanel4;
    @BindView(R.id.tv_msg_5)
    TextView tvMsg5;
    @BindView(R.id.iv_bottom_5)
    ImageView ivBottom5;
    @BindView(R.id.tv_bottom_5)
    TextView tvBottom5;
    @BindView(R.id.main_panel_5)
    FrameLayout mainPanel5;
    @BindView(R.id.bottom_container)
    LinearLayout bottomContainer;
    @BindView(R.id.viewpager)
    NoScrollViewPager viewPager;
    @BindView(R.id.activity_main)
    RelativeLayout activityMain;
    @BindView(R.id.iv_scan)
    ImageView ivScan;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_msg)
    ImageView ivMsg;
    @BindView(R.id.msg_dot)
    TextView msgDot;
    @BindView(R.id.me_panel)
    FrameLayout mePanel;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private MainFragmentPagerAdapter adapter;
    private ImageView[] imageViews;
    private TextView[] textViews;
    private TextView[] msgViews;
    private HeadlineFragment headlineFragment;

    private PartyaffairsFragment partyaffairsFragment;
    private int[] inactRes = {R.drawable.home_tab_1_2, R.drawable.home_tab_2_2, R.drawable.home_tab_3_2, R.drawable.home_tab_4_2, R.drawable.home_tab_5_2};
    private int[] actRes = {R.drawable.home_tab_1_1, R.drawable.home_tab_2_1, R.drawable.home_tab_3_1, R.drawable.home_tab_4_1, R.drawable.home_tab_5_1};
    private List<Fragment> list;
    MyApplication app = null;
    //签到提示框
    private AlertDialog signDialog;
    private TextView tvSignTitle;
    private TextView tvSignContent;
    private TextView tvSignCommit;
    MeFragment meFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        app = (MyApplication) getApplicationContext();
        Utils.creatTempFolder();
        Utils.delFolder(Consts.SD_ROOT_OLD);
        hasFragment = true;
        imageViews = new ImageView[]{ivBottom1, ivBottom2, ivBottom3,  ivBottom4};
        textViews = new TextView[]{tvBottom1, tvBottom2, tvBottom3, tvBottom4};
        msgViews = new TextView[]{tvMsg1, tvMsg2, tvMsg3, tvMsg5, tvMsg4};
        list = new ArrayList<>();
        headlineFragment = new HeadlineFragment();
        partyaffairsFragment = new PartyaffairsFragment();
        meFragment = new MeFragment();
        list.add(headlineFragment);
        list.add(partyaffairsFragment);
        list.add(new StudyCommunicationFragment());
        //list.add(new SupervisionAndCheckFragment());
        list.add(meFragment);
        adapter = new MainFragmentPagerAdapter(getSupportFragmentManager(), list);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);
        viewPager.addOnPageChangeListener(this);
        viewPager.setNoScroll(true);
        viewPager.setCurrentItem(0);
        setUI(0);
    }

//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        boolean autoRefresh = intent.getBooleanExtra("auto_refresh",false);
//        if(autoRefresh){
//            MainActivity.this.recreate();
//            if(viewPager!=null){
//                viewPager.setCurrentItem(0);
//            }
//        }
//    }

    @Override
    protected void initToolbar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setUI(position);
        if (position == 0) {
            ivScan.setVisibility(View.VISIBLE);
        } else {
            ivScan.setVisibility(View.GONE);
        }
        if (position == 3) {
            ivMsg.setImageResource(R.drawable.ic_set);
            msgDot.setVisibility(View.GONE);
            meFragment.getMsgNum();
        } else {
            getMsgNum();
//            msgDot.setVisibility(View.VISIBLE);
            ivMsg.setImageResource(R.drawable.home_top_message);
        }
        switch (position) {
            case 0:
                tvTitle.setText("龙虎网党员之家");
                break;
            case 1:
                tvTitle.setText("管理服务");
                break;
            case 2:
                tvTitle.setText("学习交流");
                break;
            case 3:
                tvTitle.setText("我的");
                //tvTitle.setText("监督考核");
                break;
//            case 4:
//                tvTitle.setText("我的");
//                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    int mPositon = 0;

    private void setUI(int position) {
        mPositon = position;
        int size = textViews.length;
        for (int i = 0; i < size; i++) {
            if (i == position) {
                textViews[i].setTextColor(getResources().getColor(R.color.main_bottombar_active));
                imageViews[i].setImageResource(actRes[i]);
                if (msgViews[i].isShown())
                    msgViews[i].setVisibility(View.GONE);
            } else {
                textViews[i].setTextColor(getResources().getColor(R.color.main_bottombar_inactive));
                imageViews[i].setImageResource(inactRes[i]);
            }

        }
    }

    @OnClick({R.id.main_panel_1, R.id.main_panel_2, R.id.main_panel_3, R.id.main_panel_4, R.id.main_panel_5
            , R.id.iv_scan, R.id.me_panel})
    void onClick(View view) {
        if (!EnterCheckUtil.getInstance().isLogin(true)) {
            return;
        }
        switch (view.getId()) {
            case R.id.main_panel_1:
                viewPager.setCurrentItem(0, false);
                headlineFragment.onResume();
                break;
            case R.id.main_panel_2:
                viewPager.setCurrentItem(1, false);
                break;
            case R.id.main_panel_3:
                viewPager.setCurrentItem(2, false);
                break;
            case R.id.main_panel_4:
                viewPager.setCurrentItem(3, false);
                break;
            case R.id.main_panel_5:
                viewPager.setCurrentItem(3, false);
                break;
            case R.id.iv_scan:
                new IntentIntegrator(this).setPrompt("扫描条码或二维码").setCaptureActivity(MyCaptureActivity.class).initiateScan();
                break;
            case R.id.me_panel:
                if (viewPager.getCurrentItem() == 3) {
                    Intent intent = new Intent(this, SetActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(this, MyTaskActivity.class);
                    startActivity(intent);

                    //通知公告
//                    Intent intent = new Intent(this, PartyflzActivity.class);
//                    intent.putExtra("type", "5");
//                    this.startActivity(intent);
                }

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ULog.e("cc", requestCode + " " + resultCode);
        String scanResult = "";
        if (resultCode == RESULT_OK) {
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (result != null) {
                scanResult = result.getContents();
            }
        } else if (resultCode == MyCaptureActivity.RESULT_CODE_PICK_CODE) {
            // 从本地图片二维码获取链接
            if (data != null) {
                scanResult = data.getStringExtra("result");
            }
        }
        if (TextUtils.isEmpty(scanResult)) {
            ToastUtils.getInstance().showToast(this, "取消扫描~");
        } else {
            ULog.e(Consts.TAG, "扫描地址：" + scanResult);
            doSign(scanResult);
        }
    }

    private void doSign(String url) {
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        if (url.contains("type")) {
            url = url.substring(url.lastIndexOf("type"));
            String[] strings = url.split("[&]");
            for (String param : strings) {
                String[] arrSplitEqual = null;
                arrSplitEqual = param.split("[=]");
                if (arrSplitEqual.length > 1) {
                    params.put(arrSplitEqual[0], arrSplitEqual[1]);
                    //ULog.e("ck",arrSplitEqual[0]+" "+arrSplitEqual[1]);
                }
            }
        }
        String requestUrl = Consts.BASE_URL + "/Index/createEwm";
        OkHttpUtil.getInstance().doAsyncPost(requestUrl, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                ULog.e("cc", "扫码签到：" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.optString("code");
                    String msg = jsonObject.optString("msg");
                    if (!TextUtils.equals(code, "0")) {
                        showSignDialog("签到失败", msg, "", "");
                        return;
                    }
                    String content = "";
                    String time = "";
                    String id = "";
                    String type = "";
                    JSONObject data = jsonObject.optJSONObject("data");
                    if (data != null) {
                        content = data.optString("content");
                        time = data.optString("time");
                        type = data.optString("type");
                        id = data.optString("id");
                    }
                    showSignDialog("签到成功", content + Utils.getDataTime(time) + "，请认真参加哦！", id, type);
                } catch (JSONException e) {
                    e.printStackTrace();
                    showSignDialog("签到失败", "服务器异常，签到失败~", "", "");
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                showSignDialog("签到失败", "网络异常，签到失败~", "", "");
            }
        });
    }

    private void showSignDialog(String title, String content, final String id, final String type) {
        signDialog = new AlertDialog.Builder(this).create();
        signDialog.show();
        Window window = signDialog.getWindow();
        window.setContentView(R.layout.alert_sign);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = Utils.getDeviceSize(this).x * 650 / 750;
        window.setAttributes(params);
        tvSignTitle = (TextView) signDialog.findViewById(R.id.tv_sign_title);
        tvSignContent = (TextView) signDialog.findViewById(R.id.tv_sign_content);
        tvSignCommit = (TextView) signDialog.findViewById(R.id.tv_sign_commit);
        tvSignTitle.setText(title);
        tvSignContent.setText(content);
        tvSignCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSignDialog();
            }
        });
        signDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                ULog.e("ck", type + " pppp " + id);
                if (!TextUtils.isEmpty(id) && !TextUtils.isEmpty(type)) {
                    //1 组织生活 2活动报名 3培训班报名
                    if (TextUtils.equals(type, "1")) {
                        OrganizationLifedetailActivity.goTo(MainActivity.this, id);
                    } else if (TextUtils.equals(type, "2")) {
                        ActsEncrollDetailActivity.goTo(MainActivity.this, id);
                    } else if (TextUtils.equals(type, "3")) {
                        TrainCourseDetailActivity.goTo(MainActivity.this, id);
                    }
                }
            }
        });
    }

    private void hideSignDialog() {
        if (signDialog != null) {
            if (signDialog.isShowing()) {
                signDialog.dismiss();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!EnterCheckUtil.getInstance().isLogin(false)) {
            msgDot.setVisibility(View.GONE);
            return;
        }
        getMsgNum();
    }

    /**
     * 获取未读消息数
     */
    public int opentag = 1; //0:可见  1：隐藏

    private void getMsgNum() {
        String mSupportUrl = Consts.BASE_URL + "/application/myTasksNums";
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        OkHttpUtil.getInstance().doAsyncPost(mSupportUrl, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                if (isFinishing()) {
                    return;
                }
                ULog.e("cc", "未读消息：" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.optString("code");
                    String msg = jsonObject.optString("msg");
                    int msgNum = 0;
                    JSONObject data = jsonObject.optJSONObject("data");
                    if (data != null) {
                        msgNum = data.optInt("data");
                        opentag = data.optInt("open");
                    }
                    if (TextUtils.equals(code, "0")) {
                        if (msgNum <= 0) {
                            msgDot.setVisibility(View.GONE);
                        } else {
                            if (viewPager.getCurrentItem() == 3) {
                                msgDot.setVisibility(View.GONE);
                                msgDot.setText(msgNum + "");
                            } else {
                                msgDot.setVisibility(View.VISIBLE);
                                msgDot.setText(msgNum + "");
                            }

                        }


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


    long lastTime;

    @Override
    public void onBackPressed() {
        lastTime = SPTool.getLong(this, "last_back_time", 0);
        long curTime = System.currentTimeMillis();
        if (curTime - lastTime > 2 * 1000) {
            ToastUtils.getInstance().showToast(this, "再按一次退出");
            SPTool.putLong(this, "last_back_time", curTime);
            return;
        }
        MyApplication application = (MyApplication) getApplication();
        application.RemoveAllActivity();
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideSignDialog();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        String msg = event.message;
        if (TextUtils.equals(msg, MeFragment.MSG_LOGIN_OUT)) {
//            viewPager.setCurrentItem(0,false);
//            if (headlineFragment != null) {
//                headlineFragment.onResume();
//            }
            //到登录界面
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
