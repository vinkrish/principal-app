package com.aanglearning.principalapp.chathome;

/**
 * Created by Vinay on 28-04-2017.
 */

interface ChatsPresenter {
    void getChats(long teacherId);

    void onDestroy();
}
