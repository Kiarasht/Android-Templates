package com.restart.notification;

import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * Group id constants, required for initialization and to place channels into them.
     */
    private static final String GROUP_ID_SMS = "group_id_sms";
    private static final String GROUP_ID_PROMOTION = "promotion_id_sms";

    /**
     * Channels id constants, required on initialization and possible later usage.
     */
    private static final String SMS_CHANNEL_ID = "sms_channel_id";
    private static final String PICTURE_CHANNEL_ID = "picture_channel_id";
    private static final String PROMOTION_CHANNEL_ID = "promotion_channel_id";

    private static int NOTIFICATION_ID;
    private static final int PRIORITY_DEFAULT = NotificationManager.IMPORTANCE_DEFAULT;
    private NotificationManager mNotificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Create a notification manager, and create two groups that will categorize the notification channels */
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.createNotificationChannelGroup(new NotificationChannelGroup(GROUP_ID_SMS, getString(R.string.SMS)));
        mNotificationManager.createNotificationChannelGroup(new NotificationChannelGroup(GROUP_ID_PROMOTION, getString(R.string.Promotions)));

        /* Create two notification channels for the group SMS, one for receiving texts, and one for pictures */
        NotificationChannel smsNotificationChannel = new NotificationChannel(SMS_CHANNEL_ID, getString(R.string.sms_channel_name), PRIORITY_DEFAULT);
        NotificationChannel pictureNotificationChannel = new NotificationChannel(PICTURE_CHANNEL_ID, getString(R.string.picture_channel_name), PRIORITY_DEFAULT);
        pictureNotificationChannel.setGroup(GROUP_ID_SMS);
        smsNotificationChannel.setGroup(GROUP_ID_SMS);

        /* Create one notification channel for ads, that will go into a promotional group */
        NotificationChannel promotionNotificationChannel = new NotificationChannel(PROMOTION_CHANNEL_ID, getString(R.string.ads_channel_name), PRIORITY_DEFAULT);
        promotionNotificationChannel.setGroup(GROUP_ID_PROMOTION);

        /* Use the notification manager to finalize the initializations of the channels */
        mNotificationManager.createNotificationChannel(smsNotificationChannel);
        mNotificationManager.createNotificationChannel(pictureNotificationChannel);
        mNotificationManager.createNotificationChannel(promotionNotificationChannel);

        /* Set the onClickListeners for each button on the main page */
        findViewById(R.id.send_sms_notification).setOnClickListener(this);
        findViewById(R.id.send_picture_notification).setOnClickListener(this);
        findViewById(R.id.send_promotion_notification).setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param view The view that was clicked.
     */
    @Override
    public void onClick(View view) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MainActivity.this)
                .setSmallIcon(R.drawable.ic_action_android);

        /* Send a basic notification and modify basic features of it such as title, description, and which
        * channel it's coming from. This helps Android know how to behave in terms of sound, vibration by
        * letting it know the channel. */
        switch (view.getId()) {
            case R.id.send_sms_notification:
                mBuilder.setContentTitle(getString(R.string.sms_notification_title))
                        .setContentText(getString(R.string.sms_notification_content))
                        .setChannelId(SMS_CHANNEL_ID);
                NOTIFICATION_ID = 1234;
                break;
            case R.id.send_picture_notification:
                mBuilder.setContentTitle(getString(R.string.picture_notification_title))
                        .setContentText(getString(R.string.picture_notification_content))
                        .setChannelId(PICTURE_CHANNEL_ID);
                NOTIFICATION_ID = 2345;
                break;
            case R.id.send_promotion_notification:
                mBuilder.setContentTitle(getString(R.string.promotion_notification_title))
                        .setContentText(getString(R.string.promotion_notification_content))
                        .setChannelId(PROMOTION_CHANNEL_ID);
                NOTIFICATION_ID = 3456;
                break;
        }

        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}
