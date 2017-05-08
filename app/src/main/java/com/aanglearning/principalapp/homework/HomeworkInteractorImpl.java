package com.aanglearning.principalapp.homework;

import com.aanglearning.principalapp.App;
import com.aanglearning.principalapp.R;
import com.aanglearning.principalapp.api.ApiClient;
import com.aanglearning.principalapp.api.PrincipalApi;
import com.aanglearning.principalapp.model.Clas;
import com.aanglearning.principalapp.model.Homework;
import com.aanglearning.principalapp.model.Section;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vinay on 21-04-2017.
 */

public class HomeworkInteractorImpl implements HomeworkInteractor {

    @Override
    public void getClassList(long teacherId, final OnFinishedListener listener) {
        PrincipalApi api = ApiClient.getAuthorizedClient().create(PrincipalApi.class);

        Call<List<Clas>> classList = api.getSectionTeacherClasses(teacherId);
        classList.enqueue(new Callback<List<Clas>>() {
            @Override
            public void onResponse(Call<List<Clas>> call, Response<List<Clas>> response) {
                if(response.isSuccessful()) {
                    listener.onClassReceived(response.body());
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
    public void getSectionList(long classId, long teacherId, final OnFinishedListener listener) {
        PrincipalApi api = ApiClient.getAuthorizedClient().create(PrincipalApi.class);

        Call<List<Section>> classList = api.getSectionTeacherSections(classId, teacherId);
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
    public void getHomework(long sectionId, String date, final OnFinishedListener listener) {
        PrincipalApi api = ApiClient.getAuthorizedClient().create(PrincipalApi.class);

        Call<List<Homework>> classList = api.getHomework(sectionId, date);
        classList.enqueue(new Callback<List<Homework>>() {
            @Override
            public void onResponse(Call<List<Homework>> call, Response<List<Homework>> response) {
                if(response.isSuccessful()) {
                    listener.onHomeworkReceived(response.body());
                } else {
                    listener.onError(App.getInstance().getString(R.string.request_error));
                }
            }

            @Override
            public void onFailure(Call<List<Homework>> call, Throwable t) {
                listener.onError(App.getInstance().getString(R.string.network_error));
            }
        });
    }

    @Override
    public void saveHomework(Homework homework, final OnFinishedListener listener) {
        PrincipalApi api = ApiClient.getAuthorizedClient().create(PrincipalApi.class);

        Call<Homework> classList = api.saveHomework(homework);
        classList.enqueue(new Callback<Homework>() {
            @Override
            public void onResponse(Call<Homework> call, Response<Homework> response) {
                if(response.isSuccessful()) {
                    listener.onHomeworkSaved(response.body());
                } else {
                    listener.onError(App.getInstance().getString(R.string.request_error));
                }
            }

            @Override
            public void onFailure(Call<Homework> call, Throwable t) {
                listener.onError(App.getInstance().getString(R.string.network_error));
            }
        });
    }

    @Override
    public void updateHomework(Homework homework, final OnFinishedListener listener) {
        PrincipalApi api = ApiClient.getAuthorizedClient().create(PrincipalApi.class);

        Call<Void> classList = api.updateHomework(homework);
        classList.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()) {
                    listener.onHomeworkUpdated();
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
    public void deleteHomework(ArrayList<Homework> homeworks, final OnFinishedListener listener) {
        PrincipalApi api = ApiClient.getAuthorizedClient().create(PrincipalApi.class);

        Call<Void> deleteHW = api.deleteHomework(homeworks);
        deleteHW.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()) {
                    listener.onHomeworkDeleted();
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
