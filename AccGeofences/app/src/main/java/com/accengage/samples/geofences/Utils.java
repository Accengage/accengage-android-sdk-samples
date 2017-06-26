package com.accengage.samples.geofences;

import android.content.Context;
import android.content.SharedPreferences;

import com.accengage.samples.geofences.activities.DBPreferencesActivity;
import com.google.android.gms.maps.model.Circle;

public class Utils {

    public static int _ID = 0;
    public static int SERVER_ID = 1;
    public static int EXTERNAL_ID = 2;
    public static int NAME = 3;
    public static int LATITUDE = 4;
    public static int LONGITUDE = 5;
    public static int RADIUS = 6;
    public static int DETECTED_TIME = 7;
    public static int NOTIFIED_TIME = 8;
    public static int DETECTED_COUNT = 9;
    public static int DEVICE_LATITUDE = 10;
    public static int DEVICE_LONGITUDE = 11;
    public static int DISTANCE = 12;

    /**
     * Checks in the SharedPreferences how you chose the geofences to be displayed
     * @param context
     * @return selected option as a keyword
     */
    public static String getGeofenceSortingField(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                DBPreferencesActivity.DBVIEW_PREFERENCES_FILE_NAME,
                Context.MODE_PRIVATE);
        String keyword = preferences.getString(context.getResources().getString(R.string.geofence_sorting_keyword), "");
        boolean descOrder = preferences.getBoolean(context.getResources().getString(R.string.geofence_sorting_order), false);
        if (descOrder) keyword += " DESC";
        return keyword;
    }

    /**
     * Calculates the optimal zoom to apply to the maps' camera when drawing a geofence using circle
     * @param circle you want to zoom to
     * @return the best zoom level
     */
    public static int getZoomLevel(Circle circle) {
        int zoomLevel = 11;
        if (circle != null) {
            double radius = circle.getRadius() + circle.getRadius() / 2;
            double scale = radius / 500;
            zoomLevel = (int) (15 - Math.log(scale) / Math.log(2));
        }
        return zoomLevel;
    }
}
