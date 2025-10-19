package com.example.androidApp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidApp.model.entity.User;
import com.example.androidApp.presenter.server.ServiceGenerator;
import com.example.androidApp.view.AuthFragment;
import com.example.androidApp.view.ProfileFragment;
import com.example.androidapp.R;

public class MainActivity extends AppCompatActivity {
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        String api_key = sharedPref.getString("api_key", "");

        if (api_key.isEmpty()) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, AuthFragment.class, null)
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, ProfileFragment.class, null)
                    .commit();
            ServiceGenerator.updateKey(api_key);
        }
    }


}