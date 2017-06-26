package com.accengage.samples.inbox;

import java.util.Set;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
/**
 * InAppHandler
 * @author Jonathan Salamon
 * BroadcastReceiver used to receive and use in-app custom parameters
 */
public class InAppHandler extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Set<String> cats = intent.getCategories();

		//Show the category of this notification
		for (String currentCat : cats) {
			String msg = "Received notification : " + intent.getAction() + "\nC:[" + currentCat + "]";
			Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
		}

		//If there is custom params, display each of these
		try {
			Set<String> extras = intent.getExtras().keySet();
			for (String extra: extras) {
				String msg = "Key: " + extra + " Value: " + intent.getExtras().getString(extra);
				Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
			}

		} catch (Exception e) {
			Toast.makeText(context, "No extras", Toast.LENGTH_SHORT).show();
		}

	}

}
