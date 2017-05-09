package com.aanglearning.principalapp.newchat;

import com.aanglearning.principalapp.model.Chat;

/**
 * Created by Vinay on 28-04-2017.
 */

public interface NewChatPresenter {
    void getClassList(long schoolId);

    void getSectionList(long classId);

    void getStudentList(long sectionId);

    void saveChat(Chat chat);

    void onDestroy();
}
