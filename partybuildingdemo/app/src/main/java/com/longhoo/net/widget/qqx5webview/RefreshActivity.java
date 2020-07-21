package com.longhoo.net.widget.qqx5webview;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class RefreshActivity extends Activity{
	X5WebView webView;
	TextView title;
	
	/**
	 * 此类实现了下拉刷新，
	 * 使用extension interface将会准确回去overScroll的时机
	 * 
	 */
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}
	
	
}
