package android.example.com.squawker.fcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.example.com.squawker.MainActivity;
import android.example.com.squawker.R;
import android.example.com.squawker.provider.SquawkContract;
import android.example.com.squawker.provider.SquawkProvider;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Created by BappY on 5/9/2017.
 * // COMPLETED (1) Make a new Service in the fcm package that extends from FirebaseMessagingService.
 // COMPLETED (2) As part of the new Service - Override onMessageReceived. This method will
 // be triggered whenever a squawk is received. You can get the data from the squawk
 // message using getData(). When you send a test message, this data will include the
 // following key/value pairs:
 // test: true
 // author: Ex. "TestAccount"
 // authorKey: Ex. "key_test"
 // message: Ex. "Hello world"
 // date: Ex. 1484358455343
 */

public class SquawkFirebaseMessageService extends FirebaseMessagingService {

    private static final int NOTIFICATION_MAX_CHARACTERS = 30;
    private static String LOG_TAG = SquawkFirebaseMessageService.class.getSimpleName();


    private static final String JSON_KEY_AUTHOR = SquawkContract.COLUMN_AUTHOR;
    private static final String JSON_KEY_AUTHOR_KEY = SquawkContract.COLUMN_AUTHOR_KEY;
    private static final String JSON_KEY_MESSAGE = SquawkContract.COLUMN_MESSAGE;
    private static final String JSON_KEY_DATE = SquawkContract.COLUMN_DATE;


    // Called when message is received.
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // There are two types of messages data messages and notification messages. Data messages
        // are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data
        // messages are the type
        // traditionally used with FCM. Notification messages are only received here in
        // onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated
        // notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages
        // containing both notification
        // and data payloads are treated as notification messages. The Firebase console always
        // sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options\

        // The Squawk server always sends just *data* messages, meaning that onMessageReceived when
        // the app is both in the foreground AND the background

        Log.d(LOG_TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        Map<String, String> data = remoteMessage.getData();
        if (data.size() > 0){
            Log.d(LOG_TAG, "Message date payload: " +data);

            // Sending notification to the app that wee got a message!
            sendNotification(data);
            // data insertion
            insertSquawk(data);
        }
    }

    // Create and show a simple notification containing the received FCM message
    private void sendNotification(Map<String, String> data) {

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Create the pending intent to launch the activity
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 , intent, PendingIntent.FLAG_ONE_SHOT);

        // variables for author and message
        String author = data.get(JSON_KEY_AUTHOR);
        String messagee = data.get(JSON_KEY_MESSAGE);

        // If the message is longer than the max number of characters we want in our
        // notification, truncate it and add the unicode character for ellipsis
        if (messagee.length()>NOTIFICATION_MAX_CHARACTERS){
            messagee = messagee.substring(0, NOTIFICATION_MAX_CHARACTERS);
        }
        // Creating the Uri for this new message sound notification
        Uri messageDefaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder messageNotificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_duck)
                .setContentTitle(String.format(getString(R.string.notification_message), author))
                .setContentText(messagee)
                .setAutoCancel(true)
                .setSound(messageDefaultSoundUri)
                .setContentIntent(pendingIntent);
        // NotificationManger will be used to notify app
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, messageNotificationBuilder.build());
    }

    private void insertSquawk(final Map<String, String> data) {

        // Database operations should not be done on the main thread
        AsyncTask<Void, Void, Void> insertSquawkTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                ContentValues newMessageValues = new ContentValues();

                // inserting the data
                newMessageValues.put(SquawkContract.COLUMN_AUTHOR, data.get(JSON_KEY_AUTHOR));
                newMessageValues.put(SquawkContract.COLUMN_MESSAGE, data.get(JSON_KEY_MESSAGE).trim());
                newMessageValues.put(SquawkContract.COLUMN_DATE, data.get(JSON_KEY_DATE));
                newMessageValues.put(SquawkContract.COLUMN_AUTHOR_KEY, data.get(JSON_KEY_AUTHOR_KEY));
                // its a common practice that we will use content resolver to insert data into the database
                getContentResolver().insert(SquawkProvider.SquawkMessages.CONTENT_URI, newMessageValues);
                return null;
            }
        };
        // executing AsyncTask loader
        insertSquawkTask.execute();
    }
}
