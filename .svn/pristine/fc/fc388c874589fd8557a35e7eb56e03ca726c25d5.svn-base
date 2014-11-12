package com.sb.framework.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

public class NetUtil {
	//当前正在处于连接的APN信息
	private static Uri PREFERRED_APN_URI = Uri.parse("content://telephony/carriers/preferapn");
	public static String PROXY_IP;
	public static int PROXY_PORT;
	
	
	/**
	 * 通过判断wifi和mobile两种方式是否能够连接网络
	 */
	public static boolean checkNetWork(Context context) {
		boolean isWIFI = isWIFI(context);
		boolean isMobile = isMobile(context);

		// 如果两个渠道都无法使用，提示用户设置网络信息
		if (isWIFI == false && isMobile == false) {
			return false;
		}
		
		// 判断APN列表中哪个渠道
		if(isMobile)
		{
			// 读取当前处于连接状态的的Apn的配置信息：ip和端口
			readAPN(context);//联系人信息的读取基本一致
		}
		

		return true;
	}
	
	/**
	 * 读取当前处于连接状态的的Apn的配置信息：ip和端口
	 * @param context
	 */
	
	
	private static void readAPN(Context context) {
		ContentResolver contentResolver = context.getContentResolver();
		Cursor query = contentResolver.query(PREFERRED_APN_URI, null, null, null, null);
		if(query!=null&&query.moveToFirst())
		{
			//读取ip和prot
			PROXY_IP=query.getString(query.getColumnIndex("proxy"));
			PROXY_PORT=query.getInt(query.getColumnIndex("port"));
		}
		
	}

	/**
	 * 判断是否WIFI处于连接状态
	 * 
	 * @return
	 */
	public static boolean isWIFI(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (networkInfo != null) {
			return networkInfo.isConnected();
		}
		return false;
	}

	/**
	 * 判断是否APN列表中某个渠道处于连接状态
	 * 
	 * @return
	 */
	public static boolean isMobile(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (networkInfo != null) {
			return networkInfo.isConnected();
		}
		return false;
	}

}
