package com.accengage.samples.geofences;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceFragment;

public class DBPreferencesActivity extends Activity {

    public static final String KEY_PREFERENCE_ID = "key_preference_id";
    public static final String DBVIEW_PREFERENCES_FILE_NAME = BuildConfig.APPLICATION_ID + ".dbview_preferences";

    public static final int GEOFENCE_PREFERENCES = 0;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int preferenceId = getIntent().getIntExtra(KEY_PREFERENCE_ID, GEOFENCE_PREFERENCES);
        if (preferenceId == GEOFENCE_PREFERENCES) {
            // Display geofence preferences fragment
            getFragmentManager().beginTransaction()
                    .replace(android.R.id.content, new GeofencePreferencesFragment())
                    .commit();
        }
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class GeofencePreferencesFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            //PreferenceManager.setDefaultValues(getActivity(), R.xml.geofence_dbview_settings, false);
            getPreferenceManager().setSharedPreferencesName(DBVIEW_PREFERENCES_FILE_NAME);

            addPreferencesFromResource(R.xml.geofence_dbview_settings);
        }
    }
}
