package com.sb.framework.http.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * 图片二级缓存：内存（Lru），本地文件
 * 
 * 图片需要一个唯一的id
 * ——本地图片使用本地绝对路径
 * ——web图片使用下载到本地之后的绝对路径
 * 
 * @author seven
 *
 */
public class ImagePool {
	/**
	 * 图片缓存技术的核心类，用于缓存所有下载好的图片，在程序内存达到设定值时会将最少最近使用的图片移除掉。
	 */
	private static LruCache<String, Bitmap> mMemoryCache;
	
	public static ImagePool instance = new ImagePool();
	
	private ImagePool() {
		
		if(mMemoryCache != null) return;
		
		// 获取应用程序最大可用内存
		int maxMemory = (int) Runtime.getRuntime().maxMemory();
		int cacheSize = maxMemory / 8;
		
		// 设置图片缓存大小为程序最大可用内存的1/8
		mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				return bitmap.getByteCount();
			}
		};
		
	}
	
	
	//==================================池操作===========================//
	/**
	 * 将一张图片存储到LruCache中。
	 * 
	 * @param key
	 *            LruCache的键，这里传入图片的URL地址。
	 * @param bitmap
	 *            LruCache的键，这里传入从网络上下载的Bitmap对象。
	 */
	public void add(String key, Bitmap bitmap, boolean keyIsURL) {
		if (get(key, keyIsURL) == null) {
			String realKey = key;
			if(keyIsURL) realKey = ImageFileUtils.getImagePath(key);
			mMemoryCache.put(realKey, bitmap);
		}
	}
	
	/**
	 * 从LruCache中获取一张图片，如果不存在就返回null。
	 * 
	 * @param key
	 *            LruCache的键，这里传入图片的URL地址。
	 * @return 对应传入键的Bitmap对象，或者null。
	 */
	public Bitmap get(String key, boolean keyIsURL) {
		String realKey = key;
		if(keyIsURL) realKey = ImageFileUtils.getImagePath(key);
		return mMemoryCache.get(realKey);
	}
	
	
	//=============================
}
