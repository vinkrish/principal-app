package com.aanglearning.principalapp.newchat;

import com.aanglearning.principalapp.model.Chat;
import com.aanglearning.principalapp.model.Clas;
import com.aanglearning.principalapp.model.Section;
import com.aanglearning.principalapp.model.Student;

import java.util.List;

/**
 * Created by Vinay on 28-04-2017.
 */

interface NewChatInteractor {

    interface OnFinishedListener {
        void onError(String message);

        void onClasReceived(List<Clas> clasList);

        void onSectionReceived(List<Section> sectionList);

        void onStudentReceived(List<Student> studentList);

        void onChatSaved(Chat chat);
    }

    void getClassList(long schoolId, NewChatInteractor.OnFinishedListener listener);

    void getSectionList(long classId, NewChatInteractor.OnFinishedListener listener);

    void getStudentList(long sectionId, NewChatInteractor.OnFinishedListener listener);

    void saveChat(Chat chat, NewChatInteractor.OnFinishedListener listener);
}
