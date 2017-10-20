package com.aanglearning.principalapp.usergroup;

import com.aanglearning.principalapp.model.DeletedGroup;
import com.aanglearning.principalapp.model.UserGroup;

import java.util.ArrayList;

/**
 * Created by Vinay on 01-04-2017.
 */

interface UserGroupPresenter {
    void getUserGroup(long groupId);

    void saveUserGroup(ArrayList<UserGroup> userGroups);

    void deleteUsers(ArrayList<UserGroup> userGroups);

    void deleteGroup(DeletedGroup deletedGroup);

    void onDestroy();
}
