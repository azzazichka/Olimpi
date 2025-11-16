package com.example.androidApp.presenter.server.requests;

import androidx.lifecycle.MutableLiveData;

import com.example.androidApp.model.entity.Contest;

import java.util.List;

public class UserEventRequests {
    private static ContestRequests instance;


    public static ContestRequests getInstance() {
        if (instance == null) {
            instance = new ContestRequests();
        }
        return instance;
    }

}
