package com.sb.framework.http.image;

public class ImageConstant {
	
	/**
	 * 下载图片时开启的最大线程数：考虑到有些服务器不希望过多的并发访问
	 */
	public static final int THREAD_COUNT = 2;
	
	/**
	 * 本应用在sd卡上的工作目录
	 */
	public static final String APP_SD_HOME = ImageFileUtils.getAppHomeDirAtSDCard();
	
}
