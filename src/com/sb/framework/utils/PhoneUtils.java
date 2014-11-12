package com.sb.framework.utils;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class PhoneUtils {
    public static boolean isEmulator(Context context){
        String androidId= Settings.System.getString(context.getContentResolver(), "android_id");
        if(TextUtils.isEmpty(androidId)
                || Build.MODEL.equals("sdk")
                || Build.MODEL.equals("google_sdk")){
            return true;
        }
        return false;
    }

    public static void hideSoftInputMode(Context context, View windowToken) {
        ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(windowToken.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
