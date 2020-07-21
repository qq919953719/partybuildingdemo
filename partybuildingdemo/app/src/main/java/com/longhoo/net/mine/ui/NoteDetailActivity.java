package com.longhoo.net.mine.ui;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.longhoo.net.MyApplication;
import com.longhoo.net.R;
import com.longhoo.net.mine.adapter.NoteDetailImageAdapter;
import com.longhoo.net.mine.bean.NoteAddShareBean;
import com.longhoo.net.mine.bean.NoteDetailBean;
import com.longhoo.net.mine.bean.NoteShareOrgBean;
import com.longhoo.net.mine.bean.SharePeopleListBean;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.MessageEvent;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.utils.Utils;
import com.longhoo.net.utils.WriteReadListUtil;
import com.longhoo.net.utils.httprequest.HttpRequestPresenter;
import com.longhoo.net.utils.httprequest.HttpRequestView;
import com.longhoo.net.utils.httprequest.OkHttpCallback;
import com.longhoo.net.utils.httprequest.OkHttpUtil;
import com.longhoo.net.widget.MyDialog;
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

public class NoteDetailActivity extends BaseActivity implements HttpRequestView {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_edit)
    ImageView ivEdit;
    @BindView(R.id.iv_del)
    ImageView ivDel;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.fl_progress)
    FrameLayout flProgress;
    @BindView(R.id.tv_note_title)
    TextView tvNoteTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.tv_share)
    TextView tvShare;
    private HttpRequestPresenter requestPresenter;
    private String url = Consts.BASE_URL + "/Application/biJiDetailById";
    private String noteId = "";
    private int noteType;
    private NoteDetailImageAdapter adapter;
    private List<String> picList = new ArrayList<>();

    @Override
    protected int getContentId() {
        return R.layout.activity_note_detail;
    }

    @Override
    protected void initViews() {
        EventBus.getDefault().register(this);
        //点击软键盘外部，收起软键盘
        ((EditText) findViewById(R.id.edt_comment)).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager manager = ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE));
                    if (manager != null)
                        manager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });
        if (getIntent() != null) {
            noteId = getIntent().getStringExtra("note_id");
            noteType = getIntent().getIntExtra("note_type", 0);
        }

        getShareOrg();
        tvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NoteListActivity.type == 0) {
                    getSharePeople();
                    //私有笔记
                    return;
                } else if (NoteListActivity.type == 1) {
                    //评论
                    getAddComment();
                    return;
                }
                MyDialog dialog = new MyDialog(NoteDetailActivity.this, R.style.MyDialogStyle);
                String[] arrays = new String[shareOrgList.size()];
                for (int i = 0; i < shareOrgList.size(); i++) {
                    arrays[i] = shareOrgList.get(i).getName();
                }
                dialog.setMyItems(arrays);
                dialog.setMyDialogItemClickListener(new MyDialog.MyDialogItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        curShareId = shareOrgList.get(position).getOid();
                        addShare();
                    }
                });
                dialog.show();


            }
        });
        adapter = new NoteDetailImageAdapter(this, picList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);
        requestPresenter = new HttpRequestPresenter(this, this);
        Map<String, String> params = new HashMap<>();
        params.put("uid", EnterCheckUtil.getInstance().getUid_Token()[0]);
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("id", noteId);
        requestPresenter.doHttpData(url, Consts.REQUEST_METHOD_POST, Consts.REQUEST_REFRESH, params);
        if (NoteListActivity.type == 1) {
            ((EditText) findViewById(R.id.edt_comment)).setVisibility(View.VISIBLE);
            findViewById(R.id.tv_tip_comment).setVisibility(View.VISIBLE);
            tvShare.setText("提交");
            //共享笔记
        }
        if (NoteListActivity.type == 0) {
            tvShare.setText("分享我的笔记");
            //私有笔记
        }
        if (NoteListActivity.type == 3) {

            //f分享笔记
        }
    }

    @Override
    protected void initToolbar() {
        tvTitle.setText("笔记详情");
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.left_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onNetworkError() {
        ToastUtils.getInstance().showToast(this, "网络异常~");
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onRefreshSuccess(String response) {
        ULog.e("笔记详情：" + response);
        Gson gson = new Gson();
        try {
            NoteDetailBean detailBean = gson.fromJson(response, NoteDetailBean.class);
            String code = detailBean.getCode();
            if (!TextUtils.equals(code, "0")) {
                ToastUtils.getInstance().showToast(this, "获取数据失败~");
                return;
            }
            String title = detailBean.getData().getInfo().getTitle();
            String content = detailBean.getData().getInfo().getContent();
            String time = detailBean.getData().getInfo().getTime() + "";
            List<String> tempList = detailBean.getData().getInfo().getUploads();
            tvNoteTitle.setText(title + "");
            tvContent.setText(content + "");
            tvTime.setText(Utils.getDataTime(time, "yyyy-MM-dd HH:mm"));
            //图片
            if (tempList.size() > 0) {
                picList.clear();
                picList.addAll(tempList);
                adapter.notifyDataSetChanged();
                recyclerView.setVisibility(View.VISIBLE);
            } else {
                recyclerView.setVisibility(View.GONE);
            }
            ((LinearLayout) findViewById(R.id.lv_all_comment)).removeAllViews();
            for (int i = 0; i < detailBean.getData().getInfo().getComments().size(); i++) {
                LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.item_comment, null);
                ((TextView) (view.findViewById(R.id.tv_item_comment))).setText(detailBean.getData().
                        getInfo().getComments().get(i).getRealname() + ":" +
                        detailBean.getData().getInfo().getComments().get(i).getContent());
                ((LinearLayout) findViewById(R.id.lv_all_comment)).addView(view);
            }
            if (noteType == NoteListActivity.TAG_MY_NOTE) {
                tvShare.setVisibility(View.VISIBLE);
                if ((detailBean.getData().getInfo().getUid() + "").equals(EnterCheckUtil.getInstance().getUid_Token()[0])) {
                    ivDel.setVisibility(View.VISIBLE);
                    ivEdit.setVisibility(View.VISIBLE);
                } else {
                    ivDel.setVisibility(View.GONE);
                    ivEdit.setVisibility(View.GONE);
                }

            } else {
                tvShare.setVisibility(View.GONE);
                ivDel.setVisibility(View.GONE);
                ivEdit.setVisibility(View.GONE);
            }
            flProgress.setVisibility(View.GONE);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            ToastUtils.getInstance().showToast(this, "服务器异常~");
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRefreshError() {
        ToastUtils.getInstance().showToast(this, "网络异常~");
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onLoadMoreSuccess(String response) {

    }

    @Override
    public void onLoadMoreError() {

    }

    /**
     * 获取分享笔记的范围
     */
    private String curShareId = "";
    private List<NoteShareOrgBean.DataBean.OrgBean> shareOrgList = new ArrayList<>();

    private void getShareOrg() {
        String url = Consts.BASE_URL + "/Application/getShareOrg";
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                Gson gson = new Gson();
                try {
                    NoteShareOrgBean shareOrgBean = gson.fromJson(response, NoteShareOrgBean.class);
                    String code = shareOrgBean.getCode();
                    if (!TextUtils.equals(code, "0")) {
                        ToastUtils.getInstance().showToast(NoteDetailActivity.this, "获取数据失败~");
                        return;
                    }
                    List<NoteShareOrgBean.DataBean.OrgBean> list = shareOrgBean.getData().getOrg();
                    if (list != null && list.size() > 0) {
                        shareOrgList.clear();
                        shareOrgList.addAll(list);
                    }
                } catch (JsonSyntaxException e) {

                }
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });
    }


    /**
     * 添加评论
     */
    private void getAddComment() {
        if (TextUtils.isEmpty(((EditText) findViewById(R.id.edt_comment)).getText().toString().trim())) {
            ToastUtils.getInstance().showToast(NoteDetailActivity.this, "请填写评论内容~");
            return;
        }
        final String url = Consts.BASE_URL + "/Application/add_biji_comment";
        final Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("bid", noteId);
        params.put("content", ((EditText) findViewById(R.id.edt_comment)).getText().toString().trim());
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                Gson gson = new Gson();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.optString("code");
                    String msg = jsonObject.optString("msg");
                    ToastUtils.getInstance().showToast(NoteDetailActivity.this, msg);
                    if (!TextUtils.equals(code, "0")) {
                        return;
                    }
                    MyApplication app = (MyApplication) NoteDetailActivity.this.getApplicationContext();
                    if (app.getmLoginBean() == null) {
                        ToastUtils.getInstance().showToast(NoteDetailActivity.this, "登录过期，请重新登陆！");
                        return;
                    }
                    LayoutInflater inflater = (LayoutInflater) NoteDetailActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View view = inflater.inflate(R.layout.item_comment, null);
                    ((TextView) (view.findViewById(R.id.tv_item_comment))).setText(app.mLoginBean.getData().getRealname() + ":" + ((EditText) findViewById(R.id.edt_comment)).getText().toString().trim());

                    ((LinearLayout) findViewById(R.id.lv_all_comment)).addView(view);
                    ((EditText) findViewById(R.id.edt_comment)).setText("");
                    // requestPresenter.doHttpData(url, Consts.REQUEST_METHOD_POST, Consts.REQUEST_REFRESH, params);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });
    }

    /**
     * 分享笔记
     */
    private void addShare() {
        String url = Consts.BASE_URL + "/Application/addShare";
        Map<String, String> params = new HashMap<>();
        params.put("oid", curShareId);
        params.put("bid", noteId);
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                Gson gson = new Gson();
                try {
                    NoteAddShareBean addShareBean = gson.fromJson(response, NoteAddShareBean.class);
                    String code = addShareBean.getCode();
                    String msg = addShareBean.getMsg();
                    if (!TextUtils.equals(code, "0")) {
                        ToastUtils.getInstance().showToast(NoteDetailActivity.this, msg);
                        return;
                    }
                    ToastUtils.getInstance().showToast(NoteDetailActivity.this, msg);
                    MessageEvent event = new MessageEvent();
                    event.msgType = MessageEvent.MSG_SHARE_NOTE;
                    EventBus.getDefault().post(event);
                    finish();
                } catch (JsonSyntaxException e) {
                    ToastUtils.getInstance().showToast(NoteDetailActivity.this, "解析异常~");
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(NoteDetailActivity.this, "网络错误~");
            }
        });
    }

    @OnClick({R.id.iv_del, R.id.iv_edit})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_del:
                final MyDialog dialog = new MyDialog(this, R.style.MyDialogStyle);
                dialog.setMyText("确定删除？");
                dialog.setMyDialogClickListener(new MyDialog.MyDialogClickListener() {
                    @Override
                    public void onClick(View view, TextView dialogEdit) {
                        if (view.getId() == R.id.dialog_ok) {
                            delNote();
                        } else if (view.getId() == R.id.dialog_cancle) {
                            dialog.dismiss();
                        }
                    }
                });
                dialog.show();
                break;
            case R.id.iv_edit:
                NoteEditActivity.goEditNote(this, noteId);
                break;
        }
    }

    private void delNote() {
        String url = Consts.BASE_URL + "/Application/deleteBiJiById";
        Map<String, String> params = new HashMap<>();
        params.put("id", noteId);
        params.put("uid", EnterCheckUtil.getInstance().getUid_Token()[0]);
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.optString("code");
                    String msg = jsonObject.optString("msg");
                    if (TextUtils.equals(code, "0")) {
                        //成功,删除该条数据
                        ToastUtils.getInstance().showToast(NoteDetailActivity.this, "删除成功~");
                        finish();
                        MessageEvent event = new MessageEvent();
                        event.msgType = MessageEvent.MSG_DEL_NOTE;
                        EventBus.getDefault().post(event);
                    } else {
                        ToastUtils.getInstance().showToast(NoteDetailActivity.this, msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtils.getInstance().showToast(NoteDetailActivity.this, "服务器异常~");
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(NoteDetailActivity.this, "网络异常~");
            }
        });
    }

    @Override
    protected void onDestroy() {
        WriteReadListUtil mWriteReadListUtil = new WriteReadListUtil();
        mWriteReadListUtil.UpLoadWriteInfo(this, "4", noteId);
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    void getNoteType(final SharePeopleListBean mSharePeopleListBean) {
        MyDialog dialog = new MyDialog(NoteDetailActivity.this, R.style.MyDialogStyle);
        List<String> stringList = new ArrayList<String>();
        for (int i = 0; i < mSharePeopleListBean.getData().getPeople().size(); i++) {
            stringList.add(mSharePeopleListBean.getData().getPeople().get(i).getName());
        }
        String[] arrays = new String[stringList.size()];
        stringList.toArray(arrays);
        dialog.setMyItems(arrays);
        dialog.setMyDialogItemClickListener(new MyDialog.MyDialogItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gettoPeople(mSharePeopleListBean.getData().getPeople().get(position));
            }
        });
        dialog.show();

    }

    /**
     * 获取可以分享的人员列表
     */
    private void getSharePeople() {
        String url = Consts.BASE_URL + "/Application/getSharePeo";
        Map<String, String> params = new HashMap<>();
        params.put("uid", EnterCheckUtil.getInstance().getUid_Token()[0]);
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                Gson gson = new Gson();
                try {
                    SharePeopleListBean detailBean = gson.fromJson(response, SharePeopleListBean.class);
                    String code = detailBean.getCode();
                    if (!TextUtils.equals(code, "0")) {
                        ToastUtils.getInstance().showToast(NoteDetailActivity.this, "获取数据失败~");
                        return;
                    }
                    getNoteType(detailBean);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    ToastUtils.getInstance().showToast(NoteDetailActivity.this, "服务器异常~");
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(NoteDetailActivity.this, "网络异常~");
            }
        });
    }

    class ResultBean {


        /**
         * code : 0
         * msg : 分享成功
         * data : {}
         */

        private String code;
        private String msg;
        private DataBean data;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public class DataBean {
        }
    }

    /**
     * 分享给某人
     */
    private void gettoPeople(SharePeopleListBean.DataBean.PeopleBean mdetailBean) {
        String url = Consts.BASE_URL + "/Application/addSharePri";
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("bid", noteId);
        params.put("type", "1");
        params.put("uid", mdetailBean.getUid() + "");
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                Gson gson = new Gson();
                try {
                    ResultBean detailBean = gson.fromJson(response, ResultBean.class);
                    String code = detailBean.getCode();
                    if (!TextUtils.equals(code, "0")) {
                        ToastUtils.getInstance().showToast(NoteDetailActivity.this, "获取数据失败~");
                        return;
                    } else {
                        ToastUtils.getInstance().showToast(NoteDetailActivity.this, "分享成功~");
                        return;
                    }
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    ToastUtils.getInstance().showToast(NoteDetailActivity.this, "服务器异常~");
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(NoteDetailActivity.this, "网络异常~");
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        int msgType = event.msgType;
        switch (msgType) {
            case MessageEvent.MSG_EDIT_NOTE:
//                Map<String, String> params = new HashMap<>();
//                params.put("uid", EnterCheckUtil.getInstance().getUid_Token()[0]);
//                params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
//                params.put("id", noteId);
//                requestPresenter.doHttpData(url, Consts.REQUEST_METHOD_POST, Consts.REQUEST_REFRESH, params);

                event.msgType = MessageEvent.MSG_ADD_NOTE;
                EventBus.getDefault().post(event);
                finish();
                break;
        }
    }
}
