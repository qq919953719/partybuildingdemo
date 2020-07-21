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
import android.widget.TextView;

import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.longhoo.net.R;
import com.longhoo.net.manageservice.adapter.PostGridAdapter;
import com.longhoo.net.study.bean.TrainReportDetailsBean;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.DisplayUtil;
import com.longhoo.net.utils.EnterCheckUtil;
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
 * 自主培训详情
 */
public class TrainReportDetailsActivity extends BaseActivity implements PostGridAdapter.OnItemClickListener {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_train_name)
    TextView tvTrainName;
    @BindView(R.id.tv_train_address)
    TextView tvTrainAddress;
    @BindView(R.id.tv_train_time_s)
    TextView tvTrainTimeS;
    @BindView(R.id.tv_train_time_e)
    TextView tvTrainTimeE;
    @BindView(R.id.tv_learn_time)
    TextView tvLearnTime;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.tv_train_content)
    TextView tvTrainContent;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private String title = ""; //课程名
    private String id = ""; //课程id
    private PostGridAdapter adapter;
    private List<LocalMedia> selectList = new ArrayList<>();

    public static void goTo(Context context, String id) {
        Intent intent = new Intent(context, TrainReportDetailsActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_train_report_details;
    }

    @Override
    protected void initViews() {
        if (getIntent() != null) {
            id = getIntent().getStringExtra("id");
        }
//        if (title.length() > 15) {
//            title = title.substring(0, 15) + "...";
//        }
        tvTitle.setText( "自主上报");
        GridLayoutManager manager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, DisplayUtil.dp2px(this, 4), false));
        adapter = new PostGridAdapter(this, null);
        adapter.setList(selectList);
        adapter.setHideSelect(true);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        getData();
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

    private void getData() {
        String url = Consts.BASE_URL + "/Study/getCourseReg";
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("id", id + "");
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                if (isFinishing()) {
                    return;
                }
                Gson gson = new Gson();
                try {
                    TrainReportDetailsBean bean = gson.fromJson(response, TrainReportDetailsBean.class);
                    String code = bean.getCode();
                    if (!TextUtils.equals(code, "0")) {
                        ToastUtils.getInstance().showToast(TrainReportDetailsActivity.this, "获取数据失败~");
                        return;
                    }
                    tvTrainName.setText(bean.getData().getTitle());
                    tvTrainAddress.setText(bean.getData().getAddr());
                    tvTrainTimeS.setText(bean.getData().getTrain_starttime());
                    tvTrainTimeE.setText(bean.getData().getTrain_endtime());
                    tvLearnTime.setText(bean.getData().getClass_hour()+"");
                    tvCompany.setText(bean.getData().getHost_unit());
                    tvTrainContent.setText(bean.getData().getContent());
                    List<String> tempList = bean.getData().getUploads();
                    if (tempList.size() > 0) {
                        selectList.clear();
                        for (String path : tempList) {
                            selectList.add(new LocalMedia(path, 0, PictureMimeType.ofImage(), ""));
                        }
                        adapter.notifyDataSetChanged();
                    }
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(TrainReportDetailsActivity.this, "网络异常，提交失败~");
                if (isFinishing()) {
                    return;
                }
            }
        });
    }
}
