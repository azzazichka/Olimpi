package com.example.androidApp.presenter.server.service;

import com.example.androidApp.model.entity.UserEvent;

import io.reactivex.rxjava3.core.Single;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserEventApi {
    @POST("/api/user_events")
    Single<ResponseBody> createUserEvent(@Body UserEvent userEvent);
}
