package com.aanglearning.principalapp.newgroup;

import com.aanglearning.principalapp.model.Clas;
import com.aanglearning.principalapp.model.Groups;
import com.aanglearning.principalapp.model.Section;

import java.util.List;

/**
 * Created by Vinay on 30-03-2017.
 */

interface NewGroupInteractor {
    interface OnFinishedListener {
        void onError(String message);

        void onClasReceived(List<Clas> clasList);

        void onSectionReceived(List<Section> sectionList);

        void onGroupSaved(Groups groups);
    }

    void getClassList(long schoolId, NewGroupInteractor.OnFinishedListener listener);

    void getSectionList(long classId, NewGroupInteractor.OnFinishedListener listener);

    void saveGroup(Groups groups, NewGroupInteractor.OnFinishedListener listener);
}
