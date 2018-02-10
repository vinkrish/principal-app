package com.aanglearning.principalapp.api;

import com.aanglearning.principalapp.model.Authorization;
import com.aanglearning.principalapp.model.Credentials;
import com.aanglearning.principalapp.model.TeacherCredentials;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Vinay on 16-02-2017.
 */

public interface AuthApi {

    @Headers("content-type: application/json")
    @POST("teacher/principal/v2/login")
    Call<TeacherCredentials> login(@Body Credentials credentials);

    @Headers("content-type: application/json")
    @POST("sms/principal/{username}")
    Call<Void> newPassword(@Path("username") String username);

    @Headers("content-type: application/json")
    @POST("authorization/fcm")
    Call<Void> updateFcmToken(@Body Authorization authorization);

}
