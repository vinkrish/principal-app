package com.aanglearning.principalapp.attendance;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.aanglearning.principalapp.R;
import com.aanglearning.principalapp.dao.AttendanceDao;
import com.aanglearning.principalapp.dao.ClassDao;
import com.aanglearning.principalapp.dao.SectionDao;
import com.aanglearning.principalapp.dao.TeacherDao;
import com.aanglearning.principalapp.dao.TimetableDao;
import com.aanglearning.principalapp.model.Attendance;
import com.aanglearning.principalapp.model.Clas;
import com.aanglearning.principalapp.model.Section;
import com.aanglearning.principalapp.model.Timetable;
import com.aanglearning.principalapp.util.Conversion;
import com.aanglearning.principalapp.util.DatePickerFragment;
import com.aanglearning.principalapp.util.DateUtil;
import com.aanglearning.principalapp.util.DividerItemDecoration;
import com.aanglearning.principalapp.util.NetworkUtil;

import org.joda.time.LocalDate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AttendanceActivity extends AppCompatActivity implements AttendanceView,
        AdapterView.OnItemSelectedListener {
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.coordinatorLayout) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.refreshLayout) SwipeRefreshLayout refreshLayout;
    @BindView(R.id.spinner_class) Spinner classSpinner;
    @BindView(R.id.spinner_section) Spinner sectionSpinner;
    @BindView(R.id.section_layout) LinearLayout sectionLayout;
    @BindView(R.id.date_tv) TextView dateView;
    @BindView(R.id.session_spinner) Spinner sessionSpinner;
    @BindView(R.id.session_layout) LinearLayout sessionLayout;
    @BindView(R.id.period_spinner) Spinner periodSpinner;
    @BindView(R.id.period_layout) LinearLayout periodLayout;
    @BindView(R.id.absentees_recycler_view) RecyclerView absenteesRecycler;
    @BindView(R.id.no_attendance) LinearLayout noAttendance;

    private AttendancePresenter presenter;
    private String attendanceDate;
    private AttendanceAdapter attendanceAdapter;

    private String[] days = {"", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new AttendancePresenterImpl(this, new AttendanceInteractorImpl());
        setDefaultDate();

        setupRecyclerView();

        showSession();

        if(NetworkUtil.isNetworkAvailable(this)) {
            presenter.getClassList(TeacherDao.getTeacher().getSchoolId());
        } else {
            showOfflineClass();
        }
    }

    private void setupRecyclerView() {
        absenteesRecycler.setLayoutManager(new LinearLayoutManager(this));
        absenteesRecycler.setNestedScrollingEnabled(false);
        absenteesRecycler.setItemAnimator(new DefaultItemAnimator());
        absenteesRecycler.addItemDecoration(new DividerItemDecoration(this));

        attendanceAdapter = new AttendanceAdapter(new ArrayList<Attendance>(0));
        absenteesRecycler.setAdapter(attendanceAdapter);

        refreshLayout.setColorSchemeColors(
                ContextCompat.getColor(this, R.color.colorPrimary),
                ContextCompat.getColor(this, R.color.colorAccent),
                ContextCompat.getColor(this, R.color.colorPrimaryDark)
        );

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(NetworkUtil.isNetworkAvailable(AttendanceActivity.this)) {
                    presenter.getClassList(TeacherDao.getTeacher().getSchoolId());
                }
            }
        });
    }

    private void showSnackbar(String message) {
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showError(String message) {
        refreshLayout.setRefreshing(false);
        showSnackbar(message);
    }

    @Override
    public void showOffline(String tableName) {
        switch (tableName){
            case "class":
                showOfflineClass();
                break;
            case "section":
                showOfflineSection();
                break;
            case "attendance":
                showOfflineAttendance();
                break;
            case "timetable":
                showOfflineTimetable();
                break;
            default:
                break;
        }
    }

    @Override
    public void showClass(List<Clas> clasList) {
        ArrayAdapter<Clas> adapter = new
                ArrayAdapter<>(this, android.R.layout.simple_spinner_item, clasList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classSpinner.setAdapter(adapter);
        classSpinner.setOnItemSelectedListener(this);
        backupClass(clasList);
    }

    private void backupClass(final List<Clas> classList) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ClassDao.delete(TeacherDao.getTeacher().getSchoolId());
                ClassDao.insert(classList);
            }
        }).start();
    }

    private void showOfflineClass() {
        List<Clas> clasList = ClassDao.getClassList(TeacherDao.getTeacher().getSchoolId());
        ArrayAdapter<Clas> adapter = new
                ArrayAdapter<>(this, android.R.layout.simple_spinner_item, clasList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classSpinner.setAdapter(adapter);
        classSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void showSection(List<Section> sectionList) {
        setSectionAdapter(sectionList);
        backupSection(sectionList);
    }

    private void backupSection(final List<Section> sectionList) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SectionDao.delete(((Clas) classSpinner.getSelectedItem()).getId());
                SectionDao.insert(sectionList);
            }
        }).start();
    }

    private void showOfflineSection() {
        List<Section> sectionList = SectionDao.getSectionList(((Clas) classSpinner.getSelectedItem()).getId());
        setSectionAdapter(sectionList);
    }

    private void setSectionAdapter(List<Section> sectionList) {
        ArrayAdapter<Section> adapter = new
                ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sectionList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sectionSpinner.setAdapter(adapter);
        sectionSpinner.setOnItemSelectedListener(this);
        if(sectionList.size() == 1 && sectionList.get(0).getSectionName().equals("none")) {
            sectionLayout.setVisibility(View.INVISIBLE);
            sectionLayout.setLayoutParams(new LinearLayout.LayoutParams(0,0));
        } else {
            sectionLayout.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            int px = Conversion.dpToPx(10, getApplicationContext());
            sectionLayout.setPadding(px, px, px, px);
            sectionLayout.setLayoutParams(params);
        }
    }

    private void showSession() {
        String[] sessions = {"Morning", "Afternoon"};
        ArrayAdapter<String> adapter = new
                ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Arrays.asList(sessions));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sessionSpinner.setAdapter(adapter);
        sessionSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void showTimetable(List<Timetable> timetableList) {
        ArrayAdapter<Timetable> adapter = new
                ArrayAdapter<>(this, android.R.layout.simple_spinner_item, timetableList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        periodSpinner.setAdapter(adapter);
        periodSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void showAttendance(AttendanceSet attendanceSet) {
        refreshLayout.setRefreshing(false);
        if (attendanceSet.getAttendanceList().size() == 0) {
            noAttendance.setVisibility(View.VISIBLE);
        } else {
            noAttendance.setVisibility(View.INVISIBLE);
            attendanceAdapter.setDataSet(attendanceSet.getAttendanceList());
            backupAttendance(attendanceSet.getAttendanceList());
        }
    }

    private void backupAttendance(final List<Attendance> attendanceList) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String attendanceType = ((Clas) classSpinner.getSelectedItem()).getAttendanceType();
                switch (attendanceType) {
                    case "Daily":
                        AttendanceDao.delete(((Section) sectionSpinner.getSelectedItem()).getId(), attendanceDate, 0);
                        break;
                    case "Session":
                        AttendanceDao.delete(((Section) sectionSpinner.getSelectedItem()).getId(),
                                attendanceDate,  sessionSpinner.getSelectedItem().equals("Morning") ? 0 : 1);
                        break;
                    case "Period":
                        AttendanceDao.delete(((Section) sectionSpinner.getSelectedItem()).getId(),
                                attendanceDate, ((Timetable) periodSpinner.getSelectedItem()).getPeriodNo());
                    break;
                    default:
                        break;
                }
                AttendanceDao.insert(attendanceList);
            }
        }).start();
    }

    private void showOfflineAttendance() {
        String attendanceType = ((Clas) classSpinner.getSelectedItem()).getAttendanceType();
        switch (attendanceType) {
            case "Daily":
                List<Attendance> attendanceList1 = AttendanceDao.getAttendance(
                            ((Section) sectionSpinner.getSelectedItem()).getId(), attendanceDate, 0);
                viewAttendance(attendanceList1);
                break;
            case "Session":
                List<Attendance> attendanceList2 = AttendanceDao.getAttendance(
                        ((Section) sectionSpinner.getSelectedItem()).getId(), attendanceDate,
                        sessionSpinner.getSelectedItem().equals("Morning") ? 0 : 1);
                viewAttendance(attendanceList2);
                break;
            case "Period":
                showOfflineTimetable();
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        switch (parent.getId()) {
            case R.id.spinner_class:
                if(NetworkUtil.isNetworkAvailable(this)) {
                    presenter.getSectionList(((Clas) classSpinner.getSelectedItem()).getId());
                } else {
                    showOfflineSection();
                }
                break;
            case R.id.spinner_section:
                getAttendance();
                break;
            case R.id.session_spinner:
                if(NetworkUtil.isNetworkAvailable(this)) {
                    presenter.getAttendance(((Section) sectionSpinner.getSelectedItem()).getId(),
                            attendanceDate, sessionSpinner.getSelectedItem().equals("Morning") ? 0 : 1);
                } else {
                    List<Attendance> attendanceList = AttendanceDao.getAttendance(
                            ((Section) sectionSpinner.getSelectedItem()).getId(), attendanceDate,
                            sessionSpinner.getSelectedItem().equals("Morning") ? 0 : 1);
                    viewAttendance(attendanceList);
                }
                break;
            case R.id.period_spinner:
                if(NetworkUtil.isNetworkAvailable(this)) {
                    presenter.getAttendance(((Section) sectionSpinner.getSelectedItem()).getId(), attendanceDate,
                            ((Timetable) periodSpinner.getSelectedItem()).getPeriodNo());
                } else {
                    List<Attendance> attendanceList = AttendanceDao.getAttendance(
                            ((Section) sectionSpinner.getSelectedItem()).getId(), attendanceDate,
                            ((Timetable) periodSpinner.getSelectedItem()).getPeriodNo());
                    viewAttendance(attendanceList);
                }

                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void showOfflineTimetable() {
        List<Timetable> timetableList = TimetableDao.getDayTimetable(((Section) sectionSpinner.getSelectedItem()).getId(),
                days[getFormattedCalendar().get(Calendar.DAY_OF_WEEK)]);
        ArrayAdapter<Timetable> adapter = new
                ArrayAdapter<>(this, android.R.layout.simple_spinner_item, timetableList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        periodSpinner.setAdapter(adapter);
        periodSpinner.setOnItemSelectedListener(this);
    }

    private void setDefaultDate() {
        dateView.setText(DateUtil.getDisplayFormattedDate(new LocalDate().toString()));
        attendanceDate = new LocalDate().toString();
    }

    public void changeDate(View view) {
        Calendar cal = getFormattedCalendar();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerFragment newFragment = new DatePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("year", year);
        bundle.putInt("month", month);
        bundle.putInt("day", day);
        newFragment.setCallBack(onDate);
        newFragment.setArguments(bundle);
        newFragment.show(this.getSupportFragmentManager(), "datePicker");
    }

    private Calendar getFormattedCalendar() {
        SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = new Date();
        try {
            date = defaultFormat.parse(attendanceDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    DatePickerDialog.OnDateSetListener onDate = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

            Calendar cal = Calendar.getInstance();
            cal.set(year, monthOfYear, dayOfMonth);
            Date date = cal.getTime();

            Calendar tomorrowCal = Calendar.getInstance();
            Date tomorrowDate = tomorrowCal.getTime();

            if (date.after(tomorrowDate)) {
                showSnackbar(getResources().getText(R.string.future_date).toString());
            } else {
                dateView.setText(DateUtil.getDisplayFormattedDate(dateFormat.format(date)));
                attendanceDate = dateFormat.format(date);
                getAttendance();
            }
        }
    };

    private void getAttendance() {
        String attendanceType = ((Clas) classSpinner.getSelectedItem()).getAttendanceType();
        switch (attendanceType) {
            case "Daily":
                sessionLayout.setVisibility(View.GONE);
                periodLayout.setVisibility(View.GONE);
                if(NetworkUtil.isNetworkAvailable(this)) {
                    presenter.getAttendance(((Section) sectionSpinner.getSelectedItem()).getId(), attendanceDate, 0);
                } else {
                    List<Attendance> attendanceList = AttendanceDao.getAttendance(
                            ((Section) sectionSpinner.getSelectedItem()).getId(), attendanceDate, 0);
                    viewAttendance(attendanceList);
                }
                break;
            case "Session":
                periodLayout.setVisibility(View.GONE);
                sessionLayout.setVisibility(View.VISIBLE);
                if(NetworkUtil.isNetworkAvailable(this)) {
                    presenter.getAttendance(((Section) sectionSpinner.getSelectedItem()).getId(),
                            attendanceDate, sessionSpinner.getSelectedItem().equals("Morning") ? 0 : 1);
                } else {
                    List<Attendance> attendanceList = AttendanceDao.getAttendance(
                            ((Section) sectionSpinner.getSelectedItem()).getId(), attendanceDate,
                            sessionSpinner.getSelectedItem().equals("Morning") ? 0 : 1);
                    viewAttendance(attendanceList);
                }
                break;
            case "Period":
                sessionLayout.setVisibility(View.GONE);
                periodLayout.setVisibility(View.VISIBLE);
                if(NetworkUtil.isNetworkAvailable(this)) {
                    presenter.getTimetable(((Section) sectionSpinner.getSelectedItem()).getId(),
                            days[getFormattedCalendar().get(Calendar.DAY_OF_WEEK)]);
                } else {
                    showOfflineTimetable();
                }
                break;
            default:
                break;
        }
    }

    private void viewAttendance(List<Attendance> attendanceList) {
        if (attendanceList.size() == 0) {
            noAttendance.setVisibility(View.VISIBLE);
        } else {
            noAttendance.setVisibility(View.INVISIBLE);
            attendanceAdapter.setDataSet(attendanceList);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
