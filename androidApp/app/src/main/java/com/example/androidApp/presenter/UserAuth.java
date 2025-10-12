package com.example.androidApp.presenter;

import android.text.Editable;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.androidApp.model.entity.User;
import com.example.androidApp.presenter.server.ServiceGenerator;
import com.example.androidApp.presenter.server.service.UserKeyApi;

import java.io.IOException;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAuth {

    public static Response<String> logIn(String email, String password) throws IOException {
        UserKeyApi userKeyApi = ServiceGenerator.createService(UserKeyApi.class);
        Call<String> logInCall = userKeyApi.getUserKey(email, password);


        return logInCall.execute();
    }

    public static void register(String email, String password) {

    }
}
