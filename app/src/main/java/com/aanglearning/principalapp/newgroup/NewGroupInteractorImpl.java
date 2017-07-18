package com.aanglearning.principalapp.newgroup;

import com.aanglearning.principalapp.App;
import com.aanglearning.principalapp.R;
import com.aanglearning.principalapp.api.ApiClient;
import com.aanglearning.principalapp.api.PrincipalApi;
import com.aanglearning.principalapp.model.Clas;
import com.aanglearning.principalapp.model.Groups;
import com.aanglearning.principalapp.model.Section;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vinay on 30-03-2017.
 */

class NewGroupInteractorImpl implements NewGroupInteractor {
    @Override
    public void getClassList(long schoolId, final OnFinishedListener listener) {
        PrincipalApi api = ApiClient.getAuthorizedClient().create(PrincipalApi.class);

        Call<List<Clas>> classList = api.getClassList(schoolId);
        classList.enqueue(new Callback<List<Clas>>() {
            @Override
            public void onResponse(Call<List<Clas>> call, Response<List<Clas>> response) {
                if(response.isSuccessful()) {
                    listener.onClasReceived(response.body());
                } else {
                    listener.onError(App.getInstance().getString(R.string.request_error));
                }
            }

            @Override
            public void onFailure(Call<List<Clas>> call, Throwable t) {
                listener.onError(App.getInstance().getString(R.string.network_error));
            }
        });
    }

    @Override
    public void getSectionList(long classId, final OnFinishedListener listener) {
        PrincipalApi api = ApiClient.getAuthorizedClient().create(PrincipalApi.class);

        Call<List<Section>> classList = api.getSectionList(classId);
        classList.enqueue(new Callback<List<Section>>() {
            @Override
            public void onResponse(Call<List<Section>> call, Response<List<Section>> response) {
                if(response.isSuccessful()) {
                    listener.onSectionReceived(response.body());
                } else {
                    listener.onError(App.getInstance().getString(R.string.request_error));
                }
            }

            @Override
            public void onFailure(Call<List<Section>> call, Throwable t) {
                listener.onError(App.getInstance().getString(R.string.network_error));
            }
        });
    }

    @Override
    public void saveGroup(Groups groups, final OnFinishedListener listener) {
        PrincipalApi api = ApiClient.getAuthorizedClient().create(PrincipalApi.class);

        Call<Groups> classList = api.saveGroup(groups);
        classList.enqueue(new Callback<Groups>() {
            @Override
            public void onResponse(Call<Groups> call, Response<Groups> response) {
                if(response.isSuccessful()) {
                    listener.onGroupSaved(response.body());
                } else {
                    listener.onError(App.getInstance().getString(R.string.request_error));
                }
            }

            @Override
            public void onFailure(Call<Groups> call, Throwable t) {
                listener.onError(App.getInstance().getString(R.string.request_error));
            }
        });
    }
}
