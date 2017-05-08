package com.aanglearning.principalapp.messagegroup;

import com.aanglearning.principalapp.model.Message;

/**
 * Created by Vinay on 07-04-2017.
 */

interface MessagePresenter {
    void saveMessage(Message message);

    void getMessages(long groupId);

    void getFollowupMessages(long groupId, long messageId);

    void onDestroy();
}
