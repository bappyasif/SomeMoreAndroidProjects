package android.example.com.squawker.following;

import android.content.SharedPreferences;
import android.example.com.squawker.R;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.SwitchPreferenceCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;

/**
 * Created by BappY on 5/9/2017.
 */

public class FollowingPreferenceActivity extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String LOG_TAG = FollowingPreferenceActivity.class.getSimpleName();

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        // adding the visual layout for following preference activity.
        addPreferencesFromResource(R.xml.following_squawker);
    }

    // we also need to override onCreate and onDestroy method as well to ensure its full funtionality in app

    // we will register the prference in onCreate()
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // adding the shared prefernce change listener in the activity
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    // we will unregister the prefernce activity in onDestroy()
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Removing the shared prefernce change listener from the activity
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    /** Triggered when shared preferences changes. This will be triggered when a person is followed
     * or un-followed*/
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        if (preference != null && preference instanceof SwitchPreferenceCompat){
            // get the current state of the preference from subscribe to list
            boolean subsctibeIsOn = sharedPreferences.getBoolean(key, false);
            if (subsctibeIsOn){
                // when the subscription is on for that the associate instructor in FCM

                // subscribing to that associate intrustor from that squawk app instructor list.
                FirebaseMessaging.getInstance().subscribeToTopic(key);
                Log.d(LOG_TAG, "Subscribing to: " +key);
            } else {
                // Un subscribing
                FirebaseMessaging.getInstance().unsubscribeFromTopic(key);
                Log.d(LOG_TAG, "Unsubscring from: " +key);
            }
        }
    }
}
