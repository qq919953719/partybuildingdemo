package com.longhoo.net.manageservice.ui;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;
import com.luck.picture.lib.entity.LocalMedia;
import com.longhoo.net.R;
import com.longhoo.net.manageservice.adapter.PostGridAdapter;
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
import com.longhoo.net.widget.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static butterknife.internal.Utils.arrayOf;

/**
 * Created by ${CC} on 2017/12/5.
 */

public class PublishTopicActivity extends BaseActivity implements PostGridAdapter.onAddPicClickListener, PostGridAdapter.OnItemClickListener {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.text2)
    TextView text2;
    @BindView(R.id.et_sign)
    EditText etSign;
    @BindView(R.id.iv_sel_sign)
    TextView ivSelSign;
    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.tv_post)
    TextView tvPost;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_uploadfile)
    TextView tvUploadfile;
    @BindView(R.id.img_show_video_image)
    ImageView imgShowVideoImage;
    @BindView(R.id.img_del)
    ImageView imgDel;
    @BindView(R.id.rv_show_video_image)
    RelativeLayout rvShowVideoImage;
    private PostGridAdapter adapter;
    //private List<LocalMedia> picList = new ArrayList<>();
    private List<LocalMedia> selectList = new ArrayList<>();
    private List<SignBan> signList1;
    private List<SignBan> signList2;
    private int type; //0文明 1不文明
    private String signId;

    @Override
    protected int getContentId() {
        return R.layout.activity_post;
    }

    @Override
    protected void initViews() {
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
        etTitle.addTextChangedListener(new MyTextWatch(etTitle));
        etContent.addTextChangedListener(new MyTextWatch(etContent));
    }

    @Override
    protected void initToolbar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        tvTitle.setText("发布");
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

    @OnClick({R.id.iv_sel_sign, R.id.tv_post, R.id.tv_uploadfile, R.id.img_show_video_image, R.id.img_del})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_sel_sign:
                showDialog();
                break;
            case R.id.tv_uploadfile:
                chooseVieo();
                break;
            case R.id.img_del:
                mFileInfo.mFile = null;
                mFileInfo.mFileImage = null;
                mFileInfo.strFileName = "";
                rvShowVideoImage.setVisibility(View.GONE);
                tvUploadfile.setVisibility(View.VISIBLE);
                break;
            case R.id.img_show_video_image:
                chooseVieo();
                break;

            case R.id.tv_post:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),
                            0);
                }
                if (!EnterCheckUtil.getInstance().isLogin(true)) {
                    return;
                }
                doPost();
                break;


        }
    }

    /**
     * 发布
     */
    private void doPost() {
        String title = etTitle.getText().toString().trim();
        String content = etContent.getText().toString().trim();
        if (TextUtils.isEmpty(title)) {
            ToastUtils.getInstance().showToast(this, "标题不能为空！");
            return;
        } else if (TextUtils.isEmpty(etSign.getText().toString().trim())) {
            ToastUtils.getInstance().showToast(this, "话题类型不能为空！");
            return;
        } else if (TextUtils.isEmpty(content)) {
            ToastUtils.getInstance().showToast(this, "内容不能为空！");
            return;
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
                ULog.e("cc", path);
                list.add(file);
            }
        }
        tvPost.setText("正在发布中...");
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("title", title);
        params.put("info", content);
        params.put("isfile", selectList.size() > 0 ? "1" : "0");
        params.put("type", String.valueOf(type));
        params.put("signid", signId);
        String url = Consts.BASE_URL + "/Said/log_addsaid";
        ULog.e("cc", "title:" + title + "  content:" + content + "  type:" + type + "  isfile:" + (selectList.size() > 0 ? "1" : "0") + "  " +
                "signid:" + signId + "  signname:" + etSign.getText().toString());
        OkHttpUtil.getInstance().doAsyncMultiUpload4(url, params, mFileInfo.mFile, mFileInfo.mFileImage, list, "1", new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                ULog.e("cc", "发布response" + response);
                tvPost.setText("发布");
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    ToastUtils.getInstance().showToast(PublishTopicActivity.this, jsonObject.optString("msg"));
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
                    ToastUtils.getInstance().showToast(PublishTopicActivity.this, "服务器异常~");
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                tvPost.setText("发布");
                ToastUtils.getInstance().showToast(PublishTopicActivity.this, "网络异常~");
            }
        });
    }

    private List<SignBan> doGetSigns() {
        final List<SignBan> list = new ArrayList<>();
        String url = Consts.BASE_URL + "/Said/public_saidsign";
        Map<String, String> params = new HashMap<>();
        params.put("type", String.valueOf(type));
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (!TextUtils.equals(jsonObject.optString("code"), "0")) {
                        ToastUtils.getInstance().showToast(PublishTopicActivity.this, jsonObject.optString("msg"));
                        return;
                    }
                    JSONObject data = jsonObject.optJSONObject("data");
                    JSONArray sign = data.optJSONArray("sign");
                    int size = sign.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject item = sign.optJSONObject(i);
                        list.add(new SignBan(item.optString("signid"), item.optString("signname")));
                    }
                    showDialog();
                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtils.getInstance().showToast(PublishTopicActivity.this, "服务器异常~");
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    ToastUtils.getInstance().showToast(PublishTopicActivity.this, "服务器异常~");
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(PublishTopicActivity.this, "网络异常~");
            }
        });
        return list;
    }

    private void showDialog() {
        String[] signs = null;
        switch (type) {
            case 0:
                if (signList1 == null) {
                    signList1 = doGetSigns();
                    return;
                }
                int size = signList1.size();
                signs = new String[size];
                for (int i = 0; i < size; i++) {
                    signs[i] = (signList1.get(i).getSignname());
                }
                break;
            case 1:
                if (signList2 == null) {
                    signList2 = doGetSigns();
                    return;
                }
                int size2 = signList2.size();
                signs = new String[size2];
                for (int i = 0; i < size2; i++) {
                    signs[i] = (signList2.get(i).getSignname());
                }
                break;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //设置标题
        builder.setTitle("");
        builder.setItems(signs, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (type == 0) {
                    etSign.setText(signList1.get(i).getSignname());
                    ivSelSign.setText(signList1.get(i).getSignname());
                    signId = signList1.get(i).getSignid();
                } else if (type == 1) {
                    etSign.setText(signList2.get(i).getSignname());
                    ivSelSign.setText(signList2.get(i).getSignname());
                    signId = signList2.get(i).getSignid();
                }
            }
        });
        builder.create();
        builder.show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onAddPicClick() {
        // 进入相册 以下是例子：用不到的api可以不写
//        PictureSelector.create(this)
//                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
//                .theme(R.style.picture_default_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
//                .maxSelectNum(6)// 最大图片选择数量
//                .minSelectNum(0)// 最小选择数量
//                .imageSpanCount(3)// 每行显示个数
//                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
//                .previewImage(true)// 是否可预览图片
//                .compressGrade(Luban.THIRD_GEAR)// luban压缩档次，默认3档 Luban.FIRST_GEAR、Luban.CUSTOM_GEAR
//                .isCamera(true)// 是否显示拍照按钮
//                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
//                //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
//                //.enableCrop(true)// 是否裁剪
//                .compress(true)// 是否压缩
//                .compressMode(PictureConfig.LUBAN_COMPRESS_MODE)//系统自带 or 鲁班压缩 PictureConfig.SYSTEM_COMPRESS_MODE or LUBAN_COMPRESS_MODE
//                //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
//                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
//                .isGif(false)// 是否显示gif图片
//                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
//                .circleDimmedLayer(false)// 是否圆形裁剪
//                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
//                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
//                .openClickSound(false)// 是否开启点击声音
//                .selectionMedia(selectList)// 是否传入已选图片
//                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code


        // 进入相册 以下是例子：用不到的api可以不写
        PictureSelector.create(PublishTopicActivity.this)
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

    void chooseVieo() {


        if (ContextCompat.checkSelfPermission(
                PublishTopicActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
                != PackageManager.PERMISSION_GRANTED
        ) {
            // 未授权，此时需要申请权限
            ActivityCompat.requestPermissions(
                    PublishTopicActivity.this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    123123
            );
            return;
        }
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        // intent.setType("vedio/*");
        intent.setType("*/*");
        startActivityForResult(intent, REQUEST_CODE_CHOOSE);


    }

    private int REQUEST_CODE_CHOOSE = 23;

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
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            Uri result = data.getData();

            if (result != null) {
                Uri File_url = result;


                String img_path = "";
                try {
                    img_path = PathUtils.getPath(PublishTopicActivity.this, File_url);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (img_path == null) {
                    ToastUtils.getInstance().showToast(PublishTopicActivity.this, "不支持文件类型");
                    return;
                }
                if (img_path == null) {
                    ToastUtils.getInstance().showToast(PublishTopicActivity.this, "不支持文件类型");
                    return;
                }
                if (img_path.contains(".")) {
                    String fileTyleimg_path =
                            img_path.substring(img_path.lastIndexOf("."), img_path.length());
                    Log.i("TAG", "文件类型：" + fileTyleimg_path);
                    if (!".mp4,.3gp,.avi".contains(
                            fileTyleimg_path
                    )
                    ) {
                        ToastUtils.getInstance().showToast(PublishTopicActivity.this, "不支持此文件类型");
                        return;
                    }
                }
                File file = new File(img_path);
                if (file.length() / 1024 > 20000) {
                    ToastUtils.getInstance().showToast(PublishTopicActivity.this, "文件大小不能大于20M");
                    return;
                }
                String fileName = RealPathFromUriUtils.getFileName(File_url);
                if (fileName.contains(".")) {
                    String fileTyle = fileName.substring(fileName.lastIndexOf("."), fileName.length());
                    Log.i("TAG", "文件类型：" + fileTyle);
                    if (!".mp4,.3gp,.avi,".contains(
                            fileTyle
                    )
                    ) {
                        ToastUtils.getInstance().showToast(PublishTopicActivity.this, "不支持此文件类型");
                        return;
                    }
                }
                mFileInfo.strFileName = fileName;
                mFileInfo.mFile = file;
                try {
                    mFileInfo.mFileImage = saveFile(getVideoThumbnail(img_path, 200, 200, 1), System.currentTimeMillis() + ".jpg");
                    imgShowVideoImage.setImageBitmap(getVideoThumbnail(img_path, 200, 200, 1));
                    tvUploadfile.setVisibility(View.GONE);
                    imgShowVideoImage.setVisibility(View.VISIBLE);
                    rvShowVideoImage.setVisibility(View.VISIBLE);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //tvUploadfile.setText(fileName);
            }


        }
    }

    public File saveFile(Bitmap bm, String fileName) throws IOException {//将Bitmap类型的图片转化成file类型，便于上传到服务器
        String path = Environment.getExternalStorageDirectory() + "/Ask";
        File dirFile = new File(path);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        File myCaptureFile = new File(path + fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
        return myCaptureFile;

    }

    public Bitmap getVideoThumbnail(String videoPath, int width, int height, int kind) {
        Bitmap bitmap = null;
        // 获取视频的缩略图
        bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind); //調用ThumbnailUtils類的靜態方法createVideoThumbnail獲取視頻的截圖；
        if (bitmap != null) {
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);//調用ThumbnailUtils類的靜態方法extractThumbnail將原圖片（即上方截取的圖片）轉化為指定大小；
        }
        return bitmap;
    }

    FileInfo mFileInfo = new FileInfo();

    class FileInfo {
        public File mFile;
        public File mFileImage;
        public String strFileName = "";
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
                case R.id.et_title:
                    if (s.length() > 36) {
                        etTitle.setText(s.toString().subSequence(0, 36));
                        etTitle.setSelection(36);
                        ToastUtils.getInstance().showToast(PublishTopicActivity.this, "标题最多输入36个字！");
                        return;
                    }
                    break;
                case R.id.et_content:
                    if (s.length() > 10000) {
                        etContent.setText(s.toString().subSequence(0, 10000));
                        etContent.setSelection(10000);
                        ToastUtils.getInstance().showToast(PublishTopicActivity.this, "内容最多输入10000个字！");
                        return;
                    }
                    break;
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
