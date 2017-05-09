package com.aanglearning.principalapp.usergroup;

import com.aanglearning.principalapp.model.GroupUsers;

/**
 * Created by Vinay on 01-04-2017.
 */

interface UserGroupView {
    void showProgress();

    void hideProgress();

    void showError(String message);

    void showUserGroup(GroupUsers groupUsers);
}
