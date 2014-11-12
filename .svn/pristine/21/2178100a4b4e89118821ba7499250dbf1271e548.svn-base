package com.sb.framework.utils;

import java.io.File;
import java.io.FileOutputStream;

import org.xmlpull.v1.XmlSerializer;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.util.Xml;

public class SmsUtils {

	private static final String TAG = "SmsUtils";
	
	
	public interface BackupCallback{
		public void beforeBackup(int total);
		
		public void onBackupProcess(int process);
	}
	

	public static void backupSms(Context context, String path, BackupCallback callback) throws Exception{
		XmlSerializer serializer = Xml.newSerializer();
		File file = new File(path);
		FileOutputStream fos = new FileOutputStream(file);
		serializer.setOutput(fos, "UTF-8");
		ContentResolver resolver = context.getContentResolver();
		Cursor cursor = resolver.query(Uri.parse("content://sms/"), new String[] { "address",
				"date", "type", "body" }, null, null, null);
		int total  = cursor.getCount();
		callback.beforeBackup(total);
		serializer.startDocument("UTF-8", true);
		serializer.startTag(null, "smss");
		int process = 0;
		while(cursor.moveToNext()){
			serializer.startTag(null, "sms");
			serializer.startTag(null, "address");
			serializer.text(cursor.getString(0));
			serializer.endTag(null, "address");
			
			serializer.startTag(null, "date");
			serializer.text(cursor.getString(1));
			serializer.endTag(null, "date");
			
			serializer.startTag(null, "type");
			serializer.text(cursor.getString(2));
			serializer.endTag(null, "type");
			
			serializer.startTag(null, "body");
			serializer.text(cursor.getString(3));
			serializer.endTag(null, "body");
		
			serializer.endTag(null, "sms");
			fos.flush();
			Thread.sleep(200);
			process++;
			
			callback.onBackupProcess(process);
		}
		serializer.endTag(null, "smss");
		serializer.endDocument();
		fos.close();
	}
}
