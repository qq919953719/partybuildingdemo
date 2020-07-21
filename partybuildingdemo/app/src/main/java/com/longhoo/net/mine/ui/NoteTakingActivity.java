package com.longhoo.net.mine.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.longhoo.net.R;
import com.longhoo.net.manageservice.adapter.PostGridAdapter;
import com.longhoo.net.mine.bean.NoteDetailBean;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.DisplayUtil;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.MessageEvent;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.utils.httprequest.OkHttpCallback;
import com.longhoo.net.utils.httprequest.OkHttpUtil;
import com.longhoo.net.widget.GridSpacingItemDecoration;
import com.longhoo.net.widget.MyDialog;
import com.longhoo.net.widget.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoteTakingActivity extends BaseActivity implements PostGridAdapter.onAddPicClickListener, PostGridAdapter.OnItemClickListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_sign_up)
    TextView tvSignUp;
    @BindView(R.id.ll_pic_select)
    LinearLayout llPicSelect;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.container)
    LinearLayout container;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_type)
    TextView tvType;

    private PostGridAdapter adapter;
    private List<LocalMedia> selectList = new ArrayList<>();
    private String title = "", content = "";
    private boolean isCommitted;//是否已经提交

    private int tag;
    private String noteId = "";
    private static final int TAG_ADD_NOTE = 0;
    private static final int TAG_EDIT_NOTE = 1;

    public static void goAddNote(Context context) {
        Intent intent = new Intent(context, NoteTakingActivity.class);
        intent.putExtra("tag", TAG_ADD_NOTE);
        context.startActivity(intent);
    }

    public static void goEditNote(Context context, String noteId) {
        Intent intent = new Intent(context, NoteTakingActivity.class);
        intent.putExtra("tag", TAG_EDIT_NOTE);
        intent.putExtra("noteId", noteId);
        context.startActivity(intent);
    }

    int type = 3;

    void getNoteType(final TextView tvSelMontn) {
        MyDialog dialog = new MyDialog(NoteTakingActivity.this, R.style.MyDialogStyle);
        final int[] arraysInt = new int[]{1, 0};
        final String[] arrays = new String[]{"私有笔记", "共享笔记"};
        dialog.setMyItems(arrays);
        dialog.setMyDialogItemClickListener(new MyDialog.MyDialogItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                type = arraysInt[position];
                tvSelMontn.setText(arrays[position]);


            }
        });
        dialog.show();

    }

    @Override
    protected int getContentId() {
        return R.layout.activity_note_taking;
    }

    @Override
    protected void initViews() {
        if (getIntent() != null) {
            tag = getIntent().getIntExtra("tag", 0);
        }
        if (tag == TAG_ADD_NOTE) {
            tvTitle.setText("学习笔记");
        } else if (tag == TAG_EDIT_NOTE) {
            noteId = getIntent().getStringExtra("noteId");
            tvTitle.setText("修改笔记");
            getNoteDetail();
        }
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tag == TAG_ADD_NOTE) {
                    doSignUp();
                } else if (tag == TAG_EDIT_NOTE) {
                    doEditNote();
                }

            }
        });
        tvType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNoteType(tvType);
            }
        });

        GridLayoutManager manager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, DisplayUtil.dp2px(this, 4), false));
        adapter = new PostGridAdapter(this, this);
        adapter.setList(selectList);
        adapter.setSelectMax(6);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);


        InputFilter inputFilter = new InputFilter() {
            Pattern pattern = Pattern.compile("[^a-zA-Z0-9\\u4E00-\\u9FA5_,.?!:;…~_\\-\"\"/@*+'()<>{}/[/]()<>{}\\[\\]=%&$|\\/♀♂#¥£¢€\"^` ，。？！：；……～“”、“（）”、（——）‘’＠‘·’＆＊＃《》￥《〈〉》〈＄〉［］￡［］｛｝｛｝￠【】【】％〖〗〖〗／〔〕〔〕＼『』『』＾「」「」｜﹁﹂｀．]");

            @Override
            public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                Matcher matcher = pattern.matcher(charSequence);
                if (!matcher.find()) {
                    return null;
                } else {
                    ToastUtils.getInstance().showToast(NoteTakingActivity.this, "不允许输入非法字符！");
                    return "";
                }

            }
        };
        etTitle.setFilters(new InputFilter[]{inputFilter, new InputFilter.LengthFilter(50)});
        //etContent.setFilters(new InputFilter[]{inputFilter, new InputFilter.LengthFilter(300)});
    }

    @Override
    protected void initToolbar() {
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

    private void doSignUp() {
        title = etTitle.getText().toString().trim();
        content = etContent.getText().toString().trim();
        if (TextUtils.isEmpty(title)) {
            ToastUtils.getInstance().showToast(this, "请输入标题！");
            etTitle.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(content)) {
            ToastUtils.getInstance().showToast(this, "请输入内容！");
            etContent.requestFocus();
            return;
        }
        if (type == 3) {
            ToastUtils.getInstance().showToast(this, "请选择笔记类型！");
            return;
        }
        String url = Consts.BASE_URL + "/Application/add_biji";
        Map<String, String> params = new HashMap<>();
        params.put("uid", EnterCheckUtil.getInstance().getUid_Token()[0]);
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("title", title);
        params.put("content", content);
        params.put("type", type + "");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            ULog.e("cc", "Key = " + entry.getKey() + ", Value = " + entry.getValue() + "\n");
        }

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
                list.add(file);
            }
        }
        tvSignUp.setText("正在提交中...");
        tvSignUp.setEnabled(false);
        OkHttpUtil.getInstance().doAsyncMultiUpload(url, params, list, "uploads[]", new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                if (isFinishing()) {
                    return;
                }
                ULog.e("cc", "提交：" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.optString("code");
                    String msg = jsonObject.optString("msg");
                    ToastUtils.getInstance().showToast(NoteTakingActivity.this, msg);
                    if (TextUtils.equals(jsonObject.optString("code"), "0")) {
                        isCommitted = true;
                        onBackPressed();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    tvSignUp.setEnabled(true);
                    ToastUtils.getInstance().showToast(NoteTakingActivity.this, "服务器异常，提交失败~");
                }
                tvSignUp.setText("立即提交");
            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(NoteTakingActivity.this, "网络异常，提交失败~");
                if (isFinishing()) {
                    return;
                }
                tvSignUp.setText("立即提交");
                tvSignUp.setEnabled(true);
            }
        });
    }

    @Override
    public void onAddPicClick() {
        PictureSelector.create(this)
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
                    //PictureSelector.create(MainActivity.this).externalPicturePreview(position, "/custom_file", selectList);
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

    /**
     * 获取笔记详情
     */
    private void getNoteDetail() {
        String url = Consts.BASE_URL + "/Application/biJiDetailById";
        Map<String, String> params = new HashMap<>();
        params.put("uid", EnterCheckUtil.getInstance().getUid_Token()[0]);
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("id", noteId + "");
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                Gson gson = new Gson();
                try {
                    NoteDetailBean detailBean = gson.fromJson(response, NoteDetailBean.class);
                    String code = detailBean.getCode();
                    if (!TextUtils.equals(code, "0")) {
                        ToastUtils.getInstance().showToast(NoteTakingActivity.this, "获取数据失败~");
                        return;
                    }
                    String title = detailBean.getData().getInfo().getTitle();
                    String content = detailBean.getData().getInfo().getContent();
                    String time = detailBean.getData().getInfo().getTime() + "";
                    List<String> tempList = detailBean.getData().getInfo().getUploads();
                    etTitle.setText(title + "");
                    etContent.setText(content + "");
                    //图片
                    if (tempList.size() > 0) {
                        selectList.clear();
                        for (String path : tempList) {
                            selectList.add(new LocalMedia(path, 0, PictureMimeType.ofImage(), ""));
                        }
                        adapter.notifyDataSetChanged();
                    }
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    ToastUtils.getInstance().showToast(NoteTakingActivity.this, "服务器异常~");
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(NoteTakingActivity.this, "网络异常~");
            }
        });
    }

    /**
     * 修改笔记
     */
    private void doEditNote() {
        title = etTitle.getText().toString().trim();
        content = etContent.getText().toString().trim();
        if (TextUtils.isEmpty(title)) {
            ToastUtils.getInstance().showToast(this, "请输入标题！");
            etTitle.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(content)) {
            ToastUtils.getInstance().showToast(this, "请输入内容！");
            etContent.requestFocus();
            return;
        }
        String url = Consts.BASE_URL + "/application/biJiEdit";
        Map<String, String> params = new HashMap<>();
        params.put("uid", EnterCheckUtil.getInstance().getUid_Token()[0]);
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("id", noteId + "");
        params.put("title", title);
        params.put("content", content);

        for (Map.Entry<String, String> entry : params.entrySet()) {
            ULog.e("ck", "Key = " + entry.getKey() + ", Value = " + entry.getValue() + "\n");
        }

        List<File> list = new ArrayList<>();
        if (selectList.size() > 0) {
            for (LocalMedia media : selectList) {
                String path = "";
                if (media.isCut() && !media.isCompressed()) {
                    // 裁剪过
                    path = media.getCutPath();
                    ULog.e("ck", "裁剪过" + path);
                } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
                    // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                    path = media.getCompressPath();
                    ULog.e("ck", "压缩过" + path);
                } else {
                    // 原图
                    path = media.getPath();
                    ULog.e("ck", "原图" + path);
                }
                File file = new File(path);
                list.add(file);
            }
        }
        tvSignUp.setText("正在提交中...");
        tvSignUp.setEnabled(false);
        OkHttpUtil.getInstance().doAsyncMultiUpload(url, params, list, "uploads[]", new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                if (isFinishing()) {
                    return;
                }
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.optString("code");
                    String msg = jsonObject.optString("msg");
                    if (TextUtils.equals(code, "0")) {
                        //成功
                        MessageEvent event = new MessageEvent();
                        event.msgType = MessageEvent.MSG_EDIT_NOTE;
                        EventBus.getDefault().post(event);
                        finish();
                    }
                    ToastUtils.getInstance().showToast(NoteTakingActivity.this, msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                tvSignUp.setText("立即提交");
                tvSignUp.setEnabled(true);
            }

            @Override
            public void onFailure(String errorMsg) {
                tvSignUp.setText("立即提交");
                tvSignUp.setEnabled(true);
                ToastUtils.getInstance().showToast(NoteTakingActivity.this, "网络异常~");
            }
        });
    }

    @Override
    public void onBackPressed() {
        //如果记笔记成功，则告诉列表页需刷新
        if (isCommitted) {
            MessageEvent event = new MessageEvent();
            event.msgType = MessageEvent.MSG_ADD_NOTE;
            EventBus.getDefault().post(event);
        }
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
