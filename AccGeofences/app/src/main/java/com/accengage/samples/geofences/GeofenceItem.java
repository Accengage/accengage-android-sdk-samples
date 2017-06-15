package com.accengage.samples.geofences;

public class GeofenceItem {

    private static String mID;
    private static String mServerId;
    private static String mExternalId;
    private static String mName;
    private static String mLatitude;
    private static String mLongitude;
    private static String mRadius;
    private static String mDetectedTime;
    private static String mNotifiedTime;
    private static String mDetectedCount;
    private static String mDeviceLatitude;
    private static String mDeviceLongitude;
    private static String mDistance;

    public GeofenceItem () {

    }

    public String getId() {
        return mID;
    }

    public void setId(String Id) {
        mID = Id;
    }

    public String getServerId() {
        return mServerId;
    }

    public void setServerId(String serverId) {
        mServerId = serverId;
    }

    public String getExternalId() {
        return mExternalId;
    }

    public void setExternalId(String externalId) {
        mExternalId = externalId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        GeofenceItem.mName = name;
    }

    public String getLatitude() {
        return mLatitude;
    }

    public void setLatitude(String latitude) {
        GeofenceItem.mLatitude = latitude;
    }

    public String getLongitude() {
        return mLongitude;
    }

    public void setLongitude(String longitude) {
        GeofenceItem.mLongitude = longitude;
    }

    public String getRadius() {
        return mRadius;
    }

    public void setRadius(String radius) {
        GeofenceItem.mRadius = radius;
    }

    public String getDetectedTime() {
        return mDetectedTime;
    }

    public void setDetectedTime(String detectedTime) {
        mDetectedTime = detectedTime;
    }

    public String getNotifiedTime() {
        return mNotifiedTime;
    }

    public void setNotifiedTime(String notifiedTime) {
        mNotifiedTime = notifiedTime;
    }

    public String getDetectedCount() {
        return mDetectedCount;
    }

    public void setDetectedCount(String detectedCount) {
        mDetectedCount = detectedCount;
    }

    public String getDeviceLatitude() {
        return mDeviceLatitude;
    }

    public void setDeviceLatitude(String deviceLatitude) {
        mDeviceLatitude = deviceLatitude;
    }

    public String getDeviceLongitude() {
        return mDeviceLongitude;
    }

    public void setDeviceLongitude(String deviceLongitude) {
        mDeviceLongitude = deviceLongitude;
    }

    public String getDistance() {
        return mDistance;
    }

    public void setDistance(String distance) {
        GeofenceItem.mDistance = distance;
    }
}
