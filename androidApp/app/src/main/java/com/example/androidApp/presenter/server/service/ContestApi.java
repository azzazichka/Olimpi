package com.example.androidApp.presenter.server.service;

import com.example.androidApp.model.entity.Contest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ContestApi {
    @GET("api/contests")
    Call<List<Contest>> getContestsBySubjects(@Query("subjects_names") List<String> subjects_names);
}
