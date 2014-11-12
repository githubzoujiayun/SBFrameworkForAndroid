package com.sb.framework.http.image;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;

public class ImageFileUtils {
	
	/**
	 * 文件是否存在
	 * @param fileName
	 * @return
	 */
	public static boolean isFileExists(String fileName){
		return new File(fileName).exists();
	}
	
	/**
	 * 文件大小
	 * @param fileName
	 * @return
	 */
	public static long getFileSize(String fileName) {
		return new File(fileName).length();
	}
	
	public static boolean isValid(String fileName){
		return isFileExists(fileName) && getFileSize(fileName) > 0;
	}
	
	/**
	 * 删除文件及其子文件--不删除子目录
	 */
	public static void deleteFile() {
		File dirFile = new File(getAppHomeDirAtSDCard());
		if(! dirFile.exists()){
			return;
		}
		if (dirFile.isDirectory()) {
			String[] children = dirFile.list();
			for (int i = 0; i < children.length; i++) {
				new File(dirFile, children[i]).delete();
			}
		}
		
		dirFile.delete();
	}
	
	/**
	 * 获取图片的本地存储路径。
	 * 
	 * @param imageUrl
	 *            图片的URL地址。
	 * @return 图片的本地存储路径。
	 */
	public static String getImagePath(String imageUrl) {
		int lastSlashIndex = imageUrl.lastIndexOf("/");
		String imageName = imageUrl.substring(lastSlashIndex + 1);
		String imageDir = ImageMgmr.localCachePath;
		File file = new File(imageDir);
		if (!file.exists()) {
			file.mkdirs();
		}
		if(ImageMgmr.nameGenerator == null){
			String imagePath = imageDir + imageName;
			return imagePath;
		}else{
			return ImageMgmr.nameGenerator.generateNewName(imageUrl, imageName);
		}
		
	}
	
	public static String getAppHomeDirAtSDCard(){
		String imageDir = Environment.getExternalStorageDirectory()
				.getPath() + "/sb_image_utils/";
		return imageDir;
	}
}
