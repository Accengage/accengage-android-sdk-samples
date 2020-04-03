package com.accengage.samples.coffeesample.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.accengage.samples.coffeesample.R;
import com.ad4screen.sdk.A4S;
import com.ad4screen.sdk.Log;

public class SampleSplashScreen extends Activity {

	private static final int LOCATION_REQUEST = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sample_splashscreen);
		//Lock Push Notifications 
		//(Popup or notification will be displayed but rich push will not open until push notifications are unlocked)
		A4S.get(this).setPushNotificationLocked(true);
		//Lock In-App Display
		A4S.get(this).setInAppDisplayLocked(true);

		int result = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
		if (result != PackageManager.PERMISSION_GRANTED) {

			// Should we show an explanation?
			if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

				// Show an expanation to the user *asynchronously* -- don't block
				// this thread waiting for the user's response! After the user
				// sees the explanation, try again to request the permission.
				Log.verbose("SplashScreenActivity|ask about ACCESS_FINE_LOCATION permission");

			} else {
				// No explanation needed, we can request the permission.
				Log.verbose("SplashScreenActivity|request ACCESS_FINE_LOCATION permission");
				ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST);
			}
		} else {
            startSampleCoffeeMaker(3000);
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
		switch (requestCode) {
			case LOCATION_REQUEST: {
				// If request is cancelled, the result arrays are empty.
				if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					Log.verbose("SplashScreenActivity|ACCESS_FINE_LOCATION permission is granted");
                    startSampleCoffeeMaker(3000);
				} else {
					// permission denied, boo! Disable the
					// functionality that depends on this permission.
					Log.verbose("SplashScreenActivity|ACCESS_FINE_LOCATION permission is not granted");
				}
			}
		}
	}

    private void startSampleCoffeeMaker(int delay) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent coffeeMaker = new Intent(getApplicationContext(), SampleCoffeeMaker.class);
                startActivity(coffeeMaker);
                finish();
            }
        }, delay);
    }
}
