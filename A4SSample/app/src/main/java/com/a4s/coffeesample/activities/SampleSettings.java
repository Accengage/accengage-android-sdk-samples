package com.a4s.coffeesample.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;

import com.a4s.coffeesample.R;
import com.ad4screen.sdk.A4S;
import com.ad4screen.sdk.Tag;

@Tag(name = "CoffeeSettings")
public class SampleSettings extends PreferenceActivity {

	private CheckBoxPreference mInappDisplayLock;

	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		addPreferencesFromResource(R.xml.samplesettings);
		setTitle("View : CoffeeSettings");
		mInappDisplayLock = (CheckBoxPreference) findPreference("displayLocked");

		mInappDisplayLock.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {

			@Override
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				if(newValue.toString().equals("true")) {
					mInappDisplayLock.setChecked(true);
					A4S.get(SampleSettings.this).setInAppDisplayLocked(true);
				}
				else {
					mInappDisplayLock.setChecked(false);
					A4S.get(SampleSettings.this).setInAppDisplayLocked(false);
				}
				return false;
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		A4S.get(this).startActivity(this);
		// This activity can be opened through url schema 
		// Here we are parsing uri for parameters
		Intent intent = getIntent();
		if (intent != null && intent.getData() != null) {
			Uri uri = intent.getData();
			//Use datas here and display anything you want relative to these parameters.
		}
	}


	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		A4S.get(this).setIntent(intent);
	}

	@Override
	protected void onPause() {
		super.onPause();
		A4S.get(this).stopActivity(this);
	}
}
