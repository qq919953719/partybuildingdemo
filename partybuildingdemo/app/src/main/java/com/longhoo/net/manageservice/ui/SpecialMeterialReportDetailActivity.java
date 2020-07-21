package com.longhoo.net.manageservice.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.longhoo.net.MyApplication;
import com.longhoo.net.R;
import com.longhoo.net.manageservice.bean.SpecialMaterialReportBean;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.FileUtils;
import com.longhoo.net.utils.GlideManager;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.httprequest.OkHttpCallback;
import com.longhoo.net.utils.httprequest.OkHttpUtil;
import com.longhoo.net.widget.MyDialog;
import com.longhoo.net.widget.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * 专项材料上报详情
 */
public class SpecialMeterialReportDetailActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_deadline)
    TextView tvDeadline;
    @BindView(R.id.tv_require)
    TextView tvRequire;
    @BindView(R.id.tv_object)
    TextView tvObject;
    @BindView(R.id.tv_report_tag)
    TextView tvReportTag;
    @BindView(R.id.fl_report_tag)
    FrameLayout flReportTag;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.tv_report_title)
    TextView tvReportTitle;
    @BindView(R.id.iv_file)
    ImageView ivFile;
    @BindView(R.id.tv_file)
    TextView tvFile;
    @BindView(R.id.ll_file)
    LinearLayout llFile;
    @BindView(R.id.video_player)
    JZVideoPlayerStandard videoPlayer;
    @BindView(R.id.iv_pic_live)
    ImageView ivPicLive;
    @BindView(R.id.fl_container)
    FrameLayout flContainer;
    @BindView(R.id.tv_read)
    TextView tvRead;
    @BindView(R.id.lv_read)
    LinearLayout lvRead;
    @BindView(R.id.tv_already_read)
    TextView tvAlreadyRead;
    @BindView(R.id.lv_already_read)
    LinearLayout lvAlreadyRead;
    private String filePath = "";
    private String fileName = "";
    private String fileSize = "";

    private String sid;
    private List<SpecialMaterialReportBean.DataBeanX.DataBean.PutDataBean> dataList = new ArrayList<>();
    private MyAdapter adapter;

    public static void goTo(Context context, String sid) {
        Intent intent = new Intent(context, SpecialMeterialReportDetailActivity.class);
        intent.putExtra("sid", sid);
        context.startActivity(intent);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_special_meterial_report_detail;
    }

    @Override
    protected void initViews() {
        if (getIntent() != null) {
            sid = getIntent().getStringExtra("sid");
        }
        flReportTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDialog dialog = new MyDialog(SpecialMeterialReportDetailActivity.this, R.style.MyDialogStyle);
                String[] arrays = new String[]{"全部", "已提报", "未提报"};
                dialog.setMyItems(arrays);
                dialog.setMyDialogItemClickListener(new MyDialog.MyDialogItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (position == 0) { //全部
                            getData(false, "0");
                            tvReportTag.setBackground(getResources().getDrawable(R.drawable.bg_red_solid_13));
                            tvReportTag.setText("全部");
                        } else if (position == 1) { //已提报
                            getData(false, "1");
                            tvReportTag.setBackground(getResources().getDrawable(R.drawable.bg_red_solid_13));
                            tvReportTag.setText("已提报");
                        } else {  //未提报
                            getData(false, "2");
                            tvReportTag.setBackground(getResources().getDrawable(R.drawable.bg_grey_solid_13));
                            tvReportTag.setText("未提报");
                        }
                    }
                });
                dialog.show();
            }
        });
        adapter = new MyAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        getData(true, "1");
    }

    @Override
    protected void initToolbar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.left_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    /**
     * @param isInit
     * @param type   0 全部  1已提交  2未提交
     */
    private void getData(final boolean isInit, String type) {
        String url = Consts.BASE_URL + "/Inforeporting/infoReportingDetails";
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("sid", sid);
        params.put("type", type);
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                Gson gson = new Gson();
                List<SpecialMaterialReportBean.DataBeanX.DataBean.PutDataBean> tempList = null;
                try {
                    SpecialMaterialReportBean reportBean = gson.fromJson(response, SpecialMaterialReportBean.class);
                    String code = reportBean.getCode();
                    String msg = reportBean.getMsg();
                    if (!TextUtils.equals(code, "0")) {
                        ToastUtils.getInstance().showToast(SpecialMeterialReportDetailActivity.this, msg);
                        return;
                    }


                    MyApplication app = (MyApplication) getApplicationContext();
                    if (app.getmLoginBean().getData().getUid().equals(reportBean.getData().getData().getAdminid() + "") && (!reportBean.getData().getData().getBack().isEmpty())) {
                        tvRead.setText(reportBean.getData().getData().getBack());
                        tvAlreadyRead.setText(reportBean.getData().getData().getUnback());
                        lvRead.setVisibility(View.VISIBLE);
                        lvAlreadyRead.setVisibility(View.VISIBLE);
                    } else {
                        lvRead.setVisibility(View.GONE);
                        lvAlreadyRead.setVisibility(View.GONE);
                    }

                    if (isInit) {
                        String title = reportBean.getData().getData().getTitle();
                        if (title != null && title.length() > 15) {
                            tvTitle.setText(title.substring(0, 15) + "...");
                        } else {
                            tvTitle.setText(title);
                        }
                        tvDeadline.setText(reportBean.getData().getData().getEnd_time());
                        tvRequire.setText(Html.fromHtml(reportBean.getData().getData().getContent() + ""));
                        tvObject.setText(reportBean.getData().getData().getRep_obj());
                        if (!TextUtils.isEmpty(reportBean.getData().getData().getVideourl())) {
                            videoPlayer.setVisibility(View.VISIBLE);
                            videoPlayer.setUp(reportBean.getData().getData().getVideourl(), JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "");
                            videoPlayer.setIsLiveStream(true);
                            //videoPlayer.startButton.performClick();
                            if (!TextUtils.isEmpty(reportBean.getData().getData().getVideoimg())) {
                                GlideManager.getInstance().load(SpecialMeterialReportDetailActivity.this, reportBean.getData().getData().getVideoimg(), videoPlayer.thumbImageView);
                            }
                            videoPlayer.fullscreenButton.setVisibility(View.GONE);
                            ivPicLive.setVisibility(View.GONE);
                        } else {

                            flContainer.setVisibility(View.GONE);
                        }


                        tvReportTitle.setText(reportBean.getData().getData().getTitle());
                        List<String> files = reportBean.getData().getData().getFiles();
                        if (files != null && files.size() > 0) {
                            filePath = files.get(0);
                            fileName = reportBean.getData().getData().getFilename();
                            llFile.setVisibility(View.VISIBLE);
                            tvFile.setText(fileName);
                            setFileImage(filePath);
                        }
                    }
                    tempList = reportBean.getData().getData().getPut_data();
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
                if (tempList != null) {
                    dataList.clear();
                    dataList.addAll(tempList);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(SpecialMeterialReportDetailActivity.this, "网络异常~");
            }
        });
    }

    private void setFileImage(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return;
        }
        String fileType = FileUtils.getFileType(filePath);
        if (TextUtils.equals(fileType, ".doc") || TextUtils.equals(fileType, ".docx")) {
            ivFile.setImageResource(R.drawable.file_word);
        } else if (TextUtils.equals(fileType, ".xlsx") || TextUtils.equals(fileType, ".xls")) {
            ivFile.setImageResource(R.drawable.file_excel);
        } else if (TextUtils.equals(fileType, ".ppt") || TextUtils.equals(fileType, ".pptx")) {
            ivFile.setImageResource(R.drawable.file_ppt);
        } else if (TextUtils.equals(fileType, ".pdf")) {
            ivFile.setImageResource(R.drawable.file_pdf);
        } else {
            ivFile.setImageResource(R.drawable.file_normal);
        }
    }

    @OnClick({R.id.ll_file})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_file:
                //WebViewActivity.goToWebView(this,"https://view.officeapps.live.com/op/view.aspx?src="+filePath,fileName,false);
                Intent intent = new Intent(this, OpenFileActivity.class);
                intent.putExtra("file_path", filePath);
                intent.putExtra("file_name", fileName);
                intent.putExtra("file_size", fileSize);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    public class MyAdapter extends RecyclerView.Adapter {
        private Context context;

        public MyAdapter(Context context) {
            this.context = context;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_special_metarial_list, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof MyViewHolder) {
                ((MyViewHolder) holder).tvItemName.setText(dataList.get(position).getRealname());
                ((MyViewHolder) holder).tvItemTime.setText(dataList.get(position).getTime());
                ((MyViewHolder) holder).tvItemOname.setText(dataList.get(position).getRealname());
            }
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.tv_item_name)
            TextView tvItemName;
            @BindView(R.id.tv_item_time)
            TextView tvItemTime;
            @BindView(R.id.tv_item_oname)
            TextView tvItemOname;

            public MyViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
