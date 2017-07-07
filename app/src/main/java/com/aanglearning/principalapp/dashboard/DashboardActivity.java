package com.aanglearning.principalapp.dashboard;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.aanglearning.principalapp.model.Teacher;
import com.aanglearning.principalapp.sqlite.SqlDbHelper;
import com.aanglearning.principalapp.timetable.TimetableActivity;
import com.aanglearning.principalapp.util.DividerItemDecoration;
import com.aanglearning.principalapp.util.NetworkUtil;
import com.aanglearning.principalapp.util.PermissionUtil;
import com.aanglearning.principalapp.util.SharedPreferenceUtil;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
    @BindView(R.id.noGroups) LinearLayout noGroups;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    private GroupPresenter presenter;
    private GroupAdapter adapter;
    private Teacher teacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);

        //getSupportActionBar().setTitle("Principal");

        teacher = TeacherDao.getTeacher();

        toolbar.setTitle(teacher.getName());
        toolbar.setSubtitle("Principal");
        setSupportActionBar(toolbar);

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

        setProfile();

        hideDrawerItem();

        refreshLayout.setColorSchemeColors(
                ContextCompat.getColor(this, R.color.colorPrimary),
                ContextCompat.getColor(this, R.color.colorAccent),
                ContextCompat.getColor(this, R.color.colorPrimaryDark)
        );

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getGroups(teacher.getSchoolId());
            }
        });

        if(NetworkUtil.isNetworkAvailable(this)) {
            presenter.getGroups(teacher.getSchoolId());
        } else {
            loadOfflineData();
        }
    }

    private void loadOfflineData() {
        List<Groups> groups = GroupDao.getGroups();
        if(groups.size() == 0) {
            noGroups.setVisibility(View.VISIBLE);
        } else {
            noGroups.setVisibility(View.INVISIBLE);
            adapter.replaceData(groups);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
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
        if (!service.getIsTimetable()) menu.findItem(R.id.timetable_item).setVisible(false);
    }

    private void showSnackbar(String message) {
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showProgress() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgess() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showError(String message) {
        showSnackbar(message);
        loadOfflineData();
    }

    @Override
    public void setGroups(List<Groups> groups) {
        if(groups.size() == 0) {
            GroupDao.clear();
            noGroups.setVisibility(View.VISIBLE);
        } else {
            noGroups.setVisibility(View.INVISIBLE);
            adapter.replaceData(groups);
            backupGroups(groups);
        }
        refreshLayout.setRefreshing(false);
        updateFcmToken();
    }

    private void backupGroups(final List<Groups> groups) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                GroupDao.clear();
                GroupDao.insertMany(groups);
            }
        }).start();
    }

    private void updateFcmToken() {
        if(!SharedPreferenceUtil.isFcmTokenSaved(DashboardActivity.this)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    presenter.updateFcmToken(SharedPreferenceUtil.getAuthorization(DashboardActivity.this));
                }
            }).start();
        }
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
                            case R.id.timetable_item:
                                startActivity(new Intent(DashboardActivity.this, TimetableActivity.class));
                                break;
                            case R.id.chat_item:
                                startActivity(new Intent(DashboardActivity.this, ChatsActivity.class));
                                break;
                            case R.id.logout_item:
                                logout();
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

    private void logout() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("Are you sure you want to logout?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SharedPreferenceUtil.logout(DashboardActivity.this);
                SqlDbHelper.getInstance(DashboardActivity.this).deleteTables();
                startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
                finish();
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();
    }

    private void setProfile() {
        View hView = navigationView.inflateHeaderView(R.layout.header);
        final ImageView imageView = (ImageView) hView.findViewById(R.id.user_image);
        TextView tv = (TextView) hView.findViewById(R.id.name);
        tv.setText(teacher.getName());

        if(PermissionUtil.getStoragePermissionStatus(this)) {
            File dir = new File(Environment.getExternalStorageDirectory().getPath(), "Shikshitha/Principal/Images");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            final File file = new File(dir, teacher.getImage());
            if(file.exists()) {
                imageView.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
            } else {
                Picasso.with(this)
                        .load("https://s3.ap-south-1.amazonaws.com/aang-solutions/" + teacher.getImage())
                        .placeholder(R.drawable.splash_image)
                        .into(imageView, new Callback() {
                            @Override
                            public void onSuccess() {
                                Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                                try {
                                    FileOutputStream fos = new FileOutputStream(file);
                                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                                    fos.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError() {
                                imageView.setImageResource(R.drawable.ic_account);
                            }
                        });
            }
        }
    }

    GroupAdapter.OnItemClickListener mItemListener = new GroupAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(Groups group) {
            Intent intent = new Intent(DashboardActivity.this, MessageActivity.class);
            intent.putExtra("groupId", group.getId());
            intent.putExtra("groupName", group.getName());
            startActivity(intent);
        }
    };
}
