package com.aanglearning.principalapp.chathome;

import com.aanglearning.principalapp.model.Chat;

import java.util.List;

/**
 * Created by Vinay on 28-04-2017.
 */

interface ChatsView {
    void showProgress();

    void hideProgess();

    void showError(String message);

    void setGroups(List<Chat> chats);
}
