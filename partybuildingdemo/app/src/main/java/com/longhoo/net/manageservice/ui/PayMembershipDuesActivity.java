package com.longhoo.net.manageservice.ui;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.mine.ui.PartyFeeActivity;
import com.longhoo.net.pay.ZhiFuUtil;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.MessageEvent;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.utils.httprequest.OkHttpCallback;
import com.longhoo.net.utils.httprequest.OkHttpUtil;
import com.longhoo.net.widget.LastInputEditText;
import com.longhoo.net.widget.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 党费缴纳
 */
public class PayMembershipDuesActivity extends BaseActivity implements ZhiFuUtil.ZhiFuResultListerner, ZhiFuUtil.ZhiFuBaoResultListerner {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_month)
    TextView tvMonth;
    @BindView(R.id.tv_default_fee)
    TextView tvDefaultFee;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.iv_add)
    ImageView ivAdd;
    @BindView(R.id.tv_money)
    LastInputEditText tvMoney;
    @BindView(R.id.iv_minus)
    ImageView ivMinus;
    @BindView(R.id.et_remarks)
    EditText etRemarks;
    @BindView(R.id.iv_pay_weixin)
    ImageView ivPayWeixin;
    @BindView(R.id.ll_pay_weixin)
    LinearLayout llPayWeixin;
    @BindView(R.id.iv_pay_zfb)
    ImageView ivPayZfb;
    @BindView(R.id.ll_pay_zfb)
    LinearLayout llPayZfb;
    @BindView(R.id.tv_pay)
    TextView tvPay;

    private double fee;
    private int type = 0;   //0 支付宝 1 微信
    private String year = "", month = "";//年 月
    private String ordernum;
    private String order_id;

    private Timer addMinusTimer;
    String ORDER_INFO = "";

    @Override
    protected int getContentId() {
        return R.layout.activity_pay_membership_dues;
    }

    @Override
    protected void initViews() {
        if (getIntent() != null) {
            year = getIntent().getStringExtra("year");
            month = getIntent().getStringExtra("month");
        }
        getPayInfo();
    }

    @Override
    protected void initToolbar() {
        tvTitle.setText("党费缴纳");
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.left_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PayMembershipDuesActivity.this, PayMoonListActivity.class);
                startActivity(intent);
            }
        });
    }


    @OnClick({R.id.ll_pay_weixin, R.id.ll_pay_zfb, R.id.tv_pay, R.id.iv_add, R.id.iv_minus, R.id.tv_see_history})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_pay_weixin:
                ivPayWeixin.setImageResource(R.drawable.pay_select);
                ivPayZfb.setImageResource(R.drawable.pay_unselect);
                type = 1;
                break;
            case R.id.ll_pay_zfb:
                ToastUtils.getInstance().showToast(this, "支付功能暂未开通~");
               // return;
//                ivPayWeixin.setImageResource(R.drawable.pay_unselect);
//                ivPayZfb.setImageResource(R.drawable.pay_select);
//                type = 0;
                break;
            case R.id.tv_pay:
                doPay();
                break;
            case R.id.iv_add:
                addOrMinus(0);
                break;
            case R.id.iv_minus:
                addOrMinus(1);
                break;
            case R.id.tv_see_history:
                Intent intent = new Intent(this, PartyFeeActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void addOrMinus(int type) {
        Double money = 0.0;
        if (TextUtils.isEmpty(tvMoney.getText().toString().trim())) {
            if (type == 0) {
                money++;
            } else if (type == 1) {
                money = 0.0;
            }
            DecimalFormat format = new DecimalFormat("#.0");
            tvMoney.setText(format.format(money));
            return;
        }
        try {
            money = Double.parseDouble(tvMoney.getText().toString().trim());
            if (type == 0) {
                money++;
            } else if (type == 1) {
                money--;
                if (money <= 0.0) {
                    money = 0.0;
                }
            }
            DecimalFormat format = new DecimalFormat("#.0");
            tvMoney.setText(format.format(money));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }


    private void getOrdeInfo() {

        //http://zhdj.xzcit.cn/index.php/app/Fee/ali_trade_pay
        String payMoney = tvMoney.getText().toString().trim();
        if (TextUtils.isEmpty(order_id)) {
            ToastUtils.getInstance().showToast(PayMembershipDuesActivity.this, "order_id参数异常~");
            return;
        }
        if (TextUtils.isEmpty(payMoney)) {
            ToastUtils.getInstance().showToast(PayMembershipDuesActivity.this, "金额异常~");
            return;
        }


        String url = Consts.BASE_URL + "/Fee/ali_trade_pay";
        Map<String, String> params = new HashMap<>();
        params.put("uid", EnterCheckUtil.getInstance().getUid_Token()[0]);
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("order_id", order_id);
        params.put("smoney", payMoney);
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                if (isFinishing()) {
                    return;
                }
                ULog.e("cc", "支付宝支付商品信息：" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.optString("code");
                    String msg = jsonObject.optString("msg");
                    if (!TextUtils.equals(code, "0")) {
                        tvPay.setEnabled(false);
                        etRemarks.setEnabled(false);
                        return;
                    }
                    JSONObject data = jsonObject.optJSONObject("data");
                    if (data != null) {
                        ORDER_INFO = data.getString("pay_info");
                        if (TextUtils.isEmpty(ORDER_INFO)) {
                            ToastUtils.getInstance().showToast(PayMembershipDuesActivity.this, "ORDER_INFO参数异常~");
                            return;
                        }


                        doZhiFuBao();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    tvPay.setEnabled(false);
                    etRemarks.setEnabled(false);
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                tvPay.setEnabled(false);
                etRemarks.setEnabled(false);
            }
        });
    }

    private void getPayInfo() {
        String url = Consts.BASE_URL + "/fee/fee_order";
        Map<String, String> params = new HashMap<>();
        params.put("uid", EnterCheckUtil.getInstance().getUid_Token()[0]);
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        if (!TextUtils.isEmpty(year)) {
            params.put("year", year);
        }        if (!TextUtils.isEmpty(month)) {
            params.put("month", month);
        }
        ULog.e("cc--", month);
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                if (isFinishing()) {
                    return;
                }
                ULog.e("cc", "党费数据：" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.optString("code");
                    String msg = jsonObject.optString("msg");
                    if (!TextUtils.equals(code, "0")) {
                        tvPay.setEnabled(false);
                        etRemarks.setEnabled(false);
                        return;
                    }
                    JSONObject data = jsonObject.optJSONObject("data");
                    if (data != null) {
                        tvTime.setText(data.optString("year") + "-" + data.optString("month"));
                        tvMonth.setText(data.optString("month") + "月");
                        tvDefaultFee.setText(data.optDouble("yfee") + "元");
                        tvMoney.setText(data.optDouble("yfee") + "");
                        tvMoney.setSelection(tvMoney.getText().toString().trim().length());
                        fee = data.optDouble("yfee");
                        ordernum = data.optString("ordernum");
                        order_id = data.optString("order_id");
                        String status = data.optString("status");
                        if (TextUtils.equals(status, "1")) {
                            tvPay.setEnabled(false);
                            etRemarks.setEnabled(false);
                            tvPay.setText("已支付");
                        } else {
                            tvPay.setEnabled(true);
                            etRemarks.setEnabled(true);
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    tvPay.setEnabled(false);
                    etRemarks.setEnabled(false);
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                tvPay.setEnabled(false);
                etRemarks.setEnabled(false);
            }
        });
    }

//    private void doPay() {
//        try {
//            Double d1 = Double.parseDouble(tvMoney.getText().toString().trim());
//            if (d1 < fee) {
//                ToastUtils.getInstance().showToast(this, "党费缴纳金额不能低于" + fee + "元");
//                tvMoney.requestFocus();
//                return;
//            }
//        } catch (NumberFormatException e) {
//            ToastUtils.getInstance().showToast(this, "请输入正确的金额！");
//            tvMoney.requestFocus();
//            return;
//        }
//        String url = Consts.BASE_URL + "/Application/takeOrder";
//        Map<String, String> params = new HashMap<>();
//        params.put("uid", EnterCheckUtil.getInstance().getUid_Token()[0]);
//        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
//        params.put("type", String.valueOf(type));
//        params.put("year", year);
//        params.put("month", month);
//        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
//            @Override
//            public void onSuccess(String response) {
//                ULog.e("ck", "党费支付：" + response);
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    String code = jsonObject.optString("code");
//                    String msg = jsonObject.optString("msg");
//                    //ToastUtils.getInstance().showToast(PayMembershipDuesActivity.this, msg);
//                    if (!TextUtils.equals(code, "0")) {
//                        ToastUtils.getInstance().showToast(PayMembershipDuesActivity.this, "订单获取失败~");
//                        return;
//                    }
//                    JSONObject data = jsonObject.optJSONObject("data");
//                    if (data != null) {
//                        String name = data.optString("name");
//                        String ordernum = data.optString("ordernum");
//                        if (type == 0) {
//                            ToastUtils.getInstance().showToast(PayMembershipDuesActivity.this, "支付宝支付暂未开通~");
//                        } else if (type == 1) {
//                            doWeixin(name, ordernum);
//                        } else {
//                            ToastUtils.getInstance().showToast(PayMembershipDuesActivity.this, "请选择支付方式~");
//                        }
//                    } else {
//                        ToastUtils.getInstance().showToast(PayMembershipDuesActivity.this, "订单获取失败~");
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    ToastUtils.getInstance().showToast(PayMembershipDuesActivity.this, "订单获取失败~");
//                }
//            }
//
//            @Override
//            public void onFailure(String errorMsg) {
//                ToastUtils.getInstance().showToast(PayMembershipDuesActivity.this, "网络异常，订单获取失败~");
//            }
//        });
//    }

    private void doPay() {
        try {
            Double d1 = Double.parseDouble(tvMoney.getText().toString().trim());
            if (d1 < fee) {
                ToastUtils.getInstance().showToast(this, "党费缴纳金额不能低于" + fee + "元");
                tvMoney.setText(fee + "");
                tvMoney.requestFocus();
                return;
            }
        } catch (NumberFormatException e) {
            ToastUtils.getInstance().showToast(this, "请输入正确的金额！");
            tvMoney.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(ordernum)) {
            ToastUtils.getInstance().showToast(this, "订单号获取失败！");
            return;
        }
        if (type == 0) {
            getOrdeInfo();
        } else if (type == 1) {
            doWeixin("党费缴纳", ordernum);
        } else {
            ToastUtils.getInstance().showToast(PayMembershipDuesActivity.this, "请选择支付方式~");
        }
    }

    private void doWeixin(String orderName, String orderNum) {
        int money = 0;
        try {
            Double d = Double.parseDouble(tvMoney.getText().toString().trim());
            BigDecimal bigDecimal = new BigDecimal(d);
            bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
            money = (int) (bigDecimal.doubleValue() * 100);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            ToastUtils.getInstance().showToast(this, "订单金额获取失败~");
            return;
        }
        ULog.e("cc", money + "");
        ZhiFuUtil zhifu = new ZhiFuUtil();
        zhifu.SetResultListerner(PayMembershipDuesActivity.this);
        zhifu.WeiXinZhiFu(orderName,
                String.valueOf(money), orderNum,
                Consts.BASE_URL + "/fee/public_callback",
                PayMembershipDuesActivity.this);

    }


    void doZhiFuBao() {
        ZhiFuUtil zhifu = new ZhiFuUtil();
        zhifu.SetZhiFuBaoResultListerner(PayMembershipDuesActivity.this);
        zhifu.payZhiFuBao(PayMembershipDuesActivity.this,
                ORDER_INFO);
    }

    @Override
    public void OnZhiFuSuccess(String strOrderNum) {
        //关闭PayMoonList页面
        MessageEvent event = new MessageEvent();
        event.message = "msg_finish";
        EventBus.getDefault().post(event);
        ToastUtils.getInstance().showToast(this, "支付成功~");
        tvPay.setEnabled(false);
        etRemarks.setEnabled(false);
        Intent intent = new Intent(this, PartyFeeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void OnZhiFuFail(String strInfo) {
        ToastUtils.getInstance().showToast(this, strInfo);
    }

    @Override
    public void OnZhiFuBaoSuccess(String strOrderNum) {
        MessageEvent event = new MessageEvent();
        event.message = "msg_finish";
        EventBus.getDefault().post(event);
        ToastUtils.getInstance().showToast(this, "支付成功~");
        tvPay.setEnabled(false);
        etRemarks.setEnabled(false);
        Intent intent = new Intent(this, PartyFeeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void OnZhiFuBaoFail(String strInfo) {
        ToastUtils.getInstance().showToast(this, strInfo);
    }
}
