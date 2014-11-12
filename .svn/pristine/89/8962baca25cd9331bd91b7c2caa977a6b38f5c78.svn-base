package com.sb.framework.http;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class CookieStrategyUseFile implements CookieStrategy{

	private Context context;
	public CookieStrategyUseFile(Context context){
		this.context = context;
	}
	
	@SuppressLint("NewApi")
	@Override
	public void saveCookie(String url, String setCookieStr) {
		SharedPreferences sp = context.getSharedPreferences("cookie.conf", Context.MODE_PRIVATE);
		sp.edit().putString(HttpHelper.getDomainName(url), setCookieStr).apply();
	}

	@Override
	public String getCookie(String url) {
		SharedPreferences sp = context.getSharedPreferences("cookie.conf", Context.MODE_PRIVATE);
		return sp.getString(HttpHelper.getDomainName(url), "");
	}

}
