package com.accengage.samples.integration;

import android.os.Bundle;

import com.ad4screen.sdk.activities.A4SActivity;

public class MainActivity extends A4SActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.accengage.samples.integration.R.layout.activity_main);
    }
}