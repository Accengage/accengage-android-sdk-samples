package com.accengage.sample.custominapps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.accengage.sample.custominapps.customviews.AccengageView;
import com.ad4screen.sdk.A4S;
import com.ad4screen.sdk.InApp;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.Tag;

@Tag(name = "MainActivity")
public class MainActivity extends AppCompatActivity {

    private static final String BANNER_DOWN = "Banner Down";
    private static final String BANNER_OFFER = "Banner Offer";
    private static final String BANNER_OFFER_PROMOCODE = "Banner Offer Promocode";
    private static final String POPUP_BASIC = "Popup Basic";
    private static final String POPUP_OFFER = "Popup Offer";
    private static final String POPUP_PROMOCODE = "Popup Promocode";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView listView = (ListView) findViewById(R.id.activity_main_listview);

        String[] values = new String[]{
                BANNER_DOWN,
                BANNER_OFFER,
                BANNER_OFFER_PROMOCODE,
                POPUP_BASIC,
                POPUP_OFFER,
                POPUP_PROMOCODE
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch ((String) listView.getItemAtPosition(position)) {
                    case BANNER_DOWN:
                        getA4S().setView("BannerDownActivity");
                        break;
                    case BANNER_OFFER:
                        getA4S().setView("BannerOfferActivity");
                        break;
                    case BANNER_OFFER_PROMOCODE:
                        getA4S().setView("BannerOfferPromo");
                        break;
                    case POPUP_BASIC:
                        getA4S().setView("PopupBasicActivity");
                        break;
                    case POPUP_OFFER:
                        getA4S().setView("PopupOfferActivity");
                        break;
                    case POPUP_PROMOCODE:
                        getA4S().setView("PopupPromoActivity");
                        break;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Initialize callback in onResume because stopActivity reinitialize callbacks
        getA4S().setInAppInflatedCallback(new A4S.Callback<InApp>() {
            @Override
            public void onResult(InApp inapp) {
                AccengageView customView = AccengageCustomViewFactory.create(MainActivity.this, inapp.getDisplayTemplate());
                if (customView != null) {
                    customView.setInApp(inapp);
                    FrameLayout frameLayout = inapp.getLayout(); // Get the layout created by the SDK
                    frameLayout.removeAllViews(); // Clear views already created by the SDK
                    frameLayout.addView(customView); // Add the created view to the layout that will be inflated by the SDK
                }
            }

            @Override
            public void onError(int i, String s) {
                Log.error("Somehow didn't work : " + s);
            }
        });

        getA4S().startActivity(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getA4S().stopActivity(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        getA4S().setIntent(intent);
    }

    public A4S getA4S() {
        return A4S.get(this);
    }

}
