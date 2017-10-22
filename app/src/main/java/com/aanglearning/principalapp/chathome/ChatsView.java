package com.aanglearning.principalapp.chathome;

import com.aanglearning.principalapp.model.Chat;

import java.util.List;

/**
 * Created by Vinay on 28-04-2017.
 */

interface ChatsView {
    void showProgress();

    void hideProgress();

    void showError(String message);

    void setChats(List<Chat> chats);

    void onChatDeleted();
}
