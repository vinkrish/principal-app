package com.aanglearning.principalapp.newchat;

import com.aanglearning.principalapp.model.Chat;
import com.aanglearning.principalapp.model.Clas;
import com.aanglearning.principalapp.model.Section;
import com.aanglearning.principalapp.model.Student;

import java.util.List;

/**
 * Created by Vinay on 28-04-2017.
 */

public interface NewChatView {
    void showProgress();

    void hideProgess();

    void showError(String message);

    void showClass(List<Clas> clasList);

    void showSection(List<Section> sectionList);

    void showStudent(List<Student> studentList);

    void chatSaved(Chat chat);
}
