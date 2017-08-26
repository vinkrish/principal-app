package com.aanglearning.principalapp.fcm;

import android.app.IntentService;
import android.content.Intent;

import com.aanglearning.principalapp.App;
import com.aanglearning.principalapp.api.ApiClient;
import com.aanglearning.principalapp.api.AuthApi;
import com.aanglearning.principalapp.util.SharedPreferenceUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FCMIntentService extends IntentService {

    public FCMIntentService() {
        super("FCMIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        AuthApi api = ApiClient.getAuthorizedClient().create(AuthApi.class);

        Call<Void> classList = api.updateFcmToken(SharedPreferenceUtil.getAuthorization(getApplicationContext()));
        classList.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()) {
                    SharedPreferenceUtil.fcmTokenSaved(App.getInstance());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

}
