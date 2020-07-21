package com.longhoo.net.manageservice.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.longhoo.net.MyApplication;
import com.longhoo.net.R;
import com.longhoo.net.manageservice.adapter.AnimatedListAdapter;
import com.longhoo.net.manageservice.bean.OrgnizationGroupBean;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.GlideManager;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.httprequest.OkHttpCallback;
import com.longhoo.net.utils.httprequest.OkHttpUtil;
import com.longhoo.net.widget.AnimatedExpandableListView;
import com.longhoo.net.widget.base.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/11/28.
 */

public class OrgazationGroupActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private List<OrgnizationGroupBean.DataBean.OrganizationListBean> parentList = new ArrayList<>();
    LinearLayout headView = null;
    String strTitle = "";
    String strOid = "";

    @Override
    protected int getContentId() {
        return R.layout.activity_group;
    }

    @Override
    protected void initViews() {
        Intent intent = getIntent();
        strTitle = intent.getStringExtra("title");


        strOid = intent.getStringExtra("oid");
        toolbar.setBackgroundColor(Color.parseColor("#e71e1e"));
        tvTitle.setTextColor(Color.parseColor("#ffffff"));
        if (strTitle != null) {
            ((TextView) findViewById(R.id.tv_title)).setText(strTitle);
        }

        GetOrgazationGroup();
    }

    @Override
    protected void initToolbar() {
        toolbar.setNavigationIcon(R.drawable.left_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    void GetOrgazationGroup() {
        Map<String, String> map = new HashMap<>();
        map.put("oid", String.valueOf(strOid));
        map.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        OkHttpUtil.getInstance().doAsyncPost(Consts.BASE_URL + "/Organization/frameworkList", map, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if (obj.has("code")) {
                        if (obj.getString("code").equals("0")) {
                            Gson gson = new Gson();
                            final OrgnizationGroupBean mOrgnizationGroupBean = gson.fromJson(response, OrgnizationGroupBean.class);

                            final AnimatedExpandableListView mListView = (AnimatedExpandableListView) findViewById(R.id.expandableListView);
                            LayoutInflater inflater = LayoutInflater.from(OrgazationGroupActivity.this);
                            headView =
                                    (LinearLayout) LayoutInflater.from(OrgazationGroupActivity.this).inflate(R.layout.head_orgazan_group, null);


                            ((TextView) headView.findViewById(R.id.tv_mygroupname)).setText(mOrgnizationGroupBean.getData().getMyorganization().getName());


                            ((TextView) headView.findViewById(R.id.tv_zhibuname)).setText(strTitle);
                            LinearLayout itemAll = (LinearLayout) headView.findViewById(R.id.totalview);
                            itemAll.removeAllViews();


                            for (int i = 0; i < mOrgnizationGroupBean.getData().getList().size(); i++) {
                                View v = inflater
                                        .inflate(R.layout.item_headview_group, null);
                                if (i == (mOrgnizationGroupBean.getData().getList().size()-1)) {
                                    ((View) v.findViewById(R.id.view_li)).setVisibility(View.GONE);
                                } else {
                                    ((View) v.findViewById(R.id.view_li)).setVisibility(View.VISIBLE);
                                }


                                if (!TextUtils.isEmpty(mOrgnizationGroupBean.getData().getList().get(i).getThumb())) {
                                    GlideManager.getInstance().load(OrgazationGroupActivity.this,mOrgnizationGroupBean.getData().getList().get(i).getThumb(),(ImageView) v.findViewById(R.id.img_gtype));
                                }

                                if (mOrgnizationGroupBean.getData().getList().get(i).getNoread() != null) {
                                    if (mOrgnizationGroupBean.getData().getList().get(i).getNoread().equals("0")) {
                                        ((TextView) v.findViewById(R.id.tv_meg)).setVisibility(View.GONE);
                                    } else {
                                        ((TextView) v.findViewById(R.id.tv_meg)).setVisibility(View.VISIBLE);
                                        ((TextView) v.findViewById(R.id.tv_meg)).setText(mOrgnizationGroupBean.getData().getList().get(i).getNoread());
                                    }
                                }

                                ((TextView) v.findViewById(R.id.tv_gname)).setText(mOrgnizationGroupBean.getData().getList().get(i).getFname());
                                final int finalI = i;
                                v.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        /**
                                         * fid : 1
                                         * parentid : 3
                                         * fname : 党员社区
                                         * thumb : http://dangjian.025nj.com/UploadFile/organizatioo/2017/1128/5a1d3cee387a0.png
                                         * time : 1511865583
                                         * <option value="1">党员社区</option>
                                         <option value="2">明主生活会</option>
                                         <option value="3">组织生活会</option>
                                         <option value="4">三会一课</option>
                                         */

                                        ((TextView) v.findViewById(R.id.tv_meg)).setVisibility(View.GONE);
//                                        Toast.makeText(OrgazationGroupActivity.this, "type值：" + mOrgnizationGroupBean.getData().getList().get(finalI).getType(), Toast.LENGTH_SHORT).show();

                                        if (mOrgnizationGroupBean.getData().getList().get(finalI).getType().equals("1")) {
                                            Intent intent = new Intent();
                                            intent.setClass(OrgazationGroupActivity.this, PartyCommunity.class);
                                            startActivity(intent);
                                            return;

                                        }
                                        if (mOrgnizationGroupBean.getData().getList().get(finalI).getType().equals("2")) {
                                            Intent intent = new Intent();
                                            intent.putExtra("oid", strOid);
                                            intent.putExtra("title", mOrgnizationGroupBean.getData().getList().get(finalI).getFname());
                                            intent.setClass(OrgazationGroupActivity.this, DemocraticLifeActivity.class);
                                            startActivity(intent);
                                            return;

                                        }

                                        if (mOrgnizationGroupBean.getData().getList().get(finalI).getType().equals("3")) {
                                            Intent intent = new Intent();
                                            intent.putExtra("oid", strOid);
                                            intent.putExtra("title", mOrgnizationGroupBean.getData().getList().get(finalI).getFname());
                                            intent.putExtra("type", "1");
                                            intent.setClass(OrgazationGroupActivity.this, OrgazationLifeActivity.class);
                                            startActivity(intent);
                                            return;

                                        }
                                        if (mOrgnizationGroupBean.getData().getList().get(finalI).getType().equals("4")) {
                                            Intent intent = new Intent();
                                            intent.putExtra("oid", strOid);
                                            intent.putExtra("title", mOrgnizationGroupBean.getData().getList().get(finalI).getFname());
                                            intent.putExtra("type", "2");
                                            intent.setClass(OrgazationGroupActivity.this, OrgazationLifeActivity.class);
                                            startActivity(intent);
                                            return;

                                        }


                                    }
                                });
                                itemAll.addView(v);
                            }
                            mListView.addHeaderView(headView);
                            parentList = mOrgnizationGroupBean.getData().getOrganizationList();
                            mListView.setAdapter(new AnimatedListAdapter(OrgazationGroupActivity.this, parentList));
                            //点击分组打开或关闭时添加动画
                            mListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                                @Override
                                public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                                    if (mListView.isGroupExpanded(groupPosition)) {

                                        mListView.collapseGroupWithAnimation(groupPosition);
                                    } else {
                                        mListView.expandGroupWithAnimation(groupPosition);
                                    }
                                    return true;
                                }
                            });
                        } else {
                            Toast.makeText(OrgazationGroupActivity.this, "数据异常", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(OrgazationGroupActivity.this, "数据异常", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(OrgazationGroupActivity.this, "网络错误~");

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
