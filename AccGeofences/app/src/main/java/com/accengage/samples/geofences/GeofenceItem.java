package com.accengage.samples.geofences;

public class GeofenceItem {

    private String mID;
    private String mServerId;
    private String mExternalId;
    private String mName;
    private String mLatitude;
    private String mLongitude;
    private String mRadius;
    private String mDetectedTime;
    private String mNotifiedTime;
    private String mDetectedCount;
    private String mDeviceLatitude;
    private String mDeviceLongitude;
    private String mDistance;

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
        mName = name;
    }

    public String getLatitude() {
        return mLatitude;
    }

    public void setLatitude(String latitude) {
        mLatitude = latitude;
    }

    public String getLongitude() {
        return mLongitude;
    }

    public void setLongitude(String longitude) {
        mLongitude = longitude;
    }

    public String getRadius() {
        return mRadius;
    }

    public void setRadius(String radius) {
        mRadius = radius;
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
        mDistance = distance;
    }
}
