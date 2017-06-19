package com.aanglearning.principalapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.aanglearning.principalapp.dashboard.DashboardActivity;
import com.aanglearning.principalapp.login.LoginActivity;
import com.aanglearning.principalapp.util.AppGlobal;
import com.aanglearning.principalapp.util.SharedPreferenceUtil;

import org.joda.time.LocalDate;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppGlobal.setSqlDbHelper(getApplicationContext());

        if(SharedPreferenceUtil.getTeacher(this).getAuthToken().equals("")) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else {
            startActivity(new Intent(this, DashboardActivity.class));
            finish();
        }
    }
}
