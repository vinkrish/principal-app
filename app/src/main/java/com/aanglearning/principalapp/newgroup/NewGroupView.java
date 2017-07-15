package com.aanglearning.principalapp.newgroup;

import com.aanglearning.principalapp.model.Clas;
import com.aanglearning.principalapp.model.Groups;
import com.aanglearning.principalapp.model.Section;

import java.util.List;

/**
 * Created by Vinay on 30-03-2017.
 */

interface NewGroupView {
    void showProgress();

    void hideProgress();

    void showError(String message);

    void showClass(List<Clas> clasList);

    void showSection(List<Section> sectionList);

    void groupSaved(Groups groups);
}
