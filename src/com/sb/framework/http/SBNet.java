package com.sb.framework.http;

import android.content.Context;

/**
 * http请求的上层框架，用于封装各种第三方http库，用法：
 * 
 * q.request()
	.flag(String flag)
	.param(name, value)
	.param(name, value)
	.param(name, value)
	.entity(json或xml)
	.method("get") ---------可封装到用户层
	.cache(true)   ---------可封装到用户层
	.cookie(String cookie) ---------可封装到用户层
	.url(String url) ---------可封装到用户层
	.callback(this)  
	.repeat(true)  ---------可封装到用户层
	.header(name, value)   ---------可封装到用户层
	.go();

public interface NetAccessListener {
		public void onAccessComplete(boolean success, String object,
				VolleyError error, String flag){
			
			if(!success){
				error是根据404,500等状态码转换的文本
				return;
			}
			
			if(flag.equals("add")){
			
				//flag区分不同的请求
				
			}
				
		}
	}	
 * 
 * @author seven
 *
 */
public class SBNet {
	
	public SBRequest request(){
		SBRequest r = new SBRequest();
		return r;
	}
	
}
