package com.a4s.coffeesample.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.a4s.coffeesample.R;
import com.a4s.sdk.plugins.annotations.UseA4S;
import com.ad4screen.sdk.A4S;

/**
 * Example of use of a SplashScreen
 * @author Jonathan Salamon
 *
 */
@UseA4S
public class SampleSplashScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sample_splashscreen);
		//Lock Push Notifications 
		//(Popup or notification will be displayed but rich push will not open until push notifications are unlocked)
		A4S.get(this).setPushNotificationLocked(true);
		//Lock In-App Display
		A4S.get(this).setInAppDisplayLocked(true);

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				//Activity opened by this SplashScreen is SampleCoffeeMaker.
				//We have to unlock push and in app on this next activity
				Intent coffeeMaker = new Intent(getApplicationContext(), SampleCoffeeMaker.class);
				startActivity(coffeeMaker);
				finish();
			}
		}, 3000);
	}

}
