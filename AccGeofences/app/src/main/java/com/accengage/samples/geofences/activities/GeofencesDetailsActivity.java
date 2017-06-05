package com.accengage.samples.geofences.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.accengage.samples.geofences.Constants;
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

    ArrayList<String> mInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geofences_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i = getIntent();
        mInfoList = i.getStringArrayListExtra("cursor");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        initTV();
    }

    private void initTV(){
        TextView tv = (TextView) findViewById(R.id.geofence_name_value);
        tv.setText(mInfoList.get(Constants.NAME));
        TextView tv1 = (TextView) findViewById(R.id.tv_geofence_radius_value);
        tv1.setText(mInfoList.get(Constants.RADIUS));
        TextView tv2 = (TextView) findViewById(R.id.geofence_id_number);
        tv2.setText(mInfoList.get(Constants._ID));
        TextView tv3 = (TextView) findViewById(R.id.tv_geofence_server_id_value);
        tv3.setText(mInfoList.get(Constants.SERVER_ID));
        TextView tv4 = (TextView) findViewById(R.id.tv_geofence_external_id_value);
        tv4.setText(mInfoList.get(Constants.EXTERNAL_ID));
        TextView tv5 = (TextView) findViewById(R.id.tv_geofence_latitude_value);
        tv5.setText(mInfoList.get(Constants.LATITUDE));
        TextView tv6 = (TextView) findViewById(R.id.tv_geofence_longitude_value);
        tv6.setText(mInfoList.get(Constants.LONGITUDE));
        TextView tv7 = (TextView) findViewById(R.id.tv_geofence_detected_time_value);
        tv7.setText(mInfoList.get(Constants.DETECTED_TIME));
        TextView tv8 = (TextView) findViewById(R.id.tv_geofence_notified_time_value);
        tv8.setText(mInfoList.get(Constants.NOTIFIED_TIME));
        TextView tv9 = (TextView) findViewById(R.id.tv_geofence_detected_count_value);
        tv9.setText(mInfoList.get(Constants.DETECTED_COUNT));
        TextView tv10 = (TextView) findViewById(R.id.tv_geofence_device_latitude_value);
        tv10.setText(mInfoList.get(Constants.DEVICE_LATITUDE));
        TextView tv11 = (TextView) findViewById(R.id.tv_geofence_device_longitude_value);
        tv11.setText(mInfoList.get(Constants.DEVICE_LONGITUDE));
        TextView tv12 = (TextView) findViewById(R.id.tv_geofence_distance_value);
        tv12.setText(mInfoList.get(Constants.DISTANCE));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker in Sydney and move the camera
        LatLng myPosition = new LatLng(
                Double.valueOf(mInfoList.get(Constants.LATITUDE)),
                Double.valueOf(mInfoList.get(Constants.LONGITUDE)));
        MarkerOptions mo = new MarkerOptions()
                .position(myPosition)
                .visible(true)
                .title(mInfoList.get(Constants.NAME));
        Marker marker = googleMap.addMarker(mo);
        marker.showInfoWindow();

        Circle circle = googleMap.addCircle(new CircleOptions()
                .center(myPosition)
                .radius(Double.valueOf(mInfoList.get(Constants.RADIUS)))
                .strokeColor(Color.RED));

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(myPosition));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myPosition, getZoomLevel(circle)));
    }

    private int getZoomLevel(Circle circle) {
        int zoomLevel = 11;
        if (circle != null) {
            double radius = circle.getRadius() + circle.getRadius() / 2;
            double scale = radius / 500;
            zoomLevel = (int) (15 - Math.log(scale) / Math.log(2));
        }
        return zoomLevel;
    }
}
