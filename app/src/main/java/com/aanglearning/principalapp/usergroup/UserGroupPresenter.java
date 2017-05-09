package com.aanglearning.principalapp.usergroup;

import com.aanglearning.principalapp.model.UserGroup;

import java.util.ArrayList;

/**
 * Created by Vinay on 01-04-2017.
 */

public interface UserGroupPresenter {
    void getUserGroup(long groupId);

    void onDestroy();
}
