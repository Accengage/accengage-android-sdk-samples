package com.accengage.samples.beacons.activities;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.accengage.samples.beacons.Beacon;
import com.accengage.samples.beacons.BeaconAdapter;
import com.accengage.samples.beacons.R;
import com.ad4screen.sdk.A4S;
import com.ad4screen.sdk.Constants;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

public class MainActivity extends Activity {

    private static final int LOCATION_REQUEST = 1;

    private RecyclerView mBeaconList;
    private BeaconAdapter mBeaconAdapter;
    private LinkedHashMap<String, Beacon> mBeacons = new LinkedHashMap<>();

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle payload = intent.getExtras();
            if (payload == null) {
                return;
            }

            Bundle beacons = payload.getBundle(Constants.EXTRA_BEACON_PAYLOAD);
            if (beacons == null) {
                return;
            }

            List<Beacon> beaconsList = fromBundle(beacons);
            for (Beacon beacon : beaconsList) {
                mBeacons.put(beacon.getInternalId(), beacon);
            }
            mBeaconAdapter.notifyDataSetChanged();
        }

        private List<Beacon> fromBundle(Bundle beaconsBundle) {
            List<Beacon> beacons = new ArrayList<>();
            Set<String> triggeredBeacons = beaconsBundle.keySet();
            for (String beaconId : triggeredBeacons) {
                Bundle beaconDetails = beaconsBundle.getBundle(beaconId);
                if (beaconDetails != null) {
                    beacons.add(Beacon.fromBundle(beaconDetails));
                }
            }
            return beacons;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        A4S.get(this).setPushNotificationLocked(false);
        setContentView(R.layout.activity_beacon);

        // Request location permission for beacons features
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST);
        }

        mBeaconList = (RecyclerView) findViewById(R.id.listBeacons);
        mBeaconList.setHasFixedSize(true);
        mBeaconList.setLayoutManager(new LinearLayoutManager(this));
        mBeaconList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mBeaconAdapter = new BeaconAdapter(mBeacons);
        mBeaconList.setAdapter(mBeaconAdapter);

        findViewById(R.id.buttonClearBeacons).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mBeacons.clear();
                mBeaconAdapter.notifyDataSetChanged();
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
        listenBeacons();
    }

    @Override
    protected void onPause() {
        super.onPause();
        A4S.get(this).stopActivity(this);
        unlistenBeacons();
    }

    private void listenBeacons() {
        IntentFilter filter = new IntentFilter(Constants.ACTION_TRIGGER);
        filter.addCategory(Constants.CATEGORY_BEACON_NOTIFICATIONS);
        registerReceiver(mReceiver, filter);
    }

    private void unlistenBeacons() {
        unregisterReceiver(mReceiver);
    }

}
