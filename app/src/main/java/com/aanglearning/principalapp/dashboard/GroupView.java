package com.aanglearning.principalapp.dashboard;

import com.aanglearning.principalapp.model.Groups;

import java.util.List;

/**
 * Created by Vinay on 02-04-2017.
 */

interface GroupView {
    void showProgress();

    void hideProgess();

    void showError(String message);

    void setRecentGroups(List<Groups> groups);

    void setGroups(List<Groups> groups);

    void setRecentPrincipalGroups(List<Groups> groups);

    void setPrincipalGroups(List<Groups> groups);
}
