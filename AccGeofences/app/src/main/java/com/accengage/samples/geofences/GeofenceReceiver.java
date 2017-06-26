package com.accengage.samples.geofences;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.accengage.samples.geofences.activities.MainActivity;
import com.ad4screen.sdk.Constants;

import java.util.Arrays;


public class GeofenceReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Bundle geofenceData = bundle.getBundle(Constants.EXTRA_GEOFENCE_PAYLOAD);

        if (geofenceData != null) {
            //Retrieve ids of geofences triggered :
            String[] geofencesIds = geofenceData.getStringArray("ids");
            //Retrieve transition code (1 == enter, 0 == exit)
            int transitionCode = geofenceData.getInt("transition");

            //Retrieve the geolocation that has triggered these geofences
            //(Note : you will have to parse it as a JSONObject)
            //Keys are : provider, latitude, longitude, altitude, accuracy, bearing, speed, time)
            String location = geofenceData.getString("triggeringLocation");
            if (location != null) {
                Log.d("GeofenceReceiver", "location: " + location);
            }

            //Do anything you want, here we will show a notification
            Intent notifIntent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 1000, notifIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
            notificationBuilder.setAutoCancel(true)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Geofence(s) triggered!")
                    .setContentText((transitionCode == 1 ? "Enter" : "Exit")
                            + " - Id(s) : " + Arrays.toString(geofencesIds))
                    .setContentIntent(pendingIntent);

            int id = (int) (Math.random() * 10000);
            NotificationManagerCompat.from(context).notify(id, notificationBuilder.build());
        }
    }
}