package com.aanglearning.principalapp.dashboard;

import com.aanglearning.principalapp.model.Authorization;

/**
 * Created by Vinay on 02-04-2017.
 */

interface GroupPresenter {
    void getGroupsAboveId(long schoolId, long id);

    void getGroups(long schoolId);

    void getPrincipalGroupsAboveId(long teacherId, long id);

    void getPrincipalGroups(long teacherId);

    void onDestroy();
}
