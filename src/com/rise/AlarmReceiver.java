package com.rise;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.base.L;

/**
 * Created by kai.wang on 3/12/14.
 */
public class AlarmReceiver extends BroadcastReceiver {
    public static final String ALARM_INTENT = "com.rise.alarm";

    public void onReceive(Context context, Intent intent) {
        L.i("alarm Receiver");
        showNotification(context);
    }

    private void showNotification(Context context){
        Bitmap btm = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context).setSmallIcon(R.drawable.ic_launcher)
                                  .setContentTitle(context.getString(R.string.please_record_today_event))
                                  .setContentText(context.getString(R.string.alarm_summary));
        // 第一次提示消息的时候显示在通知栏上
        builder.setTicker(context.getString(R.string.please_record_today_event));
        // builder.setNumber(12);
        builder.setLargeIcon(btm);
        // 自己维护通知的消失
        builder.setAutoCancel(true);
        builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);

        // 构建一个Intent
        Intent resultIntent = new Intent(context,LoadingActivity.class);
        // 封装一个Intent
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        // 设置通知主题的意图
        builder.setContentIntent(resultPendingIntent);

        // 获取通知管理器对象
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }
}
