package com.accengage.sample.custominapps.customviews;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.accengage.sample.custominapps.R;
import com.ad4screen.sdk.InApp;

import java.util.HashMap;

/**
 * Template used: popup_basic
 *
 * Associated custom parameters:
 * - inapp_title: Title text
 * - inapp_text1: Content title
 * - inapp_cta: Call to action text
 */
public class PopupBasicView extends AccengageView {

    private TextView mTvTitle;
    private TextView mTvDesc;
    private TextView mTvCTA;
    private ImageView mCloseButton;
    private View mPopupCoreView;

    public PopupBasicView(Context context) {
        super(context);
        initViews();
    }

    public PopupBasicView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public PopupBasicView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews() {
        inflate(getContext(), R.layout.popup_basic, this);

        mTvTitle = (TextView) findViewById(R.id.title);
        mTvDesc = (TextView) findViewById(R.id.text1);
        mTvCTA = (TextView) findViewById(R.id.cta);
        mCloseButton = (ImageView) findViewById(R.id.close_popup);
        mPopupCoreView = findViewById(R.id.popup_core);
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

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                inApp.dismiss();
            }
        });

        mPopupCoreView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // Nothing
            }
        });
    }
}
