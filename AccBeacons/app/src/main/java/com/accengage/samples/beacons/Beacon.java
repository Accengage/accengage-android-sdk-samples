package com.accengage.samples.beacons;


import android.os.Bundle;

import java.util.Date;

public class Beacon {

    private String mId;
    private String mUuid;
    private int mMajor;
    private int mMinor;
    private int mTransition;
    private int mPower;
    private double mDistance;
    private String mAccuracy;
    private int mRssi;
    private Date mDate;

    public String getInternalId() {
        return mUuid + String.valueOf(mMajor) + String.valueOf(mMinor);
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getUuid() {
        return mUuid;
    }

    public void setUuid(String uuid) {
        mUuid = uuid;
    }

    public int getMajor() {
        return mMajor;
    }

    public void setMajor(int major) {
        mMajor = major;
    }

    public int getMinor() {
        return mMinor;
    }

    public void setMinor(int minor) {
        mMinor = minor;
    }

    public int getTransition() {
        return mTransition;
    }

    public void setTransition(int transition) {
        mTransition = transition;
    }

    public int getPower() {
        return mPower;
    }

    public void setPower(int power) {
        mPower = power;
    }

    public double getDistance() {
        return mDistance;
    }

    public void setDistance(double distance) {
        mDistance = distance;
    }

    public String getAccuracy() {
        return mAccuracy;
    }

    public void setAccuracy(String accuracy) {
        mAccuracy = accuracy;
    }

    public int getRssi() {
        return mRssi;
    }

    public void setRssi(int rssi) {
        mRssi = rssi;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public static Beacon fromBundle(Bundle bundle) {
        Beacon beacon = new Beacon();
        beacon.setId(bundle.getString("id"));
        beacon.setUuid(bundle.getString("uuid"));
        beacon.setMajor(bundle.getInt("maj"));
        beacon.setMinor(bundle.getInt("min"));
        beacon.setTransition(bundle.getInt("transition"));
        beacon.setPower(bundle.getInt("power"));
        beacon.setDistance(bundle.getDouble("dist"));
        beacon.setAccuracy(bundle.getString("acc"));
        beacon.setRssi(bundle.getInt("rssi"));
        beacon.setDate(new Date(bundle.getLong("date")));
        return beacon;
    }
}
