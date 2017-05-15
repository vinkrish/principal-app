package com.aanglearning.principalapp.homework;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.aanglearning.principalapp.R;
import com.aanglearning.principalapp.dao.TeacherDao;
import com.aanglearning.principalapp.model.Clas;
import com.aanglearning.principalapp.model.Homework;
import com.aanglearning.principalapp.model.Section;
import com.aanglearning.principalapp.util.DatePickerFragment;
import com.aanglearning.principalapp.util.DateUtil;

import org.joda.time.LocalDate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeworkActivity extends AppCompatActivity implements HomeworkView,
        AdapterView.OnItemSelectedListener {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.coordinatorLayout) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.refreshLayout) SwipeRefreshLayout refreshLayout;
    @BindView(R.id.spinner_class) Spinner classSpinner;
    @BindView(R.id.spinner_section) Spinner sectionSpinner;
    @BindView(R.id.date_tv) TextView dateView;
    @BindView(R.id.homework_recycler_view) RecyclerView homeworkRecycler;
    @BindView(R.id.homework_tv) TextView homeworkTv;

    private HomeworkPresenter presenter;
    private HomeworkAdapter homeworkAdapter;
    private String homeworkDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new HomeworkPresenterImpl(this, new HomeworkInteractorImpl());

        homeworkRecycler.setLayoutManager(new LinearLayoutManager(this));
        homeworkRecycler.setNestedScrollingEnabled(false);
        homeworkRecycler.setItemAnimator(new DefaultItemAnimator());

        homeworkAdapter = new HomeworkAdapter(new ArrayList<Homework>(0));
        homeworkRecycler.setAdapter(homeworkAdapter);

        setDefaultDate();

        refreshLayout.setColorSchemeColors(
                ContextCompat.getColor(this, R.color.colorPrimary),
                ContextCompat.getColor(this, R.color.colorAccent),
                ContextCompat.getColor(this, R.color.colorPrimaryDark)
        );

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getClassList(TeacherDao.getTeacher().getSchoolId());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getClassList(TeacherDao.getTeacher().getSchoolId());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    private void setDefaultDate() {
        dateView.setText(DateUtil.getDisplayFormattedDate(new LocalDate().toString()));
        homeworkDate = new LocalDate().toString();
    }

    public void changeDate(View view) {
        SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = new Date();
        try {
            date = defaultFormat.parse(homeworkDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
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
                homeworkDate = dateFormat.format(date);
                getHomework();
            }
        }
    };

    private void showSnackbar(String message) {
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG).show();
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
    public void showClass(List<Clas> clasList) {
        ArrayAdapter<Clas> adapter = new
                ArrayAdapter<>(this, android.R.layout.simple_spinner_item, clasList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classSpinner.setAdapter(adapter);
        classSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void showSection(List<Section> sectionList) {
        ArrayAdapter<Section> adapter = new
                ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sectionList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sectionSpinner.setAdapter(adapter);
        sectionSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void showHomeworks(List<Homework> hws) {
        refreshLayout.setRefreshing(false);
        List<Homework> homeworks = new ArrayList<>();
        for(Homework hw: hws) {
            if(hw.getId() != 0)
            homeworks.add(hw);
        }
        homeworkAdapter.setDataSet(homeworks);
        if(homeworks.size() == 0) homeworkTv.setVisibility(View.GONE);
        else homeworkTv.setVisibility(View.VISIBLE);
    }

    private void getHomework() {
        presenter.getHomework(((Section)sectionSpinner.getSelectedItem()).getId(), homeworkDate);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        switch (parent.getId()) {
            case R.id.spinner_class:
                presenter.getSectionList(((Clas) classSpinner.getSelectedItem()).getId());
                break;
            case R.id.spinner_section:
                getHomework();
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
