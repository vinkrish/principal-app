package com.aanglearning.principalapp.login;

import com.aanglearning.principalapp.model.Credentials;
import com.aanglearning.principalapp.model.TeacherCredentials;

/**
 * Created by Vinay on 28-03-2017.
 */

interface LoginInteractor {
    interface OnLoginFinishedListener{

        void onSuccess(TeacherCredentials credentials);

        void onPwdRecovered();

        void onNoUser();

        void onError(String message);
    }

    void login(Credentials credentials, OnLoginFinishedListener listener);

    void recoverPwd(String authToken, String newPassword, OnLoginFinishedListener listener);
}
