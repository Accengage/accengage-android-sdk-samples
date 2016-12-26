package com.accengage.samples.beacons.activities;

import java.util.ArrayList;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.accengage.samples.beacons.R;
import com.ad4screen.sdk.Constants;
import com.ad4screen.sdk.activities.A4SActivity;

public class BeaconDetectorActivity extends A4SActivity {

	private ListView mListView;
	private ArrayAdapter<String> mAdapter;
	private ArrayList<String> mBeacons;

	BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle payload = intent.getExtras();
			if(payload != null) {
				Bundle beacons = payload.getBundle(Constants.EXTRA_BEACON_PAYLOAD);
				if(beacons == null) {
					return;
				}
				try {
					JSONObject root = new JSONObject();
					JSONArray beaconsArray = new JSONArray();
					Set<String> triggeredBeacons = beacons.keySet();
					for (String beaconId : triggeredBeacons) {
						JSONObject beaconObject = new JSONObject();
						Bundle beaconDetails = beacons.getBundle(beaconId);
						if (beaconDetails != null) {
							beaconObject.put("id", beaconDetails.getString("id"));
							beaconObject.put("transition", beaconDetails.getInt("transition") == 1 ? "enter" : "exit");
							beaconObject.put("uuid", beaconDetails.getString("uuid"));
							beaconObject.put("maj", beaconDetails.getInt("maj"));
							beaconObject.put("min", beaconDetails.getInt("min"));
							beaconObject.put("power", beaconDetails.getInt("power"));
							beaconObject.put("dist", beaconDetails.getDouble("dist"));
							beaconObject.put("acc", beaconDetails.getString("acc"));
							beaconObject.put("rssi", beaconDetails.getInt("rssi"));
							beaconObject.put("date", beaconDetails.getLong("date"));
						}
						beaconsArray.put(beaconObject);
					}

					root.put("beacons", beaconsArray);
					mBeacons.add(0, root.toString());
					mAdapter = new ArrayAdapter<>(BeaconDetectorActivity.this,
							android.R.layout.simple_list_item_1, 
							mBeacons.toArray(new String[mBeacons.size()]));
					mListView.setAdapter(mAdapter);

				} catch(JSONException e) {
					e.printStackTrace();
				}
			}

		}
	};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_beacon);

		mBeacons = new ArrayList<>();
		mAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, new String[] {"No Beacon Found"});
		mListView = (ListView) findViewById(R.id.listview_beacons);
		mListView.setAdapter(mAdapter);

		findViewById(R.id.buttonClearBeacons).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mAdapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1, new String[] {"No Beacon Found"});
				mBeacons.clear();
				mListView.setAdapter(mAdapter);
			}
		});

	}

	@Override
	protected void onResume() {
		super.onResume();
		//Listen for beacons
		IntentFilter filter = new IntentFilter(Constants.ACTION_TRIGGER);
		filter.addCategory(Constants.CATEGORY_BEACON_NOTIFICATIONS);
		registerReceiver(receiver, filter);
	}

	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(receiver);
	}
}




