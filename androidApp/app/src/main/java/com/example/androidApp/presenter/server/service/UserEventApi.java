package com.example.androidApp.presenter.server.service;

import com.example.androidApp.model.entity.Achievement;
import com.example.androidApp.model.entity.UserEvent;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserEventApi {
    @POST("/api/user_events")
    Single<ResponseBody> createUserEvent(@Body UserEvent userEvent);

    @GET("/api/user_events/all")
    Single<List<UserEvent>> getUserEvents();

    @DELETE("/api/user_events")
    Single<ResponseBody> deleteUserEvent(@Query("id") Long userEventId);

    @GET("/api/user_events")
    Single<UserEvent> getUserEvent(@Query("userId") Long userId, @Query("contestId") Long contestId);
}
