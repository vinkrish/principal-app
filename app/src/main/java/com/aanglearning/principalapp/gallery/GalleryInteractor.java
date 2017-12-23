package com.aanglearning.principalapp.gallery;

import com.aanglearning.principalapp.model.Album;
import com.aanglearning.principalapp.model.Clas;
import com.aanglearning.principalapp.model.DeletedAlbum;
import com.aanglearning.principalapp.model.Section;

import java.util.List;

/**
 * Created by Vinay on 30-10-2017.
 */

interface GalleryInteractor {
    interface OnFinishedListener {
        void onError(String message);

        void onClassReceived(List<Clas> classList);

        void onSectionReceived(List<Section> sectionList);

        void onAlbumDeleted();

        void onRecentAlbumsReceived(List<Album> albumList);

        void onAlbumsReceived(List<Album> albumList);

        void onDeletedAlbumsReceived(List<DeletedAlbum> deletedAlbums);
    }

    void getClassList(long schoolId, GalleryInteractor.OnFinishedListener listener);

    void getSectionList(long classId, GalleryInteractor.OnFinishedListener listener);

    void deleteAlbum(DeletedAlbum deletedAlbum, GalleryInteractor.OnFinishedListener listener);

    void getAlbumsAboveId(long schoolId, long id, GalleryInteractor.OnFinishedListener listener);

    void getAlbums(long schoolId, GalleryInteractor.OnFinishedListener listener);

    void getRecentDeletedAlbums(long schoolId, long id, GalleryInteractor.OnFinishedListener listener);

    void getDeletedAlbums(long schoolId, GalleryInteractor.OnFinishedListener listener);
}
