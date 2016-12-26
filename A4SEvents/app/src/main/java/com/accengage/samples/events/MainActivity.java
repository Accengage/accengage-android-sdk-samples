package com.accengage.samples.events;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ad4screen.sdk.A4S;
import com.ad4screen.sdk.analytics.Cart;
import com.ad4screen.sdk.analytics.Item;
import com.ad4screen.sdk.analytics.Lead;
import com.ad4screen.sdk.analytics.Purchase;

public class MainActivity extends AppCompatActivity {

    private static final int LOCATION_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Your first button will send a trackLead event
        findViewById(R.id.event_lead).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Lead lead = new Lead("Lead Label", "Lead Value");
                A4S.get(MainActivity.this).trackLead(lead);
                Snackbar.make(findViewById(R.id.main_view), "Sent Lead event #10", Snackbar.LENGTH_LONG).show();
            }
        });

        // Your second button will send a trackCart event
        findViewById(R.id.event_cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item item = new Item("ArticleID", "Label", "Category", "EUR", 12.30, 1);
                Cart cart = new Cart("CartId", item);
                A4S.get(MainActivity.this).trackAddToCart(cart);
                Snackbar.make(findViewById(R.id.main_view), "Sent Cart event #30", Snackbar.LENGTH_LONG).show();
            }
        });

        // Your third button will send a trackPurchase event
        findViewById(R.id.event_purchase).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Purchase purchase = new Purchase("Purchase ID", "EUR", 12.30);
                A4S.get(MainActivity.this).trackPurchase(purchase);
                Snackbar.make(findViewById(R.id.main_view), "Sent Purchase event #50", Snackbar.LENGTH_LONG).show();
            }
        });


        //Request location permission for geolocation/geofencing features
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_REQUEST);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        A4S.get(this).setIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        A4S.get(this).startActivity(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        A4S.get(this).stopActivity(this);
    }

}
