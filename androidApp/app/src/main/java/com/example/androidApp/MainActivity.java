package com.example.androidApp;

import static android.view.View.GONE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.androidApp.model.entity.User;
import com.example.androidApp.presenter.UserAuth;
import com.example.androidApp.presenter.server.ServiceGenerator;
import com.example.androidApp.view.AuthFragment;
import com.example.androidApp.view.ProfileFragment;
import com.example.androidapp.R;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPref;
    ContentLoadingProgressBar loading_progress_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        loading_progress_bar = findViewById(R.id.loading);


        sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        String api_key = sharedPref.getString("api_key", "");

        LiveData<Boolean> authData = UserAuth.getInstance().getAuthData();
        authData.observe(this, authed -> {
            if (Boolean.FALSE.equals(authed)) {
                logOutUser();
            } else {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, ProfileFragment.class, null)
                        .commit();
                ServiceGenerator.updateKey(api_key);
            }

            loading_progress_bar.hide();
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        });

        loading_progress_bar.show();
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        UserAuth.getInstance().userAuth(api_key);
    }

    public void logOutUser() {
        UserAuth.getInstance().logOut();

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("api_key", "");
        editor.apply();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, AuthFragment.class, null)
                .commit();
    }


}