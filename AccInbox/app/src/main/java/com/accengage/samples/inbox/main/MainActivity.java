package com.accengage.samples.inbox.main;

import android.content.Intent;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;

import com.accengage.samples.inbox.R;
import com.accengage.samples.inbox.base.BaseActivity;
import com.accengage.samples.inbox.messages.MessageActivity;
import com.accengage.samples.inbox.utils.InboxUtil;
import com.ad4screen.sdk.A4S;
import com.ad4screen.sdk.Inbox;
import com.ad4screen.sdk.Message;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity implements ShowMessageListener {

    private static final String TAG = MainActivity.class.getName();
    private static final int SHOW_MESSAGE = 1000;

    private Inbox inbox;
    private MessageInboxAdapter mAdapter;

    private List<Message> messages = new ArrayList<>();

    private A4S.Callback<Inbox> inboxCallback;

    @BindView(R.id.srl_inbox) SwipeRefreshLayout srlInbox;
    @BindView(R.id.rv_messages_inbox) RecyclerView rvMsgInbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            // Check if the previous Activity is MessageActivity
            case SHOW_MESSAGE:

                // Check the resultCode
                if (resultCode != RESULT_OK) {
                    return;
                }

                // Remove the message
                removeMessage(data.getIntExtra(MessageActivity.MESSAGE_POSITION_KEY, -1));
                break;
        }
    }

    private void initView() {
        setNbrInbox(0);
        initPullToRefresh();
        retrieveInbox();
        setupAdapter();
    }

    private void initPullToRefresh() {
        srlInbox.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                A4S.get(MainActivity.this).getInbox(inboxCallback);
            }
        });
    }

    private void retrieveInbox() {
        inboxCallback = new A4S.Callback<Inbox>() {
            @Override
            public void onResult(Inbox result) {
                inbox = result;

                if (inbox == null || inbox.countMessages() == 0) {
                    setNbrInbox(0);
                    srlInbox.setRefreshing(false);
                    return;
                }

                retrieveMessages();
            }

            @Override
            public void onError(int error, String errorMessage) {
                Log.e(TAG, "errorCode : " + error + "errorMsg : " + errorMessage);
            }
        };

        A4S.get(this).getInbox(inboxCallback);
    }

    private void retrieveMessages() {

        final int nbrMessage = inbox.countMessages();

        for(int i = 0; i < nbrMessage; i++) {

            inbox.getMessage(i, new A4S.MessageCallback() {
                @Override
                public void onResult(Message result, int index) {
                    try {
                        // If messages is already fill we replace the older ones
                        messages.set(index, result);
                        mAdapter.notifyItemChanged(index);
                    } catch (Exception e) {
                        // else we add it
                        messages.add(result);
                        mAdapter.notifyItemInserted(index);
                    }

                    if (index + 1 == nbrMessage) {
                        // At the end we stop the anim and set the number in the header
                        srlInbox.setRefreshing(false);
                        setNbrInbox(InboxUtil.getNumberInbox(messages));
                    }
                }

                @Override
                public void onError(int error, String errorMessage) {
                    Log.e(TAG, "errorCode : " + error + "errorMsg : " + errorMessage);
                }
            });
        }
    }

    private void setupAdapter() {
        mAdapter = new MessageInboxAdapter(getApplicationContext(), this,  messages);
        rvMsgInbox.setAdapter(mAdapter);
        rvMsgInbox.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setNbrInbox(int nbr) {
        String n = nbr > 0 ? " (" + nbr +")" : "";
        setTitle(String.format(getString(R.string.inbox), n));
    }

    // Show Message Listener
    @Override
    public void gotToMessageActivity(Message message, final int position) {
        // pass the message to the MessageActivity
        final Intent intent = new Intent(getApplicationContext(), MessageActivity.class);
        intent.putExtra(MessageActivity.MESSAGE_KEY, message);

        // notify the adapter and the server that the message is read
        mAdapter.notifyItemChanged(position);

        A4S.get(this).updateMessages(inbox);

        // update the number in the header
        setNbrInbox(InboxUtil.getNumberInbox(messages));

        // To notify the SDK that the message is displayed
        message.display(getApplicationContext(), new A4S.Callback<Message>() {
            @Override
            public void onResult(Message message) {
                startActivityForResult(intent, SHOW_MESSAGE);
            }

            @Override
            public void onError(int i, String s) {
                Log.e(TAG, s);
            }
        });
    }

    public void removeMessage(int position) {

        if (position < 0) {
            Log.d(TAG, "Invalid position");
            return;
        }

        // Remove it in the message list
        messages.get(position).setArchived(true);
        messages.remove(position);

        // Update messages display
        mAdapter.notifyItemRemoved(position);

        // Notify the SDK
        A4S.get(this).updateMessages(inbox);

        setNbrInbox(InboxUtil.getNumberInbox(messages));
    }
}
