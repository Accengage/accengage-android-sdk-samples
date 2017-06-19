package com.accengage.samples.custominapps.customviews;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.accengage.samples.custominapps.R;
import com.ad4screen.sdk.InApp;

import java.util.HashMap;

/**
 * Template used: banner_offer_promo
 *
 * Associated custom parameters:
 * - inapp_title: Title text
 * - inapp_text1: Content text
 * - inapp_text2: Content text 2
 * - inapp_promo: Promotion code
 * - inapp_bg: Background color
 */
public class BannerOfferPromoView extends CustomInAppLayout {

    private TextView mTvTitle;
    private TextView mTvDesc;
    private TextView mTvCTA;
    private TextView mTvPromocode;
    private ImageView mCloseButton;
    private RelativeLayout mContentLayout;

    public BannerOfferPromoView(Context context) {
        super(context);
        initViews();
    }

    public BannerOfferPromoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public BannerOfferPromoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }


    private void initViews() {
        inflate(getContext(), R.layout.banner_offer_promo, this);

        mTvTitle = (TextView) findViewById(R.id.title);
        mTvDesc = (TextView) findViewById(R.id.t1);
        mTvCTA = (TextView) findViewById(R.id.t2);
        mTvPromocode = (TextView) findViewById(R.id.promocode);
        mCloseButton = (ImageView) findViewById(R.id.close_banner);
        mContentLayout = (RelativeLayout) findViewById(R.id.relative_layout);
    }

    @Override
    public void populate(InApp inApp) {
        super.populate(inApp);

        HashMap<String, String> customParameters = inApp.getCustomParameters();

        mTvTitle.setText(customParameters.get("inapp_title"));
        mTvDesc.setText(customParameters.get("inapp_text1"));
        mTvCTA.setText(customParameters.get("inapp_text2"));
        mTvPromocode.setText(customParameters.get("inapp_promo"));

        mCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInApp.dismiss();
            }
        });

        mContentLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mInApp.handleClick();
                Toast.makeText(getContext(), "BannerOfferPromo was clicked", Toast.LENGTH_SHORT).show();
            }
        });


        String background = customParameters.get("inapp_bg");
        if (!background.isEmpty()) {
            mContentLayout.setBackgroundColor(Color.parseColor(customParameters.get("inapp_bg")));
        }
    }
}

