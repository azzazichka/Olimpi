package com.example.androidApp.presenter.server.service;

import com.example.androidApp.model.entity.Contest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;

public interface ContestApi {
    @GET("api/contests")
    Call<List<Contest>> getContestsBySubjects(@Body List<String> subjects_names);
}
