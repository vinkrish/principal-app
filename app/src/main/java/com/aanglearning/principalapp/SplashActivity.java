package com.aanglearning.principalapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.aanglearning.principalapp.login.LoginActivity;
import com.aanglearning.principalapp.util.AppGlobal;
import com.aanglearning.principalapp.util.SharedPreferenceUtil;

import org.joda.time.LocalDate;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppGlobal.setSqlDbHelper(getApplicationContext());
        LocalDate localDate = new LocalDate();
        SharedPreferenceUtil.saveAttendanceDate(this, localDate.toString());
        SharedPreferenceUtil.saveHomeworkDate(this, localDate.toString());

        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
