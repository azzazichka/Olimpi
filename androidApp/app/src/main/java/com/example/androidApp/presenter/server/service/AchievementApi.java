package com.example.androidApp.presenter.server.service;

import com.example.androidApp.model.entity.Achievement;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AchievementApi {
    @GET("/api/achievements")
    Single<List<Achievement>> getAchievements();

    @POST("/api/achievements")
    Single<ResponseBody> createAchievement(@Body Achievement achievement);

    @DELETE("/api/achievements")
    Single<ResponseBody> deleteAchievement(@Query("contestId") Long contestId);
}
