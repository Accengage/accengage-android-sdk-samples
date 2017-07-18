package com.accengage.samples.beacons;


import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.ad4screen.sdk.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Beacon implements Parcelable {

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
    private DateFormat mSimpleDateFormat = SimpleDateFormat.getDateInstance();

    private Beacon(Parcel in) {
        mId = in.readString();
        mUuid = in.readString();
        mMajor = in.readInt();
        mMinor = in.readInt();
        mTransition = in.readInt();
        mPower = in.readInt();
        mDistance = in.readDouble();
        mAccuracy = in.readString();
        mRssi = in.readInt();
        try {
            mDate = mSimpleDateFormat.parse(in.readString());
        } catch (ParseException e) {
            Log.error("Date format not compatible : " + e);
        }
    }
    public Beacon(){

    }

    public static final Creator<Beacon> CREATOR = new Creator<Beacon>() {
        @Override
        public Beacon createFromParcel(Parcel in) {
            return new Beacon(in);
        }

        @Override
        public Beacon[] newArray(int size) {
            return new Beacon[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mUuid);
        dest.writeInt(mMajor);
        dest.writeInt(mMinor);
        dest.writeInt(mTransition);
        dest.writeInt(mPower);
        dest.writeDouble(mDistance);
        dest.writeString(mAccuracy);
        dest.writeInt(mRssi);
        dest.writeString(mSimpleDateFormat.format(mDate));
    }
}
