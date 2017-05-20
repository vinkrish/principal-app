package com.aanglearning.principalapp.chathome;

import android.content.Intent;
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
import android.widget.LinearLayout;

import com.aanglearning.principalapp.R;
import com.aanglearning.principalapp.chat.ChatActivity;
import com.aanglearning.principalapp.dao.ChatDao;
import com.aanglearning.principalapp.dao.TeacherDao;
import com.aanglearning.principalapp.model.Chat;
import com.aanglearning.principalapp.newchat.NewChatActivity;
import com.aanglearning.principalapp.util.DividerItemDecoration;
import com.aanglearning.principalapp.util.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatsActivity extends AppCompatActivity implements ChatsView {
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.coordinatorLayout) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.refreshLayout) SwipeRefreshLayout refreshLayout;
    @BindView(R.id.no_chats) LinearLayout noChats;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    private ChatsPresenter presenter;
    private ChatsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new ChatsPresenterImpl(this, new ChatsInteractorImpl());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this));

        adapter = new ChatsAdapter(new ArrayList<Chat>(0), mItemListener);
        recyclerView.setAdapter(adapter);

        refreshLayout.setColorSchemeColors(
                ContextCompat.getColor(this, R.color.colorPrimary),
                ContextCompat.getColor(this, R.color.colorAccent),
                ContextCompat.getColor(this, R.color.colorPrimaryDark)
        );

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getChats(TeacherDao.getTeacher().getId());
            }
        });

        if(NetworkUtil.isNetworkAvailable(this)) {
            presenter.getChats(TeacherDao.getTeacher().getId());
        } else {
            List<Chat> chats = ChatDao.getChats();
            if(chats.size() == 0) {
                noChats.setVisibility(View.VISIBLE);
            } else {
                noChats.setVisibility(View.INVISIBLE);
                adapter.setDataSet(chats);
            }
        }
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
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgess() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showError(String message) {
        refreshLayout.setRefreshing(false);
        showSnackbar(message);
    }

    @Override
    public void setChats(List<Chat> chats) {
        if(chats.size() == 0) {
            noChats.setVisibility(View.VISIBLE);
        } else {
            noChats.setVisibility(View.INVISIBLE);
            adapter.setDataSet(chats);
            backupChats(chats);
        }
        refreshLayout.setRefreshing(false);
    }

    private void backupChats(final List<Chat> chats) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ChatDao.clear();
                ChatDao.insertMany(chats);
            }
        }).start();
    }

    ChatsAdapter.OnItemClickListener mItemListener = new ChatsAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(Chat chat) {
            Intent intent = new Intent(ChatsActivity.this, ChatActivity.class);
            intent.putExtra("recipientId", chat.getStudentId());
            intent.putExtra("recipientName", chat.getStudentName());
            startActivity(intent);
        }
    };

}
