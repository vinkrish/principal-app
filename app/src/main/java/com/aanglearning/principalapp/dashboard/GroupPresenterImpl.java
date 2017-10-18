package com.aanglearning.principalapp.dashboard;

import com.aanglearning.principalapp.model.Authorization;
import com.aanglearning.principalapp.model.Groups;

import java.util.List;

/**
 * Created by Vinay on 02-04-2017.
 */

class GroupPresenterImpl implements GroupPresenter, GroupInteractor.OnFinishedListener {
    private GroupView mView;
    private GroupInteractor mInteractor;

    GroupPresenterImpl(GroupView view, GroupInteractor interactor) {
        mView = view;
        mInteractor = interactor;
    }

    @Override
    public void getGroupsAboveId(long schoolId, long id) {
        if (mView != null) {
            mInteractor.getGroupsAboveId(schoolId, id, this);
        }
    }

    @Override
    public void getGroups(long schoolId) {
        if (mView != null) {
            mView.showProgress();
            mInteractor.getGroups(schoolId, this);
        }
    }

    @Override
    public void getPrincipalGroupsAboveId(long teacherId, long id) {
        if (mView != null) {
            mInteractor.getPrincipalGroupsAboveId(teacherId, id, this);
        }
    }

    @Override
    public void getPrincipalGroups(long teacherId) {
        if (mView != null) {
            mView.showProgress();
            mInteractor.getPrincipalGroups(teacherId, this);
        }
    }

    @Override
    public void onDestroy() {
        mView = null;
    }

    @Override
    public void onError(String message) {
        if (mView != null) {
            mView.hideProgess();
            mView.showError(message);
        }
    }

    @Override
    public void onRecentGroupsReceived(List<Groups> groupsList) {
        if (mView != null) {
            mView.hideProgess();
        }
    }

    @Override
    public void onGroupsReceived(List<Groups> groupsList) {
        if (mView != null) {
            mView.hideProgess();
            mView.setGroups(groupsList);
        }
    }

    @Override
    public void onRecentPrincipalGroupsReceived(List<Groups> groupsList) {
        if (mView != null) {
            mView.setRecentPrincipalGroups(groupsList);
        }
    }

    @Override
    public void onPrincipalGroupsReceived(List<Groups> groupsList) {
        if (mView != null) {
            mView.hideProgess();
            mView.setPrincipalGroups(groupsList);
        }
    }

}
