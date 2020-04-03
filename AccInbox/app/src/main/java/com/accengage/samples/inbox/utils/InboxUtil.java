package com.accengage.samples.inbox.utils;

import com.ad4screen.sdk.Message;

import java.util.List;

public class InboxUtil {

    public static int getNumberInbox(List<Message> messages) {

        int nbr = 0;

        for (Message message : messages) {
            if (!message.isRead()) {
                nbr++;
            }
        }

        return nbr;
    }

}
