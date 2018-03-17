package com.ketank619gmail.govschemes;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Ketan-PC on 12/15/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.




        Intent intent = new Intent();
        intent.setAction("com.ketank619gmail.govschemes.CUSTOM_EVENT");
        intent.putExtra("Key", remoteMessage.getData().get("data"));
        LocalBroadcastManager.getInstance(getBaseContext()).sendBroadcast(intent);

        Intent intent1= new Intent(this, MainActivity.class);
// use System.currentTimeMillis() to have a unique ID for the pending intent
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent1, 0);


       if( remoteMessage.getData().get("data").equalsIgnoreCase("new_scheme")) {
           Notification n = new Notification.Builder(this)
                   .setContentTitle("New Scheme Added")
                   .setContentText("Tap to see")
                   .setContentIntent(pIntent)
                   .setSmallIcon(R.drawable.parliament)
                   .setAutoCancel(true)
                   .build();


           NotificationManager notificationManager =
                   (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

           notificationManager.notify(0, n);
       }




        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
}
