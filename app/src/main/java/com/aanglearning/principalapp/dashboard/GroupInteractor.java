package com.aanglearning.principalapp.dashboard;

import com.aanglearning.principalapp.model.Authorization;
import com.aanglearning.principalapp.model.Groups;

import java.util.List;

/**
 * Created by Vinay on 02-04-2017.
 */

interface GroupInteractor {
    interface OnFinishedListener {
        void onError(String message);

        void onRecentGroupsReceived(List<Groups> groupsList);

        void onGroupsReceived(List<Groups> groupsList);

        void onRecentPrincipalGroupsReceived(List<Groups> groupsList);

        void onPrincipalGroupsReceived(List<Groups> groupsList);
    }

    void getGroupsAboveId(long schoolId, long id, GroupInteractor.OnFinishedListener listener);

    void getGroups(long schoolId, GroupInteractor.OnFinishedListener listener);

    void getPrincipalGroupsAboveId(long userId, long id, GroupInteractor.OnFinishedListener listener);

    void getPrincipalGroups(long teacherId, GroupInteractor.OnFinishedListener listener);
}
