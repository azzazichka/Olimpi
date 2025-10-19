package com.example.androidApp.presenter;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.androidApp.model.entity.Achievement;
import com.example.androidApp.model.entity.Attachment;
import com.example.androidApp.model.entity.Contest;
import com.example.androidApp.model.entity.Subject;
import com.example.androidApp.model.entity.User;
import com.example.androidApp.presenter.server.ServiceGenerator;
import com.example.androidApp.presenter.server.service.AchievementApi;
import com.example.androidApp.presenter.server.service.AttachmentApi;
import com.example.androidApp.presenter.server.service.ContestApi;
import com.example.androidApp.presenter.server.service.SubjectApi;
import com.example.androidApp.presenter.server.service.UserApi;
import com.example.androidApp.presenter.server.service.UserEventApi;
import com.example.androidApp.presenter.server.service.UserKeyApi;

import java.io.IOException;
import java.util.List;


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
        logOutCall.enqueue(new Callback<>() {
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

    public static void loadAllTables() {
        UserApi userApi = ServiceGenerator.createService(UserApi.class);
        AchievementApi achievementApi = ServiceGenerator.createService(AchievementApi.class);
        AttachmentApi attachmentApi = ServiceGenerator.createService(AttachmentApi.class);
        UserEventApi userEventApi = ServiceGenerator.createService(UserEventApi.class);



//        Call<User> userCall = userApi.getUser();
//        Call<List<Achievement>> achievementsCall = achievementApi.getUserAchievements();
//        Call<List<userEventApi>>
//        Call<List<Attachment>> attachmentsCall = attachmentApi.getUserAttachments();

    }
}


// TODO: переделать x-api-logic, сделать только один ключ у юзера, рассмотреть вариант, когда у двух юзеров одинаковый api ключ