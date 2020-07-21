package com.longhoo.net.manageservice.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.longhoo.net.MyApplication;
import com.longhoo.net.R;
import com.longhoo.net.mine.ui.LoginActivity;
import com.longhoo.net.manageservice.adapter.PartyCommunityCommentAdapter;
import com.longhoo.net.manageservice.bean.OrganizationallCommentBean;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.utils.Utils;
import com.longhoo.net.utils.httprequest.HttpRequestPresenter;
import com.longhoo.net.utils.httprequest.HttpRequestView;
import com.longhoo.net.utils.httprequest.OkHttpCallback;
import com.longhoo.net.utils.httprequest.OkHttpUtil;
import com.longhoo.net.widget.base.BaseActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ${CC} on 2017/12/14.
 */

public class CCMTComentActivity extends BaseActivity implements OnRefreshLoadmoreListener, HttpRequestView, EditText.OnEditorActionListener {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.tv_reload)
    TextView tvReload;
    @BindView(R.id.ll_net_error_panel)
    LinearLayout llNetErrorPanel;
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

    private String titleText;
    private String InfoText;
    private String strSid;

    private int num;
    private int collectnum;

    private String strIsZan;

    @Override
    protected void initViews() {

//        Intent intent = new Intent();
//        intent.setClass(PTCConetntActivity.this, CCMTComentActivity.class);
//        intent.putExtra("sid", strSid);
//        intent.putExtra("title", tvMainTitle.getText().toString().trim());
//        intent.putExtra("info", tvMainContent.getText().toString().trim());
//        intent.putExtra("commentnum", tvCommentsNum.getText().toString().trim());
//        intent.putExtra("iszan", strIszan);
//        intent.putExtra("zancount", tvCollectNum.getText().toString().trim());
//        startActivity(intent);
        requestPresenter = new HttpRequestPresenter(this, this);
        if (getIntent() != null) {
            titleText = getIntent().getStringExtra("title") + "";
            strSid = getIntent().getStringExtra("sid");
            InfoText = getIntent().getStringExtra("info") + "";
            num = getIntent().getIntExtra("commentnum", 0);
            collectnum = getIntent().getIntExtra("zancount", 0);
            strIsZan = getIntent().getStringExtra("iszan") + "";
//            tvNewsTitle.setText(titleText);
            tvCommentsNum.setText(num + "");
            tvTime.setText(InfoText + "");
            tvCollectNum.setText(collectnum + "");

            if (strIsZan.equals("0")) {
                ivCollect.setImageResource(R.drawable.icon_collection);
            } else {
                ivCollect.setImageResource(R.drawable.icon_collectioned);
            }
            ULog.e("cc", getIntent().getStringExtra("sid") + "   " + getIntent().getStringExtra("title"));
        }
        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(this));
        smartRefreshLayout.setRefreshFooter(new ClassicsFooter(this));
        smartRefreshLayout.setOnRefreshLoadmoreListener(this);
        smartRefreshLayout.autoRefresh(0);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new PartyCommunityCommentAdapter(this, dataList);
        recyclerView.setAdapter(adapter);
        etComment.setOnEditorActionListener(this);
        etComment.addTextChangedListener(new MyTextWatch(etComment));
        ivCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collectNews();
            }
        });
        lvAllview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etComment.clearFocus();
                hintKbTwo();

            }
        });
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
    }

    private List<OrganizationallCommentBean.DataBean.ComsBean> dataList = new ArrayList<>();
    PartyCommunityCommentAdapter adapter;
    private HttpRequestPresenter requestPresenter;
    private int mPage = 1;


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
        if (!EnterCheckUtil.getInstance().isLogin(true)) {
            return;
        }
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
                        ToastUtils.getInstance().showToast(CCMTComentActivity.this, msg);
                    }

                    if (jsonObject.has("data")) {
                        JSONObject myJect = jsonObject.getJSONObject("data");
                        if (myJect.has("iszan")) {
                            String iszan = myJect.optString("iszan");
                            if (iszan.equals("0")) {
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
                    ToastUtils.getInstance().showToast(CCMTComentActivity.this, "服务器异常~");
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(CCMTComentActivity.this, "网络异常~");
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
    private void releaseComments(String info) {
        if(!EnterCheckUtil.getInstance().isLogin(true)){
            return;
        }
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


                    ToastUtils.getInstance().showToast(CCMTComentActivity.this, msg);
                    etComment.clearFocus();
                    etComment.setText("");
                    Utils.showHideSoftInput(CCMTComentActivity.this, etComment, false);
                    if (TextUtils.equals(code, "0")) {
                        tvCommentsNum.setText(++num + "");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtils.getInstance().showToast(CCMTComentActivity.this, "服务器异常~");
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(CCMTComentActivity.this, "网络异常~");
            }
        });
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onNetworkError() {
        finishRefreshLoad();
        ToastUtils.getInstance().showToast(this, "网络连接异常，请检查网络设置~");
    }

    @Override
    public void onRefreshSuccess(String response) {
        smartRefreshLayout.finishRefresh(0);

        progressbar.setVisibility(View.GONE);
        try {
            JSONObject obj = null;
            obj = new JSONObject(response);
            if (obj.has("code")) {
                if (obj.getString("code").equals("0")) {

                    Gson gson = new Gson();
                    OrganizationallCommentBean searchResultBean = gson.fromJson(response, OrganizationallCommentBean.class);
                    if (searchResultBean.getData().getComs().size() > 0) {
                        dataList.clear();
                        dataList.addAll(searchResultBean.getData().getComs());
                    }


                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(CCMTComentActivity.this, "数据异常", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(CCMTComentActivity.this, "数据异常", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRefreshError() {

    }

    @Override
    public void onLoadMoreSuccess(String response) {
        smartRefreshLayout.finishLoadmore(0);

        try {
            JSONObject obj = null;
            obj = new JSONObject(response);
            if (obj.has("code")) {
                if (obj.getString("code").equals("0")) {

                    Gson gson = new Gson();
                    OrganizationallCommentBean searchResultBean = gson.fromJson(response, OrganizationallCommentBean.class);

                    if (searchResultBean.getData().getComs().size() > 0) {
                        dataList.addAll(searchResultBean.getData().getComs());
                    } else {
                        ToastUtils.getInstance().showToast(CCMTComentActivity.this, "没有更多了~");
                        return;
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(CCMTComentActivity.this, "数据异常", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(CCMTComentActivity.this, "数据异常", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onLoadMoreError() {
        ToastUtils.getInstance().showToast(this, "加载失败~");
        smartRefreshLayout.finishLoadmore(0);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_ccmtcomment;
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

    //此方法只是关闭软键盘
    private void hintKbTwo() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    @Override
    protected void initToolbar() {


        toolbar.setNavigationIcon(R.drawable.left_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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
                        ToastUtils.getInstance().showToast(CCMTComentActivity.this, "评论最多输入300个字！");
                        return;
                    }
                    break;
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    @Override
    public void onDestroy() {
        finishRefreshLoad();
        super.onDestroy();
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        mPage++;
        MyApplication App = (MyApplication) getApplicationContext();
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


        requestPresenter.doHttpData(Consts.BASE_URL + "/Said/public_saidcoms", Consts.REQUEST_METHOD_POST, Consts.REQUEST_LOADMORE, params);

    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mPage = 1;
        MyApplication App = (MyApplication) getApplicationContext();
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


        requestPresenter.doHttpData(Consts.BASE_URL + "/Said/public_saidcoms", Consts.REQUEST_METHOD_POST, Consts.REQUEST_REFRESH, params);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
