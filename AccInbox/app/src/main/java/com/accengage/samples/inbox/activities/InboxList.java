package com.accengage.samples.inbox.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.accengage.samples.inbox.R;
import com.accengage.samples.inbox.adapters.MessagesAdapter;
import com.accengage.samples.inbox.adapters.MessagesAdapter.OnMessageCheckedListener;
import com.accengage.samples.inbox.models.InboxMessage;
import com.ad4screen.sdk.A4S;
import com.ad4screen.sdk.A4S.Callback;
import com.ad4screen.sdk.A4S.MessageCallback;
import com.ad4screen.sdk.Inbox;
import com.ad4screen.sdk.Message;
import com.ad4screen.sdk.Tag;

import java.util.ArrayList;

/**
 * InboxList
 * List all inbox messages of this device
 * @author Jonathan Salamon/Jean-Vincent D'Adda 
 */
@Tag(name = "InboxList")
public class InboxList extends Activity {

	private static final int LOCATION_REQUEST = 1;
	private static final int REQUEST_SHOWMESSAGE = 1000;
	private Inbox mInbox;
	private Callback<Inbox> mInboxCallback;
	private SwipeRefreshLayout mInboxSwipeRefreshLayout;

	private Handler mHandler;

	private ArrayList<Integer> mCheckedMessagePosition;
	private int mArchivedMessages = 0;

	private ArrayList<InboxMessage> mMessagesList = new ArrayList<>();

	private ListView mListView;
	private MessagesAdapter mListAdapter;
	private int mCurrentDisplayedMessageIndex;

	//
	// LIFECYCLE
	//

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// This handler is only used to auto-cancel refresh animation after a
		// timeout
		mHandler = new Handler();

		setContentView(R.layout.activity_sample_inbox_list);
		setTitle("Inbox");

		// Save checked message position in order to do grouped update (set
		// read, unread, archive)
		mCheckedMessagePosition = new ArrayList<>();

		// Setup Layout
		mInboxSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
		mInboxSwipeRefreshLayout.setColorSchemeColors(
				getResources().getColor(R.color.progress_blue_light),
				getResources().getColor(R.color.progress_green_light),
				getResources().getColor(R.color.progress_red_light),
				getResources().getColor(R.color.progress_orange_light));
		mInboxSwipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				if (mInbox != null) {
					// If we have already an inbox, synchronize changes with SDK
					// before getting updated Inbox
                    A4S.get(InboxList.this).updateMessages(mInbox);
				}
				// Retrieve Inbox
                A4S.get(InboxList.this).getInbox(mInboxCallback);
			}
		});

		mInboxCallback = new Callback<Inbox>() {

			@Override
			public void onResult(Inbox result) {
				if(result == null) {
					stopRefreshing();
					return;
				}
				// Reset list if something changed (message archived, new
				// message..)
				if (mMessagesList.size() != result.countMessages()) {
					mMessagesList.clear();
					for (int i = 0; i < result.countMessages(); i++) {
						mMessagesList.add(new InboxMessage());
					}
				}

				mListAdapter = new MessagesAdapter(result, mMessagesList);
				mListAdapter.setOnMessageCheckedListener(new OnInboxMessageCheckedListener());

				mListView = (ListView) findViewById(R.id.listView1);
				mListView.setAdapter(mListAdapter);
				mListView.setOnItemClickListener(new OnInboxItemClickListener());
				// Reset checked messages positions
				mCheckedMessagePosition.clear();
				ActivityCompat.invalidateOptionsMenu(InboxList.this);
				mInbox = result;

				final int countMessages = result.countMessages();
				if (countMessages == 0) {
					stopRefreshing();
				}

				int countUnReadMessages = result.countUnReadMessages();
				if (countUnReadMessages > 0) {
					// Display unread messages count
					setTitle("Inbox (" + countUnReadMessages + ")");
				} else {
					setTitle("Inbox");
				}
				stopRefreshing();
			}

			@Override
			public void onError(int error, String errorMessage) {
				// Something wrong happens : Server unavailable, no internet
				// connection
				Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
				stopRefreshing();
			}
		};

		//Request location permission for geolocation/geofencing features
		if (ContextCompat.checkSelfPermission(this,
				Manifest.permission.ACCESS_FINE_LOCATION)
				!= PackageManager.PERMISSION_GRANTED) {

			ActivityCompat.requestPermissions(this,
					new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
					LOCATION_REQUEST);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		A4S.get(this).startActivity(this);
		startRefreshing();
		if (mInbox != null) {
			// if we have already an inbox, display it
			mInboxCallback.onResult(mInbox);
		} else {
			// otherwise, load it from server
			A4S.get(this).getInbox(mInboxCallback);
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		A4S.get(this).setIntent(intent);
	}

	@Override
	protected void onPause() {
		if (mInbox != null) {
			// Synchronize any changes with SDK
            A4S.get(InboxList.this).updateMessages(mInbox);
		}
		super.onPause();
		A4S.get(this).stopActivity(this);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_SHOWMESSAGE) {
			// ShowMessage activity can update the currently displayed message.
			// Retrieve any change and update our inbox object
			if (resultCode == RESULT_OK) {
				if (mInbox != null) {
					mInbox.getMessage(mCurrentDisplayedMessageIndex, new MessageCallback() {

						@Override
						public void onResult(Message result, int index) {
							result.setRead(data.getBooleanExtra("read", true));
							result.setArchived(data.getBooleanExtra("archived", false));
							mInboxCallback.onResult(mInbox);
						}

						@Override
						public void onError(int error, String errorMessage) {
						}
					});
				}
			}
		}
	}

	private Runnable refreshingRunnable = new Runnable() {

		@Override
		public void run() {
			mInboxSwipeRefreshLayout.setRefreshing(false);
		}
	};

	/**
	 * Display refresh animation
	 */
	private void startRefreshing() {
		mInboxSwipeRefreshLayout.setRefreshing(true);
		mHandler.removeCallbacks(refreshingRunnable);
		mHandler.postDelayed(refreshingRunnable, 30 * 1000);
	}

	/**
	 * Hide refresh animation
	 */
	private void stopRefreshing() {
		mInboxSwipeRefreshLayout.setRefreshing(false);
		mHandler.removeCallbacks(refreshingRunnable);
	}

	//
	// Menu
	//

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		if (mCheckedMessagePosition.size() > 0) {
			// We are displaying distinct menus if messages are all read,
			// archived, unread..
			for (int i = 0; i < mCheckedMessagePosition.size(); i++) {

				Message result = mMessagesList.get(mCheckedMessagePosition.get(i)).getMessage();
				if (result.isArchived())
					mArchivedMessages++;
			}
			if (mCheckedMessagePosition.size() == mArchivedMessages)
				getMenuInflater().inflate(R.menu.menu_message_unarchive, menu);
			else
				getMenuInflater().inflate(R.menu.menu_message_actions, menu);

			mArchivedMessages = 0;
		}
		else {
			getMenuInflater().inflate(R.menu.menu_inbox, menu);
		}

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Reset status of all messages
		if (item.getItemId() == R.id.menu_reset) {
			if (mInbox == null)
				return false;
			for (int i = 0; i < mInbox.countMessages(); i++) {

				mInbox.getMessage(i, new MessageCallback() {

					@Override
					public void onResult(Message result, int index) {
						result.setRead(false);
						result.setArchived(false);
						mInboxCallback.onResult(mInbox);
					}

					@Override
					public void onError(int error, String errorMessage) {

					}
				});
			}

		}
		// Synchronize and retrieve messages from server
		if (item.getItemId() == R.id.menu_get_messages) {
			if (mInbox != null) {
                A4S.get(this).updateMessages(mInbox);
			}
            A4S.get(this).getInbox(mInboxCallback);
			startRefreshing();
		}
		// Mark messages as read
		if (item.getItemId() == R.id.menu_markread) {
			for (int i = 0; i < mCheckedMessagePosition.size(); i++) {

				mInbox.getMessage(mCheckedMessagePosition.get(i), new MessageCallback() {
					@Override
					public void onResult(Message result, int index) {
						result.setRead(true);
						// If it's the last message to update, update list
						if (mCheckedMessagePosition.get(mCheckedMessagePosition.size() - 1) == index) {
							mInboxCallback.onResult(mInbox);
						}
					}

					@Override
					public void onError(int error, String errorMessage) {

					}
				});
			}

			ActivityCompat.invalidateOptionsMenu(InboxList.this);
		}
		// Mark messages as unread
		if (item.getItemId() == R.id.menu_markunread) {
			for (int i = 0; i < mCheckedMessagePosition.size(); i++) {

				mInbox.getMessage(mCheckedMessagePosition.get(i), new MessageCallback() {

					@Override
					public void onResult(Message result, int index) {
						result.setRead(false);
						if (mCheckedMessagePosition.get(mCheckedMessagePosition.size() - 1) == index) {
							mInboxCallback.onResult(mInbox);
						}
					}

					@Override
					public void onError(int error, String errorMessage) {
						// Nothing
					}
				});
			}
			ActivityCompat.invalidateOptionsMenu(InboxList.this);
		}
		// Archive messages.
		// WARNING : Archive = Delete (archived messages will not be received
		// again when calling getInbox)
		if (item.getItemId() == R.id.menu_archive) {
			for (int i = 0; i < mCheckedMessagePosition.size(); i++) {

				mInbox.getMessage(mCheckedMessagePosition.get(i), new MessageCallback() {

					@Override
					public void onResult(Message result, int index) {
						result.setArchived(true);
						if (mCheckedMessagePosition.get(mCheckedMessagePosition.size() - 1) == index) {
							mInboxCallback.onResult(mInbox);
						}
					}

					@Override
					public void onError(int error, String errorMessage) {
						// Nothing
					}
				});
			}
			ActivityCompat.invalidateOptionsMenu(InboxList.this);
		}
		// Restore messages
		if (item.getItemId() == R.id.menu_unarchive) {
			for (int i = 0; i < mCheckedMessagePosition.size(); i++) {

				mInbox.getMessage(mCheckedMessagePosition.get(i), new MessageCallback() {

					@Override
					public void onResult(Message result, int index) {
						result.setArchived(false);
						if (mCheckedMessagePosition.get(mCheckedMessagePosition.size() - 1) == index) {
							mInboxCallback.onResult(mInbox);
						}
					}

					@Override
					public void onError(int error, String errorMessage) {
						// Nothing
					}
				});

			}
			ActivityCompat.invalidateOptionsMenu(InboxList.this);
		}

		return false;
	}

	/**
	 * InboxItemClickListener Action done when a message is clicked
	 * 
	 * @author Jonathan Salamon/Jean-Vincent D'Adda
	 *
	 */
	private final class OnInboxItemClickListener implements OnItemClickListener {
		@SuppressLint("NewApi")
		@Override
		public void onItemClick(AdapterView<?> arg0, final View view, final int position, long arg3) {
			if (mInboxSwipeRefreshLayout.isRefreshing())
				return;

			mInbox.getMessage(position, new MessageCallback() {

				@Override
				public void onResult(Message message, final int index) {
					// Message is now read because we clicked on it
					message.setRead(true);
					// Synchronize changes
                    A4S.get(InboxList.this).updateMessages(mInbox);
					// Re-display inbox
					mInboxCallback.onResult(mInbox);

					message.display(getApplicationContext(), new Callback<Message>() {

						@Override
						public void onResult(Message m) {
							mCurrentDisplayedMessageIndex = index;
							Intent intent = new Intent(getApplicationContext(), ShowMessage.class);
							Bundle bundle = new Bundle();
							bundle.putParcelable("message", m);
							intent.putExtras(bundle);
							if (Build.VERSION.SDK_INT >= 21) {
								// Set Transition
								@SuppressWarnings("unchecked")
								ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(InboxList.this,
										Pair.create(view.findViewById(R.id.title), "title"),
										Pair.create(view.findViewById(R.id.content), "extract"));

								startActivityForResult(intent, REQUEST_SHOWMESSAGE, options.toBundle());
							} else {
								// No transition
								startActivityForResult(intent, REQUEST_SHOWMESSAGE);
							}

						}

						@Override
						public void onError(int error, String errorMessage) {
							// Nothing
						}
					});
				}

				@Override
				public void onError(int error, String errorMessage) {
					// Nothing
				}
			});
		}
	}

	/**
	 * OnInboxMessageCheckedListener Action done as soon as a message is checked
	 * 
	 * @author Jonathan Salamon/Jean-Vincent D'Adda
	 *
	 */
	private final class OnInboxMessageCheckedListener implements OnMessageCheckedListener {
		@Override
		public void onMessageUnChecked(int position) {
			mCheckedMessagePosition.add(position);
			ActivityCompat.invalidateOptionsMenu(InboxList.this);

		}

		@Override
		public void onMessageChecked(int position) {
			if (mCheckedMessagePosition.size() > 0)
				mCheckedMessagePosition.remove(mCheckedMessagePosition.lastIndexOf(position));
			ActivityCompat.invalidateOptionsMenu(InboxList.this);

		}
	}
}
