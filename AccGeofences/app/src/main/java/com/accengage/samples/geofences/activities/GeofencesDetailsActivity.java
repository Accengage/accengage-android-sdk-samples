package com.accengage.samples.geofences.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.accengage.samples.geofences.Utils;
import com.accengage.samples.geofences.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class GeofencesDetailsActivity extends AppCompatActivity implements OnMapReadyCallback{

    ArrayList<String> mGeofencesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geofences_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i = getIntent();
        mGeofencesList = i.getStringArrayListExtra("cursor");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        initTV();
    }

    private void initTV(){
        TextView tv = (TextView) findViewById(R.id.geofence_name_value);
        tv.setText(mGeofencesList.get(Utils.NAME));
        TextView tv1 = (TextView) findViewById(R.id.tv_geofence_radius_value);
        tv1.setText(mGeofencesList.get(Utils.RADIUS));
        TextView tv2 = (TextView) findViewById(R.id.geofence_id_number);
        tv2.setText(mGeofencesList.get(Utils._ID));
        TextView tv3 = (TextView) findViewById(R.id.tv_geofence_server_id_value);
        tv3.setText(mGeofencesList.get(Utils.SERVER_ID));
        TextView tv4 = (TextView) findViewById(R.id.tv_geofence_external_id_value);
        tv4.setText(mGeofencesList.get(Utils.EXTERNAL_ID));
        TextView tv5 = (TextView) findViewById(R.id.tv_geofence_latitude_value);
        tv5.setText(mGeofencesList.get(Utils.LATITUDE));
        TextView tv6 = (TextView) findViewById(R.id.tv_geofence_longitude_value);
        tv6.setText(mGeofencesList.get(Utils.LONGITUDE));
        TextView tv7 = (TextView) findViewById(R.id.tv_geofence_detected_time_value);
        tv7.setText(mGeofencesList.get(Utils.DETECTED_TIME));
        TextView tv8 = (TextView) findViewById(R.id.tv_geofence_notified_time_value);
        tv8.setText(mGeofencesList.get(Utils.NOTIFIED_TIME));
        TextView tv9 = (TextView) findViewById(R.id.tv_geofence_detected_count_value);
        tv9.setText(mGeofencesList.get(Utils.DETECTED_COUNT));
        TextView tv10 = (TextView) findViewById(R.id.tv_geofence_device_latitude_value);
        tv10.setText(mGeofencesList.get(Utils.DEVICE_LATITUDE));
        TextView tv11 = (TextView) findViewById(R.id.tv_geofence_device_longitude_value);
        tv11.setText(mGeofencesList.get(Utils.DEVICE_LONGITUDE));
        TextView tv12 = (TextView) findViewById(R.id.tv_geofence_distance_value);
        tv12.setText(mGeofencesList.get(Utils.DISTANCE));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng myPosition = new LatLng(
                Double.valueOf(mGeofencesList.get(Utils.LATITUDE)),
                Double.valueOf(mGeofencesList.get(Utils.LONGITUDE)));
        MarkerOptions mo = new MarkerOptions()
                .position(myPosition)
                .visible(true)
                .title(mGeofencesList.get(Utils.NAME));
        Marker marker = googleMap.addMarker(mo);
        marker.showInfoWindow();

        Circle circle = googleMap.addCircle(new CircleOptions()
                .center(myPosition)
                .radius(Double.valueOf(mGeofencesList.get(Utils.RADIUS)))
                .strokeColor(Color.RED));

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(myPosition));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myPosition, Utils.getZoomLevel(circle)));
    }
}
