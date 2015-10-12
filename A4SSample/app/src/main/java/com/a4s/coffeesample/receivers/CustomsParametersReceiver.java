package com.a4s.coffeesample.receivers;

import java.util.Set;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.a4s.coffeesample.activities.SampleSettings;
/**
 * Handle custom parameters as explained on 
 * @see http://docs.accengage.com/display/AND/Android#Android-RetrievingIn-App/Push/InboxandotherCustomParameterswithaBroadcastReceiver
 * In this sample, this class is called as soon as an in-app or push is clicked
 * @author Jonathan Salamon
 *
 */
public class CustomsParametersReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Set<String> cats = intent.getCategories();
		Bundle bundle = intent.getExtras();
		if(bundle != null) {
			//Use category in order to know which case to handle
			//You can retrieve the action too in order to know which action has been triggered
			//All actions and categories available can be found inside com.ad4screen.sdk.Constants
			for (String currentCat : cats) {
				if(currentCat.equals(com.ad4screen.sdk.Constants.CATEGORY_PUSH_NOTIFICATIONS)) {
					//Handle push custom parameters
					handlePushCustomParameters(context, bundle);
				}

				if(currentCat.equals(com.ad4screen.sdk.Constants.CATEGORY_INAPP_NOTIFICATIONS)) {
					//Handle in-app custom parameters
				} 
			}
		}
	}

	/**
	 * Handle and trigger an action on some custom parameters
	 * @param context
	 * @param bundle
	 */
	private void handlePushCustomParameters(Context context, Bundle bundle) {
		//Get your key and do what you want
		String myCustomParameter = bundle.getString("my-key");
		//For instance, here we will open Settings activity if my-key value is settings
		if(myCustomParameter != null && myCustomParameter.equals("settings")) {
			Intent intent = new Intent(context, SampleSettings.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			//Start new activity
			context.startActivity(intent);
		}
	}

}
