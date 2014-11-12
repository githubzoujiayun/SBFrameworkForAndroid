package com.sb.framework.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class Drawable2BitmapUtils {
	public static Bitmap drawableToBitmap(Drawable drawable) {
		Bitmap bitmap = Bitmap
				.createBitmap(
						drawable.getIntrinsicWidth(),
						drawable.getIntrinsicHeight(),
						drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
								: Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		// canvas.setBitmap(bitmap);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight());
		drawable.draw(canvas);
		return bitmap;
	}

	public static Bitmap resouresToBitmap(Context context, int id) {
		Drawable drawable = context.getResources().getDrawable(id);
		Bitmap drawableToBitmap = drawableToBitmap(drawable);
		return drawableToBitmap;

	}

	/**
	 * 将图片转换成圆形
	 * 
	 * @param bitmap
	 * @param Diameter
	 *            圆的直径
	 * @return
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int Diameter) {
		Bitmap squareBitmap;
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		int left = 0;
		int top = 0;
		int right = 0;
		int bottom;
		if (width > height) {
			left = (width - height) / 2;
			top = 0;
			bottom = height;
			right = (height + left);

		} else {
			left = 0;
			top = (height - width) / 2;
			right = width;
			bottom = width + top;
		}
		squareBitmap = Bitmap.createBitmap(bitmap, left, top, (right - left),
				(bottom - top));
		if (squareBitmap.getWidth() != Diameter
				|| squareBitmap.getHeight() != Diameter) {
			squareBitmap = Bitmap.createScaledBitmap(squareBitmap, Diameter,
					Diameter, true);
		}
		Bitmap output = Bitmap.createBitmap(squareBitmap.getWidth(),
				squareBitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		Paint paint = new Paint();
		Rect rect = new Rect(0, 0, squareBitmap.getWidth(),
				squareBitmap.getHeight());
		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		paint.setDither(true);
		canvas.drawARGB(0, 0, 0, 0);
		canvas.drawCircle(squareBitmap.getWidth() / 2,
				squareBitmap.getHeight() / 2, squareBitmap.getWidth() / 2,
				paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(squareBitmap, rect, rect, paint);
		// bitmap回收(recycle导致在布局文件XML看不到效果)
		// bmp.recycle();
		// squareBitmap.recycle();
		// scaledSrcBmp.recycle();
		bitmap = null;
		squareBitmap = null;

		return output;
	}

}
