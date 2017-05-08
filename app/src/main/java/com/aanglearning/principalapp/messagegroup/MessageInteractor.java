package com.aanglearning.principalapp.messagegroup;

import com.aanglearning.principalapp.model.Message;

import java.util.ArrayList;

/**
 * Created by Vinay on 07-04-2017.
 */

interface MessageInteractor {
    interface OnFinishedListener {
        void onError(String message);

        void onMessageSaved(Message message);

        void onMessageReceived(ArrayList<Message> messages);

        void onFollowupMessagesReceived(ArrayList<Message> messages);
    }

    void saveMessage(Message message, MessageInteractor.OnFinishedListener listener);

    void getMessages(long groupId, MessageInteractor.OnFinishedListener listener);

    void getFollowupMessages(long groupId, long messageId, MessageInteractor.OnFinishedListener listener);
}
