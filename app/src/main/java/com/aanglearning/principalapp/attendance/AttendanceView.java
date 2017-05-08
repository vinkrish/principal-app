package com.aanglearning.principalapp.attendance;

import com.aanglearning.principalapp.model.Clas;
import com.aanglearning.principalapp.model.Section;
import com.aanglearning.principalapp.model.Timetable;

import java.util.List;

/**
 * Created by Vinay on 21-04-2017.
 */

interface AttendanceView {
    void showProgress();

    void hideProgress();

    void showError(String message);

    void showClass(List<Clas> clasList);

    void showSection(List<Section> sectionList);

    void showTimetable(List<Timetable> timetableList);

    void showAttendance(AttendanceSet attendanceSet);

    void attendanceSaved();

    void attendanceDeleted();
}
