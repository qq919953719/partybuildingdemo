package com.longhoo.net.manageservice.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.longhoo.net.R;
import com.longhoo.net.manageservice.adapter.PostGridAdapter;
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
import com.longhoo.net.widget.GridSpacingItemDecoration;
import com.longhoo.net.widget.MyDialog;
import com.longhoo.net.widget.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static butterknife.internal.Utils.arrayOf;

/**
 * 个人获奖上报/集体获奖上报
 */
public class ReportAwardActivity extends BaseActivity implements PostGridAdapter.onAddPicClickListener, PostGridAdapter.OnItemClickListener {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_reward_type)
    TextView tvRewardType;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_reward_company)
    EditText etRewardCompany;
    @BindView(R.id.et_reward_level)
    EditText etRewardLevel;
    @BindView(R.id.tv_reward_time)
    TextView tvRewardTime;
    @BindView(R.id.iv_date)
    ImageView ivDate;
    @BindView(R.id.tv_pic_tag_name)
    TextView tvPicTagName;
    @BindView(R.id.ll_pic_select)
    LinearLayout llPicSelect;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.tv_uploadfile)
    TextView tvUploadfile;
    @BindView(R.id.img_show_video_image)
    ImageView imgShowVideoImage;
    @BindView(R.id.img_del)
    ImageView imgDel;
    @BindView(R.id.rv_show_video_image)
    RelativeLayout rvShowVideoImage;
    @BindView(R.id.tv_uploadfilefujian)
    TextView tvUploadfilefujian;
    private String type;
    private PostGridAdapter adapter;
    private List<LocalMedia> selectList = new ArrayList<>();

    public static void goTo(Context context, String type) {
        Intent intent = new Intent(context, ReportAwardActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    FileInfo mFileInfo = new FileInfo();

    class FileInfo {
        public File mFile;
        public File mFileImage;
        public String strFileName = "";
    }

    void chooseVieo() {


        if (ContextCompat.checkSelfPermission(
                ReportAwardActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
                != PackageManager.PERMISSION_GRANTED
        ) {
            // 未授权，此时需要申请权限
            ActivityCompat.requestPermissions(
                    ReportAwardActivity.this,
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
    protected int getContentId() {
        return R.layout.activity_report_award;
    }

    @Override
    protected void initViews() {
        mFileInfo.mFile=null;
        mFileInfo.mFileImage=null;
        mFileInfo.strFileName=null;

        mFileInfofujian.mFile=null;
        mFileInfofujian.strFileName=null;
        if (getIntent() != null) {
            type = getIntent().getStringExtra("type");
        }
        if (TextUtils.equals(type, MaterialReportFragment.TYPE_GROUP)) {
            tvTitle.setText("上报集体获奖");
            tvPicTagName.setText("上传获奖图片");
        } else if (TextUtils.equals(type, MaterialReportFragment.TYPE_PERSONAL)) {
            tvTitle.setText("上报个人获奖");
            tvPicTagName.setText("上传获奖图片");
        }
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doSubmit();
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

    @OnClick({R.id.tv_reward_type, R.id.iv_date, R.id.tv_reward_time, R.id.tv_uploadfile, R.id.img_show_video_image, R.id.img_del, R.id.tv_uploadfilefujian})
    public void onClick(View view) {
        switch (view.getId()) {

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
                VideoActivity.startVideoPlay(this, mFileInfo.strFileName);
                break;

            case R.id.tv_reward_type:
                MyDialog dialog = new MyDialog(this, R.style.MyDialogStyle);
                final String[] arrays = new String[]{"党建类", "非党建类"};
                dialog.setMyItems(arrays);
                dialog.setMyDialogItemClickListener(new MyDialog.MyDialogItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        tvRewardType.setText(arrays[position]);
                    }
                });
                dialog.show();
                break;
            case R.id.tv_reward_time:
            case R.id.iv_date:
                pickData();
                break;
            case R.id.tv_uploadfilefujian: {
                if (ContextCompat.checkSelfPermission(
                        ReportAwardActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                        != PackageManager.PERMISSION_GRANTED
                ) {
                    // 未授权，此时需要申请权限
                    ActivityCompat.requestPermissions(
                            ReportAwardActivity.this,
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
            break;
        }
    }

    private void pickData() {
        DatePickDialog dialog = new DatePickDialog(this);
        //设置上下年分限制
        dialog.setYearLimt(5);
        //设置标题
        dialog.setTitle("选择时间");
        //设置类型
        dialog.setType(DateType.TYPE_YM);
        //设置消息体的显示格式，日期格式
        dialog.setMessageFormat("yyyy-MM");
        //设置选择回调
        dialog.setOnChangeLisener(null);
        //设置点击确定按钮回调
        dialog.setOnSureLisener(new OnSureLisener() {
            @Override
            public void onSure(Date date) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
                Date curDate = new Date(System.currentTimeMillis());
                if (date.getTime() > curDate.getTime()) {
                    ToastUtils.getInstance().showToast(ReportAwardActivity.this, "获奖时间不能晚于当前时间！");
                    tvRewardTime.setText(simpleDateFormat.format(curDate));
                } else {
                    tvRewardTime.setText(simpleDateFormat.format(date));
                }

            }
        });
        dialog.show();
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

    String path = Environment.getExternalStorageDirectory() + "/Ask";

    public File saveFile(Bitmap bm, String fileName) throws IOException {//将Bitmap类型的图片转化成file类型，便于上传到服务器

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

    FileInfoFujian mFileInfofujian = new FileInfoFujian();

    class FileInfoFujian {
        public File mFile;
        public String strFileName = "";
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
                    img_path = PathUtils.getPath(ReportAwardActivity.this, File_url);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (img_path == null) {
                    ToastUtils.getInstance().showToast(ReportAwardActivity.this, "不支持文件类型");
                    return;
                }
                if (img_path == null) {
                    ToastUtils.getInstance().showToast(ReportAwardActivity.this, "不支持文件类型");
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
                        ToastUtils.getInstance().showToast(ReportAwardActivity.this, "不支持此文件类型");
                        return;
                    }
                }
                File file = new File(img_path);
                if (file.length() / 1024 > 8000) {
                    ToastUtils.getInstance().showToast(ReportAwardActivity.this, "文件大小不能大于20M");
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
                        ToastUtils.getInstance().showToast(ReportAwardActivity.this, "不支持此文件类型");
                        return;
                    }
                }
                mFileInfofujian.strFileName = fileName;
                mFileInfofujian.mFile = file;
                tvUploadfilefujian.setText(fileName);
            }


        }

        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            Uri result = data.getData();

            if (result != null) {
                Uri File_url = result;


                String img_path = "";
                try {
//                    if (File_url.toString().contains("file:")){
//                        File mmmmfile=new File(File_url.toString());
//                        img_path=mmmmfile.getPath();
//                    }else {
//                        img_path = PathUtils.getPath(ReportAwardActivity.this, File_url);
//                    }
                    img_path = PathUtils.getPath(ReportAwardActivity.this, File_url);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (img_path == null) {
                    ToastUtils.getInstance().showToast(ReportAwardActivity.this, "不支持文件类型");
                    return;
                }
                if (img_path == null) {
                    ToastUtils.getInstance().showToast(ReportAwardActivity.this, "不支持文件类型");
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
                        ToastUtils.getInstance().showToast(ReportAwardActivity.this, "不支持此文件类型");
                        return;
                    }
                }
                File file = new File(img_path);
                if (file.length() / 1024 > 20000) {
                    ToastUtils.getInstance().showToast(ReportAwardActivity.this, "文件大小不能大于20M");
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
                        ToastUtils.getInstance().showToast(ReportAwardActivity.this, "不支持此文件类型");
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

            }


        }
    }

    private int REQUEST_CODE_CHOOSE24 = 24;

    private void doSubmit() {
        String rewardType = tvRewardType.getText().toString().trim();
        String name = etName.getText().toString().trim();
        String rewardCompany = etRewardCompany.getText().toString().trim();
        String rewardLevel = etRewardLevel.getText().toString().trim();
        String rewardTime = tvRewardTime.getText().toString().trim();
        if (TextUtils.isEmpty(rewardType)) {
            ToastUtils.getInstance().showToast(this, "请选择奖项类别！");
            return;
        }
        if (TextUtils.isEmpty(name)) {
            ToastUtils.getInstance().showToast(this, "请输入奖项名称！");
            etName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(rewardCompany)) {
            ToastUtils.getInstance().showToast(this, "请输入授予单位！");
            etRewardCompany.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(rewardTime)) {
            ToastUtils.getInstance().showToast(this, "请输入获奖时间！");
            return;
        }
        if (selectList.size() <= 0) {
            ToastUtils.getInstance().showToast(this, "请选择图片！");
            return;
        }
        String url = Consts.BASE_URL + "/Application/infoReporting_add";
        Map<String, String> params = new HashMap<>();
        params.put("uid", EnterCheckUtil.getInstance().getUid_Token()[0]);
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("title", name);
        params.put("type", type);
        params.put("reward_type", TextUtils.equals(rewardType, "党建类") ? "1" : "2");
        params.put("reward_name", name);
        params.put("reward_company", rewardCompany);
        params.put("reward_level", rewardLevel);
        params.put("reward_time", rewardTime);

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
        tvSubmit.setText("正在提交中...");
        tvSubmit.setEnabled(false);
        OkHttpUtil.getInstance().doAsyncMultiUpload443(url, params, mFileInfo.mFile, mFileInfofujian.mFile, mFileInfo.mFileImage, list, "files[]", new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                if (isFinishing()) {
                    return;
                }
                ULog.e("ck", "提交：" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.optString("code");
                    String msg = jsonObject.optString("msg");
                    ToastUtils.getInstance().showToast(ReportAwardActivity.this, msg);
                    if (TextUtils.equals(jsonObject.optString("code"), "0")) {
                        MessageEvent event = new MessageEvent();
                        event.message = "report_award_refresh";
                        EventBus.getDefault().post(event);
                        onBackPressed();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtils.getInstance().showToast(ReportAwardActivity.this, "服务器异常，提交失败~");
                }
                tvSubmit.setText("提交");
                tvSubmit.setEnabled(true);
            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(ReportAwardActivity.this, "网络异常，提交失败~");
                if (isFinishing()) {
                    return;
                }
                tvSubmit.setText("提交");
                tvSubmit.setEnabled(true);
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
