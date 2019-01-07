package com.qishouqing.frame.receiver;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.qishouqing.frame.R;
import com.qishouqing.frame.TestActivity;
import com.qishouqing.frame.utils.LogUtils;

import cn.jpush.android.api.JPushInterface;

public class NoticeReceiver extends BroadcastReceiver {
    private static final String TAG = "NoticeReceiver";

    private NotificationManager nm;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (null == nm) {
            nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        Bundle bundle = intent.getExtras();

//        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
//            LogUtils.D(TAG, "JPush 用户注册成功");
//        } else
        if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            LogUtils.D(TAG, "接受到推送下来的自定义消息");
            String content = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            // 可选配置 可为json
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
            // 显示通知
            mynotify(context,  content);
        }
//        else if (JPushInterface.ACTION_NOTIFICATION_CLICK_ACTION.equals(intent.getAction())) {
//        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
//            LogUtils.D(TAG, "接受到推送下来的通知");
//        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
//            LogUtils.D(TAG, "用户点击打开了通知");
//        } else {
//            LogUtils.D(TAG, "Unhandled intent - " + intent.getAction());
//        }
    }

    public void mynotify(Context context, String msg_content) {
        String id = "channel_1";
        String description = "1234";
        NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                context, id);

        NotificationChannel mChannel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(id, description, NotificationManager.IMPORTANCE_DEFAULT);
            nm.createNotificationChannel(mChannel);
        }
        Notification notification;
        Intent i = new Intent(context,TestActivity.class);

        PendingIntent pi = PendingIntent.getActivity(context, 0, i,
                PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(pi);

        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setWhen(System.currentTimeMillis());
        builder.setContentTitle(context.getString(R.string.app_name));
        builder.setContentText(msg_content).setTicker(msg_content);

        notification = builder.build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        notification.defaults = Notification.DEFAULT_LIGHTS;
        notification.defaults = Notification.DEFAULT_VIBRATE;

        notification.defaults |= Notification.DEFAULT_SOUND;

        nm.notify(0, notification);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            nm.deleteNotificationChannel(String.valueOf(mChannel));
        }
    }


}
