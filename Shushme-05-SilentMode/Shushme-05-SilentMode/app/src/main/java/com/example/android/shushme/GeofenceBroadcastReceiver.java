package com.example.android.shushme;

/*
* Copyright (C) 2017 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*  	http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

public class GeofenceBroadcastReceiver extends BroadcastReceiver {

    public static final String TAG = GeofenceBroadcastReceiver.class.getSimpleName();

    /***
     * Handles the Broadcast message sent when the Geofence Transition is triggered
     * Careful here though, this is running on the main thread so make sure you start an AsyncTask for
     * anything that takes longer than say 10 second to run
     *
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive called");
        // COMPLETED (4) Use GeofencingEvent.fromIntent to retrieve the GeofencingEvent that caused the transition

        // In onReceive, we can use GeofencingEvent.fromIntent to retrieve the GeofencingEvent that caused the transition.
        // From that we can call getGeofenceTransition to get the transition type.

        // Get the Geofence event from the Intent sent through
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);

        // COMPLETED (5) Call getGeofenceTransition to get the transition type and use AudioManager to set the
        // phone ringer mode based on the transition type. Feel free to create a helper method (setRingerMode)

        // get the transition type
        int geofencingTransition = geofencingEvent.getGeofenceTransition();
        // Cheecking which transition type has triggered this event
        if (geofencingTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
            setRingerMode(context, AudioManager.RINGER_MODE_SILENT);
        } else if (geofencingTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {
            setRingerMode(context, AudioManager.RINGER_MODE_NORMAL);
        } else {
            // Log the error
            Log.e(TAG, String.format("Unknown transition pops IN: %d", geofencingTransition));
            return;
        }
    }

    // And based on the transition type, we can use AudioManager to set the phone ringer mode.(Sielent or Normal)
    // It also creates a NotificationManager to check for permissions for Android N or later.

    private void setRingerMode(Context context, int mode) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT < 24 || (Build.VERSION.SDK_INT >= 24) || notificationManager.isNotificationPolicyAccessGranted()) {
            AudioManager audioManager = (AudioManager) context.getSystemService(context.AUDIO_SERVICE);
            audioManager.setRingerMode(mode);
        }
    }

    // COMPLETED (6) Show a notification to alert the user that the ringer mode has changed.
    // Feel free to create a helper method (sendNotification)

    // It would also be nice to have the app notify the user whenever the device has been set to silent or back to normal.
    // Checking the transition type to display the relevant icons
    private void sendNotification(Context context, int transition){
        // Create an explicit content Intent that starts the main Activity.
        Intent notificationIntent = new Intent(context, MainActivity.class);
        // Construct a task stack.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        // Add the main Activity to the task stack as the parent.
        stackBuilder.addParentStack(MainActivity.class);
        // Push the content Intent onto the stack.
        stackBuilder.addNextIntent(notificationIntent);
        // Get a PendingIntent containing the entire back stack.
        PendingIntent notificationPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        // Get a notification builder
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
        // Check the transition type to display the relevant icon image
        if (transition == Geofence.GEOFENCE_TRANSITION_ENTER){
            notificationBuilder
                    .setSmallIcon(R.drawable.ic_volume_off_white_24dp)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_volume_off_white_24dp))
                    .setContentTitle(context.getString(R.string.silent_mode_activated));
        } else if (transition == Geofence.GEOFENCE_TRANSITION_EXIT){
            notificationBuilder
                    .setSmallIcon(R.drawable.ic_volume_off_white_24dp)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_volume_off_white_24dp))
                    .setContentTitle(context.getString(R.string.back_to_normal));
        }
        // Continue building the notification
        notificationBuilder.setContentText(context.getString(R.string.touch_to_relaunch));
        notificationBuilder.setContentIntent(notificationPendingIntent);

        // Dismiss notification once the user touches it.
        notificationBuilder.setAutoCancel(true);

        // Get an instance of the Notification manager
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        // Issue the notification
        mNotificationManager.notify(0, notificationBuilder.build());
    }


}

