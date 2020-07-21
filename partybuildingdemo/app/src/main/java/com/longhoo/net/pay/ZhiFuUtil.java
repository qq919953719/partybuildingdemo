package com.longhoo.net.pay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.longhoo.net.pay.payzhifubao.AuthResult;
import com.longhoo.net.pay.payzhifubao.PayResult;
import com.longhoo.net.pay.simcpux.Constants;
import com.longhoo.net.pay.simcpux.Util;
import com.longhoo.net.wxapi.WXPayEntryActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class ZhiFuUtil {
    // -------------------weixin pay------------------

    // public static String WX_APP_ID = "wxd4ce7f0ea2e7a615";//新的APPID

    final int WXZHIFUREQ_FAIL = 0;
    final int WXZHIFUREQ_SUCCESS = 1;
    String mstrMerchant = "龙虎网";
    String mstrName; // 物品名称
    String mstrJiaGe; // 物品价格
    String mstrOrderNum; // 订单号
    String mstrPackageValue; // 包的值
    String mstrNonceStr;
    String mstrCallbackUrl;
    long miTimeStamp; // 时间戳
    Context mContext;// 上下文
    ZhiFuResultListerner mListerner = null;
    ZhiFuBaoResultListerner mZhiFuBaoListerner = null;
    StringBuffer sb;
    PayReq req;
    Map<String, String> resultunifiedorder;
    /**
     * 微信支付的API
     */
    IWXAPI msgApi;
    progressDialog dialog;

    /**
     * @param
     * @return
     */
    public String removeAllSpace(String str) {
        String strTmp = str.replace(" ", "");
        return strTmp;
    }

    public interface ZhiFuResultListerner {
        public void OnZhiFuSuccess(String strOrderNum);

        public void OnZhiFuFail(String strInfo);
    }

    public interface ZhiFuBaoResultListerner {
        public void OnZhiFuBaoSuccess(String strOrderNum);

        public void OnZhiFuBaoFail(String strInfo);
    }


    public void SetResultListerner(ZhiFuResultListerner listerner) {
        mListerner = listerner;
        WXPayEntryActivity.mListerner = mListerner;
    }

    public void SetZhiFuBaoResultListerner(ZhiFuBaoResultListerner listerner) {
        mZhiFuBaoListerner = listerner;

    }

    public boolean isSuportWXZhiFu(Context context) {
        IWXAPI api = WXAPIFactory.createWXAPI(context, Constants.APP_ID);
        int iWxSdkVersion = api.getWXAppSupportAPI();
        if (iWxSdkVersion == 0) {
            Toast.makeText(context, "请先安装微信客户端再进行支付", Toast.LENGTH_SHORT)
                    .show();
            return false;
        } else if (api.getWXAppSupportAPI() < Build.PAY_SUPPORTED_SDK_INT) {
            Toast.makeText(context, "微信版本不支持支付", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    /*
     * strName:物品名称 strJiaGe:物品价格 strOderNum:订单号
     */
    @SuppressLint("SimpleDateFormat")
    public boolean WeiXinZhiFu(String strName, String strJiaGeFen,
                               String strOderNum, String strCallbackUrl,
                               Context context) {

        mstrOrderNum = strOderNum;

        mContext = context;
        mstrName = removeAllSpace(strName.trim());
        mstrJiaGe = strJiaGeFen;

        strOderNum = "";
        WXPayEntryActivity.mstrOderNum = mstrOrderNum;
        mstrCallbackUrl = strCallbackUrl;

        if (false == isSuportWXZhiFu(context)) {
            return false;
        }
        initWX(context);
        return true;
    }

    private void initWX(Context context) {
        dialog = new progressDialog();
        dialog.onPreExecute();
        try {
            req = new PayReq();
            msgApi = WXAPIFactory.createWXAPI(context, null);
            sb = new StringBuffer();
            msgApi.registerApp(Constants.APP_ID);
            GetPrepayIdTask getPrepayId = new GetPrepayIdTask();
            getPrepayId.execute();

        } catch (Exception e) {
            GetPrepayIdTask getPrepayId = new GetPrepayIdTask();
            getPrepayId.execute();

        }

    }

    /**
     * 生成签名
     */

    private String genPackageSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(Constants.API_KEY);

        String packageSign = MD5.getMessageDigest(sb.toString().getBytes())
                .toUpperCase();
        Log.e("orion", packageSign);
        return packageSign;
    }

    private String genAppSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(Constants.API_KEY);

        this.sb.append("sign str\n" + sb.toString() + "\n\n");
//		System.out.println("---------->sign str" + sb.toString() + "\n\n");
        String appSign = MD5.getMessageDigest(sb.toString().getBytes())
                .toUpperCase();
        Log.e("orion", appSign);
        return appSign;
    }

    private String toXml(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        for (int i = 0; i < params.size(); i++) {
            sb.append("<" + params.get(i).getName() + ">");

            sb.append(params.get(i).getValue());
            sb.append("</" + params.get(i).getName() + ">");
        }
        sb.append("</xml>");

        Log.e("orion", sb.toString());
        return sb.toString();
    }

    private class progressDialog {

        private ProgressDialog dialog;

        protected void onPreExecute() {
            dialog = ProgressDialog.show(mContext, "提示", "正在获取预支付订单...");
        }

        private void onDissmiss() {
            if (dialog != null) {
                dialog.dismiss();
            }
        }

    }

    private class GetPrepayIdTask extends
            AsyncTask<Void, Void, Map<String, String>> {
        protected void onPostExecute(Map<String, String> result) {

            sb.append("prepay_id\n" + result.get("prepay_id") + "\n\n");
//			System.out.println("---------->prepay_id" + result.get("prepay_id")
//					+ "\n\n");

            resultunifiedorder = result;
            genPayReq();

        }

        @Override
        protected Map<String, String> doInBackground(Void... params) {

            String url = String
                    .format("https://api.mch.weixin.qq.com/pay/unifiedorder");
            String entity = genProductArgs();

            Log.e("orion", entity);
            byte[] buf = Util.httpPost(url, entity);
            String content = new String(buf);
            Log.e("orion", content);
            Map<String, String> xml = decodeXml(content);
            // sendPayReq();
            return xml;
        }
    }

    public Map<String, String> decodeXml(String content) {

        try {
            Map<String, String> xml = new HashMap<String, String>();
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(new StringReader(content));
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {

                String nodeName = parser.getName();
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:

                        break;
                    case XmlPullParser.START_TAG:
                        if ("xml".equals(nodeName) == false) {
                            // 实例化student对象
                            xml.put(nodeName, parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                event = parser.next();
            }

            return xml;
        } catch (Exception e) {
            Log.e("orion", e.toString());
        }
        return null;

    }

    private String genNonceStr() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000))
                .getBytes());
    }

    private long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

    @SuppressLint("SimpleDateFormat")
    public static String genOutTradNo() {
        String str = "1";
        try {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            str = sdf.format(date);
            int end = str.length();
            str = str + "";
            if (str.length() > 10) {
                str = (String) str.subSequence(4, end);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return str;
    }

    //
    private String genProductArgs() {
        StringBuffer xml = new StringBuffer();
        try {
            String nonceStr = genNonceStr();
            /*
             * StringmstrName; // 物品名称 String mstrJiaGe; // 物品价格 String
             * mstrOrderNum; // 订单号 String mstrPackageValue; // 包的值 String
             * mstrNonceStr; String mstrCallbackUrl; long miTimeStamp; // 时间戳\
             */
//			System.out.println("---------->" + "Name:" + mstrName + "JiaGe:"
//					+ mstrJiaGe + "CallbackUrl:" + mstrCallbackUrl + "OrderNum"
//					+ mstrOrderNum);
            xml.append("</xml>");
            List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
            packageParams
                    .add(new BasicNameValuePair("appid", Constants.APP_ID));
            packageParams.add(new BasicNameValuePair("body", mstrName));// 商品名
            packageParams
                    .add(new BasicNameValuePair("mch_id", Constants.MCH_ID));
            packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
            packageParams.add(new BasicNameValuePair("notify_url",
                    mstrCallbackUrl));// 回调地址
            /**
             * 代金券或立减优惠ID
             */

            packageParams.add(new BasicNameValuePair("out_trade_no",
                    mstrOrderNum));// 订单号
            packageParams.add(new BasicNameValuePair("spbill_create_ip",
                    "127.0.0.1"));// 用户IP
            packageParams.add(new BasicNameValuePair("total_fee", mstrJiaGe));// 价格
            packageParams.add(new BasicNameValuePair("trade_type", "APP"));// 支付类型
            String sign = genPackageSign(packageParams);
            packageParams.add(new BasicNameValuePair("sign", sign));

            String xmlstring = toXml(packageParams);
//			System.out.println("---------->xmlstring" + xmlstring + "\n\n");
            return new String(xmlstring.toString().getBytes(), "ISO8859-1");
            // return xmlstring;

        } catch (Exception e) {
            // Log.e(TAG, "genProductArgs fail, ex = " + e.getMessage());
            return null;
        }

    }

    private void genPayReq() {
        req.appId = Constants.APP_ID;
        req.partnerId = Constants.MCH_ID;
        req.prepayId = resultunifiedorder.get("prepay_id");
        req.packageValue = "Sign=WXPay";
        req.nonceStr = genNonceStr();
        req.timeStamp = String.valueOf(genTimeStamp());
        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
        signParams.add(new BasicNameValuePair("appid", req.appId));
        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
        signParams.add(new BasicNameValuePair("package", req.packageValue));
        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));
        req.sign = genAppSign(signParams);
        sb.append("sign\n" + req.sign + "\n\n");
        Log.e("orion", signParams.toString());
        dialog.onDissmiss();
        sendPayReq();
    }

    private void sendPayReq() {
        msgApi.registerApp(Constants.APP_ID);
        msgApi.sendReq(req);

    }


    public void ToastNews(String strText) {
        Toast.makeText(mContext, strText, Toast.LENGTH_SHORT).show();
    }


    //支付宝支付


    /**
     * 用于支付宝支付业务的入参 app_id。
     */
    //public static final String APPID = "2021001165633917";


    /**
     * pkcs8 格式的商户私钥。
     * <p>
     * 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个，如果两个都设置了，本 Demo 将优先
     * 使用 RSA2_PRIVATE。RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议商户使用
     * RSA2_PRIVATE。
     * <p>
     * 建议使用支付宝提供的公私钥生成工具生成和获取 RSA2_PRIVATE。
     * 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1
     */
    // public static final String RSA2_PRIVATE = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQC6ifIdIJmFwtXrPIpxqn2qQodCPPb/LPXdjU3R0H9+m+dzfTSKzUFmcF0dBtXJkfYZZbd1aAraTYw/AewM9kFZpjxIvFDwbq3jTEg3leWF9B0mhARUePL6VOw1I4YLOLKl3EP0JKt9VZYcP0qB3Z1zsh5zJ9RCQL+jawEI5FYyKbtZL/zR52I42oi71uBKwyVx+jKK/FQvOo09OI5a06AhAvFjvJiS2N9jG/BmD8o1wtezRG955u9hVHZYPyqA7+L78FgUVcRsx3xF5unxz4fWWky3gTREkzUcE/JT6u04SEew9CfjyP7OQgTPXhsh8CQjxJdmDmBarwpP5GJNWukdAgMBAAECggEBAJ1WqT7kNlnaovDNmcCSG3i2sLuwDG3jkGYVP6RPhppWCf0hiGXjBtzOvdsz3rMsSDXX08xVu4Gyky66Ak5Uj358drCrFmIWGNl1iBxKdNRtuQC5raVKdzeq1alkkzV310eK6E5Pd845mGmvALtVH+9sN77Nw302e+3s3WyQnBumIgP5SYI/Q5+xOGi0sbqLVgTTZf3LY0nrE/BKqguseOFhQ3NOuK0uIDzDqs6xdLFnD8vUEO9tRpL7/2aNVd+BJz25G6VcPgTCIdcN6mJvE8PhcMDdQwUCucfUcp53tcrKbqLMrhpRWtCDsFRYQOMtMqolfV1Kgx82pCmSuUyEV8kCgYEA6bcqlpkUlP+jc71u0/pfn9UJQUGKrOnOqR5B5diAy42XtvKxXPxj/uYZwZS1xUNDcS8qw/1KMsa6r7cC5FQ8+EjukyWhl3zDFvGaiRhowHXVt+dF68Y2UXVvp4Q1g7/w+XqLPee+cAzX80D1PA7v6cvKTuJKd1HT/sVd1lpub/8CgYEAzFM660uTcsOBmxWFHHFzPMYWP0drG/Xk1EttzByX9h/fDvY5ruKNOulqJQaghHBAsHY+mrJVHI9Tavohn+T1NBSHHKTdj+/4XIGQcKrl6XBxhEBEmgAXaMZpChUU4IZozfrbR8dqXuEiok6Uo6GvkqAcjlIjef2OiEZxmcMyZuMCgYBELnJXMttlSFq31iix4JXq++xSRDufjCoE/lW9JvcT6v+6mHyTovHNF/WlmquYm7HeICN6v7bYr08hGePXFpge2q/274B/wB7t35f0rNFKbFXn66ljShTomLj1BsXR5Ln5Hf3nFmdhKu4JAgdI+RaWfyRK9QtCWfXdmlhwwzr6owKBgQC42uQHVIzF25M6V7HiFdIIPb6K6XZCCalJWCSx8XyaNCN+Sc3OJzFXBy386Y33xAcCnzfshULzX8g8lKgvHNmqkJfcllvNiXHNX3rqM/Nly1EoMWzfVw3WwQrvEDufFntjP3175zKZPF41MZrQn8tUgH7VVAK8r2VwbSsBzdOfuQKBgQCwD2FljUZFRt+XbeZpwbH16P+Yp48L4cXGmiASwaaNHkJiRnecL6/NqdyBOEjXynRnsbpvadDkGHOahT+pDj52x/cEKe+cghIsGVu0PSuxmZXEvd52R9JgrxIO+92fD/hQH/YFROANJwA/h+mi9Dldpfzzg/m0zqSbzh2gyAQcTg==";
    //public static final String RSA_PRIVATE = "G09vZskQrWnqB0EuiOsZjw==";
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;


    /**
     * 支付宝支付业务示例
     */
    public void payZhiFuBao(final Activity mActivity, final String orderInfo) {

        if (TextUtils.isEmpty(orderInfo)) {
            ToastNews("错误: orderInfo参数异常");
            return;
        }

        /*
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo 的获取必须来自服务端；
         */

//        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID);
//        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
//
//        String privateKey = "";
//        String sign = OrderInfoUtil2_0.getSign(params, privateKey, true);
//        final String orderInfo = orderParam + "&" + sign;

        final Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(mActivity);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        mZhiFuBaoListerner.OnZhiFuBaoSuccess("支付成功");
                        //ToastNews("支付成功" + payResult);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        //ToastNews("支付失败" + payResult);
                        mZhiFuBaoListerner.OnZhiFuBaoFail("支付失败");
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        ToastNews("授权成功" + authResult);
                    } else {
                        // 其他状态值则为授权失败
                        ToastNews("授权失败" + authResult);
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

}
