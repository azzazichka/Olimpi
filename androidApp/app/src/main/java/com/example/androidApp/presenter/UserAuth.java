package com.example.androidApp.presenter;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.androidApp.model.entity.User;
import com.example.androidApp.presenter.server.ServiceGenerator;
import com.example.androidApp.presenter.server.service.UserApi;
import com.example.androidApp.presenter.server.service.UserKeyApi;

import java.io.IOException;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAuth {

    public static Response<String> logIn(String email, String password) throws IOException {
        UserKeyApi userKeyApi = ServiceGenerator.createService(UserKeyApi.class);
        Call<String> logInCall = userKeyApi.getUserKey(email, password);

        return logInCall.execute();
    }

    public static Response<ResponseBody> register(String email, String password) throws IOException {
        UserApi userApi = ServiceGenerator.createService(UserApi.class);
        User user = new User(null, "Пользователь", email, password, 0);

        Call<ResponseBody> registerCall = userApi.registerUser(user);

        return registerCall.execute();
    }

    public static void logOut() {
        UserKeyApi userKeyApi = ServiceGenerator.createService(UserKeyApi.class);

        Call<ResponseBody> logOutCall = userKeyApi.logOutUser();
        logOutCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.i("AZZA", "log out success");
                } else {
                    Log.e("AZZA", "error on server while logging out");
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable throwable) {
                Log.e("AZZA", throwable.toString());
            }
        });
    }
}
