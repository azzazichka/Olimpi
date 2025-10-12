package com.example.androidApp.presenter.server.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface UserKeyApi {
    @GET("/api/users_keys")
    public Call<String> getUserKey(@Header("email") String email, @Header("password") String password);

}
