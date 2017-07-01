package com.aanglearning.principalapp.login;

import com.aanglearning.principalapp.model.Credentials;

/**
 * Created by Vinay on 28-03-2017.
 */

interface LoginPresenter {

    void validateCredentials(Credentials credentials);

    void pwdRecovery(String username);

    void onDestroy();
}
