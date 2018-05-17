package com.accengage.accgdpr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;

import com.ad4screen.sdk.A4S;
import com.ad4screen.sdk.OptinType;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.switch_optin_data) SwitchCompat switchOptinData;
    @BindView(R.id.switch_optin_geoloc) SwitchCompat switchOptinGeoloc;

    @OnCheckedChanged(R.id.switch_optin_data)
    void onOptinDataCheckedChanged() {
        A4S.get(this).setOptinData(this, switchOptinData.isChecked() ? OptinType.YES : OptinType.NO);

        if (switchOptinData.isChecked()) {
            A4S.get(this).startActivity(this);
        }
    }

    @OnCheckedChanged(R.id.switch_optin_geoloc)
    void onOptinGeolocCheckedChanged() {
        A4S.get(this).setOptinGeoloc(this, switchOptinGeoloc.isChecked() ? OptinType.YES : OptinType.NO);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        A4S.get(this).setIntent(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
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
        OptinType isOptinData = A4S.get(this).getOptinDataStatus(this);
        OptinType isOptinGeoloc = A4S.get(this).getOptinGeolocStatus(this);

        switchOptinData.setChecked(OptinType.YES.equals(isOptinData));
        switchOptinGeoloc.setChecked(OptinType.YES.equals(isOptinGeoloc));
    }
}
