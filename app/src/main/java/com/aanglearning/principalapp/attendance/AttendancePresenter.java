package com.aanglearning.principalapp.attendance;

import com.aanglearning.principalapp.model.Attendance;

import java.util.ArrayList;

/**
 * Created by Vinay on 21-04-2017.
 */

public interface AttendancePresenter {
    void getClassList(long teacherId);

    void getSectionList(long classId, long teacherId);

    void getAttendance(long sectionId, String date, int session);

    void getTimetable(long sectionId, String dayOfWeek);

    void saveAttendance(ArrayList<Attendance> attendances);

    void deleteAttendance(ArrayList<Attendance> attendances);

    void onDestroy();
}
