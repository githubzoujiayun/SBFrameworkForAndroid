package com.sb.framework.http;

/**
 * Cookie的保存和获取，Cookie是服务器保存的，给服务器用的，只不过是保存在客户端，来回来的传就是了
 * 
 * cookie的值类似于：
 * //Set-Cookie = addtime=null; Expires=Thu, 01-Jan-1970 00:00:10 GMT; Path=/
		//cookie = LoginCookie=d512310ecf8bc7f152945872bfcd18d1; JSESSIONID=73148C205CEEFE2BB34E9619FC3C7D38
 * 
 * if (!(cookies == null || cookies.length() == 0)) {
			headers.put("Cookie", cookies);
		}
		
if (re_header!=null) {
			re_cookies = re_header.get("Set-Cookie");
		}
 * 
 * @author seven
 *
 */
public interface CookieStrategy {

	void saveCookie(String url,String setCookieStr);
	String getCookie(String url);
	
}
