package com.a4s.geofences;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.ad4screen.sdk.Constants;


public class GeofenceReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Bundle geofenceData = bundle.getBundle(Constants.EXTRA_GEOFENCE_PAYLOAD);

        //Retrieve ids of geofences triggered :
        String[] geofencesIds = geofenceData.getStringArray("ids");
        //Retrieve transition code (1 == enter, 0 == exit)
        int transitionCode = geofenceData.getInt("transition");

        //Retrieve the geolocation that has triggered these geofences
        //(Note : you will have to parse it as a JSONObject)
        //Keys are : provider, latitude, longitude, altitude, accuracy, bearing, speed, time)
        String location = geofenceData.getString("triggeringLocation");

        //Do anything you want, here we will show a notification
        Intent notifIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1000, notifIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
        notificationBuilder.setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Geofence(s) Triggered !")
                .setContentText((transitionCode == 1 ? "Enter" : "Exit")
                        + " - Id(s) : " + geofencesIds)
                .setContentIntent(pendingIntent);

        int id = (int) (Math.random() * 10000);
        NotificationManagerCompat.from(context).notify(id, notificationBuilder.build());
    }
}