package com.sb.framework.bitmap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Typeface;
import android.os.Environment;

import com.sb.framework.R;

/**
 * 影像服务器需要的完整身份证图片生成工具
 * 
 * @author Administrator
 *
 */
public class CertPicCreatUtil {

	private Context context;

	public CertPicCreatUtil(Context context) {
		this.context = context;
	}

	// /** 获取证件图片 **/
	// public void initGoalView(ImageView frontView, ImageView backView) {
	// this.viewFront = frontView;
	// this.viewback = backView;
	// }

	/**
	 * 正面合成
	 * 
	 * @param decodeInfo
	 *            需要合成的数据
	 * @throws IOException
	 *             错误
	 */
	@SuppressLint("NewApi")
	public Map<String, String> creatBitmap(String decodeInfo[], String outpath)
			throws IOException {
		Map<String, String> map = new HashMap<String, String>();
		File Folder = new File(outpath);
		if (!Folder.exists()) {
			Folder.mkdirs();
		}
		Paint mPaint = new Paint();
		if (decodeInfo[0] == "1") {
			// imageF.setImageBitmap(BitmapFactory.decodeResource(
			// context.getResources(), R.drawable.f));
			// imageB.setImageBitmap(BitmapFactory.decodeResource(
			// context.getResources(), R.drawable.b));
			return null;
		}
		Bitmap bmpF = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.cert_front);
		int FcvWidth = bmpF.getWidth();
		int FcvHeight = bmpF.getHeight();
		Bitmap newbmpF = Bitmap.createBitmap(FcvWidth, FcvHeight,
				Config.RGB_565);
		float FontMultf = (float) (FcvHeight * FcvHeight + FcvWidth * FcvWidth)
				/ (float) (329 * 329 + 210 * 210);
		FontMultf = (float) Math.sqrt(FontMultf);
		float xMultF = (float) FcvWidth / 329f;
		float yMultF = (float) FcvHeight / 210f;
		Canvas Fcv = new Canvas(newbmpF);
		Fcv.drawBitmap(bmpF, 0, 0, mPaint);
		int bsC = Color.argb(0, 71, 95, 155);
		Paint Fp = new Paint(Paint.ANTI_ALIAS_FLAG);
		Fp.setTextAlign(Paint.Align.LEFT);
		Fp.setColor(bsC);
		Fp.setAntiAlias(true);
		Fp.setDither(true);
		Fp.setAlpha(255);
		Fp.setStyle(android.graphics.Paint.Style.FILL);
		Fp.setTextSize(13 * FontMultf);

		Fcv.drawText("姓  名", 23 * xMultF, 39 * yMultF, Fp);
		Fcv.drawText("性  别", 23 * xMultF, 66 * yMultF, Fp);
		Fcv.drawText("民  族", 98 * xMultF, 66 * yMultF, Fp);
		Fcv.drawText("出  生", 23 * xMultF, 92 * yMultF, Fp);
		Fcv.drawText("年", 98 * xMultF, 92 * yMultF, Fp);
		Fcv.drawText("月", 131 * xMultF, 92 * yMultF, Fp);
		Fcv.drawText("日", 165 * xMultF, 92 * yMultF, Fp);
		Fcv.drawText("住  址", 23 * xMultF, 115 * yMultF, Fp);
		Fcv.drawText("公民身份号码", 23 * xMultF, 184 * yMultF, Fp);

		Fp.setColor(Color.BLACK);
		Fp.setTextSize(14 * FontMultf);
		Fcv.drawText(decodeInfo[0], 66 * xMultF, 38 * yMultF, Fp);
		Fp.setTextSize(13 * FontMultf);
		Fcv.drawText(decodeInfo[1], 66 * xMultF, 65 * yMultF, Fp);
		Fcv.drawText(decodeInfo[2], 138 * xMultF, 65 * yMultF, Fp);
		Fcv.drawText(decodeInfo[3].substring(0, 4), 66 * xMultF, 92 * yMultF,
				Fp);
		if (decodeInfo[3].substring(4, 5).endsWith("0")) {
			Fcv.drawText(decodeInfo[3].substring(5, 6), 119 * xMultF,
					92 * yMultF, Fp);
		} else {
			Fcv.drawText(decodeInfo[3].substring(4, 6), 116 * xMultF,
					92 * yMultF, Fp);
		}
		if (decodeInfo[3].substring(6, 7).endsWith("0")) {
			Fcv.drawText(decodeInfo[3].substring(7, 8), 153 * xMultF,
					92 * yMultF, Fp);
		} else {
			Fcv.drawText(decodeInfo[3].substring(6, 8), 150 * xMultF,
					92 * yMultF, Fp);
		}

		String straddr = decodeInfo[4];
		if (straddr.length() > 22) {
			Fcv.drawText(straddr.substring(0, 11), 66 * xMultF, 115 * yMultF,
					Fp);
			Fcv.drawText(straddr.substring(11, 22), 66 * xMultF, 137 * yMultF,
					Fp);
			Fcv.drawText(straddr.substring(22, straddr.length()), 66 * xMultF,
					157 * yMultF, Fp);
		} else {
			if (straddr.length() > 11) {
				Fcv.drawText(straddr.substring(0, 11), 66 * xMultF,
						115 * yMultF, Fp);
				Fcv.drawText(straddr.substring(11, straddr.length()),
						63 * xMultF, 137 * yMultF, Fp);
			} else {
				Fcv.drawText(straddr.substring(0, straddr.length()),
						63 * xMultF, 115 * yMultF, Fp);
			}
		}

		Fp.setTextSize(14 * FontMultf);
		Fp.setTypeface(Typeface.DEFAULT_BOLD);
		Fp.setStrokeWidth(3f);
		Fp.setTextScaleX(1.2f);
		Fcv.drawText(decodeInfo[5], 115 * xMultF, 183 * yMultF, Fp);

		Fcv.save(Canvas.ALL_SAVE_FLAG);
		Fcv.restore();
		Matrix matrix = new Matrix();
		matrix.postScale(xMultF, yMultF); // 长和宽放大缩小的比例
		Bitmap bmphead = BitmapFactory.decodeFile(Environment
				.getExternalStorageDirectory() + "/wltlib/zp.bmp");
		bmphead = Bitmap.createBitmap(bmphead, 0, 0, bmphead.getWidth(),
				bmphead.getHeight(), matrix, true);
		// bmphead.setHeight((int) (bmphead.getHeight() * 1.2));
		// bmphead.setWidth((int) (bmphead.getWidth() * 1.2));
		int colorflage = bmphead.getPixel(5, 5);
		Paint phead = new Paint();
		for (int i = 0; i < bmphead.getWidth(); i++) {
			for (int j = 0; j < bmphead.getHeight() - 3; j++) {
				if (bmphead.getPixel(i, j) != colorflage) {
					phead.setColor(bmphead.getPixel(i, j));
					Fcv.drawPoint(211 * xMultF + i, 31 * yMultF + j, phead);
				}
			}
		}
		phead.reset();
		Fcv.save(Canvas.ALL_SAVE_FLAG);
		Fcv.restore();

		OutputStream Fos = null;
		try {
			File FdestFile = new File(outpath
					+ decodeInfo[0] + "-"
					+ decodeInfo[5] + "_front.png");
			FdestFile.createNewFile();
			Fos = new FileOutputStream(FdestFile);
			newbmpF.compress(Bitmap.CompressFormat.JPEG, 100, Fos);

		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (Fos != null) {
				try {
					Fos.flush();
					Fos.close();
				} catch (IOException e) {
				}
			}
		}

		Bitmap bmpB = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.cert_back);
		Bitmap newbmpB = Bitmap.createBitmap(bmpB.getWidth(), bmpB.getHeight(),
				Config.ARGB_8888);
		int BcvWidth = bmpF.getWidth();
		int BcvHeight = bmpF.getHeight();

		float FontMultB = (float) (BcvWidth * BcvWidth + BcvHeight * BcvHeight)
				/ (float) (329 * 329 + 210 * 210);
		FontMultB = (float) Math.sqrt(FontMultB);
		float xMultB = (float) BcvWidth / 329f;
		float yMultB = (float) BcvHeight / 210f;

		Canvas Bcv = new Canvas(newbmpB);
		Bcv.drawBitmap(bmpB, 0, 0, mPaint);

		Fp.reset();
		Fp.setTextAlign(Paint.Align.LEFT);
		Fp.setColor(Color.BLACK);
		Fp.setAntiAlias(true);
		Fp.setDither(true);
		Fp.setAlpha(255);
		Fp.setStyle(android.graphics.Paint.Style.FILL);
		Fp.setTextSize(13 * FontMultB);
		Fp.setTypeface(Typeface.DEFAULT_BOLD);

		Bcv.drawText("签发机关", 68 * xMultB, 169 * yMultB, Fp);
		Bcv.drawText("有效期限", 68 * xMultB, 193 * yMultB, Fp);

		Fp.setTypeface(Typeface.DEFAULT);
		Bcv.drawText(decodeInfo[6], 130 * xMultB, 169 * yMultB, Fp);
		if (decodeInfo[8] == "长期") {
			Bcv.drawText(
					decodeInfo[7].substring(0, 4) + "."
							+ decodeInfo[7].substring(4, 6) + "."
							+ decodeInfo[7].substring(6, 8) + "-"
							+ decodeInfo[8], 130 * xMultB, 193 * yMultB, Fp);
		} else {
			Bcv.drawText(
					decodeInfo[7].substring(0, 4) + "."
							+ decodeInfo[7].substring(4, 6) + "."
							+ decodeInfo[7].substring(6, 8) + "-"
							+ decodeInfo[8].substring(0, 4) + "."
							+ decodeInfo[8].substring(4, 6) + "."
							+ decodeInfo[8].substring(6, 8), 130 * xMultB,
					193 * yMultB, Fp);
		}

		Bcv.save(Canvas.ALL_SAVE_FLAG);
		Bcv.restore();

		OutputStream Bos = null;
		try {
			File BdestFile = new File(outpath
					+ decodeInfo[0] + "-"
					+ decodeInfo[5] + "_back.png");
			BdestFile.createNewFile();
			Bos = new FileOutputStream(BdestFile);
			newbmpB.compress(Bitmap.CompressFormat.JPEG, 100, Bos);
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (Bos != null) {
				try {
					Bos.flush();
					Bos.close();
				} catch (IOException e) {
				}
			}
		}

		FileInputStream fis = null;
		try {
			fis = new FileInputStream(outpath
					+ decodeInfo[0] + "-"
					+ decodeInfo[5] + "_front.png");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		Bitmap bmp = BitmapFactory.decodeStream(fis);
		fis.close();

		FileInputStream fisB = null;
		try {
			fisB = new FileInputStream(outpath
					+ decodeInfo[0] + "-"
					+ decodeInfo[5] + "_back.png");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Bitmap bmpBB = BitmapFactory.decodeStream(fisB);
		fisB.close();

		Bitmap newbmp = null;
		newbmp = compositeImages(bmp, bmpBB);
		String name = decodeInfo[0].trim();
		/**
		 * 正反面图片地址
		 */
		File BdestFile = new File(outpath
				+ decodeInfo[0] + "-" + decodeInfo[5]
				+ ".png");// 正反面图片
		BdestFile.createNewFile();
		Bos = new FileOutputStream(BdestFile);
		newbmp.compress(Bitmap.CompressFormat.PNG, 100, Bos);

		String prefix = outpath+decodeInfo[0]+ "-"+decodeInfo[5];
		map.put("C.FRONTCERTIMG",prefix + "_front.png");
		map.put("C.BACKCERTIMG",prefix + "_back.png");
		map.put("C.FULLCERTIMG",prefix + ".png");
		return map;
	}

	/**
	 * 合成图片
	 * 
	 * @param frontBitmap
	 *            证件正面位图
	 * @param backBitmap
	 *            证件背面位图
	 * @return 合成后的位图
	 */
	private Bitmap compositeImages(Bitmap frontBitmap, Bitmap backBitmap) {
		Bitmap bmp = null;
		// 创建一个空的Bitmap
		bmp = Bitmap.createBitmap(frontBitmap.getWidth(),
				frontBitmap.getHeight() + backBitmap.getHeight(),
				frontBitmap.getConfig());
		Paint paint = new Paint();
		Canvas canvas = new Canvas(bmp);
		// 绘制第一张图片
		canvas.drawBitmap(frontBitmap, 0, 0, paint);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DARKEN));
		canvas.drawBitmap(backBitmap, 0, frontBitmap.getHeight(), paint);
		return bmp;
	}

}