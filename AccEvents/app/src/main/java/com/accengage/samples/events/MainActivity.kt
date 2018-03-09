package com.accengage.samples.events

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View

import com.ad4screen.sdk.A4S
import com.ad4screen.sdk.analytics.Cart
import com.ad4screen.sdk.analytics.Item
import com.ad4screen.sdk.analytics.Lead
import com.ad4screen.sdk.analytics.Purchase
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        // Your first button will send a trackLead event
        findViewById<View>(R.id.event_lead).setOnClickListener {
            val lead = Lead("Lead Label", "Lead Value")
            A4S.get(this@MainActivity).trackLead(lead)
            Snackbar.make(findViewById(R.id.main_view), "Sent Lead event #10", Snackbar.LENGTH_LONG).show()
        }

        // Your second button will send a trackCart event
        findViewById<View>(R.id.event_cart).setOnClickListener {
            val item = Item("ArticleID", "Label", "Category", "EUR", 12.30, 1)
            val cart = Cart("CartId", item)
            A4S.get(this@MainActivity).trackAddToCart(cart)
            Snackbar.make(findViewById(R.id.main_view), "Sent Cart event #30", Snackbar.LENGTH_LONG).show()
        }

        // Your third button will send a trackPurchase event
        findViewById<View>(R.id.event_purchase).setOnClickListener {
            val purchase = Purchase("Purchase ID", "EUR", 12.30)
            A4S.get(this@MainActivity).trackPurchase(purchase)
            Snackbar.make(findViewById(R.id.main_view), "Sent Purchase event #50", Snackbar.LENGTH_LONG).show()
        }

        // Your fourth button will send a trackEvent event, with pre-set fields, using GSON
        // to show you how to track a custom event
        findViewById<View>(R.id.event_custom).setOnClickListener {
            val myEvent = MyEvent()
            val gson = Gson()
            val json = gson.toJson(myEvent)
            A4S.get(this@MainActivity).trackEvent(1001, json)
            Snackbar.make(findViewById(R.id.main_view), "Sent Custom event #1001", Snackbar.LENGTH_LONG).show()
        }


        //Request location permission for geolocation/geofencing features
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_REQUEST)
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        A4S.get(this).setIntent(intent)
    }

    override fun onResume() {
        super.onResume()
        A4S.get(this).startActivity(this)
    }

    override fun onPause() {
        super.onPause()
        A4S.get(this).stopActivity(this)
    }

    companion object {

        private val LOCATION_REQUEST = 1
    }

}
