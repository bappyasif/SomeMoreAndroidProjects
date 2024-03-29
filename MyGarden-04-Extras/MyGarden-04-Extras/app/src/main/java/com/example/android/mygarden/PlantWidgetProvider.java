package com.example.android.mygarden;

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

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.example.android.mygarden.provider.PlantContract;
import com.example.android.mygarden.ui.MainActivity;
import com.example.android.mygarden.ui.PlantDetailActivity;

public class PlantWidgetProvider extends AppWidgetProvider {

    // LOG TAG CONSTANTS
    private static final String LOG_TAG = PlantWidgetProvider.class.getSimpleName();
    // setImageViewResource to update the widget’s image
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int imgRes, long plantId, boolean showWater, int appWidgetId) {

        // COMPLETED (3): Set the click handler to open the DetailActivity for plant ID,

        // Set the click handler to open the DetailActivity for plant ID,
        // or the MainActivity if plant ID is invalid

        Intent intent;
        if (plantId == PlantContract.INVALID_PLANT_ID){
            intent = new Intent(context, MainActivity.class);
        } else {
            // Set on Click to open the individual plant detail class instead fo main activity
            Log.d(LOG_TAG, "plantID: " +plantId);
            intent = new Intent(context, PlantDetailActivity.class);
            // putting the Extra information on individual Plant's
            intent.putExtra(PlantDetailActivity.EXTRA_PLANT_ID, plantId);
        }
        // Once we have figured out which intent to launch now we will prepeare it for startActivity using PendingIntent
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.plant_widget);
        // Update image
        views.setImageViewResource(R.id.widget_plant_image, imgRes);
        // Update PlantID text
        views.setTextViewText(R.id.widget_plant_name, String.valueOf(plantId));
        // Show/Hide the Water Drop Button
        if (showWater) {
            views.setViewVisibility(R.id.widget_water_button, View.VISIBLE);
        } else {
            views.setViewVisibility(R.id.widget_water_button, View.INVISIBLE);
        }
        // Widgets allow click handlers to only launch pending intents
        views.setOnClickPendingIntent(R.id.widget_plant_image, pendingIntent);
        // Add the wateringservice click handler
        Intent wateringIntent = new Intent(context, PlantWateringService.class);
        wateringIntent.setAction(PlantWateringService.ACTION_WATER_PLANT);
        wateringIntent.putExtra(PlantWateringService.EXTRA_PLANT_ID, plantId);
        PendingIntent wateringPendingIntent = PendingIntent.getService(context, 0, wateringIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widget_water_button, wateringPendingIntent);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        //Start the intent service update widget action, the service takes care of updating the widgets UI
        PlantWateringService.startActionUpdatePlantWidgets(context);
    }

    // COPLETED (2): Modify updatePlantWidgets and updateAppWidget to pass the plant ID as well as a boolean
    // to show/hide the water button
    public static void updatePlantWidgets(Context context, AppWidgetManager appWidgetManager,
                                          int imgRes, long plantID, boolean showWater,
                                          int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, imgRes, plantID, showWater, appWidgetId);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // Perform any action when one or more AppWidget instances have been deleted
    }

    @Override
    public void onEnabled(Context context) {
        // Perform any action when an AppWidget for this provider is instantiated
    }

    @Override
    public void onDisabled(Context context) {
        // Perform any action when the last AppWidget instance for this provider is deleted
    }

}
