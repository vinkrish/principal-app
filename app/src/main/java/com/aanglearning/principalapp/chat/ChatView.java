package com.aanglearning.principalapp.chat;

import com.aanglearning.principalapp.model.Message;

import java.util.ArrayList;

/**
 * Created by Vinay on 28-04-2017.
 */

public interface ChatView {
    void showProgress();

    void hideProgress();

    void showError(String message);

    void onMessageSaved(Message message);

    void showMessages(ArrayList<Message> messages);

    void showFollowupMessages(ArrayList<Message> messages);
}
