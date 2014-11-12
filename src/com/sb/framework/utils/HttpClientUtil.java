package com.sb.framework.utils;

import java.io.InputStream;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * 通用的连接工具（wap）
 * 
 * @author Administrator
 * 
 */
public class HttpClientUtil {
	private HttpRequest request;
	private HttpGet get;
	private HttpPost post;

	private HttpResponse response;

	private HttpClient client;

	public HttpClientUtil() {
		client = new DefaultHttpClient();

		// 判断是否读取到了ip信息（代理）
//		if (StringUtils.isNotBlank(GloableParams.PROXY_IP)) {
//			// wap方式上网
//			HttpHost host = new HttpHost(GloableParams.PROXY_IP, GloableParams.PROXY_PORT);
//			client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, host);
//		}
	}

	/**
	 * 发送xml文件
	 * 
	 * @param xml
	 * @return
	 */
	public InputStream sendXml(String uri, String xml) {
		post = new HttpPost(uri);

		try {
			StringEntity entity = new StringEntity(xml, "utf-8");
			post.setEntity(entity);

			response = client.execute(post);

			if (response.getStatusLine().getStatusCode() == 200) {
				return response.getEntity().getContent();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
