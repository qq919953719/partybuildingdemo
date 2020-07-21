package com.longhoo.net.manageservice.ui;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.FileUtils;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.utils.httprequest.OkHttpCallback;
import com.longhoo.net.utils.httprequest.OkHttpDownCallBack;
import com.longhoo.net.utils.httprequest.OkHttpUtil;
import com.longhoo.net.widget.base.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class OpenFileActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_file_name)
    TextView tvFileName;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.tv_down)
    TextView tvDown;
    @BindView(R.id.iv_file)
    ImageView ivFile;
    @BindView(R.id.tv_down_other)
    TextView tvDownOther;
    @BindView(R.id.tv_right)
    TextView tvRight;
    private String filePath = "";
    private String fileName = "";
    private String fileSize = "";
    private String tid = "";
    private File localFile;
    private int state = 0; //0：下载  1：可以打开  2：失败

    @Override
    protected int getContentId() {
        return R.layout.activity_open_file;
    }

    @Override
    protected void initViews() {
        if (getIntent() != null) {
            filePath = getIntent().getStringExtra("file_path");
            fileName = getIntent().getStringExtra("file_name");
            fileSize = getIntent().getStringExtra("file_size");
            tid = getIntent().getStringExtra("tid");
        }
        if (TextUtils.isEmpty(filePath)) {
            doGetInfo();
        } else {
            resfreshUI();
        }
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
        tvRight.setText("分享文件");
        tvRight.setVisibility(View.VISIBLE);
    }

    private void downLoadFile() {
        File cachDir = new File(this.getExternalCacheDir(), "file_down");
        if (!cachDir.exists())
            cachDir.mkdir();
        OkHttpUtil.getInstance().downLoadFile(filePath, cachDir.getAbsolutePath(), fileName, new OkHttpDownCallBack() {
            @Override
            public void onDownSuccess(File file) {
                if (isFinishing())
                    return;
                ULog.e("ck", file.getAbsolutePath() + "");
                state = 1;
                progressBar.setVisibility(View.GONE);
                tvDown.setVisibility(View.VISIBLE);
                tvDown.setText("打开文件");
                tvDownOther.setEnabled(true);
                tvDownOther.setTextColor(getResources().getColor(android.R.color.holo_blue_dark));
                tvRight.setEnabled(true);
                tvRight.setTextColor(Color.parseColor("#ffffff"));
                localFile = file;
            }

            @Override
            public void onDownloading(long total, long current) {
                if (isFinishing())
                    return;
                progressBar.setVisibility(View.VISIBLE);
                tvDown.setVisibility(View.GONE);
                float percent = (float) current * progressBar.getMax() / total;
                ULog.e("ck", percent + " " + progressBar.getMax() + " " + total);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    progressBar.setProgress((int) percent, true);
                } else {
                    progressBar.setProgress((int) percent);
                }
            }

            @Override
            public void onDownFailed(String msg) {
                //删除失败的文件
                File cachDir = new File(getExternalCacheDir(), "file_down");
                if (!cachDir.exists())
                    cachDir.mkdir();
                File file = new File(cachDir.getAbsoluteFile(), fileName);
                if (file.exists()) {
                    file.delete();
                }
                if (isFinishing())
                    return;
                state = 2;
                progressBar.setVisibility(View.GONE);
                tvDown.setVisibility(View.VISIBLE);
                tvDown.setText("下载失败");
            }
        });
    }

    @Override
    protected void onDestroy() {
        OkHttpUtil.getInstance().stop();
        super.onDestroy();
    }

    /**
     * 打开文件
     */
    private void openImageFile() {
        if (localFile == null) {
            return;
        }
        ULog.e("cc", FileUtils.getFileMIME(localFile.getAbsolutePath()) + ":MIME");
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(this, "com.longhoo.net.provider", localFile);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(localFile);
        }
        //intent.setDataAndType(uri, FileUtils.getFileMIME(localFile.getAbsolutePath()));
        intent.setType(FileUtils.getFileMIME(localFile.getAbsolutePath()));
        intent.putExtra(Intent.EXTRA_STREAM, uri);
//        startActivity(Intent.createChooser(intent, "分享"));

        //打开指定的一张照片
        Intent intent2 = new Intent();
        intent2.setAction(android.content.Intent.ACTION_VIEW);
        intent2.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent2.setDataAndType(uri, "image/*");
        startActivity(intent2);


    }

    /**
     * 打开文件
     */
    private void openFile() {
        if (localFile == null) {
            return;
        }
        ULog.e("cc", FileUtils.getFileMIME(localFile.getAbsolutePath()) + ":MIME");
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(this, "com.longhoo.net.provider", localFile);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(localFile);
        }
        //intent.setDataAndType(uri, FileUtils.getFileMIME(localFile.getAbsolutePath()));
        intent.setType(FileUtils.getFileMIME(localFile.getAbsolutePath()));
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(intent, "分享"));


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

    private void doGetInfo() {
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("tid", tid);
        OkHttpUtil.getInstance().doAsyncPost(Consts.BASE_URL + "/News/jobDetailsNew", params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                ULog.e("ck", "文件材料：" + tid + "  " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.optString("code");
                    String msg = jsonObject.optString("msg");
                    if (!TextUtils.equals(code, "0")) {
                        ToastUtils.getInstance().showToast(OpenFileActivity.this, msg);
                        finish();
                        return;
                    }
                    JSONObject data = jsonObject.optJSONObject("data");
                    JSONObject news = data.optJSONObject("news");
                    if (news != null) {
                        filePath = news.optString("files");
                        fileName = news.optString("filename");
                        fileSize = news.optString("filesize");
                        resfreshUI();
                    } else {
                        ToastUtils.getInstance().showToast(OpenFileActivity.this, "该文件已被删除！");
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtils.getInstance().showToast(OpenFileActivity.this, "解析异常~");
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(OpenFileActivity.this, "网络异常~");
            }
        });
    }

    private void resfreshUI() {
        String title = fileName;
        if (title != null) {
            if (title.length() >= 12) {
                title = title.substring(0, 9) + "...";
            }
            tvTitle.setText(title);
        }
        tvFileName.setText(fileName);
        setFileImage(filePath);
        File cachDir = new File(getExternalCacheDir(), "file_down");
        if (!cachDir.exists())
            cachDir.mkdir();
        File file = new File(cachDir.getAbsolutePath(), fileName);
        if (!TextUtils.isEmpty(fileName) && file.exists()) {
            state = 1;
            localFile = file;
            tvDown.setText("打开文件");
            ULog.e("ck", "打开文件");
        }
        tvDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state == 0) {
                    downLoadFile();
                } else if (state == 1) {
                    try {
                        String fileType = FileUtils.getFileType(localFile.getAbsolutePath());
                        if (TextUtils.equals(fileType, ".jpg") || TextUtils.equals(fileType, ".png")) {
                            openFile();
//                            openImageFile();
                        } else {


                            if (TextUtils.equals(fileType, ".doc") || TextUtils.equals(fileType, ".docx")) {
                                PreviewOfficeActivity.goTo(OpenFileActivity.this, localFile.getAbsolutePath(), fileName);
                            } else if (TextUtils.equals(fileType, ".xlsx") || TextUtils.equals(fileType, ".xls")) {
                                PreviewOfficeActivity.goTo(OpenFileActivity.this, localFile.getAbsolutePath(), fileName);
                            } else if (TextUtils.equals(fileType, ".ppt") || TextUtils.equals(fileType, ".pptx")) {
                                PreviewOfficeActivity.goTo(OpenFileActivity.this, localFile.getAbsolutePath(), fileName);
                            } else if (TextUtils.equals(fileType, ".pdf")) {
                                PreviewOfficeActivity.goTo(OpenFileActivity.this, localFile.getAbsolutePath(), fileName);
                            } else {
                                openFile();
                            }


                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        ToastUtils.getInstance().showToast(OpenFileActivity.this, "打开文件失败！");
                    }
                }
            }
        });
        if (state == 0) {
            tvDownOther.setEnabled(false);
            tvDownOther.setTextColor(Color.parseColor("#c5c5c5"));
            tvRight.setEnabled(false);
            tvRight.setTextColor(Color.parseColor("#77ffffff"));
        } else if (state == 1) {
            tvDownOther.setEnabled(true);
            tvDownOther.setTextColor(getResources().getColor(android.R.color.holo_blue_dark));
            tvRight.setEnabled(true);
            tvRight.setTextColor(Color.parseColor("#ffffff"));
        }
        tvDownOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    openFile();
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.getInstance().showToast(OpenFileActivity.this, "打开文件失败！");
                }
            }
        });
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    openFile();
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.getInstance().showToast(OpenFileActivity.this, "打开文件失败！");
                }
            }
        });
    }

}
