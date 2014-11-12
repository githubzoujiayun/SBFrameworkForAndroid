package com.sb.framework.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.sb.framework.R;
import com.sb.framework.http.CookieStrategyUseFile;
import com.sb.framework.http.DiskCacheUseFile;
import com.sb.framework.http.HttpWorkerUseUrlConnection;
import com.sb.framework.http.HttpWorkerUseVolly;
import com.sb.framework.http.SBHttpWorker;
import com.sb.framework.http.SBNet;
import com.sb.framework.http.SBRequest.NetAccessListener;
import com.sb.framework.view.SBQuery;

public class HttpTestActivity extends Activity 
	implements NetAccessListener
{

	private String url, method;
	private boolean useCache;
	private boolean useCookie;
	private String useWhichThirdJar;

	public static void startHttp(Context context, String url, String method, boolean useCache, boolean useCookie, String useWhichThirdJar){
		Intent intent = new Intent(context, HttpTestActivity.class);
		intent.putExtra("url", url);
		intent.putExtra("useCache", useCache);
		intent.putExtra("useCookie", useCookie);
		intent.putExtra("useWhichHttpComponent", useWhichThirdJar);
		intent.putExtra("method", method);
		context.startActivity(intent);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_http_test_urlconnection);
		
		url = getIntent().getStringExtra("url");
		useCache = getIntent().getBooleanExtra("useCache", false);
		useCookie = getIntent().getBooleanExtra("useCookie", false);
		useWhichThirdJar = getIntent().getStringExtra("useWhichHttpComponent");
		method = getIntent().getStringExtra("method");
		
//		url = "http://m.weather.com.cn/data/101010100.html";
		
//		try {
//			url = "http://api.map.baidu.com/telematics/v3/weather?location="
//					+ URLEncoder.encode("北京", "utf-8")
//					+ "&output=json&ak=5slgyqGDENN7Sy7pw29IUvrZ";
//			url = "http://api.map.baidu.com/telematics/v3/weather";
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		SBQuery q = new SBQuery(this);
		q.id(R.id.tv_url).text(url);
	}

	public void btn_send(View v){
		SBHttpWorker worker = null;
		if(useWhichThirdJar.equals("urlconnection")){
			worker = new HttpWorkerUseUrlConnection();
		}else if(useWhichThirdJar.equals("volly")){
			worker = new HttpWorkerUseVolly();
		}else if(useWhichThirdJar.equals("xutils")){
			worker = new HttpWorkerUseUrlConnection();
		}
		SBNet sb = new SBNet();
		
//		String location = "";
//		try {
//			location = URLEncoder.encode("北京", "utf-8");
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		sb.request()
			.flag("请求1")
			.url(url)
			.method(method)
			.cookie(useCookie)
			.cookieStrategy(new CookieStrategyUseFile(this))
			.cache(useCache)
			.cacheStrategy(new DiskCacheUseFile())
//			.param("location", location)
//			.param("output", "json")
//			.param("ak", "5slgyqGDENN7Sy7pw29IUvrZ")
			.callback(this)
			.worker(worker)
			.go();
	}
	public void btn_clean(View v){
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater()
				.inflate(R.menu.activity_http_test_urlconnection, menu);
		return true;
	}

	@Override
	public void onRequestFinished(boolean success, String object, String error,
			String flag) {
		SBQuery q = new SBQuery(this);
		if(success){
			q.id(R.id.tv_content).text(flag + "返回结果成功：\n" + object);
		}else{
			q.id(R.id.tv_content).text(flag + "返回结果失败：\n" + error);
		}
	}

	@Override
	public void onRequestStart(String flag) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNetOff() {
		// TODO Auto-generated method stub
		
	}

}
