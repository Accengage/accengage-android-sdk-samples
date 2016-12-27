package com.a4s.coffeesample.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import com.a4s.coffeesample.R;
import com.ad4screen.sdk.A4S;
import com.ad4screen.sdk.Tag;

@Tag(name = "CoffeeMaker")
public class SampleCoffeeMaker extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_sample_coffee_maker);
		setTitle("View : CoffeeMaker");

		//Unlock in-app and push notifications
		//Any waiting in app and rich push will be displayed
		A4S.get(this).setPushNotificationLocked(false);
		A4S.get(this).setInAppDisplayLocked(false);

		findViewById(R.id.btnStartBrew).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				A4S.get(SampleCoffeeMaker.this).trackEvent(Long.parseLong("1000"), "Start");
				startActivity(new Intent(SampleCoffeeMaker.this, SampleMakeCoffee.class));
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_coffee_maker, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.menu_coffee_settings) {
			startActivity(new Intent(this, SampleSettings.class));
			return true;
		}

		return false;
	}
}
