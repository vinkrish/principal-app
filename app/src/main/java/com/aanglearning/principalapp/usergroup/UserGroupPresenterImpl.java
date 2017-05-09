package com.aanglearning.principalapp.usergroup;

import com.aanglearning.principalapp.model.GroupUsers;

/**
 * Created by Vinay on 01-04-2017.
 */

public class UserGroupPresenterImpl implements UserGroupPresenter,
        UserGroupInteractor.OnFinishedListener {

    private UserGroupView mView;
    private UserGroupInteractor mInteractor;

    UserGroupPresenterImpl(UserGroupView view, UserGroupInteractor interactor) {
        mView = view;
        mInteractor = interactor;
    }

    @Override
    public void getUserGroup(long groupId) {
        if (mView != null) {
            mView.showProgress();
            mInteractor.getUserGroup(groupId, this);
        }
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
    public void onUserGroupReceived(GroupUsers groupUsers) {
        if (mView != null) {
            mView.showUserGroup(groupUsers);
            mView.hideProgress();
        }
    }

}
