package com.example.androidApp.presenter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.androidApp.model.entity.Contest;
import com.example.androidApp.presenter.server.ServiceGenerator;
import com.example.androidApp.presenter.server.service.ContestApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServerRequests {
    private static ServerRequests instance;
    MutableLiveData<List<Contest>> contestsData = new MutableLiveData<>();


    public static ServerRequests getInstance() {
        if (instance == null) {
            instance = new ServerRequests();
        }
        return instance;
    }

    public void updateContestsListBySubjects(List<String> subjects) {
        ContestApi contestApi = ServiceGenerator.createService(ContestApi.class);

        Call<List<Contest>> getContestsCall = contestApi.getContestsBySubjects(subjects);
        getContestsCall.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<List<Contest>> call, @NonNull Response<List<Contest>> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    Log.i("AZZA", "Got " + response.body().size() + " contests");
                    contestsData.postValue(response.body());
                } else {
                    Log.e("AZZA", "Response update contest not successful");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Contest>> call, @NonNull Throwable t) {
                Log.e("AZZA", "Failure with updating contest_list: " + t.toString());
            }
        });
    }

    public LiveData<List<Contest>> getContestsData() {
        return contestsData;
    }
}
