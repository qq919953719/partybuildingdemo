package com.longhoo.net.pay;




import java.io.ByteArrayOutputStream;

import java.io.InputStream;

import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.URL;

import java.security.KeyStore;
import java.security.MessageDigest;
import java.util.Map;


import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import org.json.JSONObject;


import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;


import android.util.Log;


public class HttpUtil {

	private String jSONString;


	public HttpUtil() {
	}
	public static HttpClient getNewHttpClient() {  
        try {  
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());  
            trustStore.load(null, null);  
            
            SSLSocketFactory sf = new SSLSocketFactoryEx(trustStore);  
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);  
    
            HttpParams params = new BasicHttpParams();  
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);  
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);  
    
            SchemeRegistry registry = new SchemeRegistry();  
            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));  
            registry.register(new Scheme("https", sf, 443));  
    
            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);  
    
            return new DefaultHttpClient(ccm, params);  
        } catch (Exception e) {  
            return new DefaultHttpClient();  
        }  
    }  
	/**
	 * 
	 * 
	 * @param path
	 * @param data
	 * @return
	 */
	public static String doPost(String path, String data) {
		// 路径
		try {
			HttpClient httpClient = getNewHttpClient();
			
			HttpPost httpPost = new HttpPost(path);
			
			try {
				httpPost.setEntity(new StringEntity(data));
				httpPost.setHeader("Accept", "application/json");
				httpPost.setHeader("Content-type", "application/json");
				
				HttpResponse resp = httpClient.execute(httpPost);
				if (resp.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
					return null;
				}

				return new String(EntityUtils.toByteArray(resp.getEntity()));
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
//			System.out.println("当前路径为："+path+"网络连接超时");
			return null;
		}
		
		

	}

	public static String doGet(String path, String data) {
		// 路径
		try {
			String strRequest = String.format("%s?%s", path, data);
			URL url = new URL(strRequest);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setConnectTimeout(15000);
			connection.setRequestMethod("GET");
			connection.setUseCaches(true); // 不允许使用缓存
//			System.out.println("提交路径：" + path);
			System.out.println("请求数据：" + data);
			long iStartTime = System.currentTimeMillis();
			data = "json=" + data;// URLEncoder.encode(data, "utf-8")
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			connection.setDoOutput(true);
			OutputStream os = connection.getOutputStream();
			os.write(data.getBytes());

			int code = connection.getResponseCode();
//			System.out.println("请求时间:" + (System.currentTimeMillis() - iStartTime));
//			System.out.println("返回码：" + code);
			if (code == 200) {
				// 请求成功
				InputStream is = connection.getInputStream();
				String text = readInputStream(is);
//				System.out.println("服务器返回数据：" + text);
				return text;
			} else {
				// 请求失败
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
//			System.out.println("当前路径为："+path+"网络连接超时");
			return null;
		}
	}
	
	/**
	 * 输入流转换成字符串
	 * 
	 */
	public static String readInputStream(InputStream is) 
	{

		try 
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int len = 0;
			byte[] buffer = new byte[1024];
			while ((len = is.read(buffer)) != -1) 
			{
				baos.write(buffer, 0, len);
			}
			is.close();
			baos.close();
			byte[] result = baos.toByteArray();
			String temp = new String(result);
			return temp;
		} 
		catch (Exception e) 
		{
			return "获取失败";
		}
	}
	
	public String getJson(Map<String, Object> params) {
		JSONObject jsonRequest = new JSONObject();
		try {
			if (params != null) {
				for (String key : params.keySet()) {
					jsonRequest.put(key, params.get(key));
				}
			}
			jSONString = jsonRequest.toString();
		} catch (Exception e) {
		}
		return jSONString;
	}

	public static String sha1(String str) {
		if (str == null || str.length() == 0) {
			return null;
		}
		
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
			mdTemp.update(str.getBytes());
			
			byte[] md = mdTemp.digest();
			int j = md.length;
			char buf[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
				buf[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(buf);
		} catch (Exception e) {
			return null;
		}
	}
}
