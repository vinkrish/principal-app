package com.aanglearning.principalapp.timetable;

import com.aanglearning.principalapp.model.Clas;
import com.aanglearning.principalapp.model.Section;
import com.aanglearning.principalapp.model.Timetable;

import java.util.List;

/**
 * Created by Vinay on 13-06-2017.
 */

interface TimetableInteractor {
    interface OnFinishedListener {
        void onError(String message);

        void onClassReceived(List<Clas> clasList);

        void onSectionReceived(List<Section> sectionList);

        void onTimetableReceived(List<Timetable> timetableList);
    }
    void getClassList(long schoolId, TimetableInteractor.OnFinishedListener listener);

    void getSectionList(long classId, TimetableInteractor.OnFinishedListener listener);

    void getTimetable(long sectionId, TimetableInteractor.OnFinishedListener listener);
}
