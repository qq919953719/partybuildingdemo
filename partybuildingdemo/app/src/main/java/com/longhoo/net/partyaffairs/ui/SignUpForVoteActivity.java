package com.longhoo.net.partyaffairs.ui;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.longhoo.net.R;
import com.longhoo.net.manageservice.adapter.PostGridAdapter;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.utils.httprequest.OkHttpCallback;
import com.longhoo.net.utils.httprequest.OkHttpUtil;
import com.longhoo.net.widget.base.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class SignUpForVoteActivity extends BaseActivity implements PostGridAdapter.onAddPicClickListener,PostGridAdapter.OnItemClickListener{

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fields_panel)
    LinearLayout fieldsPanel;
    @BindView(R.id.tv_sign_up)
    TextView tvSignUp;
    @BindView(R.id.ll_pic_select)
    LinearLayout llPicSelect;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    //private VoteOptionBean.DataBean.AddBean.FieldsBean fieldsBean;
    private String cid;
    private String title1, title2, title3, title4, title5, content1, content2, uploads;
    private EditText[] editTexts = new EditText[7];
    private PostGridAdapter adapter;
    private List<LocalMedia> selectList = new ArrayList<>();

    @Override
    protected int getContentId() {
        return R.layout.activity_sign_up_for_vote;
    }

    @Override
    protected void initViews() {
//        if (getIntent() != null) {
//            fieldsBean = (VoteOptionBean.DataBean.AddBean.FieldsBean) getIntent().getSerializableExtra("vote_fields");
//            cid = getIntent().getStringExtra("cid");
//        }
//        MyApplication app = (MyApplication)getApplicationContext();
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtil.dp2px(this, 88));
//        lp.bottomMargin = DisplayUtil.dp2px(this, 15);
//        for (int i = 0; i < 7; i++) {
//            editTexts[i] = new EditText(this);
//            editTexts[i].setLayoutParams(lp);
//            editTexts[i].setHintTextColor(Color.parseColor("#b3b3b3"));
//            editTexts[i].setTextSize(13);
//            editTexts[i].setIncludeFontPadding(false);
//            editTexts[i].setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_grey_solid_25));
//            editTexts[i].setPadding(DisplayUtil.dp2px(this, 15), DisplayUtil.dp2px(this, 13), DisplayUtil.dp2px(this, 15), DisplayUtil.dp2px(this, 13));
//        }
//        if (fieldsBean != null) {
//            title1 = fieldsBean.getTitle1();
//            title2 = fieldsBean.getTitle2();
//            title3 = fieldsBean.getTitle3();
//            title4 = fieldsBean.getTitle4();
//            title5 = fieldsBean.getTitle5();
//            content1 = fieldsBean.getContent1();
//            content2 = fieldsBean.getContent2();
//            uploads = fieldsBean.getUploads();
//            //ToastUtils.getInstance().showToast(this,title1+" "+title2+" "+title3+" "+title4+" "+title4+" "+content1+" "+content2+" ck");
//            if (!TextUtils.isEmpty(title1)) {
//                editTexts[0].setHint(title1);
//                fieldsPanel.addView(editTexts[0]);
//            }
//            if (!TextUtils.isEmpty(title2)) {
//                editTexts[1].setHint(title2);
//                fieldsPanel.addView(editTexts[1]);
//            }
//            if (!TextUtils.isEmpty(title3)) {
//                editTexts[2].setHint(title3);
//                fieldsPanel.addView(editTexts[2]);
//            }
//            if (!TextUtils.isEmpty(title4)) {
//                editTexts[3].setHint(title4);
//                fieldsPanel.addView(editTexts[3]);
//            }
//            if (!TextUtils.isEmpty(title5)) {
//                editTexts[4].setHint(title5);
//                fieldsPanel.addView(editTexts[4]);
//            }
//            if (!TextUtils.isEmpty(content1)) {
//                editTexts[5].setHint(content1);
//                fieldsPanel.addView(editTexts[5]);
//            }
//            if (!TextUtils.isEmpty(content2)) {
//                editTexts[6].setHint(content2);
//                fieldsPanel.addView(editTexts[6]);
//            }
//        }
//        tvSignUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                doSignUp();
//            }
//        });
//        GridLayoutManager manager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
//        recyclerView.setLayoutManager(manager);
//        recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, DisplayUtil.dp2px(this,4),false));
//        adapter = new PostGridAdapter(this, this);
//        adapter.setList(selectList);
//        adapter.setSelectMax(6);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    protected void initToolbar() {
        tvTitle.setText("我要报名");
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
        String url = Consts.BASE_URL + "/Score/add_voteitem";
        Map<String, String> params = new HashMap<>();
        params.put("uid", EnterCheckUtil.getInstance().getUid_Token()[0]);
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        if (!TextUtils.isEmpty(title1)) {
            if(TextUtils.isEmpty(editTexts[0].getText().toString().trim())){
                editTexts[0].requestFocus();
                ToastUtils.getInstance().showToast(this,title1+"字段不能为空！");
                return;
            }
            params.put("title1",editTexts[0].getText().toString().trim());
        }
        if (!TextUtils.isEmpty(title2)) {
            if(TextUtils.isEmpty(editTexts[1].getText().toString().trim())){
                editTexts[1].requestFocus();
                ToastUtils.getInstance().showToast(this,title2+"字段不能为空！");
                return;
            }
            params.put("title2",editTexts[1].getText().toString().trim());
        }
        if (!TextUtils.isEmpty(title3)) {
            if(TextUtils.isEmpty(editTexts[2].getText().toString().trim())){
                editTexts[2].requestFocus();
                ToastUtils.getInstance().showToast(this,title3+"字段不能为空！");
                return;
            }
            params.put("title3",editTexts[2].getText().toString().trim());
        }
        if (!TextUtils.isEmpty(title4)) {
            if(TextUtils.isEmpty(editTexts[3].getText().toString().trim())){
                editTexts[3].requestFocus();
                ToastUtils.getInstance().showToast(this,title4+"字段不能为空！");
                return;
            }
            params.put("title4",editTexts[3].getText().toString().trim());
        }
        if (!TextUtils.isEmpty(title5)) {
            if(TextUtils.isEmpty(editTexts[4].getText().toString().trim())){
                editTexts[4].requestFocus();
                ToastUtils.getInstance().showToast(this,title5+"字段不能为空！");
                return;
            }
            params.put("title5",editTexts[4].getText().toString().trim());
        }
        if (!TextUtils.isEmpty(content1)) {
            if(TextUtils.isEmpty(editTexts[5].getText().toString().trim())){
                editTexts[5].requestFocus();
                ToastUtils.getInstance().showToast(this,content1+"字段不能为空！");
                return;
            }
            params.put("content1",editTexts[5].getText().toString().trim());
        }
        if (!TextUtils.isEmpty(content2)) {
            if(TextUtils.isEmpty(editTexts[6].getText().toString().trim())){
                editTexts[6].requestFocus();
                ToastUtils.getInstance().showToast(this,content2+"字段不能为空！");
                return;
            }
            params.put("content2",editTexts[6].getText().toString().trim());
        }
        for (Map.Entry<String, String> entry : params.entrySet()) {
           ULog.e("ck","Key = " + entry.getKey() + ", Value = " + entry.getValue()+"\n");
        }

        List<File> list = new ArrayList<>();
        if(selectList.size()>0){
            for(LocalMedia media:selectList){
                String path = "";
                if (media.isCut() && !media.isCompressed()) {
                    // 裁剪过
                    path = media.getCutPath();
                    ULog.e("ck","裁剪过"+path);
                } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
                    // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                    path = media.getCompressPath();
                    ULog.e("ck","压缩过"+path);
                } else {
                    // 原图
                    path = media.getPath();
                    ULog.e("ck","原图"+path);
                }
                File file = new File(path);
                ULog.e("ck",path);
                list.add(file);
            }
        }
        params.put("type",cid);
        tvSignUp.setText("正在提交中...");
        OkHttpUtil.getInstance().doAsyncMultiUpload(url, params, list,"uploads[]",new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                if(isFinishing()){
                    return;
                }
                ULog.e("ck","报名："+response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.optString("code");
                    String msg = jsonObject.optString("msg");
                    ToastUtils.getInstance().showToast(SignUpForVoteActivity.this,msg);
                    if(TextUtils.equals(jsonObject.optString("code"),"0")){
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtils.getInstance().showToast(SignUpForVoteActivity.this,"服务器异常，提交失败~");
                }
                tvSignUp.setText("提交报名");
            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(SignUpForVoteActivity.this,"网络异常，提交失败~");
                if(isFinishing()){
                    return;
                }
                tvSignUp.setText("提交报名");
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
}
