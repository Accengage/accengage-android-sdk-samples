package com.a4s.beacons;

import java.util.Set;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.a4s.beacons.activities.MainActivity;
import com.ad4screen.sdk.Constants;

public class BeaconReceiver extends BroadcastReceiver {


	@Override
	public void onReceive(Context context, Intent intent) {
		Intent notifIntent = new Intent(context, MainActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 1000, notifIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		Bundle bundle = intent.getExtras();
		Bundle beacons = bundle.getBundle(Constants.EXTRA_BEACON_PAYLOAD);


		Set<String> triggeredBeacons = beacons.keySet();
		for (String beaconId : triggeredBeacons) {
			Bundle beaconDetails = beacons.getBundle(beaconId);
			NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
			notificationBuilder.setAutoCancel(true)
			.setSmallIcon(R.mipmap.ic_launcher)
			.setContentTitle("Beacon Triggered !")
			.setContentText((beaconDetails.getInt("transition") == 1 ? "Enter":"Exit") 
					+ " - Id : " + beaconDetails.getString("id") + " - Distance : " 
					+ beaconDetails.getDouble("dist")
					+ " ("+beaconDetails.getString("acc")+")")
					.setContentIntent(pendingIntent);

			int id = (int) (Math.random()*10000);
			if(beaconDetails.getString("id") != null) {
				id = Integer.valueOf(beaconDetails.getString("id"));
			}

			NotificationManagerCompat.from(context).notify(id, notificationBuilder.build());
		}

	}

}
