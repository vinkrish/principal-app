package com.aanglearning.principalapp.messagegroup;

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
import android.view.Menu;
import android.view.MenuItem;

import com.aanglearning.principalapp.R;
import com.aanglearning.principalapp.dao.MessageDao;
import com.aanglearning.principalapp.model.Groups;
import com.aanglearning.principalapp.model.Message;
import com.aanglearning.principalapp.usergroup.UserGroupActivity;
import com.aanglearning.principalapp.util.EndlessRecyclerViewScrollListener;
import com.aanglearning.principalapp.util.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageActivity extends AppCompatActivity implements MessageView{
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.coordinatorLayout) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.refreshLayout) SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    private static final String TAG = "MessageActivity";
    private MessagePresenter presenter;
    private Groups group;
    private MessageAdapter adapter;
    private EndlessRecyclerViewScrollListener scrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            group = new Groups();
            group.setId(extras.getLong("groupId"));
            group.setName(extras.getString("groupName"));
        }
        getSupportActionBar().setTitle(group.getName());

        presenter = new MessagePresenterImpl(this, new MessageInteractorImpl());

        setupRecyclerView();

        refreshLayout.setColorSchemeColors(
                ContextCompat.getColor(this, R.color.colorPrimary),
                ContextCompat.getColor(this, R.color.colorAccent),
                ContextCompat.getColor(this, R.color.colorPrimaryDark)
        );

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getMessages(group.getId());
            }
        });

        if(NetworkUtil.isNetworkAvailable(this)) {
            presenter.getMessages(group.getId());
        } else {
            List<Message> messages = MessageDao.getGroupMessages(group.getId());
            adapter.setDataSet(messages);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new MessageAdapter(this, new ArrayList<Message>(0));
        recyclerView.setAdapter(adapter);

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if(NetworkUtil.isNetworkAvailable(MessageActivity.this)) {
                    presenter.getFollowupMessages(group.getId(), adapter.getDataSet().get(adapter.getDataSet().size()-1).getId());
                }
            }
        };
        recyclerView.addOnScrollListener(scrollListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.message_group_overflow, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.group_info:
                Intent intent = new Intent(this, UserGroupActivity.class);
                intent.putExtra("groupId", group.getId());
                intent.putExtra("groupName", group.getName());
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
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
    public void hideProgress() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showError(String message) {
        refreshLayout.setRefreshing(false);
        showSnackbar(message);
    }

    @Override
    public void onMessageSaved(Message message) {
        adapter.insertDataSet(message);
    }

    @Override
    public void showMessages(List<Message> messages) {
        refreshLayout.setRefreshing(false);
        adapter.setDataSet(messages);
        backupMessages(messages);
    }

    private void backupMessages(final List<Message> messages) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MessageDao.clearGroupMessages(group.getId());
                MessageDao.insertGroupMessages(messages);
            }
        }).start();
    }

    @Override
    public void showFollowupMessages(List<Message> msgs) {
        adapter.updateDataSet(msgs);
    }

}
