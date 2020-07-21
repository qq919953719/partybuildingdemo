package com.longhoo.net.manageservice.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.widget.base.BaseActivity;
import com.tencent.smtt.sdk.TbsReaderView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PreviewOfficeActivity extends BaseActivity implements TbsReaderView.ReaderCallback {
    TbsReaderView mTbsReaderView;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.lv_allview)
    LinearLayout lvAllview;
    String filePath = "";
    String fileName = "";

    @Override
    protected int getContentId() {
        return R.layout.activity_preview_office;
    }

    private String parseFormat(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public static void goTo(Context context, String filePath, String fileName) {
        Intent intent = new Intent(context, PreviewOfficeActivity.class);
        intent.putExtra("file_path", filePath);
        intent.putExtra("file_name", fileName);
        context.startActivity(intent);
    }

    @Override
    protected void initViews() {

        mTbsReaderView = new TbsReaderView(this, this);

        lvAllview.addView(mTbsReaderView, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        if (getIntent() != null) {
            filePath = getIntent().getStringExtra("file_path");
            fileName = getIntent().getStringExtra("file_name");
            Bundle bundle = new Bundle();
            bundle.putString("filePath", filePath);
            bundle.putString("tempPath", Environment.getExternalStorageDirectory().getPath());
            boolean result = mTbsReaderView.preOpen(parseFormat(fileName), false);
            if (result) {
                mTbsReaderView.openFile(bundle);
            }
            String title = fileName;
            if (title != null) {
                if (title.length() >= 12) {
                    title = title.substring(0, 9) + "...";
                }
                tvTitle.setText(title);
            }
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
    }

    @Override
    protected void onDestroy() {
        mTbsReaderView.onStop();
        super.onDestroy();
    }

    @Override
    public void onCallBackAction(Integer integer, Object o, Object o1) {

    }
}
