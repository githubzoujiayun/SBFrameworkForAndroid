package com.sb.framework.http;

import com.sb.framework.http.SBRequest.NetAccessListener;

/**
 * 所有的实现类自己来管理：
 * 1、线程池
 * 2、如何回调
 * 3、header，cookie
 * 4、缓存
 * 5、重试机制
 * 
 * @author seven
 *
 */
public interface SBHttpWorker{
	/**
	 * 
	 * @param request
	 * @param listender
	 */
	void sendRequest(SBRequest request, NetAccessListener listender);
	void sendRequestSync(SBRequest request, NetAccessListener listender);
}
