package com.aanglearning.principalapp.gallery;

import com.aanglearning.principalapp.model.Album;
import com.aanglearning.principalapp.model.Clas;
import com.aanglearning.principalapp.model.Section;

import java.util.List;

/**
 * Created by Vinay on 30-10-2017.
 */

interface GalleryView {
    void showProgress();

    void hideProgress();

    void showError(String message);

    void setAlbum(Album album);

    void albumDeleted();

    void setRecentAlbums(List<Album> albums);

    void setAlbums(List<Album> albums);

    void showClass(List<Clas> clasList);

    void showSection(List<Section> sectionList);
}
