package com.aanglearning.principalapp.chat;

import com.aanglearning.principalapp.model.Message;

/**
 * Created by Vinay on 28-04-2017.
 */

interface ChatPresenter {
    void saveMessage(Message message);

    void getMessages(String senderRole, long senderId, String recipientRole, long recipeintId);

    void getFollowupMessages(String senderRole, long senderId, String recipientRole, long recipeintId, long messageId);

    void onDestroy();
}
