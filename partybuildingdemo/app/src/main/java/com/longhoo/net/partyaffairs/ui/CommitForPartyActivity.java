package com.longhoo.net.partyaffairs.ui;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.longhoo.net.MyApplication;
import com.longhoo.net.R;
import com.longhoo.net.mine.ui.LoginActivity;
import com.longhoo.net.partyaffairs.bean.PartyBranchBean;
import com.longhoo.net.partyaffairs.adapter.PartyBranchAdapter;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.utils.Utils;
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

public class CommitForPartyActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_id)
    EditText etId;
    @BindView(R.id.et_reason)
    EditText etReason;
    @BindView(R.id.et_referee)
    EditText etReferee;
    @BindView(R.id.et_company)
    EditText etCompany;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    @BindView(R.id.spinner_party_branch)
    Spinner spinner;
    private PartyBranchAdapter partyBranchAdapter;
    private List<PartyBranchBean.DataBean> branchList = new ArrayList<>();
    private String mBranchName = "";
    private String did = "";
    private EditText[] editTexts;

    @Override
    protected int getContentId() {
        return R.layout.activity_commit_for_party;
    }

    @Override
    protected void initViews() {
        partyBranchAdapter = new PartyBranchAdapter(this, branchList);
        spinner.setAdapter(partyBranchAdapter);
        spinner.setOnItemSelectedListener(this);
        doGetBranch();
        editTexts = new EditText[]{etName, etPhone, etId, etReason, etReferee, etCompany, etAddress};
        for (EditText editText : editTexts) {
            editText.addTextChangedListener(new MyTextWacher(editText));
        }
        tvCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doCommit();
            }
        });
        editTexts[0].requestFocus();
    }

    @Override
    protected void initToolbar() {
        tvTitle.setText("填写表单");
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

    private void doGetBranch() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", EnterCheckUtil.getInstance().getUid_Token()[0]);
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        String url = Consts.BASE_URL + "/Application/getPartyBranch";
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                ULog.e("ck", "所属党支部：" + response);
                Gson gson = new Gson();
                try {
                    PartyBranchBean partyBranchBean = gson.fromJson(response, PartyBranchBean.class);
                    String code = partyBranchBean.getCode();
                    String msg = partyBranchBean.getMsg();
                    if (!TextUtils.equals(code, "0")) {
                        tvCommit.setEnabled(false);
                        ToastUtils.getInstance().showToast(CommitForPartyActivity.this, "所属党支部信息获取失败，请返回重试~");
                        return;
                    }
                    branchList.clear();
                    branchList.addAll(partyBranchBean.getData());
                    partyBranchAdapter.notifyDataSetChanged();
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    tvCommit.setEnabled(false);
                    ToastUtils.getInstance().showToast(CommitForPartyActivity.this, "所属党支部信息获取失败，请返回重试~");
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                tvCommit.setEnabled(false);
                ToastUtils.getInstance().showToast(CommitForPartyActivity.this, "所属党支部信息获取失败，请返回重试~");
            }
        });
    }

    private void doCommit() {
        ULog.e("ck", mBranchName + "");
        if(!EnterCheckUtil.getInstance().isLogin(true)){
            return;
        }
        if (TextUtils.isEmpty(did)) {
            ToastUtils.getInstance().showToast(CommitForPartyActivity.this, "所属党支部信息获取失败，请返回重试~");
            return;
        }
        if (!Utils.isMobile(editTexts[1].getText().toString().trim())) {
            ToastUtils.getInstance().showToast(this, "电话号码格式不正确！");
            editTexts[1].requestFocus();
            editTexts[1].postDelayed(new Runnable() {
                @Override
                public void run() {
                    editTexts[1].requestFocus();
                }
            }, 200);
            editTexts[1].setFocusableInTouchMode(true);
            editTexts[1].setSelection(editTexts[1].getText().toString().trim().length());
            Utils.showHideSoftInput(CommitForPartyActivity.this, editTexts[1], false);
            return;
        }
        if (!Utils.is18ByteIdCard(editTexts[2].getText().toString().trim())) {
            ToastUtils.getInstance().showToast(this, "身份证号码格式不正确！");
            editTexts[2].requestFocus();
            editTexts[2].postDelayed(new Runnable() {
                @Override
                public void run() {
                    editTexts[2].requestFocus();
                }
            }, 200);
            editTexts[2].setSelection(editTexts[2].getText().toString().trim().length());
            Utils.showHideSoftInput(CommitForPartyActivity.this, editTexts[2], false);
            return;
        }
        String[] contents = {etName.getText().toString().trim(), etPhone.getText().toString().trim(), etId.getText().toString().trim(), etReason.getText().toString().trim()
                , etReferee.getText().toString().trim(), etCompany.getText().toString().trim(), etAddress.getText().toString().trim()};
        final String url = Consts.BASE_URL + "/Application/addMembership";
        Map<String, String> params = new HashMap<>();
        params.put("name", contents[0]);
        params.put("mobile", contents[1]);
        params.put("idcard", contents[2]);
        params.put("reason", contents[3]);
        params.put("did", did);
        params.put("recommend", contents[4]);
        params.put("company", contents[5]);
        params.put("address", contents[6]);
        params.put("uid", EnterCheckUtil.getInstance().getUid_Token()[0]);
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        ULog.e("ck", url);
        for (Map.Entry<String, String> entry : params.entrySet()) {
            ULog.e("ck",entry.getKey() + " " + entry.getValue());
        }
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                if(isFinishing()){
                    return;
                }
                ULog.e("ck", response);
                String msg = "服务器异常，提交失败~";
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.optString("code");
                    msg = jsonObject.optString("msg");
                    if(TextUtils.equals(code,"0")){
                        tvCommit.setEnabled(false);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ToastUtils.getInstance().showToast(CommitForPartyActivity.this, msg);
            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(CommitForPartyActivity.this, "网络异常~");
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (i >= 0 && i < branchList.size()) {
            mBranchName = branchList.get(i).getName() + "";
             did= branchList.get(i).getOid()+"";
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private class MyTextWacher implements TextWatcher {

        private EditText editText;

        public MyTextWacher(EditText editText) {
            this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            int size = editTexts.length;
            for (int i = 0; i < size; i++) {
                if (TextUtils.isEmpty(editTexts[i].getText().toString().trim())) {
                    tvCommit.setEnabled(false);
                    return;
                }
            }
            tvCommit.setEnabled(true);
        }
    }
}
