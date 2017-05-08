package com.aanglearning.principalapp.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.aanglearning.principalapp.R;
import com.aanglearning.principalapp.dao.ServiceDao;
import com.aanglearning.principalapp.dao.TeacherDao;
import com.aanglearning.principalapp.dashboard.DashboardActivity;
import com.aanglearning.principalapp.model.Credentials;
import com.aanglearning.principalapp.model.TeacherCredentials;
import com.aanglearning.principalapp.util.SharedPreferenceUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements LoginView {
    @BindView(R.id.login_id_et)
    EditText loginId;
    @BindView(R.id.password_et) EditText password;
    @BindView(R.id.login_id)
    TextInputLayout loginLayout;
    @BindView(R.id.password) TextInputLayout passwordLayout;
    @BindView(R.id.progress)
    ProgressBar progressBar;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        presenter = new LoginPresenterImpl(this);
    }

    public void login(View view) {
        Credentials c = new Credentials();
        c.setUsername(loginId.getText().toString());
        c.setPassword(password.getText().toString());
        presenter.validateCredentials(c);
    }

    public void forgotPassword(View view) {
        View v = this.getCurrentFocus();
        if (v != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        presenter.pwdRecovery(
                SharedPreferenceUtil.getTeacher(this).getAuthToken(),
                loginId.getText().toString());

    }

    private void showSnackbar(String message) {
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        showSnackbar(message);
    }

    @Override
    public void pwdRecovered() {

    }

    @Override
    public void noUser() {
        showSnackbar(getString(R.string.no_user));
    }

    @Override
    public void saveUser(TeacherCredentials teacherCredentials) {
        TeacherDao.clear();
        TeacherDao.insert(teacherCredentials.getTeacher());
        ServiceDao.clear();
        ServiceDao.insert(teacherCredentials.getService());
        SharedPreferenceUtil.saveTeacher(this, teacherCredentials);
    }

    @Override
    public void navigateToDashboard() {
        startActivity(new Intent(this, DashboardActivity.class));
        finish();
    }
}
