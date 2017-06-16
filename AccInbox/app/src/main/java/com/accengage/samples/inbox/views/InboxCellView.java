package com.accengage.samples.inbox.views;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.accengage.samples.inbox.R;
import com.accengage.samples.inbox.adapters.MessagesAdapter.OnMessageCheckedListener;
import com.accengage.samples.inbox.models.InboxMessage;
import com.ad4screen.sdk.Message.onIconDownloadedListener;
/**
 * InboxCellView
 * View dealing with display of a specific message
 * @author Jonathan Salamon/Jean-Vincent D'Adda
 *
 */
public class InboxCellView extends LinearLayout {

	private TextView mHeadlineView;
	private TextView mContentView;
	private TextView mDateView;
	private ImageView mSquareView;
	private View mContainer;
	private View mSelectionIndicator;
	private TextView mCategoryView;

	private int mPosition;

	private Animation mAnimationFlipToMiddle;
	private Animation mAnimationFlipFromMiddle;
	private OnMessageCheckedListener mOnMessageCheckedListener;

	private InboxMessage mMessage = new InboxMessage();

	//
	// CONSTRUCTOR
	//

	public InboxCellView(Context context) {
		super(context);

		initializeLayout();
		initializeAnimation();

		mSquareView.setOnClickListener(new OnCheckBoxClickListener());
	}

	//
	// API
	//

	public void setData(final InboxMessage message, final int position, final OnMessageCheckedListener onMessageCheckedListener) {
		mMessage = message;
		mPosition = position;
		mOnMessageCheckedListener = onMessageCheckedListener;

		updateUI();
		updateBackground(mMessage);
		updateIconImage();
		downloadIconIfNeeded(mMessage);
	}

	//
	// PRIVATE METHODS
	//

	private void initializeLayout() {
		LayoutInflater.from(getContext()).inflate(R.layout.list_row, this);

		mHeadlineView = (TextView) findViewById(R.id.title);
		mContentView = (TextView) findViewById(R.id.content);
		mDateView = (TextView) findViewById(R.id.date);
		mSquareView = (ImageView) findViewById(R.id.icon);
		mContainer = findViewById(R.id.container);
		mSelectionIndicator = findViewById(R.id.selectedIndicator);
		mCategoryView = (TextView) findViewById(R.id.category);
	}

	private void initializeAnimation() {
		FlipAnimationListener animationListener = new FlipAnimationListener();

		mAnimationFlipToMiddle = AnimationUtils.loadAnimation(getContext(), R.anim.flip_to_middle);
		mAnimationFlipToMiddle.setAnimationListener(animationListener);
		mAnimationFlipFromMiddle = AnimationUtils.loadAnimation(getContext(), R.anim.flip_from_middle);
		mAnimationFlipFromMiddle.setAnimationListener(animationListener);
	}

	@SuppressWarnings("deprecation")
	private void updateIconImage() {
		if (mMessage.isSelected()) {
			mSquareView.setBackgroundResource(R.drawable.circle_grey);
			mSquareView.setImageResource(R.drawable.ic_check_wht_24dp);
		} else {
			mSquareView.setImageDrawable(null);
			if (mMessage.getMessageIconDrawable() == null) {
				mSquareView.setBackgroundResource(R.drawable.circle_grey);
			} else {
				mSquareView.setBackgroundDrawable(mMessage.getMessageIconDrawable());
			}
		}
	}

	private void downloadIconIfNeeded(final InboxMessage message) {
		if(mMessage.getMessage() == null) return;
		if (message.getMessageIconDrawable() == null) {
			message.getMessage().getIcon(new onIconDownloadedListener() {

				@Override
				public void onIconDownloaded(Bitmap image) {
					if (image != null) {
						message.setMessageIconDrawable(new BitmapDrawable(getResources(), image));
						updateIconImage();
					}
				}
			});
		}
	}

	private void updateUI() {
		if (mMessage == null) {
			mHeadlineView.setText("");
			mContentView.setText("");
			mDateView.setText("");
			mCategoryView.setVisibility(View.GONE);
			return;
		}

		String title = mMessage.getTitle();
		if (title != null) {
			mHeadlineView.setText(title);
		} else {
			mHeadlineView.setText("");
		}

		String content = mMessage.getContent();
		if (content != null) {
			mContentView.setText(content);
		} else {
			mContentView.setText("");
		}

		Date sendDate = mMessage.getSendDate();
		if (sendDate != null) {
			Calendar sendCalendar = Calendar.getInstance();
			sendCalendar.setTime(sendDate);
			mDateView.setText(getFormattedDate(Calendar.getInstance(), sendCalendar));
		} else {
			mDateView.setText("");
		}

		if(mMessage.getMessage() == null || mMessage.getMessage().getCategory() == null) {
			mCategoryView.setVisibility(View.GONE);
			return;
		}
		String category = mMessage.getMessage().getCategory();
		if (!TextUtils.isEmpty(category)) {
			mCategoryView.setText(category);
			mCategoryView.setVisibility(View.VISIBLE);
		} else {
			mCategoryView.setVisibility(View.GONE);
		}
	}

	private void updateBackground(InboxMessage message) {
		if (mPosition % 2 == 0) {
			mContainer.setBackgroundColor(Color.parseColor("#F5F5F5"));
		} else {
			mContainer.setBackgroundColor(Color.WHITE);
		}

		mSelectionIndicator.setBackgroundColor(Color.parseColor("#007AFF"));
		mSelectionIndicator.setVisibility(View.VISIBLE);

		if (message.isRead()) {
			mSelectionIndicator.setVisibility(View.INVISIBLE);
		}
		if(message.isArchived()) {
			mSelectionIndicator.setBackgroundColor(Color.RED);
			mSelectionIndicator.setVisibility(View.VISIBLE);
		}

		if(message.isOutdated()) {
			mSelectionIndicator.setBackgroundColor(Color.YELLOW);
			mSelectionIndicator.setVisibility(View.VISIBLE);
		}
	}

	private void notifyMessageChecked(boolean isSelected) {
		if (mOnMessageCheckedListener != null) {
			if (!isSelected) {
				mOnMessageCheckedListener.onMessageChecked(mPosition);
			} else {
				mOnMessageCheckedListener.onMessageUnChecked(mPosition);
			}
		}
	}

	private String getFormattedDate(Calendar now, Calendar date) {
		long diffDays = daysBetween(date, now);

		if (diffDays == 0) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
			return dateFormat.format(date.getTime());
		} else if (diffDays == 1) {
			return getContext().getString(R.string.inbox_date_yesterday);
		} else if (diffDays < 7) {
			return getContext().getString(R.string.inbox_date_days_ago, diffDays);
		} else if (diffDays < 15) {
			return getContext().getString(R.string.inbox_date_last_week);
		} else if (diffDays < 30) {
			return getContext().getString(R.string.inbox_date_weeks_ago, diffDays / 7);
		} else if (diffDays < 60) {
			return getContext().getString(R.string.inbox_date_last_month);
		} else {
			return getContext().getString(R.string.inbox_date_months_ago);
		}
	}

	public static long daysBetween(Calendar startDate, Calendar endDate) {
		if (endDate.before(startDate))
			return daysBetween(endDate, startDate);

		if (startDate.get(Calendar.YEAR) == endDate.get(Calendar.YEAR)) {
			return endDate.get(Calendar.DAY_OF_YEAR) - startDate.get(Calendar.DAY_OF_YEAR);
		}

		int daysInYear = startDate.getActualMaximum(Calendar.DAY_OF_YEAR);
		startDate.add(Calendar.YEAR, 1);
		return daysBetween(startDate, endDate) + daysInYear;
	}

	//
	// INNER CLASS
	//

	private class OnCheckBoxClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			mMessage.setSelected(!mMessage.isSelected());
			mSquareView.clearAnimation();
			mSquareView.setAnimation(mAnimationFlipToMiddle);
			mSquareView.startAnimation(mAnimationFlipToMiddle);
		}
	}

	private class FlipAnimationListener implements AnimationListener {

		@Override
		public void onAnimationEnd(Animation animation) {
			if (animation == mAnimationFlipToMiddle) {
				updateIconImage();

				mSquareView.clearAnimation();
				mSquareView.setAnimation(mAnimationFlipFromMiddle);
				mSquareView.startAnimation(mAnimationFlipFromMiddle);
			} else {
				updateBackground(mMessage);
				notifyMessageChecked(mMessage.isSelected());
			}
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			// Nothing
		}

		@Override
		public void onAnimationStart(Animation animation) {
			// Nothing
		}
	}

}
