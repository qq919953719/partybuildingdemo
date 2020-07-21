package com.longhoo.net.manageservice.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.longhoo.net.MyApplication;
import com.longhoo.net.R;
import com.longhoo.net.mine.ui.LoginActivity;
import com.longhoo.net.manageservice.adapter.GridViewAdapter;
import com.longhoo.net.manageservice.bean.PTCConetntBean;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.DisplayUtil;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.Utils;
import com.longhoo.net.utils.httprequest.OkHttpCallback;
import com.longhoo.net.utils.httprequest.OkHttpUtil;
import com.longhoo.net.widget.CustomGridView;
import com.longhoo.net.widget.base.BaseActivity;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ${CC} on 2017/12/4.
 */

public class PTCConetntActivity extends BaseActivity implements EditText.OnEditorActionListener {

    String strSid = "";
    String strTitle = "";
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
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
    @BindView(R.id.bottom_container)
    LinearLayout bottomContainer;
    @BindView(R.id.line2)
    View line2;
    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;
    @BindView(R.id.tv_main_content)
    TextView tvMainContent;
    @BindView(R.id.item_grid_view)
    CustomGridView itemGridView;
    @BindView(R.id.info_container)
    ScrollView infoContainer;
    @BindView(R.id.lv_content)
    LinearLayout lvContent;
    @BindView(R.id.tv_collect_num)
    TextView tvCollectNum;
    private int num;
    private int collectnum;
    private String strIszan = "";

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //刷新评论和收藏
        if (requestCode == 100 && resultCode == 100) {
            if (data != null) {
                try {
                    num = Integer.parseInt(data.getStringExtra("new_comment_num"));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                tvCommentsNum.setText(num + "");


            }
        }
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
        if(!EnterCheckUtil.getInstance().isLogin(true)){
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
                        ToastUtils.getInstance().showToast(PTCConetntActivity.this, msg);
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
                    ToastUtils.getInstance().showToast(PTCConetntActivity.this, "服务器异常~");
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(PTCConetntActivity.this, "网络异常~");
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


                    ToastUtils.getInstance().showToast(PTCConetntActivity.this, msg);
                    etComment.clearFocus();
                    etComment.setText("");
                    Utils.showHideSoftInput(PTCConetntActivity.this, etComment, false);
                    if (TextUtils.equals(code, "0")) {
                        tvCommentsNum.setText(++num + "");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtils.getInstance().showToast(PTCConetntActivity.this, "服务器异常~");
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(PTCConetntActivity.this, "网络异常~");
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
                        ToastUtils.getInstance().showToast(PTCConetntActivity.this, "评论最多输入300个字！");
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
    protected int getContentId() {
        return R.layout.activity_ptccontent;
    }

    @Override
    protected void initViews() {
        Intent intent = getIntent();
        strSid = intent.getStringExtra("sid");
        GetPTCConetnt();


        etComment.setOnEditorActionListener(PTCConetntActivity.this);
        etComment.addTextChangedListener(new MyTextWatch(etComment));
        etComment.clearFocus();

        ivCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collectNews();
            }
        });
        lvContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etComment.clearFocus();
                hintKbTwo();

            }
        });
        llComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(PTCConetntActivity.this, CCMTComentActivity.class);
                intent.putExtra("sid", strSid);
                intent.putExtra("title", tvMainTitle.getText().toString().trim());
                intent.putExtra("info", tvMainContent.getText().toString().trim());
                intent.putExtra("commentnum", Integer.parseInt(tvCommentsNum.getText().toString().trim()));

                intent.putExtra("iszan", strIszan);
                intent.putExtra("zancount", Integer.parseInt(tvCollectNum.getText().toString().trim()));
                startActivity(intent);
            }
        });
    }

    void GetPTCConetnt() {
        Map<String, String> map = new HashMap<>();
        map.put("sid", String.valueOf(strSid));
        map.put("uid",EnterCheckUtil.getInstance().getUid_Token()[0]);
        map.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        OkHttpUtil.getInstance().doAsyncPost(Consts.BASE_URL + "/Said/public_saidinfo", map, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if (obj.has("code")) {
                        if (obj.getString("code").equals("0")) {
                            Gson gson = new Gson();
                            final PTCConetntBean mPTCConetntBean = gson.fromJson(response, PTCConetntBean.class);
                            num = Integer.parseInt(mPTCConetntBean.getData().getSaid().getCom());
                            tvMainTitle.setText(mPTCConetntBean.getData().getSaid().getTitle());
                            tvMainContent.setText(mPTCConetntBean.getData().getSaid().getInfo());
                            tvCommentsNum.setText(mPTCConetntBean.getData().getSaid().getCom());
//                            iszan 是否已点赞  0否    大于0 是

                            strIszan = mPTCConetntBean.getData().getSaid().getIszan();
                            if (mPTCConetntBean.getData().getSaid().getIszan().equals("0")) {
                                ivCollect.setImageResource(R.drawable.icon_collection);
                            } else {
                                ivCollect.setImageResource(R.drawable.icon_collectioned);
                            }

                            tvCollectNum.setText(mPTCConetntBean.getData().getSaid().getZan() + "");
                            //加载网格图片
                            List<String> picList = mPTCConetntBean.getData().getSaid().getPicbig();
                            GridViewAdapter adapter = new GridViewAdapter(PTCConetntActivity.this, picList);
                            int width = 0;
                            int height = 0;
                            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) itemGridView.getLayoutParams();
                            if (picList.size() <= 0) {
                                itemGridView.setVisibility(View.GONE);
                            } else {

                                if (picList.size() == 1) {
                                    itemGridView.setVisibility(View.VISIBLE);
                                    itemGridView.setNumColumns(1);
                                    width = Utils.getDeviceSize(PTCConetntActivity.this).x - DisplayUtil.dp2px(PTCConetntActivity.this, 30);
                                    lp.width = RecyclerView.LayoutParams.MATCH_PARENT;

                                } else if (picList.size() == 2 || picList.size() == 4) {
                                    itemGridView.setVisibility(View.VISIBLE);
                                    itemGridView.setNumColumns(2);
                                    width = (Utils.getDeviceSize(PTCConetntActivity.this).x - DisplayUtil.dp2px(PTCConetntActivity.this, 38)) / 3;
                                    lp.width = width * 2 + DisplayUtil.dp2px(PTCConetntActivity.this, 4);
                                } else {
                                    itemGridView.setVisibility(View.VISIBLE);
                                    itemGridView.setNumColumns(3);
                                    width = (Utils.getDeviceSize(PTCConetntActivity.this).x - DisplayUtil.dp2px(PTCConetntActivity.this, 38)) / 3;
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
                                                photoList.add(new LocalMedia(photo, 0, PictureMimeType.ofImage(), ""));
                                            }
                                            PictureSelector.create(PTCConetntActivity.this).externalPicturePreview(position, photoList);
                                        }
                                    }
                                });
                            }


                        } else {
                            Toast.makeText(PTCConetntActivity.this, "数据异常", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(PTCConetntActivity.this, "数据异常", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(PTCConetntActivity.this, "网络错误~");

            }
        });
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
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        tvTitle.setText("详情");
        toolbar.setNavigationIcon(R.drawable.left_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
