package com.accengage.samples.inbox.base;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.ad4screen.sdk.A4S;

public abstract class BaseActivity extends AppCompatActivity {

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

    protected A4S getA4S() {
        return A4S.get(this);
    }

}
