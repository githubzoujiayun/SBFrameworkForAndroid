package com.sb.framework.http;

import java.util.HashMap;
import java.util.Map;

import com.sb.framework.SB;
import com.sb.framework.utils.NetUtil;

import android.content.Context;
import android.text.TextUtils;


/**
 * .flag(String flag)
	.param(name, value)
	.param(name, value)
	.param(name, value)
	.entity(json或xml)---???????????????????
	.method("get") ---------可封装到用户层
	.cache(true)   ---------可封装到用户层
	.cache(new DiskCacheUseFile(缓存目录地址，sd卡上的))   ---------可封装到用户层
	.cookie(是否启用) ---------可封装到用户层
	.cookieStrategy(new CookieStrategyUseDB(context))
	.url(String url) ---------可封装到用户层
	.callback(this)  
	.repeat(true)  ---------可封装到用户层---????????????????????
	.header(name, value)   ---------可封装到用户层
	.connectionTimeout(毫秒)
	.go();
 * @author seven
 *
 */
public class SBRequest {
	

	//public Context context;
	public SBRequest(){
		//this.context = context;
	}
	
	public interface NetAccessListener {
		public void onNetOff();
		public void onRequestStart(String flag);
		public void onRequestFinished(boolean success, String object, String error, String flag);
	}
	public NetAccessListener listender;
	public Map<String, String> params = new HashMap<String, String>();
	public Map<String, String> headers = new HashMap<String, String>();
	public boolean tryRepeat = false;
	public String url = "";
	public boolean cookie = true;
	public CookieStrategy cookieHandler;
	public String entity = "";
	public boolean useCache = false;
	public DiskCacheStrategy cacheHandler;
	public String method = "get";
	public int conncetionTimeout = 10000;
	public String flag = "";
	
	public void go(){
		if(!NetUtil.checkNetWork(SB.context)){
			if(listender!=null) listender.onNetOff();
		}
		if(worker == null){
			worker = new HttpWorkerUseUrlConnection();
		}
		worker.sendRequest(this, listender);
	}
	
	public void goSync(){
		if(worker == null){
			worker = new HttpWorkerUseUrlConnection();
		}
		worker.sendRequestSync(this, listender);
	}
	
	SBHttpWorker worker;
	
	
	//---------------------------------------------------------------//
	public SBRequest flag(String flag){
		if(!TextUtils.isEmpty(this.flag)){
			throw new RuntimeException("flag已经被设置过，此对象不可重用！");
		}
		
		this.flag = flag;
		return this;
	}
	
	public SBRequest param(String name, String value){
		if(this.params == null) this.params = new HashMap<String, String>();
		params.put(name, value);
		return this;
	}
	
	public SBRequest header(String name, String value){
		if(this.headers == null) this.headers = new HashMap<String, String>();
		headers.put(name, value);
		return this;
	}
	
	public SBRequest method(String method){
		this.method = method;
		return this;
	}
	
	public SBRequest cache(boolean useCache){
		this.useCache = useCache;
		return this;
	}
	public SBRequest cacheStrategy(DiskCacheStrategy cacheHandler){
		this.cacheHandler = cacheHandler;
		return this;
	}
	public SBRequest cookie(boolean useCookie){
		this.cookie = useCookie;
		return this;
	}
	public SBRequest cookieStrategy(CookieStrategy cookieHandler){
		this.cookieHandler = cookieHandler;
		return this;
	}
	
	public SBRequest url(String url){
		this.url = url;
		return this;
	}
	
	public SBRequest callback(NetAccessListener callback){
		this.listender = callback;
		return this;
	}
	
	public SBRequest repeat(boolean useRepeat){
		this.tryRepeat = useRepeat;
		return this;
	}
	
	public SBRequest connectionTimeout(int mill){
		this.conncetionTimeout = mill;
		return this;
	}
	
	public SBRequest worker(SBHttpWorker worker){
		this.worker = worker;
		return this;
	}
	//---------------------------------------------------------------//
	
}
