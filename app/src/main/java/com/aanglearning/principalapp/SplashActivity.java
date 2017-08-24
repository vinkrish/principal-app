package com.aanglearning.principalapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.aanglearning.principalapp.dao.TeacherDao;
import com.aanglearning.principalapp.dashboard.DashboardActivity;
import com.aanglearning.principalapp.login.LoginActivity;
import com.aanglearning.principalapp.model.TeacherCredentials;
import com.aanglearning.principalapp.service.FCMIntentService;
import com.aanglearning.principalapp.util.AppGlobal;
import com.aanglearning.principalapp.util.SharedPreferenceUtil;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppGlobal.setSqlDbHelper(getApplicationContext());

        TeacherCredentials credentials = SharedPreferenceUtil.getTeacher(this);
        if(!credentials.getMobileNo().equals("") && !SharedPreferenceUtil.isFcmTokenSaved(this)) {
            startService(new Intent(this, FCMIntentService.class));
        }

        if (TeacherDao.getTeacher().getId() == 0) {
            SharedPreferenceUtil.logout(this);
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else if (credentials.getAuthToken().equals("")) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else {
            startActivity(new Intent(this, DashboardActivity.class));
            finish();
        }
    }
}
