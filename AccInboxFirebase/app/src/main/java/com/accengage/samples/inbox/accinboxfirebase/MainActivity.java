package com.accengage.samples.inbox.accinboxfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.accengage.samples.inbox.InboxNavActivity;
import com.ad4screen.sdk.A4S;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startActivity(new Intent(this, InboxNavActivity.class));
        finish();
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