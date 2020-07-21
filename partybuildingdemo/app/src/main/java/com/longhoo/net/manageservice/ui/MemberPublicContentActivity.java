package com.longhoo.net.manageservice.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.longhoo.net.R;
import com.longhoo.net.manageservice.adapter.GridViewAdapter;
import com.longhoo.net.manageservice.bean.MemberPublicListBean;
import com.longhoo.net.utils.DisplayUtil;
import com.longhoo.net.utils.Utils;
import com.longhoo.net.widget.CustomGridView;
import com.longhoo.net.widget.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MemberPublicContentActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_membername)
    TextView tvMembername;
    @BindView(R.id.tv_state)
    TextView tvState;
    @BindView(R.id.edt_name)
    TextView edtName;
    @BindView(R.id.edt_phone)
    TextView edtPhone;
    @BindView(R.id.edt_identity_number)
    TextView edtIdentityNumber;
    @BindView(R.id.edt_reson)
    TextView edtReson;
    @BindView(R.id.edt_party)
    TextView edtParty;
    @BindView(R.id.edt_recomend_person)
    TextView edtRecomendPerson;
    @BindView(R.id.edt_unit)
    TextView edtUnit;
    @BindView(R.id.edt_adress)
    TextView edtAdress;
    @BindView(R.id.tv_pic_length)
    TextView tvPicLength;
    @BindView(R.id.item_grid_view)
    CustomGridView itemGridView;
    @BindView(R.id.scroll_view)
    ScrollView scrollView;
    @BindView(R.id.tv_file_name)
    TextView tvFileName;
    @BindView(R.id.tv_pic_name)
    TextView tvPicName;
    @BindView(R.id.tv_bohui_reson)
    TextView tvBohuiReson;


    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_into_date)
    TextView tvIntoDate;
    @BindView(R.id.tv_confirm_activist_time)
    TextView tvConfirmActivistTime;
    @BindView(R.id.tv_confirm_obj_time)
    TextView tvConfirmObjTime;
    @BindView(R.id.tv_confirm_ready_time)
    TextView tvConfirmReadyTime;
    @BindView(R.id.tv_rcomand)
    TextView tvRcomand;

    private MemberPublicListBean.DataBean.ListBean Bean;

    @Override
    protected int getContentId() {
        return R.layout.activity_member_public_content;
    }

    public static void goTo(Context context, MemberPublicListBean.DataBean.ListBean tid) {
        Intent intent = new Intent(context, MemberPublicContentActivity.class);
        intent.putExtra("tid", tid);
        context.startActivity(intent);
    }

    @Override
    protected void initViews() {
        if (getIntent() != null) {
            Bean = getIntent().getParcelableExtra("tid");
        }
//check_status 审核状态  0审核中   1已通过   2驳回
        if (Bean.getCheck_status().equals("0")) {
            tvState.setTextColor(Color.parseColor("#EBB22B"));
            tvState.setBackgroundResource(R.drawable.bg_hui_stroke_6);
            tvState.setText("审核中");
            tvBohuiReson.setVisibility(View.GONE);
        }
        if (Bean.getCheck_status().equals("1")) {
            tvState.setText("已通过");
            tvState.setBackgroundResource(R.drawable.bg_hui_cross_stroke_3);
            tvState.setTextColor(Color.parseColor("#e71e1e"));
            tvBohuiReson.setVisibility(View.GONE);
        }
        if (Bean.getCheck_status().equals("2")) {
            tvState.setText("已驳回");
            tvState.setBackgroundResource(R.drawable.bg_hui_stroke_3);
            tvState.setTextColor(Color.parseColor("#666666"));
            tvBohuiReson.setText(Bean.getCheck_remarks());
            tvBohuiReson.setVisibility(View.VISIBLE);
        }

        edtName.setText(Bean.getName());
        edtPhone.setText(Bean.getMobile());
        edtIdentityNumber.setText(Bean.getId_number());
        edtReson.setText(Bean.getReason());
        edtParty.setText(Bean.getOname());
        edtRecomendPerson.setText(Bean.getRecommender());
        edtUnit.setText(Bean.getCompany());
        edtAdress.setText(Bean.getAddr());

        tvDate.setText(Bean.getBirthday());
        tvIntoDate.setText(Bean.getIn_party_time());
        tvConfirmActivistTime.setText(Bean.getConfirm_activist_time());
        tvConfirmObjTime.setText(Bean.getConfirm_obj_time());
        tvConfirmReadyTime.setText(Bean.getConfirm_ready_time());

        if (Bean.getTid() == 1) {
            //入党联系人
            tvRcomand.setText("入党联系人");
        }
        if (Bean.getTid() == 4) {
            //
            tvRcomand.setText("入党介绍人");
        }
//加载网格图片
        tvPicName.setVisibility(View.GONE);
        List<String> picList = Bean.getThumb();
        GridViewAdapter adapter = new GridViewAdapter(MemberPublicContentActivity.this, picList);
        int width = 0;
        int height = 0;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) itemGridView.getLayoutParams();
        if (picList.size() <= 0) {
            itemGridView.setVisibility(View.GONE);
            tvPicName.setVisibility(View.VISIBLE);
        } else {
            if (picList.size() == 1) {
                itemGridView.setVisibility(View.VISIBLE);
                itemGridView.setNumColumns(1);
                width = Utils.getDeviceSize(MemberPublicContentActivity.this).x - DisplayUtil.dp2px(MemberPublicContentActivity.this, 30);
                lp.width = RecyclerView.LayoutParams.MATCH_PARENT;

            } else if (picList.size() == 2 || picList.size() == 4) {
                itemGridView.setVisibility(View.VISIBLE);
                itemGridView.setNumColumns(2);
                width = (Utils.getDeviceSize(MemberPublicContentActivity.this).x - DisplayUtil.dp2px(MemberPublicContentActivity.this, 38)) / 3;
                lp.width = width * 2 + DisplayUtil.dp2px(MemberPublicContentActivity.this, 4);
            } else {
                itemGridView.setVisibility(View.VISIBLE);
                itemGridView.setNumColumns(3);
                width = (Utils.getDeviceSize(MemberPublicContentActivity.this).x - DisplayUtil.dp2px(MemberPublicContentActivity.this, 38)) / 3;
                lp.width = RecyclerView.LayoutParams.MATCH_PARENT;
            }
            itemGridView.setLayoutParams(lp);
            height = width * 3 / 4;
            adapter.setItemHeight(height);
            itemGridView.setAdapter(adapter);
            itemGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (position >= 0 && position <= Bean.getThumb().size() - 1) {
                        List<LocalMedia> photoList = new ArrayList<>();
                        for (String photo : Bean.getThumb()) {
                            photoList.add(new LocalMedia(photo, 0, PictureMimeType.ofImage(), ""));
                        }
                        PictureSelector.create(MemberPublicContentActivity.this).externalPicturePreview(position, photoList);
                    }
                }
            });
        }
        tvFileName.setText(Bean.getFile_name());
        if (TextUtils.isEmpty(Bean.getFile_name())) {
            tvFileName.setText("无");
        } else {
            tvFileName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MemberPublicContentActivity.this, OpenFileActivity.class);
                    intent.putExtra("file_path", Bean.getFile_url());
                    intent.putExtra("file_name", Bean.getFile_name());
                    intent.putExtra("file_size", "");
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    protected void initToolbar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        tvTitle.setText("申请");
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
