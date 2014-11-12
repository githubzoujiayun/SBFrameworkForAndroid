
package com.sb.framework.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.CallLog;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class PhoneInfo {

    private static final String TAG = "PhoneInfo";

    /**
     * 获得通话记录
     * 
     * @param context
     */
    @SuppressLint("SimpleDateFormat")
    public static void printCallLog(Context context) {

        String str = "";
        String str2 = "";
        int type;
        Date date;
        String time = "";
        ContentResolver cr = context.getContentResolver();
        final Cursor cursor = cr.query(CallLog.Calls.CONTENT_URI, new String[] {
                CallLog.Calls.NUMBER, CallLog.Calls.CACHED_NAME, CallLog.Calls.TYPE,
                CallLog.Calls.DATE
        }, null, null, CallLog.Calls.DEFAULT_SORT_ORDER);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            str = cursor.getString(0);// 电话好吗
            str2 = cursor.getString(1);// 名字
            type = cursor.getInt(2);// 1，打进来的电话。2， 拨打出去的，3，未接电话
            SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            date = new Date(Long.parseLong(cursor.getString(3)));
            time = sfd.format(date);
            Log.e(TAG, "str:" + str + " , str2:" + str2 + " , type:" + type + " , time:" + time);
        }
    }

    /**
     * 隐藏软键盘
     */
    public static void hideSoftInputMode(Context context, View windowToken) {
        ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(windowToken.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 获得channel id
     * 
     * @param ctx <meta-data android:name="SZICITY_CHANNEL"
     *            android:value="CHANNEL108"/>
     * @return
     */
    public static String getMetaData(Context context, String key) {
        String CHANNELID = "000000";
        if (TextUtils.isEmpty(key)) {
            key = "SZICITY_CHANNEL";
        }
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
            CHANNELID = ai.metaData.getString(key);
        } catch (Exception e) {
        }
        Log.e(TAG, "CHANNELID:" + CHANNELID);
        return CHANNELID;
    }

    /**
     * 获取当前的手机号
     */
    public static String getPhoneNumber(Context context) {
        TelephonyManager tManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String number = tManager.getLine1Number();
        Log.e(TAG, "所获得的手机号：" + number);
        return number;
    }

    

    /**
     * 检查清单文件是否配置某些权限
     * 
     * @param paramContext
     * @param paramString
     * @return
     */
    protected static boolean checkPermission(Context paramContext, String paramString) {
        PackageManager localPackageManager = paramContext.getPackageManager();
        return (localPackageManager.checkPermission(paramString, paramContext.getPackageName()) != 0);
    }

    // protected static String[] getGpuInfo(GL10 paramGL10)
    // {
    // try
    // {
    // String[] arrayOfString = new String[2];
    // String str1 = paramGL10.glGetString(7936);
    // String str2 = paramGL10.glGetString(7937);
    // arrayOfString[0] = str1;
    // arrayOfString[1] = str2;
    // return arrayOfString;
    // }
    // catch (Exception localException)
    // {
    // Log.e(TAG, "Could not read gpu infor:", localException);
    // }
    // return new String[0];
    // }

    /**
     * 获取终端设备的CPU信息
     */
    protected static String getCpuInfo() {
        String str = null;
        FileReader localFileReader = null;
        BufferedReader localBufferedReader = null;
        try {
            localFileReader = new FileReader("/proc/cpuinfo");
            if (localFileReader != null)
                try {
                    localBufferedReader = new BufferedReader(localFileReader, 1024);
                    str = localBufferedReader.readLine();
                    localBufferedReader.close();
                    localFileReader.close();
                } catch (IOException localIOException) {
                    Log.e(TAG, "Could not read from file /proc/cpuinfo", localIOException);
                }
        } catch (FileNotFoundException localFileNotFoundException) {
            Log.e(TAG, "Could not open file /proc/cpuinfo", localFileNotFoundException);
        }
        if (str != null) {
            int i = str.indexOf(58) + 1;
            str = str.substring(i);
        }
        return str.trim();
    }
}
