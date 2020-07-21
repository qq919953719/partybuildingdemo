package com.longhoo.net.manageservice.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.longhoo.net.MyApplication;
import com.longhoo.net.R;
import com.longhoo.net.manageservice.adapter.GridViewAdapter;
import com.longhoo.net.manageservice.adapter.PartyCommunityCommentAdapter;
import com.longhoo.net.manageservice.bean.OrganizationallCommentBean;
import com.longhoo.net.manageservice.bean.PTCConetntBean;
import com.longhoo.net.study.ui.VerificationCodeActivity;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.DisplayUtil;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.GlideManager;
import com.longhoo.net.utils.MessageEvent;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.utils.Utils;
import com.longhoo.net.utils.httprequest.HttpRequestPresenter;
import com.longhoo.net.utils.httprequest.HttpRequestView;
import com.longhoo.net.utils.httprequest.OkHttpCallback;
import com.longhoo.net.utils.httprequest.OkHttpUtil;
import com.longhoo.net.widget.CustomGridView;
import com.longhoo.net.widget.HeaderAndFooterWrapper;
import com.longhoo.net.widget.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * Created by ${CC} on 2018/1/8.
 */

public class HuaTiContentActivity extends BaseActivity implements HttpRequestView, OnRefreshLoadmoreListener, HeaderAndFooterWrapper.OnItemClickListener, EditText.OnEditorActionListener {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    @BindView(R.id.tv_reload)
    TextView tvReload;
    @BindView(R.id.ll_net_error_panel)
    LinearLayout netErrorPanel;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.et_comment)
    EditText etComment;
    @BindView(R.id.tv_comment)
    TextView tvComment;
    @BindView(R.id.fl_write_comment)
    FrameLayout flWriteComment;
    @BindView(R.id.iv_comments)
    ImageView ivComments;
    @BindView(R.id.tv_comments_num)
    TextView tvCommentsNum;
    @BindView(R.id.ll_comments)
    LinearLayout llComments;
    @BindView(R.id.iv_collect)
    ImageView ivCollect;
    @BindView(R.id.tv_collect_num)
    TextView tvCollectNum;
    @BindView(R.id.bottom_container)
    LinearLayout bottomContainer;
    @BindView(R.id.lv_allview)
    LinearLayout lvAllview;
    private Activity activity;
    PartyCommunityCommentAdapter adapter;
    private int mPage = 1;
    private List<OrganizationallCommentBean.DataBean.ComsBean> dataList = new ArrayList<>();
    private HttpRequestPresenter requestPresenter;
    private HeaderAndFooterWrapper adapterWrapper;

    private String url = Consts.BASE_URL + "/Said/public_saidcoms";

    private View headerView;
    private String titleText;
    private String InfoText;
    private String strSid;
    private int position;//从列表的位置点过来
    private int num;
    private int collectnum;
    private String strIszan = "";
    CustomGridView itemGridView;
    TextView tvMainTitle;
    TextView tvMainContent;

    public static void goTo(Context context, String sid, int position) {
        Intent intent = new Intent(context, HuaTiContentActivity.class);
        intent.putExtra("sid", sid);
        intent.putExtra("position", position);
        context.startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!EnterCheckUtil.getInstance().isLogin(false)) {
            tvComment.setVisibility(View.VISIBLE);
            etComment.setVisibility(View.GONE);
        } else {
            tvComment.setVisibility(View.GONE);
            etComment.setVisibility(View.VISIBLE);
        }
        JZVideoPlayer.goOnPlayOnResume();
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_ccmtcomment;
    }

    @Override
    protected void initViews() {
        if (getIntent() != null) {
            titleText = getIntent().getStringExtra("title") + "";
            strSid = getIntent().getStringExtra("sid");
            position = getIntent().getIntExtra("position", 0);
            InfoText = getIntent().getStringExtra("info") + "";
            num = getIntent().getIntExtra("commentnum", 0);
            collectnum = getIntent().getIntExtra("zancount", 0);
            tvCommentsNum.setText(num + "");
//            tvTime.setText(InfoText + "");
            tvCollectNum.setText(collectnum + "");
            ULog.e("cc", getIntent().getStringExtra("sid") + "   " + getIntent().getStringExtra("title"));
        }
        activity = this;
        requestPresenter = new HttpRequestPresenter(this, this);
        adapter = new PartyCommunityCommentAdapter(this, dataList);
        adapterWrapper = new HeaderAndFooterWrapper(adapter);
        adapterWrapper.setOnItemClickListener(this);
        recyclerView.setAdapter(adapterWrapper);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(this));
        smartRefreshLayout.setRefreshFooter(new ClassicsFooter(this));
        smartRefreshLayout.setOnRefreshLoadmoreListener(this);
        //第一次加载
        ReshView();
    }

    void ReshView() {
        mPage = 1;
        MyApplication App = (MyApplication) getApplicationContext();
        if (App.getmLoginBean() == null) {
            ToastUtils.getInstance().showToast(this, "登录过期，请重新登陆！");
            smartRefreshLayout.finishRefresh(0);
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("page", String.valueOf(mPage + ""));
        params.put("sid", String.valueOf(strSid));
//        http:
//test.025nj.com/dangjian/index.php?m=Api&/Said/public_said&uid=0&page=1&signid=0&search=
        if (App.getmLoginBean() == null) {
            params.put("token", "");
            params.put("uid", String.valueOf(""));
        } else {
            params.put("token", String.valueOf(App.getmLoginBean().getData().getToken()));
            params.put("uid", String.valueOf(App.getmLoginBean().getData().getUid()));
        }

//        params.put("token", String.valueOf("mCR2G6JKoWvjOoDfOreYcg=="));
        requestPresenter.doInitHttpData(url, Consts.REQUEST_METHOD_POST, Consts.REQUEST_REFRESH, params);
    }

    @Override
    protected void initToolbar() {
        toolbar.setTitle("");
        tvTitle.setText("详情");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.left_arrow);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onNetworkError() {
        finishRefreshLoad();
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        netErrorPanel.setVisibility(View.VISIBLE);
        ToastUtils.getInstance().showToast(this, "网络异常~");
        tvReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                netErrorPanel.setVisibility(View.GONE);
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPage = 1;
                        MyApplication App = (MyApplication) getApplicationContext();
                        Map<String, String> params = new HashMap<>();
                        if (App.getmLoginBean() == null) {
                            params.put("token", "");
                            params.put("uid", String.valueOf(""));
                        } else {
                            params.put("token", String.valueOf(App.getmLoginBean().getData().getToken()));
                            params.put("uid", String.valueOf(App.getmLoginBean().getData().getUid()));
                        }
                        if (!isFinishing()) {
                            requestPresenter.doInitHttpData(url, Consts.REQUEST_METHOD_POST, Consts.REQUEST_REFRESH, params);
                        }
                    }
                }, 200);
            }
        });
    }


    void GetPTCConetnt() {

        MyApplication App = (MyApplication) getApplicationContext();
        Map<String, String> map = new HashMap<>();

        map.put("sid", String.valueOf(strSid));
        map.put("uid", String.valueOf(App.getmLoginBean().getData().getUid()));
        map.put("token", String.valueOf(App.getmLoginBean().getData().getToken()));
        OkHttpUtil.getInstance().doAsyncPost(Consts.BASE_URL + "/Said/saidinfo", map, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if (obj.has("code")) {
                        if (obj.getString("code").equals("0")) {
                            Gson gson = new Gson();
                            final PTCConetntBean mPTCConetntBean = gson.fromJson(response, PTCConetntBean.class);

                            etComment.setOnEditorActionListener(HuaTiContentActivity.this);
                            etComment.addTextChangedListener(new HuaTiContentActivity.MyTextWatch(etComment));
                            etComment.clearFocus();

                            ivCollect.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    collectNews();
                                }
                            });
                            if (!TextUtils.isEmpty(mPTCConetntBean.getData().getSaid().getVideo_url())) {
                                videoPlayer.setVisibility(View.VISIBLE);
                                videoPlayer.setUp(mPTCConetntBean.getData().getSaid().getVideo_url(), JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "");
                                if (!TextUtils.isEmpty(mPTCConetntBean.getData().getSaid().getVideo_img())) {
                                    GlideManager.getInstance().load(HuaTiContentActivity.this, mPTCConetntBean.getData().getSaid().getVideo_img(), videoPlayer.thumbImageView);
                                }
                                videoPlayer.setIsLiveStream(false);
                                //videoPlayer.startButton.performClick();

//                                new Thread() {
//                                    @Override
//                                    public void run() {
//                                        super.run();
//                                        try {
//                                            Thread.sleep(1000);//休眠3秒
//                                            mHandler.post(mRunnable);
//                                        } catch (InterruptedException e) {
//                                            e.printStackTrace();
//                                        }
//                                        /**
//                                         * 要执行的操作
//                                         */
//                                    }
//                                }.start();

                            } else {
                                fl_container.setVisibility(View.GONE);
                            }

                            num = Integer.parseInt(mPTCConetntBean.getData().getSaid().getCom());
                            tvMainTitle.setText(mPTCConetntBean.getData().getSaid().getTitle());
                            String info = mPTCConetntBean.getData().getSaid().getInfo() + "";
                            tvMainContent.setText(Utils.replaceTransference(info));
                            tvCommentsNum.setText(mPTCConetntBean.getData().getSaid().getCom());
//                            iszan 是否已点赞  0否    大于0 是

                            strIszan = mPTCConetntBean.getData().getSaid().getIszan();
                            if (mPTCConetntBean.getData().getSaid().getIszan().equals("0")) {
                                ivCollect.setImageResource(R.drawable.icon_collection);
                            } else {
                                ivCollect.setImageResource(R.drawable.icon_collectioned);
                            }
                            collectnum = Integer.parseInt(mPTCConetntBean.getData().getSaid().getZan());
                            tvCollectNum.setText(mPTCConetntBean.getData().getSaid().getZan() + "");
                            //加载网格图片
                            List<String> picList = mPTCConetntBean.getData().getSaid().getPicbig();
                            GridViewAdapter adapter = new GridViewAdapter(HuaTiContentActivity.this, picList);
                            int width = 0;
                            int height = 0;
                            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) itemGridView.getLayoutParams();
                            if (picList.size() <= 0) {
                                itemGridView.setVisibility(View.GONE);
                            } else {

                                if (picList.size() == 1) {
                                    itemGridView.setVisibility(View.VISIBLE);
                                    itemGridView.setNumColumns(1);
                                    width = Utils.getDeviceSize(HuaTiContentActivity.this).x - DisplayUtil.dp2px(HuaTiContentActivity.this, 30);
                                    lp.width = RecyclerView.LayoutParams.MATCH_PARENT;

                                } else if (picList.size() == 2 || picList.size() == 4) {
                                    itemGridView.setVisibility(View.VISIBLE);
                                    itemGridView.setNumColumns(2);
                                    width = (Utils.getDeviceSize(HuaTiContentActivity.this).x - DisplayUtil.dp2px(HuaTiContentActivity.this, 38)) / 2;
                                    lp.width = width * 2 + DisplayUtil.dp2px(HuaTiContentActivity.this, 4);
                                } else {
                                    itemGridView.setVisibility(View.VISIBLE);
                                    itemGridView.setNumColumns(3);
                                    width = (Utils.getDeviceSize(HuaTiContentActivity.this).x - DisplayUtil.dp2px(HuaTiContentActivity.this, 38)) / 3;
                                    lp.width = RecyclerView.LayoutParams.MATCH_PARENT;
                                }
                                itemGridView.setLayoutParams(lp);
                                height = width * 3 / 4;
                                adapter.setItemHeight(height);
                                itemGridView.setAdapter(adapter);
                                itemGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        if (position >= 0 && position <= mPTCConetntBean.getData().getSaid().getPicbig().size() - 1) {
                                            List<LocalMedia> photoList = new ArrayList<>();
                                            for (String photo : mPTCConetntBean.getData().getSaid().getPicbig()) {
                                                ULog.e(photo);
                                                photoList.add(new LocalMedia(photo, 0, PictureMimeType.ofImage(), ""));
                                            }
                                            PictureSelector.create(HuaTiContentActivity.this).externalPicturePreview(position, photoList);
                                        }
                                    }
                                });
                            }


                        } else {
                            Toast.makeText(HuaTiContentActivity.this, "数据异常", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(HuaTiContentActivity.this, "数据异常", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(HuaTiContentActivity.this, "网络错误~");

            }
        });
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEND) {
            String info = etComment.getText().toString().trim();
            if (TextUtils.isEmpty(info)) {
                ToastUtils.getInstance().showToast(this, "请输入内容！");
                return true;
            }
            releaseComments(info);
        }
        return true;
    }

    /**
     * 收藏/取消收藏
     */
    private boolean isCollectChanged;

    private void collectNews() {
        String url = Consts.BASE_URL + "/Said/log_saidzan";
        Map<String, String> params = new HashMap<>();
        params.put("sid", strSid);
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.has("data")) {
                        String code = jsonObject.optString("code");
                    }
                    if (jsonObject.has("msg")) {
                        String msg = jsonObject.optString("msg");
                        ToastUtils.getInstance().showToast(HuaTiContentActivity.this, msg);
                    }

                    if (jsonObject.has("data")) {
                        JSONObject myJect = jsonObject.getJSONObject("data");
                        if (myJect.has("iszan")) {
                            strIszan = myJect.optString("iszan");
                            if (strIszan.equals("0")) {
                                ivCollect.setImageResource(R.drawable.icon_collection);

                                collectnum--;
                                tvCollectNum.setText(collectnum + "");
                            } else {
                                ivCollect.setImageResource(R.drawable.icon_collectioned);

                                collectnum++;
                                tvCollectNum.setText(collectnum + "");
                            }
                        }


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtils.getInstance().showToast(HuaTiContentActivity.this, "服务器异常~");
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(HuaTiContentActivity.this, "网络异常~");
            }
        });
    }
    /**
     * 发布评论
     *
     * @param info
     */

    /**
     * 发布话题评论
     * Post参数
     * {
     * token
     * sid 话题sid
     * info 评论内容
     * }
     * post 接口名 /Said/log_saidcom
     * get
     * http://test.025nj.com/dangjian/index.php?m=Api&/Said/log_saidcom&token=2133asf112as33&sid=12287&info=54655523
     * 返回
     * {
     * code 返回状态 0成功 3发布成功，待审核   9失败 5登录超时
     * msg  提示信息
     * data{ data不用解析
     * }
     * }
     */
    int mycount = 0;

    private void releaseComments(String info) {
        String url = Consts.BASE_URL + "/Said/log_saidcom";
        Map<String, String> params = new HashMap<>();
        params.put("sid", strSid); //回复给指定用户的uid(默认0)
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("info", info);
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = "";
                    String msg = "";
                    if (jsonObject.has("code")) {
                        code = jsonObject.optString("code");
                    }
                    if (jsonObject.has("msg")) {
                        msg = jsonObject.optString("msg");
                    }


                    ToastUtils.getInstance().showToast(HuaTiContentActivity.this, msg);
                    etComment.clearFocus();
                    etComment.setText("");
                    Utils.showHideSoftInput(HuaTiContentActivity.this, etComment, false);
                    if (TextUtils.equals(code, "0")) {
//                        tvCommentsNum.setText(++num + "");
//                        GetPTCConetnt();
                        num++;
                        tvCommentsNum.setText(num + "");
                        ReshView();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtils.getInstance().showToast(HuaTiContentActivity.this, "服务器异常~");
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(HuaTiContentActivity.this, "网络异常~");
            }
        });
    }


    private class MyTextWatch implements TextWatcher {

        private View view;

        public MyTextWatch(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            switch (view.getId()) {
                case R.id.et_comment:
                    if (s.length() > 300) {
                        etComment.setText(s.toString().subSequence(0, 300));
                        etComment.setSelection(300);
                        ToastUtils.getInstance().showToast(HuaTiContentActivity.this, "评论最多输入300个字！");
                        return;
                    }
                    break;
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }


    JZVideoPlayerStandard videoPlayer;
    FrameLayout fl_container;

    @Override
    public void onRefreshSuccess(String response) {
        ULog.e(Consts.TAG, "special:" + response);
        //处理数据
        List<OrganizationallCommentBean.DataBean.ComsBean> tempList = null;
        Gson gson = new Gson();
        try {
            OrganizationallCommentBean specialNewsBean = gson.fromJson(response, OrganizationallCommentBean.class);


            String code = specialNewsBean.getCode();
            if (!TextUtils.equals(code, "0")) {
                ToastUtils.getInstance().showToast(this, "获取数据失败~");
                return;
            }
            tempList = specialNewsBean.getData().getComs();
            //更新UI
            //添加头部
            if (headerView == null) {
                headerView = LayoutInflater.from(this).inflate(R.layout.headview_huati, recyclerView, false);

                videoPlayer = (JZVideoPlayerStandard) headerView.findViewById(R.id.video_player);

                fl_container = (FrameLayout) headerView.findViewById(R.id.fl_container);
                itemGridView = (CustomGridView) headerView.findViewById(R.id.item_grid_view);

                tvMainTitle = (TextView) headerView.findViewById(R.id.tv_main_title);

                tvMainContent = (TextView) headerView.findViewById(R.id.tv_main_content);
                GetPTCConetnt();
                adapterWrapper.addHeaderView(headerView);
            }


        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        if (tempList == null) {
            ToastUtils.getInstance().showToast(this, "服务器异常~");
            if (progressBar.isShown()) {
                progressBar.setVisibility(View.GONE);
            }
            return;
        }
        dataList.clear();
        if (tempList.size() > 0) {
            dataList.addAll(tempList);
            mPage++;
            //缓存数据
            //FileUtils.saveList(activity, (ArrayList) tempList, FileUtils.createCacheFile(activity,"data","headline_list_cache.txt"));
        }
        adapterWrapper.notifyDataSetChanged();
        smartRefreshLayout.finishRefresh(0);
        if (!recyclerView.isShown()) {
            recyclerView.setVisibility(View.VISIBLE);
        }
        if (progressBar.isShown()) {
            progressBar.setVisibility(View.GONE);
        }
        if (netErrorPanel.isShown()) {
            netErrorPanel.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRefreshError() {
        if (progressBar.isShown()) {
            progressBar.setVisibility(View.GONE);
        }
        if (netErrorPanel.isShown()) {
            netErrorPanel.setVisibility(View.GONE);
        }
        ToastUtils.getInstance().showToast(this, "刷新失败~");
        smartRefreshLayout.finishRefresh(0);
    }

    @Override
    public void onLoadMoreSuccess(String response) {
        ULog.e(Consts.TAG, "special:" + mPage + ":" + response);
        //处理数据
        List<OrganizationallCommentBean.DataBean.ComsBean> tempList2 = null;
        Gson gson = new Gson();
        try {
            OrganizationallCommentBean bean = gson.fromJson(response, OrganizationallCommentBean.class);
            String code = bean.getCode();
            if (!TextUtils.equals(code, "0")) {
                ToastUtils.getInstance().showToast(this, "获取数据失败~");
                return;
            }
            tempList2 = bean.getData().getComs();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        if (tempList2 == null) {
            ToastUtils.getInstance().showToast(this, "解析错误！");
            return;
        }
        //没有数据或数据<limit，说明无更多
        if (tempList2.size() <= 0) {
            ToastUtils.getInstance().showToast(this, "没有更多了~");
        } else {
            dataList.addAll(tempList2);
            mPage++;
            adapterWrapper.notifyDataSetChanged();
        }
        smartRefreshLayout.finishLoadmore(0);
    }

    @Override
    public void onLoadMoreError() {
        if (progressBar.isShown()) {
            progressBar.setVisibility(View.GONE);
        }
        ToastUtils.getInstance().showToast(this, "加载失败~");
        smartRefreshLayout.finishLoadmore(0);
    }

//    @Override
//    public void onItemClick(View view, int position) {
//        //新闻详情
//        Intent intent = new Intent(this, NewsDetailActivity.class);
//        intent.putExtra("news_nid", dataList.get(position).getNid());
//        intent.putExtra("news_position",position);
//        startActivityForResult(intent,100);
//    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
        //发送消息给投票选项列表，投票数增加
        if (position >= 0) {
            MessageEvent messageEvent = new MessageEvent();
            messageEvent.position = position;
            messageEvent.message = strIszan + "|" + collectnum + "|" + num;
            EventBus.getDefault().post(messageEvent);
        }
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        Map<String, String> params = new HashMap<>();
        params.put("page", String.valueOf(mPage + ""));
        params.put("sid", String.valueOf(strSid));
        mPage++;
        params.put("uid", EnterCheckUtil.getInstance().getUid_Token()[0]);
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        requestPresenter.doHttpData(url, Consts.REQUEST_METHOD_POST, Consts.REQUEST_LOADMORE, params);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mPage = 1;
        Map<String, String> params = new HashMap<>();
        params.put("page", String.valueOf(mPage + ""));
        params.put("sid", String.valueOf(strSid));
        params.put("uid", EnterCheckUtil.getInstance().getUid_Token()[0]);
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        requestPresenter.doHttpData(url, Consts.REQUEST_METHOD_POST, Consts.REQUEST_REFRESH, params);
    }

    @Override
    public void onDestroy() {
        finishRefreshLoad();

        super.onDestroy();
        JZVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onPause() {
        super.onPause();
        //videoPlayer.pauseAllVideos();
        JZVideoPlayer.goOnPlayOnPause();
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

    @Override
    public void onItemClick(View view, int position) {
//        ToastUtils.getInstance().showToast(this, "第" + position + "个");
//        if (position >= dataList.size()) {
//            return;
//        }
//        String nid = dataList.get(position).getNid();
//        Intent intent = new Intent(this, NewsDetailActivity.class);
//        intent.putExtra("news_nid", nid);
//        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
