package com.aanglearning.principalapp.usergroup;

import com.aanglearning.principalapp.model.GroupUsers;

/**
 * Created by Vinay on 01-04-2017.
 */

interface UserGroupInteractor {

    interface OnFinishedListener {
        void onError(String message);

        void onUserGroupReceived(GroupUsers groupUsers);
    }

    void getUserGroup(long groupId, UserGroupInteractor.OnFinishedListener listener);
}
