package com.restart.notification;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static int NOTIFICATION_ID = 1234;
    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity = this;

        Button simpleNotification = (Button) findViewById(R.id.simple_notification);
        simpleNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                String CHANNEL_ID = "my_channel_01";
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mActivity)
                        .setSmallIcon(R.drawable.ic_action_android)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!")
                        .setChannelId(CHANNEL_ID)
                        .setSound(soundUri);

                // Creates an explicit intent for an Activity in your app
                Intent resultIntent = new Intent(mActivity, MainActivity.class);

                // The stack builder object will contain an artificial back stack for the
                // started Activity.
                // This ensures that navigating backward from the Activity leads out of
                // your application to the Home screen.
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(mActivity);
                // Adds the back stack for the Intent (but not the Intent itself)
                stackBuilder.addParentStack(MainActivity.class);
                // Adds the Intent that starts the Activity to the top of the stack
                stackBuilder.addNextIntent(resultIntent);
                PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                mBuilder.setContentIntent(resultPendingIntent);
                NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                // mId allows you to update the notification later on.
                mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
            }
        });
    }
}
