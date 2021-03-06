package com.aanglearning.principalapp.messagegroup;

import com.aanglearning.principalapp.dao.DeletedMessageDao;
import com.aanglearning.principalapp.model.DeletedMessage;
import com.aanglearning.principalapp.model.Message;

import java.util.List;

/**
 * Created by Vinay on 07-04-2017.
 */

class MessagePresenterImpl implements MessagePresenter, MessageInteractor.OnFinishedListener {
    private MessageView mView;
    private MessageInteractor mInteractor;

    MessagePresenterImpl(MessageView view, MessageInteractor interactor) {
        mView = view;
        mInteractor = interactor;
    }

    @Override
    public void saveMessage(Message message) {
        mView.showProgress();
        mInteractor.saveMessage(message, this);
    }

    @Override
    public void getRecentMessages(long groupId, long messageId) {
        mInteractor.getRecentMessages(groupId, messageId, this);
    }

    @Override
    public void getMessages(long groupId) {
        mView.showProgress();
        mInteractor.getMessages(groupId, this);
    }

    @Override
    public void deleteMessage(DeletedMessage deletedMessage) {
        mView.showProgress();
        mInteractor.deleteMessage(deletedMessage, this);
    }

    @Override
    public void getRecentDeletedMessages(long groupId, long id) {
        mInteractor.getRecentDeletedMessages(groupId, id, this);
    }

    @Override
    public void getDeletedMessages(long groupId) {
        mInteractor.getDeletedMessages(groupId, this);
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
    public void onMessageSaved(Message message) {
        if (mView != null) {
            mView.onMessageSaved(message);
            mView.hideProgress();
        }
    }

    @Override
    public void onRecentMessagesReceived(List<Message> messages) {
        if (mView != null) {
            mView.showRecentMessages(messages);
            mView.hideProgress();
        }
    }

    @Override
    public void onMessageReceived(List<Message> messages) {
        if (mView != null) {
            mView.showMessages(messages);
            mView.hideProgress();
        }
    }

    @Override
    public void onMessageDeleted(DeletedMessage deletedMessage) {
        if (mView != null) {
            mView.onMessageDeleted(deletedMessage);
            mView.hideProgress();
        }
    }

    @Override
    public void onDeletedMessagesReceived(List<DeletedMessage> messages) {
        if (mView != null) {
            DeletedMessageDao.insertDeletedMessages(messages);
        }
    }
}
