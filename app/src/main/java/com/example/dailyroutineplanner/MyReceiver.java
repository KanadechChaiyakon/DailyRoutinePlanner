package com.example.dailyroutineplanner;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class MyReceiver extends BroadcastReceiver {

    private String ActivityName, Detail;

    @Override
    public void onReceive(Context context, Intent intent) {

        ActivityName = intent.getStringExtra("ActivityName");
        Detail = intent.getStringExtra("Detail");


        Intent intent12 = new Intent(context,MainActivity.class);
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(context,0,intent12  ,0);


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "SAMPLE_CHANNEL";
            NotificationChannel channel = new NotificationChannel(channelId, "Notitest", NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(true);
            notificationManager.createNotificationChannel(channel);
        }



        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"SAMPLE_CHANNEL")
                .setSmallIcon(R.drawable.ic_baseline_post_add_24)
                .setContentTitle(ActivityName)
                .setContentText(Detail)
                .setContentIntent(pendingIntent1)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        notificationManager.notify(1,builder.build());

    }
}
