package com.accengage.samples.inbox.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.accengage.samples.inbox.R;
import com.ad4screen.sdk.Message;
import com.ad4screen.sdk.Message.MessageContentType;
import com.ad4screen.sdk.Message.onIconDownloadedListener;
import com.ad4screen.sdk.Tag;


/**
 * ShowMessage
 * Display a specific message
 * @author Jonathan Salamon/Jean-Vincent D'Adda
 *
 */
@SuppressWarnings("deprecation")
@Tag(name = "ShowMessage")
public class ShowMessage extends Activity {
	private Message mMessage;

	@SuppressLint({ "SetJavaScriptEnabled", "NewApi" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_showmessage);
		setTitle("View : ShowMessage");

		Bundle bundle = getIntent().getExtras();
		mMessage = bundle.getParcelable("message");
		
		//Show title
		TextView title = (TextView) findViewById(R.id.titleMessage);
		title.setText(mMessage.getTitle());

		//Show date
		TextView date = (TextView) findViewById(R.id.dateMessage);
		date.setText("Reçu le " + mMessage.getSendDate().toLocaleString());

		//Show sender
		TextView sender = (TextView) findViewById(R.id.senderMessage);
		sender.setText("Expéditeur : " + mMessage.getSender());

		// Show extract
		TextView preview = (TextView) findViewById(R.id.summary);
		preview.setText(mMessage.getText());

		// Show category if any
		TextView category = (TextView) findViewById(R.id.categoryMessage);
		String categoryMessage = mMessage.getCategory();
		if (TextUtils.isEmpty(categoryMessage)) {
			category.setVisibility(View.INVISIBLE);
		} else {
			category.setVisibility(View.VISIBLE);
			category.setText(mMessage.getCategory());
		}

		//Show content
		TextView body = (TextView) findViewById(R.id.contentMessage);
		WebView webviewMessage = (WebView) findViewById(R.id.webViewShowMessage);
		webviewMessage.getSettings().setJavaScriptEnabled(true);
		//If this message is a text, display content in a text view
		if (mMessage.getContentType() == MessageContentType.Text) {
			body.setVisibility(View.VISIBLE);
			body.setText(mMessage.getBody());
		} else
			body.setVisibility(View.GONE);

		//Or hide the textview and display a webview
		if (mMessage.getContentType() == MessageContentType.Web) {
			webviewMessage.setWebViewClient(new WebViewClient());
			webviewMessage.setVisibility(View.VISIBLE);
			webviewMessage.loadUrl(mMessage.getBody());
		} else
			webviewMessage.setVisibility(View.GONE);

		final ImageView iconImageView = (ImageView) findViewById(R.id.iconMessage);

		//Retrieve thumbnail
		mMessage.getIcon(new onIconDownloadedListener() {

			@Override
			public void onIconDownloaded(Bitmap image) {
				//Show thumbnail
				iconImageView.setImageBitmap(image);

			}
		});

		// Show Buttons
		LinearLayout buttonsLayout = (LinearLayout) findViewById(R.id.buttonsLayout);
		buttonsLayout.setWeightSum(1.0f);
		for (int i = 0; i < mMessage.countButtons(); i++) {
			final com.ad4screen.sdk.Message.Button inboxButton = mMessage.getButton(i);
			Button button = new Button(getApplicationContext());
			button.setText(mMessage.getButton(i).getTitle());
			button.setTextColor(Color.WHITE);
			button.setBackgroundColor(Color.parseColor("#007AFF"));
			button.setPadding(10, 2, 10, 2);

			button.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					inboxButton.click(getApplicationContext());

				}
			});

			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 0.5f);
			params.leftMargin = 20;
			params.rightMargin = 20;
			buttonsLayout.addView(button, params);
		}

		//Set transition
		if(Build.VERSION.SDK_INT >= 21) {
			title.setTransitionName("title");
			preview.setTransitionName("extract");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (mMessage.isArchived())
			getMenuInflater().inflate(R.menu.menu_message_unarchive, menu);
		else {
			getMenuInflater().inflate(R.menu.menu_message_actions, menu);
			if(mMessage.isRead()) {
				menu.findItem(R.id.menu_markread).setVisible(false);
				menu.findItem(R.id.menu_markunread).setVisible(true);
			} else {
				menu.findItem(R.id.menu_markread).setVisible(true);
				menu.findItem(R.id.menu_markunread).setVisible(false);
			}
		}

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.menu_markread) {
			mMessage.setRead(true);
			ActivityCompat.invalidateOptionsMenu(this);
		}
		if (item.getItemId() == R.id.menu_markunread) {
			mMessage.setRead(false);
			ActivityCompat.invalidateOptionsMenu(this);
		}
		if (item.getItemId() == R.id.menu_archive) {
			mMessage.setArchived(true);
			ActivityCompat.invalidateOptionsMenu(this);
		}
		if (item.getItemId() == R.id.menu_unarchive) {
			mMessage.setArchived(false);
			ActivityCompat.invalidateOptionsMenu(this);
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		finish();
	}
	@Override
	public void finish() {
		//Send any status update back to preivous activity
		Intent intent = new Intent();
		intent.putExtra("read", mMessage.isRead());
		intent.putExtra("archived", mMessage.isArchived());
		setResult(RESULT_OK, intent);
		super.finish();
	}
}
