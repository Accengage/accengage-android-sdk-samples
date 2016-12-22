package com.accengage.sample.custominapps;

import android.content.Context;

import com.accengage.sample.custominapps.customviews.AccengageView;
import com.accengage.sample.custominapps.customviews.BannerDownView;
import com.accengage.sample.custominapps.customviews.BannerOfferPromoView;
import com.accengage.sample.custominapps.customviews.BannerOfferView;
import com.accengage.sample.custominapps.customviews.PopupBasicView;
import com.accengage.sample.custominapps.customviews.PopupOfferView;
import com.accengage.sample.custominapps.customviews.PopupPromoView;

public class AccengageCustomViewFactory {

    public static AccengageView create(Context context, String template) {

        if ("banner_down".equals(template)) {
            return new BannerDownView(context);
        } else if ("banner_offer".equals(template)) {
            return new BannerOfferView(context);
        } else if ("banner_offer_promo".equals(template)) {
            return new BannerOfferPromoView(context);
        } else if ("popup_basic".equals(template)) {
            return new PopupBasicView(context);
        } else if ("popup_offer".equals(template)) {
            return new PopupOfferView(context);
        } else if ("popup_promo".equals(template)) {
            return new PopupPromoView(context);
        }

        return null;
    }
}
