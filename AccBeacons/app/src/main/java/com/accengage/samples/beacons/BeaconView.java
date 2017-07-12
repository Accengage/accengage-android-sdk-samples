package com.accengage.samples.beacons;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;


public class BeaconView extends LinearLayout {

    private TextView mMajorTextView;
    private TextView mMinorTextView;
    private TextView mTransitionTextView;
    private TextView mDistanceTextView;
    private TextView mAccuracyTextView;

    public BeaconView(Context context) {
        super(context);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.cell_beacon, this);

        mMajorTextView = (TextView) findViewById(R.id.tvMajorValue);
        mMinorTextView = (TextView) findViewById(R.id.tvMinorValue);
        mTransitionTextView = (TextView) findViewById(R.id.tvTransitionValue);
        mDistanceTextView = (TextView) findViewById(R.id.tvDistanceValue);
        mAccuracyTextView = (TextView) findViewById(R.id.tvAccuracyValue);
    }

    public void bind(Beacon beacon) {
        mMajorTextView.setText(String.valueOf(beacon.getMajor()));
        mMinorTextView.setText(String.valueOf(beacon.getMinor()));
        mTransitionTextView.setText(String.valueOf(beacon.getTransition()));
        mDistanceTextView.setText(String.valueOf(beacon.getDistance()));
        mAccuracyTextView.setText(String.valueOf(beacon.getAccuracy()));
    }

}
