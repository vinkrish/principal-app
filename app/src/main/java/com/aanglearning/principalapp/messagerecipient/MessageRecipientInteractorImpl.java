package com.aanglearning.principalapp.messagerecipient;

import com.aanglearning.principalapp.App;
import com.aanglearning.principalapp.R;
import com.aanglearning.principalapp.api.ApiClient;
import com.aanglearning.principalapp.api.PrincipalApi;
import com.aanglearning.principalapp.model.MessageRecipient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vinay on 25-08-2017.
 */

class MessageRecipientInteractorImpl implements MessageRecipientInteractor {
    @Override
    public void getMessageRecipient(long groupId, long groupMessageId, final OnFinishedListener listener) {
        PrincipalApi api = ApiClient.getAuthorizedClient().create(PrincipalApi.class);

        Call<ArrayList<MessageRecipient>> queue = api.getMessageRecipients(groupId, groupMessageId);
        queue.enqueue(new Callback<ArrayList<MessageRecipient>>() {
            @Override
            public void onResponse(Call<ArrayList<MessageRecipient>> call, Response<ArrayList<MessageRecipient>> response) {
                if(response.isSuccessful()) {
                    listener.onMessageRecipientReceived(response.body());
                } else {
                    listener.onError(App.getInstance().getString(R.string.request_error));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<MessageRecipient>> call, Throwable t) {
                listener.onError(App.getInstance().getString(R.string.network_error));
            }
        });
    }

    @Override
    public void getSchoolRecipient(long groupId, long groupMessageId, final OnFinishedListener listener) {
        PrincipalApi api = ApiClient.getAuthorizedClient().create(PrincipalApi.class);

        Call<ArrayList<MessageRecipient>> queue = api.getSchoolRecipients(groupId, groupMessageId);
        queue.enqueue(new Callback<ArrayList<MessageRecipient>>() {
            @Override
            public void onResponse(Call<ArrayList<MessageRecipient>> call, Response<ArrayList<MessageRecipient>> response) {
                if(response.isSuccessful()) {
                    listener.onMessageRecipientReceived(response.body());
                } else {
                    listener.onError(App.getInstance().getString(R.string.request_error));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<MessageRecipient>> call, Throwable t) {
                listener.onError(App.getInstance().getString(R.string.network_error));
            }
        });
    }

    @Override
    public void getSchoolRecipientFromId(long groupId, long groupMessageId, long id, final OnFinishedListener listener) {
        PrincipalApi api = ApiClient.getAuthorizedClient().create(PrincipalApi.class);

        Call<ArrayList<MessageRecipient>> queue = api.getSchoolRecipientsFromId(groupId, groupMessageId, id);
        queue.enqueue(new Callback<ArrayList<MessageRecipient>>() {
            @Override
            public void onResponse(Call<ArrayList<MessageRecipient>> call, Response<ArrayList<MessageRecipient>> response) {
                if(response.isSuccessful()) {
                    listener.onFollowUpRecipientReceived(response.body());
                } else {
                    listener.onError(App.getInstance().getString(R.string.request_error));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<MessageRecipient>> call, Throwable t) {
                listener.onError(App.getInstance().getString(R.string.network_error));
            }
        });
    }
}
