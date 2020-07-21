package com.longhoo.net.partyaffairs.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.longhoo.net.MyApplication;
import com.longhoo.net.R;
import com.longhoo.net.partyaffairs.bean.ApprovalBean;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.httprequest.OkHttpCallback;
import com.longhoo.net.utils.httprequest.OkHttpUtil;
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
 * Created by ${CC} on 2018/1/3.
 */

public class ApprovalActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_name)
    TextView etName;
    @BindView(R.id.et_phone)
    TextView etPhone;
    @BindView(R.id.et_id)
    TextView etId;
    @BindView(R.id.et_reason)
    TextView etReason;
    @BindView(R.id.et_organaztion)
    TextView etOrganaztion;
    @BindView(R.id.et_referee)
    TextView etReferee;
    @BindView(R.id.et_company)
    TextView etCompany;
    @BindView(R.id.et_address)
    TextView etAddress;
    String strId = "";

    @BindView(R.id.text2)
    TextView text2;

    @BindView(R.id.text2sss)
    TextView text2sss;
    @BindView(R.id.et_note)
    EditText etNote;
    @BindView(R.id.iv_sel_sign)
    Spinner ivSelSign;
    @BindView(R.id.tv_set)
    TextView tvSet;
    String strRes = "1";
    private List<String> list = new ArrayList<String>();


    private ArrayAdapter<String> adapter;

    @Override
    protected int getContentId() {
        return R.layout.activity_approval;
    }

    @Override
    protected void initViews() {
        if (getIntent() != null) {
            strId = getIntent().getStringExtra("id");
        }
        GetPTCConetnt();
        list.add("同意  ");//0
        list.add("不同意  ");//1
        list.add("其他  ");//2

        //第二步：为下拉列表定义一个适配器，这里就用到里前面定义的list。
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        //第三步：为适配器设置下拉列表下拉时的菜单样式。
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        ivSelSign.setAdapter(adapter);
        //第五步：为下拉列表设置各种事件的响应，这个事响应菜单被选中
        ivSelSign.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                /* 将所选mySpinner 的值带入myTextView 中*/
//                tvSelSign.setText("您选择的是：" + adapter.getItem(arg2));
                /* 将mySpinner 显示*/

                strRes = arg2 + 1 + "";
                arg0.setVisibility(View.VISIBLE);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
//                tvSelSign.setText("NONE");
                arg0.setVisibility(View.VISIBLE);
            }
        });
        /*下拉菜单弹出的内容选项触屏事件处理*/
        ivSelSign.setOnTouchListener(new Spinner.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                /**
                 *
                 */
                return false;
            }
        });
        /*下拉菜单弹出的内容选项焦点改变事件处理*/
        ivSelSign.setOnFocusChangeListener(new Spinner.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub

            }
        });

        tvSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Approval();
            }
        });
    }

    void Approval() {

//        另外需要传入id(主键id)、res(1:同意2：否决:3其他)、val(备注)
//        http://test.025nj.com/dangjian/index.php?m=Api&/Application/docheck\
        if (etNote.getText().toString().trim().equals("")) {
            Toast.makeText(ApprovalActivity.this, "请输入备注", Toast.LENGTH_SHORT).show();
            return;
        }
        MyApplication App = (MyApplication) getApplicationContext();
        Map<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(strId));
        map.put("token", String.valueOf(App.getmLoginBean().getData().getToken()));
        map.put("res", String.valueOf(strRes));
        map.put("val", String.valueOf(etNote.getText().toString().trim()));
        OkHttpUtil.getInstance().doAsyncPost(Consts.BASE_URL + "/Application/docheck", map, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if (obj.has("code")) {
                        if (obj.getString("code").equals("0")) {
                            finish();
                            Toast.makeText(ApprovalActivity.this, "审批成功", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(ApprovalActivity.this, "数据异常", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ApprovalActivity.this, "数据异常", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(ApprovalActivity.this, "网络错误~");

            }
        });

    }

    @Override
    protected void initToolbar() {
        tvTitle.setText("入党申请审批");
        toolbar.setNavigationIcon(R.drawable.left_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    void GetPTCConetnt() {
        MyApplication App = (MyApplication) getApplicationContext();
        if (App.getmLoginBean() == null) {
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(strId));
        map.put("token", String.valueOf(App.getmLoginBean().getData().getToken()));
        OkHttpUtil.getInstance().doAsyncPost(Consts.BASE_URL + "/Application/membershipDetail", map, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if (obj.has("code")) {
                        if (obj.getString("code").equals("0")) {
                            Gson gson = new Gson();
                            final ApprovalBean mPTCConetntBean = gson.fromJson(response, ApprovalBean.class);
                            etName.setText(mPTCConetntBean.getData().getName());
                            etPhone.setText(mPTCConetntBean.getData().getMobile());
                            etId.setText(mPTCConetntBean.getData().getIdcard());
                            etReason.setText(mPTCConetntBean.getData().getReason());
                            etOrganaztion.setText(mPTCConetntBean.getData().getOname());
                            etReferee.setText(mPTCConetntBean.getData().getRecommend());
                            etCompany.setText(mPTCConetntBean.getData().getCompany());
                            etAddress.setText(mPTCConetntBean.getData().getAddress());
                            //            status  0 待审核 1已通过   2未通过
                            if (!mPTCConetntBean.getData().getStatus().equals("0")) {

                                list.clear();
                                if (mPTCConetntBean.getData().getStatus().equals("1")) {
                                    list.add("同意  ");//1
                                } else if (mPTCConetntBean.getData().getStatus().equals("2")) {
                                    list.add("不同意  ");//1
                                } else {
                                    list.add("其他  ");//1
                                }
                                tvSet.setVisibility(View.GONE);
                                //第四步：将适配器添加到下拉列表上
                                ivSelSign.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                                etNote.setText(mPTCConetntBean.getData().getCheckreason());
                                etNote.clearFocus();
                                etNote.setEnabled(false);
                                ivSelSign.setEnabled(false);
                            }
                        } else {
                            Toast.makeText(ApprovalActivity.this, "数据异常", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ApprovalActivity.this, "数据异常", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(ApprovalActivity.this, "网络错误~");

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
