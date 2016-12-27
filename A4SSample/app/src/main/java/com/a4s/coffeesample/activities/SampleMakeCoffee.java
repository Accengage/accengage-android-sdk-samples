package com.a4s.coffeesample.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.a4s.coffeesample.R;
import com.ad4screen.sdk.A4S;
import com.ad4screen.sdk.Tag;

@Tag(name = "MakeCoffee")
public class SampleMakeCoffee extends Activity {
	private int step = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if(step <= 3) 
			setContentView(R.layout.activity_sample_make_coffee);
		else 
			setContentView(R.layout.activity_sample_make_coffee_positionned);
		
		
		setTitle("View : MakeCoffee");

		step = getIntent().getIntExtra("step", 1);

		TextView lblStep = (TextView) findViewById(R.id.lblStep);
		lblStep.setText("You are on step #" + step);

		TextView eventText = (TextView) findViewById(R.id.txtEvent);
		if(step == 1) eventText.setText("Event 2000 / Step" + step);
		else eventText.setText("Event 3000 / Step" + step);

		findViewById(R.id.btnMoreBrew).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(step == 1) A4S.get(SampleMakeCoffee.this).trackEvent(Long.parseLong("2000"), "Step" + step);
				else A4S.get(SampleMakeCoffee.this).trackEvent(Long.parseLong("3000"), "Step" + step);

				Intent i = new Intent(SampleMakeCoffee.this, SampleMakeCoffee.class);
				i.putExtra("step", step+1);

				startActivity(i);
			}
		});

		findViewById(R.id.btnSendEvent).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				A4S.get(SampleMakeCoffee.this).trackEvent(Long.parseLong("5000"), "SendEvent");
			}
		});
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		A4S.get(this).setIntent(intent);
	}

	@Override
	protected void onResume() {
		super.onResume();
		A4S.get(this).startActivity(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		A4S.get(this).stopActivity(this);
	}
}


