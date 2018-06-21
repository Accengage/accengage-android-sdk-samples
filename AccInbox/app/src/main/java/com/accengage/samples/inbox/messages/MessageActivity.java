package com.accengage.samples.inbox.messages;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.accengage.samples.inbox.R;
import com.accengage.samples.inbox.base.BaseActivity;
import com.ad4screen.sdk.Message;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageActivity extends BaseActivity {

    public static final String MESSAGE_KEY = "message";
    public static final String MESSAGE_POSITION_KEY = "position";

    private static final String TAG = MessageActivity.class.getName();

    private int messagePosition;

    private Message message;
    private List<Message.Button> buttons;

    @BindView(R.id.iv_icon) ImageView ivIcon;
    @BindView(R.id.tv_title) TextView tvTitle;
    @BindView(R.id.tv_sender) TextView tvSender;
    @BindView(R.id.tv_date) TextView tvDate;
    @BindView(R.id.tv_summary) TextView tvSummary;
    @BindView(R.id.tv_category) TextView tvCategory;
    @BindView(R.id.tv_content) TextView tvContent;
    @BindView(R.id.wv_message) WebView wvMessage;
    @BindView(R.id.rv_buttons) RecyclerView rvButtons;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Display a pop up on click delete
        if (item.getItemId() == R.id.action_bar_delete) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.message)
                    .setMessage(R.string.delete_message)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            intent.putExtra(MESSAGE_POSITION_KEY, messagePosition);
                            setResult(RESULT_OK, intent);
                            message.setArchived(true);
                            finish();
                        }
                    })
                    .setNegativeButton(R.string.no, null)
                    .setCancelable(false)
                    .show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        retrieveMessageObject();
        setupAdapter();
        displayContent();
    }

    private void retrieveMessageObject() {
        // Retrieve Message & position
        Bundle extras = getIntent().getExtras();
        message = extras != null ? (Message) extras.getParcelable(MESSAGE_KEY) : null;
        messagePosition = extras != null ? extras.getInt(MESSAGE_POSITION_KEY) : -1;

        // Retrieve Buttons
        int buttonNbr = 0;
        buttons = new ArrayList<>();

        if (message != null && (buttonNbr = message.countButtons()) <= 0) {
            Log.d(TAG, "This message haven't buttons");
            return;
        }

        for (int i = 0; i < buttonNbr; i++) {
            buttons.add(message.getButton(i));
        }
    }

    private void setupAdapter() {
        ButtonMessageAdapter mAdapter = new ButtonMessageAdapter(getApplicationContext(), buttons);
        rvButtons.setAdapter(mAdapter);
        rvButtons.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    private void displayContent() {
        if (message == null) {
            Log.e(TAG, "Message is null");
            return;
        }

        message.getIcon(new Message.onIconDownloadedListener() {
            @Override
            public void onIconDownloaded(Bitmap bitmap) {
                ivIcon.setImageBitmap(bitmap);
            }
        });
        tvTitle.setText(message.getTitle());
        tvSender.setText(String.format(Locale.FRANCE, "Expéditeur: %s", message.getSender()));
        tvDate.setText(String.format(Locale.FRANCE, "Reçu le : %s", SimpleDateFormat.getDateTimeInstance().format(message.getSendDate())));
        tvSummary.setText(message.getText());

        displayCategory();
        displayBody();
    }

    private void displayCategory() {
        if (message.getCategory() != null && !message.getCategory().isEmpty()) {
            tvCategory.setText(message.getCategory());
        } else {
            tvCategory.setVisibility(View.INVISIBLE);
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void displayBody() {
        // There are also the following types : System, Event, Url, Push
        switch (message.getContentType()) {

            case Web:
                tvContent.setVisibility(View.GONE);
                wvMessage.getSettings().setJavaScriptEnabled(true);
                wvMessage.loadUrl(message.getBody());
                break;

            case Text:
                wvMessage.setVisibility(View.GONE);
                tvContent.setText(message.getBody());
                break;
        }
    }
}
