package com.aanglearning.principalapp.homework;

import com.aanglearning.principalapp.model.Clas;
import com.aanglearning.principalapp.model.Homework;
import com.aanglearning.principalapp.model.Section;

import java.util.List;

/**
 * Created by Vinay on 21-04-2017.
 */

interface HomeworkInteractor {
    interface OnFinishedListener {
        void onError(String message);

        void loadOffline(String tableName);

        void onClassReceived(List<Clas> clasList);

        void onSectionReceived(List<Section> sectionList);

        void onHomeworkReceived(List<Homework> homeworks);
    }

    void getClassList(long schoolId, HomeworkInteractor.OnFinishedListener listener);

    void getSectionList(long classId, HomeworkInteractor.OnFinishedListener listener);

    void getHomework(long sectionId, String date, HomeworkInteractor.OnFinishedListener listener);
}
