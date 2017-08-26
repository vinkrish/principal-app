package com.aanglearning.principalapp.usergroup;

import com.aanglearning.principalapp.model.GroupUsers;
import com.aanglearning.principalapp.model.UserGroup;

import java.util.ArrayList;

/**
 * Created by Vinay on 01-04-2017.
 */

interface UserGroupInteractor {

    interface OnFinishedListener {
        void onError(String message);

        void onUserGroupReceived(GroupUsers groupUsers);

        void onUserGroupSaved();

        void onUsersDeleted();
    }

    void getUserGroup(long groupId, UserGroupInteractor.OnFinishedListener listener);

    void saveUserGroup(ArrayList<UserGroup> userGroups, UserGroupInteractor.OnFinishedListener listener);

    void deleteUsers(ArrayList<UserGroup> userGroups, UserGroupInteractor.OnFinishedListener listener);
}
