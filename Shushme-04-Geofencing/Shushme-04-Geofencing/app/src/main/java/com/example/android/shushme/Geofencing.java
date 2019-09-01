package com.example.android.shushme;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BappY on 5/11/2017.
 */

public class Geofencing implements ResultCallback {

    public static final String TAG = Geofencing.class.getSimpleName();

    // constants
    private static final long GEOFENCE_TIMEOUT = 24 * 60 * 60 * 1000;  // 24 hours in mili seconds
    private static final float GEOFENCE_RADIUS = 50 ; // 50  meters of radius


    // COMPLETED (1) Create a Geofencing class with a Context and GoogleApiClient constructor that
    // initializes a private member ArrayList of Geofences called mGeofenceList
    private GoogleApiClient mGoogleApiClient;
    private Context mContext;
    private List<Geofence> mGeofenceList;
    private PendingIntent mGeofencePendingIntent;

    public Geofencing(Context context, GoogleApiClient googleApiClient){
        this.mContext = context;
        this.mGoogleApiClient = googleApiClient;
        mGeofenceList = new ArrayList<>();
        mGeofencePendingIntent = null;
    }
    // COMPLETED (2) Inside Geofencing, implement a public method called updateGeofencesList that
    // given a PlaceBuffer will create a Geofence object for each Place using Geofence.Builder
    // and add that Geofence to mGeofenceList
    public void updateGeofencesList(PlaceBuffer places){
        // initializing the geofence list
        mGeofenceList = new ArrayList<>();
        if (places == null || places.getCount()==0) return;
        for (Place place:places){
            // Read the place information from the database cursor and store it to variables
            String placeUID = place.getId();
            double placeLatitude = place.getLatLng().latitude;
            double placceLongitude = place.getLatLng().longitude;
            // as we havee necessary data available for the geo fence to kick start, lets build a geofence object
            Geofence geofence = new Geofence.Builder()
                    .setRequestId(placeUID)
                    .setExpirationDuration(GEOFENCE_TIMEOUT)
                    .setCircularRegion(placeLatitude, placceLongitude, GEOFENCE_RADIUS)
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
                    .build();
            // lastly Add it to the ArrayList of geofence values
            mGeofenceList.add(geofence);
        }
    }

    // COMPLETED (3) Inside Geofencing, implement a private helper method called getGeofencingRequest that
    // uses GeofencingRequest.Builder to return a GeofencingRequest object from the Geofence list
    private GeofencingRequest getGeofencingRequest(){
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(mGeofenceList);
        return builder.build();
    }

    // COMPLETED (5) Inside Geofencing, implement a private helper method called getGeofencePendingIntent that
    // returns a PendingIntent for the GeofenceBroadcastReceiver class
    private PendingIntent getGeofencePendingIntent(){
        // reuse the pending intent once we have it
        if (mGeofencePendingIntent != null) return mGeofencePendingIntent;
        Intent intent = new Intent(mContext, GeofenceBroadcastReceiver.class);
        mGeofencePendingIntent = PendingIntent.getBroadcast(mContext, 0 , intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return mGeofencePendingIntent;
    }

    // COMPLETED (6) Inside Geofencing, implement a public method called registerAllGeofences that
    // registers the GeofencingRequest by calling LocationServices.GeofencingApi.addGeofences
    // using the helper functions getGeofencingRequest() and getGeofencePendingIntent()
    public void registerAllGeofences(){
        // Registers the list of Geofences specified in mGeofenceList with Google Place Services
        // Check that the API client is connected and that the list has Geofences in it
        if (mGoogleApiClient == null || mGoogleApiClient.isConnected() || mGeofenceList == null || mGeofenceList.size()==0) return;

        try {
            LocationServices.GeofencingApi.addGeofences(
                    mGoogleApiClient,
                    getGeofencingRequest(),
                    getGeofencePendingIntent()
            ).setResultCallback(this);
        } catch (SecurityException securityException){
            Log.e(TAG, securityException.getMessage());
        }
    }

    // COMPLETED (7) Inside Geofencing, implement a public method called unRegisterAllGeofences that
    // unregisters all geofences by calling LocationServices.GeofencingApi.removeGeofences
    // using the helper function getGeofencePendingIntent()
    public void unRegisterAllGeofences(){
        // Unregisters all the Geofences created by this app from Google Place Services
        // Check that the API client is connected and that the list has Geofences in it
        if (mGoogleApiClient == null || mGoogleApiClient.isConnected()) return;
        try {
            LocationServices.GeofencingApi.removeGeofences(
                    mGoogleApiClient,
                    getGeofencePendingIntent()
            ).setResultCallback(this);
        } catch (SecurityException securityException){
            // Catch exception generated if the app does not use ACCESS_FINE_LOCATION permission.
            Log.e(TAG, securityException.getMessage());
        }
    }


    @Override
    public void onResult(@NonNull Result result) {
        Log.e(TAG, String.format("Error adding/removing geofence : %s", result.getStatus().toString()));
    }
}
