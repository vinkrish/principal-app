package com.aanglearning.principalapp.messagerecipient;

import com.aanglearning.principalapp.model.MessageRecipient;

import java.util.List;

/**
 * Created by Vinay on 25-08-2017.
 */

interface MessageRecipientInteractor {
    interface OnFinishedListener {
        void onError(String message);

        void onMessageRecipientReceived(List<MessageRecipient> messageRecipient);
    }

    void getMessageRecipient(long groupId, long groupMessageId, OnFinishedListener listener);
}
