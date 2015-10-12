package com.a4s.coffeesample.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;

import com.a4s.coffeesample.R;
import com.a4s.sdk.plugins.annotations.UseA4S;
import com.ad4screen.sdk.A4S;
import com.ad4screen.sdk.Tag;

@UseA4S
@Tag(name = "CoffeeSettings")
public class SampleSettings extends PreferenceActivity {

	CheckBoxPreference inappDisplayLock;
	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		addPreferencesFromResource(R.xml.samplesettings);
		setTitle("View : CoffeeSettings");
		inappDisplayLock = (CheckBoxPreference) findPreference("displayLocked");

		inappDisplayLock.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {

			@Override
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				if(newValue.toString().equals("true")) {
					inappDisplayLock.setChecked(true);
					A4S.get(SampleSettings.this).setInAppDisplayLocked(true);
				}
				else {
					inappDisplayLock.setChecked(false);
					A4S.get(SampleSettings.this).setInAppDisplayLocked(false);
				}
				return false;
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();	
		// This activity can be opened through url schema 
		// Here we are parsing uri for parameters
		Intent intent = getIntent();
		if (intent != null && intent.getData() != null) {
			Uri uri = intent.getData();
			//Use datas here and display anything you want relative to these parameters.
		}
	}
}
