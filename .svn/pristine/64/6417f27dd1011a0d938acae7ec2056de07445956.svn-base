package com.sb.framework.http.image;

import android.graphics.Bitmap;

public interface OnImageDownloadCallback{
	void onStart();
	void onOK(String path);
	void onFuck(String errorInfo);
	void onLoading(int progress, int total);

	/**
	 * Volly直接提供了返回Bitmap的实现，且缩放效果更好，所以单独为Volly提供了这个回调
	 * @param bitmap
	 */
	//void onOK(Bitmap bitmap);
}
