package com.aanglearning.principalapp.messagegroup;

import com.aanglearning.principalapp.model.DeletedMessage;
import com.aanglearning.principalapp.model.Message;

/**
 * Created by Vinay on 07-04-2017.
 */

interface MessagePresenter {
    void saveMessage(Message message);

    void getRecentMessages(long groupId, long messageId);

    void getMessages(long groupId);

    void deleteMessage(DeletedMessage deletedMessage);

    void getRecentDeletedMessages(long groupId, long id);

    void getDeletedMessages(long groupId);

    void onDestroy();
}
