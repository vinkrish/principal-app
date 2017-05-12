package com.aanglearning.principalapp.dashboard;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aanglearning.principalapp.R;
import com.aanglearning.principalapp.attendance.AttendanceActivity;
import com.aanglearning.principalapp.chathome.ChatsActivity;
import com.aanglearning.principalapp.dao.GroupDao;
import com.aanglearning.principalapp.dao.ServiceDao;
import com.aanglearning.principalapp.dao.TeacherDao;
import com.aanglearning.principalapp.homework.HomeworkActivity;
import com.aanglearning.principalapp.login.LoginActivity;
import com.aanglearning.principalapp.messagegroup.MessageActivity;
import com.aanglearning.principalapp.model.Groups;
import com.aanglearning.principalapp.model.Service;
import com.aanglearning.principalapp.util.DividerItemDecoration;
import com.aanglearning.principalapp.util.SharedPreferenceUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardActivity extends AppCompatActivity implements GroupView {
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.coordinatorLayout) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.refreshLayout) SwipeRefreshLayout refreshLayout;
    @BindView(R.id.navigation_view) NavigationView navigationView;
    @BindView(R.id.drawer) DrawerLayout drawerLayout;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    private GroupPresenter presenter;
    private GroupAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);

        toolbar.setTitle("Thy Campus");
        toolbar.setSubtitle("Principal");
        setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle("Principala");

        presenter = new GroupPresenterImpl(this, new GroupInteractorImpl());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this));
        adapter = new GroupAdapter(new ArrayList<Groups>(0), mItemListener);
        recyclerView.setAdapter(adapter);

        setupDrawerContent(navigationView);

        ActionBarDrawerToggle actionBarDrawerToggle = new
                ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name) {

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        super.onDrawerClosed(drawerView);
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        super.onDrawerOpened(drawerView);
                    }
                };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        View hView = navigationView.inflateHeaderView(R.layout.header);
        ImageView imageView = (ImageView) hView.findViewById(R.id.user_image);
        TextView tv = (TextView) hView.findViewById(R.id.name);
        imageView.setImageResource(R.drawable.ic_account);
        tv.setText(TeacherDao.getTeacher().getTeacherName());

        hideDrawerItem();

        refreshLayout.setColorSchemeColors(
                ContextCompat.getColor(this, R.color.colorPrimary),
                ContextCompat.getColor(this, R.color.colorAccent),
                ContextCompat.getColor(this, R.color.colorPrimaryDark)
        );

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getGroups(TeacherDao.getTeacher().getSchoolId());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getGroups(TeacherDao.getTeacher().getSchoolId());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (isNavDrawerOpen()) {
            closeNavDrawer();
        } else {
            super.onBackPressed();
        }
    }

    protected boolean isNavDrawerOpen() {
        return drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START);
    }

    protected void closeNavDrawer() {
        if (drawerLayout != null) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    private void hideDrawerItem() {
        Menu menu = navigationView.getMenu();
        Service service = ServiceDao.getServices();
        if (!service.getIsAttendance()) menu.findItem(R.id.attendance_item).setVisible(false);
        if (!service.getIsHomework()) menu.findItem(R.id.homework_item).setVisible(false);
        if (!service.getIsChat()) menu.findItem(R.id.chat_item).setVisible(false);
    }

    private void showSnackbar(String message) {
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgess() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        refreshLayout.setRefreshing(false);
        showSnackbar(message);
    }

    @Override
    public void setGroups(List<Groups> groups) {
        refreshLayout.setRefreshing(false);
        adapter.replaceData(groups);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.dashboard_item:
                                startActivity(new Intent(DashboardActivity.this, DashboardActivity.class));
                                break;
                            case R.id.attendance_item:
                                startActivity(new Intent(DashboardActivity.this, AttendanceActivity.class));
                                break;
                            case R.id.homework_item:
                                startActivity(new Intent(DashboardActivity.this, HomeworkActivity.class));
                                break;
                            case R.id.chat_item:
                                startActivity(new Intent(DashboardActivity.this, ChatsActivity.class));
                                break;
                            case R.id.logout_item:
                                SharedPreferenceUtil.logout(DashboardActivity.this);
                                startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
                                finish();
                                break;
                            default:
                                break;
                        }
                        menuItem.setChecked(false);
                        drawerLayout.closeDrawers();
                        return false;
                    }
                });
    }

    GroupAdapter.OnItemClickListener mItemListener = new GroupAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(Groups group) {
            GroupDao.clear();
            GroupDao.insert(group);
            startActivity(new Intent(DashboardActivity.this, MessageActivity.class));
        }
    };
}
