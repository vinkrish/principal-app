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

        void onGroupsReceived(List<Groups> groupsList);
    }

    void getGroups(long userId, GroupInteractor.OnFinishedListener listener);

    void updateFcmToken(Authorization authorization);
}
