package com.longhoo.net.wxapi;






import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import com.longhoo.net.pay.ZhiFuUtil;
import com.longhoo.net.pay.simcpux.Constants;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;


    public static ZhiFuUtil.ZhiFuResultListerner mListerner = null;
	public static String mstrOderNum;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
        	api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);

            api.handleIntent(getIntent(), this);
		} catch (Exception e) {
			// TODO: handle exception
		}

    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		Log.d("tagecc", "onPayFinish, errCode = " + resp.errCode);
//		System.out.println(resp.errCode);
//		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//			AlertDialog.Builder builder = new AlertDialog.Builder(this);
//			builder.setTitle(R.string.app_tip);
//			builder.setMessage(getString(R.string.pay_result_callback_msg, resp.errStr +";code=" + String.valueOf(resp.errCode)));
//			builder.show();
//		}


		/*
		 * resp.errCode== 0 ：表示支付成功 resp.errCode== -1 ：表示支付失败 resp.errCode== -2
		 * ：表示取消支付
		 */
		switch (resp.errCode) {
		case 0: {
			mListerner.OnZhiFuSuccess(mstrOderNum);
			break;
		}
		case -1: {
			try {
				mListerner.OnZhiFuFail("支付失败");
			} catch (Exception e) {
				// TODO: handle exception
			}

			break;
		}
		case -2: {
			mListerner.OnZhiFuFail("取消支付");
			break;
		}
		default: {
			mListerner.OnZhiFuFail("支付失败");
			break;
		}
		}

		finish();

	}
}