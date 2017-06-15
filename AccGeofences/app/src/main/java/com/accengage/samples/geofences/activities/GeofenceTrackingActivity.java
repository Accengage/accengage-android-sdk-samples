package com.accengage.samples.geofences.activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.accengage.samples.geofences.GeofenceItem;
import com.accengage.samples.geofences.R;
import com.accengage.samples.geofences.Utils;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.contract.A4SContract;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class GeofenceTrackingActivity extends FragmentActivity implements OnMapReadyCallback, LoaderManager.LoaderCallbacks<Cursor> {

    LocationManager mLocationManager;
    Location mLocation;
    GoogleMap mGooglemap;
    String mSortingField = "";
    List<GeofenceItem> mGeofenceItemList;

    // Identifies a particular Loader being used in this component
    private static final int URL_LOADER = 0;

    private static final String[] PROJECTION = {
            A4SContract.Geofences._ID,
            A4SContract.Geofences.SERVER_ID,
            A4SContract.Geofences.EXTERNAL_ID,
            A4SContract.Geofences.NAME,
            A4SContract.Geofences.LATITUDE,
            A4SContract.Geofences.LONGITUDE,
            A4SContract.Geofences.RADIUS,
            A4SContract.Geofences.DETECTED_TIME,
            A4SContract.Geofences.NOTIFIED_TIME,
            A4SContract.Geofences.DETECTED_COUNT,
            A4SContract.Geofences.DEVICE_LATITUDE,
            A4SContract.Geofences.DEVICE_LONGITUDE,
            A4SContract.Geofences.DISTANCE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geofence_tracking);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Initialises the CursorLoader
        getSupportLoaderManager().initLoader(URL_LOADER, null, this);
    }

    @Override
    public void onResume() {
        super.onResume();
        String sortingField = Utils.getGeofenceSortingField(this);
        if (!mSortingField.equals(sortingField)) {
            getSupportLoaderManager().restartLoader(URL_LOADER, null, this);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle args) {
        mSortingField = Utils.getGeofenceSortingField(this);
        Log.debug("GeofencesFragment|onCreateLoader sorting by " + mSortingField);
        switch (loaderId) {
            case URL_LOADER:
                return new CursorLoader(this,
                        A4SContract.Geofences.getContentUri(this), PROJECTION,
                        null, null, mSortingField);
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (mGooglemap != null && data != null) {
            mGeofenceItemList = new ArrayList<>();
            data.moveToFirst();
            while (data.moveToNext()) {
                GeofenceItem geofenceItem = new GeofenceItem();

                geofenceItem.setId(data.getString(Utils._ID));
                geofenceItem.setServerId(data.getString(Utils.SERVER_ID));
                geofenceItem.setExternalId(data.getString(Utils.EXTERNAL_ID));
                geofenceItem.setName(data.getString(Utils.NAME));
                geofenceItem.setLatitude(data.getString(Utils.LATITUDE));
                geofenceItem.setLongitude(data.getString(Utils.LONGITUDE));
                geofenceItem.setRadius(data.getString(Utils.RADIUS));
                geofenceItem.setDetectedTime(data.getString(Utils.DETECTED_TIME));
                geofenceItem.setNotifiedTime(data.getString(Utils.NOTIFIED_TIME));
                geofenceItem.setDetectedCount(data.getString(Utils.DETECTED_COUNT));
                geofenceItem.setDeviceLatitude(data.getString(Utils.DEVICE_LATITUDE));
                geofenceItem.setDeviceLongitude(data.getString(Utils.DEVICE_LONGITUDE));
                geofenceItem.setDistance(data.getString(Utils.DISTANCE));

                drawDBGeofences(geofenceItem);
                mGeofenceItemList.add(geofenceItem);
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        for (GeofenceItem geofenceItem : mGeofenceItemList) {
            drawDBGeofences(geofenceItem);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGooglemap = googleMap;
        mLocation = getLastKnownLocation();
        if (mLocation != null) {
            LatLng myPosition = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
            mGooglemap.moveCamera(CameraUpdateFactory.newLatLng(myPosition));
            mGooglemap.animateCamera(CameraUpdateFactory.newLatLngZoom(myPosition, 13));
            checkLocationPermission();
            mGooglemap.setMyLocationEnabled(true);
        }
    }

    private boolean checkLocationPermission() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
                return checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_DENIED;
            }
        }
         return true;
    }

    private Location getLastKnownLocation() {
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        checkLocationPermission();
        for (String provider : providers) {
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    private void drawDBGeofences(GeofenceItem geofenceItem) {

         LatLng myPosition = new LatLng(
                 Double.valueOf(geofenceItem.getLatitude()),
                 Double.valueOf(geofenceItem.getLongitude()));
         MarkerOptions mo = new MarkerOptions()
                 .position(myPosition)
                 .visible(true)
                 .title(geofenceItem.getName());
         Marker marker = mGooglemap.addMarker(mo);
         marker.showInfoWindow();

         mGooglemap.addCircle(new CircleOptions()
                 .center(myPosition)
                 .radius(Double.valueOf(geofenceItem.getRadius()))
                 .strokeColor(Color.RED));
    }
}
