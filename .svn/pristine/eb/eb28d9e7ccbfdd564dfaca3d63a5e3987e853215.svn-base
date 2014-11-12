package com.sb.framework.test;

import com.sb.framework.R;
import com.sb.framework.R.id;
import com.sb.framework.R.layout;
import com.sb.framework.R.menu;
import com.sb.framework.view.SBQuery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements View.OnClickListener {

	SBQuery q = new SBQuery();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		q.acticity(this).id(R.id.btn_textview).clicked(this);
		
		q.acticity(this).id(R.id.btn_imageview1).clicked(this);
		q.acticity(this).id(R.id.btn_imageview2).clicked(this);
		q.acticity(this).id(R.id.btn_imageview3).clicked(this);
		q.acticity(this).id(R.id.btn_imageview4).clicked(this);
		
		q.acticity(this).id(R.id.btn_rubbish).clicked(this);
		q.acticity(this).id(R.id.btn_sbadapter).clicked(this);
		
		q.acticity(this).id(R.id.btn_http1).clicked(this);
		q.acticity(this).id(R.id.btn_http2).clicked(this);
		q.acticity(this).id(R.id.btn_http3).clicked(this);
		q.acticity(this).id(R.id.btn_http4).clicked(this);
		
		q.acticity(this).id(R.id.btn_volly1).clicked(this);
		q.acticity(this).id(R.id.btn_volly2).clicked(this);
		q.acticity(this).id(R.id.btn_volly3).clicked(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	String url = "http://img.my.csdn.net/uploads/201309/01/1378037235_3453.jpg";
	String url2 = "http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg";
	@Override
	public void onClick(View v) {
		int id = v.getId();
		if(id == R.id.btn_textview){
			
		}
		if(id == R.id.btn_imageview1){
			//网络图片：URLConnection,无缓存
			ImageViewActivity.startTestImageViewOnNet(this, url, false, "urlconnection");
		}
		if(id == R.id.btn_imageview2){
			//网络图片：URLConnection,有缓存
			ImageViewActivity.startTestImageViewOnNet(this, url, true, "urlconnection");
		}
		if(id == R.id.btn_imageview3){
			//网络图片：volly,无缓存
			ImageViewActivity.startTestImageViewOnNet(this, url2, false, "volly");
		}
		if(id == R.id.btn_imageview4){
			//网络图片：volly,缓存
			ImageViewActivity.startTestImageViewOnNet(this, url2, true, "volly");
		}
		if(id == R.id.btn_rubbish){
			startTest(RubbishOOMActivity.class);
		}
		if(id == R.id.btn_sbadapter){
			startTest(ListViewNormalActivity.class);
		}
		if(id == R.id.btn_http1){
			//普通get
			HttpTestActivity.startHttp(this, "http://www.baidu.com/s", "get", false, false, "urlconnection");
		}
		if(id == R.id.btn_http2){
			//普通post
			HttpTestActivity.startHttp(this, "http://www.baidu.com/s", "post", false, false, "urlconnection");
		}
		if(id == R.id.btn_http3){
			//普通get：Cookie和Disk Cache
			HttpTestActivity.startHttp(this, "http://www.baidu.com/s", "get", true, true, "urlconnection");
		}
		if(id == R.id.btn_http4){
			//普通post：Cookie
			HttpTestActivity.startHttp(this, "http://www.baidu.com/s", "post", false, true, "urlconnection");
		}
		if(id == R.id.btn_volly1){
			//Http-Volly：普通get,无缓存，无cookie
			HttpTestActivity.startHttp(this, "http://www.baidu.com/s", "get", false, false, "volly");
		}
		if(id == R.id.btn_volly2){
			//Http-Volly：普通get,有缓存，无cookie
			HttpTestActivity.startHttp(this, "http://www.baidu.com/s", "get", false, true, "volly");
		}
		if(id == R.id.btn_volly3){
			//Http-Volly：普通post,无cookie
			HttpTestActivity.startHttp(this, "http://www.baidu.com/s", "post", false, false, "volly");
		}
	}

	private void startTest(Class<?> clazz){
		Intent i = new Intent(this, clazz);
		startActivity(i);
	}
}
