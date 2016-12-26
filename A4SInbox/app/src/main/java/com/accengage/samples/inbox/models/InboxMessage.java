package com.accengage.samples.inbox.models;

import java.util.Date;

import android.graphics.drawable.Drawable;

import com.ad4screen.sdk.Message;
/**
 * InboxMessage
 * Specialized model from com.ad4screen.sdk.Message 
 * in order to fit specific needs
 * @author Jonathan Salamon/Jean-Vincent D'Adda
 *
 */
public class InboxMessage {

	private Message mMessage;

	private boolean mIsSelected;
	private Drawable mMessageIconDrawable;

	public String getTitle() {
		if (mMessage == null)
			return "";
		return mMessage.getTitle();
	}

	public String getContent() {
		if (mMessage == null)
			return "";
		return mMessage.getText();
	}

	public boolean isRead() {
		if (mMessage == null)
			return false;
		return mMessage.isRead();
	}
	
	public boolean isArchived() {
		if (mMessage == null)
			return false;
		return mMessage.isArchived();
	}


	public boolean isSelected() {
		return mIsSelected;
	}

	public void setSelected(boolean isSelected) {
		mIsSelected = isSelected;
	}
	
	public boolean isDownloaded() {
		if (mMessage == null)
			return false;
		return mMessage.isDownloaded();
	}
	
	public boolean isOutdated() {
		if (mMessage == null)
			return false;
		return mMessage.isOutdated();
	}

	public Date getSendDate() {
		if (mMessage == null)
			return new Date();
		return mMessage.getSendDate();
	}

	public Message getMessage() {
		return mMessage;
	}

	public void setMessage(Message message) {
		mMessage = message;
	}

	public Drawable getMessageIconDrawable() {
		return mMessageIconDrawable;
	}

	public void setMessageIconDrawable(Drawable messageIconDrawable) {
		mMessageIconDrawable = messageIconDrawable;
	}
}
