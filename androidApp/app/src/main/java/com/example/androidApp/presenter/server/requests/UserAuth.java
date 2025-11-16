package com.example.androidApp.presenter.server.requests;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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
    private static UserAuth instance;
    private User user;
    private final MutableLiveData<Boolean> authData = new MutableLiveData<>();

    public LiveData<Boolean> getAuthData() {
        return authData;
    }

    public static UserAuth getInstance() {
        if (instance == null) {
            instance = new UserAuth();
        }
        return instance;
    }

    public User getUser() {
        return user;
    }

    public void userAuth(String key) {
        if (key.isEmpty()) {
            authData.setValue(false);
            return;
        }

        ServiceGenerator.updateKey(key);

        UserApi userApi = ServiceGenerator.createService(UserApi.class);
        Call<User> userCall = userApi.getUser();

        userCall.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful()) {
                    user = response.body();
                    assert user != null;
                    user.setApi_key(key);

                    authData.postValue(true);
                } else {
                    user = null;
                    authData.postValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                Log.e("AZZA", t.toString());
                authData.postValue(false);
            }
        });
    }

    public Response<String> logIn(String email, String password) throws IOException {
        UserKeyApi userKeyApi = ServiceGenerator.createService(UserKeyApi.class);
        Call<String> logInCall = userKeyApi.getUserKey(email, password);

        return logInCall.execute();
    }

    public Response<ResponseBody> register(String email, String password) throws IOException {
        UserApi userApi = ServiceGenerator.createService(UserApi.class);
        User user = new User(null, "Пользователь", email, password, 0);

        Call<ResponseBody> registerCall = userApi.registerUser(user);

        return registerCall.execute();
    }

    public void logOut() {
        UserKeyApi userKeyApi = ServiceGenerator.createService(UserKeyApi.class);

        Call<ResponseBody> logOutCall = userKeyApi.logOutUser();
        logOutCall.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.i("AZZA", "log out success");
                } else {
                    Log.e("AZZA", "error on server :" + response.toString());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable throwable) {
                Log.e("AZZA", throwable.toString());
            }
        });
    }
}


// TODO: переделать x-api-logic, сделать только один ключ у юзера, рассмотреть вариант, когда у двух юзеров одинаковый api ключ