package com.longhoo.net.partyaffairs.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.longhoo.net.MyApplication;
import com.longhoo.net.R;
import com.longhoo.net.manageservice.adapter.PostGridAdapter;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.DisplayUtil;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.utils.httprequest.OkHttpCallback;
import com.longhoo.net.utils.httprequest.OkHttpUtil;
import com.longhoo.net.widget.LoadingDialog;
import com.longhoo.net.widget.base.BaseActivity;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;
import com.luck.picture.lib.entity.LocalMedia;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ${CC} on 2017/12/25.
 */

public class PartyDisciplineSupervisionActivity extends BaseActivity implements PostGridAdapter.onAddPicClickListener, PostGridAdapter.OnItemClickListener {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.tv_post)
    TextView tvPost;
    @BindView(R.id.lv_allview)
    LinearLayout lvAllview;
    @BindView(R.id.check_box_1)
    CheckBox checkBox1;
    @BindView(R.id.check_box_2)
    CheckBox checkBox2;
    private PostGridAdapter adapter;
    //private List<LocalMedia> picList = new ArrayList<>();
    private List<LocalMedia> selectList = new ArrayList<>();
    LoadingDialog mLoadingDialog;
    private List<String> sidList = new ArrayList<>();
    private List<String> nameList = new ArrayList<>();

    @Override
    protected int getContentId() {
        return R.layout.activity_partydisciplinesupervision;
    }

    /**
     * 发布
     */
    private void doPost() {

//        党纪监督提交接口：
//        http://test.025nj.com/dangjian/index.php?m=Api&/Discipline/index
//        传值：content:内容； isfile 是否有文件上传  0无  1图片 2有视频；pic[]/video：文件域名称(pic图片，video视频)
//        返回：成功或失败
        String strIsFile = "";
        List<File> list = new ArrayList<>();
        if (isImage) {
            strIsFile = "1";

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
        } else {
            strIsFile = "2";
            File file = new File(selectList.get(0).getPath());
            ULog.e("cc", selectList.get(0).getPath());
            list.add(file);
        }


//        tvPost.setText("正在发布中...");
        Map<String, String> params = new HashMap<>();
        MyApplication App = (MyApplication) getApplicationContext();
//        params.put("token", App.getmLoginBean().getData().getToken());
        params.put("isfile", strIsFile);
        params.put("content", etContent.getText().toString().trim());
        //选择提交给的人
        String sids = "";
       List<String> tempList = new ArrayList<>();
        if(checkBox1.isChecked()){
           tempList.add(sidList.get(0));
        }
        if(checkBox2.isChecked()){
           tempList.add(sidList.get(1));
        }
        if(tempList.size()>0){
            for(String string:tempList){
                sids+=string+",";
            }
            sids = sids.substring(0,sids.lastIndexOf(","));
        }else{
            ToastUtils.getInstance().showToast(this,"请选择要提交给的人~");
            return;
        }
        params.put("sid",sids);
        String url = Consts.BASE_URL + "/Discipline/public_index";
        tvPost.setText("上传中...");
        tvPost.setEnabled(false);
        mLoadingDialog.show();
        OkHttpUtil.getInstance().doAsyncMultiUpload2(url, params, list, strIsFile, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                ULog.e("cc", "发布response" + response);
                tvPost.setText("立即提交");
                tvPost.setEnabled(true);
//                tvPost.setText("发布");
                mLoadingDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    ToastUtils.getInstance().showToast(PartyDisciplineSupervisionActivity.this, jsonObject.optString("msg"));
                    if (TextUtils.equals(jsonObject.optString("code"), "0")) {
                        Intent intent = getIntent();
                        setResult(100, intent);
                        finish();
                    } else if (jsonObject.optString("code").equals("3")) {
                        Intent intent = getIntent();
                        setResult(100, intent);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtils.getInstance().showToast(PartyDisciplineSupervisionActivity.this, "服务器异常~");
                }
            }

            @Override
            public void onFailure(String errorMsg) {
//                tvPost.setText("发布");
                mLoadingDialog.dismiss();
                tvPost.setText("立即提交");
                tvPost.setEnabled(true);
                ToastUtils.getInstance().showToast(PartyDisciplineSupervisionActivity.this, "网络异常~");
            }
        });
    }

    private void getSelectPerson(){
        String url = Consts.BASE_URL+"/Application/getDiscipline";
        Map<String,String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                ULog.e("提交给："+response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.optString("code");
                    String msg = jsonObject.optString("msg");
                    JSONArray info = jsonObject.optJSONArray("data");

                    if (info != null) {
                        int size = info.length();
                        sidList.clear();
                        nameList.clear();
                        for(int i=0;i<size;i++){
                            JSONObject data = (JSONObject) info.opt(i);
                            sidList.add(data.optString("uid"));
                            nameList.add(data.optString("name"));
                        }
                    }
                   if(TextUtils.equals("0",code)){
                        //成功
                       if(nameList.size()>=2){
                           checkBox1.setText(nameList.get(0));
                           checkBox2.setText(nameList.get(1));
                       }
                   }else{
                       ToastUtils.getInstance().showToast(PartyDisciplineSupervisionActivity.this,msg);
                   }
                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtils.getInstance().showToast(PartyDisciplineSupervisionActivity.this,"解析异常~");
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(PartyDisciplineSupervisionActivity.this,"网络异常~");
            }
        });
    }
    @Override
    protected void initViews() {
        if (getIntent() != null) {
            isImage = getIntent().getBooleanExtra("image", true);
            isVideo = getIntent().getBooleanExtra("video", false);
            maxCount = getIntent().getIntExtra("count", 6);
        }


        mLoadingDialog = new LoadingDialog(this);

        tvPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doPost();
            }
        });
        GridLayoutManager manager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, DisplayUtil.dp2px(this, 4), false));
        adapter = new PostGridAdapter(this, this);
        adapter.setList(selectList);
        adapter.setSelectMax(maxCount);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        getSelectPerson();
    }

    @Override
    protected void initToolbar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        tvTitle.setText("党纪监督");
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

    boolean isImage = true;
    boolean isVideo = false;
    int maxCount = 6;


    @Override
    public void onAddPicClick() {
        // 进入相册 以下是例子：用不到的api可以不写
        if (isImage) {
            PictureSelector.create(PartyDisciplineSupervisionActivity.this)


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
        } else {

            PictureSelector.create(PartyDisciplineSupervisionActivity.this)
                    .openGallery(PictureMimeType.ofVideo())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                    .theme(R.style.picture_default_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                    .maxSelectNum(1)// 最大图片选择数量 int
                    .minSelectNum(0)// 最小选择数量 int
                    .imageSpanCount(3)// 每行显示个数 int
                    .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                    .previewImage(false)// 是否可预览图片 true or false
                    .previewVideo(true)// 是否可预览视频 true or false
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
                    .videoMinSecond(1)// 显示多少秒以内的视频or音频也可适用 int
                    .recordVideoSecond(10)//视频秒数录制 默认60s int
                    .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code

        }

    }

    @Override
    public void onItemClick(int position, View v) {

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
