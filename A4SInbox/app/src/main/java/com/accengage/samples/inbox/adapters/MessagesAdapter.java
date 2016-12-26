package com.accengage.samples.inbox.adapters;

import java.util.ArrayList;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.accengage.samples.inbox.models.InboxMessage;
import com.accengage.samples.inbox.views.InboxCellView;
import com.ad4screen.sdk.A4S.MessageCallback;
import com.ad4screen.sdk.Inbox;
import com.ad4screen.sdk.Message;
/**
 * MessagesAdapter
 * @author Jonathan Salamon/Jean-Vincent D'Adda
 *
 */
public class MessagesAdapter extends BaseAdapter {

	public interface OnMessageCheckedListener {
		public void onMessageChecked(int position);

		public void onMessageUnChecked(int position);
	}

	private OnMessageCheckedListener mListener;
	private ArrayList<InboxMessage> mMessages;
	private Inbox mInbox;

	public MessagesAdapter(Inbox inbox,ArrayList<InboxMessage> messages) {
		mMessages = messages;
		mInbox = inbox;
	}

	public void setOnMessageCheckedListener(OnMessageCheckedListener listener) {
		mListener = listener;
	}

	@Override
	public int getCount() {
		return mMessages.size();
	}

	@Override
	public InboxMessage getItem(int position) {
		loadMessage(position);
		return mMessages.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		InboxCellView inboxCellView;

		if (convertView == null) {
			inboxCellView = new InboxCellView(parent.getContext());
		} else {
			inboxCellView = (InboxCellView) convertView;
		}
		inboxCellView.setData(getItem(position), position, mListener);

		return inboxCellView;
	}

	private void loadMessage(int index) {
		NewMessageCallback callback = new NewMessageCallback();
		mInbox.getMessage(index, callback);
	}

	//
	// INNER CLASS
	//

	private final class NewMessageCallback implements MessageCallback {

		private NewMessageCallback() {

		}
		@Override
		public void onResult(Message msg, int index) {
			InboxMessage message = mMessages.get(index);
			message.setMessage(msg);
			message.setSelected(false);
			notifyDataSetChanged();
		}

		@Override
		public void onError(int error, String errorMessage) {
			//Nothing
		}
	}
}