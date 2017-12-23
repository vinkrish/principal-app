package com.aanglearning.principalapp.newalbum;

import com.aanglearning.principalapp.model.Album;
import com.aanglearning.principalapp.model.Clas;
import com.aanglearning.principalapp.model.Section;

import java.util.List;

/**
 * Created by Vinay on 19-12-2017.
 */

public interface NewAlbumView {
    void showProgress();

    void hideProgress();

    void showError(String message);

    void showClass(List<Clas> clasList);

    void showSection(List<Section> sectionList);

    void albumSaved(Album album);
}
