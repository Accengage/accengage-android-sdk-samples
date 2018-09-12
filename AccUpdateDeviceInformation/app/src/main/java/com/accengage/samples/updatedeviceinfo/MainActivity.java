package com.accengage.samples.updatedeviceinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.ad4screen.sdk.A4S;
import com.ad4screen.sdk.service.modules.profile.DeviceInformation;

public class MainActivity extends AppCompatActivity {

    private EditText mUsername;
    private EditText mEmail;
    private TextView mEasterEggMessage;
    private NumberPicker mAgeNumberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initView();

        // The first button will send all your critical information in our database mouahahaha
        // Also it will increase the number of user profile update
        findViewById(R.id.put).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeviceInformation deviceInformation = new DeviceInformation();
                deviceInformation.set("username", mUsername.getText().toString());
                deviceInformation.set("email", mEmail.getText().toString());
                deviceInformation.set("age", mAgeNumberPicker.getValue());
                deviceInformation.increment("nb_profile_adding", 1);
                A4S.get(getApplicationContext()).updateDeviceInformation(deviceInformation);
                Snackbar.make(findViewById(R.id.main_view), "Sending the data to accengage database", Snackbar.LENGTH_LONG).show();

                easterEgg(mAgeNumberPicker.getValue());
            }
        });

        // The second button will clear your information from the base
        // Also it will decrease the number of user profile update
        findViewById(R.id.remove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeviceInformation deviceInformation = new DeviceInformation();
                deviceInformation.delete("username");
                deviceInformation.delete("email");
                deviceInformation.delete("age");
                deviceInformation.decrement("nb_profile_adding", 1);
                A4S.get(getApplicationContext()).updateDeviceInformation(deviceInformation);
                Snackbar.make(findViewById(R.id.main_view), "Deleting the data from accengage database", Snackbar.LENGTH_LONG).show();

                hideEasterEgg();
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

    private void initView() {
        mUsername = findViewById(R.id.username);
        mEmail = findViewById(R.id.email);
        mEasterEggMessage = findViewById(R.id.easter_egg_message);
        mAgeNumberPicker = findViewById(R.id.age);

        mAgeNumberPicker.setMinValue(0);
        mAgeNumberPicker.setMaxValue(80);
        mAgeNumberPicker.setValue(15);
        mAgeNumberPicker.setWrapSelectorWheel(true);
    }

    private void easterEgg(int age) {
        if(age < 20)
            mEasterEggMessage.setText("Wait, what? No way! You are definitely not " + age + "...");
        else if(age > 20 && age < 30)
            mEasterEggMessage.setText("Oh, you are " + age + "! Me too, we are twins!");
        else if(age > 40)
            mEasterEggMessage.setText("Really, " + age + "? You look way younger. I was barely giving you " + (age - 6));
        mEasterEggMessage.setVisibility(View.VISIBLE);
    }

    private void hideEasterEgg() {
        mEasterEggMessage.setVisibility(View.GONE);
    }
}
