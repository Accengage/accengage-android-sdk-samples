package com.accengage.samples.beacons.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.accengage.samples.beacons.Beacon;
import com.accengage.samples.beacons.R;
import com.ad4screen.sdk.A4S;
import com.ad4screen.sdk.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DetailActivity extends AppCompatActivity {

    private TextView mUuidTextView;
    private TextView mMajorTextView;
    private TextView mMinorTextView;
    private TextView mTransitionTextView;
    private TextView mPowerTextView;
    private TextView mDistanceTextView;
    private TextView mAccuracyTextView;
    private TextView mRssiTextView;
    private TextView mDateTextView;

    static Beacon mTargetBeacon = new Beacon();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTargetBeacon = getIntent().getExtras().getParcelable("beacon");

        mUuidTextView = (TextView) findViewById(R.id.tvUuidValue);
        mMajorTextView = (TextView) findViewById(R.id.tvMajorValue);
        mMinorTextView = (TextView) findViewById(R.id.tvMinorValue);
        mTransitionTextView = (TextView) findViewById(R.id.tvTransitionValue);
        mPowerTextView = (TextView) findViewById(R.id.tvPowerValue);
        mDistanceTextView = (TextView) findViewById(R.id.tvDistanceValue);
        mAccuracyTextView = (TextView) findViewById(R.id.tvAccuracyValue);
        mRssiTextView = (TextView) findViewById(R.id.tvRssiValue);
        mDateTextView = (TextView) findViewById(R.id.tvDateValue);
    }

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
                if (mTargetBeacon.getMajor() == beacon.getMajor()
                        && mTargetBeacon.getMinor() == beacon.getMinor()) {
                    mTargetBeacon = beacon;
                    refreshData();
                }
            }
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
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        A4S.get(this).setIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        A4S.get(this).startActivity(this);
        refreshData();
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

    private void refreshData() {
        mUuidTextView.setText(mTargetBeacon.getUuid());
        mMajorTextView.setText(String.valueOf(mTargetBeacon.getMajor()));
        mMinorTextView.setText(String.valueOf(mTargetBeacon.getMinor()));
        mTransitionTextView.setText(String.valueOf(mTargetBeacon.getTransition()));
        mPowerTextView.setText(String.valueOf(mTargetBeacon.getPower()));
        mDistanceTextView.setText(String.valueOf(mTargetBeacon.getDistance()));
        mAccuracyTextView.setText(mTargetBeacon.getAccuracy());
        mRssiTextView.setText(String.valueOf(mTargetBeacon.getRssi()));
        mDateTextView.setText(mTargetBeacon.getDate().toString());
    }
}
