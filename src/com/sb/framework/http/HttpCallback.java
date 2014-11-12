package com.sb.framework.http;

public interface HttpCallback<T> {
	
	/** 请求开始之前 */
	void onStart();
	/** 网络没连：如果调用了这个，则不会走onStart() */
	void onOffline();
	/** 请求失败，表示连上了服务器，已经返回了200，但业务上有问题，导致服务器提示出错，也许是正常业务会出现的情况，也可能是对返回值的解析有错，这个正式版不应该出现，所以只要简单打印info就可以 */
	void onFail(String info);
	/** 成功请求 */
	void onSuccess(T t);
	/** 服务器连接问题，可能没连上，也可能超时，这里你一般不会直接显示info，而是显示：网络超时，或者服务器错误等友好信息，或者根据状态码显示页面不存在等 */
	void onConnectWrong(String info);
}
