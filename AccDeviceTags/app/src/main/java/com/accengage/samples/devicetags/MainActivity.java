package com.accengage.samples.devicetags;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.ad4screen.sdk.A4S;
import com.ad4screen.sdk.DeviceTag;

public class MainActivity extends AppCompatActivity {

    private TextView mEasterEggMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mEasterEggMessage = findViewById(R.id.easter_egg_message);
    }

    /**
     * Send Device Tag to Accengage. This method is executed on the click of each button
     * @param view
     */
    public void sendDeviceTag(View view) {
        ToggleButton button = (ToggleButton)view;
        // This will create a Device Tag with category "Ligne" and with id the train identifier
        DeviceTag deviceTag = new DeviceTag("Ligne", button.getText().toString());
        if(button.isChecked()) {
            // Add a custom parameter to the Device Tag with key "Type" and value "Metro" or "RER" in function of the train identifier
            deviceTag.addCustomParameter("Type", button.getText().toString().startsWith("M") ? "Métro" : "RER");
            // Send the Device Tag
            A4S.get(MainActivity.this).setDeviceTag(deviceTag);
            Snackbar.make(findViewById(R.id.main_view), "Sending Device Tag to Accengage", Snackbar.LENGTH_LONG).show();

            easterEgg(button.getText().toString());
        } else {
            // Remove the Device Tag
            A4S.get(MainActivity.this).deleteDeviceTag(deviceTag);
            Snackbar.make(findViewById(R.id.main_view), "Removing Device Tag from Accengage", Snackbar.LENGTH_LONG).show();

            hideEasterEgg();
        }
    }

    public void easterEgg(String line) {
        switch (line) {
            case "M1" :
                mEasterEggMessage.setText(R.string.m1_easter_egg);
                break;
            case "M2" :
                mEasterEggMessage.setText(R.string.m2_easter_egg);
                break;
            case "M3" :
                mEasterEggMessage.setText(R.string.m3_easter_egg);
                break;
            case "M6" :
                mEasterEggMessage.setText(R.string.m6_easter_egg);
                break;
            case "M8" :
                mEasterEggMessage.setText(R.string.m8_easter_egg);
                break;
            case "M11" :
                mEasterEggMessage.setText(R.string.m11_easter_egg);
                break;
            case "M13" :
                mEasterEggMessage.setText(R.string.m13_easter_egg);
                break;
            case "M14" :
                mEasterEggMessage.setText(R.string.m14_easter_egg);
                break;
            case "A" :
                mEasterEggMessage.setText(R.string.rer_a_easter_egg);
                break;
            case "B" :
                mEasterEggMessage.setText(R.string.rer_b_easter_egg);
                break;
            case "C" :
                mEasterEggMessage.setText(R.string.rer_c_easter_egg);
                break;
            case "D" :
                mEasterEggMessage.setText(R.string.rer_d_easter_egg);
                break;
            case "E" :
                mEasterEggMessage.setText(R.string.rer_e_easter_egg);
                break;
        }
        mEasterEggMessage.setVisibility(View.VISIBLE);
    }

    public void hideEasterEgg() {
        mEasterEggMessage.setText("");
        mEasterEggMessage.setVisibility(View.GONE);
    }
}
