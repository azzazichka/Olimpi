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

    @GET("/api/achievements/get_one")
    Single<Achievement> getAchievement(@Query("userId") Long userId, @Query("contestId") Long contestId);

    @POST("/api/achievements")
    Single<ResponseBody> createAchievement(@Body Achievement achievement);

    @DELETE("/api/achievements")
    Single<ResponseBody> deleteAchievement(@Query("contestId") Long contestId);
}
