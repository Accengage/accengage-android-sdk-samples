package com.accengage.samples.inappmultiaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ad4screen.sdk.A4S;
import com.ad4screen.sdk.InApp;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private InApp mInApp = null;
    private String mActionUrl = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        A4S.get(this).setInAppInflatedCallback(new A4S.Callback<InApp>() {
            @Override
            public void onResult(InApp inapp) {
                Log.d(TAG, "InAppInflatedCallback");
                mInApp = inapp;
                HashMap<String, String> params = mInApp.getCustomParameters();

                android.util.Log.d(TAG, "Custom display parameters: " + params);
                if (params.containsKey("action_url")) {
                    mActionUrl = params.get("action_url");
                }


                FrameLayout layout = inapp.getLayout();
                WebView webview = (WebView) layout.findViewById(R.id.com_ad4screen_sdk_webview);
                if (webview != null) {
                    webview.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            com.ad4screen.sdk.Log.debug("motion=" + event.getAction());
                            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                                Toast.makeText(MainActivity.this, "Zone#WebView, track CLICK", Toast.LENGTH_SHORT).show();
                                mInApp.setClickZone("Zone#WebView");
                                mInApp.handleClick();
                            }
                            return false;
                        }
                    });
                }

                Button buttonInform = (Button) layout.findViewById(R.id.btn_inform);
                if (buttonInform != null) {
                    buttonInform.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // If the action url is not null, open it in a browser
                            if (mActionUrl != null) {
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mActionUrl));
                                startActivity(browserIntent);
                            }
                            // Set tracking zone for tracking and close inapp
                            Toast.makeText(MainActivity.this, "Zone1, track CLICK", Toast.LENGTH_SHORT).show();
                            mInApp.setClickZone("Zone1");
                            mInApp.handleClick();
                        }
                    });
                }

                Button buttonLater = (Button) layout.findViewById(R.id.btn_later);
                if (buttonLater != null) {
                    buttonLater.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(MainActivity.this, "Zone2, track CLICK", Toast.LENGTH_SHORT).show();
                            mInApp.setClickZone("Zone2");
                            mInApp.handleClick();
                        }
                    });
                }

                ImageView iv = (ImageView) layout.findViewById(R.id.iv_close);
                if (iv != null) {
                    iv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(MainActivity.this, "Zone#Close, track CLOSE", Toast.LENGTH_SHORT).show();
                            mInApp.setClickZone("Zone#Close");
                            mInApp.dismiss();
                        }
                    });
                }

                LinearLayout layoutBanner = (LinearLayout) layout.findViewById(R.id.ll_banner);
                if (layoutBanner != null) {
                    layoutBanner.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(MainActivity.this, "The zone is not used", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });

    }
}
