package com.accengage.samples.beacons;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;


public class BeaconView extends LinearLayout {

    private TextView mUuidTextView;
    private TextView mMajorTextView;
    private TextView mMinorTextView;
    private TextView mTransitionTextView;
    private TextView mPowerTextView;
    private TextView mDistanceTextView;
    private TextView mAccuracyTextView;
    private TextView mRssiTextView;
    private TextView mDateTextView;

    public BeaconView(Context context) {
        super(context);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.cell_beacon, this);

        mUuidTextView = (TextView) findViewById(R.id.tvUuid);
        mMajorTextView = (TextView) findViewById(R.id.tvMajor);
        mMinorTextView = (TextView) findViewById(R.id.tvMinor);
        mTransitionTextView = (TextView) findViewById(R.id.tvTransition);
        mPowerTextView = (TextView) findViewById(R.id.tvPower);
        mDistanceTextView = (TextView) findViewById(R.id.tvDistance);
        mAccuracyTextView = (TextView) findViewById(R.id.tvAccuracy);
        mRssiTextView = (TextView) findViewById(R.id.tvRssi);
        mDateTextView = (TextView) findViewById(R.id.tvDate);
    }

    public void bind(Beacon beacon) {
        mUuidTextView.setText(beacon.getUuid());
        mMajorTextView.setText(String.valueOf(beacon.getMajor()));
        mMinorTextView.setText(String.valueOf(beacon.getMinor()));
        mTransitionTextView.setText(String.valueOf(beacon.getTransition()));
        mPowerTextView.setText(String.valueOf(beacon.getPower()));
        mDistanceTextView.setText(String.valueOf(beacon.getDistance()));
        mAccuracyTextView.setText(String.valueOf(beacon.getAccuracy()));
        mRssiTextView.setText(String.valueOf(beacon.getRssi()));
        mDateTextView.setText(beacon.getDate().toString());
    }

}
