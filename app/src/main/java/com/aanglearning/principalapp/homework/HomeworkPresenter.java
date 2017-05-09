package com.aanglearning.principalapp.homework;

/**
 * Created by Vinay on 21-04-2017.
 */

interface HomeworkPresenter {
    void getClassList(long schoolId);

    void getSectionList(long classId);

    void getHomework(long sectionId, String date);

    void onDestroy();
}
