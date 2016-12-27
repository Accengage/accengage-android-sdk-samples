package com.accengage.sample.custominapps.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.ad4screen.sdk.InApp;

public class CustomInAppLayout extends LinearLayout {

    protected InApp mInApp;

    public CustomInAppLayout(Context context) {
        super(context);
    }

    public CustomInAppLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomInAppLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void populate(InApp inApp) {
        mInApp = inApp;
    }
}
