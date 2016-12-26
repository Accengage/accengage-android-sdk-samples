package com.accengage.test.inappcallbacks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ad4screen.sdk.A4S;
import com.ad4screen.sdk.InApp;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
        A4S.get(this).startActivity(this);

        // register in-app callbacks
        A4S.get(this).setInAppReadyCallback(false, new A4S.Callback<InApp>() {
            @Override
            public void onResult(InApp inApp) {
                Log.d(TAG, "InAppReady onResult");
            }

            @Override
            public void onError(int i, String s) {
                Log.d(TAG, "InAppReady onError");
            }
        });

        A4S.get(this).setInAppDisplayedCallback(new A4S.Callback<InApp>() {
            @Override
            public void onResult(InApp inApp) {
                Log.d(TAG, "InAppDisplayed onResult");
                HashMap<String, String> params = inApp.getCustomParameters();
                if (params != null) {
                    for (Map.Entry<String, String> entry : params.entrySet()) {
                        Log.d(TAG, "key(" + entry.getKey() + ") : value(" + entry.getValue() + ")");
                    }
                }
            }

            @Override
            public void onError(int i, String s) {
                Log.d(TAG, "InAppDisplayed onError");
            }
        });

        A4S.get(this).setInAppClickedCallback(new A4S.Callback<InApp>() {
            @Override
            public void onResult(InApp inApp) {
                Log.d(TAG, "InAppClicked onResult");
            }

            @Override
            public void onError(int i, String s) {
                Log.d(TAG, "InAppClicked onError");
            }
        });

        A4S.get(this).setInAppClosedCallback(new A4S.Callback<InApp>() {
            @Override
            public void onResult(InApp inApp) {
                Log.d(TAG, "InAppClosed onResult");
            }

            @Override
            public void onError(int i, String s) {
                Log.d(TAG, "InAppError onResult");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
        A4S.get(this).stopActivity(this); // in-app callbacks are unregistered here
    }

}
