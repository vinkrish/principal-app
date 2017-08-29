package com.aanglearning.principalapp.util;

import android.app.IntentService;
import android.content.Intent;

import com.aanglearning.principalapp.App;
import com.aanglearning.principalapp.BuildConfig;
import com.aanglearning.principalapp.api.ApiClient;
import com.aanglearning.principalapp.api.PrincipalApi;
import com.aanglearning.principalapp.model.AppVersion;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VersionIntentService extends IntentService {

    public VersionIntentService() {
        super("VersionIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        PrincipalApi api = ApiClient.getAuthorizedClient().create(PrincipalApi.class);

        Call<AppVersion> queue = api.getAppVersion(BuildConfig.VERSION_CODE, "principal");
        queue.enqueue(new Callback<AppVersion>() {
            @Override
            public void onResponse(Call<AppVersion> call, Response<AppVersion> response) {
                if(response.isSuccessful()) {
                    SharedPreferenceUtil.saveAppVersion(App.getInstance(), response.body());
                }
            }

            @Override
            public void onFailure(Call<AppVersion> call, Throwable t) {
            }
        });
    }

}
