package com.aanglearning.principalapp.login;

import com.aanglearning.principalapp.App;
import com.aanglearning.principalapp.R;
import com.aanglearning.principalapp.api.APIError;
import com.aanglearning.principalapp.api.ApiClient;
import com.aanglearning.principalapp.api.AuthApi;
import com.aanglearning.principalapp.api.ErrorUtils;
import com.aanglearning.principalapp.model.CommonResponse;
import com.aanglearning.principalapp.model.Credentials;
import com.aanglearning.principalapp.model.TeacherCredentials;
import com.aanglearning.principalapp.util.SharedPreferenceUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vinay on 28-03-2017.
 */

class LoginInteractorImpl implements LoginInteractor {
    @Override
    public void login(final Credentials credentials, final OnLoginFinishedListener listener) {
        AuthApi authApi = ApiClient.getClient().create(AuthApi.class);

        Call<TeacherCredentials> queue = authApi.login(credentials);
        queue.enqueue(new Callback<TeacherCredentials>() {
            @Override
            public void onResponse(Call<TeacherCredentials> call, Response<TeacherCredentials> response) {
                if(response.isSuccessful()) {
                    SharedPreferenceUtil.saveAuthorizedUser(App.getInstance(), credentials.getUsername());
                    listener.onSuccess(response.body());
                } else {
                    listener.onError("mobile number and password don't match");
                }
            }

            @Override
            public void onFailure(Call<TeacherCredentials> call, Throwable t) {
                listener.onError(App.getInstance().getString(R.string.network_error));
            }
        });
    }

    @Override
    public void recoverPwd(String username, final OnLoginFinishedListener listener) {
        AuthApi authApi = ApiClient.getClient().create(AuthApi.class);

        Call<Void> queue = authApi.newPassword(username);
        queue.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()) {
                    listener.onPwdRecovered();
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
