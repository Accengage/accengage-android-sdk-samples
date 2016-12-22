package com.accengage.sample.custominapps.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.ad4screen.sdk.InApp;

/**
 * Parent view for all Accengage custom views
 */
public abstract class AccengageView extends LinearLayout {

    public AccengageView(Context context) {
        super(context);
    }

    public AccengageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AccengageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public abstract void setInApp(InApp inApp);
}
