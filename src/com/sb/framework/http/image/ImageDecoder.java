package com.sb.framework.http.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * 解析图片
 * @author seven
 *
 */
public class ImageDecoder {
	
	//ImagePool pool = ImagePool.instance;
	
	/**
	 * 根据需求的高度和宽度计算缩放比例
	 * @param options
	 * @param reqWidth
	 * @return
	 */
	private static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) 
	{
		if(reqWidth == 0 && reqHeight == 0) return 1;
		// 源图片的宽度
		final int width = options.outWidth;
		final int height = options.outHeight;
		int inSampleSizeW = 1;
		int inSampleSizeH = 1;
		
		if (width > reqWidth) {
			// 计算出实际宽度和目标宽度的比率
			final int widthRatio = reqWidth == 0 ? 1 : Math.round((float) width / (float) reqWidth);
			inSampleSizeW = widthRatio;
		}
		
		if (height > reqHeight) {
			// 计算出实际宽度和目标宽度的比率
			final int widthRatio = reqHeight == 0 ? 1 : Math.round((float) height / (float) reqHeight);
			inSampleSizeH = widthRatio;
		}
		
		return Math.max(inSampleSizeW, inSampleSizeH);
	}
	
	
	
	/**
	 * 获得指定大小的图片--大小只是个参考值
	 * @param pathName
	 * @param reqWidth
	 * @param reqHeight
	 * @param useCache 为true则使用缓存，false则总是去解析--适用于当需求大小变了时
	 * @return
	 */
	public static Bitmap decodeBitmap(String pathName, int reqWidth, int reqHeight, boolean useCache) 
	{
		// 先到池中找找
		if(useCache){
			if(ImagePool.instance.get(pathName, false) != null){
				ImageMgmr.log("不用解析了，内存中有缓存！！");
				return ImagePool.instance.get(pathName, false);
			}
		}
		
		
		// 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(pathName, options);
		
		// 调用上面定义的方法计算inSampleSize值
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		
		// 使用获取到的inSampleSize值再次解析图片
		options.inJustDecodeBounds = false;
		ImageMgmr.log("解析Bitmap：原始大小（" + options.outWidth + "," + options.outHeight + "),缩放" + options.inSampleSize);
		Bitmap bm = BitmapFactory.decodeFile(pathName, options);
		ImagePool.instance.add(pathName, bm, false);
		return bm;
	}
	
}
