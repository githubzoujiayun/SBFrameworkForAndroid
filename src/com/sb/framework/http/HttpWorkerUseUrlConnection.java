package com.sb.framework.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.sb.framework.http.SBRequest.NetAccessListener;

public class HttpWorkerUseUrlConnection implements SBHttpWorker{

	private static boolean debug = true;
	private void alert(String msg){
		if(debug) System.out.println(msg);
	}
	
	public HttpWorkerUseUrlConnection(){
		
	}
	NetAccessListener listender;
	SBRequest request;
	@Override
	public void sendRequest(final SBRequest request, NetAccessListener listender) {
		this.listender = listender;
		this.request = request;
		listender.onRequestStart(request.flag);
		getThreadPool().execute(new Runnable() {
			
			@Override
			public void run() {
				if(request.method.equalsIgnoreCase("get")){
					alert("--get请求：" + request.flag);
					doGet(request, true);
				}else if(request.method.equalsIgnoreCase("post")){
					alert("--post请求：" + request.flag);
					doPost(request, true);
				}
				
			}
		});
		
	}

	@Override
	public void sendRequestSync(SBRequest request, NetAccessListener listender) {
		this.listender = listender;
		this.request = request;
		if(request.method.equalsIgnoreCase("get")){
			alert("--get请求：" + request.flag);
			doGet(request, false);
		}else if(request.method.equalsIgnoreCase("post")){
			alert("--post请求：" + request.flag);
			doPost(request, false);
		}
	}
	
	//======================================线程池============================//
	
	/**
	 * http请求的线程池
	 */
	private static ExecutorService threadPool = null;
	
	
	/**
	 * 获取线程池的方法，因为涉及到并发的问题，我们加上同步锁
	 * @return
	 */
	private static ExecutorService getThreadPool(){
		if(threadPool == null){
			synchronized(ExecutorService.class){
				if(threadPool == null){
					//用了10个线程来请求http
					threadPool = Executors.newFixedThreadPool(10);
				}
			}
		}
		
		return threadPool;
		
	}

	//----------------------------------------------------------//
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what == 1){
				//成功，obj是String
				listender.onRequestFinished(true, (String)msg.obj, "", request.flag);
			}else if(msg.what == 2){
				//异常，obj是String
				listender.onRequestFinished(true, "", (String)msg.obj, request.flag);
			}
		}
	};
	
	private void sendMsg(int what, Object obj, int arg1, int arg2){
		Message m = Message.obtain();
		m.what = what;
		m.obj = obj;
		m.arg1 = arg1;
		m.arg2 = arg2;
		handler.sendMessage(m);
	}
	private void sendMsg(int what){
		sendMsg(what, null, 0, 0);
	}
	private void sendMsg(int what, Object obj){
		sendMsg(what, obj, 0, 0);
	}
	
	public String doGet(SBRequest request, boolean isAsync) {
		String result="";
		String realUrl = HttpHelper.makeURL(request.url, request.params); 
		if(request.useCache){
			String cacheData = request.cacheHandler.get(realUrl);
			if(!TextUtils.isEmpty(cacheData)){
				alert("--直接使用缓存！");
				if(isAsync) sendMsg(1, cacheData);
				return cacheData;
			}
		}
		try {
			alert("--去服务器请求！");
			URL murl = new URL(realUrl);
			
			HttpURLConnection conn;
			conn = (HttpURLConnection) murl.openConnection();
			
			processURLConnection(conn, "get");
			
			if(request.cookie){
				alert("--请求中加入cookie：" + request.cookieHandler.getCookie(request.url));
				conn.setRequestProperty("Set-Cookie", request.cookieHandler.getCookie(request.url));
			}
			conn.connect();
			
			if(conn.getResponseCode() != 200){
				//结果不对
				result = HttpHelper.translateResponseStatusCode(conn.getResponseCode());
				//this.listender.onAccessComplete(false, null, result, request.flag);
				if(isAsync) sendMsg(2, result);
			}else{
				//结果对
				InputStream stream=conn.getInputStream();
				result = HttpHelper.processInputStreamLikeString(stream);
				request.cacheHandler.save(realUrl, result);
				request.cookieHandler.saveCookie(request.url, conn.getHeaderField("Set-Cookie"));
				if(isAsync) sendMsg(1, result);
				//this.listender.onAccessComplete(true, result, "", request.flag);
			}
			conn.disconnect();
			return result;
		} catch (MalformedURLException e) {
			//Log.e(TAG, "getFromWebByHttpUrlCOnnection:"+e.getMessage());
			e.printStackTrace();
			result = HttpHelper.HTTP_ERROR_INTO_PREFIX + e.getMessage();
			if(isAsync) sendMsg(2, result);
			//this.listender.onAccessComplete(false, null, result, request.flag);
		} catch (IOException e) {
			//Log.e(TAG, "getFromWebByHttpUrlCOnnection:"+e.getMessage());
			e.printStackTrace();
			result = HttpHelper.HTTP_ERROR_INTO_PREFIX + e.getMessage();
			if(isAsync) sendMsg(2, result);
			//this.listender.onAccessComplete(false, null, result, request.flag);
		}catch (Exception e) {
			//Log.e(TAG, "getFromWebByHttpUrlCOnnection:"+e.getMessage());
			e.printStackTrace();
			result = HttpHelper.HTTP_ERROR_INTO_PREFIX + e.getMessage();
			if(isAsync) sendMsg(2, result);
			//this.listender.onAccessComplete(false, null, result, request.flag);
		}
		return result;
	}
	
	public String doPost(SBRequest request, boolean isAsync) {
		String result="";
		try {
			alert("--post总是去服务器请求，不能使用缓存");
			URL url = new URL(request.url);
			
			HttpURLConnection conn;
			conn = (HttpURLConnection) url.openConnection();
			
			processURLConnection(conn, "post");
			
			if(request.cookie){
				alert("--请求中加入cookie：" + request.cookieHandler.getCookie(request.url));
				conn.setRequestProperty("Set-Cookie", request.cookieHandler.getCookie(request.url));
			}
			
			//获取URLConnection对应的输出流并发送参数
			PrintWriter out=new PrintWriter(conn.getOutputStream());
		    out.print(HttpHelper.makeParamForPost(request.params));
		    //flush输出流的缓存
		    out.flush();
			
		    if(conn.getResponseCode() != 200){
				//结果不对
				result = HttpHelper.translateResponseStatusCode(conn.getResponseCode());
				//this.listender.onAccessComplete(false, null, result, request.flag);
				if(isAsync) sendMsg(2, result);
		    }else{
				//结果对
				InputStream stream=conn.getInputStream();
				result = HttpHelper.processInputStreamLikeString(stream);
				request.cookieHandler.saveCookie(request.url, conn.getHeaderField("Set-Cookie"));
				if(isAsync) sendMsg(1, result);
				//this.listender.onAccessComplete(true, result, "", request.flag);
			}
		    conn.disconnect();
			return result;
		} catch (IOException ex) {
			//Log.e(TAG,"PostFromWebByHttpURLConnection："+ ex.getMessage());
			ex.printStackTrace();
			result = HttpHelper.HTTP_ERROR_INTO_PREFIX + ex.getMessage();
			if(isAsync) sendMsg(2, result);
		}catch (Exception ex) {
			//Log.e(TAG,"PostFromWebByHttpURLConnection："+ ex.getMessage());
			ex.printStackTrace();
			result = HttpHelper.HTTP_ERROR_INTO_PREFIX + ex.getMessage();
			if(isAsync) sendMsg(2, result);
		}
		return result;
	}
	
	///----------------------------------------------///
	private static void processURLConnection(HttpURLConnection conn, String method){
		/*
		还可以用setRequestProperty方法给请求添加：
		Host，
		Content-Type，
		Content-Lenth，
		Authentication等参数

		再使用Post的时候还有一个注意点在官方文档中提及的：
		上传数据到服务器时为了达到最好的性能，你可以在知道数据固定
		大小长度的情况下使用 setFixedLengthStreamingMode(int) 方法，
		或者在不知道长度的情况下使用setChunkedStreamingMode(int)。
		因为如果不这样的话，HttpURLConnection 在发生请求之前是将
		数据全部放到内存里面的，这样浪费内存（会造成OutOfMemoryError）
		而且延时。这个东西再这里详细说了：http://www.mzone.cc/article/198.html
		*/
		if(method.equalsIgnoreCase("get")){
			conn.setDoInput(true);
			conn.setConnectTimeout(8000);
			conn.setReadTimeout(4000);
			conn.setRequestProperty("accept", "*/*");
			
			try {
				conn.setRequestMethod("GET");//默认的
			} catch (ProtocolException e) {
				e.printStackTrace();
			} 
			
		    conn.setRequestProperty("Connection", "Keep-Alive");
		    conn.setRequestProperty("Charset", "UTF-8");
			
		}else if(method.equalsIgnoreCase("post")){
			// 设置是否从httpUrlConnection读入，默认情况下是true; 
			conn.setDoInput(true);
			// 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在   
			// http正文内，因此需要设为true, 默认情况下是false; 
			conn.setDoOutput(true);
			//设置超时
			conn.setConnectTimeout(8000);
			conn.setReadTimeout(4000);
			// Post 请求不能使用缓存 
			conn.setUseCaches(false);
			conn.setInstanceFollowRedirects(true);
			// 设定传送的内容类型是可序列化的java对象   
			// (如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)  
//			conn.setRequestProperty("Content-Type",
//					"application/x-www-form-urlencoded");
			
			// 设定请求的方法为"POST"，默认是GET 
			try {
				conn.setRequestMethod("POST");
			} catch (ProtocolException e) {
				e.printStackTrace();
			}
			
			// 连接，从上述第2条中url.openConnection()至此的配置必须要在connect之前完成，
			//发送get请求只需要调用URLConnection方法的connect()方法建立实际的连接即可，而发送post请求则需要获取URLConnection的OutputStream.然后向网络中输出请求参数
		
			conn.setRequestProperty("Connection", "Keep-Alive");
		    conn.setRequestProperty("Charset", "UTF-8");
		    
		}
		
	}
	
}
