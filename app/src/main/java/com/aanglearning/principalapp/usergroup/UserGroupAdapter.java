package com.aanglearning.principalapp.usergroup;

import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aanglearning.principalapp.R;
import com.aanglearning.principalapp.model.UserGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Vinay on 03-04-2017.
 */

class UserGroupAdapter extends RecyclerView.Adapter<UserGroupAdapter.ViewHolder> {
    private List<UserGroup> users;

    UserGroupAdapter(List<UserGroup> users) {
        this.users = users;
    }

    public List<UserGroup> getDataSet() {
        return users;
    }

    @UiThread
    void setDataSet(List<UserGroup> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    @Override
    public UserGroupAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_group_item, parent, false);
        return new UserGroupAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(UserGroupAdapter.ViewHolder holder, int position) {
        holder.bind(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name) TextView name;
        @BindView(R.id.role) TextView role;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void bind(final UserGroup userGroup) {
            name.setText(userGroup.getName());
            role.setText(userGroup.getRole());
        }

    }
}
