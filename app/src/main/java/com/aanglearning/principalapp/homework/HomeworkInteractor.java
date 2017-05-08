package com.aanglearning.principalapp.homework;

import com.aanglearning.principalapp.model.Clas;
import com.aanglearning.principalapp.model.Homework;
import com.aanglearning.principalapp.model.Section;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vinay on 21-04-2017.
 */

public interface HomeworkInteractor {
    interface OnFinishedListener {
        void onError(String message);

        void onClassReceived(List<Clas> clasList);

        void onSectionReceived(List<Section> sectionList);

        void onHomeworkReceived(List<Homework> homeworks);

        void onHomeworkSaved(Homework homework);

        void onHomeworkUpdated();

        void onHomeworkDeleted();
    }

    void getClassList(long teacherId, HomeworkInteractor.OnFinishedListener listener);

    void getSectionList(long classId, long teacherId, HomeworkInteractor.OnFinishedListener listener);

    void getHomework(long sectionId, String date, HomeworkInteractor.OnFinishedListener listener);

    void saveHomework(Homework homework, HomeworkInteractor.OnFinishedListener listener);

    void updateHomework(Homework homework, HomeworkInteractor.OnFinishedListener listener);

    void deleteHomework(ArrayList<Homework> homeworks, HomeworkInteractor.OnFinishedListener listener);
}
