package com.aanglearning.principalapp.calendar;

import com.aanglearning.principalapp.App;
import com.aanglearning.principalapp.R;
import com.aanglearning.principalapp.api.ApiClient;
import com.aanglearning.principalapp.api.PrincipalApi;
import com.aanglearning.principalapp.model.Evnt;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vinay on 31-07-2017.
 */

class EventInteractorImpl implements EventInteractor {
    @Override
    public void getEvents(long schoolId, final OnFinishedListener listener) {
        PrincipalApi api = ApiClient.getAuthorizedClient().create(PrincipalApi.class);

        Call<List<Evnt>> queue = api.getEvents(schoolId);
        queue.enqueue(new Callback<List<Evnt>>() {
            @Override
            public void onResponse(Call<List<Evnt>> call, Response<List<Evnt>> response) {
                if(response.isSuccessful()) {
                    listener.onEventsReceived(response.body());
                } else {
                    listener.onError(App.getInstance().getString(R.string.request_error));
                }
            }

            @Override
            public void onFailure(Call<List<Evnt>> call, Throwable t) {
                listener.onError(App.getInstance().getString(R.string.network_error));
            }
        });
    }
}
