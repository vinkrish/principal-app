package com.aanglearning.principalapp.attendance;

import com.aanglearning.principalapp.model.Attendance;
import com.aanglearning.principalapp.model.Clas;
import com.aanglearning.principalapp.model.Section;
import com.aanglearning.principalapp.model.Timetable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vinay on 21-04-2017.
 */

public class AttendancePresenterImpl implements AttendancePresenter,
        AttendanceInteractor.OnFinishedListener {

    private AttendanceView mView;
    private AttendanceInteractor mInteractor;

    AttendancePresenterImpl(AttendanceView view, AttendanceInteractor interactor) {
        mView = view;
        mInteractor = interactor;
    }

    @Override
    public void getClassList(long teacherId) {
        if (mView != null) {
            mView.showProgress();
            mInteractor.getClassList(teacherId, this);
        }
    }

    @Override
    public void getSectionList(long classId, long teacherId) {
        if (mView != null) {
            mView.showProgress();
            mInteractor.getSectionList(classId, teacherId, this);
        }
    }

    @Override
    public void getAttendance(long sectionId, String date, int session) {
        if (mView != null) {
            mView.showProgress();
            mInteractor.getAttendance(sectionId, date, session, this);
        }
    }

    @Override
    public void getTimetable(long sectionId, String dayOfWeek) {
        if (mView != null) {
            mView.showProgress();
            mInteractor.getTimetable(sectionId, dayOfWeek, this);
        }
    }

    @Override
    public void saveAttendance(ArrayList<Attendance> attendances) {
        if (mView != null) {
            mView.showProgress();
            mInteractor.saveAttendance(attendances, this);
        }
    }

    @Override
    public void deleteAttendance(ArrayList<Attendance> attendances) {
        if (mView != null) {
            mView.showProgress();
            mInteractor.deleteAttendance(attendances, this);
        }
    }

    @Override
    public void onDestroy() {
        mView = null;
    }

    @Override
    public void onError(String message) {
        if (mView != null) {
            mView.hideProgress();
            mView.showError(message);
        }
    }

    @Override
    public void onClassReceived(List<Clas> clasList) {
        if (mView != null) {
            mView.showClass(clasList);
            mView.hideProgress();
        }
    }

    @Override
    public void onSectionReceived(List<Section> sectionList) {
        if (mView != null) {
            mView.showSection(sectionList);
            mView.hideProgress();
        }
    }

    @Override
    public void onTimetableReceived(List<Timetable> timetables) {
        if (mView != null) {
            mView.showTimetable(timetables);
            mView.hideProgress();
        }
    }

    @Override
    public void onAttendanceReceived(AttendanceSet attendanceSet) {
        if (mView != null) {
            mView.showAttendance(attendanceSet);
            mView.hideProgress();
        }
    }

    @Override
    public void onAttendanceSaved() {
        if (mView != null) {
            mView.attendanceSaved();
            mView.hideProgress();
        }
    }

    @Override
    public void onAttendanceDeleted() {
        if (mView != null) {
            mView.attendanceDeleted();
            mView.hideProgress();
        }
    }
}
