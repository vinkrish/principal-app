package com.aanglearning.principalapp.dashboard;

import com.aanglearning.principalapp.model.Authorization;

/**
 * Created by Vinay on 02-04-2017.
 */

interface GroupPresenter {
    void getGroups(long teacherId);

    void updateFcmToken(Authorization authorization);

    void onDestroy();
}
