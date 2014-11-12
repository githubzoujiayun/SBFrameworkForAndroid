package com.sb.framework.utils;

//import com.wanda.sdk.platform.api.sharedpreferences.SharedPreferencesSaverManager;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;

public class NotificationUtils {
    public static final String PUSH_MESSAGE_NOTIFICATION_ID = "push_notification_id";
    private static final String NOTIFICATION_ID_KEY = "notification_id";

    public static final int BASE_NOTIFICATION_ID = 30000;

    public static void showNotification(Context context,
            Intent notificationIntent, String content, int notificationIcon) {
        showNotification(context, notificationIntent, content,
                notificationIcon, true);
    }

    public static void showNotification(Context context,
            Intent notificationIntent, String content, int notificationIcon,
            int notificationId) {
        showNotification(context, notificationIntent, content,
                notificationIcon, true, false, false, notificationId);
    }

    public static void showNotification(Context context,
            Intent notificationIntent, String content, int notificationIcon,
            boolean vibrate) {
        int notificationId = getNextNotificationId(context);
        showNotification(context, notificationIntent, content,
                notificationIcon, vibrate, false, false, notificationId);
    }

    public static void showNotification(Context context,
            PendingIntent notificationIntent, String content,
            int notificationIcon) {
        showNotification(context, notificationIntent, content,
                notificationIcon, true);
    }

    public static void showNotification(Context context,
            PendingIntent notificationIntent, String content,
            int notificationIcon, int notificationId) {
        showNotification(context, notificationIntent, content,
                notificationIcon, true, false, false, notificationId);
    }

    public static void showNotification(Context context,
            PendingIntent notificationIntent, String content,
            int notificationIcon, boolean vibrate) {
        int notificationId = getNextNotificationId(context);
        showNotification(context, notificationIntent, content,
                notificationIcon, vibrate, false, false, notificationId);
    }

    public static void showNotification(Context context,
            Intent notificationIntent, String content, int notificationIcon,
            boolean vibrate, boolean autoCancel, boolean onGoing,
            int notificationId) {
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        notificationIntent.putExtra(PUSH_MESSAGE_NOTIFICATION_ID,
                notificationId);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        showNotification(context, contentIntent, content, notificationIcon,
                vibrate, autoCancel, onGoing, notificationId);
    }

    public static void showNotification(Context context,
            PendingIntent contentIntent, String content, int notificationIcon,
            boolean vibrate, boolean autoCancel, boolean onGoing,
            int notificationId) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                context).setTicker(content).setWhen(System.currentTimeMillis())
                .setContentTitle(content).setSmallIcon(notificationIcon)
                .setContentIntent(contentIntent).setAutoCancel(autoCancel)
                .setOngoing(onGoing);
        if (vibrate) {
            long[] pattern = { 20, 150, 100, 150 };
            builder.setVibrate(pattern);
        }
        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        NotificationManager notificationMgr = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        notificationMgr.notify(notificationId, notification);
    }

    // Each incoming message gets its own notification. We have to use a new
    // unique notification id
    // for each one.
    @SuppressLint("NewApi")
	private static int getNextNotificationId(Context context) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        int notificationId = sp.getInt(NOTIFICATION_ID_KEY, 0);
        ++notificationId;
        if (notificationId > BASE_NOTIFICATION_ID) {
            notificationId = 1; // wrap around before it gets dangerous
        }

        // Save the updated notificationId in SharedPreferences
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(NOTIFICATION_ID_KEY, notificationId).apply();
        
        //new SharedPreferencesSaverManager().build().asyncSave(editor);

        return notificationId;
    }
}
