package com.aanglearning.principalapp.gallery;

import com.aanglearning.principalapp.App;
import com.aanglearning.principalapp.R;
import com.aanglearning.principalapp.api.ApiClient;
import com.aanglearning.principalapp.api.GalleryApi;
import com.aanglearning.principalapp.api.PrincipalApi;
import com.aanglearning.principalapp.model.Album;
import com.aanglearning.principalapp.model.Clas;
import com.aanglearning.principalapp.model.DeletedAlbum;
import com.aanglearning.principalapp.model.Section;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vinay on 30-10-2017.
 */

class GalleryInteractorImpl implements GalleryInteractor {
    @Override
    public void getClassList(long schoolId, final OnFinishedListener listener) {
        PrincipalApi api = ApiClient.getAuthorizedClient().create(PrincipalApi.class);

        Call<List<Clas>> queue = api.getClassList(schoolId);
        queue.enqueue(new Callback<List<Clas>>() {
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
    public void getSectionList(long classId, final OnFinishedListener listener) {
        PrincipalApi api = ApiClient.getAuthorizedClient().create(PrincipalApi.class);

        Call<List<Section>> queue = api.getSectionList(classId);
        queue.enqueue(new Callback<List<Section>>() {
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
    public void deleteAlbum(DeletedAlbum deletedAlbum, final OnFinishedListener listener) {
        GalleryApi api = ApiClient.getAuthorizedClient().create(GalleryApi.class);

        Call<DeletedAlbum> queue = api.deleteAlbum(deletedAlbum);
        queue.enqueue(new Callback<DeletedAlbum>() {
            @Override
            public void onResponse(Call<DeletedAlbum> call, Response<DeletedAlbum> response) {
                if(response.isSuccessful()) {
                    listener.onAlbumDeleted();
                } else {
                    listener.onError(App.getInstance().getString(R.string.request_error));
                }
            }

            @Override
            public void onFailure(Call<DeletedAlbum> call, Throwable t) {
                listener.onError(App.getInstance().getString(R.string.network_error));
            }
        });
    }

    @Override
    public void getAlbumsAboveId(long schoolId, long id, final OnFinishedListener listener) {
        GalleryApi api = ApiClient.getAuthorizedClient().create(GalleryApi.class);

        Call<List<Album>> queue = api.getAlbumAboveId(schoolId, id);
        queue.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                if(response.isSuccessful()) {
                    listener.onRecentAlbumsReceived(response.body());
                } else {
                    listener.onError(App.getInstance().getString(R.string.request_error));
                }
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {
                listener.onError(App.getInstance().getString(R.string.network_error));
            }
        });
    }

    @Override
    public void getAlbums(long schoolId, final OnFinishedListener listener) {
        GalleryApi api = ApiClient.getAuthorizedClient().create(GalleryApi.class);

        Call<List<Album>> queue = api.getAlbums(schoolId);
        queue.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                if(response.isSuccessful()) {
                    listener.onAlbumsReceived(response.body());
                } else {
                    listener.onError(App.getInstance().getString(R.string.request_error));
                }
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {
                listener.onError(App.getInstance().getString(R.string.network_error));
            }
        });
    }

    @Override
    public void getRecentDeletedAlbums(long schoolId, long id, final OnFinishedListener listener) {
        GalleryApi api = ApiClient.getAuthorizedClient().create(GalleryApi.class);

        Call<List<DeletedAlbum>> queue = api.getDeletedAlbumsAboveId(schoolId, id);
        queue.enqueue(new Callback<List<DeletedAlbum>>() {
            @Override
            public void onResponse(Call<List<DeletedAlbum>> call, Response<List<DeletedAlbum>> response) {
                if(response.isSuccessful()) {
                    listener.onDeletedAlbumsReceived(response.body());
                } else {
                    listener.onError(App.getInstance().getString(R.string.request_error));
                }
            }

            @Override
            public void onFailure(Call<List<DeletedAlbum>> call, Throwable t) {
                listener.onError(App.getInstance().getString(R.string.network_error));
            }
        });
    }

    @Override
    public void getDeletedAlbums(long schoolId, final OnFinishedListener listener) {
        GalleryApi api = ApiClient.getAuthorizedClient().create(GalleryApi.class);

        Call<List<DeletedAlbum>> queue = api.getDeletedAlbums(schoolId);
        queue.enqueue(new Callback<List<DeletedAlbum>>() {
            @Override
            public void onResponse(Call<List<DeletedAlbum>> call, Response<List<DeletedAlbum>> response) {
                if(response.isSuccessful()) {
                    listener.onDeletedAlbumsReceived(response.body());
                } else {
                    listener.onError(App.getInstance().getString(R.string.request_error));
                }
            }

            @Override
            public void onFailure(Call<List<DeletedAlbum>> call, Throwable t) {
                listener.onError(App.getInstance().getString(R.string.network_error));
            }
        });
    }

}
