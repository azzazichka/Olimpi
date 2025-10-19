package com.example.androidApp.presenter.server.service;

import com.example.androidApp.model.entity.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserApi {
    @POST("/api/users")
    Call<ResponseBody> registerUser(@Body User user);

    @GET("/api/users")
    Call<User> getUser(String key);
}
