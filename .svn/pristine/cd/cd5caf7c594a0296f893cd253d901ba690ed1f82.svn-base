package com.sb.framework.http.image;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * 图片下载工具
 * @author seven
 *
 */
public class ImageReaper {
	
	
	//======================================线程池============================//
	
	/**
	 * 下载Image的线程池
	 */
	private static ExecutorService mImageThreadPool = null;
	
	
	/**
	 * 获取线程池的方法，因为涉及到并发的问题，我们加上同步锁
	 * @return
	 */
	private static ExecutorService getThreadPool(){
		if(mImageThreadPool == null){
			synchronized(ExecutorService.class){
				if(mImageThreadPool == null){
					//为了下载图片更加的流畅，我们用了2个线程来下载图片
					mImageThreadPool = Executors.newFixedThreadPool(ImageMgmr.threadCount);
				}
			}
		}
		
		return mImageThreadPool;
		
	}
	
	//========================下载操作=========================//
	
	
	static class MyHandler extends Handler{
		private OnImageDownloadCallback callback;
		
		public MyHandler(OnImageDownloadCallback callback){
			this.callback = callback;
		}
		
		@Override
		public void handleMessage(Message msg) {
			if(msg.what == 1){
				if(callback != null){
					callback.onOK((String)msg.obj);
				}
			}else if(msg.what == 2){
				if(callback != null){
					callback.onFuck((String)msg.obj);
				}
			}else if(msg.what == 3){
				if(callback != null){
					callback.onLoading(msg.arg1, msg.arg2);
				}
			}
			
		}
	};
	
	/**
	 * 将图片下载到SD卡缓存起来。
	 * 
	 * @param imageUrl 图片的URL地址
	 * @param needAsync true表示需要异步下载，使用现成的线程池，需要给回调，总是返回null
	 * @param useOldFile 是否使用本地缓存，true则如果曾经下载过并且缓存过，则不用再下载了
	 * @return 如果是同步下载，即参数2为false，则下载完成后在本地的存储路径，否则返回null
	 * 
	 */
	public static String downloadImage(final String imageUrl, boolean needAsync, final OnImageDownloadCallback callback, boolean useOldFile)
	{
		//ImageReaper.callback = callback;
		final MyHandler handler = new MyHandler(callback);
		String outPath = ImageFileUtils.getImagePath(imageUrl);
		
		if(useOldFile){
			if(ImageFileUtils.isValid(outPath)){
				//已经下载过了，本地有
				ImageMgmr.log("使用sd卡缓存: " + outPath);
				if(!needAsync){
					return outPath;
				}else{
					if(callback != null){
						callback.onOK(outPath);
						return null;
					}
				}
			}
		}
		
		
//		if (Environment.getExternalStorageState().equals(
//				Environment.MEDIA_MOUNTED)) {
//			Log.d("TAG", "monted sdcard");
//		} else {
//			Log.d("TAG", "has no sdcard");
//		}
		if(!needAsync){
			ImageMgmr.log("到服务器下载了！");
			return downloadImage(imageUrl, handler);
		}else{
			if(callback != null){
				callback.onStart();
			}
			getThreadPool().execute(new Runnable() {
				
				@Override
				public void run() {
					Message msg = Message.obtain();;
					
					try {
						ImageMgmr.log("到服务器下载了！");
						String path = downloadImage(imageUrl, handler);
						
						msg.what = 1;
						msg.obj = path;
						
					} catch (Exception e) {
						
						e.printStackTrace();
						msg.what = 2;
						msg.obj = e.getMessage();
						
					}
					
					handler.sendMessage(msg);
					
				}
			});
			
			return null;
		}
		
	}
	
	private static String downloadImage(String imageUrl, MyHandler handler)
	{
		String outPath = ImageFileUtils.getImagePath(imageUrl);
		HttpURLConnection con = null;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		BufferedInputStream bis = null;
		File imageFile = null;
		try {
			URL url = new URL(imageUrl);
			con = (HttpURLConnection) url.openConnection();
			con.setConnectTimeout(5 * 1000);
			con.setReadTimeout(15 * 1000);
			con.setDoInput(true);
			con.setDoOutput(true);
			int total = con.getContentLength();
			bis = new BufferedInputStream(con.getInputStream());
			imageFile = new File(outPath);
			fos = new FileOutputStream(imageFile);
			bos = new BufferedOutputStream(fos);
			byte[] b = new byte[512];
			int length = 0;
			int downloadLen = 0;
			while ((length = bis.read(b)) != -1) {
				bos.write(b, 0, length);
				bos.flush();
				downloadLen += length;
				Message m = Message.obtain();
				m.what = 3;
				m.arg1 = downloadLen;
				m.arg2 = total;
				handler.sendMessage(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			try {
				if (bis != null) {
					bis.close();
				}
				if (bos != null) {
					bos.close();
				}
				if (con != null) {
					con.disconnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
//		if (imageFile != null) {
//			Bitmap bitmap = ImageLoader.decodeSampledBitmapFromResource(
//					imageFile.getPath(), columnWidth);
//			if (bitmap != null) {
//				imageLoader.addBitmapToMemoryCache(imageUrl, bitmap);
//			}
//		}
		return outPath;
	}
	
	
	
}
