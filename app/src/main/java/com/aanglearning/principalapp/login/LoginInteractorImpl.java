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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vinay on 28-03-2017.
 */

class LoginInteractorImpl implements LoginInteractor {
    @Override
    public void login(Credentials credentials, final OnLoginFinishedListener listener) {
        AuthApi authApi = ApiClient.getClient().create(AuthApi.class);

        Call<TeacherCredentials> login = authApi.login(credentials);
        login.enqueue(new Callback<TeacherCredentials>() {
            @Override
            public void onResponse(Call<TeacherCredentials> call, Response<TeacherCredentials> response) {
                if(response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else {
                    listener.onError(App.getInstance().getString(R.string.request_error));
                }
            }

            @Override
            public void onFailure(Call<TeacherCredentials> call, Throwable t) {
                listener.onError(App.getInstance().getString(R.string.network_error));
            }
        });
    }

    @Override
    public void recoverPwd(String authToken, String newPassword, final OnLoginFinishedListener listener) {
        AuthApi authApi = ApiClient.getAuthorizedClient().create(AuthApi.class);

        Call<CommonResponse> sendNewPwd = authApi.newPassword(newPassword);
        sendNewPwd.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                if(response.isSuccessful()) {
                    if(response.body().isSuccess()){
                        listener.onPwdRecovered();
                    } else {
                        listener.onNoUser();
                    }
                } else {
                    listener.onError(App.getInstance().getString(R.string.request_error));
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                listener.onError(App.getInstance().getString(R.string.network_error));
            }
        });
    }
}
