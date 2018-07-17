package com.accengage.samples.inbox.main;

import com.ad4screen.sdk.Message;

public interface ShowMessageListener {
    void gotToMessageActivity(Message message, int position);
}
