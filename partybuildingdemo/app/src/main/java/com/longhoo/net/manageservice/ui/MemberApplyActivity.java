package com.longhoo.net.manageservice.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;
import com.luck.picture.lib.entity.LocalMedia;
import com.longhoo.net.R;
import com.longhoo.net.manageservice.adapter.PostGridAdapter;
import com.longhoo.net.manageservice.bean.PartListBean;
import com.longhoo.net.manageservice.bean.SignBan;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.DisplayUtil;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.MessageEvent;
import com.longhoo.net.utils.PathUtils;
import com.longhoo.net.utils.RealPathFromUriUtils;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.utils.httprequest.OkHttpCallback;
import com.longhoo.net.utils.httprequest.OkHttpUtil;
import com.longhoo.net.widget.MyDialog;
import com.longhoo.net.widget.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static butterknife.internal.Utils.arrayOf;

public class MemberApplyActivity extends BaseActivity implements PostGridAdapter.onAddPicClickListener, PostGridAdapter.OnItemClickListener {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_membername)
    TextView tvMembername;
    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.edt_identity_number)
    EditText edtIdentityNumber;
    @BindView(R.id.edt_reson)
    EditText edtReson;
    @BindView(R.id.edt_party)
    TextView edtParty;
    @BindView(R.id.edt_recomend_person)
    EditText edtRecomendPerson;
    @BindView(R.id.edt_unit)
    EditText edtUnit;
    @BindView(R.id.edt_adress)
    EditText edtAdress;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.scroll_view)
    ScrollView scrollView;
    @BindView(R.id.tv_publishphone)
    TextView tvPublishphone;
    @BindView(R.id.tv_pic_length)
    TextView tvPicLength;
    @BindView(R.id.tv_uploadfile)
    TextView tvUploadfile;


    @BindView(R.id.tv_recomand)
    TextView tvRecomand;
    @BindView(R.id.in_party_time)
    LinearLayout inPartyTime;
    @BindView(R.id.lv_confirm_activist_time)
    LinearLayout lvConfirmActivistTime;
    @BindView(R.id.lv_confirm_obj_time)
    LinearLayout lvConfirmObjTime;
    @BindView(R.id.lv_confirm_ready_time)
    LinearLayout lvConfirmReadyTime;
    @BindView(R.id.lv_birthday)
    LinearLayout lvBirthday;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.iv_date)
    ImageView ivDate;
    @BindView(R.id.tv_into_date)
    TextView tvIntoDate;
    @BindView(R.id.iv_into_date)
    ImageView ivIntoDate;
    @BindView(R.id.tv_activity_date)
    TextView tvActivityDate;
    @BindView(R.id.iv_activity_date)
    ImageView ivActivityDate;
    @BindView(R.id.tv_object_date)
    TextView tvObjectDate;
    @BindView(R.id.iv_object_date)
    ImageView ivObjectDate;
    @BindView(R.id.tv_read_date)
    TextView tvReadDate;
    @BindView(R.id.iv_read_date)
    ImageView ivReadDate;
    private PostGridAdapter adapter;
    //private List<LocalMedia> picList = new ArrayList<>();
    private List<LocalMedia> selectList = new ArrayList<>();
    private List<SignBan> signList1;
    private List<SignBan> signList2;
    int miReduceHeigh = 0;
    private int REQUEST_CODE_CHOOSE24 = 24;
    String strTid = "";

    //申请入党页面
    @Override
    protected int getContentId() {
        return R.layout.activity_member_apply;
    }


    @Override
    protected void initViews() {
        strTid = getIntent().getStringExtra("tid");
        edtIdentityNumber.setRawInputType(InputType.TYPE_CLASS_NUMBER);
        getPartData();
        mFileInfo.mFile = null;
        mFileInfo.strFileName = "";
        GridLayoutManager manager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, DisplayUtil.dp2px(this, 4), false));
        adapter = new PostGridAdapter(this, this);
        adapter.setList(selectList);
        adapter.setSelectMax(6);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        tvUploadfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(
                        MemberApplyActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                        != PackageManager.PERMISSION_GRANTED
                ) {
                    // 未授权，此时需要申请权限
                    ActivityCompat.requestPermissions(
                            MemberApplyActivity.this,
                            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                            123123
                    );

                    return;
                }
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");
                //intent.setType("file/*");
                startActivityForResult(intent, REQUEST_CODE_CHOOSE24);


            }
        });
        tvPublishphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpLoadData();
            }
        });
        ivIntoDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickData(tvIntoDate);
            }
        });
        selectCategrovy(strTid);
        ivDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickData(tvDate);
            }
        });
        ivActivityDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickData(tvActivityDate);
            }
        });
        ivObjectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickData(tvObjectDate);
            }
        });


        ivReadDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickData(tvReadDate);
            }
        });
    }


    private void pickData(final TextView tvDate) {
        DatePickDialog dialog = new DatePickDialog(this);
        //设置上下年分限制
        dialog.setYearLimt(90);
        //设置标题
        dialog.setTitle("选择时间");
        //设置类型
        dialog.setType(DateType.TYPE_YMD);
        //设置消息体的显示格式，日期格式
        dialog.setMessageFormat("yyyy-MM-dd");
        //设置选择回调
        dialog.setOnChangeLisener(null);
        //设置点击确定按钮回调
        dialog.setOnSureLisener(new OnSureLisener() {
            @Override
            public void onSure(Date date) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                tvDate.setText(simpleDateFormat.format(date));

            }
        });
        dialog.show();
    }

    void selectCategrovy(String id) {

        if (id.equals("1")) {
            //预备党员
            tvDate.setEnabled(true);
            tvIntoDate.setEnabled(true);
            tvActivityDate.setVisibility(View.GONE);
            ivActivityDate.setVisibility(View.GONE);
            tvObjectDate.setVisibility(View.GONE);
            ivObjectDate.setVisibility(View.GONE);
            tvReadDate.setVisibility(View.GONE);
            ivReadDate.setVisibility(View.GONE);

            lvConfirmActivistTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtils.getInstance().showToast(MemberApplyActivity.this, "当前阶段无需填写！");

                }
            });
            lvConfirmObjTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtils.getInstance().showToast(MemberApplyActivity.this, "当前阶段无需填写！");

                }
            });
            lvConfirmReadyTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtils.getInstance().showToast(MemberApplyActivity.this, "当前阶段无需填写！");
                }
            });
            tvRecomand.setText("入党联系人");
        }
        if (id.equals("2")) {
            //积极分子

            tvDate.setEnabled(true);
            tvIntoDate.setEnabled(true);
            tvActivityDate.setEnabled(true);
            tvObjectDate.setEnabled(true);
            tvObjectDate.setVisibility(View.GONE);
            ivObjectDate.setVisibility(View.GONE);
            tvReadDate.setVisibility(View.GONE);
            ivReadDate.setVisibility(View.GONE);
            lvConfirmObjTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtils.getInstance().showToast(MemberApplyActivity.this, "当前阶段无需填写！");

                }
            });
            lvConfirmReadyTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtils.getInstance().showToast(MemberApplyActivity.this, "当前阶段无需填写！");
                }
            });

        }
        if (id.equals("3")) {
            //发展对象
            tvDate.setEnabled(true);
            tvIntoDate.setEnabled(true);
            tvActivityDate.setEnabled(true);
            tvReadDate.setVisibility(View.GONE);
            ivReadDate.setVisibility(View.GONE);
            lvConfirmReadyTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtils.getInstance().showToast(MemberApplyActivity.this, "当前阶段无需填写！");

                }
            });
        }
        if (id.equals("4")) {
            //申请入党
            tvDate.setEnabled(true);
            tvIntoDate.setEnabled(true);
            tvActivityDate.setEnabled(true);
            tvObjectDate.setEnabled(true);
            tvReadDate.setEnabled(true);
            tvRecomand.setText("入党介绍人");
        }

    }


    @Override
    protected void initToolbar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        tvTitle.setText("申请");
        toolbar.setNavigationIcon(R.drawable.left_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),
                            0);
                }
                onBackPressed();
            }
        });
    }

    private String[] zhibuArrays;
    private MyDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    PartListBean Partbean = null;
    String strOid = "";

    void showZhiBuList() {
        zhibuArrays = new String[Partbean.getData().size()];
        for (int i = 0; i < Partbean.getData().size(); i++) {
            zhibuArrays[i] = Partbean.getData().get(i).getName();
        }

        dialog = new MyDialog(this, R.style.MyDialogStyle);
        dialog.setMyItems(zhibuArrays);
        dialog.setMyDialogItemClickListener(new MyDialog.MyDialogItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                edtParty.setText(Partbean.getData().get(position).getName());
                strOid = Partbean.getData().get(position).getOid() + "";

            }
        });
        dialog.show();


    }

    void getPartData() {

        String url = Consts.BASE_URL + "/apply/public_orglist";
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);

        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                Gson gson = new Gson();
                String msg = "";
                try {
                    Partbean = gson.fromJson(response, PartListBean.class);
                    edtParty.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showZhiBuList();
                        }
                    });
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(MemberApplyActivity.this, "网络异常~");
            }
        });


    }

    String title = "";

    /**
     * 上传申请人信息
     */
    private void UpLoadData() {

        if (TextUtils.isEmpty(strTid.toString().trim())) {
            ToastUtils.getInstance().showToast(this, "tid不能为空！");
            return;
        }

        if (TextUtils.isEmpty(edtName.getText().toString().trim())) {
            ToastUtils.getInstance().showToast(this, "姓名不能为空！");
            return;
        }
        if (TextUtils.isEmpty(edtPhone.getText().toString().trim())) {
            ToastUtils.getInstance().showToast(this, "手机号码不能为空！");
            return;
        }
        if (edtPhone.getText().toString().trim().length() != 11) {
            ToastUtils.getInstance().showToast(this, "手机号码不正确！");
            return;
        }
        if (TextUtils.isEmpty(edtIdentityNumber.getText().toString().trim())) {
            ToastUtils.getInstance().showToast(this, "身份证号不能为空！");
            return;
        }
        if (edtIdentityNumber.getText().length() != 15 && edtIdentityNumber.getText().length() != 18) {
            ToastUtils.getInstance().showToast(this, "身份证号长度不对！");
            return;
        }
        if (TextUtils.isEmpty(edtReson.getText().toString().trim())) {
            ToastUtils.getInstance().showToast(this, "申请理由不能为空！");
            return;
        }
        if (TextUtils.isEmpty(strOid.toString().trim())) {
            ToastUtils.getInstance().showToast(this, "所属党支部不能为空！");
            return;
        }
        if (TextUtils.isEmpty(edtParty.getText().toString().trim())) {
            ToastUtils.getInstance().showToast(this, "所属党支部不能为空！");
            return;
        }
        if (TextUtils.isEmpty(edtRecomendPerson.getText().toString().trim())) {
            ToastUtils.getInstance().showToast(this, "推荐人不能为空！");
            return;
        }
        if (TextUtils.isEmpty(edtUnit.getText().toString().trim())) {
            ToastUtils.getInstance().showToast(this, "所属单位不能为空！");
            return;
        }
        if (TextUtils.isEmpty(edtAdress.getText().toString().trim())) {
            ToastUtils.getInstance().showToast(this, "居住地址不能为空！");
            return;
        }
        if (strTid.equals("4")) {
            //预备党员
            if (TextUtils.isEmpty(tvDate.getText().toString().trim())) {
                ToastUtils.getInstance().showToast(MemberApplyActivity.this, "请填写出生日期！");
                return;
            }

            if (TextUtils.isEmpty(tvIntoDate.getText().toString().trim())) {
                ToastUtils.getInstance().showToast(MemberApplyActivity.this, "请填写入党日期！");
                return;
            }

        }
        if (strTid.equals("3")) {
            //发展对象

            if (TextUtils.isEmpty(tvDate.getText().toString().trim())) {
                ToastUtils.getInstance().showToast(MemberApplyActivity.this, "请填写出生日期！");
                return;
            }
            if (TextUtils.isEmpty(tvIntoDate.getText().toString().trim())) {
                ToastUtils.getInstance().showToast(MemberApplyActivity.this, "请填写入党日期！");
                return;
            }
            if (TextUtils.isEmpty(tvActivityDate.getText().toString().trim())) {
                ToastUtils.getInstance().showToast(MemberApplyActivity.this, "请填写确认积极分子日期！");
                return;
            }
            if (TextUtils.isEmpty(tvObjectDate.getText().toString().trim())) {
                ToastUtils.getInstance().showToast(MemberApplyActivity.this, "请填写确认发展对象日期！");
                return;
            }

        }
        if (strTid.equals("2")) {
            //积极分子
            if (TextUtils.isEmpty(tvDate.getText().toString().trim())) {
                ToastUtils.getInstance().showToast(MemberApplyActivity.this, "请填写出生日期！");
                return;
            }
            if (TextUtils.isEmpty(tvIntoDate.getText().toString().trim())) {
                ToastUtils.getInstance().showToast(MemberApplyActivity.this, "请填写入党日期！");
                return;
            }
            if (TextUtils.isEmpty(tvActivityDate.getText().toString().trim())) {
                ToastUtils.getInstance().showToast(MemberApplyActivity.this, "请填写确认积极分子日期！");
                return;
            }

        }
        if (strTid.equals("1")) {
            //申请入党
            if (TextUtils.isEmpty(tvDate.getText().toString().trim())) {
                ToastUtils.getInstance().showToast(MemberApplyActivity.this, "请填写出生日期！");
                return;
            }
            if (TextUtils.isEmpty(tvIntoDate.getText().toString().trim())) {
                ToastUtils.getInstance().showToast(MemberApplyActivity.this, "请填写入党日期！");
                return;
            }
            if (TextUtils.isEmpty(tvActivityDate.getText().toString().trim())) {
                ToastUtils.getInstance().showToast(MemberApplyActivity.this, "请填写确认积极分子日期！");
                return;
            }
            if (TextUtils.isEmpty(tvObjectDate.getText().toString().trim())) {
                ToastUtils.getInstance().showToast(MemberApplyActivity.this, "请填写确认发展对象日期！");
                return;
            }
            if (TextUtils.isEmpty(tvReadDate.getText().toString().trim())) {
                ToastUtils.getInstance().showToast(MemberApplyActivity.this, "请填写成为预备党员日期！");
                return;
            }
        }

//        birthday  出生年月
//        in_party_time   入党申请日期
//        confirm_activist_time   确定积极分子日期
//        confirm_obj_time     确定发展对象日期
//        confirm_ready_time    成为预备党员日期


        List<File> list = new ArrayList<>();
        if (selectList.size() > 0) {
            for (LocalMedia media : selectList) {
                String path = "";
                if (media.isCut() && !media.isCompressed()) {
                    // 裁剪过
                    path = media.getCutPath();
                    ULog.e("cc", "裁剪过" + path);
                } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
                    // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                    path = media.getCompressPath();
                    ULog.e("cc", "压缩过" + path);
                } else {
                    // 原图
                    path = media.getPath();
                    ULog.e("cc", "原图" + path);
                }
                File file = new File(path);
                ULog.e("cc", path);
                list.add(file);
            }
        }
        tvPublishphone.setText("发布...");
        tvPublishphone.setEnabled(false);
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("name", edtName.getText().toString().trim());
        params.put("mobile", edtPhone.getText().toString().trim());
        params.put("id_number", edtIdentityNumber.getText().toString().trim());
        params.put("reason", edtReson.getText().toString().trim());
        params.put("oid", strOid);
        params.put("tid", strTid);
        params.put("recommender", edtRecomendPerson.getText().toString().trim());
        params.put("company", edtUnit.getText().toString().trim());
        params.put("addr", edtAdress.getText().toString().trim());


        if (strTid.equals("4")) {
            //预备党员


            params.put("birthday", tvDate.getText().toString().trim());
            params.put("in_party_time", tvIntoDate.getText().toString().trim());


        }
        if (strTid.equals("3")) {
            //发展对象
            params.put("birthday", tvDate.getText().toString().trim());
            params.put("in_party_time", tvIntoDate.getText().toString().trim());
            params.put("confirm_activist_time", tvActivityDate.getText().toString().trim());
            params.put("confirm_obj_time", tvObjectDate.getText().toString().trim());

        }
        if (strTid.equals("2")) {
            //积极分子
            if (TextUtils.isEmpty(tvDate.getText().toString().trim())) {
                ToastUtils.getInstance().showToast(MemberApplyActivity.this, "请填写出生日期！");
                return;
            }
            if (TextUtils.isEmpty(tvIntoDate.getText().toString().trim())) {
                ToastUtils.getInstance().showToast(MemberApplyActivity.this, "请填写入党日期！");
                return;
            }
            if (TextUtils.isEmpty(tvActivityDate.getText().toString().trim())) {
                ToastUtils.getInstance().showToast(MemberApplyActivity.this, "请填写确认积极分子日期！");
                return;
            }
            params.put("birthday", tvDate.getText().toString().trim());
            params.put("in_party_time", tvIntoDate.getText().toString().trim());
            params.put("confirm_activist_time", tvActivityDate.getText().toString().trim());

        }
        if (strTid.equals("1")) {
            //申请入党
            params.put("birthday", tvDate.getText().toString().trim());
            params.put("in_party_time", tvIntoDate.getText().toString().trim());
            params.put("confirm_activist_time", tvActivityDate.getText().toString().trim());
            params.put("confirm_obj_time", tvObjectDate.getText().toString().trim());
            params.put("confirm_ready_time", tvReadDate.getText().toString().trim());
        }


        String url = Consts.BASE_URL + "/apply/apply_set";

        OkHttpUtil.getInstance().doAsyncMultiUpload3(url, params, mFileInfo.mFile, list, "1", new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                ULog.e("cc", "活菜单：" + response);
                tvPublishphone.setText("发布");
                tvPublishphone.setEnabled(true);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    ToastUtils.getInstance().showToast(MemberApplyActivity.this, jsonObject.optString("msg"));
                    if (TextUtils.equals(jsonObject.optString("code"), "0")) {
                        //发布成功
                        MessageEvent event = new MessageEvent();
                        event.msgType = MessageEvent.MSG_FRESH_STUDY_CLASS;
                        EventBus.getDefault().post(event);
                        finish();
                    } else if (TextUtils.equals(jsonObject.optString("code"), "3")) {
                        //待审核
                        MessageEvent event = new MessageEvent();
                        event.msgType = MessageEvent.MSG_FRESH_STUDY_CLASS;
                        EventBus.getDefault().post(event);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    tvPublishphone.setText("发布");
                    tvPublishphone.setEnabled(true);
                    ToastUtils.getInstance().showToast(MemberApplyActivity.this, "服务器异常~");
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                tvPublishphone.setText("发布");
                tvPublishphone.setEnabled(true);
                ToastUtils.getInstance().showToast(MemberApplyActivity.this, "网络异常~");
            }
        });
    }

    @Override
    public void onAddPicClick() {
        // 进入相册 以下是例子：用不到的api可以不写
        PictureSelector.create(MemberApplyActivity.this)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(R.style.picture_default_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                .maxSelectNum(6)// 最大图片选择数量 int
                .minSelectNum(0)// 最小选择数量 int
                .imageSpanCount(3)// 每行显示个数 int
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .previewVideo(false)// 是否可预览视频 true or false
                .enablePreviewAudio(false) // 是否可播放音频 true or false
                .isCamera(true)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                .enableCrop(false)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
                .glideOverride(160, 160)// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .withAspectRatio(3, 4)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示 true or false
                .isGif(false)// 是否显示gif图片 true or false
//                .compressSavePath(Consts.SD_ROOT)//压缩图片保存地址
                .freeStyleCropEnabled(false)// 裁剪框是否可拖拽 true or false
                .circleDimmedLayer(false)// 是否圆形裁剪 true or false
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .openClickSound(true)// 是否开启点击声音 true or false
                .selectionMedia(selectList)// 是否传入已选图片 List<LocalMedia> list
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .cropCompressQuality(90)// 裁剪压缩质量 默认90 int
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .synOrAsy(false)//同步true或异步false 压缩 默认同步
                .cropWH(4, 3)// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
                .rotateEnabled(false) // 裁剪是否可旋转图片 true or false
                .scaleEnabled(false)// 裁剪是否可放大缩小图片 true or false
                .videoQuality(0)// 视频录制质量 0 or 1 int
                .videoMaxSecond(15)// 显示多少秒以内的视频or音频也可适用 int
                .videoMinSecond(10)// 显示多少秒以内的视频or音频也可适用 int
                .recordVideoSecond(60)//视频秒数录制 默认60s int
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    @Override
    public void onItemClick(int position, View v) {
        if (selectList.size() > 0) {
            LocalMedia media = selectList.get(position);
            String pictureType = media.getPictureType();
            int mediaType = PictureMimeType.pictureToVideo(pictureType);
            switch (mediaType) {
                case 1:
                    // 预览图片 可自定长按保存路径
                    PictureSelector.create(this).externalPicturePreview(position, selectList);
                    break;
                case 2:
                    // 预览视频
                    PictureSelector.create(this).externalPictureVideo(media.getPath());
                    break;
                case 3:
                    // 预览音频
                    PictureSelector.create(this).externalPictureAudio(media.getPath());
                    break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 123123:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //用户已授权
                    ToastUtils.getInstance().showToast(MemberApplyActivity.this, "您已经成功授权，请重新点击");
                } else {
                    //用户未授权
                    ToastUtils.getInstance().showToast(MemberApplyActivity.this, "您已拒绝授权，如仍想使用请重新选择！");
                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList.clear();
                    selectList.addAll(PictureSelector.obtainMultipleResult(data));
                    adapter.notifyDataSetChanged();
                    break;

            }
        }
        if (requestCode == REQUEST_CODE_CHOOSE24 && resultCode == RESULT_OK) {
            Uri result = data.getData();

            if (result != null) {
                Uri File_url = result;


                String img_path = "";
                try {
                    img_path = PathUtils.getPath(MemberApplyActivity.this, File_url);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (img_path == null) {
                    ToastUtils.getInstance().showToast(MemberApplyActivity.this, "不支持文件类型");
                    return;
                }
                if (img_path == null) {
                    ToastUtils.getInstance().showToast(MemberApplyActivity.this, "不支持文件类型");
                    return;
                }
                if (img_path.contains(".")) {
                    String fileTyleimg_path =
                            img_path.substring(img_path.lastIndexOf("."), img_path.length());
                    Log.i("TAG", "文件类型：" + fileTyleimg_path);
                    if (!".jpg,.jpeg,.png,.gif,.zip,.rar,.doc,.docx,.xls,.xlsx,.ppt,.pptx,.txt,.pdf".contains(
                            fileTyleimg_path
                    )
                    ) {
                        ToastUtils.getInstance().showToast(MemberApplyActivity.this, "不支持此文件类型");
                        return;
                    }
                }
                File file = new File(img_path);
                if (file.length() / 1024 > 8000) {
                    ToastUtils.getInstance().showToast(MemberApplyActivity.this, "文件大小不能大于20M");
                    return;
                }
                String fileName = RealPathFromUriUtils.getFileName(File_url);
                if (fileName.contains(".")) {
                    String fileTyle = fileName.substring(fileName.lastIndexOf("."), fileName.length());
                    Log.i("TAG", "文件类型：" + fileTyle);
                    if (!".jpg,.jpeg,.png,.gif,.zip,.rar,.doc,.docx,.xls,.xlsx,.ppt,.pptx,.txt,.pdf".contains(
                            fileTyle
                    )
                    ) {
                        ToastUtils.getInstance().showToast(MemberApplyActivity.this, "不支持此文件类型");
                        return;
                    }
                }
                mFileInfo.strFileName = fileName;
                mFileInfo.mFile = file;
                tvUploadfile.setText(fileName);
            }


        }

    }

    FileInfoFujian mFileInfo = new FileInfoFujian();


    class FileInfoFujian {
        public File mFile;
        public String strFileName = "";
    }

    /**
     * 发布
     */
    private void doPost() {
//        String title = etTitle.getText().toString().trim();
//        String content = etContent.getText().toString().trim();
//        if (TextUtils.isEmpty(title)) {
//            ToastUtils.getInstance().showToast(this, "标题不能为空！");
//            return;
//        } else if (TextUtils.isEmpty(etSign.getText().toString().trim())) {
//            ToastUtils.getInstance().showToast(this, "话题类型不能为空！");
//            return;
//        } else if (TextUtils.isEmpty(content)) {
//            ToastUtils.getInstance().showToast(this, "内容不能为空！");
//            return;
//        }

        List<File> list = new ArrayList<>();
        if (selectList.size() > 0) {
            for (LocalMedia media : selectList) {
                String path = "";
                if (media.isCut() && !media.isCompressed()) {
                    // 裁剪过
                    path = media.getCutPath();
                    ULog.e("cc", "裁剪过" + path);
                } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
                    // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                    path = media.getCompressPath();
                    ULog.e("cc", "压缩过" + path);
                } else {
                    // 原图
                    path = media.getPath();
                    ULog.e("cc", "原图" + path);
                }
                File file = new File(path);
                ULog.e("cc", path);
                list.add(file);
            }
        }
        tvPublishphone.setText("正在发布中...");
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
//        params.put("title", title);
//        params.put("info", content);
        params.put("isfile", selectList.size() > 0 ? "1" : "0");
//        params.put("type", String.valueOf(type));
//        params.put("signid", signId);
        String url = Consts.BASE_URL + "/Said/log_addsaid";
//        ULog.e("cc", "title:" + title + "  content:" + content + "  type:" + type + "  isfile:" + (selectList.size() > 0 ? "1" : "0") + "  " +
//                "signid:" + signId + "  signname:" + etSign.getText().toString());
        OkHttpUtil.getInstance().doAsyncMultiUpload2(url, params, list, "1", new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                ULog.e("cc", "发布response" + response);
                tvPublishphone.setText("发布");
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    ToastUtils.getInstance().showToast(MemberApplyActivity.this, jsonObject.optString("msg"));
                    if (TextUtils.equals(jsonObject.optString("code"), "0")) {
                        //发布成功
                        MessageEvent event = new MessageEvent();
                        event.message = "refresh";
                        EventBus.getDefault().post(event);
                        finish();
                    } else if (TextUtils.equals(jsonObject.optString("code"), "3")) {
                        //待审核
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtils.getInstance().showToast(MemberApplyActivity.this, "服务器异常~");
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                tvPublishphone.setText("发布");
                ToastUtils.getInstance().showToast(MemberApplyActivity.this, "网络异常~");
            }
        });
    }

}
