package com.aanglearning.principalapp.homework;

import com.aanglearning.principalapp.model.Homework;

import java.util.ArrayList;

/**
 * Created by Vinay on 21-04-2017.
 */

public interface HomeworkPresenter {
    void getClassList(long teacherId);

    void getSectionList(long classId, long teacherId);

    void getHomework(long sectionId, String date);

    void saveHomework(Homework homework);

    void updateHomework(Homework homework);

    void deleteHomework(ArrayList<Homework> homeworks);

    void onDestroy();
}
