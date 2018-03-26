package com.restart.notification;

import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int NOTIFICATION_ID = 1234;
    private NotificationManager mNotificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.createNotificationChannelGroup(new NotificationChannelGroup("group_id", "group name"));

        NotificationChannel smsNotificationChannel = new NotificationChannel(getString(R.string.sms_channel_id),
                getString(R.string.sms_channel_name), NotificationManager.IMPORTANCE_DEFAULT);
        NotificationChannel pictureNotificationChannel = new NotificationChannel(getString(R.string.picture_channel_id),
                getString(R.string.picture_channel_name), NotificationManager.IMPORTANCE_DEFAULT);
        smsNotificationChannel.setGroup("group_id");
        pictureNotificationChannel.setGroup("group_id");

        mNotificationManager.createNotificationChannel(smsNotificationChannel);
        mNotificationManager.createNotificationChannel(pictureNotificationChannel);

        findViewById(R.id.send_sms_notification).setOnClickListener(this);
        findViewById(R.id.send_picture_notification).setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MainActivity.this)
                .setSmallIcon(R.drawable.ic_action_android);

        switch (v.getId()) {
            case R.id.send_sms_notification:
                mBuilder.setContentTitle(getString(R.string.sms_notification_title))
                        .setContentText(getString(R.string.sms_notification_content))
                        .setChannelId(getString(R.string.sms_channel_id));
                break;
            case R.id.send_picture_notification:
                mBuilder.setContentTitle(getString(R.string.picture_notification_title))
                        .setContentText(getString(R.string.picture_notification_content))
                        .setChannelId(getString(R.string.sms_channel_id));
                break;
        }

        Intent resultIntent = new Intent(MainActivity.this, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(MainActivity.this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}
