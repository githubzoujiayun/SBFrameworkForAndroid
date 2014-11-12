package com.sb.framework.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.sb.framework.SB;

import android.os.Environment;
import android.util.Log;

/**
 * 日志记录
 * 
 */
public class SBLog {
	/**
	 * 开发阶段
	 */
	public static final int DEVELOP = 0;
	/**
	 * 内部测试阶段
	 */
	public static final int DEBUG = 1;
	/**
	 * 公开测试
	 */
	public static final int BATE = 2;
	/**
	 * 正式版
	 */
	public static final int RELEASE = 3;

	/**
	 * 当前阶段标示
	 */
	private static int currentStage = DEVELOP;

	private static String path;
	private static File file;
	private static FileOutputStream outputStream;
	private static String pattern = "yyyy-MM-dd HH:mm:ss";
	
	public static void setLogLevel(int logLevel){
		currentStage = logLevel;
	}

	static {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			// File externalStorageDirectory =
			// Environment.getExternalStorageDirectory();
			path = SB.path.getPathInRoot("log.txt");
			File directory = new File(path);
			if (!directory.exists()) {
				directory.mkdirs();
			}
			file = new File(new File(path), "log.txt");
			android.util.Log.i("SDCAEDTAG", path);
			try {
				outputStream = new FileOutputStream(file, true);
			} catch (FileNotFoundException e) {

			}
		} else {

		}
	}

	public static void info(String msg) {
		info(SBLog.class, msg);
	}

	public static void info(Class clazz, String msg) {
		switch (currentStage) {
		case DEVELOP:
			// 控制台输出
			Log.i(clazz.getSimpleName(), msg);
			break;
		case DEBUG:
			// 在应用下面创建目录存放日志

			break;
		case BATE:
			// 写日志到sdcard
			Date date = new Date();
			String time = new SimpleDateFormat(pattern).format(date);
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				if (outputStream != null) {
					try {
						outputStream.write(time.getBytes());
						String className = "";
						if (clazz != null) {
							className = clazz.getSimpleName();
						}
						outputStream.write(("    " + className + "\r\n")
								.getBytes());

						outputStream.write(msg.getBytes());
						outputStream.write("\r\n".getBytes());
						outputStream.flush();
					} catch (IOException e) {

					}
				} else {
					android.util.Log.i("SDCAEDTAG", "file is null");
				}
			}
			break;
		case RELEASE:
			// 一般不做日志记录
			break;
		}
	}

	public static void logToFile(String msg, String path) {
		File file = new File(path);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
		}
		try {
			FileWriter fw = new FileWriter(path, true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.append(msg);
			bw.close();
			fw.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	//-----对内调试输出----//
	public static void debug(String msg){
		Log.i("sb", msg);
	}
}
