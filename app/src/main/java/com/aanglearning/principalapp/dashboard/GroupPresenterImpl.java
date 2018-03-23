package com.aanglearning.principalapp.dashboard;

import com.aanglearning.principalapp.dao.DeletedGroupDao;
import com.aanglearning.principalapp.model.Authorization;
import com.aanglearning.principalapp.model.DeletedGroup;
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
        mInteractor.getGroupsAboveId(schoolId, id, this);
    }

    @Override
    public void getGroups(long schoolId) {
        mView.showProgress();
        mInteractor.getGroups(schoolId, this);
    }

    @Override
    public void getPrincipalGroupsAboveId(long teacherId, long id) {
        mInteractor.getPrincipalGroupsAboveId(teacherId, id, this);
    }

    @Override
    public void getPrincipalGroups(long teacherId) {
        mView.showProgress();
        mInteractor.getPrincipalGroups(teacherId, this);
    }

    @Override
    public void getRecentDeletedGroups(long schoolId, long id) {
        mInteractor.getRecentDeletedGroups(schoolId, id, this);
    }

    @Override
    public void getDeletedGroups(long schoolId) {
        mInteractor.getDeletedGroups(schoolId, this);
    }

    @Override
    public void onDestroy() {
        mView = null;
    }

    @Override
    public void onError(String message) {
        if (mView != null) {
            mView.hideProgress();
            mView.showError(message);
        }
    }

    @Override
    public void onRecentGroupsReceived(List<Groups> groupsList) {
        if (mView != null) {
            mView.setRecentGroups(groupsList);
            mView.hideProgress();
        }
    }

    @Override
    public void onGroupsReceived(List<Groups> groupsList) {
        if (mView != null) {
            mView.setGroups(groupsList);
            mView.hideProgress();
        }
    }

    @Override
    public void onRecentPrincipalGroupsReceived(List<Groups> groupsList) {
        if (mView != null) {
            mView.setRecentPrincipalGroups(groupsList);
            mView.hideProgress();
        }
    }

    @Override
    public void onPrincipalGroupsReceived(List<Groups> groupsList) {
        if (mView != null) {
            mView.setPrincipalGroups(groupsList);
            mView.hideProgress();
        }
    }

    @Override
    public void onDeletedGroupsReceived(List<DeletedGroup> deletedGroups) {
        if (mView != null) {
            DeletedGroupDao.insertDeletedGroups(deletedGroups);
        }
    }

}
