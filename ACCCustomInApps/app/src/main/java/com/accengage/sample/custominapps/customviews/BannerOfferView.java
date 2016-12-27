package com.accengage.sample.custominapps.customviews;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.accengage.sample.custominapps.R;
import com.ad4screen.sdk.InApp;

import java.util.HashMap;

/**
 * Template used: banner_offer
 *
 * Associated custom parameters:
 * - inapp_title: Title text
 * - inapp_text1: Content text
 * - inapp_text2: Content text 2
 * - inapp_bg: Background color
 */
public class BannerOfferView extends CustomInAppLayout {

    private TextView mTvTitle;
    private TextView mTvText1;
    private TextView mTvText2;
    private ImageView mCloseButton;
    private RelativeLayout mContentView;

    public BannerOfferView(Context context) {
        super(context);
        initViews();
    }

    public BannerOfferView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public BannerOfferView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews() {
        inflate(getContext(), R.layout.banner_offer, this);

        mTvTitle = (TextView) findViewById(R.id.title);
        mTvText1 = (TextView) findViewById(R.id.t1);
        mTvText2 = (TextView) findViewById(R.id.t2);
        mCloseButton = (ImageView) findViewById(R.id.close_banner);
        mContentView = (RelativeLayout) findViewById(R.id.relative_layout);
    }

    @Override
    public void populate(InApp inApp) {
        super.populate(inApp);

        HashMap<String, String> customParameters = inApp.getCustomParameters();

        mTvTitle.setText(customParameters.get("inapp_title"));
        mTvText1.setText(customParameters.get("inapp_text1"));
        mTvText2.setText(customParameters.get("inapp_text2"));

        mCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInApp.dismiss();
            }
        });

        mContentView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mInApp.handleClick();
                Toast.makeText(getContext(), "BannerOffer was clicked", Toast.LENGTH_SHORT).show();
            }
        });

        mContentView.setBackgroundColor(Color.parseColor(customParameters.get("inapp_bg")));
    }
}
