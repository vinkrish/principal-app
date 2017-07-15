package com.aanglearning.principalapp.newgroup;

import com.aanglearning.principalapp.model.Groups;

/**
 * Created by Vinay on 30-03-2017.
 */

interface NewGroupPresenter {
    void getClassList(long schoolId);

    void getSectionList(long classId);

    void saveGroup(Groups groups);

    void onDestroy();
}
