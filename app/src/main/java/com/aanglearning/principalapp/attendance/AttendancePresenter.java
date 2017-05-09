package com.aanglearning.principalapp.attendance;

/**
 * Created by Vinay on 21-04-2017.
 */

public interface AttendancePresenter {
    void getClassList(long schoolId);

    void getSectionList(long classId);

    void getAttendance(long sectionId, String date, int session);

    void getTimetable(long sectionId, String dayOfWeek);

    void onDestroy();
}
