package com.sb.framework.utils;

import java.util.List;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class PackageUtils {
    public static String getVersion(Context appContext) {
        String version = "1.0.0";
        PackageManager packageManager = appContext.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    appContext.getPackageName(), 0);
            version = packageInfo.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }
    
    public static int getVersionCode(Context appContext) {
        int version = 1000;
        PackageManager packageManager = appContext.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    appContext.getPackageName(), 0);
            version = packageInfo.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    public static String getPackageName(Context appContext) {
        String packageName = "com.wanda.sdk";
        PackageManager packageManager = appContext.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    appContext.getPackageName(), 0);
            packageName = packageInfo.packageName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageName;
    }

    /*
     * require    <uses-permission android:name="android.permission.GET_TASKS" />
     */
    public static boolean isMyPackageRunningOnTop(Context appContext) {
        ActivityManager am = (ActivityManager) appContext.getSystemService(Context.ACTIVITY_SERVICE);
        if(am == null) {
            return false;
        }
        List<ActivityManager.RunningTaskInfo> infos = am.getRunningTasks(1);
        if (infos != null && !infos.isEmpty()) {
            ActivityManager.RunningTaskInfo info = infos.get(0);
            ComponentName componentName = info.topActivity;
            if (componentName != null
                    && componentName.getPackageName().equals(appContext.getPackageName())) {
                return true;
            }
        }
        return false;
    }
}
