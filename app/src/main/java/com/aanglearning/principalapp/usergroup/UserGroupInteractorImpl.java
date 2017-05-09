package com.aanglearning.principalapp.usergroup;

import com.aanglearning.principalapp.App;
import com.aanglearning.principalapp.R;
import com.aanglearning.principalapp.api.ApiClient;
import com.aanglearning.principalapp.api.PrincipalApi;
import com.aanglearning.principalapp.model.GroupUsers;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vinay on 01-04-2017.
 */

public class UserGroupInteractorImpl implements UserGroupInteractor {

    @Override
    public void getUserGroup(long groupId, final OnFinishedListener listener) {
        PrincipalApi api = ApiClient.getAuthorizedClient().create(PrincipalApi.class);

        Call<GroupUsers> classList = api.getUserGroup(groupId);
        classList.enqueue(new Callback<GroupUsers>() {
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

}
