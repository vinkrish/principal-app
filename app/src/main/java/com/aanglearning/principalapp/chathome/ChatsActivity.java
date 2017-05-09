package com.aanglearning.principalapp.chathome;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.aanglearning.principalapp.R;
import com.aanglearning.principalapp.chat.ChatActivity;
import com.aanglearning.principalapp.dao.TeacherDao;
import com.aanglearning.principalapp.model.Chat;
import com.aanglearning.principalapp.newchat.NewChatActivity;
import com.aanglearning.principalapp.util.DividerItemDecoration;
import com.aanglearning.principalapp.util.NetworkUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatsActivity extends AppCompatActivity implements ChatsView {
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private ChatsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new ChatsPresenterImpl(this, new ChatsInteractorImpl());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this));

    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getChats(TeacherDao.getTeacher().getId());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    public void newChat(View view) {
        if (NetworkUtil.isNetworkAvailable(this)) {
            startActivity(new Intent(ChatsActivity.this, NewChatActivity.class));
        } else {
            showSnackbar("You are offline,check your internet.");
        }
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
        Snackbar errorSnackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG);
        //errorSnackbar.setAction(R.string.retry, this);
        errorSnackbar.show();
    }

    @Override
    public void setGroups(List<Chat> chats) {
        ChatsAdapter adapter = new ChatsAdapter(chats, new ChatsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Chat chat) {
                Intent intent = new Intent(ChatsActivity.this, ChatActivity.class);
                intent.putExtra("recipientId", chat.getStudentId());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
    }

}
