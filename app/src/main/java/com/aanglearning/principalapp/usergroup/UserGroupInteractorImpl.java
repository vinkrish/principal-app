package com.aanglearning.principalapp.usergroup;

import com.aanglearning.principalapp.App;
import com.aanglearning.principalapp.R;
import com.aanglearning.principalapp.api.ApiClient;
import com.aanglearning.principalapp.api.PrincipalApi;
import com.aanglearning.principalapp.model.GroupUsers;
import com.aanglearning.principalapp.model.UserGroup;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vinay on 01-04-2017.
 */

class UserGroupInteractorImpl implements UserGroupInteractor {

    @Override
    public void getUserGroup(long groupId, final OnFinishedListener listener) {
        PrincipalApi api = ApiClient.getAuthorizedClient().create(PrincipalApi.class);

        Call<GroupUsers> queue = api.getUserGroup(groupId);
        queue.enqueue(new Callback<GroupUsers>() {
            @Override
            public void onResponse(Call<GroupUsers> call, Response<GroupUsers> response) {
                if(response.isSuccessful()) {
                    listener.onUserGroupReceived(response.body());
                } else {
                    listener.onError(App.getInstance().getString(R.string.request_error));
                }
            }

            @Override
            public void onFailure(Call<GroupUsers> call, Throwable t) {
                listener.onError(App.getInstance().getString(R.string.network_error));
            }
        });
    }

    @Override
    public void saveUserGroup(ArrayList<UserGroup> userGroups, final OnFinishedListener listener) {
        PrincipalApi api = ApiClient.getAuthorizedClient().create(PrincipalApi.class);

        Call<Void> queue = api.saveUserGroupList(userGroups);
        queue.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()) {
                    listener.onUserGroupSaved();
                } else {
                    listener.onError(App.getInstance().getString(R.string.request_error));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                listener.onError(App.getInstance().getString(R.string.network_error));
            }
        });
    }

    @Override
    public void deleteUsers(ArrayList<UserGroup> userGroups, final OnFinishedListener listener) {
        PrincipalApi api = ApiClient.getAuthorizedClient().create(PrincipalApi.class);

        Call<Void> queue = api.deleteUserGroupUsers(userGroups);
        queue.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()) {
                    listener.onUsersDeleted();
                } else {
                    listener.onError(App.getInstance().getString(R.string.request_error));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                listener.onError(App.getInstance().getString(R.string.network_error));
            }
        });
    }

}
