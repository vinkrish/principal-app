package com.aanglearning.principalapp.usergroup;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aanglearning.principalapp.R;
import com.aanglearning.principalapp.dao.GroupDao;
import com.aanglearning.principalapp.model.GroupUsers;
import com.aanglearning.principalapp.model.Groups;
import com.aanglearning.principalapp.util.DividerItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserGroupActivity extends AppCompatActivity implements UserGroupView {
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.progress)
    ProgressBar progressBar;
    @BindView(R.id.group_name_tv)
    TextView groupName;
    @BindView(R.id.member_recycler_view)
    RecyclerView memberView;

    private UserGroupPresenter presenter;
    private Groups group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_group);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new UserGroupPresenterImpl(this, new UserGroupInteractorImpl());

        memberView.setLayoutManager(new LinearLayoutManager(this));
        memberView.setNestedScrollingEnabled(false);
        memberView.setItemAnimator(new DefaultItemAnimator());
        memberView.addItemDecoration(new DividerItemDecoration(this));
    }

    @Override
    public void onResume() {
        super.onResume();
        group = GroupDao.getGroup();
        groupName.setText(group.getName());
        presenter.getUserGroup(group.getId());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
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
    public void showUserGroup(GroupUsers groupUsers) {
        UserGroupAdapter adapter = new UserGroupAdapter(groupUsers.getUserGroupList());
        memberView.setAdapter(adapter);
    }

}
