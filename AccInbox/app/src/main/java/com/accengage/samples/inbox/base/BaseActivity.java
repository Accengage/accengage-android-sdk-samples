package com.accengage.samples.inbox.base;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import com.ad4screen.sdk.A4S;

public abstract class BaseActivity extends AppCompatActivity {

    protected A4S getA4S() {
        return A4S.get(this);
    }

}
