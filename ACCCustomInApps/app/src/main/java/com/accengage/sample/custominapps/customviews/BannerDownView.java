package com.accengage.sample.custominapps.customviews;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.accengage.sample.custominapps.R;
import com.ad4screen.sdk.InApp;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

/**
 * Template used: banner_down
 *
 * Associated custom parameters:
 * - inapp_title: Title text
 * - inapp_text1: Content text
 * - inapp_cta: Call to Action text
 * - inapp_img: Url of a distant image
 */
public class BannerDownView extends AccengageView {

    private TextView mTvTitle;
    private TextView mTvDesc;
    private TextView mTvCTA;
    private ImageView mImageView;
    private ImageView mCloseButton;

    public BannerDownView(Context context) {
        super(context);
        initViews();
    }

    public BannerDownView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public BannerDownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews() {
        inflate(getContext(), R.layout.banner_down, this);

        mTvTitle = (TextView) findViewById(R.id.title);
        mTvDesc = (TextView) findViewById(R.id.description);
        mTvCTA = (TextView) findViewById(R.id.cta);
        mImageView = (ImageView) findViewById(R.id.imageIcon);
        mCloseButton = (ImageView) findViewById(R.id.close_banner);
    }

    @Override
    public void setInApp(final InApp inApp) {
        HashMap<String, String> customParameters = inApp.getCustomParameters();
        mTvTitle.setText(customParameters.get(getResources().getString(R.string.inapp_title)));
        mTvDesc.setText(customParameters.get(getResources().getString(R.string.inapp_text1)));
        mTvCTA.setText(customParameters.get(getResources().getString(R.string.inapp_cta)));

        mCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inApp.dismiss();
            }
        });

        mTvCTA.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                inApp.handleClick();
                Toast.makeText(getContext(), "BannerDown was clicked", Toast.LENGTH_SHORT).show();
            }
        });

        Picasso.with(getContext())
                .load(customParameters.get(getResources().getString(R.string.inapp_img)))
                .into(mImageView);
    }
}