package com.longhoo.net.study.ui;

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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.longhoo.net.R;
import com.longhoo.net.manageservice.adapter.PostGridAdapter;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.DisplayUtil;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.MessageEvent;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.utils.Utils;
import com.longhoo.net.utils.httprequest.OkHttpCallback;
import com.longhoo.net.utils.httprequest.OkHttpUtil;
import com.longhoo.net.widget.GridSpacingItemDecoration;
import com.longhoo.net.widget.base.BaseActivity;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 自主上报校外培训/校外培训登记详情
 */
public class OffCampusTrainReportActivity extends BaseActivity implements PostGridAdapter.onAddPicClickListener, PostGridAdapter.OnItemClickListener {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_train_name)
    EditText etTrainName;
    @BindView(R.id.et_train_address)
    EditText etTrainAddress;
    @BindView(R.id.tv_train_time_s)
    TextView tvTrainTimeS;
    @BindView(R.id.tv_train_time_e)
    TextView tvTrainTimeE;
    @BindView(R.id.et_train_content)
    EditText etTrainContent;
    @BindView(R.id.ll_pic_select)
    LinearLayout llPicSelect;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.et_learn_time)
    EditText etLearnTime;
    @BindView(R.id.et_company)
    EditText etCompany;

    public static final int TAG_REPORT_SELF = 0; //自主上报培训
    public static final int TAG_REPORT_OFFICIAL = 1; //列表详情点击，官方培训登记
    private int tag;
    private String title = ""; //课程名
    private String id = ""; //课程id
    private PostGridAdapter adapter;
    private List<LocalMedia> selectList = new ArrayList<>();

    public static void goTo(Context context, int tag, String title, String id) {
        Intent intent = new Intent(context, OffCampusTrainReportActivity.class);
        intent.putExtra("tag", tag);
        intent.putExtra("title", title);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_train_report;
    }

    @Override
    protected void initViews() {
        if (getIntent() != null) {
            tag = getIntent().getIntExtra("tag", 0);
            title = getIntent().getStringExtra("title");
            id = getIntent().getStringExtra("id");
        }
        if (tag == TAG_REPORT_OFFICIAL) {
            etTrainName.setText(title);
            etTrainName.setEnabled(false);
            tvTitle.setText("上报校外培训");
        } else if (tag == TAG_REPORT_SELF) {
            tvTitle.setText("自主上报");
        }
        GridLayoutManager manager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, DisplayUtil.dp2px(this, 4), false));
        adapter = new PostGridAdapter(this, this);
        adapter.setList(selectList);
        adapter.setSelectMax(6);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doCommit();
            }
        });

        InputFilter inputFilter=new InputFilter() {
            Pattern pattern = Pattern.compile("[^a-zA-Z0-9\\u4E00-\\u9FA5_,.?!:;…~_\\-\"\"/@*+'()<>{}/[/]()<>{}\\[\\]=%&$|\\/♀♂#¥£¢€\"^` ，。？！：；……～“”、“（）”、（——）‘’＠‘·’＆＊＃《》￥《〈〉》〈＄〉［］￡［］｛｝｛｝￠【】【】％〖〗〖〗／〔〕〔〕＼『』『』＾「」「」｜﹁﹂｀．]");
            @Override
            public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                Matcher matcher=  pattern.matcher(charSequence);
                if(!matcher.find()){
                    return null;
                }else{
                    ToastUtils.getInstance().showToast(OffCampusTrainReportActivity.this,"不允许输入非法字符！");
                    return "";
                }

            }
        };
        etTrainName.setFilters(new InputFilter[]{inputFilter,new InputFilter.LengthFilter(50)});
        etTrainAddress.setFilters(new InputFilter[]{inputFilter,new InputFilter.LengthFilter(50)});
        etLearnTime.setFilters(new InputFilter[]{inputFilter,new InputFilter.LengthFilter(5)});
        etCompany.setFilters(new InputFilter[]{inputFilter,new InputFilter.LengthFilter(50)});
        etTrainContent.setFilters(new InputFilter[]{inputFilter,new InputFilter.LengthFilter(500)});
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

    @OnClick({R.id.tv_train_time_s, R.id.tv_train_time_e})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_train_time_s:
                pickData(0);
                break;
            case R.id.tv_train_time_e:
                pickData(1);
                break;
        }
    }

    private void pickData(final int type) {
        DatePickDialog dialog = new DatePickDialog(this);
        //设置上下年分限制
        dialog.setYearLimt(5);
        //设置标题
        dialog.setTitle("选择时间");
        //设置类型
        dialog.setType(DateType.TYPE_ALL);
        //设置消息体的显示格式，日期格式
        dialog.setMessageFormat("yyyy-MM-dd HH:mm");
        //设置选择回调
        dialog.setOnChangeLisener(null);
        //设置点击确定按钮回调
        dialog.setOnSureLisener(new OnSureLisener() {
            @Override
            public void onSure(Date date) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                if (type == 0) {
                    tvTrainTimeS.setText(simpleDateFormat.format(date));
                } else {
                    tvTrainTimeE.setText(simpleDateFormat.format(date));
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

    private void doCommit() {
        String trainName = etTrainName.getText().toString().trim();
        String trainAddress = etTrainAddress.getText().toString().trim();
        String trainStartTime = tvTrainTimeS.getText().toString().trim();
        String trainEndTime = tvTrainTimeE.getText().toString().trim();
        String learnTime = etLearnTime.getText().toString().trim();
        String trainContent = etTrainContent.getText().toString().trim();
        String company = etCompany.getText().toString().trim();
        if (TextUtils.isEmpty(trainName)) {
            ToastUtils.getInstance().showToast(this, "请输入培训名称！");
            etTrainName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(trainAddress)) {
            ToastUtils.getInstance().showToast(this, "请输入培训地点！");
            etTrainAddress.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(trainStartTime)) {
            ToastUtils.getInstance().showToast(this, "请输入培训开始时间！");
            return;
        }
        if (TextUtils.isEmpty(trainEndTime)) {
            ToastUtils.getInstance().showToast(this, "请输入培训结束时间！");
            return;
        }
        long trainTimeS = Utils.getTimestap(tvTrainTimeS.getText().toString().trim(),"yyyy-MM-dd HH:mm");
        long trainTimeE = Utils.getTimestap(tvTrainTimeE.getText().toString().trim(),"yyyy-MM-dd HH:mm");
        if(trainTimeS>trainTimeE){
            ToastUtils.getInstance().showToast(this,"培训开始时间不能大于结束时间！");
            return;
        }
        if (TextUtils.isEmpty(learnTime)) {
            ToastUtils.getInstance().showToast(this, "请输入学时！");
            etLearnTime.requestFocus();
            return;
        }
        try{
            int learnTimeInt = Integer.parseInt(learnTime);
            if (learnTimeInt <= 0) {
                ToastUtils.getInstance().showToast(this, "学时不能为0！");
                etLearnTime.requestFocus();
                return;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        if (TextUtils.isEmpty(company)) {
            ToastUtils.getInstance().showToast(this, "请输入举办单位！");
            etCompany.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(trainContent)) {
            ToastUtils.getInstance().showToast(this, "请输入培训内容！");
            etTrainContent.requestFocus();
            return;
        }

        String url = Consts.BASE_URL + "/Study/setCourseReg";
        Map<String, String> params = new HashMap<>();
        if (tag == TAG_REPORT_SELF) {
            params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
            params.put("type", "3");
            params.put("title", trainName);
            params.put("content", trainContent);
            params.put("starttime", trainStartTime);
            params.put("endtime", trainEndTime);
            params.put("addr", trainAddress);
            params.put("class_hour",learnTime);
            params.put("host_unit",company);
        } else if (tag == TAG_REPORT_OFFICIAL) {
            params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
            params.put("id", id);
            params.put("type", "2");
            params.put("title", trainName);
            params.put("content", trainContent);
            params.put("starttime", trainStartTime);
            params.put("endtime", trainEndTime);
            params.put("addr", trainAddress);
            params.put("host_unit",company);
        }
        if (selectList.size() > 0) {
            params.put("isfile", "1");
        }

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
        OkHttpUtil.getInstance().doAsyncMultiUpload(url, params, list, "uploads[]", new OkHttpCallback() {
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
                    ToastUtils.getInstance().showToast(OffCampusTrainReportActivity.this, msg);
                    if (TextUtils.equals(code, "0")) {
                        MessageEvent event = new MessageEvent();
                        event.msgType = MessageEvent.MSG_REFRESH_STUDY_ARCHIVES;
                        EventBus.getDefault().post(event);
                        onBackPressed();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtils.getInstance().showToast(OffCampusTrainReportActivity.this, "服务器异常，提交失败~");
                }
                tvSubmit.setText("提交");
            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(OffCampusTrainReportActivity.this, "网络异常，提交失败~");
                if (isFinishing()) {
                    return;
                }
                tvSubmit.setText("立即提交");
            }
        });
    }

}
