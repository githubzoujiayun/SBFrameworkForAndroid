package com.sb.framework.http.file;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;

public class FileDownloadUseSystem {
	@SuppressLint("NewApi")
	public static void download(Context context, String url) {
		DownloadManager downloadManager = (DownloadManager) context
				.getSystemService(Context.DOWNLOAD_SERVICE);
		String apkUrl = url; // "http://img.meilishuo.net/css/images/AndroidShare/Meilishuo_3.6.1_10006.apk";
		DownloadManager.Request request = new DownloadManager.Request(
				Uri.parse(apkUrl));
		request.setDestinationInExternalPublicDir("meiweidian",
				"meiweidian.apk");
		request.setTitle("美味点");
		request.setDescription("餐厅管家，绿色免费");
		request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
		// request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
		//request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
		// request.setMimeType("application/com.trinea.download.file");
		long downloadId = downloadManager.enqueue(request);

	}
}
