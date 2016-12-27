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
 * Template used: popup_promo
 *
 * Associated custom parameters:
 * - inapp_title: Title text
 * - inapp_text1: Content text
 * - inapp_text2: Content text 2
 * - inapp_img: Url of a distant image
 * - inapp_cta: Call to action text
 */
public class PopupPromoView extends CustomInAppLayout {

    private TextView mTvTitle;
    private TextView mTvText1;
    private TextView mTvText2;
    private TextView mTvCTA;
    private ImageView mImageView;
    private ImageView mCloseButton;
    private View mPopupCoreView;

    public PopupPromoView(Context context) {
        super(context);
        initViews();
    }

    public PopupPromoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public PopupPromoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews() {
        inflate(getContext(), R.layout.popup_promo, this);

        mTvTitle = (TextView) findViewById(R.id.title);
        mTvText1 = (TextView) findViewById(R.id.text1);
        mTvText2 = (TextView) findViewById(R.id.text2);
        mTvCTA = (TextView) findViewById(R.id.cta);
        mImageView = (ImageView) findViewById(R.id.background);
        mCloseButton = (ImageView) findViewById(R.id.close_popup);
        mPopupCoreView = findViewById(R.id.popup_core);
    }

    @Override
    public void populate(InApp inApp) {
        super.populate(inApp);

        HashMap<String, String> customParameters = inApp.getCustomParameters();

        mTvTitle.setText(customParameters.get("inapp_title"));
        mTvText1.setText(customParameters.get("inapp_text1"));
        mTvText2.setText(customParameters.get("inapp_text2"));
        mTvCTA.setText(customParameters.get("inapp_cta"));

        mCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInApp.dismiss();
            }
        });

        mTvCTA.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mInApp.handleClick();
                Toast.makeText(getContext(), "PopupPromo clicked", Toast.LENGTH_SHORT).show();
            }
        });

        Picasso.with(getContext())
                .load(customParameters.get("inapp_img"))
                .into(mImageView);

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mInApp.dismiss();
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
