package com.a4s.beacons.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.a4s.beacons.R;
import com.ad4screen.sdk.A4S;

public class MainActivity extends Activity {
	private static final int LOCATION_REQUEST = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		A4S.get(this).setPushNotificationLocked(false);
		setContentView(R.layout.activity_sample_coffee_maker);

		//Request location permission for geolocation/geofencing features
		if (ContextCompat.checkSelfPermission(this,
				Manifest.permission.ACCESS_FINE_LOCATION)
				!= PackageManager.PERMISSION_GRANTED) {

			ActivityCompat.requestPermissions(this,
					new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
					LOCATION_REQUEST);
		}

		findViewById(R.id.button_beacons).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {

						startActivity(new Intent(MainActivity.this,
								BeaconDetectorActivity.class));

					}
				});
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		A4S.get(this).setIntent(intent);
	}

	@Override
	protected void onResume() {
		super.onResume();
		A4S.get(this).startActivity(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		A4S.get(this).stopActivity(this);
	}

}
